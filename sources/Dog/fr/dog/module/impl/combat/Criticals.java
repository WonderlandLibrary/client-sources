package fr.dog.module.impl.combat;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;

public class Criticals extends Module {

    private final ModeProperty mode = ModeProperty.newInstance("Mode", new String[]{"Timer", "NoGround"}, "Timer");
    private final NumberProperty timer = NumberProperty.newInstance("Timer", 0.1f, 1f, 10f, 0.01f, () -> mode.is("Timer"));

    public Criticals() {
        super("Criticals", ModuleCategory.COMBAT);
        this.registerProperties(mode, timer);
    }

    public void onEnable() {
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1f;
    }

    @SubscribeEvent
    private void onPlayerNetworkTick(PlayerNetworkTickEvent event) {
        this.setSuffix(mode.getValue());
        switch (mode.getValue()) {
            case "Timer":
                if (mc.thePlayer.motionY < 0 && !mc.thePlayer.onGround) {
                    mc.timer.timerSpeed = timer.getValue();
                } else {
                    mc.timer.timerSpeed = 1f;
                }
                break;
            case "NoGround":
                if (mc.thePlayer.fallDistance < 1.8 && mc.thePlayer.ticksSinceHurt < 45) {
                    event.setOnGround(false);
                }
        }
    }
}
