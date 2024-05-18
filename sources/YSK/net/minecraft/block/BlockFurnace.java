package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.inventory.*;
import net.minecraft.block.material.*;
import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockFurnace extends BlockContainer
{
    private static boolean keepInventory;
    private final boolean isBurning;
    public static final PropertyDirection FACING;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    private static final String[] I;
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockFurnace.FACING, entityLivingBase.getHorizontalFacing().getOpposite()), "  ".length());
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityFurnace) {
                ((TileEntityFurnace)tileEntity).setCustomInventoryName(itemStack.getDisplayName());
            }
        }
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void setDefaultFacing(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            final Block block = world.getBlockState(blockPos.north()).getBlock();
            final Block block2 = world.getBlockState(blockPos.south()).getBlock();
            final Block block3 = world.getBlockState(blockPos.west()).getBlock();
            final Block block4 = world.getBlockState(blockPos.east()).getBlock();
            EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockFurnace.FACING);
            if (enumFacing == EnumFacing.NORTH && block.isFullBlock() && !block2.isFullBlock()) {
                enumFacing = EnumFacing.SOUTH;
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else if (enumFacing == EnumFacing.SOUTH && block2.isFullBlock() && !block.isFullBlock()) {
                enumFacing = EnumFacing.NORTH;
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else if (enumFacing == EnumFacing.WEST && block3.isFullBlock() && !block4.isFullBlock()) {
                enumFacing = EnumFacing.EAST;
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else if (enumFacing == EnumFacing.EAST && block4.isFullBlock() && !block3.isFullBlock()) {
                enumFacing = EnumFacing.WEST;
            }
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockFurnace.FACING, enumFacing), "  ".length());
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockFurnace.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x42 ^ 0x44);
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x57 ^ 0x53);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0xB7 ^ 0xB2);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockFurnace.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.furnace);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!BlockFurnace.keepInventory) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityFurnace) {
                InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
                world.updateComparatorOutputLevel(blockPos, this);
            }
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFurnace) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
            entityPlayer.triggerAchievement(StatList.field_181741_Y);
        }
        return " ".length() != 0;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setDefaultFacing(world, blockPos, blockState);
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockFurnace.FACING, entityLivingBase.getHorizontalFacing().getOpposite());
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstone(world.getTileEntity(blockPos));
    }
    
    protected BlockFurnace(final boolean isBurning) {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockFurnace.FACING, EnumFacing.NORTH));
        this.isBurning = isBurning;
    }
    
    public static void setState(final boolean b, final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        BlockFurnace.keepInventory = (" ".length() != 0);
        if (b) {
            world.setBlockState(blockPos, Blocks.lit_furnace.getDefaultState().withProperty((IProperty<Comparable>)BlockFurnace.FACING, (EnumFacing)blockState.getValue((IProperty<V>)BlockFurnace.FACING)), "   ".length());
            world.setBlockState(blockPos, Blocks.lit_furnace.getDefaultState().withProperty((IProperty<Comparable>)BlockFurnace.FACING, (EnumFacing)blockState.getValue((IProperty<V>)BlockFurnace.FACING)), "   ".length());
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            world.setBlockState(blockPos, Blocks.furnace.getDefaultState().withProperty((IProperty<Comparable>)BlockFurnace.FACING, (EnumFacing)blockState.getValue((IProperty<V>)BlockFurnace.FACING)), "   ".length());
            world.setBlockState(blockPos, Blocks.furnace.getDefaultState().withProperty((IProperty<Comparable>)BlockFurnace.FACING, (EnumFacing)blockState.getValue((IProperty<V>)BlockFurnace.FACING)), "   ".length());
        }
        BlockFurnace.keepInventory = ("".length() != 0);
        if (tileEntity != null) {
            tileEntity.validate();
            world.setTileEntity(blockPos, tileEntity);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityFurnace();
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockFurnace.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockFurnace.FACING;
        return new BlockState(this, array);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("$2\u00108\u001a%", "BSsQt");
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockFurnace.FACING, EnumFacing.SOUTH);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockFurnace.FACING, enumFacing);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<EnumFacing>)BlockFurnace.FACING).getIndex();
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isBurning) {
            final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockFurnace.FACING);
            final double n = blockPos.getX() + 0.5;
            final double n2 = blockPos.getY() + random.nextDouble() * 6.0 / 16.0;
            final double n3 = blockPos.getZ() + 0.5;
            final double n4 = 0.52;
            final double n5 = random.nextDouble() * 0.6 - 0.3;
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                case 5: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n - n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int["".length()]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n - n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int["".length()]);
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int["".length()]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n + n4, n2, n3 + n5, 0.0, 0.0, 0.0, new int["".length()]);
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n5, n2, n3 - n4, 0.0, 0.0, 0.0, new int["".length()]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n + n5, n2, n3 - n4, 0.0, 0.0, 0.0, new int["".length()]);
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n5, n2, n3 + n4, 0.0, 0.0, 0.0, new int["".length()]);
                    world.spawnParticle(EnumParticleTypes.FLAME, n + n5, n2, n3 + n4, 0.0, 0.0, 0.0, new int["".length()]);
                    break;
                }
            }
        }
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.furnace);
    }
}
