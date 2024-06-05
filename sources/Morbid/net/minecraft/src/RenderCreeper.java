package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderCreeper extends RenderLiving
{
    private ModelBase creeperModel;
    
    public RenderCreeper() {
        super(new ModelCreeper(), 0.5f);
        this.creeperModel = new ModelCreeper(2.0f);
    }
    
    protected void updateCreeperScale(final EntityCreeper par1EntityCreeper, final float par2) {
        float var4 = par1EntityCreeper.getCreeperFlashIntensity(par2);
        final float var5 = 1.0f + MathHelper.sin(var4 * 100.0f) * var4 * 0.01f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        if (var4 > 1.0f) {
            var4 = 1.0f;
        }
        var4 *= var4;
        var4 *= var4;
        final float var6 = (1.0f + var4 * 0.4f) * var5;
        final float var7 = (1.0f + var4 * 0.1f) / var5;
        GL11.glScalef(var6, var7, var6);
    }
    
    protected int updateCreeperColorMultiplier(final EntityCreeper par1EntityCreeper, final float par2, final float par3) {
        final float var5 = par1EntityCreeper.getCreeperFlashIntensity(par3);
        if ((int)(var5 * 10.0f) % 2 == 0) {
            return 0;
        }
        int var6 = (int)(var5 * 0.2f * 255.0f);
        if (var6 < 0) {
            var6 = 0;
        }
        if (var6 > 255) {
            var6 = 255;
        }
        final short var7 = 255;
        final short var8 = 255;
        final short var9 = 255;
        return var6 << 24 | var7 << 16 | var8 << 8 | var9;
    }
    
    protected int renderCreeperPassModel(final EntityCreeper par1EntityCreeper, final int par2, final float par3) {
        if (par1EntityCreeper.getPowered()) {
            if (par1EntityCreeper.isInvisible()) {
                GL11.glDepthMask(false);
            }
            else {
                GL11.glDepthMask(true);
            }
            if (par2 == 1) {
                final float var4 = par1EntityCreeper.ticksExisted + par3;
                this.loadTexture("/armor/power.png");
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                final float var5 = var4 * 0.01f;
                final float var6 = var4 * 0.01f;
                GL11.glTranslatef(var5, var6, 0.0f);
                this.setRenderPassModel(this.creeperModel);
                GL11.glMatrixMode(5888);
                GL11.glEnable(3042);
                final float var7 = 0.5f;
                GL11.glColor4f(var7, var7, var7, 1.0f);
                GL11.glDisable(2896);
                GL11.glBlendFunc(1, 1);
                return 1;
            }
            if (par2 == 2) {
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(5888);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
            }
        }
        return -1;
    }
    
    protected int func_77061_b(final EntityCreeper par1EntityCreeper, final int par2, final float par3) {
        return -1;
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.updateCreeperScale((EntityCreeper)par1EntityLiving, par2);
    }
    
    @Override
    protected int getColorMultiplier(final EntityLiving par1EntityLiving, final float par2, final float par3) {
        return this.updateCreeperColorMultiplier((EntityCreeper)par1EntityLiving, par2, par3);
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.renderCreeperPassModel((EntityCreeper)par1EntityLiving, par2, par3);
    }
    
    @Override
    protected int inheritRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.func_77061_b((EntityCreeper)par1EntityLiving, par2, par3);
    }
}
