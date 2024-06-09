package com.leafclient.leaf.ui.theme.tabgui.panel

import com.leafclient.leaf.management.ui.Renderer
import com.leafclient.leaf.management.ui.utils.Animation
import com.leafclient.leaf.management.ui.utils.Transitions
import com.leafclient.leaf.ui.Themes
import com.leafclient.leaf.ui.hud.tabgui.panel.TabPanel
import com.leafclient.leaf.utils.client.keyboard.Key
import com.leafclient.leaf.utils.math.Area
import com.leafclient.leaf.utils.render.RenderUtils.scissor
import com.leafclient.leaf.utils.render.RenderUtils.rect
import org.lwjgl.input.Keyboard
import java.awt.Color
import kotlin.math.max

class LeafTabPanel: Renderer<TabPanel> {

    private val openingAnimation = Animation(100L, Transitions.SIGMOID_HIGH)

    override fun update(c: TabPanel) {
        var width = 0F
        var height = 0F

        // Updates the animation
        when(c.state) {
            TabPanel.State.OPENING -> {
                openingAnimation.setRunning(true).setForward(true)
                if(openingAnimation.isOver)
                    c.state = TabPanel.State.OPENED
            }
            TabPanel.State.CLOSING -> {
                openingAnimation.setForward(false)
                if(openingAnimation.isOver)
                    c.state = TabPanel.State.CLOSED
            }
            else -> {}
        }

        val theme = Themes.LEAF
        // Updates the components and the size of our panel
        c.tabs.forEach { tab ->
            // Sets the tab position
            tab.area.apply {
                x = c.area.x
                y = c.area.y + height
            }
            // Updates it
            theme.rendererFor(tab)?.update(tab)

            // Updates the width & height of this panel
            width = max(width, tab.area.width)
            height += tab.area.height
        }
        // Sets the size of our selector
        c.selector.area.apply {
            x = c.selector.selectedTab.area.x
            y = c.selector.selectedTab.area.y
            this.width = c.area.width
            this.height = c.selector.selectedTab.area.height
        }
        // Updates the selector
        theme.rendererFor(c.selector)?.update(c.selector)

        // Final update
        c.area.apply {
            this.width = width
            this.height = height
        }
    }

    override fun render(c: TabPanel) {
        val theme = Themes.LEAF

        val progression = openingAnimation.value.toFloat()
        c.area.apply {
            x = c.area.x
            y = c.area.y
            width = c.area.width * progression
            height = c.area.height * progression
        }

        rect(c.area, Color(0, 0, 0, 160))
        theme.rendererFor(c.selector)?.render(c.selector)
        scissor(c.area) {
            c.tabs.forEach { tab ->
                theme.rendererFor(tab)?.render(tab)
            }
        }
    }

    override fun onInput(c: TabPanel, e: Key, pressed: Boolean): Boolean {
        return Themes.LEAF.rendererFor(c.selector)?.onInput(c.selector, e, pressed) ?: false
    }


}