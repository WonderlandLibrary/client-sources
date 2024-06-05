package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderOcelot extends RenderLiving
{
    public RenderOcelot(final ModelBase par1ModelBase, final float par2) {
        super(par1ModelBase, par2);
    }
    
    public void renderLivingOcelot(final EntityOcelot par1EntityOcelot, final double par2, final double par4, final double par6, final float par8, final float par9) {
        super.doRenderLiving(par1EntityOcelot, par2, par4, par6, par8, par9);
    }
    
    protected void preRenderOcelot(final EntityOcelot par1EntityOcelot, final float par2) {
        super.preRenderCallback(par1EntityOcelot, par2);
        if (par1EntityOcelot.isTamed()) {
            GL11.glScalef(0.8f, 0.8f, 0.8f);
        }
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.preRenderOcelot((EntityOcelot)par1EntityLiving, par2);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderLivingOcelot((EntityOcelot)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderLivingOcelot((EntityOcelot)par1Entity, par2, par4, par6, par8, par9);
    }
}
