package com.esilvwl.plex_manager.home.movie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder_categorie.view.*

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //For debugging
    private val TAG = CategoryViewHolder::class.qualifiedName

    //Fields
    val genreTitle = itemView.genreTitle
    val recyclerView = itemView.genreRecyclerv

}