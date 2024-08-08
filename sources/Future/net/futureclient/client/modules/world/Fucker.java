package net.futureclient.client.modules.world;

import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.init.Items;
import net.futureclient.client.ZG;
import net.minecraft.item.ItemHoe;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockBed;
import net.futureclient.client.Oa;
import net.futureclient.client.IG;
import net.minecraft.entity.Entity;
import java.util.HashSet;
import java.util.LinkedList;
import net.futureclient.client.modules.world.fucker.Listener2;
import net.futureclient.client.modules.world.fucker.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.futureclient.client.utils.Timer;
import net.minecraft.util.EnumFacing;
import net.futureclient.client.va;
import net.futureclient.client.R;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class Fucker extends Ea
{
    private byte g;
    private NumberValue stopRange;
    private Value<Boolean> throughWalls;
    private float H;
    private R<va.Ca> mode;
    private Value<Boolean> rotate;
    private NumberValue range;
    private EnumFacing A;
    private Timer j;
    private float K;
    private Value<Boolean> instantBreak;
    private Value<Boolean> rightClick;
    private float a;
    private NumberValue breakSpeed;
    private BlockPos k;
    
    public static Minecraft getMinecraft() {
        return Fucker.D;
    }
    
    public Fucker() {
        super("Fucker", new String[] { "Fucker", "BedFucker", "EggFucker", "BlockRape" }, true, -2271915, Category.WORLD);
        this.mode = new R<va.Ca>(va.Ca.M, new String[] { "Mode", "Block", "Target", "Bloach", "Blcok", "Blok", "b" });
        this.throughWalls = new Value<Boolean>(false, new String[] { "Through Walls", "ThroughWalls", "RayTrace", "RayTracing", "NCP" });
        this.rotate = new Value<Boolean>(true, new String[] { "Rotate", "Aiming", "Aim", "Rotation", "Facing", "Face", "F", "Look" });
        this.rightClick = new Value<Boolean>(false, new String[] { "Right Click", "RightClick", "RightClicking", "RClick" });
        this.instantBreak = new Value<Boolean>(true, new String[] { "Instant Break", "InstantBreak", "InstaBreak", "IB", "FastBreak", "Break", "IB" });
        this.range = new NumberValue(4.0f, 0.1f, 6.5f, 1.273197475E-314, new String[] { "Range", "Reach", "R" });
        this.stopRange = new NumberValue(6.0f, 0.1f, 8.0f, 1.273197475E-314, new String[] { "StopRange", "Stop", "SR" });
        this.breakSpeed = new NumberValue(1.0f, 0.0f, 10.0f, 1.273197475E-314, new String[] { "BreakSpeed", "Speed", "BS" });
        final int n = 8;
        this.j = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.mode;
        array[1] = this.throughWalls;
        array[2] = this.rotate;
        array[3] = this.rightClick;
        array[4] = this.range;
        array[5] = this.stopRange;
        array[6] = this.instantBreak;
        array[7] = this.breakSpeed;
        this.M(array);
        final int n2 = 2;
        final byte g = 0;
        this.A = EnumFacing.UP;
        this.g = g;
        final n[] array2 = new n[n2];
        array2[0] = new Listener1(this);
        array2[1] = new Listener2(this);
        this.M(array2);
    }
    
    public static Minecraft getMinecraft1() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Fucker.D;
    }
    
    public static float b(final Fucker fucker, final float a) {
        return fucker.a = a;
    }
    
    public static float b(final Fucker fucker) {
        return fucker.H;
    }
    
    public static Value b(final Fucker fucker) {
        return fucker.rotate;
    }
    
    public static Minecraft getMinecraft9() {
        return Fucker.D;
    }
    
    public static byte e(final Fucker fucker) {
        return fucker.g;
    }
    
    public static Value e(final Fucker fucker) {
        return fucker.rightClick;
    }
    
    public static Minecraft getMinecraft10() {
        return Fucker.D;
    }
    
    public static NumberValue e(final Fucker fucker) {
        return fucker.stopRange;
    }
    
    public static BlockPos e(final Fucker fucker) {
        return fucker.M();
    }
    
    public static float e(final Fucker fucker) {
        return fucker.a;
    }
    
    public static float e(final Fucker fucker, final float h) {
        return fucker.H = h;
    }
    
    public static Minecraft getMinecraft11() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft12() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft14() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft15() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft16() {
        return Fucker.D;
    }
    
    public static byte M(final Fucker fucker) {
        return (byte)(--fucker.g);
    }
    
    public static float M(final Fucker fucker, final float k) {
        return fucker.K = k;
    }
    
    public static Value M(final Fucker fucker) {
        return fucker.instantBreak;
    }
    
    public static BlockPos M(final Fucker fucker, final BlockPos k) {
        return fucker.k = k;
    }
    
    public static Minecraft getMinecraft17() {
        return Fucker.D;
    }
    
    private BlockPos M() {
        final LinkedList<BlockPos> list = new LinkedList<BlockPos>();
        final HashSet<BlockPos> set = new HashSet<BlockPos>();
        list.add(new BlockPos((Entity)Fucker.D.player));
        BlockPos blockPos = null;
    Label_0396:
        while (true) {
            LinkedList<BlockPos> list2 = list;
            while (!list2.isEmpty()) {
                blockPos = list.poll();
                if (set.contains(blockPos)) {
                    list2 = list;
                }
                else {
                    set.add(blockPos);
                    if (blockPos == null) {
                        continue Label_0396;
                    }
                    if (IG.M(blockPos) > this.range.B().floatValue() * this.range.B().floatValue()) {
                        list2 = list;
                    }
                    else {
                        switch (Oa.k[this.mode.M().ordinal()]) {
                            case 1:
                                if (Fucker.D.world.getBlockState(blockPos).getBlock() instanceof BlockBed) {
                                    return blockPos;
                                }
                                break;
                            case 2:
                                if (Fucker.D.world.getBlockState(blockPos).getBlock() instanceof BlockDragonEgg) {
                                    return blockPos;
                                }
                                break;
                            case 3:
                                if (Fucker.D.world.getBlockState(blockPos).getBlock() instanceof BlockCake) {
                                    return blockPos;
                                }
                                break;
                            case 4:
                                if (!Fucker.D.world.getBlockState(blockPos.offset(EnumFacing.UP)).getBlock().equals(Blocks.AIR)) {
                                    break;
                                }
                                if (ZG.M((Class)ItemHoe.class) != null) {
                                    if (Fucker.D.world.getBlockState(blockPos).getBlock().equals(Blocks.DIRT)) {
                                        break Label_0396;
                                    }
                                    if (Fucker.D.world.getBlockState(blockPos).getBlock().equals(Blocks.GRASS)) {
                                        return blockPos;
                                    }
                                    break;
                                }
                                else if (ZG.M(Items.NETHER_WART) != null) {
                                    if (Fucker.D.world.getBlockState(blockPos).getBlock().equals(Blocks.SOUL_SAND)) {
                                        return blockPos;
                                    }
                                    break;
                                }
                                else {
                                    if ((ZG.M((Class)ItemSeeds.class) != null || ZG.M((Class)ItemSeedFood.class) != null) && Fucker.D.world.getBlockState(blockPos).getBlock().equals(Blocks.FARMLAND)) {
                                        break Label_0396;
                                    }
                                    break;
                                }
                                break;
                        }
                        if (!this.throughWalls.M() && !IG.e(blockPos)) {
                            list2 = list;
                        }
                        else {
                            (list2 = list).add(blockPos.north());
                            list.add(blockPos.south());
                            list.add(blockPos.west());
                            list.add(blockPos.east());
                            list.add(blockPos.down());
                            list.add(blockPos.up());
                        }
                    }
                }
            }
            return null;
        }
        return blockPos;
    }
    
    public static BlockPos M(final Fucker fucker) {
        return fucker.k;
    }
    
    public static R M(final Fucker fucker) {
        return fucker.mode;
    }
    
    public static NumberValue M(final Fucker fucker) {
        return fucker.breakSpeed;
    }
    
    public static byte M(final Fucker fucker, final byte g) {
        return fucker.g = g;
    }
    
    public static EnumFacing M(final Fucker fucker) {
        return fucker.A;
    }
    
    public static float M(final Fucker fucker) {
        return fucker.K;
    }
    
    public static Timer M(final Fucker fucker) {
        return fucker.j;
    }
    
    public static Minecraft getMinecraft18() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft19() {
        return Fucker.D;
    }
    
    public static Minecraft getMinecraft20() {
        return Fucker.D;
    }
}
