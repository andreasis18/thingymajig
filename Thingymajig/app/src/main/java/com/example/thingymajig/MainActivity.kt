package com.example.thingymajig

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        scrollView.isFillViewport = true

        var q = Volley.newRequestQueue(this)
        val url = "http://penir.jitusolution.com"
        val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String>{}, Response.ErrorListener {})

        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab != null) {
                    viewPager.currentItem = tab.position
                    tab?.select()
                }
            }

        })
    }
}
