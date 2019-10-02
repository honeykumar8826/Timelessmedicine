package com.app.timelessmedicine.ui.oilsandblends

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.app.timelessmedicine.R
import com.app.timelessmedicine.SelectedLetterCallback
import com.app.timelessmedicine.adapter.AlphabetListAdapter
import com.app.timelessmedicine.adapter.OilAndBlendAdapter
import com.app.timelessmedicine.bean.response.alphabets.AlphabetBean
import com.app.timelessmedicine.bean.response.common.CommonNameTypeBean
import com.app.timelessmedicine.bean.response.oilblends.Blend
import com.app.timelessmedicine.bean.response.oilblends.Oil
import com.app.timelessmedicine.bean.response.oilblends.OilBlendBean
import com.app.timelessmedicine.ui.base.MyBaseActivity
import com.app.timelessmedicine.utils.NetworkUtils
import com.app.timelessmedicine.utils.ProgressDialogUtils
import kotlinx.android.synthetic.main.activity_oils_blends.*
import showShortToast


class OilsBlendsActivity : MyBaseActivity(), View.OnClickListener, SelectedLetterCallback {


    //Default Alphabet
    private var alphabet = arrayOf(
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z"
    )

    //Example of data
    internal val orderedDataForOils = ArrayList<CommonNameTypeBean>()
    internal val orderedDataForBlends = ArrayList<CommonNameTypeBean>()
    internal val indexOfLetterList = ArrayList<Int>()
    internal val indexOfLetterListForBlend = ArrayList<Int>()
    private lateinit var oilBlendsViewModel: OilBlendsViewModel
    var fragment: Fragment? = null
    lateinit var oilBlendBean: OilBlendBean
    private lateinit var progressDialogUtils: ProgressDialogUtils
    private var scrollPosition: Int = 0
    private var scrollPositionForBlend: Int = 0
    private var selectedTextview: TextView? = null
    internal val alphabetListWithStyle = ArrayList<AlphabetBean>()
    // for storing previous check state of alphabet for scrolling
    var storePreviousIndex: Int = 0
    var storePreviousIndexForBlend: Int = 0
    // for storing previous check state of alphabet for button click
    var previousButtonClickIndex: Int = 0
    var recyclerViewStatus:Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oils_blends)
        setInItId()
        setAlphabetList()
        // 0 for visibility off and 1 for on 1
        setVisibility(0)

        if (NetworkUtils.isInternetAvailable(this)) {
            progressDialogUtils.showProgress(this, true)
            setValueInTextView()
            // ProgressDialogUtils.getInstance().showProgress(this,false)
            oilBlendsViewModel.getOilAndBlend().observe(this, Observer {
                oilBlendBean = it
                setDataListForOils(oilBlendBean.response.oil)
                setUpRecyclerViewForAlphabetList()
                progressDialogUtils.hideProgress()
                setVisibility(1)
                // ProgressDialogUtils.getInstance().showProgress(this,false)
            })

        } else {
            showShortToast("internet not available", this)
        }

    }

    private fun setAlphabetList() {
        for (i in 0..25) {
            val alphabetBean = AlphabetBean(alphabet[i], 0)
            alphabetListWithStyle.add(alphabetBean)
        }

    }

    private fun setVisibility(i: Int) {
        if (i == 0) {
            tv_sort_letter.visibility = View.GONE
            rl_up_arrow.visibility = View.GONE
            rl_img_scroll_up.visibility = View.GONE
        } else {
            tv_sort_letter.visibility = View.VISIBLE
            rl_up_arrow.visibility = View.VISIBLE
            rl_img_scroll_up.visibility = View.VISIBLE
        }

    }

    private fun setValueInTextView() {
        tv_title_name.text = "Oils & Blends"
    }

    private fun checkIndexOfLetters(orderedDataForBlends: ArrayList<CommonNameTypeBean>) {
        //default value -1 when no data is found for any index
        setDefaultValueOnList()

        for (i in 0..orderedDataForBlends.size - 1) {

            when (orderedDataForBlends[i].name[0]) {

                // without indexing, problem occur  when any alphabet is missing in a list
                'A' -> {
                    if (indexOfLetterList.get(0) == -1) {
                        indexOfLetterList.set(0, i)
                    }


                }
                'B' -> {
                    if (indexOfLetterList.get(1) == -1) {
                        indexOfLetterList.set(1, i)
                    }


                }
                'C' -> {
                    if (indexOfLetterList.get(2) == -1) {
                        indexOfLetterList.set(2, i)
                    }


                }
                'D' -> {
                    if (indexOfLetterList.get(3) == -1) {
                        indexOfLetterList.set(3, i)
                    }


                }
                'E' -> {
                    if (indexOfLetterList.get(4) == -1) {
                        indexOfLetterList.set(4, i)
                    }


                }
                'F' -> {
                    if (indexOfLetterList.get(5) == -1) {
                        indexOfLetterList.set(5, i)
                    }


                }
                'G' -> {
                    if (indexOfLetterList.get(6) == -1) {
                        indexOfLetterList.set(6, i)
                    }


                }
                'H' -> {
                    if (indexOfLetterList.get(7) == -1) {
                        indexOfLetterList.set(7, i)
                    }


                }
                'I' -> {
                    if (indexOfLetterList.get(8) == -1) {
                        indexOfLetterList.set(8, i)
                    }


                }
                'J' -> {
                    if (indexOfLetterList.get(9) == -1) {
                        indexOfLetterList.set(9, i)
                    }


                }
                'K' -> {
                    if (indexOfLetterList.get(10) == -1) {
                        indexOfLetterList.set(10, i)
                    }


                }
                'L' -> {
                    if (indexOfLetterList.get(11) == -1) {
                        indexOfLetterList.set(11, i)
                    }


                }
                'M' -> {
                    if (indexOfLetterList.get(12) == -1) {
                        indexOfLetterList.set(12, i)
                    }


                }
                'N' -> {
                    if (indexOfLetterList.get(13) == -1) {
                        indexOfLetterList.set(13, i)
                    }


                }
                'O' -> {
                    if (indexOfLetterList.get(14) == -1) {
                        indexOfLetterList.set(14, i)
                    }


                }
                'P' -> {
                    if (indexOfLetterList.get(15) == -1) {
                        indexOfLetterList.set(15, i)
                    }

                }
                'Q' -> {
                    if (indexOfLetterList.get(16) == -1) {
                        indexOfLetterList.set(16, i)
                    }


                }
                'R' -> {
                    if (indexOfLetterList.get(17) == -1) {
                        indexOfLetterList.set(17, i)
                    }


                }
                'S' -> {
                    if (indexOfLetterList.get(18) == -1) {
                        indexOfLetterList.set(18, i)
                    }


                }
                'T' -> {
                    if (indexOfLetterList.get(19) == -1) {
                        indexOfLetterList.set(19, i)
                    }


                }
                'U' -> {
                    if (indexOfLetterList.get(20) == -1) {
                        indexOfLetterList.set(20, i)
                    }


                }
                'V' -> {
                    if (indexOfLetterList.get(21) == -1) {
                        indexOfLetterList.set(21, i)
                    }


                }
                'W' -> {
                    if (indexOfLetterList.get(22) == -1) {
                        indexOfLetterList.set(22, i)
                    }


                }
                'X' -> {
                    if (indexOfLetterList.get(23) == -1) {
                        indexOfLetterList.set(23, i)
                    }


                }

                'Y' -> {
                    if (indexOfLetterList.get(24) == -1) {
                        indexOfLetterList.set(24, i)
                    }


                }
                'Z' -> {
                    if (indexOfLetterList.get(25) == -1) {
                        indexOfLetterList.set(25, i)
                    }


                }
            }
        }
    }


    private fun setDefaultValueOnList() {
        for (i in 0..26) {
            indexOfLetterList.add(i, -1)
        }
    }

    private fun setUpRecyclerViewForAlphabetList() {
        rv_letters.adapter =
            AlphabetListAdapter(this, alphabetListWithStyle, this, indexOfLetterList)
        rv_letters!!.layoutManager = /*LinearLayoutPagerManager(this, VERTICAL, false,26)*/
            LinearLayoutManager(this)
        rv_letters.setHasFixedSize(true)
    }

    private fun setInItId() {
        oilBlendsViewModel = ViewModelProviders.of(this).get(OilBlendsViewModel::class.java)
        oilBlendsViewModel.inIt()
        btn_oils.setOnClickListener(this)
        btn_blends.setOnClickListener(this)
        img_back.setOnClickListener(this)
        rl_img_scroll_up.setOnClickListener(this)
        progressDialogUtils = ProgressDialogUtils().getInstance()

    }

    private fun setUpRecyclerView(type:String) {
        if(type=="oils")
        {
            setListenerForRecyclerviewForOil()
        }

        else
        {
            setListenerForRecycerviewForBlend()
        }

        } /*else {
            rv_oil_blend.adapter = OilAndBlendAdapter(this, orderedDataForBlends)
            tv_sort_letter.text = "A"
            setListenerForRecycerviewForBlend()
        }*/

    @SuppressLint("WrongConstant")
    private fun setListenerForRecyclerviewForOil() {
        var linearLayoutManager = LinearLayoutManager(this, VERTICAL, false)
        rv_oil!!.layoutManager = linearLayoutManager
        rv_oil.adapter = OilAndBlendAdapter(this, orderedDataForOils)
        tv_sort_letter.text = "A"
        setScrollListenerForOil()
        (rv_oil.adapter as OilAndBlendAdapter).notifyDataSetChanged()

    }

    @SuppressLint("WrongConstant")
    private fun setListenerForRecycerviewForBlend() {
        var linearLayoutManager = LinearLayoutManager(this, VERTICAL, false)
        rv_blend!!.layoutManager = linearLayoutManager
        rv_blend.adapter = OilAndBlendAdapter(this, orderedDataForBlends)
        tv_sort_letter.text = "A"
        rv_blend.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                scrollPositionForBlend =
                    (rv_blend.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                //showShortToast(""+scrollPosition,this@OilsBlendsActivity)
                if (scrollPositionForBlend <= orderedDataForBlends.size) {
                    tv_sort_letter.text =
                        orderedDataForBlends[scrollPositionForBlend].name[0].toString()
                    /*    if(storePreviousIndexForBlend !=0)
                        {

                            var alphabetBean = AlphabetBean(alphabet[storePreviousIndexForBlend],0)
                            alphabetListWithStyle.set(storePreviousIndexForBlend,alphabetBean)
                            (rv_letters.adapter as AlphabetListAdapter).notifyDataSetChanged()
                        }*/
                    scrolAlphabetView(tv_sort_letter.text.toString(), "blends")
                    selectedTextview?.setTypeface(
                        selectedTextview!!.getTypeface(),
                        Typeface.NORMAL
                    )
                }
                (rv_blend.adapter as OilAndBlendAdapter).notifyDataSetChanged()
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })




        //


    }

    private fun setScrollListenerForOil() {
        rv_oil.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                    scrollPosition =
                        (rv_oil.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (scrollPosition <= orderedDataForOils.size) {

                        tv_sort_letter.text = orderedDataForOils[scrollPosition].name[0].toString()
                        /*      if(storePreviousIndex !=0)
                              {

                                  var alphabetBean = AlphabetBean(alphabet[storePreviousIndex],0)
                                  alphabetListWithStyle.set(storePreviousIndex,alphabetBean)
                                  (rv_letters.adapter as AlphabetListAdapter).notifyDataSetChanged()
                              }*/
                        scrolAlphabetView(tv_sort_letter.text.toString(), "Oils")
                        selectedTextview?.setTypeface(
                            selectedTextview!!.getTypeface(),
                            Typeface.NORMAL
                        )

                    }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun scrolAlphabetView(alphabetName: String, type: String) {


        var alphabetIndex = alphabet.indexOf(alphabetName)
        setListWithNormalStyleByScroll(alphabetIndex)
        /*      if(type=="Oils")
              {
                  storePreviousIndex = alphabetIndex
              }
              else
              {
                  storePreviousIndexForBlend =alphabetIndex
              }

              (rv_letters.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(alphabetIndex,0)
              var alphabetBean = AlphabetBean(alphabet[alphabetIndex],1)
              alphabetListWithStyle.set(alphabetIndex,alphabetBean)
              (rv_letters.adapter as AlphabetListAdapter).notifyDataSetChanged()*/
    }

    // when we scroll on button  remove all bold style excepted selcted one
    private fun setListWithNormalStyleByScroll(index: Int) {
        for (i in 0..25) {
            if (i == index) {
                var alphabetBean = AlphabetBean(alphabet[i], 1)
                alphabetListWithStyle.set(i, alphabetBean)
            } else {
                var alphabetBean = AlphabetBean(alphabet[i], 0)
                alphabetListWithStyle.set(i, alphabetBean)
            }
        }
        (rv_letters.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            index,
            0
        )
        (rv_letters.adapter as AlphabetListAdapter).notifyDataSetChanged()

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_oils -> {
                recyclerViewStatus=0
                rv_oil.visibility = View.VISIBLE
                rv_blend.visibility = View.GONE
                (rv_letters.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    0,
                    0
                )
                setListWithNormalStyle()
                btn_oils.setBackgroundResource(R.drawable.button_profile_bg)
                btn_blends.setBackgroundResource(R.drawable.button_profile_white_bg)
                setDataListForOils(oilBlendBean.response.oil)

            }
            R.id.btn_blends -> {
                recyclerViewStatus=1
                rv_blend.visibility = View.VISIBLE
                rv_oil.visibility = View.GONE
                (rv_letters.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    0,
                    0
                )
                setListWithNormalStyle()
                btn_oils.setBackgroundResource(R.drawable.button_profile_white_bg)
                btn_blends.setBackgroundResource(R.drawable.button_profile_bg)
                setDataListForBlends(oilBlendBean.response.blends)
            }
            R.id.img_back -> {
                finish()
            }
            R.id.rl_img_scroll_up -> {
                if(recyclerViewStatus==0)
                {
                    (rv_oil.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                        0,
                        0
                    )

                }
                else
                {
                    (rv_blend.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                        0,
                        0
                    )
                }
                (rv_letters.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    0,
                    0
                )

                tv_sort_letter.text = "A"
                setListWithNormalStyle()
            }
        }


    }

    // when we scroll to top then remove all bold style
    private fun setListWithNormalStyle() {
        for (i in 0..25) {
            if (i != 0) {
                var alphabetBean = AlphabetBean(alphabet[i], 0)
                alphabetListWithStyle.set(i, alphabetBean)
            } else {
                var alphabetBean = AlphabetBean(alphabet[i], 1)
                alphabetListWithStyle.set(i, alphabetBean)
            }


        }

        (rv_letters.adapter as AlphabetListAdapter).notifyDataSetChanged()

    }

    private fun setDataListForBlends(blends: List<Blend>) {
        for (i in 0..blends.size - 1) {
            var commonNameTypeBean = CommonNameTypeBean(blends[i].blends_name, 2)
            orderedDataForBlends.add(commonNameTypeBean)
        }
        checkIndexOfLetters(orderedDataForBlends)
        setUpRecyclerView("blends")
    }

    private fun setDataListForOils(oil: List<Oil>) {
        for (i in 0..oil.size - 1) {
            var commonNameTypeBean = CommonNameTypeBean(oil[i].oil_name, 1)
            orderedDataForOils.add(commonNameTypeBean)
        }
        checkIndexOfLetters(orderedDataForOils)
        setUpRecyclerView("oils")
    }

    override fun clickItemPosition(key: Int, indexOfLetter: Int) {
        //showShortToast(alphabet[key], this)
        if (indexOfLetter != -1) {

            if(recyclerViewStatus==0)
            {
                (rv_oil.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    indexOfLetter,
                    0
                )
            }
            else
            {
                (rv_blend.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    indexOfLetter,
                    0
                )
            }

            tv_sort_letter.text = alphabet[key]

        } else {
            showShortToast(getString(R.string.no_data_find), this)
            tv_sort_letter.text = alphabet[key]
        }
    }

    override fun selectedPosition(textView: TextView?) {
        selectedTextview = textView
        var alphabetIndex = alphabet.indexOf(selectedTextview?.text.toString())
        // for button click type is btn
        //working code
        setListWithNormalStyleByButton(alphabetIndex)
        /*   if(previousButtonClickIndex !=0)
           {
               //(rv_letters.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(alphabetIndex,0)
               var alphabetBean = AlphabetBean(alphabet[previousButtonClickIndex],0)
               alphabetListWithStyle.set(previousButtonClickIndex,alphabetBean)
               (rv_letters.adapter as AlphabetListAdapter).notifyDataSetChanged()
           }
           var alphabetBean = AlphabetBean(alphabet[alphabetIndex],1)
           alphabetListWithStyle.set(alphabetIndex,alphabetBean)
           (rv_letters.adapter as AlphabetListAdapter).notifyDataSetChanged()


           previousButtonClickIndex = alphabetIndex*/
    }

    // when we click on button  remove all bold style excepted selcted one
    private fun setListWithNormalStyleByButton(index: Int) {
        for (i in 0..25) {
            if (i == index) {
                var alphabetBean = AlphabetBean(alphabet[i], 1)
                alphabetListWithStyle.set(i, alphabetBean)
            } else {
                var alphabetBean = AlphabetBean(alphabet[i], 0)
                alphabetListWithStyle.set(i, alphabetBean)
            }
        }
        (rv_letters.adapter as AlphabetListAdapter).notifyDataSetChanged()

    }

}
