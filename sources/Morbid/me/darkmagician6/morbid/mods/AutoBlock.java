package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

public final class AutoBlock extends ModBase
{
    public AutoBlock() {
        super("AutoBlock", "0", true, ".t block");
        this.setDescription("Makes you block while attacking.");
    }
    
    @Override
    public void preMotionUpdate() {
        if (KillAura.curTarget != null && this.shouldBlock()) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().ah()) {
                this.getWrapper();
                MorbidWrapper.getPlayer().c(false);
            }
            this.getWrapper();
            this.getWrapper();
            this.getWrapper();
            this.getWrapper();
            MorbidWrapper.getController().a(MorbidWrapper.mcObj().g, MorbidWrapper.getWorld(), MorbidWrapper.getPlayer().bK.h());
        }
    }
    
    private boolean shouldBlock() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().cd() != null) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().cd().b() instanceof xr) {
                this.getWrapper();
            }
            return !MorbidWrapper.mcObj().w.e.e();
        }
        return false;
    }
}
