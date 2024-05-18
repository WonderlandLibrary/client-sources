package net.minecraft.src;

public class ModelSheep2 extends ModelQuadruped
{
    private float field_78153_i;
    
    public ModelSheep2() {
        super(12, 0.0f);
        (this.head = new ModelRenderer(this, 0, 0)).addBox(-3.0f, -4.0f, -6.0f, 6, 6, 8, 0.0f);
        this.head.setRotationPoint(0.0f, 6.0f, -8.0f);
        (this.body = new ModelRenderer(this, 28, 8)).addBox(-4.0f, -10.0f, -7.0f, 8, 16, 6, 0.0f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
    }
    
    @Override
    public void setLivingAnimations(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        super.setLivingAnimations(par1EntityLiving, par2, par3, par4);
        this.head.rotationPointY = 6.0f + ((EntitySheep)par1EntityLiving).func_70894_j(par4) * 9.0f;
        this.field_78153_i = ((EntitySheep)par1EntityLiving).func_70890_k(par4);
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.head.rotateAngleX = this.field_78153_i;
    }
}
