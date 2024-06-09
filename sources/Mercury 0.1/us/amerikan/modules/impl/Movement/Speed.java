/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Movement;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import us.amerikan.amerikan;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.TimeHelper;

public class Speed
extends Module {
    public int delay;
    int delay2 = 600;
    float delay3 = 4.0f;
    int experimental1;
    int experimental2;
    private TimeHelper speedTime = new TimeHelper();
    double groundYPort;
    int gr\u00f6sserWerdendeZahl = 1;
    boolean faaf;
    int feef;
    float cameraPitch;
    float cameraYaw;
    double cubeHeight;
    private TimeHelper timer = new TimeHelper();
    private double speed = 0.0;

    public Speed() {
        super("Speed", "Speed", 0, Category.MOVEMENT);
        ArrayList<String> options = new ArrayList<String>();
        options.add("AAC");
        options.add("AAC Strafe");
        options.add("CubeCraft");
        options.add("CubeCraft New");
        options.add("CubeCraft Bhop2");
        options.add("NCP LowHop");
        options.add("Port");
        options.add("Strafe");
        options.add("Strafe-Port-B-Hop");
        options.add("Y-Port");
        options.add("Experimental");
        options.add("OldAAC Speed");
        options.add("Jartex");
        options.add("Hypixel Bhop");
        amerikan.setmgr.rSetting(new Setting("Speed Mode", this, "AAC", options));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @EventTarget
    public void onUpdate(EventUpdate event) {
        block115 : {
            block116 : {
                block113 : {
                    block114 : {
                        block112 : {
                            block111 : {
                                if (this.isEnabled() == false) return;
                                if (Speed.mc.theWorld == null) return;
                                if (Minecraft.thePlayer == null) return;
                                if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("AAC")) {
                                    this.setAddon("AAC");
                                    if (Speed.isPressed()) {
                                        if (Minecraft.thePlayer.onGround) {
                                            Minecraft.thePlayer.jump();
                                        } else {
                                            Minecraft.thePlayer.motionY -= 0.01;
                                            Minecraft.thePlayer.motionX *= 1.009;
                                            Minecraft.thePlayer.motionZ *= 1.009;
                                        }
                                    }
                                }
                                if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Cubecraft Bhop2")) {
                                    this.setAddon("Cubecraft Bhop2");
                                    if (Minecraft.thePlayer.onGround) {
                                        Minecraft.thePlayer.jump();
                                        Timer.timerSpeed = 1.3f;
                                    } else if (!Minecraft.thePlayer.onGround) {
                                        yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
                                        if (Minecraft.thePlayer.fallDistance >= 0.3f) {
                                            Timer.timerSpeed = 1.05f;
                                            Minecraft.thePlayer.motionX = -Math.sin(yaw) * 0.42;
                                            Minecraft.thePlayer.motionZ = Math.cos(yaw) * 0.42;
                                        }
                                    }
                                }
                                time = new TimeHelper();
                                if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Hypixel Bhop")) {
                                    this.setAddon("Hypixel Bhop");
                                    Speed.mc.gameSettings.keyBindSprint.pressed = true;
                                    Speed.mc.gameSettings.keyBindForward.pressed = true;
                                    if (Minecraft.thePlayer.onGround) {
                                        this.speed = 0.45;
                                        Minecraft.thePlayer.jump();
                                    } else {
                                        yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
                                        Minecraft.thePlayer.motionX = -Math.sin(yaw) * this.speed;
                                        Minecraft.thePlayer.motionZ = Math.cos(yaw) * this.speed;
                                        if (TimeHelper.hasReached(20L) && this.speed > 0.27) {
                                            this.speed -= 0.007;
                                        }
                                    }
                                }
                                if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Cubecraft New")) {
                                    this.setAddon("Cubecraft New");
                                    Speed.mc.gameSettings.keyBindSprint.pressed = true;
                                    Speed.mc.gameSettings.keyBindForward.pressed = true;
                                    if (Minecraft.thePlayer.onGround) {
                                        this.speed = 0.55;
                                        Minecraft.thePlayer.jump();
                                    } else {
                                        yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
                                        Minecraft.thePlayer.motionX = -Math.sin(yaw) * this.speed;
                                        Minecraft.thePlayer.motionZ = Math.cos(yaw) * this.speed;
                                        if (TimeHelper.hasReached(20L) && this.speed > 0.27) {
                                            this.speed -= 0.007;
                                        }
                                    }
                                }
                                if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("OldAAC Speed")) {
                                    this.setAddon("OldAAC Ground");
                                    if (Minecraft.thePlayer.onGround) {
                                        Minecraft.thePlayer.jump();
                                        Minecraft.thePlayer.motionY -= 0.43;
                                    }
                                }
                                if (!amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Jartex")) break block111;
                                this.setAddon("Jartex");
                                stop = false;
                                if (!TimeHelper.hasReached(50L)) ** GOTO lbl-1000
                                if (Minecraft.thePlayer.onGround) {
                                    stop = true;
                                    TimeHelper.reset();
                                } else lbl-1000: // 2 sources:
                                {
                                    stop = false;
                                }
                                if (Minecraft.thePlayer.onGround && !stop) {
                                    Minecraft.thePlayer.jump();
                                    Minecraft.thePlayer.motionY -= 0.43;
                                }
                            }
                            if (!amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("AAC Strafe")) break block112;
                            this.setAddon("AAC Strafe");
                            currentSpeed = Math.sqrt(Math.pow(Minecraft.thePlayer.motionX, 2.0) + Math.pow(Minecraft.thePlayer.motionZ, 2.0));
                            movementInput = Minecraft.thePlayer.movementInput;
                            forward = movementInput.moveForward;
                            strafe = movementInput.moveStrafe;
                            yaw = Minecraft.thePlayer.rotationYaw;
                            if (forward == 0.0f && strafe == 0.0f) {
                                Minecraft.thePlayer.motionX = 0.0;
                                Minecraft.thePlayer.motionZ = 0.0;
                            } else if (forward != 0.0f) {
                                if (strafe >= 1.0f) {
                                    yaw += (float)(forward > 0.0f ? -45 : 45);
                                    strafe = 0.0f;
                                } else if (strafe <= -1.0f) {
                                    yaw += (float)(forward > 0.0f ? 45 : -45);
                                    strafe = 0.0f;
                                }
                                if (forward > 0.0f) {
                                    forward = 1.0f;
                                } else if (forward < 0.0f) {
                                    forward = -1.0f;
                                }
                            }
                            mx = Math.cos(Math.toRadians(yaw + 90.0f));
                            mz = Math.sin(Math.toRadians(yaw + 90.0f));
                            ms = currentSpeed;
                            Minecraft.thePlayer.motionX = (double)forward * ms * mx + (double)strafe * ms * mz;
                            Minecraft.thePlayer.motionZ = (double)forward * ms * mz - (double)strafe * ms * mx;
                            if (!Speed.isPressed()) break block112;
                            if (!Minecraft.thePlayer.onGround) ** GOTO lbl-1000
                            if (Minecraft.thePlayer.hurtTime == 0) {
                                Minecraft.thePlayer.jump();
                                Minecraft.thePlayer.jump();
                                Minecraft.thePlayer.motionX *= 0.7;
                                Minecraft.thePlayer.motionZ *= 0.7;
                                Minecraft.thePlayer.motionY = 0.405;
                            } else lbl-1000: // 2 sources:
                            {
                                Minecraft.thePlayer.motionY -= 0.01499;
                                Minecraft.thePlayer.jumpMovementFactor = (double)Minecraft.thePlayer.fallDistance <= 0.1 ? 0.029f : 0.028f;
                            }
                            if (Minecraft.thePlayer.moveStrafing != 0.0f) {
                                Minecraft.thePlayer.motionX *= 0.93;
                                Minecraft.thePlayer.motionZ *= 0.93;
                            }
                        }
                        if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("CubeCraft")) {
                            this.setAddon("CubeCraft");
                            currentSpeed = Math.sqrt(Math.pow(Minecraft.thePlayer.motionX, 2.0) + Math.pow(Minecraft.thePlayer.motionZ, 2.0));
                            movementInput = Minecraft.thePlayer.movementInput;
                            forward = movementInput.moveForward;
                            strafe = movementInput.moveStrafe;
                            yaw = Minecraft.thePlayer.rotationYaw;
                            if (forward == 0.0f && strafe == 0.0f) {
                                Minecraft.thePlayer.motionX = 0.0;
                                Minecraft.thePlayer.motionZ = 0.0;
                            } else if (forward != 0.0f) {
                                if (strafe >= 1.0f) {
                                    yaw += (float)(forward > 0.0f ? -45 : 45);
                                    strafe = 0.0f;
                                } else if (strafe <= -1.0f) {
                                    yaw += (float)(forward > 0.0f ? 45 : -45);
                                    strafe = 0.0f;
                                }
                                if (forward > 0.0f) {
                                    forward = 1.0f;
                                } else if (forward < 0.0f) {
                                    forward = -1.0f;
                                }
                            }
                            mx = Math.cos(Math.toRadians(yaw + 90.0f));
                            mz = Math.sin(Math.toRadians(yaw + 90.0f));
                            ms = currentSpeed;
                            Minecraft.thePlayer.motionX = (double)forward * ms * mx + (double)strafe * ms * mz;
                            Minecraft.thePlayer.motionZ = (double)forward * ms * mz - (double)strafe * ms * mx;
                            if (Minecraft.thePlayer.onGround) {
                                Minecraft.thePlayer.jump();
                                Minecraft.thePlayer.motionX *= 0.1;
                                Minecraft.thePlayer.motionZ *= 0.1;
                                Minecraft.thePlayer.motionY = 0.19;
                            } else if (Minecraft.thePlayer.motionY <= -0.1) {
                                Minecraft.thePlayer.jumpMovementFactor = 0.48f;
                            }
                        }
                        if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("NCP LowHop")) {
                            this.setAddon("NCP LowHop");
                            currentSpeed = Math.sqrt(Math.pow(Minecraft.thePlayer.motionX, 2.0) + Math.pow(Minecraft.thePlayer.motionZ, 2.0));
                            movementInput = Minecraft.thePlayer.movementInput;
                            forward = movementInput.moveForward;
                            strafe = movementInput.moveStrafe;
                            yaw = Minecraft.thePlayer.rotationYaw;
                            if (forward == 0.0f && strafe == 0.0f) {
                                Minecraft.thePlayer.motionX = 0.0;
                                Minecraft.thePlayer.motionZ = 0.0;
                            } else if (forward != 0.0f) {
                                if (strafe >= 1.0f) {
                                    yaw += (float)(forward > 0.0f ? -45 : 45);
                                    strafe = 0.0f;
                                } else if (strafe <= -1.0f) {
                                    yaw += (float)(forward > 0.0f ? 45 : -45);
                                    strafe = 0.0f;
                                }
                                if (forward > 0.0f) {
                                    forward = 1.0f;
                                } else if (forward < 0.0f) {
                                    forward = -1.0f;
                                }
                            }
                            mx = Math.cos(Math.toRadians(yaw + 90.0f));
                            mz = Math.sin(Math.toRadians(yaw + 90.0f));
                            ms = currentSpeed;
                            Minecraft.thePlayer.motionX = (double)forward * ms * mx + (double)strafe * ms * mz;
                            Minecraft.thePlayer.motionZ = (double)forward * ms * mz - (double)strafe * ms * mx;
                            if (Minecraft.thePlayer.onGround) {
                                Minecraft.thePlayer.jump();
                                Minecraft.thePlayer.motionY = 0.4;
                            } else if (Minecraft.thePlayer.motionY < -0.15) {
                                Minecraft.thePlayer.motionY = -0.82;
                            }
                        }
                        if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Strafe")) {
                            this.setAddon("Strafe");
                            currentSpeed = Math.sqrt(Math.pow(Minecraft.thePlayer.motionX, 2.0) + Math.pow(Minecraft.thePlayer.motionZ, 2.0));
                            movementInput = Minecraft.thePlayer.movementInput;
                            forward = movementInput.moveForward;
                            strafe = movementInput.moveStrafe;
                            yaw = Minecraft.thePlayer.rotationYaw;
                            if (forward == 0.0f && strafe == 0.0f) {
                                Minecraft.thePlayer.motionX = 0.0;
                                Minecraft.thePlayer.motionZ = 0.0;
                            } else if (forward != 0.0f) {
                                if (strafe >= 1.0f) {
                                    yaw += (float)(forward > 0.0f ? -45 : 45);
                                    strafe = 0.0f;
                                } else if (strafe <= -1.0f) {
                                    yaw += (float)(forward > 0.0f ? 45 : -45);
                                    strafe = 0.0f;
                                }
                                if (forward > 0.0f) {
                                    forward = 1.0f;
                                } else if (forward < 0.0f) {
                                    forward = -1.0f;
                                }
                            }
                            mx = Math.cos(Math.toRadians(yaw + 90.0f));
                            mz = Math.sin(Math.toRadians(yaw + 90.0f));
                            ms = currentSpeed;
                            Minecraft.thePlayer.motionX = (double)forward * ms * mx + (double)strafe * ms * mz;
                            Minecraft.thePlayer.motionZ = (double)forward * ms * mz - (double)strafe * ms * mx;
                            if (Minecraft.thePlayer.onGround) {
                                if (Minecraft.thePlayer.hurtTime == 0) {
                                    Minecraft.thePlayer.jump();
                                }
                            }
                        }
                        if (!amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Strafe-Port-B-Hop")) break block113;
                        this.setAddon("Strafe-Port-B-Hop");
                        yaw = Minecraft.thePlayer.rotationYaw + 90.0f;
                        pitch = Minecraft.thePlayer.rotationPitch;
                        keyW = Keyboard.isKeyDown(17);
                        keyS = Keyboard.isKeyDown(31);
                        keyA = Keyboard.isKeyDown(30);
                        keyD = Keyboard.isKeyDown(32);
                        if (keyW) {
                            if (keyA) {
                                yaw -= 45.0f;
                            } else if (keyD) {
                                yaw += 45.0f;
                            }
                        } else if (keyS) {
                            yaw += 180.0f;
                            if (keyA) {
                                yaw += 45.0f;
                            } else if (keyD) {
                                yaw -= 45.0f;
                            }
                        } else if (keyA) {
                            yaw -= 90.0f;
                        } else if (keyD) {
                            yaw += 90.0f;
                        }
                        if (Minecraft.thePlayer.onGround) break block114;
                        if (keyW || keyA || keyS) ** GOTO lbl336
                        if (keyD) {
lbl336: // 2 sources:
                            Minecraft.thePlayer.motionX = Math.cos(Math.toRadians(yaw)) * 0.27;
                            Minecraft.thePlayer.motionZ = Math.sin(Math.toRadians(yaw)) * 0.27;
                            if (Minecraft.thePlayer.fallDistance > 0.01f) {
                                Timer.timerSpeed = 1.5f;
                                Minecraft.thePlayer.motionY = -0.261337;
                            } else {
                                Timer.timerSpeed = 1.0f;
                            }
                        } else {
                            Minecraft.thePlayer.motionX = 0.0;
                            Minecraft.thePlayer.motionZ = 0.0;
                        }
                        break block113;
                    }
                    Minecraft.thePlayer.jump();
                }
                if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Y-Port")) {
                    this.setAddon("Y-Port");
                    if (Speed.isPressed()) {
                        if (Minecraft.thePlayer.onGround) {
                            Minecraft.thePlayer.jump();
                        } else {
                            Minecraft.thePlayer.motionY = -0.21;
                        }
                    }
                }
                if (!amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Port")) break block115;
                this.setAddon("Port");
                if (!Speed.mc.inGameHasFocus) break block115;
                delay4 = false;
                yaw = Minecraft.thePlayer.rotationYaw + 90.0f;
                keyW = Keyboard.isKeyDown(17);
                keyS = Keyboard.isKeyDown(31);
                keyA = Keyboard.isKeyDown(30);
                keyD = Keyboard.isKeyDown(32);
                if (keyW) {
                    if (keyA) {
                        yaw -= 45.0f;
                    } else if (keyD) {
                        yaw += 45.0f;
                    }
                } else if (keyS) {
                    yaw += 180.0f;
                    if (keyA) {
                        yaw += 45.0f;
                    } else if (keyD) {
                        yaw -= 45.0f;
                    }
                } else if (keyA) {
                    yaw -= 90.0f;
                } else if (keyD) {
                    yaw += 90.0f;
                }
                if (keyW || keyA || keyS) break block116;
                if (!keyD) break block115;
            }
            vec = new Vec3(Minecraft.thePlayer.getLookVec().xCoord, Minecraft.thePlayer.posY, Minecraft.thePlayer.getLookVec().zCoord);
            if (Minecraft.thePlayer.onGround) {
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX + vec.xCoord * 0.7, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + vec.zCoord * 0.7, Minecraft.thePlayer.onGround));
                Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + vec.xCoord * 0.1, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + vec.zCoord * 0.1);
            }
        }
        if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Experimental") == false) return;
        this.setAddon("Experimental");
    }

    private boolean misPressed() {
        Speed.mc.gameSettings.keyBindJump.pressed = true;
        return false;
    }

    public static void teleportPlayer(double blockDistance) {
        Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + -Math.sin(Speed.direction()) * blockDistance, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + Math.cos(Speed.direction()) * blockDistance);
    }

    public static void teleportPlayerAndUpdate(double blockDistance) {
        Minecraft.thePlayer.setPositionAndUpdate(Minecraft.thePlayer.posX + -Math.sin(Speed.direction()) * blockDistance, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + Math.cos(Speed.direction()) * blockDistance);
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX + -Math.sin(Speed.direction()) * -blockDistance, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + Math.cos(Speed.direction()) * -blockDistance, Minecraft.thePlayer.onGround));
    }

    public static double direction() {
        return Math.toRadians(Minecraft.thePlayer.rotationYaw);
    }

    @Override
    public void onEnable() {
        if (Speed.mc.theWorld != null) {
            if (Minecraft.thePlayer != null) {
                this.faaf = false;
                this.feef = 0;
                this.delay = 0;
                this.delay2 = 600;
                this.delay3 = 4.0f;
                this.experimental1 = 0;
                this.experimental2 = 0;
                this.gr\u00f6sserWerdendeZahl = 1;
                this.cameraPitch = Minecraft.thePlayer.cameraPitch;
                this.cameraYaw = Minecraft.thePlayer.cameraYaw;
                this.cubeHeight = Minecraft.thePlayer.onGround ? 0.2 : 0.0;
                if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Strafe")) {
                    if (Minecraft.thePlayer.onGround) {
                        Minecraft.thePlayer.motionX = 0.0;
                        Minecraft.thePlayer.motionZ = 0.0;
                        Minecraft.thePlayer.jump();
                    }
                }
                if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Strafe")) {
                    if (Minecraft.thePlayer.onGround && Speed.isPressed()) {
                        Minecraft.thePlayer.jump();
                    }
                }
                Timer.timerSpeed = 1.0f;
                EventManager.register(this);
            }
        }
    }

    @Override
    public void onDisable() {
        this.speed = 0.0;
        this.feef = 0;
        this.delay = 0;
        this.delay2 = 600;
        this.experimental1 = 0;
        this.experimental2 = 0;
        this.gr\u00f6sserWerdendeZahl = 1;
        Minecraft.thePlayer.jumpMovementFactor = 0.02f;
        Minecraft.thePlayer.speedInAir = 0.02f;
        Minecraft.thePlayer.isDead = false;
        if (amerikan.setmgr.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("GommeOnGround")) {
            Minecraft.thePlayer.motionX *= 0.0;
            Minecraft.thePlayer.motionZ *= 0.0;
        }
        Minecraft.thePlayer.motionX *= 0.0;
        Minecraft.thePlayer.motionZ *= 0.0;
        EventManager.unregister(this);
        Timer.timerSpeed = 1.0f;
        Speed.mc.gameSettings.keyBindJump.pressed = false;
    }
}

