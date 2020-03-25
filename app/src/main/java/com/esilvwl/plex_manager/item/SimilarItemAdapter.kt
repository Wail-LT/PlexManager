package com.esilvwl.plex_manager.item

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esilvwl.dataClasses.Item
import com.esilvwl.plex_manager.R
import com.esilvwl.plex_manager.Utils


class SimilarItemAdapter(context: Context, itemList: List<Item>, onItemListener: OnItemListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //For Debugging
    val TAG = SimilarItemAdapter::class.simpleName

    //private constantes
    val TYPE_CATEGORY = 1
    val TYPE_ITEM = 2

    private var _itemList: List<Item> = itemList
    private var _context = context
    private var _onItemListener = onItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_similar_item, parent, false)

        return SimilarItemViewHolder(
            view,
            onItemListener = _onItemListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder : called")
        when (holder) {
            is SimilarItemViewHolder -> {
                Utils.glideImage(_context, holder.poster,_itemList[position].poster_path)
                holder.itemType = _itemList[position].type
                holder.itemId = _itemList[position].id
            }
        }
    }

    override fun getItemCount(): Int {
        return _itemList!!.count()
    }
}