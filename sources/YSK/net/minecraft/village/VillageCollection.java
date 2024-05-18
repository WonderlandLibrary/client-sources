package net.minecraft.village;

import net.minecraft.block.material.*;
import net.minecraft.block.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class VillageCollection extends WorldSavedData
{
    private World worldObj;
    private final List<VillageDoorInfo> newDoors;
    private static final String[] I;
    private final List<Village> villageList;
    private int tickCounter;
    private final List<BlockPos> villagerPositionsList;
    
    private boolean isWoodDoor(final BlockPos blockPos) {
        final Block block = this.worldObj.getBlockState(blockPos).getBlock();
        int n;
        if (block instanceof BlockDoor) {
            if (block.getMaterial() == Material.wood) {
                n = " ".length();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    private void removeAnnihilatedVillages() {
        final Iterator<Village> iterator = this.villageList.iterator();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (iterator.next().isAnnihilated()) {
                iterator.remove();
                this.markDirty();
            }
        }
    }
    
    static {
        I();
    }
    
    private int countBlocksCanSeeSky(final BlockPos blockPos, final EnumFacing enumFacing, final int n) {
        int length = "".length();
        int i = " ".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i <= (0x94 ^ 0x91)) {
            if (this.worldObj.canSeeSky(blockPos.offset(enumFacing, i)) && ++length >= n) {
                return length;
            }
            ++i;
        }
        return length;
    }
    
    public VillageCollection(final String s) {
        super(s);
        this.villagerPositionsList = (List<BlockPos>)Lists.newArrayList();
        this.newDoors = (List<VillageDoorInfo>)Lists.newArrayList();
        this.villageList = (List<Village>)Lists.newArrayList();
    }
    
    public void tick() {
        this.tickCounter += " ".length();
        final Iterator<Village> iterator = this.villageList.iterator();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().tick(this.tickCounter);
        }
        this.removeAnnihilatedVillages();
        this.dropOldestVillagerPosition();
        this.addNewDoorsToVillageOrCreateVillage();
        if (this.tickCounter % (351 + 338 - 580 + 291) == 0) {
            this.markDirty();
        }
    }
    
    public void setWorldsForAll(final World world) {
        this.worldObj = world;
        final Iterator<Village> iterator = this.villageList.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().setWorld(world);
        }
    }
    
    private VillageDoorInfo checkDoorExistence(final BlockPos blockPos) {
        final Iterator<VillageDoorInfo> iterator = this.newDoors.iterator();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final VillageDoorInfo villageDoorInfo = iterator.next();
            if (villageDoorInfo.getDoorBlockPos().getX() == blockPos.getX() && villageDoorInfo.getDoorBlockPos().getZ() == blockPos.getZ() && Math.abs(villageDoorInfo.getDoorBlockPos().getY() - blockPos.getY()) <= " ".length()) {
                return villageDoorInfo;
            }
        }
        final Iterator<Village> iterator2 = this.villageList.iterator();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final VillageDoorInfo existedDoor = iterator2.next().getExistedDoor(blockPos);
            if (existedDoor != null) {
                return existedDoor;
            }
        }
        return null;
    }
    
    public void addToVillagerPositionList(final BlockPos blockPos) {
        if (this.villagerPositionsList.size() <= (0x40 ^ 0x0) && !this.positionInList(blockPos)) {
            this.villagerPositionsList.add(blockPos);
        }
    }
    
    private void addToNewDoorsList(final BlockPos blockPos) {
        final EnumFacing facing = BlockDoor.getFacing(this.worldObj, blockPos);
        final EnumFacing opposite = facing.getOpposite();
        final int countBlocksCanSeeSky = this.countBlocksCanSeeSky(blockPos, facing, 0xE ^ 0xB);
        final int countBlocksCanSeeSky2 = this.countBlocksCanSeeSky(blockPos, opposite, countBlocksCanSeeSky + " ".length());
        if (countBlocksCanSeeSky != countBlocksCanSeeSky2) {
            final List<VillageDoorInfo> newDoors = this.newDoors;
            EnumFacing enumFacing;
            if (countBlocksCanSeeSky < countBlocksCanSeeSky2) {
                enumFacing = facing;
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                enumFacing = opposite;
            }
            newDoors.add(new VillageDoorInfo(blockPos, enumFacing, this.tickCounter));
        }
    }
    
    public static String fileNameForProvider(final WorldProvider worldProvider) {
        return VillageCollection.I[0xC6 ^ 0xC2] + worldProvider.getInternalNameSuffix();
    }
    
    private boolean positionInList(final BlockPos blockPos) {
        final Iterator<BlockPos> iterator = this.villagerPositionsList.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (iterator.next().equals(blockPos)) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger(VillageCollection.I["  ".length()], this.tickCounter);
        final NBTTagList list = new NBTTagList();
        final Iterator<Village> iterator = this.villageList.iterator();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Village village = iterator.next();
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            village.writeVillageDataToNBT(nbtTagCompound2);
            list.appendTag(nbtTagCompound2);
        }
        nbtTagCompound.setTag(VillageCollection.I["   ".length()], list);
    }
    
    private void addDoorsAround(final BlockPos blockPos) {
        final int n = 0x9E ^ 0x8E;
        final int n2 = 0x48 ^ 0x4C;
        final int n3 = 0x59 ^ 0x49;
        int i = -n;
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < n) {
            int j = -n2;
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (j < n2) {
                int k = -n3;
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                while (k < n3) {
                    final BlockPos add = blockPos.add(i, j, k);
                    if (this.isWoodDoor(add)) {
                        final VillageDoorInfo checkDoorExistence = this.checkDoorExistence(add);
                        if (checkDoorExistence == null) {
                            this.addToNewDoorsList(add);
                            "".length();
                            if (3 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            checkDoorExistence.func_179849_a(this.tickCounter);
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    private void addNewDoorsToVillageOrCreateVillage() {
        int i = "".length();
        "".length();
        if (0 == -1) {
            throw null;
        }
        while (i < this.newDoors.size()) {
            final VillageDoorInfo villageDoorInfo = this.newDoors.get(i);
            Village nearestVillage = this.getNearestVillage(villageDoorInfo.getDoorBlockPos(), 0x5B ^ 0x7B);
            if (nearestVillage == null) {
                nearestVillage = new Village(this.worldObj);
                this.villageList.add(nearestVillage);
                this.markDirty();
            }
            nearestVillage.addVillageDoorInfo(villageDoorInfo);
            ++i;
        }
        this.newDoors.clear();
    }
    
    private void dropOldestVillagerPosition() {
        if (!this.villagerPositionsList.isEmpty()) {
            this.addDoorsAround(this.villagerPositionsList.remove("".length()));
        }
    }
    
    public VillageCollection(final World worldObj) {
        super(fileNameForProvider(worldObj.provider));
        this.villagerPositionsList = (List<BlockPos>)Lists.newArrayList();
        this.newDoors = (List<VillageDoorInfo>)Lists.newArrayList();
        this.villageList = (List<Village>)Lists.newArrayList();
        this.worldObj = worldObj;
        this.markDirty();
    }
    
    private static void I() {
        (I = new String[0xBA ^ 0xBF])["".length()] = I("%\u00000\u001e", "qiSui");
        VillageCollection.I[" ".length()] = I("\u001a\u001c\u001d\u000b(+\u0010\u0002", "LuqgI");
        VillageCollection.I["  ".length()] = I("\u0002\f,\u0019", "VeOrH");
        VillageCollection.I["   ".length()] = I("4%\u000e\b\u0017\u0005)\u0011", "bLbdv");
        VillageCollection.I[0x51 ^ 0x55] = I("5\u0013#%&$\u001f<", "CzOIG");
    }
    
    public List<Village> getVillageList() {
        return this.villageList;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.tickCounter = nbtTagCompound.getInteger(VillageCollection.I["".length()]);
        final NBTTagList tagList = nbtTagCompound.getTagList(VillageCollection.I[" ".length()], 0x7E ^ 0x74);
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            final Village village = new Village();
            village.readVillageDataFromNBT(compoundTag);
            this.villageList.add(village);
            ++i;
        }
    }
    
    public Village getNearestVillage(final BlockPos blockPos, final int n) {
        Village village = null;
        double n2 = 3.4028234663852886E38;
        final Iterator<Village> iterator = this.villageList.iterator();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Village village2 = iterator.next();
            final double distanceSq = village2.getCenter().distanceSq(blockPos);
            if (distanceSq < n2) {
                final float n3 = n + village2.getVillageRadius();
                if (distanceSq > n3 * n3) {
                    continue;
                }
                village = village2;
                n2 = distanceSq;
            }
        }
        return village;
    }
}
