package net.shoreline.client.init;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.manager.ModuleManager;
import net.shoreline.client.impl.module.client.ClickGuiModule;
import net.shoreline.client.impl.module.client.ColorsModule;
import net.shoreline.client.impl.module.client.HUDModule;
import net.shoreline.client.impl.module.client.RotationsModule;
import net.shoreline.client.impl.module.combat.*;
import net.shoreline.client.impl.module.exploit.*;
import net.shoreline.client.impl.module.misc.*;
import net.shoreline.client.impl.module.movement.*;
import net.shoreline.client.impl.module.render.*;
import net.shoreline.client.impl.module.world.*;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Module
 * @see ModuleManager
 */
public class Modules
{
    // The initialized state of the modules. Once this is true, all modules
    // have been initialized and the init process is complete. As a general
    // rule, it is good practice to check this state before accessing instances.
    private static boolean initialized;
    // The module initialization cache. This prevents modules from being
    // initialized more than once.
    private static Set<Module> CACHE;
    // Module instances.
    public static ClickGuiModule CLICK_GUI;
    public static ColorsModule COLORS;
    public static HUDModule HUD;
    public static RotationsModule ROTATIONS;
    // Combat
    public static AntiBotsModule ANTI_BOTS;
    public static AuraModule AURA;
    public static AutoArmorModule AUTO_ARMOR;
    public static AutoBowReleaseModule AUTO_BOW_RELEASE;
    public static AutoCrystalModule AUTO_CRYSTAL;
    public static AutoLogModule AUTO_LOG;
    public static AutoPotModule AUTO_POT;
    public static AutoTotemModule AUTO_TOTEM;
    public static BlockLagModule BLOCK_LAG;
    public static BowAimModule BOW_AIM;
    public static CriticalsModule CRITICALS;
    public static NoHitDelayModule NO_HIT_DELAY;
    public static ReplenishModule REPLENISH;
    public static SurroundModule SURROUND;
    public static TriggerModule TRIGGER;
    // Exploit
    public static AntiHungerModule ANTI_HUNGER;
    public static ChorusControlModule CHORUS_CONTROL;
    public static ClickTPModule CLICK_TP;
    public static ColorSignsModule COLOR_SIGNS;
    public static CrasherModule CRASHER;
    public static FakeLatencyModule FAKE_LATENCY;
    public static FastProjectileModule FAST_PROJECTILE;
    public static NoMineAnimationModule NO_MINE_ANIMATION;
    public static PacketCancelerModule PACKET_CANCELER;
    public static PacketFlyModule PACKET_FLY;
    public static PortalGodModeModule PORTAL_GOD_MODE;
    public static ReachModule REACH;
    public static SwingModule SWING;
    // Misc
    public static AnnouncerModule ANNOUNCER;
    public static AntiAimModule ANTI_AIM;
    public static AntiBookBanModule ANTI_BOOK_BAN;
    public static AntiSpamModule ANTI_SPAM;
    public static AutoAcceptModule AUTO_ACCEPT;
    public static AutoEatModule AUTO_EAT;
    public static AntiVanishModule ANTI_VANISH;
    public static AutoFishModule AUTO_FISH;
    public static AutoMountModule AUTO_MOUNT;
    public static AutoReconnectModule AUTO_RECONNECT;
    public static AutoRespawnModule AUTO_RESPAWN;
    public static BeaconSelectorModule BEACON_SELECTOR;
    public static ChestAuraModule CHEST_AURA;
    public static FakePlayerModule FAKE_PLAYER;
    public static InvCleanerModule INV_CLEANER;
    public static MiddleClickModule MIDDLE_CLICK;
    public static NoSoundLagModule NO_SOUND_LAG;
    public static SkinBlinkModule SKIN_BLINK;
    public static TimerModule TIMER;
    public static TrueDurabilityModule TRUE_DURABILITY;
    public static UnfocusedFPSModule UNFOCUSED_FPS;
    public static XCarryModule XCARRY;
    // Movement
    public static AntiLevitationModule ANTI_LEVITATION;
    public static AutoWalkModule AUTO_WALK;
    public static ElytraFlyModule ELYTRA_FLY;
    public static EntityControlModule ENTITY_CONTROL;
    public static EntitySpeedModule ENTITY_SPEED;
    public static FastFallModule FAST_FALL;
    public static FlightModule FLIGHT;
    public static HighJumpModule HIGH_JUMP;
    public static IceSpeedModule ICE_SPEED;
    public static JesusModule JESUS;
    public static LongJumpModule LONG_JUMP;
    public static NoFallModule NO_FALL;
    public static NoSlowModule NO_SLOW;
    public static ParkourModule PARKOUR;
    public static SpeedModule SPEED;
    public static SprintModule SPRINT;
    public static StepModule STEP;
    public static TickShiftModule TICK_SHIFT;
    public static VelocityModule VELOCITY;
    public static YawModule YAW;
    // Render
    public static BlockHighlightModule BLOCK_HIGHLIGHT;
    public static BreakHighlightModule BREAK_HIGHLIGHT;
    public static ChamsModule CHAMS;
    public static ESPModule ESP;
    public static ExtraTabModule EXTRA_TAB;
    public static FullbrightModule FULLBRIGHT;
    public static NameProtectModule NAME_PROTECT;
    public static HoleESPModule HOLE_ESP;
    public static NametagsModule NAMETAGS;
    public static NoBobModule NO_BOB;
    public static NoRenderModule NO_RENDER;
    public static NoRotateModule NO_ROTATE;
    public static NoWeatherModule NO_WEATHER;
    public static SkeletonModule SKELETON;
    public static SkyboxModule SKYBOX;
    public static TooltipsModule TOOLTIPS;
    public static TracersModule TRACERS;
    public static ViewClipModule VIEW_CLIP;
    // public static ViewModelModule VIEW_MODEL;
    // World
    public static AntiInteractModule ANTI_INTERACT;
    public static AvoidModule AVOID;
    public static FastDropModule FAST_DROP;
    public static FastPlaceModule FAST_PLACE;
    public static MultitaskModule MULTITASK;
    public static NoGlitchBlocksModule NO_GLITCH_BLOCKS;
    public static SpeedmineModule SPEEDMINE;

