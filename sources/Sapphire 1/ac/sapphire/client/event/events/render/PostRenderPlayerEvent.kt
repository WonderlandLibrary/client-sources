package ac.sapphire.client.event.events.render

import net.minecraft.client.entity.AbstractClientPlayer

class PostRenderPlayerEvent(
    val entity: AbstractClientPlayer,
    val tick: Float,
    val x: Double,
    val y: Double,
    val z: Double
)