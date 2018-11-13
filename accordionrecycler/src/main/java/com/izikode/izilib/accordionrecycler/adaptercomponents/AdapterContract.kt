package com.izikode.izilib.accordionrecycler.adaptercomponents

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.izikode.izilib.accordionrecycler.AccordionRecyclerData
import com.izikode.izilib.accordionrecycler.AccordionRecyclerPosition
import com.izikode.izilib.accordionrecycler.AccordionRecyclerViewHolder
import java.util.*

interface AdapterContract {

    interface Model<DataType> {

        /**
         * The current sum of data entries.
         */
        val dataCount: Int

        /**
         * Replaces the current set of data with the provided array.
         *
         * @param dataArray  The array to replace the current data.
         * @param silently  When {@code true}, the [Presenter] will not be notified of the change.
         */
        fun setData(dataArray: Array<out AccordionRecyclerData<out DataType?>>, silently: Boolean = false)

        /**
         * Appends the provided array to the current set of data.
         *
         * @param dataArray  The array to be appended to the current data.
         * @param silently  When {@code true}, the [Presenter] will not be notified of the change.
         */
        fun addData(dataArray: Array<out AccordionRecyclerData<out DataType?>>, silently: Boolean = false)

        /**
         * Appends the provided array as children of the item at the provided index.
         *
         * @param enclosingIndex  The index of the parent item.
         * @param enclosedDataArray  The array to be added as children.
         * @param silently  When {@code true}, the [Presenter] will not be notified of the change.
         */
        fun addEnclosedData(enclosingIndex: Int, enclosedDataArray: Array<out AccordionRecyclerData<out DataType?>>, silently: Boolean = false)

        /**
         * Removes all current data.
         *
         * @param silently  When {@code true}, the [Presenter] will not be notified of the change.
         */
        fun clearData(silently: Boolean = false)

        /**
         * Removes the item at the provided index, along with all it's children, immediate or not.
         *
         * @param silently  When {@code true}, the [Presenter] will not be notified of the change.
         */
        fun removeData(index: Int, silently: Boolean = false)

        /**
         * Removes the children of the item at the provided index.
         *
         * @param enclosingIndex  The index of the parent item.
         * @param silently  When {@code true}, the [Presenter] will not be notified of the change.
         */
        fun removeEnclosedData(enclosingIndex: Int, silently: Boolean = false)

        /**
         * Gets the data stored at the provided index.
         *
         * @param index  The index from which to retrieve data.
         * @return The data stored at the provided index.
         */
        fun getData(index: Int): DataType?

        /**
         * Gets the viewType of the data stored at the provided index.
         *
         * @param index  The index of the data whose viewType will be returned.
         * @return The viewType of the data stored at the provided index.
         */
        fun getDataViewType(index: Int): Int

        /**
         * Gets the immediate children of the item at the provided index.
         *
         * @param index  The index of the parent item.
         * @return The immediate children of the parent at the provided index.
         */
        fun getDataEnclosedImmediate(index: Int): SortedMap<Int, out DataType?>

        /**
         * Gets all the children, immediate or not, of the item at the provided index.
         *
         * @param index  The index of the parent item.
         * @return The children, immediate or not, of the parent at the provided index.
         */
        fun getDataEnclosedTotal(index: Int): SortedMap<Int, out DataType?>

        /**
         * Gets the overall position info of the item at the provided index.
         *
         * @param index  The item whose position is to be returned.
         * @return The overall position info of the item at the provided index.
         */
        fun getDataPosition(index: Int): AccordionRecyclerPosition

        /**
         * Gets the position info of the item at the provided index, relative ot it's parent.
         *
         * @param index  The item whose position is to be returned.
         * @return The position info of the item at the provided index, relative to it's parent.
         */
        fun getDataEnclosedPosition(index: Int): AccordionRecyclerPosition

    }

    abstract class View<ViewHolder, DataType> : RecyclerView.Adapter<ViewHolder>()
            where ViewHolder : AccordionRecyclerViewHolder<out AccordionRecyclerData<out DataType?>> {

        /**
         * Creates a new ViewHolder instance for the provided parent, based on the provided viewType.
         *
         * @param parent  The parent ViewGroup of the ViewHolder.
         * @param viewType  The viewType of the new ViewHolder.
         * @return The newly created ViewHolder.
         */
        abstract fun buildViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

        /**
         * Function triggered when the provided ViewHolder must be renewed based on the provided data.
         *
         * @param position  The position of the data on the recycler.
         * @param viewHolder  The ViewHolder to be renewed.
         * @param data  The data to be used for the renewal of the ViewHolder.
         * @param immediateEnclosedItemsSum  The number of immediate children of the current item.
         * @param totalEnclosedItemsSum  The number of total children, immediate or not, of the current item.
         * @param overallPosition  The overall position info of the current item.
         * @param enclosedPosition  The position info of the current item, relative to it's parent item.
         */
        abstract fun updateViewHolder(position: Int, viewHolder: ViewHolder, data: DataType?, immediateEnclosedItemsSum: Int, totalEnclosedItemsSum: Int, overallPosition: AccordionRecyclerPosition, enclosedPosition: AccordionRecyclerPosition)

    }

