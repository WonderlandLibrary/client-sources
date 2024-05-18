package net.minecraft.src;

public class ChunkPosition
{
    public final int x;
    public final int y;
    public final int z;
    
    public ChunkPosition(final int par1, final int par2, final int par3) {
        this.x = par1;
        this.y = par2;
        this.z = par3;
    }
    
    public ChunkPosition(final Vec3 par1Vec3) {
        this(MathHelper.floor_double(par1Vec3.xCoord), MathHelper.floor_double(par1Vec3.yCoord), MathHelper.floor_double(par1Vec3.zCoord));
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof ChunkPosition)) {
            return false;
        }
        final ChunkPosition var2 = (ChunkPosition)par1Obj;
        return var2.x == this.x && var2.y == this.y && var2.z == this.z;
    }
    
    @Override
    public int hashCode() {
        return this.x * 8976890 + this.y * 981131 + this.z;
    }
}
