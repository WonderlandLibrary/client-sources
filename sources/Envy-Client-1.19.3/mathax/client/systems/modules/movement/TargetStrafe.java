package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Jebus.Interactions;
import mathax.client.utils.entity.TargetUtils;
import mathax.client.utils.misc.input.Input;
import mathax.client.utils.world.RotationHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import mathax.client.utils.entity.SortPriority;

public class TargetStrafe extends Module {

    public enum MoveMode{Basic, Scroll}
    public enum ExecuteMode{Tick, Move}


    public TargetStrafe() {
        super(Categories.Movement, Items.BARRIER, "target-strafe", "automatically circle your target");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<MoveMode> mode = sgGeneral.add(new EnumSetting.Builder<MoveMode>().name("mode").defaultValue(MoveMode.Basic).build());
    public final Setting<ExecuteMode> executeMode = sgGeneral.add(new EnumSetting.Builder<ExecuteMode>().name("execute-mode").defaultValue(ExecuteMode.Tick).build());
    public final Setting<Double> targetRange = sgGeneral.add(new DoubleSetting.Builder().name("target-range").defaultValue(7).sliderRange(1, 30).build());
    public final Setting<Double> radius = sgGeneral.add(new DoubleSetting.Builder().name("radius").defaultValue(1.9).sliderRange(1, 30).build());
    public final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder().name("speed").defaultValue(0.24).sliderRange(1, 30).build());
    public final Setting<Double> scrollSpeed = sgGeneral.add(new DoubleSetting.Builder().name("scroll-speed").defaultValue(0.26).sliderRange(1, 30).visible(() -> mode.get() == MoveMode.Scroll).build());
    public final Setting<Boolean> damageBoost = sgGeneral.add(new BoolSetting.Builder().name("damage-boost").defaultValue(false).build());
    public final Setting<Double> boost = sgGeneral.add(new DoubleSetting.Builder().name("boost").defaultValue(0.09).sliderRange(1, 30).visible(damageBoost::get).build());

    private PlayerEntity target;
    private int direction = 1;

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (executeMode.get() == ExecuteMode.Tick) run();
    }

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        if (executeMode.get() == ExecuteMode.Move) run();
    }

    private void run() {
        target = TargetUtils.getPlayerTarget(targetRange.get(), SortPriority.Closest_Angle); // find target
        if (TargetUtils.isBadTarget(target, targetRange.get())) return;

        if (mc.player.isOnGround()) mc.player.jump(); // jump if needed, set direction
        if (mc.options.leftKey.isPressed()) {
            direction = 1;
            setPressed(mc.options.forwardKey, true);

        } else if (mc.options.rightKey.isPressed()) {
            direction = -1;
        }
        if (mc.player.horizontalCollision) direction = direction == 1 ? -1 : 1; // check collision

        double speed = damageBoost.get() && mc.player.hurtTime != 0 ? this.speed.get() + boost.get() : this.speed.get(); // set speed + movement factor
        double forward = mc.player.distanceTo(target) > radius.get() ? 1 : 0;

        float yaw = RotationHelper.lookAtEntity(target)[0]; // calculate rotation
        mc.player.bodyYaw = yaw;
        mc.player.headYaw = yaw;

        switch (mode.get()) {
            case Basic -> getBasic(yaw, speed, forward, direction);
            case Scroll -> getScroll(target, speed);
        }
    }

    private void setPressed(KeyBinding key, boolean pressed) {
        key.setPressed(pressed);
        Input.setKeyState(key, pressed);
    }

    private void getScroll(PlayerEntity target, double speed) {
        double c1 = (mc.player.getX() - target.getX()) / (Math.sqrt(Math.pow(mc.player.getX() - target.getX(), 2) + Math.pow(mc.player.getZ() - target.getZ(), 2)));
        double s1 = (mc.player.getZ() - target.getZ()) / (Math.sqrt(Math.pow(mc.player.getX() - target.getX(), 2) + Math.pow(mc.player.getZ() - target.getZ(), 2)));
        double x = speed * s1 * direction - scrollSpeed.get() * speed * c1;
        double z = -speed * c1 * direction - scrollSpeed.get() * speed * s1;
        Interactions.setHVelocity(x, z);
    }

    private void getBasic(float yaw, double speed, double forward, double direction) {
        if (forward != 0.0D) {
            if (direction > 0.0D) {
                yaw += (float) (forward > 0.0D ? -45 : 45);
            } else if (direction < 0.0D) {
                yaw += (float) (forward > 0.0D ? 45 : -45);
            }
            direction = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }

        double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
        double sin = Math.sin(Math.toRadians((yaw + 90.0F)));

        double x = forward * speed * cos + direction * speed * sin;
        double z = forward * speed * sin - direction * speed * cos;
        Interactions.setHVelocity(x, z);
    }

}
