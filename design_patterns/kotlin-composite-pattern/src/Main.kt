import com.sun.jdi.IntegerType

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
interface Expr {
    // The operation we want to perform uniformly
    fun eval(): Int
    fun print(): String
}

class Val(val value: Int) : Expr {

    override fun eval(): Int {
        return value
    }

    override fun print(): String {
        return value.toString()
    }
}

class Add(val left: Expr, val right: Expr) : Expr {

    override fun eval(): Int {
        return left.eval() + right.eval()
    }

    override fun print(): String {
        return "(" + left.print() + "+" + right.print() + ")"
    }
}

class Multiply(val left: Expr, val right: Expr) : Expr {
    override fun eval(): Int {
        return left.eval() * right.eval()
    }

    override fun print(): String {
        return "(" + left.print() + "*" + right.print() + ")"
    }
}


fun main() {
    val expression = Add(
        Val(3),
        Multiply(
            Add(Val(5), Val(9)),
            Val(2)))


    println(expression.eval())
    println(expression.print())
}