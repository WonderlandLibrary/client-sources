package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

public final class JumpCrits extends ModBase
{
    public JumpCrits() {
        super("JumpCrits", "K", true, ".t jumpcrits");
        this.setDescription("Tp's you up a little when you are attacking to do critical damage.");
    }
    
    @Override
    public void postMotionUpdate() {
        if (this.shouldCrit() && this.checkBlock()) {
            this.getWrapper();
            MorbidWrapper.getPlayer().E.d(0.0, 0.6, 0.0);
        }
    }
    
    private boolean shouldCrit() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().F) {
            this.getWrapper();
            if (!MorbidWrapper.getPlayer().g_()) {
                this.getWrapper();
                if (!MorbidWrapper.getPlayer().G()) {
                    this.getWrapper();
                    if (!MorbidWrapper.getGameSettings().M.e) {
                        this.getWrapper();
                        if (!MorbidWrapper.getPlayer().G && KillAura.curTarget != null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean checkBlock() {
        this.getWrapper();
        final bds e = MorbidWrapper.mcObj().e;
        this.getWrapper();
        final int c = kx.c(MorbidWrapper.getPlayer().u);
        this.getWrapper();
        final int c2 = kx.c(MorbidWrapper.getPlayer().E.e + 1.0);
        this.getWrapper();
        final boolean b = e.c(c, c2, kx.c(MorbidWrapper.getPlayer().w));
        return b;
    }
}
