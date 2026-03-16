package de.uni_saarland.cs.se.simpledb

class Database {
    private val storage = Storage()
    private val tmpStorage = Storage()

    fun read(key: String): String? {
        println("Reading value for key '$key'.")
        return storage[key]
    }

    fun write(key: String, value: String) {
        println("Writing value '$value' at key '$key'.")
        tmpStorage[key] = value
    }

    fun commit(): Int {
        val size = tmpStorage.size
        println("Committing $size entries.")
        tmpStorage.forEach { k, v -> storage[k] = v }
        tmpStorage.clear()
        return size
    }

    fun rollback(): Int {
        val size = tmpStorage.size
        println("Rolling back $size entries.")
        tmpStorage.clear()
        return size
    }
}
