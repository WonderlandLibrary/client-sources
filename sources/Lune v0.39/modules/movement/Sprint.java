package me.superskidder.lune.modules.movement;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.combat.KillAura;
import me.superskidder.lune.values.type.Mode;

public class Sprint extends Mod {
    private Mode<?> mode = new Mode<>("Mode", Modes.values(), Modes.Single);

    public Sprint() {
        super("Sprint", ModCategory.Movement,"Just toggle sprint");
        this.addValues(this.mode);
    }

    @EventTarget
    public void onUpdate(EventUpdate e){
        if(!Lune.moduleManager.getModByClass(Scaffold.class).getState()) {
            if (mc.thePlayer.movementInput.moveForward > 0
                    || (mode.getValue() == Modes.All && mc.thePlayer.moving())) {
                mc.thePlayer.setSprinting(true);
            }
        }
    }

    @Override
    public void onDisable(){
        mc.thePlayer.setSprinting(false);
    }

    enum Modes{
        // 全方向
        All,
        // 单方向
        Single
    }
}

