package net.minecraft.block;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockEnchantmentTable extends BlockContainer
{
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        super.randomDisplayTick(world, blockPos, blockState, random);
        int i = -"  ".length();
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (i <= "  ".length()) {
            int j = -"  ".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
            while (j <= "  ".length()) {
                if (i > -"  ".length() && i < "  ".length() && j == -" ".length()) {
                    j = "  ".length();
                }
                if (random.nextInt(0xA2 ^ 0xB2) == 0) {
                    int k = "".length();
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    while (k <= " ".length()) {
                        if (world.getBlockState(blockPos.add(i, k, j)).getBlock() == Blocks.bookshelf) {
                            if (!world.isAirBlock(blockPos.add(i / "  ".length(), "".length(), j / "  ".length()))) {
                                "".length();
                                if (2 != 2) {
                                    throw null;
                                }
                                break;
                            }
                            else {
                                world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, blockPos.getX() + 0.5, blockPos.getY() + 2.0, blockPos.getZ() + 0.5, i + random.nextFloat() - 0.5, k - random.nextFloat() - 1.0f, j + random.nextFloat() - 0.5, new int["".length()]);
                            }
                        }
                        ++k;
                    }
                }
                ++j;
            }
            ++i;
        }
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityEnchantmentTable();
    }
    
    protected BlockEnchantmentTable() {
        super(Material.rock, MapColor.redColor);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        this.setLightOpacity("".length());
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, entityLivingBase, itemStack);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityEnchantmentTable) {
                ((TileEntityEnchantmentTable)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
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
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityEnchantmentTable) {
            entityPlayer.displayGui((IInteractionObject)tileEntity);
        }
        return " ".length() != 0;
    }
}
