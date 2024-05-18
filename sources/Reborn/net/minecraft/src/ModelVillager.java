package net.minecraft.src;

public class ModelVillager extends ModelBase
{
    public ModelRenderer villagerHead;
    public ModelRenderer villagerBody;
    public ModelRenderer villagerArms;
    public ModelRenderer rightVillagerLeg;
    public ModelRenderer leftVillagerLeg;
    public ModelRenderer field_82898_f;
    
    public ModelVillager(final float par1) {
        this(par1, 0.0f, 64, 64);
    }
    
    public ModelVillager(final float par1, final float par2, final int par3, final int par4) {
        (this.villagerHead = new ModelRenderer(this).setTextureSize(par3, par4)).setRotationPoint(0.0f, 0.0f + par2, 0.0f);
        this.villagerHead.setTextureOffset(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8, 10, 8, par1);
        (this.field_82898_f = new ModelRenderer(this).setTextureSize(par3, par4)).setRotationPoint(0.0f, par2 - 2.0f, 0.0f);
        this.field_82898_f.setTextureOffset(24, 0).addBox(-1.0f, -1.0f, -6.0f, 2, 4, 2, par1);
        this.villagerHead.addChild(this.field_82898_f);
        (this.villagerBody = new ModelRenderer(this).setTextureSize(par3, par4)).setRotationPoint(0.0f, 0.0f + par2, 0.0f);
        this.villagerBody.setTextureOffset(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8, 12, 6, par1);
        this.villagerBody.setTextureOffset(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8, 18, 6, par1 + 0.5f);
        (this.villagerArms = new ModelRenderer(this).setTextureSize(par3, par4)).setRotationPoint(0.0f, 0.0f + par2 + 2.0f, 0.0f);
        this.villagerArms.setTextureOffset(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4, 8, 4, par1);
        this.villagerArms.setTextureOffset(44, 22).addBox(4.0f, -2.0f, -2.0f, 4, 8, 4, par1);
        this.villagerArms.setTextureOffset(40, 38).addBox(-4.0f, 2.0f, -2.0f, 8, 4, 4, par1);
        (this.rightVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(par3, par4)).setRotationPoint(-2.0f, 12.0f + par2, 0.0f);
        this.rightVillagerLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, par1);
        this.leftVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(par3, par4);
        this.leftVillagerLeg.mirror = true;
        this.leftVillagerLeg.setRotationPoint(2.0f, 12.0f + par2, 0.0f);
        this.leftVillagerLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, par1);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.villagerHead.render(par7);
        this.villagerBody.render(par7);
        this.rightVillagerLeg.render(par7);
        this.leftVillagerLeg.render(par7);
        this.villagerArms.render(par7);
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        this.villagerHead.rotateAngleY = par4 / 57.295776f;
        this.villagerHead.rotateAngleX = par5 / 57.295776f;
        this.villagerArms.rotationPointY = 3.0f;
        this.villagerArms.rotationPointZ = -1.0f;
        this.villagerArms.rotateAngleX = -0.75f;
        this.rightVillagerLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f) * 1.4f * par2 * 0.5f;
        this.leftVillagerLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662f + 3.1415927f) * 1.4f * par2 * 0.5f;
        this.rightVillagerLeg.rotateAngleY = 0.0f;
        this.leftVillagerLeg.rotateAngleY = 0.0f;
    }
}
