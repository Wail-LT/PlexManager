package com.esilvwl.plex_manager.search

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder_search_history.view.*

class SearchViewHolder(itemView: View, onHistoryListener: OnHistoryListener?) : RecyclerView.ViewHolder(itemView){

    //For debugging
    val TAG = SearchViewHolder::class.qualifiedName

    //Public Fields
    val history  = itemView.history;

    init {
        history.setOnClickListener(View.OnClickListener {
            onHistoryListener!!.onClick(adapterPosition)
        })
    }
}