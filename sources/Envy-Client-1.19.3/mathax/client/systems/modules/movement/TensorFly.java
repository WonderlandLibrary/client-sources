package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

//broken
public class TensorFly extends Module {

    public TensorFly() {
        super(Categories.Movement, Items.ELYTRA, "tensor-fly", "Tensor Client's Elytra Fly.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> stabley = sgGeneral.add(new BoolSetting.Builder()
        .name("stable-y")
        .description("Stabilizes your y position.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> rotationy = sgGeneral.add(new BoolSetting.Builder()
        .name("rotation-y")
        .description("Stabilizes your rotation.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> ignorefluids = sgGeneral.add(new BoolSetting.Builder()
        .name("ignore-fluids")
        .description("Allows you to fly through fluids.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> alwaysmoving = sgGeneral.add(new BoolSetting.Builder()
        .name("always-moving")
        .description("Makes you always move forward.")
        .defaultValue(true)
        .build()
    );

    private Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("speed")
        .description("The speed to fly at.")
        .defaultValue(1)
        .min(0)
        .sliderMax(10)
        .build()
    );

    private float getSpeed() {
        return (float) (speed.get() / 10);
    }

    private Vec3d getFlyingVelocity() {
        double x = mc.player.getRotationVector().getX() * getSpeed();
        double y = mc.player.getRotationVector().getY();
        double z = mc.player.getRotationVector().getZ() * getSpeed();

        if (rotationy.get()) y *= getSpeed();

        return new Vec3d(x, stabley.get() ? 0 : y, z);
    }

    private boolean areButtonsDown() {
        if (mc.options.forwardKey.isPressed()) return true;
        else if (mc.options.backKey.isPressed()) return true;
        else if (mc.options.leftKey.isPressed()) return true;
        else if (mc.options.rightKey.isPressed()) return true;

        if (rotationy.get()) {
            if (mc.options.sneakKey.isPressed()) return true;
            else return mc.options.sneakKey.isPressed();
        }

        return false;
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (!mc.player.isFallFlying()) return;

        mc.player.getAbilities().flying = false;

        if (areButtonsDown() || alwaysmoving.get()) {
            mc.player.setVelocity(getFlyingVelocity());

        } else {
            mc.player.setVelocity(0, 0, 0);
        }

        if (!stabley.get() && !rotationy.get()) {
            if (mc.options.jumpKey.isPressed()) {
                mc.player.setVelocity(mc.player.getVelocity().add(0, getSpeed(), 0));
            } else if (mc.options.sneakKey.isPressed()) {
                mc.player.setVelocity(mc.player.getVelocity().add(0, -getSpeed(), 0));
            }
        }
    }
}
