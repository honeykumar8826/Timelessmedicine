package com.app.timelessmedicine.ui.homepage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.timelessmedicine.R
import com.app.timelessmedicine.bean.HomeBean
import com.app.timelessmedicine.databinding.HomeMenuItemViewBinding
import com.app.timelessmedicine.ui.home.HomeActivity
import com.app.timelessmedicine.ui.oilsandblends.OilsBlendsActivity
import com.app.timelessmedicine.ui.profile.ProfileFragments
import com.app.timelessmedicine.ui.properties.PropertyActivity
import kotlinx.android.synthetic.main.home_menu_item_view.view.*

class AdapterHomePage:RecyclerView.Adapter<AdapterHomePage.HomeViewHolder>() {

    var lists = ArrayList<HomeBean>()
    var ctx:Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {

        val binding: HomeMenuItemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.home_menu_item_view, parent, false)
        return HomeViewHolder(binding, lists, ctx!!)

        /*return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_menu_item_view,
            parent, false))*/
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(lists[position], position)
    }


    fun updateHomePageList(lists:ArrayList<HomeBean>, ctx:Context){
        this.lists = lists
        this.ctx = ctx
    }

    class HomeViewHolder(val binding:HomeMenuItemViewBinding,
                         val lists: ArrayList<HomeBean>,
                         val ctx:Context):RecyclerView.ViewHolder(binding.root),
        HomeItemViewModel.ItemClickListener{
        lateinit var intent:Intent

        init {

        }

        override fun onItemClick(position: Int) {
            if(position <= lists.size - 1) {
                when(position)
                {
                    0->{

                    }
                    1->{

                    }
                    2->{

                    }
                    3->{

                    }
                    4->
                    {
                       intent = Intent(ctx,OilsBlendsActivity::class.java)
                        (ctx as HomeActivity).startActivity(intent)
                    }
                    5->{

                    }
                    6->{
                        intent = Intent(ctx,PropertyActivity::class.java)
                        (ctx as HomeActivity).startActivity(intent)
                    }
                    7->{
                        (ctx as HomeActivity).changeFragment(ProfileFragments())
                    }
                }

            }
        }

        private val viewModel = HomeItemViewModel()


        fun bind(post:HomeBean, position: Int){
            viewModel.bind(post, position)
            viewModel.setMlistener(this)
            binding.viewModel = viewModel
            if(isEven((position+1))){
                binding.viewTop.visibility = View.GONE
                binding.viewRight.visibility = View.GONE
                binding.viewLeft.visibility = View.GONE
                binding.viewBottom.visibility = View.VISIBLE
            } else {
                binding.viewTop.visibility = View.GONE
                binding.viewBottom.visibility = View.VISIBLE
                binding.viewRight.visibility = View.VISIBLE
                binding.viewLeft.visibility = View.GONE
            }
        }


        fun isEven(position: Int):Boolean{
            return position%2 == 0
        }
    }




}