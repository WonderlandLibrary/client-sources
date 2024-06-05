package net.minecraft.src;

public class ModelWither extends ModelBase
{
    private ModelRenderer[] field_82905_a;
    private ModelRenderer[] field_82904_b;
    
    public ModelWither() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.field_82905_a = new ModelRenderer[3];
        (this.field_82905_a[0] = new ModelRenderer(this, 0, 16)).addBox(-10.0f, 3.9f, -0.5f, 20, 3, 3);
        (this.field_82905_a[1] = new ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight)).setRotationPoint(-2.0f, 6.9f, -0.5f);
        this.field_82905_a[1].setTextureOffset(0, 22).addBox(0.0f, 0.0f, 0.0f, 3, 10, 3);
        this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0f, 1.5f, 0.5f, 11, 2, 2);
        this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0f, 4.0f, 0.5f, 11, 2, 2);
        this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0f, 6.5f, 0.5f, 11, 2, 2);
        (this.field_82905_a[2] = new ModelRenderer(this, 12, 22)).addBox(0.0f, 0.0f, 0.0f, 3, 6, 3);
        this.field_82904_b = new ModelRenderer[3];
        (this.field_82904_b[0] = new ModelRenderer(this, 0, 0)).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        (this.field_82904_b[1] = new ModelRenderer(this, 32, 0)).addBox(-4.0f, -4.0f, -4.0f, 6, 6, 6);
        this.field_82904_b[1].rotationPointX = -8.0f;
        this.field_82904_b[1].rotationPointY = 4.0f;
        (this.field_82904_b[2] = new ModelRenderer(this, 32, 0)).addBox(-4.0f, -4.0f, -4.0f, 6, 6, 6);
        this.field_82904_b[2].rotationPointX = 10.0f;
        this.field_82904_b[2].rotationPointY = 4.0f;
    }
    
    public int func_82903_a() {
        return 32;
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        for (final ModelRenderer var11 : this.field_82904_b) {
            var11.render(par7);
        }
        for (final ModelRenderer var11 : this.field_82905_a) {
            var11.render(par7);
        }
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        final float var8 = MathHelper.cos(par3 * 0.1f);
        this.field_82905_a[1].rotateAngleX = (0.065f + 0.05f * var8) * 3.1415927f;
        this.field_82905_a[2].setRotationPoint(-2.0f, 6.9f + MathHelper.cos(this.field_82905_a[1].rotateAngleX) * 10.0f, -0.5f + MathHelper.sin(this.field_82905_a[1].rotateAngleX) * 10.0f);
        this.field_82905_a[2].rotateAngleX = (0.265f + 0.1f * var8) * 3.1415927f;
        this.field_82904_b[0].rotateAngleY = par4 / 57.295776f;
        this.field_82904_b[0].rotateAngleX = par5 / 57.295776f;
    }
    
    @Override
    public void setLivingAnimations(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        final EntityWither var5 = (EntityWither)par1EntityLiving;
        for (int var6 = 1; var6 < 3; ++var6) {
            this.field_82904_b[var6].rotateAngleY = (var5.func_82207_a(var6 - 1) - par1EntityLiving.renderYawOffset) / 57.295776f;
            this.field_82904_b[var6].rotateAngleX = var5.func_82210_r(var6 - 1) / 57.295776f;
        }
    }
}
