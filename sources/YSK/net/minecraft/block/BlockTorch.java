package net.minecraft.block;

import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;

public class BlockTorch extends Block
{
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public static final PropertyDirection FACING;
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (this.canPlaceAt(world, blockPos, enumFacing)) {
            return this.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, enumFacing);
        }
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing2 = iterator.next();
            if (world.isBlockNormalCube(blockPos.offset(enumFacing2.getOpposite()), " ".length() != 0)) {
                return this.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, enumFacing2);
            }
        }
        return this.getDefaultState();
    }
    
    protected boolean checkForDrop(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (blockState.getBlock() == this && this.canPlaceAt(world, blockToAir, blockState.getValue((IProperty<EnumFacing>)BlockTorch.FACING))) {
            return " ".length() != 0;
        }
        if (world.getBlockState(blockToAir).getBlock() == this) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
        }
        return "".length() != 0;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.checkForDrop(world, blockPos, blockState);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001c\"%\u0001\u0004\u001d", "zCFhj");
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        final EnumFacing enumFacing = world.getBlockState(blockPos).getValue((IProperty<EnumFacing>)BlockTorch.FACING);
        final float n = 0.15f;
        if (enumFacing == EnumFacing.EAST) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - n, n * 2.0f, 0.8f, 0.5f + n);
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.WEST) {
            this.setBlockBounds(1.0f - n * 2.0f, 0.2f, 0.5f - n, 1.0f, 0.8f, 0.5f + n);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            this.setBlockBounds(0.5f - n, 0.2f, 0.0f, 0.5f + n, 0.8f, n * 2.0f);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.NORTH) {
            this.setBlockBounds(0.5f - n, 0.2f, 1.0f - n * 2.0f, 0.5f + n, 0.8f, 1.0f);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            final float n2 = 0.1f;
            this.setBlockBounds(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, 0.6f, 0.5f + n2);
        }
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int length = "".length();
        int n = 0;
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[blockState.getValue((IProperty<EnumFacing>)BlockTorch.FACING).ordinal()]) {
            case 6: {
                n = (length | " ".length());
                "".length();
                if (3 < -1) {
                    throw null;
                }
                break;
            }
            case 5: {
                n = (length | "  ".length());
                "".length();
                if (3 < 3) {
                    throw null;
                }
                break;
            }
            case 4: {
                n = (length | "   ".length());
                "".length();
                if (2 == 1) {
                    throw null;
                }
                break;
            }
            case 3: {
                n = (length | (0x96 ^ 0x92));
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                break;
            }
            default: {
                n = (length | (0x5F ^ 0x5A));
                break;
            }
        }
        return n;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockTorch.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x71 ^ 0x77);
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x46 ^ 0x42);
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x71 ^ 0x74);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockTorch.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockTorch.I["".length()], (Predicate<EnumFacing>)new Predicate<EnumFacing>() {
            public boolean apply(final EnumFacing enumFacing) {
                if (enumFacing != EnumFacing.DOWN) {
                    return " ".length() != 0;
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
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final Object o) {
                return this.apply((EnumFacing)o);
            }
        });
    }
    
    private boolean canPlaceOn(final World world, final BlockPos blockPos) {
        if (World.doesBlockHaveSolidTopSurface(world, blockPos)) {
            return " ".length() != 0;
        }
        final Block block = world.getBlockState(blockPos).getBlock();
        if (!(block instanceof BlockFence) && block != Blocks.glass && block != Blocks.cobblestone_wall && block != Blocks.stained_glass) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockTorch.FACING);
        final double n = blockPos.getX() + 0.5;
        final double n2 = blockPos.getY() + 0.7;
        final double n3 = blockPos.getZ() + 0.5;
        final double n4 = 0.22;
        final double n5 = 0.27;
        if (enumFacing.getAxis().isHorizontal()) {
            final EnumFacing opposite = enumFacing.getOpposite();
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n + n5 * opposite.getFrontOffsetX(), n2 + n4, n3 + n5 * opposite.getFrontOffsetZ(), 0.0, 0.0, 0.0, new int["".length()]);
            world.spawnParticle(EnumParticleTypes.FLAME, n + n5 * opposite.getFrontOffsetX(), n2 + n4, n3 + n5 * opposite.getFrontOffsetZ(), 0.0, 0.0, 0.0, new int["".length()]);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n, n2, n3, 0.0, 0.0, 0.0, new int["".length()]);
            world.spawnParticle(EnumParticleTypes.FLAME, n, n2, n3, 0.0, 0.0, 0.0, new int["".length()]);
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    private boolean canPlaceAt(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        final BlockPos offset = blockPos.offset(enumFacing.getOpposite());
        if ((!enumFacing.getAxis().isHorizontal() || !world.isBlockNormalCube(offset, " ".length() != 0)) && (!enumFacing.equals(EnumFacing.UP) || !this.canPlaceOn(world, offset))) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    protected BlockTorch() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockTorch.FACING, EnumFacing.UP));
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        IBlockState blockState = null;
        switch (n) {
            case 1: {
                blockState = defaultState.withProperty((IProperty<Comparable>)BlockTorch.FACING, EnumFacing.EAST);
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                break;
            }
            case 2: {
                blockState = defaultState.withProperty((IProperty<Comparable>)BlockTorch.FACING, EnumFacing.WEST);
                "".length();
                if (0 >= 1) {
                    throw null;
                }
                break;
            }
            case 3: {
                blockState = defaultState.withProperty((IProperty<Comparable>)BlockTorch.FACING, EnumFacing.SOUTH);
                "".length();
                if (0 == 2) {
                    throw null;
                }
                break;
            }
            case 4: {
                blockState = defaultState.withProperty((IProperty<Comparable>)BlockTorch.FACING, EnumFacing.NORTH);
                "".length();
                if (2 < 1) {
                    throw null;
                }
                break;
            }
            default: {
                blockState = defaultState.withProperty((IProperty<Comparable>)BlockTorch.FACING, EnumFacing.UP);
                break;
            }
        }
        return blockState;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final Iterator<EnumFacing> iterator = BlockTorch.FACING.getAllowedValues().iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (this.canPlaceAt(world, blockPos, iterator.next())) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    protected boolean onNeighborChangeInternal(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (!this.checkForDrop(world, blockToAir, blockState)) {
            return " ".length() != 0;
        }
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockTorch.FACING);
        final EnumFacing.Axis axis = enumFacing.getAxis();
        final EnumFacing opposite = enumFacing.getOpposite();
        int n = "".length();
        if (axis.isHorizontal() && !world.isBlockNormalCube(blockToAir.offset(opposite), " ".length() != 0)) {
            n = " ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (axis.isVertical() && !this.canPlaceOn(world, blockToAir.offset(opposite))) {
            n = " ".length();
        }
        if (n != 0) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
            return " ".length() != 0;
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockTorch.FACING;
        return new BlockState(this, array);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.onNeighborChangeInternal(world, blockPos, blockState);
    }
}
