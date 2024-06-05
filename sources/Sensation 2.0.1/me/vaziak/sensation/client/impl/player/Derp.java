package me.vaziak.sensation.client.impl.player;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.impl.combat.KillAura;
import me.vaziak.sensation.utils.math.MathUtils;

public class Derp extends Module {

    private StringsProperty modeProperty = new StringsProperty("Mode", "How this cheat will function.", null,
            true, true, new String[]{"Spin", "Nod", "Sneak"}, new Boolean[]{true, false, false});

    private DoubleProperty nod_inc = new DoubleProperty("Nod increment", "The yaw and pitch your head shakes to", () -> modeProperty.getValue().get("Nod"), 35, 5, 100, 5, null);
    private DoubleProperty spin_inc = new DoubleProperty("Spin increment", "How fast you spin", () -> modeProperty.getValue().get("Spin"), 35, 5, 100, 5, null);
    private DoubleProperty sneak_del = new DoubleProperty("Sneak delay", "SNEAK - 100  : Delay between sneaks", () -> modeProperty.getValue().get("Sneak"), 35, 5, 200, 5, null);
    int pitch;
    boolean decreasing3;

    public Derp() {
        super("Derp/AntiAim", Category.PLAYER);
        registerValue(modeProperty, nod_inc, spin_inc, sneak_del);
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        KillAura killaura = ((KillAura) Sensation.instance.cheatManager.getCheatRegistry().get("Kill Aura"));
        boolean canderp = !Sensation.instance.cheatManager.isModuleEnabled("Scaffold") && (killaura.targetIndex == -1 || !Sensation.instance.cheatManager.isModuleEnabled("Kill Aura"));
        pitch += decreasing3 ? -(spin_inc.getValue() / 2) : (spin_inc.getValue() / 2);
        if (pitch >= ((spin_inc.getValue() * 3))) {
            decreasing3 = true;
        }
        if (pitch <= 0) {
            decreasing3 = false;
        }
        if (canderp) {
            if (modeProperty.getValue().get("Spin")) {
                playerUpdateEvent.setYaw((float) (playerUpdateEvent.getLastYaw() + MathUtils.getRandomInRange(spin_inc.getValue() / 2, spin_inc.getValue())));
            }
            if (modeProperty.getValue().get("Nod")) {
                playerUpdateEvent.setPitch((float) (playerUpdateEvent.getLastPitch() + nod_inc.getValue()));
            }
        }
        if (modeProperty.getValue().get("Sneak")) {
            if (mc.thePlayer.isSneaking()) {
                mc.thePlayer.cameraYaw = .105F;
            }
            mc.gameSettings.keyBindSneak.pressed = mc.thePlayer.ticksExisted % 10 != 0;
        }
    }
}