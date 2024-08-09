/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;

public class BeehiveTileEntity
extends TileEntity
implements ITickableTileEntity {
    private final List<Bee> bees = Lists.newArrayList();
    @Nullable
    private BlockPos flowerPos = null;

    public BeehiveTileEntity() {
        super(TileEntityType.BEEHIVE);
    }

    @Override
    public void markDirty() {
        if (this.isNearFire()) {
            this.angerBees(null, this.world.getBlockState(this.getPos()), State.EMERGENCY);
        }
        super.markDirty();
    }

    public boolean isNearFire() {
        if (this.world == null) {
            return true;
        }
        for (BlockPos blockPos : BlockPos.getAllInBoxMutable(this.pos.add(-1, -1, -1), this.pos.add(1, 1, 1))) {
            if (!(this.world.getBlockState(blockPos).getBlock() instanceof FireBlock)) continue;
            return false;
        }
        return true;
    }

    public boolean hasNoBees() {
        return this.bees.isEmpty();
    }

    public boolean isFullOfBees() {
        return this.bees.size() == 3;
    }

    public void angerBees(@Nullable PlayerEntity playerEntity, BlockState blockState, State state) {
        List<Entity> list = this.tryReleaseBee(blockState, state);
        if (playerEntity != null) {
            for (Entity entity2 : list) {
                if (!(entity2 instanceof BeeEntity)) continue;
                BeeEntity beeEntity = (BeeEntity)entity2;
                if (!(playerEntity.getPositionVec().squareDistanceTo(entity2.getPositionVec()) <= 16.0)) continue;
                if (!this.isSmoked()) {
                    beeEntity.setAttackTarget(playerEntity);
                    continue;
                }
                beeEntity.setStayOutOfHiveCountdown(400);
            }
        }
    }

    private List<Entity> tryReleaseBee(BlockState blockState, State state) {
        ArrayList<Entity> arrayList = Lists.newArrayList();
        this.bees.removeIf(arg_0 -> this.lambda$tryReleaseBee$0(blockState, arrayList, state, arg_0));
        return arrayList;
    }

    public void tryEnterHive(Entity entity2, boolean bl) {
        this.tryEnterHive(entity2, bl, 1);
    }

    public int getBeeCount() {
        return this.bees.size();
    }

    public static int getHoneyLevel(BlockState blockState) {
        return blockState.get(BeehiveBlock.HONEY_LEVEL);
    }

    public boolean isSmoked() {
        return CampfireBlock.isSmokingBlockAt(this.world, this.getPos());
    }

    protected void sendDebugData() {
        DebugPacketSender.sendBeehiveDebugData(this);
    }

    public void tryEnterHive(Entity entity2, boolean bl, int n) {
        if (this.bees.size() < 3) {
            entity2.stopRiding();
            entity2.removePassengers();
            CompoundNBT compoundNBT = new CompoundNBT();
            entity2.writeUnlessPassenger(compoundNBT);
            this.bees.add(new Bee(compoundNBT, n, bl ? 2400 : 600));
            if (this.world != null) {
                Object object;
                if (entity2 instanceof BeeEntity && ((BeeEntity)(object = (BeeEntity)entity2)).hasFlower() && (!this.hasFlowerPos() || this.world.rand.nextBoolean())) {
                    this.flowerPos = ((BeeEntity)object).getFlowerPos();
                }
                object = this.getPos();
                this.world.playSound(null, (double)((Vector3i)object).getX(), (double)((Vector3i)object).getY(), ((Vector3i)object).getZ(), SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            entity2.remove();
        }
    }

    private boolean func_235651_a_(BlockState blockState, Bee bee, @Nullable List<Entity> list, State state) {
        boolean bl;
        if ((this.world.isNightTime() || this.world.isRaining()) && state != State.EMERGENCY) {
            return true;
        }
        BlockPos blockPos = this.getPos();
        CompoundNBT compoundNBT = bee.entityData;
        compoundNBT.remove("Passengers");
        compoundNBT.remove("Leash");
        compoundNBT.remove("UUID");
        Direction direction = blockState.get(BeehiveBlock.FACING);
        BlockPos blockPos2 = blockPos.offset(direction);
        boolean bl2 = bl = !this.world.getBlockState(blockPos2).getCollisionShape(this.world, blockPos2).isEmpty();
        if (bl && state != State.EMERGENCY) {
            return true;
        }
        Entity entity2 = EntityType.loadEntityAndExecute(compoundNBT, this.world, BeehiveTileEntity::lambda$func_235651_a_$1);
        if (entity2 != null) {
            if (!entity2.getType().isContained(EntityTypeTags.BEEHIVE_INHABITORS)) {
                return true;
            }
            if (entity2 instanceof BeeEntity) {
                BeeEntity beeEntity = (BeeEntity)entity2;
                if (this.hasFlowerPos() && !beeEntity.hasFlower() && this.world.rand.nextFloat() < 0.9f) {
                    beeEntity.setFlowerPos(this.flowerPos);
                }
                if (state == State.HONEY_DELIVERED) {
                    int n;
                    beeEntity.onHoneyDelivered();
                    if (blockState.getBlock().isIn(BlockTags.BEEHIVES) && (n = BeehiveTileEntity.getHoneyLevel(blockState)) < 5) {
                        int n2;
                        int n3 = n2 = this.world.rand.nextInt(100) == 0 ? 2 : 1;
                        if (n + n2 > 5) {
                            --n2;
                        }
                        this.world.setBlockState(this.getPos(), (BlockState)blockState.with(BeehiveBlock.HONEY_LEVEL, n + n2));
                    }
                }
                this.func_235650_a_(bee.ticksInHive, beeEntity);
                if (list != null) {
                    list.add(beeEntity);
                }
                float f = entity2.getWidth();
                double d = bl ? 0.0 : 0.55 + (double)(f / 2.0f);
                double d2 = (double)blockPos.getX() + 0.5 + d * (double)direction.getXOffset();
                double d3 = (double)blockPos.getY() + 0.5 - (double)(entity2.getHeight() / 2.0f);
                double d4 = (double)blockPos.getZ() + 0.5 + d * (double)direction.getZOffset();
                entity2.setLocationAndAngles(d2, d3, d4, entity2.rotationYaw, entity2.rotationPitch);
            }
            this.world.playSound(null, blockPos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return this.world.addEntity(entity2);
        }
        return true;
    }

    private void func_235650_a_(int n, BeeEntity beeEntity) {
        int n2 = beeEntity.getGrowingAge();
        if (n2 < 0) {
            beeEntity.setGrowingAge(Math.min(0, n2 + n));
        } else if (n2 > 0) {
            beeEntity.setGrowingAge(Math.max(0, n2 - n));
        }
        beeEntity.setInLove(Math.max(0, beeEntity.func_234178_eO_() - n));
        beeEntity.resetTicksWithoutNectar();
    }

    private boolean hasFlowerPos() {
        return this.flowerPos != null;
    }

    private void tickBees() {
        Iterator<Bee> iterator2 = this.bees.iterator();
        BlockState blockState = this.getBlockState();
        while (iterator2.hasNext()) {
            Bee bee = iterator2.next();
            if (bee.ticksInHive > bee.minOccupationTicks) {
                State state;
                State state2 = state = bee.entityData.getBoolean("HasNectar") ? State.HONEY_DELIVERED : State.BEE_RELEASED;
                if (this.func_235651_a_(blockState, bee, null, state)) {
                    iterator2.remove();
                }
            }
            ++bee.ticksInHive;
        }
    }

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            this.tickBees();
            BlockPos blockPos = this.getPos();
            if (this.bees.size() > 0 && this.world.getRandom().nextDouble() < 0.005) {
                double d = (double)blockPos.getX() + 0.5;
                double d2 = blockPos.getY();
                double d3 = (double)blockPos.getZ() + 0.5;
                this.world.playSound(null, d, d2, d3, SoundEvents.BLOCK_BEEHIVE_WORK, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            this.sendDebugData();
        }
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.bees.clear();
        ListNBT listNBT = compoundNBT.getList("Bees", 10);
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT2 = listNBT.getCompound(i);
            Bee bee = new Bee(compoundNBT2.getCompound("EntityData"), compoundNBT2.getInt("TicksInHive"), compoundNBT2.getInt("MinOccupationTicks"));
            this.bees.add(bee);
        }
        this.flowerPos = null;
        if (compoundNBT.contains("FlowerPos")) {
            this.flowerPos = NBTUtil.readBlockPos(compoundNBT.getCompound("FlowerPos"));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        compoundNBT.put("Bees", this.getBees());
        if (this.hasFlowerPos()) {
            compoundNBT.put("FlowerPos", NBTUtil.writeBlockPos(this.flowerPos));
        }
        return compoundNBT;
    }

    public ListNBT getBees() {
        ListNBT listNBT = new ListNBT();
        for (Bee bee : this.bees) {
            bee.entityData.remove("UUID");
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("EntityData", bee.entityData);
            compoundNBT.putInt("TicksInHive", bee.ticksInHive);
            compoundNBT.putInt("MinOccupationTicks", bee.minOccupationTicks);
            listNBT.add(compoundNBT);
        }
        return listNBT;
    }

    private static Entity lambda$func_235651_a_$1(Entity entity2) {
        return entity2;
    }

    private boolean lambda$tryReleaseBee$0(BlockState blockState, List list, State state, Bee bee) {
        return this.func_235651_a_(blockState, bee, list, state);
    }

    public static enum State {
        HONEY_DELIVERED,
        BEE_RELEASED,
        EMERGENCY;

    }

    static class Bee {
        private final CompoundNBT entityData;
        private int ticksInHive;
        private final int minOccupationTicks;

        private Bee(CompoundNBT compoundNBT, int n, int n2) {
            compoundNBT.remove("UUID");
            this.entityData = compoundNBT;
            this.ticksInHive = n;
            this.minOccupationTicks = n2;
        }
    }
}

