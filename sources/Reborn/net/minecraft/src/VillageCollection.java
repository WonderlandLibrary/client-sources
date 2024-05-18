package net.minecraft.src;

import java.util.*;

public class VillageCollection extends WorldSavedData
{
    private World worldObj;
    private final List villagerPositionsList;
    private final List newDoors;
    private final List villageList;
    private int tickCounter;
    
    public VillageCollection(final String par1Str) {
        super(par1Str);
        this.villagerPositionsList = new ArrayList();
        this.newDoors = new ArrayList();
        this.villageList = new ArrayList();
        this.tickCounter = 0;
    }
    
    public VillageCollection(final World par1World) {
        super("villages");
        this.villagerPositionsList = new ArrayList();
        this.newDoors = new ArrayList();
        this.villageList = new ArrayList();
        this.tickCounter = 0;
        this.worldObj = par1World;
        this.markDirty();
    }
    
    public void func_82566_a(final World par1World) {
        this.worldObj = par1World;
        for (final Village var3 : this.villageList) {
            var3.func_82691_a(par1World);
        }
    }
    
    public void addVillagerPosition(final int par1, final int par2, final int par3) {
        if (this.villagerPositionsList.size() <= 64 && !this.isVillagerPositionPresent(par1, par2, par3)) {
            this.villagerPositionsList.add(new ChunkCoordinates(par1, par2, par3));
        }
    }
    
    public void tick() {
        ++this.tickCounter;
        for (final Village var2 : this.villageList) {
            var2.tick(this.tickCounter);
        }
        this.removeAnnihilatedVillages();
        this.dropOldestVillagerPosition();
        this.addNewDoorsToVillageOrCreateVillage();
        if (this.tickCounter % 400 == 0) {
            this.markDirty();
        }
    }
    
    private void removeAnnihilatedVillages() {
        final Iterator var1 = this.villageList.iterator();
        while (var1.hasNext()) {
            final Village var2 = var1.next();
            if (var2.isAnnihilated()) {
                var1.remove();
                this.markDirty();
            }
        }
    }
    
    public List getVillageList() {
        return this.villageList;
    }
    
    public Village findNearestVillage(final int par1, final int par2, final int par3, final int par4) {
        Village var5 = null;
        float var6 = Float.MAX_VALUE;
        for (final Village var8 : this.villageList) {
            final float var9 = var8.getCenter().getDistanceSquared(par1, par2, par3);
            if (var9 < var6) {
                final int var10 = par4 + var8.getVillageRadius();
                if (var9 > var10 * var10) {
                    continue;
                }
                var5 = var8;
                var6 = var9;
            }
        }
        return var5;
    }
    
    private void dropOldestVillagerPosition() {
        if (!this.villagerPositionsList.isEmpty()) {
            this.addUnassignedWoodenDoorsAroundToNewDoorsList(this.villagerPositionsList.remove(0));
        }
    }
    
    private void addNewDoorsToVillageOrCreateVillage() {
        for (int var1 = 0; var1 < this.newDoors.size(); ++var1) {
            final VillageDoorInfo var2 = this.newDoors.get(var1);
            boolean var3 = false;
            for (final Village var5 : this.villageList) {
                final int var6 = (int)var5.getCenter().getDistanceSquared(var2.posX, var2.posY, var2.posZ);
                final int var7 = 32 + var5.getVillageRadius();
                if (var6 > var7 * var7) {
                    continue;
                }
                var5.addVillageDoorInfo(var2);
                var3 = true;
                break;
            }
            if (!var3) {
                final Village var8 = new Village(this.worldObj);
                var8.addVillageDoorInfo(var2);
                this.villageList.add(var8);
                this.markDirty();
            }
        }
        this.newDoors.clear();
    }
    
