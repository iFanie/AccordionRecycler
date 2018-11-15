package com.izikode.izilib.accordionrecycler

/**
 * Data class containing additional information about an item being recycled.
 */
data class AccordionRecyclerItemDetails(

    /**
     * The number of immediate children of the current item.
     */
    val numberOfImmediateEnclosedItems: Int,

    /**
     * The number of total children, immediate or not, of the current item.
     */
    val numberOfTotalEnclosedItems: Int,

    /**
     * The overall position info of the current item.
     */
    val overallPosition: AccordionRecyclerPosition,

    /**
     * The position info of the current item, relative to it's parent item.
     */
    val enclosedPosition: AccordionRecyclerPosition,

    /**
     * The recycling details of the item enclosing the current, if existing.
     */
    val enclosingDetails: AccordionRecyclerItemDetails?

)