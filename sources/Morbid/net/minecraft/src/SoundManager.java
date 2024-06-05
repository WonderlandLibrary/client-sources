package net.minecraft.src;

import paulscode.sound.libraries.*;
import paulscode.sound.*;
import paulscode.sound.codecs.*;
import java.io.*;
import java.util.*;

public class SoundManager
{
    private static SoundSystem sndSystem;
    private SoundPool soundPoolSounds;
    private SoundPool soundPoolStreaming;
    private SoundPool soundPoolMusic;
    private int latestSoundID;
    private GameSettings options;
    private Set playingSounds;
    private List field_92072_h;
    private static boolean loaded;
    private Random rand;
    private int ticksBeforeMusic;
    
    static {
        SoundManager.loaded = false;
    }
    
    public SoundManager() {
        this.soundPoolSounds = new SoundPool();
        this.soundPoolStreaming = new SoundPool();
        this.soundPoolMusic = new SoundPool();
        this.latestSoundID = 0;
        this.playingSounds = new HashSet();
        this.field_92072_h = new ArrayList();
        this.rand = new Random();
        this.ticksBeforeMusic = this.rand.nextInt(12000);
    }
    
    public void loadSoundSettings(final GameSettings par1GameSettings) {
        this.soundPoolStreaming.isGetRandomSound = false;
        this.options = par1GameSettings;
        if (!SoundManager.loaded && (par1GameSettings == null || par1GameSettings.soundVolume != 0.0f || par1GameSettings.musicVolume != 0.0f)) {
            this.tryToSetLibraryAndCodecs();
        }
    }
    
    private void tryToSetLibraryAndCodecs() {
        try {
            final float var1 = this.options.soundVolume;
            final float var2 = this.options.musicVolume;
            this.options.soundVolume = 0.0f;
            this.options.musicVolume = 0.0f;
            this.options.saveOptions();
            SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
            SoundSystemConfig.setCodec("mus", CodecMus.class);
            SoundSystemConfig.setCodec("wav", CodecWav.class);
            SoundManager.sndSystem = new SoundSystem();
            this.options.soundVolume = var1;
            this.options.musicVolume = var2;
            this.options.saveOptions();
        }
        catch (Throwable var3) {
            var3.printStackTrace();
            System.err.println("error linking with the LibraryJavaSound plug-in");
        }
        SoundManager.loaded = true;
    }
    
    public void onSoundOptionsChanged() {
        if (!SoundManager.loaded && (this.options.soundVolume != 0.0f || this.options.musicVolume != 0.0f)) {
            this.tryToSetLibraryAndCodecs();
        }
        if (SoundManager.loaded) {
            if (this.options.musicVolume == 0.0f) {
                SoundManager.sndSystem.stop("BgMusic");
                SoundManager.sndSystem.stop("streaming");
            }
            else {
                SoundManager.sndSystem.setVolume("BgMusic", this.options.musicVolume);
                SoundManager.sndSystem.setVolume("streaming", this.options.musicVolume);
            }
        }
    }
    
    public void closeMinecraft() {
        if (SoundManager.loaded) {
            SoundManager.sndSystem.cleanup();
        }
    }
    
    public void addSound(final String par1Str, final File par2File) {
        this.soundPoolSounds.addSound(par1Str, par2File);
    }
    
    public void addStreaming(final String par1Str, final File par2File) {
        this.soundPoolStreaming.addSound(par1Str, par2File);
    }
    
    public void addMusic(final String par1Str, final File par2File) {
        this.soundPoolMusic.addSound(par1Str, par2File);
    }
    
    public void playRandomMusicIfReady() {
        if (SoundManager.loaded && this.options.musicVolume != 0.0f && !SoundManager.sndSystem.playing("BgMusic") && !SoundManager.sndSystem.playing("streaming")) {
            if (this.ticksBeforeMusic > 0) {
                --this.ticksBeforeMusic;
                return;
            }
            final SoundPoolEntry var1 = this.soundPoolMusic.getRandomSound();
            if (var1 != null) {
                this.ticksBeforeMusic = this.rand.nextInt(12000) + 12000;
                SoundManager.sndSystem.backgroundMusic("BgMusic", var1.soundUrl, var1.soundName, false);
                SoundManager.sndSystem.setVolume("BgMusic", this.options.musicVolume);
                SoundManager.sndSystem.play("BgMusic");
            }
        }
    }
    
