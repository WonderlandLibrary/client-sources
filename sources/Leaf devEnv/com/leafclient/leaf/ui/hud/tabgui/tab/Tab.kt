package com.leafclient.leaf.ui.hud.tabgui.tab

import com.leafclient.leaf.management.ui.Component
import com.leafclient.leaf.management.utils.Labelable
import com.leafclient.leaf.ui.hud.tabgui.panel.TabPanel

/**
 * A [Tab] is an object contained by a panel and displayed by the tab gui, it can
 * be empty and be completely passive in certain circumstances which forces us to
 * have this simple class.
 */
open class Tab(override val label: String): Component(), Labelable {

    var panel: TabPanel? = null

    /**
     * Returns whether this tab is the current selected
     */
    val isSelected: Boolean
        get() = panel?.selector?.selectedTab == this

}