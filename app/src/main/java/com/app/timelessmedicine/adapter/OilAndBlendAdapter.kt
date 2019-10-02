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
import com.app.timelessmedicine.ui.oilsandblends.OilsBlendsActivity
import kotlinx.android.synthetic.main.custom_layout_oil_and_blend.view.*

class OilAndBlendAdapter(val context: Context,val alphabetList:ArrayList<CommonNameTypeBean>): RecyclerView.Adapter<OilAndBlendAdapter.OilBlendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OilBlendViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.custom_layout_oil_and_blend,parent,false)
    return OilBlendViewHolder(view)
    }

    override fun getItemCount(): Int {
    return alphabetList.size
    }

    override fun onBindViewHolder(holder: OilBlendViewHolder, position: Int) {

        // type oil =1 and blend = 2

        var itemType = alphabetList[position].type
        if(itemType==1)
        {
            holder.itemView.img_oil.visibility = View.VISIBLE
            holder.itemView.img_blend.visibility = View.GONE

        }
        else
        {
            holder.itemView.img_oil.visibility = View.GONE
            holder.itemView.img_blend.visibility = View.VISIBLE
        }
        holder.itemView.name_oil.text = alphabetList[position].name

    }

    inner class OilBlendViewHolder(view:View): RecyclerView.ViewHolder(view) {
        lateinit var intent:Intent
        init {
            view.cv_oils_blends.setOnClickListener {
                intent = Intent(context,OilAndBlendDetailActivity::class.java)
                intent.putExtra("item_name",alphabetList.get(adapterPosition).name)
                intent.putExtra("item_type",alphabetList.get(adapterPosition).type.toString())
                (context as OilsBlendsActivity).startActivity(intent)

            }
        }
    }
}
