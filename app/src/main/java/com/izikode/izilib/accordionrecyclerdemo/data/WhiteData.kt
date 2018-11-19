package com.izikode.izilib.accordionrecyclerdemo.data

import com.izikode.izilib.accordionrecycler.AccordionRecyclerData
import com.izikode.izilib.accordionrecyclerdemo.viewholder.WhiteViewHolder

data class WhiteData(

        override var text: String

) : ColorData(WhiteViewHolder.VIEW_TYPE) {

    override val enclosedDataArray: Array<out AccordionRecyclerData<out ColorData?>>?
        get() = null

}