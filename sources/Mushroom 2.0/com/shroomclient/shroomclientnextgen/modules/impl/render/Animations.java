package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.client.Notifications;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;

@RegisterModule(
    name = "Animations",
    uniqueId = "animations",
    description = "Custom Swing Animations",
    category = ModuleCategory.Render
)
public class Animations extends Module {

    @ConfigOption(
        name = "Pos X",
        description = "",
        min = -2,
        max = 2,
        precision = 2,
        order = 1
    )
    public static Float posX = 0f;

    @ConfigOption(
        name = "Pos Y",
        description = "",
        min = -2,
        max = 2,
        precision = 2,
        order = 2
    )
    public static Float posY = 0f;

    @ConfigOption(
        name = "Pos Z",
        description = "",
        min = -2,
        max = 2,
        precision = 2,
        order = 3
    )
    public static Float posZ = 0f;

    @ConfigOption(
        name = "Item Size",
        description = "",
        max = 2,
        precision = 2,
        order = 4
    )
    public static Float itemSize = 1f;

    @ConfigOption(
        name = "X Rotation",
        description = "",
        min = -180,
        max = 180,
        precision = 2,
        order = 5
    )
    public static Float rotX = 0f;

    @ConfigOption(
        name = "Y Rotation",
        description = "",
        min = -180,
        max = 180,
        precision = 2,
        order = 6
    )
    public static Float rotY = 0f;

    @ConfigOption(
        name = "Z Rotation",
        description = "",
        min = -180,
        max = 180,
        precision = 2,
        order = 7
    )
    public static Float rotZ = 0f;

    @ConfigOption(
        name = "Blocking Pos X",
        description = "",
        min = -2,
        max = 2,
        precision = 2,
        order = 8
    )
    public static Float blockingPosX = 0f;

    @ConfigOption(
        name = "Blocking Pos Y",
        description = "",
        min = -2,
        max = 2,
        precision = 2,
        order = 9
    )
    public static Float blockingPosY = 0f;

    @ConfigOption(
        name = "Blocking Pos Z",
        description = "",
        min = -2,
        max = 2,
        precision = 2,
        order = 10
    )
    public static Float blockingPosZ = 0f;

    @ConfigOption(
        name = "Blocking Scale",
        description = "",
        max = 2f,
        precision = 2,
        order = 11
    )
    public static Float blockingItemSize = 1f;

    @ConfigOption(name = "Show Shields", description = "")
    public static Boolean showShield = false;

    @ConfigOption(
        name = "Reset",
        description = "Resets To Default Values",
        order = 100
    )
    public static Boolean reset = false;

    @ConfigOption(name = "Smooth Swing", description = "")
    public static Boolean smoothSwing = true;

    @ConfigOption(name = "Mode", description = "")
    public static Mode mode = Mode.Old;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        if (reset) {
            blockingItemSize = 1f;
            blockingPosZ = 0f;
            blockingPosY = 0f;
            blockingPosX = 0f;
            rotZ = 0f;
            rotY = 0f;
            rotX = 0f;
            itemSize = 1f;
            posZ = 0f;
            posY = 0f;
            posX = 0f;
            showShield = false;
            smoothSwing = true;
            reset = false;
            Notifications.notify(
                "Successfully Reset!",
                ThemeUtil.themeColors()[0],
                1
            );
        }
    }

    public enum Mode {
        Old,
        Slant,
        Tap,
        Penis,
        Stab,
        Swipe,
    }
}
