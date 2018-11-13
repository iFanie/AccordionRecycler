package com.izikode.izilib.accordionrecycler

import android.view.ViewGroup
import com.izikode.izilib.accordionrecycler.adaptercomponents.AdapterContract
import com.izikode.izilib.accordionrecycler.adaptercomponents.AdapterPresenter

/**
 * Abstract class to be extended by Accordion RecyclerView adapters. Provides add & remove functions for properly
 * handling nested items and expand/contract functionality.
 */
abstract class AccordionRecyclerAdapter<ViewHolder, DataType> : AdapterContract.View<ViewHolder, DataType>()
        where ViewHolder : AccordionRecyclerViewHolder<out AccordionRecyclerData<out DataType?>> {

    private val presenter by lazy { AdapterPresenter.make(this) }

    /**
     * Replaces the current data with the provided array of items.
     *
     * @param itemArray  The array of items to replace the current data.
     */
    fun setItems(itemArray: Array<out AccordionRecyclerData<out DataType?>>) {
        presenter.setItems(itemArray)
    }

    /**
     * Replaces the current data with the provided list of items.
     *
     * @param itemList  The list of items to replace the current data.
     */
    fun setItems(itemList: ArrayList<out AccordionRecyclerData<out DataType?>>) {
        presenter.setItems(itemList)
    }

    /**
     * Replaces the current data with the provided mutable list of items.
     *
     * @param itemMutableList  The mutable list of items to replace the current data.
     */
    fun setItems(itemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>) {
        presenter.setItems(itemMutableList)
    }

    /**
     * Appends the provided array of items to the current data.
     *
     * @param itemArray  The array of items to be added to the current data.
     */
    fun addItems(itemArray: Array<out AccordionRecyclerData<out DataType?>>) {
        presenter.addItems(itemArray)
    }

    /**
     * Appends the provided list of items to the current data.
     *
     * @param itemList  The list of items to be added to the current data.
     */
    fun addItems(itemList: ArrayList<out AccordionRecyclerData<out DataType?>>) {
        presenter.addItems(itemList)
    }

    /**
     * Appends the provided mutable list of items to the current data.
     *
     * @param itemMutableList  The mutable list of items to be added to the current data.
     */
    fun addItems(itemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>) {
        presenter.addItems(itemMutableList)
    }

    /**
     * Appends the provided array of items as enclosed, child items to the enclosing, parent item at the provided index.
     *
     * @param enclosingPosition  The enclosing, parent item's positional index.
     * @param enclosedItemArray  The array of items to be appended as enclosed, child items.
     */
    fun addEnclosedItems(enclosingPosition: Int, enclosedItemArray: Array<out AccordionRecyclerData<out DataType?>>) {
        presenter.addEnclosedItems(enclosingPosition, enclosedItemArray)
    }

    /**
     * Appends the provided list of items as enclosed, child items to the enclosing, parent item at the provided index.
     *
     * @param enclosingPosition  The enclosing, parent item's positional index.
     * @param enclosedItemList  The list of items to be appended as enclosed, child items.
     */
    fun addEnclosedItems(enclosingPosition: Int, enclosedItemList: ArrayList<out AccordionRecyclerData<out DataType?>>) {
        presenter.addEnclosedItems(enclosingPosition, enclosedItemList)
    }

    /**
     * Appends the provided mutable list of items as enclosed, child items to the enclosing, parent item at the provided index.
     *
     * @param enclosingPosition  The enclosing, parent item's positional index.
     * @param enclosedItemMutableList  The mutable list of items to be appended as enclosed, child items.
     */
    fun addEnclosedItems(enclosingPosition: Int, enclosedItemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>) {
        presenter.addEnclosedItems(enclosingPosition, enclosedItemMutableList)
    }

    /**
     * Removes all current data.
     */
    fun clearItems() {
        presenter.clearItems()
    }

    /**
     * Removes the item at the provided index, along with any enclosed, child items of it, if existing.
     *
     * @param position  The positional index of the item to be removed.
     */
    fun removeItem(position: Int) {
        presenter.removeItem(position)
    }

    /**
     * Removes the enclosed, child items of the enclosing, parent item located at the provided position.
     * @param enclosingPosition  The positional index of the enclosing, parent item.
     */
    fun removeEnclosedItems(enclosingPosition: Int) {
        presenter.removeEnclosedItems(enclosingPosition)
    }

    override fun getItemCount(): Int = presenter.itemCount

    override fun getItemViewType(position: Int): Int = presenter.getItemViewType(position)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder = buildViewHolder(p0, p1)

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