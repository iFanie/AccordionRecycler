package com.izikode.accordionrecyclerdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.izikode.accordionrecyclerdemo.data.GrayData
import com.izikode.accordionrecyclerdemo.data.PinkData
import com.izikode.accordionrecyclerdemo.data.RedData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val colorData = arrayListOf(

        GrayData("First gray"),

        RedData("First red").apply {
            secondary = arrayOf(
                PinkData("First pink of First red"), PinkData("Second pink of First red"),
                PinkData("Third pink of First red"), PinkData("Fourth pink of First red")
            )
        },

        GrayData("Seconds gray"),

        RedData("Second red"),

        RedData("Third red").apply {
            secondary = arrayOf(
                PinkData("First pink of Third red"), PinkData("Second pink of Third red"),
                PinkData("Third pink of Third red"), PinkData("Fourth pink of Third red")
            )
        },

        RedData("Fourth red").apply {
            secondary = arrayOf(
                PinkData("First pink of Fourth red")
            )
        },

        GrayData("Third gray")

    )

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
