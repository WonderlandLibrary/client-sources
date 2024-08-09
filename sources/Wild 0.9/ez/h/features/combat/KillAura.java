package ez.h.features.combat;

import ez.h.ui.hudeditor.registry.*;
import ez.h.features.another.*;
import ez.h.*;
import ez.h.managers.*;
import java.util.stream.*;
import ez.h.event.*;
import ez.h.utils.*;
import java.util.*;
import java.util.concurrent.*;
import org.lwjgl.input.*;
import ez.h.event.events.*;
import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import ez.h.animengine.*;
import org.lwjgl.opengl.*;

public class KillAura extends Feature
{
    public static OptionColor barColor;
    public static OptionSlider range;
    OptionMode rotations;
    public static OptionBoolean resolver;
    OptionSlider fov;
    Counter shieldCounter;
    OptionBoolean invisible;
    OptionMode cpsMode;
    public float serverPitch;
    float espY;
    double lastCPS;
    OptionBoolean targetESP;
    OptionBoolean autoShield;
    Counter shieldFixerTimer;
    public static aed target;
    OptionBoolean adaptiveCrits;
    float easeScale;
    OptionSlider jitterStrength;
    OptionMode side;
    final Counter cpsTimeHelper;
    OptionMode colorMode;
    public OptionBoolean moveFix;
    public float serverYaw;
    OptionBoolean raytrace;
    OptionBoolean shieldbreak;
    OptionSlider hitchance;
    OptionColor colorESP;
    Counter timer;
    OptionBoolean showRange;
    TargetHUD targetHUDComponent;
    OptionBoolean shieldDesyncer;
    OptionBoolean betterCrits;
    float factor;
    OptionMode crits;
    public static OptionBoolean interactFix;
    boolean isBlocking;
    OptionMode pvp;
    float prevEspY;
    int desyncTicks;
    public static OptionBoolean targethud;
    OptionSlider cps;
    public static OptionMode sorting;
    OptionMode jitter;
    boolean wasCPSDrop;
    
    @Override
    public void updateElements() {
        this.crits.display = this.pvp.isMode("1.9+");
        this.cps.display = this.pvp.isMode("1.8");
        this.cpsMode.display = this.pvp.isMode("1.8");
        KillAura.barColor.display = KillAura.targethud.enabled;
        this.colorESP.display = (this.targetESP.enabled && this.colorMode.isMode("Custom"));
        this.jitter.display = (!this.rotations.isMode("ReallyWorld") && !this.rotations.isMode("Nexus"));
        this.jitterStrength.display = (!this.jitter.isMode("None") && this.jitter.display);
        this.adaptiveCrits.display = (this.pvp.isMode("1.9+") && this.crits.isMode("Smart"));
        this.colorMode.display = this.targetESP.enabled;
    }
    
