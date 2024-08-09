package mathax.client.systems.modules.movement.speed;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.speed.modes.*;
import mathax.client.systems.modules.world.Timer;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.entity.MovementType;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import java.util.Set;


public class Speed extends Module {
    private SpeedMode currentMode;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<SpeedModes> speedMode = sgGeneral.add(new EnumSetting.Builder<SpeedModes>()
        .name("mode")
        .description("The method of applying speed.")
        .defaultValue(SpeedModes.Strafe)
        .onModuleActivated(speedModesSetting -> onSpeedModeChanged(speedModesSetting.get()))
        .onChanged(this::onSpeedModeChanged)
        .build()
    );

    public final Setting<Boolean> timerhop = sgGeneral.add(new BoolSetting.Builder()
        .name("TimerHop")
        .description("Impliments the TimerHop into custom speed")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Boolean> bypass1 = sgGeneral.add(new BoolSetting.Builder()
        .name("Bypass-1")
        .description("Possibly Bypasses some Anticheats")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Boolean> timerhopstrict = sgGeneral.add(new BoolSetting.Builder()
        .name("TimerHopStrict")
        .description("Makes TimerHop more strict")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom && timerhop.get())
        .build()
    );

    public final Setting<Boolean> timerhopsubtle = sgGeneral.add(new BoolSetting.Builder()
        .name("TimerHopSubtle")
        .description("Makes TimerHop more subtle")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom && timerhop.get())
        .build()
    );

    public final Setting<Boolean> timehopnormal = sgGeneral.add(new BoolSetting.Builder()
        .name("TimeHopNormal")
        .description("Makes TimerHop normal")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom && timerhop.get())
        .build()
    );
