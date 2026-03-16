package de.uni_saarland.cs.se.simpledb

class Database {
    fun write(key: String, value: String) {
        println("Writing value '$value' at key '$key'.")
        storage[key] = value
    }
    private val storage = Storage()
}
