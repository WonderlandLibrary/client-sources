package net.minecraft.src;

public class RenderCow extends RenderLiving
{
    public RenderCow(final ModelBase par1ModelBase, final float par2) {
        super(par1ModelBase, par2);
    }
    
    public void renderCow(final EntityCow par1EntityCow, final double par2, final double par4, final double par6, final float par8, final float par9) {
        super.doRenderLiving(par1EntityCow, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderCow((EntityCow)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderCow((EntityCow)par1Entity, par2, par4, par6, par8, par9);
    }
}
