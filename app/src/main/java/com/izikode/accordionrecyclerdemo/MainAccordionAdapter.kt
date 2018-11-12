package com.izikode.accordionrecyclerdemo

import android.view.ViewGroup
import com.izikode.accordionrecycler.AccordionRecyclerAdapter
import com.izikode.accordionrecycler.AccordionRecyclerPosition
import com.izikode.accordionrecyclerdemo.data.ColorData
import com.izikode.accordionrecyclerdemo.data.PinkData
import com.izikode.accordionrecyclerdemo.data.RedData
import com.izikode.accordionrecyclerdemo.viewholder.ColorViewHolder
import com.izikode.accordionrecyclerdemo.viewholder.GrayViewHolder
import com.izikode.accordionrecyclerdemo.viewholder.PinkViewHolder
import com.izikode.accordionrecyclerdemo.viewholder.RedViewHolder

class MainAccordionAdapter : AccordionRecyclerAdapter<ColorViewHolder<out ColorData>, ColorData>() {

    var colors: MutableList<out ColorData>? = null
        set(value) {
            field = value

            if (value?.isNotEmpty() == true) {
                setItems(value)
            } else {
                clearItems()
            }
        }

    override fun buildViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder<out ColorData> =
        when(viewType) {

            RedViewHolder.VIEW_TYPE -> RedViewHolder(parent)
            PinkViewHolder.VIEW_TYPE -> PinkViewHolder(parent)

            else -> GrayViewHolder(parent)

        }

    override fun updateViewHolder(position: Int, viewHolder: ColorViewHolder<out ColorData>, data: ColorData?, enclosedItemsSum: Int, overallPosition: AccordionRecyclerPosition, enclosedPosition: AccordionRecyclerPosition) {
        when (viewHolder) {

            is RedViewHolder -> viewHolder.data = data as RedData?
            is PinkViewHolder -> viewHolder.update(data as PinkData?, overallPosition, enclosedPosition)

            else -> (viewHolder as GrayViewHolder).start()

        }
    }

}