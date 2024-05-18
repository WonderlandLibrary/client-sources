package net.minecraft.src;

import java.util.*;

public class Village
{
    private World worldObj;
    private final List villageDoorInfoList;
    private final ChunkCoordinates centerHelper;
    private final ChunkCoordinates center;
    private int villageRadius;
    private int lastAddDoorTimestamp;
    private int tickCounter;
    private int numVillagers;
    private int noBreedTicks;
    private TreeMap playerReputation;
    private List villageAgressors;
    private int numIronGolems;
    
    public Village() {
        this.villageDoorInfoList = new ArrayList();
        this.centerHelper = new ChunkCoordinates(0, 0, 0);
        this.center = new ChunkCoordinates(0, 0, 0);
        this.villageRadius = 0;
        this.lastAddDoorTimestamp = 0;
        this.tickCounter = 0;
        this.numVillagers = 0;
        this.playerReputation = new TreeMap();
        this.villageAgressors = new ArrayList();
        this.numIronGolems = 0;
    }
    
    public Village(final World par1World) {
        this.villageDoorInfoList = new ArrayList();
        this.centerHelper = new ChunkCoordinates(0, 0, 0);
        this.center = new ChunkCoordinates(0, 0, 0);
        this.villageRadius = 0;
        this.lastAddDoorTimestamp = 0;
        this.tickCounter = 0;
        this.numVillagers = 0;
        this.playerReputation = new TreeMap();
        this.villageAgressors = new ArrayList();
        this.numIronGolems = 0;
        this.worldObj = par1World;
    }
    
    public void func_82691_a(final World par1World) {
        this.worldObj = par1World;
    }
    
    public void tick(final int par1) {
        this.tickCounter = par1;
        this.removeDeadAndOutOfRangeDoors();
        this.removeDeadAndOldAgressors();
        if (par1 % 20 == 0) {
            this.updateNumVillagers();
        }
        if (par1 % 30 == 0) {
            this.updateNumIronGolems();
        }
        final int var2 = this.numVillagers / 10;
        if (this.numIronGolems < var2 && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0) {
            final Vec3 var3 = this.tryGetIronGolemSpawningLocation(MathHelper.floor_float(this.center.posX), MathHelper.floor_float(this.center.posY), MathHelper.floor_float(this.center.posZ), 2, 4, 2);
            if (var3 != null) {
                final EntityIronGolem var4 = new EntityIronGolem(this.worldObj);
                var4.setPosition(var3.xCoord, var3.yCoord, var3.zCoord);
                this.worldObj.spawnEntityInWorld(var4);
                ++this.numIronGolems;
            }
        }
    }
    
    private Vec3 tryGetIronGolemSpawningLocation(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        for (int var7 = 0; var7 < 10; ++var7) {
            final int var8 = par1 + this.worldObj.rand.nextInt(16) - 8;
            final int var9 = par2 + this.worldObj.rand.nextInt(6) - 3;
            final int var10 = par3 + this.worldObj.rand.nextInt(16) - 8;
            if (this.isInRange(var8, var9, var10) && this.isValidIronGolemSpawningLocation(var8, var9, var10, par4, par5, par6)) {
                return this.worldObj.getWorldVec3Pool().getVecFromPool(var8, var9, var10);
            }
        }
        return null;
    }
    
