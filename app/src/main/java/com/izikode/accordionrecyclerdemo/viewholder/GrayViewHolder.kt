package com.izikode.accordionrecyclerdemo.viewholder

import android.view.ViewGroup
import android.widget.TextView
import com.izikode.accordionrecyclerdemo.R
import com.izikode.accordionrecyclerdemo.data.GrayData

class GrayViewHolder(parent: ViewGroup) : ColorViewHolder<GrayData>(parent, R.layout.view_holder_gray) {

    private val text by lazy { itemView.findViewById<TextView>(R.id.gray_text) }

    override var data: GrayData? = null
        set(value) {
            text.text = value?.text ?: "-"
            field = value
        }

    fun start() {
        text.text = "Loading"
    }

    companion object {

        const val VIEW_TYPE = 1

    }

}