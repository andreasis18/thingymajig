package com.example.thingymajig

import android.content.Context
import android.content.Intent
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
    var recipe: ArrayList<Recipe> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object{
        fun newInstance(recipesName:ArrayList<String>, recipes:ArrayList<Recipe>):FragListRecipe{
            val fragment = FragListRecipe()
            fragment.recipeName = recipesName
            fragment.recipe = recipes
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

        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("recipeId", recipe[position].Id)
        intent.putExtra("recipeName", recipe[position].name)
        intent.putExtra("recipeDescription", recipe[position].description)
        intent.putExtra("recipeImage", recipe[position].image)
        startActivityForResult(intent,1)
    }
}
