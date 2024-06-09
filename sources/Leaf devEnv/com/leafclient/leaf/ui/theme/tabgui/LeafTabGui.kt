package com.leafclient.leaf.ui.theme.tabgui

import com.leafclient.leaf.management.ui.Renderer
import com.leafclient.leaf.mod.modInstance
import com.leafclient.leaf.mod.visual.HUD
import com.leafclient.leaf.ui.Themes
import com.leafclient.leaf.ui.hud.tabgui.TabGui
import com.leafclient.leaf.ui.hud.tabgui.panel.TabPanel
import com.leafclient.leaf.utils.client.keyboard.Key
import kotlin.math.max

class LeafTabGui: Renderer<TabGui> {

    override fun update(c: TabGui) {
        c.area.y = 18F

        var height = 0F
        var width = 0F

        c.panels.removeIf { panel ->
            // Sets the panel position to its x & y
            panel.area.apply {
                x = width
                y = c.area.y
            }
            // Calls for update
            Themes.LEAF.rendererFor(panel)?.update(panel)
            // Update tab gui
            height = max(height, panel.area.height)
            width += panel.area.width + MARGIN_BETWEEN_PANEL
            // Removes if the tab is closed
            panel.state == TabPanel.State.CLOSED
        }
    }

    override fun render(c: TabGui) {
        // Simply draws everything
        c.panels.forEach {
            Themes.LEAF.rendererFor(it)?.render(it)
        }
    }

    override fun onInput(c: TabGui, e: Key, pressed: Boolean): Boolean {
        // Inputs the last one
        return c.panels.lastOrNull()?.let {
            Themes.LEAF.rendererFor(it)?.onInput(it, e, pressed)
        } ?: false
    }

    companion object {
        /**
         * The marge between two panels can be modified using this
         */
        const val MARGIN_BETWEEN_PANEL = 2F
    }

}