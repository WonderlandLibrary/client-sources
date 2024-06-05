package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.logger.impl.IngameChatLog;
import de.tired.api.util.math.MathUtil;
import de.tired.api.util.math.TimerUtil;
import de.tired.event.EventTarget;
import de.tired.event.events.EventPreMotion;
import de.tired.event.events.PacketEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.tired.other.ClientHelper;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;

@ModuleAnnotation(name = "Speed", key = Keyboard.KEY_NONE, category = ModuleCategory.MOVEMENT, clickG = "Move faster.")
public class Speed extends Module {


    private final TimerUtil timerUtil = new TimerUtil();
    public ModeSetting speedMode = new ModeSetting("SpeedMode", this, new String[]{"BlocksMC", "Medusa", "Strafe", "Custom", "Karhu", "Hypixel", "AAC4", "ACR", "Tired", "LowHop", "OldAAC", "Spartan440.1", "Spartan441", "LatestVerus", "NCP.3.16.1", "Legit", "YPort", "NormalBypass"});
    private boolean airState, verusB3733FirstHop = false, verusB3733SpoofGround = false, verusB3733IsGround = false;
    private int stages = 0, verusB3733Stage = 0;
    public double verusB3733Speed = 0;

    public BooleanSetting jump = new BooleanSetting("jump", this, true, () -> speedMode.getValue().equalsIgnoreCase("custom"));

    public BooleanSetting speedBoost = new BooleanSetting("speedBoost", this, true, () -> speedMode.getValue().equalsIgnoreCase("custom"));

    public BooleanSetting speedTickExist = new BooleanSetting("speedTickExist", this, true, () -> speedMode.getValue().equalsIgnoreCase("custom") && speedBoost.getValue());

    public BooleanSetting speedOnlyAfterJump = new BooleanSetting("speedOnlyAfterJump", this, true, () -> speedMode.getValue().equalsIgnoreCase("custom") && speedBoost.getValue() && jump.getValue());

    public NumberSetting tickExist = new NumberSetting("tickExist", this, 5, 1, 200, 1, () -> speedBoost.getValue() && speedTickExist.getValue() && speedMode.getValue().equalsIgnoreCase("custom"));

    public NumberSetting speed = new NumberSetting("speed", this, .3, .01, 6, .01, () -> speedBoost.getValue() && speedMode.getValue().equalsIgnoreCase("custom"));

    public BooleanSetting strafe = new BooleanSetting("strafe", this, true, () -> speedMode.getValue().equalsIgnoreCase("custom") && jump.getValue());

    public BooleanSetting timer = new BooleanSetting("timer", this, true, () -> speedMode.getValue().equalsIgnoreCase("custom"));

    public NumberSetting timerValue = new NumberSetting("timerSpeed", this, 1, .1, 20, 1, () -> timer.getValue() && speedMode.getValue().equalsIgnoreCase("custom"));

    public BooleanSetting timerTickExistBoolean = new BooleanSetting("timerTickExistValue", this, true, () -> speedMode.getValue().equalsIgnoreCase("custom"));

    public NumberSetting timerTickExist = new NumberSetting("timerTickExist", this, 5, 1, 200, 1, () -> timer.getValue() && timerTickExistBoolean.getValue() && speedMode.getValue().equalsIgnoreCase("custom"));


    @EventTarget
    public void onPacket(PacketEvent e) {
        if (speedMode.getValue().equalsIgnoreCase("latestVerus")) {
            final Packet<?> packet = e.getPacket();
            if (packet instanceof C03PacketPlayer) {
                final C03PacketPlayer packetPlayer = (C03PacketPlayer) packet;
                if (verusB3733SpoofGround) {
                    packetPlayer.onGround = true;
                }

            }
        }
    }

