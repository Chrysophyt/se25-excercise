package de.uni_saarland.cs.se.simpledb

/// List-based storage implementation.
class Storage {
    private val storage: MutableList<Pair<String, String>> = mutableListOf()

    operator fun get(key: String): String? = storage.firstOrNull { it.first == key }?.second
    operator fun set(key: String, value: String) {
        storage.removeIf { it.first == key }
        storage.add(key to value)
    }
    val size: Int
        get() = storage.size
    fun clear() = storage.clear()
    fun forEach(f: (String, String) -> Unit) = storage.forEach { (k, v) -> f(k, v) }
}
