package net.silentclient.client.mixin.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.silentclient.client.Client;
import net.silentclient.client.mods.render.ClearGlassMod;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({BlockGlass.class, BlockStainedGlass.class})
public class BlockGlassMixin extends Block {

    public BlockGlassMixin(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return !Client.getInstance().getModInstances().getModByClass(ClearGlassMod.class).isEnabled() && super.shouldSideBeRendered(worldIn, pos, side);
    }
}
