/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.player;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.math.MathUtils;
import meteordevelopment.meteorclient.utils.other.TimerMS;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public final class AutoClicker extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();


    private final Setting<cpsMode> CPSMode = sgGeneral.add(new EnumSetting.Builder<cpsMode>()
        .name("cpsMode")
        .description("The way to do cps shit")
        .defaultValue(cpsMode.CPS)
        .build()
    );

    private final Setting<Integer> Cps = sgGeneral.add(new IntSetting.Builder()
        .name("delay-left")
        .description("The amount of delay between left clicks in ticks.")
        .defaultValue(2)
        .min(0)
        .sliderMax(60)
        .visible(() -> CPSMode.get() == cpsMode.CPS)
        .build()
    );

    private final Setting<Boolean> randomize = sgGeneral.add(new BoolSetting.Builder()
        .name("Random")
        .description("Randomizes shit")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> cpsMax = sgGeneral.add(new IntSetting.Builder()
        .name("cps-max-randomness")
        .description("The amount of delay between left clicks in ticks.")
        .defaultValue(2)
        .min(0)
        .sliderMax(60)
        .visible(() -> CPSMode.get() == cpsMode.CPS)
        .build()
    );

    private final Setting<Integer> cpsMin = sgGeneral.add(new IntSetting.Builder()
        .name("cps-min-randomness")
        .description("The amount of delay between left clicks in ticks.")
        .defaultValue(2)
        .min(0)
        .sliderMax(60)
        .visible(() -> CPSMode.get() == cpsMode.CPS)
        .build()
    );


    // gay meteor shit

    private final Setting<Mode> leftClickMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode-left")
        .description("The method of clicking for left clicks.")
        .defaultValue(Mode.Press)
        .visible(() -> CPSMode.get() == cpsMode.Tick)
        .build()
    );

    private final Setting<Integer> leftClickDelay = sgGeneral.add(new IntSetting.Builder()
        .name("delay-left")
        .description("The amount of delay between left clicks in ticks.")
        .defaultValue(2)
        .min(0)
        .sliderMax(60)
        .visible(() -> leftClickMode.get() == Mode.Press && CPSMode.get().equals(cpsMode.Tick))
        .build()
    );

    private final Setting<Mode> rightClickMode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode-right")
        .description("The method of clicking for right clicks.")
        .defaultValue(Mode.Press)
        .visible(() -> CPSMode.get() == cpsMode.Tick)
        .build()
    );

    private final Setting<Integer> rightClickDelay = sgGeneral.add(new IntSetting.Builder()
        .name("delay-right")
        .description("The amount of delay between right clicks in ticks.")
        .defaultValue(2)
        .min(0)
        .sliderMax(60)
        .visible(() -> rightClickMode.get() == Mode.Press && CPSMode.get().equals(cpsMode.Tick))
        .build()
    );

    private int rightClickTimer, leftClickTimer;

    TimerMS timer = new TimerMS();

    public AutoClicker() {
        super(Categories.Player, "auto-clicker", "Automatically clicks.");
    }

    @Override
    public void onActivate() {
        rightClickTimer = 0;
        leftClickTimer = 0;
        mc.options.attackKey.setPressed(false);
        mc.options.useKey.setPressed(false);
    }

    @Override
    public void onDeactivate() {
        mc.options.attackKey.setPressed(false);
        mc.options.useKey.setPressed(false);
    }

    @EventHandler
    private void onTick(final TickEvent.Post event){

        if (CPSMode.get().equals(cpsMode.Tick)) {
            switch (leftClickMode.get()) {
                case Disabled -> {
                }
                case Hold -> mc.options.attackKey.setPressed(true);
                case Press -> {
                    leftClickTimer++;
                    if (leftClickTimer > leftClickDelay.get()) {
                        Utils.leftClick();
                        leftClickTimer = 0;
                    }
                }
            }
            switch (rightClickMode.get()) {
                case Disabled -> {
                }
                case Hold -> mc.options.useKey.setPressed(true);
                case Press -> {
                    rightClickTimer++;
                    if (rightClickTimer > rightClickDelay.get()) {
                        Utils.rightClick();
                        rightClickTimer = 0;
                    }
                }
            }
        }

        if (CPSMode.get().equals(cpsMode.CPS)) {
         if (mc.currentScreen instanceof HandledScreen<?> || !mc.options.attackKey.isPressed())
             return;

            int rand = randomize.get() ? MathUtils.getRandomNumber(cpsMax.get(), cpsMin.get()) : 0;

            int cpsDelay = Cps.get() + rand <= 0 ? 1 : Cps.get() + rand;

            long delay = 1000/(cpsDelay);

            if (timer.hasTimePassed(delay)) {
                Utils.leftClick();
            }
        }
    }

    public enum Mode {
        Disabled,
        Hold,
        Press
    }

    public enum cpsMode {
        CPS,
        Tick
    }
}
