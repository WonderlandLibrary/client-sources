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
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 31/07/2020.
 */
public class Speed extends Mod {

    int hiveCount;

    String[] speedModes = {"Bhop", "OnGround", "LowHop", "Custom"};
    public Setting speedMode = new Setting("Speed Mode", speedModes, "Bhop", this);

    String[] bHopModes = {"MCCentral", "CubeCraft", "BlocksMC", "HiveMC", "Mineplex", "Timolia", "Intave"};
    public Setting bhopMode = new Setting("Bhop Mode", bHopModes, "CubeCraft", this);

    String[] lowHopModes = {"HiveMC"};
    public Setting lowHopMode = new Setting("LowHop Mode", lowHopModes, "HiveMC", this);

    String[] onGroundModes = {"MinePlex", "CubeCraft"};
    public Setting onGroundMode = new Setting("OnGround Mode", onGroundModes, "MinePlex", this);

    public Setting strafe = new Setting("Strafe", false, this);

    private double mineplex = 0, stage;

    public Setting speedInAir = new Setting("SpeedFactor", 0.2F, 0.5F, 0.2F, false, this);
    public Setting jumpMovementFactor = new Setting("JumpFactor", 0.2F, 0.5F, 0.2F, false, this);
    public Setting timerGround = new Setting("GroundTimer", 0.5F, 5, 1, false, this);
    public Setting timerAir = new Setting("AirTimer", 0, 1, 1, false, this);
    public Setting upFactor = new Setting("JumpHeight", 0, 0.5F, 0.42F, false, this);
    public Setting downFactor = new Setting("LowHop Factor", 0, 1F, 0, false, this);
    public Setting motion = new Setting("Motion", 0.9F, 1.15F, 1.0F, false, this);
    public Setting jump = new Setting("Jump", true, this);

    public Speed() {
        super("Speed", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT);

        Init.getInstance().settingManager.addSetting(speedMode);
        Init.getInstance().settingManager.addSetting(bhopMode);
        Init.getInstance().settingManager.addSetting(onGroundMode);
        Init.getInstance().settingManager.addSetting(lowHopMode);
        Init.getInstance().settingManager.addSetting(strafe);
        Init.getInstance().settingManager.addSetting(speedInAir);
        Init.getInstance().settingManager.addSetting(jumpMovementFactor);
        Init.getInstance().settingManager.addSetting(timerGround);
        Init.getInstance().settingManager.addSetting(timerAir);
        Init.getInstance().settingManager.addSetting(upFactor);
        Init.getInstance().settingManager.addSetting(downFactor);
        Init.getInstance().settingManager.addSetting(motion);
        Init.getInstance().settingManager.addSetting(jump);

    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
        mc.thePlayer.speedInAir = 0.02F;
        mc.thePlayer.jumpMovementFactor = 0.02F;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        hiveCount = 0;
        this.stage = 0;
        super.onEnable();
    }

