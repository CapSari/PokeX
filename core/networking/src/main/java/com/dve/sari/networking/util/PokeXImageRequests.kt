package com.dve.sari.networking.util


fun createImageUrl(id: String, highQuality: Boolean = false) = buildString {
    if (highQuality) {
        append("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/")
    } else {
        append("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/")
    }
    append(id)
    append(".png")
}