/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BannerTileEntity
extends TileEntity
implements INameable {
    @Nullable
    private ITextComponent name;
    @Nullable
    private DyeColor baseColor = DyeColor.WHITE;
    @Nullable
    private ListNBT patterns;
    private boolean patternDataSet;
    @Nullable
    private List<Pair<BannerPattern, DyeColor>> patternList;

    public BannerTileEntity() {
        super(TileEntityType.BANNER);
    }

    public BannerTileEntity(DyeColor dyeColor) {
        this();
        this.baseColor = dyeColor;
    }

    @Nullable
    public static ListNBT getPatternData(ItemStack itemStack) {
        ListNBT listNBT = null;
        CompoundNBT compoundNBT = itemStack.getChildTag("BlockEntityTag");
        if (compoundNBT != null && compoundNBT.contains("Patterns", 0)) {
            listNBT = compoundNBT.getList("Patterns", 10).copy();
        }
        return listNBT;
    }

    public void loadFromItemStack(ItemStack itemStack, DyeColor dyeColor) {
        this.patterns = BannerTileEntity.getPatternData(itemStack);
        this.baseColor = dyeColor;
        this.patternList = null;
        this.patternDataSet = true;
        this.name = itemStack.hasDisplayName() ? itemStack.getDisplayName() : null;
    }

    @Override
    public ITextComponent getName() {
        return this.name != null ? this.name : new TranslationTextComponent("block.minecraft.banner");
    }

    @Override
    @Nullable
    public ITextComponent getCustomName() {
        return this.name;
    }

    public void setName(ITextComponent iTextComponent) {
        this.name = iTextComponent;
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        if (this.patterns != null) {
            compoundNBT.put("Patterns", this.patterns);
        }
        if (this.name != null) {
            compoundNBT.putString("CustomName", ITextComponent.Serializer.toJson(this.name));
        }
        return compoundNBT;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        if (compoundNBT.contains("CustomName", 1)) {
            this.name = ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("CustomName"));
        }
        this.baseColor = this.hasWorld() ? ((AbstractBannerBlock)this.getBlockState().getBlock()).getColor() : null;
        this.patterns = compoundNBT.getList("Patterns", 10);
        this.patternList = null;
        this.patternDataSet = true;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 6, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public static int getPatterns(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getChildTag("BlockEntityTag");
        return compoundNBT != null && compoundNBT.contains("Patterns") ? compoundNBT.getList("Patterns", 10).size() : 0;
    }

    public List<Pair<BannerPattern, DyeColor>> getPatternList() {
        if (this.patternList == null && this.patternDataSet) {
            this.patternList = BannerTileEntity.getPatternColorData(this.getBaseColor(this::getBlockState), this.patterns);
        }
        return this.patternList;
    }

    public static List<Pair<BannerPattern, DyeColor>> getPatternColorData(DyeColor dyeColor, @Nullable ListNBT listNBT) {
        ArrayList<Pair<BannerPattern, DyeColor>> arrayList = Lists.newArrayList();
        arrayList.add(Pair.of(BannerPattern.BASE, dyeColor));
        if (listNBT != null) {
            for (int i = 0; i < listNBT.size(); ++i) {
                CompoundNBT compoundNBT = listNBT.getCompound(i);
                BannerPattern bannerPattern = BannerPattern.byHash(compoundNBT.getString("Pattern"));
                if (bannerPattern == null) continue;
                int n = compoundNBT.getInt("Color");
                arrayList.add(Pair.of(bannerPattern, DyeColor.byId(n)));
            }
        }
        return arrayList;
    }

    public static void removeBannerData(ItemStack itemStack) {
        ListNBT listNBT;
        CompoundNBT compoundNBT = itemStack.getChildTag("BlockEntityTag");
        if (compoundNBT != null && compoundNBT.contains("Patterns", 0) && !(listNBT = compoundNBT.getList("Patterns", 10)).isEmpty()) {
            listNBT.remove(listNBT.size() - 1);
            if (listNBT.isEmpty()) {
                itemStack.removeChildTag("BlockEntityTag");
            }
        }
    }

    public ItemStack getItem(BlockState blockState) {
        ItemStack itemStack = new ItemStack(BannerBlock.forColor(this.getBaseColor(() -> BannerTileEntity.lambda$getItem$0(blockState))));
        if (this.patterns != null && !this.patterns.isEmpty()) {
            itemStack.getOrCreateChildTag("BlockEntityTag").put("Patterns", this.patterns.copy());
        }
        if (this.name != null) {
            itemStack.setDisplayName(this.name);
        }
        return itemStack;
    }

    public DyeColor getBaseColor(Supplier<BlockState> supplier) {
        if (this.baseColor == null) {
            this.baseColor = ((AbstractBannerBlock)supplier.get().getBlock()).getColor();
        }
        return this.baseColor;
    }

    private static BlockState lambda$getItem$0(BlockState blockState) {
        return blockState;
    }
}

