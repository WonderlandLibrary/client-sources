package net.minecraft.util;

import net.minecraft.nbt.*;

public class Rotations
{
    protected final float z;
    protected final float x;
    protected final float y;
    
    public Rotations(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Rotations(final NBTTagList list) {
        this.x = list.getFloatAt("".length());
        this.y = list.getFloatAt(" ".length());
        this.z = list.getFloatAt("  ".length());
    }
    
    public float getZ() {
        return this.z;
    }
    
    public float getY() {
        return this.y;
    }
    
    public float getX() {
        return this.x;
    }
    
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
            if (2 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Rotations)) {
            return "".length() != 0;
        }
        final Rotations rotations = (Rotations)o;
        if (this.x == rotations.x && this.y == rotations.y && this.z == rotations.z) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public NBTTagList writeToNBT() {
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagFloat(this.x));
        list.appendTag(new NBTTagFloat(this.y));
        list.appendTag(new NBTTagFloat(this.z));
        return list;
    }
}
