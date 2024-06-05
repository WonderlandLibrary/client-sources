package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

public final class Spider extends ModBase
{
    public Spider() {
        super("Spider", "J", true, ".t spider");
        this.setDescription("Let's you climb up walls when you are hurt.");
    }
    
    @Override
    public void preMotionUpdate() {
        if (this.shouldClimb()) {
            this.getWrapper();
            MorbidWrapper.getPlayer().y = 0.4;
        }
    }
    
    private boolean shouldClimb() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().aW > 0) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().G) {
                this.getWrapper();
                if (!MorbidWrapper.getPlayer().G()) {
                    this.getWrapper();
                    if (MorbidWrapper.getPlayer().b.b <= 0.0f) {
                        this.getWrapper();
                        if (MorbidWrapper.getPlayer().b.a <= 0.0f) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
