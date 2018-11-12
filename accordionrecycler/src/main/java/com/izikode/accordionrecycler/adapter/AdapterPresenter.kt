package com.izikode.accordionrecycler.adapter

import com.izikode.accordionrecycler.AccordionRecyclerData
import com.izikode.accordionrecycler.AccordionRecyclerPosition
import com.izikode.accordionrecycler.AccordionRecyclerViewHolder

class AdapterPresenter<DataType>(

        private val model: AdapterContract.Model<DataType>,
        private val view: AdapterContract.View<out AccordionRecyclerViewHolder<out AccordionRecyclerData<out DataType?>>, out DataType>

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

    override fun clearItems() {
        model.clearData()
    }

    override fun getItem(position: Int): DataType? = model.getData(position)

    override fun getItemViewType(position: Int): Int = model.getDataViewType(position)

    override fun getItemEnclosedSum(position: Int): Int = model.getDataEnclosedSum(position)

    override fun getItemPosition(position: Int): AccordionRecyclerPosition = model.getDataPosition(position)

    override fun getItemEnclosedPosition(position: Int): AccordionRecyclerPosition = model.getDataEnclosedPosition(position)

    override fun onItemSetChanged() {
        view.notifyDataSetChanged()
    }

    override fun onItemsAdded(startingPosition: Int, numberOfItemsAdded: Int) {
        view.notifyItemRangeInserted(startingPosition, numberOfItemsAdded)
    }

    override fun onItemsRemoved(startingPosition: Int, numberOfItemsRemoved: Int) {
        view.notifyItemRangeRemoved(startingPosition, numberOfItemsRemoved)
    }

    companion object {

        @JvmStatic
        fun <DataType> make(view: AdapterContract.View<out AccordionRecyclerViewHolder<out AccordionRecyclerData<out DataType?>>, out DataType>)
                : AdapterPresenter<DataType> {

            val model = AdapterModel<DataType>()
            val presenter = AdapterPresenter(model, view)

            model.initialize(presenter)

            return presenter
        }

    }

}