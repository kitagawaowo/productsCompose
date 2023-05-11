package pe.edu.upc.superherocompose.ui.screens.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pe.edu.upc.superherocompose.data.local.ProductDatabase
import pe.edu.upc.superherocompose.data.model.Product
import pe.edu.upc.superherocompose.data.remote.ApiClient
import pe.edu.upc.superherocompose.repository.ProductRepository

class ProductsViewModel (application: Application) : AndroidViewModel(application) {
    private val productService = ApiClient.productService()
    private val productDao = ProductDatabase.getInstance(application).productDao()
    private val productRepository = ProductRepository(productService, productDao)

    private var _products = productRepository.products
    val products get() = _products

    private var _favoriteProducts = productRepository.favoriteProducts
    val favoriteProducts get() = _favoriteProducts

    private var _name = MutableLiveData<String>()
    val name get() = _name

    fun update(name: String) {
        _name.value = name
    }
    fun fetchByName() {
        productRepository.fetchByName(name.value!!)
        _products.value = productRepository.products.value
    }
    fun fetchFavorites() {
        productRepository.fetchFavoriteProducts()
        _favoriteProducts.value = productRepository.favoriteProducts.value
    }

    fun insert(product: Product) {
        productRepository.insert(product)
    }

    fun delete(product: Product) {
        productRepository.delete(product)
        fetchFavorites()
    }
}