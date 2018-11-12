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

    var onClick: ((position: Int, enclosedSum: Int) -> Unit)? = null

    override var data: PinkData? = null
        set(value) {
            text.text = value?.text
            field = value
        }

    var enclosedSum: Int = 0

    fun update(data: PinkData?, overallPosition: AccordionRecyclerPosition, enclosedPosition: AccordionRecyclerPosition, sumOfTotalEnclosedItems: Int) {
        this.data = data
        this.enclosedSum = sumOfTotalEnclosedItems

        container.setBackgroundResource(
            when (overallPosition) {

                AccordionRecyclerPosition.BOTTOM -> R.drawable.pink_background_bottom

                else -> R.drawable.pink_background_middle

            }
        )

        when {

            enclosedPosition == AccordionRecyclerPosition.SINGLE && sumOfTotalEnclosedItems == 0 -> {
                topShadow.visibility = View.VISIBLE
                bottomShadow.visibility = View.VISIBLE
                divider.visibility = View.INVISIBLE
            }

            enclosedPosition == AccordionRecyclerPosition.SINGLE -> {
                topShadow.visibility = View.VISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.INVISIBLE
            }

            enclosedPosition == AccordionRecyclerPosition.TOP && sumOfTotalEnclosedItems == 0 -> {
                topShadow.visibility = View.VISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.VISIBLE
            }

            enclosedPosition == AccordionRecyclerPosition.TOP -> {
                topShadow.visibility = View.VISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.INVISIBLE
            }

            enclosedPosition == AccordionRecyclerPosition.BOTTOM && sumOfTotalEnclosedItems == 0 -> {
                topShadow.visibility = View.INVISIBLE
                bottomShadow.visibility = View.VISIBLE
                divider.visibility = View.INVISIBLE
            }

            enclosedPosition == AccordionRecyclerPosition.BOTTOM -> {
                topShadow.visibility = View.INVISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.INVISIBLE
            }

            enclosedPosition == AccordionRecyclerPosition.MIDDLE && sumOfTotalEnclosedItems == 0 -> {
                topShadow.visibility = View.INVISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.VISIBLE
            }

            else -> {
                topShadow.visibility = View.INVISIBLE
                bottomShadow.visibility = View.INVISIBLE
                divider.visibility = View.INVISIBLE
            }

        }
    }

    init {
        container.setOnClickListener {
            onClick?.invoke(layoutPosition, enclosedSum)
        }
    }

    companion object {

        const val VIEW_TYPE = 3

    }

}