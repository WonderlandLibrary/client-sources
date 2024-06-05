package net.minecraft.src;

public class ModelSkeleton extends ModelZombie
{
    public ModelSkeleton() {
        this(0.0f);
    }
    
    public ModelSkeleton(final float par1) {
        super(par1, 0.0f, 64, 32);
        (this.bipedRightArm = new ModelRenderer(this, 40, 16)).addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, par1);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, par1);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
        (this.bipedRightLeg = new ModelRenderer(this, 0, 16)).addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, par1);
        this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, par1);
        this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
    }
    
    @Override
    public void setLivingAnimations(final EntityLiving par1EntityLiving, final float par2, final float par3, final float par4) {
        this.aimedBow = (((EntitySkeleton)par1EntityLiving).getSkeletonType() == 1);
        super.setLivingAnimations(par1EntityLiving, par2, par3, par4);
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
    }
}
