package koks.modules.impl.player;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;

/**
 * @author avox | lmao | kroko
 * @created on 08.09.2020 : 22:34
 */
public class AirJump extends Module {

    public AirJump() {
        super("AirJump", "you can jump in air", Category.MOVEMENT);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate)
            mc.thePlayer.onGround = true;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}