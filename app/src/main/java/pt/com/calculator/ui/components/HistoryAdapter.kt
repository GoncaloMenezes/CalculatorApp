package pt.com.calculator.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.com.calculator.R
import pt.com.calculator.data.model.Calculation

class HistoryAdapter(private val calculations: List<Calculation>):
    RecyclerView.Adapter<HistoryAdapter.CalculationViewHolder>(){

    class CalculationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val expressionTV: TextView = view.findViewById(R.id.textView_history_expression)
        val resultTV: TextView = view.findViewById(R.id.textView_history_result)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CalculationViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.calculation_item, viewGroup, false)

        return CalculationViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: CalculationViewHolder, position: Int) {
        viewHolder.expressionTV.text = calculations[position].expression
        viewHolder.resultTV.text = calculations[position].result
    }

    override fun getItemCount() = calculations.size
}