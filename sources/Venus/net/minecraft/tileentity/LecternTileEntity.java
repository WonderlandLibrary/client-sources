/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.LecternContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class LecternTileEntity
extends TileEntity
implements IClearable,
INamedContainerProvider {
    private final IInventory inventory = new IInventory(this){
        final LecternTileEntity this$0;
        {
            this.this$0 = lecternTileEntity;
        }

        @Override
        public int getSizeInventory() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return this.this$0.book.isEmpty();
        }

        @Override
        public ItemStack getStackInSlot(int n) {
            return n == 0 ? this.this$0.book : ItemStack.EMPTY;
        }

        @Override
        public ItemStack decrStackSize(int n, int n2) {
            if (n == 0) {
                ItemStack itemStack = this.this$0.book.split(n2);
                if (this.this$0.book.isEmpty()) {
                    this.this$0.bookRemoved();
                }
                return itemStack;
            }
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack removeStackFromSlot(int n) {
            if (n == 0) {
                ItemStack itemStack = this.this$0.book;
                this.this$0.book = ItemStack.EMPTY;
                this.this$0.bookRemoved();
                return itemStack;
            }
            return ItemStack.EMPTY;
        }

        @Override
        public void setInventorySlotContents(int n, ItemStack itemStack) {
        }

        @Override
        public int getInventoryStackLimit() {
            return 0;
        }

        @Override
        public void markDirty() {
            this.this$0.markDirty();
        }

        @Override
        public boolean isUsableByPlayer(PlayerEntity playerEntity) {
            if (this.this$0.world.getTileEntity(this.this$0.pos) != this.this$0) {
                return true;
            }
            return playerEntity.getDistanceSq((double)this.this$0.pos.getX() + 0.5, (double)this.this$0.pos.getY() + 0.5, (double)this.this$0.pos.getZ() + 0.5) > 64.0 ? false : this.this$0.hasBook();
        }

        @Override
        public boolean isItemValidForSlot(int n, ItemStack itemStack) {
            return true;
        }

        @Override
        public void clear() {
        }
    };
    private final IIntArray field_214049_b = new IIntArray(this){
        final LecternTileEntity this$0;
        {
            this.this$0 = lecternTileEntity;
        }

        @Override
        public int get(int n) {
            return n == 0 ? this.this$0.page : 0;
        }

        @Override
        public void set(int n, int n2) {
            if (n == 0) {
                this.this$0.setPage(n2);
            }
        }

        @Override
        public int size() {
            return 0;
        }
    };
    private ItemStack book = ItemStack.EMPTY;
    private int page;
    private int pages;

    public LecternTileEntity() {
        super(TileEntityType.LECTERN);
    }

    public ItemStack getBook() {
        return this.book;
    }

    public boolean hasBook() {
        Item item = this.book.getItem();
        return item == Items.WRITABLE_BOOK || item == Items.WRITTEN_BOOK;
    }

    public void setBook(ItemStack itemStack) {
        this.setBook(itemStack, null);
    }

    private void bookRemoved() {
        this.page = 0;
        this.pages = 0;
        LecternBlock.setHasBook(this.getWorld(), this.getPos(), this.getBlockState(), false);
    }

    public void setBook(ItemStack itemStack, @Nullable PlayerEntity playerEntity) {
        this.book = this.ensureResolved(itemStack, playerEntity);
        this.page = 0;
        this.pages = WrittenBookItem.getPageCount(this.book);
        this.markDirty();
    }

    private void setPage(int n) {
        int n2 = MathHelper.clamp(n, 0, this.pages - 1);
        if (n2 != this.page) {
            this.page = n2;
            this.markDirty();
            LecternBlock.pulse(this.getWorld(), this.getPos(), this.getBlockState());
        }
    }

    public int getPage() {
        return this.page;
    }

    public int getComparatorSignalLevel() {
        float f = this.pages > 1 ? (float)this.getPage() / ((float)this.pages - 1.0f) : 1.0f;
        return MathHelper.floor(f * 14.0f) + (this.hasBook() ? 1 : 0);
    }

    private ItemStack ensureResolved(ItemStack itemStack, @Nullable PlayerEntity playerEntity) {
        if (this.world instanceof ServerWorld && itemStack.getItem() == Items.WRITTEN_BOOK) {
            WrittenBookItem.resolveContents(itemStack, this.createCommandSource(playerEntity), playerEntity);
        }
        return itemStack;
    }

    private CommandSource createCommandSource(@Nullable PlayerEntity playerEntity) {
        ITextComponent iTextComponent;
        String string;
        if (playerEntity == null) {
            string = "Lectern";
            iTextComponent = new StringTextComponent("Lectern");
        } else {
            string = playerEntity.getName().getString();
            iTextComponent = playerEntity.getDisplayName();
        }
        Vector3d vector3d = Vector3d.copyCentered(this.pos);
        return new CommandSource(ICommandSource.DUMMY, vector3d, Vector2f.ZERO, (ServerWorld)this.world, 2, string, iTextComponent, this.world.getServer(), playerEntity);
    }

    @Override
    public boolean onlyOpsCanSetNbt() {
        return false;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.book = compoundNBT.contains("Book", 1) ? this.ensureResolved(ItemStack.read(compoundNBT.getCompound("Book")), null) : ItemStack.EMPTY;
        this.pages = WrittenBookItem.getPageCount(this.book);
        this.page = MathHelper.clamp(compoundNBT.getInt("Page"), 0, this.pages - 1);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        if (!this.getBook().isEmpty()) {
            compoundNBT.put("Book", this.getBook().write(new CompoundNBT()));
            compoundNBT.putInt("Page", this.page);
        }
        return compoundNBT;
    }

    @Override
    public void clear() {
        this.setBook(ItemStack.EMPTY);
    }

    @Override
    public Container createMenu(int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new LecternContainer(n, this.inventory, this.field_214049_b);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.lectern");
    }
}

