//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
interface ExprSym<T> {
    fun value(n: Int): T
    fun add(e1: T, e2: T): T
}

interface MulSym<T> : ExprSym<T> {
    fun mul(e1: T, e2: T): T
}

class Eval : MulSym<Int> {
    override fun value(n: Int): Int = n
    override fun add(e1: Int, e2: Int): Int = e1 + e2
    override fun mul(e1: Int, e2: Int): Int = e1 * e2
}

class View : MulSym<String> {
    override fun value(n: Int): String = n.toString()
    override fun add(e1: String, e2: String): String = "($e1 + $e2)"
    override fun mul(e1: String, e2: String): String = "($e1 * $e2)"
}

fun <T> makeComplexExpr(alg: MulSym<T>): T {
    return alg.add(
        alg.value(5),
        alg.mul(alg.value(3), alg.value(2))
    )
}

fun main() {
    val evaluator = Eval()
    val viewer = View()
    val res2 = makeComplexExpr(evaluator)
    println("Result: $res2")

    // Run as String
    val view2 = makeComplexExpr(viewer)
    println("View:   $view2")
}