package net.minecraft.src;

import java.net.*;

public class SoundPoolEntry
{
    public String soundName;
    public URL soundUrl;
    
    public SoundPoolEntry(final String par1Str, final URL par2URL) {
        this.soundName = par1Str;
        this.soundUrl = par2URL;
    }
}
