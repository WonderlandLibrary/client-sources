package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.item.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider
{
    private static final String[] I;
    public static final PropertyEnum<Mode> MODE;
    public static final PropertyBool POWERED;
    
    @Override
    protected boolean shouldBePowered(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int calculateInputStrength = this.calculateInputStrength(world, blockPos, blockState);
        if (calculateInputStrength >= (0x17 ^ 0x18)) {
            return " ".length() != 0;
        }
        if (calculateInputStrength == 0) {
            return "".length() != 0;
        }
        final int powerOnSides = this.getPowerOnSides(world, blockPos, blockState);
        int n;
        if (powerOnSides == 0) {
            n = " ".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else if (calculateInputStrength >= powerOnSides) {
            n = " ".length();
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isRepeaterPowered) {
            world.setBlockState(blockPos, this.getUnpoweredState(blockState).withProperty((IProperty<Comparable>)BlockRedstoneComparator.POWERED, (boolean)(" ".length() != 0)), 0x72 ^ 0x76);
        }
        this.onStateChange(world, blockPos, blockState);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockRedstoneComparator.FACING).getHorizontalIndex();
        if (blockState.getValue((IProperty<Boolean>)BlockRedstoneComparator.POWERED)) {
            n |= (0x3 ^ 0xB);
        }
        if (blockState.getValue(BlockRedstoneComparator.MODE) == Mode.SUBTRACT) {
            n |= (0x58 ^ 0x5C);
        }
        return n;
    }
    
    @Override
    protected IBlockState getPoweredState(final IBlockState blockState) {
        return Blocks.powered_comparator.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneComparator.FACING, blockState.getValue((IProperty<EnumFacing>)BlockRedstoneComparator.FACING)).withProperty((IProperty<Comparable>)BlockRedstoneComparator.POWERED, blockState.getValue((IProperty<Boolean>)BlockRedstoneComparator.POWERED)).withProperty(BlockRedstoneComparator.MODE, blockState.getValue(BlockRedstoneComparator.MODE));
    }
    
    @Override
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        super.onBlockEventReceived(world, blockPos, blockState, n, n2);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        int n3;
        if (tileEntity == null) {
            n3 = "".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            n3 = (tileEntity.receiveClientEvent(n, n2) ? 1 : 0);
        }
        return n3 != 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneComparator.FACING, EnumFacing.getHorizontal(n));
        final PropertyBool powered = BlockRedstoneComparator.POWERED;
        int n2;
        if ((n & (0x76 ^ 0x7E)) > 0) {
            n2 = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)powered, n2 != 0);
        final PropertyEnum<Mode> mode = BlockRedstoneComparator.MODE;
        Mode mode2;
        if ((n & (0x53 ^ 0x57)) > 0) {
            mode2 = Mode.SUBTRACT;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            mode2 = Mode.COMPARE;
        }
        return withProperty2.withProperty((IProperty<Comparable>)mode, mode2);
    }
    
    private int calculateOutput(final World world, final BlockPos blockPos, final IBlockState blockState) {
        int n;
        if (blockState.getValue(BlockRedstoneComparator.MODE) == Mode.SUBTRACT) {
            n = Math.max(this.calculateInputStrength(world, blockPos, blockState) - this.getPowerOnSides(world, blockPos, blockState), "".length());
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            n = this.calculateInputStrength(world, blockPos, blockState);
        }
        return n;
    }
    
    public BlockRedstoneComparator(final boolean b) {
        super(b);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockRedstoneComparator.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockRedstoneComparator.POWERED, "".length() != 0).withProperty(BlockRedstoneComparator.MODE, Mode.COMPARE));
        this.isBlockContainer = (" ".length() != 0);
    }
    
    @Override
    protected IBlockState getUnpoweredState(final IBlockState blockState) {
        return Blocks.unpowered_comparator.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneComparator.FACING, blockState.getValue((IProperty<EnumFacing>)BlockRedstoneComparator.FACING)).withProperty((IProperty<Comparable>)BlockRedstoneComparator.POWERED, blockState.getValue((IProperty<Boolean>)BlockRedstoneComparator.POWERED)).withProperty(BlockRedstoneComparator.MODE, blockState.getValue(BlockRedstoneComparator.MODE));
    }
    
    @Override
    protected boolean isPowered(final IBlockState blockState) {
        if (!this.isRepeaterPowered && !blockState.getValue((IProperty<Boolean>)BlockRedstoneComparator.POWERED)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private EntityItemFrame findItemFrame(final World world, final EnumFacing enumFacing, final BlockPos blockPos) {
        final List<Entity> entitiesWithinAABB = world.getEntitiesWithinAABB((Class<? extends Entity>)EntityItemFrame.class, new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + " ".length(), blockPos.getY() + " ".length(), blockPos.getZ() + " ".length()), (com.google.common.base.Predicate<? super Entity>)new Predicate<Entity>(this, enumFacing) {
            private final EnumFacing val$facing;
            final BlockRedstoneComparator this$0;
            
            public boolean apply(final Entity entity) {
                if (entity != null && entity.getHorizontalFacing() == this.val$facing) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
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
                    if (4 <= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        EntityItemFrame entityItemFrame;
        if (entitiesWithinAABB.size() == " ".length()) {
            entityItemFrame = entitiesWithinAABB.get("".length());
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            entityItemFrame = null;
        }
        return entityItemFrame;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        world.setTileEntity(blockPos, this.createNewTileEntity(world, "".length()));
    }
    
    private static void I() {
        (I = new String[0x70 ^ 0x74])["".length()] = I("\u00069\u0015\n\u0006\u00132", "vVbot");
        BlockRedstoneComparator.I[" ".length()] = I(";-4-", "VBPHP");
        BlockRedstoneComparator.I["  ".length()] = I("/1\u000f ~%*\u0007=14$\u001e\"\"h+\u000b 5", "FEjMP");
        BlockRedstoneComparator.I["   ".length()] = I("\u0001,;/<\u001ec6':\u0010&", "sMUKS");
    }
    
    static {
        I();
        POWERED = PropertyBool.create(BlockRedstoneComparator.I["".length()]);
        MODE = PropertyEnum.create(BlockRedstoneComparator.I[" ".length()], Mode.class);
    }
    
    @Override
    protected int getActiveSignal(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        int n;
        if (tileEntity instanceof TileEntityComparator) {
            n = ((TileEntityComparator)tileEntity).getOutputSignal();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    protected int calculateInputStrength(final World world, final BlockPos blockPos, final IBlockState blockState) {
        int n = super.calculateInputStrength(world, blockPos, blockState);
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockRedstoneComparator.FACING);
        final BlockPos offset = blockPos.offset(enumFacing);
        final Block block = world.getBlockState(offset).getBlock();
        if (block.hasComparatorInputOverride()) {
            n = block.getComparatorInputOverride(world, offset);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (n < (0xA9 ^ 0xA6) && block.isNormalCube()) {
            final BlockPos offset2 = offset.offset(enumFacing);
            final Block block2 = world.getBlockState(offset2).getBlock();
            if (block2.hasComparatorInputOverride()) {
                n = block2.getComparatorInputOverride(world, offset2);
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else if (block2.getMaterial() == Material.air) {
                final EntityItemFrame itemFrame = this.findItemFrame(world, enumFacing, offset2);
                if (itemFrame != null) {
                    n = itemFrame.func_174866_q();
                }
            }
        }
        return n;
    }
    
    @Override
    protected void updateState(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isBlockTickPending(blockPos, this)) {
            final int calculateOutput = this.calculateOutput(world, blockPos, blockState);
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            int n;
            if (tileEntity instanceof TileEntityComparator) {
                n = ((TileEntityComparator)tileEntity).getOutputSignal();
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            if (calculateOutput != n || this.isPowered(blockState) != this.shouldBePowered(world, blockPos, blockState)) {
                if (this.isFacingTowardsRepeater(world, blockPos, blockState)) {
                    world.updateBlockTick(blockPos, this, "  ".length(), -" ".length());
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                else {
                    world.updateBlockTick(blockPos, this, "  ".length(), "".length());
                }
            }
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneComparator.FACING, entityLivingBase.getHorizontalFacing().getOpposite()).withProperty((IProperty<Comparable>)BlockRedstoneComparator.POWERED, "".length() != 0).withProperty(BlockRedstoneComparator.MODE, Mode.COMPARE);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        world.removeTileEntity(blockPos);
        this.notifyNeighbors(world, blockPos, blockState);
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(BlockRedstoneComparator.I["  ".length()]);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityComparator();
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.capabilities.allowEdit) {
            return "".length() != 0;
        }
        cycleProperty = cycleProperty.cycleProperty(BlockRedstoneComparator.MODE);
        final double n4 = blockPos.getX() + 0.5;
        final double n5 = blockPos.getY() + 0.5;
        final double n6 = blockPos.getZ() + 0.5;
        final String s = BlockRedstoneComparator.I["   ".length()];
        final float n7 = 0.3f;
        float n8;
        if (cycleProperty.getValue(BlockRedstoneComparator.MODE) == Mode.SUBTRACT) {
            n8 = 0.55f;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            n8 = 0.5f;
        }
        world.playSoundEffect(n4, n5, n6, s, n7, n8);
        world.setBlockState(blockPos, cycleProperty, "  ".length());
        this.onStateChange(world, blockPos, cycleProperty);
        return " ".length() != 0;
    }
    
    @Override
    protected int getDelay(final IBlockState blockState) {
        return "  ".length();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.comparator;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["   ".length()];
        array["".length()] = BlockRedstoneComparator.FACING;
        array[" ".length()] = BlockRedstoneComparator.MODE;
        array["  ".length()] = BlockRedstoneComparator.POWERED;
        return new BlockState(this, array);
    }
    
    private void onStateChange(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int calculateOutput = this.calculateOutput(world, blockPos, blockState);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        int n = "".length();
        if (tileEntity instanceof TileEntityComparator) {
            final TileEntityComparator tileEntityComparator = (TileEntityComparator)tileEntity;
            n = tileEntityComparator.getOutputSignal();
            tileEntityComparator.setOutputSignal(calculateOutput);
        }
        if (n != calculateOutput || blockState.getValue(BlockRedstoneComparator.MODE) == Mode.COMPARE) {
            final boolean shouldBePowered = this.shouldBePowered(world, blockPos, blockState);
            final boolean powered = this.isPowered(blockState);
            if (powered && !shouldBePowered) {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockRedstoneComparator.POWERED, (boolean)("".length() != 0)), "  ".length());
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (!powered && shouldBePowered) {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockRedstoneComparator.POWERED, (boolean)(" ".length() != 0)), "  ".length());
            }
            this.notifyNeighbors(world, blockPos, blockState);
        }
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.comparator;
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public enum Mode implements IStringSerializable
    {
        private final String name;
        
        COMPARE(Mode.I["".length()], "".length(), Mode.I[" ".length()]), 
        SUBTRACT(Mode.I["  ".length()], " ".length(), Mode.I["   ".length()]);
        
        private static final Mode[] ENUM$VALUES;
        private static final String[] I;
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            I();
            final Mode[] enum$VALUES = new Mode["  ".length()];
            enum$VALUES["".length()] = Mode.COMPARE;
            enum$VALUES[" ".length()] = Mode.SUBTRACT;
            ENUM$VALUES = enum$VALUES;
        }
        
        private Mode(final String s, final int n, final String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return this.name;
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
                if (3 == 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0xB9 ^ 0xBD])["".length()] = I(".8\u0004\u0001+?2", "mwIQj");
            Mode.I[" ".length()] = I("*&\f=.;,", "IIaMO");
            Mode.I["  ".length()] = I("\u0010>\u000f8\u0015\u0002(\u0019", "CkMlG");
            Mode.I["   ".length()] = I("#//\u0002\u000b199", "PZMvy");
        }
    }
}
