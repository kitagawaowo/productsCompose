package pe.edu.upc.superherocompose.ui.screens.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pe.edu.upc.superherocompose.data.model.Product

@Composable
fun ProductsScreen(viewModel: ProductsViewModel) {
    Column {
        ProductsSearchBar(viewModel)
        ProductsList(viewModel)

    }
}

@Composable
fun FavoriteProductsScreen(viewModel: ProductsViewModel) {
    val favorites by viewModel.favoriteProducts.observeAsState(listOf())
    viewModel.fetchFavorites()

    LazyColumn {

        items(favorites) { product ->
            ProductCard(
                true,
                product,
                insertProduct = {
                    viewModel.insert(product)
                }, deleteProduct = {
                    viewModel.delete(product)
                })
        }
    }
}

@Composable
fun ProductsList(viewModel: ProductsViewModel) {
    val products by viewModel.products.observeAsState(listOf())
    LazyColumn {
        items(products) { product ->
            ProductCard(
                false,
                product,
                insertProduct = {
                    viewModel.insert(product)
                },
                deleteProduct = {
                    viewModel.delete(product)
                }
            )
        }
    }
}

@Composable
fun ProductCard(
    isFavoriteCard: Boolean,
    product: Product,
    insertProduct: () -> Unit,
    deleteProduct: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ){

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = product.id,
                    modifier = modifier.width(48.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                ProductImage(
                    product,
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                )
            }
            if (isFavoriteCard)
                FavoriteProductItem(product, deleteProduct)
            else
                ProductItem(product, insertProduct, deleteProduct)
        }
    }

}

@Composable
fun FavoriteProductItem(product: Product, deleteProduct: () -> Unit, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(9.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ){
        Column(
            modifier = modifier.weight(0.8f)
        ) {
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = modifier.width(8.dp))
        IconButton(
            modifier = modifier.weight(0.2f).padding(end = 8.dp),
            onClick = {
                deleteProduct()
            }) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = null,
            )
        }
    }
}
@Composable
fun ProductItem(product: Product, insertProduct: () -> Unit, deleteProduct: () -> Unit, modifier: Modifier = Modifier) {
    val isFavorite = remember { mutableStateOf(false) }
    isFavorite.value = product.favorite
    Spacer(modifier = modifier.width(9.dp))
    Row {
        Column(modifier = modifier.weight(0.8f)) {
            Text(text = product.name, fontWeight = FontWeight.Bold)
        }
        IconButton(
            modifier = modifier.weight(0.2f).padding(end = 8.dp),
            onClick = {
                if (isFavorite.value) {
                    deleteProduct()
                } else {
                    insertProduct()
                }
                isFavorite.value = !isFavorite.value
            }) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                tint = if (isFavorite.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ProductImage(product: Product, modifier: Modifier = Modifier) {
    AsyncImage(
        model = product.image,
        contentDescription = null,
        modifier = modifier
            .size(92.dp)
            .padding(vertical = 8.dp)
            .clip(shape = RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsSearchBar(viewModel: ProductsViewModel, modifier: Modifier = Modifier) {
    val name by viewModel.name.observeAsState("")
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        value = name,
        onValueChange = {
            viewModel.update(it)
        },
        leadingIcon = {
            Icon(Icons.Filled.Search, null)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                viewModel.fetchByName()
                focusManager.clearFocus()

            }
        )
    )

}
