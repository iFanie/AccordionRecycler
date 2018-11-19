package com.izikode.izilib.accordionrecyclerdemo.data

import com.izikode.izilib.accordionrecycler.AccordionRecyclerData
import com.izikode.izilib.accordionrecyclerdemo.viewholder.PinkViewHolder

data class PinkData(

        override var text: String,
        var arrayOfWhite: Array<WhiteData> = arrayOf()

) : ColorData(PinkViewHolder.VIEW_TYPE) {

    override val enclosedDataArray: Array<out AccordionRecyclerData<out ColorData?>>?
        get() = arrayOfWhite

}