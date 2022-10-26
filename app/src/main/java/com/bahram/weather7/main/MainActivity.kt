package com.bahram.weather7.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bahram.weather7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        goToBriefFragment()
    }

//    private fun goToBriefFragment() {
//        val briefFragment = BriefFragment()
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragment_container_main, briefFragment)
//        fragmentTransaction.commit()
//    }

}

