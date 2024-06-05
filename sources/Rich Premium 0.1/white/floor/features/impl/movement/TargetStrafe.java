package white.floor.features.impl.movement;

import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.Event3D;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.features.impl.combat.KillauraTest;
import white.floor.features.impl.visuals.TargetESP;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.combat.RotationHelper;
import white.floor.helpers.movement.MovementHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class TargetStrafe extends Feature {
    private int direction = -1;

    public TargetStrafe() {
        super("TargetStrafe", "karysel.", Keyboard.KEY_Z, Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("TSpeed", this, 3.18, 0.15, 50, false));
        Main.settingsManager.rSetting(new Setting("Radius", this, 3, 1, 6, false));
        Main.settingsManager.rSetting(new Setting("AutoJump", this, true));
        Main.settingsManager.rSetting(new Setting("KeepDistance", this, true));

    }

    public final void doStrafeAtSpeed(double d) {
        if (Main.featureDirector.getModule(KillauraTest.class).isToggled() && KillauraTest.target != null && !KillauraTest.target.isDead) {
            float[] arrf = RotationHelper.getRotations(KillauraTest.target);
            if (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TargetStrafe.class), "KeepDistance")
                    .getValBoolean()) {
                if (mc.player.getDistanceToEntity(KillauraTest.target) <= Main.settingsManager
                        .getSettingByName(Main.featureDirector.getModule(TargetStrafe.class), "Radius").getValDouble()) {
                    MovementHelper.setSpeed(d - (0.20 - Main.settingsManager
                            .getSettingByName(Main.featureDirector.getModule(TargetStrafe.class), "TSpeed").getValFloat()
                            / 100.0), arrf[0], direction, -1.0);
                } else {
                    MovementHelper.setSpeed(d - (0.20 - Main.settingsManager
                            .getSettingByName(Main.featureDirector.getModule(TargetStrafe.class), "TSpeed").getValFloat()
                            / 100.0), arrf[0], direction, 1.0);
                }
            } else {
                if (mc.player.getDistanceToEntity(KillauraTest.target) < Main.settingsManager
                        .getSettingByName(Main.featureDirector.getModule(TargetStrafe.class), "Radius").getValDouble()) {
                    MovementHelper.setSpeed(d - (0.20 - Main.settingsManager
                            .getSettingByName(Main.featureDirector.getModule(TargetStrafe.class), "TSpeed").getValFloat()
                            / 100.0), arrf[0], direction, 0);
                } else {
                    MovementHelper.setSpeed(d - (0.20 - Main.settingsManager
                            .getSettingByName(Main.featureDirector.getModule(TargetStrafe.class), "TSpeed").getValFloat()
                            / 100.0), arrf[0], direction, 1);
                }
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setModuleName("TargetStrafe §7[" + Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TargetStrafe.class), "Radius").getValFloat() + "]");
        if (Main.featureDirector.getModule(KillauraTest.class).isToggled()) {
            if (mc.player.isCollidedHorizontally && this.timerHelper.check(30.0f)) {
                this.invertStrafe();
            } else {
                this.timerHelper.resetwatermark();
            }

            if (Main.settingsManager.getSettingByName("AutoJump").getValBoolean()) {
                if (KillauraTest.target != null && !KillauraTest.target.isDead) {
                    if (mc.player.onGround) {
                        mc.player.jump();
                    }
                }
            }

            if (mc.gameSettings.keyBindLeft.isPressed()) {
                direction = 1;
            }
            if (mc.gameSettings.keyBindRight.isPressed()) {
                direction = -1;
            }

            mc.player.movementInput.moveForward = 0.0f;
            double d = 0.2873;
            this.doStrafeAtSpeed(d);
        }
    }

    private void invertStrafe() {
        direction = -direction;
    }

    @EventTarget
    public void jija(Event3D xaski) {
        if(KillauraTest.target != null && KillauraTest.target.getHealth() > 0.0 && mc.player.getDistanceToEntity(KillauraTest.target) <= Main.instance.settingsManager.getSettingByName(Main.featureDirector.getModule(KillauraTest.class), "Range").getValDouble() && Main.featureDirector.getModule(KillauraTest.class).isToggled() && !Main.featureDirector.getModule(TargetESP.class).isToggled()) {
            DrawHelper.staticJelloCircle();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}
