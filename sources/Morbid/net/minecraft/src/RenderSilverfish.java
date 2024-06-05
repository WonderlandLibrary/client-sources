package net.minecraft.src;

public class RenderSilverfish extends RenderLiving
{
    public RenderSilverfish() {
        super(new ModelSilverfish(), 0.3f);
    }
    
    protected float getSilverfishDeathRotation(final EntitySilverfish par1EntitySilverfish) {
        return 180.0f;
    }
    
    public void renderSilverfish(final EntitySilverfish par1EntitySilverfish, final double par2, final double par4, final double par6, final float par8, final float par9) {
        super.doRenderLiving(par1EntitySilverfish, par2, par4, par6, par8, par9);
    }
    
    protected int shouldSilverfishRenderPass(final EntitySilverfish par1EntitySilverfish, final int par2, final float par3) {
        return -1;
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLiving par1EntityLiving) {
        return this.getSilverfishDeathRotation((EntitySilverfish)par1EntityLiving);
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.shouldSilverfishRenderPass((EntitySilverfish)par1EntityLiving, par2, par3);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderSilverfish((EntitySilverfish)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderSilverfish((EntitySilverfish)par1Entity, par2, par4, par6, par8, par9);
    }
}
