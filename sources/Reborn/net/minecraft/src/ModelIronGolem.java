package net.minecraft.src;

public class ModelIronGolem extends ModelBase
{
    public ModelRenderer ironGolemHead;
    public ModelRenderer ironGolemBody;
    public ModelRenderer ironGolemRightArm;
    public ModelRenderer ironGolemLeftArm;
    public ModelRenderer ironGolemLeftLeg;
    public ModelRenderer ironGolemRightLeg;
    
    public ModelIronGolem() {
        this(0.0f);
    }
    
    public ModelIronGolem(final float par1) {
        this(par1, -7.0f);
    }
    
    public ModelIronGolem(final float par1, final float par2) {
        final short var3 = 128;
        final short var4 = 128;
        (this.ironGolemHead = new ModelRenderer(this).setTextureSize(var3, var4)).setRotationPoint(0.0f, 0.0f + par2, -2.0f);
        this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0f, -12.0f, -5.5f, 8, 10, 8, par1);
        this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0f, -5.0f, -7.5f, 2, 4, 2, par1);
        (this.ironGolemBody = new ModelRenderer(this).setTextureSize(var3, var4)).setRotationPoint(0.0f, 0.0f + par2, 0.0f);
        this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0f, -2.0f, -6.0f, 18, 12, 11, par1);
        this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5f, 10.0f, -3.0f, 9, 5, 6, par1 + 0.5f);
        (this.ironGolemRightArm = new ModelRenderer(this).setTextureSize(var3, var4)).setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0f, -2.5f, -3.0f, 4, 30, 6, par1);
        (this.ironGolemLeftArm = new ModelRenderer(this).setTextureSize(var3, var4)).setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0f, -2.5f, -3.0f, 4, 30, 6, par1);
        (this.ironGolemLeftLeg = new ModelRenderer(this, 0, 22).setTextureSize(var3, var4)).setRotationPoint(-4.0f, 18.0f + par2, 0.0f);
        this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5f, -3.0f, -3.0f, 6, 16, 5, par1);
        this.ironGolemRightLeg = new ModelRenderer(this, 0, 22).setTextureSize(var3, var4);
        this.ironGolemRightLeg.mirror = true;
        this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0f, 18.0f + par2, 0.0f);
        this.ironGolemRightLeg.addBox(-3.5f, -3.0f, -3.0f, 6, 16, 5, par1);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.ironGolemHead.render(par7);
        this.ironGolemBody.render(par7);
        this.ironGolemLeftLeg.render(par7);
        this.ironGolemRightLeg.render(par7);
        this.ironGolemRightArm.render(par7);
        this.ironGolemLeftArm.render(par7);
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        this.ironGolemHead.rotateAngleY = par4 / 57.295776f;
        this.ironGolemHead.rotateAngleX = par5 / 57.295776f;
        this.ironGolemLeftLeg.rotateAngleX = -1.5f * this.func_78172_a(par1, 13.0f) * par2;
        this.ironGolemRightLeg.rotateAngleX = 1.5f * this.func_78172_a(par1, 13.0f) * par2;
        this.ironGolemLeftLeg.rotateAngleY = 0.0f;
        this.ironGolemRightLeg.rotateAngleY = 0.0f;
    }
    
    @Override
    public void setLivingAnimations(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        final EntityIronGolem var5 = (EntityIronGolem)par1EntityLiving;
        final int var6 = var5.getAttackTimer();
        if (var6 > 0) {
            this.ironGolemRightArm.rotateAngleX = -2.0f + 1.5f * this.func_78172_a(var6 - par4, 10.0f);
            this.ironGolemLeftArm.rotateAngleX = -2.0f + 1.5f * this.func_78172_a(var6 - par4, 10.0f);
        }
        else {
            final int var7 = var5.getHoldRoseTick();
            if (var7 > 0) {
                this.ironGolemRightArm.rotateAngleX = -0.8f + 0.025f * this.func_78172_a(var7, 70.0f);
                this.ironGolemLeftArm.rotateAngleX = 0.0f;
            }
            else {
                this.ironGolemRightArm.rotateAngleX = (-0.2f + 1.5f * this.func_78172_a(par2, 13.0f)) * par3;
                this.ironGolemLeftArm.rotateAngleX = (-0.2f - 1.5f * this.func_78172_a(par2, 13.0f)) * par3;
            }
        }
    }
    
    private float func_78172_a(final float par1, final float par2) {
        return (Math.abs(par1 % par2 - par2 * 0.5f) - par2 * 0.25f) / (par2 * 0.25f);
    }
}
