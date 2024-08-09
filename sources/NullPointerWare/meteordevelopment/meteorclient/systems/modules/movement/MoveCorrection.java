/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.MathHelper;

import static meteordevelopment.orbit.EventPriority.HIGHEST;

public final class MoveCorrection extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<EventMode> eventMode = sgGeneral.add(new EnumSetting.Builder<EventMode>()
        .name("EventMode")
        .description("The event the action should be taken on.")
        .defaultValue(EventMode.Move)
        .build()
    );


    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("The ac you gon bipass.")
        .defaultValue(Mode.Vulcan)
        .build()
    );

    private final Setting<Mark> mark = sgGeneral.add(new EnumSetting.Builder<Mark>()
        .name("mark")
        .description("The method of changing modulo.")
        .defaultValue(Mark.Plus)
        .visible(() -> mode.get().equals(Mode.Vulcan))
        .build()
    );

    private final Setting<Boolean> debug = sgGeneral.add(new BoolSetting.Builder()
        .name("debug")
        .description("debug shit.")
        .defaultValue(false)
        .build()
    );

//    private final Setting<JumpWhen> jumpIf = sgGeneral.add(new EnumSetting.Builder<JumpWhen>()
//        .name("jump-if")
//        .description("Jump if.")
//        .defaultValue(JumpWhen.Always)
//        .build()
//    );

    private final Setting<Double> moduloAmount = sgGeneral.add(new DoubleSetting.Builder()
        .name("modulo-amount")
        .description("The amount to change the player velocity by")
        .defaultValue(0.01)
        .min(-1)
        .sliderMax(1)
        .build()
    );

    public MoveCorrection() {
        super(Categories.Movement, "move-correction", "Automatically corrects your movement to bypass anticheats.");
    }

    private double currX, currZ, lastX, lastZ, deltaX, deltaZ, deltaXZ, modulo;
    boolean invalid;
    @Override
    public void onActivate() {
        currX = mc.player.getVelocity().x;
        currZ = mc.player.getVelocity().z;
        super.onActivate();
    }

    @EventHandler(priority = HIGHEST + 10000)
    private void onTick(final TickEvent.Pre e) {
        if (eventMode.get().equals(EventMode.Tick) && mode.get().equals(Mode.Vulcan)) noModulo();
    }

    @EventHandler
    private void onMove(final PlayerMoveEvent e) {
        this.lastX = this.currX;
        this.lastZ = this.currZ;
        this.currX = mc.player.getVelocity().x;
        this.currZ = mc.player.getVelocity().z;

        this.deltaX = this.lastX - this.currX;
        this.deltaZ = this.lastZ - this.currZ;
        this.deltaXZ = MathHelper.hypot(this.deltaX, this.deltaZ);
        modulo = 0.1 % (deltaXZ % 0.1);
        invalid = checkModulo(deltaXZ, modulo);

        if (eventMode.get().equals(EventMode.Move) && mode.get().equals(Mode.Vulcan)) noModulo();

    }

    private void noModulo() {
        if (mode.get().equals(Mode.Vulcan)) {
            if (invalid && mark.get().equals(Mark.Plus)) {
                mc.player.setVelocity(mc.player.getVelocity().x + moduloAmount.get(), mc.player.getVelocity().y, mc.player.getVelocity().z + moduloAmount.get());
                if (debug.get())
                    info("Prevented flag with +modulo " + modulo);
            }
            if (invalid && mark.get().equals(Mark.Minus)) {
                mc.player.setVelocity(mc.player.getX() - moduloAmount.get(), mc.player.getY(), mc.player.getZ() - moduloAmount.get());
                if (debug.get())
                    info("Prevented flag with -modulo " + modulo);
            }
        }
    }

    private boolean checkModulo(double deltaXZ, double modulo) {
       return deltaXZ > 0.11 && modulo < 1.0E-8;
    }

    public enum Mode {
        Vulcan,
    }

    public enum EventMode{
        Tick,
        Move
    }

    public enum Mark {
        Plus,
        Minus
    }
}
