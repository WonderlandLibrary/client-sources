package net.futureclient.client.modules.world;

import net.futureclient.client.modules.world.autotunnel.Listener2;
import net.futureclient.client.modules.world.autotunnel.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.Na;
import net.futureclient.client.R;
import net.futureclient.client.utils.NumberValue;
import net.minecraft.block.Block;
import java.util.List;
import net.futureclient.client.Ea;

public class AutoTunnel extends Ea
{
    private boolean H;
    private float l;
    private boolean L;
    private float E;
    private List<Block> A;
    private NumberValue delay;
    private R<Na.oa> mode;
    private Timer M;
    private EnumFacing d;
    private NumberValue height;
    private NumberValue width;
    private BlockPos k;
    
    public static Minecraft getMinecraft() {
        return AutoTunnel.D;
    }
    
    public AutoTunnel() {
        super("AutoTunnel", new String[] { "AutoTunnel", "AutoTunneler" }, true, -15641289, Category.WORLD);
        this.M = new Timer();
        this.width = new NumberValue(1.0f, 1.0f, 6.0f, 1, new String[] { "Width", "TunnelWidth", "W" });
        this.height = new NumberValue(2.0f, 1.0f, 6.0f, 1, new String[] { "Height", "TunnelHeight", "H" });
        this.delay = new NumberValue(0.2f, 0.0f, 1.0f, 5.941588215E-315, new String[] { "Delay", "Del", "D" });
        this.mode = new R<Na.oa>(Na.oa.a, new String[] { "Mode", "Method" });
        this.A = Arrays.<Block>asList(Blocks.AIR, (Block)Blocks.WATER, (Block)Blocks.FIRE, (Block)Blocks.FLOWING_WATER, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.PORTAL, Blocks.END_PORTAL, Blocks.END_PORTAL_FRAME, Blocks.BEDROCK);
        this.M(new Value[] { this.mode, this.delay });
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft2() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft3() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft4() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft5() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft6() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft7() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft8() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft9() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft10() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft11() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft12() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft13() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft14() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft15() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft16() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft17() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft18() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft19() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft20() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft21() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft22() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft23() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft24() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft25() {
        return AutoTunnel.D;
    }
    
    public static float e(final AutoTunnel autoTunnel) {
        return autoTunnel.l;
    }
    
    public static float e(final AutoTunnel autoTunnel, final float l) {
        return autoTunnel.l = l;
    }
    
    public static boolean e(final AutoTunnel autoTunnel, final boolean h) {
        return autoTunnel.H = h;
    }
    
    public static boolean e(final AutoTunnel autoTunnel) {
        return autoTunnel.L;
    }
    
    public static Minecraft getMinecraft26() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft27() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft28() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft29() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft30() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft31() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft32() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft33() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft34() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft35() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft36() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft37() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft38() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft39() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft40() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft41() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft42() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft43() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft44() {
        return AutoTunnel.D;
    }
    
    public static R M(final AutoTunnel autoTunnel) {
        return autoTunnel.mode;
    }
    
    public static Timer M(final AutoTunnel autoTunnel) {
        return autoTunnel.M;
    }
    
    public static EnumFacing M(final AutoTunnel autoTunnel, final EnumFacing d) {
        return autoTunnel.d = d;
    }
    
    public static NumberValue M(final AutoTunnel autoTunnel) {
        return autoTunnel.delay;
    }
    
    public static BlockPos M(final AutoTunnel autoTunnel) {
        return autoTunnel.k;
    }
    
    public static boolean M(final AutoTunnel autoTunnel) {
        return autoTunnel.H;
    }
    
    public static float M(final AutoTunnel autoTunnel) {
        return autoTunnel.E;
    }
    
    public static List M(final AutoTunnel autoTunnel) {
        return autoTunnel.A;
    }
    
    public static float M(final AutoTunnel autoTunnel, final float e) {
        return autoTunnel.E = e;
    }
    
    public static boolean M(final AutoTunnel autoTunnel, final boolean l) {
        return autoTunnel.L = l;
    }
    
    public static BlockPos M(final AutoTunnel autoTunnel, final BlockPos k) {
        return autoTunnel.k = k;
    }
    
    public static EnumFacing M(final AutoTunnel autoTunnel) {
        return autoTunnel.d;
    }
    
    public static Minecraft getMinecraft45() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft46() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft47() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft48() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft49() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft50() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft51() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft52() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft53() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft54() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft55() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft56() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft57() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft58() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft59() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft60() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft61() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft62() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft63() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft64() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft65() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft66() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft67() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft68() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft69() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft70() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft71() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft72() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft73() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft74() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft75() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft76() {
        return AutoTunnel.D;
    }
    
    public static Minecraft getMinecraft77() {
        return AutoTunnel.D;
    }
}
