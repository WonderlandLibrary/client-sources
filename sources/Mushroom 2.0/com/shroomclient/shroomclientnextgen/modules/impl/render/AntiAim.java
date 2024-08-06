package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Scaffold;
import com.shroomclient.shroomclientnextgen.util.C;

@RegisterModule(
    name = "Anti-Aim",
    uniqueId = "antiaim",
    description = "Makes You Spin",
    category = ModuleCategory.Render
)
public class AntiAim extends Module {

    @ConfigOption(
        name = "Spin",
        description = "Makes Your Head Spin",
        order = 1
    )
    public static Boolean spinning = true;

    @ConfigOption(
        name = "Rotation Speed",
        description = "Changes The Speed Of Rotations",
        min = 1,
        max = 180,
        precision = 1,
        order = 2
    )
    public static Float rotationSpeed = 10.0F;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 0, 1, 2 })
    @ConfigOption(
        name = "Random Rotations",
        description = "Does Random Snaps",
        order = 3
    )
    public static Boolean randomRots = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 0, 1, 2 })
    @ConfigOption(
        name = "Nodding",
        description = "Travis Scott Head Roll",
        order = 4
    )
    public static Boolean headRoll = false;

    @ConfigOption(
        name = "Server Side",
        description = "Sends Rotations To The Server",
        order = 5
    )
    public static Boolean serverSide = false;

    @ConfigParentId("mode")
    @ConfigMode
    @ConfigOption(
        name = "Animation",
        description = "What Type Of Anti-Aim",
        order = 6
    )
    public static Mode mode = Mode.Flail;

    @ConfigOption(
        name = "Spin Speed",
        description = "",
        max = 0.5,
        precision = 3,
        order = 7
    )
    public static Float foldingSpeed = 0.05f;

    float yaw = 0;

    @SubscribeEvent(EventBus.Priority.HIGHEST)
    public void onMotion(MotionEvent.Pre e) {
        if (spinning) {
            yaw += rotationSpeed;

            if (yaw >= 180) {
                yaw -= 360;
            } else if (yaw <= -180) {
                yaw += 360;
            }

            if (randomRots && AntiAim.mode != AntiAim.Mode.Jitter) {
                yaw = (float) ((Math.random() * 360) - 180);
            }

            C.p().setHeadYaw(yaw);
            C.p().setBodyYaw(-yaw);

            if ((serverSide && !Scaffold.scaffolding())) {
                if (AntiAim.mode == AntiAim.Mode.Jitter) {
                    if (C.p().isOnGround()) {
                        e.setYaw(yaw);
                        e.setPitch(90);
                        C.p().setBodyYaw((float) ((Math.random() * 360) - 180));
                    } else {
                        e.setYaw(yaw);
                        e.setPitch(-90);
                    }
                } else {
                    e.setYaw(yaw);
                    e.setPitch(yaw / 2);
                }
            }
        }
    }

    @Override
    protected void onEnable() {
        if (
            ModuleManager.isEnabled(ServerSideRotations.class)
        ) ModuleManager.setEnabled(ServerSideRotations.class, false, false);
    }

    @Override
    protected void onDisable() {}

    public enum Mode {
        Backflip,
        Swimming,
        Flail,
        Jitter,
    }
}
