package com.izikode.izilib.accordionrecyclerdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.izikode.izilib.accordionrecyclerdemo.data.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val colorData = arrayListOf<ColorData>().apply {
        add(GrayData("1 gray"))
        addAll(redArray(2))
        add(GrayData("2 gray"))
        add(GrayData("3 gray"))
        addAll(redArray(4))
        add(GrayData("4 gray"))
        addAll(redArray(2))
    }

    fun getNewPink() = pinkArray(-1, Random().nextInt(4) + 1)

    private fun redArray(sum: Int): Array<RedData> = Array(sum) { index ->
            RedData("$index red").apply {
                arrayOfPink = pinkArray(index, Random().nextInt(8) + 1)
            }
        }

    private fun pinkArray(forRed: Int, sum: Int): Array<PinkData> = Array(sum) { index ->
            PinkData("$index pink of $forRed red").apply {
                arrayOfWhite = whiteArray(index, Random().nextInt(9))
            }
        }

    private fun whiteArray(forPink: Int, sum: Int): Array<WhiteData> = Array(sum) { index ->
            WhiteData("$index white of $forPink pink")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accordionAdapter = MainAccordionAdapter()

        recycler.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = accordionAdapter
        }

        accordionAdapter.colors = colorData
    }

}
