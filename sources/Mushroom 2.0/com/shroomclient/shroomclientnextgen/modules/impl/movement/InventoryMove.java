package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.KeyPressEvent;
import com.shroomclient.shroomclientnextgen.events.impl.KeyReleaseEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderScreenEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.protection.JnicInclude;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

@RegisterModule(
    name = "Inv Walk",
    uniqueId = "invwalk",
    description = "Allows You To Move While In A GUI",
    category = ModuleCategory.Movement
)
public class InventoryMove extends Module {

    @ConfigMode
    @ConfigParentId("invWalkMode")
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Hypixel;

    @ConfigOption(
        name = "Pause Motion",
        description = "Pauses Motion When Clicking In Inventory",
        order = 2
    )
    public static Boolean pauseMotion = false;

    @ConfigOption(
        name = "Allow Crouching",
        description = "Allows You To Crouch In Inventory",
        order = 2.1
    )
    public static Boolean AllowCrouching = false;

    @ConfigOption(
        name = "Allow Jumping",
        description = "Allows You To Jump In Inventory",
        order = 2.11
    )
    public static Boolean AllowJumping = false;

    @ConfigParentId("mouseMoveGUI")
    @ConfigOption(
        name = "Allow Mouse Movement",
        description = "Allows You To Move Your Mouse In Inventory",
        order = 2.2
    )
    public static Boolean AllowMovingMouse = true;

    @ConfigChild("mouseMoveGUI")
    @ConfigOption(
        name = "Only If Crouch Down",
        description = "Allows You To Move Your Mouse In Inventory If Crouch Key Down",
        order = 2.3
    )
    public static Boolean OnlyMoveWhenDown = false;

    @ConfigOption(
        name = "Hide GUI",
        description = "Changes GUI Opacity",
        order = 2.4
    )
    public static Boolean hideGUI = true;

    @ConfigOption(
        name = "Hide Inventory",
        description = "Changes Inventory Opacity",
        order = 2.5,
        max = 1,
        precision = 2
    )
    public static Boolean hideInventory = true;

    //@ConfigChild(value = "invWalkMode", parentEnumOrdinal = {0})
    @ConfigParentId("motionmodif")
    @ConfigOption(
        name = "Motion Modifier",
        description = "Changes Movement Speed While In Inventory",
        order = 3
    )
    public static Boolean motionModif = false;

    //@ConfigChild(value = "invWalkMode", parentEnumOrdinal = {0})
    @ConfigChild("motionmodif")
    @ConfigOption(
        name = "Motion Speed",
        description = "Sets The Speed While In Inventory, Low Helps Bypass",
        order = 3.1,
        min = 0.001f,
        max = 0.2f,
        precision = 3
    )
    public static Float motionSpeed = 0.05f;

    Screen currentScreen = null;
    double mouseCoordX = 0;
    double mouseCoordY = 0;
    boolean spaceDown = false;
    boolean sneakDown = false;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @JnicInclude
    @SubscribeEvent
    public void onMotion(MotionEvent.Pre event) {
        if (C.mc.currentScreen != null) {
            if (mode == Mode.Hypixel) {
                if (C.p().isOnGround()) MovementUtil.setMotion(0.07f);
                else MovementUtil.setMotion(0.16f);
            } else if (motionModif) MovementUtil.setMotion(motionSpeed);
        }
    }

    @SubscribeEvent
    public void onRender(RenderScreenEvent e) {
        if (C.mc.currentScreen != null) {
            if (C.p().isOnGround() && spaceDown) MovementUtil.jump();

            if (currentScreen != C.mc.currentScreen) {
                mouseCoordX = C.mc.mouse.getX();
                mouseCoordY = C.mc.mouse.getY();
            }

            if (AllowMovingMouse) {
                if (
                    C.mc.currentScreen instanceof GenericContainerScreen ||
                    C.mc.currentScreen instanceof InventoryScreen
                ) {
                    if (!OnlyMoveWhenDown || sneakDown) {
                        C.p()
                            .setYaw(
                                (float) (C.p().getYaw() +
                                    ((C.mc.mouse.getX() - mouseCoordX) *
                                        C.mc.options
                                            .getMouseSensitivity()
                                            .getValue() *
                                        0.5))
                            );
                        C.p()
                            .setPitch(
                                Math.min(
                                    Math.max(
                                        (float) (C.p().getPitch() +
                                            ((C.mc.mouse.getY() - mouseCoordY) *
                                                C.mc.options
                                                    .getMouseSensitivity()
                                                    .getValue() *
                                                0.5)),
                                        -90
                                    ),
                                    90
                                )
                            );
                    }
                }
            }

            mouseCoordX = C.mc.mouse.getX();
            mouseCoordY = C.mc.mouse.getY();
        }

        currentScreen = C.mc.currentScreen;
    }

    @SubscribeEvent
    public void onKeybindPress(KeyPressEvent e) {
        if (C.mc.currentScreen != null) {
            if (C.mc.options.sneakKey.matchesKey(e.key, e.scanCode)) sneakDown =
                true;
            if (
                C.mc.options.jumpKey.matchesKey(e.key, e.scanCode) &&
                AllowJumping
            ) spaceDown = true;
        }
    }

    @SubscribeEvent
    public void onKeybindPress(KeyReleaseEvent e) {
        if (C.mc.options.sneakKey.matchesKey(e.key, e.scanCode)) sneakDown =
            false;
        if (C.mc.options.jumpKey.matchesKey(e.key, e.scanCode)) spaceDown =
            false;
    }

    public enum Mode {
        Vanilla,
        Hypixel,
    }
}
