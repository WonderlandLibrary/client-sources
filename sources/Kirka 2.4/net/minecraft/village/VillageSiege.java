/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.village;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;

public class VillageSiege {
    private World worldObj;
    private boolean field_75535_b;
    private int field_75536_c = -1;
    private int field_75533_d;
    private int field_75534_e;
    private Village theVillage;
    private int field_75532_g;
    private int field_75538_h;
    private int field_75539_i;
    private static final String __OBFID = "CL_00001634";

    public VillageSiege(World worldIn) {
        this.worldObj = worldIn;
    }

    public void tick() {
        if (this.worldObj.isDaytime()) {
            this.field_75536_c = 0;
        } else if (this.field_75536_c != 2) {
            if (this.field_75536_c == 0) {
                float var1 = this.worldObj.getCelestialAngle(0.0f);
                if ((double)var1 < 0.5 || (double)var1 > 0.501) {
                    return;
                }
                this.field_75536_c = this.worldObj.rand.nextInt(10) == 0 ? 1 : 2;
                this.field_75535_b = false;
                if (this.field_75536_c == 2) {
                    return;
                }
            }
            if (this.field_75536_c != -1) {
                if (!this.field_75535_b) {
                    if (!this.func_75529_b()) {
                        return;
                    }
                    this.field_75535_b = true;
                }
                if (this.field_75534_e > 0) {
                    --this.field_75534_e;
                } else {
                    this.field_75534_e = 2;
                    if (this.field_75533_d > 0) {
                        this.spawnZombie();
                        --this.field_75533_d;
                    } else {
                        this.field_75536_c = 2;
                    }
                }
            }
        }
    }

    private boolean func_75529_b() {
        List var1 = this.worldObj.playerEntities;
        for (EntityPlayer var3 : var1) {
            if (var3.func_175149_v()) continue;
            this.theVillage = this.worldObj.getVillageCollection().func_176056_a(new BlockPos(var3), 1);
            if (this.theVillage == null || this.theVillage.getNumVillageDoors() < 10 || this.theVillage.getTicksSinceLastDoorAdding() < 20 || this.theVillage.getNumVillagers() < 20) continue;
            BlockPos var4 = this.theVillage.func_180608_a();
            float var5 = this.theVillage.getVillageRadius();
            boolean var6 = false;
            for (int var7 = 0; var7 < 10; ++var7) {
                float var8 = this.worldObj.rand.nextFloat() * 3.1415927f * 2.0f;
                this.field_75532_g = var4.getX() + (int)((double)(MathHelper.cos(var8) * var5) * 0.9);
                this.field_75538_h = var4.getY();
                this.field_75539_i = var4.getZ() + (int)((double)(MathHelper.sin(var8) * var5) * 0.9);
                var6 = false;
                for (Village var10 : this.worldObj.getVillageCollection().getVillageList()) {
                    if (var10 == this.theVillage || !var10.func_179866_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i))) continue;
                    var6 = true;
                    break;
                }
                if (!var6) break;
            }
            if (var6) {
                return false;
            }
            Vec3 var11 = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
            if (var11 == null) continue;
            this.field_75534_e = 0;
            this.field_75533_d = 20;
            return true;
        }
        return false;
    }

    private boolean spawnZombie() {
        EntityZombie var2;
        Vec3 var1 = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
        if (var1 == null) {
            return false;
        }
        try {
            var2 = new EntityZombie(this.worldObj);
            var2.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(var2)), null);
            var2.setVillager(false);
        }
        catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
        var2.setLocationAndAngles(var1.xCoord, var1.yCoord, var1.zCoord, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
        this.worldObj.spawnEntityInWorld(var2);
        BlockPos var3 = this.theVillage.func_180608_a();
        var2.func_175449_a(var3, this.theVillage.getVillageRadius());
        return true;
    }

    private Vec3 func_179867_a(BlockPos p_179867_1_) {
        for (int var2 = 0; var2 < 10; ++var2) {
            BlockPos var3 = p_179867_1_.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
            if (!this.theVillage.func_179866_a(var3) || !SpawnerAnimals.func_180267_a(EntityLiving.SpawnPlacementType.ON_GROUND, this.worldObj, var3)) continue;
            return new Vec3(var3.getX(), var3.getY(), var3.getZ());
        }
        return null;
    }
}

