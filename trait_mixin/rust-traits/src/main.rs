// 1. Define the Traits
trait Eval {
    fn eval(&self) -> i32;
}

trait Print {
    fn print(&self) -> String;
}

// Exp combines Eval and Print
trait Exp: Eval + Print {}

// 2. Define Val
struct Val {
    c: i32,
}
impl Eval for Val {
    fn eval(&self) -> i32 {
        self.c
    }
}
impl Print for Val {
    fn print(&self) -> String {
        format!("{}", self.c)
    }
}
impl Exp for Val {} // Manual opt-in

// 3. Define Add
struct Add {
    l: Box<dyn Exp>, // Points to anything that implements Exp
    r: Box<dyn Exp>,
}
impl Eval for Add {
    fn eval(&self) -> i32 {
        self.l.eval() + self.r.eval()
    }
}
impl Print for Add {
    fn print(&self) -> String {
        format!("({} + {})", self.l.print(), self.r.print())
    }
}
impl Exp for Add {} // Manual opt-in

// 3. Define Mul
struct Mul {
    l: Box<dyn Exp>,
    r: Box<dyn Exp>,
}
impl Eval for Mul {
    fn eval(&self) -> i32 {
        self.l.eval() * self.r.eval()
    }
}
impl Print for Mul {
    fn print(&self) -> String {
        format!("({} * {})", self.l.print(), self.r.print())
    }
}
impl Exp for Mul {} // Manual opt-in

fn main() {
    let leaf1 = Box::new(Val { c: 3 });
    let leaf2 = Box::new(Val { c: 4 });
    let leaf3 = Box::new(Val { c: 5 });

    // 2. Create the Add node
    let my_expr = Add { l: leaf1, r: leaf2 };

    let my_expr2 = Mul {
        l: Box::new(my_expr),
        r: leaf3,
    };
    println!("The value is: {}", my_expr2.eval());
    println!("The expression is: {}", my_expr2.print());
}
