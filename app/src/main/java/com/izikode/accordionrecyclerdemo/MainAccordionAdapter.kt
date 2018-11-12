package com.izikode.accordionrecyclerdemo

import android.view.ViewGroup
import com.izikode.accordionrecycler.AccordionRecyclerAdapter
import com.izikode.accordionrecycler.AccordionRecyclerPosition
import com.izikode.accordionrecyclerdemo.data.*
import com.izikode.accordionrecyclerdemo.viewholder.*

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
            WhiteViewHolder.VIEW_TYPE -> WhiteViewHolder(parent)

            else -> GrayViewHolder(parent)

        }

    override fun updateViewHolder(position: Int, viewHolder: ColorViewHolder<out ColorData>, data: ColorData?, immediateEnclosedItemsSum: Int, totalEnclosedItemsSum: Int, overallPosition: AccordionRecyclerPosition, enclosedPosition: AccordionRecyclerPosition) {
        when (viewHolder) {

            is RedViewHolder -> viewHolder.apply {
                    update(data as RedData?, overallPosition, immediateEnclosedItemsSum)

                    onClick = { position, enclosedSum ->
                        if (enclosedSum > 0) {
                            removeEnclosedItems(position)
                        } else {
                            addEnclosedItems(position, (viewHolder.itemView.context as MainActivity).getNewPink())
                        }
                    }
                }

            is PinkViewHolder -> viewHolder.apply {
                    update(data as PinkData?, overallPosition, enclosedPosition, totalEnclosedItemsSum)

                    onClick = { position, enclosedSum ->
                        if (enclosedSum > 0) {
                            removeEnclosedItems(position)
                        } else {
                            removeItem(position)
                        }
                    }
                }

            is WhiteViewHolder -> viewHolder.apply {
                    update(data as WhiteData?, overallPosition, enclosedPosition)

                    onClick = {
                        removeItem(it)
                    }
                }

            else -> (viewHolder as GrayViewHolder).update(data as GrayData?, overallPosition)

        }
    }

}