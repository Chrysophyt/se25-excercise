package de.uni_saarland.cs.se.simpledb

class Database {
    fun read(key: String): String? = storage[key]
    private val storage = Storage()
}