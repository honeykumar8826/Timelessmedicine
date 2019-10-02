package com.app.timelessmedicine.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.timelessmedicine.R
import com.app.timelessmedicine.bean.response.common.CommonNameTypeBean
import com.app.timelessmedicine.ui.properties.PropertyActivity
import com.app.timelessmedicine.ui.propertydetail.PropertyDetailActivity
import kotlinx.android.synthetic.main.custom_layout_property.view.*

class PropertyAdapter(val context: Context, val mainPropertyList: ArrayList<CommonNameTypeBean>) :
    RecyclerView.Adapter<PropertyAdapter.MainPropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPropertyViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.custom_layout_property, parent, false)
        return MainPropertyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mainPropertyList.size
    }

    override fun onBindViewHolder(holder: MainPropertyViewHolder, position: Int) {

        holder.itemView.name_property.text = mainPropertyList[position].name
    }

    inner class MainPropertyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var intent: Intent

        init {
                 view.cv_property.setOnClickListener {
                     intent = Intent(context, PropertyDetailActivity::class.java)
                     intent.putExtra("item_name",mainPropertyList.get(adapterPosition).name)
                     (context as PropertyActivity).startActivity(intent)

                 }
        }
    }
}
