package net.minecraft.src;

public class ModelZombieVillager extends ModelBiped
{
    public ModelZombieVillager() {
        this(0.0f, 0.0f, false);
    }
    
    public ModelZombieVillager(final float par1, final float par2, final boolean par3) {
        super(par1, 0.0f, 64, par3 ? 32 : 64);
        if (par3) {
            (this.bipedHead = new ModelRenderer(this, 0, 0)).addBox(-4.0f, -10.0f, -4.0f, 8, 6, 8, par1);
            this.bipedHead.setRotationPoint(0.0f, 0.0f + par2, 0.0f);
        }
        else {
            (this.bipedHead = new ModelRenderer(this)).setRotationPoint(0.0f, 0.0f + par2, 0.0f);
            this.bipedHead.setTextureOffset(0, 32).addBox(-4.0f, -10.0f, -4.0f, 8, 10, 8, par1);
            this.bipedHead.setTextureOffset(24, 32).addBox(-1.0f, -3.0f, -6.0f, 2, 4, 2, par1);
        }
    }
    
    public int func_82897_a() {
        return 10;
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        final float var8 = MathHelper.sin(this.onGround * 3.1415927f);
        final float var9 = MathHelper.sin((1.0f - (1.0f - this.onGround) * (1.0f - this.onGround)) * 3.1415927f);
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightArm.rotateAngleY = -(0.1f - var8 * 0.6f);
        this.bipedLeftArm.rotateAngleY = 0.1f - var8 * 0.6f;
        this.bipedRightArm.rotateAngleX = -1.5707964f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f;
        final ModelRenderer bipedRightArm = this.bipedRightArm;
        bipedRightArm.rotateAngleX -= var8 * 1.2f - var9 * 0.4f;
        final ModelRenderer bipedLeftArm = this.bipedLeftArm;
        bipedLeftArm.rotateAngleX -= var8 * 1.2f - var9 * 0.4f;
        final ModelRenderer bipedRightArm2 = this.bipedRightArm;
        bipedRightArm2.rotateAngleZ += MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
        bipedLeftArm2.rotateAngleZ -= MathHelper.cos(par3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer bipedRightArm3 = this.bipedRightArm;
        bipedRightArm3.rotateAngleX += MathHelper.sin(par3 * 0.067f) * 0.05f;
        final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
        bipedLeftArm3.rotateAngleX -= MathHelper.sin(par3 * 0.067f) * 0.05f;
    }
}
