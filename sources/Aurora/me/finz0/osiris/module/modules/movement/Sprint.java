package me.finz0.osiris.module.modules.movement;

import me.finz0.osiris.module.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.MOVEMENT, "Automatically sprint");
    }

    public void onUpdate(){
        if(mc.player == null || mc.world == null) return;
        if(mc.player.moveForward > 0 && !mc.player.isSprinting()){
            mc.player.setSprinting(true);
        }
    }
}
