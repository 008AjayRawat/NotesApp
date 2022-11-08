package com.app.notesapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.app.notesapp.R
import com.app.notesapp.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.container)

        //Floating action button will redirect to Add New Notes Fragment #AddFragment
        binding.fab.setOnClickListener {
            onFloatingClicked()
        }
    }

    private fun onFloatingClicked() {
        navController.navigate(R.id.action_listFragment_to_addFragment)
        binding.fab.hide()
    }

    fun showFloatingButton(){
        binding.fab.show()
        binding.fab.visibility = View.VISIBLE
    }
}