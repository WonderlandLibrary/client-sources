package ac.sapphire.client.module.impl.combat.autoclicker

import ac.sapphire.client.ext.mc
import ac.sapphire.client.ext.requireMixin
import ac.sapphire.client.mixin.bind.IKeyBindAccessor
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import ac.sapphire.client.property.impl.primitive.BooleanProperty
import ac.sapphire.client.property.impl.primitive.number.IntProperty
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.util.MovingObjectPosition
import java.security.SecureRandom
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
class AutoClickerModule : AbstractModule("autoclicker", "AutoClicker", "", ModuleCategory.COMBAT) {
    private val rng = SecureRandom()

    private val cps by IntProperty("CPS", 10, 5..20)
    private val breakBlocks by BooleanProperty("Break Blocks", false)

    val removeHitCooldown by BooleanProperty("Remove Hit Cooldown", false)

    private val thread: Thread
        get() = Thread {
            while (isToggled) {
                Thread.sleep(1)

                if (!isHoldingLeftClick()) {
                    continue
                }

                if (breakBlocks && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    val attk = mc.gameSettings.keyBindAttack
                    requireMixin<IKeyBindAccessor>(attk)
                    attk.setPressed(true)
                    continue
                }

                if ((mc.currentScreen == null || mc.currentScreen is GuiChest)) {
                    Thread.sleep(randomization(cps))
                    postMessage(0x0201)
                    Thread.sleep(randomization(cps))
                    postMessage(0x0202)
                }
            }
        }

    override fun onEnable() {
        thread.start()
    }

    private fun rng(min: Int, max: Int): Long {
        return (rng.nextInt(max - min + 1) + min).toLong()
    }

    private fun randomization(cps: Int): Long {
        val numerator = rng(450, 500)
        return if (rng.nextInt(100) < 75) numerator / rng(cps - 1, cps + 1) else numerator / rng(cps - 2, cps + 2)
    }
}