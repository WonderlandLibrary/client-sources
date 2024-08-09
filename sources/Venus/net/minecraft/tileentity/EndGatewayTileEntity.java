/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.EndGatewayConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EndGatewayTileEntity
extends EndPortalTileEntity
implements ITickableTileEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    private long age;
    private int teleportCooldown;
    @Nullable
    private BlockPos exitPortal;
    private boolean exactTeleport;

    public EndGatewayTileEntity() {
        super(TileEntityType.END_GATEWAY);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        compoundNBT.putLong("Age", this.age);
        if (this.exitPortal != null) {
            compoundNBT.put("ExitPortal", NBTUtil.writeBlockPos(this.exitPortal));
        }
        if (this.exactTeleport) {
            compoundNBT.putBoolean("ExactTeleport", this.exactTeleport);
        }
        return compoundNBT;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.age = compoundNBT.getLong("Age");
        if (compoundNBT.contains("ExitPortal", 1)) {
            this.exitPortal = NBTUtil.readBlockPos(compoundNBT.getCompound("ExitPortal"));
        }
        this.exactTeleport = compoundNBT.getBoolean("ExactTeleport");
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 256.0;
    }

    @Override
    public void tick() {
        boolean bl = this.isSpawning();
        boolean bl2 = this.isCoolingDown();
        ++this.age;
        if (bl2) {
            --this.teleportCooldown;
        } else if (!this.world.isRemote) {
            List<Entity> list = this.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(this.getPos()), EndGatewayTileEntity::func_242690_a);
            if (!list.isEmpty()) {
                this.teleportEntity(list.get(this.world.rand.nextInt(list.size())));
            }
            if (this.age % 2400L == 0L) {
                this.triggerCooldown();
            }
        }
        if (bl != this.isSpawning() || bl2 != this.isCoolingDown()) {
            this.markDirty();
        }
    }

    public static boolean func_242690_a(Entity entity2) {
        return EntityPredicates.NOT_SPECTATING.test(entity2) && !entity2.getLowestRidingEntity().func_242280_ah();
    }

    public boolean isSpawning() {
        return this.age < 200L;
    }

    public boolean isCoolingDown() {
        return this.teleportCooldown > 0;
    }

    public float getSpawnPercent(float f) {
        return MathHelper.clamp(((float)this.age + f) / 200.0f, 0.0f, 1.0f);
    }

    public float getCooldownPercent(float f) {
        return 1.0f - MathHelper.clamp(((float)this.teleportCooldown - f) / 40.0f, 0.0f, 1.0f);
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 8, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public void triggerCooldown() {
        if (!this.world.isRemote) {
            this.teleportCooldown = 40;
            this.world.addBlockEvent(this.getPos(), this.getBlockState().getBlock(), 1, 0);
            this.markDirty();
        }
    }

    @Override
    public boolean receiveClientEvent(int n, int n2) {
        if (n == 1) {
            this.teleportCooldown = 40;
            return false;
        }
        return super.receiveClientEvent(n, n2);
    }

    public void teleportEntity(Entity entity2) {
        if (this.world instanceof ServerWorld && !this.isCoolingDown()) {
            this.teleportCooldown = 100;
            if (this.exitPortal == null && this.world.getDimensionKey() == World.THE_END) {
                this.func_227015_a_((ServerWorld)this.world);
            }
            if (this.exitPortal != null) {
                Entity entity3;
                BlockPos blockPos;
                BlockPos blockPos2 = blockPos = this.exactTeleport ? this.exitPortal : this.findExitPosition();
                if (entity2 instanceof EnderPearlEntity) {
                    Entity entity4 = ((EnderPearlEntity)entity2).func_234616_v_();
                    if (entity4 instanceof ServerPlayerEntity) {
                        CriteriaTriggers.ENTER_BLOCK.trigger((ServerPlayerEntity)entity4, this.world.getBlockState(this.getPos()));
                    }
                    if (entity4 != null) {
                        entity3 = entity4;
                        entity2.remove();
                    } else {
                        entity3 = entity2;
                    }
                } else {
                    entity3 = entity2.getLowestRidingEntity();
                }
                entity3.func_242279_ag();
                entity3.teleportKeepLoaded((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5);
            }
            this.triggerCooldown();
        }
    }

    private BlockPos findExitPosition() {
        BlockPos blockPos = EndGatewayTileEntity.findHighestBlock(this.world, this.exitPortal.add(0, 2, 0), 5, false);
        LOGGER.debug("Best exit position for portal at {} is {}", (Object)this.exitPortal, (Object)blockPos);
        return blockPos.up();
    }

    private void func_227015_a_(ServerWorld serverWorld) {
        Vector3d vector3d = new Vector3d(this.getPos().getX(), 0.0, this.getPos().getZ()).normalize();
        Vector3d vector3d2 = vector3d.scale(1024.0);
        int n = 16;
        while (EndGatewayTileEntity.getChunk(serverWorld, vector3d2).getTopFilledSegment() > 0 && n-- > 0) {
            LOGGER.debug("Skipping backwards past nonempty chunk at {}", (Object)vector3d2);
            vector3d2 = vector3d2.add(vector3d.scale(-16.0));
        }
        n = 16;
        while (EndGatewayTileEntity.getChunk(serverWorld, vector3d2).getTopFilledSegment() == 0 && n-- > 0) {
            LOGGER.debug("Skipping forward past empty chunk at {}", (Object)vector3d2);
            vector3d2 = vector3d2.add(vector3d.scale(16.0));
        }
        LOGGER.debug("Found chunk at {}", (Object)vector3d2);
        Chunk chunk = EndGatewayTileEntity.getChunk(serverWorld, vector3d2);
        this.exitPortal = EndGatewayTileEntity.findSpawnpointInChunk(chunk);
        if (this.exitPortal == null) {
            this.exitPortal = new BlockPos(vector3d2.x + 0.5, 75.0, vector3d2.z + 0.5);
            LOGGER.debug("Failed to find suitable block, settling on {}", (Object)this.exitPortal);
            Features.END_ISLAND.func_242765_a(serverWorld, serverWorld.getChunkProvider().getChunkGenerator(), new Random(this.exitPortal.toLong()), this.exitPortal);
        } else {
            LOGGER.debug("Found block at {}", (Object)this.exitPortal);
        }
        this.exitPortal = EndGatewayTileEntity.findHighestBlock(serverWorld, this.exitPortal, 16, true);
        LOGGER.debug("Creating portal at {}", (Object)this.exitPortal);
        this.exitPortal = this.exitPortal.up(10);
        this.func_227016_a_(serverWorld, this.exitPortal);
        this.markDirty();
    }

    private static BlockPos findHighestBlock(IBlockReader iBlockReader, BlockPos blockPos, int n, boolean bl) {
        Vector3i vector3i = null;
        for (int i = -n; i <= n; ++i) {
            block1: for (int j = -n; j <= n; ++j) {
                if (i == 0 && j == 0 && !bl) continue;
                for (int k = 255; k > (vector3i == null ? 0 : vector3i.getY()); --k) {
                    BlockPos blockPos2 = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
                    BlockState blockState = iBlockReader.getBlockState(blockPos2);
                    if (!blockState.hasOpaqueCollisionShape(iBlockReader, blockPos2) || !bl && blockState.isIn(Blocks.BEDROCK)) continue;
                    vector3i = blockPos2;
                    continue block1;
                }
            }
        }
        return vector3i == null ? blockPos : vector3i;
    }

    private static Chunk getChunk(World world, Vector3d vector3d) {
        return world.getChunk(MathHelper.floor(vector3d.x / 16.0), MathHelper.floor(vector3d.z / 16.0));
    }

    @Nullable
    private static BlockPos findSpawnpointInChunk(Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        BlockPos blockPos = new BlockPos(chunkPos.getXStart(), 30, chunkPos.getZStart());
        int n = chunk.getTopFilledSegment() + 16 - 1;
        BlockPos blockPos2 = new BlockPos(chunkPos.getXEnd(), n, chunkPos.getZEnd());
        BlockPos blockPos3 = null;
        double d = 0.0;
        for (BlockPos blockPos4 : BlockPos.getAllInBoxMutable(blockPos, blockPos2)) {
            BlockState blockState = chunk.getBlockState(blockPos4);
            BlockPos blockPos5 = blockPos4.up();
            BlockPos blockPos6 = blockPos4.up(2);
            if (!blockState.isIn(Blocks.END_STONE) || chunk.getBlockState(blockPos5).hasOpaqueCollisionShape(chunk, blockPos5) || chunk.getBlockState(blockPos6).hasOpaqueCollisionShape(chunk, blockPos6)) continue;
            double d2 = blockPos4.distanceSq(0.0, 0.0, 0.0, false);
            if (blockPos3 != null && !(d2 < d)) continue;
            blockPos3 = blockPos4;
            d = d2;
        }
        return blockPos3;
    }

    private void func_227016_a_(ServerWorld serverWorld, BlockPos blockPos) {
        Feature.END_GATEWAY.withConfiguration(EndGatewayConfig.func_214702_a(this.getPos(), false)).func_242765_a(serverWorld, serverWorld.getChunkProvider().getChunkGenerator(), new Random(), blockPos);
    }

    @Override
    public boolean shouldRenderFace(Direction direction) {
        return Block.shouldSideBeRendered(this.getBlockState(), this.world, this.getPos(), direction);
    }

    public int getParticleAmount() {
        int n = 0;
        for (Direction direction : Direction.values()) {
            n += this.shouldRenderFace(direction) ? 1 : 0;
        }
        return n;
    }

    public void setExitPortal(BlockPos blockPos, boolean bl) {
        this.exactTeleport = bl;
        this.exitPortal = blockPos;
    }
}

