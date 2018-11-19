package com.izikode.izilib.accordionrecyclerdemo.data

import com.izikode.izilib.accordionrecycler.AccordionRecyclerData
import com.izikode.izilib.accordionrecyclerdemo.viewholder.GrayViewHolder

data class GrayData(

        override var text: String

) : ColorData(GrayViewHolder.VIEW_TYPE) {

    override val enclosedDataArray: Array<out AccordionRecyclerData<out ColorData?>>?
        get() = null

}