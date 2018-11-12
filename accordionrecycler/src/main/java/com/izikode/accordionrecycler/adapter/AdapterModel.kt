package com.izikode.accordionrecycler.adapter

import com.izikode.accordionrecycler.AccordionRecyclerData
import com.izikode.accordionrecycler.AccordionRecyclerPosition
import java.lang.ref.WeakReference
import java.util.*

class AdapterModel<DataType> : AdapterContract.Model<DataType> {

    private lateinit var presenter: AdapterContract.Presenter<out DataType>

    fun initialize(presenter: AdapterContract.Presenter<out DataType>) {
        this.presenter = presenter
    }

    /**
     * Main data container.
     */
    private val dataList: MutableList<Wrapper<out DataType?>> = arrayListOf()

    /**
     * Map of items and their enclosed subItems.
     */
    private val containingListMap: WeakHashMap<Wrapper<out DataType?>, MutableList<WeakReference<Wrapper<out DataType?>>>> = WeakHashMap()

    override val dataCount: Int get() = dataList.size

    override fun setData(dataArray: Array<out AccordionRecyclerData<out DataType?>>, silently: Boolean) {
        clearData(true)
        addData(dataArray, true)

        if (!silently) {
            presenter.onItemSetChanged()
        }
    }

    override fun addData(dataArray: Array<out AccordionRecyclerData<out DataType?>>, silently: Boolean) {
        val startingIndex = dataCount
        val sum = recursivelyAddAllAndReturnSum(dataArray)

        if (!silently) {
            presenter.onItemsAdded(startingIndex, sum)
        }
    }

    /**
     * Adds all items in the provided array along with all secondary data, in their relative order.
     *
     * @param dataArray  The items to be added, along with their secondary data.
     * @return The total number, primary plus secondary, of data entries added.
     */
    private fun recursivelyAddAllAndReturnSum(dataArray: Array<out AccordionRecyclerData<out DataType?>>,
              recursionDepth: Int = 0, containingData: Wrapper<out DataType?>? = null): Int {

        var sum = 0

        val containingList = containingData?.let {
                if (!containingListMap.containsKey(it)) {
                    val list: MutableList<WeakReference<Wrapper<out DataType?>>> = arrayListOf()
                    containingListMap[it] = list

                    list
                } else {
                    containingListMap[it]
                }
            }

        dataArray.forEachIndexed { index, accordionRecyclerData ->

            val mainWrapper = Wrapper(
                    accordionRecyclerData.viewType,
                    containingData,
                    accordionRecyclerData.main
                )

            /* Add main data */
            dataList.add(mainWrapper)
            sum += 1

            /* Update map list if needed */
            containingList?.add(WeakReference(mainWrapper))

            /* If exist, add secondary data recursively */
            accordionRecyclerData.secondary?.let {
                val secondarySum = recursivelyAddAllAndReturnSum(it, recursionDepth + 1, mainWrapper)
                sum += secondarySum
            }
        }

        return sum
    }

    override fun clearData(silently: Boolean) {
        removeData(0, dataCount, silently)
    }

    override fun removeData(startingIndex: Int, numberOfEntriesToRemove: Int, silently: Boolean) {
        for (removeIndex in startingIndex until startingIndex + numberOfEntriesToRemove) {
            containingListMap.remove(dataList[removeIndex])
            dataList.removeAt(removeIndex)
        }

        if (!silently) {
            presenter.onItemsRemoved(startingIndex, numberOfEntriesToRemove)
        }
    }

    override fun removeEnclosedData(enclosingIndex: Int, silently: Boolean) {
        if (enclosingIndex < dataCount - 1) {
            val startingIndex = enclosingIndex + 1
            val sum = recursivelyRemoveAllAndReturnSum(dataList[enclosingIndex], false)

            if (!silently) {
                presenter.onItemsRemoved(startingIndex, sum)
                presenter.onItemsChanged(enclosingIndex, 1)
            }
        }
    }

