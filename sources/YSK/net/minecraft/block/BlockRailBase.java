package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.util.*;

public abstract class BlockRailBase extends Block
{
    protected final boolean isPowered;
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public int getMobilityFlag() {
        return "".length();
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.down());
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        if (blockState.getValue(this.getShapeProperty()).isAscending()) {
            world.notifyNeighborsOfStateChange(blockPos.up(), this);
        }
        if (this.isPowered) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.down(), this);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    public abstract IProperty<EnumRailDirection> getShapeProperty();
    
    public static boolean isRailBlock(final World world, final BlockPos blockPos) {
        return isRailBlock(world.getBlockState(blockPos));
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, IBlockState func_176564_a) {
        if (!world.isRemote) {
            func_176564_a = this.func_176564_a(world, blockPos, func_176564_a, " ".length() != 0);
            if (this.isPowered) {
                this.onNeighborBlockChange(world, blockPos, func_176564_a, this);
            }
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        EnumRailDirection enumRailDirection;
        if (blockState.getBlock() == this) {
            enumRailDirection = blockState.getValue(this.getShapeProperty());
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            enumRailDirection = null;
        }
        final EnumRailDirection enumRailDirection2 = enumRailDirection;
        if (enumRailDirection2 != null && enumRailDirection2.isAscending()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        }
    }
    
    protected IBlockState func_176564_a(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        IBlockState blockState2;
        if (world.isRemote) {
            blockState2 = blockState;
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            blockState2 = new Rail(world, blockPos, blockState).func_180364_a(world.isBlockPowered(blockPos), b).getBlockState();
        }
        return blockState2;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            final EnumRailDirection enumRailDirection = blockState.getValue(this.getShapeProperty());
            int n = "".length();
            if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.down())) {
                n = " ".length();
            }
            if (enumRailDirection == EnumRailDirection.ASCENDING_EAST && !World.doesBlockHaveSolidTopSurface(world, blockToAir.east())) {
                n = " ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else if (enumRailDirection == EnumRailDirection.ASCENDING_WEST && !World.doesBlockHaveSolidTopSurface(world, blockToAir.west())) {
                n = " ".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else if (enumRailDirection == EnumRailDirection.ASCENDING_NORTH && !World.doesBlockHaveSolidTopSurface(world, blockToAir.north())) {
                n = " ".length();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else if (enumRailDirection == EnumRailDirection.ASCENDING_SOUTH && !World.doesBlockHaveSolidTopSurface(world, blockToAir.south())) {
                n = " ".length();
            }
            if (n != 0) {
                this.dropBlockAsItem(world, blockToAir, blockState, "".length());
                world.setBlockToAir(blockToAir);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                this.onNeighborChangedInternal(world, blockToAir, blockState, block);
            }
        }
    }
    
    protected void onNeighborChangedInternal(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static boolean isRailBlock(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        if (block != Blocks.rail && block != Blocks.golden_rail && block != Blocks.detector_rail && block != Blocks.activator_rail) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    protected BlockRailBase(final boolean isPowered) {
        super(Material.circuits);
        this.isPowered = isPowered;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    public class Rail
    {
        final BlockRailBase this$0;
        private final BlockPos pos;
        private final BlockRailBase block;
        private static int[] $SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection;
        private final World world;
        private final List<BlockPos> field_150657_g;
        private final boolean isPowered;
        private IBlockState state;
        
        private boolean func_180363_c(final BlockPos blockPos) {
            int i = "".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
            while (i < this.field_150657_g.size()) {
                final BlockPos blockPos2 = this.field_150657_g.get(i);
                if (blockPos2.getX() == blockPos.getX() && blockPos2.getZ() == blockPos.getZ()) {
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
                if (2 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private Rail findRailAt(final BlockPos blockPos) {
            final IBlockState blockState = this.world.getBlockState(blockPos);
            if (BlockRailBase.isRailBlock(blockState)) {
                return this.this$0.new Rail(this.world, blockPos, blockState);
            }
            final BlockPos up = blockPos.up();
            final IBlockState blockState2 = this.world.getBlockState(up);
            if (BlockRailBase.isRailBlock(blockState2)) {
                return this.this$0.new Rail(this.world, up, blockState2);
            }
            final BlockPos down = blockPos.down();
            final IBlockState blockState3 = this.world.getBlockState(down);
            Rail rail;
            if (BlockRailBase.isRailBlock(blockState3)) {
                rail = this.this$0.new Rail(this.world, down, blockState3);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else {
                rail = null;
            }
            return rail;
        }
        
        private void func_180360_a(final EnumRailDirection enumRailDirection) {
            this.field_150657_g.clear();
            switch ($SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection()[enumRailDirection.ordinal()]) {
                case 1: {
                    this.field_150657_g.add(this.pos.north());
                    this.field_150657_g.add(this.pos.south());
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    this.field_150657_g.add(this.pos.west());
                    this.field_150657_g.add(this.pos.east());
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    this.field_150657_g.add(this.pos.west());
                    this.field_150657_g.add(this.pos.east().up());
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    this.field_150657_g.add(this.pos.west().up());
                    this.field_150657_g.add(this.pos.east());
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    this.field_150657_g.add(this.pos.north().up());
                    this.field_150657_g.add(this.pos.south());
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    this.field_150657_g.add(this.pos.north());
                    this.field_150657_g.add(this.pos.south().up());
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    break;
                }
                case 7: {
                    this.field_150657_g.add(this.pos.east());
                    this.field_150657_g.add(this.pos.south());
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                    break;
                }
                case 8: {
                    this.field_150657_g.add(this.pos.west());
                    this.field_150657_g.add(this.pos.south());
                    "".length();
                    if (0 == -1) {
                        throw null;
                    }
                    break;
                }
                case 9: {
                    this.field_150657_g.add(this.pos.west());
                    this.field_150657_g.add(this.pos.north());
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                    break;
                }
                case 10: {
                    this.field_150657_g.add(this.pos.east());
                    this.field_150657_g.add(this.pos.north());
                    break;
                }
            }
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection() {
            final int[] $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection = Rail.$SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection;
            if ($switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection != null) {
                return $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection;
            }
            final int[] $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2 = new int[EnumRailDirection.values().length];
            try {
                $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.ASCENDING_EAST.ordinal()] = "   ".length();
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.ASCENDING_NORTH.ordinal()] = (0x82 ^ 0x87);
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.ASCENDING_SOUTH.ordinal()] = (0x41 ^ 0x47);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.ASCENDING_WEST.ordinal()] = (0xB4 ^ 0xB0);
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.EAST_WEST.ordinal()] = "  ".length();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.NORTH_EAST.ordinal()] = (0x40 ^ 0x4A);
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.NORTH_SOUTH.ordinal()] = " ".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.NORTH_WEST.ordinal()] = (0x7F ^ 0x76);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.SOUTH_EAST.ordinal()] = (0x98 ^ 0x9F);
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.SOUTH_WEST.ordinal()] = (0x2 ^ 0xA);
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            return Rail.$SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection = $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2;
        }
        
        protected int countAdjacentRails() {
            int length = "".length();
            final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                if (this.hasRailAt(this.pos.offset(iterator.next()))) {
                    ++length;
                }
            }
            return length;
        }
        
        private void func_150645_c(final Rail rail) {
            this.field_150657_g.add(rail.pos);
            final BlockPos north = this.pos.north();
            final BlockPos south = this.pos.south();
            final BlockPos west = this.pos.west();
            final BlockPos east = this.pos.east();
            final boolean func_180363_c = this.func_180363_c(north);
            final boolean func_180363_c2 = this.func_180363_c(south);
            final boolean func_180363_c3 = this.func_180363_c(west);
            final boolean func_180363_c4 = this.func_180363_c(east);
            EnumRailDirection enumRailDirection = null;
            if (func_180363_c || func_180363_c2) {
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
            }
            if (func_180363_c3 || func_180363_c4) {
                enumRailDirection = EnumRailDirection.EAST_WEST;
            }
            if (!this.isPowered) {
                if (func_180363_c2 && func_180363_c4 && !func_180363_c && !func_180363_c3) {
                    enumRailDirection = EnumRailDirection.SOUTH_EAST;
                }
                if (func_180363_c2 && func_180363_c3 && !func_180363_c && !func_180363_c4) {
                    enumRailDirection = EnumRailDirection.SOUTH_WEST;
                }
                if (func_180363_c && func_180363_c3 && !func_180363_c2 && !func_180363_c4) {
                    enumRailDirection = EnumRailDirection.NORTH_WEST;
                }
                if (func_180363_c && func_180363_c4 && !func_180363_c2 && !func_180363_c3) {
                    enumRailDirection = EnumRailDirection.NORTH_EAST;
                }
            }
            if (enumRailDirection == EnumRailDirection.NORTH_SOUTH) {
                if (BlockRailBase.isRailBlock(this.world, north.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_NORTH;
                }
                if (BlockRailBase.isRailBlock(this.world, south.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_SOUTH;
                }
            }
            if (enumRailDirection == EnumRailDirection.EAST_WEST) {
                if (BlockRailBase.isRailBlock(this.world, east.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_EAST;
                }
                if (BlockRailBase.isRailBlock(this.world, west.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_WEST;
                }
            }
            if (enumRailDirection == null) {
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
            }
            this.state = this.state.withProperty(this.block.getShapeProperty(), enumRailDirection);
            this.world.setBlockState(this.pos, this.state, "   ".length());
        }
        
        private boolean func_150653_a(final Rail rail) {
            return this.func_180363_c(rail.pos);
        }
        
        public Rail(final BlockRailBase this$0, final World world, final BlockPos pos, final IBlockState state) {
            this.this$0 = this$0;
            this.field_150657_g = (List<BlockPos>)Lists.newArrayList();
            this.world = world;
            this.pos = pos;
            this.state = state;
            this.block = (BlockRailBase)state.getBlock();
            final EnumRailDirection enumRailDirection = state.getValue(this$0.getShapeProperty());
            this.isPowered = this.block.isPowered;
            this.func_180360_a(enumRailDirection);
        }
        
        private void func_150651_b() {
            int i = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i < this.field_150657_g.size()) {
                final Rail rail = this.findRailAt(this.field_150657_g.get(i));
                if (rail != null && rail.func_150653_a(this)) {
                    this.field_150657_g.set(i, rail.pos);
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                }
                else {
                    this.field_150657_g.remove(i--);
                }
                ++i;
            }
        }
        
        public Rail func_180364_a(final boolean b, final boolean b2) {
            final BlockPos north = this.pos.north();
            final BlockPos south = this.pos.south();
            final BlockPos west = this.pos.west();
            final BlockPos east = this.pos.east();
            final boolean func_180361_d = this.func_180361_d(north);
            final boolean func_180361_d2 = this.func_180361_d(south);
            final boolean func_180361_d3 = this.func_180361_d(west);
            final boolean func_180361_d4 = this.func_180361_d(east);
            EnumRailDirection enumRailDirection = null;
            if ((func_180361_d || func_180361_d2) && !func_180361_d3 && !func_180361_d4) {
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
            }
            if ((func_180361_d3 || func_180361_d4) && !func_180361_d && !func_180361_d2) {
                enumRailDirection = EnumRailDirection.EAST_WEST;
            }
            if (!this.isPowered) {
                if (func_180361_d2 && func_180361_d4 && !func_180361_d && !func_180361_d3) {
                    enumRailDirection = EnumRailDirection.SOUTH_EAST;
                }
                if (func_180361_d2 && func_180361_d3 && !func_180361_d && !func_180361_d4) {
                    enumRailDirection = EnumRailDirection.SOUTH_WEST;
                }
                if (func_180361_d && func_180361_d3 && !func_180361_d2 && !func_180361_d4) {
                    enumRailDirection = EnumRailDirection.NORTH_WEST;
                }
                if (func_180361_d && func_180361_d4 && !func_180361_d2 && !func_180361_d3) {
                    enumRailDirection = EnumRailDirection.NORTH_EAST;
                }
            }
            if (enumRailDirection == null) {
                if (func_180361_d || func_180361_d2) {
                    enumRailDirection = EnumRailDirection.NORTH_SOUTH;
                }
                if (func_180361_d3 || func_180361_d4) {
                    enumRailDirection = EnumRailDirection.EAST_WEST;
                }
                if (!this.isPowered) {
                    if (b) {
                        if (func_180361_d2 && func_180361_d4) {
                            enumRailDirection = EnumRailDirection.SOUTH_EAST;
                        }
                        if (func_180361_d3 && func_180361_d2) {
                            enumRailDirection = EnumRailDirection.SOUTH_WEST;
                        }
                        if (func_180361_d4 && func_180361_d) {
                            enumRailDirection = EnumRailDirection.NORTH_EAST;
                        }
                        if (func_180361_d && func_180361_d3) {
                            enumRailDirection = EnumRailDirection.NORTH_WEST;
                            "".length();
                            if (4 == 1) {
                                throw null;
                            }
                        }
                    }
                    else {
                        if (func_180361_d && func_180361_d3) {
                            enumRailDirection = EnumRailDirection.NORTH_WEST;
                        }
                        if (func_180361_d4 && func_180361_d) {
                            enumRailDirection = EnumRailDirection.NORTH_EAST;
                        }
                        if (func_180361_d3 && func_180361_d2) {
                            enumRailDirection = EnumRailDirection.SOUTH_WEST;
                        }
                        if (func_180361_d2 && func_180361_d4) {
                            enumRailDirection = EnumRailDirection.SOUTH_EAST;
                        }
                    }
                }
            }
            if (enumRailDirection == EnumRailDirection.NORTH_SOUTH) {
                if (BlockRailBase.isRailBlock(this.world, north.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_NORTH;
                }
                if (BlockRailBase.isRailBlock(this.world, south.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_SOUTH;
                }
            }
            if (enumRailDirection == EnumRailDirection.EAST_WEST) {
                if (BlockRailBase.isRailBlock(this.world, east.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_EAST;
                }
                if (BlockRailBase.isRailBlock(this.world, west.up())) {
                    enumRailDirection = EnumRailDirection.ASCENDING_WEST;
                }
            }
            if (enumRailDirection == null) {
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
            }
            this.func_180360_a(enumRailDirection);
            this.state = this.state.withProperty(this.block.getShapeProperty(), enumRailDirection);
            if (b2 || this.world.getBlockState(this.pos) != this.state) {
                this.world.setBlockState(this.pos, this.state, "   ".length());
                int i = "".length();
                "".length();
                if (4 < 0) {
                    throw null;
                }
                while (i < this.field_150657_g.size()) {
                    final Rail rail = this.findRailAt(this.field_150657_g.get(i));
                    if (rail != null) {
                        rail.func_150651_b();
                        if (rail.func_150649_b(this)) {
                            rail.func_150645_c(this);
                        }
                    }
                    ++i;
                }
            }
            return this;
        }
        
        private boolean func_180361_d(final BlockPos blockPos) {
            final Rail rail = this.findRailAt(blockPos);
            if (rail == null) {
                return "".length() != 0;
            }
            rail.func_150651_b();
            return rail.func_150649_b(this);
        }
        
        private boolean hasRailAt(final BlockPos blockPos) {
            if (!BlockRailBase.isRailBlock(this.world, blockPos) && !BlockRailBase.isRailBlock(this.world, blockPos.up()) && !BlockRailBase.isRailBlock(this.world, blockPos.down())) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        public IBlockState getBlockState() {
            return this.state;
        }
        
        private boolean func_150649_b(final Rail rail) {
            if (!this.func_150653_a(rail) && this.field_150657_g.size() == "  ".length()) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
    }
    
    public enum EnumRailDirection implements IStringSerializable
    {
        private static final EnumRailDirection[] ENUM$VALUES;
        
        ASCENDING_EAST(EnumRailDirection.I[0x12 ^ 0x16], "  ".length(), "  ".length(), EnumRailDirection.I[0x6 ^ 0x3]);
        
        private static final String[] I;
        private static final EnumRailDirection[] META_LOOKUP;
        
        NORTH_WEST(EnumRailDirection.I[0x97 ^ 0x87], 0x38 ^ 0x30, 0xA ^ 0x2, EnumRailDirection.I[0x9 ^ 0x18]);
        
        private final String name;
        
        SOUTH_EAST(EnumRailDirection.I[0x6A ^ 0x66], 0x1B ^ 0x1D, 0xAA ^ 0xAC, EnumRailDirection.I[0xBA ^ 0xB7]), 
        SOUTH_WEST(EnumRailDirection.I[0x38 ^ 0x36], 0xB6 ^ 0xB1, 0x45 ^ 0x42, EnumRailDirection.I[0xC8 ^ 0xC7]), 
        ASCENDING_WEST(EnumRailDirection.I[0x22 ^ 0x24], "   ".length(), "   ".length(), EnumRailDirection.I[0x36 ^ 0x31]), 
        NORTH_SOUTH(EnumRailDirection.I["".length()], "".length(), "".length(), EnumRailDirection.I[" ".length()]), 
        ASCENDING_SOUTH(EnumRailDirection.I[0x41 ^ 0x4B], 0x70 ^ 0x75, 0xC7 ^ 0xC2, EnumRailDirection.I[0x57 ^ 0x5C]), 
        NORTH_EAST(EnumRailDirection.I[0x13 ^ 0x1], 0x3A ^ 0x33, 0x54 ^ 0x5D, EnumRailDirection.I[0xA7 ^ 0xB4]), 
        ASCENDING_NORTH(EnumRailDirection.I[0x49 ^ 0x41], 0x67 ^ 0x63, 0x47 ^ 0x43, EnumRailDirection.I[0xBE ^ 0xB7]);
        
        private final int meta;
        
        EAST_WEST(EnumRailDirection.I["  ".length()], " ".length(), " ".length(), EnumRailDirection.I["   ".length()]);
        
        private EnumRailDirection(final String s, final int n, final int meta, final String name) {
            this.meta = meta;
            this.name = name;
        }
        
        public boolean isAscending() {
            if (this != EnumRailDirection.ASCENDING_NORTH && this != EnumRailDirection.ASCENDING_EAST && this != EnumRailDirection.ASCENDING_SOUTH && this != EnumRailDirection.ASCENDING_WEST) {
                return "".length() != 0;
            }
            return " ".length() != 0;
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
                if (!true) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private static void I() {
            (I = new String[0x11 ^ 0x5])["".length()] = I("7\u001e?=!&\u0002\"<=1", "yQmii");
            EnumRailDirection.I[" ".length()] = I(";\u0007(%'\n\u001b5$;=", "UhZQO");
            EnumRailDirection.I["  ".length()] = I("7\u0017\u0014?'%\u0013\u0014?", "rVGkx");
            EnumRailDirection.I["   ".length()] = I("\u000e\f\u0005\u001e\u001a\u001c\b\u0005\u001e", "kmvjE");
            EnumRailDirection.I[0x3F ^ 0x3B] = I("\u001b:\u000e\u0016\b\u001e \u0003\u0014\u0019\u001f(\u001e\u0007", "ZiMSF");
            EnumRailDirection.I[0x94 ^ 0x91] = I("0*+\u0016)50&\u0014\u001848;\u0007", "QYHsG");
            EnumRailDirection.I[0x81 ^ 0x87] = I("4736\u001f1->4\u000e\"!#'", "udpsQ");
            EnumRailDirection.I[0x1B ^ 0x1C] = I("\u000f&0+9\n<=)\b\u00190 :", "nUSNW");
            EnumRailDirection.I[0x3E ^ 0x36] = I("\u000e\u0011\u000f&$\u000b\u000b\u0002$5\u0001\r\u001e7\"", "OBLcj");
            EnumRailDirection.I[0x77 ^ 0x7E] = I("46\n?\t1,\u0007=8;*\u001b.\u000f", "UEiZg");
            EnumRailDirection.I[0xBA ^ 0xB0] = I("+\u00006\u000e).\u001a;\f89\u001c \u001f/", "jSuKg");
            EnumRailDirection.I[0x9C ^ 0x97] = I("\u0006\u0012*\n\u001c\u0003\b'\b-\u0014\u000e<\u001b\u001a", "gaIor");
            EnumRailDirection.I[0x83 ^ 0x8F] = I("\u001c',\u000e<\u0010-8\t ", "OhyZt");
            EnumRailDirection.I[0x5E ^ 0x53] = I("+\"\u00032\u0002\u0007(\u00175\u001e", "XMvFj");
            EnumRailDirection.I[0x16 ^ 0x18] = I("\u001c\f4\u00071\u0010\u0014$\u0000-", "OCaSy");
            EnumRailDirection.I[0x2E ^ 0x21] = I("\n\b6,>&\u0010&+\"", "ygCXV");
            EnumRailDirection.I[0x44 ^ 0x54] = I("\u0019\u001d\u00038'\b\u0005\u0014?;", "WRQlo");
            EnumRailDirection.I[0xBF ^ 0xAE] = I("\t\u001a\u0015:98\u0002\u0002=%", "gugNQ");
            EnumRailDirection.I[0xB6 ^ 0xA4] = I("\u001d?\u001d\u0016%\f5\u000e\u00119", "SpOBm");
            EnumRailDirection.I[0x22 ^ 0x31] = I("\r\f*'/<\u00069 3", "ccXSG");
        }
        
        static {
            I();
            final EnumRailDirection[] enum$VALUES = new EnumRailDirection[0x7B ^ 0x71];
            enum$VALUES["".length()] = EnumRailDirection.NORTH_SOUTH;
            enum$VALUES[" ".length()] = EnumRailDirection.EAST_WEST;
            enum$VALUES["  ".length()] = EnumRailDirection.ASCENDING_EAST;
            enum$VALUES["   ".length()] = EnumRailDirection.ASCENDING_WEST;
            enum$VALUES[0x8C ^ 0x88] = EnumRailDirection.ASCENDING_NORTH;
            enum$VALUES[0xC1 ^ 0xC4] = EnumRailDirection.ASCENDING_SOUTH;
            enum$VALUES[0x25 ^ 0x23] = EnumRailDirection.SOUTH_EAST;
            enum$VALUES[0xB2 ^ 0xB5] = EnumRailDirection.SOUTH_WEST;
            enum$VALUES[0x75 ^ 0x7D] = EnumRailDirection.NORTH_WEST;
            enum$VALUES[0xA7 ^ 0xAE] = EnumRailDirection.NORTH_EAST;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumRailDirection[values().length];
            final EnumRailDirection[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
            while (i < length) {
                final EnumRailDirection enumRailDirection = values[i];
                EnumRailDirection.META_LOOKUP[enumRailDirection.getMetadata()] = enumRailDirection;
                ++i;
            }
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        public static EnumRailDirection byMetadata(int length) {
            if (length < 0 || length >= EnumRailDirection.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumRailDirection.META_LOOKUP[length];
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
}
