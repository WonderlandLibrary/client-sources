package com.leafclient.leaf.utils.render.font

import java.awt.Font

object FontManager {

    private val fontCache = mutableMapOf<FontData, TTFFontRenderer>()

    /**
     * Allows renderers to create a [TTFFontRenderer], this method
     * creates the [TTFFontRenderer] if it hasn't been created yet.
     */
    operator fun get(name: String, size: Float)
        = fontCache.getOrPut(FontData(name, size * 2F)) {
        TTFFontRenderer(
                javaClass.getResourceAsStream("/assets/minecraft/font/$name.ttf")?.let {
                    Font.createFont(Font.TRUETYPE_FONT, it).deriveFont(size * 2F)
                } ?: Font(name, Font.TRUETYPE_FONT, (size * 2F).toInt())
            )
        }

    /**
     * A data class used to manage our fonts
     */
    data class FontData(val name: String, val size: Float)

}