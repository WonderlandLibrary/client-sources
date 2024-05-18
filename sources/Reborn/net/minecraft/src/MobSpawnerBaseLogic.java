package net.minecraft.src;

import java.util.*;

public abstract class MobSpawnerBaseLogic
{
    public int spawnDelay;
    private String mobID;
    private List minecartToSpawn;
    private WeightedRandomMinecart randomMinecart;
    public double field_98287_c;
    public double field_98284_d;
    private int minSpawnDelay;
    private int maxSpawnDelay;
    private int spawnCount;
    private Entity field_98291_j;
    private int maxNearbyEntities;
    private int activatingRangeFromPlayer;
    private int spawnRange;
    
    public MobSpawnerBaseLogic() {
        this.spawnDelay = 20;
        this.mobID = "Pig";
        this.minecartToSpawn = null;
        this.randomMinecart = null;
        this.field_98284_d = 0.0;
        this.minSpawnDelay = 200;
        this.maxSpawnDelay = 800;
        this.spawnCount = 4;
        this.maxNearbyEntities = 6;
        this.activatingRangeFromPlayer = 16;
        this.spawnRange = 4;
    }
    
    public String getEntityNameToSpawn() {
        if (this.getRandomMinecart() == null) {
            if (this.mobID.equals("Minecart")) {
                this.mobID = "MinecartRideable";
            }
            return this.mobID;
        }
        return this.getRandomMinecart().minecartName;
    }
    
    public void setMobID(final String par1Str) {
        this.mobID = par1Str;
    }
    
    public boolean canRun() {
        return this.getSpawnerWorld().getClosestPlayer(this.getSpawnerX() + 0.5, this.getSpawnerY() + 0.5, this.getSpawnerZ() + 0.5, this.activatingRangeFromPlayer) != null;
    }
    
