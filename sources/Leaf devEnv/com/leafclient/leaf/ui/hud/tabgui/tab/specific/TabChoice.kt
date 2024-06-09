package com.leafclient.leaf.ui.hud.tabgui.tab.specific

import com.leafclient.leaf.management.setting.contraint.generic.ChoiceContraint
import com.leafclient.leaf.ui.hud.tabgui.tab.TabContainer
import com.leafclient.leaf.ui.hud.tabgui.tab.TabContraint

/**
 * A [TabContainer] implementation that implements the [TabContainer] class
 */
open class TabChoice<T: Any>(contraint: ChoiceContraint<T>): TabContraint<T>(contraint)