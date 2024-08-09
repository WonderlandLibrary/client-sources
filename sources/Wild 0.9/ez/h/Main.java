package ez.h;

import ez.h.managers.altmanager.*;
import ez.h.ui.particle.*;
import java.util.concurrent.*;
import ez.h.event.*;
import ez.h.utils.*;
import ez.h.managers.*;
import ez.h.ui.clickgui.*;
import ez.h.ui.clickgui.element.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.ui.flatclickgui.*;
import baritone.api.*;
import baritone.*;
import ez.h.ui.fonts.*;
import org.lwjgl.opengl.*;
import java.io.*;
import ez.h.utils.cosmetic.impl.*;
import ez.h.utils.cosmetic.*;
import viamcp.*;
import ez.h.features.player.*;
import ez.h.features.movement.*;
import ez.h.ui.*;
import ez.h.features.visual.*;
import ez.h.features.combat.*;
import ez.h.features.another.*;
import ez.h.ui.hudeditor.*;
import java.util.stream.*;
import ez.h.event.events.*;
import org.lwjgl.input.*;
import java.util.*;

public class Main
{
    static bib mc;
    public static final Main instance;
    static Thread deltaTickEvent;
    public static String name;
    public static String version;
    public static List<Alt> alts;
    public static aed target;
    static Thread millisCounter;
    public static ParticleManager particleManager;
    public static long millis;
    public static boolean aye;
    public static ConfigManager configManager;
    public EventManager eventManager;
    public static String title;
    public static String cmdPrefix;
    static bip fr;
    public static CopyOnWriteArrayList<Feature> features;
    
    public Main() {
        this.eventManager = new EventManager();
    }
    
    public void shutdown() {
        this.eventManager.unregister(this);
    }
    
    static {
        instance = new Main();
        Main.name = "WILD";
        Main.version = "0.9";
        Main.features = new CopyOnWriteArrayList<Feature>();
        Main.configManager = new ConfigManager();
        Main.cmdPrefix = ".";
        Main.alts = new ArrayList<Alt>();
        Main.fr = bib.z().k;
        Main.mc = bib.z();
        Main.millisCounter = new Thread(new MillisCounter());
        Main.deltaTickEvent = new Thread(new DeltaTickEvent());
    }
    
    public static Feature getFeatureByName(final String s) {
        for (final Feature feature : Main.features) {
            if (feature.getName().equalsIgnoreCase(s)) {
                return feature;
            }
        }
        return null;
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
        if (!isEnabledFeature("Freecam")) {
            return;
        }
        if (eventPacketReceive.getPacket() instanceof iy && isEnabledFeature("Freecam")) {
            getFeatureByName("Freecam").setEnabled(false);
        }
    }
    
    @EventTarget
    public static void onUpdate(final EventMotion eventMotion) throws IOException {
        if (Utils.rotationCounter < 0) {
            Utils.rotationCounter = 0;
        }
        if (Utils.rotationCounter > 0) {
            --Utils.rotationCounter;
        }
        if (Main.millisCounter.isInterrupted()) {
            Main.millisCounter.start();
        }
        if (Main.deltaTickEvent.isInterrupted()) {
            Main.deltaTickEvent.start();
        }
        if (!Main.aye) {
            Main.aye = true;
            try {
                ClientManager.load();
                Main.configManager.init();
                Main.configManager.initConfig(Main.configManager.currentCFG);
                FriendManager.load();
                MacroManager.load();
                for (final Panel panel : ClickGuiScreen.panels) {
                    final Iterator<Feature> iterator2 = getSortedModulesByCategory(panel.category, CFontManager.manropesmall).iterator();
                    while (iterator2.hasNext()) {
                        final ElementButton elementButton2 = new ElementButton(iterator2.next(), panel);
                        if (!panel.buttons.contains(elementButton2)) {
                            panel.buttons.add(elementButton2);
                        }
                    }
                    panel.buttons.sort(Comparator.comparingDouble(elementButton -> -CFontManager.manropesmall.getStringWidth(elementButton.getFeature().getName())));
                }
                for (final Category category : Category.values()) {
                    final ArrayList<FlatGuiScreen.ElementFeature> list = new ArrayList<FlatGuiScreen.ElementFeature>();
                    for (final Feature feature : getSortedModulesByCategory(category, CFontManager.montserrat)) {
                        final FlatGuiScreen.ElementFeature elementFeature = new FlatGuiScreen.ElementFeature(feature);
                        list.add(elementFeature);
                        for (final Option option : feature.options) {
                            if (option instanceof OptionSlider) {
                                elementFeature.elements.add(new FlatSlider((OptionSlider)option));
                            }
                            if (option instanceof OptionBoolean) {
                                elementFeature.elements.add(new FlatCheckBox((OptionBoolean)option));
                            }
                            if (option instanceof OptionMode) {
                                elementFeature.elements.add(new FlatMode((OptionMode)option));
                            }
                            if (option instanceof OptionColor) {
                                elementFeature.elements.add(new FlatColorPicker((OptionColor)option));
                            }
                        }
                    }
                    FlatGuiScreen.elements.put(category, list);
                }
            }
            catch (Exception ex) {}
        }
        if (Main.mc.h != null && BaritoneAPI.getProvider().getBaritoneForPlayer(Main.mc.h).getPathingBehavior().isPathing()) {
            Baritone.settings().fadePath.value = true;
        }
    }
    