    interface Presenter<DataType> {

        /**
         * The number of current items.
         */
        val itemCount: Int

        /**
         * Replaces the current set of data with the provided array.
         *
         * @param itemArray  The array to replace the current data.
         */
        fun setItems(itemArray: Array<out AccordionRecyclerData<out DataType?>>)

        /**
         * Replaces the current set of data with the provided list.
         *
         * @param itemList  The list to replace the current data.
         */
        fun setItems(itemList: ArrayList<out AccordionRecyclerData<out DataType?>>)

        /**
         * Replaces the current set of data with the provided mutable list.
         *
         * @param itemMutableList  The mutable list to replace the current data.
         */
        fun setItems(itemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>)

        /**
         * Appends the provided array to the current set of data.
         *
         * @param itemArray  The array to be appended to the current data.
         */
        fun addItems(itemArray: Array<out AccordionRecyclerData<out DataType?>>)

        /**
         * Appends the provided list to the current set of data.
         *
         * @param itemList  The list to be appended to the current data.
         */
        fun addItems(itemList: ArrayList<out AccordionRecyclerData<out DataType?>>)

        /**
         * Appends the provided mutable list to the current set of data.
         *
         * @param itemMutableList  The mutable list to be appended to the current data.
         */
        fun addItems(itemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>)

        /**
         * Appends the provided array as children of the item at the provided index.
         *
         * @param enclosingPosition  The index of the parent item.
         * @param enclosedItemArray  The array to be added as children.
         */
        fun addEnclosedItems(enclosingPosition: Int, enclosedItemArray: Array<out AccordionRecyclerData<out DataType?>>)

        /**
         * Appends the provided list as children of the item at the provided index.
         *
         * @param enclosingPosition  The index of the parent item.
         * @param enclosedItemList  The list to be added as children.
         */
        fun addEnclosedItems(enclosingPosition: Int, enclosedItemList: ArrayList<out AccordionRecyclerData<out DataType?>>)

        /**
         * Appends the provided mutable list as children of the item at the provided index.
         *
         * @param enclosingPosition  The index of the parent item.
         * @param enclosedItemMutableList  The mutable list to be added as children.
         */
        fun addEnclosedItems(enclosingPosition: Int, enclosedItemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>)

        /**
         * Removes all current data.
         */
        fun clearItems()

        /**
         * Removes the item at the provided index, along with all it's children, immediate or not.
         */
        fun removeItem(position: Int)

        /**
         * TRemoves the children of the item at the provided index.
         *
         * @param enclosingPosition  The index of the parent item.
         */
        fun removeEnclosedItems(enclosingPosition: Int)

        /**
         * Gets the data stored at the provided index.
         *
         * @param position  The index from which to retrieve data.
         */
        fun getItem(position: Int): DataType?

        /**
         * Gets the viewType of the data stored at the provided index.
         *
         * @param position  The index of the data whose viewType will be returned.
         * @return The viewType of the data stored at the provided index.
         */
        fun getItemViewType(position: Int): Int

        /**
         * Gets the number of immediate children of the item at the provided index.
         *
         * @param position  The index of the item whose immediate children are to be counted.
         * @return The number of immediate children for the item at the provided index.
         */
        fun getItemEnclosedImmediateSum(position: Int): Int

        /**
         * Gets the number total children, immediate or not, of the item at the provided index.
         *
         * @param position  The index of the item whose children, immediate or not, are to be counted.
         * @return The number of total children, immediate or not, for the item at the provided index.
         */
        fun getItemEnclosedTotalSum(position: Int): Int

        /**
         * Gets the overall position info of the item at the provided index.
         *
         * @param position  The item whose position is to be returned.
         * @return The overall position info of the item at the provided index.
         */
        fun getItemPosition(position: Int): AccordionRecyclerPosition

        /**
         * Gets the position info of the item at the provided index, relative ot it's parent.
         *
         * @param position  The item whose position is to be returned.
         * @return The position info of the item at the provided index, relative to it's parent.
         */
        fun getItemEnclosedPosition(position: Int): AccordionRecyclerPosition

        /**
         * Function called when the data set of the adapter changes.
         */
        fun onItemSetChanged()

        /**
         * Function called when new items are added to the adapter data set.
         *
         * @param startingPosition  The index of the first new item.
         * @param numberOfItemsAdded  The sum of the newly added items.
         */
        fun onItemsAdded(startingPosition: Int, numberOfItemsAdded: Int)

        /**
         * Function called when items are removed from the adapter data set.
         *
         * @param startingPosition  The index of the first removed item.
         * @param numberOfItemsRemoved  The sum of the removed items.
         */
        fun onItemsRemoved(startingPosition: Int, numberOfItemsRemoved: Int)

        /**
         * Function called when items of the adapter data set are updated.
         *
         * @param startingPosition  The index of the first updated item.
         * @param numberOfItemsChanged  The sum of the updated items.
         */
        fun onItemsChanged(startingPosition: Int, numberOfItemsChanged: Int)

    }

}