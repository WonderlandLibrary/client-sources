package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockPistonMoving extends BlockContainer
{
    public static final PropertyEnum<BlockPistonExtension.EnumPistonType> TYPE;
    public static final PropertyDirection FACING;
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return "".length() != 0;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockPistonMoving.FACING, BlockPistonExtension.getFacing(n));
        final PropertyEnum<BlockPistonExtension.EnumPistonType> type = BlockPistonMoving.TYPE;
        BlockPistonExtension.EnumPistonType enumPistonType;
        if ((n & (0x31 ^ 0x39)) > 0) {
            enumPistonType = BlockPistonExtension.EnumPistonType.STICKY;
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            enumPistonType = BlockPistonExtension.EnumPistonType.DEFAULT;
        }
        return withProperty.withProperty((IProperty<Comparable>)type, enumPistonType);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            final TileEntityPiston tileEntity = this.getTileEntity(world, blockPos);
            if (tileEntity != null) {
                final IBlockState pistonState = tileEntity.getPistonState();
                pistonState.getBlock().dropBlockAsItem(world, blockPos, pistonState, "".length());
            }
        }
    }
    
    public AxisAlignedBB getBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final EnumFacing enumFacing) {
        if (blockState.getBlock() == this || blockState.getBlock().getMaterial() == Material.air) {
            return null;
        }
        final AxisAlignedBB collisionBoundingBox = blockState.getBlock().getCollisionBoundingBox(world, blockPos, blockState);
        if (collisionBoundingBox == null) {
            return null;
        }
        double minX = collisionBoundingBox.minX;
        double minY = collisionBoundingBox.minY;
        double minZ = collisionBoundingBox.minZ;
        double maxX = collisionBoundingBox.maxX;
        double maxY = collisionBoundingBox.maxY;
        double maxZ = collisionBoundingBox.maxZ;
        if (enumFacing.getFrontOffsetX() < 0) {
            minX -= enumFacing.getFrontOffsetX() * n;
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            maxX -= enumFacing.getFrontOffsetX() * n;
        }
        if (enumFacing.getFrontOffsetY() < 0) {
            minY -= enumFacing.getFrontOffsetY() * n;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            maxY -= enumFacing.getFrontOffsetY() * n;
        }
        if (enumFacing.getFrontOffsetZ() < 0) {
            minZ -= enumFacing.getFrontOffsetZ() * n;
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            maxZ -= enumFacing.getFrontOffsetZ() * n;
        }
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public static TileEntity newTileEntity(final IBlockState blockState, final EnumFacing enumFacing, final boolean b, final boolean b2) {
        return new TileEntityPiston(blockState, enumFacing, b, b2);
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return null;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockPistonMoving.FACING;
        array[" ".length()] = BlockPistonMoving.TYPE;
        return new BlockState(this, array);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            world.getTileEntity(blockPos);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockToAir, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!world.isRemote && world.getTileEntity(blockToAir) == null) {
            world.setBlockToAir(blockToAir);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return "".length() != 0;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final TileEntityPiston tileEntity = this.getTileEntity(blockAccess, blockPos);
        if (tileEntity != null) {
            final Block block = tileEntity.getPistonState().getBlock();
            if (block == this || block.getMaterial() == Material.air) {
                return;
            }
            float progress = tileEntity.getProgress(0.0f);
            if (tileEntity.isExtending()) {
                progress = 1.0f - progress;
            }
            block.setBlockBoundsBasedOnState(blockAccess, blockPos);
            if (block == Blocks.piston || block == Blocks.sticky_piston) {
                progress = 0.0f;
            }
            final EnumFacing facing = tileEntity.getFacing();
            this.minX = block.getBlockBoundsMinX() - facing.getFrontOffsetX() * progress;
            this.minY = block.getBlockBoundsMinY() - facing.getFrontOffsetY() * progress;
            this.minZ = block.getBlockBoundsMinZ() - facing.getFrontOffsetZ() * progress;
            this.maxX = block.getBlockBoundsMaxX() - facing.getFrontOffsetX() * progress;
            this.maxY = block.getBlockBoundsMaxY() - facing.getFrontOffsetY() * progress;
            this.maxZ = block.getBlockBoundsMaxZ() - facing.getFrontOffsetZ() * progress;
        }
    }
    
    static {
        FACING = BlockPistonExtension.FACING;
        TYPE = BlockPistonExtension.TYPE;
    }
    
    private TileEntityPiston getTileEntity(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        TileEntityPiston tileEntityPiston;
        if (tileEntity instanceof TileEntityPiston) {
            tileEntityPiston = (TileEntityPiston)tileEntity;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            tileEntityPiston = null;
        }
        return tileEntityPiston;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityPiston) {
            ((TileEntityPiston)tileEntity).clearPistonTileEntity();
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            super.breakBlock(world, blockPos, blockState);
        }
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final BlockPos offset = blockPos.offset(blockState.getValue((IProperty<EnumFacing>)BlockPistonMoving.FACING).getOpposite());
        final IBlockState blockState2 = world.getBlockState(offset);
        if (blockState2.getBlock() instanceof BlockPistonBase && blockState2.getValue((IProperty<Boolean>)BlockPistonBase.EXTENDED)) {
            world.setBlockToAir(offset);
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockPistonMoving.FACING).getIndex();
        if (blockState.getValue(BlockPistonMoving.TYPE) == BlockPistonExtension.EnumPistonType.STICKY) {
            n |= (0x5C ^ 0x54);
        }
        return n;
    }
    
    public BlockPistonMoving() {
        super(Material.piston);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockPistonMoving.FACING, EnumFacing.NORTH).withProperty(BlockPistonMoving.TYPE, BlockPistonExtension.EnumPistonType.DEFAULT));
        this.setHardness(-1.0f);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        return null;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntityPiston tileEntity = this.getTileEntity(world, blockPos);
        if (tileEntity == null) {
            return null;
        }
        float progress = tileEntity.getProgress(0.0f);
        if (tileEntity.isExtending()) {
            progress = 1.0f - progress;
        }
        return this.getBoundingBox(world, blockPos, tileEntity.getPistonState(), progress, tileEntity.getFacing());
    }
}
