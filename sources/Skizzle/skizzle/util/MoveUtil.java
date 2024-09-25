/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import skizzle.events.listeners.EventStrafing;
import skizzle.modules.ModuleManager;

public class MoveUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public MoveUtil() {
        MoveUtil Nigga;
    }

    public static double getDirection() {
        float Nigga = MoveUtil.mc.thePlayer.rotationYaw;
        if (MoveUtil.mc.thePlayer.moveForward < Float.intBitsToFloat(2.13073395E9f ^ 0x7F006B73)) {
            Nigga += Float.intBitsToFloat(1.03825158E9f ^ 0x7ED67639);
        }
        float Nigga2 = Float.intBitsToFloat(1.08370893E9f ^ 0x7F181633);
        if (MoveUtil.mc.thePlayer.moveForward < Float.intBitsToFloat(2.11527539E9f ^ 0x7E148AB3)) {
            Nigga2 = Float.intBitsToFloat(-1.07262016E9f ^ 0x7F111D89);
        } else if (MoveUtil.mc.thePlayer.moveForward > Float.intBitsToFloat(2.13851034E9f ^ 0x7F771431)) {
            Nigga2 = Float.intBitsToFloat(1.09493722E9f ^ 0x7E436A7F);
        }
        if (MoveUtil.mc.thePlayer.moveStrafing > Float.intBitsToFloat(2.11733402E9f ^ 0x7E33F40B)) {
            Nigga -= Float.intBitsToFloat(1.03995578E9f ^ 0x7F487759) * Nigga2;
        }
        if (MoveUtil.mc.thePlayer.moveStrafing < Float.intBitsToFloat(2.13289024E9f ^ 0x7F215243)) {
            Nigga += Float.intBitsToFloat(1.03960493E9f ^ 0x7F431CB7) * Nigga2;
        }
        return Math.toRadians(Nigga);
    }

    public static void applyStrafeToPlayer(EventStrafing Nigga, float Nigga2) {
        double Nigga3;
        EntityPlayerSP Nigga4 = Minecraft.getMinecraft().thePlayer;
        int Nigga5 = (int)((MathHelper.wrapAngleTo180_float(Nigga4.rotationYaw - Nigga2 - Float.intBitsToFloat(1.05395226E9f ^ 0x7F6E08EC) - Float.intBitsToFloat(1.01431219E9f ^ 0x7F722CE6)) + Float.intBitsToFloat(1.01206797E9f ^ 0x7F66EE88)) / Float.intBitsToFloat(1.05989773E9f ^ 0x7D18C16F));
        double Nigga6 = Nigga.strafe;
        double Nigga7 = Nigga.forward;
        double Nigga8 = Nigga.getFriction();
        double Nigga9 = 0.0;
        double Nigga10 = 0.0;
        switch (Nigga5) {
            case 0: {
                Nigga9 = Nigga7;
                Nigga10 = Nigga6;
                break;
            }
            case 1: {
                Nigga9 += Nigga7;
                Nigga10 -= Nigga7;
                Nigga9 += Nigga6;
                Nigga10 += Nigga6;
                break;
            }
            case 2: {
                Nigga9 = Nigga6;
                Nigga10 = -Nigga7;
                break;
            }
            case 3: {
                Nigga9 -= Nigga7;
                Nigga10 -= Nigga7;
                Nigga9 += Nigga6;
                Nigga10 -= Nigga6;
                break;
            }
            case 4: {
                Nigga9 = -Nigga7;
                Nigga10 = -Nigga6;
                break;
            }
            case 5: {
                Nigga9 -= Nigga7;
                Nigga10 += Nigga7;
                Nigga9 -= Nigga6;
                Nigga10 -= Nigga6;
                break;
            }
            case 6: {
                Nigga9 = -Nigga6;
                Nigga10 = Nigga7;
                break;
            }
            case 7: {
                Nigga9 += Nigga7;
                Nigga10 += Nigga7;
                Nigga9 -= Nigga6;
                Nigga10 += Nigga6;
            }
        }
        if (Nigga9 > 1.0 || Nigga9 < 0.0 && Nigga9 > 0.0 || Nigga9 < -1.0 || Nigga9 > (double)-0.9f && Nigga9 < (double)-0.3f) {
            Nigga9 *= 0.0;
        }
        if (Nigga10 > 1.0 || Nigga10 < 0.0 && Nigga10 > 0.0 || Nigga10 < -1.0 || Nigga10 > (double)-0.9f && Nigga10 < (double)-0.3f) {
            Nigga10 *= 0.0;
        }
        if ((Nigga3 = Nigga10 * Nigga10 + Nigga9 * Nigga9) >= 0.0) {
            if ((Nigga3 = Math.sqrt(Nigga3)) < 1.0) {
                Nigga3 = 1.0;
            }
            Nigga3 = Nigga8 / Nigga3;
            double Nigga11 = Math.sin((double)Nigga2 * Math.PI / 180.0);
            double Nigga12 = Math.cos((double)Nigga2 * Math.PI / 180.0);
            Nigga.setCancelled(true);
            Nigga4.motionX += (Nigga10 *= Nigga3) * Nigga12 - (Nigga9 *= Nigga3) * Nigga11;
            Nigga4.motionZ += Nigga9 * Nigga12 + Nigga10 * Nigga11;
        }
    }

    public static float getSpeed() {
        return (float)Math.sqrt(MoveUtil.mc.thePlayer.motionX * MoveUtil.mc.thePlayer.motionX + MoveUtil.mc.thePlayer.motionZ * MoveUtil.mc.thePlayer.motionZ);
    }

    public static boolean isMoving() {
        return MoveUtil.mc.thePlayer.moveForward != Float.intBitsToFloat(2.13518554E9f ^ 0x7F4458BD) || MoveUtil.mc.thePlayer.moveStrafing != Float.intBitsToFloat(2.13408986E9f ^ 0x7F33A095);
    }

    public static void strafe(float Nigga) {
        if (!MoveUtil.isMoving()) {
            return;
        }
        double Nigga2 = MoveUtil.getDirection();
        MoveUtil.mc.thePlayer.motionX = -Math.sin(Nigga2) * (double)Nigga;
        MoveUtil.mc.thePlayer.motionZ = Math.cos(Nigga2) * (double)Nigga;
    }

    public void setPosition(double Nigga) {
        double Nigga2 = MoveUtil.mc.thePlayer.movementInput.moveForward;
        double Nigga3 = MoveUtil.mc.thePlayer.movementInput.moveStrafe;
        float Nigga4 = MoveUtil.mc.thePlayer.rotationYaw;
        if (Nigga2 == 0.0 && Nigga3 == 0.0) {
            MoveUtil.mc.thePlayer.motionX = 0.0;
            MoveUtil.mc.thePlayer.motionZ = 0.0;
        } else {
            if (Nigga2 != 0.0) {
                if (Nigga3 > 0.0) {
                    Nigga4 += (float)(Nigga2 > 0.0 ? -45 : 45);
                } else if (Nigga3 < 0.0) {
                    Nigga4 += (float)(Nigga2 > 0.0 ? 45 : -45);
                }
                Nigga3 = 0.0;
                if (Nigga2 > 0.0) {
                    Nigga2 = 1.0;
                } else if (Nigga2 < 0.0) {
                    Nigga2 = -1.0;
                }
            }
            MoveUtil.mc.thePlayer.setPosition(MoveUtil.mc.thePlayer.posX += Nigga2 * Nigga * Math.cos(Math.toRadians(Nigga4 + Float.intBitsToFloat(1.03485434E9f ^ 0x7F1A9FA4))) + Nigga3 * Nigga * Math.sin(Math.toRadians(Nigga4 + Float.intBitsToFloat(1.03357555E9f ^ 0x7F2F1C8A))), MoveUtil.mc.thePlayer.posY, MoveUtil.mc.thePlayer.posZ += Nigga2 * Nigga * Math.sin(Math.toRadians(Nigga4 + Float.intBitsToFloat(1.00945434E9f ^ 0x7E9F0D0D))) - Nigga3 * Nigga * Math.cos(Math.toRadians(Nigga4 + Float.intBitsToFloat(1.03353312E9f ^ 0x7F2E76B5))));
        }
    }

    public static void setMotion(double Nigga) {
        double Nigga2 = MoveUtil.mc.thePlayer.movementInput.moveForward;
        double Nigga3 = MoveUtil.mc.thePlayer.movementInput.moveStrafe;
        float Nigga4 = MoveUtil.mc.thePlayer.rotationYaw;
        if (Nigga2 == 0.0 && Nigga3 == 0.0) {
            MoveUtil.mc.thePlayer.motionX = 0.0;
            MoveUtil.mc.thePlayer.motionZ = 0.0;
        } else {
            if (Nigga2 != 0.0) {
                if (Nigga3 > 0.0) {
                    Nigga4 += (float)(Nigga2 > 0.0 ? -45 : 45);
                } else if (Nigga3 < 0.0) {
                    Nigga4 += (float)(Nigga2 > 0.0 ? 45 : -45);
                }
                Nigga3 = 0.0;
                if (Nigga2 > 0.0) {
                    Nigga2 = 1.0;
                } else if (Nigga2 < 0.0) {
                    Nigga2 = -1.0;
                }
            }
            MoveUtil.mc.thePlayer.motionX = Nigga2 * Nigga * Math.cos(Math.toRadians(Nigga4 + Float.intBitsToFloat(1.02192621E9f ^ 0x7E5D5B4B))) + Nigga3 * Nigga * Math.sin(Math.toRadians(Nigga4 + Float.intBitsToFloat(1.03689254E9f ^ 0x7F79B96B)));
            MoveUtil.mc.thePlayer.motionZ = Nigga2 * Nigga * Math.sin(Math.toRadians(Nigga4 + Float.intBitsToFloat(1.01282579E9f ^ 0x7EEA7EBD))) - Nigga3 * Nigga * Math.cos(Math.toRadians(Nigga4 + Float.intBitsToFloat(1.01501338E9f ^ 0x7ECBE013)));
        }
    }

    public static void setSpeed(float Nigga) {
        boolean Nigga2;
        double Nigga3 = Minecraft.getMinecraft().thePlayer.rotationYaw;
        boolean Nigga4 = Minecraft.getMinecraft().thePlayer.moveForward != Float.intBitsToFloat(2.13396749E9f ^ 0x7F31C29F) || Minecraft.getMinecraft().thePlayer.moveStrafing != Float.intBitsToFloat(2.13056026E9f ^ 0x7EFDC4CF);
        boolean Nigga5 = Minecraft.getMinecraft().thePlayer.moveForward > Float.intBitsToFloat(2.125584E9f ^ 0x7EB1D66D);
        boolean Nigga6 = Minecraft.getMinecraft().thePlayer.moveForward < Float.intBitsToFloat(2.13531789E9f ^ 0x7F465D4E);
        boolean Nigga7 = Minecraft.getMinecraft().thePlayer.moveStrafing > Float.intBitsToFloat(2.11382451E9f ^ 0x7DFE66FF);
        boolean Nigga8 = Minecraft.getMinecraft().thePlayer.moveStrafing < Float.intBitsToFloat(2.13897037E9f ^ 0x7F7E1908);
        boolean Nigga9 = Nigga8 || Nigga7;
        boolean bl = Nigga2 = Nigga5 || Nigga6;
        if (Nigga4) {
            if (ModuleManager.killaura.isEnabled() && ModuleManager.killaura.rotations.getMode().contains(Qprot0.0("\ue8f7\u71ca\ud3a5\u6494\u85b8\u6d6d\u8c6f\u84bd\u9461\u2ee5\ue755\uaf0a\u6a50")) && ModuleManager.killaura.targeting != null) {
                Nigga3 = ModuleManager.killaura.getRotations(ModuleManager.killaura.targeting)[0];
            }
            if (Nigga5 && !Nigga9) {
                Nigga3 += 0.0;
            } else if (Nigga6 && !Nigga9) {
                Nigga3 += 180.0;
            } else if (Nigga5 && Nigga8) {
                Nigga3 += 45.0;
            } else if (Nigga5) {
                Nigga3 -= 45.0;
            } else if (!Nigga2 && Nigga8) {
                Nigga3 += 90.0;
            } else if (!Nigga2 && Nigga7) {
                Nigga3 -= 90.0;
            } else if (Nigga6 && Nigga8) {
                Nigga3 += 135.0;
            } else if (Nigga6) {
                Nigga3 -= 135.0;
            }
            Nigga3 = Math.toRadians(Nigga3);
            Minecraft.getMinecraft().thePlayer.motionX = -Math.sin(Nigga3) * (double)Nigga;
            Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(Nigga3) * (double)Nigga;
        }
    }
}

