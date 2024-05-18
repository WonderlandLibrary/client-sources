package net.minecraft.src;

public class PositionTextureVertex
{
    public Vec3 vector3D;
    public float texturePositionX;
    public float texturePositionY;
    
    public PositionTextureVertex(final float par1, final float par2, final float par3, final float par4, final float par5) {
        this(Vec3.createVectorHelper(par1, par2, par3), par4, par5);
    }
    
    public PositionTextureVertex setTexturePosition(final float par1, final float par2) {
        return new PositionTextureVertex(this, par1, par2);
    }
    
    public PositionTextureVertex(final PositionTextureVertex par1PositionTextureVertex, final float par2, final float par3) {
        this.vector3D = par1PositionTextureVertex.vector3D;
        this.texturePositionX = par2;
        this.texturePositionY = par3;
    }
    
    public PositionTextureVertex(final Vec3 par1Vec3, final float par2, final float par3) {
        this.vector3D = par1Vec3;
        this.texturePositionX = par2;
        this.texturePositionY = par3;
    }
}
