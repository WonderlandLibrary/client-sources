package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class AutoPush extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> distance = sgGeneral.add(new DoubleSetting.Builder()
        .name("delta")
        .description("the length of each teleport")
        .defaultValue(0.1)
        .sliderRange(0,1)
        .build()
    );

    public AutoPush() {
        super(Categories.Movement, Items.AIR, "autopush", "Automatically push entities");
    }

    @EventHandler
    private void ontick(TickEvent.Pre event){
        Vec3d ppos = mc.player.getPos();
        Vec3d tp = Vec3d.fromPolar(0, mc.player.getYaw()).normalize().multiply(distance.get());

        mc.player.setPosition(new Vec3d(ppos.x + tp.x, ppos.y, ppos.z + tp.z));
    }
}
