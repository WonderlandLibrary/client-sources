package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.command.server.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;

public class BlockCommandBlock extends BlockContainer
{
    public static final PropertyBool TRIGGERED;
    private static final String[] I;
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        int n4;
        if (tileEntity instanceof TileEntityCommandBlock) {
            n4 = (((TileEntityCommandBlock)tileEntity).getCommandBlockLogic().tryOpenEditCommandBlock(entityPlayer) ? 1 : 0);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        return n4 != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(">+9\u000f\n/+5\f", "JYPhm");
        BlockCommandBlock.I[" ".length()] = I("1\f\u0003\u0012,-\u0004\u0000\u0017\u0001&/\b\u0013\u000b \b\u000e\u001d", "Bimvo");
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        final PropertyBool triggered = BlockCommandBlock.TRIGGERED;
        int n2;
        if ((n & " ".length()) > 0) {
            n2 = " ".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return defaultState.withProperty((IProperty<Comparable>)triggered, n2 != 0);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockCommandBlock.TRIGGERED, "".length() != 0);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityCommandBlock) {
            ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic().trigger(world);
            world.updateComparatorOutputLevel(blockPos, this);
        }
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        int n;
        if (tileEntity instanceof TileEntityCommandBlock) {
            n = ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic().getSuccessCount();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            final boolean blockPowered = world.isBlockPowered(blockPos);
            final boolean booleanValue = blockState.getValue((IProperty<Boolean>)BlockCommandBlock.TRIGGERED);
            if (blockPowered && !booleanValue) {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockCommandBlock.TRIGGERED, (boolean)(" ".length() != 0)), 0x29 ^ 0x2D);
                world.scheduleUpdate(blockPos, this, this.tickRate(world));
                "".length();
                if (1 == 3) {
                    throw null;
                }
            }
            else if (!blockPowered && booleanValue) {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockCommandBlock.TRIGGERED, (boolean)("".length() != 0)), 0xA2 ^ 0xA6);
            }
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityCommandBlock();
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityCommandBlock) {
            final CommandBlockLogic commandBlockLogic = ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic();
            if (itemStack.hasDisplayName()) {
                commandBlockLogic.setName(itemStack.getDisplayName());
            }
            if (!world.isRemote) {
                commandBlockLogic.setTrackOutput(world.getGameRules().getBoolean(BlockCommandBlock.I[" ".length()]));
            }
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockCommandBlock.TRIGGERED;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
    }
    
    static {
        I();
        TRIGGERED = PropertyBool.create(BlockCommandBlock.I["".length()]);
    }
    
    public BlockCommandBlock() {
        super(Material.iron, MapColor.adobeColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockCommandBlock.TRIGGERED, (boolean)("".length() != 0)));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int length = "".length();
        if (blockState.getValue((IProperty<Boolean>)BlockCommandBlock.TRIGGERED)) {
            length |= " ".length();
        }
        return length;
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    @Override
    public int tickRate(final World world) {
        return " ".length();
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
