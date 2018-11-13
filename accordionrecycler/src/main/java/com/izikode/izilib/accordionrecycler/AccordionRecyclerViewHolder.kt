package com.izikode.izilib.accordionrecycler

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Abstract class to be extended by the Recycler ViewHolders. Establishes a connection between view and data and
 * simplifies the layout inflation.
 */
abstract class AccordionRecyclerViewHolder<DataType>(

        /**
         * The parent ViewGroup of the ViewHolder.
         */
        parent: ViewGroup,

        /**
         * The layout resource to be inflated for the ViewHolder.
         */
        @LayoutRes
        layoutResource: Int

) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)) {

    /**
     * Wrapper around the [adapterPosition] property and a overridable alternative to that property.
     */
    open val index: Int get() = adapterPosition

    /**
     * The data of the ViewHolder.
     */
    abstract var data: DataType?

}