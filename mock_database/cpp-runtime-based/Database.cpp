#include "Database.h"
#include <iostream>



Database::Database(DatabaseConfig config) {
    // Storage
    switch (config.storageType) {
        case StorageType::LIST:
            storage_ = std::make_unique<ListStorage>();
            break;
        case StorageType::MAP: 
            storage_ = std::make_unique<MapStorage>(); 
            break;
        default:
            throw ConfigurationError();
    }

    // Logging
    if (config.logging) {
        loggingEnabled_ = true;
    }

    // Access
    if (!config.read || !config.write) {
        throw ConfigurationError();
    }

    // Transactions
    if (config.transaction) {
        transactionEnabled_ = true;
    }

}

std::optional <std::string> Database::read(std::string key) {
    if (loggingEnabled_) {
        std::cout << "[LOG] READ " << key << "\n";
    }

    if (transactionEnabled_) {
        auto it = transactionBuffer_.find(key);
        if (it != transactionBuffer_.end()) {
            return it->second;
        }
    }

    return storage_->get(key);
}

void Database::write(std::string key, std::string value) {
    if (loggingEnabled_) {
        std::cout << "[LOG] Write " << key <<":"<< value << "\n";
    }

    if (transactionEnabled_) {
        transactionBuffer_[key] = value;
    } else {
        storage_->put(key, value);
    }
}

size_t Database::commit() {
    int entries = transactionBuffer_.size();

    if (loggingEnabled_) {
        std::cout << "[LOG] Committing \n";
    }

    if (transactionEnabled_) {
        for (auto const& [key, val] : transactionBuffer_) {
            storage_->put(key, val);
        }
        transactionBuffer_.clear();
    }

    return entries;
}

size_t Database::rollback() {
    int entries = transactionBuffer_.size();

    if (loggingEnabled_) {
        std::cout << "[LOG] Rolling Back \n";
    }

    if (transactionEnabled_) {
        transactionBuffer_.clear();
    }

    return entries;
}
