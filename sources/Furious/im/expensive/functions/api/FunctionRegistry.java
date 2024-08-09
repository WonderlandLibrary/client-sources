package im.expensive.functions.api;

import com.google.common.eventbus.Subscribe;
import im.expensive.Furious;
import im.expensive.events.EventKey;
import im.expensive.functions.impl.combat.*;
import im.expensive.functions.impl.misc.*;
import im.expensive.functions.impl.movement.*;
import im.expensive.functions.impl.render.*;
import im.expensive.utils.render.font.Font;
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
    private BetterMinecraft betterMinecraft;
    private Backtrack backtrack;
    private SeeInvisibles seeInvisibles;
    private HWHelper hwHelper;
    private FTHelper ftHelper;
    private FastExp fastExp;
    private ElytraJump elytraJump;
    private Gamma gamma;
    private NoFriendDamage noFriendDamage;
    private Fov fov;
    private EntityTrails entityTrails;
    private ShulkerEsp shulkerEsp;

    public void init() {
        registerAll(hud = new HUD(), fov = new Fov(), shulkerEsp = new ShulkerEsp(), entityTrails = new EntityTrails(), chinaHat = new ChinaHat(), noFriendDamage = new NoFriendDamage(), deathEffect = new DeathEffect(), elytraJump = new ElytraJump(), gamma = new Gamma(), fastExp = new FastExp(), hwHelper = new HWHelper(), autoGapple = new AutoGapple(), ftHelper = new FTHelper(), autoSprint = new AutoSprint(), velocity = new Velocity(), noRender = new NoRender(), autoTool = new AutoTool(), xcarry = new xCarry(), seeInvisibles = new SeeInvisibles(), elytrahelper = new ElytraHelper(), phase = new Phase(), itemswapfix = new ItemSwapFix(), autopotion = new AutoPotion(), noClip = new NoClip(), triggerbot = new TriggerBot(), nojumpdelay = new NoJumpDelay(), clickfriend = new ClickFriend(), inventoryMove = new InventoryMove(), esp = new ESP(), autoTransfer = new AutoTransfer(), griefHelper = new GriefHelper(), autoArmor = new AutoArmor(), hitbox = new Hitbox(), hitsound = new HitSound(), antiPush = new AntiPush(), autoBuyUI = new AutoBuyUI(), freeCam = new FreeCam(), chestStealer = new ChestStealer(), autoLeave = new AutoLeave(), autoAccept = new AutoAccept(), autoRespawn = new AutoRespawn(), fly = new Fly(), clientSounds = new ClientSounds(), noSlow = new NoSlow(), pointers = new Pointers(), autoExplosion = new AutoExplosion(), noRotate = new NoRotate(), antiBot = new AntiBot(), trails = new Trails(), crosshair = new Crosshair(), autoTotem = new AutoTotem(), itemCooldown = new ItemCooldown(), killAura = new KillAura(autopotion), clickPearl = new ClickPearl(itemCooldown), autoSwap = new AutoSwap(autoTotem), swingAnimation = new SwingAnimation(killAura), world = new World(), viewModel = new ViewModel(), elytraFly = new ElytraFly(), snow = new Snow(), particles = new Particles(), jumpCircle = new JumpCircle(), itemPhysic = new ItemPhysic(), predictions = new Predictions(), noEntityTrace = new NoEntityTrace(), itemScroller = new ItemScroller(), autoFish = new AutoFish(), storageESP = new StorageESP(), nameProtect = new NameProtect(), noInteract = new NoInteract(), glassHand = new GlassHand(), tracers = new Tracers(), selfDestruct = new SelfDestruct(), leaveTracker = new LeaveTracker(), antiAFK = new AntiAFK(), betterMinecraft = new BetterMinecraft());

        Furious.getInstance().getEventBus().register(this);
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
}
