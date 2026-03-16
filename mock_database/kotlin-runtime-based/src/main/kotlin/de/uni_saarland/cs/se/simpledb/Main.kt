package de.uni_saarland.cs.se.simpledb

data class DatabaseConfig(
    var read: Boolean = false,
    var write: Boolean = false,
    var transaction: Boolean = false,
    var logging: Boolean = false,
    var storageType: StorageType = StorageType.MAP,
)

fun parseArgs(args: Array<String>): Database {
    val config = DatabaseConfig()
    for (arg in args) {
        when (arg.dropWhile { it == '-' }) {
            "read" -> config.read = true
            "write" -> config.write = true
            "transaction" -> config.transaction = true
            "logging" -> config.logging = true
            "storage-type=list" -> config.storageType = StorageType.LIST
            "storage-type=map" -> config.storageType = StorageType.MAP
            else -> {
                println("Unknown argument $arg")
            }
        }
    }

    var db: Database = when (config.storageType) {
        StorageType.MAP -> MapStorageDatabase()
        StorageType.LIST -> ListStorageDatabase()
    } as Database
    // if (config.read) db = Read(db)
    // if (config.write) db = Write(db)
    if (config.transaction) db = Transaction(db)
    // if (config.logging) db = Logging(db)

    return db
}

class ListStorageDatabase : Database {
    override fun read(key: String): String? {
        println("CORE LSB: Retrieving data from list storage for key: $key")
        return storage[key]
    }
    override fun write(key: String, value: String) {
        println("CORE LSB: Writing data to list $key : $value")
        storage[key] = value
    }
    override fun commit(): Int {
        println("CORE LSB: Commit called on non-transactional storage. Ignoring.")
        return 0
    }
    override fun rollback(): Int {
        println("CORE LSB: Rollback called on non-transactional storage. Ignoring.")
        return 0
    }
    override val storageType: StorageType = StorageType.LIST;
    override val storage: Storage = ListStorage();
}

class MapStorageDatabase() : Database {
    override fun read(key: String): String? {
        println("CORE LSB: Retrieving data from list storage for key: $key")
        return storage[key]
    }
    override fun write(key: String, value: String) {
        println("CORE LSB: Writing data to list $key : $value")
        storage[key] = value
    }
    override fun commit(): Int {
        println("CORE LSB: Commit called on non-transactional storage. Ignoring.")
        return 0
    }
    override fun rollback(): Int {
        println("CORE LSB: Rollback called on non-transactional storage. Ignoring.")
        return 0
    }
    override val storageType: StorageType = StorageType.MAP;
    override val storage: Storage = MapStorage();
}

fun startREPL(db: Database) {
    print("/db/> ")
    var line = readlnOrNull()
    while (line != null) {
        val args = line.split(" ");

        if (args.isEmpty()) {
            continue
        }

        try {
            when {
                args[0] == "read" && args.size == 2 -> {
                    val value = db.read(args[1])
                    println("Read: ${args[1]}=${value ?: "<empty>"}")
                }
                args[0] == "write" && args.size == 3 -> {
                    db.write(args[1], args[2])
                    println("Write: ${args[1]}=${args[2]}")
                }
                args[0] == "commit" && args.size == 1 -> {
                    val numEntries = db.commit ()
                    println("Commit: $numEntries entries")
                }
                args[0] == "rollback" && args.size == 1 -> {
                    val numEntries = db.rollback ()
                    println("Rollback: $numEntries entries")
                }
                args[0] == "exit" -> {
                    break
                }
                else -> {
                    println("Unknown command.")
                }
            }
        } catch (e: ConfigurationError) {
            println("Command not supported by current configuration!")
        }

        print("/db/> ")
        line = readlnOrNull()
    }
}

fun main(args: Array<String>) {
    val db = parseArgs(args)
    startREPL(db);
}