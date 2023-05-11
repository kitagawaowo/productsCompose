package pe.edu.upc.superherocompose.repository

import androidx.lifecycle.MutableLiveData
import pe.edu.upc.superherocompose.data.local.ProductDao
import pe.edu.upc.superherocompose.data.local.ProductEntity
import pe.edu.upc.superherocompose.data.model.Product
import pe.edu.upc.superherocompose.data.remote.ProductResponse
import pe.edu.upc.superherocompose.data.remote.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository (
    private val productService: ProductService,
    private val productDao: ProductDao
) {
    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products get() = _products

    private val _favoriteProducts = MutableLiveData<List<Product>>(emptyList())
    val favoriteProducts get() = _favoriteProducts


    fun fetchByName(name: String) {
        val fetchByName = productService.fetchByName(name = name)
        fetchByName.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.products == null) {
                        _products.value = emptyList()
                    } else {
                        _products.value = response.body()!!.products!!
                        for (product in _products.value!!) {
                            product.favorite = productDao.fetchById(product.id).isNotEmpty()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun fetchFavoriteProducts() {
        productDao.fetchAll().let {
            _favoriteProducts.value = it.map { productEntity ->
                Product(
                    productEntity.id,
                    productEntity.name,
                    productEntity.image,
                    productEntity.favorite
                )
            }
        }
    }
    fun insert(product: Product) {
        val productEntity = ProductEntity(
            product.id,
            product.name,
            product.image,
            product.favorite
        )
        product.favorite = true
        productDao.insert(productEntity)
    }

    fun delete(product: Product) {
        val productEntity = ProductEntity(
            product.id,
            product.name,
            product.image,
            product.favorite
        )
        product.favorite = false
        productDao.delete(productEntity)
    }

}