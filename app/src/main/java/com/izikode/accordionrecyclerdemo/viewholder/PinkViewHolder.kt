package com.izikode.accordionrecyclerdemo.viewholder

import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.izikode.accordionrecycler.AccordionRecyclerPosition
import com.izikode.accordionrecyclerdemo.R
import com.izikode.accordionrecyclerdemo.data.PinkData

class PinkViewHolder(parent: ViewGroup) : ColorViewHolder<PinkData>(parent, R.layout.view_holder_pink) {

    private val container by lazy { itemView.findViewById<ConstraintLayout>(R.id.pink_container) }
    private val text by lazy { itemView.findViewById<TextView>(R.id.pink_text) }
    private val topShadow by lazy { itemView.findViewById<View>(R.id.pink_topShadow) }
    private val bottomShadow by lazy { itemView.findViewById<View>(R.id.pink_bottomShadow) }
    private val divider by lazy { itemView.findViewById<View>(R.id.pink_divider) }

    init {
        container.setOnClickListener {

        }
    }

    override var data: PinkData? = null
        set(value) {
            text.text = value?.text
            field = value
        }

    fun update(data: PinkData?, overallPosition: AccordionRecyclerPosition, enclosedPosition: AccordionRecyclerPosition) {
        this.data = data

        when {

            enclosedPosition == AccordionRecyclerPosition.SINGLE -> {
                topShadow.visibility = View.VISIBLE
                bottomShadow.visibility = View.VISIBLE
                divider.visibility = View.INVISIBLE
            }

            enclosedPosition == AccordionRecyclerPosition.TOP -> {
                topShadow.visibility = View.VISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.VISIBLE
            }

            enclosedPosition == AccordionRecyclerPosition.BOTTOM -> {
                topShadow.visibility = View.INVISIBLE
                bottomShadow.visibility = View.VISIBLE
                divider.visibility = View.INVISIBLE
            }

            else -> {
                topShadow.visibility = View.INVISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.VISIBLE
            }

        }
    }

    companion object {

        const val VIEW_TYPE = 3

    }

}