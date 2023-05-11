package pe.edu.upc.superherocompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("select * from Product where id=:id")
    fun fetchById(id: String): List<ProductEntity>

    @Query("select * from Product")
    fun fetchAll(): List<ProductEntity>
    @Insert
    fun insert(productEntity: ProductEntity)

    @Delete
    fun delete(productEntity: ProductEntity)
}