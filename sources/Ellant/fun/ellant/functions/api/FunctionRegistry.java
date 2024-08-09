package fun.ellant.functions.api;

import com.google.common.eventbus.Subscribe;
import com.ibm.icu.util.CodePointTrie;
import fun.ellant.Ellant;
import fun.ellant.events.EventKey;
import fun.ellant.functions.impl.combat.*;
import fun.ellant.functions.impl.hud.*;
import fun.ellant.functions.impl.movement.*;
import fun.ellant.functions.impl.player.*;
import fun.ellant.functions.impl.render.*;
import fun.ellant.functions.impl.player.NoRender;
import fun.ellant.functions.impl.render.ViewModel;
import fun.ellant.utils.render.font.Font;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class FunctionRegistry {
    private final List<Function> functions = new CopyOnWriteArrayList<>();

    private SwingAnimation swingAnimation;
    private AutoPilot autoPilot;
    private Hotbar hotbar;
    private HUD hud;
    private AutoGapple autoGapple;
    private AutoSprint autoSprint;
    private Velocity velocity;
    private NoRender noRender;
    private Timer timer;
    private AutoTool autoTool;
    private xCarry xcarry;
    private ElytraHelper elytrahelper;
    private Phase phase;
    private AutoBuyUI autoBuyUI;
    private ItemSwapFix itemswapfix;
    private AutoPotion autopotion;
    private TriggerBot triggerbot;
    private NoJumpDelay nojumpdelay;
    private ClickFriend clickfriend;
    private InventoryMove inventoryMove;
    private ESP esp;
    private AutoTransfer autoTransfer;
    private GriefHelper griefHelper;
    private AutoDuel autoDuel;
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
    private NoEventDelay noEventDelay;
    private AutoRespawn autoRespawn;
    private Fly fly;
    private SuperBow superBow;
    private TargetStrafe targetStrafe;
    private ClientSounds clientSounds;
    private AutoTotem autoTotem;
    private NoSlow noSlow;
    private Pointers pointers;
    private AutoExplosion autoExplosion;
    private NoRotate noRotate;
    private KillAura killAura;
    private DiscrordRPC discrordRPC;
    private AntiBot antiBot;
    private Trails trails;
    private Crosshair crosshair;
    private DeathEffect deathEffect;
    private Strafe strafe;
    private World world;
    private ViewModel viewModel;
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
    private Tracers tracers;
    private SelfDestruct selfDestruct;
    private LeaveTracker leaveTracker;
    private BoatFly boatFly;
    private AntiAFK antiAFK;
    private PortalGodMode portalGodMode;
    private LeaveFly leaveFly;
    private BetterMinecraft betterMinecraft;
    private Backtrack backtrack;
    private SeeInvisibles seeInvisibles;
    private GetBalance getBalance;
    private CordDroper cordDroper;
    private DragonFly dragonFly;
    private TeleportBack teleportBack;
    private SRPSpoofer srpSpoofer;
    private AutoSell autoSell;
    private BaseFinder baseFinder;
    private AutoEXP autoEXP;
    private AutoEat autoEat;
    private BoatSpeed boatSpeed;
    private GriefHelper2 griefHelper2;
    private ElytraHighJump elytraHighJump;
    private Jesus jesus;
    private ColorCorrection colorCorrection;
    private Speed speed;
    private WaterSpeed waterSpeed;
    private NoFriendDamage noFriendDamage;
    private FullBright fullBright;
    private AuctionHelper auctionHelper;
    private Cape cape;
    private FastEXP fastEXP;
    private FreeLock freeLok;
    private ElytraTarget elytraTarget;
    private AspectRatio aspectRatio;
    private WaterPlace waterPlace;

    public void init() {
        registerAll( waterPlace = new WaterPlace(), superBow = new SuperBow(), aspectRatio = new AspectRatio(), freeLok = new FreeLock(), fastEXP = new FastEXP(), elytraTarget = new ElytraTarget(), cape = new Cape(), auctionHelper = new AuctionHelper(), fullBright = new FullBright(), discrordRPC = new DiscrordRPC(), autoDuel = new AutoDuel(), leaveFly = new LeaveFly(), noFriendDamage = new NoFriendDamage(), speed = new Speed(), waterSpeed = new WaterSpeed(), elytraHighJump = new ElytraHighJump(), jesus = new Jesus(), colorCorrection = new ColorCorrection(), autoEXP = new AutoEXP(), autoEat = new AutoEat(), boatSpeed = new BoatSpeed(), autoSell = new AutoSell(), griefHelper2 = new GriefHelper2(), srpSpoofer = new SRPSpoofer(), teleportBack = new TeleportBack(), dragonFly = new DragonFly(), autoPilot = new AutoPilot(), hotbar = new Hotbar(), noEventDelay = new NoEventDelay(), boatFly = new BoatFly(), deathEffect = new DeathEffect(), cordDroper = new CordDroper(), baseFinder = new BaseFinder(), getBalance = new GetBalance(), hud = new HUD(), autoGapple = new AutoGapple(), autoSprint = new AutoSprint(), velocity = new Velocity(), noRender = new NoRender(), autoTool = new AutoTool(), xcarry = new xCarry(), seeInvisibles = new SeeInvisibles(), elytrahelper = new ElytraHelper(), phase = new Phase(), itemswapfix = new ItemSwapFix(), autopotion = new AutoPotion(), noClip = new NoClip(), triggerbot = new TriggerBot(), nojumpdelay = new NoJumpDelay(), clickfriend = new ClickFriend(), inventoryMove = new InventoryMove(), esp = new ESP(), autoTransfer = new AutoTransfer(), griefHelper = new GriefHelper(), autoArmor = new AutoArmor(), hitbox = new Hitbox(), hitsound = new HitSound(), antiPush = new AntiPush(), autoBuyUI = new AutoBuyUI(), freeCam = new FreeCam(), chestStealer = new ChestStealer(), autoLeave = new AutoLeave(), autoAccept = new AutoAccept(), autoRespawn = new AutoRespawn(), fly = new Fly(), clientSounds = new ClientSounds(), noSlow = new NoSlow(), pointers = new Pointers(), autoExplosion = new AutoExplosion(), noRotate = new NoRotate(), antiBot = new AntiBot(), trails = new Trails(), crosshair = new Crosshair(), autoTotem = new AutoTotem(), itemCooldown = new ItemCooldown(), killAura = new KillAura(autopotion), clickPearl = new ClickPearl(itemCooldown), autoSwap = new AutoSwap(autoTotem), targetStrafe = new TargetStrafe(killAura), strafe = new Strafe(targetStrafe, killAura), swingAnimation = new SwingAnimation(killAura), targetESP = new TargetESP(killAura), world = new World(), viewModel = new ViewModel(), elytraFly = new ElytraFly(), chinaHat = new ChinaHat(), snow = new Snow(), particles = new Particles(), jumpCircle = new JumpCircle(), itemPhysic = new ItemPhysic(), predictions = new Predictions(), noEntityTrace = new NoEntityTrace(), itemScroller = new ItemScroller(), autoFish = new AutoFish(), storageESP = new StorageESP(), spider = new Spider(), timer = new Timer(), nameProtect = new NameProtect(), noInteract = new NoInteract(), glassHand = new GlassHand(), tracers = new Tracers(), selfDestruct = new SelfDestruct(), leaveTracker = new LeaveTracker(), antiAFK = new AntiAFK(), portalGodMode = new PortalGodMode(), betterMinecraft = new BetterMinecraft(), backtrack = new Backtrack(), new LongJump(), new XrayBypass(), new Parkour(), new RWHelper());

        Ellant.getInstance().getEventBus().register(this);
    }

    private void registerAll(Function... Functions) {
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

    public ClientSounds getClickGUI() {
        return null;
    }
}