package net.futureclient.client.modules.render;

import net.minecraft.entity.item.EntityItem;
import net.futureclient.client.WI;
import net.futureclient.client.pg;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.render.tracers.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.mh;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.wb;
import net.futureclient.client.R;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Tracers extends Ea
{
    private Value<Boolean> H;
    private R<wb.qd> target;
    private Value<Boolean> players;
    private Value<Boolean> E;
    private Value<Boolean> A;
    private Value<Boolean> j;
    private R<wb.CC> K;
    private Value<Boolean> M;
    private Value<Boolean> d;
    private NumberValue a;
    private Value<Boolean> animals;
    private Value<Boolean> k;
    
    public Tracers() {
        super("Tracers", new String[] { mh.M("\n2?#;2-"), "trace", mh.M("4,!=%,") }, false, -38037, Category.RENDER);
        this.players = new Value<Boolean>(true, new String[] { "Players", mh.M("\u00102!'%,"), "Mans", mh.M("\b+-?.-"), "p" });
        this.j = new Value<Boolean>(true, new String[] { mh.M("\u001827%0$-"), "Friend", mh.M("\n%?-3!*%-"), "Teammate" });
        this.M = new Value<Boolean>(false, new String[] { mh.M("\t06737\"2%-"), "invis", mh.M("7.("), "i" });
        this.E = new Value<Boolean>(false, new String[] { mh.M("\r1.-4;2-"), "Mobs", mh.M("\u0013/<") });
        this.animals = new Value<Boolean>(false, new String[] { "Animals", mh.M("\u001f.?-?,-"), "Anims" });
        this.k = new Value<Boolean>(false, new String[] { mh.M("\u0016;(7#2%-"), "Boat", mh.M("\u001c/?4-"), "Minecarts", mh.M("\r7.;#?2*"), "V" });
        this.A = new Value<Boolean>(false, new String[] { mh.M("\u00174;--"), "Item", mh.M("\u0007;--"), "aitems", mh.M(")*--"), "tems" });
        this.d = new Value<Boolean>(false, new String[] { mh.M("\u000f*(;2-"), "Other", mh.M("\u0013)-#;,2!0%15-"), "Misc", mh.M("\r73=%2,?.;5-"), "M" });
        this.H = new Value<Boolean>(true, new String[] { mh.M("\u0012)0%-"), "line", mh.M("2") });
        this.target = new R<wb.qd>(wb.qd.k, new String[] { "Target", mh.M("\u0014'0;"), "t" });
        this.K = new R<wb.CC>(wb.CC.D, new String[] { mh.M("\u0004;37'0"), "Box", mh.M(":"), "type" });
        this.a = new NumberValue(1.6f, 0.1f, 10.0f, 1.273197475E-314, new String[] { mh.M("\t):46"), "Radius", mh.M("2?):5-") });
        this.M(new Value[] { this.players, this.j, this.M, this.E, this.animals, this.k, this.A, this.d, this.target, this.K, this.H, this.a });
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return Tracers.D;
    }
    
    public static Minecraft getMinecraft1() {
        return Tracers.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Tracers.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Tracers.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Tracers.D;
    }
    
    public static R e(final Tracers tracers) {
        return tracers.K;
    }
    
    public static Minecraft getMinecraft5() {
        return Tracers.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Tracers.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Tracers.D;
    }
    
    public static R M(final Tracers tracers) {
        return tracers.target;
    }
    
    public static NumberValue M(final Tracers tracers) {
        return tracers.a;
    }
    
    public static Value M(final Tracers tracers) {
        return tracers.H;
    }
    
    public static boolean M(final Tracers tracers, final Entity entity) {
        return tracers.M(entity);
    }
    
    private boolean M(final Entity entity) {
        return entity != null && entity != Tracers.D.player && !entity.equals((Object)Tracers.D.player.getRidingEntity()) && ((this.players.M() && entity instanceof EntityPlayer && (this.M.M() || !entity.isInvisible()) && (this.j.M() || !pg.M().M().M(entity.getName()))) || (this.E.M() && (WI.C(entity) || WI.e(entity))) || (this.animals.M() && (WI.i(entity) || WI.g(entity))) || (this.k.M() && WI.H(entity)) || (this.d.M() && WI.K(entity)) || (this.A.M() && entity instanceof EntityItem));
    }
    
    public static Minecraft getMinecraft8() {
        return Tracers.D;
    }
}
