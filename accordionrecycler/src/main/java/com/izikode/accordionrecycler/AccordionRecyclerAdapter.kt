package com.izikode.accordionrecycler

import com.izikode.accordionrecycler.adapter.AdapterContract
import com.izikode.accordionrecycler.adapter.AdapterPresenter

/**
 *
 */
abstract class AccordionRecyclerAdapter<ViewHolder, DataType> : AdapterContract.View<ViewHolder, DataType>()
        where ViewHolder : AccordionRecyclerViewHolder<out AccordionRecyclerData<out DataType?>> {

    private val presenter by lazy { AdapterPresenter.make(this) }

    fun setItems(itemArray: Array<out AccordionRecyclerData<out DataType?>>) {
        presenter.setItems(itemArray)
    }

    fun setItems(itemList: ArrayList<out AccordionRecyclerData<out DataType?>>) {
        presenter.setItems(itemList)
    }

    fun setItems(itemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>) {
        presenter.setItems(itemMutableList)
    }

    fun addItems(itemArray: Array<out AccordionRecyclerData<out DataType?>>) {
        presenter.addItems(itemArray)
    }

    fun addItems(itemList: ArrayList<out AccordionRecyclerData<out DataType?>>) {
        presenter.addItems(itemList)
    }

    fun addItems(itemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>) {
        presenter.addItems(itemMutableList)
    }

    fun clearItems() {
        presenter.clearItems()
    }

    fun removeItem(position: Int) {
        presenter.removeItem(position)
    }

    fun removeEnclosedItems(enclosingPosition: Int) {
        presenter.removeEnclosedItems(enclosingPosition)
    }

    override fun getItemCount(): Int = presenter.itemCount

    override fun getItemViewType(position: Int): Int = presenter.getItemViewType(position)

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        updateViewHolder(
            position,
            viewHolder,
            presenter.getItem(position),
            presenter.getItemEnclosedImmediateSum(position),
            presenter.getItemEnclosedTotalSum(position),
            presenter.getItemPosition(position),
            presenter.getItemEnclosedPosition(position)
        )
    }

}