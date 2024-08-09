package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.world.Timer;
import mathax.client.utils.player.DamageBoostUtil;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.item.Items;

// Todo
//  Silent Sprint (Packet Sprint)
//  Universal KeepSprint (Silent Rotations toe Sprint Legit in that Direction Server Side) + Movement Correction (Silent)
public class Sprint extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public static final double OFF = 1;
    private double override = 1;

    // General

    private final Setting<Boolean> whenStationary = sgGeneral.add(new BoolSetting.Builder()
        .name("when-stationary")
        .description("Continues sprinting even if you do not move.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> MultiDirection = sgGeneral.add(new BoolSetting.Builder()
        .name("Multi-Direction")
        .description("Continues sprinting even if you change direction.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> keepSprint = sgGeneral.add(new BoolSetting.Builder()
        .name("KeepSprint")
        .description("Continues sprinting even if Attack Someone (Experimental).")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> hurtCheck = sgGeneral.add(new BoolSetting.Builder()
        .name("HurtCheck")
        .description("Checks if you are in Hurt-Time.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> onGround = sgGeneral.add(new BoolSetting.Builder()
        .name("onGround")
        .description("Checks if you are on the ground.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> Timer = sgGeneral.add(new BoolSetting.Builder()
        .name("Timer")
        .description("Enable timer")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> groundTimer = sgGeneral.add(new BoolSetting.Builder()
        .name("Ground-Timer")
        .description("Enable timer only when on ground")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> moveTimer = sgGeneral.add(new BoolSetting.Builder()
        .name("Move-Timer")
        .description("Enable timer only when moving")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> TimerMultiplier = sgGeneral.add(new DoubleSetting.Builder()
        .name("multiplier")
        .description("The timer multiplier amount.")
        .defaultValue(1)
        .min(0.1)
        .sliderRange(0.1, 3)
        .visible(() -> Timer.get())
        .build()
    );

    public Sprint() {
        super(Categories.Ghost, Items.DIAMOND_BOOTS, "sprint", "Automatically sprints.");
    }

    @Override
    public void onDeactivate() {
        mc.player.setSprinting(false);
        Modules.get().get(Timer.class).setOverride(1); //Should Fix Timer bugging out when Disabling
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (onGround.get() && !mc.player.isOnGround() && mc.player.isSprinting()) mc.player.setSprinting(false);
        if (mc.player.isSprinting() && hurtCheck.get() && DamageBoostUtil.isHurtTime()) mc.player.setSprinting(false);
        else if (hurtCheck.get() && DamageBoostUtil.isHurtTime() && !mc.player.isSprinting()) return;
        if (mc.player.forwardSpeed > 0 && !whenStationary.get()) mc.player.setSprinting(true);
        else if (whenStationary.get()) mc.player.setSprinting(true);
        if (PlayerUtils.isMoving() && MultiDirection.get()) {
            mc.player.setSprinting(true);
        }
        if (Timer.get()) {
            if (moveTimer.get()) {
                if (PlayerUtils.isMoving()) {
                    Modules.get().get(Timer.class).setOverride(TimerMultiplier.get());
                } else Modules.get().get(Timer.class).setOverride(1);
            }
            if (groundTimer.get() && Timer.get() && mc.player.isOnGround()) {
                Modules.get().get(Timer.class).setOverride(TimerMultiplier.get());
            } else if (groundTimer.get() && !mc.player.isOnGround()) Modules.get().get(Timer.class).setOverride(1);
            else Modules.get().get(Timer.class).setOverride(TimerMultiplier.get());
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (keepSprint.get() && mc.player.handSwinging) mc.player.setSprinting(true); // experimental
    }
}

