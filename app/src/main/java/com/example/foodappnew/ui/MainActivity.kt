package com.example.foodappnew.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodappnew.R
import com.example.foodappnew.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navcontroller: NavController
    private lateinit var binding2:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding2.root)

        val navcontroll = findNavController(R.id.navhostfragment)
        val appbarconfig = AppBarConfiguration(setOf(
            R.id.recipeFragment,
            R.id.favoriteRecipesFragment,
            R.id.foodJokeFragment
        ))
        binding2.bottomnavigationview.setupWithNavController(navcontroll)
        setupActionBarWithNavController(navcontroll,appbarconfig)
    }

    override fun onSupportNavigateUp(): Boolean {

        return navcontroller.navigateUp() || super.onSupportNavigateUp()
    }
}