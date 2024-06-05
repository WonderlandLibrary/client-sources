package net.minecraft.item;

import java.util.*;
import com.google.common.collect.*;

public enum FishType
{
    COD("COD", 0, 0, "cod", 2, 0.1f, 5, 0.6f), 
    SALMON("SALMON", 1, 1, "salmon", 2, 0.1f, 6, 0.8f), 
    CLOWNFISH("CLOWNFISH", 2, 2, "clownfish", 1, 0.1f), 
    PUFFERFISH("PUFFERFISH", 3, 3, "pufferfish", 1, 0.1f);
    
    private static final Map itemDamageToFishTypeMap;
    private static final FishType[] $VALUES;
    private final int itemDamage;
    private final String unlocalizedNamePart;
    private final int uncookedHealAmount;
    private final float uncookedSaturationModifier;
    private final int cookedHealAmount;
    private final float cookedSaturationModifier;
    private boolean cookable;
    
    private FishType(final String p_i45336_1_, final int p_i45336_2_, final int p_i45336_3_, final String p_i45336_4_, final int p_i45336_5_, final float p_i45336_6_, final int p_i45336_7_, final float p_i45336_8_) {
        this.cookable = false;
        this.itemDamage = p_i45336_3_;
        this.unlocalizedNamePart = p_i45336_4_;
        this.uncookedHealAmount = p_i45336_5_;
        this.uncookedSaturationModifier = p_i45336_6_;
        this.cookedHealAmount = p_i45336_7_;
        this.cookedSaturationModifier = p_i45336_8_;
        this.cookable = true;
    }
    
    private FishType(final String p_i45337_1_, final int p_i45337_2_, final int p_i45337_3_, final String p_i45337_4_, final int p_i45337_5_, final float p_i45337_6_) {
        this.cookable = false;
        this.itemDamage = p_i45337_3_;
        this.unlocalizedNamePart = p_i45337_4_;
        this.uncookedHealAmount = p_i45337_5_;
        this.uncookedSaturationModifier = p_i45337_6_;
        this.cookedHealAmount = 0;
        this.cookedSaturationModifier = 0.0f;
        this.cookable = false;
    }
    
    public static FishType getFishTypeForItemDamage(final int p_150974_0_) {
        final FishType var1 = FishType.itemDamageToFishTypeMap.get(p_150974_0_);
        return (var1 == null) ? FishType.COD : var1;
    }
    
    public static FishType getFishTypeForItemStack(final ItemStack p_150978_0_) {
        return (p_150978_0_.getItem() instanceof ItemFishFood) ? getFishTypeForItemDamage(p_150978_0_.getMetadata()) : FishType.COD;
    }
    
    public int getItemDamage() {
        return this.itemDamage;
    }
    
    public String getUnlocalizedNamePart() {
        return this.unlocalizedNamePart;
    }
    
    public int getUncookedHealAmount() {
        return this.uncookedHealAmount;
    }
    
    public float getUncookedSaturationModifier() {
        return this.uncookedSaturationModifier;
    }
    
    public int getCookedHealAmount() {
        return this.cookedHealAmount;
    }
    
    public float getCookedSaturationModifier() {
        return this.cookedSaturationModifier;
    }
    
    public boolean getCookable() {
        return this.cookable;
    }
    
    static {
        itemDamageToFishTypeMap = Maps.newHashMap();
        $VALUES = new FishType[] { FishType.COD, FishType.SALMON, FishType.CLOWNFISH, FishType.PUFFERFISH };
        for (final FishType var4 : values()) {
            FishType.itemDamageToFishTypeMap.put(var4.getItemDamage(), var4);
        }
    }
}
