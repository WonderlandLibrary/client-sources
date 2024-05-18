package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import net.minecraft.nbt.*;

public class MapGenStructureData extends WorldSavedData
{
    private static final String[] I;
    private NBTTagCompound tagCompound;
    
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
    
    public static String formatChunkCoords(final int n, final int n2) {
        return MapGenStructureData.I["  ".length()] + n + MapGenStructureData.I["   ".length()] + n2 + MapGenStructureData.I[0x36 ^ 0x32];
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.tagCompound = nbtTagCompound.getCompoundTag(MapGenStructureData.I["".length()]);
    }
    
    public MapGenStructureData(final String s) {
        super(s);
        this.tagCompound = new NBTTagCompound();
    }
    
    static {
        I();
    }
    
    public NBTTagCompound getTagCompound() {
        return this.tagCompound;
    }
    
    public void writeInstance(final NBTTagCompound nbtTagCompound, final int n, final int n2) {
        this.tagCompound.setTag(formatChunkCoords(n, n2), nbtTagCompound);
    }
    
    private static void I() {
        (I = new String[0x56 ^ 0x53])["".length()] = I("\u0000\u000e\u00077\u00164\u000e\u0015", "FkfCc");
        MapGenStructureData.I[" ".length()] = I("\f2\t6<82\u001b", "JWhBI");
        MapGenStructureData.I["  ".length()] = I("\u0010", "KfTjZ");
        MapGenStructureData.I["   ".length()] = I("V", "ziSZS");
        MapGenStructureData.I[0xA0 ^ 0xA4] = I("\u001f", "BmwdM");
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setTag(MapGenStructureData.I[" ".length()], this.tagCompound);
    }
}
