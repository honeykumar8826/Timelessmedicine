package com.app.timelessmedicine.ui.oilandblenddetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.timelessmedicine.R
import com.app.timelessmedicine.adapter.*
import com.app.timelessmedicine.bean.response.blends.ForAndroidBlend
import com.app.timelessmedicine.bean.response.blends.MainConstituents
import com.app.timelessmedicine.bean.response.blends.SingleBlendBean
import com.app.timelessmedicine.bean.response.common.CommonNameTypeBean
import com.app.timelessmedicine.bean.response.common.MainConstituentBean
import com.app.timelessmedicine.bean.response.oils.*
import com.app.timelessmedicine.utils.NetworkUtils
import com.app.timelessmedicine.utils.ProgressDialogUtils
import kotlinx.android.synthetic.main.activity_oil_and_blend_detail.*
import kotlinx.android.synthetic.main.activity_oil_and_blend_detail.img_back
import kotlinx.android.synthetic.main.activity_oil_and_blend_detail.tv_title_name
import showShortToast

class OilAndBlendDetailActivity : AppCompatActivity() {
    private lateinit var oilData: SingleOilBean
    private lateinit var blendData: SingleBlendBean
    private lateinit var oilBlendDetailViewModel: OilBlendDetailViewModel
    private lateinit var oilList: ArrayList<AllData>
    private lateinit var blendList: ArrayList<ForAndroidBlend>
    private lateinit var mainConstituentList: ArrayList<MainConstituentBean>
    private lateinit var foundBlendList: ArrayList<CommonNameTypeBean>
    private lateinit var mainPropertyList: ArrayList<CommonNameTypeBean>
    private lateinit var blendWellList: ArrayList<CommonNameTypeBean>
    private lateinit var blendConstituentList: ArrayList<CommonNameTypeBean>
    private lateinit var progressDialogUtils: ProgressDialogUtils
    private var getValueFromIntent:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oil_and_blend_detail)
        setInItId()
        // visibiility 0 for off and 1 for on
        setVisibility(0,"default")

        img_back.setOnClickListener {

                finish()

        }
        img_share_oil_blend.setOnClickListener {
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
        if(NetworkUtils.isInternetAvailable(this))
        {
            progressDialogUtils.showProgress(this,false)
            if (intent.getStringExtra("item_name") != null && intent.getStringExtra("item_type") == "2"
            ) {
                getValueFromIntent =intent.getStringExtra("item_name")
                setBlendData(intent.getStringExtra("item_name"))


            } else if (intent.getStringExtra("item_name") != null) {
                getValueFromIntent =intent.getStringExtra("item_name")
                setOilData(intent.getStringExtra("item_name"))

            } else {
                showShortToast("problem in data", this)
                progressDialogUtils.hideProgress()
            }
        }
        else
        {
            showShortToast(getString(R.string.network_not_available),this)

        }

    }

    private fun setVisibility(i: Int, itemType:String) {
        if(i==0)
        {

            ll_local_name.visibility = View.GONE
            ll_latin_name.visibility = View.GONE
            tv_dummy_short_characterstics.visibility = View.GONE
            tv_dummy_description.visibility = View.GONE
            tv_dummy_main_area_usage.visibility = View.GONE
            rv_main_usage.visibility = View.GONE
            ll_main_constitutent.visibility = View.GONE
            ll_found_blend.visibility = View.GONE
            ll_main_property.visibility = View.GONE
            ll_dummy_safety_instruction.visibility = View.GONE
            rl_blend_well_with.visibility = View.GONE
            view_dummy.visibility = View.GONE
            rl_share.visibility = View.GONE

        }
        else
        {
            if(itemType.equals("blend"))
            {
                tv_dummy_short_characterstics.visibility = View.VISIBLE
                tv_dummy_description.visibility = View.VISIBLE
                tv_dummy_main_area_usage.visibility = View.VISIBLE
                rv_main_usage.visibility = View.VISIBLE
                ll_dummy_safety_instruction.visibility = View.VISIBLE
                rl_blend_well_with.visibility = View.VISIBLE
                view_dummy.visibility = View.VISIBLE
                rl_share.visibility = View.VISIBLE
            }
            else
            {
                ll_local_name.visibility = View.VISIBLE
                ll_latin_name.visibility = View.VISIBLE
                tv_dummy_short_characterstics.visibility = View.VISIBLE
                tv_dummy_description.visibility = View.VISIBLE
                tv_dummy_main_area_usage.visibility = View.VISIBLE
                rv_main_usage.visibility = View.VISIBLE
                ll_main_constitutent.visibility = View.VISIBLE
                ll_found_blend.visibility = View.VISIBLE
                ll_main_property.visibility = View.VISIBLE
                ll_dummy_safety_instruction.visibility = View.VISIBLE
                rl_blend_well_with.visibility = View.VISIBLE
                view_dummy.visibility = View.VISIBLE
                rl_share.visibility = View.VISIBLE
            }

        }
    }

    private fun setOilData(oilName: String) {
        oilBlendDetailViewModel.getSingleOilData(oilName).observe(this, Observer {
            oilData = it
            tv_title_name.text = oilData.response.oil_name
            setDataListForOils(oilData.response.all_data)
            setVisibilityOfLayoutOn()
            setUpRecyclerView()
            setValueForTextView()
            setUpListForMainConstituent(oilData.response.Main_constituents)
            setUpListForFoundBlend(oilData.response.objectdata.Found_in_blend)
            setUpListMainProperty(oilData.response.objectdata.Main_properties)
            setUpListForBlendWellWith(oilData.response.objectdata.Blends_well_with)
            progressDialogUtils.hideProgress()
            setVisibility(1,"oil")
        })
    }

    private fun setVisibilityOfLayoutOn() {
        ll_local_name.visibility = View.VISIBLE
        ll_latin_name.visibility = View.VISIBLE
        ll_main_constitutent.visibility = View.VISIBLE
        ll_found_blend.visibility = View.VISIBLE
        ll_main_property.visibility = View.VISIBLE
        tv_blend_well_with.visibility = View.VISIBLE
        tv_main_constituent.visibility = View.GONE
    }

    private fun setBlendData(blendName: String) {
        oilBlendDetailViewModel.getSingleBlendData(blendName).observe(this, Observer {
            blendData = it
            tv_title_name.text = blendData.response.blends_name
            setValueForTextViewInBlend()
            setDataListForBlend(blendData.response.forAndroid_blends)
            setUpRecyclerViewForBlend();
            setVisibilityOfLayoutOff()
            setUpListForBlendMainConstituent(blendData.response.objectdata.Main_constituents)
            progressDialogUtils.hideProgress()
            setVisibility(1,"blend")
        })
    }

    private fun setValueForTextViewInBlend() {
        tv_short_characterstics.text = blendData.response.word_characteristic
        tv_description.text = blendData.response.Description
        tv_safety_instruction.text = blendData.response.Safety_instructions
    }

    private fun setDataListForBlend(forandroidBlends: List<ForAndroidBlend>) {
        for (i in 0..forandroidBlends.size - 1) {
            blendList.add(forandroidBlends.get(i))
        }
    }


    private fun setUpListForBlendMainConstituent(mainConstituents: MainConstituents) {
        var blendsConstituentArray = mainConstituents.Main_constituents.split(",")
        for (i in 0..blendsConstituentArray.size - 1) {
            var blendWellWithBean = CommonNameTypeBean(blendsConstituentArray[i], 1)
            blendConstituentList.add(blendWellWithBean)
        }
        setUpRecyclerViewForBlendMainConstituent()
    }

    private fun setUpRecyclerViewForBlendMainConstituent() {
        rv_blend_well_with.layoutManager = LinearLayoutManager(this)
        rv_blend_well_with.adapter = BlendWellWithAdapter(this, blendConstituentList)
    }

    private fun setVisibilityOfLayoutOff() {
        ll_local_name.visibility = View.GONE
        ll_latin_name.visibility = View.GONE
        ll_main_constitutent.visibility = View.GONE
        ll_found_blend.visibility = View.GONE
        ll_main_property.visibility = View.GONE
        tv_blend_well_with.visibility = View.GONE
        tv_main_constituent.visibility = View.VISIBLE
    }

    private fun setValueForTextView() {
       // tv_local_oil_name.text = oilData.response.local_name
        tv_local_oil_name.text = getValueFromIntent
        tv_latin_oil_name.text = oilData.response.latin_name
        tv_short_characterstics.text = oilData.response.word_characteristic
        tv_description.text = oilData.response.Description
        tv_safety_instruction.text = oilData.response.Safety_instructions
    }

    private fun setUpListForBlendWellWith(blendsWellWith: BlendsWellWith) {
        var blendsWellArray = blendsWellWith.Blends_well_with.split(",")
        for (i in 0..blendsWellArray.size - 1) {
            var blendWellWithBean = CommonNameTypeBean(blendsWellArray[i], blendsWellWith.index)
            blendWellList.add(blendWellWithBean)
        }
        setUpRecyclerViewForBlendWellWith()
    }

    private fun setUpRecyclerViewForBlendWellWith() {
        rv_blend_well_with.layoutManager = LinearLayoutManager(this)
        rv_blend_well_with.adapter = BlendWellWithAdapter(this, blendWellList)
    }

    private fun setUpListMainProperty(mainProperties: MainProperties) {
        var mainPropertyArray = mainProperties.Main_properties.split(",")
        for (i in 0..mainPropertyArray.size - 1) {
            var mainPropertyBean = CommonNameTypeBean(mainPropertyArray[i], mainProperties.index)
            mainPropertyList.add(mainPropertyBean)
        }
        setUpRecyclerViewForMainProperty()
    }

    private fun setUpRecyclerViewForMainProperty() {
        rv_main_properties.layoutManager = LinearLayoutManager(this)
        rv_main_properties.adapter = MainPropertyAdapter(this, mainPropertyList)
    }

    private fun setUpListForFoundBlend(foundInBlend: FoundInBlend) {
        var founBlendArray = foundInBlend.Found_in_blend.split(",")
        for (i in 0..founBlendArray.size - 1) {
            var foundInBlendBean = CommonNameTypeBean(founBlendArray[i], 2)
            foundBlendList.add(foundInBlendBean)

        }
        setUpRecyclerViewForFoundBlend()

    }

    private fun setUpRecyclerViewForFoundBlend() {
        rv_found_blends.layoutManager = LinearLayoutManager(this)
        rv_found_blends.adapter = FoundBlendAdapter(this, foundBlendList)
    }


    private fun setUpListForMainConstituent(mainConstituents: String) {
        var tempArray = mainConstituents.split(",")
        for (i in 0..tempArray.size - 1) {
            if (tempArray[i].contains("(")) {
                var removeParanthesisArray = tempArray[i].split("(")
                var constituentArray = removeParanthesisArray[1].split(")")
                var mainConstituentBean =
                    MainConstituentBean(removeParanthesisArray[0], constituentArray[0])
                mainConstituentList.add(mainConstituentBean)
            } else {

                var mainConstituentBean = MainConstituentBean(tempArray[i], "0")
                mainConstituentList.add(mainConstituentBean)
            }

        }
        setUpRecyclerViewForConstituents()

    }

    private fun setUpRecyclerViewForConstituents() {
        rv_main_constituents.layoutManager = LinearLayoutManager(this)
        rv_main_constituents.adapter = MainConstituentAdapter(this, mainConstituentList)
    }

    private fun setUpRecyclerView() {
        rv_main_usage!!.layoutManager = LinearLayoutManager(this)
        rv_main_usage.adapter = OilMainUsageAdapter(this, oilList)
        rv_main_usage.setHasFixedSize(true)
    }
    private fun setUpRecyclerViewForBlend() {
        rv_main_usage!!.layoutManager = LinearLayoutManager(this)
        rv_main_usage.adapter = BlendMainUsageAdapter(this, blendList)
        rv_main_usage.setHasFixedSize(true)
    }

    private fun setDataListForOils(allData: List<AllData>) {
        for (i in 0..allData.size - 1) {
            oilList.add(allData.get(i))
        }
    }

    private fun setInItId() {

        oilList = ArrayList()
        mainConstituentList = ArrayList()
        foundBlendList = ArrayList()
        mainPropertyList = ArrayList()
        blendWellList = ArrayList()
        blendConstituentList = ArrayList()
        blendList = ArrayList()
        oilBlendDetailViewModel =
            ViewModelProviders.of(this).get(OilBlendDetailViewModel::class.java)
        oilBlendDetailViewModel.inIt()
        progressDialogUtils = ProgressDialogUtils().getInstance()

    }
}
