package de.uni_saarland.cs.se.simpledb

class ConfigurationError : RuntimeException()

/**
 * Database interface.
 */
interface Database {
    fun read(key: String): String?
    fun write(key: String, value: String): Unit
    fun commit(): Int
    fun rollback(): Int
    val storageType: StorageType
    val storage: Storage
}

/**
 * The decorator interface.
 *
 * All concrete decorators must inherit from this.
 *
 * @param database the decorated database
 */
abstract class DatabaseDecorator(private val database: Database) : Database by database {}

class Transaction(db: Database) : DatabaseDecorator(db) {
    private val transactionCache = mutableMapOf<String, String?>()
    private var inTransaction: Boolean = true

    override fun read(key: String): String? {
        if (inTransaction) {
            // Check the local cache first
            if (transactionCache.containsKey(key)) {
                println("TRANSACTION: Reading key $key from local cache.")
                return transactionCache[key]
            }
        }
        // If not in cache, delegate the read to the wrapped database
        println("TRANSACTION: Key $key not in cache, delegating read to underlying DB.")
        return super.read(key)
    }

    override fun write(key: String, value: String) {
        if (!inTransaction) {
            // If not in transaction, delegate immediately (or throw an error)
            return super.write(key, value)
        }

        // Save the change to the local cache
        println("TRANSACTION: Writing key $key to local cache.")
        transactionCache[key] = value
    }
    override fun commit(): Int {
        if (!inTransaction) {
            println("TRANSACTION: No active transaction to commit.")
            return 0
        }

        println("TRANSACTION: Committing changes to underlying DB.")
        var successfulWrites = 0

        // Iterate through all cached changes and delegate the write
        for ((key, value) in transactionCache) {
            if (value != null) {
                super.write(key, value)
                successfulWrites++
            }
        }

        transactionCache.clear()
        inTransaction = false
        return successfulWrites
    }

    override fun rollback(): Int {
        if (!inTransaction) {
            println("TRANSACTION: No active transaction to rollback.")
            return 0
        }

        val discardedCount = transactionCache.size
        println("TRANSACTION: Rolling back and discarding $discardedCount changes.")
        transactionCache.clear()
        inTransaction = false
        return discardedCount
    }
}