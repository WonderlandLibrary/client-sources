package net.minecraft.src;

public class ModelBox
{
    private PositionTextureVertex[] vertexPositions;
    private TexturedQuad[] quadList;
    public final float posX1;
    public final float posY1;
    public final float posZ1;
    public final float posX2;
    public final float posY2;
    public final float posZ2;
    public String field_78247_g;
    
    public ModelBox(final ModelRenderer par1ModelRenderer, final int par2, final int par3, float par4, float par5, float par6, final int par7, final int par8, final int par9, final float par10) {
        this.posX1 = par4;
        this.posY1 = par5;
        this.posZ1 = par6;
        this.posX2 = par4 + par7;
        this.posY2 = par5 + par8;
        this.posZ2 = par6 + par9;
        this.vertexPositions = new PositionTextureVertex[8];
        this.quadList = new TexturedQuad[6];
        float var11 = par4 + par7;
        float var12 = par5 + par8;
        float var13 = par6 + par9;
        par4 -= par10;
        par5 -= par10;
        par6 -= par10;
        var11 += par10;
        var12 += par10;
        var13 += par10;
        if (par1ModelRenderer.mirror) {
            final float var14 = var11;
            var11 = par4;
            par4 = var14;
        }
        final PositionTextureVertex var15 = new PositionTextureVertex(par4, par5, par6, 0.0f, 0.0f);
        final PositionTextureVertex var16 = new PositionTextureVertex(var11, par5, par6, 0.0f, 8.0f);
        final PositionTextureVertex var17 = new PositionTextureVertex(var11, var12, par6, 8.0f, 8.0f);
        final PositionTextureVertex var18 = new PositionTextureVertex(par4, var12, par6, 8.0f, 0.0f);
        final PositionTextureVertex var19 = new PositionTextureVertex(par4, par5, var13, 0.0f, 0.0f);
        final PositionTextureVertex var20 = new PositionTextureVertex(var11, par5, var13, 0.0f, 8.0f);
        final PositionTextureVertex var21 = new PositionTextureVertex(var11, var12, var13, 8.0f, 8.0f);
        final PositionTextureVertex var22 = new PositionTextureVertex(par4, var12, var13, 8.0f, 0.0f);
        this.vertexPositions[0] = var15;
        this.vertexPositions[1] = var16;
        this.vertexPositions[2] = var17;
        this.vertexPositions[3] = var18;
        this.vertexPositions[4] = var19;
        this.vertexPositions[5] = var20;
        this.vertexPositions[6] = var21;
        this.vertexPositions[7] = var22;
        this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] { var20, var16, var17, var21 }, par2 + par9 + par7, par3 + par9, par2 + par9 + par7 + par9, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] { var15, var19, var22, var18 }, par2, par3 + par9, par2 + par9, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] { var20, var19, var15, var16 }, par2 + par9, par3, par2 + par9 + par7, par3 + par9, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] { var17, var18, var22, var21 }, par2 + par9 + par7, par3 + par9, par2 + par9 + par7 + par7, par3, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] { var16, var15, var18, var17 }, par2 + par9, par3 + par9, par2 + par9 + par7, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] { var19, var20, var21, var22 }, par2 + par9 + par7 + par9, par3 + par9, par2 + par9 + par7 + par9 + par7, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        if (par1ModelRenderer.mirror) {
            for (int var23 = 0; var23 < this.quadList.length; ++var23) {
                this.quadList[var23].flipFace();
            }
        }
    }
    
    public void render(final Tessellator par1Tessellator, final float par2) {
        for (int var3 = 0; var3 < this.quadList.length; ++var3) {
            this.quadList[var3].draw(par1Tessellator, par2);
        }
    }
    
    public ModelBox func_78244_a(final String par1Str) {
        this.field_78247_g = par1Str;
        return this;
    }
}
