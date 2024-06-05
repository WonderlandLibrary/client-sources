package alphentus.mod.mods.movement;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.mod.mods.combat.KillAura;
import alphentus.settings.Setting;
import alphentus.utils.MovementUtils;
import alphentus.utils.StrafeUtil;
import alphentus.utils.Translate;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 31.07.2020.
 */
public class Fly extends Mod {

    public Setting flySpeed = new Setting("Fly Speed", 0.0F, 1.0F, 0.5F, false, this);
    public Setting flyBoost = new Setting("Fly Boost", 1.0F, 10.0F, 7.5F, false, this);
    public Setting cubecraftSpeed = new Setting("CubeCraft Speed", 2.0F, 3.5F, 3.0F, false, this);
    public Setting cubecraftTimer = new Setting("CubeCraft Timer", 0.2F, 0.8F, 0.5F, false, this);
    String[] flyModes = {"Vanilla", "MCCentral", "AAC 3.2.2", "CubeCraft", "NeruxVace"};
    public Setting flyMode = new Setting("Fly Mode", flyModes, "Vanilla", this);
    TargetStrafe targetStrafe;
    boolean boost;
    double lastY;
    Translate height;
    Translate speed;
    private double yAdd;
    private double moveSpeed;
    private int stage;

    public Fly() {
        super("Fly", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT);

        Init.getInstance().settingManager.addSetting(flyMode);
        Init.getInstance().settingManager.addSetting(flySpeed);
        Init.getInstance().settingManager.addSetting(flyBoost);
        Init.getInstance().settingManager.addSetting(cubecraftSpeed);
        Init.getInstance().settingManager.addSetting(cubecraftTimer);
    }

    @EventTarget
    public void event(Event event) {
        if (flyMode.getSelectedCombo().equals("Vanilla")) {
            if (getState() && event.getType() == Type.TICKUPDATE)
                vanilla(flySpeed.getCurrent());
            if (event.getType() == Type.RENDER2D) {
                flySpeed.setVisible(true);
                flyBoost.setVisible(false);
                cubecraftSpeed.setVisible(false);
                cubecraftTimer.setVisible(false);
                setInfoName(flyMode.getSelectedCombo() + " " + flySpeed.getCurrent());
            }
        }
        if (flyMode.getSelectedCombo().equals("MCCentral")) {
            if (getState() && event.getType() == Type.TICKUPDATE)
                mccentral();
            if (event.getType() == Type.RENDER2D) {
                flySpeed.setVisible(false);
                flyBoost.setVisible(false);
                cubecraftSpeed.setVisible(false);
                cubecraftTimer.setVisible(false);
                setInfoName(flyMode.getSelectedCombo());
            }
        }
        if (flyMode.getSelectedCombo().equals("AAC 3.2.2")) {
            if (getState() && event.getType() == Type.TICKUPDATE)
                aac322(flyBoost.getCurrent());
            if (event.getType() == Type.RENDER2D) {
                flySpeed.setVisible(false);
                flyBoost.setVisible(true);
                cubecraftSpeed.setVisible(false);
                cubecraftTimer.setVisible(false);
                setInfoName(flyMode.getSelectedCombo() + " " + flyBoost.getCurrent());
            }
        }
        if (flyMode.getSelectedCombo().equals("CubeCraft")) {
            if (getState() && event.getType() == Type.TICKUPDATE)
                cubeCraft(cubecraftSpeed.getCurrent(), cubecraftTimer.getCurrent());
            if (event.getType() == Type.RENDER2D) {
                flySpeed.setVisible(false);
                flyBoost.setVisible(false);
                cubecraftSpeed.setVisible(true);
                cubecraftTimer.setVisible(true);
                setInfoName(flyMode.getSelectedCombo() + " " + cubecraftSpeed.getCurrent() + ", " + cubecraftTimer.getCurrent());
            }
        }
        if (flyMode.getSelectedCombo().equals("NeruxVace")) {
            if (event.getType() == Type.RENDER2D) {
                flySpeed.setVisible(false);
                flyBoost.setVisible(false);
                cubecraftSpeed.setVisible(false);
                cubecraftTimer.setVisible(false);
                setInfoName(flyMode.getSelectedCombo());
            }
        }

        if (event.getType() == Type.MOVE && getState()) {
            if (targetStrafe.canStrafe2())
                targetStrafe.strafe(event, MovementUtils.getSpeed());
        }
    }

    public void vanilla(float speed) {
        StrafeUtil.strafe();
        mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.capabilities.setFlySpeed(speed);
    }

    public void mccentral() {
        StrafeUtil.strafe();
        if (mc.gameSettings.keyBindJump.isKeyDown())
            mc.thePlayer.motionY = +0.5F;
        else if (mc.gameSettings.keyBindSneak.isKeyDown())
            mc.thePlayer.motionY = -0.5F;
        else
            mc.thePlayer.motionY = 0.0F;
        if (Init.getInstance().modManager.getModuleByClass(TargetStrafe.class).canStrafe2() && Init.getInstance().modManager.getModuleByClass(TargetStrafe.class).getState() && Init.getInstance().modManager.getModuleByClass(KillAura.class).getState())
            mc.thePlayer.speedInAir = 0.06F;
        else
            mc.thePlayer.speedInAir = 0.175F;
    }

    public void aac322(float boost) {
        if (mc.thePlayer.posY <= -70) {
            mc.thePlayer.motionY = +boost;
        }

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.timer.timerSpeed = 0.25F;
            mc.rightClickDelayTimer = 0;
        } else {
            mc.timer.timerSpeed = 1.0F;
        }
    }

    public void cubeCraft(float speed, float timer) {
        double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
        double x = -Math.sin(yaw) * speed;
        double z = Math.cos(yaw) * speed;
        mc.thePlayer.motionY = -0.02;
        mc.thePlayer.onGround = true;
        if (mc.gameSettings.keyBindForward.isKeyDown()) {
            mc.timer.timerSpeed = timer;
            if (mc.thePlayer.ticksExisted % 2 == 0) {
                mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY + 0.04, mc.thePlayer.posZ + z);
            }
        }
    }

    @EventTarget
    public void neruxVace(Event event) {
        if (!getState())
            return;
        if (event.getType() != Type.MOVE)
            return;
        if (flyMode.getSelectedCombo().equals("NeruxVace")) {

            if (mc.thePlayer.fallDistance >= 3) {
                this.lastY = mc.thePlayer.posY;
                yAdd = 0;
                boost = true;
                mc.thePlayer.fallDistance = 0;
            }
            if (yAdd >= 7) {
                boost = false;
            }

            if (boost)
                moveSpeed = 3.7;
            else
                this.moveSpeed = 0.3;

            if (Minecraft.getMinecraft().thePlayer.isMoving()) {
                MovementUtils.setSpeed(event, moveSpeed);
                mc.thePlayer.setSprinting(false);
                if (boost) {
                    event.setY(mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(yAdd / 10));
                    yAdd++;
                }
            }
        }
    }

    @Override
    public void onEnable() {
        if (targetStrafe == null)
            targetStrafe = Init.getInstance().modManager.getModuleByClass(TargetStrafe.class);
        this.moveSpeed = 0.0D;
        this.stage = 0;
        height = new Translate(0, 0);
        speed = new Translate(0, 0);
        yAdd = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        mc.timer.timerSpeed = 1.0F;
        mc.rightClickDelayTimer = 4;
        mc.thePlayer.speedInAir = 0.02F;
        mc.thePlayer.jumpMovementFactor = 0.02F;
        mc.thePlayer.capabilities.isFlying = false;
        super.onDisable();
    }
}