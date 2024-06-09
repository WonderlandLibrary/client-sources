package com.leafclient.leaf.mod

import com.leafclient.leaf.command.ModCommand
import com.leafclient.leaf.event.client.KeyboardEvent
import com.leafclient.leaf.management.event.EventManager
import com.leafclient.leaf.management.mod.Mod
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.mod.combat.*
import com.leafclient.leaf.mod.movement.*
import com.leafclient.leaf.mod.visual.*
import com.leafclient.leaf.mod.world.*
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import java.lang.RuntimeException

object ModManager {

    /**
     * The collection that contains each [Mod] instance
     */
    val mods = listOf(
        HUD(), ESP(), StorageESP(), Animation(), Step(),
        KillAura(), Sprint(), Speed(), NoSlow(), ProtocolSupport(),
        Phase(), AntiBot(), NoVelocity(), Scaffold(), InvMove(),
        ChestStealer(), Blyat(), AutoHeal(), Terrain(), Criticals(), Flight(),
        AutoTotem()
    )

    /**
     * A map that contains the mod class as a key and its instance as a value
     */
    val classToMod = mods
            .associateBy { it::class.java }

    /**
     * Returns a mod instance from its class
     */
    inline fun <reified T: Mod> get(): T =
            classToMod[T::class.java] as T?
                    ?: throw RuntimeException("Attempted to get an unregisteredmod ${T::class.java}")

    @Subscribe
    val onKeyPress = Listener<KeyboardEvent.Press> { e ->
        mods
            .filterIsInstance<ToggleableMod>()
            .forEach {
                if(e.key == it.keybind) {
                    it.toggle()
                }
            }
    }

    init {
        EventManager.register(this)
        mods.forEach {
            if(it !is ToggleableMod) {
                EventManager.register(it)
            }
            ModCommand.`for`(it)
        }
    }

}