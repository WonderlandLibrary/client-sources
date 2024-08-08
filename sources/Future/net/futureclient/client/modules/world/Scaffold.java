package net.futureclient.client.modules.world;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemBlock;
import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.oh;
import net.futureclient.client.modules.world.scaffold.Listener5;
import net.futureclient.client.modules.world.scaffold.Listener4;
import net.futureclient.client.modules.world.scaffold.Listener3;
import net.futureclient.client.modules.world.scaffold.Listener2;
import net.futureclient.client.modules.world.scaffold.Listener1;
import net.futureclient.client.n;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Value;
import net.minecraft.block.Block;
import java.util.List;
import net.futureclient.client.uG;
import net.futureclient.client.ba;
import net.futureclient.client.R;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.Ea;

public class Scaffold extends Ea
{
    private boolean B;
    private Timer H;
    private NumberValue delay;
    private float L;
    private R<ba.Ka> mode;
    private float A;
    public uG j;
    private Timer K;
    public int M;
    private List<Block> d;
    private Value<Boolean> stopMotion;
    private List<Block> D;
    private Value<Boolean> tower;
    
    public static Minecraft getMinecraft() {
        return Scaffold.D;
    }
    
    public Scaffold() {
        super("Scaffold", new String[] { "Scaffold", "ScaffoldWalk", "Scaffo", "Tower" }, true, -6772395, Category.WORLD);
        this.tower = new Value<Boolean>(true, new String[] { "Tower", "Tow", "t" });
        this.stopMotion = new Value<Boolean>(false, new String[] { "StopMotion", "Motion", "AAC", "ac" });
        this.mode = new R<ba.Ka>(ba.Ka.a, new String[] { "Mode", "Mod", "Type", "m" });
        this.delay = new NumberValue(75.0f, 1.0f, 600.0f, 1, new String[] { "Delay", "Del", "d" });
        final int n = 6;
        this.K = new Timer();
        this.H = new Timer();
        final Block[] array = new Block[n];
        array[0] = Blocks.AIR;
        array[1] = (Block)Blocks.WATER;
        array[2] = (Block)Blocks.FIRE;
        array[3] = (Block)Blocks.FLOWING_WATER;
        array[4] = (Block)Blocks.LAVA;
        array[5] = (Block)Blocks.FLOWING_LAVA;
        this.d = Arrays.<Block>asList(array);
        this.D = Arrays.<Block>asList((Block)Blocks.LAVA, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.WATER, (Block)Blocks.FLOWING_WATER);
        final int n2 = 4;
        this.j = null;
        final Value[] array2 = new Value[n2];
        array2[0] = this.mode;
        array2[1] = this.tower;
        array2[2] = this.stopMotion;
        array2[3] = this.delay;
        this.M(array2);
        this.M(new n[] { (n)new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this), new Listener5(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return Scaffold.D;
    }
    
    public void B() {
        final uG j = null;
        super.B();
        this.j = j;
    }
    
    public static Minecraft getMinecraft2() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft9() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft10() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft12() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft14() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft15() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft16() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft17() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft18() {
        return Scaffold.D;
    }
    
    public void b() {
        Scaffold scaffold;
        if (Scaffold.D.inGameHasFocus) {
            oh.M(Scaffold.D.gameSettings.keyBindJump);
            scaffold = this;
        }
        else {
            KeyBinding.setKeyBindState(Scaffold.D.gameSettings.keyBindJump.getKeyCode(), false);
            scaffold = this;
        }
        scaffold.b();
    }
    
    public static Minecraft getMinecraft19() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft20() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft21() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft22() {
        return Scaffold.D;
    }
    
    public static Value e(final Scaffold scaffold) {
        return scaffold.stopMotion;
    }
    
    public static float e(final Scaffold scaffold, final float a) {
        return scaffold.A = a;
    }
    
    private int e() {
        if (Scaffold.D.player.inventory.getCurrentItem().getCount() != 0 && Scaffold.D.player.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
            return Scaffold.D.player.inventory.currentItem;
        }
        int i = 36;
        int n = 36;
        while (i < 45) {
            if (Scaffold.D.player.inventoryContainer.getSlot(n).getStack().getItem() instanceof ItemBlock) {
                return n - 36;
            }
            i = ++n;
        }
        return -1;
    }
    
    public static float e(final Scaffold scaffold) {
        return scaffold.A;
    }
    
    public static Timer e(final Scaffold scaffold) {
        return scaffold.H;
    }
    
    public static Minecraft getMinecraft23() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft24() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft25() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft26() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft27() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft28() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft29() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft30() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft31() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft32() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft33() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft34() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft35() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft36() {
        return Scaffold.D;
    }
    
    public static float M(final Scaffold scaffold, final float l) {
        return scaffold.L = l;
    }
    
    public static Value M(final Scaffold scaffold) {
        return scaffold.tower;
    }
    
    public static float M(final Scaffold scaffold) {
        return scaffold.L;
    }
    
    public static boolean M(final Scaffold scaffold, final boolean b) {
        return scaffold.B = b;
    }
    
    public static NumberValue M(final Scaffold scaffold) {
        return scaffold.delay;
    }
    
    public static List M(final Scaffold scaffold) {
        return scaffold.D;
    }
    
    private uG M(BlockPos add) {
        final List<Block> d = this.d;
        final WorldClient world = Scaffold.D.world;
        final BlockPos blockPos = add;
        final int n = -1;
        final int n2 = 0;
        if (!d.contains(world.getBlockState(blockPos.add(n2, n, n2)).getBlock())) {
            final BlockPos blockPos2 = add;
            final int n3 = -1;
            final int n4 = 0;
            return new uG(blockPos2.add(n4, n3, n4), EnumFacing.UP);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add.add(-1, 0, 0)).getBlock())) {
            return new uG(add.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add.add(1, 0, 0)).getBlock())) {
            return new uG(add.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add.add(0, 0, -1)).getBlock())) {
            return new uG(add.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add.add(0, 0, 1)).getBlock())) {
            return new uG(add.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add2 = add.add(-1, 0, 0);
        if (!this.d.contains(Scaffold.D.world.getBlockState(add2.add(-1, 0, 0)).getBlock())) {
            return new uG(add2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add2.add(1, 0, 0)).getBlock())) {
            return new uG(add2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add2.add(0, 0, -1)).getBlock())) {
            return new uG(add2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add2.add(0, 0, 1)).getBlock())) {
            return new uG(add2.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add3 = add.add(1, 0, 0);
        if (!this.d.contains(Scaffold.D.world.getBlockState(add3.add(-1, 0, 0)).getBlock())) {
            return new uG(add3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add3.add(1, 0, 0)).getBlock())) {
            return new uG(add3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add3.add(0, 0, -1)).getBlock())) {
            return new uG(add3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add3.add(0, 0, 1)).getBlock())) {
            return new uG(add3.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add4 = add.add(0, 0, -1);
        if (!this.d.contains(Scaffold.D.world.getBlockState(add4.add(-1, 0, 0)).getBlock())) {
            return new uG(add4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add4.add(1, 0, 0)).getBlock())) {
            return new uG(add4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add4.add(0, 0, -1)).getBlock())) {
            return new uG(add4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add4.add(0, 0, 1)).getBlock())) {
            return new uG(add4.add(0, 0, 1), EnumFacing.NORTH);
        }
        add = add.add(0, 0, 1);
        if (!this.d.contains(Scaffold.D.world.getBlockState(add.add(-1, 0, 0)).getBlock())) {
            return new uG(add.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add.add(1, 0, 0)).getBlock())) {
            return new uG(add.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add.add(0, 0, -1)).getBlock())) {
            return new uG(add.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.d.contains(Scaffold.D.world.getBlockState(add.add(0, 0, 1)).getBlock())) {
            return new uG(add.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }
    
    public static int M(final Scaffold scaffold) {
        return scaffold.e();
    }
    
    public static Timer M(final Scaffold scaffold) {
        return scaffold.K;
    }
    
    public static Minecraft getMinecraft37() {
        return Scaffold.D;
    }
    
    public static R M(final Scaffold scaffold) {
        return scaffold.mode;
    }
    
    public static boolean M(final Scaffold scaffold) {
        return scaffold.B;
    }
    
    public static uG M(final Scaffold scaffold, final BlockPos blockPos) {
        return scaffold.M(blockPos);
    }
    
    public static Minecraft getMinecraft38() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft39() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft40() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft41() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft42() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft43() {
        return Scaffold.D;
    }
    
    public static Minecraft getMinecraft44() {
        return Scaffold.D;
    }
}
