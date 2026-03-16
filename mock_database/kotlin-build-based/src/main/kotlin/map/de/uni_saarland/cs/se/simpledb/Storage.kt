package de.uni_saarland.cs.se.simpledb

/// Map-based storage implementation.
class Storage {
    private val storage: MutableMap<String, String> = mutableMapOf()

    operator fun get(key: String): String? = storage[key]
    operator fun set(key: String, value: String) { storage[key] = value }
    val size: Int
        get() = storage.size
    fun clear() = storage.clear()
    fun forEach(f: (String, String) -> Unit) = storage.forEach(f)
}