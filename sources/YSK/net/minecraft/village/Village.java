package net.minecraft.village;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.server.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;

public class Village
{
    private World worldObj;
    private List<VillageAggressor> villageAgressors;
    private int villageRadius;
    private TreeMap<String, Integer> playerReputation;
    private BlockPos centerHelper;
    private int numVillagers;
    private int tickCounter;
    private int lastAddDoorTimestamp;
    private int noBreedTicks;
    private int numIronGolems;
    private BlockPos center;
    private final List<VillageDoorInfo> villageDoorInfoList;
    private static final String[] I;
    
    public int getReputationForPlayer(final String s) {
        final Integer n = this.playerReputation.get(s);
        int n2;
        if (n != null) {
            n2 = n;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2;
    }
    
    public void addVillageDoorInfo(final VillageDoorInfo villageDoorInfo) {
        this.villageDoorInfoList.add(villageDoorInfo);
        this.centerHelper = this.centerHelper.add(villageDoorInfo.getDoorBlockPos());
        this.updateVillageRadiusAndCenter();
        this.lastAddDoorTimestamp = villageDoorInfo.getInsidePosY();
    }
    
    public EntityPlayer getNearestTargetPlayer(final EntityLivingBase entityLivingBase) {
        double n = Double.MAX_VALUE;
        EntityPlayer entityPlayer = null;
        final Iterator<String> iterator = this.playerReputation.keySet().iterator();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            if (this.isPlayerReputationTooLow(s)) {
                final EntityPlayer playerEntityByName = this.worldObj.getPlayerEntityByName(s);
                if (playerEntityByName == null) {
                    continue;
                }
                final double distanceSqToEntity = playerEntityByName.getDistanceSqToEntity(entityLivingBase);
                if (distanceSqToEntity > n) {
                    continue;
                }
                entityPlayer = playerEntityByName;
                n = distanceSqToEntity;
            }
        }
        return entityPlayer;
    }
    
    private void removeDeadAndOutOfRangeDoors() {
        int n = "".length();
        int n2;
        if (this.worldObj.rand.nextInt(0xA8 ^ 0x9A) == 0) {
            n2 = " ".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        final Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator.hasNext()) {
            final VillageDoorInfo villageDoorInfo = iterator.next();
            if (n3 != 0) {
                villageDoorInfo.resetDoorOpeningRestrictionCounter();
            }
            if (!this.isWoodDoor(villageDoorInfo.getDoorBlockPos()) || Math.abs(this.tickCounter - villageDoorInfo.getInsidePosY()) > 322 + 303 + 118 + 457) {
                this.centerHelper = this.centerHelper.subtract(villageDoorInfo.getDoorBlockPos());
                n = " ".length();
                villageDoorInfo.setIsDetachedFromVillageFlag(" ".length() != 0);
                iterator.remove();
            }
        }
        if (n != 0) {
            this.updateVillageRadiusAndCenter();
        }
    }
    
    public int getVillageRadius() {
        return this.villageRadius;
    }
    
    public void tick(final int tickCounter) {
        this.tickCounter = tickCounter;
        this.removeDeadAndOutOfRangeDoors();
        this.removeDeadAndOldAgressors();
        if (tickCounter % (0x72 ^ 0x66) == 0) {
            this.updateNumVillagers();
        }
        if (tickCounter % (0x98 ^ 0x86) == 0) {
            this.updateNumIronGolems();
        }
        if (this.numIronGolems < this.numVillagers / (0x99 ^ 0x93) && this.villageDoorInfoList.size() > (0xA0 ^ 0xB4) && this.worldObj.rand.nextInt(3268 + 4713 - 7055 + 6074) == 0) {
            final Vec3 func_179862_a = this.func_179862_a(this.center, "  ".length(), 0x71 ^ 0x75, "  ".length());
            if (func_179862_a != null) {
                final EntityIronGolem entityIronGolem = new EntityIronGolem(this.worldObj);
                entityIronGolem.setPosition(func_179862_a.xCoord, func_179862_a.yCoord, func_179862_a.zCoord);
                this.worldObj.spawnEntityInWorld(entityIronGolem);
                this.numIronGolems += " ".length();
            }
        }
    }
    
