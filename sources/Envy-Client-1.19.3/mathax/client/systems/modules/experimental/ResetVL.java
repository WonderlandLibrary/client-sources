package mathax.client.systems.modules.experimental;

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

public class ResetVL extends Module {
    public ResetVL() {
        super(Categories.Experimental, Items.AIR, "ResetVL", "Tries to reset your violation level");
    }
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Double> timer = sgGeneral.add(new DoubleSetting.Builder()
        .name("Timer")
        .description("What timer to use")
        .defaultValue(1.1)
        .min(0)
        .sliderMax(5)
        .build()
    );

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player != null && mc.world != null){
            Modules.get().get(Timer.class).setOverride(timer.get());
            if (mc.player.isOnGround())
                mc.player.jump();}
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(Timer.OFF);
    }
}
