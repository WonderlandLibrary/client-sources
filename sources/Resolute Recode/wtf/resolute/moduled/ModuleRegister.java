package wtf.resolute.moduled;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.EventKey;
import wtf.resolute.moduled.impl.render.HUD.HUD;
import wtf.resolute.utiled.render.font.Font;
import lombok.Getter;
import wtf.resolute.moduled.impl.combat.*;
import wtf.resolute.moduled.impl.misc.*;
import wtf.resolute.moduled.impl.movement.*;
import wtf.resolute.moduled.impl.player.*;
import wtf.resolute.moduled.impl.render.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class ModuleRegister {
    private final List<Module> functions = new CopyOnWriteArrayList<>();

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
    private ClickGui clickGui;
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
    private BabyBoy babyBoy;
    private Hotbar hotbar;
    private ElytraSpeed elytraSpeed;
    private Trails trails;
    private Crosshair crosshair;
    private Strafe strafe;
    private World world;
    private ElytraFly elytraFly;
    private ChinaHat chinaHat;
    private WorldParticles snow;
    private Particles particles;
    private TargetESP targetESP;
    private JumpCircle jumpCircle;
    private Predictions predictions;
    private NoEntityTrace noEntityTrace;
    private ItemScroller itemScroller;
    private AutoFish autoFish;
    private Spider spider;
    private NameProtect nameProtect;
    private NoInteract noInteract;
    private GlassHand glassHand;
    private PANIC selfDestruct;
    private LeaveTracker leaveTracker;
    private AntiAFK antiAFK;
    private BetterMinecraft betterMinecraft;
    public void init() {
        registerAll(hud = new HUD(),
                autoGapple = new AutoGapple(),
                autoSprint = new AutoSprint(),
                velocity = new Velocity(),
                noRender = new NoRender(),
                autoTool = new AutoTool(),
                xcarry = new xCarry(),
                elytrahelper = new ElytraHelper(),
                phase = new Phase(),
                itemswapfix = new ItemSwapFix(),
                autopotion = new AutoPotion(),
                triggerbot = new TriggerBot(),
                nojumpdelay = new NoJumpDelay(),
                clickfriend = new ClickFriend(),
                inventoryMove = new InventoryMove(),
                esp = new ESP(), autoTransfer = new AutoTransfer(),
                griefHelper = new GriefHelper(),
                autoArmor = new AutoArmor(),
                hitbox = new Hitbox(),
                hitsound = new HitSound(),
                antiPush = new AntiPush(),
                freeCam = new FreeCam(),
                chestStealer = new ChestStealer(),
                autoLeave = new AutoLeave(),
                autoAccept = new AutoAccept(),
                autoRespawn = new AutoRespawn(),
                fly = new Fly(),
                clientSounds = new ClientSounds(),
                noSlow = new NoSlow(),
                pointers = new Pointers(),
                autoExplosion = new AutoExplosion(),
                noRotate = new NoRotate(),
                antiBot = new AntiBot(),
                trails = new Trails(),
                crosshair = new Crosshair(),
                autoTotem = new AutoTotem(),
                itemCooldown = new ItemCooldown(),
                killAura = new KillAura(autopotion),
                clickPearl = new ClickPearl(itemCooldown),
                autoSwap = new AutoSwap(autoTotem),
                targetStrafe = new TargetStrafe(killAura),
                strafe = new Strafe(targetStrafe, killAura),
                swingAnimation = new SwingAnimation(killAura),
                targetESP = new TargetESP(killAura),
                world = new World(),
                elytraFly = new ElytraFly(),
                chinaHat = new ChinaHat(),
                snow = new WorldParticles(),
                particles = new Particles(),
                jumpCircle = new JumpCircle(),
                predictions = new Predictions(),
                noEntityTrace = new NoEntityTrace(),
                itemScroller = new ItemScroller(),
                autoFish = new AutoFish(),
                spider = new Spider(),
                timer = new Timer(),
                nameProtect = new NameProtect(),
                noInteract = new NoInteract(),
                glassHand = new GlassHand(),
                selfDestruct = new PANIC(),
                leaveTracker = new LeaveTracker(),
                antiAFK = new AntiAFK(),
                betterMinecraft = new BetterMinecraft(),
                babyBoy = new BabyBoy(),
                hotbar = new Hotbar(),
                autoBuyUI = new AutoBuyUI(),
                elytraSpeed = new ElytraSpeed(),
                new XrayBypass(),
                new Parkour(),
                new RWHelper(),
                new Jesus(),
                new WaterSpeed(),
                new FullBright(),
                new Optimization(),
                new PearlTarget(),
                new SaveInventory(),
                new PigJump(),
                new FTclip());

        ResoluteInfo.getInstance().getEventBus().register(this);
    }

    private void registerAll(Module... Functions) {
        Arrays.sort(Functions, Comparator.comparing(Module::getName));

        functions.addAll(List.of(Functions));
    }

    public List<Module> getSorted(Font font, float size) {
        return functions.stream().sorted((f1, f2) -> Float.compare(font.getWidth(f2.getName(), size), font.getWidth(f1.getName(), size))).toList();
    }

    @Subscribe
    private void onKey(EventKey e) {
        if (selfDestruct.unhooked) return;
        for (Module Function : functions) {
            if (Function.getBind() == e.getKey()) {
                Function.toggle();
            }
        }
    }
}
