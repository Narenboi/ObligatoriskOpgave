package com.example.obligatoriskopgave.models

import java.io.Serializable

data class Beer(

    val id: Int,
    val user: String?,
    val brewery: String?,
    val name: String?,
    val style: String?,
    val abv: Double,
    val volume: Double,
    val pictureUrl: String?,
    val howMany: Int
): Serializable {
    override fun toString(): String {
        val userText = user ?: "Unknown"
        val breweryText = brewery ?: "Unknown"
        val nameText = name ?: "Unknown"
        val styleText = style ?: "Unknown"
        val pictureUrlText = pictureUrl ?: "Unknown"
        return "$nameText: $styleText: $abv%: $volume ml: $howMany"
    }

}