package com.esilvwl.plex_manager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.esilvwl.plex_manager.R
import com.esilvwl.plex_manager.home.movie.MovieFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.simpleName

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*navBar.setOnNavigationItemSelectedListener {
            Toast.makeText(context, it.title.toString() + it.groupId, Toast.LENGTH_SHORT).show()
            true
        }*/




        super.onViewCreated(view, savedInstanceState)

        val navController = (context as AppCompatActivity).findNavController(R.id.home_nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        (context as AppCompatActivity).setupActionBarWithNavController(navController, appBarConfiguration)
        navBar.setupWithNavController(navController)
    }
}
