package net.minecraft.village;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class VillageSiege
{
    private World worldObj;
    private int field_75538_h;
    private Village theVillage;
    private int field_75536_c;
    private int field_75532_g;
    private boolean field_75535_b;
    private int field_75534_e;
    private int field_75539_i;
    private int field_75533_d;
    
    public void tick() {
        if (this.worldObj.isDaytime()) {
            this.field_75536_c = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else if (this.field_75536_c != "  ".length()) {
            if (this.field_75536_c == 0) {
                final float celestialAngle = this.worldObj.getCelestialAngle(0.0f);
                if (celestialAngle < 0.5 || celestialAngle > 0.501) {
                    return;
                }
                int field_75536_c;
                if (this.worldObj.rand.nextInt(0x19 ^ 0x13) == 0) {
                    field_75536_c = " ".length();
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                else {
                    field_75536_c = "  ".length();
                }
                this.field_75536_c = field_75536_c;
                this.field_75535_b = ("".length() != 0);
                if (this.field_75536_c == "  ".length()) {
                    return;
                }
            }
            if (this.field_75536_c != -" ".length()) {
                if (!this.field_75535_b) {
                    if (!this.func_75529_b()) {
                        return;
                    }
                    this.field_75535_b = (" ".length() != 0);
                }
                if (this.field_75534_e > 0) {
                    this.field_75534_e -= " ".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    this.field_75534_e = "  ".length();
                    if (this.field_75533_d > 0) {
                        this.spawnZombie();
                        this.field_75533_d -= " ".length();
                        "".length();
                        if (3 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        this.field_75536_c = "  ".length();
                    }
                }
            }
        }
    }
    
    private Vec3 func_179867_a(final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (i < (0x1E ^ 0x14)) {
            final BlockPos add = blockPos.add(this.worldObj.rand.nextInt(0xA0 ^ 0xB0) - (0x69 ^ 0x61), this.worldObj.rand.nextInt(0x87 ^ 0x81) - "   ".length(), this.worldObj.rand.nextInt(0x40 ^ 0x50) - (0x66 ^ 0x6E));
            if (this.theVillage.func_179866_a(add) && SpawnerAnimals.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, this.worldObj, add)) {
                return new Vec3(add.getX(), add.getY(), add.getZ());
            }
            ++i;
        }
        return null;
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private boolean func_75529_b() {
        for (final EntityPlayer entityPlayer : this.worldObj.playerEntities) {
            if (!entityPlayer.isSpectator()) {
                this.theVillage = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(entityPlayer), " ".length());
                if (this.theVillage == null || this.theVillage.getNumVillageDoors() < (0x4B ^ 0x41) || this.theVillage.getTicksSinceLastDoorAdding() < (0x82 ^ 0x96) || this.theVillage.getNumVillagers() < (0x31 ^ 0x25)) {
                    continue;
                }
                final BlockPos center = this.theVillage.getCenter();
                final float n = this.theVillage.getVillageRadius();
                int n2 = "".length();
                int i = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (i < (0x6C ^ 0x66)) {
                    final float n3 = this.worldObj.rand.nextFloat() * 3.1415927f * 2.0f;
                    this.field_75532_g = center.getX() + (int)(MathHelper.cos(n3) * n * 0.9);
                    this.field_75538_h = center.getY();
                    this.field_75539_i = center.getZ() + (int)(MathHelper.sin(n3) * n * 0.9);
                    n2 = "".length();
                    final Iterator<Village> iterator2 = this.worldObj.getVillageCollection().getVillageList().iterator();
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                    while (iterator2.hasNext()) {
                        final Village village = iterator2.next();
                        if (village != this.theVillage && village.func_179866_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i))) {
                            n2 = " ".length();
                            "".length();
                            if (-1 >= 2) {
                                throw null;
                            }
                            break;
                        }
                    }
                    if (n2 == 0) {
                        "".length();
                        if (0 <= -1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
                if (n2 != 0) {
                    return "".length() != 0;
                }
                if (this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i)) != null) {
                    this.field_75534_e = "".length();
                    this.field_75533_d = (0xB0 ^ 0xA4);
                    return " ".length() != 0;
                }
                continue;
            }
        }
        return "".length() != 0;
    }
    
    private boolean spawnZombie() {
        final Vec3 func_179867_a = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
        if (func_179867_a == null) {
            return "".length() != 0;
        }
        EntityZombie entityZombie;
        try {
            entityZombie = new EntityZombie(this.worldObj);
            entityZombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityZombie)), null);
            entityZombie.setVillager("".length() != 0);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "".length() != 0;
        }
        entityZombie.setLocationAndAngles(func_179867_a.xCoord, func_179867_a.yCoord, func_179867_a.zCoord, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
        this.worldObj.spawnEntityInWorld(entityZombie);
        entityZombie.setHomePosAndDistance(this.theVillage.getCenter(), this.theVillage.getVillageRadius());
        return " ".length() != 0;
    }
    
    public VillageSiege(final World worldObj) {
        this.field_75536_c = -" ".length();
        this.worldObj = worldObj;
    }
}
