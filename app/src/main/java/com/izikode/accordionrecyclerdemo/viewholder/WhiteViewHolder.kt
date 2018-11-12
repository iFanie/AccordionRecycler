package com.izikode.accordionrecyclerdemo.viewholder

import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.izikode.accordionrecycler.AccordionRecyclerPosition
import com.izikode.accordionrecyclerdemo.R
import com.izikode.accordionrecyclerdemo.data.WhiteData

class WhiteViewHolder(parent: ViewGroup) : ColorViewHolder<WhiteData>(parent, R.layout.view_holder_white) {

    private val container by lazy { itemView.findViewById<ConstraintLayout>(R.id.white_container) }
    private val text by lazy { itemView.findViewById<TextView>(R.id.white_text) }
    private val topShadow by lazy { itemView.findViewById<View>(R.id.white_topShadow) }
    private val bottomShadow by lazy { itemView.findViewById<View>(R.id.white_bottomShadow) }
    private val divider by lazy { itemView.findViewById<View>(R.id.white_divider) }

    var onClick: ((position: Int) -> Unit)? = null

    override var data: WhiteData? = null
        set(value) {
            text.text = value?.text
            field = value
        }

    fun update(data: WhiteData?, overallPosition: AccordionRecyclerPosition, enclosedPosition: AccordionRecyclerPosition) {
        this.data = data

        container.setBackgroundResource(
            when (overallPosition) {

                AccordionRecyclerPosition.TOP -> R.drawable.white_background_top
                AccordionRecyclerPosition.BOTTOM -> R.drawable.white_background_bottom

                else -> R.drawable.white_background_middle

            }
        )

        when {

            overallPosition == AccordionRecyclerPosition.BOTTOM && enclosedPosition == AccordionRecyclerPosition.SINGLE -> {
                topShadow.visibility = View.VISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.INVISIBLE
            }

            overallPosition == AccordionRecyclerPosition.BOTTOM && enclosedPosition == AccordionRecyclerPosition.TOP -> {
                topShadow.visibility = View.VISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.INVISIBLE
            }

            overallPosition == AccordionRecyclerPosition.BOTTOM && enclosedPosition == AccordionRecyclerPosition.BOTTOM -> {
                topShadow.visibility = View.INVISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.INVISIBLE
            }

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

    init {
        container.setOnClickListener {
            onClick?.invoke(adapterPosition)
        }
    }

    companion object {

        const val VIEW_TYPE = 4

    }

}