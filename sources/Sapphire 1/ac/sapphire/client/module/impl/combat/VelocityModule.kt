package ac.sapphire.client.module.impl.combat

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.network.PacketReceiveEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.ext.requireMixin
import ac.sapphire.client.mixin.packet.IVelocityPacketAccessor
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import ac.sapphire.client.property.impl.primitive.number.FloatProperty
import net.minecraft.network.play.server.S12PacketEntityVelocity
import kotlin.contracts.ExperimentalContracts

class VelocityModule : AbstractModule("velocity", "Velocity", "", ModuleCategory.COMBAT) {
    private val horizontal by FloatProperty("Horizontal", 100F, 0F..100F)
    private val vertical by FloatProperty("Vertical", 100F, 0F..100F)

    @ExperimentalContracts
    @Subscribe
    fun onPacketRecv(event: PacketReceiveEvent) {
        val packet = event.packet
        if (packet is S12PacketEntityVelocity && packet.entityID == mc.thePlayer.entityId) {
            if (horizontal == 0F && vertical == 0F) {
                event.isCancelled = true
                return
            }

            requireMixin<IVelocityPacketAccessor>(packet)
            packet.setMotionX((packet.motionX * (horizontal / 100)).toInt())
            packet.setMotionY((packet.motionY * (horizontal / 100)).toInt())
            packet.setMotionZ((packet.motionZ * (horizontal / 100)).toInt())
        }
    }
}
