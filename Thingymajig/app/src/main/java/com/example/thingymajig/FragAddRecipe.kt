package com.example.thingymajig


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_frag_add_recipe.*

/**
 * A simple [Fragment] subclass.
 */
class FragAddRecipe : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
//        btnSaveRecipe.setOnClickListener {
//            val intent = Intent(activity, AddRecipe::class.java)
//            startActivity(intent)
//        }
        return inflater.inflate(R.layout.fragment_frag_add_recipe, container, false)
    }
}
