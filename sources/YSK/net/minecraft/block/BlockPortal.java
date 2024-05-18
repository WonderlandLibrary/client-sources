package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.pattern.*;
import com.google.common.cache.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockPortal extends BlockBreakable
{
    private static final String[] I;
    public static final PropertyEnum<EnumFacing.Axis> AXIS;
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        EnumFacing.Axis axis = null;
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockAccess.getBlockState(blockPos).getBlock() == this) {
            axis = blockState.getValue(BlockPortal.AXIS);
            if (axis == null) {
                return "".length() != 0;
            }
            if (axis == EnumFacing.Axis.Z && enumFacing != EnumFacing.EAST && enumFacing != EnumFacing.WEST) {
                return "".length() != 0;
            }
            if (axis == EnumFacing.Axis.X && enumFacing != EnumFacing.SOUTH && enumFacing != EnumFacing.NORTH) {
                return "".length() != 0;
            }
        }
        int n;
        if (blockAccess.getBlockState(blockPos.west()).getBlock() == this && blockAccess.getBlockState(blockPos.west("  ".length())).getBlock() != this) {
            n = " ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        int n3;
        if (blockAccess.getBlockState(blockPos.east()).getBlock() == this && blockAccess.getBlockState(blockPos.east("  ".length())).getBlock() != this) {
            n3 = " ".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        final int n4 = n3;
        int n5;
        if (blockAccess.getBlockState(blockPos.north()).getBlock() == this && blockAccess.getBlockState(blockPos.north("  ".length())).getBlock() != this) {
            n5 = " ".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            n5 = "".length();
        }
        final int n6 = n5;
        int n7;
        if (blockAccess.getBlockState(blockPos.south()).getBlock() == this && blockAccess.getBlockState(blockPos.south("  ".length())).getBlock() != this) {
            n7 = " ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n7 = "".length();
        }
        final int n8 = n7;
        int n9;
        if (n2 == 0 && n4 == 0 && axis != EnumFacing.Axis.X) {
            n9 = "".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else {
            n9 = " ".length();
        }
        final int n10 = n9;
        int n11;
        if (n6 == 0 && n8 == 0 && axis != EnumFacing.Axis.Z) {
            n11 = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n11 = " ".length();
        }
        final int n12 = n11;
        int n13;
        if (n10 != 0 && enumFacing == EnumFacing.WEST) {
            n13 = " ".length();
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else if (n10 != 0 && enumFacing == EnumFacing.EAST) {
            n13 = " ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (n12 != 0 && enumFacing == EnumFacing.NORTH) {
            n13 = " ".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (n12 != 0 && enumFacing == EnumFacing.SOUTH) {
            n13 = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n13 = "".length();
        }
        return n13 != 0;
    }
    
    public static int getMetaForAxis(final EnumFacing.Axis axis) {
        int n;
        if (axis == EnumFacing.Axis.X) {
            n = " ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else if (axis == EnumFacing.Axis.Z) {
            n = "  ".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    public BlockPortal() {
        super(Material.portal, "".length() != 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPortal.AXIS, EnumFacing.Axis.X));
        this.setTickRandomly(" ".length() != 0);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (entity.ridingEntity == null && entity.riddenByEntity == null) {
            entity.func_181015_d(blockPos);
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        final PropertyEnum<EnumFacing.Axis> axis = BlockPortal.AXIS;
        EnumFacing.Axis axis2;
        if ((n & "   ".length()) == "  ".length()) {
            axis2 = EnumFacing.Axis.Z;
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            axis2 = EnumFacing.Axis.X;
        }
        return defaultState.withProperty((IProperty<Comparable>)axis, axis2);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return getMetaForAxis(blockState.getValue(BlockPortal.AXIS));
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("710!", "VIYRf");
        BlockPortal.I[" ".length()] = I("\t$\u0017=8>;;%4\u0004%=", "mKZRZ");
        BlockPortal.I["  ".length()] = I(":+\u0010\u0013\"&j\u0012\b1>%\u000e", "JDbgC");
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        super.updateTick(world, blockPos, blockState, random);
        if (world.provider.isSurfaceWorld() && world.getGameRules().getBoolean(BlockPortal.I[" ".length()]) && random.nextInt(799 + 1200 - 560 + 561) < world.getDifficulty().getDifficultyId()) {
            final int y = blockPos.getY();
            BlockPos down = blockPos;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
            while (!World.doesBlockHaveSolidTopSurface(world, down) && down.getY() > 0) {
                down = down.down();
            }
            if (y > 0 && !world.getBlockState(down.up()).getBlock().isNormalCube()) {
                final Entity spawnCreature = ItemMonsterPlacer.spawnCreature(world, 0x3B ^ 0x2, down.getX() + 0.5, down.getY() + 1.1, down.getZ() + 0.5);
                if (spawnCreature != null) {
                    spawnCreature.timeUntilPortal = spawnCreature.getPortalCooldown();
                }
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final EnumFacing.Axis axis = blockState.getValue(BlockPortal.AXIS);
        if (axis == EnumFacing.Axis.X) {
            final Size size = new Size(world, blockPos, EnumFacing.Axis.X);
            if (!size.func_150860_b() || Size.access$0(size) < Size.access$1(size) * Size.access$2(size)) {
                world.setBlockState(blockPos, Blocks.air.getDefaultState());
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
        }
        else if (axis == EnumFacing.Axis.Z) {
            final Size size2 = new Size(world, blockPos, EnumFacing.Axis.Z);
            if (!size2.func_150860_b() || Size.access$0(size2) < Size.access$1(size2) * Size.access$2(size2)) {
                world.setBlockState(blockPos, Blocks.air.getDefaultState());
            }
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final EnumFacing.Axis axis = blockAccess.getBlockState(blockPos).getValue(BlockPortal.AXIS);
        float n = 0.125f;
        float n2 = 0.125f;
        if (axis == EnumFacing.Axis.X) {
            n = 0.5f;
        }
        if (axis == EnumFacing.Axis.Z) {
            n2 = 0.5f;
        }
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n2, 0.5f + n, 1.0f, 0.5f + n2);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (random.nextInt(0xE7 ^ 0x83) == 0) {
            world.playSound(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, BlockPortal.I["  ".length()], 0.5f, random.nextFloat() * 0.4f + 0.8f, "".length() != 0);
        }
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < (0x9A ^ 0x9E)) {
            double n = blockPos.getX() + random.nextFloat();
            final double n2 = blockPos.getY() + random.nextFloat();
            double n3 = blockPos.getZ() + random.nextFloat();
            double n4 = (random.nextFloat() - 0.5) * 0.5;
            final double n5 = (random.nextFloat() - 0.5) * 0.5;
            double n6 = (random.nextFloat() - 0.5) * 0.5;
            final int n7 = random.nextInt("  ".length()) * "  ".length() - " ".length();
            if (world.getBlockState(blockPos.west()).getBlock() != this && world.getBlockState(blockPos.east()).getBlock() != this) {
                n = blockPos.getX() + 0.5 + 0.25 * n7;
                n4 = random.nextFloat() * 2.0f * n7;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                n3 = blockPos.getZ() + 0.5 + 0.25 * n7;
                n6 = random.nextFloat() * 2.0f * n7;
            }
            world.spawnParticle(EnumParticleTypes.PORTAL, n, n2, n3, n4, n5, n6, new int["".length()]);
            ++i;
        }
    }
    
    public BlockPattern.PatternHelper func_181089_f(final World world, final BlockPos blockPos) {
        EnumFacing.Axis axis = EnumFacing.Axis.Z;
        Size size = new Size(world, blockPos, EnumFacing.Axis.X);
        final LoadingCache<BlockPos, BlockWorldState> func_181627_a = BlockPattern.func_181627_a(world, " ".length() != 0);
        if (!size.func_150860_b()) {
            axis = EnumFacing.Axis.X;
            size = new Size(world, blockPos, EnumFacing.Axis.Z);
        }
        if (!size.func_150860_b()) {
            return new BlockPattern.PatternHelper(blockPos, EnumFacing.NORTH, EnumFacing.UP, func_181627_a, " ".length(), " ".length(), " ".length());
        }
        final int[] array = new int[EnumFacing.AxisDirection.values().length];
        final EnumFacing rotateYCCW = Size.access$3(size).rotateYCCW();
        final BlockPos up = Size.access$4(size).up(size.func_181100_a() - " ".length());
        final EnumFacing.AxisDirection[] values;
        final int length = (values = EnumFacing.AxisDirection.values()).length;
        int i = "".length();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i < length) {
            final EnumFacing.AxisDirection axisDirection = values[i];
            BlockPos offset;
            if (rotateYCCW.getAxisDirection() == axisDirection) {
                offset = up;
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                offset = up.offset(Size.access$3(size), size.func_181101_b() - " ".length());
            }
            final BlockPattern.PatternHelper patternHelper = new BlockPattern.PatternHelper(offset, EnumFacing.func_181076_a(axisDirection, axis), EnumFacing.UP, func_181627_a, size.func_181101_b(), size.func_181100_a(), " ".length());
            int j = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (j < size.func_181101_b()) {
                int k = "".length();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (k < size.func_181100_a()) {
                    final BlockWorldState translateOffset = patternHelper.translateOffset(j, k, " ".length());
                    if (translateOffset.getBlockState() != null && translateOffset.getBlockState().getBlock().getMaterial() != Material.air) {
                        final int[] array2 = array;
                        final int ordinal = axisDirection.ordinal();
                        array2[ordinal] += " ".length();
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        EnumFacing.AxisDirection positive = EnumFacing.AxisDirection.POSITIVE;
        final EnumFacing.AxisDirection[] values2;
        final int length2 = (values2 = EnumFacing.AxisDirection.values()).length;
        int l = "".length();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (l < length2) {
            final EnumFacing.AxisDirection axisDirection2 = values2[l];
            if (array[axisDirection2.ordinal()] < array[positive.ordinal()]) {
                positive = axisDirection2;
            }
            ++l;
        }
        BlockPos offset2;
        if (rotateYCCW.getAxisDirection() == positive) {
            offset2 = up;
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            offset2 = up.offset(Size.access$3(size), size.func_181101_b() - " ".length());
        }
        return new BlockPattern.PatternHelper(offset2, EnumFacing.func_181076_a(positive, axis), EnumFacing.UP, func_181627_a, size.func_181101_b(), size.func_181100_a(), " ".length());
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
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean func_176548_d(final World world, final BlockPos blockPos) {
        final Size size = new Size(world, blockPos, EnumFacing.Axis.X);
        if (size.func_150860_b() && Size.access$0(size) == 0) {
            size.func_150859_c();
            return " ".length() != 0;
        }
        final Size size2 = new Size(world, blockPos, EnumFacing.Axis.Z);
        if (size2.func_150860_b() && Size.access$0(size2) == 0) {
            size2.func_150859_c();
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockPortal.AXIS;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    static {
        I();
        final String s = BlockPortal.I["".length()];
        final Class<EnumFacing.Axis> clazz = EnumFacing.Axis.class;
        final EnumFacing.Axis[] array = new EnumFacing.Axis["  ".length()];
        array["".length()] = EnumFacing.Axis.X;
        array[" ".length()] = EnumFacing.Axis.Z;
        AXIS = PropertyEnum.create(s, clazz, array);
    }
    
    public static class Size
    {
        private int field_150862_g;
        private BlockPos field_150861_f;
        private int field_150864_e;
        private final EnumFacing field_150863_d;
        private final EnumFacing.Axis axis;
        private final EnumFacing field_150866_c;
        private int field_150868_h;
        private final World world;
        
        protected int func_180120_a(final BlockPos blockPos, final EnumFacing enumFacing) {
            int i = "".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
            while (i < (0x73 ^ 0x65)) {
                final BlockPos offset = blockPos.offset(enumFacing, i);
                if (!this.func_150857_a(this.world.getBlockState(offset).getBlock())) {
                    break;
                }
                if (this.world.getBlockState(offset.down()).getBlock() != Blocks.obsidian) {
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
            int length;
            if (this.world.getBlockState(blockPos.offset(enumFacing, i)).getBlock() == Blocks.obsidian) {
                length = i;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                length = "".length();
            }
            return length;
        }
        
        protected boolean func_150857_a(final Block block) {
            if (block.blockMaterial != Material.air && block != Blocks.fire && block != Blocks.portal) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        protected int func_150858_a() {
            this.field_150862_g = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        Label_0254:
            while (this.field_150862_g < (0x24 ^ 0x31)) {
                int i = "".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
                while (i < this.field_150868_h) {
                    final BlockPos up = this.field_150861_f.offset(this.field_150866_c, i).up(this.field_150862_g);
                    final Block block = this.world.getBlockState(up).getBlock();
                    if (!this.func_150857_a(block)) {
                        "".length();
                        if (2 == 4) {
                            throw null;
                        }
                        break Label_0254;
                    }
                    else {
                        if (block == Blocks.portal) {
                            this.field_150864_e += " ".length();
                        }
                        if (i == 0) {
                            if (this.world.getBlockState(up.offset(this.field_150863_d)).getBlock() != Blocks.obsidian) {
                                "".length();
                                if (2 != 2) {
                                    throw null;
                                }
                                break Label_0254;
                            }
                        }
                        else if (i == this.field_150868_h - " ".length() && this.world.getBlockState(up.offset(this.field_150866_c)).getBlock() != Blocks.obsidian) {
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                            break Label_0254;
                        }
                        ++i;
                    }
                }
                this.field_150862_g += " ".length();
            }
            int j = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
            while (j < this.field_150868_h) {
                if (this.world.getBlockState(this.field_150861_f.offset(this.field_150866_c, j).up(this.field_150862_g)).getBlock() != Blocks.obsidian) {
                    this.field_150862_g = "".length();
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++j;
                }
            }
            if (this.field_150862_g <= (0x7 ^ 0x12) && this.field_150862_g >= "   ".length()) {
                return this.field_150862_g;
            }
            this.field_150861_f = null;
            this.field_150868_h = "".length();
            this.field_150862_g = "".length();
            return "".length();
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
                if (3 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public boolean func_150860_b() {
            if (this.field_150861_f != null && this.field_150868_h >= "  ".length() && this.field_150868_h <= (0x1E ^ 0xB) && this.field_150862_g >= "   ".length() && this.field_150862_g <= (0x44 ^ 0x51)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        static BlockPos access$4(final Size size) {
            return size.field_150861_f;
        }
        
        public int func_181101_b() {
            return this.field_150868_h;
        }
        
        static EnumFacing access$3(final Size size) {
            return size.field_150866_c;
        }
        
        public int func_181100_a() {
            return this.field_150862_g;
        }
        
        static int access$2(final Size size) {
            return size.field_150862_g;
        }
        
        static int access$1(final Size size) {
            return size.field_150868_h;
        }
        
        public Size(final World world, BlockPos down, final EnumFacing.Axis axis) {
            this.field_150864_e = "".length();
            this.world = world;
            this.axis = axis;
            if (axis == EnumFacing.Axis.X) {
                this.field_150863_d = EnumFacing.EAST;
                this.field_150866_c = EnumFacing.WEST;
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                this.field_150863_d = EnumFacing.NORTH;
                this.field_150866_c = EnumFacing.SOUTH;
            }
            final BlockPos blockPos = down;
            "".length();
            if (4 < 3) {
                throw null;
            }
            while (down.getY() > blockPos.getY() - (0x87 ^ 0x92) && down.getY() > 0 && this.func_150857_a(world.getBlockState(down.down()).getBlock())) {
                down = down.down();
            }
            final int n = this.func_180120_a(down, this.field_150863_d) - " ".length();
            if (n >= 0) {
                this.field_150861_f = down.offset(this.field_150863_d, n);
                this.field_150868_h = this.func_180120_a(this.field_150861_f, this.field_150866_c);
                if (this.field_150868_h < "  ".length() || this.field_150868_h > (0x2C ^ 0x39)) {
                    this.field_150861_f = null;
                    this.field_150868_h = "".length();
                }
            }
            if (this.field_150861_f != null) {
                this.field_150862_g = this.func_150858_a();
            }
        }
        
        static int access$0(final Size size) {
            return size.field_150864_e;
        }
        
        public void func_150859_c() {
            int i = "".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
            while (i < this.field_150868_h) {
                final BlockPos offset = this.field_150861_f.offset(this.field_150866_c, i);
                int j = "".length();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
                while (j < this.field_150862_g) {
                    this.world.setBlockState(offset.up(j), Blocks.portal.getDefaultState().withProperty(BlockPortal.AXIS, this.axis), "  ".length());
                    ++j;
                }
                ++i;
            }
        }
    }
}
