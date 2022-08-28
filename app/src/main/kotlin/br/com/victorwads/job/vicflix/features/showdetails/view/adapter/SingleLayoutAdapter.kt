package br.com.victorwads.job.vicflix.features.showdetails.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class SingleLayoutAdapter<Layout : ViewBinding>(
    val inflateLayout: (parent: ViewGroup) -> Layout,
    val onBindLayout: (Layout) -> Unit
) : RecyclerView.Adapter<SingleLayoutAdapter.LayoutHolder<Layout>>() {

    private val items: ArrayList<Any> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LayoutHolder(
        inflateLayout(parent)
    )

    override fun onBindViewHolder(holder: LayoutHolder<Layout>, position: Int) {
        onBindLayout(holder.layout)
    }

    override fun getItemCount(): Int = 1

    class LayoutHolder<Layout : ViewBinding>(val layout: Layout) :
        RecyclerView.ViewHolder(layout.root)
}