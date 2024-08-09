package wtf.shiyeno.modules;

import wtf.shiyeno.modules.impl.player.InventoryMoveFunction;
import wtf.shiyeno.modules.impl.player.NoPushFunction;
import wtf.shiyeno.modules.impl.render.*;
import wtf.shiyeno.modules.impl.combat.*;
import wtf.shiyeno.modules.impl.movement.*;
import wtf.shiyeno.modules.impl.player.*;
import wtf.shiyeno.modules.impl.util.*;
import wtf.shiyeno.modules.impl.util.NoCommands;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FunctionManager {

    private final List<Function> functions = new CopyOnWriteArrayList<>();
    public final ArrowsFunction arrowsFunction;
    public final FullBrightFunction fullBrightFunction;
    public final SprintFunction sprintFunction;
    public final FlightFunction flightFunction;
    public final StrafeFunction strafeFunction;
    public final TimerFunction timerFunction;
    public final AutoPotionFunction autoPotionFunction;
    public final AutoRespawnFunction autoRespawnFunction;
    public final VelocityFunction velocityFunction;
    public final AspectRatio aspectRatio;
    public final MiddleClickPearlFunction middleClickPearlFunction;
    public final ItemPhysic itemPhysic;
    public final AutoTotemFunction autoTotemFunction;
    public final InventoryMoveFunction inventoryMoveFunction;
    public final NoPushFunction noPushFunction;
    public final HitBoxFunction hitBoxFunction;

    public final NoSlowFunction noSlowFunction;
    public final AuraFunction auraFunction;
    public final NoServerRotFunction noServerRotFunction;
    public final FastBreakFunction fastBreakFunction;
    public final XRayFunction xRayFunction;
    public final SwingAnimationFunction swingAnimationFunction;
    public final AutoGAppleFunction autoGApple;
    public final NoRenderFunction noRenderFunction;
    public final GappleCooldownFunction gappleCooldownFunction;
    public final Optimization optimization;
    public final ItemScroller itemScroller;
    public final ESPFunction espFunction;
    public final NoInteractFunction noInteractFunction;
    public final CustomWorld customWorld;
    public final ClientSounds clientSounds;
    public final Crosshair crosshair;
    public final NameProtect nameProtect;
    public final NoCommands noCommands;
    public final UnHookFunction unhook;

    public final AutoExplosionFunction autoExplosionFunction;

    public final HitColor hitColor;
    public final FreeCam freeCam;

    public HUD2 hud2;

    public FunctionManager() {
        this.functions.addAll(Arrays.asList(
                this.crosshair = new Crosshair(),
                this.arrowsFunction = new ArrowsFunction(),
                this.fullBrightFunction = new FullBrightFunction(),
                this.noRenderFunction = new NoRenderFunction(),
                this.sprintFunction = new SprintFunction(),
                this.flightFunction = new FlightFunction(),
                this.strafeFunction = new StrafeFunction(),
                this.timerFunction = new TimerFunction(),
                this.velocityFunction = new VelocityFunction(),
                this.aspectRatio = new AspectRatio(),
                this.middleClickPearlFunction = new MiddleClickPearlFunction(),
                this.itemPhysic = new ItemPhysic(),
                this.autoTotemFunction = new AutoTotemFunction(),
                this.inventoryMoveFunction = new InventoryMoveFunction(),
                this.autoRespawnFunction = new AutoRespawnFunction(),
                this.noPushFunction = new NoPushFunction(),
                this.hitBoxFunction = new HitBoxFunction(),
                this.noSlowFunction = new NoSlowFunction(),
                this.noServerRotFunction = new NoServerRotFunction(),
                this.fastBreakFunction = new FastBreakFunction(),
                this.xRayFunction = new XRayFunction(),
                this.autoPotionFunction = new AutoPotionFunction(),
                this.swingAnimationFunction = new SwingAnimationFunction(),
                this.autoGApple = new AutoGAppleFunction(),
                this.gappleCooldownFunction = new GappleCooldownFunction(),
                this.optimization = new Optimization(),
                this.itemScroller = new ItemScroller(),
                this.espFunction = new ESPFunction(),
                this.noInteractFunction = new NoInteractFunction(),
                this.customWorld = new CustomWorld(),
                this.clientSounds = new ClientSounds(),
                this.nameProtect = new NameProtect(),
                this.hitColor = new HitColor(),
                new ElytraSwap(),
                this.auraFunction = new AuraFunction(),
                new WaterSpeed(),
                new DragonFlyFunction(),
                new GreifJoinerFunction(),
                unhook = new UnHookFunction(),
                new ShieldWarning(),
                new AutoTool(),
                new ChestStealer(),
                new Tracers(),
                new NoFriendDamage(),
                new ItemESP(),
                new PearlPrediction(),
                new AutoTpacceptFunction(),
                new MiddleClickFriendFunction(),
                new JumpCircleFunction(),
                this.autoExplosionFunction = new AutoExplosionFunction(),
                new AutoAncherFunction(),
                new TrailsFunction(),
                new BlockESP(),
                new SpeedFunction(),
                new ElytraFly(),
                new AntiAFKFunction(),
                new ItemSwapFixFunction(),
                new DeathCoordsFunction(),
                new SpiderFunction(),
                freeCam = new FreeCam(),
                new NoClip(),
                new Blink(),
                new NoCommands(),
                this.noCommands = new NoCommands(),
                new TriggerBot(),
                new AutoLeave(),
                new BackTrack(),
                new HitSound(),
                new Particles(),
                new Chams(),
                new NoDelay(),
                new AutoFish(),
                new AutoEat(),
                new ECAutoOpen(),
                new ChinaHatFunction(),
                new XCarry(),
                new ClickPearl(),
                new AutoEvents(),
                new GlowESP(),
                new RwStrafe(),
                new AntiBot(),
                new SeeInvisibles(),
                new TargetESP(),
                new SrpSpoofer(),
                new ExploitHat(),
                new AutoSwap(),
                new BindItems(),
                new BindBlock(),
                new BowSpammer(),
                new LeaveTracker(),
                new ClearInventory(),
                new GodMode(),
                new ScannerItems(),
                new ItemHelper(),
                new AutoPilot(),
                new LogsAC(),
                new FTHelperFunction(),
                hud2 = new HUD2()
        ));
    }

    /**
     * Возвращает список всех функций.
     *
     * @return список функций.
     */
    public List<Function> getFunctions() {
        return functions;
    }

    public Function get(String name) {
        for (Function function : functions) {
            if (function != null && function.name.equalsIgnoreCase(name)) {
                return function;
            }
        }
        return null;
    }
}