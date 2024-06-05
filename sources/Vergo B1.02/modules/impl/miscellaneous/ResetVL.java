package xyz.vergoclient.modules.impl.miscellaneous;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventMove;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.util.main.ChatUtils;

public class ResetVL extends Module implements OnEventInterface {

    public ResetVL() {
        super("ResetVL", Category.MISCELLANEOUS);
    }

    private int jumped;
    private double y;

    @Override
    public void onEnable() {
        jumped = 0;
        y = mc.thePlayer.posY;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventMove) {
            if(mc.thePlayer.onGround) {
                if(jumped <= 25) {
                    mc.thePlayer.motionY = 0.11;
                    jumped++;
                }
            }
            if(jumped <= 25) {
                mc.thePlayer.posY = y;
                mc.timer.timerSpeed = 2.25f;
            } else {
                mc.timer.timerSpeed = 1;
                ChatUtils.addProtMsg("VL's Reset.");
                toggle();
            }
        }
    }

}