package net.futureclient.client.modules.movement;

import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.loader.mixin.common.wrapper.ITimer;
import net.futureclient.client.Bc;
import java.util.List;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.futureclient.client.IG;
import net.minecraft.entity.Entity;
import net.futureclient.client.pg;
import net.futureclient.client.yb;
import net.futureclient.client.ZG;
import net.futureclient.client.modules.movement.speed.Listener5;
import net.futureclient.client.modules.movement.speed.Listener4;
import net.futureclient.client.modules.movement.speed.Listener3;
import net.futureclient.client.modules.movement.speed.Listener2;
import net.futureclient.client.modules.movement.speed.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.db;
import net.futureclient.client.R;
import net.futureclient.client.Ea;

public class Speed extends Ea
{
    private int C;
    public R<db.fC> mode;
    private boolean I;
    private Timer B;
    public Value<Boolean> speedInWater;
    private int l;
    private boolean L;
    private boolean E;
    private int A;
    public Value<Boolean> useTimer;
    public Value<Boolean> autoSprint;
    private double M;
    private int d;
    private int a;
    private double D;
    private int k;
    
    public static Minecraft getMinecraft() {
        return Speed.D;
    }
    
    public Speed() {
        super("Speed", new String[] { "Speed", "Sped", "fastrun" }, true, -13131324, Category.MOVEMENT);
        this.E = false;
        this.speedInWater = new Value<Boolean>(false, new String[] { "SpeedInWater", "Water", "waterspeed", "watermove", "stopinwater" });
        this.autoSprint = new Value<Boolean>(true, new String[] { "AutoSprint", "as", "sprint" });
        this.useTimer = new Value<Boolean>(true, new String[] { "UseTimer", "Timer", "TimerSpeed", "UseTim" });
        this.mode = new R<db.fC>(db.fC.a, new String[] { "Mode", "m", "NCP", "AAC", "NCPSpeed", "NCPMode" });
        final int n = 4;
        this.B = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.autoSprint;
        array[1] = this.mode;
        array[2] = this.speedInWater;
        array[3] = this.useTimer;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this), new Listener5(this) });
    }
    
    public void B() {
        super.B();
        if (Speed.D.player != null) {
            this.D = ZG.e();
        }
        final int l = 4;
        final int k = 2;
        final int c = 1;
        final int a = 1;
        final int d = 4;
        final double m = 0.0;
        this.A = 2;
        this.M = m;
        this.d = d;
        this.a = a;
        this.C = c;
        this.k = k;
        this.l = l;
    }
    
    public static boolean B(final Speed speed) {
        return speed.L;
    }
    
    public static Minecraft getMinecraft1() {
        return Speed.D;
    }
    
    public static int B(final Speed speed, final int d) {
        return speed.d = d;
    }
    
    private boolean B() {
        return !Speed.D.player.collidedHorizontally && Speed.D.player.getFoodStats().getFoodLevel() > 6 && Speed.D.player.moveForward > 0.0f;
    }
    
    public static int B(final Speed speed) {
        return speed.l;
    }
    
    public static int C(final Speed speed, final int k) {
        return speed.k = k;
    }
    
    public static int C(final Speed speed) {
        return ++speed.a;
    }
    
    private boolean C() {
        final yb yb = (yb)pg.M().M().M((Class)yb.class);
        final WorldClient world = Speed.D.world;
        final EntityPlayerSP player = Speed.D.player;
        final AxisAlignedBB entityBoundingBox = Speed.D.player.getEntityBoundingBox();
        final double n = 0.0;
        final double n2 = 1.273197475E-314;
        final List collisionBoxes = world.getCollisionBoxes((Entity)player, entityBoundingBox.grow(n2, n, n2));
        if (yb != null && yb.M() && !collisionBoxes.isEmpty()) {}
        return Speed.D.player.onGround && !IG.e() && !IG.M(true);
    }
    
    public static boolean C(final Speed speed) {
        return speed.I;
    }
    
    public static Minecraft getMinecraft2() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft9() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft10() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Speed.D;
    }
    
    public static int c(final Speed speed) {
        return speed.A;
    }
    
    public static Minecraft getMinecraft12() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft14() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft15() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft16() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft17() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft18() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft19() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft20() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft21() {
        return Speed.D;
    }
    
    public static boolean b(final Speed speed) {
        return speed.B();
    }
    
    public static int b(final Speed speed, final int a) {
        return speed.A = a;
    }
    
    public void b() {
        final net.futureclient.client.modules.miscellaneous.Timer timer;
        if ((timer = (net.futureclient.client.modules.miscellaneous.Timer)pg.M().M().M((Class)Bc.class)) != null && !timer.M()) {
            ((ITimer)((IMinecraft)Speed.D).getTimer()).setTimerSpeed(1.0f);
        }
        super.b();
    }
    
    private boolean b() {
        return !Speed.D.gameSettings.keyBindForward.isKeyDown() && !Speed.D.gameSettings.keyBindBack.isKeyDown() && !Speed.D.gameSettings.keyBindRight.isKeyDown() && !Speed.D.gameSettings.keyBindLeft.isKeyDown();
    }
    
    public static Minecraft getMinecraft22() {
        return Speed.D;
    }
    
    public static boolean b(final Speed speed, final boolean l) {
        return speed.L = l;
    }
    
    public static int b(final Speed speed) {
        return speed.d;
    }
    
    public static Minecraft getMinecraft23() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft24() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft25() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft26() {
        return Speed.D;
    }
    
    public static int e(final Speed speed) {
        return speed.a;
    }
    
    public static double e(final Speed speed, final double m) {
        return speed.M = m;
    }
    
    public static Minecraft getMinecraft27() {
        return Speed.D;
    }
    
    public static double e(final Speed speed) {
        return speed.M;
    }
    
    public static int e(final Speed speed, final int c) {
        return speed.C = c;
    }
    
    public static boolean e(final Speed speed, final boolean i) {
        return speed.I = i;
    }
    
    public static boolean e(final Speed speed) {
        return speed.b();
    }
    
    public static Minecraft getMinecraft28() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft29() {
        return Speed.D;
    }
    
    public static int i(final Speed speed) {
        return speed.C;
    }
    
    public static int i(final Speed speed, final int l) {
        return speed.l = l;
    }
    
    public static boolean i(final Speed speed) {
        return speed.C();
    }
    
    public static Minecraft getMinecraft30() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft31() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft32() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft33() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft34() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft35() {
        return Speed.D;
    }
    
    public static int g(final Speed speed) {
        return ++speed.l;
    }
    
    public static Minecraft getMinecraft36() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft37() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft38() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft39() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft40() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft41() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft42() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft43() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft44() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft45() {
        return Speed.D;
    }
    
    public static int K(final Speed speed) {
        return speed.k;
    }
    
    public static Minecraft getMinecraft46() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft47() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft48() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft49() {
        return Speed.D;
    }
    
    public static Timer M(final Speed speed) {
        return speed.B;
    }
    
    public static Minecraft getMinecraft50() {
        return Speed.D;
    }
    
    public static int M(final Speed speed) {
        return ++speed.C;
    }
    
    public static int M(final Speed speed, final int a) {
        return speed.a = a;
    }
    
    public static boolean M(final Speed speed, final boolean e) {
        return speed.E = e;
    }
    
    public static double M(final Speed speed, final double d) {
        return speed.D = d;
    }
    
    public static boolean M(final Speed speed) {
        return speed.E;
    }
    
    public static double M(final Speed speed) {
        return speed.D;
    }
    
    public static Minecraft getMinecraft51() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft52() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft53() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft54() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft55() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft56() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft57() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft58() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft59() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft60() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft61() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft62() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft63() {
        return Speed.D;
    }
    
    public static int h(final Speed speed) {
        return ++speed.d;
    }
    
    public static Minecraft getMinecraft64() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft65() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft66() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft67() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft68() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft69() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft70() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft71() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft72() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft73() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft74() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft75() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft76() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft77() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft78() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft79() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft80() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft81() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft82() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft83() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft84() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft85() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft86() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft87() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft88() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft89() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft90() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft91() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft92() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft93() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft94() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft95() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft96() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft97() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft98() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft99() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft100() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft101() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft102() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft103() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft104() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft105() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft106() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft107() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft108() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft109() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft110() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft111() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft112() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft113() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft114() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft115() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft116() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft117() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft118() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft119() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft120() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft121() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft122() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft123() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft124() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft125() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft126() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft127() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft128() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft129() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft130() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft131() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft132() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft133() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft134() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft135() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft136() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft137() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft138() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft139() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft140() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft141() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft142() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft143() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft144() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft145() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft146() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft147() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft148() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft149() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft150() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft151() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft152() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft153() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft154() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft155() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft156() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft157() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft158() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft159() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft160() {
        return Speed.D;
    }
    
    public static Minecraft getMinecraft161() {
        return Speed.D;
    }
}
