/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class SkullPlayerBlock
extends SkullBlock {
    protected SkullPlayerBlock(AbstractBlock.Properties properties) {
        super(SkullBlock.Types.PLAYER, properties);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, livingEntity, itemStack);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof SkullTileEntity) {
            SkullTileEntity skullTileEntity = (SkullTileEntity)tileEntity;
            GameProfile gameProfile = null;
            if (itemStack.hasTag()) {
                CompoundNBT compoundNBT = itemStack.getTag();
                if (compoundNBT.contains("SkullOwner", 1)) {
                    gameProfile = NBTUtil.readGameProfile(compoundNBT.getCompound("SkullOwner"));
                } else if (compoundNBT.contains("SkullOwner", 1) && !StringUtils.isBlank(compoundNBT.getString("SkullOwner"))) {
                    gameProfile = new GameProfile(null, compoundNBT.getString("SkullOwner"));
                }
            }
            skullTileEntity.setPlayerProfile(gameProfile);
        }
    }
}