    /**
     * Returns the registered {@link Module} with the param name in the
     * {@link ModuleManager}. The same module
     * cannot be retrieved more than once using this method.
     *
     * @param id The module name
     * @return The retrieved module
     * @throws IllegalStateException If the module was not registered
     *
     * @see ModuleManager
     */
    private static Module getRegisteredModule(final String id)
    {
        Module registered = Managers.MODULE.getModule(id);
        if (CACHE.add(registered))
        {
            return registered;
        }
        // already cached!!
        else
        {
            throw new IllegalStateException("Invalid module requested: " + id);
        }
    }

    /**
     * Initializes the modules instances. Should not be used if the
     * modules are already initialized. Cannot function unless the
     * {@link ModuleManager} is initialized.
     *
     * @see #getRegisteredModule(String)
     * @see Managers#isInitialized()
     */
    public static void init()
    {
        if (Managers.isInitialized())
        {
            CACHE = new HashSet<>();
            CLICK_GUI = (ClickGuiModule) getRegisteredModule(
                    "clickgui-module");
            COLORS = (ColorsModule) getRegisteredModule("colors-module");
            HUD = (HUDModule) getRegisteredModule("hud-module");
            ROTATIONS = (RotationsModule) getRegisteredModule(
                    "rotations-module");
            ANTI_BOTS = (AntiBotsModule) getRegisteredModule("antibots-module");
            AURA = (AuraModule) getRegisteredModule("aura-module");
            AUTO_ARMOR = (AutoArmorModule) getRegisteredModule("autoarmor-module");
            AUTO_BOW_RELEASE = (AutoBowReleaseModule) getRegisteredModule(
                    "autobowrelease-module");
            AUTO_CRYSTAL = (AutoCrystalModule) getRegisteredModule(
                    "autocrystal-module");
            AUTO_LOG = (AutoLogModule) getRegisteredModule("autolog-module");
            AUTO_POT = (AutoPotModule) getRegisteredModule("autopot-module");
            AUTO_TOTEM = (AutoTotemModule) getRegisteredModule(
                    "autototem-module");
            BLOCK_LAG = (BlockLagModule) getRegisteredModule("blocklag-module");
            BOW_AIM = (BowAimModule) getRegisteredModule("bowaim-module");
            CRITICALS = (CriticalsModule) getRegisteredModule("criticals-module");
            NO_HIT_DELAY = (NoHitDelayModule) getRegisteredModule(
                    "nohitdelay-module");
            REPLENISH = (ReplenishModule) getRegisteredModule("replenish-module");
            SURROUND = (SurroundModule) getRegisteredModule("surround-module");
            TRIGGER = (TriggerModule) getRegisteredModule("trigger-module");
            ANTI_HUNGER = (AntiHungerModule) getRegisteredModule(
                    "antihunger-module");
            CHORUS_CONTROL = (ChorusControlModule) getRegisteredModule(
                    "choruscontrol-module");
            CLICK_TP = (ClickTPModule) getRegisteredModule("clicktp-module");
            COLOR_SIGNS = (ColorSignsModule) getRegisteredModule(
                    "colorsigns-module");
            CRASHER = (CrasherModule) getRegisteredModule("crasher-module");
            FAKE_LATENCY = (FakeLatencyModule) getRegisteredModule(
                    "fakelatency-module");
            FAST_PROJECTILE = (FastProjectileModule) getRegisteredModule(
                    "fastprojectile-module");
            NO_MINE_ANIMATION = (NoMineAnimationModule) getRegisteredModule(
                    "nomineanimation-module");
            PACKET_CANCELER = (PacketCancelerModule) getRegisteredModule(
                    "packetcanceler-module");
            PACKET_FLY = (PacketFlyModule) getRegisteredModule(
                    "packetfly-module");
            PORTAL_GOD_MODE = (PortalGodModeModule) getRegisteredModule(
                    "portalgodmode-module");
            REACH = (ReachModule) getRegisteredModule("reach-module");
            SWING = (SwingModule) getRegisteredModule("swing-module");
            ANNOUNCER = (AnnouncerModule) getRegisteredModule(
                    "announcer-module");
            ANTI_AIM = (AntiAimModule) getRegisteredModule("antiaim-module");
            ANTI_BOOK_BAN = (AntiBookBanModule) getRegisteredModule(
                    "antibookban-module");
            ANTI_SPAM = (AntiSpamModule) getRegisteredModule("antispam-module");
            AUTO_ACCEPT = (AutoAcceptModule) getRegisteredModule(
                    "autoaccept-module");
            AUTO_EAT = (AutoEatModule) getRegisteredModule("autoeat-module");
            ANTI_VANISH = (AntiVanishModule) getRegisteredModule(
                    "antivanish-module");
            AUTO_FISH = (AutoFishModule) getRegisteredModule("autofish-module");
            AUTO_MOUNT = (AutoMountModule) getRegisteredModule("automount-module");
            AUTO_RECONNECT = (AutoReconnectModule) getRegisteredModule(
                    "autoreconnect-module");
            AUTO_RESPAWN = (AutoRespawnModule) getRegisteredModule(
                    "autorespawn-module");
            BEACON_SELECTOR = (BeaconSelectorModule) getRegisteredModule(
                    "beaconselector-module");
            CHEST_AURA = (ChestAuraModule) getRegisteredModule("chestaura-module");
            FAKE_PLAYER = (FakePlayerModule) getRegisteredModule(
                    "fakeplayer-module");
            INV_CLEANER = (InvCleanerModule) getRegisteredModule(
                    "invcleaner-module");
            MIDDLE_CLICK = (MiddleClickModule) getRegisteredModule(
                    "middleclick-module");
            NO_SOUND_LAG = (NoSoundLagModule) getRegisteredModule(
                    "nosoundlag-module");
            SKIN_BLINK = (SkinBlinkModule) getRegisteredModule("skinblink-module");
            TIMER = (TimerModule) getRegisteredModule("timer-module");
            TRUE_DURABILITY = (TrueDurabilityModule) getRegisteredModule(
                    "truedurability-module");
            UNFOCUSED_FPS = (UnfocusedFPSModule) getRegisteredModule(
                    "unfocusedfps-module");
            XCARRY = (XCarryModule) getRegisteredModule("xcarry-module");
            ANTI_LEVITATION = (AntiLevitationModule) getRegisteredModule(
                    "antilevitation-module");
            AUTO_WALK = (AutoWalkModule) getRegisteredModule("autowalk-module");
            ENTITY_CONTROL = (EntityControlModule) getRegisteredModule(
                    "entitycontrol-module");
            ENTITY_SPEED = (EntitySpeedModule) getRegisteredModule(
                    "entityspeed-module");
            FAST_FALL = (FastFallModule) getRegisteredModule("fastfall-module");
            FLIGHT = (FlightModule) getRegisteredModule("flight-module");
            HIGH_JUMP = (HighJumpModule) getRegisteredModule("highjump-module");
            ICE_SPEED = (IceSpeedModule) getRegisteredModule("icespeed-module");
            JESUS = (JesusModule) getRegisteredModule("jesus-module");
            LONG_JUMP = (LongJumpModule) getRegisteredModule("longjump-module");
            NO_FALL = (NoFallModule) getRegisteredModule("nofall-module");
            NO_SLOW = (NoSlowModule) getRegisteredModule("noslow-module");
            PARKOUR = (ParkourModule) getRegisteredModule("parkour-module");
            SPEED = (SpeedModule) getRegisteredModule("speed-module");
            SPRINT = (SprintModule) getRegisteredModule("sprint-module");
            STEP = (StepModule) getRegisteredModule("step-module");
            TICK_SHIFT = (TickShiftModule) getRegisteredModule(
                    "tickshift-module");
            VELOCITY = (VelocityModule) getRegisteredModule("velocity-module");
            YAW = (YawModule) getRegisteredModule("yaw-module");
            BLOCK_HIGHLIGHT = (BlockHighlightModule) getRegisteredModule(
                    "blockhighlight-module");
            BREAK_HIGHLIGHT = (BreakHighlightModule) getRegisteredModule(
                    "breakhighlight-module");
            CHAMS = (ChamsModule) getRegisteredModule("chams-module");
            ESP = (ESPModule) getRegisteredModule("esp-module");
            EXTRA_TAB = (ExtraTabModule) getRegisteredModule("extratab-module");
            FULLBRIGHT = (FullbrightModule) getRegisteredModule(
                    "fullbright-module");
            HOLE_ESP = (HoleESPModule) getRegisteredModule("holeesp-module");
            NAME_PROTECT = (NameProtectModule) getRegisteredModule(
                    "nameprotect-module");
            NAMETAGS = (NametagsModule) getRegisteredModule("nametags-module");
            NO_BOB = (NoBobModule) getRegisteredModule("nobob-module");
            NO_RENDER = (NoRenderModule) getRegisteredModule("norender-module");
            NO_ROTATE = (NoRotateModule) getRegisteredModule("norotate-module");
            NO_WEATHER = (NoWeatherModule) getRegisteredModule(
                    "noweather-module");
            SKELETON = (SkeletonModule) getRegisteredModule("skeleton-module");
            SKYBOX = (SkyboxModule) getRegisteredModule("skybox-module");
            TOOLTIPS = (TooltipsModule) getRegisteredModule("tooltips-module");
            TRACERS = (TracersModule) getRegisteredModule("tracers-module");
            VIEW_CLIP = (ViewClipModule) getRegisteredModule(
                    "viewclip-module");
            // VIEW_MODEL = (ViewModelModule) getRegisteredModule(
            //        "viewmodel-module");
            ANTI_INTERACT = (AntiInteractModule) getRegisteredModule(
                    "antiinteract-module");
            AVOID = (AvoidModule) getRegisteredModule("avoid-module");
            FAST_DROP = (FastDropModule) getRegisteredModule("fastdrop-module");
            FAST_PLACE = (FastPlaceModule) getRegisteredModule(
                    "fastplace-module");
            MULTITASK = (MultitaskModule) getRegisteredModule("multitask-module");
            NO_GLITCH_BLOCKS = (NoGlitchBlocksModule) getRegisteredModule(
                    "noglitchblocks-module");
            SPEEDMINE = (SpeedmineModule) getRegisteredModule(
                    "speedmine-module");
            initialized = true;
            // reflect configuration properties for each cached module
            for (Module module : CACHE)
            {
                if (module == null)
                {
                    continue;
                }
                module.reflectConfigs();
            }
            CACHE.clear();
        }
        else
        {
            throw new RuntimeException("Accessed modules before managers " +
                    "finished initializing!");
        }
    }

    /**
     * Returns <tt>true</tt> if the {@link Module} instances have been
     * initialized. This should always return <tt>true</tt> if
     * {@link Shoreline#preInit()} has finished running.
     *
     * @return <tt>true</tt> if the module instances have been initialized
     *
     * @see #init()
     * @see #initialized
     */
    public static boolean isInitialized()
    {
        return initialized;
    }
}
