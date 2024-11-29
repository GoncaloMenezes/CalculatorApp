package pt.com.calculator

import org.junit.Test
import org.junit.Assert.*
import pt.com.calculator.utils.Calculator

class CalculatorUnitTest {
    private val calculator = Calculator()

    @Test
    fun invalidExpression_expressionWithLetters() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.evaluateExpression("4+1a", true)
        }
    }

    @Test
    fun invalidExpression_expressionWithInvalidSymbols() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.evaluateExpression("4+1´", true)
        }
    }

    @Test
    fun invalidExpression_expressionWithConsecutiveOperators() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.evaluateExpression("4+1++5", true)
        }
    }

    @Test
    fun invalidExpression_expressionWithInvalidDecimalPoint() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.evaluateExpression("4+1+.5", true)
        }
    }

    @Test
    fun invalidExpression_emptyExpression() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.evaluateExpression("", true)
        }
    }


    @Test
    fun validExpression_complexExpressionWithOutRoundResult() {
        assertEquals("39.111111111111114", calculator.evaluateExpression("455/45+54-5*5", false) )
    }

    @Test
    fun validExpression_complexExpressionWithRoundResult() {
        assertEquals("39.1", calculator.evaluateExpression("455/45+54-5*5", true) )
    }

    @Test
    fun validExpression_complexExpressionWithSpacesBetween() {
        assertEquals("39.1", calculator.evaluateExpression("455 / 45 + 54 - 5 *5 ", true) )
    }

    @Test
    fun validExpression_simpleExpressionDivideByZero() {
        assertEquals("Infinity", calculator.evaluateExpression("455/0", true) )
    }


}