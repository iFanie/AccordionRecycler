package com.izikode.accordionrecyclerdemo.viewholder

import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.izikode.accordionrecycler.AccordionRecyclerPosition
import com.izikode.accordionrecyclerdemo.R
import com.izikode.accordionrecyclerdemo.data.RedData

class RedViewHolder(parent: ViewGroup) : ColorViewHolder<RedData>(parent, R.layout.view_holder_red) {

    private val container by lazy { itemView.findViewById<ConstraintLayout>(R.id.red_container) }
    private val text by lazy { itemView.findViewById<TextView>(R.id.red_text) }
    private val divider by lazy { itemView.findViewById<View>(R.id.red_divider) }

    var onClick: ((position: Int, enclosedSum: Int) -> Unit)? = null

    override var data: RedData? = null
        set(value) {
            text.text = value?.text
            field = value
        }

    var enclosedSum: Int = 0

    fun update(data: RedData?, overallPosition: AccordionRecyclerPosition, sumOfTotalEnclosedItems: Int) {
        this.data = data
        this.enclosedSum = sumOfTotalEnclosedItems

        container.setBackgroundResource(
            when (overallPosition) {

                AccordionRecyclerPosition.TOP -> R.drawable.red_background_top
                AccordionRecyclerPosition.BOTTOM -> R.drawable.red_background_bottom

                else -> R.drawable.red_background_middle

            }
        )

        divider.visibility = when {

                overallPosition == AccordionRecyclerPosition.TOP && sumOfTotalEnclosedItems == 0 -> View.VISIBLE
                overallPosition == AccordionRecyclerPosition.MIDDLE && sumOfTotalEnclosedItems == 0 -> View.VISIBLE

                else -> View.INVISIBLE

            }
    }

    init {
        container.setOnClickListener {
            onClick?.invoke(adapterPosition, enclosedSum)
        }
    }

    companion object {

        const val VIEW_TYPE = 2

    }

}