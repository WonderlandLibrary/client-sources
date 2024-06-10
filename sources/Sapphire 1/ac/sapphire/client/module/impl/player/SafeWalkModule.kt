package ac.sapphire.client.module.impl.player

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.misc.PostTickEvent
import ac.sapphire.client.event.events.player.PlayerMoveEvent
import ac.sapphire.client.event.events.player.SafeWalkEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.ext.player
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import ac.sapphire.client.property.impl.primitive.BooleanProperty
import net.minecraft.client.settings.KeyBinding
import net.minecraft.util.BlockPos
import net.minecraft.util.MathHelper

class SafeWalkModule : AbstractModule("safewalk", "SafeWalk", "", ModuleCategory.PLAYER) {
    private fun BlockPos.matches(other: BlockPos) = x == other.x && y == other.y && z == other.z

    private var shouldSneak = false
    private var lastBlock: BlockPos? = null

    private val autoCrouch by BooleanProperty("Auto Crouch", true)

    override fun onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.keyCode, false)
    }

    @Subscribe
    fun onTick(event: PostTickEvent) {
        if (!autoCrouch) {
            return
        }

        if (mc.theWorld != null) {
            // Make sure player is moving a decent bit and isn't flying
            if (player.capabilities.isFlying || player.prevPosY - player.posY > 0.4) {
                return
            }

            val blockUnder = BlockPos(
                MathHelper.floor_double(player.posX),
                MathHelper.floor_double(player.posY - 1.0),
                MathHelper.floor_double(player.posZ)
            )

            if (lastBlock == null || !blockUnder.matches(lastBlock!!)) {
                lastBlock = blockUnder
            }

            shouldSneak = mc.theWorld.isAirBlock(lastBlock)

            if (player.onGround) {
                if (player.isSneaking != shouldSneak) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.keyCode, shouldSneak)
                }
            } else {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.keyCode, false)
            }
        }
    }

    @Subscribe
    fun onSafewalk(event: SafeWalkEvent) {
        if (autoCrouch) {
            return
        }

        event.cancelled = !mc.gameSettings.keyBindJump.isPressed && player.onGround
    }
}