package net.minecraft.src;

public class ModelBlaze extends ModelBase
{
    private ModelRenderer[] blazeSticks;
    private ModelRenderer blazeHead;
    
    public ModelBlaze() {
        this.blazeSticks = new ModelRenderer[12];
        for (int var1 = 0; var1 < this.blazeSticks.length; ++var1) {
            (this.blazeSticks[var1] = new ModelRenderer(this, 0, 16)).addBox(0.0f, 0.0f, 0.0f, 2, 8, 2);
        }
        (this.blazeHead = new ModelRenderer(this, 0, 0)).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
    }
    
    public int func_78104_a() {
        return 8;
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.blazeHead.render(par7);
        for (int var8 = 0; var8 < this.blazeSticks.length; ++var8) {
            this.blazeSticks[var8].render(par7);
        }
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        float var8 = par3 * 3.1415927f * -0.1f;
        for (int var9 = 0; var9 < 4; ++var9) {
            this.blazeSticks[var9].rotationPointY = -2.0f + MathHelper.cos((var9 * 2 + par3) * 0.25f);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 9.0f;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 9.0f;
            ++var8;
        }
        var8 = 0.7853982f + par3 * 3.1415927f * 0.03f;
        for (int var9 = 4; var9 < 8; ++var9) {
            this.blazeSticks[var9].rotationPointY = 2.0f + MathHelper.cos((var9 * 2 + par3) * 0.25f);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 7.0f;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 7.0f;
            ++var8;
        }
        var8 = 0.47123894f + par3 * 3.1415927f * -0.05f;
        for (int var9 = 8; var9 < 12; ++var9) {
            this.blazeSticks[var9].rotationPointY = 11.0f + MathHelper.cos((var9 * 1.5f + par3) * 0.5f);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 5.0f;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 5.0f;
            ++var8;
        }
        this.blazeHead.rotateAngleY = par4 / 57.295776f;
        this.blazeHead.rotateAngleX = par5 / 57.295776f;
    }
}
