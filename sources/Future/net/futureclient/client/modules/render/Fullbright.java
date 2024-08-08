package net.futureclient.client.modules.render;

import net.minecraft.init.MobEffects;
import net.futureclient.client.modules.render.fullbright.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.AE;
import net.minecraft.client.Minecraft;
import net.futureclient.client.tA;
import net.futureclient.client.R;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Fullbright extends Ea
{
    private Value<Boolean> automatic;
    private Timer D;
    private R<tA.Oc> k;
    
    public static Minecraft getMinecraft() {
        return Fullbright.D;
    }
    
    public Fullbright() {
        super("Fullbright", new String[] { AE.M(" 0\n)\u00047\u000f\"\u000e1"), "fb", AE.M("\u00047\u000f\"\u000e1"), "brightness" }, true, -23445, Category.RENDER);
        this.k = new R<tA.Oc>(tA.Oc.D, new String[] { AE.M("+*\u0002 "), "m", AE.M("\u0012<\u0016 ") });
        this.automatic = new Value<Boolean>(false, new String[] { "Automatic", AE.M("'0\u0012*#+\u0007'\n "), "Auto", AE.M("\u0004\u00131\t\u0000"), "AE" });
        final int n = 2;
        this.D = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.k;
        array[1] = this.automatic;
        this.M(array);
        this.M(new n[] { new Listener1(this) });
    }
    
    public void B() {
        if (!this.D.e(300L)) {
            this.automatic.M(false);
        }
        super.B();
    }
    
    public static Minecraft getMinecraft1() {
        return Fullbright.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Fullbright.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Fullbright.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Fullbright.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Fullbright.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Fullbright.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Fullbright.D;
    }
    
    public void b() {
        if (Fullbright.D.player != null && this.k.M().equals((Object)tA.Oc.a)) {
            Fullbright.D.gameSettings.gammaSetting = 1.0f;
            Fullbright.D.player.removePotionEffect(MobEffects.NIGHT_VISION);
            super.b();
        }
        this.D.e();
    }
    
    public static Minecraft getMinecraft8() {
        return Fullbright.D;
    }
    
    public static void e(final Fullbright fullbright) {
        fullbright.b();
    }
    
    public static Minecraft getMinecraft9() {
        return Fullbright.D;
    }
    
    public static Minecraft getMinecraft10() {
        return Fullbright.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Fullbright.D;
    }
    
    public static Minecraft getMinecraft12() {
        return Fullbright.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Fullbright.D;
    }
    
    public static void M(final Fullbright fullbright) {
        fullbright.b();
    }
    
    public static Timer M(final Fullbright fullbright) {
        return fullbright.D;
    }
    
    public static Value M(final Fullbright fullbright) {
        return fullbright.automatic;
    }
    
    public static R M(final Fullbright fullbright) {
        return fullbright.k;
    }
    
    public static Minecraft getMinecraft14() {
        return Fullbright.D;
    }
}
