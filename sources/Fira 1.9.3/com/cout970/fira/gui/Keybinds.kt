package com.cout970.fira.gui

import com.cout970.fira.Config
import com.cout970.fira.MOD_NAME
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.settings.KeyConflictContext
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.lwjgl.input.Keyboard

object Keybinds {

    private var blockedKeys: MutableSet<IKeyBinding> = mutableSetOf()
    private val allKeybinds = mutableListOf<IKeyBinding>()

    fun init() {
        load(Config.CrystalPvP)
        load(Config.Debug)
        load(Config.AutoPilot)
        load(Config.Chat)
        load(Config.ElytraFly)
        load(Config.ElytraTweaks)
        load(Config.HudColors)
        load(Config.PacketFly)
        load(Config.ScreenInventory)
        load(Config.InventoryUtilities)
        load(Config.AutoFly)
        load(Config.YawLock)

        allKeybinds.forEach { it.init() }
    }

    private fun load(obj: Any) {
        obj.javaClass.declaredFields.forEach {
            if (ToggleKeybind::class.java.isAssignableFrom(it.type)) {
                val keybind = it.get(null) as ToggleKeybind
                allKeybinds.add(keybind)
            }
            if (PressKeybind::class.java.isAssignableFrom(it.type)) {
                val keybind = it.get(null) as PressKeybind
                allKeybinds.add(keybind)
            }
        }
    }

    @SubscribeEvent
    fun onTick(e: TickEvent.ClientTickEvent) {
        if (e.phase != TickEvent.Phase.START || e.type != TickEvent.Type.CLIENT) return

        val screen = Minecraft.getMinecraft().currentScreen

        blockedKeys.removeAll { key -> !key.isActive() }

        allKeybinds.forEach {
            if (it.worksOn(screen)) {
                it.update()
            }
        }
    }

    class MenuToggleKeybind(name: String, defaultKey: Int, action: () -> Unit) : ToggleKeybind(name, defaultKey, action) {
        override fun worksOn(screen: GuiScreen?): Boolean {
            return screen == null || screen is GuiContainer
        }
    }

    open class ToggleKeybind(name: String, defaultKey: Int, val action: () -> Unit) : KeyBinding("key.$name", defaultKey, MOD_NAME), IKeyBinding {

        override fun init() {
            ClientRegistry.registerKeyBinding(this)
        }

        override fun update() {
            if (this !in blockedKeys && isActive()) {
                action()
                blockedKeys.add(this)
            }
        }

        override fun isActive(): Boolean {
            return keyCode != Keyboard.KEY_ESCAPE
                    && keyCode > 0
                    && Keyboard.isKeyDown(keyCode)
                    && keyModifier.isActive(KeyConflictContext.IN_GAME)
        }
    }

    class PressKeybind(name: String, defaultKey: Int) : KeyBinding("key.$name", defaultKey, MOD_NAME), IKeyBinding {
        override fun init() {
            ClientRegistry.registerKeyBinding(this)
        }

        override fun update() {}

        override fun isActive() = keyCode != Keyboard.KEY_ESCAPE
                && keyCode > 0
                && Keyboard.isKeyDown(keyCode)
                && keyModifier.isActive(KeyConflictContext.IN_GAME)
    }

    interface IKeyBinding {
        fun init()

        fun update()

        fun isActive(): Boolean

        fun worksOn(screen: GuiScreen?): Boolean = screen == null
    }
}