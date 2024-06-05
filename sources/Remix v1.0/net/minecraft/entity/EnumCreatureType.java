package net.minecraft.entity;

import net.minecraft.block.material.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;

public enum EnumCreatureType
{
    MONSTER("MONSTER", 0, (Class)IMob.class, 70, Material.air, false, false), 
    CREATURE("CREATURE", 1, (Class)EntityAnimal.class, 10, Material.air, true, true), 
    AMBIENT("AMBIENT", 2, (Class)EntityAmbientCreature.class, 15, Material.air, true, false), 
    WATER_CREATURE("WATER_CREATURE", 3, (Class)EntityWaterMob.class, 5, Material.water, true, false);
    
    private static final EnumCreatureType[] $VALUES;
    private final Class creatureClass;
    private final int maxNumberOfCreature;
    private final Material creatureMaterial;
    private final boolean isPeacefulCreature;
    private final boolean isAnimal;
    
    private EnumCreatureType(final String p_i1596_1_, final int p_i1596_2_, final Class p_i1596_3_, final int p_i1596_4_, final Material p_i1596_5_, final boolean p_i1596_6_, final boolean p_i1596_7_) {
        this.creatureClass = p_i1596_3_;
        this.maxNumberOfCreature = p_i1596_4_;
        this.creatureMaterial = p_i1596_5_;
        this.isPeacefulCreature = p_i1596_6_;
        this.isAnimal = p_i1596_7_;
    }
    
    public Class getCreatureClass() {
        return this.creatureClass;
    }
    
    public int getMaxNumberOfCreature() {
        return this.maxNumberOfCreature;
    }
    
    public boolean getPeacefulCreature() {
        return this.isPeacefulCreature;
    }
    
    public boolean getAnimal() {
        return this.isAnimal;
    }
    
    static {
        $VALUES = new EnumCreatureType[] { EnumCreatureType.MONSTER, EnumCreatureType.CREATURE, EnumCreatureType.AMBIENT, EnumCreatureType.WATER_CREATURE };
    }
}
