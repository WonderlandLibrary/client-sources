package com.leafclient.leaf.ui.hud.tabgui.tab.specific

import com.leafclient.leaf.management.setting.contraint.generic.BoundContraint
import com.leafclient.leaf.management.setting.contraint.generic.IncrementContraint
import com.leafclient.leaf.ui.hud.tabgui.tab.TabContainer
import com.leafclient.leaf.ui.hud.tabgui.tab.TabContraint

/**
 * A [TabContainer] implementation that implements the [TabContainer] class
 */
open class TabNumber<T: Number>(bound: BoundContraint<T>, val increment: IncrementContraint<T>): TabContraint<T>(bound)