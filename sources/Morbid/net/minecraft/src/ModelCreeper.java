package net.minecraft.src;

public class ModelCreeper extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer field_78133_b;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    
    public ModelCreeper() {
        this(0.0f);
    }
    
    public ModelCreeper(final float par1) {
        final byte var2 = 4;
        (this.head = new ModelRenderer(this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, par1);
        this.head.setRotationPoint(0.0f, var2, 0.0f);
        (this.field_78133_b = new ModelRenderer(this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, par1 + 0.5f);
        this.field_78133_b.setRotationPoint(0.0f, var2, 0.0f);
        (this.body = new ModelRenderer(this, 16, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, par1);
        this.body.setRotationPoint(0.0f, var2, 0.0f);
        (this.leg1 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, par1);
        this.leg1.setRotationPoint(-2.0f, 12 + var2, 4.0f);
        (this.leg2 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, par1);
        this.leg2.setRotationPoint(2.0f, 12 + var2, 4.0f);
        (this.leg3 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, par1);
        this.leg3.setRotationPoint(-2.0f, 12 + var2, -4.0f);
        (this.leg4 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, par1);
        this.leg4.setRotationPoint(2.0f, 12 + var2, -4.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.head.render(par7);
        this.body.render(par7);
        this.leg1.render(par7);
        this.leg2.render(par7);
        this.leg3.render(par7);
        this.leg4.render(par7);
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        this.head.rotateAngleY = par4 / 57.295776f;
        this.head.rotateAngleX = par5 / 57.295776f;
        this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
        this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.4f * par2;
        this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.4f * par2;
        this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2;
    }
}
