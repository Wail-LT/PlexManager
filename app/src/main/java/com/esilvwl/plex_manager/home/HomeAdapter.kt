package com.esilvwl.plex_manager.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esilvwl.dataClasses.Item
import com.esilvwl.dataClasses.ItemList
import com.esilvwl.plex_manager.R
import com.esilvwl.plex_manager.Utils
import com.esilvwl.plex_manager.home.movie.CategoryViewHolder
import com.esilvwl.plex_manager.item.SimilarItemAdapter
import com.esilvwl.plex_manager.item.ItemViewHolder
import com.esilvwl.plex_manager.item.OnItemListener
import java.lang.Exception


class HomeAdapter(context: Context, categoryList: List<ItemList>? = null,
                  itemList: List<Item>? = null, onItemListener: OnItemListener? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //For Debugging
    val TAG = SimilarItemAdapter::class.simpleName

    //private constantes
    val TYPE_CATEGORY = 1
    val TYPE_ITEM = 2

    private var _categoryList: List<ItemList>? = categoryList
    private var _itemList:List<Item>? = itemList
    private var _context = context
    private var _onItemListener= onItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view : View
        //Get the view holder
        var holder : RecyclerView.ViewHolder
        when(viewType){
            TYPE_CATEGORY -> {
                //Get the view
                view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_categorie, parent, false)
                holder =
                    CategoryViewHolder(view)
            }

            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_item, parent, false)
                holder =
                    ItemViewHolder(
                        view,
                        _onItemListener!!
                    )
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder : called")

        when(holder){
            is ItemViewHolder ->{
                Utils.glideImage(_context, holder.poster,_itemList!![position].poster_path)

                holder.itemType = _itemList!![position].type
                holder.itemId = _itemList!![position].id
            }

            is CategoryViewHolder ->{
                val genreStrId = _context.resources.getIdentifier(_categoryList!![position].category!!.name.toLowerCase(), "string", _context.packageName)
                try {
                    holder.genreTitle.text = _context.resources.getString(genreStrId)
                }catch (e : Exception){
                    holder.genreTitle.text = ""
                }

                holder.recyclerView.apply {
                    adapter = HomeAdapter(
                        _context,
                        itemList = _categoryList!![position].itemList,
                        onItemListener= _onItemListener
                    )
                    layoutManager = LinearLayoutManager(_context, LinearLayoutManager.HORIZONTAL,false)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if ( _categoryList == null) {
            _itemList!!.count()
        } else {
            _categoryList!!.count()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ( _categoryList == null) {
            TYPE_ITEM
        } else {
            TYPE_CATEGORY
        }
    }
}