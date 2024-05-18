package net.minecraft.client.resources.data;

public class AnimationFrame
{
    private final int frameIndex;
    private final int frameTime;
    
    public AnimationFrame(final int frameIndex, final int frameTime) {
        this.frameIndex = frameIndex;
        this.frameTime = frameTime;
    }
    
    public int getFrameIndex() {
        return this.frameIndex;
    }
    
    public AnimationFrame(final int n) {
        this(n, -" ".length());
    }
    
    public boolean hasNoTime() {
        if (this.frameTime == -" ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getFrameTime() {
        return this.frameTime;
    }
}
