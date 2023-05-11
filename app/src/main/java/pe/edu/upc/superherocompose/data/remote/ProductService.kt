package pe.edu.upc.superherocompose.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("search")
    fun fetchByName(
        @Query("query") name: String,
        @Query("apiKey") apiKey: String = ApiClient.getAPIKEY(),
        @Query("number") number: Int = 10,
    ): Call<ProductResponse>
}