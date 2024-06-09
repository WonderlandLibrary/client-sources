package com.leafclient.leaf.ui.hud.tabgui.tab

import com.leafclient.leaf.management.setting.Setting
import com.leafclient.leaf.management.setting.contraint.Contraint
import com.leafclient.leaf.management.setting.contraint.generic.ChoiceContraint
import com.leafclient.leaf.management.utils.Toggleable
import com.leafclient.leaf.management.utils.label
import com.leafclient.leaf.ui.hud.tabgui.tab.TabContainer

/**
 * A [TabContainer] implementation used to work around a [Contraint]
 */
abstract class TabContraint<T: Any>(contraint: Contraint<T>): TabContainer<Contraint<T>>(contraint) {

    /**
     * Returns the concerned setting's instance
     */
    val setting: Setting<T>
        get() = value.setting

    /**
     * Returns the concerned setting's value
     */
    val settingValue: T
        get() = setting.value

    /**
     * Returns the [setting]'s label
     */
    override val label: String
        get() = setting.label

}