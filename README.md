# AccordionRecycler

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-AccordionRecycler-green.svg?style=flat )]( https://android-arsenal.com/details/1/7321 )
[![Bintray](https://img.shields.io/badge/Bintray-0.4-lightgrey.svg)](https://dl.bintray.com/ifanie/izilib/com/izikode/izilib/accordionrecycler/0.4/)

#### Android RecyclerView Adapter with nested items & expand/contract functionality
With AccordionRecycler you can easily create awesome RecyclerViews, containing infinitely nested items and the ability to expand or collapse any part of the View at will.

<img src="demoapp.gif" alt="Demo GIF" width="300" height="600" />

## Installation
```
implementation 'com.izikode.izilib:accordionrecycler:0.4'
```
## Usage
#### Have your Model Classes implement the ```AccordionRecyclerData``` interface.
- The Adapter needs a way to get the ViewType, Primary data and the Enclosed (child) data, if existing. You use the above interface to provide those.
- Fill the diamond operator with the type the model provides.
- Optionally, you can use a base class as a reference for your different data sub-classes and also your ViewHolders. In the provided demo, and examples below, ColorData is the base class and it provided itself.
```kotlin
abstract class ColorData : AccordionRecyclerData<ColorData>

data class RedData(

        var arrayOfPink: Array<PinkData> = arrayOf()

) : ColorData(RedViewHolder.VIEW_TYPE) {

    override val mainData: ColorData?
            get() = this

    override val enclosedDataArray: Array<out AccordionRecyclerData<out ColorData?>>?
            get() = arrayOfPink

}

data class GrayData( ... ) : ColorData() { ... }
data class PinkData( ... ) : ColorData() { ... }
data class WhiteData( ... ) : ColorData() { ... }
```
#### Have your ViewHolders extend the ```AccordionRecyclerViewHolder``` abstract class.
- Provide the ViewHolder constructor with the parent ViewGroup and the layout to be inflated.
- Fill the diamond operator with the type of Model being handled.
- Again, optionally, create a base ViewHolder as a reference.
```kotlin
abstract class ColorViewHolder<Data>( ... ) : AccordionRecyclerViewHolder<Data> where Data : ColorData

class RedViewHolder(parent: ViewGroup) : ColorViewHolder<RedData>(parent, R.layout.view_holder_red) {

    override var data: RedData? = null
    
    companion object {
        const val VIEW_TYPE = 2
    }

}

class GrayViewHolder( ... ) : ColorViewHolder<GrayData>
class PinkViewHolder( ... ) : ColorViewHolder<GrayData>
class WhiteViewHolder( ... ) : ColorViewHolder<RedData>
```
#### Create an Adapter that extends the ```AccordionRecyclerAdapter``` abstract class.
- Override the ```buildViewHolder``` function to provide new ViewHolder instances.
- Override the ```updateViewHolder``` function to update each item being recycled. The ```AccordionRecyclerItemDetails``` parameter contains information about the current item and all enclosing (parent) items, when existing. This allows for unlimited visual combinations for all view types.
- Expand/Collapse is accomplished with:
    - ```removeItem``` to remove an item and all it's child items, if existing.
    - ```removeEnclosedItems``` to remove the child items only.
    - ```addItems``` to add all items and their child items, if existing.
    - ```addEnclosedItems``` to add all items as child items to another.
- Optionally, you can modify how each item is handled when it is iterated and added into the recycler data. You do that by overriding the ```processForAdditionalItems``` function. You can use this feature to handle edge case. One such case is shown in the demo, in which whenever a Pink item does not have any child White items, a new item is added, showing the text 'Nothing to see here'.
```kotlin
class MainAccordionAdapter : AccordionRecyclerAdapter<ColorViewHolder<out ColorData>, ColorData>() {

    override fun buildViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder<out ColorData> =
            when(viewType) {

                RedViewHolder.VIEW_TYPE -> RedViewHolder(parent)
                PinkViewHolder.VIEW_TYPE -> PinkViewHolder(parent)
                WhiteViewHolder.VIEW_TYPE -> WhiteViewHolder(parent)

                else -> GrayViewHolder(parent)

            }

    override fun updateViewHolder(position: Int, viewHolder: ColorViewHolder<out ColorData>, data: ColorData?, 
                details: AccordionRecyclerItemDetails) {
                
            when (viewHolder) {

                is RedViewHolder -> viewHolder.apply { ... }
                is PinkViewHolder -> viewHolder.apply { ... }
                is WhiteViewHolder -> viewHolder.apply { ... }

                else -> (viewHolder as GrayViewHolder).apply { ... }

            }
        }
        
    override fun processForAdditionalItems(position: Int, item: AccordionRecyclerData<out ColorData?>?)
            : Array<out AccordionRecyclerData<out ColorData?>?> = item?.let {

                    if (it is PinkData && it.enclosedDataArray.isNullOrEmpty()) {
                        arrayOf(
                            it,
                            EmptyPinkViewHolder.EmptyPinkData()
                        )
                    } else {
                        super.processForAdditionalItems(position, item)
                    }

                } ?: super.processForAdditionalItems(position, item)

}
```

#### For a full example, see the sample app.

## Licence
```
Copyright 2018 Fanie Veizis

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```