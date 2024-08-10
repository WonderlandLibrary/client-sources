package cc.slack.features.modules.impl.movement.flights.impl.vanilla;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.flights.IFlight;

public class CreativeFly implements IFlight {

    @Override
    public void onEnable() {
        mc.thePlayer.capabilities.isFlying = true;
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.thePlayer.capabilities.isFlying = true;

    }

    @Override
    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
    }

    @Override
    public String toString() {
        return "Creative";
    }
}
