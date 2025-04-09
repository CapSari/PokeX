package com.dve.sari.minipokedex.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dve.sari.minipokedex.R
import com.dve.sari.minipokedex.domain.model.PokemonListing
import com.dve.sari.minipokedex.ui.UrlImageView
import com.dve.sari.networking.util.createImageUrl
import java.util.Locale
import java.util.UUID

val sampleItems = List(
    20,
) {
    PokemonListing(
        id = UUID.randomUUID().toString(),
        name = "Pokemon $it",
        imageUrl = createImageUrl((it + 1).toString())
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var items by remember {
        mutableStateOf(listOf<PokemonListing>())
    }
    var searchQuery by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        when (state) {
            is CharactersUIState.Error -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.an_error_occurred),
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            }

            CharactersUIState.Loading -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            is CharactersUIState.Success -> {
                items = (state as CharactersUIState.Success).characters

                val filteredItems = items.filter { character ->
                    character.name.contains(searchQuery, ignoreCase = true)
                }

                Spacer(modifier = modifier.height(16.dp))

                CharactersContent(
                    characters = filteredItems,
                    searchText = searchQuery,
                    scrollState = scrollState,
                    onItemClicked = onItemClicked,
                    onSearchQueryChange = { searchQuery = it } // Update the search query
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharactersContent(
    modifier: Modifier = Modifier,
    characters: List<PokemonListing>,
    searchText: String,
    scrollState: ScrollState,
    onItemClicked: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 8.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokemon),
                contentDescription = "Header Image",
                modifier = modifier.size(100.dp)
            )
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = onSearchQueryChange,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        innerTextField()
                    }
                }
            )
        }

        Spacer(modifier = modifier.height(24.dp))

        if (characters.isEmpty()) {
            Text(text = "Sorry ! we dont have that character", style = MaterialTheme.typography.bodyLarge)
        } else {
            FlowRow(
                modifier = modifier.fillMaxWidth(),
                maxItemsInEachRow = 2,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                characters.forEach { character ->
                    Box(
                        modifier = modifier
                            .size(150.dp)
                            .clickable { onItemClicked(character.id) }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = modifier
                                .size(180.dp)
                                .padding(18.dp)
                        ) {
                            UrlImageView(
                                url = character.imageUrl,
                                imageSize = 120.dp,
                                modifier = modifier.fillMaxSize()
                            )
                        }

                        Text(
                            modifier = modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 8.dp),
                            text = character.name.replaceFirstChar { it.titlecase(Locale.getDefault()) },
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                        )
                    }
                }
            }
        }
    }
}
