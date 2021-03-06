package com.izikode.izilib.accordionrecyclerdemo.viewholder

import android.support.constraint.ConstraintLayout
import android.view.ViewGroup
import android.widget.TextView
import com.izikode.izilib.accordionrecycler.AccordionRecyclerPosition
import com.izikode.izilib.accordionrecyclerdemo.R
import com.izikode.izilib.accordionrecyclerdemo.data.GrayData

class GrayViewHolder(parent: ViewGroup) : ColorViewHolder<GrayData>(parent, R.layout.view_holder_gray) {

    private val container by lazy { itemView.findViewById<ConstraintLayout>(R.id.gray_container) }
    private val text by lazy { itemView.findViewById<TextView>(R.id.gray_text) }

    override var data: GrayData? = null
        set(value) {
            text.text = value?.text ?: "-"
            field = value
        }

    fun update(data: GrayData?, overallPosition: AccordionRecyclerPosition) {
        this.data = data

        container.setBackgroundResource(
            when (overallPosition) {

                AccordionRecyclerPosition.TOP -> R.drawable.gray_background_top
                AccordionRecyclerPosition.BOTTOM -> R.drawable.gray_background_bottom

                else -> R.drawable.gray_background_middle

            }
        )
    }

    companion object {

        const val VIEW_TYPE = 1

    }

}