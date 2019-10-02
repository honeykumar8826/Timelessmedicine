package com.app.timelessmedicine.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.timelessmedicine.R
import com.app.timelessmedicine.bean.response.common.CommonNameTypeBean
import com.app.timelessmedicine.ui.oilandblenddetail.OilAndBlendDetailActivity
import kotlinx.android.synthetic.main.custom_layout_blend.view.*

class FoundBlendAdapter(val context: Context, val foundBlendList: ArrayList<CommonNameTypeBean>) :
    RecyclerView.Adapter<FoundBlendAdapter.FoundBlendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundBlendViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.custom_layout_blend, parent, false)
        return FoundBlendViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foundBlendList.size
    }

    override fun onBindViewHolder(holder: FoundBlendViewHolder, position: Int) {

        holder.itemView.name_blend.text = foundBlendList[position].name
    }

    inner class FoundBlendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var intent: Intent

        init {
                 view.cv_oils_blends.setOnClickListener {
                     intent = Intent(context, OilAndBlendDetailActivity::class.java)
                     intent.putExtra("item_name",foundBlendList.get(adapterPosition).name)
                     intent.putExtra("item_type",foundBlendList.get(adapterPosition).type.toString())
                     (context as OilAndBlendDetailActivity).startActivity(intent)

                 }
        }
    }
}
