package com.esilvwl.plex_manager.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.esilvwl.plexmanager.enums.EItemTypes
import kotlinx.android.synthetic.main.viewholder_item.view.*

class SimilarItemViewHolder(itemView: View, onItemListener: OnItemListener) : RecyclerView.ViewHolder(itemView){

    //For debugging
    val TAG = SimilarItemViewHolder::class.qualifiedName

    //Private Fields
    private var _itemId = -1

    //Public Fields
    val poster  = itemView.poster

    var itemType: EItemTypes? = null

    var itemId: Int
        get() = _itemId
        set(id){
            if(id<0)
                throw Exception(TAG + " : Invalid movie id")

            _itemId = id
        }
    init {
        poster.setOnClickListener(View.OnClickListener {
            onItemListener.onClick(it, _itemId, itemType)
        })
    }
}