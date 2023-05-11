package pe.edu.upc.superherocompose.data.model

import com.google.gson.annotations.SerializedName

data class Product (
    val id: String,
    @SerializedName("title")
    val name: String,
    val image: String,
    var favorite: Boolean
)