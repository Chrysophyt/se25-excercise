package de.uni_saarland.cs.se.simpledb

class Database {
    fun read(key: String): String? = storage[key]
    fun write(key: String, value: String) { storage[key] = value }
    private val storage = Storage()
}
