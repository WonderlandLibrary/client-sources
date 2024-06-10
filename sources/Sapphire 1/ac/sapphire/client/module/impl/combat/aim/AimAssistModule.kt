package ac.sapphire.client.module.impl.combat.aim

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.game.UpdateEvent
import ac.sapphire.client.event.events.render.PostRenderPlayerEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.ext.player
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import ac.sapphire.client.module.impl.combat.autoclicker.isHoldingLeftClick
import ac.sapphire.client.property.impl.primitive.BooleanProperty
import ac.sapphire.client.property.impl.primitive.number.FloatProperty
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.MovingObjectPosition
import kotlin.contracts.ExperimentalContracts
import kotlin.math.atan2

class AimAssistModule : AbstractModule("aimassist", "AimAssist", "", ModuleCategory.COMBAT) {
    private val distance by FloatProperty("Distance", 4F, 2.5F..6F)
    private val speed by FloatProperty("Speed", 10F, 1F..100F)
    private val fov by FloatProperty("FOV", 60F, 5F..180F)
    private val requireClick by BooleanProperty("Require Click", true)
    private val breakBlocks by BooleanProperty("Break Blocks", false)

    private var entityPlayer: EntityPlayer? = null
    private var aimAt: AxisAlignedBB? = null

    @Subscribe
    fun onPostRenderPlayer(event: PostRenderPlayerEvent) {
        val enemy = findEnemy()
        if (enemy == null) {
            entityPlayer = null
            aimAt = null
            return
        }

        entityPlayer = enemy

        val entity = event.entity
        if (entityPlayer == entity) {
            val playerAABB = AABBFinder(event, mc.thePlayer)
            val enemyAABB = AABBFinder(event, entity)
            aimAt = playerAABB.between(enemyAABB)
        }
    }

    @Subscribe
    @ExperimentalContracts
    fun onUpdate(event: UpdateEvent) {
        if (aimAt == null || entityPlayer == null) {
            return
        }

        if (breakBlocks && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            return
        }

        val rots = RotationUtil.getRandomizedRotations(aimAt!!, entityPlayer!!, speed)
        mc.thePlayer.rotationYaw = rots[0]
        mc.thePlayer.rotationPitch = rots[1]
    }

    private fun findEnemy(): EntityPlayer? {
        if (requireClick && !isHoldingLeftClick()) {
            return null
        }

        if (mc.currentScreen != null) {
            return null
        }

        for (entity in mc.theWorld.playerEntities.sortedBy { player.getDistanceToEntity(it) }) {
            if (entity.entityId == player.entityId) {
                continue
            }

            if (entity.deathTime != 0) {
                continue
            }

            if (player.getDistanceToEntity(entity) > distance) {
                continue
            }

            if (isWithinFov(entity, fov * 2)) {
                return entity
            }
        }

        return null
    }

    private fun fovToEntity(ent: Entity): Float {
        val x: Double = ent.posX - mc.thePlayer.posX
        val z: Double = ent.posZ - mc.thePlayer.posZ
        val yaw = atan2(x, z) * 57.2957795
        return (yaw * -1.0).toFloat()
    }

    @Suppress("NAME_SHADOWING")
    private fun isWithinFov(entity: Entity, fov: Float): Boolean {
        var fov = fov
        fov = (fov.toDouble() * 0.5).toFloat()
        val v = ((player.rotationYaw - fovToEntity(entity)).toDouble() % 360.0 + 540.0) % 360.0 - 180.0
        return v > 0.0 && v < fov.toDouble() || (-fov).toDouble() < v && v < 0.0
    }
}