package ac.sapphire.client.module.impl.player

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.misc.PreTickEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import ac.sapphire.client.property.impl.primitive.number.FloatProperty

class TimerModule : AbstractModule("timer", "Timer", "Makes your game go nyooooom", ModuleCategory.PLAYER) {
    private val speed by FloatProperty("Speed", 10F, 0.01F..10F)

    override fun onDisable() {
        mc.timer.timerSpeed = 1F
    }

    @Subscribe
    fun onTick(tickEvent: PreTickEvent) {
        mc.timer.timerSpeed = speed
    }
}