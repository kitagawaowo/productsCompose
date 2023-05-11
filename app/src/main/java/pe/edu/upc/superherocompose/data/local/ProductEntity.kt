package pe.edu.upc.superherocompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Product")
class ProductEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val image: String,
    var favorite: Boolean
)