package com.leafclient.leaf.mod.movement

import com.leafclient.leaf.event.game.input.KeyboardTickEvent
import com.leafclient.leaf.extension.isKeyPressed
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.gui.inventory.GuiContainer
import org.lwjgl.input.Keyboard

class InvMove: ToggleableMod("InvMove", Category.MOVEMENT) {

    private val keys = arrayOf(
        mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack,
        mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight,
        mc.gameSettings.keyBindJump
    )

    /**
     * Modifies the pressed statue of keys in inventory to allow movement input
     */
    @Subscribe
    val onKeyboardTick = Listener<KeyboardTickEvent> { e ->
        if(mc.currentScreen is GuiContainer) {
            keys
                .filter { it.keyCode >= 0 }
                .forEach {
                    it.isKeyPressed = Keyboard.isKeyDown(it.keyCode)
                }
        } else if(mc.currentScreen == null) {
            keys
                .filter { !Keyboard.isKeyDown(it.keyCode) }
                .forEach { it.isKeyPressed =  false }
        }
    }

    init {
        isRunning = true
    }

}