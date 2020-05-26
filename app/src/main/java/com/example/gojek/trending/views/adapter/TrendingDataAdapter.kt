package com.example.gojek.trending.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.gojek.databinding.LayoutRowItemBinding
import com.example.gojek.model.GithubAccountDataModelItem
import com.example.gojek.trending.viewmodel.TrendingAdapterViewModel

class TrendingDataAdapter(private val lifeCycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<TrendingDataAdapter.ViewHolder>() {
    private var context: Context? = null
    private var list = ArrayList<GithubAccountDataModelItem>()
    private var previousPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (context == null) {
            context = parent.context
        }
        return ViewHolder(LayoutRowItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class ViewHolder(private val binding: LayoutRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var trendingAdapterViewModel: TrendingAdapterViewModel? = null
        fun bind(item: GithubAccountDataModelItem, adapterPos: Int) {
            trendingAdapterViewModel = TrendingAdapterViewModel(item)
            binding.apply {
                lifecycleOwner = lifeCycleOwner
                rowItemData = trendingAdapterViewModel
                position = adapterPos
                executePendingBindings()
            }
            trendingAdapterViewModel?.itemPosition?.observe(lifeCycleOwner, Observer {
                if (previousPosition != -1 && previousPosition != it) {
                    val obj = list[previousPosition]
                    if (obj.isExpanded) {
                        obj.isExpanded = !obj.isExpanded
                        list[previousPosition] = obj
                        notifyItemChanged(previousPosition)
                    }
                }
                val obj = list[it]
                obj.isExpanded = !obj.isExpanded
                list[it] = obj
                previousPosition = it
                notifyItemChanged(it)
            })

        }
    }

    fun updateData(list: ArrayList<GithubAccountDataModelItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

}