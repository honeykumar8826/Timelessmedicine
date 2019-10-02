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
import kotlinx.android.synthetic.main.custom_layout_oil_and_blend.view.*

class BlendWellWithAdapter(val context: Context, val blendWellList:ArrayList<CommonNameTypeBean>): RecyclerView.Adapter<BlendWellWithAdapter.BlendWellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlendWellViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_layout_oil_and_blend,parent,false)
        return BlendWellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return blendWellList.size
    }

    override fun onBindViewHolder(holder: BlendWellViewHolder, position: Int) {

        holder.itemView.name_oil.text = blendWellList[position].name
    }

    inner class BlendWellViewHolder(view: View): RecyclerView.ViewHolder(view) {
        lateinit var intent: Intent
        init {
            view.cv_oils_blends.setOnClickListener {
                intent = Intent(context, OilAndBlendDetailActivity::class.java)
                intent.putExtra("item_name",blendWellList.get(adapterPosition).name)
                (context as OilAndBlendDetailActivity).startActivity(intent)

            }
        }
    }
}
