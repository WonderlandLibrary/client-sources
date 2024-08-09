package mathax.client.systems.modules.render;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.render.HeldItemRendererEvent;
import mathax.client.settings.EnumSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.combat.KillAura;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;

public class Animations extends Module {
    private final SettingGroup general = settings.createGroup("General");


    public final Setting<Mode> mode = general.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Animation mode")
        .defaultValue(Mode.None)
        .build()
    );

    public Animations() {
        super(Categories.Render, Items.AIR, "Animations", "Sword Animations like 1.8");
    }

    @EventHandler
    private void onHeldItemRender(HeldItemRendererEvent event) {
        if (!isActive()) return;
        if (event.hand == Hand.MAIN_HAND) {
            if (mode.get() == Mode.None) return;
            if (mode.get() == Mode.Slide && mc.options.useKey.isPressed()) {
                event.matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-95));
                event.matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
                event.matrix.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(80));
                event.matrix.translate(0, -0.080, 0.050);
            }
        }
        if (event.hand == Hand.OFF_HAND && mc.player.getOffHandStack().getItem() == Items.SHIELD) {
            event.matrix.scale(0, 0, 0);
        }
    }

    public enum Mode {
        Slide("Slide"),
        Dev("Dev"),
        None("None");

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
