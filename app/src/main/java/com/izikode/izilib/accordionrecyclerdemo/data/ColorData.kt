package com.izikode.izilib.accordionrecyclerdemo.data

import com.izikode.izilib.accordionrecycler.AccordionRecyclerData

abstract class ColorData(

        override val viewType: Int

) : AccordionRecyclerData<ColorData> {

    override val mainData: ColorData?
        get() = this

    abstract var text: String

}