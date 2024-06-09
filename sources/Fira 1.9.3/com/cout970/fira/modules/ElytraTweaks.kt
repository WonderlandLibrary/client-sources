package com.cout970.fira.modules

import com.cout970.fira.Config
import com.cout970.fira.util.Utils
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.init.Items
import net.minecraft.network.play.client.CPacketEntityAction
import net.minecraft.util.EnumHand
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object ElytraTweaks {

    fun hud(): String {
        return "ElytraTweaks"
    }

    @SubscribeEvent
    fun onTick(e: TickEvent.ClientTickEvent) {
        if (e.phase != TickEvent.Phase.START) return

        if (!Config.ElytraTweaks.autoEnableElytras) return
        if (!ElytraFly.canFly()) return
        val player = Minecraft.getMinecraft().player ?: return

        if (player.foodStats.foodLevel > 6.0f
                && !player.onGround
                && player.motionY < 0.0
                && !player.isElytraFlying
                && !player.capabilities.isFlying) {

            Utils.withCooldown("openElytras", Config.ElytraTweaks.autoEnableCooldown) {
                player.connection.sendPacket(CPacketEntityAction(player, CPacketEntityAction.Action.START_FALL_FLYING))
            }
        }
    }

    @SubscribeEvent
    fun onRightClick(e: PlayerInteractEvent.RightClickItem) {
        if (!Config.ElytraTweaks.enableEasyTakeOff) return

        val player = e.entityPlayer as? EntityPlayerSP ?: return

        // Ignorar si el jugador no tiene elytra
        if (!ElytraFly.hasElytra(player)) return

        // o no esta volando
        if (player.isElytraFlying) return

        // Ignorar si el jugador no tiene cohetes en la mano
        if (player.heldItemMainhand.item != Items.FIREWORKS || e.hand != EnumHand.MAIN_HAND) return

        if (player.onGround) player.jump()

        player.connection.sendPacket(CPacketEntityAction(player, CPacketEntityAction.Action.START_FALL_FLYING))
    }

    fun onOpenElytra(): Boolean {
        return Config.ElytraTweaks.disableVanillaBehaviour
    }
}