    private static void I() {
        (I = new String[0x20 ^ 0xF])["".length()] = I("\u0002>!+\"(4", "RQQxK");
        Village.I[" ".length()] = I("%4\u000f,\u0012\u0004", "wUkEg");
        Village.I["  ".length()] = I("\u0004-4\u0004\u001e0", "CBXas");
        Village.I["   ".length()] = I("0&++\u000b\u0006", "cRJIg");
        Village.I[0x4F ^ 0x4B] = I("\u0011\u0007\u0007%", "EndNz");
        Village.I[0x7D ^ 0x78] = I(",\u0019%49", "aMLWR");
        Village.I[0x62 ^ 0x64] = I("\n.", "IvqYI");
        Village.I[0x1A ^ 0x1D] = I("*)", "iptQk");
        Village.I[0xA0 ^ 0xA8] = I("2\u0014", "qNMyG");
        Village.I[0x82 ^ 0x8B] = I("\u000f\u0015\u0010", "NVHsu");
        Village.I[0x39 ^ 0x33] = I("//(", "nlqNs");
        Village.I[0x56 ^ 0x5D] = I("2\u0005\b", "sFRHO");
        Village.I[0x5C ^ 0x50] = I("5\u001e\u0015$#", "qqzVP");
        Village.I[0xA6 ^ 0xAB] = I("\u000b", "SYVag");
        Village.I[0xC8 ^ 0xC6] = I("7", "nWNKm");
        Village.I[0x3A ^ 0x35] = I(";", "asxZh");
        Village.I[0x9D ^ 0x8D] = I(".2\n", "gvRnk");
        Village.I[0xA8 ^ 0xB9] = I("'*0", "nnjOU");
        Village.I[0x55 ^ 0x47] = I("\u0003?", "Wlbte");
        Village.I[0x77 ^ 0x64] = I("\t\u00035(&+\u001c", "YoTQC");
        Village.I[0x45 ^ 0x51] = I("\u0016<\u0010-", "CiYip");
        Village.I[0x24 ^ 0x31] = I("8=-,", "mhdhg");
        Village.I[0x76 ^ 0x60] = I("+", "xLugl");
        Village.I[0xA6 ^ 0xB1] = I("%\f*\u0007", "kmGbW");
        Village.I[0x72 ^ 0x6A] = I("\u0005", "VufVB");
        Village.I[0x9E ^ 0x87] = I("1\u001d\b4-\u001b\u0017", "arxgD");
        Village.I[0xBA ^ 0xA0] = I("\u0004+\u0013\u000f\u000f%", "VJwfz");
        Village.I[0x64 ^ 0x7F] = I("\u0011-.\t\u0017%", "VBBlz");
        Village.I[0x65 ^ 0x79] = I("\u0010%\u0006;\"&", "CQgYN");
        Village.I[0x20 ^ 0x3D] = I("\u0007>\u000b\u001f", "SWhty");
        Village.I[0xF ^ 0x11] = I("\u001901-#", "TdXNH");
        Village.I[0x49 ^ 0x56] = I("\r\u0012", "NJyBv");
        Village.I[0x66 ^ 0x46] = I(" \u0013", "cJAeU");
        Village.I[0x80 ^ 0xA1] = I("\u000e>", "MdyGZ");
        Village.I[0xE3 ^ 0xC1] = I(" \u0006 ", "aExMe");
        Village.I[0x8E ^ 0xAD] = I("\"\u0010\u001b", "cSBeC");
        Village.I[0xE7 ^ 0xC3] = I(";\n-", "zIwJA");
        Village.I[0x16 ^ 0x33] = I("\u0012", "JmLxH");
        Village.I[0x74 ^ 0x52] = I(")", "pGHpc");
        Village.I[0x4C ^ 0x6B] = I("\u0017", "MOnRt");
        Village.I[0x43 ^ 0x6B] = I("3\u0010\t", "zTQTg");
        Village.I[0xD ^ 0x24] = I("/'\u0016", "fcLBQ");
        Village.I[0x12 ^ 0x38] = I("\u0010%", "DvKSF");
        Village.I[0xA5 ^ 0x8E] = I("(7\u0019\u0005\u0007", "lXvwt");
        Village.I[0x3 ^ 0x2F] = I("\u001d\u0012\u000e\u001c", "HGGXq");
        Village.I[0xBF ^ 0x92] = I("4", "gswCR");
        Village.I[0x41 ^ 0x6F] = I("'\u000e7;1\u0005\u0011", "wbVBT");
    }
    
