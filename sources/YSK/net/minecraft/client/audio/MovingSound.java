package net.minecraft.client.audio;

import net.minecraft.util.*;

public abstract class MovingSound extends PositionedSound implements ITickableSound
{
    protected boolean donePlaying;
    
    protected MovingSound(final ResourceLocation resourceLocation) {
        super(resourceLocation);
        this.donePlaying = ("".length() != 0);
    }
    
    @Override
    public boolean isDonePlaying() {
        return this.donePlaying;
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
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