    @EventTarget
    public void event(Event event) {

        // VALUES
        if (event.getType() == Type.RENDER2D) {
            if (speedMode.getSelectedCombo().equals("Bhop")) {
                bhopMode.setVisible(true);
                onGroundMode.setVisible(false);
                lowHopMode.setVisible(false);

                speedInAir.setVisible(false);
                jumpMovementFactor.setVisible(false);
                timerGround.setVisible(false);
                timerAir.setVisible(false);
                upFactor.setVisible(false);
                downFactor.setVisible(false);
                motion.setVisible(false);
                jump.setVisible(false);
                this.setInfoName(bhopMode.getSelectedCombo());
            } else {
                if (speedMode.getSelectedCombo().equals("OnGround")) {
                    lowHopMode.setVisible(false);
                    bhopMode.setVisible(false);
                    onGroundMode.setVisible(true);

                    speedInAir.setVisible(false);
                    jumpMovementFactor.setVisible(false);
                    timerGround.setVisible(false);
                    timerAir.setVisible(false);
                    upFactor.setVisible(false);
                    downFactor.setVisible(false);
                    motion.setVisible(false);
                    jump.setVisible(false);
                    this.setInfoName(onGroundMode.getSelectedCombo());
                } else {
                    if (speedMode.getSelectedCombo().equals("LowHop")) {
                        bhopMode.setVisible(false);
                        onGroundMode.setVisible(false);
                        lowHopMode.setVisible(true);

                        speedInAir.setVisible(false);
                        jumpMovementFactor.setVisible(false);
                        timerGround.setVisible(false);
                        timerAir.setVisible(false);
                        upFactor.setVisible(false);
                        downFactor.setVisible(false);
                        motion.setVisible(false);
                        jump.setVisible(false);
                        this.setInfoName(lowHopMode.getSelectedCombo());
                    } else {
                        if (speedMode.getSelectedCombo().equals("Custom")) {
                            bhopMode.setVisible(false);
                            onGroundMode.setVisible(false);
                            lowHopMode.setVisible(false);

                            speedInAir.setVisible(true);
                            jumpMovementFactor.setVisible(true);
                            timerGround.setVisible(true);
                            timerAir.setVisible(true);
                            if (!jump.isState())
                                upFactor.setVisible(true);
                            else
                                upFactor.setVisible(false);
                            downFactor.setVisible(true);
                            motion.setVisible(true);
                            jump.setVisible(true);
                            this.setInfoName("Custom");
                        }
                    }
                }
            }
        }

        if (getState()) {
            //mc.gameSettings.keyBindJump.pressed = false;
            if (event.getType() == Type.MOVE) {
                if (Init.getInstance().modManager.getModuleByClass(TargetStrafe.class).canStrafe2())
                    Init.getInstance().modManager.getModuleByClass(TargetStrafe.class).strafe(event, MovementUtils.getSpeed());
            }

            if (event.getType() == Type.TICKUPDATE && strafe.isState())
                StrafeUtil.strafe();

            if (event.getType() == Type.TICKUPDATE && mc.thePlayer.isMoving()) {
                speedModeBhop(event);
                speedModeOnGround(event);
                speedModeLowHop(event);
                customSpeed(event);
            }

            if (event.getType() == Type.PRE) {
                if (speedMode.getSelectedCombo().equals("Bhop")) {
                    if (bhopMode.getSelectedCombo().equals("Mineplex")) {

                        double speed = 0.15;
                        if (Minecraft.getMinecraft().thePlayer.isMoving()) {
                            mineplex = -1.95;
                        }
                        if (Minecraft.getMinecraft().thePlayer.onGround && Minecraft.getMinecraft().thePlayer.isMoving()) {
                            stage = 3;
                            mc.thePlayer.motionY = 0.41;
                            if (mineplex < 0)
                                mineplex += 4;
                            if (mc.thePlayer.posY != (int) mc.thePlayer.posY) {
                                mineplex = 0;
                            }
                        } else {
                            speed = 0.86 - stage / 300 + mineplex / 5;
                            stage++;

                        }
                        MovementUtils.setSpeed(speed);
                    }
                

                }
            }
        }
    }

    public void customSpeed(Event event) {
        if (!speedMode.getSelectedCombo().equals("Custom"))
            return;

        if (mc.thePlayer.onGround) {
            mc.timer.timerSpeed = timerGround.getCurrent();
            if (jump.isState())
                mc.thePlayer.jump();
            else
                event.setY(mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(upFactor.getCurrent()));
        } else {
            mc.timer.timerSpeed = timerAir.getCurrent();
            mc.thePlayer.motionY -= downFactor.getCurrent() / 10;
        }
        mc.thePlayer.jumpMovementFactor = jumpMovementFactor.getCurrent() / 10;
        mc.thePlayer.speedInAir = speedInAir.getCurrent() / 10;
        mc.thePlayer.motionX *= motion.getCurrent();
        mc.thePlayer.motionZ *= motion.getCurrent();
    }