    void fixRotations(final EventMotion eventMotion) {
        eventMotion.pitch -= eventMotion.pitch % MathUtils.Rotations.getGCD();
        if (this.rotations.isMode("ReallyWorld")) {
            eventMotion.yaw *= (float)1.0001;
        }
        else {
            eventMotion.yaw -= eventMotion.yaw % MathUtils.Rotations.getGCD();
        }
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.rotations.getMode() + " " + KillAura.range.getNum() + " " + this.pvp.getMode());
        if (!this.isEnabled()) {
            return;
        }
        final AntiBot antiBot;
        final List list = (List)KillAura.mc.f.i.stream().filter(aed3 -> {
            antiBot = (AntiBot)Main.getFeatureByName("AntiBot");
            return !antiBot.isEnabled() || antiBot.remove.enabled || !antiBot.isBot(aed3);
        }).filter(bud -> bud != KillAura.mc.h).filter(aed -> !aed.F && aed.cd() > 0.0f).filter(aed4 -> this.invisible.enabled || !aed4.aX()).filter(aed2 -> !FriendManager.isFriend(aed2.h_())).filter(vg -> (!KillAura.sorting.isMode("Health") && !KillAura.sorting.isMode("Crosshair")) || KillAura.mc.h.getDistance(vg) <= KillAura.range.getNum()).collect(Collectors.toList());
        if (KillAura.sorting.isMode("Health")) {
            list.sort(Comparator.comparingDouble(vp::cd));
        }
        if (KillAura.sorting.isMode("Distance")) {
            list.sort(Comparator.comparingDouble(vg2 -> KillAura.mc.h.getDistance(vg2)));
        }
        if (KillAura.sorting.isMode("Crosshair")) {
            list.sort(Comparator.comparingDouble(vg3 -> Math.abs(MathUtils.getYawDifference((vg)KillAura.mc.h, vg3))));
        }
        KillAura.target = ((list.size() > 0) ? list.get(0) : null);
        if (KillAura.target == null) {
            this.serverYaw = KillAura.mc.h.v;
            this.serverPitch = KillAura.mc.h.w;
            TargetHUD.barWidth = 0.0f;
            TargetHUD.scaleFactor = 0.0f;
            return;
        }
        if (KillAura.mc.v() == null || !KillAura.targethud.enabled || KillAura.target == null || KillAura.mc.v().a(KillAura.target.h_()) == null) {
            TargetHUD.barWidth = 0.0f;
        }
        float[] array = { 0.0f, 0.0f };
        final String mode = this.rotations.getMode();
        switch (mode) {
            case "Sunrise": {
                array = MathUtils.Rotations.getSunriseRotations(KillAura.target);
                break;
            }
            case "Nexus": {
                array = MathUtils.Rotations.getNexusRotations(KillAura.target);
                break;
            }
            case "Matrix": {
                array = MathUtils.Rotations.getMatrixRotations(KillAura.target);
                break;
            }
            case "AAC": {
                array = MathUtils.Rotations.getAACRotations(KillAura.target);
                break;
            }
            case "Intave": {
                array = MathUtils.Rotations.getIntaveRotations(KillAura.target);
                break;
            }
            case "ReallyWorld": {
                array = MathUtils.Rotations.getMatrix2Rotations(KillAura.target);
                break;
            }
        }
        if (KillAura.mc.h.getDistanceResolved((vg)KillAura.target) > KillAura.range.getNum()) {
            return;
        }
        if (!MathUtils.canSeeEntityAtFov((vg)KillAura.target, this.fov.getNum())) {
            return;
        }
        float n2 = 0.0f;
        float n3 = 0.0f;
        if (this.jitter.isMode("Random")) {
            n2 += MathUtils.nextFloat(-this.jitterStrength.getNum(), this.jitterStrength.getNum());
            n3 += MathUtils.nextFloat(-this.jitterStrength.getNum(), this.jitterStrength.getNum());
        }
        if (this.jitter.isMode("Sinus")) {
            n2 += (float)(Math.sin((double)System.currentTimeMillis()) * this.jitterStrength.getNum());
            n3 += (float)(Math.sin((double)System.currentTimeMillis()) * this.jitterStrength.getNum());
        }
        if (this.raytrace.enabled) {
            if (Utils.rotationCounter == 0) {
                eventMotion.yaw = array[0] + n2;
                eventMotion.pitch = array[1] + n3;
            }
            boolean b = false;
            final bhc rayTrace = MathUtils.rayTrace((vg)KillAura.mc.h, array[0], array[1], KillAura.range.getNum(), KillAura.mc.aj());
            if (KillAura.mc.s.d != null && KillAura.mc.s.d.equals((Object)KillAura.target)) {
                if (Utils.rotationCounter == 0) {
                    eventMotion.pitch = KillAura.mc.h.w;
                }
                b = true;
            }
            else if (rayTrace.a().q() == (int)KillAura.target.q) {
                b = true;
            }
            this.fixRotations(eventMotion);
            if (b) {
                this.attack();
            }
        }
        else {
            if (this.side.isMode("Server")) {
                if (Utils.rotationCounter == 0) {
                    eventMotion.yaw = array[0] + n2;
                    eventMotion.pitch = array[1] + n3;
                }
            }
            else {
                KillAura.mc.h.v = array[0];
                KillAura.mc.h.w = array[1];
            }
            this.fixRotations(eventMotion);
            this.serverYaw = eventMotion.yaw;
            this.serverPitch = eventMotion.pitch;
            this.attack();
        }
        KillAura.mc.h.aP = eventMotion.yaw;
        KillAura.mc.h.rotationPitchHead = eventMotion.pitch;
        KillAura.mc.h.aN = eventMotion.yaw;
        KillAura.mc.h.aO = eventMotion.yaw;
    }
    
    @EventTarget
    public void onRender2(final EventRender3D eventRender3D) {
        if (KillAura.mc.h == null || KillAura.target == null || KillAura.mc.f == null) {
            return;
        }
        if (this.showRange.enabled) {
            RenderUtils.draw3DCircle((float)(KillAura.mc.h.M + (KillAura.mc.h.p - KillAura.mc.h.M) * KillAura.mc.aj()), (float)(KillAura.mc.h.N + (KillAura.mc.h.q - KillAura.mc.h.N) * KillAura.mc.aj()), (float)(KillAura.mc.h.O + (KillAura.mc.h.r - KillAura.mc.h.O) * KillAura.mc.aj()), KillAura.range.getNum(), 100.0f);
        }
        if (!this.targetESP.enabled) {
            return;
        }
        this.drawTargetESP(KillAura.target, eventRender3D.getPartialTicks());
    }
    
    @Override
    public void onDisable() {
        this.serverYaw = KillAura.mc.h.v;
        this.serverPitch = KillAura.mc.h.w;
        if (KillAura.mc.t.ad.i && !Mouse.isButtonDown(1)) {
            KillAura.mc.t.ad.i = false;
        }
        KillAura.mc.h.rotationPitchHead = 0.0f;
        KillAura.target = null;
        TargetHUD.barWidth = 0.0f;
        TargetHUD.scaleFactor = 0.0f;
        this.easeScale = 0.0f;
        super.onDisable();
    }
    
    @EventTarget
    public void onRenderDraggable(final EventRenderDraggable eventRenderDraggable) {
        this.targetHUDComponent.render(eventRenderDraggable.mouseX, eventRenderDraggable.mouseY, eventRenderDraggable.ticks, false);
    }
    
    public void calculateCPS() {
        double smooth = (this.cps.getNum() > 10.0f) ? (this.cps.getNum() + 5.0f) : ((double)this.cps.getNum());
        if (this.cpsMode.isMode("Legit")) {
            final Random random = new Random();
            final double n = smooth + 1.0;
            this.lastCPS = smooth + random.nextInt() * (n - smooth);
            if (this.cpsTimeHelper.hasReached((float)(long)(1000.0 / MathUtils.nextFloat((float)smooth, (float)n)))) {
                this.wasCPSDrop = !this.wasCPSDrop;
                this.counter.reset();
            }
            final double n2 = Math.max(this.lastCPS, (double)(System.currentTimeMillis() * random.nextInt(136 + 107 - 213 + 190))) / 3.0;
            if (this.wasCPSDrop) {
                this.lastCPS = (int)n2;
            }
            else {
                this.lastCPS = (int)(smooth + random.nextInt() * (n - smooth) / n2);
            }
        }
        else {
            if ("Smooth".equals(this.cpsMode.getMode())) {
                smooth = MathUtils.smooth(this.cps.getNum() + 1.0f, this.cps.getNum(), 0.05, true, 0.8500000238418579);
            }
            this.lastCPS = 1000.0 / smooth;
            if (this.cps.getNum() == 20.0f) {
                this.lastCPS = 0.0;
            }
            if (this.cpsMode.isMode("Randomize") || this.cpsMode.isMode("Calculate after Hit")) {
                this.lastCPS += ThreadLocalRandom.current().nextGaussian() * 50.0;
            }
        }
    }
    
    @EventTarget
    public void onPitch(final EventPitchHead eventPitchHead) {
        if (KillAura.target == null || KillAura.mc.h == null || KillAura.mc.f == null) {
            return;
        }
        if (!MathUtils.canSeeEntityAtFov((vg)KillAura.target, this.fov.getNum())) {
            return;
        }
        if (KillAura.mc.h.getDistanceResolved((vg)KillAura.target) > KillAura.range.getNum()) {
            return;
        }
        eventPitchHead.pitchHead = this.serverPitch;
    }
    
    @EventTarget
    public void onAttack(final EventAttack eventAttack) {
        if (!this.autoShield.enabled) {
            return;
        }
        if (KillAura.mc.h.cO() && this.shieldFixerTimer.hasReached(200.0f) && KillAura.mc.h.b(ub.b).c() instanceof ajm) {
            KillAura.mc.h.d.a((ht)new lp(lp.a.f, new et(885 + 236 - 1096 + 875, 547 + 621 - 492 + 224, 296 + 600 - 24 + 28), fa.b));
            KillAura.mc.c.a((aed)KillAura.mc.h, (amu)KillAura.mc.f, ub.b);
            this.shieldFixerTimer.reset();
            KillAura.mc.t.ad.i = true;
        }
    }
    
    void attack() {
        if (this.shieldDesyncer.enabled && KillAura.mc.h.cp().c() instanceof ajm) {
            KillAura.mc.t.ad.i = true;
            if (KillAura.mc.h.T % this.desyncTicks == 0 && KillAura.mc.t.ad.i && !Keyboard.isKeyDown(1)) {
                KillAura.mc.h.d.a((ht)new lp(lp.a.f, new et(845 + 250 - 964 + 769, 40 + 347 - 13 + 526, 830 + 267 - 733 + 536), fa.c));
                KillAura.mc.c.a((aed)KillAura.mc.h, (amu)KillAura.mc.f, ub.b);
                this.desyncTicks = MathUtils.nextInt(9, 0x9A ^ 0x91);
                KillAura.mc.t.ad.i = true;
            }
        }
        if (this.pvp.isMode("1.9+")) {
            if (this.crits.isMode("Smart") && (!Main.getFeatureByName("Jesus").isEnabled() || KillAura.mc.h.z) && !Main.getFeatureByName("Criticals").isEnabled() && !KillAura.mc.h.E && !KillAura.mc.h.isInLiquid() && !KillAura.mc.h.m_() && !KillAura.mc.h.cP() && Utils.isBlockAboveHead() && KillAura.mc.h.L <= 0.1f && (!this.adaptiveCrits.enabled || KillAura.mc.t.X.i)) {
                return;
            }
            if (this.crits.isMode("Default") && KillAura.mc.h.L <= 0.1f) {
                return;
            }
        }
        if (Main.getFeatureByName("Criticals").isEnabled() && KillAura.mc.h.T % 2 == 0) {
            return;
        }
        final float n = this.crits.isMode("None") ? 1.0f : 0.95f;
        if (this.pvp.isMode("1.9+")) {
            if (KillAura.mc.h.n(0.0f) < n || !this.counter.hasReached((float)(long)(Math.random() * 350.0))) {
                return;
            }
        }
        else {
            if (!this.counter.hasReached((float)this.lastCPS)) {
                return;
            }
            this.calculateCPS();
            this.counter.reset();
        }
        if (KillAura.mc.h.aV() && this.betterCrits.enabled) {
            KillAura.mc.h.d.a((ht)new lq((vg)KillAura.mc.h, lq.a.e));
            KillAura.mc.h.setMotion(0.9f);
        }
        if (this.hitchance.getNum() >= Math.random() * 100.0) {
            KillAura.mc.c.a((aed)KillAura.mc.h, (vg)KillAura.target);
        }
        KillAura.mc.h.a(ub.a);
        KillAura.mc.h.ds();
        if (this.shieldbreak.isEnabled()) {
            final aed target = KillAura.target;
            if (target == null) {
                return;
            }
            if (Utils.doesHotbarHaveAxe() && target.cG() && (target.cp().c() instanceof ajm || target.co().c() instanceof ajm) && this.timer.hasReached(250.0f)) {
                KillAura.mc.h.d.a((ht)new lv(Utils.getAxeH()));
                KillAura.mc.c.a((aed)KillAura.mc.h, (vg)KillAura.target);
                KillAura.mc.h.a(ub.a);
                KillAura.mc.h.ds();
                KillAura.mc.h.d.a((ht)new lv(KillAura.mc.h.bv.d));
                this.timer.reset();
                this.isBlocking = false;
            }
        }
        this.counter.reset();
    }
    
    @EventTarget
    public void onSendPacket(final EventPacketSend eventPacketSend) {
        if (!KillAura.interactFix.enabled) {
            return;
        }
        if (eventPacketSend.getPacket() instanceof li) {
            final li li = (li)eventPacketSend.getPacket();
            if (li.a() == li.a.a) {
                eventPacketSend.setCancelled(true);
            }
            if (li.a() == li.a.c) {
                eventPacketSend.setCancelled(true);
            }
        }
    }
    
    public KillAura() {
        super("KillAura", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0430\u0442\u0430\u043a\u0443\u0435\u0442 \u0438\u0433\u0440\u043e\u043a\u043e\u0432.", Category.COMBAT);
        this.cpsTimeHelper = new Counter();
        this.betterCrits = new OptionBoolean(this, "Better Crits", true);
        this.adaptiveCrits = new OptionBoolean(this, "Adaptive Crits", true);
        this.showRange = new OptionBoolean(this, "Show Range", false);
        this.timer = new Counter();
        this.easeScale = 0.0f;
        this.lastCPS = 70.0;
        this.targetHUDComponent = new TargetHUD();
        this.shieldCounter = new Counter();
        this.desyncTicks = 9;
        this.shieldFixerTimer = new Counter();
        this.isBlocking = false;
        this.espY = 0.0f;
        this.prevEspY = 0.0f;
        this.rotations = new OptionMode(this, "Rotations", "Matrix", new String[] { "Matrix", "Nexus", "ReallyWorld", "AAC", "Intave", "Sunrise" }, 0);
        this.pvp = new OptionMode(this, "Click Type", "1.9+", new String[] { "1.9+", "1.8" }, 0);
        this.side = new OptionMode(this, "Side", "Server", new String[] { "Server", "Client" }, 0);
        KillAura.sorting = new OptionMode(this, "Sorting", "Distance", new String[] { "Distance", "Health", "Crosshair" }, 0);
        this.moveFix = new OptionBoolean(this, "Move Fix", false);
        this.crits = new OptionMode(this, "Criticals", "Smart", new String[] { "Smart", "Default", "None" }, 0);
        this.jitter = new OptionMode(this, "Jitter", "None", new String[] { "None", "Sinus", "Random" }, 0);
        this.raytrace = new OptionBoolean(this, "Ray Trace", false);
        this.jitterStrength = new OptionSlider(this, "Jitter Strength", 2.0f, 0.01f, 10.0f, OptionSlider.SliderType.NULL);
        this.invisible = new OptionBoolean(this, "Invisibles", false);
        KillAura.resolver = new OptionBoolean(this, "Resolver", true);
        KillAura.interactFix = new OptionBoolean(this, "Interact Fix", true);
        KillAura.range = new OptionSlider(this, "Range", 3.75f, 0.01f, 6.0f, OptionSlider.SliderType.M);
        this.cps = new OptionSlider(this, "CPS", 14.0f, 1.0f, 30.0f, OptionSlider.SliderType.NULLINT);
        this.cpsMode = new OptionMode(this, "CPS Mode", "Randomize", new String[] { "Randomize", "Smooth", "Static", "Calculate after Hit", "Legit" }, 0);
        this.fov = new OptionSlider(this, "FOV", 360.0f, 0.0f, 360.0f, OptionSlider.SliderType.NULLINT);
        this.hitchance = new OptionSlider(this, "Hit Chance", 95.0f, 1.0f, 100.0f, OptionSlider.SliderType.NULLINT);
        this.shieldbreak = new OptionBoolean(this, "Shield Break", true);
        this.autoShield = new OptionBoolean(this, "Shield Fix", true);
        this.shieldDesyncer = new OptionBoolean(this, "Shield Desyncer", false);
        KillAura.targethud = new OptionBoolean(this, "TargetHUD", true);
        KillAura.barColor = new OptionColor(this, "Bar Color", new Color(0x39 ^ 0x2D, 43 + 73 - 17 + 101, 91 + 128 - 137 + 118));
        this.targetESP = new OptionBoolean(this, "TargetESP", true);
        this.colorMode = new OptionMode(this, "Color Mode", "Sky", new String[] { "Sky", "Liquid", "White", "Aquafresh", "Custom" }, 0);
        this.colorESP = new OptionColor(this, "TargetESP Color", new Color(0, 84 + 103 - 124 + 137, 118 + 32 + 35 + 15));
        final Option[] array = new Option[0x64 ^ 0x78];
        array[0] = this.rotations;
        array[1] = this.pvp;
        array[2] = this.side;
        array[3] = KillAura.range;
        array[4] = this.cpsMode;
        array[5] = this.cps;
        array[6] = KillAura.sorting;
        array[7] = this.crits;
        array[8] = this.adaptiveCrits;
        array[9] = KillAura.resolver;
        array[0x72 ^ 0x78] = KillAura.interactFix;
        array[0xAE ^ 0xA5] = this.betterCrits;
        array[0x69 ^ 0x65] = this.jitter;
        array[0x7B ^ 0x76] = this.jitterStrength;
        array[0x5D ^ 0x53] = this.fov;
        array[0x75 ^ 0x7A] = this.hitchance;
        array[0x1C ^ 0xC] = this.moveFix;
        array[0x11 ^ 0x0] = this.raytrace;
        array[0x70 ^ 0x62] = this.shieldbreak;
        array[0xD0 ^ 0xC3] = this.autoShield;
        array[0x3B ^ 0x2F] = this.shieldDesyncer;
        array[0xD3 ^ 0xC6] = KillAura.targethud;
        array[0x49 ^ 0x5F] = KillAura.barColor;
        array[0xB0 ^ 0xA7] = this.targetESP;
        array[0x7E ^ 0x66] = this.colorMode;
        array[0x5D ^ 0x44] = this.colorESP;
        array[0xBD ^ 0xA7] = this.invisible;
        array[0xBA ^ 0xA1] = this.showRange;
        this.addOptions(array);
    }
    
    @Override
    public void deltaTickEvent() {
        this.prevEspY = this.espY;
        if (this.targetESP.isEnabled()) {
            this.factor = (float)Math.cos(Main.millis / 265.0f);
            final float n = Easings.easeOutBack(Math.abs(this.factor)) / 100.0f;
            this.espY = ((this.factor > 0.0f) ? MathUtils.lerp(this.espY, 1.0f, n) : MathUtils.lerp(this.espY, -1.0f, n));
        }
        super.deltaTickEvent();
    }
    
    void drawTargetESP(final aed aed, final float n) {
        final double n2 = aed.M + (aed.p - aed.M) * n - KillAura.mc.ac().h;
        final double n3 = aed.N + (aed.q - aed.N) * n - KillAura.mc.ac().i + 1.0;
        final double n4 = aed.O + (aed.r - aed.O) * n - KillAura.mc.ac().j;
        Color color = Color.WHITE;
        final String mode = this.colorMode.getMode();
        switch (mode) {
            case "Custom": {
                color = this.colorESP.getColor();
                break;
            }
            case "Liquid": {
                color = Utils.liquidSlowly(System.nanoTime(), 0, 1.0f, 1.0f);
                break;
            }
            case "Sky": {
                color = Utils.skyRainbow(0, 1.0f, 1.0f);
                break;
            }
            case "White": {
                color = Color.WHITE;
                break;
            }
            case "Aquafresh": {
                color = Utils.getGradientOffset(Utils.getGradientOffset(Color.BLUE, Color.RED, Main.millis / 2000.0f, 13 + 58 + 62 + 67), Color.WHITE.brighter(), Main.millis / 1000.0f, 17 + 45 + 97 + 41);
                break;
            }
        }
        bus.G();
        bus.a(495 + 86 - 136 + 71, 0.01f);
        bus.m();
        bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
        bus.z();
        GL11.glShadeModel(1939 + 2710 + 2147 + 629);
        GL11.glDisable(2332 + 772 - 1256 + 1081);
        GL11.glDisable(2549 + 1831 - 4183 + 2687);
        bus.g();
        final double n6 = aed.G * 0.75f;
        GL11.glTranslatef(0.0f, -this.espY, 0.0f);
        GL11.glBegin(5);
        for (int i = 0; i < 81 + 338 - 58 + 0; ++i) {
            RenderUtils.color(new Color(color.getRed(), color.getGreen(), color.getBlue(), 120 + 187 - 292 + 185).getRGB());
            final double n7 = n2 + Math.cos(Math.toRadians(i)) * n6;
            final double n8 = n4 - Math.sin(Math.toRadians(i)) * n6;
            GL11.glVertex3d(n7, n3, n8);
            RenderUtils.color(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0).getRGB());
            final float n9 = 0.45f * Easings.easeInOutSine(1.0f - Math.abs(this.espY));
            GL11.glVertex3d(n7, n3 + ((this.prevEspY - this.espY > 0.0f) ? (-n9) : n9), n8);
        }
        GL11.glEnd();
        GL11.glEnable(185 + 701 - 377 + 2339);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(2);
        for (int j = 0; j < 43 + 147 - 53 + 224; ++j) {
            RenderUtils.color(new Color(color.getRed(), color.getGreen(), color.getBlue(), 108 + 80 - 87 + 99).getRGB());
            GL11.glVertex3d(n2 + Math.cos(Math.toRadians(j)) * n6, n3, n4 - Math.sin(Math.toRadians(j)) * n6);
        }
        GL11.glEnd();
        GL11.glShadeModel(298 + 6062 - 5301 + 6365);
        GL11.glDisable(1027 + 2396 - 1625 + 1050);
        GL11.glEnable(1956 + 1319 - 2989 + 2598);
        GL11.glEnable(1386 + 1492 - 1814 + 1865);
        bus.y();
        bus.I();
        bus.H();
    }
}
