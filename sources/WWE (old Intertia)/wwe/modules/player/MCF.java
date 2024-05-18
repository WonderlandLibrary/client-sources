package wwe.modules.player;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import wwe.*;

public class MCF extends qMIe
{
    public MCF() {
        super("MCF", "Middle Click Friends", 0, Category.PLAYER);
    }
    
    @Override
    public void OgHM() {
        if (!this.IuiN()) {
            return;
        }
        if (Wrapper.mc.objectMouseOver != null && Wrapper.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            final String name = Wrapper.mc.objectMouseOver.entityHit.getName();
            if (!vlwv.Zqnd().UsVJ(name)) {
                vlwv.Zqnd().kDXm(name, name);
                HAxG.TuzD("Added " + TextFormatting.DARK_RED + name + TextFormatting.DARK_RED + " to friend" + TextFormatting.DARK_RED + "s list");
                Wrapper.ypJK().pRVY();
                if (MCF.\u2001\u200d\u200f\u200d\u2004\u200d\u2007\u200e) {
                    throw null;
                }
            }
            else {
                vlwv.Zqnd().bSEt(name);
                HAxG.TuzD("Removed " + TextFormatting.DARK_RED + name + TextFormatting.DARK_RED + " from friend" + TextFormatting.DARK_RED + "s list");
                Wrapper.ypJK().pRVY();
            }
        }
    }
}
