package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;

public final class Clip extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public Clip() {
        super(Categories.Movement, "Clip", "Clips you up ig?");
    }

    private final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder()
        .name("height")
        .description("The Height you should be clipped")
        .defaultValue(10)
        .sliderMin(-200)
        .sliderMax(200)
        .build()
    );

    private final Setting<Boolean> noAir = sgGeneral.add(new BoolSetting.Builder()
        .name("no-in-air")
        .description("No clip if player is in air and nofall is on to prevent kicks...")
        .defaultValue(true)
        .build()
    );

    @Override
    public void onActivate() {

        if (Modules.get().isActive(NoFall.class) && Modules.get().get(NoFall.class).isVulcanMode() && !mc.player.isOnGround() && noAir.get()) {
            error("Can't clip while in air when nofall is on and vulcan mode is packet :)");
            return;
        }

        mc.player.updatePosition(mc.player.getX(), mc.player.getY() + range.get(), mc.player.getZ());
        super.onActivate();
        this.toggle();
    }
}
