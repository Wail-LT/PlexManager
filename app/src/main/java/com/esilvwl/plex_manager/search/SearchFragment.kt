package com.esilvwl.plex_manager.search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.esilvwl.dataClasses.Item

import com.esilvwl.plex_manager.R
import com.esilvwl.plex_manager.item.OnItemListener
import com.esilvwl.plexmanager.api.Api
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), OnHistoryListener{

    private val TAG = SearchFragment::class.simpleName

    private var historyList =  mutableListOf<String>()
    private var sharedPreferences: SharedPreferences? = null
    private var currentIndex: Int = 0
    private val PREF_CURR_INDEX = "current_index"
    private var validateSearch = false

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        sharedPreferencesSetup()
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerHistorySetup()
        recyclerResultSetup()
        searchBarSetup()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if(!searchBar.query.isNullOrBlank())
            searchBar.clearFocus()
        searchBar.setQuery(searchBar.query, true)
    }

    private fun addToHistory(value : String){
        if(!historyList.contains(value))
        {
            sharedPreferences!!.edit()
                .putInt(PREF_CURR_INDEX, currentIndex+1)
                .putString(currentIndex.toString(), value)
                .apply()

            currentIndex++
            historyList.add(value)
        }
    }

    private fun recyclerHistorySetup(){

        //Init recyclerView
        recyclervHistory.apply {
            adapter = SearchAdapter(
                recyclervHistory.context,
                this@SearchFragment,
                historyList)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun recyclerResultSetup(){
        //Init recyclerView
        recyclervResult.apply {
            adapter = SearchAdapter(
                context,
                onItemListener = OnItemListener(
                    (context as AppCompatActivity),
                    R.id.action_bottom_nav_search_to_bottom_nav_movieDetails,
                    R.id.action_bottom_nav_search_to_bottom_nav_serieDetails
                ),
                itemList = listOf<Item>()
            )
            layoutManager = GridLayoutManager(context, 3)
            visibility = View.GONE

            val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
            layoutAnimation = controller
        }
    }


    private fun searchBarSetup(){

        searchBar.apply{
            isIconified = false
            isFocusable = true
            isFocusedByDefault = !validateSearch

            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val search = searchBar.query.toString()
                    addToHistory(search)
                    val result = mutableListOf<Item>()

                    Api.searchMovie(search){ itemList ->
                        run {
                            result.addAll(itemList!!.itemList)
                            Api.searchSerie(search) { itemList ->
                                run {
                                    result.addAll(itemList!!.itemList)
                                    result.removeIf { it.poster_path.isNullOrBlank() }
                                    showResults(result)
                                }
                            }
                        }
                    }
                    validateSearch = true
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    validateSearch = false
                    return false
                }
            })

            setOnQueryTextFocusChangeListener(object: View.OnFocusChangeListener{
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if(hasFocus){
                        recyclervResult.visibility = View.GONE
                        recyclervHistory.visibility = View.VISIBLE
                    } else{
                        recyclervHistory.visibility = View.GONE
                        recyclervResult.visibility = View.VISIBLE
                        clearFocus()
                    }
                }
            })
            val query = query.toString()
            if(!query.toString().isNullOrBlank())
                setQuery(query.toString(), true)
        }
    }

    private fun showResults(mList : List<Item>?) {
        searchBar.clearFocus()
        recyclervResult.apply {
            (adapter as SearchAdapter)._itemList = mList
            adapter!!.notifyDataSetChanged()
            scheduleLayoutAnimation()
        }
    }

    private fun sharedPreferencesSetup()
    {
        sharedPreferences =  context?.getSharedPreferences("search_history", Context.MODE_PRIVATE)

        //Get all element from sharedPreferences
        val preferences = sharedPreferences!!.all
        if (!preferences.isEmpty()) {
            //Retrieve the current index and delete it from the map
            currentIndex = preferences[PREF_CURR_INDEX]as Int
            preferences.remove(PREF_CURR_INDEX)
            historyList = mutableListOf<String>().apply { addAll(preferences.values as Collection<String>) }
        }
        else{
            sharedPreferences!!.edit()
                .putInt(PREF_CURR_INDEX, currentIndex)
                .apply()
        }
    }

    override fun onClick(position: Int) {
        searchBar.setQuery(historyList[position], true)
    }
}
