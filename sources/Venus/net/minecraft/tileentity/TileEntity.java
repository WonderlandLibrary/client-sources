/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    private final TileEntityType<?> type;
    @Nullable
    protected World world;
    protected BlockPos pos = BlockPos.ZERO;
    protected boolean removed;
    @Nullable
    private BlockState cachedBlockState;
    private boolean warnedInvalidBlock;

    public TileEntity(TileEntityType<?> tileEntityType) {
        this.type = tileEntityType;
    }

    @Nullable
    public World getWorld() {
        return this.world;
    }

    public void setWorldAndPos(World world, BlockPos blockPos) {
        this.world = world;
        this.pos = blockPos.toImmutable();
    }

    public boolean hasWorld() {
        return this.world != null;
    }

    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        this.pos = new BlockPos(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("z"));
    }

    public CompoundNBT write(CompoundNBT compoundNBT) {
        return this.writeInternal(compoundNBT);
    }

    private CompoundNBT writeInternal(CompoundNBT compoundNBT) {
        ResourceLocation resourceLocation = TileEntityType.getId(this.getType());
        if (resourceLocation == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        compoundNBT.putString("id", resourceLocation.toString());
        compoundNBT.putInt("x", this.pos.getX());
        compoundNBT.putInt("y", this.pos.getY());
        compoundNBT.putInt("z", this.pos.getZ());
        return compoundNBT;
    }

    @Nullable
    public static TileEntity readTileEntity(BlockState blockState, CompoundNBT compoundNBT) {
        String string = compoundNBT.getString("id");
        return Registry.BLOCK_ENTITY_TYPE.getOptional(new ResourceLocation(string)).map(arg_0 -> TileEntity.lambda$readTileEntity$0(string, arg_0)).map(arg_0 -> TileEntity.lambda$readTileEntity$1(blockState, compoundNBT, string, arg_0)).orElseGet(() -> TileEntity.lambda$readTileEntity$2(string));
    }

    public void markDirty() {
        if (this.world != null) {
            this.cachedBlockState = this.world.getBlockState(this.pos);
            this.world.markChunkDirty(this.pos, this);
            if (!this.cachedBlockState.isAir()) {
                this.world.updateComparatorOutputLevel(this.pos, this.cachedBlockState.getBlock());
            }
        }
    }

    public double getMaxRenderDistanceSquared() {
        return 64.0;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public BlockState getBlockState() {
        if (this.cachedBlockState == null) {
            this.cachedBlockState = this.world.getBlockState(this.pos);
        }
        return this.cachedBlockState;
    }

    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return null;
    }

    public CompoundNBT getUpdateTag() {
        return this.writeInternal(new CompoundNBT());
    }

    public boolean isRemoved() {
        return this.removed;
    }

    public void remove() {
        this.removed = true;
    }

    public void validate() {
        this.removed = false;
    }

    public boolean receiveClientEvent(int n, int n2) {
        return true;
    }

    public void updateContainingBlockInfo() {
        this.cachedBlockState = null;
    }

    public void addInfoToCrashReport(CrashReportCategory crashReportCategory) {
        crashReportCategory.addDetail("Name", this::lambda$addInfoToCrashReport$3);
        if (this.world != null) {
            CrashReportCategory.addBlockInfo(crashReportCategory, this.pos, this.getBlockState());
            CrashReportCategory.addBlockInfo(crashReportCategory, this.pos, this.world.getBlockState(this.pos));
        }
    }

    public void setPos(BlockPos blockPos) {
        this.pos = blockPos.toImmutable();
    }

    public boolean onlyOpsCanSetNbt() {
        return true;
    }

    public void rotate(Rotation rotation) {
    }

    public void mirror(Mirror mirror) {
    }

    public TileEntityType<?> getType() {
        return this.type;
    }

    public void warnInvalidBlock() {
        if (!this.warnedInvalidBlock) {
            this.warnedInvalidBlock = true;
            LOGGER.warn("Block entity invalid: {} @ {}", this::lambda$warnInvalidBlock$4, this::getPos);
        }
    }

    private Object lambda$warnInvalidBlock$4() {
        return Registry.BLOCK_ENTITY_TYPE.getKey(this.getType());
    }

    private String lambda$addInfoToCrashReport$3() throws Exception {
        return Registry.BLOCK_ENTITY_TYPE.getKey(this.getType()) + " // " + this.getClass().getCanonicalName();
    }

    private static TileEntity lambda$readTileEntity$2(String string) {
        LOGGER.warn("Skipping BlockEntity with id {}", (Object)string);
        return null;
    }

    private static TileEntity lambda$readTileEntity$1(BlockState blockState, CompoundNBT compoundNBT, String string, TileEntity tileEntity) {
        try {
            tileEntity.read(blockState, compoundNBT);
            return tileEntity;
        } catch (Throwable throwable) {
            LOGGER.error("Failed to load data for block entity {}", (Object)string, (Object)throwable);
            return null;
        }
    }

    private static TileEntity lambda$readTileEntity$0(String string, TileEntityType tileEntityType) {
        try {
            return tileEntityType.create();
        } catch (Throwable throwable) {
            LOGGER.error("Failed to create block entity {}", (Object)string, (Object)throwable);
            return null;
        }
    }
}

