package net.minecraft.src;

public abstract class WorldSavedData
{
    public final String mapName;
    private boolean dirty;
    
    public WorldSavedData(final String par1Str) {
        this.mapName = par1Str;
    }
    
    public abstract void readFromNBT(final NBTTagCompound p0);
    
    public abstract void writeToNBT(final NBTTagCompound p0);
    
    public void markDirty() {
        this.setDirty(true);
    }
    
    public void setDirty(final boolean par1) {
        this.dirty = par1;
    }
    
    public boolean isDirty() {
        return this.dirty;
    }
}
