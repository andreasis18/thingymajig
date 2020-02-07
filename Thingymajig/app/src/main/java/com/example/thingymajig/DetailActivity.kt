package com.example.thingymajig

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.imageResource
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class DetailActivity : AppCompatActivity() {

    var recipeId:Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = this.intent
        recipeId = intent.getIntExtra("recipeId",1)

        textViewNameRecipe.text = intent.getStringExtra("recipeName")
        textViewDescriptionRecipe.text = intent.getStringExtra("recipeDescription")
        val urlImage = URL(intent.getStringExtra("recipeImage"))
//        val bitmap = BitmapFactory.decodeStream(urlImage.openConnection().getInputStream())
//        imageViewRecipe.setImageBitmap(bitmap)

        var q = Volley.newRequestQueue(this)
        val url = Generator.url + "getDetail.php"

        val stringRequest = object: StringRequest(Method.POST, url, Response.Listener<String>{
                response-> try{
            Log.e("resp", response)
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
            val jsonObject = JSONObject(response)
            val jsonArray: JSONArray = jsonObject.getJSONArray("recipe")

            for(i in 0 until jsonArray.length()){
                val jsonRecipe = jsonArray.getJSONObject(i)

                val tempIngredient = TextView(this)
                tempIngredient.text = jsonRecipe.getString("ingredient")
                linearLayoutDetail.addView(tempIngredient)
            }
            val jsonFavorite: JSONArray = jsonObject.getJSONArray("favorite")
            val jsonStatus = jsonFavorite.getJSONObject(0)
            if(jsonStatus.getBoolean("status")){
                imageButton.setImageResource(R.drawable.ic_star_black_24dp)
            }else{
                imageButton.setImageResource(R.drawable.ic_star_border_black_24dp)
            }

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
                params.put("recipeId", recipeId.toString())
                return params
            }
        }
        q.add(stringRequest)

        imageButton.setOnClickListener {
            var q2 = Volley.newRequestQueue(this)
            val url2 = Generator.url + "addFav.php"

            val stringRequest2 = object: StringRequest(Method.POST, url2, Response.Listener<String>{
                    response-> try{
                Log.e("resp", response)
                if(response=="true"){
                    imageButton.setImageResource(R.drawable.ic_star_black_24dp)
                }else{
                    imageButton.setImageResource(R.drawable.ic_star_border_black_24dp)
                }
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
                    params.put("recipeId", recipeId.toString())
                    return params
                }
            }
            q2.add(stringRequest2)
        }
    }
}
