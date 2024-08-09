/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.api;

import com.google.common.eventbus.Subscribe;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.impl.combat.AimBow;
import mpp.venusfr.functions.impl.combat.AntiBot;
import mpp.venusfr.functions.impl.combat.AutoArmor;
import mpp.venusfr.functions.impl.combat.AutoExplosion;
import mpp.venusfr.functions.impl.combat.AutoGapple;
import mpp.venusfr.functions.impl.combat.AutoPotion;
import mpp.venusfr.functions.impl.combat.AutoSwap;
import mpp.venusfr.functions.impl.combat.AutoTotem;
import mpp.venusfr.functions.impl.combat.Backtrack;
import mpp.venusfr.functions.impl.combat.Criticals;
import mpp.venusfr.functions.impl.combat.Hitbox;
import mpp.venusfr.functions.impl.combat.KillAura;
import mpp.venusfr.functions.impl.combat.NoEntityTrace;
import mpp.venusfr.functions.impl.combat.NoFriendDamage;
import mpp.venusfr.functions.impl.combat.TriggerBot;
import mpp.venusfr.functions.impl.combat.Velocity;
import mpp.venusfr.functions.impl.misc.AntiAFK;
import mpp.venusfr.functions.impl.misc.AntiPush;
import mpp.venusfr.functions.impl.misc.AutoAccept;
import mpp.venusfr.functions.impl.misc.AutoBuyUI;
import mpp.venusfr.functions.impl.misc.AutoEvent;
import mpp.venusfr.functions.impl.misc.AutoFish;
import mpp.venusfr.functions.impl.misc.AutoPilot;
import mpp.venusfr.functions.impl.misc.AutoRespawn;
import mpp.venusfr.functions.impl.misc.AutoTransfer;
import mpp.venusfr.functions.impl.misc.BaseFinder;
import mpp.venusfr.functions.impl.misc.BetterMinecraft;
import mpp.venusfr.functions.impl.misc.ClientSounds;
import mpp.venusfr.functions.impl.misc.ElytraHelper;
import mpp.venusfr.functions.impl.misc.GriefHelper;
import mpp.venusfr.functions.impl.misc.HWHelper;
import mpp.venusfr.functions.impl.misc.HitSound;
import mpp.venusfr.functions.impl.misc.ItemScroller;
import mpp.venusfr.functions.impl.misc.ItemSwapFix;
import mpp.venusfr.functions.impl.misc.LeaveTracker;
import mpp.venusfr.functions.impl.misc.NameProtect;
import mpp.venusfr.functions.impl.misc.NoEventDelay;
import mpp.venusfr.functions.impl.misc.Nuker;
import mpp.venusfr.functions.impl.misc.Optimization;
import mpp.venusfr.functions.impl.misc.RWHelper;
import mpp.venusfr.functions.impl.misc.SpammerEXP;
import mpp.venusfr.functions.impl.misc.xCarry;
import mpp.venusfr.functions.impl.movement.AutoSprint;
import mpp.venusfr.functions.impl.movement.BoatFly;
import mpp.venusfr.functions.impl.movement.ElytraFly;
import mpp.venusfr.functions.impl.movement.Fly;
import mpp.venusfr.functions.impl.movement.Jesus;
import mpp.venusfr.functions.impl.movement.LongJump;
import mpp.venusfr.functions.impl.movement.NoClip;
import mpp.venusfr.functions.impl.movement.NoSlow;
import mpp.venusfr.functions.impl.movement.Parkour;
import mpp.venusfr.functions.impl.movement.Speed;
import mpp.venusfr.functions.impl.movement.Spider;
import mpp.venusfr.functions.impl.movement.Strafe;
import mpp.venusfr.functions.impl.movement.TargetStrafe;
import mpp.venusfr.functions.impl.movement.Timer;
import mpp.venusfr.functions.impl.movement.WaterSpeed;
import mpp.venusfr.functions.impl.player.AutoLeave;
import mpp.venusfr.functions.impl.player.AutoTool;
import mpp.venusfr.functions.impl.player.ChestStealer;
import mpp.venusfr.functions.impl.player.ClickFriend;
import mpp.venusfr.functions.impl.player.ClickPearl;
import mpp.venusfr.functions.impl.player.FreeCam;
import mpp.venusfr.functions.impl.player.InventoryMove;
import mpp.venusfr.functions.impl.player.ItemCooldown;
import mpp.venusfr.functions.impl.player.NoInteract;
import mpp.venusfr.functions.impl.player.NoJumpDelay;
import mpp.venusfr.functions.impl.player.NoRotate;
import mpp.venusfr.functions.impl.player.PortalGodMode;
import mpp.venusfr.functions.impl.render.Cape;
import mpp.venusfr.functions.impl.render.ChinaHat;
import mpp.venusfr.functions.impl.render.Crosshair;
import mpp.venusfr.functions.impl.render.DeathEffect;
import mpp.venusfr.functions.impl.render.ESP;
import mpp.venusfr.functions.impl.render.FullBright;
import mpp.venusfr.functions.impl.render.GlassHand;
import mpp.venusfr.functions.impl.render.GlowESP;
import mpp.venusfr.functions.impl.render.HUD;
import mpp.venusfr.functions.impl.render.Hotbar;
import mpp.venusfr.functions.impl.render.ItemPhysic;
import mpp.venusfr.functions.impl.render.JumpCircle;
import mpp.venusfr.functions.impl.render.NoRender;
import mpp.venusfr.functions.impl.render.Particles;
import mpp.venusfr.functions.impl.render.Pointers;
import mpp.venusfr.functions.impl.render.Predictions;
import mpp.venusfr.functions.impl.render.SeeInvisibles;
import mpp.venusfr.functions.impl.render.ShulkerESP;
import mpp.venusfr.functions.impl.render.Snow;
import mpp.venusfr.functions.impl.render.StorageESP;
import mpp.venusfr.functions.impl.render.SwingAnimation;
import mpp.venusfr.functions.impl.render.TargetESP;
import mpp.venusfr.functions.impl.render.Tracers;
import mpp.venusfr.functions.impl.render.Trails;
import mpp.venusfr.functions.impl.render.ViewModel;
import mpp.venusfr.functions.impl.render.World;
import mpp.venusfr.functions.impl.render.XrayBypass;
import mpp.venusfr.utils.render.font.Font;
import mpp.venusfr.venusfr;

