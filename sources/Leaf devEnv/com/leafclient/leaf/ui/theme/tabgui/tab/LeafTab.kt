package com.leafclient.leaf.ui.theme.tabgui.tab

import com.leafclient.leaf.management.ui.Renderer
import com.leafclient.leaf.management.ui.utils.Animation
import com.leafclient.leaf.management.ui.utils.Transitions
import com.leafclient.leaf.ui.hud.tabgui.tab.Tab
import com.leafclient.leaf.utils.render.font.FontManager
import java.awt.Color

open class LeafTab<T: Tab>: Renderer<T> {

    val textAnimation = Animation(130L, Transitions.SIGMOID).setForward(false)

    override fun update(c: T) {
        // Updates the tab size
        c.area.apply {
            width = TEXT_MARGIN + font.getWidth(c.label) + RIGHT_PADDING
            height = font.getHeight(c.label)
        }
        textAnimation.setRunning(true).setForward(c.isSelected)
    }

    override fun render(c: T) {
        val font = if(c.panel?.isCurrent == false && c.isSelected) font_bold else font

        font.drawStringWithShadow(
            c.label,
            c.area.x + TEXT_MARGIN + TEXT_ANIMATION_MARGIN * textAnimation.value.toFloat(),
            c.area.y,
            Color.WHITE.rgb
        )
    }

    companion object {
        /**
         * The padding at the right of the tab
         */
        const val RIGHT_PADDING = 10F

        /**
         * The margin at he left
         */
        const val TEXT_MARGIN = 2F

        /**
         * The margin at he left
         */
        const val TEXT_ANIMATION_MARGIN = 3F

        val font = FontManager["SourceSansPro-Light", 12F]
        val font_bold = FontManager["SourceSansPro-Regular", 12F]
    }

}