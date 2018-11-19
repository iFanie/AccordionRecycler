package com.izikode.izilib.accordionrecyclerdemo.viewholder

import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.izikode.izilib.accordionrecycler.AccordionRecyclerData
import com.izikode.izilib.accordionrecycler.AccordionRecyclerPosition
import com.izikode.izilib.accordionrecyclerdemo.R
import com.izikode.izilib.accordionrecyclerdemo.data.ColorData
import com.izikode.izilib.accordionrecyclerdemo.data.PinkData

class EmptyPinkViewHolder(parent: ViewGroup) : ColorViewHolder<EmptyPinkViewHolder.EmptyPinkData>(parent, R.layout.view_holder_empty_pink) {

    private val container by lazy { itemView.findViewById<ConstraintLayout>(R.id.pink_container) }
    private val text by lazy { itemView.findViewById<TextView>(R.id.pink_text) }
    private val topShadow by lazy { itemView.findViewById<View>(R.id.pink_topShadow) }
    private val bottomShadow by lazy { itemView.findViewById<View>(R.id.pink_bottomShadow) }
    private val divider by lazy { itemView.findViewById<View>(R.id.pink_divider) }

    var onClick: ((position: Int, enclosedSum: Int) -> Unit)? = null

    override var data: EmptyPinkData? = null
        set(value) {
            text.text = value?.text
            field = value
        }

    var enclosedSum: Int = 0

    fun update(data: EmptyPinkData?, overallPosition: AccordionRecyclerPosition, enclosedPosition: AccordionRecyclerPosition, sumOfTotalEnclosedItems: Int) {
        this.data = data
        this.enclosedSum = sumOfTotalEnclosedItems

        container.setBackgroundResource(
            when (overallPosition) {

                AccordionRecyclerPosition.BOTTOM -> R.drawable.empty_pink_background_bottom

                else -> R.drawable.empty_pink_background_middle

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

        const val VIEW_TYPE = 31

    }

    class EmptyPinkData : ColorData(VIEW_TYPE) {

        override var text: String = "Nothing to see here"

        override val enclosedDataArray: Array<out AccordionRecyclerData<out ColorData?>>?
            get() = null

    }

}