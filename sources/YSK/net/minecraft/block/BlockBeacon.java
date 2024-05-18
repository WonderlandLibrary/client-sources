package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.stats.*;

public class BlockBeacon extends BlockContainer
{
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBeacon) {
            ((TileEntityBeacon)tileEntity).updateBeacon();
            world.addBlockEvent(blockPos, this, " ".length(), "".length());
        }
    }
    
    public static void updateColorAsync(final World world, final BlockPos blockPos) {
        HttpUtil.field_180193_a.submit((Runnable)new Runnable(world, blockPos) {
            private final World val$worldIn;
            private final BlockPos val$glassPos;
            
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
                    if (1 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void run() {
                final Chunk chunkFromBlockCoords = this.val$worldIn.getChunkFromBlockCoords(this.val$glassPos);
                int i = this.val$glassPos.getY() - " ".length();
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
                while (i >= 0) {
                    final BlockPos blockPos = new BlockPos(this.val$glassPos.getX(), i, this.val$glassPos.getZ());
                    if (!chunkFromBlockCoords.canSeeSky(blockPos)) {
                        "".length();
                        if (0 <= -1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        if (this.val$worldIn.getBlockState(blockPos).getBlock() == Blocks.beacon) {
                            ((WorldServer)this.val$worldIn).addScheduledTask(new Runnable(this, this.val$worldIn, blockPos) {
                                private final BlockPos val$blockpos;
                                private final World val$worldIn;
                                final BlockBeacon$1 this$1;
                                
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
                                        if (1 >= 4) {
                                            throw null;
                                        }
                                    }
                                    return sb.toString();
                                }
                                
                                @Override
                                public void run() {
                                    final TileEntity tileEntity = this.val$worldIn.getTileEntity(this.val$blockpos);
                                    if (tileEntity instanceof TileEntityBeacon) {
                                        ((TileEntityBeacon)tileEntity).updateBeacon();
                                        this.val$worldIn.addBlockEvent(this.val$blockpos, Blocks.beacon, " ".length(), "".length());
                                    }
                                }
                            });
                        }
                        --i;
                    }
                }
            }
        });
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityBeacon();
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, entityLivingBase, itemStack);
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityBeacon) {
                ((TileEntityBeacon)tileEntity).setName(itemStack.getDisplayName());
            }
        }
    }
    
    public BlockBeacon() {
        super(Material.glass, MapColor.diamondColor);
        this.setHardness(3.0f);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBeacon) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
            entityPlayer.triggerAchievement(StatList.field_181730_N);
        }
        return " ".length() != 0;
    }
}
