package mathax.client.systems.modules.ghost;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.world.Timer;
import net.minecraft.item.Items;

public class AutoBlock extends Module {

    public AutoBlock() {
        super(Categories.Ghost, Items.COMMAND_BLOCK, "auto-block", "Automatically blocks | Works best on 1.8 servers");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> timer = sgGeneral.add(new DoubleSetting.Builder()
        .name("timer")
        .description("The timer speed to use while Blocking.")
        .defaultValue(2)
        .min(1)
        .sliderMax(10)
        .build()
    );

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player.handSwingProgress > 0 && (mc.player.getOffHandStack().getItem().equals(Items.SHIELD))) {
            mc.options.useKey.setPressed(true);
            Modules.get().get(Timer.class).setOverride(timer.get());
        }
        else {
            mc.options.useKey.setPressed(false);
            Modules.get().get(Timer.class).setOverride(1);
        }
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(1);
    }
}
