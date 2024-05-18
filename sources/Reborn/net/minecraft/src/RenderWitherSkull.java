package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderWitherSkull extends Render
{
    ModelSkeletonHead skeletonHeadModel;
    
    public RenderWitherSkull() {
        this.skeletonHeadModel = new ModelSkeletonHead();
    }
    
    private float func_82400_a(final float par1, final float par2, final float par3) {
        float var4;
        for (var4 = par2 - par1; var4 < -180.0f; var4 += 360.0f) {}
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        return par1 + par3 * var4;
    }
    
    public void func_82399_a(final EntityWitherSkull par1EntityWitherSkull, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        final float var10 = this.func_82400_a(par1EntityWitherSkull.prevRotationYaw, par1EntityWitherSkull.rotationYaw, par9);
        final float var11 = par1EntityWitherSkull.prevRotationPitch + (par1EntityWitherSkull.rotationPitch - par1EntityWitherSkull.prevRotationPitch) * par9;
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        final float var12 = 0.0625f;
        GL11.glEnable(32826);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glEnable(3008);
        if (par1EntityWitherSkull.isInvulnerable()) {
            this.loadTexture("/mob/wither_invul.png");
        }
        else {
            this.loadTexture("/mob/wither.png");
        }
        this.skeletonHeadModel.render(par1EntityWitherSkull, 0.0f, 0.0f, 0.0f, var10, var11, var12);
        GL11.glPopMatrix();
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82399_a((EntityWitherSkull)par1Entity, par2, par4, par6, par8, par9);
    }
}
