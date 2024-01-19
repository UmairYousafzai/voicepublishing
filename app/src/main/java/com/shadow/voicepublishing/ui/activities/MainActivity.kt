package com.shadow.voicepublishing.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.shadow.voicepublishing.R
import com.shadow.voicepublishing.databinding.ActivityMainBinding
import com.shadow.voicepublishing.databinding.CustomNavHeaderBinding
import com.shadow.voicepublishing.utils.gone
import com.shadow.voicepublishing.utils.showSnackBar
import com.shadow.voicepublishing.utils.visible
import com.shadow.voicepublishing.view.models.AuthViewModel
import com.shadow.voicepublishing.view.models.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var actionLogin: MenuItem
    private lateinit var actionChangeOrForgotPassword: MenuItem
    var isSubscribe= false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
//        Handler(Looper.getMainLooper()).postDelayed({
//            setupNavigationHeader()
//
//        },200)//  \
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                dataStoreViewModel.isSubscribeStatus.collect{
                    isSubscribe = it
                }

            }
            lifecycleScope.launch {
                dataStoreViewModel.alreadyLoginStatus.collect{
                    if (it) {
                        actionLogin.title= "Logout"
                        actionChangeOrForgotPassword.setVisible(true)
                    }else
                    {
                        actionLogin.title= "Login"
                        actionChangeOrForgotPassword.setVisible(false)

                    }

                }
            }
        },200)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        actionLogin = menu?.findItem(R.id.item_login)!!
        actionChangeOrForgotPassword = menu?.findItem(R.id.item_forgot_password)!!


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_login -> {
                if (actionLogin.title=="Login") {

                    navController.navigate(R.id.loginFragment)
                }else
                {
                    signOut()
                }
                true
            }
            R.id.item_forgot_password ->{
                navController.navigate(R.id.forgotPasswordFragment)
                true
            }
            R.id.item_archive ->{
                if (isSubscribe) {
                    navController.navigate(R.id.archivesFragment)
                }else
                {
                    showSnackBar(binding.root,"Please subscribe for view archives")
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {

        authViewModel.signOut()
        dataStoreViewModel.clearData()
        Handler(Looper.getMainLooper()).postDelayed({
            actionLogin.title="Login"

        },1000)
    }

//    private fun setupNavigationHeader() {
//
//        val headerView: View = binding.navView.getHeaderView(0)
//        val headerBinding = CustomNavHeaderBinding.bind(headerView)
//        lifecycleScope.launch {
//            dataStoreViewModel.alreadyLoginStatus.collect {
//                if (it) {
//                    dataStoreViewModel.user.collect {
//                        runOnUiThread {
//                            actionLogin.title = "Logout"
//                            actionChangeOrForgotPassword.setVisible(true)
//                            headerBinding.tvName.text = it.name
//                            headerBinding.tvEmail.text = it.email
//                            headerBinding.parent.visible()
//                        }
//                    }
//                } else {
//                    runOnUiThread { headerBinding.parent.gone()
//                        actionChangeOrForgotPassword.setVisible(false)
//                    }
//                }
//            }
//        }
//
//    }
//

    private fun setUpNavigation() {
        setSupportActionBar(binding.customToolbar)
//        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!
        navController = navHostFragment.navController
        val appBarConfiguration: AppBarConfiguration =
            AppBarConfiguration.Builder(
                R.id.categoryFragment,
            )
//                .setOpenableLayout(binding.drawerLayout)
                .build()
//        setupWithNavController(binding.navView, navController)
        setupWithNavController(binding.customToolbar, navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d("Navigation", "setUpNavigation: ${destination.id}")
            when (destination.id) {

                R.id.categoryFragment -> {
//                    binding.customToolbar.setNavigationIcon(R.drawable.ic_humberger)
                    binding.customToolbar.visible()
//                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

                }

                else -> {
                    binding.customToolbar.gone()
//                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

            }
        }
    }

}