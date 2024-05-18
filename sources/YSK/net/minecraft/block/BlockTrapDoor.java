package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class BlockTrapDoor extends Block
{
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    private static final String[] I;
    public static final PropertyDirection FACING;
    public static final PropertyEnum<DoorHalf> HALF;
    public static final PropertyBool OPEN;
    
    protected static int getMetaForFacing(final EnumFacing enumFacing) {
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 3: {
                return "".length();
            }
            case 4: {
                return " ".length();
            }
            case 5: {
                return "  ".length();
            }
            default: {
                return "   ".length();
            }
        }
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            if (!isValidSupportBlock(world.getBlockState(blockToAir.offset(blockState.getValue((IProperty<EnumFacing>)BlockTrapDoor.FACING).getOpposite())).getBlock())) {
                world.setBlockToAir(blockToAir);
                this.dropBlockAsItem(world, blockToAir, blockState, "".length());
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                final boolean blockPowered = world.isBlockPowered(blockToAir);
                if ((blockPowered || block.canProvidePower()) && blockState.getValue((IProperty<Boolean>)BlockTrapDoor.OPEN) != blockPowered) {
                    world.setBlockState(blockToAir, blockState.withProperty((IProperty<Comparable>)BlockTrapDoor.OPEN, blockPowered), "  ".length());
                    final EntityPlayer entityPlayer = null;
                    int n;
                    if (blockPowered) {
                        n = 132 + 78 - 140 + 933;
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                    }
                    else {
                        n = 980 + 510 - 761 + 277;
                    }
                    world.playAuxSFXAtEntity(entityPlayer, n, blockToAir, "".length());
                }
            }
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["   ".length()];
        array["".length()] = BlockTrapDoor.FACING;
        array[" ".length()] = BlockTrapDoor.OPEN;
        array["  ".length()] = BlockTrapDoor.HALF;
        return new BlockState(this, array);
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
            if (1 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockTrapDoor.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xA4 ^ 0xA2);
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x5A ^ 0x5E);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x6A ^ 0x6F);
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockTrapDoor.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        int n;
        if (blockAccess.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockTrapDoor.OPEN)) {
            n = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0004\u0006\u001a(-\u0005", "bgyAC");
        BlockTrapDoor.I[" ".length()] = I(";%-\u0014", "TUHzj");
        BlockTrapDoor.I["  ".length()] = I("*7?\u001c", "BVSzs");
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | getMetaForFacing(blockState.getValue((IProperty<EnumFacing>)BlockTrapDoor.FACING));
        if (blockState.getValue((IProperty<Boolean>)BlockTrapDoor.OPEN)) {
            n |= (0x45 ^ 0x41);
        }
        if (blockState.getValue(BlockTrapDoor.HALF) == DoorHalf.TOP) {
            n |= (0x4B ^ 0x43);
        }
        return n;
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    private static boolean isValidSupportBlock(final Block block) {
        if ((!block.blockMaterial.isOpaque() || !block.isFullCube()) && block != Blocks.glowstone && !(block instanceof BlockSlab) && !(block instanceof BlockStairs)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    protected BlockTrapDoor(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockTrapDoor.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockTrapDoor.OPEN, "".length() != 0).withProperty(BlockTrapDoor.HALF, DoorHalf.BOTTOM));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.40625f, 0.0f, 1.0f, 0.59375f, 1.0f);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockTrapDoor.FACING, getFacing(n));
        final PropertyBool open = BlockTrapDoor.OPEN;
        int n2;
        if ((n & (0x32 ^ 0x36)) != 0x0) {
            n2 = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)open, n2 != 0);
        final PropertyEnum<DoorHalf> half = BlockTrapDoor.HALF;
        DoorHalf doorHalf;
        if ((n & (0x3B ^ 0x33)) == 0x0) {
            doorHalf = DoorHalf.BOTTOM;
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            doorHalf = DoorHalf.TOP;
        }
        return withProperty2.withProperty((IProperty<Comparable>)half, doorHalf);
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        if (!enumFacing.getAxis().isVertical() && isValidSupportBlock(world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockTrapDoor.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
        OPEN = PropertyBool.create(BlockTrapDoor.I[" ".length()]);
        HALF = PropertyEnum.create(BlockTrapDoor.I["  ".length()], DoorHalf.class);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (this.blockMaterial == Material.iron) {
            return " ".length() != 0;
        }
        cycleProperty = cycleProperty.cycleProperty((IProperty<Comparable>)BlockTrapDoor.OPEN);
        world.setBlockState(blockPos, cycleProperty, "  ".length());
        int n4;
        if (cycleProperty.getValue((IProperty<Boolean>)BlockTrapDoor.OPEN)) {
            n4 = 155 + 633 - 603 + 818;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n4 = 115 + 317 + 331 + 243;
        }
        world.playAuxSFXAtEntity(entityPlayer, n4, blockPos, "".length());
        return " ".length() != 0;
    }
    
    protected static EnumFacing getFacing(final int n) {
        switch (n & "   ".length()) {
            case 0: {
                return EnumFacing.NORTH;
            }
            case 1: {
                return EnumFacing.SOUTH;
            }
            case 2: {
                return EnumFacing.WEST;
            }
            default: {
                return EnumFacing.EAST;
            }
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBounds(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        IBlockState blockState = this.getDefaultState();
        if (enumFacing.getAxis().isHorizontal()) {
            final IBlockState withProperty = blockState.withProperty((IProperty<Comparable>)BlockTrapDoor.FACING, enumFacing).withProperty((IProperty<Comparable>)BlockTrapDoor.OPEN, "".length() != 0);
            final PropertyEnum<DoorHalf> half = BlockTrapDoor.HALF;
            DoorHalf doorHalf;
            if (n2 > 0.5f) {
                doorHalf = DoorHalf.TOP;
                "".length();
                if (2 == 3) {
                    throw null;
                }
            }
            else {
                doorHalf = DoorHalf.BOTTOM;
            }
            blockState = withProperty.withProperty((IProperty<Comparable>)half, doorHalf);
        }
        return blockState;
    }
    
    public void setBounds(final IBlockState blockState) {
        if (blockState.getBlock() == this) {
            int n;
            if (blockState.getValue(BlockTrapDoor.HALF) == DoorHalf.TOP) {
                n = " ".length();
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            final int n2 = n;
            final Boolean b = blockState.getValue((IProperty<Boolean>)BlockTrapDoor.OPEN);
            final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockTrapDoor.FACING);
            if (n2 != 0) {
                this.setBlockBounds(0.0f, 0.8125f, 0.0f, 1.0f, 1.0f, 1.0f);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.1875f, 1.0f);
            }
            if (b) {
                if (enumFacing == EnumFacing.NORTH) {
                    this.setBlockBounds(0.0f, 0.0f, 0.8125f, 1.0f, 1.0f, 1.0f);
                }
                if (enumFacing == EnumFacing.SOUTH) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.1875f);
                }
                if (enumFacing == EnumFacing.WEST) {
                    this.setBlockBounds(0.8125f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                if (enumFacing == EnumFacing.EAST) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.1875f, 1.0f, 1.0f);
                }
            }
        }
    }
    
    public enum DoorHalf implements IStringSerializable
    {
        private final String name;
        
        TOP(DoorHalf.I["".length()], "".length(), DoorHalf.I[" ".length()]);
        
        private static final DoorHalf[] ENUM$VALUES;
        private static final String[] I;
        
        BOTTOM(DoorHalf.I["  ".length()], " ".length(), DoorHalf.I["   ".length()]);
        
        static {
            I();
            final DoorHalf[] enum$VALUES = new DoorHalf["  ".length()];
            enum$VALUES["".length()] = DoorHalf.TOP;
            enum$VALUES[" ".length()] = DoorHalf.BOTTOM;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String[0x2D ^ 0x29])["".length()] = I("'\u0016\u001f", "sYOsq");
            DoorHalf.I[" ".length()] = I("\u00016:", "uYJvb");
            DoorHalf.I["  ".length()] = I("-&\u00063:\"", "oiRgu");
            DoorHalf.I["   ".length()] = I("\u0015\u0018\u001f\u0019\u0019\u001a", "wwkmv");
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private DoorHalf(final String s, final int n, final String name) {
            this.name = name;
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
                if (1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
