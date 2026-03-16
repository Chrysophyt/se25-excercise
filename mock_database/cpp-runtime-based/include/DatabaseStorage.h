#include <algorithm>
#include <functional>
#include <map>
#include <optional>
#include <string>
#include <utility>
#include <vector>

enum class StorageType {
    MAP, LIST
};

/// Interface for database storage implementations.
class Storage {
protected:
    explicit Storage(StorageType storageType) : storageType(storageType) {}
public:
    virtual void put(std::string key, std::string value) = 0;
    [[nodiscard]] virtual std::optional<std::string> get(std::string key) const = 0;
    [[nodiscard]] virtual size_t size() const = 0;
    virtual void clear() = 0;
    virtual void forEach(std::function<void(const std::string&, const std::string&)> f) const = 0;

    const StorageType storageType;
};


/// Map-based storage implementation.
class MapStorage : public Storage {
public:
    MapStorage() : Storage(StorageType::MAP) {}

    void put(std::string key, std::string value) override {
        storage[key] = value;
    }

    [[nodiscard]] std::optional<std::string> get(std::string key) const override {
        if (storage.find(key) == storage.end()) {
            return {};
        }
        return storage.at(key);
    }

    [[nodiscard]] size_t size() const override {
        return storage.size();
    };

    void clear() override {
        storage.clear();
    }

    void forEach(std::function<void(const std::string &, const std::string &)> f) const override {
        for (const auto &[k, v] : storage) {
            f(k, v);
        }
    }

private:
    std::map<std::string, std::string> storage;
};


/// List-based storage implementation.
class ListStorage : public Storage {
public:
    ListStorage() : Storage(StorageType::LIST) {}

    void put(std::string key, std::string value) override {
        auto existingEntry = std::find_if(storage.begin(), storage.end(),
                                          [&key](auto entry){ return entry.first == key; });
        if (existingEntry != storage.end()) {
            existingEntry->second = value;
        } else {
            storage.emplace_back(key, value);
        }
    }

    [[nodiscard]] std::optional<std::string> get(std::string key) const override {
        for (auto const& [k, v]: storage) {
            if (k == key) {
                return v;
            }
        }
        return {};
    }

    [[nodiscard]] size_t size() const override {
        return storage.size();
    };

    void clear() override {
        storage.clear();
    }

    void forEach(std::function<void(const std::string &, const std::string &)> f) const override {
        for (const auto &[k, v] : storage) {
            f(k, v);
        }
    }

private:
    std::vector<std::pair<std::string, std::string>> storage;
};