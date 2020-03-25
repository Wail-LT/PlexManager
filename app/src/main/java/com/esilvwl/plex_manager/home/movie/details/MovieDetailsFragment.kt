package com.esilvwl.plex_manager.home.movie.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.esilvwl.dataClasses.Item
import com.esilvwl.dataClasses.MovieDetails
import com.esilvwl.plex_manager.R
import com.esilvwl.plex_manager.Utils
import com.esilvwl.plex_manager.item.OnItemListener
import com.esilvwl.plex_manager.item.SimilarItemAdapter
import com.esilvwl.plexmanager.api.Api
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_details.poster
import kotlinx.android.synthetic.main.fragment_movie_details.recyclerv


class MovieDetailsFragment : Fragment() {

    companion object{
        const val ARG_ITEM_ID = "itemId"
    }

    private val TAG = MovieDetailsFragment::class.simpleName

    private var itemId = -1
    private var similarMovieList = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        itemId = arguments!!.getInt(ARG_ITEM_ID)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        closeIcon.setOnClickListener { (context as AppCompatActivity).onBackPressed() }
        downloadButton.setOnClickListener {
            Toast.makeText(context,"Downloading ...", Toast.LENGTH_SHORT).show()
            it.visibility = View.INVISIBLE
        }
        //Api request
        Api.getMovieDetails(itemId){ movieDetails -> fillView(movieDetails)}
        Api.getSimilarMovie(itemId){ movieDetails ->
            run {
                similarMovieList.clear()
                if(movieDetails!!.itemList.isNotEmpty())
                    similarMovieList.addAll(Utils.removeNullPath(movieDetails).itemList.subList(0,6))
                else
                    similarMoviesLabel.visibility = View.GONE
                recyclerv.apply {
                    adapter!!.notifyDataSetChanged()
                    scheduleLayoutAnimation()
                }
            }
        }
    }

    private fun fillView(movieDetails: MovieDetails) {

        Utils.glideImage(this, poster,movieDetails.poster_path)
        Utils.glideImageWithBlur(this, backdrop,movieDetails.backdrop_path)

        movieTitle.text = movieDetails.original_title.replace(": ", ":\n")
        desc.text = movieDetails.overview
        releaseDate.text = movieDetails.release_date
        val hours = movieDetails.runtime / 60
        val min = movieDetails.runtime - hours*60

        lenght.text = (if (hours == 0) "" else hours.toString() + "H" ) + (if (min == 0) "" else if(min<10) " 0" + min + "min" else " " + min + "min")

        for ((index, g) in movieDetails.genres.withIndex())
            genre.text = genre.text.toString() + g.name + if(index == movieDetails.genres.size-1) "" else ", "

        ratingBar.rating = movieDetails.vote_average.toFloat()/2
    }

    private fun setupRecycler(){
        //Init recyclerView
        recyclerv.apply {
            adapter = SimilarItemAdapter(
                context,
                similarMovieList,
                onItemListener = OnItemListener(
                    (context as AppCompatActivity),
                    R.id.action_bottom_nav_movieDetails_self
                )
            )
            layoutManager = GridLayoutManager(context, 3)

            val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
            layoutAnimation = controller
        }
    }
}
