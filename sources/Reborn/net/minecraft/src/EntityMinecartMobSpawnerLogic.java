package net.minecraft.src;

class EntityMinecartMobSpawnerLogic extends MobSpawnerBaseLogic
{
    final EntityMinecartMobSpawner spawnerMinecart;
    
    EntityMinecartMobSpawnerLogic(final EntityMinecartMobSpawner par1EntityMinecartMobSpawner) {
        this.spawnerMinecart = par1EntityMinecartMobSpawner;
    }
    
    @Override
    public void func_98267_a(final int par1) {
        this.spawnerMinecart.worldObj.setEntityState(this.spawnerMinecart, (byte)par1);
    }
    
    @Override
    public World getSpawnerWorld() {
        return this.spawnerMinecart.worldObj;
    }
    
    @Override
    public int getSpawnerX() {
        return MathHelper.floor_double(this.spawnerMinecart.posX);
    }
    
    @Override
    public int getSpawnerY() {
        return MathHelper.floor_double(this.spawnerMinecart.posY);
    }
    
    @Override
    public int getSpawnerZ() {
        return MathHelper.floor_double(this.spawnerMinecart.posZ);
    }
}
