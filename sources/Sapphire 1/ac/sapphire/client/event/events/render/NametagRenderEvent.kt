package ac.sapphire.client.event.events.render

import net.minecraft.entity.EntityLivingBase

class NametagRenderEvent(val entity: EntityLivingBase, val x: Double, val y: Double, val z: Double, var cancelled: Boolean = true)