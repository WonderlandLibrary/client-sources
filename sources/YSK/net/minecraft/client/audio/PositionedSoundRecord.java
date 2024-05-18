package net.minecraft.client.audio;

import net.minecraft.util.*;

public class PositionedSoundRecord extends PositionedSound
{
    private PositionedSoundRecord(final ResourceLocation resourceLocation, final float volume, final float pitch, final boolean repeat, final int repeatDelay, final ISound.AttenuationType attenuationType, final float xPosF, final float yPosF, final float zPosF) {
        super(resourceLocation);
        this.volume = volume;
        this.pitch = pitch;
        this.xPosF = xPosF;
        this.yPosF = yPosF;
        this.zPosF = zPosF;
        this.repeat = repeat;
        this.repeatDelay = repeatDelay;
        this.attenuationType = attenuationType;
    }
    
    public static PositionedSoundRecord create(final ResourceLocation resourceLocation) {
        return new PositionedSoundRecord(resourceLocation, 1.0f, 1.0f, "".length() != 0, "".length(), ISound.AttenuationType.NONE, 0.0f, 0.0f, 0.0f);
    }
    
    public static PositionedSoundRecord create(final ResourceLocation resourceLocation, final float n) {
        return new PositionedSoundRecord(resourceLocation, 0.25f, n, "".length() != 0, "".length(), ISound.AttenuationType.NONE, 0.0f, 0.0f, 0.0f);
    }
    
    public static PositionedSoundRecord create(final ResourceLocation resourceLocation, final float n, final float n2, final float n3) {
        return new PositionedSoundRecord(resourceLocation, 4.0f, 1.0f, "".length() != 0, "".length(), ISound.AttenuationType.LINEAR, n, n2, n3);
    }
    
    public PositionedSoundRecord(final ResourceLocation resourceLocation, final float n, final float n2, final float n3, final float n4, final float n5) {
        this(resourceLocation, n, n2, "".length() != 0, "".length(), ISound.AttenuationType.LINEAR, n3, n4, n5);
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
