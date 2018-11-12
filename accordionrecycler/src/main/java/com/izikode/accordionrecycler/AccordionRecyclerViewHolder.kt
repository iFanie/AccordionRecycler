package com.izikode.accordionrecycler

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 *
 */
abstract class AccordionRecyclerViewHolder<DataType>(

        /**
         *
         */
        parent: ViewGroup,

        /**
         *
         */
        @LayoutRes
        layoutResource: Int

) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)) {

    /**
     *
     */
    open val index: Int get() = adapterPosition

    /**
     *
     */
    abstract var data: DataType?

}