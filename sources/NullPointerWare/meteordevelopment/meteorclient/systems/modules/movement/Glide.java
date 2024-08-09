package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public final class Glide extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("Mode")
        .description("Select the Glide mode.")
        .defaultValue(Mode.Vulcant)
        .build()
    );

    // Vulcan
    private int Vulcanticks;
    private int minemaliaticks;

    public Glide() {
        super(Categories.Movement, "Glide", "Glide down!");
    }

    @EventHandler
   private void onTick(final TickEvent.Pre e) {
        if (mc.player == null || mc.world == null) return;
        if (mode.get().equals(Mode.Vulcant)) {
            if (mc.player.getVelocity().y <= -0.1) {
                int n = Vulcanticks;
                Vulcanticks = n + 1;
                if (Vulcanticks % 2 == 0) {
                    mc.player.setVelocity(mc.player.getVelocity().x, -0.1, mc.player.getVelocity().z);
                } else {
                    mc.player.setVelocity(mc.player.getVelocity().x, -0.16, mc.player.getVelocity().z);
                }
            } else {
                Vulcanticks = 0;
            }
        }
        if (mode.get().equals(Mode.Minemalia)) {
            minemaliaticks++;
            if (minemaliaticks >= 15) {
                if (mc.player.getVelocity().y <= -0.1) {
                    int n = Vulcanticks;
                    Vulcanticks = n + 1;
                    if (Vulcanticks % 2 == 0) {
                        mc.player.setVelocity(mc.player.getVelocity().x, -0.1, mc.player.getVelocity().z);
                    } else {
                        mc.player.setVelocity(mc.player.getVelocity().x, -0.16, mc.player.getVelocity().z);
                    }
                } else {
                    Vulcanticks = 0;
                }
                if (minemaliaticks >= 25) minemaliaticks = 0;
            }
        }
        if (mode.get().equals(Mode.Vanilla)) {
            mc.player.setOnGround(true);
            mc.player.setVelocity(mc.player.getVelocity().x,-0.045, mc.player.getVelocity().z);
        }
    }

    public enum Mode {
        Vulcant,
        Minemalia,
        Vanilla
    }
}
