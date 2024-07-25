package club.bluezenith.module.modules.movement;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;

public class AirJump extends Module {

    public AirJump() {
        super("AirJump", ModuleCategory.MOVEMENT);
    }

    int timer = 0;
    @Listener
    public void move(UpdatePlayerEvent event) {
        final boolean jumpKeyDown = mc.gameSettings.keyBindJump.pressed;
        if(jumpKeyDown && timer == 0) {
            timer = 1;
            mc.thePlayer.jump();
        } else if(!jumpKeyDown) {
            timer = 0;
        }
    }
}