public class FunctionRegistry {
    private final List<Function> functions = new CopyOnWriteArrayList<Function>();
    private SwingAnimation swingAnimation;
    private HUD hud;
    private AutoGapple autoGapple;
    private AutoSprint autoSprint;
    private Velocity velocity;
    private NoRender noRender;
    private Timer timer;
    private AutoTool autoTool;
    private xCarry xcarry;
    private ElytraHelper elytrahelper;
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
    private TargetStrafe targetStrafe;
    private ClientSounds clientSounds;
    private AutoTotem autoTotem;
    private NoSlow noSlow;
    private Pointers pointers;
    private AutoExplosion autoExplosion;
    private NoRotate noRotate;
    private KillAura killAura;
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
    private LeaveTracker leaveTracker;
    private BoatFly boatFly;
    private AntiAFK antiAFK;
    private PortalGodMode portalGodMode;
    private BetterMinecraft betterMinecraft;
    private Backtrack backtrack;
    private SeeInvisibles seeInvisibles;
    private Hotbar hotbar;
    private Speed speed;
    private WaterSpeed waterSpeed;
    private Optimization optimization;
    private SpammerEXP spammerEXP;
    private Cape cape;
    private NoFriendDamage noFriendDamage;
    private Nuker nuker;
    private Jesus jesus;
    private AutoPilot autoPilot;
    private FullBright fullBright;
    private Criticals criticals;
    private ShulkerESP shulkerESP;
    private AimBow aimBow;
    private AutoEvent autoEvent;
    private HWHelper hwHelper;
    private BaseFinder baseFinder;
    private GlowESP glowESP;

