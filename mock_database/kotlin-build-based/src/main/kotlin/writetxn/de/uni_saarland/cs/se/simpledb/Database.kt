package de.uni_saarland.cs.se.simpledb

class Database {
    private val storage = Storage()
    private val tmpStorage = Storage()

    fun write(key: String, value: String) {
        tmpStorage[key] = value
    }

    fun commit(): Int {
        val size = tmpStorage.size
        tmpStorage.forEach { k, v -> storage[k] = v }
        tmpStorage.clear()
        return size
    }

    fun rollback(): Int {
        val size = tmpStorage.size
        tmpStorage.clear()
        return size
    }
}
