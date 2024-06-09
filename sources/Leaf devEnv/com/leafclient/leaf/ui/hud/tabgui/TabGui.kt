package com.leafclient.leaf.ui.hud.tabgui

import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.ui.Component
import com.leafclient.leaf.ui.hud.tabgui.factory.TabFactories
import com.leafclient.leaf.ui.hud.tabgui.panel.TabPanel

class TabGui: Component() {

    /**
     * The list of our panels with the default one at first.
     */
    val panels = mutableListOf(
        TabPanel(Category.values().map { TabFactories.tabFor(it) })
    )

    /**
     * Opens specified [panel]
     */
    fun open(panel: TabPanel) {
        if(panel.tabs.isEmpty())
            return

        panel.tabGui = this
        panels.add(panel)
    }

    /**
     * Opens specified [panel]
     */
    operator fun plusAssign(panel: TabPanel)
        = open(panel)

    /**
     * Removes specified [panel]
     */
    fun close(panel: TabPanel) {
        if(panels.size > 1)
            panel.state = TabPanel.State.CLOSING
    }

    /**
     * Removes specified [panel]
     */
    operator fun minusAssign(panel: TabPanel)
            = close(panel)

    init {
        panels.forEach { it.tabGui = this }
    }

}