    public void setListener(final EntityLiving par1EntityLiving, final float par2) {
        if (SoundManager.loaded && this.options.soundVolume != 0.0f && par1EntityLiving != null) {
            final float var3 = par1EntityLiving.prevRotationPitch + (par1EntityLiving.rotationPitch - par1EntityLiving.prevRotationPitch) * par2;
            final float var4 = par1EntityLiving.prevRotationYaw + (par1EntityLiving.rotationYaw - par1EntityLiving.prevRotationYaw) * par2;
            final double var5 = par1EntityLiving.prevPosX + (par1EntityLiving.posX - par1EntityLiving.prevPosX) * par2;
            final double var6 = par1EntityLiving.prevPosY + (par1EntityLiving.posY - par1EntityLiving.prevPosY) * par2;
            final double var7 = par1EntityLiving.prevPosZ + (par1EntityLiving.posZ - par1EntityLiving.prevPosZ) * par2;
            final float var8 = MathHelper.cos(-var4 * 0.017453292f - 3.1415927f);
            final float var9 = MathHelper.sin(-var4 * 0.017453292f - 3.1415927f);
            final float var10 = -var9;
            final float var11 = -MathHelper.sin(-var3 * 0.017453292f - 3.1415927f);
            final float var12 = -var8;
            final float var13 = 0.0f;
            final float var14 = 1.0f;
            final float var15 = 0.0f;
            SoundManager.sndSystem.setListenerPosition((float)var5, (float)var6, (float)var7);
            SoundManager.sndSystem.setListenerOrientation(var10, var11, var12, var13, var14, var15);
        }
    }
    
    public void stopAllSounds() {
        for (final String var2 : this.playingSounds) {
            SoundManager.sndSystem.stop(var2);
        }
        this.playingSounds.clear();
    }
    
    public void playStreaming(final String par1Str, final float par2, final float par3, final float par4) {
        if (SoundManager.loaded && (this.options.soundVolume != 0.0f || par1Str == null)) {
            final String var5 = "streaming";
            if (SoundManager.sndSystem.playing(var5)) {
                SoundManager.sndSystem.stop(var5);
            }
            if (par1Str != null) {
                final SoundPoolEntry var6 = this.soundPoolStreaming.getRandomSoundFromSoundPool(par1Str);
                if (var6 != null) {
                    if (SoundManager.sndSystem.playing("BgMusic")) {
                        SoundManager.sndSystem.stop("BgMusic");
                    }
                    final float var7 = 16.0f;
                    SoundManager.sndSystem.newStreamingSource(true, var5, var6.soundUrl, var6.soundName, false, par2, par3, par4, 2, var7 * 4.0f);
                    SoundManager.sndSystem.setVolume(var5, 0.5f * this.options.soundVolume);
                    SoundManager.sndSystem.play(var5);
                }
            }
        }
    }
    
    public void updateSoundLocation(final Entity par1Entity) {
        this.updateSoundLocation(par1Entity, par1Entity);
    }
    
    public void updateSoundLocation(final Entity par1Entity, final Entity par2Entity) {
        final String var3 = "entity_" + par1Entity.entityId;
        if (this.playingSounds.contains(var3)) {
            if (SoundManager.sndSystem.playing(var3)) {
                SoundManager.sndSystem.setPosition(var3, (float)par2Entity.posX, (float)par2Entity.posY, (float)par2Entity.posZ);
                SoundManager.sndSystem.setVelocity(var3, (float)par2Entity.motionX, (float)par2Entity.motionY, (float)par2Entity.motionZ);
            }
            else {
                this.playingSounds.remove(var3);
            }
        }
    }
    
    public boolean isEntitySoundPlaying(final Entity par1Entity) {
        if (par1Entity != null && SoundManager.loaded) {
            final String var2 = "entity_" + par1Entity.entityId;
            return SoundManager.sndSystem.playing(var2);
        }
        return false;
    }
    
    public void stopEntitySound(final Entity par1Entity) {
        if (par1Entity != null && SoundManager.loaded) {
            final String var2 = "entity_" + par1Entity.entityId;
            if (this.playingSounds.contains(var2)) {
                if (SoundManager.sndSystem.playing(var2)) {
                    SoundManager.sndSystem.stop(var2);
                }
                this.playingSounds.remove(var2);
            }
        }
    }
    
    public void setEntitySoundVolume(final Entity par1Entity, final float par2) {
        if (par1Entity != null && SoundManager.loaded && SoundManager.loaded && this.options.soundVolume != 0.0f) {
            final String var3 = "entity_" + par1Entity.entityId;
            if (SoundManager.sndSystem.playing(var3)) {
                SoundManager.sndSystem.setVolume(var3, par2 * this.options.soundVolume);
            }
        }
    }
    
    public void setEntitySoundPitch(final Entity par1Entity, final float par2) {
        if (par1Entity != null && SoundManager.loaded && SoundManager.loaded && this.options.soundVolume != 0.0f) {
            final String var3 = "entity_" + par1Entity.entityId;
            if (SoundManager.sndSystem.playing(var3)) {
                SoundManager.sndSystem.setPitch(var3, par2);
            }
        }
    }
    
