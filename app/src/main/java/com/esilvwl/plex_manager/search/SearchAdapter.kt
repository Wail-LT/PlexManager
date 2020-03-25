package com.esilvwl.plex_manager.search

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esilvwl.dataClasses.Item
import com.esilvwl.plex_manager.R
import com.esilvwl.plex_manager.Utils
import com.esilvwl.plex_manager.item.OnItemListener
import com.esilvwl.plex_manager.item.SimilarItemViewHolder


class SearchAdapter(context: Context, onHistoryListener: OnHistoryListener? = null, historyList: MutableCollection<String>? = null, itemList: List<Item>? = null, onItemListener: OnItemListener? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //For Debugging
    val TAG = SearchAdapter::class.simpleName

    //private constantes
    val TYPE_HISTORY = 1
    val TYPE_MOVIE = 2

    var _context = context
    var _historyList = historyList
    var _itemList = itemList
    private var _onHistoryListener = onHistoryListener
    private var _onItemListener = onItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view : View
        //Get the view holder
        var holder : RecyclerView.ViewHolder
        when(viewType) {
            TYPE_HISTORY -> {
                //Get the view
                view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_search_history, parent, false)
                holder =
                    SearchViewHolder(view, _onHistoryListener)
            }
            else -> {
                //Get the view
                view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_similar_item, parent, false)
                holder =
                    SimilarItemViewHolder(
                        view,
                        _onItemListener!!
                    )
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder : called")
        when (holder) {
            is SearchViewHolder -> {
                holder.history.text = _historyList!!.elementAt(position)
            }
            is SimilarItemViewHolder ->{
                Utils.glideImage(_context, holder.poster,_itemList!![position].poster_path)

                holder.itemType = _itemList!![position].type
                holder.itemId = _itemList!![position].id
            }
        }
    }

    override fun getItemCount(): Int {
        return if ( _itemList != null) {
            _itemList!!.count()
        } else if ( _historyList != null){
            _historyList!!.count()
        }
        else{
            0
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ( _historyList == null) {
            TYPE_MOVIE
        } else {
            TYPE_HISTORY
        }
    }


}