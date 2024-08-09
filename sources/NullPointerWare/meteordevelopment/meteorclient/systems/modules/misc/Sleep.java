/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

public final class Sleep extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> time = sgGeneral.add(new DoubleSetting.Builder()
        .name("time")
        .description("time... 10 is a whole second")
        .defaultValue(100)
        .sliderMax(1_000)
        .min(0)
        .max(100_000)
        .build()
    );

     public Sleep() {
        super(Categories.Misc, "Sleep", "Does thread.sleep");
    }

    @Override
    public void onActivate() {
         try {
             Thread.sleep((long) (this.time.get() * 100));
             this.toggle();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
        super.onActivate();
    }
}
