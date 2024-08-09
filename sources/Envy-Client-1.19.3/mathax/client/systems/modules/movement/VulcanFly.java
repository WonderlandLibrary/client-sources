package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.MoveHelper2;
import net.minecraft.item.Items;

public class VulcanFly extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    double startHeight;

    public VulcanFly() {
        super(Categories.Movement, Items.AIR, "VulcanFly", "Vulcan Fly Bypass");
    }

    private final Setting<Double> clip = sgGeneral.add(new DoubleSetting.Builder()
        .name("clip")
        .description("The clip amount.")
        .defaultValue(10)
        .min(1)
        .sliderRange(1, 100)
        .build()
    );

    // should clip
    private final Setting<Boolean> ShouldClip = sgGeneral.add(new BoolSetting.Builder()
        .name("Clip")
        .description("Should clip.")
        .defaultValue(false)
        .build()
    );

    @Override
    public boolean onActivate() {

        startHeight = mc.player.getY();
        if (ShouldClip.get()) {
            mc.player.updatePosition(mc.player.getX(), mc.player.getY() + clip.get(), mc.player.getZ());
        }
        return false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null) return;

        double clipHeight = startHeight - clip.get();
        //System.out.println("The Player Height is " + mc.player.getY() + "\n And the clip height is " + mc.player.getY());

        if (mc.player.fallDistance > 2) {
            mc.player.setOnGround(true);
            mc.player.fallDistance = 0f;
        }
        if (mc.player.age % 3 == 0) {
            MoveHelper2.motionYPlus(0.026);
        } else {
            MoveHelper2.motionY(-0.0991);
        }
    }
}
