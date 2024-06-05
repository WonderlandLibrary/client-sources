package alphentus.mod.mods.movement;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.MovementUtils;
import alphentus.utils.StrafeUtil;
import alphentus.utils.Translate;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 31/07/2020.
 */
public class LongJump extends Mod {

    String[] longModes = {"NeruxVace", "BlocksMC", "CubeCraft"};
    public Setting longMode = new Setting("Mode", longModes, "BlocksMC", this);
    Translate height;
    Translate speed;
    private double moveSpeed;
    private int stage;
    private boolean slowingNeruxVaceLongJumpDown = false;

    public LongJump() {
        super("LongJump", Keyboard.KEY_NONE, true, ModCategory.MOVEMENT);
        Init.getInstance().settingManager.addSetting(longMode);
    }

    @Override
    public void onEnable() {
        this.moveSpeed = 0.0D;
        this.stage = 0;
        slowingNeruxVaceLongJumpDown = false;
        height = new Translate(0, 0);
        speed = new Translate(0, 0);

        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        mc.thePlayer.speedInAir = 0.02F;
        mc.timer.timerSpeed = 1.00F;
        super.onDisable();
    }

    @EventTarget
    public void event(Event event) {
        if (!getState())
            return;


        if (event.getType() == Type.TICKUPDATE) {
            Minecraft.getMinecraft().gameSettings.keyBindLeft.pressed = false;
            Minecraft.getMinecraft().gameSettings.keyBindRight.pressed = false;
            Minecraft.getMinecraft().gameSettings.keyBindJump.pressed = false;
            Minecraft.getMinecraft().gameSettings.keyBindBack.pressed = false;

            if (longMode.getSelectedCombo().equals("CubeCraft")) {
                double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
                double x = -Math.sin(yaw) * 1.5;
                double z = Math.cos(yaw) * 1.5;
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                } else {
                    if (mc.thePlayer.fallDistance > 0.3) {
                        mc.timer.timerSpeed = 0.5F;
                        mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY - 0.1, mc.thePlayer.posZ + z);
                        mc.thePlayer.onGround = true;
                        mc.thePlayer.onGround = false;
                    }
                }
            }
        }

        if (event.getType() == Type.MOVE) {
            if (longMode.getSelectedCombo().equals("NeruxVace")) {
                if (mc.thePlayer.onGround)
                    moveSpeed = 0F;

                if (Minecraft.getMinecraft().thePlayer.isMoving()) {
                    mc.thePlayer.setSprinting(false);
                    switch (this.stage) {
                        case 2:
                            if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                                event.setY(mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(height.getY()));
                                this.moveSpeed = speed.getX();
                            }
                            break;
                        case 3:
                            this.moveSpeed = speed.getX() - 1F;
                            break;
                        default:
                            height.interpolate(0, (float) (5 * stage / 0.5), 1);
                            speed.interpolate((float) (60 * stage / 2), 0, 1);
                            break;
                    }
                    this.stage++;
                    MovementUtils.setSpeed(event, moveSpeed);

                    if (mc.thePlayer.fallDistance > 4)
                        moveSpeed = 0;
                }
            } else if (longMode.getSelectedCombo().equals("BlocksMC")) {
                if (mc.thePlayer.onGround)
                    this.moveSpeed = 0F;

                if (mc.thePlayer.fallDistance > 0) {
                    setState(false);
                }

                if (mc.thePlayer.motionY > 0.1)
                    this.moveSpeed = 0.5F;

                if (Minecraft.getMinecraft().thePlayer.isMoving()) {
                    mc.thePlayer.setSprinting(false);
                    switch (this.stage) {
                        case 0:
                        case 1:
                            this.moveSpeed = 0.0D;
                            break;
                        case 2:
                            if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                                event.setY(mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(0));
                                this.moveSpeed = 0.45D * stage / 2;
                            }
                            break;
                        case 3:
                            event.setY(mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(0.14 * stage / 0.5));
                            this.moveSpeed = 0.50D * stage / 2;
                            break;
                        case 4:
                            break;
                        default:
                            if (mc.thePlayer.motionY < 0.0D && mc.thePlayer.fallDistance <= 0)
                                mc.thePlayer.motionY *= 0.40D;
                            break;
                    }
                    this.stage++;
                    if (mc.thePlayer.fallDistance <= 0) {
                        MovementUtils.setSpeed(event, moveSpeed);
                    }
                }
            }
        }
    }
}