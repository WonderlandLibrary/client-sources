package net.minecraft.world;

import net.minecraft.entity.player.*;

public enum GameType
{
    NOT_SET("NOT_SET", 0, -1, ""), 
    SURVIVAL("SURVIVAL", 1, 0, "survival"), 
    CREATIVE("CREATIVE", 2, 1, "creative"), 
    ADVENTURE("ADVENTURE", 3, 2, "adventure"), 
    SPECTATOR("SPECTATOR", 4, 3, "spectator");
    
    private static final GameType[] $VALUES;
    int id;
    String name;
    
    private GameType(final String p_i1956_1_, final int p_i1956_2_, final int typeId, final String nameIn) {
        this.id = typeId;
        this.name = nameIn;
    }
    
    public static GameType getByID(final int idIn) {
        for (final GameType var4 : values()) {
            if (var4.id == idIn) {
                return var4;
            }
        }
        return GameType.SURVIVAL;
    }
    
    public static GameType getByName(final String p_77142_0_) {
        for (final GameType var4 : values()) {
            if (var4.name.equals(p_77142_0_)) {
                return var4;
            }
        }
        return GameType.SURVIVAL;
    }
    
    public int getID() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void configurePlayerCapabilities(final PlayerCapabilities capabilities) {
        if (this == GameType.CREATIVE) {
            capabilities.allowFlying = true;
            capabilities.isCreativeMode = true;
            capabilities.disableDamage = true;
        }
        else if (this == GameType.SPECTATOR) {
            capabilities.allowFlying = true;
            capabilities.isCreativeMode = false;
            capabilities.disableDamage = true;
            capabilities.isFlying = true;
        }
        else {
            capabilities.allowFlying = false;
            capabilities.isCreativeMode = false;
            capabilities.disableDamage = false;
            capabilities.isFlying = false;
        }
        capabilities.allowEdit = !this.isAdventure();
    }
    
    public boolean isAdventure() {
        return this == GameType.ADVENTURE || this == GameType.SPECTATOR;
    }
    
    public boolean isCreative() {
        return this == GameType.CREATIVE;
    }
    
    public boolean isSurvivalOrAdventure() {
        return this == GameType.SURVIVAL || this == GameType.ADVENTURE;
    }
    
    static {
        $VALUES = new GameType[] { GameType.NOT_SET, GameType.SURVIVAL, GameType.CREATIVE, GameType.ADVENTURE, GameType.SPECTATOR };
    }
}
