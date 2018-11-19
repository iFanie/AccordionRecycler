package com.izikode.izilib.accordionrecyclerdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.izikode.izilib.accordionrecyclerdemo.data.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var grayIndex = 0
    private val colorData = arrayListOf<ColorData>().apply {
        repeat(100) {
            add(gray())
            addAll(redArray(2))
            add(gray())
            add(gray())
            addAll(redArray(8))
            add(gray())
            add(gray())
            add(gray())
            addAll(redArray(3))
            add(gray())
            addAll(redArray(1))
            add(gray())
            addAll(redArray(4))
        }
    }

    fun getNewPink() = pinkArray(-1, Random().nextInt(4) + 1)

    private fun gray() = GrayData("$grayIndex gray").also { grayIndex += 1 }

    var redIndex = 0
    private fun redArray(sum: Int): Array<RedData> = Array(sum) {
            RedData("$redIndex red").apply {
                arrayOfPink = pinkArray(redIndex, Random().nextInt(8) + 1)
            }.also { redIndex += 1 }
        }

    var pinkIndex = 0
    private fun pinkArray(forRed: Int, sum: Int): Array<PinkData> = Array(sum) {
            PinkData("$pinkIndex pink of $forRed red").apply {
                arrayOfWhite = whiteArray(pinkIndex, Random().nextInt(9))
            }.also { pinkIndex += 1 }
        }

    var whiteIndex = 0
    private fun whiteArray(forPink: Int, sum: Int): Array<WhiteData> = Array(sum) {
            WhiteData("$whiteIndex white of $forPink pink").also { whiteIndex += 1 }
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
        hello.text = "Hello Accordion Recycler! size: ${accordionAdapter.itemCount}"
    }

}
