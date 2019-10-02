package com.app.timelessmedicine.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.timelessmedicine.R
import com.app.timelessmedicine.bean.response.common.CommonNameTypeBean
import kotlinx.android.synthetic.main.custom_layout_property.view.*

class PropertyDetailAdapter(val context: Context, val propertyDetailList: ArrayList<CommonNameTypeBean>) :
    RecyclerView.Adapter<PropertyDetailAdapter.PropertyDetailViewHolder>() {
    lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyDetailViewHolder {

        when(viewType)
        {
            1->
            {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.custom_layout_blend, parent, false)
            }
            2->
            {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.custom_layout_property, parent, false)
            }
            3->
            {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.custom_layout_oil_and_blend, parent, false)
            }
            else->
            {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.custom_layout_property, parent, false)
            }
        }

        return PropertyDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return propertyDetailList.size
    }

    override fun getItemViewType(position: Int): Int {
         super.getItemViewType(position)
        return  propertyDetailList[position].type
    }

    override fun onBindViewHolder(holder: PropertyDetailViewHolder, position: Int) {

        holder.itemView.name_property.text = propertyDetailList[position].name
    }

    inner class PropertyDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var intent: Intent

        init {
            /*     view.cv_oils_blends.setOnClickListener {
                     intent = Intent(context, OilAndBlendDetailActivity::class.java)
                     intent.putExtra("item_name",blendWellList.get(adapterPosition))
                     (context as OilsBlendsActivity).startActivity(intent)

                 }*/
        }
    }
}
