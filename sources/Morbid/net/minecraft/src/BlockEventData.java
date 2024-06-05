package net.minecraft.src;

public class BlockEventData
{
    private int coordX;
    private int coordY;
    private int coordZ;
    private int blockID;
    private int eventID;
    private int eventParameter;
    
    public BlockEventData(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.coordX = par1;
        this.coordY = par2;
        this.coordZ = par3;
        this.eventID = par5;
        this.eventParameter = par6;
        this.blockID = par4;
    }
    
    public int getX() {
        return this.coordX;
    }
    
    public int getY() {
        return this.coordY;
    }
    
    public int getZ() {
        return this.coordZ;
    }
    
    public int getEventID() {
        return this.eventID;
    }
    
    public int getEventParameter() {
        return this.eventParameter;
    }
    
    public int getBlockID() {
        return this.blockID;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof BlockEventData)) {
            return false;
        }
        final BlockEventData var2 = (BlockEventData)par1Obj;
        return this.coordX == var2.coordX && this.coordY == var2.coordY && this.coordZ == var2.coordZ && this.eventID == var2.eventID && this.eventParameter == var2.eventParameter && this.blockID == var2.blockID;
    }
    
    @Override
    public String toString() {
        return "TE(" + this.coordX + "," + this.coordY + "," + this.coordZ + ")," + this.eventID + "," + this.eventParameter + "," + this.blockID;
    }
}
