package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderTNTPrimed extends Render
{
    private RenderBlocks blockRenderer;
    
    public RenderTNTPrimed() {
        this.blockRenderer = new RenderBlocks();
        this.shadowSize = 0.5f;
    }
    
    public void renderPrimedTNT(final EntityTNTPrimed par1EntityTNTPrimed, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        if (par1EntityTNTPrimed.fuse - par9 + 1.0f < 10.0f) {
            float var10 = 1.0f - (par1EntityTNTPrimed.fuse - par9 + 1.0f) / 10.0f;
            if (var10 < 0.0f) {
                var10 = 0.0f;
            }
            if (var10 > 1.0f) {
                var10 = 1.0f;
            }
            var10 *= var10;
            var10 *= var10;
            final float var11 = 1.0f + var10 * 0.3f;
            GL11.glScalef(var11, var11, var11);
        }
        float var10 = (1.0f - (par1EntityTNTPrimed.fuse - par9 + 1.0f) / 100.0f) * 0.8f;
        this.loadTexture("/terrain.png");
        this.blockRenderer.renderBlockAsItem(Block.tnt, 0, par1EntityTNTPrimed.getBrightness(par9));
        if (par1EntityTNTPrimed.fuse / 5 % 2 == 0) {
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 772);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, var10);
            this.blockRenderer.renderBlockAsItem(Block.tnt, 0, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
        GL11.glPopMatrix();
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderPrimedTNT((EntityTNTPrimed)par1Entity, par2, par4, par6, par8, par9);
    }
}
