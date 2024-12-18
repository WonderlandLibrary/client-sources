package net.ccbluex.liquidbounce.features.module.modules.movement.flights.aac

import net.ccbluex.liquidbounce.CrossSine
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlyMode
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.PacketUtils
import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.network.play.server.S08PacketPlayerPosLook
import kotlin.math.cos
import kotlin.math.sin

class AAC520Fly : FlyMode("AAC5.2.0") {
    override fun onEnable() {
        if (mc.isSingleplayer) {
            CrossSine.hud.addNotification(Notification("Fly", "Use AAC5.2.0 Fly will crash single player", NotifyType.ERROR, 2000, 500))
            flight.state = false
            return
        }

        MovementUtils.resetMotion(true)

        PacketUtils.sendPacketNoEvent(C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, 1.7976931348623157E+308, mc.thePlayer.posZ, true))
    }

    override fun onUpdate(event: UpdateEvent) {
        mc.thePlayer.motionY = 0.003
        MovementUtils.resetMotion(false)
    }

    override fun onPacket(event: PacketEvent) {
        val packet = event.packet

        if (packet is S08PacketPlayerPosLook) {
            event.cancelEvent()
            mc.thePlayer.setPosition(packet.x, packet.y, packet.z)
            PacketUtils.sendPacketNoEvent(C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, packet.yaw, packet.pitch, false))
            val dist = 0.14
            val yaw = Math.toRadians(mc.thePlayer.rotationYaw.toDouble())
            mc.thePlayer.setPosition(mc.thePlayer.posX + -sin(yaw) * dist, mc.thePlayer.posY, mc.thePlayer.posZ + cos(yaw) * dist)
            PacketUtils.sendPacketNoEvent(C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false))
            PacketUtils.sendPacketNoEvent(C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, 1.7976931348623157E+308, mc.thePlayer.posZ, true))
        } else if (packet is C03PacketPlayer) {
            event.cancelEvent()
        }
    }
}