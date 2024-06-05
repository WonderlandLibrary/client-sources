package net.minecraft.util;

public static final class MutableBlockPos extends BlockPos
{
    public int x;
    public int y;
    public int z;
    
    private MutableBlockPos(final int x_, final int y_, final int z_) {
        super(0, 0, 0);
        this.x = x_;
        this.y = y_;
        this.z = z_;
    }
    
    MutableBlockPos(final int p_i46025_1_, final int p_i46025_2_, final int p_i46025_3_, final Object p_i46025_4_) {
        this(p_i46025_1_, p_i46025_2_, p_i46025_3_);
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    @Override
    public int getY() {
        return this.y;
    }
    
    @Override
    public int getZ() {
        return this.z;
    }
    
    @Override
    public Vec3i crossProduct(final Vec3i vec) {
        return super.crossProductBP(vec);
    }
}
