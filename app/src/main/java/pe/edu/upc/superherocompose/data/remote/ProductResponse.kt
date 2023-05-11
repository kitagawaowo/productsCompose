package pe.edu.upc.superherocompose.data.remote

import pe.edu.upc.superherocompose.data.model.Product


data class ProductResponse(
    val products: List<Product>?
)