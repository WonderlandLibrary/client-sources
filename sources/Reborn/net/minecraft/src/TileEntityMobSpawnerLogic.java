package net.minecraft.src;

class TileEntityMobSpawnerLogic extends MobSpawnerBaseLogic
{
    final TileEntityMobSpawner mobSpawnerEntity;
    
    TileEntityMobSpawnerLogic(final TileEntityMobSpawner par1TileEntityMobSpawner) {
        this.mobSpawnerEntity = par1TileEntityMobSpawner;
    }
    
    @Override
    public void func_98267_a(final int par1) {
        this.mobSpawnerEntity.worldObj.addBlockEvent(this.mobSpawnerEntity.xCoord, this.mobSpawnerEntity.yCoord, this.mobSpawnerEntity.zCoord, Block.mobSpawner.blockID, par1, 0);
    }
    
    @Override
    public World getSpawnerWorld() {
        return this.mobSpawnerEntity.worldObj;
    }
    
    @Override
    public int getSpawnerX() {
        return this.mobSpawnerEntity.xCoord;
    }
    
    @Override
    public int getSpawnerY() {
        return this.mobSpawnerEntity.yCoord;
    }
    
    @Override
    public int getSpawnerZ() {
        return this.mobSpawnerEntity.zCoord;
    }
    
    @Override
    public void setRandomMinecart(final WeightedRandomMinecart par1WeightedRandomMinecart) {
        super.setRandomMinecart(par1WeightedRandomMinecart);
        if (this.getSpawnerWorld() != null) {
            this.getSpawnerWorld().markBlockForUpdate(this.mobSpawnerEntity.xCoord, this.mobSpawnerEntity.yCoord, this.mobSpawnerEntity.zCoord);
        }
    }
}
