package pe.edu.upc.superherocompose.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase: RoomDatabase(){
    abstract fun productDao(): ProductDao

    companion object {
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            INSTANCE = Room.databaseBuilder(
                context,
                ProductDatabase::class.java,
                "product2.db"
            )
                .allowMainThreadQueries()
                .build()
            return INSTANCE as ProductDatabase
        }
    }

}