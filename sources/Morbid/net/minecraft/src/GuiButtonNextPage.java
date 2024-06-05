package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

class GuiButtonNextPage extends GuiButton
{
    private final boolean nextPage;
    
    public GuiButtonNextPage(final int par1, final int par2, final int par3, final boolean par4) {
        super(par1, par2, par3, 23, 13, "");
        this.nextPage = par4;
    }
    
    @Override
    public void drawButton(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (this.drawButton) {
            final boolean var4 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            par1Minecraft.renderEngine.bindTexture("/gui/book.png");
            int var5 = 0;
            int var6 = 192;
            if (var4) {
                var5 += 23;
            }
            if (!this.nextPage) {
                var6 += 13;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, var5, var6, 23, 13);
        }
    }
}
