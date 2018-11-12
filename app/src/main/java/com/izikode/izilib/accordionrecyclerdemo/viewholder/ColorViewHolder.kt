package com.izikode.izilib.accordionrecyclerdemo.viewholder

import android.support.annotation.LayoutRes
import android.view.ViewGroup
import com.izikode.izilib.accordionrecycler.AccordionRecyclerViewHolder
import com.izikode.izilib.accordionrecyclerdemo.data.ColorData

abstract class ColorViewHolder<Data>(

    parent: ViewGroup,

    @LayoutRes
    layoutResource: Int

) : AccordionRecyclerViewHolder<Data>(parent, layoutResource) where Data : ColorData