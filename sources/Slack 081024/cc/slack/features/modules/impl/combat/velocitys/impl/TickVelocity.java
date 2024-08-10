package cc.slack.features.modules.impl.combat.velocitys.impl;

import cc.slack.start.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.combat.Velocity;
import cc.slack.features.modules.impl.combat.velocitys.IVelocity;

public class TickVelocity implements IVelocity {

    @Override
    public void onUpdate(UpdateEvent event) {
        Velocity velocityModule = Slack.getInstance().getModuleManager().getInstance(Velocity.class);
        double horizontalValue = velocityModule.horizontal.getValue().doubleValue();
        double verticalValue = velocityModule.vertical.getValue().doubleValue();
        int tickValue = velocityModule.velocityTick.getValue();
        if (mc.thePlayer.ticksSinceLastDamage == tickValue) {
            mc.thePlayer.motionX *= horizontalValue / 100;
            mc.thePlayer.motionY *= verticalValue / 100;
            mc.thePlayer.motionZ *= horizontalValue / 100;
        }
    }

    @Override
    public String toString() {
        return "Tick";
    }
}
