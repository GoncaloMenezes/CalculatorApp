package pt.com.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.com.calculator.room.AppDatabase
import pt.com.calculator.room.Calculation

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val db = AppDatabase.getInstance(this)
        val calculationDao = db.calculationDao()

        val goBackBtn: Button = findViewById(R.id.button_goBack)

        goBackBtn.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.IO).launch {
            var history:List<Calculation> = calculationDao.getAll()

            withContext(Dispatchers.Main) {
                if(history.isNotEmpty()){
                    val historyAdapter: HistoryAdapter = HistoryAdapter(history)

                    val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
                    recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    recyclerView.adapter = historyAdapter

                }else{
                    val emptyHistoryTV: TextView = findViewById(R.id.textView_emptyHistory)
                    emptyHistoryTV.visibility = View.VISIBLE
                }
            }

        }
    }
}