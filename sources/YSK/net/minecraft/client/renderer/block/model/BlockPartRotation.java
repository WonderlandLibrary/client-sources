package net.minecraft.client.renderer.block.model;

import org.lwjgl.util.vector.*;
import net.minecraft.util.*;

public class BlockPartRotation
{
    public final Vector3f origin;
    public final float angle;
    public final EnumFacing.Axis axis;
    public final boolean rescale;
    
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
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public BlockPartRotation(final Vector3f origin, final EnumFacing.Axis axis, final float angle, final boolean rescale) {
        this.origin = origin;
        this.axis = axis;
        this.angle = angle;
        this.rescale = rescale;
    }
}
