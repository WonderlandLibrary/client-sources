package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

public final class Regen extends ModBase
{
    int speed;
    
    public Regen() {
        super("Regen", "K", true, ".t reg");
        this.speed = 2100;
        this.setDescription("La vita si rigenera in fretta.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled() && this.shouldRegenerate()) {
            for (int i = 0; i < this.speed; ++i) {
                MorbidWrapper.mcObj().r().c(new ee(MorbidWrapper.getPlayer().F));
            }
        }
    }
    
    public boolean shouldRegenerate() {
        return MorbidWrapper.mcObj().g.F && !MorbidWrapper.mcObj().g.ce.d && MorbidWrapper.mcObj().g.cn().a() > 18 && MorbidWrapper.getPlayer().aX() < 20.0f && !MorbidWrapper.mcObj().g.aj();
    }
    
    @Override
    public void onCommand(final String paramString) {
        final String[] arrayOfString = paramString.split(" ");
        if (paramString.toLowerCase().startsWith(".rn")) {
            try {
                final int d1 = Integer.parseInt(arrayOfString[1]);
                this.speed = d1;
                this.getWrapper();
                MorbidWrapper.addChat("Regen speed set to: " + d1);
            }
            catch (Exception localException1) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .rn [regen speed]");
            }
            ModBase.setCommandExists(true);
        }
    }
}
