package net.minecraft.client.audio;

import net.minecraft.util.*;

public enum MusicType
{
    MENU("MENU", 0, new ResourceLocation("minecraft:music.menu"), 20, 600), 
    GAME("GAME", 1, new ResourceLocation("minecraft:music.game"), 12000, 24000), 
    CREATIVE("CREATIVE", 2, new ResourceLocation("minecraft:music.game.creative"), 1200, 3600), 
    CREDITS("CREDITS", 3, new ResourceLocation("minecraft:music.game.end.credits"), Integer.MAX_VALUE, Integer.MAX_VALUE), 
    NETHER("NETHER", 4, new ResourceLocation("minecraft:music.game.nether"), 1200, 3600), 
    END_BOSS("END_BOSS", 5, new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0), 
    END("END", 6, new ResourceLocation("minecraft:music.game.end"), 6000, 24000);
    
    private static final MusicType[] $VALUES;
    private final ResourceLocation musicLocation;
    private final int minDelay;
    private final int maxDelay;
    
    private MusicType(final String p_i45111_1_, final int p_i45111_2_, final ResourceLocation location, final int p_i45111_4_, final int p_i45111_5_) {
        this.musicLocation = location;
        this.minDelay = p_i45111_4_;
        this.maxDelay = p_i45111_5_;
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
    
    static {
        $VALUES = new MusicType[] { MusicType.MENU, MusicType.GAME, MusicType.CREATIVE, MusicType.CREDITS, MusicType.NETHER, MusicType.END_BOSS, MusicType.END };
    }
}
