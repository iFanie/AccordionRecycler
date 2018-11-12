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
        var main: Type?

        /**
         *
         */
        var secondary: Array<out AccordionRecyclerData<out Type?>>?

}