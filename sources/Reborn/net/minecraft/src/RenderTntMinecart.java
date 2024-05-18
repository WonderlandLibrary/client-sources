package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderTntMinecart extends RenderMinecart
{
    protected void func_94146_a(final EntityMinecartTNT par1EntityMinecartTNT, final float par2, final Block par3Block, final int par4) {
        final int var5 = par1EntityMinecartTNT.func_94104_d();
        if (var5 > -1 && var5 - par2 + 1.0f < 10.0f) {
            float var6 = 1.0f - (var5 - par2 + 1.0f) / 10.0f;
            if (var6 < 0.0f) {
                var6 = 0.0f;
            }
            if (var6 > 1.0f) {
                var6 = 1.0f;
            }
            var6 *= var6;
            var6 *= var6;
            final float var7 = 1.0f + var6 * 0.3f;
            GL11.glScalef(var7, var7, var7);
        }
        super.renderBlockInMinecart(par1EntityMinecartTNT, par2, par3Block, par4);
        if (var5 > -1 && var5 / 5 % 2 == 0) {
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 772);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, (1.0f - (var5 - par2 + 1.0f) / 100.0f) * 0.8f);
            GL11.glPushMatrix();
            this.field_94145_f.renderBlockAsItem(Block.tnt, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
    }
    
    @Override
    protected void renderBlockInMinecart(final EntityMinecart par1EntityMinecart, final float par2, final Block par3Block, final int par4) {
        this.func_94146_a((EntityMinecartTNT)par1EntityMinecart, par2, par3Block, par4);
    }
}
