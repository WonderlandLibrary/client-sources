/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableInt;

public class BellTileEntity
extends TileEntity
implements ITickableTileEntity {
    private long ringTime;
    public int ringingTicks;
    public boolean isRinging;
    public Direction ringDirection;
    private List<LivingEntity> entitiesAtRing;
    private boolean shouldReveal;
    private int revealWarmup;

    public BellTileEntity() {
        super(TileEntityType.BELL);
    }

    @Override
    public boolean receiveClientEvent(int n, int n2) {
        if (n == 1) {
            this.func_213941_c();
            this.revealWarmup = 0;
            this.ringDirection = Direction.byIndex(n2);
            this.ringingTicks = 0;
            this.isRinging = true;
            return false;
        }
        return super.receiveClientEvent(n, n2);
    }

    @Override
    public void tick() {
        if (this.isRinging) {
            ++this.ringingTicks;
        }
        if (this.ringingTicks >= 50) {
            this.isRinging = false;
            this.ringingTicks = 0;
        }
        if (this.ringingTicks >= 5 && this.revealWarmup == 0 && this.hasRaidersNearby()) {
            this.shouldReveal = true;
            this.resonate();
        }
        if (this.shouldReveal) {
            if (this.revealWarmup < 40) {
                ++this.revealWarmup;
            } else {
                this.glowRaiders(this.world);
                this.addRaiderParticles(this.world);
                this.shouldReveal = false;
            }
        }
    }

    private void resonate() {
        this.world.playSound(null, this.getPos(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public void ring(Direction direction) {
        BlockPos blockPos = this.getPos();
        this.ringDirection = direction;
        if (this.isRinging) {
            this.ringingTicks = 0;
        } else {
            this.isRinging = true;
        }
        this.world.addBlockEvent(blockPos, this.getBlockState().getBlock(), 1, direction.getIndex());
    }

    private void func_213941_c() {
        BlockPos blockPos = this.getPos();
        if (this.world.getGameTime() > this.ringTime + 60L || this.entitiesAtRing == null) {
            this.ringTime = this.world.getGameTime();
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos).grow(48.0);
            this.entitiesAtRing = this.world.getEntitiesWithinAABB(LivingEntity.class, axisAlignedBB);
        }
        if (!this.world.isRemote) {
            for (LivingEntity livingEntity : this.entitiesAtRing) {
                if (!livingEntity.isAlive() || livingEntity.removed || !blockPos.withinDistance(livingEntity.getPositionVec(), 32.0)) continue;
                livingEntity.getBrain().setMemory(MemoryModuleType.HEARD_BELL_TIME, this.world.getGameTime());
            }
        }
    }

    private boolean hasRaidersNearby() {
        BlockPos blockPos = this.getPos();
        for (LivingEntity livingEntity : this.entitiesAtRing) {
            if (!livingEntity.isAlive() || livingEntity.removed || !blockPos.withinDistance(livingEntity.getPositionVec(), 32.0) || !livingEntity.getType().isContained(EntityTypeTags.RAIDERS)) continue;
            return false;
        }
        return true;
    }

    private void glowRaiders(World world) {
        if (!world.isRemote) {
            this.entitiesAtRing.stream().filter(this::isNearbyRaider).forEach(this::glow);
        }
    }

    private void addRaiderParticles(World world) {
        if (world.isRemote) {
            BlockPos blockPos = this.getPos();
            MutableInt mutableInt = new MutableInt(16700985);
            int n = (int)this.entitiesAtRing.stream().filter(arg_0 -> BellTileEntity.lambda$addRaiderParticles$0(blockPos, arg_0)).count();
            this.entitiesAtRing.stream().filter(this::isNearbyRaider).forEach(arg_0 -> BellTileEntity.lambda$addRaiderParticles$1(blockPos, n, mutableInt, world, arg_0));
        }
    }

    private boolean isNearbyRaider(LivingEntity livingEntity) {
        return livingEntity.isAlive() && !livingEntity.removed && this.getPos().withinDistance(livingEntity.getPositionVec(), 48.0) && livingEntity.getType().isContained(EntityTypeTags.RAIDERS);
    }

    private void glow(LivingEntity livingEntity) {
        livingEntity.addPotionEffect(new EffectInstance(Effects.GLOWING, 60));
    }

    private static void lambda$addRaiderParticles$1(BlockPos blockPos, int n, MutableInt mutableInt, World world, LivingEntity livingEntity) {
        float f = 1.0f;
        float f2 = MathHelper.sqrt((livingEntity.getPosX() - (double)blockPos.getX()) * (livingEntity.getPosX() - (double)blockPos.getX()) + (livingEntity.getPosZ() - (double)blockPos.getZ()) * (livingEntity.getPosZ() - (double)blockPos.getZ()));
        double d = (double)((float)blockPos.getX() + 0.5f) + (double)(1.0f / f2) * (livingEntity.getPosX() - (double)blockPos.getX());
        double d2 = (double)((float)blockPos.getZ() + 0.5f) + (double)(1.0f / f2) * (livingEntity.getPosZ() - (double)blockPos.getZ());
        int n2 = MathHelper.clamp((n - 21) / -2, 3, 15);
        for (int i = 0; i < n2; ++i) {
            int n3 = mutableInt.addAndGet(5);
            double d3 = (double)ColorHelper.PackedColor.getRed(n3) / 255.0;
            double d4 = (double)ColorHelper.PackedColor.getGreen(n3) / 255.0;
            double d5 = (double)ColorHelper.PackedColor.getBlue(n3) / 255.0;
            world.addParticle(ParticleTypes.ENTITY_EFFECT, d, (float)blockPos.getY() + 0.5f, d2, d3, d4, d5);
        }
    }

    private static boolean lambda$addRaiderParticles$0(BlockPos blockPos, LivingEntity livingEntity) {
        return blockPos.withinDistance(livingEntity.getPositionVec(), 48.0);
    }
}

