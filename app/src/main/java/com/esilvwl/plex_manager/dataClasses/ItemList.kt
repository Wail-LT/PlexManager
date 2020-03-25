package com.esilvwl.dataClasses

import com.esilvwl.plexmanager.enums.ECategories
import com.google.gson.annotations.SerializedName

data class ItemList (
    var category: ECategories?,
    @SerializedName("results")
    val itemList: List<Item>
)