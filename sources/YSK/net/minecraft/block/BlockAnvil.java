package net.minecraft.block;

import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class BlockAnvil extends BlockFalling
{
    public static final PropertyDirection FACING;
    public static final PropertyInteger DAMAGE;
    private static final String[] I;
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return " ".length() != 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return "".length() | blockState.getValue((IProperty<EnumFacing>)BlockAnvil.FACING).getHorizontalIndex() | blockState.getValue((IProperty<Integer>)BlockAnvil.DAMAGE) << "  ".length();
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockAnvil.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
        DAMAGE = PropertyInteger.create(BlockAnvil.I[" ".length()], "".length(), "  ".length());
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockAnvil.FACING, EnumFacing.SOUTH);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockAnvil.DAMAGE);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("/\u0004\b-&.", "IekDH");
        BlockAnvil.I[" ".length()] = I("\u0014;\u0003\u0012\u0010\u0015", "pZnsw");
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockAnvil.FACING;
        array[" ".length()] = BlockAnvil.DAMAGE;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    protected BlockAnvil() {
        super(Material.anvil);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockAnvil.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockAnvil.DAMAGE, "".length()));
        this.setLightOpacity("".length());
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!world.isRemote) {
            entityPlayer.displayGui(new Anvil(world, blockPos));
        }
        return " ".length() != 0;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty((IProperty<Comparable>)BlockAnvil.FACING, entityLivingBase.getHorizontalFacing().rotateY()).withProperty((IProperty<Comparable>)BlockAnvil.DAMAGE, n4 >> "  ".length());
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), "".length()));
        list.add(new ItemStack(item, " ".length(), " ".length()));
        list.add(new ItemStack(item, " ".length(), "  ".length()));
    }
    
    @Override
    protected void onStartFalling(final EntityFallingBlock entityFallingBlock) {
        entityFallingBlock.setHurtEntities(" ".length() != 0);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockAccess.getBlockState(blockPos).getValue((IProperty<EnumFacing>)BlockAnvil.FACING).getAxis() == EnumFacing.Axis.X) {
            this.setBlockBounds(0.0f, 0.0f, 0.125f, 1.0f, 1.0f, 0.875f);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            this.setBlockBounds(0.125f, 0.0f, 0.0f, 0.875f, 1.0f, 1.0f);
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onEndFalling(final World world, final BlockPos blockPos) {
        world.playAuxSFX(959 + 21 - 877 + 919, blockPos, "".length());
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockAnvil.FACING, EnumFacing.getHorizontal(n & "   ".length())).withProperty((IProperty<Comparable>)BlockAnvil.DAMAGE, (n & (0x89 ^ 0x86)) >> "  ".length());
    }
    
    public static class Anvil implements IInteractionObject
    {
        private final World world;
        private static final String[] I;
        private final BlockPos position;
        
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
                if (4 < 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
        }
        
        public Anvil(final World world, final BlockPos position) {
            this.world = world;
            this.position = position;
        }
        
        @Override
        public IChatComponent getDisplayName() {
            return new ChatComponentTranslation(String.valueOf(Blocks.anvil.getUnlocalizedName()) + Anvil.I[" ".length()], new Object["".length()]);
        }
        
        @Override
        public String getGuiID() {
            return Anvil.I["  ".length()];
        }
        
        @Override
        public String getName() {
            return Anvil.I["".length()];
        }
        
        @Override
        public boolean hasCustomName() {
            return "".length() != 0;
        }
        
        @Override
        public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
            return new ContainerRepair(inventoryPlayer, this.world, this.position, entityPlayer);
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("&)1+\u0015", "GGGBy");
            Anvil.I[" ".length()] = I("o\u000f#=\u0014", "AaBPq");
            Anvil.I["  ".length()] = I("\u00020#<$\u001d8+-}\u000e7;0+", "oYMYG");
        }
    }
}