    public void updateSpawner() {
        if (this.canRun()) {
            if (this.getSpawnerWorld().isRemote) {
                final double var1 = this.getSpawnerX() + this.getSpawnerWorld().rand.nextFloat();
                final double var2 = this.getSpawnerY() + this.getSpawnerWorld().rand.nextFloat();
                final double var3 = this.getSpawnerZ() + this.getSpawnerWorld().rand.nextFloat();
                this.getSpawnerWorld().spawnParticle("smoke", var1, var2, var3, 0.0, 0.0, 0.0);
                this.getSpawnerWorld().spawnParticle("flame", var1, var2, var3, 0.0, 0.0, 0.0);
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                }
                this.field_98284_d = this.field_98287_c;
                this.field_98287_c = (this.field_98287_c + 1000.0f / (this.spawnDelay + 200.0f)) % 360.0;
            }
            else {
                if (this.spawnDelay == -1) {
                    this.func_98273_j();
                }
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                    return;
                }
                boolean var4 = false;
                for (int var5 = 0; var5 < this.spawnCount; ++var5) {
                    final Entity var6 = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());
                    if (var6 == null) {
                        return;
                    }
                    final int var7 = this.getSpawnerWorld().getEntitiesWithinAABB(var6.getClass(), AxisAlignedBB.getAABBPool().getAABB(this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ(), this.getSpawnerX() + 1, this.getSpawnerY() + 1, this.getSpawnerZ() + 1).expand(this.spawnRange * 2, 4.0, this.spawnRange * 2)).size();
                    if (var7 >= this.maxNearbyEntities) {
                        this.func_98273_j();
                        return;
                    }
                    final double var3 = this.getSpawnerX() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * this.spawnRange;
                    final double var8 = this.getSpawnerY() + this.getSpawnerWorld().rand.nextInt(3) - 1;
                    final double var9 = this.getSpawnerZ() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * this.spawnRange;
                    final EntityLiving var10 = (var6 instanceof EntityLiving) ? ((EntityLiving)var6) : null;
                    var6.setLocationAndAngles(var3, var8, var9, this.getSpawnerWorld().rand.nextFloat() * 360.0f, 0.0f);
                    if (var10 == null || var10.getCanSpawnHere()) {
                        this.func_98265_a(var6);
                        this.getSpawnerWorld().playAuxSFX(2004, this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ(), 0);
                        if (var10 != null) {
                            var10.spawnExplosionParticle();
                        }
                        var4 = true;
                    }
                }
                if (var4) {
                    this.func_98273_j();
                }
            }
        }
    }
    
    public Entity func_98265_a(final Entity par1Entity) {
        if (this.getRandomMinecart() != null) {
            NBTTagCompound var2 = new NBTTagCompound();
            par1Entity.addEntityID(var2);
            for (final NBTBase var4 : this.getRandomMinecart().field_98222_b.getTags()) {
                var2.setTag(var4.getName(), var4.copy());
            }
            par1Entity.readFromNBT(var2);
            if (par1Entity.worldObj != null) {
                par1Entity.worldObj.spawnEntityInWorld(par1Entity);
            }
            Entity var5 = par1Entity;
            while (var2.hasKey("Riding")) {
                final NBTTagCompound var6 = var2.getCompoundTag("Riding");
                final Entity var7 = EntityList.createEntityByName(var6.getString("id"), this.getSpawnerWorld());
                if (var7 != null) {
                    final NBTTagCompound var8 = new NBTTagCompound();
                    var7.addEntityID(var8);
                    for (final NBTBase var10 : var6.getTags()) {
                        var8.setTag(var10.getName(), var10.copy());
                    }
                    var7.readFromNBT(var8);
                    var7.setLocationAndAngles(var5.posX, var5.posY, var5.posZ, var5.rotationYaw, var5.rotationPitch);
                    this.getSpawnerWorld().spawnEntityInWorld(var7);
                    var5.mountEntity(var7);
                }
                var5 = var7;
                var2 = var6;
            }
        }
        else if (par1Entity instanceof EntityLiving && par1Entity.worldObj != null) {
            ((EntityLiving)par1Entity).initCreature();
            this.getSpawnerWorld().spawnEntityInWorld(par1Entity);
        }
        return par1Entity;
    }
    
    private void func_98273_j() {
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
        }
        else {
            final int var10003 = this.maxSpawnDelay - this.minSpawnDelay;
            this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(var10003);
        }
        if (this.minecartToSpawn != null && this.minecartToSpawn.size() > 0) {
            this.setRandomMinecart((WeightedRandomMinecart)WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.minecartToSpawn));
        }
        this.func_98267_a(1);
    }
    
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.mobID = par1NBTTagCompound.getString("EntityId");
        this.spawnDelay = par1NBTTagCompound.getShort("Delay");
        if (par1NBTTagCompound.hasKey("SpawnPotentials")) {
            this.minecartToSpawn = new ArrayList();
            final NBTTagList var2 = par1NBTTagCompound.getTagList("SpawnPotentials");
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                this.minecartToSpawn.add(new WeightedRandomMinecart(this, (NBTTagCompound)var2.tagAt(var3)));
            }
        }
        else {
            this.minecartToSpawn = null;
        }
        if (par1NBTTagCompound.hasKey("SpawnData")) {
            this.setRandomMinecart(new WeightedRandomMinecart(this, par1NBTTagCompound.getCompoundTag("SpawnData"), this.mobID));
        }
        else {
            this.setRandomMinecart(null);
        }
        if (par1NBTTagCompound.hasKey("MinSpawnDelay")) {
            this.minSpawnDelay = par1NBTTagCompound.getShort("MinSpawnDelay");
            this.maxSpawnDelay = par1NBTTagCompound.getShort("MaxSpawnDelay");
            this.spawnCount = par1NBTTagCompound.getShort("SpawnCount");
        }
        if (par1NBTTagCompound.hasKey("MaxNearbyEntities")) {
            this.maxNearbyEntities = par1NBTTagCompound.getShort("MaxNearbyEntities");
            this.activatingRangeFromPlayer = par1NBTTagCompound.getShort("RequiredPlayerRange");
        }
        if (par1NBTTagCompound.hasKey("SpawnRange")) {
            this.spawnRange = par1NBTTagCompound.getShort("SpawnRange");
        }
        if (this.getSpawnerWorld() != null && this.getSpawnerWorld().isRemote) {
            this.field_98291_j = null;
        }
    }
    
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setString("EntityId", this.getEntityNameToSpawn());
        par1NBTTagCompound.setShort("Delay", (short)this.spawnDelay);
        par1NBTTagCompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
        par1NBTTagCompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        par1NBTTagCompound.setShort("SpawnCount", (short)this.spawnCount);
        par1NBTTagCompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
        par1NBTTagCompound.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
        par1NBTTagCompound.setShort("SpawnRange", (short)this.spawnRange);
        if (this.getRandomMinecart() != null) {
            par1NBTTagCompound.setCompoundTag("SpawnData", (NBTTagCompound)this.getRandomMinecart().field_98222_b.copy());
        }
        if (this.getRandomMinecart() != null || (this.minecartToSpawn != null && this.minecartToSpawn.size() > 0)) {
            final NBTTagList var2 = new NBTTagList();
            if (this.minecartToSpawn != null && this.minecartToSpawn.size() > 0) {
                for (final WeightedRandomMinecart var4 : this.minecartToSpawn) {
                    var2.appendTag(var4.func_98220_a());
                }
            }
            else {
                var2.appendTag(this.getRandomMinecart().func_98220_a());
            }
            par1NBTTagCompound.setTag("SpawnPotentials", var2);
        }
    }
    
    public Entity func_98281_h() {
        if (this.field_98291_j == null) {
            Entity var1 = EntityList.createEntityByName(this.getEntityNameToSpawn(), null);
            var1 = this.func_98265_a(var1);
            this.field_98291_j = var1;
        }
        return this.field_98291_j;
    }
    
    public boolean setDelayToMin(final int par1) {
        if (par1 == 1 && this.getSpawnerWorld().isRemote) {
            this.spawnDelay = this.minSpawnDelay;
            return true;
        }
        return false;
    }
    
    public WeightedRandomMinecart getRandomMinecart() {
        return this.randomMinecart;
    }
    
    public void setRandomMinecart(final WeightedRandomMinecart par1WeightedRandomMinecart) {
        this.randomMinecart = par1WeightedRandomMinecart;
    }
    
    public abstract void func_98267_a(final int p0);
    
    public abstract World getSpawnerWorld();
    
    public abstract int getSpawnerX();
    
    public abstract int getSpawnerY();
    
    public abstract int getSpawnerZ();
}
