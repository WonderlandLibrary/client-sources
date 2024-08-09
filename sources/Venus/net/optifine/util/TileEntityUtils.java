/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.EnchantingTableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.INameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.optifine.reflect.Reflector;
import net.optifine.util.IntegratedServerUtils;

public class TileEntityUtils {
    public static String getTileEntityName(IBlockReader iBlockReader, BlockPos blockPos) {
        TileEntity tileEntity = iBlockReader.getTileEntity(blockPos);
        return TileEntityUtils.getTileEntityName(tileEntity);
    }

    public static String getTileEntityName(TileEntity tileEntity) {
        if (!(tileEntity instanceof INameable)) {
            return null;
        }
        INameable iNameable = (INameable)((Object)tileEntity);
        TileEntityUtils.updateTileEntityName(tileEntity);
        return !iNameable.hasCustomName() ? null : iNameable.getCustomName().getUnformattedComponentText();
    }

    public static void updateTileEntityName(TileEntity tileEntity) {
        BlockPos blockPos = tileEntity.getPos();
        ITextComponent iTextComponent = TileEntityUtils.getTileEntityRawName(tileEntity);
        if (iTextComponent == null) {
            ITextComponent iTextComponent2 = TileEntityUtils.getServerTileEntityRawName(blockPos);
            if (iTextComponent2 == null) {
                iTextComponent2 = new StringTextComponent("");
            }
            TileEntityUtils.setTileEntityRawName(tileEntity, iTextComponent2);
        }
    }

    public static ITextComponent getServerTileEntityRawName(BlockPos blockPos) {
        TileEntity tileEntity = IntegratedServerUtils.getTileEntity(blockPos);
        return tileEntity == null ? null : TileEntityUtils.getTileEntityRawName(tileEntity);
    }

    public static ITextComponent getTileEntityRawName(TileEntity tileEntity) {
        if (tileEntity instanceof INameable) {
            return ((INameable)((Object)tileEntity)).getCustomName();
        }
        return tileEntity instanceof BeaconTileEntity ? (ITextComponent)Reflector.getFieldValue(tileEntity, Reflector.TileEntityBeacon_customName) : null;
    }

    public static boolean setTileEntityRawName(TileEntity tileEntity, ITextComponent iTextComponent) {
        if (tileEntity instanceof LockableTileEntity) {
            ((LockableTileEntity)tileEntity).setCustomName(iTextComponent);
            return false;
        }
        if (tileEntity instanceof BannerTileEntity) {
            ((BannerTileEntity)tileEntity).setName(iTextComponent);
            return false;
        }
        if (tileEntity instanceof EnchantingTableTileEntity) {
            ((EnchantingTableTileEntity)tileEntity).setCustomName(iTextComponent);
            return false;
        }
        if (tileEntity instanceof BeaconTileEntity) {
            ((BeaconTileEntity)tileEntity).setCustomName(iTextComponent);
            return false;
        }
        return true;
    }
}

