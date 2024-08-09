package src.Wiksi.functions.api;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.Wiksi;
import src.Wiksi.events.EventKey;
import src.Wiksi.functions.impl.combat.*;
import src.Wiksi.functions.impl.misc.*;
import src.Wiksi.functions.impl.player.BaseFinder;
import src.Wiksi.functions.impl.player.HPNotify;
import src.Wiksi.functions.impl.movement.*;
import src.Wiksi.functions.impl.player.*;
import src.Wiksi.functions.impl.render.*;
import src.Wiksi.ui.autobuy.AutoBuyUI;
import src.Wiksi.utils.render.font.Font;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class FunctionRegistry {
    private final List<Function> functions = new CopyOnWriteArrayList<>();

    private SwingAnimation swingAnimation;
    private HUD hud;
    private AutoGapple autoGapple;
    private AutoSprint autoSprint;
    private Velocity velocity;
    private NoRender noRender;
    private Timer timer;
    private AutoTool autoTool;
    private Criticals criticals;
    private xCarry xcarry;
    private ElytraHelper elytrahelper;
    private ItemSwapFix itemswapfix;
    private AutoPotion autopotion;
    private TriggerBot triggerbot;
    private NoJumpDelay nojumpdelay;
    private ClickFriend clickfriend;
    private InventoryMove inventoryMove;
    private ESP esp;
    private AutoTransfer autoTransfer;
    private FuntimeHelper funtimeHelper;
    private ItemCooldown itemCooldown;
    private ClickPearl clickPearl;
    private AutoSwap autoSwap;
    private AutoArmor autoArmor;
    private Hitbox hitbox;
    private HitSound hitsound;
    private AntiPush antiPush;
    private FreeCam freeCam;
    private ChestStealer chestStealer;
    private AutoLeave autoLeave;
    private AutoAccept autoAccept;
    private AutoRespawn autoRespawn;
    private Fly fly;
    private ClientSounds clientSounds;
    private AutoTotem autoTotem;
    private NoSlow noSlow;
    private Arrows arrows;
    private AutoExplosion autoExplosion;
    private NoRotate noRotate;
    private KillAura killAura;
    private AntiBot antiBot;
    private Trails trails;
    private Crosshair crosshair;
    private DeathEffect deathEffect;
    private World world;
    private ElytraFly elytraFly;
    private ChinaHat chinaHat;

    private Snow snow;
    private Particles particles;
    private TargetESP targetESP;
    private JumpCircle jumpCircle;

    private ItemPhysic itemPhysic;
    private Predictions predictions;
    private NoEntityTrace noEntityTrace;
    private NoClip noClip;
    private ItemScroller itemScroller;
    private AutoFish autoFish;
    private StorageESP storageESP;
    private Spider spider;
    private NameProtect nameProtect;
    private NoInteract noInteract;
    private GlassHand glassHand;
    private Proverka selfDestruct;
    private BoatFly boatFly;
    private AntiAFK antiAFK;

    private BetterMinecraft betterMinecraft;
    private Backtrack backtrack;
    private SeeInvisibles seeInvisibles;
    private CordDroper cordDroper;
    private FullBrightFunction fullBrightFunction;
private AutoModerLeave autoModerLeave;
private BaseFinder baseFinder;
private SkanItems scannerItems;
private Tracers tracers;
private AutoSphera autoSphera;
private Parkour parkour;
private ClearInv clearInv;
private EventDelay eventDelay;
private ECAutoOpen ecAutoOpen;
private DragonFly dragonFly;
private Hotbar hotbar;
private AutoEat autoEat;
private WaterSpeed waterSpeed;
private ElytraHighJump elytraHighJump;
private Speed speed;
private AirPlace airPlace;
private AutoEventGps autoEventGps;
    private SpookyTransfer spookyTransfer;
private AutoAuth autoAuth;
private NoFriendDamage noFriendDamage;
private ElytraTarget elytraTarget;
private HPNotify hpNotify;
private FTConvert ftConvert;
private TotemNotify totemNotify;
private NoWeb noWeb;
private Eagle eagle;
private LavaDetector lavaDetector;
private WaterPlace waterPlace;
private HatExploit hatExploit;
private ElytraNotification elytraNotification;
private FOV fov;
private Proverka proverka;
private RWTransfer rwTransfer;
private AhHelper ahHelper;
public GlowESP glowESP;
private Skirt skirt;
private AutoPilot2 autoPilot2;
private Trails2 trails2;
private ElytraFlyHw elytraFlyHw;
private ViewModel viewModel;
private Chams chams;
private FastEXP fastEXP;

//16.06
    private Nuker nuker;
    public void init() {
        registerAll(fastEXP = new FastEXP(), criticals = new Criticals(), viewModel = new ViewModel(), chams = new Chams(), trails2 = new Trails2(), autoPilot2 = new AutoPilot2(), skirt = new Skirt(this.skirt), glowESP = new GlowESP(), ahHelper = new AhHelper(), rwTransfer = new RWTransfer(), hud = new HUD(), nuker = new Nuker(), fov = new FOV(), elytraNotification = new ElytraNotification(), hatExploit = new HatExploit(), waterPlace = new WaterPlace(), lavaDetector = new LavaDetector(), eagle = new Eagle(), noWeb = new NoWeb(), totemNotify = new TotemNotify(), hpNotify = new HPNotify(), ftConvert = new FTConvert(), elytraTarget = new ElytraTarget(), noFriendDamage = new NoFriendDamage(), autoAuth = new AutoAuth(), spookyTransfer = new SpookyTransfer(), autoEventGps = new AutoEventGps(), airPlace = new AirPlace(), speed = new Speed(), elytraHighJump = new ElytraHighJump(), waterSpeed = new WaterSpeed(), autoEat = new AutoEat(), dragonFly = new DragonFly(), hotbar = new Hotbar(), ecAutoOpen = new ECAutoOpen(), eventDelay = new EventDelay(), clearInv = new ClearInv(), boatFly = new BoatFly(), parkour = new Parkour(), autoSphera = new AutoSphera(), tracers = new Tracers(), scannerItems = new SkanItems(), baseFinder = new BaseFinder(), autoModerLeave = new AutoModerLeave(), fullBrightFunction = new FullBrightFunction(), deathEffect = new DeathEffect(), cordDroper = new CordDroper(), autoGapple = new AutoGapple(), autoSprint = new AutoSprint(), velocity = new Velocity(), noRender = new NoRender(), autoTool = new AutoTool(), xcarry = new xCarry(), seeInvisibles = new SeeInvisibles(), elytrahelper = new ElytraHelper(), itemswapfix = new ItemSwapFix(), autopotion = new AutoPotion(), noClip = new NoClip(), triggerbot = new TriggerBot(), nojumpdelay = new NoJumpDelay(), clickfriend = new ClickFriend(), inventoryMove = new InventoryMove(), esp = new ESP(), autoTransfer = new AutoTransfer(), funtimeHelper = new FuntimeHelper(), autoArmor = new AutoArmor(), hitbox = new Hitbox(), hitsound = new HitSound(), antiPush = new AntiPush(), freeCam = new FreeCam(), chestStealer = new ChestStealer(), autoLeave = new AutoLeave(), autoAccept = new AutoAccept(), autoRespawn = new AutoRespawn(), fly = new Fly(), clientSounds = new ClientSounds(), noSlow = new NoSlow(), arrows = new Arrows(), autoExplosion = new AutoExplosion(), noRotate = new NoRotate(), antiBot = new AntiBot(), trails = new Trails(), crosshair = new Crosshair(), autoTotem = new AutoTotem(), itemCooldown = new ItemCooldown(), killAura = new KillAura(autopotion), clickPearl = new ClickPearl(itemCooldown), autoSwap = new AutoSwap(autoTotem), swingAnimation = new SwingAnimation(killAura), targetESP = new TargetESP(killAura), world = new World(), elytraFly = new ElytraFly(), chinaHat = new ChinaHat(this.chinaHat), snow = new Snow(), particles = new Particles(), jumpCircle = new JumpCircle(), itemPhysic = new ItemPhysic(), predictions = new Predictions(), noEntityTrace = new NoEntityTrace(), itemScroller = new ItemScroller(), autoFish = new AutoFish(), storageESP = new StorageESP(), spider = new Spider(), timer = new Timer(), nameProtect = new NameProtect(), noInteract = new NoInteract(), glassHand = new GlassHand(), selfDestruct = new Proverka(), antiAFK = new AntiAFK(), betterMinecraft = new BetterMinecraft(), backtrack = new Backtrack(), new LongJump());

        Wiksi.getInstance().getEventBus().register(this);
    }


    private void registerAll( Function... Functions) {
        Arrays.sort(Functions, Comparator.comparing(Function::getName));

        functions.addAll(List.of(Functions));
    }

    public List<Function> getSorted(Font font, float size) {
        return functions.stream().sorted((f1, f2) -> Float.compare(font.getWidth(f2.getName(), size), font.getWidth(f1.getName(), size))).toList();
    }

    @Subscribe
    private void onKey(EventKey e) {
        if (selfDestruct.unhooked) return;
        for (Function Function : functions) {
            if (Function.getBind() == e.getKey()) {
                Function.toggle();
            }
        }
    }

}
