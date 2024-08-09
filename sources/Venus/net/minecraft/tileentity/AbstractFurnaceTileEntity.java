/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class AbstractFurnaceTileEntity
extends LockableTileEntity
implements ISidedInventory,
IRecipeHolder,
IRecipeHelperPopulator,
ITickableTileEntity {
    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int burnTime;
    private int recipesUsed;
    private int cookTime;
    private int cookTimeTotal;
    protected final IIntArray furnaceData = new IIntArray(this){
        final AbstractFurnaceTileEntity this$0;
        {
            this.this$0 = abstractFurnaceTileEntity;
        }

        @Override
        public int get(int n) {
            switch (n) {
                case 0: {
                    return this.this$0.burnTime;
                }
                case 1: {
                    return this.this$0.recipesUsed;
                }
                case 2: {
                    return this.this$0.cookTime;
                }
                case 3: {
                    return this.this$0.cookTimeTotal;
                }
            }
            return 1;
        }

        @Override
        public void set(int n, int n2) {
            switch (n) {
                case 0: {
                    this.this$0.burnTime = n2;
                    break;
                }
                case 1: {
                    this.this$0.recipesUsed = n2;
                    break;
                }
                case 2: {
                    this.this$0.cookTime = n2;
                    break;
                }
                case 3: {
                    this.this$0.cookTimeTotal = n2;
                }
            }
        }

        @Override
        public int size() {
            return 1;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap();
    protected final IRecipeType<? extends AbstractCookingRecipe> recipeType;

    protected AbstractFurnaceTileEntity(TileEntityType<?> tileEntityType, IRecipeType<? extends AbstractCookingRecipe> iRecipeType) {
        super(tileEntityType);
        this.recipeType = iRecipeType;
    }

    public static Map<Item, Integer> getBurnTimes() {
        LinkedHashMap<Item, Integer> linkedHashMap = Maps.newLinkedHashMap();
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.LAVA_BUCKET, 20000);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.COAL_BLOCK, 16000);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.BLAZE_ROD, 2400);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.COAL, 1600);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.CHARCOAL, 1600);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.LOGS, 300);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.PLANKS, 300);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.WOODEN_STAIRS, 300);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.WOODEN_SLABS, 150);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.WOODEN_TRAPDOORS, 300);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.WOODEN_PRESSURE_PLATES, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.OAK_FENCE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.BIRCH_FENCE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.SPRUCE_FENCE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.JUNGLE_FENCE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.DARK_OAK_FENCE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.ACACIA_FENCE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.OAK_FENCE_GATE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.BIRCH_FENCE_GATE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.SPRUCE_FENCE_GATE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.JUNGLE_FENCE_GATE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.DARK_OAK_FENCE_GATE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.ACACIA_FENCE_GATE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.NOTE_BLOCK, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.BOOKSHELF, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.LECTERN, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.JUKEBOX, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.CHEST, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.TRAPPED_CHEST, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.CRAFTING_TABLE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.DAYLIGHT_DETECTOR, 300);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.BANNERS, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.BOW, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.FISHING_ROD, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.LADDER, 300);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.SIGNS, 200);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.WOODEN_SHOVEL, 200);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.WOODEN_SWORD, 200);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.WOODEN_HOE, 200);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.WOODEN_AXE, 200);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.WOODEN_PICKAXE, 200);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.WOODEN_DOORS, 200);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.BOATS, 1200);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.WOOL, 100);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.WOODEN_BUTTONS, 100);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.STICK, 100);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.SAPLINGS, 100);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.BOWL, 100);
        AbstractFurnaceTileEntity.addItemTagBurnTime(linkedHashMap, ItemTags.CARPETS, 67);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.DRIED_KELP_BLOCK, 4001);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Items.CROSSBOW, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.BAMBOO, 50);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.DEAD_BUSH, 100);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.SCAFFOLDING, 400);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.LOOM, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.BARREL, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.CARTOGRAPHY_TABLE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.FLETCHING_TABLE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.SMITHING_TABLE, 300);
        AbstractFurnaceTileEntity.addItemBurnTime(linkedHashMap, Blocks.COMPOSTER, 300);
        return linkedHashMap;
    }

    private static boolean isNonFlammable(Item item) {
        return ItemTags.NON_FLAMMABLE_WOOD.contains(item);
    }

    private static void addItemTagBurnTime(Map<Item, Integer> map, ITag<Item> iTag, int n) {
        for (Item item : iTag.getAllElements()) {
            if (AbstractFurnaceTileEntity.isNonFlammable(item)) continue;
            map.put(item, n);
        }
    }

    private static void addItemBurnTime(Map<Item, Integer> map, IItemProvider iItemProvider, int n) {
        Item item = iItemProvider.asItem();
        if (AbstractFurnaceTileEntity.isNonFlammable(item)) {
            if (SharedConstants.developmentMode) {
                throw Util.pauseDevMode(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item.getDisplayName(null).getString() + " a furnace fuel. That will not work!"));
            }
        } else {
            map.put(item, n);
        }
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compoundNBT, this.items);
        this.burnTime = compoundNBT.getShort("BurnTime");
        this.cookTime = compoundNBT.getShort("CookTime");
        this.cookTimeTotal = compoundNBT.getShort("CookTimeTotal");
        this.recipesUsed = this.getBurnTime(this.items.get(1));
        CompoundNBT compoundNBT2 = compoundNBT.getCompound("RecipesUsed");
        for (String string : compoundNBT2.keySet()) {
            this.recipes.put(new ResourceLocation(string), compoundNBT2.getInt(string));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        compoundNBT.putShort("BurnTime", (short)this.burnTime);
        compoundNBT.putShort("CookTime", (short)this.cookTime);
        compoundNBT.putShort("CookTimeTotal", (short)this.cookTimeTotal);
        ItemStackHelper.saveAllItems(compoundNBT, this.items);
        CompoundNBT compoundNBT2 = new CompoundNBT();
        this.recipes.forEach((arg_0, arg_1) -> AbstractFurnaceTileEntity.lambda$write$0(compoundNBT2, arg_0, arg_1));
        compoundNBT.put("RecipesUsed", compoundNBT2);
        return compoundNBT;
    }

    @Override
    public void tick() {
        boolean bl = this.isBurning();
        boolean bl2 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }
        if (!this.world.isRemote) {
            ItemStack itemStack = this.items.get(1);
            if (this.isBurning() || !itemStack.isEmpty() && !this.items.get(0).isEmpty()) {
                IRecipe iRecipe = this.world.getRecipeManager().getRecipe(this.recipeType, this, this.world).orElse(null);
                if (!this.isBurning() && this.canSmelt(iRecipe)) {
                    this.recipesUsed = this.burnTime = this.getBurnTime(itemStack);
                    if (this.isBurning()) {
                        bl2 = true;
                        if (!itemStack.isEmpty()) {
                            Item item = itemStack.getItem();
                            itemStack.shrink(1);
                            if (itemStack.isEmpty()) {
                                Item item2 = item.getContainerItem();
                                this.items.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                            }
                        }
                    }
                }
                if (this.isBurning() && this.canSmelt(iRecipe)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getCookTime();
                        this.smelt(iRecipe);
                        bl2 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }
            if (bl != this.isBurning()) {
                bl2 = true;
                this.world.setBlockState(this.pos, (BlockState)this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, this.isBurning()), 0);
            }
        }
        if (bl2) {
            this.markDirty();
        }
    }

    protected boolean canSmelt(@Nullable IRecipe<?> iRecipe) {
        if (!this.items.get(0).isEmpty() && iRecipe != null) {
            ItemStack itemStack = iRecipe.getRecipeOutput();
            if (itemStack.isEmpty()) {
                return true;
            }
            ItemStack itemStack2 = this.items.get(2);
            if (itemStack2.isEmpty()) {
                return false;
            }
            if (!itemStack2.isItemEqual(itemStack)) {
                return true;
            }
            if (itemStack2.getCount() < this.getInventoryStackLimit() && itemStack2.getCount() < itemStack2.getMaxStackSize()) {
                return false;
            }
            return itemStack2.getCount() < itemStack.getMaxStackSize();
        }
        return true;
    }

    private void smelt(@Nullable IRecipe<?> iRecipe) {
        if (iRecipe != null && this.canSmelt(iRecipe)) {
            ItemStack itemStack = this.items.get(0);
            ItemStack itemStack2 = iRecipe.getRecipeOutput();
            ItemStack itemStack3 = this.items.get(2);
            if (itemStack3.isEmpty()) {
                this.items.set(2, itemStack2.copy());
            } else if (itemStack3.getItem() == itemStack2.getItem()) {
                itemStack3.grow(1);
            }
            if (!this.world.isRemote) {
                this.setRecipeUsed(iRecipe);
            }
            if (itemStack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
                this.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }
            itemStack.shrink(1);
        }
    }

    protected int getBurnTime(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return 1;
        }
        Item item = itemStack.getItem();
        return AbstractFurnaceTileEntity.getBurnTimes().getOrDefault(item, 0);
    }

    protected int getCookTime() {
        return this.world.getRecipeManager().getRecipe(this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

    public static boolean isFuel(ItemStack itemStack) {
        return AbstractFurnaceTileEntity.getBurnTimes().containsKey(itemStack.getItem());
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_DOWN;
        }
        return direction == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
    }

    @Override
    public boolean canInsertItem(int n, ItemStack itemStack, @Nullable Direction direction) {
        return this.isItemValidForSlot(n, itemStack);
    }

    @Override
    public boolean canExtractItem(int n, ItemStack itemStack, Direction direction) {
        Item item;
        return direction == Direction.DOWN && n == 1 && (item = itemStack.getItem()) != Items.WATER_BUCKET && item != Items.BUCKET;
    }

    @Override
    public int getSizeInventory() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.items) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int n) {
        return this.items.get(n);
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        return ItemStackHelper.getAndSplit(this.items, n, n2);
    }

    @Override
    public ItemStack removeStackFromSlot(int n) {
        return ItemStackHelper.getAndRemove(this.items, n);
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        ItemStack itemStack2 = this.items.get(n);
        boolean bl = !itemStack.isEmpty() && itemStack.isItemEqual(itemStack2) && ItemStack.areItemStackTagsEqual(itemStack, itemStack2);
        this.items.set(n, itemStack);
        if (itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }
        if (n == 0 && !bl) {
            this.cookTimeTotal = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        if (this.world.getTileEntity(this.pos) != this) {
            return true;
        }
        return playerEntity.getDistanceSq((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public boolean isItemValidForSlot(int n, ItemStack itemStack) {
        if (n == 2) {
            return true;
        }
        if (n != 1) {
            return false;
        }
        ItemStack itemStack2 = this.items.get(1);
        return AbstractFurnaceTileEntity.isFuel(itemStack) || itemStack.getItem() == Items.BUCKET && itemStack2.getItem() != Items.BUCKET;
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> iRecipe) {
        if (iRecipe != null) {
            ResourceLocation resourceLocation = iRecipe.getId();
            this.recipes.addTo(resourceLocation, 1);
        }
    }

    @Override
    @Nullable
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void onCrafting(PlayerEntity playerEntity) {
    }

    public void unlockRecipes(PlayerEntity playerEntity) {
        List<IRecipe<?>> list = this.grantStoredRecipeExperience(playerEntity.world, playerEntity.getPositionVec());
        playerEntity.unlockRecipes(list);
        this.recipes.clear();
    }

    public List<IRecipe<?>> grantStoredRecipeExperience(World world, Vector3d vector3d) {
        ArrayList<IRecipe<?>> arrayList = Lists.newArrayList();
        for (Object2IntMap.Entry entry : this.recipes.object2IntEntrySet()) {
            world.getRecipeManager().getRecipe((ResourceLocation)entry.getKey()).ifPresent(arg_0 -> AbstractFurnaceTileEntity.lambda$grantStoredRecipeExperience$1(arrayList, world, vector3d, entry, arg_0));
        }
        return arrayList;
    }

    private static void splitAndSpawnExperience(World world, Vector3d vector3d, int n, float f) {
        int n2 = MathHelper.floor((float)n * f);
        float f2 = MathHelper.frac((float)n * f);
        if (f2 != 0.0f && Math.random() < (double)f2) {
            ++n2;
        }
        while (n2 > 0) {
            int n3 = ExperienceOrbEntity.getXPSplit(n2);
            n2 -= n3;
            world.addEntity(new ExperienceOrbEntity(world, vector3d.x, vector3d.y, vector3d.z, n3));
        }
    }

    @Override
    public void fillStackedContents(RecipeItemHelper recipeItemHelper) {
        for (ItemStack itemStack : this.items) {
            recipeItemHelper.accountStack(itemStack);
        }
    }

    private static void lambda$grantStoredRecipeExperience$1(List list, World world, Vector3d vector3d, Object2IntMap.Entry entry, IRecipe iRecipe) {
        list.add(iRecipe);
        AbstractFurnaceTileEntity.splitAndSpawnExperience(world, vector3d, entry.getIntValue(), ((AbstractCookingRecipe)iRecipe).getExperience());
    }

    private static void lambda$write$0(CompoundNBT compoundNBT, ResourceLocation resourceLocation, Integer n) {
        compoundNBT.putInt(resourceLocation.toString(), n);
    }
}

