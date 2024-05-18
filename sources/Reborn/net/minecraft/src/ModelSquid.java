package net.minecraft.src;

public class ModelSquid extends ModelBase
{
    ModelRenderer squidBody;
    ModelRenderer[] squidTentacles;
    
    public ModelSquid() {
        this.squidTentacles = new ModelRenderer[8];
        final byte var1 = -16;
        (this.squidBody = new ModelRenderer(this, 0, 0)).addBox(-6.0f, -8.0f, -6.0f, 12, 16, 12);
        final ModelRenderer squidBody = this.squidBody;
        squidBody.rotationPointY += 24 + var1;
        for (int var2 = 0; var2 < this.squidTentacles.length; ++var2) {
            this.squidTentacles[var2] = new ModelRenderer(this, 48, 0);
            double var3 = var2 * 3.141592653589793 * 2.0 / this.squidTentacles.length;
            final float var4 = (float)Math.cos(var3) * 5.0f;
            final float var5 = (float)Math.sin(var3) * 5.0f;
            this.squidTentacles[var2].addBox(-1.0f, 0.0f, -1.0f, 2, 18, 2);
            this.squidTentacles[var2].rotationPointX = var4;
            this.squidTentacles[var2].rotationPointZ = var5;
            this.squidTentacles[var2].rotationPointY = 31 + var1;
            var3 = var2 * 3.141592653589793 * -2.0 / this.squidTentacles.length + 1.5707963267948966;
            this.squidTentacles[var2].rotateAngleY = (float)var3;
        }
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        for (final ModelRenderer var11 : this.squidTentacles) {
            var11.rotateAngleX = par3;
        }
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.squidBody.render(par7);
        for (int var8 = 0; var8 < this.squidTentacles.length; ++var8) {
            this.squidTentacles[var8].render(par7);
        }
    }
}
