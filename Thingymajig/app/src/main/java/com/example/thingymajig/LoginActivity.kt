package com.example.thingymajig

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login2.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)


        val q = Volley.newRequestQueue(this)
        val urlAddress = Generator.url+"login.php"
        buttonLogin.setOnClickListener {
            val uname = editTextUsername.text.toString()
            val pword = editTextPassword.text.toString()
            val stringRequest = object: StringRequest(Request.Method.POST, urlAddress, Response.Listener<String>{
                    response-> try{
                Log.e("resp", response)
                if(response!="error"){
                    Generator.loggedUser = uname

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Tidak ada User", Toast.LENGTH_SHORT).show()
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
                    params.put("name", uname)
                    params.put("pass", pword)
                    return params
                }
            }
            q.add(stringRequest)
        }

    }
}
