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
            args[0] == "write" && args.size == 3 -> {
                db.write(args[1], args[2])
                println("Write: ${args[1]}=${args[2]}")
            }
            args[0] == "commit" && args.size == 1 -> {
                val numEntries = db.commit ()
                println("Commit: $numEntries entries")
            }
            args[0] == "rollback" && args.size == 1 -> {
                val numEntries = db.rollback ()
                println("Rollback: $numEntries entries")
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