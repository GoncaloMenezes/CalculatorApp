package pt.com.calculator.utils

fun main() {

    val x = Calculator()

    x.evaluateExpression("455−1", false)

}
class Calculator {

    fun evaluateExpression(expression: CharSequence, roundResult: Boolean): CharSequence {
        println("Original: $expression")

        // Step 1: Convert the charsequence to a list
        val expressionList = expressionToList(expression)

        // Step 2: Handle × and ÷ operations
        evaluateMultiplicationAndDivision(expressionList)
        println("After × | ÷ : $expressionList")

        // Step 3: Handle + and - operations
        evaluateAdditionAndSubtraction(expressionList)
        println("After + | - : $expressionList")

        val result: CharSequence

        if(roundResult){
            result = String.format("%.1f", expressionList.first().toString().toDouble())
        }else{
            result = expressionList.first()
        }
        println("Result: $result")
        return result
    }

    private fun expressionToList(expression:CharSequence): MutableList<CharSequence> {
        val expression = expression
            .replace("\\s".toRegex(), "")
            .replace("*", "×")
            .replace("/", "÷")
            .replace("-", "−")

        val pattern = "^(\\d+(\\.\\d+)?)([+\\−×÷](?![+\\−×÷])\\d+(\\.\\d+)?)*\$"
        val regex = pattern.toRegex()
        if(!regex.containsMatchIn(expression)){
            throw IllegalArgumentException("The expression is invalid. Please ensure that:\n" +
                    "- No letters or unsupported symbols are included.\n" +
                    "- Operators are not consecutive (e.g., '+-', '**').\n" +
                    "- Decimal points are preceded by at least one digit (e.g., '0.06', not '.06').")
        }

        var expressionList: MutableList<CharSequence> = mutableListOf()
        var currentNumber: CharSequence = ""

        for(char:Char in expression){
            if(char.isDigit() || char=='.' ){
                currentNumber = "${currentNumber}${char}"
            }else{
                if (currentNumber.isNotEmpty()) {
                    expressionList.add(currentNumber)
                    currentNumber = ""
                }
                expressionList.add("$char")
            }
        }

        if (currentNumber.isNotEmpty()) {
            expressionList.add(currentNumber)
        }
        println("As list: $expressionList")
        return expressionList
    }

    private fun evaluateMultiplicationAndDivision(expressionList: MutableList<CharSequence>) {
        if(expressionList.contains("×") || expressionList.contains("÷")) {
            val iterator = expressionList.listIterator()
            while (iterator.hasNext()) {
                val current = iterator.next()

                if (current == "×" || current == "÷") {
                    iterator.previous()
                    val prev = iterator.previous()
                    iterator.remove()

                    iterator.next()
                    val next = iterator.next()
                    iterator.remove()

                    var result: Double = 0.0
                    //println("Current: $current Prev:: $prev Next: $next")
                    if (current == "×") {
                        result = prev.toString().toDouble() * next.toString().toDouble()
                        iterator.previous()
                        iterator.set(result.toString())
                    }

                    if (current == "÷") {
                        result = prev.toString().toDouble() / next.toString().toDouble()
                        iterator.previous()
                        iterator.set(result.toString())
                    }
                }
            }

        }
    }

    private fun evaluateAdditionAndSubtraction(expressionList: MutableList<CharSequence>) {
        if(expressionList.contains("+") || expressionList.contains("−")) {
            val iterator = expressionList.listIterator()
            while (iterator.hasNext()) {
                val current = iterator.next()

                if (current == "+" || current == "−") {
                    iterator.previous()
                    val prev = iterator.previous()
                    iterator.remove()


                    iterator.next()
                    val next = iterator.next()
                    iterator.remove()

                    var result: Double = 0.0
                    //println("Current: $current Prev:: $prev Next: $next")
                    if (current == "+") {
                        result = prev.toString().toDouble() + next.toString().toDouble()
                        iterator.previous()
                        iterator.set(result.toString())
                    }

                    if (current == "−") {
                        result = prev.toString().toDouble() - next.toString().toDouble()
                        iterator.previous()
                        iterator.set(result.toString())
                    }
                }
            }
        }
    }

}