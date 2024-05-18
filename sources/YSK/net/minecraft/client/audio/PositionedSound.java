package net.minecraft.client.audio;

import net.minecraft.util.*;

public abstract class PositionedSound implements ISound
{
    protected float pitch;
    protected float zPosF;
    protected float volume;
    protected final ResourceLocation positionedSoundLocation;
    protected int repeatDelay;
    protected boolean repeat;
    protected AttenuationType attenuationType;
    protected float xPosF;
    protected float yPosF;
    
    @Override
    public AttenuationType getAttenuationType() {
        return this.attenuationType;
    }
    
    @Override
    public float getVolume() {
        return this.volume;
    }
    
    @Override
    public ResourceLocation getSoundLocation() {
        return this.positionedSoundLocation;
    }
    
    @Override
    public float getPitch() {
        return this.pitch;
    }
    
    @Override
    public int getRepeatDelay() {
        return this.repeatDelay;
    }
    
    @Override
    public boolean canRepeat() {
        return this.repeat;
    }
    
    @Override
    public float getYPosF() {
        return this.yPosF;
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
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public float getZPosF() {
        return this.zPosF;
    }
    
    @Override
    public float getXPosF() {
        return this.xPosF;
    }
    
    protected PositionedSound(final ResourceLocation positionedSoundLocation) {
        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.repeat = ("".length() != 0);
        this.repeatDelay = "".length();
        this.attenuationType = AttenuationType.LINEAR;
        this.positionedSoundLocation = positionedSoundLocation;
    }
}
