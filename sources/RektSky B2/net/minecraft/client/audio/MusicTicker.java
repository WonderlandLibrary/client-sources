package net.minecraft.client.audio;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class MusicTicker implements ITickable
{
    public final Random rand;
    public final Minecraft mc;
    public ISound currentMusic;
    public int timeUntilNextMusic;
    
    public MusicTicker(final Minecraft mcIn) {
        this.rand = new Random();
        this.timeUntilNextMusic = 100;
        this.mc = mcIn;
    }
    
    @Override
    public void update() {
        final MusicType musicticker$musictype = this.mc.getAmbientMusicType();
        if (this.currentMusic != null) {
            if (!musicticker$musictype.getMusicLocation().equals(this.currentMusic.getSoundLocation())) {
                this.mc.getSoundHandler().stopSound(this.currentMusic);
                this.timeUntilNextMusic = MathHelper.getRandomIntegerInRange(this.rand, 0, musicticker$musictype.getMinDelay() / 2);
            }
            if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic)) {
                this.currentMusic = null;
                this.timeUntilNextMusic = Math.min(MathHelper.getRandomIntegerInRange(this.rand, musicticker$musictype.getMinDelay(), musicticker$musictype.getMaxDelay()), this.timeUntilNextMusic);
            }
        }
        if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
            this.playMusicWithType(musicticker$musictype);
        }
    }
    
    public void playMusicWithType(final MusicType musicType) {
        this.currentMusic = PositionedSoundRecord.create(musicType.getMusicLocation());
        this.mc.getSoundHandler().playSound(this.currentMusic);
        this.timeUntilNextMusic = Integer.MAX_VALUE;
    }
    
    public void playBeatGameMusic() {
        if (this.currentMusic != null) {
            this.mc.getSoundHandler().stopSound(this.currentMusic);
            this.currentMusic = null;
            this.timeUntilNextMusic = 0;
        }
    }
    
    public enum MusicType
    {
        MENU(new ResourceLocation("minecraft:sound.menu"), 20, 600), 
        GAME(new ResourceLocation("minecraft:sound.game"), Integer.MAX_VALUE, Integer.MAX_VALUE), 
        CREATIVE(new ResourceLocation("minecraft:sound.game.creative"), 1200, 3600), 
        CREDITS(new ResourceLocation("minecraft:sound.game.end.credits"), Integer.MAX_VALUE, Integer.MAX_VALUE), 
        NETHER(new ResourceLocation("minecraft:sound.game.nether"), 1200, 3600), 
        END_BOSS(new ResourceLocation("minecraft:sound.game.end.dragon"), 0, 0), 
        END(new ResourceLocation("minecraft:sound.game.end"), 6000, 24000);
        
        private final ResourceLocation musicLocation;
        private final int minDelay;
        private final int maxDelay;
        
        private MusicType(final ResourceLocation location, final int minDelayIn, final int maxDelayIn) {
            this.musicLocation = location;
            this.minDelay = minDelayIn;
            this.maxDelay = maxDelayIn;
        }
        
        public ResourceLocation getMusicLocation() {
            return this.musicLocation;
        }
        
        public int getMinDelay() {
            return this.minDelay;
        }
        
        public int getMaxDelay() {
            return this.maxDelay;
        }
    }
}
