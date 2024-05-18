package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

public final class God extends ModBase
{
    private long stored;
    
    public God() {
        super("God", "G", true, ".t god");
        this.stored = -1L;
        this.setDescription("Ti rende immortale.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled() && (this.stored + 1000L < System.currentTimeMillis() || this.stored == -1L)) {
            MorbidWrapper.mcObj().g.a.c(new ef(MorbidWrapper.mcObj().g.u, MorbidWrapper.mcObj().g.E.b - 0.2, MorbidWrapper.mcObj().g.v - 1.5, MorbidWrapper.mcObj().g.w, MorbidWrapper.mcObj().g.F));
            this.stored = System.currentTimeMillis();
        }
    }
}
