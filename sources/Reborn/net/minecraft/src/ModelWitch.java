package net.minecraft.src;

public class ModelWitch extends ModelVillager
{
    public boolean field_82900_g;
    private ModelRenderer field_82901_h;
    private ModelRenderer witchHat;
    
    public ModelWitch(final float par1) {
        super(par1, 0.0f, 64, 128);
        this.field_82900_g = false;
        (this.field_82901_h = new ModelRenderer(this).setTextureSize(64, 128)).setRotationPoint(0.0f, -2.0f, 0.0f);
        this.field_82901_h.setTextureOffset(0, 0).addBox(0.0f, 3.0f, -6.75f, 1, 1, 1, -0.25f);
        this.field_82898_f.addChild(this.field_82901_h);
        (this.witchHat = new ModelRenderer(this).setTextureSize(64, 128)).setRotationPoint(-5.0f, -10.03125f, -5.0f);
        this.witchHat.setTextureOffset(0, 64).addBox(0.0f, 0.0f, 0.0f, 10, 2, 10);
        this.villagerHead.addChild(this.witchHat);
        final ModelRenderer var2 = new ModelRenderer(this).setTextureSize(64, 128);
        var2.setRotationPoint(1.75f, -4.0f, 2.0f);
        var2.setTextureOffset(0, 76).addBox(0.0f, 0.0f, 0.0f, 7, 4, 7);
        var2.rotateAngleX = -0.05235988f;
        var2.rotateAngleZ = 0.02617994f;
        this.witchHat.addChild(var2);
        final ModelRenderer var3 = new ModelRenderer(this).setTextureSize(64, 128);
        var3.setRotationPoint(1.75f, -4.0f, 2.0f);
        var3.setTextureOffset(0, 87).addBox(0.0f, 0.0f, 0.0f, 4, 4, 4);
        var3.rotateAngleX = -0.10471976f;
        var3.rotateAngleZ = 0.05235988f;
        var2.addChild(var3);
        final ModelRenderer var4 = new ModelRenderer(this).setTextureSize(64, 128);
        var4.setRotationPoint(1.75f, -2.0f, 2.0f);
        var4.setTextureOffset(0, 95).addBox(0.0f, 0.0f, 0.0f, 1, 2, 1, 0.25f);
        var4.rotateAngleX = -0.20943952f;
        var4.rotateAngleZ = 0.10471976f;
        var3.addChild(var4);
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        final ModelRenderer field_82898_f = this.field_82898_f;
        final ModelRenderer field_82898_f2 = this.field_82898_f;
        final ModelRenderer field_82898_f3 = this.field_82898_f;
        final float field_82906_o = 0.0f;
        field_82898_f3.field_82907_q = field_82906_o;
        field_82898_f2.field_82908_p = field_82906_o;
        field_82898_f.field_82906_o = field_82906_o;
        final float var8 = 0.01f * (par7Entity.entityId % 10);
        this.field_82898_f.rotateAngleX = MathHelper.sin(par7Entity.ticksExisted * var8) * 4.5f * 3.1415927f / 180.0f;
        this.field_82898_f.rotateAngleY = 0.0f;
        this.field_82898_f.rotateAngleZ = MathHelper.cos(par7Entity.ticksExisted * var8) * 2.5f * 3.1415927f / 180.0f;
        if (this.field_82900_g) {
            this.field_82898_f.rotateAngleX = -0.9f;
            this.field_82898_f.field_82907_q = -0.09375f;
            this.field_82898_f.field_82908_p = 0.1875f;
        }
    }
    
    public int func_82899_a() {
        return 0;
    }
}
