package net.minecraft.world;

import net.minecraft.nbt.*;

public abstract class WorldSavedData
{
    private boolean dirty;
    public final String mapName;
    
    public void setDirty(final boolean dirty) {
        this.dirty = dirty;
    }
    
    public abstract void writeToNBT(final NBTTagCompound p0);
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WorldSavedData(final String mapName) {
        this.mapName = mapName;
    }
    
    public boolean isDirty() {
        return this.dirty;
    }
    
    public abstract void readFromNBT(final NBTTagCompound p0);
    
    public void markDirty() {
        this.setDirty(" ".length() != 0);
    }
}
