package com.esilvwl.plex_manager.home.serie.details

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
import com.esilvwl.dataClasses.SerieDetails
import com.esilvwl.plex_manager.R
import com.esilvwl.plex_manager.Utils
import com.esilvwl.plex_manager.item.OnItemListener
import com.esilvwl.plex_manager.item.SimilarItemAdapter
import com.esilvwl.plexmanager.api.Api
import kotlinx.android.synthetic.main.fragment_serie_details.*
import kotlinx.android.synthetic.main.fragment_serie_details.backdrop
import kotlinx.android.synthetic.main.fragment_serie_details.closeIcon
import kotlinx.android.synthetic.main.fragment_serie_details.desc
import kotlinx.android.synthetic.main.fragment_serie_details.downloadButton
import kotlinx.android.synthetic.main.fragment_serie_details.genre
import kotlinx.android.synthetic.main.fragment_serie_details.poster
import kotlinx.android.synthetic.main.fragment_serie_details.ratingBar
import kotlinx.android.synthetic.main.fragment_serie_details.recyclerv
import kotlinx.android.synthetic.main.fragment_serie_details.releaseDate
import kotlinx.android.synthetic.main.fragment_serie_details.similarMoviesLabel


class SerieDetailsFragment : Fragment() {

    companion object{
        const val ARG_ITEM_ID = "itemId"
    }

    private val TAG = SerieDetailsFragment::class.simpleName

    private var itemId = -1
    private var similarMovieList = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        itemId = arguments!!.getInt(ARG_ITEM_ID)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_serie_details, container, false)
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
        Api.getSerieDetails(itemId){ serieDetails -> fillView(serieDetails)}
        Api.getSimilarSerie(itemId){ serieDetails ->
            run {
                run {
                    similarMovieList.clear()
                    if(serieDetails!!.itemList.isNotEmpty())
                        similarMovieList.addAll(Utils.removeNullPath(serieDetails).itemList.subList(0,6))
                    else
                        similarMoviesLabel.visibility = View.GONE
                    recyclerv.apply {
                        adapter!!.notifyDataSetChanged()
                        scheduleLayoutAnimation()
                    }
                }
            }
        }
    }

    private fun fillView(serieDetails: SerieDetails) {

        Utils.glideImage(this, poster,serieDetails.poster_path)
        Utils.glideImageWithBlur(this, backdrop,serieDetails.backdrop_path)

        serieTitle.text = serieDetails.original_title.replace(": ", ":\n")
        desc.text = serieDetails.overview
        releaseDate.text = serieDetails.release_date

        season.text = serieDetails.number_of_seasons.toString() + " Seasons"

        for ((index, g) in serieDetails.genres.withIndex())
            genre.text = genre.text.toString() + g.name + if(index == serieDetails.genres.size-1) "" else ", "

        ratingBar.rating = serieDetails.vote_average.toFloat()/2
    }

    private fun setupRecycler(){
        //Init recyclerView
        recyclerv.apply {
            adapter =
                SimilarItemAdapter(
                    context,
                    similarMovieList,
                    onItemListener = OnItemListener(
                        (context as AppCompatActivity),
                        R.id.action_bottom_nav_serieDetails_self
                    )
                )
            layoutManager = GridLayoutManager(context, 3)

            val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
            layoutAnimation = controller
        }
    }
}
