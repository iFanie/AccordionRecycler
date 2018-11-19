package com.izikode.izilib.accordionrecycler.adaptercomponents

import com.izikode.izilib.accordionrecycler.AccordionRecyclerData
import com.izikode.izilib.accordionrecycler.AccordionRecyclerItemDetails
import com.izikode.izilib.accordionrecycler.AccordionRecyclerPosition
import com.izikode.izilib.accordionrecycler.AccordionRecyclerViewHolder

class AdapterPresenter<DataType>(

    private val model: AdapterContract.Model<DataType>,
    private val view: AdapterContract.View<out AccordionRecyclerViewHolder<out AccordionRecyclerData<out DataType?>>, DataType>

) : AdapterContract.Presenter<DataType> {

    override val itemCount: Int get() = model.dataCount

    override fun setItems(itemArray: Array<out AccordionRecyclerData<out DataType?>>) {
        model.setData(itemArray)
    }

    override fun setItems(itemList: ArrayList<out AccordionRecyclerData<out DataType?>>) {
        model.setData(itemList.toTypedArray())
    }

    override fun setItems(itemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>) {
        model.setData(itemMutableList.toTypedArray())
    }

    override fun addItems(itemArray: Array<out AccordionRecyclerData<out DataType?>>) {
        model.addData(itemArray)
    }

    override fun addItems(itemList: ArrayList<out AccordionRecyclerData<out DataType?>>) {
        model.addData(itemList.toTypedArray())
    }

    override fun addItems(itemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>) {
        model.addData(itemMutableList.toTypedArray())
    }

    override fun addEnclosedItems(enclosingPosition: Int, enclosedItemArray: Array<out AccordionRecyclerData<out DataType?>>) {
        model.addEnclosedData(enclosingPosition, enclosedItemArray)
    }

    override fun addEnclosedItems(enclosingPosition: Int, enclosedItemList: ArrayList<out AccordionRecyclerData<out DataType?>>) {
        model.addEnclosedData(enclosingPosition, enclosedItemList.toTypedArray())
    }

    override fun addEnclosedItems(enclosingPosition: Int, enclosedItemMutableList: MutableList<out AccordionRecyclerData<out DataType?>>) {
        model.addEnclosedData(enclosingPosition, enclosedItemMutableList.toTypedArray())
    }

    override fun processForAdditionalItems(position: Int, item: AccordionRecyclerData<out DataType?>?): Array<out AccordionRecyclerData<out DataType?>?> = view.processForAdditionalItems(position, item)

    override fun clearItems() {
        model.clearData()
    }

    override fun removeItem(position: Int) {
        model.removeData(position)
    }

    override fun removeEnclosedItems(enclosingPosition: Int) {
        model.removeEnclosedData(enclosingPosition)
    }

    override fun getItem(position: Int): DataType? = model.getData(position)

    override fun getItemViewType(position: Int): Int = model.getDataViewType(position)

    override fun getItemEnclosedImmediateSum(position: Int): Int = model.getDataEnclosedImmediate(position).size

    override fun getItemEnclosedTotalSum(position: Int): Int = model.getDataEnclosedTotal(position).size

    override fun getItemPosition(position: Int): AccordionRecyclerPosition = model.getDataPosition(position)

    override fun getItemEnclosedPosition(position: Int): AccordionRecyclerPosition = model.getDataEnclosedPosition(position)

    override fun getItemRecyclingDetails(position: Int): AccordionRecyclerItemDetails = model.getDataRecyclingDetails(position)

    override fun onItemSetChanged() {
        view.notifyDataSetChanged()
    }

    override fun onItemsAdded(startingPosition: Int, numberOfItemsAdded: Int) {
        if (numberOfItemsAdded == 1) {
            view.notifyItemInserted(startingPosition)
        } else if (numberOfItemsAdded > 1) {
            view.notifyItemRangeInserted(startingPosition, numberOfItemsAdded)
        }
    }

    override fun onItemsRemoved(startingPosition: Int, numberOfItemsRemoved: Int) {
        if (numberOfItemsRemoved == 1) {
            view.notifyItemRemoved(startingPosition)
        } else if (numberOfItemsRemoved > 1) {
            view.notifyItemRangeRemoved(startingPosition, numberOfItemsRemoved)
        }
    }

    override fun onItemsChanged(startingPosition: Int, numberOfItemsChanged: Int) {
        if (numberOfItemsChanged == 1) {
            model.removeDataRecyclingDetails(startingPosition)
            view.notifyItemChanged(startingPosition)
        } else if (numberOfItemsChanged > 1) {
            repeat(numberOfItemsChanged) { iteration ->
                model.removeDataRecyclingDetails(startingPosition + iteration)
            }

            view.notifyItemRangeChanged(startingPosition, numberOfItemsChanged)
        }
    }

    companion object {

        @JvmStatic
        fun <DataType> make(view: AdapterContract.View<out AccordionRecyclerViewHolder<out AccordionRecyclerData<out DataType?>>, DataType>)
                : AdapterPresenter<DataType> {

            val model = AdapterModel<DataType>()
            val presenter =
                AdapterPresenter(model, view)

            model.initialize(presenter)

            return presenter
        }

    }

}