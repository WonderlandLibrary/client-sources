package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderSquid extends RenderLiving
{
    public RenderSquid(final ModelBase par1ModelBase, final float par2) {
        super(par1ModelBase, par2);
    }
    
    public void renderLivingSquid(final EntitySquid par1EntitySquid, final double par2, final double par4, final double par6, final float par8, final float par9) {
        super.doRenderLiving(par1EntitySquid, par2, par4, par6, par8, par9);
    }
    
    protected void rotateSquidsCorpse(final EntitySquid par1EntitySquid, final float par2, final float par3, final float par4) {
        final float var5 = par1EntitySquid.prevSquidPitch + (par1EntitySquid.squidPitch - par1EntitySquid.prevSquidPitch) * par4;
        final float var6 = par1EntitySquid.prevSquidYaw + (par1EntitySquid.squidYaw - par1EntitySquid.prevSquidYaw) * par4;
        GL11.glTranslatef(0.0f, 0.5f, 0.0f);
        GL11.glRotatef(180.0f - par3, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(var5, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(var6, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, -1.2f, 0.0f);
    }
    
    protected float handleRotationFloat(final EntitySquid par1EntitySquid, final float par2) {
        return par1EntitySquid.prevTentacleAngle + (par1EntitySquid.tentacleAngle - par1EntitySquid.prevTentacleAngle) * par2;
    }
    
    @Override
    protected float handleRotationFloat(final EntityLiving par1EntityLiving, final float par2) {
        return this.handleRotationFloat((EntitySquid)par1EntityLiving, par2);
    }
    
    @Override
    protected void rotateCorpse(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        this.rotateSquidsCorpse((EntitySquid)par1EntityLiving, par2, par3, par4);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderLivingSquid((EntitySquid)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderLivingSquid((EntitySquid)par1Entity, par2, par4, par6, par8, par9);
    }
}
