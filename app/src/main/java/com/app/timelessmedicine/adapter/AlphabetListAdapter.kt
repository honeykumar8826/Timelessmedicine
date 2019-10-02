package com.app.timelessmedicine.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.timelessmedicine.R
import com.app.timelessmedicine.SelectedLetterCallback
import com.app.timelessmedicine.bean.response.alphabets.AlphabetBean
import kotlinx.android.synthetic.main.custom_layout_alphabet_list.view.*
import java.util.*
import kotlin.collections.ArrayList


class AlphabetListAdapter(
    val context: Context,
    val alphabetList: ArrayList<AlphabetBean>,
    val selectedLetterCallback: SelectedLetterCallback,
    val indexOfLetter: ArrayList<Int>
) : RecyclerView.Adapter<AlphabetListAdapter.AlphabetListViewHolder>() {
    private lateinit var textView:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetListViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_alphabet_list, parent, false)
        return AlphabetListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return alphabetList.size
    }

    override fun onBindViewHolder(holder: AlphabetListViewHolder, position: Int) {
        textView = holder.itemView.tv_letters
        if(alphabetList[position].styleType==1)
        {
            holder.itemView.tv_letters.text = alphabetList[position].alphabet
            holder.itemView.tv_letters.setTypeface(textView.getTypeface(), Typeface.BOLD)
        }
        else
        {
            holder.itemView.tv_letters.text = alphabetList[position].alphabet
            holder.itemView.tv_letters.setTypeface(textView.getTypeface(), Typeface.NORMAL)
        }

    }

    inner class AlphabetListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {

            itemView.setOnClickListener {
                val pos = adapterPosition
                if (indexOfLetter[pos] != -1) {
                    //itemView.tv_letters.setTypeface(textView.getTypeface(), Typeface.BOLD)
                    selectedLetterCallback.clickItemPosition(pos, indexOfLetter[pos])
                    selectedLetterCallback.selectedPosition(itemView.tv_letters)

                } else {
                    //itemView.tv_letters.setTypeface(textView.getTypeface(), Typeface.BOLD)
                    selectedLetterCallback.clickItemPosition(pos, -1)
                    selectedLetterCallback.selectedPosition(itemView.tv_letters)

                }
            }
        }

    }
}

