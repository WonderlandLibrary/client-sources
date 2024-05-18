package net.minecraft.src;

public class ModelSkeletonHead extends ModelBase
{
    public ModelRenderer skeletonHead;
    
    public ModelSkeletonHead() {
        this(0, 35, 64, 64);
    }
    
    public ModelSkeletonHead(final int par1, final int par2, final int par3, final int par4) {
        this.textureWidth = par3;
        this.textureHeight = par4;
        (this.skeletonHead = new ModelRenderer(this, par1, par2)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.0f);
        this.skeletonHead.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.skeletonHead.render(par7);
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.skeletonHead.rotateAngleY = par4 / 57.295776f;
        this.skeletonHead.rotateAngleX = par5 / 57.295776f;
    }
}
