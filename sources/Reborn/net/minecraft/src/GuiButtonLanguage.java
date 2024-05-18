package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class GuiButtonLanguage extends GuiButton
{
    public GuiButtonLanguage(final int par1, final int par2, final int par3) {
        super(par1, par2, par3, 20, 20, "");
    }
    
    @Override
    public void drawButton(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (this.drawButton) {
            par1Minecraft.renderEngine.bindTexture("/gui/gui.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean var4 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int var5 = 106;
            if (var4) {
                var5 += this.height;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, var5, this.width, this.height);
        }
    }
}
