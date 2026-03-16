package de.uni_saarland.cs.se.simpledb

class Database {
    fun read(key: String): String? {
        println("Reading value for key '$key'.")
        return storage[key]
    }
    private val storage = Storage()
}