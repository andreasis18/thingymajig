package com.example.thingymajig

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var q = Volley.newRequestQueue(this)
        val url = "http://penir.jitusolution.com"
        val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String>{}, Response.ErrorListener {})
    }
}
