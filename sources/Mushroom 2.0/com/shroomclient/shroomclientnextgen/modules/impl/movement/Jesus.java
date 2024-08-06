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
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.WorldUtil;

@RegisterModule(
    name = "Jesus",
    uniqueId = "walkonwater",
    description = "Walk On Water",
    category = ModuleCategory.Movement
)
public class Jesus extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Vulcan;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Vulcan Water Jump",
        description = "Jumps Up When Space Is Pressed",
        order = 2
    )
    public Boolean VWaterJump = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 1, 2 })
    @ConfigOption(
        name = "Descend With Sneak",
        description = "Go Down When Sneak Pressed",
        order = 3
    )
    public Boolean sneakDescent = true;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        switch (mode) {
            case Vulcan -> {
                if (C.p().isInFluid() || C.p().isInLava()) {
                    C.p().setVelocity(0, 0.1, 0);
                    MovementUtil.setMotion(0.2);
                }
                if (
                    VWaterJump &&
                    C.p().isInFluid() &&
                    C.mc.options.jumpKey.isPressed()
                ) {
                    C.p().setVelocity(0, 0.9, 0);
                }
            }
            case NCP -> {
                if (
                    WorldUtil.overWater() &&
                    !C.p().isInFluid() &&
                    (!sneakDescent || !C.mc.options.sneakKey.isPressed())
                ) {
                    MovementUtil.setYmotion(0);
                    if (MovementUtil.ticks % 2 == 0) e.setY(e.getY() - 0.1);
                    else e.setY(e.getY() + 0.1);
                    C.p().setOnGround(true);
                }
            }
            case Vanilla -> {
                if (
                    (!sneakDescent || !C.mc.options.sneakKey.isPressed()) &&
                    WorldUtil.overWater()
                ) {
                    MovementUtil.setYmotion(0);
                    C.p().setOnGround(true);
                }
            }
            case Hypixel -> {
                if (WorldUtil.overWater() && !C.p().isInFluid()) {
                    MovementUtil.setYmotion(0);

                    if (MovementUtil.isMoving()) {
                        if (MovementUtil.ticks % 2 == 0) e.setY(
                            e.getY() - 0.05
                        );
                        else e.setY(e.getY() + 0.05);
                    }

                    MovementUtil.setMotion(0.16);
                }

                if (C.p().isInFluid()) {
                    MovementUtil.setYmotion(0.05);
                }
            }
        }
    }

    public static boolean canJump() {
        return mode == Mode.Vanilla;
    }

    public enum Mode {
        Vulcan,
        NCP,
        Vanilla,
        Hypixel,
    }
}
