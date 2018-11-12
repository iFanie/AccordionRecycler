package com.izikode.izilib.accordionrecyclerdemo.data

import com.izikode.izilib.accordionrecycler.AccordionRecyclerData

abstract class ColorData(

        override var viewType: Int,
        override var enclosedDataArray: Array<out AccordionRecyclerData<out ColorData?>>? = null

) : AccordionRecyclerData<ColorData> {

    override var mainData: ColorData? = this
    abstract var text: String

}