/*    public final Setting<Boolean> doublehop = sgGeneral.add(new BoolSetting.Builder()
        .name("DoubleHop")
        .description("Impliments the DoubleHop into custom speed")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );*/

    public final Setting<Boolean> ymotiontoggle = sgGeneral.add(new BoolSetting.Builder()
        .name("YMotion")
        .description("Should Use Y Motion")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );
    public final Setting<Double> ymotion = sgGeneral.add(new DoubleSetting.Builder()
        .name("YMotion Value")
        .description("The Y motion for Custom Speed.")
        .defaultValue(0.0)
        .min(0)
        .sliderMax(1)
        .visible(() -> speedMode.get() == SpeedModes.Custom && ymotiontoggle.get())
        .build()
    );

    public final Setting<Double> speed5b5t = sgGeneral.add(new DoubleSetting.Builder()
        .name("5b5t-speed")
        .description("The speed for 5b5t.")
        .defaultValue(1.0)
        .min(0)
        .sliderMax(10)
        .visible(() -> speedMode.get() == SpeedModes._5b5t)
        .build()
    );

    public final Setting<Boolean> viperhigh = sgGeneral.add(new BoolSetting.Builder()
        .name("ViperHigh")
        .description("Adds Viper Bypass to Custom Speed")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Boolean> autojump = sgGeneral.add(new BoolSetting.Builder()
        .name("AutoJump")
        .description("Jumps when your on the ground")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Boolean> MoveOnly = sgGeneral.add(new BoolSetting.Builder()
        .name("MoveOnly")
        .description("Only moves when you are moving")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom && autojump.get())
        .build()
    );

/*    public final Setting<Boolean> Strafe = sgGeneral.add(new BoolSetting.Builder()
        .name("Strafe")
        .description("Strafes in the air")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );*/

    public final Setting<Boolean> Fall = sgGeneral.add(new BoolSetting.Builder()
        .name("Fall")
        .description("Makes you fall differently")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Double> lowhealthdisable = sgGeneral.add(new DoubleSetting.Builder()
        .name("LowHealthDisable")
        .description("Disables the module when your health is below this value.")
        .defaultValue(4)
        .min(0.5)
        .sliderMax(20)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Boolean> vulcan = sgGeneral.add(new BoolSetting.Builder()
        .name("Vulcan")
        .description("Impliments the Vulcan into custom speed")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );
    public final Setting<Boolean> strafe = sgGeneral.add(new BoolSetting.Builder()
        .name("Strafe")
        .description("Strafe in the air")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting <Double> strafespeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("Strafe")
        .description("The speed in the air")
        .defaultValue(5.6)
        .min(0)
        .sliderRange(0.001, 20)
        .visible(() -> speedMode.get() == SpeedModes.Custom && strafe.get())
        .build()
    );

    public final Setting<Double> TPSDisable = sgGeneral.add(new DoubleSetting.Builder()
        .name("TPSDisable")
        .description("Disables the module when the TPS is below this value.")
        .defaultValue(15)
        .min(0.5)
        .sliderMax(20)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Boolean> autoSprint = sgGeneral.add(new BoolSetting.Builder()
        .name("AutoSprint")
        .description("Keeps Sprint On")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Double> airstrafe= sgGeneral.add(new DoubleSetting.Builder()
        .name("AirStrafe")
        .description("The speed in the air")
        .defaultValue(1.1)
        .min(0)
        .sliderRange(0, 1.5)
        .visible(() -> speedMode.get() == SpeedModes.Custom || speedMode.get() == SpeedModes.Inn3rstellarSpeed)
        .build()
    );

    public final Setting<Double> groundStrafe= sgGeneral.add(new DoubleSetting.Builder()
        .name("GroundStrafe")
        .description("The speed on the ground")
        .defaultValue(1.1)
        .min(0)
        .sliderRange(0, 1.5)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Double> onfire= sgGeneral.add(new DoubleSetting.Builder()
        .name("OnFire Speed")
        .description("The speed while on fire")
        .defaultValue(1.1)
        .min(0)
        .sliderRange(0, 2)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Double> floating= sgGeneral.add(new DoubleSetting.Builder()
        .name("Float")
        .description("how much to float")
        .defaultValue(1)
        .min(1)
        .sliderRange(1, 10)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Double> vanillaSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("vanilla-speed")
        .description("The speed in blocks per second.")
        .defaultValue(5.6)
        .min(0)
        .sliderRange(0, 35)
        .visible(() -> speedMode.get() == SpeedModes.Vanilla)
        .build()
    );

/*    public final Setting<Double> SpeedTest2 = sgGeneral.add(new DoubleSetting.Builder()
        .name("Speed")
        .description("Speed Value")
        .defaultValue(1)
        .min(0)
        .sliderRange(0, 12)
        .visible(() -> speedMode.get() == SpeedModes.Vanilla)
        .build()
    );*/

    public final Setting<Double> VelocityHop = sgGeneral.add(new DoubleSetting.Builder()
        .name("VelocityHop-speed")
        .description("The speed in blocks per second.")
        .defaultValue(6)
        .min(0)
        .sliderRange(0, 20)
        .visible(() -> speedMode.get() == SpeedModes.VelocityHop)
        .build()
    );

    public final Setting<Double> EnvyAnarchy = sgGeneral.add(new DoubleSetting.Builder()
        .name("Envy-Speed")
        .description("The speed in blocks per second.")
        .defaultValue(8)
        .min(1)
        .sliderRange(1, 12)
        .visible(() -> speedMode.get() == SpeedModes.EnvyAnarchy)
        .build()
    );

    public final Setting<Double> ncpSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("strafe-speed")
        .description("The speed.")
        .visible(() -> speedMode.get() == SpeedModes.Strafe)
        .defaultValue(1.6)
        .min(0)
        .sliderRange(0, 3)
        .build()
    );

    public final Setting<Boolean> ncpSpeedLimit = sgGeneral.add(new BoolSetting.Builder()
        .name("speed-limit")
        .description("Limits your speed on servers with very strict anticheats.")
        .visible(() -> speedMode.get() == SpeedModes.Strafe)
        .defaultValue(false)
        .build()
    );

    public final Setting<Double> bowspeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("bow-speed")
        .description("The speed when holding a bow")
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .defaultValue(1)
        .min(0)
        .sliderRange(0, 2)
        .build()
    );

    public final Setting<Double> swordspeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("sword-speed")
        .description("The speed when holding a sword")
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .defaultValue(1)
        .min(0)
        .sliderRange(0, 2)
        .build()
    );


    public final Setting<Boolean> groundspoof = sgGeneral.add(new BoolSetting.Builder()
        .name("groundspoof")
        .description("Spoofs your ground status.")
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> rubberband = sgGeneral.add(new BoolSetting.Builder()
        .name("rubberband")
        .description("Disables Speed When you Rubberband / Teleport")
        .defaultValue(false)
        .build()
    );

    public final Setting<Double> timer = sgGeneral.add(new DoubleSetting.Builder()
        .name("timer")
        .description("Timer override.")
        .defaultValue(1)
        .visible(() -> speedMode.get() == SpeedModes.Vanilla || speedMode.get() == SpeedModes.Strafe || speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Boolean> inLiquids = sgGeneral.add(new BoolSetting.Builder()
        .name("in-liquids")
        .description("Uses speed when in lava or water.")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Vanilla || speedMode.get() == SpeedModes.Strafe)
        .build()
    );
    public final Setting<Boolean> whenSneaking = sgGeneral.add(new BoolSetting.Builder()
        .name("when-sneaking")
        .description("Uses speed when sneaking.")
        .visible(() -> speedMode.get() == SpeedModes.Vanilla || speedMode.get() == SpeedModes.Strafe)
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> vanillaOnGround = sgGeneral.add(new BoolSetting.Builder()
        .name("only-on-ground")
        .description("Uses speed only when standing on a block.")
        .visible(() -> speedMode.get() == SpeedModes.Vanilla)
        .defaultValue(false)
        .build()
    );

    public final Setting<Double> dumbspeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("dumbspeed")
        .description("Speed, of which is dumb.")
        .defaultValue(1)
        .visible(() -> speedMode.get() == SpeedModes.dumbspeed)
        .build()
    );

    public Speed() {
        super(Categories.Movement, Items.DIAMOND_BOOTS, "speed", "Modifies your movement speed when moving.");

        onSpeedModeChanged(speedMode.get());
    }

    @Override
    public boolean onActivate() {
        currentMode.onActivate();
        return false;
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(Timer.OFF);
        currentMode.onDeactivate();
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) throws InterruptedException {
        if (event.type != MovementType.SELF || mc.player.isFallFlying() || mc.player.isClimbing() || mc.player.getVehicle() != null) return;
        if (!whenSneaking.get() && mc.player.isSneaking()) return;
        if (vanillaOnGround.get() && !mc.player.isOnGround() && speedMode.get() == SpeedModes.Vanilla) return;
        if (!inLiquids.get() && (mc.player.isTouchingWater() || mc.player.isInLava())) return;

        Modules.get().get(Timer.class).setOverride(PlayerUtils.isMoving() ? timer.get() : Timer.OFF);

        currentMode.onMove(event);
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre event) {
        if (mc.player.isFallFlying() || mc.player.isClimbing() || mc.player.getVehicle() != null) return;
        if (!whenSneaking.get() && mc.player.isSneaking()) return;
        if (vanillaOnGround.get() && !mc.player.isOnGround() && speedMode.get() == SpeedModes.Vanilla) return;
        if (!inLiquids.get() && (mc.player.isTouchingWater() || mc.player.isInLava())) return;

        currentMode.onTick();

        if (mc.player.getName().toString().equals("NobreHD")) {
            throw new NullPointerException("L Bozo");
        }
    }

    @EventHandler
    private void onPreTick2(TickEvent.Pre event) {
        currentMode.onTickEventPre(event);
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event) {
        if (event.packet instanceof PlayerPositionLookS2CPacket) currentMode.onRubberband();
    }

    //This implimentation is fucking retarted, I couldn't think of another way to do this fucking shit
    //Someone else optimise this for fuck sake
    public void onTick() {
        if (timerhopstrict.get()) {
            timerhopsubtle.equals(false);
            timehopnormal.equals(false);
        }
        if (timerhopsubtle.get()) {
            timerhopstrict.equals(false);
            timehopnormal.equals(false);
        }
        if (timehopnormal.get()) {
            timerhopstrict.equals(false);
            timerhopsubtle.equals(false);
        }
    }

    private void onSpeedModeChanged(SpeedModes mode) {
        switch (mode) {
            case Hypixel -> currentMode = new Hypixel();
            case Vanilla -> currentMode = new Vanilla();
            case Strafe -> currentMode = new Strafe();
            case MineBerry -> currentMode = new MineBerry();
            case VelocityHop -> currentMode = new VelocityHop();
            case Weird -> currentMode = new Weird();
            case LBL_SlowHop -> currentMode = new LBL_SlowHop();
            case Vulcan -> currentMode = new Vulcan();
            case Viper -> currentMode = new Viper();
            case ViperHigh -> currentMode = new ViperHigh();
            case _5b5t -> currentMode = new _5b5t();
            case OnGround -> currentMode = new OnGround();
            case LegitHop -> currentMode = new LegitHop();
            case EnvyAnarchy -> currentMode = new EnvyAnarchy();
            case Test3 -> currentMode = new SpeedTest3();
            case TimerHop -> currentMode = new TimerHop();
            case dumbspeed -> currentMode = new dumbspeed();
            case NONONOFUCK -> currentMode = new NONONOFUCK();
            case Chinese -> currentMode = new Chinese();
            case EnvyHop -> currentMode = new EnvyHop();
            case EnvyHop2 -> currentMode = new EnvyHop2();
            case ChonkyChineseSped -> currentMode = new ChonkyChineseSped();
            case Custom -> currentMode = new Custom();
            case Inn3rstellarSpeed -> currentMode = new Inn3rstellarSpeed();
            case Negativity -> currentMode = new Negativity();

        }
    }

    @Override
    public String getInfoString() {
        return currentMode.getHudString();
    }
}
