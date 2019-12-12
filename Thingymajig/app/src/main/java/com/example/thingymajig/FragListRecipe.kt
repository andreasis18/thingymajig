package com.example.thingymajig

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class FragListRecipe : ListFragment() {

    var recipeName: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object{
        fun newInstance(recipes:ArrayList<String>):FragListRecipe{
            val fragment = FragListRecipe()
            fragment.recipeName = recipes
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, recipeName)
        listAdapter = arrayAdapter
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
    }
}
