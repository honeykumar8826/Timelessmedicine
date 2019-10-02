package com.app.timelessmedicine.ui.propertydetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.timelessmedicine.R
import com.app.timelessmedicine.adapter.PropertyDetailAdapter
import com.app.timelessmedicine.bean.response.common.CommonNameTypeBean
import com.app.timelessmedicine.bean.response.propertydetail.ProductNameWithIndex
import com.app.timelessmedicine.bean.response.propertydetail.SinglePropertyBean
import com.app.timelessmedicine.utils.NetworkUtils
import com.app.timelessmedicine.utils.ProgressDialogUtils
import kotlinx.android.synthetic.main.activity_oil_and_blend_detail.*
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.android.synthetic.main.activity_property_detail.*
import showShortToast

class PropertyDetailActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var propertyDetailViewModel: PropertyDetailViewModel
    private lateinit var singlePropertyBean: SinglePropertyBean
    private lateinit var propertyDetailList: ArrayList<CommonNameTypeBean>
    private lateinit var progressDialogUtils: ProgressDialogUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)
        setInItId()
        if(NetworkUtils.isInternetAvailable(this))
        {
            setObserverForData()
            if (intent.getStringExtra("item_name") != null) {
                setPropertyData(intent.getStringExtra("item_name"))
            } else {
                showShortToast("problem in data", this)
                progressDialogUtils.hideProgress()
            }
        }
        else
        {
            showShortToast(getString(R.string.network_not_available),this)

        }


        /*    if (intent.getStringExtra("item_name") != null && intent.getStringExtra("item_type") == "2"
            ) {

                setPropertyData(intent.getStringExtra("item_name"))

            }*/

    }

    private fun setObserverForData() {

            progressDialogUtils.showProgress(this, true)
            propertyDetailViewModel =
                ViewModelProviders.of(this).get(PropertyDetailViewModel::class.java)
            img_back_property.setOnClickListener(this)

            propertyDetailViewModel.responsePropertyDetail
                .observe(this, Observer {
                    Log.e("###", "ObserverCalled")
                    singlePropertyBean = it

                    if (singlePropertyBean != null) {

                        //showShortToast(singlePropertyBean.message, this)
                        setTextViewValue()
                        setDataListForPropertyDetail(singlePropertyBean.response.product_name_with_index)
                        progressDialogUtils.hideProgress()
                        setVisibility(1)
                    }

                })


    }

    private fun setVisibility(i: Int) {
        if (i == 0) {
            rl_share_property.visibility = View.GONE
            rv_property_detail.visibility = View.GONE
        } else {
            rl_share_property.visibility = View.VISIBLE
            rv_property_detail.visibility = View.VISIBLE
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_back_property -> {
                finish()
            }
            R.id.img_property_share -> {

                    val i = Intent(Intent.ACTION_VIEW)
                    // i.data = Uri.parse(offer!!.storewebaddress)
                    try {
                        var url = "https://play.google.com/store?hl=en"
                        if (!url!!.startsWith("http://") && !url.startsWith("https://")) {
                            url = "http://" + url
                            i.setData(Uri.parse(url))
                            startActivity(i)
                        } else {
                            i.setData(Uri.parse(url))
                            startActivity(i)
                        }

                    } catch (e: Exception) {
                        showShortToast("some problem occured", this)
                    }
                }


        }
    }

    private fun setInItId() {
        progressDialogUtils = ProgressDialogUtils().getInstance()
        propertyDetailList = ArrayList()
        setVisibility(0)
        img_property_share.setOnClickListener(this)



    }

    private fun setTextViewValue() {
        tv_title_name_property.text = singlePropertyBean.response.property_name
    }


    private fun setPropertyData(name: String) {
        propertyDetailViewModel.hitPropertyDetail(name)
    }

    private fun setDataListForPropertyDetail(response: List<ProductNameWithIndex>) {
        for (i in 0..response!!.size - 1) {
            var commonNameTypeBean = CommonNameTypeBean(response[i].name, response[i].index)
            propertyDetailList.add(commonNameTypeBean)
        }
        setUpRecyclerViewForPropertyDetail()
    }

    private fun setUpRecyclerViewForPropertyDetail() {
        rv_property_detail.adapter = PropertyDetailAdapter(this, propertyDetailList)
    }
}




