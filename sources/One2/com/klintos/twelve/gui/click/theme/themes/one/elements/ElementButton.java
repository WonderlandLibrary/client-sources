// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.click.theme.themes.one.elements;

import net.minecraft.client.Minecraft;
import com.klintos.twelve.utils.NahrFont;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.gui.click.elements.ElementBase;

public class ElementButton extends ElementBase
{
    private Mod BUTTON_Mod;
    
    public ElementButton(final Mod Mod) {
        super(14);
        this.setMod(Mod);
    }
    
    @Override
    public void drawElement(final int POSX, final int POSY, final int MOUSEX, final int MOUSEY) {
        this.setPosX(POSX);
        this.setPosY(POSY);
        final boolean isHover = MOUSEX >= this.getPosX() && MOUSEY >= this.getPosY() + 4 && MOUSEX <= this.getPosX() + this.getWidth() / 2 / 2 + 78 && MOUSEY <= this.getPosY() + this.getHeight() + 5;
        GuiUtils.drawFineBorderedRect(this.getPosX(), this.getPosY() + 4, this.getPosX() + this.getWidth() / 2 / 2 + 79, this.getPosY() + this.getHeight() + 6, isHover ? -36752 : -12303292, this.getMod().getEnabled() ? -44976 : -13882324);
        Twelve.getInstance().guiFont.drawCenteredString(this.getMod().getModName(), this.getPosX() + 52, this.getPosY() + 8, NahrFont.FontType.PLAIN, this.getMod().getEnabled() ? -1 : -7829368, 0);
    }
    
    @Override
    public void mouseClicked(final int par1, final int par2, final int par3) {
        if (par1 >= this.getPosX() && par2 >= this.getPosY() + 4 && par1 <= this.getPosX() + this.getWidth() / 2 / 2 + 78 && par2 <= this.getPosY() + this.getHeight() + 5 && par3 == 0) {
            this.getMod().onToggle();
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0f, 1.0f);
        }
    }
    
    public Mod getMod() {
        return this.BUTTON_Mod;
    }
    
    public void setMod(final Mod Mod) {
        this.BUTTON_Mod = Mod;
    }
}