    public void init() {
        Function[] functionArray = new Function[94];
        this.hud = new HUD();
        functionArray[0] = this.hud;
        this.autoGapple = new AutoGapple();
        functionArray[1] = this.autoGapple;
        this.autoSprint = new AutoSprint();
        functionArray[2] = this.autoSprint;
        this.velocity = new Velocity();
        functionArray[3] = this.velocity;
        this.noRender = new NoRender();
        functionArray[4] = this.noRender;
        this.autoTool = new AutoTool();
        functionArray[5] = this.autoTool;
        this.xcarry = new xCarry();
        functionArray[6] = this.xcarry;
        this.seeInvisibles = new SeeInvisibles();
        functionArray[7] = this.seeInvisibles;
        this.elytrahelper = new ElytraHelper();
        functionArray[8] = this.elytrahelper;
        this.itemswapfix = new ItemSwapFix();
        functionArray[9] = this.itemswapfix;
        this.autopotion = new AutoPotion();
        functionArray[10] = this.autopotion;
        this.noClip = new NoClip();
        functionArray[11] = this.noClip;
        this.triggerbot = new TriggerBot();
        functionArray[12] = this.triggerbot;
        this.nojumpdelay = new NoJumpDelay();
        functionArray[13] = this.nojumpdelay;
        this.clickfriend = new ClickFriend();
        functionArray[14] = this.clickfriend;
        this.inventoryMove = new InventoryMove();
        functionArray[15] = this.inventoryMove;
        this.esp = new ESP();
        functionArray[16] = this.esp;
        this.autoTransfer = new AutoTransfer();
        functionArray[17] = this.autoTransfer;
        this.griefHelper = new GriefHelper();
        functionArray[18] = this.griefHelper;
        this.autoArmor = new AutoArmor();
        functionArray[19] = this.autoArmor;
        this.hitbox = new Hitbox();
        functionArray[20] = this.hitbox;
        this.hitsound = new HitSound();
        functionArray[21] = this.hitsound;
        this.antiPush = new AntiPush();
        functionArray[22] = this.antiPush;
        this.autoBuyUI = new AutoBuyUI();
        functionArray[23] = this.autoBuyUI;
        this.freeCam = new FreeCam();
        functionArray[24] = this.freeCam;
        this.chestStealer = new ChestStealer();
        functionArray[25] = this.chestStealer;
        this.autoLeave = new AutoLeave();
        functionArray[26] = this.autoLeave;
        this.autoAccept = new AutoAccept();
        functionArray[27] = this.autoAccept;
        this.autoRespawn = new AutoRespawn();
        functionArray[28] = this.autoRespawn;
        this.fly = new Fly();
        functionArray[29] = this.fly;
        this.clientSounds = new ClientSounds();
        functionArray[30] = this.clientSounds;
        this.noSlow = new NoSlow();
        functionArray[31] = this.noSlow;
        this.pointers = new Pointers();
        functionArray[32] = this.pointers;
        this.autoExplosion = new AutoExplosion();
        functionArray[33] = this.autoExplosion;
        this.noRotate = new NoRotate();
        functionArray[34] = this.noRotate;
        this.antiBot = new AntiBot();
        functionArray[35] = this.antiBot;
        this.trails = new Trails();
        functionArray[36] = this.trails;
        this.crosshair = new Crosshair();
        functionArray[37] = this.crosshair;
        this.autoTotem = new AutoTotem();
        functionArray[38] = this.autoTotem;
        this.itemCooldown = new ItemCooldown();
        functionArray[39] = this.itemCooldown;
        this.killAura = new KillAura(this.autopotion);
        functionArray[40] = this.killAura;
        this.criticals = new Criticals(this.killAura);
        functionArray[41] = this.criticals;
        this.clickPearl = new ClickPearl(this.itemCooldown);
        functionArray[42] = this.clickPearl;
        this.autoSwap = new AutoSwap(this.autoTotem);
        functionArray[43] = this.autoSwap;
        this.targetStrafe = new TargetStrafe(this.killAura);
        functionArray[44] = this.targetStrafe;
        this.strafe = new Strafe(this.targetStrafe, this.killAura);
        functionArray[45] = this.strafe;
        this.swingAnimation = new SwingAnimation(this.killAura);
        functionArray[46] = this.swingAnimation;
        this.targetESP = new TargetESP(this.killAura);
        functionArray[47] = this.targetESP;
        this.world = new World();
        functionArray[48] = this.world;
        this.viewModel = new ViewModel();
        functionArray[49] = this.viewModel;
        this.elytraFly = new ElytraFly();
        functionArray[50] = this.elytraFly;
        this.chinaHat = new ChinaHat();
        functionArray[51] = this.chinaHat;
        this.snow = new Snow();
        functionArray[52] = this.snow;
        this.particles = new Particles();
        functionArray[53] = this.particles;
        this.jumpCircle = new JumpCircle();
        functionArray[54] = this.jumpCircle;
        this.itemPhysic = new ItemPhysic();
        functionArray[55] = this.itemPhysic;
        this.predictions = new Predictions();
        functionArray[56] = this.predictions;
        this.noEntityTrace = new NoEntityTrace();
        functionArray[57] = this.noEntityTrace;
        this.itemScroller = new ItemScroller();
        functionArray[58] = this.itemScroller;
        this.autoFish = new AutoFish();
        functionArray[59] = this.autoFish;
        this.storageESP = new StorageESP();
        functionArray[60] = this.storageESP;
        this.spider = new Spider();
        functionArray[61] = this.spider;
        this.timer = new Timer();
        functionArray[62] = this.timer;
        this.nameProtect = new NameProtect();
        functionArray[63] = this.nameProtect;
        this.noInteract = new NoInteract();
        functionArray[64] = this.noInteract;
        this.glassHand = new GlassHand();
        functionArray[65] = this.glassHand;
        this.hwHelper = new HWHelper();
        functionArray[66] = this.hwHelper;
        this.aimBow = new AimBow();
        functionArray[67] = this.aimBow;
        this.tracers = new Tracers();
        functionArray[68] = this.tracers;
        this.leaveTracker = new LeaveTracker();
        functionArray[69] = this.leaveTracker;
        this.glowESP = new GlowESP();
        functionArray[70] = this.glowESP;
        this.antiAFK = new AntiAFK();
        functionArray[71] = this.antiAFK;
        this.portalGodMode = new PortalGodMode();
        functionArray[72] = this.portalGodMode;
        this.betterMinecraft = new BetterMinecraft();
        functionArray[73] = this.betterMinecraft;
        this.backtrack = new Backtrack();
        functionArray[74] = this.backtrack;
        functionArray[75] = new LongJump();
        functionArray[76] = new XrayBypass();
        functionArray[77] = new Parkour();
        functionArray[78] = new RWHelper();
        this.hotbar = new Hotbar();
        functionArray[79] = this.hotbar;
        this.speed = new Speed();
        functionArray[80] = this.speed;
        this.waterSpeed = new WaterSpeed();
        functionArray[81] = this.waterSpeed;
        this.optimization = new Optimization();
        functionArray[82] = this.optimization;
        this.spammerEXP = new SpammerEXP();
        functionArray[83] = this.spammerEXP;
        this.cape = new Cape();
        functionArray[84] = this.cape;
        this.noFriendDamage = new NoFriendDamage();
        functionArray[85] = this.noFriendDamage;
        this.nuker = new Nuker();
        functionArray[86] = this.nuker;
        this.jesus = new Jesus();
        functionArray[87] = this.jesus;
        this.autoEvent = new AutoEvent();
        functionArray[88] = this.autoEvent;
        this.autoPilot = new AutoPilot();
        functionArray[89] = this.autoPilot;
        this.baseFinder = new BaseFinder();
        functionArray[90] = this.baseFinder;
        this.deathEffect = new DeathEffect();
        functionArray[91] = this.deathEffect;
        this.fullBright = new FullBright();
        functionArray[92] = this.fullBright;
        this.shulkerESP = new ShulkerESP();
        functionArray[93] = this.shulkerESP;
        this.registerAll(functionArray);
        venusfr.getInstance().getEventBus().register(this);
    }

