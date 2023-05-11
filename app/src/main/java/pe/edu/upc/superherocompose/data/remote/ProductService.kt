package pe.edu.upc.superherocompose.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("search")
    fun fetchByName(
        @Query("query") name: String,
        @Query("apiKey") apiKey: String = "a4bfa10d0f5c484ab7414544617a55f9",
        @Query("number") number: Int = 10,
    ): Call<ProductResponse>
}