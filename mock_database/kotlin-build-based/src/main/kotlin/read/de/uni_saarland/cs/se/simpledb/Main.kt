package de.uni_saarland.cs.se.simpledb

fun startREPL(db: Database) {
    print("/db/> ")
    var line = readlnOrNull()
    while (line != null) {
        val args = line.split(" ");

        if (args.isEmpty()) {
            continue
        }

        when {
            args[0] == "read" && args.size == 2 -> {
                val value = db.read(args[1])
                println("Read: ${args[1]}=${value ?: "<empty>"}")
            }
            args[0] == "exit" -> {
                break
            }
            else -> {
                println("Unknown command.")
            }
        }

        print("/db/> ")
        line = readlnOrNull()
    }
}

fun main(args: Array<String>) {
    startREPL(Database());
}