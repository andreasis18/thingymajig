package com.example.thingymajig

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        scrollView.isFillViewport = true

        var q = Volley.newRequestQueue(this)
        val url = Generator.url

        val stringRequest = object: StringRequest(Request.Method.POST, url, Response.Listener<String>{
                response-> try{
            Log.e("resp", response)
            val jsonObject = JSONObject(response)
            val jsonArray: JSONArray = jsonObject.getJSONArray("projects")
            for(i in 0 until jsonArray.length()){
                val jsonProject = jsonArray.getJSONObject(i)
                projects.add(Project(jsonProject.getInt("id"), jsonProject.getString("project_name"),
                    jsonProject.getString("leader"), jsonProject.getString("project_status")))

                if(Global.loggedUser == jsonProject.getString("leader")){
                    projectsLead.add(Project(jsonProject.getInt("id"), jsonProject.getString("project_name"),
                        jsonProject.getString("leader"), jsonProject.getString("project_status")))
                }
                else{
                    projectsParticipating.add(Project(jsonProject.getInt("id"), jsonProject.getString("project_name"),
                        jsonProject.getString("leader"), jsonProject.getString("project_status")))
                }
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
                params.put("username", Generator.loggedUser)
                return params
            }
        }
        q.add(stringRequest)

        val fragmentAdapter = FragmentAdapter(supportFragmentManager)
        fragmentAdapter.addFragment(FragFavRecipe())
        fragmentAdapter.addFragment(FragListRecipe())
        fragmentAdapter.addFragment(FragAddRecipe())
        viewPager.adapter = fragmentAdapter

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
