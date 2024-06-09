package com.leafclient.leaf.mod.combat

import com.leafclient.leaf.event.game.network.PacketEvent
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.network.play.server.SPacketEntityVelocity
import net.minecraft.network.play.server.SPacketExplosion
import net.minecraft.util.math.Vec3d

class NoVelocity: ToggleableMod("NoVelocity", Category.FIGHT) {

    private var hFactor by setting("Horizontal factor", 0.0) {
        bound(0.0, 1.0)
        increment(0.05)
    }
    private var vFactor by setting("Vertical factor", 0.0) {
        bound(0.0, 1.0)
        increment(0.05)
    }

    private val shouldCancel: Boolean
        get() = hFactor == 0.0 && vFactor == 0.0

    /**
     * Cancels the [SPacketEntityVelocity] or [SPacketExplosion] if required,
     * otherwise modifies the motion inside.
     */
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @Subscribe
    val onReceivePacket = Listener<PacketEvent.Receive> { e ->
        val packet = e.packet
        if(packet is SPacketEntityVelocity && packet.entityID == mc.player.entityId) {
            if(shouldCancel) {
                e.isCancelled = true
            } else {
                e.packet = SPacketEntityVelocity(
                    mc.player.entityId,
                    packet.motionX / 8000.0 * hFactor,
                    packet.motionY / 8000.0 * vFactor,
                    packet.motionZ / 8000.0 * hFactor
                )
            }
        } else if(packet is SPacketExplosion) {
            if(shouldCancel || (packet.motionX == 0F && packet.motionY == 0F && packet.motionZ == 0F)) {
                e.packet = SPacketExplosion(packet.x, packet.y, packet.z, packet.strength, packet.affectedBlockPositions, null)
            } else {
                e.packet = SPacketExplosion(packet.x, packet.y, packet.z, packet.strength, packet.affectedBlockPositions,
                    Vec3d(packet.motionX * hFactor, packet.motionY * vFactor, packet.motionZ * hFactor)
                )
            }
        }
    }

    override val suffix: String
        get() = "H: $hFactor V: $vFactor"

}