package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.protection.JnicInclude;
import com.shroomclient.shroomclientnextgen.util.BlinkUtil;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.TimerUtil;

@RegisterModule(
    name = "Timer",
    uniqueId = "timer",
    description = "Changes The Speed Minecraft Runs At",
    category = ModuleCategory.Movement
)
public class Timer extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Vanilla;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Timer Speed",
        description = "Timer Value",
        min = 0,
        max = 10,
        precision = 3,
        order = 2
    )
    public Float timerSpeed = 2.0F;

    int ticks = 0;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {
        BlinkUtil.setOutgoingBlink(false);
        BlinkUtil.setIncomingBlink(false);
        TimerUtil.resetTPS();
    }

    @JnicInclude
    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        switch (mode) {
            case Vanilla -> {
                TimerUtil.setTPSMulti(timerSpeed);
            }
            case Watchdog -> {
                if (C.p().isOnGround() && !C.mc.options.jumpKey.isPressed()) {
                    MovementUtil.jump();
                }
                if (
                    MovementUtil.airTicks > 5 &&
                    MovementUtil.airTicks < 15 &&
                    C.p().hurtTime == 0 &&
                    !C.p().handSwinging
                ) {
                    if (MovementUtil.jumps % 2 == 0) {
                        TimerUtil.setTPSMulti(1f);
                    } else TimerUtil.setTPSMulti(1.5F);
                } else if (C.p().isOnGround()) {
                    TimerUtil.setTPSMulti(0.6f);
                } else {
                    TimerUtil.setTPSMulti(1F);
                }
            }
            case Watchdog_Duels -> {
                if (C.p().isOnGround() && !C.mc.options.jumpKey.isPressed()) {
                    MovementUtil.jump();
                }
                if (
                    MovementUtil.airTicks > 5 &&
                    MovementUtil.airTicks < 15 &&
                    C.p().hurtTime == 0 &&
                    !C.p().handSwinging
                ) {
                    if (MovementUtil.jumps % 6 == 0) {
                        TimerUtil.setTPSMulti(1f);
                    } else TimerUtil.setTPSMulti(1.5F);
                } else if (C.p().isOnGround()) {
                    TimerUtil.setTPSMulti(0.6f);
                } else {
                    TimerUtil.setTPSMulti(1F);
                }
            }
            case Vulcan -> {
                if (MovementUtil.isUserMoving(false)) {
                    C.p().setSneaking(true);
                    if (!C.p().isSprinting()) C.p().setSprinting(true);
                    if (C.p().isOnGround()) {
                        // BlinkUtil.setIncomingBlink(false);
                        TimerUtil.setTPSMulti(0.1F);
                        C.p().jump();
                    } else {
                        // BlinkUtil.setIncomingBlink(true);
                        TimerUtil.setTPSMulti(2.6F);
                    }
                } else {
                    TimerUtil.resetTPS();
                }
            }
            case Vulcan2 -> {
                if (MovementUtil.isUserMoving(false)) {
                    int currentTick = MovementUtil.ticks % 35;
                    if (currentTick < 2) {
                        TimerUtil.setTPSMulti(0.1F);
                    } else {
                        if (
                            C.mc.options.forwardKey.isPressed() &&
                            (C.mc.options.rightKey.isPressed() ||
                                C.mc.options.leftKey.isPressed())
                        ) {
                            TimerUtil.setTPSMulti(2.3F);
                        } else {
                            TimerUtil.setTPSMulti(2.6F);
                        }
                    }
                } else {
                    TimerUtil.resetTPS();
                }
            }
            case New_Watchdog -> {
                if (MovementUtil.isUserMoving(false)) {
                    int currentTick = MovementUtil.ticks % 20;
                    if (currentTick < 3) {
                        TimerUtil.setTPSMulti(1);
                    } else {
                        if (
                            C.mc.options.forwardKey.isPressed() &&
                            (C.mc.options.rightKey.isPressed() ||
                                C.mc.options.leftKey.isPressed())
                        ) {
                            TimerUtil.setTPSMulti(2);
                        } else {
                            TimerUtil.setTPSMulti(2.5F);
                        }
                    }
                } else {
                    TimerUtil.resetTPS();
                }
            }
        }
    }

    public enum Mode {
        Vanilla,
        Watchdog,
        Watchdog_Duels,
        New_Watchdog,
        Vulcan,
        Vulcan2,
    }
}
