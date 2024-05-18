package net.minecraft.entity;

import net.minecraft.block.material.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;

public enum EnumCreatureType
{
    AMBIENT((Class<? extends IAnimals>)EntityAmbientCreature.class, 0x85 ^ 0x8A, Material.air, (boolean)(" ".length() != 0), (boolean)("".length() != 0)), 
    WATER_CREATURE((Class<? extends IAnimals>)EntityWaterMob.class, 0x73 ^ 0x76, Material.water, (boolean)(" ".length() != 0), (boolean)("".length() != 0));
    
    private static final EnumCreatureType[] ENUM$VALUES;
    private final Material creatureMaterial;
    
    MONSTER((Class<? extends IAnimals>)IMob.class, 0x6B ^ 0x2D, Material.air, (boolean)("".length() != 0), (boolean)("".length() != 0));
    
    private final int maxNumberOfCreature;
    private final boolean isAnimal;
    private final Class<? extends IAnimals> creatureClass;
    
    CREATURE((Class<? extends IAnimals>)EntityAnimal.class, 0x55 ^ 0x5F, Material.air, (boolean)(" ".length() != 0), (boolean)(" ".length() != 0));
    
    private final boolean isPeacefulCreature;
    private static final String[] I;
    
    private static void I() {
        (I = new String[0x50 ^ 0x54])["".length()] = I(" \n\u0019;\u0017(\u0017", "mEWhC");
        EnumCreatureType.I[" ".length()] = I(":\u001f? \u0002,\u001f?", "yMzaV");
        EnumCreatureType.I["  ".length()] = I("\u0012)\u000f\u0004?\u001d0", "SdMMz");
        EnumCreatureType.I["   ".length()] = I("\u0011\u0005\u0001\"\u0015\u0019\u0007\u0007\"\u0006\u0012\u0011\u0007\"", "FDUgG");
    }
    
    private EnumCreatureType(final Class<? extends IAnimals> creatureClass, final int maxNumberOfCreature, final Material creatureMaterial, final boolean isPeacefulCreature, final boolean isAnimal) {
        this.creatureClass = creatureClass;
        this.maxNumberOfCreature = maxNumberOfCreature;
        this.creatureMaterial = creatureMaterial;
        this.isPeacefulCreature = isPeacefulCreature;
        this.isAnimal = isAnimal;
    }
    
    public int getMaxNumberOfCreature() {
        return this.maxNumberOfCreature;
    }
    
    static {
        I();
        final EnumCreatureType[] enum$VALUES = new EnumCreatureType[0x57 ^ 0x53];
        enum$VALUES["".length()] = EnumCreatureType.MONSTER;
        enum$VALUES[" ".length()] = EnumCreatureType.CREATURE;
        enum$VALUES["  ".length()] = EnumCreatureType.AMBIENT;
        enum$VALUES["   ".length()] = EnumCreatureType.WATER_CREATURE;
        ENUM$VALUES = enum$VALUES;
    }
    
    public Class<? extends IAnimals> getCreatureClass() {
        return this.creatureClass;
    }
    
    public boolean getPeacefulCreature() {
        return this.isPeacefulCreature;
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean getAnimal() {
        return this.isAnimal;
    }
}
