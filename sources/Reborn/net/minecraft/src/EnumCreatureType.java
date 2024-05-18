package net.minecraft.src;

public enum EnumCreatureType
{
    monster("monster", 0, (Class)IMob.class, 70, Material.air, false, false), 
    creature("creature", 1, (Class)EntityAnimal.class, 10, Material.air, true, true), 
    ambient("ambient", 2, (Class)EntityAmbientCreature.class, 15, Material.air, true, false), 
    waterCreature("waterCreature", 3, (Class)EntityWaterMob.class, 5, Material.water, true, false);
    
    private final Class creatureClass;
    private final int maxNumberOfCreature;
    private final Material creatureMaterial;
    private final boolean isPeacefulCreature;
    private final boolean isAnimal;
    
    private EnumCreatureType(final String s, final int n, final Class par3Class, final int par4, final Material par5Material, final boolean par6, final boolean par7) {
        this.creatureClass = par3Class;
        this.maxNumberOfCreature = par4;
        this.creatureMaterial = par5Material;
        this.isPeacefulCreature = par6;
        this.isAnimal = par7;
    }
    
    public Class getCreatureClass() {
        return this.creatureClass;
    }
    
    public int getMaxNumberOfCreature() {
        return this.maxNumberOfCreature;
    }
    
    public Material getCreatureMaterial() {
        return this.creatureMaterial;
    }
    
    public boolean getPeacefulCreature() {
        return this.isPeacefulCreature;
    }
    
    public boolean getAnimal() {
        return this.isAnimal;
    }
}
