/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ChestTileEntity
extends LockableLootTileEntity
implements IChestLid,
ITickableTileEntity {
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(27, ItemStack.EMPTY);
    protected float lidAngle;
    protected float prevLidAngle;
    protected int numPlayersUsing;
    private int ticksSinceSync;

    protected ChestTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public ChestTileEntity() {
        this(TileEntityType.CHEST);
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.chest");
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compoundNBT)) {
            ItemStackHelper.loadAllItems(compoundNBT, this.chestContents);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        if (!this.checkLootAndWrite(compoundNBT)) {
            ItemStackHelper.saveAllItems(compoundNBT, this.chestContents);
        }
        return compoundNBT;
    }

    @Override
    public void tick() {
        int n = this.pos.getX();
        int n2 = this.pos.getY();
        int n3 = this.pos.getZ();
        ++this.ticksSinceSync;
        this.numPlayersUsing = ChestTileEntity.calculatePlayersUsingSync(this.world, this, this.ticksSinceSync, n, n2, n3, this.numPlayersUsing);
        this.prevLidAngle = this.lidAngle;
        float f = 0.1f;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0f) {
            this.playSound(SoundEvents.BLOCK_CHEST_OPEN);
        }
        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0f || this.numPlayersUsing > 0 && this.lidAngle < 1.0f) {
            float f2 = this.lidAngle;
            this.lidAngle = this.numPlayersUsing > 0 ? (this.lidAngle += 0.1f) : (this.lidAngle -= 0.1f);
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            float f3 = 0.5f;
            if (this.lidAngle < 0.5f && f2 >= 0.5f) {
                this.playSound(SoundEvents.BLOCK_CHEST_CLOSE);
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }

    public static int calculatePlayersUsingSync(World world, LockableTileEntity lockableTileEntity, int n, int n2, int n3, int n4, int n5) {
        if (!world.isRemote && n5 != 0 && (n + n2 + n3 + n4) % 200 == 0) {
            n5 = ChestTileEntity.calculatePlayersUsing(world, lockableTileEntity, n2, n3, n4);
        }
        return n5;
    }

    public static int calculatePlayersUsing(World world, LockableTileEntity lockableTileEntity, int n, int n2, int n3) {
        int n4 = 0;
        float f = 5.0f;
        for (PlayerEntity playerEntity : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((float)n - 5.0f, (float)n2 - 5.0f, (float)n3 - 5.0f, (float)(n + 1) + 5.0f, (float)(n2 + 1) + 5.0f, (float)(n3 + 1) + 5.0f))) {
            IInventory iInventory;
            if (!(playerEntity.openContainer instanceof ChestContainer) || (iInventory = ((ChestContainer)playerEntity.openContainer).getLowerChestInventory()) != lockableTileEntity && (!(iInventory instanceof DoubleSidedInventory) || !((DoubleSidedInventory)iInventory).isPartOfLargeChest(lockableTileEntity))) continue;
            ++n4;
        }
        return n4;
    }

    private void playSound(SoundEvent soundEvent) {
        ChestType chestType = this.getBlockState().get(ChestBlock.TYPE);
        if (chestType != ChestType.LEFT) {
            double d = (double)this.pos.getX() + 0.5;
            double d2 = (double)this.pos.getY() + 0.5;
            double d3 = (double)this.pos.getZ() + 0.5;
            if (chestType == ChestType.RIGHT) {
                Direction direction = ChestBlock.getDirectionToAttached(this.getBlockState());
                d += (double)direction.getXOffset() * 0.5;
                d3 += (double)direction.getZOffset() * 0.5;
            }
            this.world.playSound(null, d, d2, d3, soundEvent, SoundCategory.BLOCKS, 0.5f, this.world.rand.nextFloat() * 0.1f + 0.9f);
        }
    }

    @Override
    public boolean receiveClientEvent(int n, int n2) {
        if (n == 1) {
            this.numPlayersUsing = n2;
            return false;
        }
        return super.receiveClientEvent(n, n2);
    }

    @Override
    public void openInventory(PlayerEntity playerEntity) {
        if (!playerEntity.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    @Override
    public void closeInventory(PlayerEntity playerEntity) {
        if (!playerEntity.isSpectator()) {
            --this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    protected void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof ChestBlock) {
            this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, block);
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.chestContents = nonNullList;
    }

    @Override
    public float getLidAngle(float f) {
        return MathHelper.lerp(f, this.prevLidAngle, this.lidAngle);
    }

    public static int getPlayersUsing(IBlockReader iBlockReader, BlockPos blockPos) {
        TileEntity tileEntity;
        BlockState blockState = iBlockReader.getBlockState(blockPos);
        if (blockState.getBlock().isTileEntityProvider() && (tileEntity = iBlockReader.getTileEntity(blockPos)) instanceof ChestTileEntity) {
            return ((ChestTileEntity)tileEntity).numPlayersUsing;
        }
        return 1;
    }

    public static void swapContents(ChestTileEntity chestTileEntity, ChestTileEntity chestTileEntity2) {
        NonNullList<ItemStack> nonNullList = chestTileEntity.getItems();
        chestTileEntity.setItems(chestTileEntity2.getItems());
        chestTileEntity2.setItems(nonNullList);
    }

    @Override
    protected Container createMenu(int n, PlayerInventory playerInventory) {
        return ChestContainer.createGeneric9X3(n, playerInventory, this);
    }
}