    private void addUnassignedWoodenDoorsAroundToNewDoorsList(final ChunkCoordinates par1ChunkCoordinates) {
        final byte var2 = 16;
        final byte var3 = 4;
        final byte var4 = 16;
        for (int var5 = par1ChunkCoordinates.posX - var2; var5 < par1ChunkCoordinates.posX + var2; ++var5) {
            for (int var6 = par1ChunkCoordinates.posY - var3; var6 < par1ChunkCoordinates.posY + var3; ++var6) {
                for (int var7 = par1ChunkCoordinates.posZ - var4; var7 < par1ChunkCoordinates.posZ + var4; ++var7) {
                    if (this.isWoodenDoorAt(var5, var6, var7)) {
                        final VillageDoorInfo var8 = this.getVillageDoorAt(var5, var6, var7);
                        if (var8 == null) {
                            this.addDoorToNewListIfAppropriate(var5, var6, var7);
                        }
                        else {
                            var8.lastActivityTimestamp = this.tickCounter;
                        }
                    }
                }
            }
        }
    }
    
    private VillageDoorInfo getVillageDoorAt(final int par1, final int par2, final int par3) {
        for (final VillageDoorInfo var5 : this.newDoors) {
            if (var5.posX == par1 && var5.posZ == par3 && Math.abs(var5.posY - par2) <= 1) {
                return var5;
            }
        }
        for (final Village var6 : this.villageList) {
            final VillageDoorInfo var7 = var6.getVillageDoorAt(par1, par2, par3);
            if (var7 != null) {
                return var7;
            }
        }
        return null;
    }
    
    private void addDoorToNewListIfAppropriate(final int par1, final int par2, final int par3) {
        final int var4 = ((BlockDoor)Block.doorWood).getDoorOrientation(this.worldObj, par1, par2, par3);
        if (var4 != 0 && var4 != 2) {
            int var5 = 0;
            for (int var6 = -5; var6 < 0; ++var6) {
                if (this.worldObj.canBlockSeeTheSky(par1, par2, par3 + var6)) {
                    --var5;
                }
            }
            for (int var6 = 1; var6 <= 5; ++var6) {
                if (this.worldObj.canBlockSeeTheSky(par1, par2, par3 + var6)) {
                    ++var5;
                }
            }
            if (var5 != 0) {
                this.newDoors.add(new VillageDoorInfo(par1, par2, par3, 0, (var5 > 0) ? -2 : 2, this.tickCounter));
            }
        }
        else {
            int var5 = 0;
            for (int var6 = -5; var6 < 0; ++var6) {
                if (this.worldObj.canBlockSeeTheSky(par1 + var6, par2, par3)) {
                    --var5;
                }
            }
            for (int var6 = 1; var6 <= 5; ++var6) {
                if (this.worldObj.canBlockSeeTheSky(par1 + var6, par2, par3)) {
                    ++var5;
                }
            }
            if (var5 != 0) {
                this.newDoors.add(new VillageDoorInfo(par1, par2, par3, (var5 > 0) ? -2 : 2, 0, this.tickCounter));
            }
        }
    }
    
    private boolean isVillagerPositionPresent(final int par1, final int par2, final int par3) {
        for (final ChunkCoordinates var5 : this.villagerPositionsList) {
            if (var5.posX == par1 && var5.posY == par2 && var5.posZ == par3) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isWoodenDoorAt(final int par1, final int par2, final int par3) {
        final int var4 = this.worldObj.getBlockId(par1, par2, par3);
        return var4 == Block.doorWood.blockID;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.tickCounter = par1NBTTagCompound.getInteger("Tick");
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Villages");
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            final Village var5 = new Village();
            var5.readVillageDataFromNBT(var4);
            this.villageList.add(var5);
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("Tick", this.tickCounter);
        final NBTTagList var2 = new NBTTagList("Villages");
        for (final Village var4 : this.villageList) {
            final NBTTagCompound var5 = new NBTTagCompound("Village");
            var4.writeVillageDataToNBT(var5);
            var2.appendTag(var5);
        }
        par1NBTTagCompound.setTag("Villages", var2);
    }
}