    public boolean isPlayerReputationTooLow(final String s) {
        if (this.getReputationForPlayer(s) <= -(0x9E ^ 0x91)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void addOrRenewAgressor(final EntityLivingBase entityLivingBase) {
        final Iterator<VillageAggressor> iterator = this.villageAgressors.iterator();
        "".length();
        if (3 == 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final VillageAggressor villageAggressor = iterator.next();
            if (villageAggressor.agressor == entityLivingBase) {
                villageAggressor.agressionTime = this.tickCounter;
                return;
            }
        }
        this.villageAgressors.add(new VillageAggressor(entityLivingBase, this.tickCounter));
    }
    
    public int getTicksSinceLastDoorAdding() {
        return this.tickCounter - this.lastAddDoorTimestamp;
    }
    
    static {
        I();
    }
    
    private void updateNumIronGolems() {
        this.numIronGolems = this.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityIronGolem.class, new AxisAlignedBB(this.center.getX() - this.villageRadius, this.center.getY() - (0x30 ^ 0x34), this.center.getZ() - this.villageRadius, this.center.getX() + this.villageRadius, this.center.getY() + (0x99 ^ 0x9D), this.center.getZ() + this.villageRadius)).size();
    }
    
    public EntityLivingBase findNearestVillageAggressor(final EntityLivingBase entityLivingBase) {
        double n = Double.MAX_VALUE;
        VillageAggressor villageAggressor = null;
        int i = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i < this.villageAgressors.size()) {
            final VillageAggressor villageAggressor2 = this.villageAgressors.get(i);
            final double distanceSqToEntity = villageAggressor2.agressor.getDistanceSqToEntity(entityLivingBase);
            if (distanceSqToEntity <= n) {
                villageAggressor = villageAggressor2;
                n = distanceSqToEntity;
            }
            ++i;
        }
        EntityLivingBase agressor;
        if (villageAggressor != null) {
            agressor = villageAggressor.agressor;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            agressor = null;
        }
        return agressor;
    }
    
