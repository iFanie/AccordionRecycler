package com.izikode.izilib.accordionrecycler

/**
 * Interface to be implemented by the Recycler data. It provides the Adapter with all the needed information to
 * display different ViewHolders and correctly handle the data nesting.
 */
interface AccordionRecyclerData<Type> {

    /**
     * The value matching the data to it's view.
     */
    val viewType: Int

    /**
     * The main and primary data of the item.
     */
    var mainData: Type?

    /**
     * The enclosed, secondary and nested set of items.
     */
    var enclosedDataArray: Array<out AccordionRecyclerData<out Type?>>?

}