package com.esilvwl.plex_manager

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.esilvwl.dataClasses.Item
import com.esilvwl.dataClasses.ItemList
import com.esilvwl.plexmanager.api.Api
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_movie_details.*

class Utils {
    companion object{
        fun removeNullPath(list: ItemList) : ItemList{
            val items = mutableListOf<Item>().apply { addAll(list.itemList) }
            items.removeIf { it.poster_path.isNullOrBlank() }
            return ItemList(list.category, items)
        }

        fun glideImage(context: Context, imageView: ImageView, path: String?){
            val imageUrl = Api.imageBaseUrl + Api.posterSize +path

            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(imageView)
        }

        fun glideImage(context: Fragment, imageView: ImageView, path: String?){
            val imageUrl = Api.imageBaseUrl + Api.posterSize +path

            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(imageView)
        }

        fun glideImageWithBlur(context: Fragment, imageView: ImageView, path: String?) {
            val imageUrl = Api.imageBaseUrl + Api.backdropSize +path
            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(18)))
                .into(imageView)
        }
    }
}