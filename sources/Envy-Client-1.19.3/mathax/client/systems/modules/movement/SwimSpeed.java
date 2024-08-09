package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.SwimSpeedUtils;
import net.minecraft.item.Items;

import java.util.Objects;

import static mathax.client.MatHax.mc;

//This is utterly fucking retarded
public class SwimSpeed extends Module {

    public SwimSpeed() {
        super(Categories.Movement, Items.SCULK_SENSOR, "swim-speed", "Speedy");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> velocityMultiplier = sgGeneral.add(new DoubleSetting.Builder()
        .name("velocity-multiplier")
        .description("The velocity multiplier.")
        .defaultValue(1.0)
        .min(0)
        .sliderMax(10)
        .build()
    );

    @EventHandler
    public boolean onTick(TickEvent.Post event) {
        if (mc.options.forwardKey.isPressed() && Objects.requireNonNull(mc.player).isSwimming() && mc.player.isSubmergedInWater()) {
            SwimSpeedUtils.throwPlayer(velocityMultiplier.get());
        }
        return false;
    }
}
