package com.izikode.accordionrecyclerdemo.viewholder

import android.support.constraint.ConstraintLayout
import android.view.ViewGroup
import android.widget.TextView
import com.izikode.accordionrecyclerdemo.R
import com.izikode.accordionrecyclerdemo.data.RedData

class RedViewHolder(parent: ViewGroup) : ColorViewHolder<RedData>(parent, R.layout.view_holder_red) {

    private val container by lazy { itemView.findViewById<ConstraintLayout>(R.id.red_container) }
    private val text by lazy { itemView.findViewById<TextView>(R.id.red_text) }

    init {
        container.setOnClickListener {
            
        }
    }

    override var data: RedData? = null
        set(value) {
            text.text = value?.text
            field = value
        }

    companion object {

        const val VIEW_TYPE = 2

    }

}