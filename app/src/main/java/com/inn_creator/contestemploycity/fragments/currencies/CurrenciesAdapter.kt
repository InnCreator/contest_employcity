package com.inn_creator.contestemploycity.fragments.currencies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inn_creator.contestemploycity.BR
import com.inn_creator.contestemploycity.databinding.RecyclerviewItemBinding
import com.inn_creator.contestemploycity.view.BySort
import com.inn_creator.contestemploycity.view.TypeSort
import com.inn_creator.contestemploycity.model.ValueCurrency

class CurrenciesAdapter(
    private val currencies: ArrayList<ValueCurrency>,
    private val onClick: (int: Int) -> Unit,
) :
    ListAdapter<ValueCurrency, CurrenciesAdapter.MyViewHolder>(Companion) {

    class MyViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = currencies[position]
        holder.binding.setVariable(BR.currency, item)
        holder.binding.favorite.setOnClickListener {
            onClick(position)
            item.isFavorite = !item.isFavorite
            notifyItemChanged(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sort(bySort: BySort, typeSort: TypeSort) {
        val list: List<ValueCurrency> = if (bySort == BySort.ASC)
            if (typeSort == TypeSort.ALPHABET)
                currencies.sortedBy { it.code }
            else
                currencies.sortedBy { it.value }
        else
            if (typeSort == TypeSort.ALPHABET)
                currencies.sortedByDescending { it.code }
            else
                currencies.sortedByDescending { it.value }

        currencies.clear()
        currencies.addAll(list)
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun hasFavorite(value: ArrayList<ValueCurrency>, isFavorite: Boolean) {
        val list = if (isFavorite)
            value.filter { it.isFavorite }
        else
            value
        currencies.clear()
        currencies.addAll(list)

        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    companion object : DiffUtil.ItemCallback<ValueCurrency>() {
        override fun areItemsTheSame(oldItem: ValueCurrency, newItem: ValueCurrency): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: ValueCurrency, newItem: ValueCurrency): Boolean =
            oldItem.value == newItem.value

    }
}