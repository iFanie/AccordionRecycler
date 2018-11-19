package com.izikode.izilib.accordionrecyclerdemo.data

import com.izikode.izilib.accordionrecycler.AccordionRecyclerData
import com.izikode.izilib.accordionrecyclerdemo.viewholder.RedViewHolder

data class RedData(

        override var text: String,
        var arrayOfPink: Array<PinkData> = arrayOf()

) : ColorData(RedViewHolder.VIEW_TYPE) {

    override val enclosedDataArray: Array<out AccordionRecyclerData<out ColorData?>>?
        get() = arrayOfPink

}