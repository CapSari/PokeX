package com.dve.sari.minipokedex.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import java.util.Locale


@Composable
fun InfoScreen(
    modifier: Modifier = Modifier,
    id: String,
    navigateBack: () -> Unit
) {
    val viewModel = hiltViewModel<InfoViewModel>()
    val uiState by viewModel.uIState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = id) {
        viewModel.getDetails(id)
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = uiState) {
            is CharacterDetailsUIState.Error -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = modifier
                            .background(Color.Red)
                            .clickable { viewModel.getDetails(id) },
                        text = "An error occurred. Tap to retry."
                    )
                }
            }

            CharacterDetailsUIState.Loading -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = modifier.size(48.dp))
                    Text(text = "Loading Pokémon details...")
                }
            }

            CharacterDetailsUIState.Idle -> {
            }

            is CharacterDetailsUIState.Success -> {
                val character = state.pokemon

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(character.pokemonColor.colorValue.copy(alpha = .5F))
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .weight(.45F),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            modifier = modifier
                                .align(Alignment.TopStart)
                                .padding(start = 16.dp)
                                .clickable { navigateBack() }
                        )
                        Column(
                            modifier = modifier
                                .align(Alignment.Center)
                                .padding(bottom = 16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            UrlImageView(
                                url = character.imageUrl,
                                imageSize = 160.dp,
                                scale = ContentScale.Fit
                            )
                        }

                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .padding(start = 8.dp, bottom = 8.dp, top = 8.dp)
                        ) {
                            Text(
                                text = character.name.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                },
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Spacer(modifier = modifier.width(8.dp))

                            Row(
                                modifier = modifier.fillMaxWidth()
                            ) {
                                character.types.forEach {
                                    InputChip(
                                        selected = false,
                                        onClick = { },
                                        label = {
                                            Text(
                                                text = it.replaceFirstChar {
                                                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                                },
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        },
                                        shape = RoundedCornerShape(percent = 80),
                                        colors = InputChipDefaults.inputChipColors(
                                            containerColor = MaterialTheme.colorScheme.surface,
                                            labelColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                    Spacer(modifier = modifier.width(16.dp))
                                }
                            }
                        }
                    }

                    ElevatedCard(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(.65F),
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .verticalScroll(scrollState)
                                .padding(top = 20.dp, start = 16.dp, end = 16.dp),
                        ) {
                            Text(
                                text = "About",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = modifier.height(8.dp))
                            Text(
                                text = character.about,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = modifier.height(16.dp))

                            Card(modifier = modifier.fillMaxWidth()) {
                                Column(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(all = 16.dp)
                                ) {
                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "Weight:")
                                        Spacer(modifier = modifier.width(8.dp))
                                        Text(
                                            text = character.weight,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }

                                    Spacer(modifier = modifier.height(8.dp))

                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "Height:")
                                        Spacer(modifier = modifier.width(8.dp))
                                        Text(
                                            text = character.height,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }

                                    Spacer(modifier = modifier.height(16.dp))

                                    Text(text = "Abilities")
                                    Spacer(modifier = modifier.height(8.dp))

                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        character.abilities.forEach { (name, selected) ->
                                            InputChip(
                                                selected = selected,
                                                onClick = { },
                                                label = {
                                                    Text(
                                                        text = name,
                                                        style = MaterialTheme.typography.bodySmall
                                                    )
                                                },
                                                shape = RoundedCornerShape(percent = 80),
                                                colors = InputChipDefaults.inputChipColors(
                                                    selectedContainerColor = character.pokemonColor.colorValue.copy(
                                                        alpha = .5F
                                                    )
                                                )
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = modifier.height(16.dp))
                            Text(
                                text = "Stats",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = modifier.height(8.dp))
                            character.stats.forEach { nameAndValue ->
                                Row(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp, bottom = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val statsValue = nameAndValue.second.toFloat() / 100
                                    Text(
                                        modifier = modifier.weight(.5F),
                                        text = nameAndValue.first.replaceFirstChar {
                                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                        },
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Box(modifier = modifier.weight(.6F)) {
                                        LinearProgressIndicator(
                                            progress = statsValue,
                                            modifier = modifier
                                                .fillMaxWidth()
                                                .height(16.dp),
                                            strokeCap = StrokeCap.Round
                                        )
                                        Text(
                                            text = nameAndValue.second.toString(),
                                            modifier = modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(end = 4.dp),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UrlImageView(
    url: String,
    imageSize: Dp = 150.dp,
    modifier: Modifier = Modifier,
    scale: ContentScale = ContentScale.Crop
) {
    Box(
        modifier = modifier
            .size(imageSize)
            .background(Color.Gray)
    ) {
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = scale
        )
    }
}
