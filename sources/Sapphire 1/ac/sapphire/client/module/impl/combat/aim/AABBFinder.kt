package ac.sapphire.client.module.impl.combat.aim

import ac.sapphire.client.event.events.render.PostRenderPlayerEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB

class AABBFinder(private val event: PostRenderPlayerEvent, entity: EntityPlayer) {
    private val radius: Double = 0.1
    private val eyeHeight: Double
    private val d: Double
    private val e: Double
    private val f: Double
    private val g: Double
    private val h: Double
    private val i: Double

    init {
        eyeHeight = entity.getEyeHeight().toDouble()
        d = entity.width / 2.0 + 0.1
        e = entity.width + 0.2
        f = entity.height + 0.2
        g = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.tick.toDouble()
        h = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.tick.toDouble()
        i = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.tick.toDouble()
    }

    fun between(a: AABBFinder): AxisAlignedBB {
        val b = a(event.x - a.g, g, a.g - d, e, radius)
        val c = a(event.y - a.h, h + eyeHeight, a.h - 0.1, f, this.radius)
        val d = a(event.z - a.i, i, a.i - d, e, this.radius)
        return AxisAlignedBB(b - this.radius, c - this.radius, d - this.radius, b + this.radius, c + this.radius, d + this.radius)
    }

    private fun a(a: Double, b: Double, c: Double, d: Double, e: Double): Double {
        if (b <= c + e) {
            return c + a + e
        }
        return if (b >= c + d - e) {
            c + d + a - e
        } else b + a
    }
}