    @EventTarget
    public void onPre(EventPreMotion e) {

        if (speedMode.getValue().equalsIgnoreCase("ACR")) {
            int tickExits = MC.thePlayer.ticksExisted;
            final boolean tick3 = tickExits % 3 == 0;
            final boolean tick4 = tickExits % 4 == 0;

            float yaw = MC.thePlayer.rotationYaw;

            if (tick3) {
                yaw =  Math.abs(MC.thePlayer.rotationYaw);
            }

            if (tick4) {
                yaw =  Math.abs(MC.thePlayer.rotationYaw);
            }

            MC.thePlayer.rotationYaw = yaw;
            e.yaw = yaw;

        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        setDesc(speedMode.getValue());
        if (!ClientHelper.INSTANCE.moving()) return;
        switch (speedMode.getValue()) {
            case "LowHop":
                if (MC.thePlayer.onGround && ClientHelper.INSTANCE.moving()) {
                    MC.thePlayer.onGround = true;
                    //         ClientHelper.INSTANCE.doSpeedup((MC.thePlayer.ticksExisted % 2 == 0) ? 0.30 : 0.46);
                    MC.thePlayer.motionY = 0.308676344563F;
                }
                break;

            case "AAC4": {

                if (blockUnder(MC.thePlayer, .4f) != Blocks.air) {
                    MC.timer.timerSpeed = 1.2f;
                } else {
                    MC.timer.timerSpeed = .944F;

                }

                if (MC.thePlayer.onGround) {
                    MC.thePlayer.jump();
                } else {
                    MC.timer.timerSpeed = 1.002f;
                }

            }
            break;

            case "Medusa": {


                if (MC.thePlayer.onGround) {
                    MC.thePlayer.jump();
                } else {
                    if (MC.thePlayer.isCollided) {
                        ClientHelper.INSTANCE.doSpeedup(.21);
                    } else {
                        MC.thePlayer.speedInAir = .025F;
                    }
                }
            }
            break;

            case "Strafe": {
                ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ));
                if (MC.thePlayer.onGround) {
                    MC.thePlayer.jump();
                }

            }
            break;

            case "Custom": {

                if (strafe.getValue()) {
                    ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ));
                }

                if (timer.getValue()) {
                    if (timerTickExistBoolean.getValue()) {
                        if (MC.thePlayer.ticksExisted % timerTickExist.getValueInt() == 0) {
                            MC.timer.timerSpeed = timerValue.getValueFloat();
                        } else {
                            MC.timer.timerSpeed = 1.0F;
                        }
                    } else {
                        MC.timer.timerSpeed = timerValue.getValueFloat();
                    }
                }