    private void updateVillageRadiusAndCenter() {
        final int size = this.villageDoorInfoList.size();
        if (size == 0) {
            this.center = new BlockPos("".length(), "".length(), "".length());
            this.villageRadius = "".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            this.center = new BlockPos(this.centerHelper.getX() / size, this.centerHelper.getY() / size, this.centerHelper.getZ() / size);
            int n = "".length();
            final Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                n = Math.max(iterator.next().getDistanceToDoorBlockSq(this.center), n);
            }
            this.villageRadius = Math.max(0x2C ^ 0xC, (int)Math.sqrt(n) + " ".length());
        }
    }
    
    public boolean isMatingSeason() {
        if (this.noBreedTicks != 0 && this.tickCounter - this.noBreedTicks < 1476 + 3308 - 4561 + 3377) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void readVillageDataFromNBT(final NBTTagCompound nbtTagCompound) {
        this.numVillagers = nbtTagCompound.getInteger(Village.I["".length()]);
        this.villageRadius = nbtTagCompound.getInteger(Village.I[" ".length()]);
        this.numIronGolems = nbtTagCompound.getInteger(Village.I["  ".length()]);
        this.lastAddDoorTimestamp = nbtTagCompound.getInteger(Village.I["   ".length()]);
        this.tickCounter = nbtTagCompound.getInteger(Village.I[0x12 ^ 0x16]);
        this.noBreedTicks = nbtTagCompound.getInteger(Village.I[0x7A ^ 0x7F]);
        this.center = new BlockPos(nbtTagCompound.getInteger(Village.I[0x84 ^ 0x82]), nbtTagCompound.getInteger(Village.I[0xB5 ^ 0xB2]), nbtTagCompound.getInteger(Village.I[0x3B ^ 0x33]));
        this.centerHelper = new BlockPos(nbtTagCompound.getInteger(Village.I[0x97 ^ 0x9E]), nbtTagCompound.getInteger(Village.I[0x87 ^ 0x8D]), nbtTagCompound.getInteger(Village.I[0xBA ^ 0xB1]));
        final NBTTagList tagList = nbtTagCompound.getTagList(Village.I[0x62 ^ 0x6E], 0x69 ^ 0x63);
        int i = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            this.villageDoorInfoList.add(new VillageDoorInfo(new BlockPos(compoundTag.getInteger(Village.I[0x2 ^ 0xF]), compoundTag.getInteger(Village.I[0x6E ^ 0x60]), compoundTag.getInteger(Village.I[0x84 ^ 0x8B])), compoundTag.getInteger(Village.I[0x4A ^ 0x5A]), compoundTag.getInteger(Village.I[0x95 ^ 0x84]), compoundTag.getInteger(Village.I[0x52 ^ 0x40])));
            ++i;
        }
        final NBTTagList tagList2 = nbtTagCompound.getTagList(Village.I[0x62 ^ 0x71], 0xCE ^ 0xC4);
        int j = "".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (j < tagList2.tagCount()) {
            final NBTTagCompound compoundTag2 = tagList2.getCompoundTagAt(j);
            if (compoundTag2.hasKey(Village.I[0x25 ^ 0x31])) {
                final GameProfile profileByUUID = MinecraftServer.getServer().getPlayerProfileCache().getProfileByUUID(UUID.fromString(compoundTag2.getString(Village.I[0xB6 ^ 0xA3])));
                if (profileByUUID != null) {
                    this.playerReputation.put(profileByUUID.getName(), compoundTag2.getInteger(Village.I[0x5 ^ 0x13]));
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                }
            }
            else {
                this.playerReputation.put(compoundTag2.getString(Village.I[0x57 ^ 0x40]), compoundTag2.getInteger(Village.I[0x9F ^ 0x87]));
            }
            ++j;
        }
    }
    
    public int getNumVillageDoors() {
        return this.villageDoorInfoList.size();
    }
    
    public void writeVillageDataToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger(Village.I[0x1B ^ 0x2], this.numVillagers);
        nbtTagCompound.setInteger(Village.I[0x77 ^ 0x6D], this.villageRadius);
        nbtTagCompound.setInteger(Village.I[0x2B ^ 0x30], this.numIronGolems);
        nbtTagCompound.setInteger(Village.I[0x4 ^ 0x18], this.lastAddDoorTimestamp);
        nbtTagCompound.setInteger(Village.I[0x7D ^ 0x60], this.tickCounter);
        nbtTagCompound.setInteger(Village.I[0x82 ^ 0x9C], this.noBreedTicks);
        nbtTagCompound.setInteger(Village.I[0xB2 ^ 0xAD], this.center.getX());
        nbtTagCompound.setInteger(Village.I[0x7 ^ 0x27], this.center.getY());
        nbtTagCompound.setInteger(Village.I[0x30 ^ 0x11], this.center.getZ());
        nbtTagCompound.setInteger(Village.I[0xE4 ^ 0xC6], this.centerHelper.getX());
        nbtTagCompound.setInteger(Village.I[0xAB ^ 0x88], this.centerHelper.getY());
        nbtTagCompound.setInteger(Village.I[0x60 ^ 0x44], this.centerHelper.getZ());
        final NBTTagList list = new NBTTagList();
        final Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final VillageDoorInfo villageDoorInfo = iterator.next();
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            nbtTagCompound2.setInteger(Village.I[0xB6 ^ 0x93], villageDoorInfo.getDoorBlockPos().getX());
            nbtTagCompound2.setInteger(Village.I[0x8F ^ 0xA9], villageDoorInfo.getDoorBlockPos().getY());
            nbtTagCompound2.setInteger(Village.I[0x18 ^ 0x3F], villageDoorInfo.getDoorBlockPos().getZ());
            nbtTagCompound2.setInteger(Village.I[0xBD ^ 0x95], villageDoorInfo.getInsideOffsetX());
            nbtTagCompound2.setInteger(Village.I[0xAE ^ 0x87], villageDoorInfo.getInsideOffsetZ());
            nbtTagCompound2.setInteger(Village.I[0xEA ^ 0xC0], villageDoorInfo.getInsidePosY());
            list.appendTag(nbtTagCompound2);
        }
        nbtTagCompound.setTag(Village.I[0x33 ^ 0x18], list);
        final NBTTagList list2 = new NBTTagList();
        final Iterator<String> iterator2 = this.playerReputation.keySet().iterator();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final String s = iterator2.next();
            final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
            final GameProfile gameProfileForUsername = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(s);
            if (gameProfileForUsername != null) {
                nbtTagCompound3.setString(Village.I[0xEC ^ 0xC0], gameProfileForUsername.getId().toString());
                nbtTagCompound3.setInteger(Village.I[0x11 ^ 0x3C], this.playerReputation.get(s));
                list2.appendTag(nbtTagCompound3);
            }
        }
        nbtTagCompound.setTag(Village.I[0x49 ^ 0x67], list2);
    }
    
    public List<VillageDoorInfo> getVillageDoorInfoList() {
        return this.villageDoorInfoList;
    }
    
    private boolean isWoodDoor(final BlockPos blockPos) {
        final Block block = this.worldObj.getBlockState(blockPos).getBlock();
        int n;
        if (block instanceof BlockDoor) {
            if (block.getMaterial() == Material.wood) {
                n = " ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public BlockPos getCenter() {
        return this.center;
    }
    
    public boolean isAnnihilated() {
        return this.villageDoorInfoList.isEmpty();
    }
    
    public Village() {
        this.villageDoorInfoList = (List<VillageDoorInfo>)Lists.newArrayList();
        this.centerHelper = BlockPos.ORIGIN;
        this.center = BlockPos.ORIGIN;
        this.playerReputation = new TreeMap<String, Integer>();
        this.villageAgressors = (List<VillageAggressor>)Lists.newArrayList();
    }
    
    public VillageDoorInfo getNearestDoor(final BlockPos blockPos) {
        VillageDoorInfo villageDoorInfo = null;
        int n = 2050495727 + 1713845677 - 1940262173 + 323404416;
        final Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
        "".length();
        if (2 < 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final VillageDoorInfo villageDoorInfo2 = iterator.next();
            final int distanceToDoorBlockSq = villageDoorInfo2.getDistanceToDoorBlockSq(blockPos);
            if (distanceToDoorBlockSq < n) {
                villageDoorInfo = villageDoorInfo2;
                n = distanceToDoorBlockSq;
            }
        }
        return villageDoorInfo;
    }
    
    public boolean func_179866_a(final BlockPos blockPos) {
        if (this.center.distanceSq(blockPos) < this.villageRadius * this.villageRadius) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public VillageDoorInfo getExistedDoor(final BlockPos blockPos) {
        if (this.center.distanceSq(blockPos) > this.villageRadius * this.villageRadius) {
            return null;
        }
        final Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
        "".length();
        if (!true) {
            throw null;
        }
        while (iterator.hasNext()) {
            final VillageDoorInfo villageDoorInfo = iterator.next();
            if (villageDoorInfo.getDoorBlockPos().getX() == blockPos.getX() && villageDoorInfo.getDoorBlockPos().getZ() == blockPos.getZ() && Math.abs(villageDoorInfo.getDoorBlockPos().getY() - blockPos.getY()) <= " ".length()) {
                return villageDoorInfo;
            }
        }
        return null;
    }
    
    private boolean func_179861_a(final BlockPos blockPos, final BlockPos blockPos2) {
        if (!World.doesBlockHaveSolidTopSurface(this.worldObj, blockPos2.down())) {
            return "".length() != 0;
        }
        final int n = blockPos2.getX() - blockPos.getX() / "  ".length();
        final int n2 = blockPos2.getZ() - blockPos.getZ() / "  ".length();
        int i = n;
        "".length();
        if (4 == 2) {
            throw null;
        }
        while (i < n + blockPos.getX()) {
            int j = blockPos2.getY();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (j < blockPos2.getY() + blockPos.getY()) {
                int k = n2;
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                while (k < n2 + blockPos.getZ()) {
                    if (this.worldObj.getBlockState(new BlockPos(i, j, k)).getBlock().isNormalCube()) {
                        return "".length() != 0;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    private void updateNumVillagers() {
        this.numVillagers = this.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityVillager.class, new AxisAlignedBB(this.center.getX() - this.villageRadius, this.center.getY() - (0xAE ^ 0xAA), this.center.getZ() - this.villageRadius, this.center.getX() + this.villageRadius, this.center.getY() + (0x63 ^ 0x67), this.center.getZ() + this.villageRadius)).size();
        if (this.numVillagers == 0) {
            this.playerReputation.clear();
        }
    }
    
    public void endMatingSeason() {
        this.noBreedTicks = this.tickCounter;
    }
    
    public VillageDoorInfo getDoorInfo(final BlockPos blockPos) {
        VillageDoorInfo villageDoorInfo = null;
        int n = 1470195614 + 1425594867 - 1856405653 + 1108098819;
        final Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final VillageDoorInfo villageDoorInfo2 = iterator.next();
            final int distanceToDoorBlockSq = villageDoorInfo2.getDistanceToDoorBlockSq(blockPos);
            int doorOpeningRestrictionCounter;
            if (distanceToDoorBlockSq > 226 + 142 - 346 + 234) {
                doorOpeningRestrictionCounter = distanceToDoorBlockSq * (403 + 556 - 647 + 688);
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                doorOpeningRestrictionCounter = villageDoorInfo2.getDoorOpeningRestrictionCounter();
            }
            if (doorOpeningRestrictionCounter < n) {
                villageDoorInfo = villageDoorInfo2;
                n = doorOpeningRestrictionCounter;
            }
        }
        return villageDoorInfo;
    }
    
    private void removeDeadAndOldAgressors() {
        final Iterator<VillageAggressor> iterator = this.villageAgressors.iterator();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final VillageAggressor villageAggressor = iterator.next();
            if (!villageAggressor.agressor.isEntityAlive() || Math.abs(this.tickCounter - villageAggressor.agressionTime) > 53 + 52 + 161 + 34) {
                iterator.remove();
            }
        }
    }
    
    private Vec3 func_179862_a(final BlockPos blockPos, final int n, final int n2, final int n3) {
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < (0xA9 ^ 0xA3)) {
            final BlockPos add = blockPos.add(this.worldObj.rand.nextInt(0xD2 ^ 0xC2) - (0x97 ^ 0x9F), this.worldObj.rand.nextInt(0x3F ^ 0x39) - "   ".length(), this.worldObj.rand.nextInt(0x7 ^ 0x17) - (0x11 ^ 0x19));
            if (this.func_179866_a(add) && this.func_179861_a(new BlockPos(n, n2, n3), add)) {
                return new Vec3(add.getX(), add.getY(), add.getZ());
            }
            ++i;
        }
        return null;
    }
    
    public int getNumVillagers() {
        return this.numVillagers;
    }
    
    public int setReputationForPlayer(final String s, final int n) {
        final int clamp_int = MathHelper.clamp_int(this.getReputationForPlayer(s) + n, -(0x6D ^ 0x73), 0x6D ^ 0x67);
        this.playerReputation.put(s, clamp_int);
        return clamp_int;
    }
    
    public Village(final World worldObj) {
        this.villageDoorInfoList = (List<VillageDoorInfo>)Lists.newArrayList();
        this.centerHelper = BlockPos.ORIGIN;
        this.center = BlockPos.ORIGIN;
        this.playerReputation = new TreeMap<String, Integer>();
        this.villageAgressors = (List<VillageAggressor>)Lists.newArrayList();
        this.worldObj = worldObj;
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setWorld(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public void setDefaultPlayerReputation(final int n) {
        final Iterator<String> iterator = this.playerReputation.keySet().iterator();
        "".length();
        if (4 == 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.setReputationForPlayer(iterator.next(), n);
        }
    }
    
    class VillageAggressor
    {
        public EntityLivingBase agressor;
        final Village this$0;
        public int agressionTime;
        
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
                if (0 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        VillageAggressor(final Village this$0, final EntityLivingBase agressor, final int agressionTime) {
            this.this$0 = this$0;
            this.agressor = agressor;
            this.agressionTime = agressionTime;
        }
    }
}
