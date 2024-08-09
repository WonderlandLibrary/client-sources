package mathax.client.systems.modules.player;

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

public class FastEat extends Module {

    public FastEat() {
        super(Categories.Player, Items.GOLDEN_APPLE, "fast-eat", "Allows you to eat faster.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> timer = sgGeneral.add(new DoubleSetting.Builder()
        .name("timer")
        .description("The timer speed to use while eating.")
        .defaultValue(2)
        .min(1)
        .sliderMax(10)
        .build()
    );

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player.isHolding(item -> item.isFood() && mc.player.isUsingItem())) {
            Modules.get().get(Timer.class).setOverride(timer.get());
        }
        else {
            Modules.get().get(Timer.class).setOverride(1);
        }
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(1);
    }
}
