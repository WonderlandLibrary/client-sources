package net.minecraft.src;

public class TexturedQuad
{
    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    private boolean invertNormal;
    
    public TexturedQuad(final PositionTextureVertex[] par1ArrayOfPositionTextureVertex) {
        this.nVertices = 0;
        this.invertNormal = false;
        this.vertexPositions = par1ArrayOfPositionTextureVertex;
        this.nVertices = par1ArrayOfPositionTextureVertex.length;
    }
    
    public TexturedQuad(final PositionTextureVertex[] par1ArrayOfPositionTextureVertex, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7) {
        this(par1ArrayOfPositionTextureVertex);
        final float var8 = 0.0f / par6;
        final float var9 = 0.0f / par7;
        par1ArrayOfPositionTextureVertex[0] = par1ArrayOfPositionTextureVertex[0].setTexturePosition(par4 / par6 - var8, par3 / par7 + var9);
        par1ArrayOfPositionTextureVertex[1] = par1ArrayOfPositionTextureVertex[1].setTexturePosition(par2 / par6 + var8, par3 / par7 + var9);
        par1ArrayOfPositionTextureVertex[2] = par1ArrayOfPositionTextureVertex[2].setTexturePosition(par2 / par6 + var8, par5 / par7 - var9);
        par1ArrayOfPositionTextureVertex[3] = par1ArrayOfPositionTextureVertex[3].setTexturePosition(par4 / par6 - var8, par5 / par7 - var9);
    }
    
    public void flipFace() {
        final PositionTextureVertex[] var1 = new PositionTextureVertex[this.vertexPositions.length];
        for (int var2 = 0; var2 < this.vertexPositions.length; ++var2) {
            var1[var2] = this.vertexPositions[this.vertexPositions.length - var2 - 1];
        }
        this.vertexPositions = var1;
    }
    
    public void draw(final Tessellator par1Tessellator, final float par2) {
        final Vec3 var3 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[0].vector3D);
        final Vec3 var4 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[2].vector3D);
        final Vec3 var5 = var4.crossProduct(var3).normalize();
        par1Tessellator.startDrawingQuads();
        if (this.invertNormal) {
            par1Tessellator.setNormal(-(float)var5.xCoord, -(float)var5.yCoord, -(float)var5.zCoord);
        }
        else {
            par1Tessellator.setNormal((float)var5.xCoord, (float)var5.yCoord, (float)var5.zCoord);
        }
        for (int var6 = 0; var6 < 4; ++var6) {
            final PositionTextureVertex var7 = this.vertexPositions[var6];
            par1Tessellator.addVertexWithUV((float)var7.vector3D.xCoord * par2, (float)var7.vector3D.yCoord * par2, (float)var7.vector3D.zCoord * par2, var7.texturePositionX, var7.texturePositionY);
        }
        par1Tessellator.draw();
    }
}
