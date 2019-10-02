package com.app.timelessmedicine.ui.properties

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.timelessmedicine.R
import com.app.timelessmedicine.SelectedLetterCallback
import com.app.timelessmedicine.adapter.AlphabetListAdapter
import com.app.timelessmedicine.adapter.PropertyAdapter
import com.app.timelessmedicine.bean.response.alphabets.AlphabetBean
import com.app.timelessmedicine.bean.response.common.CommonNameTypeBean
import com.app.timelessmedicine.bean.response.property.PropertyBean
import com.app.timelessmedicine.bean.response.property.Response
import com.app.timelessmedicine.utils.NetworkUtils
import com.app.timelessmedicine.utils.ProgressDialogUtils
import kotlinx.android.synthetic.main.activity_property.*
import showShortToast

class PropertyActivity : AppCompatActivity(), SelectedLetterCallback, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rl_img_scroll_up_property -> {
                (rv_property.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    0,
                    0
                )
                (rv_letters_for_property.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    0,
                    0
                )
                tv_sort_letter_for_property.text = "A"
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

        (rv_letters_for_property.adapter as AlphabetListAdapter).notifyDataSetChanged()

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
        (rv_letters_for_property.adapter as AlphabetListAdapter).notifyDataSetChanged()

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
        (rv_letters_for_property.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            index,
            0
        )
        (rv_letters_for_property.adapter as AlphabetListAdapter).notifyDataSetChanged()

    }


    //Default Alphabet
    internal val propertyList = ArrayList<CommonNameTypeBean>()
    internal val indexOfLetterList = ArrayList<Int>()
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
    private lateinit var propertyViewModel: PropertyViewModel
    lateinit var propertyBean: PropertyBean
    private lateinit var progressDialogUtils: ProgressDialogUtils
    private var scrollPosition: Int = 0
    internal val alphabetListWithStyle = ArrayList<AlphabetBean>()
    private var selectedTextview: TextView? = null
    var storePreviousIndex: Int = 0
    var previousButtonClickIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)

        setInItId()
        setAlphabetList()
        setVisibility(0)



        if (NetworkUtils.isInternetAvailable(this)) {
            progressDialogUtils.showProgress(this, true)
            propertyViewModel.propertyRepo.getData().observe(this, Observer {
                propertyBean = it
                if (propertyBean != null) {

                    // showShortToast(propertyBean.message, this)
                    setDataListForProperty(propertyBean.response)
                    setUpRecyclerViewForAlphabetList()
                    progressDialogUtils.hideProgress()
                    setVisibility(1)
                }

            })
            propertyViewModel.getPropertyData()
            img_back_property_detail.setOnClickListener {
                finish()

            }
        } else {
            showShortToast(getString(R.string.network_not_available), this)

        }

    }

    private fun setVisibility(i: Int) {
        if (i == 0) {
            tv_sort_letter_for_property.visibility = View.GONE
            rl_up_arrow_property.visibility = View.GONE
        } else {
            tv_sort_letter_for_property.visibility = View.VISIBLE
            rl_up_arrow_property.visibility = View.VISIBLE
        }
    }


    private fun setUpRecyclerViewForAlphabetList() {
        rv_letters_for_property.adapter =
            AlphabetListAdapter(this, alphabetListWithStyle, this, indexOfLetterList)
        rv_letters_for_property!!.layoutManager =
                /*LinearLayoutPagerManager(this, VERTICAL, false,26)*/
            LinearLayoutManager(this)
        rv_letters_for_property.setHasFixedSize(true)
    }

    private fun setDataListForProperty(response: List<Response>) {
        for (i in 0..response!!.size - 1) {
            var commonNameTypeBean = CommonNameTypeBean(response[i].property_name, 3)
            propertyList.add(commonNameTypeBean)
        }
        checkIndexOfLetters(propertyList)
        setUpRecyclerViewForProperty()
    }

    private fun setUpRecyclerViewForProperty() {
        rv_property.adapter = PropertyAdapter(this, propertyList)
        rv_property.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollPosition =
                    (rv_property.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (scrollPosition <= propertyList.size) {
                    /*   if (storePreviousIndex != 0) {

                           var alphabetBean = AlphabetBean(alphabet[storePreviousIndex], 0)
                           alphabetListWithStyle.set(storePreviousIndex, alphabetBean)
                           (rv_letters_for_property.adapter as AlphabetListAdapter).notifyDataSetChanged()
                       }*/
                    tv_sort_letter_for_property.text =
                        propertyList[scrollPosition].name[0].toString()
                    scrolAlphabetView(tv_sort_letter_for_property.text.toString())
                    selectedTextview?.setTypeface(selectedTextview!!.getTypeface(), Typeface.NORMAL)
                }
                //(rv_letters.adapter as AlphabetListAdapter).ite


                //showShortToast((rv_oil_blend.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition().toString(),this@OilsBlendsActivity)

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    private fun scrolAlphabetView(alphabetName: String) {

        var alphabetIndex = alphabet.indexOf(alphabetName)
        setListWithNormalStyleByScroll(alphabetIndex)

        /* storePreviousIndex = alphabetIndex
         (rv_letters_for_property.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
             alphabetIndex,
             0
         )
         var alphabetBean = AlphabetBean(alphabet[alphabetIndex], 1)
         alphabetListWithStyle.set(alphabetIndex, alphabetBean)
         (rv_letters_for_property.adapter as AlphabetListAdapter).notifyDataSetChanged()*/
    }

    private fun setInItId() {
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel::class.java)
        //  propertyViewModel.inIt()
        progressDialogUtils = ProgressDialogUtils().getInstance()
        rl_img_scroll_up_property.setOnClickListener(this)
    }

    private fun checkIndexOfLetters(propertyData: ArrayList<CommonNameTypeBean>) {
        //default value -1 when no data is found for any index
        setDefaultValueOnList()
        for (i in 0..propertyData.size - 1) {
            if (!propertyData[i].name[0].equals("") && propertyData[i].name[0] != null)
                when (propertyData[i].name[0]) {

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
                    else -> {

                    }
                }
        }
    }

    private fun setDefaultValueOnList() {
        for (i in 0..26) {
            indexOfLetterList.add(i, -1)
        }
    }

    override fun clickItemPosition(key: Int, indexOfLetter: Int) {
        // showShortToast(alphabet[key], this)
        if (indexOfLetter != -1) {
            (rv_property.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                indexOfLetter,
                0
            )

            tv_sort_letter_for_property.text = alphabet[key]

        } else {
            tv_sort_letter_for_property.text = alphabet[key]
            showShortToast(getString(R.string.no_data_find), this)
        }
    }

    override fun selectedPosition(textView: TextView?) {
        selectedTextview = textView

        var alphabetIndex = alphabet.indexOf(selectedTextview?.text.toString())
        // for button click type is btn
        //working code
        setListWithNormalStyleByButton(alphabetIndex)
        /*  if (previousButtonClickIndex != 0) {
              //(rv_letters.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(alphabetIndex,0)
              var alphabetBean = AlphabetBean(alphabet[previousButtonClickIndex], 0)
              alphabetListWithStyle.set(previousButtonClickIndex, alphabetBean)
              (rv_letters_for_property.adapter as AlphabetListAdapter).notifyDataSetChanged()
          }
          var alphabetBean = AlphabetBean(alphabet[alphabetIndex], 1)
          alphabetListWithStyle.set(alphabetIndex, alphabetBean)
          (rv_letters_for_property.adapter as AlphabetListAdapter).notifyDataSetChanged()


          previousButtonClickIndex = alphabetIndex*/
    }

    private fun setAlphabetList() {
        for (i in 0..25) {
            val alphabetBean = AlphabetBean(alphabet[i], 0)
            alphabetListWithStyle.add(alphabetBean)
        }

    }
}

