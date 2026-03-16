//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
interface ExprVisitor<T> {
    fun visit(valueVisit: Val): T
    fun visit(valueVisit: Add): T
    fun visit(valueVisit: Mul): T
}

interface Expr {
    fun <T> accept(visitor: ExprVisitor<T>): T
}

class Val(val value: Int) : Expr {
    override fun <Int> accept(visitor: ExprVisitor<Int>): Int {
        return visitor.visit(this)
    }
}

class Add(val left: Expr, val right: Expr) : Expr {
    override fun <Int> accept(visitor: ExprVisitor<Int>): Int {
        return visitor.visit(this)
    }
}

class Mul(val left: Expr, val right: Expr) : Expr {
    override fun <Int> accept(visitor: ExprVisitor<Int>): Int {
        return visitor.visit(this)
    }
}

class EvalVisitor : ExprVisitor<Int> {
    override fun visit(valNode: Val): Int {
        return valNode.value
    }
    override fun visit(addNode: Add): Int {
        return addNode.left.accept(this) + addNode.right.accept(this)
    }
    override fun visit(addNode: Mul): Int {
        return addNode.left.accept(this) * addNode.right.accept(this)
    }
}

class PrintVisitor : ExprVisitor<String> {
    override fun visit(valNode: Val): String {
        return valNode.value.toString()
    }
    override fun visit(addNode: Add): String {
        return "(" + addNode.left.accept(this) + "+" + addNode.right.accept(this) + ")"
    }
    override fun visit(addNode: Mul): String {
        return "(" + addNode.left.accept(this) + "*" + addNode.right.accept(this) + ")"
    }
}

fun main() {
    val expression = Mul(Val(1), Add(
        Val(5),
        Add(Val(10), Val(2))
    ))

    // 1. Calculate it (Returns Int)
    val calc = EvalVisitor()
    val result: Int = expression.accept(calc)
    println("Result: $result") // Output: 17

    // 2. Print it (Returns String)
    val printer = PrintVisitor()
    val text: String = expression.accept(printer)
    println("Pretty Print: $text") // Output: (5 + (10 + 2))


}