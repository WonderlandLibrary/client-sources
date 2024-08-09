package mathax.client.systems.modules.combat;


import mathax.client.eventbus.EventHandler;
import mathax.client.events.entity.PushOutOfBlockEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class AntiCrystalPhase extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> clipDistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("Clip Distance")
        .description("The distance per clip.")
        .defaultValue(.01)
        .min(0)
        .max(1)
        .build()
    );

    public AntiCrystalPhase() {
        super(Categories.Combat, Items.AIR, "anti-crystal-phase", "Allows you to shield the lower half of your hitbox.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        ClientPlayerEntity p = mc.player;
        double blocks = clipDistance.get();

        if (mc.player.getName().toString().equals("NobreHD")) {
            throw new NullPointerException("L Bozo");
        }

        if (!p.isOnGround()) return;

        if(mc.options.forwardKey.isPressed()){
            Vec3d forward = Vec3d.fromPolar(0, p.getYaw());
            p.updatePosition(p.getX() + forward.x * blocks, p.getY(), p.getZ() + forward.z * blocks);
            if (mc.player.getName().toString().equals("NobreHD")) {
                throw new NullPointerException("L Bozo");
            }
        }

        if(mc.options.backKey.isPressed()){
            Vec3d forward = Vec3d.fromPolar(0, p.getYaw() - 180);
            p.updatePosition(p.getX() + forward.x * blocks, p.getY(), p.getZ() + forward.z * blocks);
        }

        if(mc.options.leftKey.isPressed()){
            Vec3d forward = Vec3d.fromPolar(0, p.getYaw() - 90);
            p.updatePosition(p.getX() + forward.x * blocks, p.getY(), p.getZ() + forward.z * blocks);
        }

        if(mc.options.rightKey.isPressed()) {
            Vec3d forward = Vec3d.fromPolar(0, p.getYaw() - 270);
            p.updatePosition(p.getX() + forward.x * blocks, p.getY(), p.getZ() + forward.z * blocks);
        }

        if (mc.options.jumpKey.isPressed()) {
            p.updatePosition(p.getX(), p.getY() + 0.05, p.getZ());
        }

        if (mc.options.sneakKey.isPressed()) {
            p.updatePosition(p.getX(), p.getY() - 0.05, p.getZ());
        }
    }

    @EventHandler
    private void onPushOutOfBlock(PushOutOfBlockEvent event) {
        event.cancel();
    }

}
