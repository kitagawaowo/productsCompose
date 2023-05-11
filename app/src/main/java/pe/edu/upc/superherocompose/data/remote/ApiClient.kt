package pe.edu.upc.superherocompose.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.spoonacular.com/food/products/"
    private const val API_KEY = "a4bfa10d0f5c484ab7414544617a55f9"
    fun productService(): ProductService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ProductService::class.java)
    }
    fun getAPIKEY(): String {
        return API_KEY
    }
}