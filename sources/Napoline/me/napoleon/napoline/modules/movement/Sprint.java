package me.napoleon.napoline.modules.movement;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.events.EventUpdate;
import me.napoleon.napoline.junk.values.type.Mode;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.modules.combat.KillAura;

public class Sprint extends Mod {
    private Mode<?> mode = new Mode<>("Mode", Modes.values(), Modes.Single);

    public Sprint() {
        super("Sprint", ModCategory.Movement,"Just toggle sprint");
        this.addValues(this.mode);
    }

    @EventTarget
    public void onUpdate(EventUpdate e){
        if(!Napoline.moduleManager.getModByClass(Scaffold.class).getState()) {
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

