package com.esilvwl.plex_manager.item

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.esilvwl.plex_manager.R
import com.esilvwl.plex_manager.home.movie.details.MovieDetailsFragment
import com.esilvwl.plexmanager.enums.EItemTypes

class OnItemListener(context: AppCompatActivity, navigateToMovie: Int? = null , navigateToSerie: Int? = null) {

    private val _context = context
    private val _navigateToMovie = navigateToMovie
    private val _navigateToSerie = navigateToSerie

    fun onClick(v: View?, itemId: Int, type: EItemTypes?)
    {
        when(v!!.id){
            R.id.downloadIcon -> {
                Toast.makeText(_context, (type?.name ?: "" ) +" Downloading", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val bundle = bundleOf(
                    MovieDetailsFragment.ARG_ITEM_ID to itemId
                )
                _context.findNavController(R.id.nav_host_fragment).navigate(if(type == EItemTypes.Movie)_navigateToMovie!! else _navigateToSerie!!, bundle)
            }
        }
    }
}