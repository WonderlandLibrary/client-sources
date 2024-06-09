package com.leafclient.leaf.ui.hud.tabgui.selector

import com.leafclient.leaf.management.ui.Component
import com.leafclient.leaf.ui.hud.tabgui.panel.TabPanel
import com.leafclient.leaf.ui.hud.tabgui.tab.Tab

/**
 * Represents the selector of [panel] when rendering allowing us to navigate through.
 */
class TabSelector(val panel: TabPanel): Component() {

    /**
     * Contains the index of our actual selected tab
     */
    var selectedIndex = 0
        set(value) {
            field = value.coerceIn(panel.tabs.indices)
        }

    /**
     * Returns the current selected tab or allow you to move to another
     * [Tab] instance inside of this panel
     */
    var selectedTab: Tab
        get() = panel.tabs[selectedIndex]
        set(value) {
            val index = panel.tabs.indexOf(value)
            if(index in panel.tabs.indices)
                selectedIndex = index
        }

    /**
     * Moves the [TabSelector] with [movement]
     */
    fun move(movement: Movement) {
        selectedIndex += movement.indexOperation
    }

    enum class Movement(val indexOperation: Int) {
        UP(-1), // Going up means we go down in our list
        DOWN(1), // opposite if u have a brain
    }

}