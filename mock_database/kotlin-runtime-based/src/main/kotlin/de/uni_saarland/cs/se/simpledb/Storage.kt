package de.uni_saarland.cs.se.simpledb

enum class StorageType {
    MAP, LIST
}

/// Interface for database storage implementations.
sealed interface Storage {
    operator fun get(key: String): String?
    operator fun set(key: String, value: String)
    val size: Int
    fun clear()
    fun forEach(f: (String, String) -> Unit)
};

/// Map-based storage implementation.
class MapStorage : Storage {
    private val storage: MutableMap<String, String> = mutableMapOf()

    override fun get(key: String): String? = storage[key]
    override fun set(key: String, value: String) { storage[key] = value }
    override val size: Int
        get() = storage.size
    override fun clear() = storage.clear()
    override fun forEach(f: (String, String) -> Unit) = storage.forEach(f)
}

/// List-based storage implementation.
class ListStorage : Storage {
    private val storage: MutableList<Pair<String, String>> = mutableListOf()

    override fun get(key: String): String? = storage.firstOrNull { it.first == key }?.second
    override fun set(key: String, value: String) {
        storage.removeIf { it.first == key }
        storage.add(key to value)
    }
    override val size: Int
        get() = storage.size
    override fun clear() = storage.clear()
    override fun forEach(f: (String, String) -> Unit) = storage.forEach { (k, v) -> f(k, v) }
}
