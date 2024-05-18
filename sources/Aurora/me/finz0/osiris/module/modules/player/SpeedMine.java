package me.finz0.osiris.module.modules.player;

import me.finz0.osiris.module.Module;

public class SpeedMine extends Module {
    public SpeedMine() {
        super("SpeedMine", Category.PLAYER, "Mine blocks faster");
    }

    public void onUpdate(){
        if(mc.playerController.curBlockDamageMP >= 0)
            mc.playerController.curBlockDamageMP = 1;
    }
}
