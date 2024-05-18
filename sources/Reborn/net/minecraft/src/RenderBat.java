package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderBat extends RenderLiving
{
    private int renderedBatSize;
    
    public RenderBat() {
        super(new ModelBat(), 0.25f);
        this.renderedBatSize = ((ModelBat)this.mainModel).getBatSize();
    }
    
    public void func_82443_a(final EntityBat par1EntityBat, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final int var10 = ((ModelBat)this.mainModel).getBatSize();
        if (var10 != this.renderedBatSize) {
            this.renderedBatSize = var10;
            this.mainModel = new ModelBat();
        }
        super.doRenderLiving(par1EntityBat, par2, par4, par6, par8, par9);
    }
    
    protected void func_82442_a(final EntityBat par1EntityBat, final float par2) {
        GL11.glScalef(0.35f, 0.35f, 0.35f);
    }
    
    protected void func_82445_a(final EntityBat par1EntityBat, final double par2, final double par4, final double par6) {
        super.renderLivingAt(par1EntityBat, par2, par4, par6);
    }
    
    protected void func_82444_a(final EntityBat par1EntityBat, final float par2, final float par3, final float par4) {
        if (!par1EntityBat.getIsBatHanging()) {
            GL11.glTranslatef(0.0f, MathHelper.cos(par2 * 0.3f) * 0.1f, 0.0f);
        }
        else {
            GL11.glTranslatef(0.0f, -0.1f, 0.0f);
        }
        super.rotateCorpse(par1EntityBat, par2, par3, par4);
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.func_82442_a((EntityBat)par1EntityLiving, par2);
    }
    
    @Override
    protected void rotateCorpse(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        this.func_82444_a((EntityBat)par1EntityLiving, par2, par3, par4);
    }
    
    @Override
    protected void renderLivingAt(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6) {
        this.func_82445_a((EntityBat)par1EntityLiving, par2, par4, par6);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82443_a((EntityBat)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82443_a((EntityBat)par1Entity, par2, par4, par6, par8, par9);
    }
}
