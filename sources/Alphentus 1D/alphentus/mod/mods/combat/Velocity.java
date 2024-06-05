package alphentus.mod.mods.combat;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.TimeUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 31/07/2020.
 */
public class Velocity extends Mod {

    public Setting reverse = new Setting("Reverse Strength", 0.02F, 0.10F, 0.05F, false, this);
    public Setting velX = new Setting("Velocity XZ", 0, 100, 100, true, this);
    public Setting velY = new Setting("Velocity Y", 0, 100, 100, true, this);
    public Setting motionX = new Setting("Motion XZ", 0, 1, 1, false, this);
    public Setting motionY = new Setting("Motion Y", 0, 1, 1, false, this);
    public Setting delay = new Setting("Push Delay", 0, 300, 0, true, this);
    public Setting speedAir = new Setting("Air Speed", 0.2F, 1.00F, 0.2F, false, this);
    public Setting jumpFactor = new Setting("Jump Factor", 0.2F, 1.00F, 0.2F, false, this);
    public Setting jumpGround = new Setting("Jump", false, this);
    public Setting simGround = new Setting("Simulate Ground", false, this);
    public Setting cancel = new Setting("Cancel Velocity", false, this);
    String modes[] = {"Reverse", "Simple", "Simulate Ground", "Intave", "Custom"};
    public Setting setting = new Setting("Mode", modes, "Simple", this);
    private TimeUtil timeUtil = new TimeUtil();
    public float velocityX;
    public float velocityY;

    public Velocity() {
        super("Velocity", Keyboard.KEY_NONE, true, ModCategory.COMBAT);
        Init.getInstance().settingManager.addSetting(setting);
        Init.getInstance().settingManager.addSetting(reverse);
        Init.getInstance().settingManager.addSetting(velX);
        Init.getInstance().settingManager.addSetting(velY);
        Init.getInstance().settingManager.addSetting(motionX);
        Init.getInstance().settingManager.addSetting(motionY);
        Init.getInstance().settingManager.addSetting(delay);
        Init.getInstance().settingManager.addSetting(speedAir);
        Init.getInstance().settingManager.addSetting(jumpFactor);
        Init.getInstance().settingManager.addSetting(jumpGround);
        Init.getInstance().settingManager.addSetting(simGround);
        Init.getInstance().settingManager.addSetting(cancel);
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() == Type.RENDER2D) {
            if (setting.getSelectedCombo().equals("Reverse")) {
                reverse.setVisible(true);
            } else {
                reverse.setVisible(false);
            }

            if (setting.getSelectedCombo().equals("Custom")) {
                if (!cancel.isState()) {
                    velX.setVisible(true);
                    velY.setVisible(true);
                } else {
                    velX.setVisible(false);
                    velY.setVisible(false);
                }
                motionX.setVisible(true);
                motionY.setVisible(true);
                delay.setVisible(true);
                if (!simGround.isState()) {
                    speedAir.setVisible(true);
                    jumpFactor.setVisible(true);
                } else {
                    speedAir.setVisible(false);
                    jumpFactor.setVisible(false);
                }
                jumpGround.setVisible(true);
                simGround.setVisible(true);
                cancel.setVisible(true);
            } else {
                velX.setVisible(false);
                velY.setVisible(false);
                motionX.setVisible(false);
                motionY.setVisible(false);
                delay.setVisible(false);
                speedAir.setVisible(false);
                jumpFactor.setVisible(false);
                jumpGround.setVisible(false);
                simGround.setVisible(false);
                cancel.setVisible(false);
            }
        }

