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

public class FastBow extends Module {

    public FastBow() {
        super(Categories.Player, Items.BOW, "fast-bow", "Allows you to shoot faster.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> timer = sgGeneral.add(new DoubleSetting.Builder()
        .name("timer")
        .description("The timer speed to use while using a Bow.")
        .defaultValue(2)
        .min(1)
        .sliderMax(10)
        .build()
    );

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player.isUsingItem() && mc.player.getActiveItem().getItem() == Items.BOW) {
            Modules.get().get(Timer.class).setOverride(timer.get());
        }
        if (mc.player.isUsingItem() && mc.player.getActiveItem().getItem() == Items.CROSSBOW) {
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
