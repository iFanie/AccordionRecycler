# AccordionRecycler
RecyclerView adapter with nested items & expand/contract functionality
## Installation
```
implementation 'com.izikode.izilib:accordionrecycler:0.3'
```
## Usage
#### Have your Model Classes implement the ```AccordionRecyclerData``` interface.
```kotlin
abstract class ColorData( ... ) : AccordionRecyclerData<ColorData>

class GrayData( ... ) : ColorData

class RedData( ... ) : ColorData
```
#### Have your ViewHolders extend the ```AccordionRecyclerViewHolder``` abstract class.
```kotlin
abstract class ColorViewHolder<Data>( ... ) : AccordionRecyclerViewHolder<Data> where Data : ColorData

class GrayViewHolder( ... ) : ColorViewHolder<GrayData>

class RedViewHolder( ... ) : ColorViewHolder<RedData>
```
#### Create an Adapter that extends the ```AccordionRecyclerAdapter``` abstract class.
```kotlin
class MainAccordionAdapter : AccordionRecyclerAdapter<ColorViewHolder<out ColorData>, ColorData>() {

    override fun buildViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder<out ColorData> =
            when(viewType) {

                RedViewHolder.VIEW_TYPE -> RedViewHolder(parent)
                PinkViewHolder.VIEW_TYPE -> PinkViewHolder(parent)
                WhiteViewHolder.VIEW_TYPE -> WhiteViewHolder(parent)

                else -> GrayViewHolder(parent)

            }

    override fun updateViewHolder(position: Int, viewHolder: ColorViewHolder<out ColorData>, data: ColorData?, immediateEnclosedItemsSum: Int, totalEnclosedItemsSum: Int, overallPosition: AccordionRecyclerPosition, enclosedPosition: AccordionRecyclerPosition) {
            when (viewHolder) {

                is RedViewHolder -> viewHolder.apply { ... }
                is PinkViewHolder -> viewHolder.apply { ... }
                is WhiteViewHolder -> viewHolder.apply { ... }

                else -> (viewHolder as GrayViewHolder).apply { ... }

            }
        }

}
```
##### For a full example, see the sample app.