module Main where

-- 1. DEFINE THE INTERFACES (The Capabilities)
-- This allows us to do addition and values
class ExprSym repr where
    val :: Int -> repr
    add :: repr -> repr -> repr

-- This allows us to do multiplication (extended capability)
class MulSym repr where
    mul :: repr -> repr -> repr

-- 2. DEFINE THE INTERPRETERS (The Machines)
-- Interpreter A: Calculator (Computes the Integer)
newtype Eval = Eval { runEval :: Int }

instance ExprSym Eval where
    val n     = Eval n
    add a b   = Eval (runEval a + runEval b)

instance MulSym Eval where
    mul a b   = Eval (runEval a * runEval b)

-- Interpreter B: Printer (Creates a String representation)
newtype View = View { view :: String }

instance ExprSym View where
    val n     = View (show n)
    add a b   = View ("(" ++ view a ++ " + " ++ view b ++ ")")

instance MulSym View where
    mul a b   = View ("(" ++ view a ++ " * " ++ view b ++ ")")

-- 3. WRITE THE PROGRAMS (The Expressions)
-- Note: 'repr' is generic. It becomes Eval or View later.

-- Example 1: Simple Addition (3 + 5)
expr1 :: ExprSym repr => repr
expr1 = add (val 3) (val 5)

-- Example 2: Complex Mixed ( (2 * 3) + 4 )
-- Note: Needs both ExprSym and MulSym capabilities
expr2 :: (ExprSym repr, MulSym repr) => repr
expr2 = add (mul (val 2) (val 3)) (val 4)

-- Example 3: Adding 5 numbers (Chaining/Nesting)
-- 1 + 2 + 3 + 4 + 5
expr3 :: ExprSym repr => repr
expr3 = add (val 1) 
            (add (val 2) 
                 (add (val 3) 
                      (add (val 4) (val 5))))

-- 4. RUN EVERYTHING
main :: IO ()
main = do
    putStrLn "--- Expression 1: (3 + 5) ---"
    putStrLn $ "Result: " ++ show (runEval expr1)
    putStrLn $ "View:   " ++ view expr1
    
    putStrLn "\n--- Expression 2: ((2 * 3) + 4) ---"
    putStrLn $ "Result: " ++ show (runEval expr2)
    putStrLn $ "View:   " ++ view expr2

    putStrLn "\n--- Expression 3: (1 + 2 + 3 + 4 + 5) ---"
    putStrLn $ "Result: " ++ show (runEval expr3)
    putStrLn $ "View:   " ++ view expr3