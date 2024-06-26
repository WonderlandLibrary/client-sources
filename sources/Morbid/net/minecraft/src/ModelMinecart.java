package net.minecraft.src;

public class ModelMinecart extends ModelBase
{
    public ModelRenderer[] sideModels;
    
    public ModelMinecart() {
        (this.sideModels = new ModelRenderer[7])[0] = new ModelRenderer(this, 0, 10);
        this.sideModels[1] = new ModelRenderer(this, 0, 0);
        this.sideModels[2] = new ModelRenderer(this, 0, 0);
        this.sideModels[3] = new ModelRenderer(this, 0, 0);
        this.sideModels[4] = new ModelRenderer(this, 0, 0);
        this.sideModels[5] = new ModelRenderer(this, 44, 10);
        final byte var1 = 20;
        final byte var2 = 8;
        final byte var3 = 16;
        final byte var4 = 4;
        this.sideModels[0].addBox(-var1 / 2, -var3 / 2, -1.0f, var1, var3, 2, 0.0f);
        this.sideModels[0].setRotationPoint(0.0f, var4, 0.0f);
        this.sideModels[5].addBox(-var1 / 2 + 1, -var3 / 2 + 1, -1.0f, var1 - 2, var3 - 2, 1, 0.0f);
        this.sideModels[5].setRotationPoint(0.0f, var4, 0.0f);
        this.sideModels[1].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[1].setRotationPoint(-var1 / 2 + 1, var4, 0.0f);
        this.sideModels[2].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[2].setRotationPoint(var1 / 2 - 1, var4, 0.0f);
        this.sideModels[3].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[3].setRotationPoint(0.0f, var4, -var3 / 2 + 1);
        this.sideModels[4].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[4].setRotationPoint(0.0f, var4, var3 / 2 - 1);
        this.sideModels[0].rotateAngleX = 1.5707964f;
        this.sideModels[1].rotateAngleY = 4.712389f;
        this.sideModels[2].rotateAngleY = 1.5707964f;
        this.sideModels[3].rotateAngleY = 3.1415927f;
        this.sideModels[5].rotateAngleX = -1.5707964f;
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.sideModels[5].rotationPointY = 4.0f - par4;
        for (int var8 = 0; var8 < 6; ++var8) {
            this.sideModels[var8].render(par7);
        }
    }
}
