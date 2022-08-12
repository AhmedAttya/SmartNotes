package com.tresole.smartnotes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.tresole.smartnotes.databinding.ActivityMainBinding
import com.tresole.smartnotes.main.MainFragment
import com.tresole.smartnotes.note.NoteFragment


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var controller :NavController
    private lateinit var binding: ActivityMainBinding
    lateinit var  listener :NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_settings -> true
                    android.R.id.home -> {
                        onBackPressed()
                        true
                    }
                    else -> true
                }

            }
        })
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        controller = navController
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.allnotes, R.id.favourite, R.id.trash), binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        binding.drawerLayout.setDrawerListener(toggle)
        toggle.syncState()
        listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.mainFragment) {
                binding.fab.setImageResource(R.drawable.outline_add_circle_24)
                binding.toolbar.setNavigationIcon(R.drawable.ic_menu_24dp)
            }
            else if (destination.id== R.id.noteFragment)
            {
                 binding.fab.setImageResource(R.drawable.outline_done_black_24)
                toggle.isDrawerIndicatorEnabled = false
            }
        }
        toggle.setToolbarNavigationClickListener {
            toggle.isDrawerIndicatorEnabled = true
            navController.navigate(R.id.action_noteFragment_to_mainFragment)
        }
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            binding.drawerLayout.close()
            val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
            val fragment=navHostFragment?.childFragmentManager?.fragments?.get(0)

            when (menuItem.itemId){
                R.id.allnotes ->{
                    if(fragment is MainFragment){
                 fragment.loadall()
                }
                }
                R.id.trash-> {
                    if(fragment is MainFragment) {
                        fragment.loadtrash()
                    }
                }
                R.id.favourite -> {
                    if(fragment is MainFragment) {
                        fragment.loadfavourite()
                    }

                }
            }

            true
        }

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        binding.fab.setOnClickListener {
            val currentfragment=navHostFragment?.childFragmentManager?.fragments?.get(0)
            if (currentfragment is MainFragment ) {
                navController.navigate(R.id.action_mainFragment_to_noteFragment)
                binding.fab.setImageResource(R.drawable.outline_done_24)

            }
            if (currentfragment is NoteFragment){
                currentfragment.savenote()
                navController.navigate(R.id.action_noteFragment_to_mainFragment)
                binding.fab.setImageResource(R.drawable.outline_add_circle_black_24)
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        controller.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        controller.removeOnDestinationChangedListener(listener)
        super.onPause()
    }
}