                if (jump.getValue()) {
                    if (MC.thePlayer.onGround) {
                        MC.thePlayer.jump();
                    } else {
                        if (speedOnlyAfterJump.getValue()) {
                            if (speedBoost.getValue()) {
                                if (speedTickExist.getValue()) {
                                    if (MC.thePlayer.ticksExisted % tickExist.getValueInt() == 0) {
                                        ClientHelper.INSTANCE.doSpeedup(speed.getValue());
                                    }
                                } else {
                                    ClientHelper.INSTANCE.doSpeedup(speed.getValue());
                                }
                            }
                        }
                        if (!speedOnlyAfterJump.getValue()) {
                            if (speedBoost.getValue()) {
                                if (speedTickExist.getValue()) {
                                    if (MC.thePlayer.ticksExisted % tickExist.getValueInt() == 0) {
                                        ClientHelper.INSTANCE.doSpeedup(speed.getValue());
                                    } else {
                                        ClientHelper.INSTANCE.doSpeedup(speed.getValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            break;

            case "Hypixel": {
                if (!Keyboard.isKeyDown(57)) {
                    if (MC.thePlayer.onGround) { // Moving on Ground
                        MC.thePlayer.jump();
                    }
                    if (ClientHelper.INSTANCE.moving()) {
                        if (MC.thePlayer.fallDistance > 0.6) {
                            MC.timer.timerSpeed = 1.11f;
                            MC.thePlayer.speedInAir = 0.02030f;
                        } else if (MC.thePlayer.fallDistance > 0.2) {
                            MC.timer.timerSpeed = 1.15f;
                        } else if (MC.thePlayer.fallDistance > 0.1) {
                            MC.timer.timerSpeed = 1.16f;
                        }
                    }
                }
            }
            break;

            case "Karhu": {
                if (!Keyboard.isKeyDown(57)) {
                    if (MC.thePlayer.onGround) { // Moving on Ground
                        MC.thePlayer.jump();
                    }
                    if (ClientHelper.INSTANCE.moving()) {
                        if (MC.thePlayer.fallDistance > 0.6) {
                            ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ) * 1.0023);
                            MC.thePlayer.onGround = true;
                        }

                        if (MC.thePlayer.motionY > .4) {
                            MC.thePlayer.motionY = 0.4000000059604645D;
                        }

                        if (MC.thePlayer.motionY == 0.4000000059604645D) {
                            MC.thePlayer.fallDistance = 0;
                            MC.thePlayer.motionY = 0.42F;
                            ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ) * 1.0023);
                        }
                    }
                }
            }
            break;

            case "ACR": {

                if (MC.thePlayer.onGround) {
                    MC.thePlayer.jump();
                } else {

                    double roundedMotion = Math.round(MC.thePlayer.motionY * 10.0) / 10.0;

                    int tickExits = MC.thePlayer.ticksExisted;
                    final boolean tick3 = tickExits % 3 == 0;
                    final boolean tick4 = tickExits % 4 == 0;

                    if (tick4) {
                        ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ) * 1.0027);
                    }
                    if (tick3) {
                        ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ) * 1.0057);

                    }
                }

            }
            break;

            case "BlocksMC":
                if (!Keyboard.isKeyDown(57)) {
                    MC.timer.timerSpeed = 1;
                    ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ) * 1.0157);
                    if (MC.thePlayer.onGround) {
                        MC.thePlayer.jump();
                    } else {
                        MC.thePlayer.motionX *= 1.0058F;
                        MC.thePlayer.motionZ *= 1.0058F;
                        MC.thePlayer.moveStrafing *= 2;
                    }

                    if (MC.thePlayer.ticksExisted % 45 == 0) {
                        MC.thePlayer.motionY -= 0.3;
                        MC.thePlayer.moveStrafing *= 3;
                        MC.thePlayer.motionX *= 1.0018F;
                        MC.thePlayer.motionZ *= 1.0018F;

                    }

                    if (MC.thePlayer.ticksExisted % 12 == 0 && MC.thePlayer.onGround) {
                        MC.thePlayer.motionY = 0.333333f;
                    }

                }

                break;
            case "Spartan441":
                if (ClientHelper.INSTANCE.moving()) if (getPlayer().onGround) {
                    ClientHelper.INSTANCE.doSpeedup(1.9);
                    getPlayer().jump();
                }
                break;
            case "Spartan440.1":
                double tickPos = 0;
                if (MC.thePlayer.onGround) {
                    MC.thePlayer.jump();
                } else {
                    if (MC.thePlayer.ticksExisted % MathUtil.getRandom(15, 37) == 0) {
                        tickPos = MC.thePlayer.motionY;
                    }

                    if (MC.thePlayer.posY > 0.4000000059604645D) {
                        MC.thePlayer.posY = 0.4000000059604645D; //fixxing spartan´s weird motion y autism
                    }

                    int tickExits = MC.thePlayer.ticksExisted;
                    final boolean tick3 = tickExits % 3 == 0;
                    final boolean tick4 = tickExits % 4 == 0;

                    if (tick4)
                        ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ) * 1.0327);

                    if (tick3)
                        ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ) * 1.0257);

                }


                if (tickPos != 0) {
                    MC.timer.timerSpeed = 3;
                } else {
                    MC.timer.timerSpeed = 1;
                }
                break;

            case "LatestVerus": {
                if (!(getBlockUnderPlayer(1) instanceof BlockAir)) {
                    getTimer().timerSpeed = 1F;
                    if (!ClientHelper.INSTANCE.moving()) {
                        verusB3733Stage = 0;
                        verusB3733FirstHop = true;
                        return;
                    }
                    if (!verusB3733IsGround) {
                        verusB3733IsGround = getPlayer().onGround;
                        return;
                    }
                    if (verusB3733FirstHop) {
                        if (getPlayer().onGround) {
                            getPlayer().motionY = (double) 0.42F;
                            verusB3733SpoofGround = true;
                            verusB3733Stage = 0;
                        } else if (verusB3733Stage <= 7) {
                            getPlayer().motionY = 0;
                            verusB3733Stage++;
                        } else {
                            verusB3733SpoofGround = false;
                            verusB3733FirstHop = false;
                        }
                    } else {
                        if (getPlayer().onGround) {
                            verusB3733Speed = 0.5;
                            getPlayer().motionY = (double) 0.42F;
                            verusB3733SpoofGround = true;
                            verusB3733Stage = 0;
                        } else if (verusB3733Stage <= 7) {
                            verusB3733Speed += 0.12;
                            getPlayer().motionY = 0;
                            verusB3733Stage++;
                        } else {
                            verusB3733Speed = 0.24;
                            verusB3733SpoofGround = false;
                        }
                        ClientHelper.INSTANCE.doSpeedup(verusB3733Speed - 1E-4);
                    }
                }
            }
            break;

            case "OldAAC": {
                MC.timer.timerSpeed = 1.6F;
                if (!Keyboard.isKeyDown(57)) {
                    if (MC.thePlayer.hurtTime != 0) {
                        return;
                    }
                    if (MC.thePlayer.onGround) {
                        MC.thePlayer.jump();
                        MC.thePlayer.motionX *= 1.008;
                        MC.thePlayer.motionZ *= 1.008;
                    } else {
                        MC.timer.timerSpeed = 1.065F;
                        MC.thePlayer.motionX *= 1.0045F;
                        MC.thePlayer.motionZ *= 1.0045F;
                        MC.thePlayer.moveStrafing *= 3f;
                    }
                } else {
                    if (!MC.thePlayer.onGround && timerUtil.reachedTime(1L) && !airState) {
                        airState = true;
                        MC.thePlayer.motionX *= -1;
                        MC.thePlayer.motionZ *= -1;
                    } else if (MC.thePlayer.onGround) {
                        airState = false;
                    }
                }

            }
            break;
            case "NormalBypass": {
                double speed = 0.78;

                if (!MC.thePlayer.isPotionActive(Potion.poison)) {
                    if (ClientHelper.INSTANCE.getPotionLevel() != 0) {
                        speed += 0.1;
                    }

                    if (MC.thePlayer.hurtTime != 0) {
                        speed += 0.07 + MC.thePlayer.motionY / 2;
                    }

                    if (MC.thePlayer.onGround) {
                        MC.thePlayer.jump();
                    } else {
                        if (MC.thePlayer.motionY == 0.4000000059604645D) {
                            MC.thePlayer.motionY += .1f;
                        }
                        switch ((int) MC.thePlayer.motionY) {
                            case 1:
                                MC.thePlayer.motionY -= 0.01;
                                break;
                            case 2:
                                MC.thePlayer.motionY -= 0.02;
                                break;
                            case 3:
                                MC.thePlayer.motionY -= 0.03;
                                break;
                            default:
                                MC.thePlayer.motionY -= 0;
                        }

                        ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ) * 1 + ((speed / Math.PI) / 8));

                    }

                }

            }
            break;

            case "Tired": {

                final String motionToString = String.valueOf(MC.thePlayer.motionY);

                if (MC.thePlayer.onGround) {
                    MC.thePlayer.jump();
                    ClientHelper.INSTANCE.doSpeedup(.53);
                }
                if (motionToString.contains("0.41") || motionToString.startsWith("0.0")) {
                    ClientHelper.INSTANCE.doSpeedup(.43);
                }

                break;
            }

            case "Legit":
                if (!Keyboard.isKeyDown(57)) {
                    if (ClientHelper.INSTANCE.moving()) {
                        if (MC.thePlayer.onGround) {
                            MC.thePlayer.jump();
                        }
                    }
                }
                break;

            case "YPort":

                if (MC.thePlayer.hurtTime == 0) {
                    if (MC.thePlayer.onGround) {
                        MC.thePlayer.jump();

                    } else {
                        ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ) * 1.0071);
                    }

                }

                break;

            case "NCP.3.16.1":

                MC.thePlayer.distanceWalkedModified = 0.0f;
                if (MC.thePlayer.onGround) {
                    MC.thePlayer.jump();
                    MC.thePlayer.motionX *= 0.75;
                    MC.thePlayer.motionZ *= 0.75;
                    MC.timer.timerSpeed = .8F;
                } else {
                    if (MC.thePlayer.motionY < 0.4) {
                        MC.thePlayer.motionY = -69;
                        MC.timer.timerSpeed = 1.6F;
                        ClientHelper.INSTANCE.doSpeedup(Math.sqrt(MC.thePlayer.motionX * MC.thePlayer.motionX + MC.thePlayer.motionZ * MC.thePlayer.motionZ) * 1.02);
                    }
                }

                break;
        }
    }

    @Override
    public void onState() {
        MC.thePlayer.ticksExisted = 0;
        MC.timer.timerSpeed = 1;
        stages = 0;
        verusB3733Speed = 0;
        verusB3733SpoofGround = false;
        verusB3733FirstHop = true;
        verusB3733Stage = 0;
        verusB3733IsGround = false;
    }

    @Override
    public void onUndo() {
        MC.thePlayer.ticksExisted = 0;
        stages = 0;
        MC.timer.timerSpeed = 1;
    }
}
