package pt.com.calculator.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView
import pt.com.calculator.utils.Calculator
import pt.com.calculator.R
import pt.com.calculator.data.model.AppDatabase
import pt.com.calculator.data.repository.CalculationRepository
import pt.com.calculator.data.room.CalculationDao
import androidx.activity.viewModels
import pt.com.calculator.ui.history.HistoryActivity

class MainActivity : AppCompatActivity() {
    private val db: AppDatabase by lazy { AppDatabase.getInstance(this) }
    private val calculationDao: CalculationDao by lazy { db.calculationDao() }

    private val calculationRepository: CalculationRepository by lazy {
        CalculationRepository(
            calculationDao
        )
    }
    private val calculator: Calculator by lazy { Calculator() }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(calculationRepository, calculator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputTV: TextView = findViewById(R.id.textView_input)
        val horizontalScrollViewInput: HorizontalScrollView = findViewById(R.id.horizontalScrollView_input)
        val resultTV: TextView = findViewById(R.id.textView_result)
        val horizontalScrollViewResult: HorizontalScrollView = findViewById(R.id.horizontalScrollView_result)

        val historyBtn: Button = findViewById(R.id.button_history)

        setupClearBtns()
        setupOperatorBtns(inputTV, horizontalScrollViewInput)
        setupNumberBtns(inputTV, horizontalScrollViewInput)
        setupDecimalBtn(inputTV, horizontalScrollViewInput)
        setupEqualsBtns(resultTV, horizontalScrollViewResult)

        historyBtn.setOnClickListener {
            val intent: Intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        viewModel.input.observe(this) { input ->
            inputTV.text = input
        }

        viewModel.result.observe(this) { result ->
            resultTV.text = result
        }
    }

    private fun setupDecimalBtn(textView: TextView, scrollView: HorizontalScrollView){
        findViewById<Button>(R.id.button_decimalPoint).setOnClickListener {
            viewModel.addDecimalPoint()
            handleHorizontalScroll(textView, scrollView)
        }
    }
    private fun setupEqualsBtns(textView: TextView, scrollView: HorizontalScrollView) {
        findViewById<Button>(R.id.button_equals).setOnClickListener {
            viewModel.calculateResult(false)
            handleHorizontalScroll(textView, scrollView)
        }
        findViewById<Button>(R.id.button_round).setOnClickListener {
            viewModel.calculateResult(true)
            handleHorizontalScroll(textView, scrollView)
        }
    }

    private fun setupClearBtns() {
        findViewById<Button>(R.id.button_clearAll).setOnClickListener {
            viewModel.clearAll()
        }
        findViewById<Button>(R.id.button_clearLast).setOnClickListener {
            viewModel.clearLast()
        }
    }

    private fun setupNumberBtns(textView: TextView, scrollView: HorizontalScrollView) {
        val numberBtnIds = listOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
            R.id.button_8, R.id.button_9
        )

        numberBtnIds.forEach { id ->
            val button = findViewById<Button>(id)
            button.setOnClickListener {
                viewModel.addNumber(button.text.toString())
                handleHorizontalScroll(textView, scrollView)
            }
        }
    }

    private fun setupOperatorBtns(textView: TextView, scrollView: HorizontalScrollView) {
        val operatorsBtnIds = listOf(
            R.id.button_multiply,
            R.id.button_divide,
            R.id.button_addition,
            R.id.button_subtract
        )

        operatorsBtnIds.forEach { id ->
            val button = findViewById<Button>(id)
            button.setOnClickListener {
                viewModel.addOperator(button.text.toString())
                handleHorizontalScroll(textView, scrollView)
            }
        }
    }

    private fun handleHorizontalScroll(textView: TextView, scrollView: HorizontalScrollView) {
        textView.post {
            val contentWidth = textView.width
            val scrollViewWidth = scrollView.width

            if (contentWidth > scrollViewWidth) {
                scrollView.post {
                    scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
                }
            }
        }
    }
}