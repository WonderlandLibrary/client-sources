package com.leafclient.leaf.ui.hud.tabgui.factory

import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.management.setting.SettingContainer
import com.leafclient.leaf.management.setting.contraint.contraint
import com.leafclient.leaf.management.setting.contraint.generic.BoundContraint
import com.leafclient.leaf.management.setting.contraint.generic.ChoiceContraint
import com.leafclient.leaf.management.setting.contraint.generic.IncrementContraint
import com.leafclient.leaf.management.utils.Toggleable
import com.leafclient.leaf.management.utils.label
import com.leafclient.leaf.mod.ModManager
import com.leafclient.leaf.ui.hud.tabgui.tab.Tab
import com.leafclient.leaf.ui.hud.tabgui.tab.TabContainer
import com.leafclient.leaf.ui.hud.tabgui.tab.specific.TabChoice
import com.leafclient.leaf.ui.hud.tabgui.tab.specific.TabNumber
import com.leafclient.leaf.ui.hud.tabgui.tab.specific.TabToggleable
import com.leafclient.leaf.utils.client.setting.ToggleableContraint

object TabFactories {

    private val factories = mapOf<Class<*>, TabFactory<Any>>(
        Category::class.java to { TabContainer(it as Category) },
        ChoiceContraint::class.java to { TabChoice(it as ChoiceContraint<*>) },
        Setting::class.java to {
            it as Setting<Any>
            if(it.value is Number) {
                val bound = it.contraint<BoundContraint<Number>>()
                val increment = it.contraint<IncrementContraint<Number>>()
                if(bound != null && increment != null)
                    TabNumber(bound, increment)
                else
                    TabContainer(it)
            } else if(it.value is Boolean) {
                tabFor(it.contraint<ToggleableContraint>()!!)
            } else {
                val choice = it.contraint<ChoiceContraint<*>>()
                if(choice != null)
                    tabFor(choice)
                else
                    TabContainer(it)
            }
        },
        Toggleable::class.java to { TabToggleable(it as Toggleable) }
    )

    /**
     * Contains the explorers that allows us to look for object mappable into
     * a map.
     */
    private val explorers = mapOf<Class<*>, TabExplorer<Any>>(
        SettingContainer::class.java to { (it as SettingContainer).settings },
        Category::class.java to { ModManager.mods.filter { mod ->
            mod.category == (it as Category) }
        },
    )

    /**
     * Returns a [TabFactory] implementation for [o] if one is found.
     */
    fun tabFor(o: Any): Tab = factories[o::class.java]?.invoke(o)
        ?: factories.mapNotNull { (oClass, factory) ->
            if(oClass.isAssignableFrom(o::class.java)) factory.invoke(o) else null
        }.firstOrNull()
        ?: Tab(o.label ?: o.toString())

    /**
     * Returns a [TabExplorer] implementation for [o] if one is found.
     */
    fun explore(o: Any): List<Any> = explorers[o::class.java]?.invoke(o)
        ?: explorers.mapNotNull { (oClass, explorer) ->
            if(oClass.isAssignableFrom(o::class.java)) explorer.invoke(o) else null
        }.firstOrNull()
        ?: emptyList()

}

typealias TabFactory<T> = (T) -> Tab

typealias TabExplorer<T> = (T) -> List<Any>