    private void registerAll(Function ... functionArray) {
        this.functions.addAll(List.of((Object[])functionArray));
    }

    public List<Function> getSorted(Font font, float f) {
        return this.functions.stream().sorted((arg_0, arg_1) -> FunctionRegistry.lambda$getSorted$0(font, f, arg_0, arg_1)).toList();
    }

    @Subscribe
    private void onKey(EventKey eventKey) {
        for (Function function : this.functions) {
            if (function.getBind() != eventKey.getKey()) continue;
            function.toggle();
        }
    }

    public List<Function> getFunctions() {
        return this.functions;
    }

    public SwingAnimation getSwingAnimation() {
        return this.swingAnimation;
    }

    public HUD getHud() {
        return this.hud;
    }

    public AutoGapple getAutoGapple() {
        return this.autoGapple;
    }

    public AutoSprint getAutoSprint() {
        return this.autoSprint;
    }

    public Velocity getVelocity() {
        return this.velocity;
    }

    public NoRender getNoRender() {
        return this.noRender;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public AutoTool getAutoTool() {
        return this.autoTool;
    }

    public xCarry getXcarry() {
        return this.xcarry;
    }

    public ElytraHelper getElytrahelper() {
        return this.elytrahelper;
    }

    public AutoBuyUI getAutoBuyUI() {
        return this.autoBuyUI;
    }

    public ItemSwapFix getItemswapfix() {
        return this.itemswapfix;
    }

    public AutoPotion getAutopotion() {
        return this.autopotion;
    }

    public TriggerBot getTriggerbot() {
        return this.triggerbot;
    }

    public NoJumpDelay getNojumpdelay() {
        return this.nojumpdelay;
    }

    public ClickFriend getClickfriend() {
        return this.clickfriend;
    }

    public InventoryMove getInventoryMove() {
        return this.inventoryMove;
    }

    public ESP getEsp() {
        return this.esp;
    }

    public AutoTransfer getAutoTransfer() {
        return this.autoTransfer;
    }

    public GriefHelper getGriefHelper() {
        return this.griefHelper;
    }

    public ItemCooldown getItemCooldown() {
        return this.itemCooldown;
    }

    public ClickPearl getClickPearl() {
        return this.clickPearl;
    }

    public AutoSwap getAutoSwap() {
        return this.autoSwap;
    }

    public AutoArmor getAutoArmor() {
        return this.autoArmor;
    }

    public Hitbox getHitbox() {
        return this.hitbox;
    }

    public HitSound getHitsound() {
        return this.hitsound;
    }

    public AntiPush getAntiPush() {
        return this.antiPush;
    }

    public FreeCam getFreeCam() {
        return this.freeCam;
    }

    public ChestStealer getChestStealer() {
        return this.chestStealer;
    }

    public AutoLeave getAutoLeave() {
        return this.autoLeave;
    }

    public AutoAccept getAutoAccept() {
        return this.autoAccept;
    }

    public NoEventDelay getNoEventDelay() {
        return this.noEventDelay;
    }

    public AutoRespawn getAutoRespawn() {
        return this.autoRespawn;
    }

    public Fly getFly() {
        return this.fly;
    }

    public TargetStrafe getTargetStrafe() {
        return this.targetStrafe;
    }

    public ClientSounds getClientSounds() {
        return this.clientSounds;
    }

    public AutoTotem getAutoTotem() {
        return this.autoTotem;
    }

    public NoSlow getNoSlow() {
        return this.noSlow;
    }

    public Pointers getPointers() {
        return this.pointers;
    }

    public AutoExplosion getAutoExplosion() {
        return this.autoExplosion;
    }

    public NoRotate getNoRotate() {
        return this.noRotate;
    }

    public KillAura getKillAura() {
        return this.killAura;
    }

    public AntiBot getAntiBot() {
        return this.antiBot;
    }

    public Trails getTrails() {
        return this.trails;
    }

    public Crosshair getCrosshair() {
        return this.crosshair;
    }

    public DeathEffect getDeathEffect() {
        return this.deathEffect;
    }

    public Strafe getStrafe() {
        return this.strafe;
    }

    public World getWorld() {
        return this.world;
    }

    public ViewModel getViewModel() {
        return this.viewModel;
    }

    public ElytraFly getElytraFly() {
        return this.elytraFly;
    }

    public ChinaHat getChinaHat() {
        return this.chinaHat;
    }

    public Snow getSnow() {
        return this.snow;
    }

    public Particles getParticles() {
        return this.particles;
    }

    public TargetESP getTargetESP() {
        return this.targetESP;
    }

    public JumpCircle getJumpCircle() {
        return this.jumpCircle;
    }

    public ItemPhysic getItemPhysic() {
        return this.itemPhysic;
    }

    public Predictions getPredictions() {
        return this.predictions;
    }

    public NoEntityTrace getNoEntityTrace() {
        return this.noEntityTrace;
    }

    public NoClip getNoClip() {
        return this.noClip;
    }

    public ItemScroller getItemScroller() {
        return this.itemScroller;
    }

    public AutoFish getAutoFish() {
        return this.autoFish;
    }

    public StorageESP getStorageESP() {
        return this.storageESP;
    }

    public Spider getSpider() {
        return this.spider;
    }

    public NameProtect getNameProtect() {
        return this.nameProtect;
    }

    public NoInteract getNoInteract() {
        return this.noInteract;
    }

    public GlassHand getGlassHand() {
        return this.glassHand;
    }

    public Tracers getTracers() {
        return this.tracers;
    }

    public LeaveTracker getLeaveTracker() {
        return this.leaveTracker;
    }

    public BoatFly getBoatFly() {
        return this.boatFly;
    }

    public AntiAFK getAntiAFK() {
        return this.antiAFK;
    }

    public PortalGodMode getPortalGodMode() {
        return this.portalGodMode;
    }

    public BetterMinecraft getBetterMinecraft() {
        return this.betterMinecraft;
    }

    public Backtrack getBacktrack() {
        return this.backtrack;
    }

    public SeeInvisibles getSeeInvisibles() {
        return this.seeInvisibles;
    }

    public Hotbar getHotbar() {
        return this.hotbar;
    }

    public Speed getSpeed() {
        return this.speed;
    }

    public WaterSpeed getWaterSpeed() {
        return this.waterSpeed;
    }

    public Optimization getOptimization() {
        return this.optimization;
    }

    public SpammerEXP getSpammerEXP() {
        return this.spammerEXP;
    }

    public Cape getCape() {
        return this.cape;
    }

    public NoFriendDamage getNoFriendDamage() {
        return this.noFriendDamage;
    }

    public Nuker getNuker() {
        return this.nuker;
    }

    public Jesus getJesus() {
        return this.jesus;
    }

    public AutoPilot getAutoPilot() {
        return this.autoPilot;
    }

    public FullBright getFullBright() {
        return this.fullBright;
    }

    public Criticals getCriticals() {
        return this.criticals;
    }

    public ShulkerESP getShulkerESP() {
        return this.shulkerESP;
    }

    public AimBow getAimBow() {
        return this.aimBow;
    }

    public AutoEvent getAutoEvent() {
        return this.autoEvent;
    }

    public HWHelper getHwHelper() {
        return this.hwHelper;
    }

    public BaseFinder getBaseFinder() {
        return this.baseFinder;
    }

    public GlowESP getGlowESP() {
        return this.glowESP;
    }

    private static int lambda$getSorted$0(Font font, float f, Function function, Function function2) {
        return Float.compare(font.getWidth(function2.getName(), f), font.getWidth(function.getName(), f));
    }
}

