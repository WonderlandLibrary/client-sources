package com.cout970.fira.modules

import com.cout970.fira.Config
import com.cout970.fira.util.Utils
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object PacketFly {
    var counter = 0f

    fun hud(): String = "PacketFly"

    @SubscribeEvent
    fun onTick(e: TickEvent.ClientTickEvent) {
        if (!Config.PacketFly.enable) return

        val player = Utils.mc.player ?: return

        if (e.phase == TickEvent.Phase.END) {
            if (!player.isElytraFlying) {
                if (counter < 1) {
                    counter += Config.PacketFly.cooldown
                    player.connection.sendPacket(CPacketPlayer.Position(player.posX, player.posY, player.posZ, false))
                    player.connection.sendPacket(CPacketPlayer.Position(player.posX, player.posY - 0.03, player.posZ, true))
                } else {
                    counter -= 1
                }
            }
        } else {
            if (Config.PacketFly.packetFlyAscending.isActive()) {
                player.motionY = Config.PacketFly.ascendingSpeed.toDouble()
            } else {
                player.motionY = -Config.PacketFly.fallSpeed.toDouble()
            }
        }
    }
}