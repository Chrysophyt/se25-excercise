#include "Database.h"

#include <iostream>
#include <sstream>

void parseArgs(int argc, char *const *argv, DatabaseConfig &config) {
    for (int i = 1; i < argc; i++) {
        std::string arg = argv[i];
        auto argStart = arg.find_first_not_of('-');
        arg = arg.substr(argStart);

        if (arg == "read") { config.read = true; }
        else if (arg == "write") { config.write = true; }
        else if (arg == "transaction") { config.transaction = true; }
        else if (arg == "logging") { config.logging = true; }
        else if (arg == "storage-type=list") { config.storageType = StorageType::LIST; }
        else if (arg == "storage-type=map") { config.storageType = StorageType::MAP; }
        else { std::cout << "Unknown option '" << arg << "'\n"; }
    }
}

void parseLine(std::string const &line, std::vector<std::string> &args) {
    std::string arg;
    auto is = std::stringstream(line);
    while (std::getline(is, arg, ' ')) {
        args.push_back(arg);
    }
}

void startREPL(Database &db) {
    std::string line;
    std::cout << "/db/> " << std::flush;
    while (std::getline(std::cin, line)) {
        std::vector<std::string> args;
        parseLine(line, args);

        if (args.empty()) {
            continue;
        }

        try {
            if (args[0] == "read" && args.size() == 2) {
                auto val = db.read(args[1]);
                std::cout << "Read: " << args[1] << "=" << val.value_or("<empty>") << "\n";
            } else if (args[0] == "write" && args.size() == 3) {
                db.write(args[1], args[2]);
                std::cout << "Write: " << args[1] << "=" << args[2] << "\n";
            } else if (args[0] == "commit" && args.size() == 1) {
                auto numEntries = db.commit();
                std::cout << "Commit: " << numEntries << " entries\n";
            } else if (args[0] == "rollback" && args.size() == 1) {
                auto numEntries = db.rollback();
                std::cout << "Rollback: " << numEntries << " entries\n";
            } else if (args[0] == "exit") {
                break;
            } else {
                std::cout << "Unknown command.\n";
            }
        } catch (ConfigurationError &e) {
            std::cout << "Command not supported by current configuration!\n";
        }

        std::cout << "/db/> " << std::flush;
    }
}

/// Start the database.
///
/// You can use the following commandline parameters:
/// --read: enable read operations
/// --write: enable write operations
/// --transaction: enable transactions
/// --logging: enable logging
/// --storage-type=[list|map]: select a storage type
///
/// Afterwards, you can use the following commands to interact with
/// the database (depending on which features are selected:
/// read <key>
/// write <key> <value>
/// commit
/// rollback
/// exit
int main(int argc, char *argv[]) {
    DatabaseConfig config{};
    parseArgs(argc, argv, config);
    auto db = Database(config);
    startREPL(db);
}
