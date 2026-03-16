#include "DatabaseStorage.h"

#include <exception>
#include <memory>
#include <map>

/// Exception that should be thrown if a function is called that is not
/// supported by the current configuration.
class ConfigurationError : public std::exception {};

/// Configuration class for databases.
///
/// \param read whether the database should support read operations
/// \param write whether the database should support write operations
/// \param transaction whether the database should support transactions
/// \param logging whether the database should support logging
/// \param storageType the type of storage the database should use
struct DatabaseConfig {
    bool read = false;
    bool write = false;
    bool transaction = false;
    bool logging = false;
    StorageType storageType = StorageType::LIST;
};

/// A runtime-configurable version of our database SPL.
class Database {
public:
    explicit Database(DatabaseConfig config);

    /// Read a value from the database.
    ///
    /// \param key the key under which the value is stored
    /// \return the stored value if present or an empty option
    std::optional<std::string> read(std::string key);

    /// Read a value from the database.
    ///
    /// \param key the key under which the value is stored
    /// \return the stored value if present or an empty option
    void write(std::string key, std::string value);

    /// Commit all pending writes.
    ///
    /// \return the number of committed write operations.
    size_t commit();

    /// Roll back all pending writes.
    ///
    /// \return the number of roll-backed write operations.
    size_t rollback();

private:
    std::unique_ptr<Storage> storage_;
    bool loggingEnabled_ = false;
    bool transactionEnabled_ = false;
    std::map<std::string, std::string> transactionBuffer_;
};
