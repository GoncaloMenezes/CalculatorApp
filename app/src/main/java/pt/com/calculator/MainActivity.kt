package pt.com.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var canAddOperation: Boolean = false
        var canAddDecimalPoint: Boolean = true
        var canAddNumber: Boolean = true

        val clearAllBtn: Button = findViewById(R.id.button_clearAll)
        val deleteBtn: Button = findViewById(R.id.button_delete)

        val horizontalScrollView: HorizontalScrollView = findViewById(R.id.horizontalScrollView)
        val inputTV: TextView = findViewById(R.id.textView_input)
        val resultTV: TextView = findViewById(R.id.textView_result)

        val button0: Button = findViewById(R.id.button_0)
        val button1: Button = findViewById(R.id.button_1)
        val button2: Button = findViewById(R.id.button_2)
        val button3: Button = findViewById(R.id.button_3)
        val button4: Button = findViewById(R.id.button_4)
        val button5: Button = findViewById(R.id.button_5)
        val button6: Button = findViewById(R.id.button_6)
        val button7: Button = findViewById(R.id.button_7)
        val button8: Button = findViewById(R.id.button_8)
        val button9: Button = findViewById(R.id.button_9)
        val decimalBtn: Button = findViewById(R.id.button_decimalPoint)

        val divideBtn: Button = findViewById(R.id.button_divide)
        val multiplyBtn: Button = findViewById(R.id.button_multiply)
        val subtractBtn: Button = findViewById(R.id.button_subtract)
        val additionBtn: Button = findViewById(R.id.button_addition)
        val percentageBtn: Button = findViewById(R.id.button_percentage)

        val equalsBtn: Button = findViewById(R.id.button_equals)
        fun endsWithOperator(input: CharSequence): Boolean {
            return input.endsWith("+") || input.endsWith("-") || input.endsWith("×") || input.endsWith(
                "÷"
            )
        }

        fun handleHorizontalScroll() {
            // Measure the width of the content and compare it to the width of the visible area
            inputTV.post {
                val contentWidth = inputTV.width
                val scrollViewWidth = horizontalScrollView.width

                if (contentWidth > scrollViewWidth) {
                    horizontalScrollView.post {
                        horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
                    }
                }
            }
        }

        fun handleDecimalPoint() {
            if (canAddDecimalPoint) {
                if (inputTV.text.isEmpty() || endsWithOperator(inputTV.text)) {
                    inputTV.append("0")
                }
                inputTV.append(".")
                handleHorizontalScroll()
                canAddOperation = false
                canAddDecimalPoint = false
                canAddNumber = true
            }
        }

        fun handleNumber(number: String) {
            if (canAddNumber) {
                inputTV.append(number)
                handleHorizontalScroll()
                canAddOperation = true
            }
        }


        val numberClickListener = View.OnClickListener { view ->
            if (view is Button) {
                val inputText = view.text.toString()

                if (inputText == ".") {
                    handleDecimalPoint()
                } else {
                    handleNumber(inputText)
                }
            }
        }

        val operatorClickListener = View.OnClickListener { view ->
            if (view is Button) {
                if (canAddOperation) {
                    // Append the operator and reset the decimal allowance
                    inputTV.append(view.text)
                    canAddOperation = false
                    canAddDecimalPoint = true
                    canAddNumber = true
                }
            }
        }

        button0.setOnClickListener(numberClickListener)
        button1.setOnClickListener(numberClickListener)
        button2.setOnClickListener(numberClickListener)
        button3.setOnClickListener(numberClickListener)
        button4.setOnClickListener(numberClickListener)
        button5.setOnClickListener(numberClickListener)
        button6.setOnClickListener(numberClickListener)
        button7.setOnClickListener(numberClickListener)
        button8.setOnClickListener(numberClickListener)
        button9.setOnClickListener(numberClickListener)
        decimalBtn.setOnClickListener(numberClickListener)

        divideBtn.setOnClickListener(operatorClickListener)
        multiplyBtn.setOnClickListener(operatorClickListener)
        subtractBtn.setOnClickListener(operatorClickListener)
        additionBtn.setOnClickListener(operatorClickListener)

        equalsBtn.setOnClickListener {
            resultTV.text = "To implement"
        }

        percentageBtn.setOnClickListener { view ->
            if (view is Button) {
                if (inputTV.text.isNotEmpty() &&
                    !inputTV.text.endsWith("%") && // Cannot have another % at the end
                    !inputTV.text.endsWith("+") &&
                    !inputTV.text.endsWith("−") &&
                    !inputTV.text.endsWith("×") &&
                    !inputTV.text.endsWith("÷")
                ) { // Check if the last character is not an operator
                    inputTV.append("%")
                    canAddDecimalPoint = false
                    canAddOperation = true
                    canAddNumber = false
                }
            }

        }

        clearAllBtn.setOnClickListener {
            inputTV.text = ""
            resultTV.text = ""
            canAddOperation = false
            canAddDecimalPoint = true
            canAddNumber = true
        }

        deleteBtn.setOnClickListener {
            if (inputTV.length() > 0) {
                inputTV.text = inputTV.text.substring(0, inputTV.length() - 1)
                canAddNumber = true
                if (!inputTV.text.endsWith(".")) {
                    canAddDecimalPoint = true
                }
            }
        }
    }
}