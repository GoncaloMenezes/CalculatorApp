package pt.com.calculator.ui.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.com.calculator.data.model.Calculation
import pt.com.calculator.databinding.CalculationItemBinding

class HistoryAdapter(private val calculations: List<Calculation>):
    RecyclerView.Adapter<HistoryAdapter.CalculationViewHolder>(){

    class CalculationViewHolder(val calculationItemBinding: CalculationItemBinding) : RecyclerView.ViewHolder(calculationItemBinding.root) { }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CalculationViewHolder {
        val calculationItemBinding: CalculationItemBinding = CalculationItemBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false)

        return CalculationViewHolder(calculationItemBinding)
    }

    override fun onBindViewHolder(viewHolder: CalculationViewHolder, position: Int) {
        viewHolder.calculationItemBinding.textViewHistoryExpression.text = calculations[position].expression
        viewHolder.calculationItemBinding.textViewHistoryResult.text = calculations[position].result
    }

    override fun getItemCount() = calculations.size
}