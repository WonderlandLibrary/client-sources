package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.ChatUtil;
import com.shroomclient.shroomclientnextgen.util.RotationUtil;

//@RegisterModule(
//        name = "Record Movement",
//        uniqueId = "devModule",
//        description = "Haiiii this is only here for devshit",
//        category = ModuleCategory.Combat
//)
public class RotationRecorder extends Module {

    @ConfigOption(name = "Velocity", description = "Tracks Velocity")
    public Boolean trackVelocity = false;

    @ConfigOption(name = "Position", description = "Tracks Position")
    public Boolean trackPosition = false;

    @ConfigOption(name = "Rotations", description = "Tracks Rotations")
    public Boolean trackRotations = false;

    @ConfigOption(
        name = "Only Track Rotation Change",
        description = "Tracks Rotations Change"
    )
    public Boolean trackRotationsChange = false;

    @ConfigOption(name = "Track Clicks", description = "Tracks Clicks")
    public Boolean trackClicks = false;

    @ConfigOption(name = "Disable On Swing", description = "Disables On Swing")
    public Boolean swingDisable = false;

    @ConfigOption(
        name = "Disable On Right Click",
        description = "Disables On Right Click"
    )
    public Boolean clickDisable = true;

    @ConfigOption(
        name = "Start On First Move",
        description = "Starts On Mouse Move"
    )
    public Boolean startOnMove = false;

    @ConfigOption(
        name = "Print Movements",
        description = "Prints Full Unformatted Movements"
    )
    public Boolean printMovements = false;

    @ConfigOption(
        name = "Print Extras",
        description = "Prints Extra Useful Info"
    )
    public Boolean printExtras = false;

    String movements = "";
    String formattedRots = "{";
    String formattedCPS = "{";

    boolean moved = false;
    float ogYaw = 0;
    float ogPitch = 0;
    int ticks = 0;
    float lastPitch;
    float lastYaw;

    @Override
    protected void onEnable() {
        if (C.p() != null) {
            ogPitch = C.p().getPitch();
            ogYaw = C.p().getYaw();

            lastYaw = C.p().getYaw();

            ticks = 0;
            if (startOnMove) moved = false;
            else {
                moved = true;
                ChatUtil.chat("recording movements");
            }
            movements = "";
            formattedRots = "{";
            formattedCPS = "{";
        }
    }

    @Override
    protected void onDisable() {
        if (C.p() != null) {
            ChatUtil.chat("printed movements to cmd");
            if (printMovements) {
                System.out.println("----------movements----------");
                System.out.println(movements);
            }
            System.out.println("----------formatted rotations----------");
            System.out.println(
                formattedRots.substring(0, formattedRots.length() - 1) + "}"
            );
            System.out.println("----------formatted CPS----------");
            System.out.println(
                formattedCPS.substring(0, formattedCPS.length() - 1) + "}"
            );

            if (printExtras) {
                System.out.println("----------extra----------");
                System.out.println(
                    "original yaw: " +
                    ogYaw +
                    " | new yaw: " +
                    C.p().getYaw() +
                    " | yaw change: " +
                    RotationUtil.getAngleDifference(ogYaw, C.p().getYaw())
                );
                System.out.println(
                    "original pitch: " +
                    ogPitch +
                    " | new pitch: " +
                    C.p().getPitch() +
                    " | pitch change: " +
                    RotationUtil.getAngleDifference(ogPitch, C.p().getPitch())
                );
            }
        }
    }

    @SubscribeEvent(EventBus.Priority.HIGHEST)
    public void onPreMotion(MotionEvent.Pre e) {
        if (!moved) {
            if (ogYaw != C.p().getYaw() || ogPitch != C.p().getPitch()) {
                moved = true;
                ChatUtil.chat("recording movements");
                //formattedRots += "new RotationUtil.Rotation(" + RotationUtil.wrapAngleTo180_float(C.p().lastRenderYaw) + "f, " + RotationUtil.wrapAngleTo180_float(C.p().lastRenderPitch) + "f),";
            }
        }
        if (moved) {
            ticks++;
            if (clickDisable && C.mc.options.useKey.isPressed()) {
                ModuleManager.toggle(RotationRecorder.class, false);
                return;
            }

            if (swingDisable && C.p().handSwinging) {
                ModuleManager.toggle(RotationRecorder.class, false);
                return;
            }
            if (trackRotations) {
                if (trackRotationsChange) {
                    float yaw = lastYaw - C.p().getYaw();
                    float pitch = lastPitch - C.p().getPitch();

                    if (yaw < 0) yaw *= -1;
                    if (pitch < 0) pitch *= -1;

                    movements +=
                    ticks + ": yaw: " + yaw + " | pitch: " + pitch + "\n";

                    formattedRots +=
                    "new RotationUtil.Rotation(" + pitch + "f, " + yaw + "f),";

                    lastPitch = C.p().getPitch();
                    lastYaw = C.p().getYaw();
                } else {
                    movements +=
                    ticks +
                    ": yaw: " +
                    C.p().getYaw() +
                    " | pitch: " +
                    C.p().getPitch() +
                    "\n";

                    formattedRots +=
                    "new RotationUtil.Rotation(" +
                    C.p().getPitch() +
                    "f, " +
                    C.p().getYaw() +
                    "f),";
                }
            }

            if (trackClicks) {
                if (C.p().handSwingTicks == 0 && C.p().handSwinging) {
                    formattedCPS += "1,";
                } else {
                    formattedCPS += "0,";
                }
            }

            if (trackPosition) {
                movements +=
                ticks +
                ": x: " +
                e.getX() +
                " | y: " +
                e.getY() +
                " | z: " +
                e.getZ() +
                "\n";
            }

            if (trackVelocity) {
                movements +=
                ticks +
                ": x velo: " +
                C.p().getVelocity().x +
                " | y velo: " +
                C.p().getVelocity().y +
                " | z velo: " +
                C.p().getVelocity().z +
                "\n";
            }
        }
    }
}
