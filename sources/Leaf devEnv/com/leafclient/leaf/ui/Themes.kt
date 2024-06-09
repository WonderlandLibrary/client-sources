package com.leafclient.leaf.ui

import com.leafclient.leaf.management.ui.Theme
import com.leafclient.leaf.ui.hud.ModList
import com.leafclient.leaf.ui.hud.Watermark
import com.leafclient.leaf.ui.hud.tabgui.TabGui
import com.leafclient.leaf.ui.hud.tabgui.panel.TabPanel
import com.leafclient.leaf.ui.hud.tabgui.selector.TabSelector
import com.leafclient.leaf.ui.hud.tabgui.tab.Tab
import com.leafclient.leaf.ui.hud.tabgui.tab.TabContainer
import com.leafclient.leaf.ui.hud.tabgui.tab.specific.TabChoice
import com.leafclient.leaf.ui.hud.tabgui.tab.specific.TabNumber
import com.leafclient.leaf.ui.hud.tabgui.tab.specific.TabToggleable
import com.leafclient.leaf.ui.theme.LeafModList
import com.leafclient.leaf.ui.theme.LeafWatermark
import com.leafclient.leaf.ui.theme.tabgui.LeafTabGui
import com.leafclient.leaf.ui.theme.tabgui.panel.LeafTabPanel
import com.leafclient.leaf.ui.theme.tabgui.selector.LeafTabSelector
import com.leafclient.leaf.ui.theme.tabgui.tab.LeafTab
import com.leafclient.leaf.ui.theme.tabgui.tab.LeafTabFocusable
import com.leafclient.leaf.ui.theme.tabgui.tab.LeafTabToggleable

object Themes {

    val LEAF = Theme("Leaf",
        ModList::class.java to { LeafModList() },
        Watermark::class.java to { LeafWatermark() },
        TabGui::class.java to { LeafTabGui() },
        TabPanel::class.java to { LeafTabPanel() },
        Tab::class.java to { LeafTab<Tab>() },
        TabContainer::class.java to { LeafTab<TabContainer<*>>() },
        TabToggleable::class.java to { LeafTabToggleable() },
        TabChoice::class.java to { LeafTabFocusable<TabChoice<*>>() },
        TabNumber::class.java to { LeafTabFocusable<TabNumber<*>>() },
        TabSelector::class.java to { LeafTabSelector() },
    )

}