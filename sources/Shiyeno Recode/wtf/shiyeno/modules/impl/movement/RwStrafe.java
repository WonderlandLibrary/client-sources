package wtf.shiyeno.modules.impl.movement;

import net.minecraft.entity.player.PlayerEntity;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(name="RwStrafe", type=Type.Movement)
public class RwStrafe
        extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    public ModeSetting mode = new ModeSetting("Режим", "Player Hard", "Player", "Player Hard");

    public RwStrafe() {
        this.addSettings(this.mode);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate && (this.mode.is("Player") || this.mode.is("Player Hard"))) {
            for (PlayerEntity playerEntity : RwStrafe.mc.world.getPlayers()) {
                float speed;
                if (RwStrafe.mc.player == playerEntity) continue;
                if (this.mode.is("Player Hard") && RwStrafe.mc.player.getDistance(playerEntity) <= 2.0f && (RwStrafe.mc.gameSettings.keyBindForward.isKeyDown() || RwStrafe.mc.gameSettings.keyBindRight.isKeyDown() || RwStrafe.mc.gameSettings.keyBindLeft.isKeyDown() || RwStrafe.mc.gameSettings.keyBindBack.isKeyDown())) {
                    speed = 1.185f;
                    RwStrafe.mc.player.getMotion().x *= (double)speed;
                    RwStrafe.mc.player.getMotion().z *= (double)speed;
                }
                if (!this.mode.is("Player") || !(RwStrafe.mc.player.getDistance(playerEntity) <= 2.0f) || !RwStrafe.mc.gameSettings.keyBindForward.isKeyDown() && !RwStrafe.mc.gameSettings.keyBindRight.isKeyDown() && !RwStrafe.mc.gameSettings.keyBindLeft.isKeyDown() && !RwStrafe.mc.gameSettings.keyBindBack.isKeyDown()) continue;
                speed = 1.15f;
                RwStrafe.mc.player.getMotion().x *= (double)speed;
                RwStrafe.mc.player.getMotion().z *= (double)speed;
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}