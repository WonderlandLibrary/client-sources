package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

import static mathax.client.systems.modules.movement.FastFall.Mode.*;


public class FastFall extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public FastFall() {
        super(Categories.Movement, Items.ARMOR_STAND, "fast-fall", "Allows you to fall faster.");
    }

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("How to Handle Falling")
        .defaultValue(Mode.Vanilla)
        .build()
    );

    private final Setting<Double> LowHealthDisable = sgGeneral.add(new DoubleSetting.Builder()
        .name("LowHealthDisable")
        .description("Disables the module when your health is below this value.")
        .defaultValue(4)
        .min(0.5)
        .sliderMax(20)
        .build()
    );

    private final Setting<Boolean> Sprint = sgGeneral.add(new BoolSetting.Builder()
        .name("Sprint")
        .description("keeps sprint on | only works in vanilla mode")
        .defaultValue(false)
        .visible(() -> mode.get() == Vanilla)
        .build()
    );


    @EventHandler
    public void onTick(TickEvent.Post event) {

        if (mode.get() == Vanilla) {
            if (mc.player.fallDistance > 0.4) {
                mc.player.setVelocity(mc.player.getVelocity().x, -1.6, mc.player.getVelocity().z);
            }
        }
        if (mode.get() == Matrix) {
            if (mc.player.fallDistance > 0.8) {
                mc.player.setVelocity(0, -0.54, 0);
            }
        }
        if (mode.get() == CPVP) {
            if (mc.player.fallDistance > 0) {
                mc.player.setVelocity(0, -50, 0);
            }
        }
        if (mc.player.getHealth() < LowHealthDisable.get()) {
            toggle();
        }
        if (Sprint.get() && mode.get() == Vanilla) {
            mc.player.setSprinting(true);
        }
    }

    public enum Mode {
        Vanilla("Vanilla"),
        Matrix("Matrix"),
        CPVP("CPVP");

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
