package me.darkmagician6.morbid.gui;

import net.minecraft.client.*;
import me.darkmagician6.morbid.*;
import me.darkmagician6.morbid.util.*;
import me.darkmagician6.morbid.mods.*;
import me.darkmagician6.morbid.mods.base.*;
import java.util.*;

public class MorbidIngame extends aww
{
    public MorbidIngame(final Minecraft mc) {
        super(mc);
    }
    
    @Override
    public void a(final float par1, final boolean par2, final int par3, final int par4) {
        super.a(par1, par2, par3, par4);
        if (Morbid.getManager().getMod("vanilla").isEnabled() || MorbidWrapper.mcObj().z.ab) {
            return;
        }
        MorbidWrapper.getFontRenderer().a(MorbidHelper.getVersion(), 2, 2, 16729088);
        if (Morbid.getManager().getMod("autosoup").isEnabled()) {
            this.a(MorbidWrapper.getFontRenderer(), "Soups§0 [§f" + AutoSoup.soups + "§0]", MorbidWrapper.getScreenWidth() / 2, 2, 16729088);
        }
        int posY = 2;
        for (final ModBase o : Morbid.getManager().getMods()) {
            final String name = o.getName();
            final int xPos = MorbidWrapper.getScreenWidth() - (MorbidWrapper.getFontRenderer().a("[" + name + "]") + 2);
            if (o.isEnabled() && o.shouldShow()) {
                MorbidWrapper.getFontRenderer().a("§0[§r" + name + "§0]", xPos, posY, o.getColor());
                posY += 12;
            }
        }
    }
}