    public void speedModeBhop(Event event) {
        if (!speedMode.getSelectedCombo().equals("Bhop"))
            return;

        switch (bhopMode.getSelectedCombo()) {
            case "MCCentral":
                if (mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isOnLadder())
                    return;
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    if (!Init.getInstance().modManager.getModuleByClass(TargetStrafe.class).canStrafe2())
                        mc.timer.timerSpeed = 2.00F;
                } else {
                    if ((Init.getInstance().modManager.getModuleByClass(KillAura.class).finalEntity == null || !Init.getInstance().modManager.getModuleByClass(KillAura.class).getState())) {
                        if (mc.gameSettings.keyBindJump.isKeyDown()) {
                            mc.timer.timerSpeed = 1.10F;
                            mc.thePlayer.speedInAir = 0.08F;
                            mc.thePlayer.motionX *= 1.05F;
                            mc.thePlayer.motionZ *= 1.05F;
                        } else {
                            mc.timer.timerSpeed = 1.0F;
                            mc.thePlayer.speedInAir = 0.075F;
                        }
                    } else {
                        mc.timer.timerSpeed = 1.00F;
                        mc.thePlayer.speedInAir = 0.04F;
                    }
                }
                break;
            case "CubeCraft":
                double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
                double x = -Math.sin(yaw) * 3;
                double z = Math.cos(yaw) * 3;
                if (mc.thePlayer.ticksExisted % 4 == 0)
                    mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
                jump();
                mc.timer.timerSpeed = 0.3F;
                mc.thePlayer.jumpMovementFactor = 0.1F;
                break;
            case "BlocksMC":
                jump();
                break;
            case "HiveMC":
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    mc.timer.timerSpeed = 1.0F;
                    if (hiveCount < 2)
                        hiveCount++;
                }
                if (mc.thePlayer.isAirBorne && hiveCount > 1) {
                    mc.thePlayer.jumpMovementFactor = 0.085F;
                }
                break;
            case "Timolia":
                jump();
                if (mc.thePlayer.onGround)
                    mc.timer.timerSpeed = 2.0F;
                else
                    mc.timer.timerSpeed = 1.0F;
                mc.thePlayer.jumpMovementFactor = 0.027F;
                break;
            case "Intave":
                jump();
                if (mc.thePlayer.onGround)
                    mc.timer.timerSpeed = 1.0F;
                else
                    mc.timer.timerSpeed = 1.0F;
                mc.thePlayer.jumpMovementFactor = 0.025F;
                break;
        }
    }

    public void speedModeLowHop(Event event) {
        if (!speedMode.getSelectedCombo().equals("LowHop"))
            return;

        switch (lowHopMode.getSelectedCombo()) {
            case "HiveMC":
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    mc.timer.timerSpeed = 1.0F;
                }
                if (mc.thePlayer.fallDistance > 0.4 && hiveCount < 2) {
                    hiveCount++;
                    mc.thePlayer.fallDistance = 0;
                }
                if (mc.thePlayer.isAirBorne && hiveCount > 1) {
                    mc.thePlayer.motionY -= 0.024999;
                    mc.thePlayer.jumpMovementFactor = 0.032F;
                }
                break;
        }
    }

    public void speedModeOnGround(Event event) {
        if (!speedMode.getSelectedCombo().equals("OnGround"))
            return;

        switch (onGroundMode.getSelectedCombo()) {
            case "MinePlex":
                double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
                double x = -Math.sin(yaw) * 0.06;
                double z = Math.cos(yaw) * 0.06;
                if (mc.thePlayer.ticksExisted % 4 == 0)
                    mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
                mc.timer.timerSpeed = 1.1F;
                break;
            case "CubeCraft":
                mc.thePlayer.setSprinting(false);
                yaw = Math.toRadians(mc.thePlayer.rotationYaw);
                x = -Math.sin(yaw) * 3;
                z = Math.cos(yaw) * 3;
                if (mc.thePlayer.ticksExisted % 2 == 0)
                    mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
                mc.timer.timerSpeed = 0.6F;
                break;
        }
    }
}