    public static List<Feature> getSortedModulesByCategory(final Category category, final CFontRenderer cFontRenderer) {
        final ArrayList<Object> list = new ArrayList<Object>();
        for (final Feature feature3 : Main.features) {
            if (feature3.getCategory() != category) {
                continue;
            }
            list.add(feature3);
        }
        final String s;
        final String s2;
        final int n;
        list.sort((feature, feature2) -> {
            feature.getSuffix();
            feature2.getSuffix();
            n = cFontRenderer.getStringWidth(s) - cFontRenderer.getStringWidth(s2);
            return (n != 0) ? n : s.compareTo(s2);
        });
        return (List<Feature>)list;
    }
    
    public static void keyPress(final int n) {
        if (MacroManager.macroses.containsKey(n)) {
            bib.z().h.g((String)MacroManager.macroses.get(n));
        }
        for (final Feature feature : Main.features) {
            if (n == feature.getKey()) {
                feature.toggle();
            }
        }
    }
    
    public static void close() {
        bib.z().n();
    }
    
    public static boolean isEnabledFeature(final String s) {
        return getFeatureByName(s) != null && getFeatureByName(s).isEnabled();
    }
    
    public void onStart() throws IOException {
        Display.setTitle(Main.title = Main.name + " " + Main.version + " | vk.com/wildclient");
        new File("C:/.wildclient").mkdir();
        Main.instance.eventManager.register(this);
        new DragonWing();
        for (final cct cct : bib.z().ac().getSkinMap().values()) {
            cct.a((ccg)new CosmeticRender(cct));
        }
        try {
            ViaMCP.getInstance().start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        Main.features.add(new KillAura());
        Main.features.add(new Reach());
        Main.features.add(new TargetStrafe());
        Main.features.add(new HitBox());
        Main.features.add(new FastBow());
        Main.features.add(new NoFriendHurt());
        Main.features.add(new GappleCooldown());
        Main.features.add(new AutoHeal());
        Main.features.add(new Criticals());
        Main.features.add(new AutoPotion());
        Main.features.add(new AimAssist());
        Main.features.add(new AutoTotem());
        Main.features.add(new PushAttack());
        Main.features.add(new TriggerBot());
        Main.features.add(new SuperKnockback());
        Main.features.add(new MobAura());
        Main.features.add(new AutoShift());
        Main.features.add(new Sprint());
        Main.features.add(new NoPush());
        Main.features.add(new Velocity());
        Main.features.add(new InventoryCleaner());
        Main.features.add(new InventoryManager());
        Main.features.add(new FastBreak());
        Main.features.add(new AutoGapple());
        Main.features.add(new AutoEat());
        Main.features.add(new NameProtect());
        Main.features.add(new NoFall());
        Main.features.add(new Blink());
        Main.features.add(new Eagle());
        Main.features.add(new FastLadder());
        Main.features.add(new AutoParkour());
        Main.features.add(new InventoryWalk());
        Main.features.add(new AutoArmor());
        Main.features.add(new AutoFish());
        Main.features.add(new Scaffold());
        Main.features.add(new AutoTool());
        Main.features.add(new FastPlace());
        Main.features.add(new NoBadEffects());
        Main.features.add(new NoJumpDelay());
        Main.features.add(new Phase());
        Main.features.add(new Freecam());
        Main.features.add(new NoInteract());
        Main.features.add(new Macros());
        Main.features.add(new StaffAlert());
        Main.features.add(new AutoRespawn());
        Main.features.add(new ItemScroller());
        Main.features.add(new Notifications());
        Main.features.add(new SuperBow());
        Main.features.add(new MCFriend());
        Main.features.add(new DiscordRP());
        Main.features.add(new KeyPearl());
        Main.features.add(new SelfDamage());
        Main.features.add(new WorldCache());
        Main.features.add(new FakePlayer());
        Main.features.add(new AntiServerSwap());
        Main.features.add(new AutoDuel());
        Main.features.add(new XRay());
        Main.features.add(new ChestStealer());
        Main.features.add(new AutoLeave());
        Main.features.add(new HitSound());
        Main.features.add(new Disabler());
        Main.features.add(new Optimizer());
        Main.features.add(new XCarry());
        Main.features.add(new XRayBypass());
        Main.features.add(new NoRotateSet());
        Main.features.add(new PlayerTracker());
        Main.features.add(new ECExploit());
        Main.features.add(new AutoAccept());
        Main.features.add(new AntiAim());
        Main.features.add(new AntiBot());
        Main.features.add(new FlagDetector());
        Main.features.add(new ClickTP());
        Main.features.add(new Strafe());
        Main.features.add(new SmartVClip());
        Main.features.add(new NoSlowdown());
        Main.features.add(new Speed());
        Main.features.add(new Spider());
        Main.features.add(new WaterSpeed());
        Main.features.add(new NoClip());
        Main.features.add(new Jesus());
        Main.features.add(new BoatFly());
        Main.features.add(new Flight());
        Main.features.add(new AntiVoid());
        Main.features.add(new HighJump());
        Main.features.add(new Glide());
        Main.features.add(new SafeWalk());
        Main.features.add(new Step());
        Main.features.add(new AirJump());
        Main.features.add(new ElytraFly());
        Main.features.add(new LongJump());
        Main.features.add(new Teleport());
        Main.features.add(new WaterLeave());
        Main.features.add(new Timer());
        Main.features.add(new DamageFly());
        Main.features.add(new HUD());
        Main.features.add(new Perspective());
        Main.features.add(new ChinaHat());
        Main.features.add(new SwingAnimation());
        Main.features.add(new ColorPlus());
        Main.features.add(new HurtCam());
        Main.features.add(new FireFlyes());
        Main.features.add(new ClientCape());
        Main.features.add(new BlockOverlay());
        Main.features.add(new CustomModel());
        Main.features.add(new EnchantColor());
        Main.features.add(new JumpCircles());
        Main.features.add(new Crosshair());
        Main.features.add(new ClickGUI());
        Main.features.add(new SpawnerESP());
        Main.features.add(new ItemPhysics());
        Main.features.add(new StorageESP());
        Main.features.add(new CameraClip());
        Main.features.add(new BlockHit());
        Main.features.add(new SlideWalk());
        Main.features.add(new Wings());
        Main.features.add(new NoOverlay());
        Main.features.add(new CustomGUIs());
        Main.features.add(new BlockOutline());
        Main.features.add(new Gamma());
        Main.features.add(new PlayerInfo());
        Main.features.add(new ScoreBoard());
        Main.features.add(new CustomLight());
        Main.features.add(new Item360());
        Main.features.add(new CustomWeather());
        Main.features.add(new MotionGraph());
        Main.features.add(new Trails());
        Main.features.add(new Ambience());
        Main.features.add(new ViewModel());
        Main.features.add(new CustomFog());
        Main.features.add(new NameTags());
        Main.features.add(new ESP());
        Main.features.add(new Tracers());
        Main.features.add(new ChunkAnimator());
        Main.features.add(new ItemESP());
        Main.features.add(new AutoPlay());
        Main.features.add(new AutoCrystal());
        Main.features.add(new Debug());
        float n = 50.0f;
        Main.particleManager = new ParticleManager();
        final Category[] values = Category.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            ClickGuiScreen.panels.add(new Panel(values[i], 105.0f, 20.0f, 100.0f, n += 25.0f));
        }
        FlagDetector.init();
        Main.configManager.init();
        Main.configManager.initAlts();
        HUDEditor.loadFile();
        final Iterator<Feature> iterator2 = (Iterator<Feature>)Main.features.stream().sorted(Comparator.comparingDouble(feature -> -Main.fr.a(feature.getSuffix()))).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).iterator();
        while (iterator2.hasNext()) {
            HUD.anims.put(iterator2.next(), 200.0f);
        }
        Main.deltaTickEvent.start();
        Main.millisCounter.start();
    }
    
    @EventTarget
    public void onChatMessage(final EventChatSending eventChatSending) throws IOException {
        if (eventChatSending.msg.startsWith(Main.cmdPrefix)) {
            final bib z = bib.z();
            final String[] split = eventChatSending.msg.substring(1).split(" ");
            if (split[0].equalsIgnoreCase("help")) {
                z.h.a((hh)new ho(a.l + "Command helper: \n .vclip {amount}, clips you up/down \n .hclip {amount}, clips you forward/back \n .config {name}, switches current config \n .bind {feature} {key}, switching bindings \n .ddos {packets}, ddosing server with animation packets \n .alt {username}, sets new username \n .friends add/remove/list/clear \n .teleport x y z \n .hide {feature} true/false, hides feature is toggled features \n .macros add/remove/clear/list command, addes macros"));
            }
            if (split[0].equalsIgnoreCase("vclip")) {
                z.h.b(z.h.p, z.h.q + Float.parseFloat(split[1]), z.h.r);
                z.h.d.a((ht)new lk.a(z.h.p, z.h.q + Float.parseFloat(split[1]), z.h.r, true));
                z.h.d.a((ht)new lk.a(z.h.p, z.h.q + Float.parseFloat(split[1]), z.h.r, true));
            }
            if (split[0].equalsIgnoreCase("eclip")) {
                for (final agr agr : z.h.bx.c) {
                    if (agr.d().c() == air.cS) {
                        z.c.a(0, agr.e, 0, afw.a, (aed)z.h);
                        z.c.a(0, 6, 0, afw.a, (aed)z.h);
                        z.h.d.a((ht)new lq((vg)z.h, lq.a.i));
                        z.h.d.a((ht)new lq((vg)z.h, lq.a.i));
                        z.h.d.a((ht)new lk.a(z.h.p, Double.parseDouble(split[1]), z.h.r, false));
                        z.h.d.a((ht)new lk.a(z.h.p, Double.parseDouble(split[1]), z.h.r, false));
                        z.h.b(z.h.p, Double.parseDouble(split[1]), z.h.r);
                        z.c.a(0, 6, 0, afw.a, (aed)z.h);
                        z.c.a(0, agr.e, 0, afw.a, (aed)z.h);
                        z.c.e();
                    }
                }
            }
            if (split[0].equalsIgnoreCase("hclip")) {
                final float[] array = { (float)(-Math.sin(Math.toRadians(z.h.v))), (float)Math.cos(Math.toRadians(z.h.v)) };
                z.h.a(z.h.p + array[0] * Float.parseFloat(split[1]), z.h.q, z.h.r + array[1] * Float.parseFloat(split[1]));
            }
            if (split[0].equalsIgnoreCase("config")) {
                try {
                    Main.configManager.currentCFG = split[1] + ".w";
                    Main.configManager.initConfig(split[1] + ".w");
                    z.h.a((hh)new ho(a.k + "Successfully switched config to " + Main.configManager.currentCFG));
                    ClientManager.save();
                }
                catch (IOException ex) {
                    z.h.a((hh)new ho(a.e + "[!] An error while loading config " + Main.configManager.currentCFG + " " + ex.getMessage()));
                }
            }
            if (split[0].equalsIgnoreCase("configinit")) {
                Main.configManager.init();
            }
            if (split[0].equalsIgnoreCase("friends")) {
                if (split[1].equalsIgnoreCase("add")) {
                    FriendManager.addFriend(split[2]);
                    z.h.a((hh)new ho(a.k + "Added friend: " + split[2]));
                    FriendManager.save();
                }
                if (split[1].equalsIgnoreCase("remove")) {
                    FriendManager.removeFriend(split[2]);
                    z.h.a((hh)new ho(a.m + "Removed friend: " + split[2]));
                    FriendManager.save();
                }
                if (split[1].equalsIgnoreCase("list")) {
                    z.h.a((hh)new ho(a.l + "Friends:\n" + FriendManager.friends.toString().replace("[", "").replace("]", "")));
                }
                if (split[1].equalsIgnoreCase("clear")) {
                    FriendManager.friends.clear();
                    z.h.a((hh)new ho(a.e + "Cleared friends :( You are dead inside now"));
                    FriendManager.save();
                }
            }
            if (split[0].equalsIgnoreCase("bind") && split.length == 3) {
                getFeatureByName(split[1]).setKey(Keyboard.getKeyIndex(split[2].toUpperCase()));
                z.h.a((hh)new ho(a.k + "Successfully switched bind " + split[1] + " on " + split[2]));
            }
            if (split[0].equalsIgnoreCase("ddos")) {
                for (int i = 1; i <= Integer.parseInt(split[1]); ++i) {
                    z.h.d.a((ht)new ly(new Random().nextBoolean() ? ub.a : ub.b));
                }
                z.h.a((hh)new ho(a.e + "DDoS info: attacked with " + split[1] + " packets"));
            }
            if (split[0].equalsIgnoreCase("teleport")) {
                z.h.a((double)Float.parseFloat(split[1]), (double)Float.parseFloat(split[2]), (double)Float.parseFloat(split[3]));
                z.h.d.a((ht)new ll());
            }
            if (split[0].equalsIgnoreCase("alt")) {
                z.af = new bii(split[1], split[1], UUID.randomUUID().toString(), bii.a.a.toString());
                z.h.a((hh)new ho(a.k + "Successfully switched username to " + split[1]));
                z.h.a((hh)new ho(a.k + "Please reconnect"));
            }
            if (split[0].equalsIgnoreCase("hide")) {
                getFeatureByName(split[1]).setHidden(Boolean.parseBoolean(split[2]));
                z.h.a((hh)new ho(Boolean.parseBoolean(split[2]) ? (a.r + "Hidden " + getFeatureByName(split[1]).getName()) : (a.r + "Unhidden " + getFeatureByName(split[1]).getName())));
            }
            if (getFeatureByName("Macros").isEnabled() && split[0].equalsIgnoreCase("macros")) {
                if (split[1].equalsIgnoreCase("add")) {
                    final String replace = eventChatSending.msg.replace("." + split[0] + " " + split[1] + " " + split[2] + " ", "");
                    MacroManager.addMacros(Keyboard.getKeyIndex(split[2].toUpperCase().replace(" ", "")), replace);
                    MacroManager.save();
                    z.h.a((hh)new ho(a.k + "Added macros: " + split[2] + " " + replace));
                }
                if (split[1].equalsIgnoreCase("remove")) {
                    MacroManager.removeMacros(split[2]);
                    MacroManager.save();
                    z.h.a((hh)new ho(a.m + "Removed macros: " + split[2]));
                }
                if (split[1].equalsIgnoreCase("list")) {
                    z.h.a((hh)new ho(MacroManager.macroses.toString()));
                    MacroManager.save();
                }
                if (split[1].equalsIgnoreCase("clear")) {
                    MacroManager.macroses.clear();
                    MacroManager.save();
                }
            }
            eventChatSending.setCancelled(true);
        }
    }
    
    public static class DeltaTickEvent implements Runnable
    {
        @Override
        public void run() {
            while (Thread.currentThread().isAlive()) {
                try {
                    Thread.sleep(5L);
                    Main.features.forEach(Feature::deltaTickEvent);
                }
                catch (Exception ex) {}
            }
        }
    }
    
    public static class MillisCounter implements Runnable
    {
        @Override
        public void run() {
            while (Thread.currentThread().isAlive()) {
                try {
                    Thread.sleep(1L);
                    ++Main.millis;
                }
                catch (Exception ex) {}
            }
        }
    }
}
