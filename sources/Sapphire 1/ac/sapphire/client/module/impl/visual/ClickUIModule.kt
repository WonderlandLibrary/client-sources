package ac.sapphire.client.module.impl.visual

import ac.sapphire.client.bind.impl.keyboard.ModuleToggleKeyboardBinding
import ac.sapphire.client.ext.mc
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import me.lemon.client.gui.click.ClickGui
import org.lwjgl.input.Keyboard

class ClickUIModule : AbstractModule("clickui", "ClickUI", "", ModuleCategory.VISUALS) {
    private val gui by lazy { ClickGui() }

    init {
        bind = ModuleToggleKeyboardBinding(Keyboard.KEY_RSHIFT, this)
    }

    override fun onEnable() {
        toggle(false)

        mc.displayGuiScreen(gui)
    }
}