package com.izikode.izilib.accordionrecyclerdemo

import android.view.ViewGroup
import com.izikode.izilib.accordionrecycler.AccordionRecyclerAdapter
import com.izikode.izilib.accordionrecycler.AccordionRecyclerData
import com.izikode.izilib.accordionrecycler.AccordionRecyclerItemDetails
import com.izikode.izilib.accordionrecyclerdemo.data.*
import com.izikode.izilib.accordionrecyclerdemo.viewholder.*

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

    override fun processForAdditionalItems(position: Int, item: AccordionRecyclerData<out ColorData?>?)
            : Array<out AccordionRecyclerData<out ColorData?>?> = item?.let {

                    if (it is PinkData && it.enclosedDataArray.isNullOrEmpty()) {
                        arrayOf(
                            it,
                            EmptyPinkViewHolder.EmptyPinkData()
                        )
                    } else {
                        super.processForAdditionalItems(position, item)
                    }

                } ?: super.processForAdditionalItems(position, item)

    override fun buildViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder<out ColorData> =
        when(viewType) {

            RedViewHolder.VIEW_TYPE -> RedViewHolder(parent)
            PinkViewHolder.VIEW_TYPE -> PinkViewHolder(parent)
            EmptyPinkViewHolder.VIEW_TYPE -> EmptyPinkViewHolder(parent)
            WhiteViewHolder.VIEW_TYPE -> WhiteViewHolder(parent)

            else -> GrayViewHolder(parent)

        }

    override fun updateViewHolder(position: Int, viewHolder: ColorViewHolder<out ColorData>, data: ColorData?, details: AccordionRecyclerItemDetails) {
        when (viewHolder) {

            is RedViewHolder -> viewHolder.apply {
                    update(data as RedData?, details.overallPosition, details.numberOfImmediateEnclosedItems)

                    onClick = { position, enclosedSum ->
                        if (enclosedSum > 0) {
                            removeEnclosedItems(position)
                        } else {
                            addEnclosedItems(position, (viewHolder.itemView.context as MainActivity).getNewPink())
                        }
                    }
                }

            is PinkViewHolder -> viewHolder.apply {
                    update(data as PinkData?, details.overallPosition, details.enclosedPosition, details.numberOfTotalEnclosedItems)

                    onClick = { position, enclosedSum ->
                        if (enclosedSum > 0) {
                            removeEnclosedItems(position)
                        } else {
                            removeItem(position)
                        }
                    }
                }

            is EmptyPinkViewHolder -> viewHolder.apply {
                update(data as EmptyPinkViewHolder.EmptyPinkData?, details.overallPosition, details.enclosedPosition, details.numberOfTotalEnclosedItems)
            }

            is WhiteViewHolder -> viewHolder.apply {
                    update(data as WhiteData?, details.overallPosition, details.enclosedPosition)

                    onClick = {
                        removeItem(it)
                    }
                }

            else -> (viewHolder as GrayViewHolder).update(data as GrayData?, details.overallPosition)

        }
    }

}