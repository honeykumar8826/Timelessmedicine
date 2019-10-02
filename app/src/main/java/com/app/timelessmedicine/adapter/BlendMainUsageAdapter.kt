package com.app.timelessmedicine.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.timelessmedicine.R
import com.app.timelessmedicine.bean.response.blends.ForAndroidBlend
import com.app.timelessmedicine.bean.response.oils.AllData
import kotlinx.android.synthetic.main.custom_layout_main_area_usage.view.*

class BlendMainUsageAdapter(
    val context: Context,
    val oilMainUsageList: ArrayList<ForAndroidBlend>
) : RecyclerView.Adapter<BlendMainUsageAdapter.BlendMainUsageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlendMainUsageViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_main_area_usage, parent, false)
        return BlendMainUsageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return oilMainUsageList.size
    }

    override fun onBindViewHolder(holder: BlendMainUsageViewHolder, position: Int) {
        holder.itemView.tv_usage.text = oilMainUsageList[position].Main_areas_of_usage
        holder.itemView.tv_description.text = oilMainUsageList[position].How_to_use
        if(oilMainUsageList[position].ATI_bar.contains("A"))
        {
           holder.itemView.tv_A.setBackgroundResource(R.drawable.bg_solid_color)
           holder.itemView.tv_A.setTextColor(Color.WHITE)
        }
        if(oilMainUsageList[position].ATI_bar.contains("T"))
        {
            holder.itemView.tv_T.setBackgroundResource(R.drawable.bg_solid_color)
            holder.itemView.tv_T.setTextColor(Color.WHITE)
        }
        if(oilMainUsageList[position].ATI_bar.contains("I"))
        {
            holder.itemView.tv_I.setBackgroundResource(R.drawable.bg_solid_color)
            holder.itemView.tv_I.setTextColor(Color.WHITE)
        }
        if(oilMainUsageList[position].ATI_bar.contains("N"))
        {
            holder.itemView.tv_N.setBackgroundResource(R.drawable.bg_solid_color)
            holder.itemView.tv_N.setTextColor(Color.WHITE)
        }
        if(oilMainUsageList[position].ATI_bar.contains("S"))
        {
            holder.itemView.tv_S.setBackgroundResource(R.drawable.bg_solid_color)
            holder.itemView.tv_S.setTextColor(Color.WHITE)
        }
        if(oilMainUsageList[position].ATI_bar.contains("D"))
        {
            holder.itemView.tv_D.setBackgroundResource(R.drawable.bg_solid_color)
            holder.itemView.tv_D.setTextColor(Color.WHITE)
        }

    }

    inner class BlendMainUsageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {

            itemView.setOnClickListener {
                /*       val pos = adapterPosition
                       if (indexOfLetter[pos] != -1) {

                           selectedLetterCallback.clickItemPosition(pos, indexOfLetter[pos])
                       } else {
                           selectedLetterCallback.clickItemPosition(pos, -1)
                       }*/
            }
        }

    }
}

