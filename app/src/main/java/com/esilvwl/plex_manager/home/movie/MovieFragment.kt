package com.esilvwl.plex_manager.home.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.esilvwl.dataClasses.ItemList
import com.esilvwl.plex_manager.R
import com.esilvwl.plex_manager.Utils
import com.esilvwl.plex_manager.home.HomeAdapter
import com.esilvwl.plex_manager.item.OnItemListener
import com.esilvwl.plexmanager.api.Api
import com.esilvwl.plexmanager.enums.ECategories
import kotlinx.android.synthetic.main.fragment_recycler_view.*


class MovieFragment : Fragment(){

    private val TAG = MovieFragment::class.simpleName
    private var movieListByGenre = mutableListOf<ItemList>()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        Log.d(TAG, "setup onCreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        //Api request
        if(movieListByGenre.isEmpty())
            for(genre in enumValues<ECategories>())
                Api.getMoviesByGenre(genre){ movieList -> fillItemLists(movieList) }
    }

    private fun fillItemLists(mList : ItemList?) {
        if (mList != null && mList.itemList.count()>1)
            movieListByGenre.add(Utils.removeNullPath(mList))

        recyclerv!!.apply{
            adapter!!.notifyDataSetChanged()
            scheduleLayoutAnimation()
        }
    }

    private fun setupRecycler(){
        //Init recyclerView
        recyclerv!!.apply {
            adapter = HomeAdapter(
                context,
                movieListByGenre,
                onItemListener = OnItemListener(
                    (context as AppCompatActivity),
                    R.id.action_bottom_nav_home_to_bottom_nav_movieDetails
                )
            )
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
            layoutAnimation = controller
        }
    }
}