    private fun recursivelyRemoveAllAndReturnSum(enclosingWrapper: Wrapper<out DataType?>, inclusive: Boolean = true): Int {
        var sum = 0

        containingListMap[enclosingWrapper]?.forEach { enclosedWeakReference ->
            enclosedWeakReference.get()?.let { enclosedWrapper ->
                sum += recursivelyRemoveAllAndReturnSum(enclosedWrapper, true)
            }
        }

        if (inclusive) {
            containingListMap.remove(enclosingWrapper)
            dataList.remove(enclosingWrapper)

            sum += 1
        } else {
            containingListMap[enclosingWrapper] = arrayListOf()
        }

        return sum
    }

    override fun getData(index: Int): DataType? = dataList[index].data

    override fun getDataViewType(index: Int): Int = dataList[index].viewType

    override fun getDataEnclosedImmediate(index: Int): SortedMap<Int, DataType?> = containingListMap[dataList[index]]?.let {
        clearListFromNullReferences(it)

        val enclosedImmediate: HashMap<Int, DataType?> = hashMapOf()

        it.forEach { reference ->
            reference.get()?.let { wrapper ->
                enclosedImmediate.put(dataList.indexOf(wrapper), wrapper.data)
            }
        }

        enclosedImmediate.toSortedMap()
    } ?: sortedMapOf()

    override fun getDataEnclosedTotal(index: Int): SortedMap<Int, DataType?> {
        val enclosedTotal: HashMap<Int, DataType?> = hashMapOf()

        val enclosedImmediate = getDataEnclosedImmediate(index)
        enclosedTotal.putAll(enclosedImmediate)

        enclosedImmediate.forEach { immediatePair ->
            enclosedTotal.putAll(getDataEnclosedTotal(immediatePair.key))
        }

        return enclosedTotal.toSortedMap()
    }

    override fun getDataPosition(index: Int): AccordionRecyclerPosition = getPositionFromEnclosureIndexAndSize(index, dataCount)

    override fun getDataEnclosedPosition(index: Int): AccordionRecyclerPosition = getEnclosedPosition(dataList[index])

    private fun getEnclosedPosition(wrapper: Wrapper<out DataType?>): AccordionRecyclerPosition = containingListMap[wrapper.enclosingWrapper]?.let { containingList ->
            clearListFromNullReferences(containingList)

            var enclosedPosition = AccordionRecyclerPosition.UNKNOWN

            for (index in 0 until containingList.size) {
                val itemInList = containingList[index].get()

                if (itemInList != null) {
                    if (itemInList == wrapper) {
                        enclosedPosition = getPositionFromEnclosureIndexAndSize(index, containingList.size)
                        break
                    }
                }
            }

        enclosedPosition
        } ?: AccordionRecyclerPosition.UNKNOWN

    private fun clearListFromNullReferences(list: MutableList<WeakReference<Wrapper<out DataType?>>>) {
        val nullIndexes = arrayListOf<Int>()

        for (index in 0 until list.size) {
            if (list[index].get() == null) {
                nullIndexes.add(index)
            }
        }

        nullIndexes.forEach { nullIndex ->
            list.removeAt(nullIndex)
        }
    }

    private fun getPositionFromEnclosureIndexAndSize(enclosureIndex: Int, enclosureSize: Int) = when {

            enclosureIndex == 0 && enclosureSize == 1 -> AccordionRecyclerPosition.SINGLE
            enclosureIndex == 0 -> AccordionRecyclerPosition.TOP
            enclosureIndex == enclosureSize - 1 -> AccordionRecyclerPosition.BOTTOM
            else -> AccordionRecyclerPosition.MIDDLE

        }

    /**
     * Helper class for the model data items.
     */
    data class Wrapper<Type>(

        /**
         * The viewType of the current item.
         */
        var viewType: Int,

        /**
         * The item from which the current item hails from.
         */
        var enclosingWrapper: Wrapper<out Type?>?,

        /**
         * The current item value.
         */
        var data: Type?

    )

}