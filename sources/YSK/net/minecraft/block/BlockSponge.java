package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import java.util.*;

public class BlockSponge extends Block
{
    private static final String[] I;
    public static final PropertyBool WET;
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.tryAbsorb(world, blockPos, blockState);
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + BlockSponge.I[" ".length()]);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.tryAbsorb(world, blockPos, blockState);
        super.onNeighborBlockChange(world, blockPos, blockState, block);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), "".length()));
        list.add(new ItemStack(item, " ".length(), " ".length()));
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (blockState.getValue((IProperty<Boolean>)BlockSponge.WET)) {
            final EnumFacing random2 = EnumFacing.random(random);
            if (random2 != EnumFacing.UP && !World.doesBlockHaveSolidTopSurface(world, blockPos.offset(random2))) {
                final double n = blockPos.getX();
                final double n2 = blockPos.getY();
                final double n3 = blockPos.getZ();
                double n4;
                double n5;
                double n6;
                if (random2 == EnumFacing.DOWN) {
                    n4 = n2 - 0.05;
                    n5 = n + random.nextDouble();
                    n6 = n3 + random.nextDouble();
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
                else {
                    n4 = n2 + random.nextDouble() * 0.8;
                    if (random2.getAxis() == EnumFacing.Axis.X) {
                        n6 = n3 + random.nextDouble();
                        if (random2 == EnumFacing.EAST) {
                            n5 = n + 1.0;
                            "".length();
                            if (4 <= 3) {
                                throw null;
                            }
                        }
                        else {
                            n5 = n + 0.05;
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                        }
                    }
                    else {
                        n5 = n + random.nextDouble();
                        if (random2 == EnumFacing.SOUTH) {
                            n6 = n3 + 1.0;
                            "".length();
                            if (3 >= 4) {
                                throw null;
                            }
                        }
                        else {
                            n6 = n3 + 0.05;
                        }
                    }
                }
                world.spawnParticle(EnumParticleTypes.DRIP_WATER, n5, n4, n6, 0.0, 0.0, 0.0, new int["".length()]);
            }
        }
    }
    
    static {
        I();
        WET = PropertyBool.create(BlockSponge.I["".length()]);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        final PropertyBool wet = BlockSponge.WET;
        int n2;
        if ((n & " ".length()) == " ".length()) {
            n2 = " ".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return defaultState.withProperty((IProperty<Comparable>)wet, n2 != 0);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockSponge.WET;
        return new BlockState(this, array);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n;
        if (blockState.getValue((IProperty<Boolean>)BlockSponge.WET)) {
            n = " ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        int n;
        if (blockState.getValue((IProperty<Boolean>)BlockSponge.WET)) {
            n = " ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    protected void tryAbsorb(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!blockState.getValue((IProperty<Boolean>)BlockSponge.WET) && this.absorb(world, blockPos)) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockSponge.WET, (boolean)(" ".length() != 0)), "  ".length());
            world.playAuxSFX(448 + 299 + 1247 + 7, blockPos, Block.getIdFromBlock(Blocks.water));
        }
    }
    
    private boolean absorb(final World world, final BlockPos blockPos) {
        final LinkedList linkedList = Lists.newLinkedList();
        final ArrayList arrayList = Lists.newArrayList();
        linkedList.add(new Tuple<BlockPos, Integer>(blockPos, (B)"".length()));
        int length = "".length();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (!linkedList.isEmpty()) {
            final Tuple<BlockPos, B> tuple = linkedList.poll();
            final BlockPos blockPos2 = tuple.getFirst();
            final int intValue = (int)tuple.getSecond();
            final EnumFacing[] values;
            final int length2 = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < length2) {
                final BlockPos offset = blockPos2.offset(values[i]);
                if (world.getBlockState(offset).getBlock().getMaterial() == Material.water) {
                    world.setBlockState(offset, Blocks.air.getDefaultState(), "  ".length());
                    arrayList.add(offset);
                    ++length;
                    if (intValue < (0x6F ^ 0x69)) {
                        linkedList.add(new Tuple<BlockPos, Integer>(offset, intValue + " ".length()));
                    }
                }
                ++i;
            }
            if (length <= (0x62 ^ 0x22)) {
                continue;
            }
            "".length();
            if (-1 >= 2) {
                throw null;
            }
            break;
        }
        final Iterator<BlockPos> iterator = arrayList.iterator();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            world.notifyNeighborsOfStateChange(iterator.next(), Blocks.air);
        }
        if (length > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected BlockSponge() {
        super(Material.sponge);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockSponge.WET, (boolean)("".length() != 0)));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("9<%", "NYQDY");
        BlockSponge.I[" ".length()] = I("H\r \u0000d\b\b?\u001c", "fiRyJ");
    }
}
