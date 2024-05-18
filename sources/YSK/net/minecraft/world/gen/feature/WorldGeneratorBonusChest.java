package net.minecraft.world.gen.feature;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class WorldGeneratorBonusChest extends WorldGenerator
{
    private final int itemsToGenerateInBonusChest;
    private final List<WeightedRandomChestContent> chestItems;
    
    public WorldGeneratorBonusChest(final List<WeightedRandomChestContent> chestItems, final int itemsToGenerateInBonusChest) {
        this.chestItems = chestItems;
        this.itemsToGenerateInBonusChest = itemsToGenerateInBonusChest;
    }
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        "".length();
        if (-1 != -1) {
            throw null;
        }
        Block block;
        while (((block = world.getBlockState(blockPos).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && blockPos.getY() > " ".length()) {
            blockPos = blockPos.down();
        }
        if (blockPos.getY() < " ".length()) {
            return "".length() != 0;
        }
        blockPos = blockPos.up();
        int i = "".length();
        "".length();
        if (0 == -1) {
            throw null;
        }
        while (i < (0x83 ^ 0x87)) {
            final BlockPos add = blockPos.add(random.nextInt(0x8B ^ 0x8F) - random.nextInt(0x6A ^ 0x6E), random.nextInt("   ".length()) - random.nextInt("   ".length()), random.nextInt(0xBE ^ 0xBA) - random.nextInt(0x0 ^ 0x4));
            if (world.isAirBlock(add) && World.doesBlockHaveSolidTopSurface(world, add.down())) {
                world.setBlockState(add, Blocks.chest.getDefaultState(), "  ".length());
                final TileEntity tileEntity = world.getTileEntity(add);
                if (tileEntity instanceof TileEntityChest) {
                    WeightedRandomChestContent.generateChestContents(random, this.chestItems, (IInventory)tileEntity, this.itemsToGenerateInBonusChest);
                }
                final BlockPos east = add.east();
                final BlockPos west = add.west();
                final BlockPos north = add.north();
                final BlockPos south = add.south();
                if (world.isAirBlock(west) && World.doesBlockHaveSolidTopSurface(world, west.down())) {
                    world.setBlockState(west, Blocks.torch.getDefaultState(), "  ".length());
                }
                if (world.isAirBlock(east) && World.doesBlockHaveSolidTopSurface(world, east.down())) {
                    world.setBlockState(east, Blocks.torch.getDefaultState(), "  ".length());
                }
                if (world.isAirBlock(north) && World.doesBlockHaveSolidTopSurface(world, north.down())) {
                    world.setBlockState(north, Blocks.torch.getDefaultState(), "  ".length());
                }
                if (world.isAirBlock(south) && World.doesBlockHaveSolidTopSurface(world, south.down())) {
                    world.setBlockState(south, Blocks.torch.getDefaultState(), "  ".length());
                }
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
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
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
