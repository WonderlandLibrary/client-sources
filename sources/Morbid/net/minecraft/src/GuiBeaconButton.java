package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

class GuiBeaconButton extends GuiButton
{
    private final String buttonTexture;
    private final int field_82257_l;
    private final int field_82258_m;
    private boolean field_82256_n;
    
    protected GuiBeaconButton(final int par1, final int par2, final int par3, final String par4Str, final int par5, final int par6) {
        super(par1, par2, par3, 22, 22, "");
        this.buttonTexture = par4Str;
        this.field_82257_l = par5;
        this.field_82258_m = par6;
    }
    
    @Override
    public void drawButton(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (this.drawButton) {
            par1Minecraft.renderEngine.bindTexture("/gui/beacon.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_82253_i = (par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height);
            final short var4 = 219;
            int var5 = 0;
            if (!this.enabled) {
                var5 += this.width * 2;
            }
            else if (this.field_82256_n) {
                var5 += this.width * 1;
            }
            else if (this.field_82253_i) {
                var5 += this.width * 3;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, var5, var4, this.width, this.height);
            if (!"/gui/beacon.png".equals(this.buttonTexture)) {
                par1Minecraft.renderEngine.bindTexture(this.buttonTexture);
            }
            this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.field_82257_l, this.field_82258_m, 18, 18);
        }
    }
    
    public boolean func_82255_b() {
        return this.field_82256_n;
    }
    
    public void func_82254_b(final boolean par1) {
        this.field_82256_n = par1;
    }
}
