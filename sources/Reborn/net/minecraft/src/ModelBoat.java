package net.minecraft.src;

public class ModelBoat extends ModelBase
{
    public ModelRenderer[] boatSides;
    
    public ModelBoat() {
        (this.boatSides = new ModelRenderer[5])[0] = new ModelRenderer(this, 0, 8);
        this.boatSides[1] = new ModelRenderer(this, 0, 0);
        this.boatSides[2] = new ModelRenderer(this, 0, 0);
        this.boatSides[3] = new ModelRenderer(this, 0, 0);
        this.boatSides[4] = new ModelRenderer(this, 0, 0);
        final byte var1 = 24;
        final byte var2 = 6;
        final byte var3 = 20;
        final byte var4 = 4;
        this.boatSides[0].addBox(-var1 / 2, -var3 / 2 + 2, -3.0f, var1, var3 - 4, 4, 0.0f);
        this.boatSides[0].setRotationPoint(0.0f, var4, 0.0f);
        this.boatSides[1].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[1].setRotationPoint(-var1 / 2 + 1, var4, 0.0f);
        this.boatSides[2].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[2].setRotationPoint(var1 / 2 - 1, var4, 0.0f);
        this.boatSides[3].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[3].setRotationPoint(0.0f, var4, -var3 / 2 + 1);
        this.boatSides[4].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[4].setRotationPoint(0.0f, var4, var3 / 2 - 1);
        this.boatSides[0].rotateAngleX = 1.5707964f;
        this.boatSides[1].rotateAngleY = 4.712389f;
        this.boatSides[2].rotateAngleY = 1.5707964f;
        this.boatSides[3].rotateAngleY = 3.1415927f;
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        for (int var8 = 0; var8 < 5; ++var8) {
            this.boatSides[var8].render(par7);
        }
    }
}