        if (getState()) {

            // Stuff for the NetHandlerPlayClient.java (Velocity amount in Percent) 100 means Legit, 0 is about the same as cancel
            if (setting.getSelectedCombo().equals("Custom")) {
                velocityX = velX.getCurrent();
                velocityY = velY.getCurrent();
            } else if (setting.getSelectedCombo().equals("Intave")) {
                velocityX = 100 * -0.1F;
                velocityY = 100;
            } else {
                velocityX = 100;
                velocityY = 100;
            }

            if (mc.thePlayer.isPotionActive(Potion.poison))
                return;
            if (mc.thePlayer.isBurning())
                return;

            if (setting.getSelectedCombo().equals("Simulate Ground")) {
                if (event.getType() == Type.TICKUPDATE) {
                    if (!getInfoName().equals("Simulate Ground"))
                        this.setInfoName("Simulate Ground");
                    if (mc.thePlayer.hurtTime != 0) {
                        mc.thePlayer.onGround = true;
                    }
                }
            }

            if (setting.getSelectedCombo().equals("Reverse")) {
                if (event.getType() == Type.TICKUPDATE) {
                    if (!getInfoName().equals("Reverse"))
                        this.setInfoName("Reverse");
                    if (mc.thePlayer.hurtTime != 0) {
                        jump();
                        mc.thePlayer.jumpMovementFactor = reverse.getCurrent();
                        mc.thePlayer.speedInAir = reverse.getCurrent();
                    } else {
                        if (mc.thePlayer.speedInAir == reverse.getCurrent())
                            mc.thePlayer.speedInAir = 0.02F;
                    }
                }
            }

            if (setting.getSelectedCombo().equals("Simple")) {
                if (event.getType() == Type.TICKUPDATE) {
                    if (!getInfoName().equals("Simple"))
                        this.setInfoName("Simple");
                }
                if (event.getType() == Type.RECIEVE) {
                    Packet packet = event.getPacket();
                    if (packet instanceof S12PacketEntityVelocity) {
                        if (((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
                            event.setCancelled(true);
                        }
                    }
                }
            }

            if (setting.getSelectedCombo().equals("Intave")) {
                if (event.getType() == Type.TICKUPDATE) {
                    if (!getInfoName().equals("Intave"))
                        this.setInfoName("Intave");

                    if (mc.thePlayer.hurtTime != 0) {
                    //    mc.thePlayer.setSprinting(true);
                    //    mc.thePlayer.setSprinting(false);
                    //    jump();
                    }

                    if (!mc.thePlayer.onGround) {
                    //    if (mc.thePlayer.hurtTime > 1 && mc.thePlayer.hurtTime < 9) {
                    //        mc.gameSettings.keyBindSneak.pressed = false;
                    //    } else if (mc.thePlayer.hurtTime == 9) {
                    //        mc.gameSettings.keyBindSneak.pressed = true;
                    //    }
                    }

                }
            }

            if (setting.getSelectedCombo().equals("Custom")) {
                if (event.getType() == Type.TICKUPDATE) {
                    if (!getInfoName().equals("Custom"))
                        this.setInfoName("Custom");
                    if (mc.thePlayer.hurtTime != 0 && !mc.thePlayer.isBurning()) {
                        mc.thePlayer.motionX *= motionX.getCurrent();
                        mc.thePlayer.motionY *= motionY.getCurrent();
                        mc.thePlayer.motionZ *= motionX.getCurrent();
                        if (jumpGround.isState())
                            jump();
                        if (timeUtil.isDelayComplete(delay.getCurrent())) {
                            if (simGround.isState()) {
                                mc.thePlayer.onGround = true;
                            } else {
                                mc.thePlayer.onGround = false;
                                mc.thePlayer.speedInAir = speedAir.getCurrent() / 10;
                                mc.thePlayer.jumpMovementFactor = jumpFactor.getCurrent() / 10;
                            }
                        }
                    } else {
                        if (mc.thePlayer.speedInAir == speedAir.getCurrent() / 10)
                            mc.thePlayer.speedInAir = 0.02F;
                        timeUtil.reset();
                    }
                }

                if (event.getType() == Type.RECIEVE) {
                    Packet packet = event.getPacket();
                    if (packet instanceof S12PacketEntityVelocity) {
                        if (((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
                            if (cancel.isState())
                                event.setCancelled(true);
                        }
                    }
                }
            }
        } else {
            velocityX = 100;
            velocityY = 100;
        }
    }

    public double getYaw(double blockX, double blockZ) {
        return (MathHelper.func_181159_b(blockZ - mc.thePlayer.posZ, blockX - mc.thePlayer.posX) * 180.0D / Math.PI) - 90.0F;
    }
}