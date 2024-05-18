package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

class GuiButtonMerchant extends GuiButton
{
    private final boolean mirrored;
    
    public GuiButtonMerchant(final int par1, final int par2, final int par3, final boolean par4) {
        super(par1, par2, par3, 12, 19, "");
        this.mirrored = par4;
    }
    
    @Override
    public void drawButton(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (this.drawButton) {
            par1Minecraft.renderEngine.bindTexture("/gui/trading.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean var4 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int var5 = 0;
            int var6 = 176;
            if (!this.enabled) {
                var6 += this.width * 2;
            }
            else if (var4) {
                var6 += this.width;
            }
            if (!this.mirrored) {
                var5 += this.height;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, var6, var5, this.width, this.height);
        }
    }
}