    public void playEntitySound(final String par1Str, final Entity par2Entity, float par3, final float par4, final boolean par5) {
        if (par2Entity != null && SoundManager.loaded && (this.options.soundVolume != 0.0f || par1Str == null)) {
            final String var6 = "entity_" + par2Entity.entityId;
            if (this.playingSounds.contains(var6)) {
                this.updateSoundLocation(par2Entity);
            }
            else {
                if (SoundManager.sndSystem.playing(var6)) {
                    SoundManager.sndSystem.stop(var6);
                }
                if (par1Str == null) {
                    return;
                }
                final SoundPoolEntry var7 = this.soundPoolSounds.getRandomSoundFromSoundPool(par1Str);
                if (var7 != null && par3 > 0.0f) {
                    float var8 = 16.0f;
                    if (par3 > 1.0f) {
                        var8 *= par3;
                    }
                    SoundManager.sndSystem.newSource(par5, var6, var7.soundUrl, var7.soundName, false, (float)par2Entity.posX, (float)par2Entity.posY, (float)par2Entity.posZ, 2, var8);
                    SoundManager.sndSystem.setLooping(var6, true);
                    SoundManager.sndSystem.setPitch(var6, par4);
                    if (par3 > 1.0f) {
                        par3 = 1.0f;
                    }
                    SoundManager.sndSystem.setVolume(var6, par3 * this.options.soundVolume);
                    SoundManager.sndSystem.setVelocity(var6, (float)par2Entity.motionX, (float)par2Entity.motionY, (float)par2Entity.motionZ);
                    SoundManager.sndSystem.play(var6);
                    this.playingSounds.add(var6);
                }
            }
        }
    }
    
    public void playSound(final String par1Str, final float par2, final float par3, final float par4, float par5, final float par6) {
        if (SoundManager.loaded && this.options.soundVolume != 0.0f) {
            final SoundPoolEntry var7 = this.soundPoolSounds.getRandomSoundFromSoundPool(par1Str);
            if (var7 != null && par5 > 0.0f) {
                this.latestSoundID = (this.latestSoundID + 1) % 256;
                final String var8 = "sound_" + this.latestSoundID;
                float var9 = 16.0f;
                if (par5 > 1.0f) {
                    var9 *= par5;
                }
                SoundManager.sndSystem.newSource(par5 > 1.0f, var8, var7.soundUrl, var7.soundName, false, par2, par3, par4, 2, var9);
                SoundManager.sndSystem.setPitch(var8, par6);
                if (par5 > 1.0f) {
                    par5 = 1.0f;
                }
                SoundManager.sndSystem.setVolume(var8, par5 * this.options.soundVolume);
                SoundManager.sndSystem.play(var8);
            }
        }
    }
    
    public void playSoundFX(final String par1Str, float par2, final float par3) {
        if (SoundManager.loaded && this.options.soundVolume != 0.0f) {
            final SoundPoolEntry var4 = this.soundPoolSounds.getRandomSoundFromSoundPool(par1Str);
            if (var4 != null) {
                this.latestSoundID = (this.latestSoundID + 1) % 256;
                final String var5 = "sound_" + this.latestSoundID;
                SoundManager.sndSystem.newSource(false, var5, var4.soundUrl, var4.soundName, false, 0.0f, 0.0f, 0.0f, 0, 0.0f);
                if (par2 > 1.0f) {
                    par2 = 1.0f;
                }
                par2 *= 0.25f;
                SoundManager.sndSystem.setPitch(var5, par3);
                SoundManager.sndSystem.setVolume(var5, par2 * this.options.soundVolume);
                SoundManager.sndSystem.play(var5);
            }
        }
    }
    
    public void pauseAllSounds() {
        for (final String var2 : this.playingSounds) {
            SoundManager.sndSystem.pause(var2);
        }
    }
    
    public void resumeAllSounds() {
        for (final String var2 : this.playingSounds) {
            SoundManager.sndSystem.play(var2);
        }
    }
    
    public void func_92071_g() {
        if (!this.field_92072_h.isEmpty()) {
            final Iterator var1 = this.field_92072_h.iterator();
            while (var1.hasNext()) {
                final ScheduledSound scheduledSound;
                final ScheduledSound var2 = scheduledSound = var1.next();
                --scheduledSound.field_92064_g;
                if (var2.field_92064_g <= 0) {
                    this.playSound(var2.field_92069_a, var2.field_92067_b, var2.field_92068_c, var2.field_92065_d, var2.field_92066_e, var2.field_92063_f);
                    var1.remove();
                }
            }
        }
    }
    
    public void func_92070_a(final String par1Str, final float par2, final float par3, final float par4, final float par5, final float par6, final int par7) {
        this.field_92072_h.add(new ScheduledSound(par1Str, par2, par3, par4, par5, par6, par7));
    }
}
