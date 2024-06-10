package ac.sapphire.client.module.impl.combat

import ac.sapphire.client.ext.mc
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import ac.sapphire.client.property.impl.primitive.number.FloatProperty
import com.google.common.base.Predicate
import com.google.common.base.Predicates
import net.minecraft.client.renderer.EntityRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItemFrame
import net.minecraft.util.*

class ReachModule : AbstractModule("reach", "Reach", "", ModuleCategory.COMBAT) {
    private val range by FloatProperty("Range", 0F, 0F..6F)
    private val hitboxExpansion by FloatProperty("Expand HitBox", 0F, 0F..1F)

    init {
        mc.entityRenderer = CustomEntityRenderer()
    }

    private inner class CustomEntityRenderer : EntityRenderer(mc, mc.resourceManager) {
        private var pointedEntity: Entity? = null

        override fun getMouseOver(partialTicks: Float) {
            val entity = mc.renderViewEntity
            if (entity != null) {
                if (mc.theWorld != null) {
                    mc.mcProfiler.startSection("pick")
                    mc.pointedEntity = null
                    var d0 = mc.playerController.blockReachDistance.toDouble()
                    mc.objectMouseOver = entity.rayTrace(d0, partialTicks)

                    var d1 = d0
                    val vec3 = entity.getPositionEyes(partialTicks)
                    var flag = false
                    if (mc.playerController.extendedReach()) {
                        d0 = 6.0
                        d1 = 6.0
                    } else {
                        if (isToggled) {
                            if (d0 > range) {
                                d1 = range.toDouble()
                            }

                            d0 = d1
                        } else {
                            if (d0 > 3.0) {
                                flag = true
                            }
                        }
                    }
                    if (mc.objectMouseOver != null) {
                        d1 = mc.objectMouseOver.hitVec.distanceTo(vec3)
                    }
                    val vec31 = entity.getLook(partialTicks)
                    val vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0)
                    this.pointedEntity = null
                    var vec33: Vec3? = null
                    val f = 1.0f
                    val list = mc.theWorld.getEntitiesInAABBexcluding(
                        entity,
                        entity.entityBoundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0)
                            .expand(f.toDouble(), f.toDouble(), f.toDouble()),
                        Predicates.and(EntitySelectors.NOT_SPECTATING,
                            Predicate { p_apply_1_ -> p_apply_1_?.canBeCollidedWith() ?: false })
                    )
                    var d2 = d1
                    for (j in list.indices) {
                        val entity1 = list[j] as Entity
                        val f1 = entity1.collisionBorderSize
                        val axisalignedbb =
                            entity1.entityBoundingBox.expand(f1.toDouble(), f1.toDouble(), f1.toDouble()).expand(
                                hitboxExpansion.toDouble(), hitboxExpansion.toDouble(), hitboxExpansion.toDouble()
                            )
                        val movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32)
                        if (axisalignedbb.isVecInside(vec3)) {
                            if (d2 >= 0.0) {
                                this.pointedEntity = entity1
                                vec33 = if (movingobjectposition == null) vec3 else movingobjectposition.hitVec
                                d2 = 0.0
                            }
                        } else if (movingobjectposition != null) {
                            val d3 = vec3.distanceTo(movingobjectposition.hitVec)
                            if (d3 < d2 || d2 == 0.0) {
                                if (entity1 === entity.ridingEntity && !entity.canRiderInteract()) {
                                    if (d2 == 0.0) {
                                        this.pointedEntity = entity1
                                        vec33 = movingobjectposition.hitVec
                                    }
                                } else {
                                    this.pointedEntity = entity1
                                    vec33 = movingobjectposition.hitVec
                                    d2 = d3
                                }
                            }
                        }
                    }
                    if (this.pointedEntity != null && flag && vec3.distanceTo(vec33) > 3.0) {
                        this.pointedEntity = null
                        mc.objectMouseOver = MovingObjectPosition(
                            MovingObjectPosition.MovingObjectType.MISS, vec33, null as EnumFacing?, BlockPos(vec33)
                        )
                    }
                    if (this.pointedEntity != null && (d2 < d1 || mc.objectMouseOver == null)) {
                        mc.objectMouseOver = MovingObjectPosition(this.pointedEntity, vec33)
                        if (this.pointedEntity is EntityLivingBase || this.pointedEntity is EntityItemFrame) {
                            mc.pointedEntity = this.pointedEntity
                        }
                    }
                    mc.mcProfiler.endSection()
                }
            }
        }
    }
}