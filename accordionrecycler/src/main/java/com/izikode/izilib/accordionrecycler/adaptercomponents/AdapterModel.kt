package com.izikode.izilib.accordionrecycler.adaptercomponents

import com.izikode.izilib.accordionrecycler.AccordionRecyclerData
import com.izikode.izilib.accordionrecycler.AccordionRecyclerPosition
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
        val sum = recursivelyAddAllAndReturnSum(startingIndex, dataArray)

        if (!silently) {
            presenter.onItemsAdded(startingIndex, sum)
        }
    }

    override fun addEnclosedData(enclosingIndex: Int, enclosedDataArray: Array<out AccordionRecyclerData<out DataType?>>, silently: Boolean) {
        val startingIndex = enclosingIndex + 1
        val sum = recursivelyAddAllAndReturnSum(startingIndex, enclosedDataArray, dataList[enclosingIndex])

        if (!silently) {
            presenter.onItemsAdded(startingIndex, sum)
            presenter.onItemsChanged(enclosingIndex, 1)

            val trailingIndex = startingIndex + sum

            if (trailingIndex < dataCount) {
                presenter.onItemsChanged(trailingIndex, 1)
            }
        }
    }

    /**
     * Adds all items in the provided array along with all enclosedDataArray data, in their relative order.
     *
     * @param dataArray  The items to be added, along with their enclosedDataArray data.
     * @param containingData  The parent item, if existing, for the provided array.
     * @return The total number, primary plus enclosedDataArray, of data entries added.
     */
    private fun recursivelyAddAllAndReturnSum(startingIndex: Int, dataArray: Array<out AccordionRecyclerData<out DataType?>>, containingData: Wrapper<out DataType?>? = null): Int {
        var sum = 0
        var startFrom = startingIndex

        val containingList = containingData?.let {
                if (!containingListMap.containsKey(it)) {
                    val list: MutableList<WeakReference<Wrapper<out DataType?>>> = arrayListOf()
                    containingListMap[it] = list

                    list
                } else {
                    containingListMap[it]
                }
            }

        dataArray.forEach { accordionRecyclerData ->

            val mainWrapper = Wrapper(
                accordionRecyclerData.viewType,
                containingData,
                accordionRecyclerData.mainData
            )

            /* Add main data */
            dataList.add(startFrom, mainWrapper)

            sum += 1
            startFrom += 1

            /* Update map list if needed */
            containingList?.add(WeakReference(mainWrapper))

            /* If exist, add enclosed data array recursively */
            accordionRecyclerData.enclosedDataArray?.let {
                val secondarySum = recursivelyAddAllAndReturnSum(startFrom, it, mainWrapper)

                sum += secondarySum
                startFrom += secondarySum
            }
        }

        return sum
    }

    override fun clearData(silently: Boolean) {
        containingListMap.clear()
        dataList.clear()

        if (!silently) {
            presenter.onItemSetChanged()
        }
    }

    override fun removeData(index: Int, silently: Boolean) {
        dataList[index].let { wrapper ->
            val previousIndex = index - 1
            val sum = recursivelyRemoveAllAndReturnSum(wrapper, true, true)

            if (!silently) {
                presenter.onItemsRemoved(index, sum)

                if (previousIndex >= 0) {
                    val changedSum = if (previousIndex < dataCount - 1) 2 else 1
                    presenter.onItemsChanged(previousIndex, changedSum)
                }
            }
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

    /**
     * Removes the provided item's child data, and possibly, the provided item as well.
     *
     * @param enclosingWrapper  The item whose children will be removed, and possibly itself too.
     * @param inclusive  When {@code true} the provided item gets removed as well.
     * @param backwards  When {@code true} the provided item gets removed from it's parent, if existing.
     * @return The total number of data entries removed.
     */
    private fun recursivelyRemoveAllAndReturnSum(enclosingWrapper: Wrapper<out DataType?>, inclusive: Boolean = true, backwards: Boolean = false): Int {
        var sum = 0

        containingListMap[enclosingWrapper]?.let { enclosedWeakReferences ->
            clearListFromNullReferences(enclosedWeakReferences)

            enclosedWeakReferences.forEach { enclosedWeakReference ->
                enclosedWeakReference.get()?.let { enclosedWrapper ->
                    sum += recursivelyRemoveAllAndReturnSum(enclosedWrapper, true)
                }
            }

            enclosedWeakReferences.clear()
        }

        if (inclusive) {
            if (backwards) {
                enclosingWrapper.enclosingWrapper?.let { outerEnclosingWrapper ->
                    containingListMap[outerEnclosingWrapper]?.let { outerEnclosingWeakReferences ->
                        clearListFromNullReferences(outerEnclosingWeakReferences)

                        var indexInOuter: Int? = null

                        for (index in 0 until outerEnclosingWeakReferences.size) {
                            if (outerEnclosingWeakReferences[index].get() == enclosingWrapper) {
                                indexInOuter = index
                                break
                            }
                        }

                        indexInOuter?.let {
                            outerEnclosingWeakReferences.removeAt(it)
                        }
                    }
                }
            }

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

    /**
     * Gets the position info of an item relative to it's parent.
     *
     * @param wrapper  The item whose position is to be calculated.
     * @return The position info for the item, relative to it's parent.
     */
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

    /**
     * Removes any WeakReference entries that have been nullified by the Garbage Collector.
     *
     * @param list  The list from which to remove nullified WeakReferences.
     */
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

    /**
     * Calculates position info based on index and size of total data.
     * @param enclosureIndex  The index for the calculation.
     * @param enclosureSize  The size of the total data.
     * @return The calculated position info.
     */
    private fun getPositionFromEnclosureIndexAndSize(enclosureIndex: Int, enclosureSize: Int) = when {

            enclosureIndex == 0 && enclosureSize == 1 -> AccordionRecyclerPosition.SINGLE
            enclosureIndex == 0 -> AccordionRecyclerPosition.TOP
            enclosureIndex == enclosureSize - 1 -> AccordionRecyclerPosition.BOTTOM
            else -> AccordionRecyclerPosition.MIDDLE

        }

    /**
     * Helper class for the model data items.
     */
    data class Wrapper<DataType>(

        /**
         * The viewType of the current item.
         */
        val viewType: Int,

        /**
         * The item from which the current item hails from.
         */
        val enclosingWrapper: Wrapper<out DataType?>?,

        /**
         * The current item value.
         */
        val data: DataType?

    )

}