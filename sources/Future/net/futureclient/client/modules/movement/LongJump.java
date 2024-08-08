package net.futureclient.client.modules.movement;

import java.util.Iterator;
import java.util.List;
import net.minecraft.world.World;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.loader.mixin.common.wrapper.ITimer;
import net.futureclient.client.ZG;
import net.futureclient.client.modules.movement.longjump.Listener3;
import net.futureclient.client.modules.movement.longjump.Listener2;
import net.futureclient.client.modules.movement.longjump.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.hb;
import net.futureclient.client.R;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class LongJump extends Ea
{
    private Value<Boolean> miniJumps;
    private int B;
    private boolean H;
    private int l;
    private boolean L;
    private int E;
    private NumberValue boost;
    private double j;
    private int K;
    private double M;
    private double d;
    private int a;
    private Timer D;
    private R<hb.wc> mode;
    
    public static Minecraft getMinecraft() {
        return LongJump.D;
    }
    
    public LongJump() {
        super("LongJump", new String[] { "LongJump", "LJ" }, true, -15421042, Category.MOVEMENT);
        this.miniJumps = new Value<Boolean>(true, new String[] { "MiniJumps", "MiniJump", "SmallJumps", "HypixelJumps", "HypixelJump" });
        this.boost = new NumberValue(0.0, 0.0, 0.0, 1.273197475E-314, new String[] { "Boost", "Distance", "Length" });
        this.mode = new R<hb.wc>(hb.wc.a, new String[] { "Mode", "Type", "Method", "m", "mod" });
        final int n = 3;
        final double j = 1.1441801305E-314;
        this.D = new Timer();
        this.j = j;
        final Value[] array = new Value[n];
        array[0] = this.mode;
        array[1] = this.miniJumps;
        array[2] = this.boost;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this) });
    }
    
    public static int B(final LongJump longJump, final int e) {
        return longJump.E = e;
    }
    
    public static Minecraft getMinecraft1() {
        return LongJump.D;
    }
    
    public void B() {
        if (LongJump.D.player != null && LongJump.D.world != null) {
            this.j = ZG.e();
            LongJump.D.player.onGround = true;
            if (this.mode.M() == hb.wc.D) {
                ((ITimer)((IMinecraft)LongJump.D).getTimer()).setTimerSpeed(1.0f);
            }
        }
        final int a = 1;
        final double d = 0.0;
        final boolean l = true;
        this.H = false;
        this.L = l;
        this.d = d;
        this.a = a;
        super.B();
    }
    
    public static int B(final LongJump longJump) {
        return longJump.E;
    }
    
    public static int C(final LongJump longJump) {
        return --longJump.B;
    }
    
    public static Minecraft getMinecraft2() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft3() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft4() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft5() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft6() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft7() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft8() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft9() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft10() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft11() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft12() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft13() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft14() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft15() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft16() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft17() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft18() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft19() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft20() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft21() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft22() {
        return LongJump.D;
    }
    
    public static int b(final LongJump longJump) {
        return ++longJump.B;
    }
    
    public static int b(final LongJump longJump, final int k) {
        return longJump.K = k;
    }
    
    public static double b(final LongJump longJump) {
        return longJump.d;
    }
    
    public static double b(final LongJump longJump, final double j) {
        return longJump.j = j;
    }
    
    public void b() {
        if (this.mode.M() == hb.wc.D) {
            ((ITimer)((IMinecraft)LongJump.D).getTimer()).setTimerSpeed(1.0f);
            KeyBinding.setKeyBindState(LongJump.D.gameSettings.keyBindJump.getKeyCode(), false);
            LongJump.D.player.onGround = false;
            LongJump.D.player.capabilities.isFlying = false;
        }
        if (this.mode.M() == hb.wc.k) {
            final double d = 0.0;
            this.E = 4;
            this.d = d;
        }
        super.b();
    }
    
    public static Minecraft getMinecraft23() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft24() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft25() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft26() {
        return LongJump.D;
    }
    
    public static double e(final LongJump longJump, final double d) {
        return longJump.d = d;
    }
    
    public static Minecraft getMinecraft27() {
        return LongJump.D;
    }
    
    public static double e(final LongJump longJump) {
        return longJump.M;
    }
    
    public static int e(final LongJump longJump, final int a) {
        return longJump.a = a;
    }
    
    public static boolean e(final LongJump longJump, final boolean h) {
        return longJump.H = h;
    }
    
    public static int e(final LongJump longJump) {
        return longJump.l;
    }
    
    public static boolean e(final LongJump longJump) {
        return longJump.L;
    }
    
    public static Minecraft getMinecraft28() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft29() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft30() {
        return LongJump.D;
    }
    
    public static int i(final LongJump longJump) {
        return longJump.B;
    }
    
    public static Minecraft getMinecraft31() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft32() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft33() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft34() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft35() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft36() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft37() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft38() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft39() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft40() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft41() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft42() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft43() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft44() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft45() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft46() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft47() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft48() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft49() {
        return LongJump.D;
    }
    
    public static double M(final LongJump longJump, final EntityPlayer entityPlayer, final double n) {
        return longJump.M(entityPlayer, n);
    }
    
    public static NumberValue M(final LongJump longJump) {
        return longJump.boost;
    }
    
    private void M(final double n, final double n2, final double n3) {
        LongJump.D.player.connection.sendPacket((Packet)new CPacketPlayer.Position(n, n2, n3, LongJump.D.player.onGround));
    }
    
    public static double M(final LongJump longJump, final double m) {
        return longJump.M = m;
    }
    
    public static boolean M(final LongJump longJump, final boolean l) {
        return longJump.L = l;
    }
    
    public static Value M(final LongJump longJump) {
        return longJump.miniJumps;
    }
    
    public static Minecraft getMinecraft50() {
        return LongJump.D;
    }
    
    public static boolean M(final LongJump longJump) {
        return longJump.H;
    }
    
    public static int M(final LongJump longJump) {
        return longJump.a;
    }
    
    public static Timer M(final LongJump longJump) {
        return longJump.D;
    }
    
    public static R M(final LongJump longJump) {
        return longJump.mode;
    }
    
    private double M(final EntityPlayer entityPlayer, double maxY) {
        final World world = entityPlayer.world;
        final AxisAlignedBB entityBoundingBox = entityPlayer.getEntityBoundingBox();
        final double n = -maxY;
        final double n2 = 0.0;
        final List collisionBoxes;
        if ((collisionBoxes = world.getCollisionBoxes((Entity)entityPlayer, entityBoundingBox.grow(n2, n, n2))).isEmpty()) {
            return 0.0;
        }
        maxY = 0.0;
        final Iterator<AxisAlignedBB> iterator = collisionBoxes.iterator();
        while (iterator.hasNext()) {
            final AxisAlignedBB axisAlignedBB;
            if ((axisAlignedBB = iterator.next()).maxY > maxY) {
                maxY = axisAlignedBB.maxY;
            }
        }
        return entityPlayer.posY - maxY;
    }
    
    public static int M(final LongJump longJump, final int l) {
        return longJump.l = l;
    }
    
    public static double M(final LongJump longJump) {
        return longJump.j;
    }
    
    public static void M(final LongJump longJump, final double n, final double n2, final double n3) {
        longJump.M(n, n2, n3);
    }
    
    public static Minecraft getMinecraft51() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft52() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft53() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft54() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft55() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft56() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft57() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft58() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft59() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft60() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft61() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft62() {
        return LongJump.D;
    }
    
    public static int h(final LongJump longJump) {
        return longJump.K;
    }
    
    public static Minecraft getMinecraft63() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft64() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft65() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft66() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft67() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft68() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft69() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft70() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft71() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft72() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft73() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft74() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft75() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft76() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft77() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft78() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft79() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft80() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft81() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft82() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft83() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft84() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft85() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft86() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft87() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft88() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft89() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft90() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft91() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft92() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft93() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft94() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft95() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft96() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft97() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft98() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft99() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft100() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft101() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft102() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft103() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft104() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft105() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft106() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft107() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft108() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft109() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft110() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft111() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft112() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft113() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft114() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft115() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft116() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft117() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft118() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft119() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft120() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft121() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft122() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft123() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft124() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft125() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft126() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft127() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft128() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft129() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft130() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft131() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft132() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft133() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft134() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft135() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft136() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft137() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft138() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft139() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft140() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft141() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft142() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft143() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft144() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft145() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft146() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft147() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft148() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft149() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft150() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft151() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft152() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft153() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft154() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft155() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft156() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft157() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft158() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft159() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft160() {
        return LongJump.D;
    }
    
    public static Minecraft getMinecraft161() {
        return LongJump.D;
    }
}
