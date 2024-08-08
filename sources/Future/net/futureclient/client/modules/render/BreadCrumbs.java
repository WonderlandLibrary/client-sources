package net.futureclient.client.modules.render;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.render.breadcrumbs.Listener2;
import net.futureclient.client.modules.render.breadcrumbs.Listener1;
import net.futureclient.client.n;
import java.util.ArrayList;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.mG;
import java.util.List;
import net.futureclient.client.Ea;

public class BreadCrumbs extends Ea
{
    private List<mG> K;
    private final Timer M;
    private NumberValue width;
    private mG a;
    private NumberValue delay;
    private Value<Boolean> render;
    
    public BreadCrumbs() {
        super("BreadCrumbs", new String[] { "BreadCrumbs", "BreadMan", "BreadManCrumbs", "Breads", "BreadyCrumbs" }, true, -15011266, Category.RENDER);
        this.render = new Value<Boolean>(true, new String[] { "Render", "Draw", "r" });
        this.delay = new NumberValue(0.0f, 0.0f, 10.0f, 1.273197475E-314, new String[] { "Delay", "Del", "d" });
        this.width = new NumberValue(1.6f, 0.1f, 10.0f, 1.273197475E-314, new String[] { "Width", "With", "Radius", "raidus" });
        final int n = 3;
        this.K = new ArrayList<mG>();
        this.M = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.render;
        array[1] = this.width;
        array[2] = this.delay;
        this.M(array);
        this.M(new n[] { new Listener1(this), (n)new Listener2(this) });
    }
    
    public static Minecraft getMinecraft() {
        return BreadCrumbs.D;
    }
    
    public void B() {
        super.B();
    }
    
    public static Minecraft getMinecraft1() {
        return BreadCrumbs.D;
    }
    
    public static Minecraft getMinecraft2() {
        return BreadCrumbs.D;
    }
    
    public void b() {
        final mG a = null;
        super.b();
        this.a = a;
        this.K.clear();
    }
    
    public static Minecraft getMinecraft3() {
        return BreadCrumbs.D;
    }
    
    public static NumberValue e(final BreadCrumbs breadCrumbs) {
        return breadCrumbs.width;
    }
    
    public static Minecraft getMinecraft4() {
        return BreadCrumbs.D;
    }
    
    public static Minecraft getMinecraft5() {
        return BreadCrumbs.D;
    }
    
    public static Minecraft getMinecraft6() {
        return BreadCrumbs.D;
    }
    
    public static Minecraft getMinecraft7() {
        return BreadCrumbs.D;
    }
    
    public static List M(final BreadCrumbs breadCrumbs) {
        return breadCrumbs.K;
    }
    
    public static mG M(final BreadCrumbs breadCrumbs) {
        return breadCrumbs.a;
    }
    
    public static Minecraft getMinecraft8() {
        return BreadCrumbs.D;
    }
    
    public static NumberValue M(final BreadCrumbs breadCrumbs) {
        return breadCrumbs.delay;
    }
    
    public static Timer M(final BreadCrumbs breadCrumbs) {
        return breadCrumbs.M;
    }
    
    public static mG M(final BreadCrumbs breadCrumbs, final mG a) {
        return breadCrumbs.a = a;
    }
    
    public static Value M(final BreadCrumbs breadCrumbs) {
        return breadCrumbs.render;
    }
    
    public static Minecraft getMinecraft9() {
        return BreadCrumbs.D;
    }
}
