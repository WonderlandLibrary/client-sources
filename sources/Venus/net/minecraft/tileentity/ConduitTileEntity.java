/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class ConduitTileEntity
extends TileEntity
implements ITickableTileEntity {
    private static final Block[] field_205042_e = new Block[]{Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.SEA_LANTERN, Blocks.DARK_PRISMARINE};
    public int ticksExisted;
    private float activeRotation;
    private boolean active;
    private boolean eyeOpen;
    private final List<BlockPos> prismarinePositions = Lists.newArrayList();
    @Nullable
    private LivingEntity target;
    @Nullable
    private UUID targetUuid;
    private long nextSoundTime;

    public ConduitTileEntity() {
        this(TileEntityType.CONDUIT);
    }

    public ConduitTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.targetUuid = compoundNBT.hasUniqueId("Target") ? compoundNBT.getUniqueId("Target") : null;
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        if (this.target != null) {
            compoundNBT.putUniqueId("Target", this.target.getUniqueID());
        }
        return compoundNBT;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 5, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void tick() {
        ++this.ticksExisted;
        long l = this.world.getGameTime();
        if (l % 40L == 0L) {
            this.setActive(this.shouldBeActive());
            if (!this.world.isRemote && this.isActive()) {
                this.addEffectsToPlayers();
                this.attackMobs();
            }
        }
        if (l % 80L == 0L && this.isActive()) {
            this.playSound(SoundEvents.BLOCK_CONDUIT_AMBIENT);
        }
        if (l > this.nextSoundTime && this.isActive()) {
            this.nextSoundTime = l + 60L + (long)this.world.getRandom().nextInt(40);
            this.playSound(SoundEvents.BLOCK_CONDUIT_AMBIENT_SHORT);
        }
        if (this.world.isRemote) {
            this.updateClientTarget();
            this.spawnParticles();
            if (this.isActive()) {
                this.activeRotation += 1.0f;
            }
        }
    }

    private boolean shouldBeActive() {
        int n;
        int n2;
        int n3;
        this.prismarinePositions.clear();
        for (n3 = -1; n3 <= 1; ++n3) {
            for (n2 = -1; n2 <= 1; ++n2) {
                for (n = -1; n <= 1; ++n) {
                    BlockPos blockPos = this.pos.add(n3, n2, n);
                    if (this.world.hasWater(blockPos)) continue;
                    return true;
                }
            }
        }
        for (n3 = -2; n3 <= 2; ++n3) {
            for (n2 = -2; n2 <= 2; ++n2) {
                for (n = -2; n <= 2; ++n) {
                    int n4 = Math.abs(n3);
                    int n5 = Math.abs(n2);
                    int n6 = Math.abs(n);
                    if (n4 <= 1 && n5 <= 1 && n6 <= 1 || (n3 != 0 || n5 != 2 && n6 != 2) && (n2 != 0 || n4 != 2 && n6 != 2) && (n != 0 || n4 != 2 && n5 != 2)) continue;
                    BlockPos blockPos = this.pos.add(n3, n2, n);
                    BlockState blockState = this.world.getBlockState(blockPos);
                    for (Block block : field_205042_e) {
                        if (!blockState.isIn(block)) continue;
                        this.prismarinePositions.add(blockPos);
                    }
                }
            }
        }
        this.setEyeOpen(this.prismarinePositions.size() >= 42);
        return this.prismarinePositions.size() >= 16;
    }

    private void addEffectsToPlayers() {
        int n;
        int n2;
        int n3 = this.prismarinePositions.size();
        int n4 = n3 / 7 * 16;
        int n5 = this.pos.getX();
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(n5, n2 = this.pos.getY(), n = this.pos.getZ(), n5 + 1, n2 + 1, n + 1).grow(n4).expand(0.0, this.world.getHeight(), 0.0);
        List<PlayerEntity> list = this.world.getEntitiesWithinAABB(PlayerEntity.class, axisAlignedBB);
        if (!list.isEmpty()) {
            for (PlayerEntity playerEntity : list) {
                if (!this.pos.withinDistance(playerEntity.getPosition(), (double)n4) || !playerEntity.isWet()) continue;
                playerEntity.addPotionEffect(new EffectInstance(Effects.CONDUIT_POWER, 260, 0, true, true));
            }
        }
    }

    private void attackMobs() {
        List<LivingEntity> list;
        LivingEntity livingEntity = this.target;
        int n = this.prismarinePositions.size();
        if (n < 42) {
            this.target = null;
        } else if (this.target == null && this.targetUuid != null) {
            this.target = this.findExistingTarget();
            this.targetUuid = null;
        } else if (this.target == null) {
            list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getAreaOfEffect(), ConduitTileEntity::lambda$attackMobs$0);
            if (!list.isEmpty()) {
                this.target = (LivingEntity)list.get(this.world.rand.nextInt(list.size()));
            }
        } else if (!this.target.isAlive() || !this.pos.withinDistance(this.target.getPosition(), 8.0)) {
            this.target = null;
        }
        if (this.target != null) {
            this.world.playSound(null, this.target.getPosX(), this.target.getPosY(), this.target.getPosZ(), SoundEvents.BLOCK_CONDUIT_ATTACK_TARGET, SoundCategory.BLOCKS, 1.0f, 1.0f);
            this.target.attackEntityFrom(DamageSource.MAGIC, 4.0f);
        }
        if (livingEntity != this.target) {
            list = this.getBlockState();
            this.world.notifyBlockUpdate(this.pos, (BlockState)((Object)list), (BlockState)((Object)list), 2);
        }
    }

    private void updateClientTarget() {
        if (this.targetUuid == null) {
            this.target = null;
        } else if (this.target == null || !this.target.getUniqueID().equals(this.targetUuid)) {
            this.target = this.findExistingTarget();
            if (this.target == null) {
                this.targetUuid = null;
            }
        }
    }

    private AxisAlignedBB getAreaOfEffect() {
        int n = this.pos.getX();
        int n2 = this.pos.getY();
        int n3 = this.pos.getZ();
        return new AxisAlignedBB(n, n2, n3, n + 1, n2 + 1, n3 + 1).grow(8.0);
    }

    @Nullable
    private LivingEntity findExistingTarget() {
        List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getAreaOfEffect(), this::lambda$findExistingTarget$1);
        return list.size() == 1 ? list.get(0) : null;
    }

    private void spawnParticles() {
        float f;
        float f2;
        Random random2 = this.world.rand;
        double d = MathHelper.sin((float)(this.ticksExisted + 35) * 0.1f) / 2.0f + 0.5f;
        d = (d * d + d) * (double)0.3f;
        Vector3d vector3d = new Vector3d((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 1.5 + d, (double)this.pos.getZ() + 0.5);
        for (BlockPos blockPos : this.prismarinePositions) {
            if (random2.nextInt(50) != 0) continue;
            f2 = -0.5f + random2.nextFloat();
            f = -2.0f + random2.nextFloat();
            float f3 = -0.5f + random2.nextFloat();
            BlockPos blockPos2 = blockPos.subtract(this.pos);
            Vector3d vector3d2 = new Vector3d(f2, f, f3).add(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
            this.world.addParticle(ParticleTypes.NAUTILUS, vector3d.x, vector3d.y, vector3d.z, vector3d2.x, vector3d2.y, vector3d2.z);
        }
        if (this.target != null) {
            Vector3d vector3d3 = new Vector3d(this.target.getPosX(), this.target.getPosYEye(), this.target.getPosZ());
            float f4 = (-0.5f + random2.nextFloat()) * (3.0f + this.target.getWidth());
            f2 = -1.0f + random2.nextFloat() * this.target.getHeight();
            f = (-0.5f + random2.nextFloat()) * (3.0f + this.target.getWidth());
            Vector3d vector3d4 = new Vector3d(f4, f2, f);
            this.world.addParticle(ParticleTypes.NAUTILUS, vector3d3.x, vector3d3.y, vector3d3.z, vector3d4.x, vector3d4.y, vector3d4.z);
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isEyeOpen() {
        return this.eyeOpen;
    }

    private void setActive(boolean bl) {
        if (bl != this.active) {
            this.playSound(bl ? SoundEvents.BLOCK_CONDUIT_ACTIVATE : SoundEvents.BLOCK_CONDUIT_DEACTIVATE);
        }
        this.active = bl;
    }

    private void setEyeOpen(boolean bl) {
        this.eyeOpen = bl;
    }

    public float getActiveRotation(float f) {
        return (this.activeRotation + f) * -0.0375f;
    }

    public void playSound(SoundEvent soundEvent) {
        this.world.playSound(null, this.pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    private boolean lambda$findExistingTarget$1(LivingEntity livingEntity) {
        return livingEntity.getUniqueID().equals(this.targetUuid);
    }

    private static boolean lambda$attackMobs$0(LivingEntity livingEntity) {
        return livingEntity instanceof IMob && livingEntity.isWet();
    }
}

