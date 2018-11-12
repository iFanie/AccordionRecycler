package com.izikode.accordionrecycler.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.izikode.accordionrecycler.AccordionRecyclerData
import com.izikode.accordionrecycler.AccordionRecyclerPosition
import com.izikode.accordionrecycler.AccordionRecyclerViewHolder
import java.util.*

interface AdapterContract {

    interface Model<DataType> {

        /**
         * The current sum of data entries.
         */
        val dataCount: Int

        /**
         * Replaces the current set of data with the provided array.
         */
        fun setData(dataArray: Array<out AccordionRecyclerData<out DataType?>>, silently: Boolean = false)

        /**
         * Adds the provided array to the current set of data.
         */
        fun addData(dataArray: Array<out AccordionRecyclerData<out DataType?>>, silently: Boolean = false)

        /**
         * Removes all the stored data and notifies the [Presenter].
         */
        fun clearData(silently: Boolean = false)

        /**
         * Removes the provided number of data entries, starting from the provided index, and notifies the [Presenter].
         *
         * @param startingIndex  The index of the first data entry to be removed.
         * @param numberOfEntriesToRemove  The number of total data entries to be removed.
         */
        fun removeData(startingIndex: Int, numberOfEntriesToRemove: Int, silently: Boolean = false)

        /**
         * TODO
         * @param enclosingIndex
         * @param silently
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
         *
         */
        fun getDataEnclosedImmediate(index: Int): SortedMap<Int, out DataType?>

        /**
         *
         */
        fun getDataEnclosedTotal(index: Int): SortedMap<Int, out DataType?>

        /**
         *
         */
        fun getDataPosition(index: Int): AccordionRecyclerPosition

        /**
         *
         */
        fun getDataEnclosedPosition(index: Int): AccordionRecyclerPosition

    }

    abstract class View<ViewHolder, DataType> : RecyclerView.Adapter<ViewHolder>()
            where ViewHolder : AccordionRecyclerViewHolder<out AccordionRecyclerData<out DataType?>> {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder = buildViewHolder(p0, p1)

        /**
         *
         */
        abstract fun buildViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

        /**
         *
         */
        abstract fun updateViewHolder(position: Int, viewHolder: ViewHolder, data: DataType?, immediateEnclosedItemsSum: Int, totalEnclosedItemsSum: Int, overallPosition: AccordionRecyclerPosition, enclosedPosition: AccordionRecyclerPosition)

    }

    interface Presenter<DataType> {

        /**
         *
         */
        val itemCount: Int

        /**
         *
         */
        fun setItems(itemArray: Array<out AccordionRecyclerData<out DataType?>>)

        /**
         *
         */
        fun setItems(itemList: ArrayList<out AccordionRecyclerData<out DataType?>>)

        /**
         *
         */
        fun setItems(itemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>)

        /**
         *
         */
        fun addItems(itemArray: Array<out AccordionRecyclerData<out DataType?>>)

        /**
         *
         */
        fun addItems(itemList: ArrayList<out AccordionRecyclerData<out DataType?>>)

        /**
         *
         */
        fun addItems(itemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>)

        /**
         *
         */
        fun clearItems()

        /**
         * TODO
         * @param enclosingPosition
         */
        fun removeEnclosedItems(enclosingPosition: Int)

        /**
         *
         */
        fun getItem(position: Int): DataType?

        /**
         *
         */
        fun getItemViewType(position: Int): Int

        /**
         *
         */
        fun getItemEnclosedImmediateSum(position: Int): Int

        /**
         *
         */
        fun getItemEnclosedTotalSum(position: Int): Int

        /**
         *
         */
        fun getItemPosition(position: Int): AccordionRecyclerPosition

        /**
         *
         */
        fun getItemEnclosedPosition(position: Int): AccordionRecyclerPosition

        /**
         *
         */
        fun onItemSetChanged()

        /**
         *
         */
        fun onItemsAdded(startingPosition: Int, numberOfItemsAdded: Int)

        /**
         *
         */
        fun onItemsRemoved(startingPosition: Int, numberOfItemsRemoved: Int)

        /**
         *
         */
        fun onItemsChanged(startingPosition: Int, numberOfItemsChanged: Int)

    }

}