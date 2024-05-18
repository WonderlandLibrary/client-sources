package net.minecraft.block;

import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class BlockRedstoneOre extends Block
{
    private final boolean isOn;
    
    @Override
    public int quantityDropped(final Random random) {
        return (0x19 ^ 0x1D) + random.nextInt("  ".length());
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.redstone;
    }
    
    public BlockRedstoneOre(final boolean isOn) {
        super(Material.rock);
        if (isOn) {
            this.setTickRandomly(" ".length() != 0);
        }
        this.isOn = isOn;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        if (this.getItemDropped(blockState, world.rand, n2) != Item.getItemFromBlock(this)) {
            this.dropXpOnBlockBreak(world, blockPos, " ".length() + world.rand.nextInt(0xBC ^ 0xB9));
        }
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isOn) {
            this.spawnParticles(world, blockPos);
        }
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return this.quantityDropped(random) + random.nextInt(n + " ".length());
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Blocks.redstone_ore);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final Entity entity) {
        this.activate(world, blockPos);
        super.onEntityCollidedWithBlock(world, blockPos, entity);
    }
    
    private void activate(final World world, final BlockPos blockPos) {
        this.spawnParticles(world, blockPos);
        if (this == Blocks.redstone_ore) {
            world.setBlockState(blockPos, Blocks.lit_redstone_ore.getDefaultState());
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        this.activate(world, blockPos);
        return super.onBlockActivated(world, blockPos, blockState, entityPlayer, enumFacing, n, n2, n3);
    }
    
    @Override
    public int tickRate(final World world) {
        return 0xBE ^ 0xA0;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this == Blocks.lit_redstone_ore) {
            world.setBlockState(blockPos, Blocks.redstone_ore.getDefaultState());
        }
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.activate(world, blockPos);
        super.onBlockClicked(world, blockPos, entityPlayer);
    }
    
    private void spawnParticles(final World world, final BlockPos blockPos) {
        final Random rand = world.rand;
        final double n = 0.0625;
        int i = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i < (0x5 ^ 0x3)) {
            double n2 = blockPos.getX() + rand.nextFloat();
            double n3 = blockPos.getY() + rand.nextFloat();
            double n4 = blockPos.getZ() + rand.nextFloat();
            if (i == 0 && !world.getBlockState(blockPos.up()).getBlock().isOpaqueCube()) {
                n3 = blockPos.getY() + n + 1.0;
            }
            if (i == " ".length() && !world.getBlockState(blockPos.down()).getBlock().isOpaqueCube()) {
                n3 = blockPos.getY() - n;
            }
            if (i == "  ".length() && !world.getBlockState(blockPos.south()).getBlock().isOpaqueCube()) {
                n4 = blockPos.getZ() + n + 1.0;
            }
            if (i == "   ".length() && !world.getBlockState(blockPos.north()).getBlock().isOpaqueCube()) {
                n4 = blockPos.getZ() - n;
            }
            if (i == (0x2B ^ 0x2F) && !world.getBlockState(blockPos.east()).getBlock().isOpaqueCube()) {
                n2 = blockPos.getX() + n + 1.0;
            }
            if (i == (0x15 ^ 0x10) && !world.getBlockState(blockPos.west()).getBlock().isOpaqueCube()) {
                n2 = blockPos.getX() - n;
            }
            if (n2 < blockPos.getX() || n2 > blockPos.getX() + " ".length() || n3 < 0.0 || n3 > blockPos.getY() + " ".length() || n4 < blockPos.getZ() || n4 > blockPos.getZ() + " ".length()) {
                world.spawnParticle(EnumParticleTypes.REDSTONE, n2, n3, n4, 0.0, 0.0, 0.0, new int["".length()]);
            }
            ++i;
        }
    }
}
