package net.minecraft.src;

public class RenderPig extends RenderLiving
{
    public RenderPig(final ModelBase par1ModelBase, final ModelBase par2ModelBase, final float par3) {
        super(par1ModelBase, par3);
        this.setRenderPassModel(par2ModelBase);
    }
    
    protected int renderSaddledPig(final EntityPig par1EntityPig, final int par2, final float par3) {
        if (par2 == 0 && par1EntityPig.getSaddled()) {
            this.loadTexture("/mob/saddle.png");
            return 1;
        }
        return -1;
    }
    
    public void renderLivingPig(final EntityPig par1EntityPig, final double par2, final double par4, final double par6, final float par8, final float par9) {
        super.doRenderLiving(par1EntityPig, par2, par4, par6, par8, par9);
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.renderSaddledPig((EntityPig)par1EntityLiving, par2, par3);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderLivingPig((EntityPig)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderLivingPig((EntityPig)par1Entity, par2, par4, par6, par8, par9);
    }
}
