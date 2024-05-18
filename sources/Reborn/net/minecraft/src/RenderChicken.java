package net.minecraft.src;

public class RenderChicken extends RenderLiving
{
    public RenderChicken(final ModelBase par1ModelBase, final float par2) {
        super(par1ModelBase, par2);
    }
    
    public void renderChicken(final EntityChicken par1EntityChicken, final double par2, final double par4, final double par6, final float par8, final float par9) {
        super.doRenderLiving(par1EntityChicken, par2, par4, par6, par8, par9);
    }
    
    protected float getWingRotation(final EntityChicken par1EntityChicken, final float par2) {
        final float var3 = par1EntityChicken.field_70888_h + (par1EntityChicken.field_70886_e - par1EntityChicken.field_70888_h) * par2;
        final float var4 = par1EntityChicken.field_70884_g + (par1EntityChicken.destPos - par1EntityChicken.field_70884_g) * par2;
        return (MathHelper.sin(var3) + 1.0f) * var4;
    }
    
    @Override
    protected float handleRotationFloat(final EntityLiving par1EntityLiving, final float par2) {
        return this.getWingRotation((EntityChicken)par1EntityLiving, par2);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderChicken((EntityChicken)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderChicken((EntityChicken)par1Entity, par2, par4, par6, par8, par9);
    }
}
