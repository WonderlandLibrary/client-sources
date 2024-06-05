package net.minecraft.src;

import java.util.*;

public class MapGenNetherBridge extends MapGenStructure
{
    private List spawnList;
    
    public MapGenNetherBridge() {
        (this.spawnList = new ArrayList()).add(new SpawnListEntry(EntityBlaze.class, 10, 2, 3));
        this.spawnList.add(new SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
        this.spawnList.add(new SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
        this.spawnList.add(new SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
    }
    
    public List getSpawnList() {
        return this.spawnList;
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(final int par1, final int par2) {
        final int var3 = par1 >> 4;
        final int var4 = par2 >> 4;
        this.rand.setSeed(var3 ^ var4 << 4 ^ this.worldObj.getSeed());
        this.rand.nextInt();
        return this.rand.nextInt(3) == 0 && par1 == (var3 << 4) + 4 + this.rand.nextInt(8) && par2 == (var4 << 4) + 4 + this.rand.nextInt(8);
    }
    
    @Override
    protected StructureStart getStructureStart(final int par1, final int par2) {
        return new StructureNetherBridgeStart(this.worldObj, this.rand, par1, par2);
    }
}
