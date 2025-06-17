package com.itis.bookclub.presentation

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itis.bookclub.R
import com.itis.bookclub.domain.usecase.IsUserAuthorizedUseCase
import com.itis.bookclub.util.appComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var isUserAuthorizedUseCase: IsUserAuthorizedUseCase

    var navController: NavController? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this,
                    getString(R.string.notifications_is_disabled), Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(activity = this)

        initNavigation()
        requestNotificationPermission()
    }

    private fun initNavigation() {
        navController = (supportFragmentManager
            .findFragmentById(R.id.fv_root) as NavHostFragment)
            .navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> {
                    navController?.navigate(R.id.bookListFragment)
                    true
                }

                R.id.nav_library -> {
                    navController?.navigate(R.id.libraryListFragment)
                    true
                }

                R.id.nav_profile -> {
                    Toast
                        .makeText(
                            this,
                            getString(R.string.coming_soon),
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    true
                }

                else ->
                    false
            }
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.bookListFragment,
                R.id.libraryListFragment -> bottomNav.visibility = BottomNavigationView.VISIBLE
                else -> bottomNav.visibility = BottomNavigationView.GONE
            }
        }

        if (isUserAuthorizedUseCase.invoke()) {
            navController?.navigate(R.id.bookListFragment)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (
                checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
