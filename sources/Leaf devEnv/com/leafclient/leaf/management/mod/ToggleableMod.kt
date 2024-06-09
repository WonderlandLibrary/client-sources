package com.leafclient.leaf.management.mod

import com.leafclient.leaf.management.event.EventManager
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.contraint.contraint
import com.leafclient.leaf.management.setting.contraint.generic.ChoiceContraint
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.management.utils.Toggleable
import com.leafclient.leaf.utils.client.keyboard.Key
import org.lwjgl.input.Keyboard

abstract class ToggleableMod @JvmOverloads
constructor(
    name: String, category: Category, description: String = "", contributors: Array<String> = emptyArray(),
    defaultKey: Int = Keyboard.KEY_NONE,
): Mod(name, category, description, contributors), Toggleable
{

    val keybind by setting("Keybind", Key(defaultKey))
    val visible by setting("Visible", true)

    override var isRunning: Boolean = false
        set(value) {
            field = value
            if(field) {
                EventManager.register(this)
                onEnable()
                // Wacky solution
                settings
                    .mapNotNull { it.contraint<ChoiceContraint<*>>() }
                    .forEach { EventManager.register(it.setting.value) }
            } else {
                EventManager.unregister(this)
                onDisable()
                // Wacky solution
                settings
                    .mapNotNull { it.contraint<ChoiceContraint<*>>() }
                    .forEach { EventManager.unregister(it.setting.value) }
            }
        }

}