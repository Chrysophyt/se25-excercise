# Software Engineering Exercises

This is from Software Engineering Exercise Winter 2025/2026 course at UdS.

This repository is a collection of small projects and code examples created to explore various software engineering concepts, design patterns, and programming language features. Each directory focuses on a specific theme.

## Projects Overview

The repository is structured into three main thematic areas:

-   `design_patterns/`: Implementations of classic design patterns in Kotlin.
-   `mock_database/`: A simple key-value store where variability is implemented by runtime args or build scripts.
-   `trait_mixin/`: Examples of using traits and typeclasses in Rust and Haskell.

---

### `design_patterns`

This section contains implementations of common software design patterns in **Kotlin**. The examples are all structured around building and evaluating arithmetic expressions, showing different ways to approach the problem. Each sub-directory is a self-contained IntelliJ project.

-   **`CompositePattern`**: Demonstrates the Composite design pattern, which allows you to compose objects into tree structures to represent part-whole hierarchies.
-   **`ObjectAlgebra`**: Implements the Object Algebra pattern, an alternative to the Visitor pattern that allows for adding new operations and new data types without modifying existing code.
-   **`VisitorPattern`**: Shows the Visitor design pattern, which lets you add new operations to existing object structures without modifying them.

---

### `mock_database`

This section explores building a simple key-value store.

-   **`cpp_runtimebased`**: A command-line REPL for a mock database written in **C++**. It uses command-line flags to configure its features at runtime (e.g., enabling/disabling reads, writes, transactions, and changing the underlying storage mechanism).

    **To run the C++ database:**
    ```bash
    # Navigate to the project directory
    cd mock_database/cpp_runtimebased

    # Create a build directory
    mkdir -p build && cd build

    # Configure the project with CMake
    cmake ..

    # Compile the source code
    make

    # Run the executable with desired features
    # Example: enable read, write, and transactions with map-based storage
    ./SimpleDB --read --write --transaction --storage-type=map
    ```
    You can then interact with the database using commands like `read <key>`, `write <key> <value>`, `commit`, and `rollback`.

---

### `trait_mixin`

This section contains examples of using traits and their equivalents in different languages to achieve polymorphism and code reuse.

-   **`rust`**: An example in **Rust** that uses traits to define `Eval` and `Print` capabilities for an arithmetic expression tree. This demonstrates how to use trait bounds (`Box<dyn Exp>`) to work with different types that share common behavior.

-   **`haskell`**: An example in **Haskell** that uses typeclasses to implement an extensible expression evaluator. This is a powerful functional programming pattern that achieves similar goals to Object Algebras, allowing for the addition of new operations (interpreters) and data variants without modifying existing code.
