/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.render;

import meteordevelopment.meteorclient.events.render.HeldItemRendererEvent;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.combat.Aura;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;

public class Animations extends Module {
    public Animations() {
        super(Categories.Render, "animations", "Adds 1.8 Style Sword Animations");
    }

    private final SettingGroup general = settings.createGroup("General");


    public final Setting<Mode> mode = general.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Animation mode")
        .defaultValue(Mode.None)
        .build()
    );


    @EventHandler
    private void onHeldItemRender(HeldItemRendererEvent event) {
        if (!isActive()) return;
        if (event.hand == Hand.MAIN_HAND) {
            if (mode.get() == Mode.None) return;
            if (mode.get() == Mode.Slide && (mc.options.useKey.isPressed() || Aura.fakeBlock)) {
                event.matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-95));
                event.matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
                event.matrix.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(80));
                event.matrix.translate(0, -0.080, 0.050);
            }
        }
/*        if (event.hand == Hand.OFF_HAND && mc.player.getOffHandStack().getItem() == Items.SHIELD) {
            event.matrix.scale(0, 0, 0);
        }*/
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
