/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.storage.MapDecoration;

public class MapBanner {
    private final BlockPos pos;
    private final DyeColor color;
    @Nullable
    private final ITextComponent name;

    public MapBanner(BlockPos blockPos, DyeColor dyeColor, @Nullable ITextComponent iTextComponent) {
        this.pos = blockPos;
        this.color = dyeColor;
        this.name = iTextComponent;
    }

    public static MapBanner read(CompoundNBT compoundNBT) {
        BlockPos blockPos = NBTUtil.readBlockPos(compoundNBT.getCompound("Pos"));
        DyeColor dyeColor = DyeColor.byTranslationKey(compoundNBT.getString("Color"), DyeColor.WHITE);
        IFormattableTextComponent iFormattableTextComponent = compoundNBT.contains("Name") ? ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("Name")) : null;
        return new MapBanner(blockPos, dyeColor, iFormattableTextComponent);
    }

    @Nullable
    public static MapBanner fromWorld(IBlockReader iBlockReader, BlockPos blockPos) {
        TileEntity tileEntity = iBlockReader.getTileEntity(blockPos);
        if (tileEntity instanceof BannerTileEntity) {
            BannerTileEntity bannerTileEntity = (BannerTileEntity)tileEntity;
            DyeColor dyeColor = bannerTileEntity.getBaseColor(() -> MapBanner.lambda$fromWorld$0(iBlockReader, blockPos));
            ITextComponent iTextComponent = bannerTileEntity.hasCustomName() ? bannerTileEntity.getCustomName() : null;
            return new MapBanner(blockPos, dyeColor, iTextComponent);
        }
        return null;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public MapDecoration.Type getDecorationType() {
        switch (1.$SwitchMap$net$minecraft$item$DyeColor[this.color.ordinal()]) {
            case 1: {
                return MapDecoration.Type.BANNER_WHITE;
            }
            case 2: {
                return MapDecoration.Type.BANNER_ORANGE;
            }
            case 3: {
                return MapDecoration.Type.BANNER_MAGENTA;
            }
            case 4: {
                return MapDecoration.Type.BANNER_LIGHT_BLUE;
            }
            case 5: {
                return MapDecoration.Type.BANNER_YELLOW;
            }
            case 6: {
                return MapDecoration.Type.BANNER_LIME;
            }
            case 7: {
                return MapDecoration.Type.BANNER_PINK;
            }
            case 8: {
                return MapDecoration.Type.BANNER_GRAY;
            }
            case 9: {
                return MapDecoration.Type.BANNER_LIGHT_GRAY;
            }
            case 10: {
                return MapDecoration.Type.BANNER_CYAN;
            }
            case 11: {
                return MapDecoration.Type.BANNER_PURPLE;
            }
            case 12: {
                return MapDecoration.Type.BANNER_BLUE;
            }
            case 13: {
                return MapDecoration.Type.BANNER_BROWN;
            }
            case 14: {
                return MapDecoration.Type.BANNER_GREEN;
            }
            case 15: {
                return MapDecoration.Type.BANNER_RED;
            }
        }
        return MapDecoration.Type.BANNER_BLACK;
    }

    @Nullable
    public ITextComponent getName() {
        return this.name;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            MapBanner mapBanner = (MapBanner)object;
            return Objects.equals(this.pos, mapBanner.pos) && this.color == mapBanner.color && Objects.equals(this.name, mapBanner.name);
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.pos, this.color, this.name);
    }

    public CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("Pos", NBTUtil.writeBlockPos(this.pos));
        compoundNBT.putString("Color", this.color.getTranslationKey());
        if (this.name != null) {
            compoundNBT.putString("Name", ITextComponent.Serializer.toJson(this.name));
        }
        return compoundNBT;
    }

    public String getMapDecorationId() {
        return "banner-" + this.pos.getX() + "," + this.pos.getY() + "," + this.pos.getZ();
    }

    private static BlockState lambda$fromWorld$0(IBlockReader iBlockReader, BlockPos blockPos) {
        return iBlockReader.getBlockState(blockPos);
    }
}

