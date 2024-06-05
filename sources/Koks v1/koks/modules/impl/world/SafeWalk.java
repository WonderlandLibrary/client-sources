package koks.modules.impl.world;

import koks.event.Event;
import koks.event.impl.SafeWalkEvent;
import koks.modules.Module;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 11:08
 */
public class SafeWalk extends Module {

    public SafeWalk() {
        super("SafeWalk", "You cant fall down from a block", Category.WORLD);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof SafeWalkEvent) {
            if (mc.thePlayer.onGround)
                ((SafeWalkEvent) event).setSafe(true);
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}