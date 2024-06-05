package net.minecraft.src;

public enum EnumGameType
{
    NOT_SET("NOT_SET", 0, -1, ""), 
    SURVIVAL("SURVIVAL", 1, 0, "survival"), 
    CREATIVE("CREATIVE", 2, 1, "creative"), 
    ADVENTURE("ADVENTURE", 3, 2, "adventure");
    
    int id;
    String name;
    
    private EnumGameType(final String s, final int n, final int par3, final String par4Str) {
        this.id = par3;
        this.name = par4Str;
    }
    
    public int getID() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void configurePlayerCapabilities(final PlayerCapabilities par1PlayerCapabilities) {
        if (this == EnumGameType.CREATIVE) {
            par1PlayerCapabilities.allowFlying = true;
            par1PlayerCapabilities.isCreativeMode = true;
            par1PlayerCapabilities.disableDamage = true;
        }
        else {
            par1PlayerCapabilities.allowFlying = false;
            par1PlayerCapabilities.isCreativeMode = false;
            par1PlayerCapabilities.disableDamage = false;
            par1PlayerCapabilities.isFlying = false;
        }
        par1PlayerCapabilities.allowEdit = !this.isAdventure();
    }
    
    public boolean isAdventure() {
        return this == EnumGameType.ADVENTURE;
    }
    
    public boolean isCreative() {
        return this == EnumGameType.CREATIVE;
    }
    
    public boolean isSurvivalOrAdventure() {
        return this == EnumGameType.SURVIVAL || this == EnumGameType.ADVENTURE;
    }
    
    public static EnumGameType getByID(final int par0) {
        for (final EnumGameType var4 : values()) {
            if (var4.id == par0) {
                return var4;
            }
        }
        return EnumGameType.SURVIVAL;
    }
    
    public static EnumGameType getByName(final String par0Str) {
        for (final EnumGameType var4 : values()) {
            if (var4.name.equals(par0Str)) {
                return var4;
            }
        }
        return EnumGameType.SURVIVAL;
    }
}