    private boolean isValidIronGolemSpawningLocation(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        if (!this.worldObj.doesBlockHaveSolidTopSurface(par1, par2 - 1, par3)) {
            return false;
        }
        final int var7 = par1 - par4 / 2;
        final int var8 = par3 - par6 / 2;
        for (int var9 = var7; var9 < var7 + par4; ++var9) {
            for (int var10 = par2; var10 < par2 + par5; ++var10) {
                for (int var11 = var8; var11 < var8 + par6; ++var11) {
                    if (this.worldObj.isBlockNormalCube(var9, var10, var11)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private void updateNumIronGolems() {
        final List var1 = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, AxisAlignedBB.getAABBPool().getAABB(this.center.posX - this.villageRadius, this.center.posY - 4, this.center.posZ - this.villageRadius, this.center.posX + this.villageRadius, this.center.posY + 4, this.center.posZ + this.villageRadius));
        this.numIronGolems = var1.size();
    }
    
    private void updateNumVillagers() {
        final List var1 = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getAABBPool().getAABB(this.center.posX - this.villageRadius, this.center.posY - 4, this.center.posZ - this.villageRadius, this.center.posX + this.villageRadius, this.center.posY + 4, this.center.posZ + this.villageRadius));
        this.numVillagers = var1.size();
        if (this.numVillagers == 0) {
            this.playerReputation.clear();
        }
    }
    
    public ChunkCoordinates getCenter() {
        return this.center;
    }
    
    public int getVillageRadius() {
        return this.villageRadius;
    }
    
    public int getNumVillageDoors() {
        return this.villageDoorInfoList.size();
    }
    
    public int getTicksSinceLastDoorAdding() {
        return this.tickCounter - this.lastAddDoorTimestamp;
    }
    
    public int getNumVillagers() {
        return this.numVillagers;
    }
    
    public boolean isInRange(final int par1, final int par2, final int par3) {
        return this.center.getDistanceSquared(par1, par2, par3) < this.villageRadius * this.villageRadius;
    }
    
    public List getVillageDoorInfoList() {
        return this.villageDoorInfoList;
    }
    
    public VillageDoorInfo findNearestDoor(final int par1, final int par2, final int par3) {
        VillageDoorInfo var4 = null;
        int var5 = Integer.MAX_VALUE;
        for (final VillageDoorInfo var7 : this.villageDoorInfoList) {
            final int var8 = var7.getDistanceSquared(par1, par2, par3);
            if (var8 < var5) {
                var4 = var7;
                var5 = var8;
            }
        }
        return var4;
    }
    
    public VillageDoorInfo findNearestDoorUnrestricted(final int par1, final int par2, final int par3) {
        VillageDoorInfo var4 = null;
        int var5 = Integer.MAX_VALUE;
        for (final VillageDoorInfo var7 : this.villageDoorInfoList) {
            int var8 = var7.getDistanceSquared(par1, par2, par3);
            if (var8 > 256) {
                var8 *= 1000;
            }
            else {
                var8 = var7.getDoorOpeningRestrictionCounter();
            }
            if (var8 < var5) {
                var4 = var7;
                var5 = var8;
            }
        }
        return var4;
    }
    
    public VillageDoorInfo getVillageDoorAt(final int par1, final int par2, final int par3) {
        if (this.center.getDistanceSquared(par1, par2, par3) > this.villageRadius * this.villageRadius) {
            return null;
        }
        for (final VillageDoorInfo var5 : this.villageDoorInfoList) {
            if (var5.posX == par1 && var5.posZ == par3 && Math.abs(var5.posY - par2) <= 1) {
                return var5;
            }
        }
        return null;
    }
    
    public void addVillageDoorInfo(final VillageDoorInfo par1VillageDoorInfo) {
        this.villageDoorInfoList.add(par1VillageDoorInfo);
        final ChunkCoordinates centerHelper = this.centerHelper;
        centerHelper.posX += par1VillageDoorInfo.posX;
        final ChunkCoordinates centerHelper2 = this.centerHelper;
        centerHelper2.posY += par1VillageDoorInfo.posY;
        final ChunkCoordinates centerHelper3 = this.centerHelper;
        centerHelper3.posZ += par1VillageDoorInfo.posZ;
        this.updateVillageRadiusAndCenter();
        this.lastAddDoorTimestamp = par1VillageDoorInfo.lastActivityTimestamp;
    }
    
    public boolean isAnnihilated() {
        return this.villageDoorInfoList.isEmpty();
    }
    
    public void addOrRenewAgressor(final EntityLiving par1EntityLiving) {
        for (final VillageAgressor var3 : this.villageAgressors) {
            if (var3.agressor == par1EntityLiving) {
                var3.agressionTime = this.tickCounter;
                return;
            }
        }
        this.villageAgressors.add(new VillageAgressor(this, par1EntityLiving, this.tickCounter));
    }
    
    public EntityLiving findNearestVillageAggressor(final EntityLiving par1EntityLiving) {
        double var2 = Double.MAX_VALUE;
        VillageAgressor var3 = null;
        for (int var4 = 0; var4 < this.villageAgressors.size(); ++var4) {
            final VillageAgressor var5 = this.villageAgressors.get(var4);
            final double var6 = var5.agressor.getDistanceSqToEntity(par1EntityLiving);
            if (var6 <= var2) {
                var3 = var5;
                var2 = var6;
            }
        }
        return (var3 != null) ? var3.agressor : null;
    }
    
    public EntityPlayer func_82685_c(final EntityLiving par1EntityLiving) {
        double var2 = Double.MAX_VALUE;
        EntityPlayer var3 = null;
        for (final String var5 : this.playerReputation.keySet()) {
            if (this.isPlayerReputationTooLow(var5)) {
                final EntityPlayer var6 = this.worldObj.getPlayerEntityByName(var5);
                if (var6 == null) {
                    continue;
                }
                final double var7 = var6.getDistanceSqToEntity(par1EntityLiving);
                if (var7 > var2) {
                    continue;
                }
                var3 = var6;
                var2 = var7;
            }
        }
        return var3;
    }
    
    private void removeDeadAndOldAgressors() {
        final Iterator var1 = this.villageAgressors.iterator();
        while (var1.hasNext()) {
            final VillageAgressor var2 = var1.next();
            if (!var2.agressor.isEntityAlive() || Math.abs(this.tickCounter - var2.agressionTime) > 300) {
                var1.remove();
            }
        }
    }
    
    private void removeDeadAndOutOfRangeDoors() {
        boolean var1 = false;
        final boolean var2 = this.worldObj.rand.nextInt(50) == 0;
        final Iterator var3 = this.villageDoorInfoList.iterator();
        while (var3.hasNext()) {
            final VillageDoorInfo var4 = var3.next();
            if (var2) {
                var4.resetDoorOpeningRestrictionCounter();
            }
            if (!this.isBlockDoor(var4.posX, var4.posY, var4.posZ) || Math.abs(this.tickCounter - var4.lastActivityTimestamp) > 1200) {
                final ChunkCoordinates centerHelper = this.centerHelper;
                centerHelper.posX -= var4.posX;
                final ChunkCoordinates centerHelper2 = this.centerHelper;
                centerHelper2.posY -= var4.posY;
                final ChunkCoordinates centerHelper3 = this.centerHelper;
                centerHelper3.posZ -= var4.posZ;
                var1 = true;
                var4.isDetachedFromVillageFlag = true;
                var3.remove();
            }
        }
        if (var1) {
            this.updateVillageRadiusAndCenter();
        }
    }
    
    private boolean isBlockDoor(final int par1, final int par2, final int par3) {
        final int var4 = this.worldObj.getBlockId(par1, par2, par3);
        return var4 > 0 && var4 == Block.doorWood.blockID;
    }
    
    private void updateVillageRadiusAndCenter() {
        final int var1 = this.villageDoorInfoList.size();
        if (var1 == 0) {
            this.center.set(0, 0, 0);
            this.villageRadius = 0;
        }
        else {
            this.center.set(this.centerHelper.posX / var1, this.centerHelper.posY / var1, this.centerHelper.posZ / var1);
            int var2 = 0;
            for (final VillageDoorInfo var4 : this.villageDoorInfoList) {
                var2 = Math.max(var4.getDistanceSquared(this.center.posX, this.center.posY, this.center.posZ), var2);
            }
            this.villageRadius = Math.max(32, (int)Math.sqrt(var2) + 1);
        }
    }
    
    public int getReputationForPlayer(final String par1Str) {
        final Integer var2 = this.playerReputation.get(par1Str);
        return (var2 != null) ? var2 : 0;
    }
    
    public int setReputationForPlayer(final String par1Str, final int par2) {
        final int var3 = this.getReputationForPlayer(par1Str);
        final int var4 = MathHelper.clamp_int(var3 + par2, -30, 10);
        this.playerReputation.put(par1Str, var4);
        return var4;
    }
    
    public boolean isPlayerReputationTooLow(final String par1Str) {
        return this.getReputationForPlayer(par1Str) <= -15;
    }
    
    public void readVillageDataFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.numVillagers = par1NBTTagCompound.getInteger("PopSize");
        this.villageRadius = par1NBTTagCompound.getInteger("Radius");
        this.numIronGolems = par1NBTTagCompound.getInteger("Golems");
        this.lastAddDoorTimestamp = par1NBTTagCompound.getInteger("Stable");
        this.tickCounter = par1NBTTagCompound.getInteger("Tick");
        this.noBreedTicks = par1NBTTagCompound.getInteger("MTick");
        this.center.posX = par1NBTTagCompound.getInteger("CX");
        this.center.posY = par1NBTTagCompound.getInteger("CY");
        this.center.posZ = par1NBTTagCompound.getInteger("CZ");
        this.centerHelper.posX = par1NBTTagCompound.getInteger("ACX");
        this.centerHelper.posY = par1NBTTagCompound.getInteger("ACY");
        this.centerHelper.posZ = par1NBTTagCompound.getInteger("ACZ");
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Doors");
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            final VillageDoorInfo var5 = new VillageDoorInfo(var4.getInteger("X"), var4.getInteger("Y"), var4.getInteger("Z"), var4.getInteger("IDX"), var4.getInteger("IDZ"), var4.getInteger("TS"));
            this.villageDoorInfoList.add(var5);
        }
        final NBTTagList var6 = par1NBTTagCompound.getTagList("Players");
        for (int var7 = 0; var7 < var6.tagCount(); ++var7) {
            final NBTTagCompound var8 = (NBTTagCompound)var6.tagAt(var7);
            this.playerReputation.put(var8.getString("Name"), var8.getInteger("S"));
        }
    }
    
    public void writeVillageDataToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("PopSize", this.numVillagers);
        par1NBTTagCompound.setInteger("Radius", this.villageRadius);
        par1NBTTagCompound.setInteger("Golems", this.numIronGolems);
        par1NBTTagCompound.setInteger("Stable", this.lastAddDoorTimestamp);
        par1NBTTagCompound.setInteger("Tick", this.tickCounter);
        par1NBTTagCompound.setInteger("MTick", this.noBreedTicks);
        par1NBTTagCompound.setInteger("CX", this.center.posX);
        par1NBTTagCompound.setInteger("CY", this.center.posY);
        par1NBTTagCompound.setInteger("CZ", this.center.posZ);
        par1NBTTagCompound.setInteger("ACX", this.centerHelper.posX);
        par1NBTTagCompound.setInteger("ACY", this.centerHelper.posY);
        par1NBTTagCompound.setInteger("ACZ", this.centerHelper.posZ);
        final NBTTagList var2 = new NBTTagList("Doors");
        for (final VillageDoorInfo var4 : this.villageDoorInfoList) {
            final NBTTagCompound var5 = new NBTTagCompound("Door");
            var5.setInteger("X", var4.posX);
            var5.setInteger("Y", var4.posY);
            var5.setInteger("Z", var4.posZ);
            var5.setInteger("IDX", var4.insideDirectionX);
            var5.setInteger("IDZ", var4.insideDirectionZ);
            var5.setInteger("TS", var4.lastActivityTimestamp);
            var2.appendTag(var5);
        }
        par1NBTTagCompound.setTag("Doors", var2);
        final NBTTagList var6 = new NBTTagList("Players");
        for (final String var8 : this.playerReputation.keySet()) {
            final NBTTagCompound var9 = new NBTTagCompound(var8);
            var9.setString("Name", var8);
            var9.setInteger("S", this.playerReputation.get(var8));
            var6.appendTag(var9);
        }
        par1NBTTagCompound.setTag("Players", var6);
    }
    
    public void endMatingSeason() {
        this.noBreedTicks = this.tickCounter;
    }
    
    public boolean isMatingSeason() {
        return this.noBreedTicks == 0 || this.tickCounter - this.noBreedTicks >= 3600;
    }
    
    public void func_82683_b(final int par1) {
        for (final String var3 : this.playerReputation.keySet()) {
            this.setReputationForPlayer(var3, par1);
        }
    }
}
