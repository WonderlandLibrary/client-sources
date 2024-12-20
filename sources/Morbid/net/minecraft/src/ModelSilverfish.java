package net.minecraft.src;

public class ModelSilverfish extends ModelBase
{
    private ModelRenderer[] silverfishBodyParts;
    private ModelRenderer[] silverfishWings;
    private float[] field_78170_c;
    private static final int[][] silverfishBoxLength;
    private static final int[][] silverfishTexturePositions;
    
    static {
        silverfishBoxLength = new int[][] { { 3, 2, 2 }, { 4, 3, 2 }, { 6, 4, 3 }, { 3, 3, 3 }, { 2, 2, 3 }, { 2, 1, 2 }, { 1, 1, 2 } };
        silverfishTexturePositions = new int[][] { new int[2], { 0, 4 }, { 0, 9 }, { 0, 16 }, { 0, 22 }, { 11, 0 }, { 13, 4 } };
    }
    
    public ModelSilverfish() {
        this.silverfishBodyParts = new ModelRenderer[7];
        this.field_78170_c = new float[7];
        float var1 = -3.5f;
        for (int var2 = 0; var2 < this.silverfishBodyParts.length; ++var2) {
            (this.silverfishBodyParts[var2] = new ModelRenderer(this, ModelSilverfish.silverfishTexturePositions[var2][0], ModelSilverfish.silverfishTexturePositions[var2][1])).addBox(ModelSilverfish.silverfishBoxLength[var2][0] * -0.5f, 0.0f, ModelSilverfish.silverfishBoxLength[var2][2] * -0.5f, ModelSilverfish.silverfishBoxLength[var2][0], ModelSilverfish.silverfishBoxLength[var2][1], ModelSilverfish.silverfishBoxLength[var2][2]);
            this.silverfishBodyParts[var2].setRotationPoint(0.0f, 24 - ModelSilverfish.silverfishBoxLength[var2][1], var1);
            this.field_78170_c[var2] = var1;
            if (var2 < this.silverfishBodyParts.length - 1) {
                var1 += (ModelSilverfish.silverfishBoxLength[var2][2] + ModelSilverfish.silverfishBoxLength[var2 + 1][2]) * 0.5f;
            }
        }
        this.silverfishWings = new ModelRenderer[3];
        (this.silverfishWings[0] = new ModelRenderer(this, 20, 0)).addBox(-5.0f, 0.0f, ModelSilverfish.silverfishBoxLength[2][2] * -0.5f, 10, 8, ModelSilverfish.silverfishBoxLength[2][2]);
        this.silverfishWings[0].setRotationPoint(0.0f, 16.0f, this.field_78170_c[2]);
        (this.silverfishWings[1] = new ModelRenderer(this, 20, 11)).addBox(-3.0f, 0.0f, ModelSilverfish.silverfishBoxLength[4][2] * -0.5f, 6, 4, ModelSilverfish.silverfishBoxLength[4][2]);
        this.silverfishWings[1].setRotationPoint(0.0f, 20.0f, this.field_78170_c[4]);
        (this.silverfishWings[2] = new ModelRenderer(this, 20, 18)).addBox(-3.0f, 0.0f, ModelSilverfish.silverfishBoxLength[4][2] * -0.5f, 6, 5, ModelSilverfish.silverfishBoxLength[1][2]);
        this.silverfishWings[2].setRotationPoint(0.0f, 19.0f, this.field_78170_c[1]);
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        for (int var8 = 0; var8 < this.silverfishBodyParts.length; ++var8) {
            this.silverfishBodyParts[var8].render(par7);
        }
        for (int var8 = 0; var8 < this.silverfishWings.length; ++var8) {
            this.silverfishWings[var8].render(par7);
        }
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        for (int var8 = 0; var8 < this.silverfishBodyParts.length; ++var8) {
            this.silverfishBodyParts[var8].rotateAngleY = MathHelper.cos(par3 * 0.9f + var8 * 0.15f * 3.1415927f) * 3.1415927f * 0.05f * (1 + Math.abs(var8 - 2));
            this.silverfishBodyParts[var8].rotationPointX = MathHelper.sin(par3 * 0.9f + var8 * 0.15f * 3.1415927f) * 3.1415927f * 0.2f * Math.abs(var8 - 2);
        }
        this.silverfishWings[0].rotateAngleY = this.silverfishBodyParts[2].rotateAngleY;
        this.silverfishWings[1].rotateAngleY = this.silverfishBodyParts[4].rotateAngleY;
        this.silverfishWings[1].rotationPointX = this.silverfishBodyParts[4].rotationPointX;
        this.silverfishWings[2].rotateAngleY = this.silverfishBodyParts[1].rotateAngleY;
        this.silverfishWings[2].rotationPointX = this.silverfishBodyParts[1].rotationPointX;
    }
}
