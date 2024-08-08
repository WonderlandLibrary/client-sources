package net.futureclient.client.modules.world;

import java.util.Iterator;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.BlockReed;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStone;
import net.futureclient.client.Aa;
import net.minecraft.world.World;
import net.futureclient.client.IG;
import net.futureclient.client.s;
import net.futureclient.client.modules.world.nuker.Listener3;
import net.futureclient.client.modules.world.nuker.Listener2;
import net.futureclient.client.modules.world.nuker.Listener1;
import net.futureclient.client.n;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import java.util.ArrayList;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import java.util.List;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.ka;
import net.futureclient.client.R;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.Ea;

public class Nuker extends Ea
{
    private Timer I;
    private Value<Boolean> clickSelect;
    private R<ka.la> mode;
    private NumberValue range;
    private float L;
    private Value<Boolean> raytrace;
    private Value<Boolean> flatten;
    private float j;
    private Timer K;
    public List<IBlockState> M;
    private boolean d;
    private BlockPos a;
    private Value<Boolean> rotate;
    private List<Block> k;
    
    public static Minecraft getMinecraft() {
        return Nuker.D;
    }
    
    public Nuker() {
        super("Nuker", new String[] { "Nuker", "nuke", "nkr" }, true, -6772463, Category.WORLD);
        this.range = new NumberValue(5.0f, 0.1f, 7.0f, 1, new String[] { "Range", "Distnace", "Rang", "Length" });
        this.flatten = new Value<Boolean>(false, new String[] { "Flatten", "Flat" });
        this.rotate = new Value<Boolean>(true, new String[] { "Rotate", "Aiming", "Aim", "Rotation", "Facing", "Face", "F", "Look" });
        this.raytrace = new Value<Boolean>(true, new String[] { "Raytrace", "raytrace", "rt" });
        this.clickSelect = new Value<Boolean>(true, new String[] { "ClickSelect", "CS" });
        this.mode = new R<ka.la>(ka.la.k, new String[] { "Mode", "Type", "Creative", "Survival" });
        final int n = 10;
        this.K = new Timer();
        this.I = new Timer();
        this.M = new ArrayList<IBlockState>();
        final Block[] array = new Block[n];
        array[0] = Blocks.AIR;
        array[1] = (Block)Blocks.WATER;
        array[2] = (Block)Blocks.FIRE;
        array[3] = (Block)Blocks.FLOWING_WATER;
        array[4] = (Block)Blocks.LAVA;
        array[5] = (Block)Blocks.FLOWING_LAVA;
        array[6] = (Block)Blocks.PORTAL;
        array[7] = Blocks.END_PORTAL;
        array[8] = Blocks.END_PORTAL_FRAME;
        array[9] = Blocks.BEDROCK;
        this.k = Arrays.<Block>asList(array);
        this.M(new Value[] { this.mode, this.clickSelect, this.flatten, this.rotate, this.raytrace, this.range });
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this) });
    }
    
    public void B() {
        super.B();
        if (this.M.isEmpty() && this.mode.M().equals((Object)ka.la.M)) {
            s.M().M(String.format("%s to begin nuking.", this.clickSelect.M() ? "Punch a block" : "Use command NukerBlocks"));
        }
    }
    
    public static Minecraft getMinecraft1() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft9() {
        return Nuker.D;
    }
    
    public static Value e(final Nuker nuker) {
        return nuker.clickSelect;
    }
    
    public static float e(final Nuker nuker) {
        return nuker.L;
    }
    
    public static Minecraft getMinecraft10() {
        return Nuker.D;
    }
    
    public static float e(final Nuker nuker, final float j) {
        return nuker.j = j;
    }
    
    public static Timer e(final Nuker nuker) {
        return nuker.I;
    }
    
    public static Minecraft getMinecraft11() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft12() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft14() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft15() {
        return Nuker.D;
    }
    
    public static Timer M(final Nuker nuker) {
        return nuker.K;
    }
    
    public static boolean M(final Nuker nuker, final boolean d) {
        return nuker.d = d;
    }
    
    public static float M(final Nuker nuker, final float l) {
        return nuker.L = l;
    }
    
    public static Value M(final Nuker nuker) {
        return nuker.rotate;
    }
    
    public static Minecraft getMinecraft16() {
        return Nuker.D;
    }
    
    public static float M(final Nuker nuker) {
        return nuker.j;
    }
    
    public static BlockPos M(final Nuker nuker, final BlockPos a) {
        return nuker.a = a;
    }
    
    private boolean M(final IBlockState blockState, final Block block) {
        if (!this.K.e(50L)) {
            return false;
        }
        if (Nuker.D.playerController == null) {
            return false;
        }
        if (this.flatten.M() && this.a.getY() < Nuker.D.player.posY) {
            return false;
        }
        if (this.raytrace.M() && !IG.e(this.a)) {
            return false;
        }
        if (this.k.contains(block)) {
            return false;
        }
        if (blockState.getBlockHardness((World)Nuker.D.world, this.a) == -1.0f && !Nuker.D.playerController.isInCreativeMode()) {
            return false;
        }
        switch (Aa.D[this.mode.M().ordinal()]) {
            case 2: {
                final Iterator<IBlockState> iterator = this.M.iterator();
                while (iterator.hasNext()) {
                    final IBlockState blockState2;
                    if ((blockState2 = iterator.next()).getBlock().equals(blockState.getBlock())) {
                        return !blockState2.getProperties().containsKey(BlockStone.VARIANT) || !blockState.getProperties().containsKey(BlockStone.VARIANT) || ((Comparable<?>)blockState2.getProperties().get(BlockStone.VARIANT)).equals(blockState.getProperties().get(BlockStone.VARIANT));
                    }
                }
            }
            case 3:
                return (block instanceof BlockCrops && ((BlockCrops)block).isMaxAge(blockState)) || (block instanceof BlockNetherWart && (int)blockState.getValue((IProperty)BlockNetherWart.AGE) >= 3) || (block instanceof BlockReed && Nuker.D.world.getBlockState(this.a.offset(EnumFacing.DOWN)).getBlock().equals(Blocks.REEDS) && !Nuker.D.world.getBlockState(this.a.offset(EnumFacing.DOWN, 2)).getBlock().equals(Blocks.REEDS));
            default:
                return true;
        }
    }
    
    public static boolean M(final Nuker nuker) {
        return nuker.d;
    }
    
    public static boolean M(final Nuker nuker, final IBlockState blockState, final Block block) {
        return nuker.M(blockState, block);
    }
    
    public static R M(final Nuker nuker) {
        return nuker.mode;
    }
    
    public static NumberValue M(final Nuker nuker) {
        return nuker.range;
    }
    
    public static BlockPos M(final Nuker nuker) {
        return nuker.a;
    }
    
    public static Minecraft getMinecraft17() {
        return Nuker.D;
    }
    
    public static Minecraft getMinecraft18() {
        return Nuker.D;
    }
}
