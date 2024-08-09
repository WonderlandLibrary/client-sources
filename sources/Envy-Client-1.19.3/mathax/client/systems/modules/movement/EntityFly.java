package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.LivingEntityMoveEvent;
import mathax.client.mixininterface.IVec3d;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class EntityFly extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("speed")
        .description("Horizontal speed in blocks per second.")
        .defaultValue(10)
        .min(0)
        .sliderMax(50)
        .build()
    );

    private final Setting<Double> verticalSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("vertical-speed")
        .description("Vertical speed in blocks per second.")
        .defaultValue(6)
        .min(0)
        .sliderMax(20)
        .build()
    );

    private final Setting<Double> fallSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("fall-speed")
        .description("How fast you fall in blocks per second.")
        .defaultValue(0.1)
        .min(0)
        .build()
    );

    // Constructor

    public EntityFly() {
        super(Categories.Movement, Items.AIR, "entity-fly", "Allows you to fly with any entity.");
    }

    // Living Entity Move Event

    @EventHandler
    private void onLivingEntityMove(LivingEntityMoveEvent event) {
        if (event.entity.getPrimaryPassenger() == mc.player) {
            event.entity.setYaw(mc.player.getYaw());

            // Horizontal Movement

            Vec3d vel = PlayerUtils.getHorizontalVelocity(speed.get());
            double velX = vel.getX();
            double velY = 0;
            double velZ = vel.getZ();

            // Vertical Movement

            if (mc.options.jumpKey.isPressed()) velY += verticalSpeed.get() / 20;
            if (mc.options.sprintKey.isPressed()) velY -= verticalSpeed.get() / 20;
            else velY -= fallSpeed.get() / 20;

            // Apply Velocity

            ((IVec3d) event.entity.getVelocity()).set(velX, velY, velZ);
        }
    }
}
