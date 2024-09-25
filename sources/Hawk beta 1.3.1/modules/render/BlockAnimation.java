package eze.modules.render;

import eze.modules.*;
import eze.util.*;
import eze.settings.*;
import eze.events.*;
import eze.events.listeners.*;

public class BlockAnimation extends Module
{
    public static int BlockAnimationInt;
    public ModeSetting BlockAnimation;
    Timer timer;
    boolean PlayerEat;
    
    static {
        BlockAnimation.BlockAnimationInt = 0;
    }
    
    public BlockAnimation() {
        super("SwordAnimations", 0, Category.RENDER);
        this.BlockAnimation = new ModeSetting("Hit and block animation", "EZE", new String[] { "EZE", "Vanilla" });
        this.timer = new Timer();
        this.PlayerEat = false;
        this.addSettings(this.BlockAnimation);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            if (this.BlockAnimation.is("Vanilla")) {
                eze.modules.render.BlockAnimation.BlockAnimationInt = 0;
            }
            if (this.BlockAnimation.is("EZE")) {
                eze.modules.render.BlockAnimation.BlockAnimationInt = 1;
            }
        }
    }
}
