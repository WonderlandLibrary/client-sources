package net.futureclient.client.modules.miscellaneous;

import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.futureclient.client.ZG;
import net.minecraft.entity.Entity;
import net.futureclient.client.GH;
import net.futureclient.client.pg;
import net.futureclient.client.modules.miscellaneous.notifications.Listener4;
import net.futureclient.client.modules.miscellaneous.notifications.Listener3;
import net.futureclient.client.modules.miscellaneous.notifications.Listener2;
import net.futureclient.client.modules.miscellaneous.notifications.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import net.futureclient.client.FC;
import net.futureclient.client.R;
import net.futureclient.client.Zg;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Notifications extends Ea
{
    public Value<Boolean> showFriends;
    private Zg h;
    public Value<Boolean> damage;
    private static boolean J;
    private Zg F;
    private static boolean G;
    private R<FC.XC> mode;
    private Zg C;
    public Value<Boolean> stuck;
    public Value<Boolean> pm;
    private static int B;
    private Zg H;
    public Value<Boolean> kick;
    public Value<Boolean> name;
    public Value<Boolean> nearby;
    private static boolean A;
    public Value<Boolean> queue;
    private static boolean K;
    private ArrayList<EntityPlayer> M;
    private Zg d;
    private Zg a;
    private static boolean D;
    private Zg k;
    
    public static Minecraft getMinecraft() {
        return Notifications.D;
    }
    
    public Notifications() {
        super("Notifications", new String[] { "Notifications", "Notifcations", "Notify", "Notif" }, true, -5692121, Category.MISCELLANEOUS);
        this.mode = new R<FC.XC>(FC.XC.a, new String[] { "Mode", "Mod", "Method", "Time" });
        this.queue = new Value<Boolean>(true, new String[] { "Queue" });
        this.kick = new Value<Boolean>(true, new String[] { "Kick" });
        this.pm = new Value<Boolean>(true, new String[] { "PM" });
        this.name = new Value<Boolean>(true, new String[] { "Name" });
        this.stuck = new Value<Boolean>(true, new String[] { "Stuck" });
        this.damage = new Value<Boolean>(true, new String[] { "Damage" });
        this.nearby = new Value<Boolean>(true, new String[] { "Nearby" });
        this.showFriends = new Value<Boolean>(true, new String[] { "Show Friends", "ShowFriends", "HideFriends", "ShowFriends", "HF", "SF" });
        final int n = 9;
        this.F = new Zg();
        this.H = new Zg();
        this.a = new Zg();
        this.h = new Zg();
        this.d = new Zg();
        this.k = new Zg();
        this.C = new Zg();
        this.M = new ArrayList<EntityPlayer>();
        final Value[] array = new Value[n];
        array[0] = this.mode;
        array[1] = this.queue;
        array[2] = this.kick;
        array[3] = this.pm;
        array[4] = this.name;
        array[5] = this.stuck;
        array[6] = this.damage;
        array[7] = this.nearby;
        array[8] = this.showFriends;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this) });
    }
    
    static {
        Notifications.G = false;
        Notifications.B = 0;
    }
    
    public void B() {
        if (pg.M().M() == null) {
            pg.M().M(new GH());
        }
        Notifications.A = false;
        Notifications.J = false;
        Notifications.K = false;
        Notifications.D = false;
        Notifications.G = false;
        this.M.clear();
        if (Notifications.D.player != null && this.nearby.M()) {
            final Iterator<Entity> iterator = Notifications.D.world.playerEntities.iterator();
            while (iterator.hasNext()) {
                final EntityPlayer entityPlayer;
                if (ZG.b((Entity)(entityPlayer = (EntityPlayer)iterator.next())) && !this.M.contains(entityPlayer) && (this.showFriends.M() || !pg.M().M().M(entityPlayer.getName())) && !entityPlayer.getName().equals(Notifications.D.player.getName()) && !(entityPlayer instanceof EntityPlayerSP)) {
                    this.M.add(entityPlayer);
                }
            }
        }
        super.B();
    }
    
    public static boolean B() {
        return Notifications.A;
    }
    
    public static Minecraft getMinecraft1() {
        return Notifications.D;
    }
    
    public static Zg B(final Notifications notifications) {
        return notifications.C;
    }
    
    public static boolean B(final boolean a) {
        return Notifications.A = a;
    }
    
    public static boolean C(final boolean d) {
        return Notifications.D = d;
    }
    
    public static Zg C(final Notifications notifications) {
        return notifications.h;
    }
    
    public static boolean C() {
        return Notifications.G;
    }
    
    public static Minecraft getMinecraft2() {
        return Notifications.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Notifications.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Notifications.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Notifications.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Notifications.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Notifications.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Notifications.D;
    }
    
    public static int b() {
        return Notifications.B;
    }
    
    public static Zg b(final Notifications notifications) {
        return notifications.H;
    }
    
    public static boolean b(final boolean g) {
        return Notifications.G = g;
    }
    
    public void b() {
        if (pg.M().M() != null) {
            pg.M().M().D.remove(pg.M().M().k);
            pg.M().M((GH)null);
        }
        Notifications.A = false;
        Notifications.J = false;
        Notifications.K = false;
        Notifications.D = false;
        Notifications.G = false;
        this.M.clear();
        super.b();
    }
    
    public static boolean b() {
        return Notifications.J;
    }
    
    public static Minecraft getMinecraft9() {
        return Notifications.D;
    }
    
    public static int e() {
        return Notifications.B++;
    }
    
    public static Minecraft getMinecraft10() {
        return Notifications.D;
    }
    
    public static Zg e(final Notifications notifications) {
        return notifications.F;
    }
    
    public static boolean e(final boolean j) {
        return Notifications.J = j;
    }
    
    public static boolean i() {
        return Notifications.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Notifications.D;
    }
    
    public static Zg i(final Notifications notifications) {
        return notifications.d;
    }
    
    public static Minecraft getMinecraft12() {
        return Notifications.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Notifications.D;
    }
    
    public static Minecraft getMinecraft14() {
        return Notifications.D;
    }
    
    public static R M(final Notifications notifications) {
        return notifications.mode;
    }
    
    public static boolean M(final boolean k) {
        return Notifications.K = k;
    }
    
    public static int M(final int b) {
        return Notifications.B = b;
    }
    
    public static ArrayList M(final Notifications notifications) {
        return notifications.M;
    }
    
    public static Minecraft getMinecraft15() {
        return Notifications.D;
    }
    
    public static Zg M(final Notifications notifications) {
        return notifications.a;
    }
    
    public static Minecraft getMinecraft16() {
        return Notifications.D;
    }
    
    public static boolean h() {
        return Notifications.K;
    }
    
    public static Minecraft getMinecraft17() {
        return Notifications.D;
    }
    
    public static Zg h(final Notifications notifications) {
        return notifications.k;
    }
}
