package net.minecraft.src;

public enum EnumFacing
{
    DOWN("DOWN", 0, 0, 1, 0, -1, 0), 
    UP("UP", 1, 1, 0, 0, 1, 0), 
    NORTH("NORTH", 2, 2, 3, 0, 0, -1), 
    SOUTH("SOUTH", 3, 3, 2, 0, 0, 1), 
    EAST("EAST", 4, 4, 5, -1, 0, 0), 
    WEST("WEST", 5, 5, 4, 1, 0, 0);
    
    private final int order_a;
    private final int order_b;
    private final int frontOffsetX;
    private final int frontOffsetY;
    private final int frontOffsetZ;
    private static final EnumFacing[] faceList;
    
    static {
        faceList = new EnumFacing[6];
        for (final EnumFacing var4 : values()) {
            EnumFacing.faceList[var4.order_a] = var4;
        }
    }
    
    private EnumFacing(final String s, final int n, final int par3, final int par4, final int par5, final int par6, final int par7) {
        this.order_a = par3;
        this.order_b = par4;
        this.frontOffsetX = par5;
        this.frontOffsetY = par6;
        this.frontOffsetZ = par7;
    }
    
    public int getFrontOffsetX() {
        return this.frontOffsetX;
    }
    
    public int getFrontOffsetY() {
        return this.frontOffsetY;
    }
    
    public int getFrontOffsetZ() {
        return this.frontOffsetZ;
    }
    
    public static EnumFacing getFront(final int par0) {
        return EnumFacing.faceList[par0 % EnumFacing.faceList.length];
    }
}
