package meteordevelopment.meteorclient.systems.modules.world;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public final class Regen extends Module {

    public Regen() {
        super(Categories.Misc, "regen", "Heals you faster.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> packetamount = sgGeneral.add(new IntSetting.Builder()
        .name("packetamount")
        .description("How many exploit packets timer should send.")
        .defaultValue(1)
        .min(1)
        .sliderMax(100)
        .build()
    );


    private final Setting<Double> healthThreshold = sgGeneral.add(new DoubleSetting.Builder()
        .name("health-threshold")
        .description("The level of health you regen at.")
        .defaultValue(10)
        .range(1, 19)
        .sliderRange(1, 19)
        .build()
    );


    @EventHandler
    private void onTickPre(final TickEvent.Pre event) {
        if (mc.player.getHealth() < healthThreshold.get())
            for (int i = 0; i < packetamount.get(); i++) { emptyFull(); }
    }

}
