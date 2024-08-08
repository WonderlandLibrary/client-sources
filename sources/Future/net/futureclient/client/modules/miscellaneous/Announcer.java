package net.futureclient.client.modules.miscellaneous;

import net.futureclient.client.s;
import net.futureclient.client.modules.miscellaneous.announcer.Listener7;
import net.futureclient.client.modules.miscellaneous.announcer.Listener6;
import net.futureclient.client.modules.miscellaneous.announcer.Listener5;
import net.futureclient.client.modules.miscellaneous.announcer.Listener4;
import net.futureclient.client.modules.miscellaneous.announcer.Listener3;
import net.futureclient.client.modules.miscellaneous.announcer.Listener2;
import net.futureclient.client.modules.miscellaneous.announcer.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Timer;
import java.util.HashMap;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Announcer extends Ea
{
    private Value<Boolean> place;
    private Value<Boolean> leave;
    private HashMap<String, Integer> e;
    private String[] J;
    private String[] F;
    private String[] G;
    private HashMap<String, Integer> i;
    private String[] C;
    private String[] g;
    private Timer I;
    private Value<Boolean> clientSideOnly;
    private Value<Boolean> worldTime;
    private Timer l;
    private Value<Boolean> break;
    private Value<Boolean> join;
    private Value<Boolean> food;
    private String[] j;
    private String[] K;
    private Timer M;
    private HashMap<String, Integer> d;
    private String[] a;
    private String[] D;
    private String[] k;
    
    public static Minecraft getMinecraft() {
        return Announcer.D;
    }
    
    public Announcer() {
        super("Announcer", new String[] { "Announcer", "Anounce", "Greeter", "Greet" }, true, -4191950, Category.MISCELLANEOUS);
        this.join = new Value<Boolean>(true, new String[] { "Join" });
        this.leave = new Value<Boolean>(true, new String[] { "Leave" });
        this.place = new Value<Boolean>(true, new String[] { "Place" });
        this.break = new Value<Boolean>(true, new String[] { "Break" });
        this.food = new Value<Boolean>(true, new String[] { "Food" });
        this.worldTime = new Value<Boolean>(true, new String[] { "WorldTime", "World", "WorldTimer", "TimeData", "Night", "Time" });
        this.clientSideOnly = new Value<Boolean>(false, new String[] { "ClientSideOnly", "ClientSide", "cso", "cs" });
        final int n = 7;
        this.d = new HashMap<String, Integer>();
        this.e = new HashMap<String, Integer>();
        this.i = new HashMap<String, Integer>();
        this.I = new Timer();
        this.M = new Timer();
        this.l = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.join;
        array[1] = this.leave;
        array[2] = this.food;
        array[3] = this.place;
        array[4] = this.break;
        array[5] = this.worldTime;
        array[6] = this.clientSideOnly;
        this.M(array);
        this.j = new String[] { "See you later, ", "Catch ya later, ", "See you next time, ", "Farewell, ", "Bye, ", "Good bye, ", "Later, " };
        this.K = new String[] { "Good to see you, ", "Greetings, ", "Hello, ", "Howdy, ", "Hey, ", "Good evening, ", "Welcome to SERVERIP1D5A9E, " };
        this.J = new String[] { "Good morning!", "Top of the morning to you!", "Good day!", "You survived another night!", "Good morning everyone!", "The sun is rising in the east, hurrah, hurrah!" };
        this.D = new String[] { "Let's go tanning!", "Let's go to the beach!", "Grab your sunglasses!", "Enjoy the sun outside! It is currently very bright!", "It's the brightest time of the day!" };
        this.g = new String[] { "Good afternoon!", "Let's grab lunch!", "Lunch time, kids!", "Good afternoon everyone!", "IT'S HIGH NOON!" };
        this.a = new String[] { "Happy hour!", "Let's get crunk!", "Enjoy the sunset everyone!" };
        this.C = new String[] { "Let's get comfy!", "Netflix and chill!", "You survived another day!", "Time to go to bed kids!" };
        this.G = new String[] { "Sunset has now ended! You may eat your lunch now if you are a muslim." };
        this.F = new String[] { "It's so dark outside...", "It's the opposite of noon!" };
        this.k = new String[] { "Good bye, zombies!", "Monsters are now burning!", "Burn baby, burn!" };
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this), new Listener5(this), new Listener6(this), new Listener7(this) });
    }
    
    public static Value B(final Announcer announcer) {
        return announcer.leave;
    }
    
    public void B() {
        this.d.clear();
        this.e.clear();
        this.i.clear();
        super.B();
    }
    
    public static String[] B(final Announcer announcer) {
        return announcer.G;
    }
    
    public static Minecraft getMinecraft1() {
        return Announcer.D;
    }
    
    public static Value C(final Announcer announcer) {
        return announcer.break;
    }
    
    public static Minecraft getMinecraft2() {
        return Announcer.D;
    }
    
    public static String[] C(final Announcer announcer) {
        return announcer.g;
    }
    
    public static Minecraft getMinecraft3() {
        return Announcer.D;
    }
    
    public static String[] c(final Announcer announcer) {
        return announcer.J;
    }
    
    public static Minecraft getMinecraft4() {
        return Announcer.D;
    }
    
    public void b() {
        this.d.clear();
        this.e.clear();
        this.i.clear();
        super.b();
    }
    
    public static String[] b(final Announcer announcer) {
        return announcer.C;
    }
    
    public static Value b(final Announcer announcer) {
        return announcer.join;
    }
    
    public static Timer b(final Announcer announcer) {
        return announcer.I;
    }
    
    public static HashMap b(final Announcer announcer) {
        return announcer.i;
    }
    
    private void b(final String s) {
        if (this.clientSideOnly.M()) {
            s.M().M(s);
            return;
        }
        Announcer.D.player.sendChatMessage(new StringBuilder().insert(0, "> ").append(s).toString());
    }
    
    public static Minecraft getMinecraft5() {
        return Announcer.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Announcer.D;
    }
    
    public static Value e(final Announcer announcer) {
        return announcer.worldTime;
    }
    
    public static Minecraft getMinecraft7() {
        return Announcer.D;
    }
    
    public static Timer e(final Announcer announcer) {
        return announcer.M;
    }
    
    public static String[] e(final Announcer announcer) {
        return announcer.j;
    }
    
    public static HashMap e(final Announcer announcer) {
        return announcer.d;
    }
    
    public static Value i(final Announcer announcer) {
        return announcer.food;
    }
    
    public static Minecraft getMinecraft8() {
        return Announcer.D;
    }
    
    public static String[] i(final Announcer announcer) {
        return announcer.F;
    }
    
    public static String[] g(final Announcer announcer) {
        return announcer.a;
    }
    
    public static Minecraft getMinecraft9() {
        return Announcer.D;
    }
    
    public static String[] K(final Announcer announcer) {
        return announcer.K;
    }
    
    public static Minecraft getMinecraft10() {
        return Announcer.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Announcer.D;
    }
    
    public static String[] M(final Announcer announcer) {
        return announcer.k;
    }
    
    public static void M(final Announcer announcer, final String s) {
        announcer.b(s);
    }
    
    public static HashMap M(final Announcer announcer) {
        return announcer.e;
    }
    
    public static Timer M(final Announcer announcer) {
        return announcer.l;
    }
    
    public static Value M(final Announcer announcer) {
        return announcer.place;
    }
    
    public static Minecraft getMinecraft12() {
        return Announcer.D;
    }
    
    public static String[] h(final Announcer announcer) {
        return announcer.D;
    }
}
