package com.leafclient.leaf.ui.hud.tabgui.tab.specific

import com.leafclient.leaf.management.utils.Toggleable
import com.leafclient.leaf.ui.hud.tabgui.tab.TabContainer

/**
 * A [TabContainer] implementation that implements the [TabContainer] class
 */
class TabToggleable<T: Toggleable>(toggleable: T): TabContainer<T>(toggleable), Toggleable {

    override var isRunning: Boolean
        get() = value.isRunning
        set(runningState) {
            value.isRunning = runningState
        }

}