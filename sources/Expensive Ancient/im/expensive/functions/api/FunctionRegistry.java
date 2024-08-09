package im.expensive.functions.api;

import com.google.common.eventbus.Subscribe;
import im.expensive.Expensive;
import im.expensive.events.EventKey;
import im.expensive.functions.impl.combat.*;
import im.expensive.functions.impl.misc.*;
import im.expensive.functions.impl.movement.*;
import im.expensive.functions.impl.player.*;
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

    private final SwingAnimation swingAnimation;
    private final HUD hud;
    private final AutoGapple autoGapple;
    private final AutoSprint autoSprint;
    private final Velocity velocity;
    private final NoRender noRender;
    private final Timer timer;
    private final AutoTool autoTool;
    private final xCarry xcarry;
    private final ElytraHelper elytrahelper;
    private final Phase phase;
    private final AutoBuyUI autoBuyUI;
    private final ItemSwapFix itemswapfix;
    private final AutoPotion autopotion;
    private final TriggerBot triggerbot;
    private final NoJumpDelay nojumpdelay;
    private final ClickFriend clickfriend;
    private final InventoryMove inventoryMove;
    private final ESP esp;
    private final AutoTransfer autoTransfer;
    private final GriefHelper griefHelper;
    private final ItemCooldown itemCooldown;
    private final ClickPearl clickPearl;
    private final AutoSwap autoSwap;
    private final AutoArmor autoArmor;
    private final Hitbox hitbox;
    private final HitSound hitsound;
    private final AntiPush antiPush;
    private final FreeCam freeCam;
    private final ChestStealer chestStealer;
    private final AutoLeave autoLeave;
    private final AutoAccept autoAccept;
    private final AutoRespawn autoRespawn;
    private final Fly fly;
    private final TargetStrafe targetStrafe;
    private final ClientSounds clientSounds;
    private final AutoTotem autoTotem;
    private final NoSlow noSlow;
    private final Pointers pointers;
    private final AutoExplosion autoExplosion;
    private final NoRotate noRotate;
    private final KillAura killAura;
    private final AntiBot antiBot;
    private final Trails trails;
    private final Crosshair crosshair;
    private final Strafe strafe;
    private final World world;
    private final ViewModel viewModel;
    private final ElytraFly elytraFly;
    private final ChinaHat chinaHat;
    private final Snow snow;
    private final Particles particles;
    private final TargetESP targetESP;
    private final JumpCircle jumpCircle;
    private final ItemPhysic itemPhysic;
    private final Predictions predictions;
    private final NoEntityTrace noEntityTrace;
    private final NoClip noClip;
    private final ItemScroller itemScroller;
    private final AutoFish autoFish;
    private final StorageESP storageESP;
    private final Spider spider;
    private final NameProtect nameProtect;
    private final NoInteract noInteract;
    private final GlassHand glassHand;
    private final Tracers tracers;
    private final SelfDestruct selfDestruct;
    private final LeaveTracker leaveTracker;
    private final AntiAFK antiAFK;
    private final PortalGodMode portalGodMode;
    private final BetterMinecraft betterMinecraft;
    private final Backtrack backtrack;
    private final SeeInvisibles seeInvisibles;
    private final FastPlace fastPlace;

    public FunctionRegistry() {
        registerAll(hud = new HUD(), autoGapple = new AutoGapple(),  autoSprint = new AutoSprint(), velocity = new Velocity(), noRender = new NoRender(), autoTool = new AutoTool(), xcarry = new xCarry(), seeInvisibles = new SeeInvisibles(), elytrahelper = new ElytraHelper(), phase = new Phase(), itemswapfix = new ItemSwapFix(), autopotion = new AutoPotion(), noClip = new NoClip(), triggerbot = new TriggerBot(), nojumpdelay = new NoJumpDelay(), clickfriend = new ClickFriend(), inventoryMove = new InventoryMove(), esp = new ESP(), autoTransfer = new AutoTransfer(), griefHelper = new GriefHelper(), autoArmor = new AutoArmor(), hitbox = new Hitbox(), hitsound = new HitSound(), antiPush = new AntiPush(), autoBuyUI = new AutoBuyUI(), freeCam = new FreeCam(), chestStealer = new ChestStealer(), autoLeave = new AutoLeave(), autoAccept = new AutoAccept(), autoRespawn = new AutoRespawn(), fly = new Fly(), clientSounds = new ClientSounds(), noSlow = new NoSlow(), pointers = new Pointers(), autoExplosion = new AutoExplosion(), noRotate = new NoRotate(), antiBot = new AntiBot(), trails = new Trails(), crosshair = new Crosshair(), autoTotem = new AutoTotem(), itemCooldown = new ItemCooldown(), killAura = new KillAura(autopotion), clickPearl = new ClickPearl(itemCooldown), autoSwap = new AutoSwap(), targetStrafe = new TargetStrafe(killAura), strafe = new Strafe(targetStrafe, killAura), swingAnimation = new SwingAnimation(killAura), targetESP = new TargetESP(), world = new World(), viewModel = new ViewModel(), elytraFly = new ElytraFly(), chinaHat = new ChinaHat(), snow = new Snow(), particles = new Particles(), jumpCircle = new JumpCircle(), itemPhysic = new ItemPhysic(), predictions = new Predictions(), noEntityTrace = new NoEntityTrace(), itemScroller = new ItemScroller(), autoFish = new AutoFish(), storageESP = new StorageESP(), spider = new Spider(), timer = new Timer(), nameProtect = new NameProtect(), noInteract = new NoInteract(), glassHand = new GlassHand(), tracers = new Tracers(), selfDestruct = new SelfDestruct(), leaveTracker = new LeaveTracker(), antiAFK = new AntiAFK(), portalGodMode = new PortalGodMode(), betterMinecraft = new BetterMinecraft(), backtrack = new Backtrack(), new LongJump(), new XrayBypass(), new Parkour(), new RWHelper(), new SRPSpoffer(), fastPlace = new FastPlace());
        Expensive.getInstance().getEventBus().register(this);
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
        if (selfDestruct.enabled) return;
        for (Function Function : functions) {
            if (Function.getBind() == e.getKey()) {
                Function.toggle();
            }
        }
    }
}
