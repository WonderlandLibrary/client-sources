package com.leafclient.leaf.ui.hud.tabgui.panel

import com.leafclient.leaf.management.ui.Component
import com.leafclient.leaf.ui.hud.tabgui.TabGui
import com.leafclient.leaf.ui.hud.tabgui.tab.Tab
import com.leafclient.leaf.ui.hud.tabgui.selector.TabSelector

/**
 * Represents a panel to the tab gui, it contains various [Tab] instances and
 * manage them.
 */
class TabPanel(
    val tabs: List<Tab> = mutableListOf(),
    var state: State = State.OPENING
): Component() {

    var tabGui: TabGui? = null

    /**
     * The selector
     */
    val selector = TabSelector(this)

    /**
     * Returns whether this panel is the current one or not.
     */
    val isCurrent: Boolean
        get() = tabGui?.panels?.indexOf(this) == tabGui?.panels?.size?.minus(1)

    init {
        tabs.forEach { it.panel = this }
    }

    enum class State {
        OPENING,
        OPENED,
        CLOSING,
        CLOSED;
    }

}