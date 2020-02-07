package com.example.thingymajig

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    var recipes = ArrayList<Recipe>()
    var recipesName = ArrayList<String>()
    var recipesFav = ArrayList<Recipe>()
    var recipesFavName = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        scrollView.isFillViewport = true

        var q = Volley.newRequestQueue(this)
        val url = Generator.url + "getAll.php"

        val stringRequest = object: StringRequest(Request.Method.POST, url, Response.Listener<String>{
                response-> try{
            Log.e("resp", response)
            val jsonObject = JSONObject(response)
            val jsonArray: JSONArray = jsonObject.getJSONArray("recipe")
            for(i in 0 until jsonArray.length()){
                val jsonRecipe = jsonArray.getJSONObject(i)
                recipes.add(Recipe(jsonRecipe.getInt("id"), jsonRecipe.getString("name"),
                    jsonRecipe.getString("description"), jsonRecipe.getString("image")))
                recipesName.add(jsonRecipe.getString("name"))
            }
            val jsonArrayFav: JSONArray = jsonObject.getJSONArray("fav")
            for(i in 0 until jsonArrayFav.length()){
                val jsonRecipe = jsonArrayFav.getJSONObject(i)
                recipesFav.add(Recipe(jsonRecipe.getInt("id"), jsonRecipe.getString("name"),
                    jsonRecipe.getString("description"), jsonRecipe.getString("image")))
                recipesFavName.add(jsonRecipe.getString("name"))
            }

            val fragmentAdapter = FragmentAdapter(supportFragmentManager)
            fragmentAdapter.addFragment(FragFavRecipe.newInstance(recipesFavName, recipesFav))
            fragmentAdapter.addFragment(FragListRecipe.newInstance(recipesName, recipes))
            fragmentAdapter.addFragment(FragAddRecipe())
            viewPager.adapter = fragmentAdapter
        } catch (e: JSONException){
            Log.e("error",e.message)
        }
        }, Response.ErrorListener{
                response-> Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
        })
        {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("name", Generator.loggedUser)
                return params
            }
        }
        q.add(stringRequest)

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


    override fun onActivityResult(requestCode:Int, ResultCode:Int, data: Intent?){
        if(requestCode == 1){
            if(ResultCode== Activity.RESULT_OK){
                finish();
                startActivity(intent);
            }
        }
    }
}
