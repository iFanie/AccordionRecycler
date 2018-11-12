package com.izikode.accordionrecycler

/**
 *
 */
interface AccordionRecyclerData<Type> {

    /**
     *
     */
    var viewType: Int

    /**
     *
     */
    var mainData: Type?

    /**
     *
     */
    var enclosedDataArray: Array<out AccordionRecyclerData<out Type?>>?

}