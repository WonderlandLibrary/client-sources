package net.minecraft.client.audio;

import net.minecraft.util.*;

public class SoundPoolEntry
{
    private double pitch;
    private final ResourceLocation location;
    private double volume;
    private final boolean streamingSound;
    
    public ResourceLocation getSoundPoolEntryLocation() {
        return this.location;
    }
    
    public double getPitch() {
        return this.pitch;
    }
    
    public SoundPoolEntry(final ResourceLocation location, final double pitch, final double volume, final boolean streamingSound) {
        this.location = location;
        this.pitch = pitch;
        this.volume = volume;
        this.streamingSound = streamingSound;
    }
    
    public SoundPoolEntry(final SoundPoolEntry soundPoolEntry) {
        this.location = soundPoolEntry.location;
        this.pitch = soundPoolEntry.pitch;
        this.volume = soundPoolEntry.volume;
        this.streamingSound = soundPoolEntry.streamingSound;
    }
    
    public boolean isStreamingSound() {
        return this.streamingSound;
    }
    
    public double getVolume() {
        return this.volume;
    }
    
    public void setPitch(final double pitch) {
        this.pitch = pitch;
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setVolume(final double volume) {
        this.volume = volume;
    }
}
