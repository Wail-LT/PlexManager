package com.esilvwl.dataClasses

import com.esilvwl.plexmanager.enums.EItemTypes

//Data class because only used to store data
data class Item (
    val id : Int,
    val poster_path : String?,
    var type: EItemTypes?
)