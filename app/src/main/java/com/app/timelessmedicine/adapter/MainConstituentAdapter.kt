package com.app.timelessmedicine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.timelessmedicine.R
import com.app.timelessmedicine.bean.response.common.MainConstituentBean
import kotlinx.android.synthetic.main.custom_layout_main_constituents.view.*

class MainConstituentAdapter(
    val context: Context,
    val mainConstituentList: ArrayList<MainConstituentBean>
) :
    RecyclerView.Adapter<MainConstituentAdapter.MainConstituentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainConstituentViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.custom_layout_main_constituents, parent, false)
        return MainConstituentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mainConstituentList.size
    }

    override fun onBindViewHolder(holder: MainConstituentViewHolder, position: Int) {

        holder.itemView.tv_usage.text = mainConstituentList[position].name
        holder.itemView.tv_percentage.text = mainConstituentList[position].percentage+"%"
    }

    inner class MainConstituentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}
