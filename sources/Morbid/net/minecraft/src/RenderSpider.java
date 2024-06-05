package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderSpider extends RenderLiving
{
    public RenderSpider() {
        super(new ModelSpider(), 1.0f);
        this.setRenderPassModel(new ModelSpider());
    }
    
    protected float setSpiderDeathMaxRotation(final EntitySpider par1EntitySpider) {
        return 180.0f;
    }
    
    protected int setSpiderEyeBrightness(final EntitySpider par1EntitySpider, final int par2, final float par3) {
        if (par2 != 0) {
            return -1;
        }
        this.loadTexture("/mob/spider_eyes.png");
        final float var4 = 1.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(1, 1);
        if (par1EntitySpider.isInvisible()) {
            GL11.glDepthMask(false);
        }
        else {
            GL11.glDepthMask(true);
        }
        final char var5 = '\uf0f0';
        final int var6 = var5 % 65536;
        final int var7 = var5 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0f, var7 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, var4);
        return 1;
    }
    
    protected void scaleSpider(final EntitySpider par1EntitySpider, final float par2) {
        final float var3 = par1EntitySpider.spiderScaleAmount();
        GL11.glScalef(var3, var3, var3);
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.scaleSpider((EntitySpider)par1EntityLiving, par2);
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLiving par1EntityLiving) {
        return this.setSpiderDeathMaxRotation((EntitySpider)par1EntityLiving);
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.setSpiderEyeBrightness((EntitySpider)par1EntityLiving, par2, par3);
    }
}
