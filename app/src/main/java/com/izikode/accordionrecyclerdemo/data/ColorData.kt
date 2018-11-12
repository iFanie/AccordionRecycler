package com.izikode.accordionrecyclerdemo.data

import com.izikode.accordionrecycler.AccordionRecyclerData

abstract class ColorData(

        override var viewType: Int,
        override var secondary: Array<out AccordionRecyclerData<out ColorData?>>? = null

) : AccordionRecyclerData<ColorData> {

    override var main: ColorData? = this
    abstract var text: String

}