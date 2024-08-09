package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

public final class SmoothAim extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public SmoothAim() {
        super(Categories.Combat, "SmoothAim", "Makes your aim SMOOTH");
    }

    public final Setting<Integer> sensitivity = sgGeneral.add(new IntSetting.Builder()
        .name("Sensitivity")
        .description("The sensitivity you should have when looking at a player")
        .defaultValue(10)
        .sliderMin(0)
        .sliderMax(200)
        .build()
    );

    private double normalSens;

    @Override
    public void onActivate() {
        normalSens = mc.options.getMouseSensitivity().getValue();
        super.onActivate();
    }

    @Override
    public void onDeactivate() {
        setMouseSensitivity(normalSens);
        super.onDeactivate();
    }

    private void onTick(final TickEvent.Pre e) {
       if (mc.targetedEntity == null)
           setMouseSensitivity(normalSens);

       if (mc.targetedEntity.isPlayer())
           setMouseSensitivity(sensitivity.get());
   }

   private void setMouseSensitivity(double sensitivity) {
        mc.options.getMouseSensitivity().setValue(sensitivity);
    }
}
