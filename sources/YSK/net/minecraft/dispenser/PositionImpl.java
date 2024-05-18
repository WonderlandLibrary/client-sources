package net.minecraft.dispenser;

public class PositionImpl implements IPosition
{
    protected final double z;
    protected final double x;
    protected final double y;
    
    @Override
    public double getY() {
        return this.y;
    }
    
    public PositionImpl(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public double getZ() {
        return this.z;
    }
    
    @Override
    public double getX() {
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
