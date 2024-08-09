/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.MoveToSkylightTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;

public class CelebrateRaidVictoryTask
extends Task<VillagerEntity> {
    @Nullable
    private Raid raid;

    public CelebrateRaidVictoryTask(int n, int n2) {
        super(ImmutableMap.of(), n, n2);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        BlockPos blockPos = villagerEntity.getPosition();
        this.raid = serverWorld.findRaid(blockPos);
        return this.raid != null && this.raid.isVictory() && MoveToSkylightTask.func_226306_a_(serverWorld, villagerEntity, blockPos);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return this.raid != null && !this.raid.isStopped();
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        this.raid = null;
        villagerEntity.getBrain().updateActivity(serverWorld.getDayTime(), serverWorld.getGameTime());
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        Random random2 = villagerEntity.getRNG();
        if (random2.nextInt(100) == 0) {
            villagerEntity.playCelebrateSound();
        }
        if (random2.nextInt(200) == 0 && MoveToSkylightTask.func_226306_a_(serverWorld, villagerEntity, villagerEntity.getPosition())) {
            DyeColor dyeColor = Util.getRandomObject(DyeColor.values(), random2);
            int n = random2.nextInt(3);
            ItemStack itemStack = this.makeFirework(dyeColor, n);
            FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(villagerEntity.world, villagerEntity, villagerEntity.getPosX(), villagerEntity.getPosYEye(), villagerEntity.getPosZ(), itemStack);
            villagerEntity.world.addEntity(fireworkRocketEntity);
        }
    }

    private ItemStack makeFirework(DyeColor dyeColor, int n) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET, 1);
        ItemStack itemStack2 = new ItemStack(Items.FIREWORK_STAR);
        CompoundNBT compoundNBT = itemStack2.getOrCreateChildTag("Explosion");
        ArrayList<Integer> arrayList = Lists.newArrayList();
        arrayList.add(dyeColor.getFireworkColor());
        compoundNBT.putIntArray("Colors", arrayList);
        compoundNBT.putByte("Type", (byte)FireworkRocketItem.Shape.BURST.getIndex());
        CompoundNBT compoundNBT2 = itemStack.getOrCreateChildTag("Fireworks");
        ListNBT listNBT = new ListNBT();
        CompoundNBT compoundNBT3 = itemStack2.getChildTag("Explosion");
        if (compoundNBT3 != null) {
            listNBT.add(compoundNBT3);
        }
        compoundNBT2.putByte("Flight", (byte)n);
        if (!listNBT.isEmpty()) {
            compoundNBT2.put("Explosions", listNBT);
        }
        return itemStack;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (VillagerEntity)livingEntity);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        return this.shouldContinueExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.resetTask(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.updateTask(serverWorld, (VillagerEntity)livingEntity, l);
    }
}

