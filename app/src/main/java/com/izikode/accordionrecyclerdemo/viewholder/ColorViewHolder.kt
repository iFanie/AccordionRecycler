package com.izikode.accordionrecyclerdemo.viewholder

import android.support.annotation.LayoutRes
import android.view.ViewGroup
import com.izikode.accordionrecycler.AccordionRecyclerViewHolder
import com.izikode.accordionrecyclerdemo.data.ColorData

abstract class ColorViewHolder<Data>(

    parent: ViewGroup,

    @LayoutRes
    layoutResource: Int

) : AccordionRecyclerViewHolder<Data>(parent, layoutResource) where Data : ColorData