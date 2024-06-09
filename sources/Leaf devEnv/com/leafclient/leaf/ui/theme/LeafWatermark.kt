package com.leafclient.leaf.ui.theme

import com.leafclient.leaf.management.ui.Renderer
import com.leafclient.leaf.management.ui.utils.Animation
import com.leafclient.leaf.management.ui.utils.Transitions
import com.leafclient.leaf.ui.hud.Watermark
import com.leafclient.leaf.utils.render.font.FontManager
import java.awt.Color

class LeafWatermark: Renderer<Watermark> {

    private val font = FontManager["SourceSansPro-ExtraLight", 16F]
    private val smallFont = FontManager["SourceSansPro-ExtraLight", 8F]

    override fun render(c: Watermark) {
        font.drawStringWithShadow(c.clientName, 1F, 0F,
            Color(151, 206, 104, 180).rgb
        )
        val clientNameWidth = font.getWidth(c.clientName)
        smallFont.drawStringWithShadow(c.clientVersion, 1F + clientNameWidth + 1F, 0F,
            Color(191, 246, 144, 180).rgb
        )
    }


}