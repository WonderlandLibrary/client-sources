package net.minecraft.src;

import java.util.*;
import java.io.*;
import java.net.*;

public class SoundPool
{
    private Random rand;
    private Map nameToSoundPoolEntriesMapping;
    private List allSoundPoolEntries;
    public int numberOfSoundPoolEntries;
    public boolean isGetRandomSound;
    
    public SoundPool() {
        this.rand = new Random();
        this.nameToSoundPoolEntriesMapping = new HashMap();
        this.allSoundPoolEntries = new ArrayList();
        this.numberOfSoundPoolEntries = 0;
        this.isGetRandomSound = true;
    }
    
    public SoundPoolEntry addSound(String par1Str, final File par2File) {
        try {
            final String var3 = par1Str;
            par1Str = par1Str.substring(0, par1Str.indexOf("."));
            if (this.isGetRandomSound) {
                while (Character.isDigit(par1Str.charAt(par1Str.length() - 1))) {
                    par1Str = par1Str.substring(0, par1Str.length() - 1);
                }
            }
            par1Str = par1Str.replaceAll("/", ".");
            if (!this.nameToSoundPoolEntriesMapping.containsKey(par1Str)) {
                this.nameToSoundPoolEntriesMapping.put(par1Str, new ArrayList());
            }
            final SoundPoolEntry var4 = new SoundPoolEntry(var3, par2File.toURI().toURL());
            this.nameToSoundPoolEntriesMapping.get(par1Str).add(var4);
            this.allSoundPoolEntries.add(var4);
            ++this.numberOfSoundPoolEntries;
            return var4;
        }
        catch (MalformedURLException var5) {
            var5.printStackTrace();
            throw new RuntimeException(var5);
        }
    }
    
    public SoundPoolEntry getRandomSoundFromSoundPool(final String par1Str) {
        final List var2 = this.nameToSoundPoolEntriesMapping.get(par1Str);
        return (var2 == null) ? null : var2.get(this.rand.nextInt(var2.size()));
    }
    
    public SoundPoolEntry getRandomSound() {
        return this.allSoundPoolEntries.isEmpty() ? null : this.allSoundPoolEntries.get(this.rand.nextInt(this.allSoundPoolEntries.size()));
    }
}
