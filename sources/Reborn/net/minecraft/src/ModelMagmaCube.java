package net.minecraft.src;

public class ModelMagmaCube extends ModelBase
{
    ModelRenderer[] field_78109_a;
    ModelRenderer field_78108_b;
    
    public ModelMagmaCube() {
        this.field_78109_a = new ModelRenderer[8];
        for (int var1 = 0; var1 < this.field_78109_a.length; ++var1) {
            byte var2 = 0;
            int var3;
            if ((var3 = var1) == 2) {
                var2 = 24;
                var3 = 10;
            }
            else if (var1 == 3) {
                var2 = 24;
                var3 = 19;
            }
            (this.field_78109_a[var1] = new ModelRenderer(this, var2, var3)).addBox(-4.0f, 16 + var1, -4.0f, 8, 1, 8);
        }
        (this.field_78108_b = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 18.0f, -2.0f, 4, 4, 4);
    }
    
    public int func_78107_a() {
        return 5;
    }
    
    @Override
    public void setLivingAnimations(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        final EntityMagmaCube var5 = (EntityMagmaCube)par1EntityLiving;
        float var6 = var5.field_70812_c + (var5.field_70811_b - var5.field_70812_c) * par4;
        if (var6 < 0.0f) {
            var6 = 0.0f;
        }
        for (int var7 = 0; var7 < this.field_78109_a.length; ++var7) {
            this.field_78109_a[var7].rotationPointY = -(4 - var7) * var6 * 1.7f;
        }
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.field_78108_b.render(par7);
        for (int var8 = 0; var8 < this.field_78109_a.length; ++var8) {
            this.field_78109_a[var8].render(par7);
        }
    }
}
