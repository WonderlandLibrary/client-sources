/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.Event3D;
import ru.govno.client.event.events.EventAction;
import ru.govno.client.event.events.EventMove2;
import ru.govno.client.event.events.EventPostMove;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.Fly;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.JesusSpeed;
import ru.govno.client.module.modules.LongJump;
import ru.govno.client.module.modules.PearlFlight;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.modules.Strafe;
import ru.govno.client.module.modules.WaterSpeed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MatrixStrafeMovement;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.Render.RenderUtils;

public class TargetStrafe
extends Module {
    public static TargetStrafe get;
    public static int b;
    private static double speed;
    public static boolean needSprintState;
    public Settings Distance;
    public Settings SpeedF;
    public Settings SmartSpeed;
    public Settings DamageBoost;
    public Settings DamageSpeed;
    public Settings AutoJump;
    public Settings RenderCurrentDist;
    public Settings SharpMoveDir;
    static EntityLivingBase target;
    private float prevRot = 0.0f;

    public TargetStrafe() {
        super("TargetStrafe", 0, Module.Category.MOVEMENT);
        get = this;
        this.Distance = new Settings("Distance", 2.0f, 12.0f, 1.0f, this);
        this.settings.add(this.Distance);
        this.SpeedF = new Settings("Speed", 0.24f, 1.0f, 0.0f, this, () -> !this.SmartSpeed.bValue);
        this.settings.add(this.SpeedF);
        this.SharpMoveDir = new Settings("SharpMoveDir", true, (Module)this);
        this.settings.add(this.SharpMoveDir);
        this.SmartSpeed = new Settings("SmartSpeed", true, (Module)this);
        this.settings.add(this.SmartSpeed);
        this.DamageBoost = new Settings("DamageBoost", false, (Module)this);
        this.settings.add(this.DamageBoost);
        this.DamageSpeed = new Settings("DamageSpeed", 0.6f, 2.0f, 0.0f, this, () -> this.DamageBoost.bValue);
        this.settings.add(this.DamageSpeed);
        this.AutoJump = new Settings("AutoJump", true, (Module)this);
        this.settings.add(this.AutoJump);
        this.RenderCurrentDist = new Settings("RenderCurrentDist", true, (Module)this);
        this.settings.add(this.RenderCurrentDist);
    }

    float[] getRotations(Entity ent) {
        double x = RenderUtils.interpolate(ent.posX, ent.lastTickPosX, mc.getRenderPartialTicks());
        double y = RenderUtils.interpolate(ent.posY, ent.lastTickPosY, mc.getRenderPartialTicks()) * (double)ent.getEyeHeight();
        double z = RenderUtils.interpolate(ent.posZ, ent.lastTickPosZ, mc.getRenderPartialTicks());
        return this.getRotationFromPosition(x, z, y);
    }

    float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.player.posX;
        double zDiff = z - Minecraft.player.posZ;
        double yDiff = y - Minecraft.player.posY - 1.8;
        double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    private static float getMaxRange() {
        return 20.0f;
    }

    private static boolean isSmartKeep() {
        return true;
    }

    private static int keepPercent100() {
        return 45;
    }

    void Motion(double d, float f, double d2, double d3, boolean onMove, boolean smartKeep) {
        double d4 = d3;
        double d5 = d2;
        float keep = 90.0f - (float)TargetStrafe.keepPercent100() * 0.9f;
        float dst = Minecraft.player.getSmoothDistanceToEntityXZ(target);
        float cdst = this.Distance.fValue;
        float rng = TargetStrafe.getMaxRange();
        if (smartKeep) {
            float pcDel = (float)MoveMeHelp.getCuttingSpeed() / 0.65f;
            cdst = (float)((double)cdst - MoveMeHelp.getCuttingSpeed() / 10.0);
            keep = MathUtils.clamp(1.0f - (float)MathUtils.getDifferenceOf(dst, cdst), 0.0f, 1.0f) * 90.0f;
        }
        float f2 = f;
        if (d4 != 0.0 || d5 != 0.0) {
            if (d4 != 0.0) {
                if (d5 > 0.0) {
                    f2 += d4 > 0.0 ? -keep : keep;
                } else if (d5 < 0.0) {
                    f2 += d4 > 0.0 ? keep : -keep;
                }
                d5 = 0.0;
                if (d4 > 0.0) {
                    d4 = 1.0;
                } else if (d4 < 0.0) {
                    d4 = -1.0;
                }
            }
            double d6 = Math.cos(Math.toRadians(f2 + 93.5f));
            double d7 = Math.sin(Math.toRadians(f2 + 93.5f));
            if (onMove) {
                Entity.motionx = (d4 * d * d6 + d5 * d * d7) / 1.06;
                Entity.motionz = (d4 * d * d7 - d5 * d * d6) / 1.06;
            } else {
                Minecraft.player.motionX = d4 * d * d6 + d5 * d * d7;
                Minecraft.player.motionZ = d4 * d * d7 - d5 * d * d6;
            }
        }
    }

    void Motion2(EventMove2 move, double d, float f, double d2, double d3, boolean smartKeep) {
        double d4 = d3;
        double d5 = d2;
        float keep = 90.0f - (float)TargetStrafe.keepPercent100() * 0.9f;
        float dst = Minecraft.player.getSmoothDistanceToEntityXZ(target);
        float cdst = this.Distance.fValue;
        float rng = TargetStrafe.getMaxRange();
        if (smartKeep) {
            cdst = (float)((double)cdst - MoveMeHelp.getCuttingSpeed());
            double dstPardon = 1.0 + MoveMeHelp.getCuttingSpeed() / 9.953;
            keep = (float)MathUtils.clamp(dstPardon - MathUtils.getDifferenceOf(dst, cdst), 0.0, 1.0) * 90.0f;
        }
        float f2 = f;
        if (d4 == 0.0 && d5 == 0.0) {
            MatrixStrafeMovement.oldSpeed = 0.0;
            move.motion().xCoord = 0.0;
            move.motion().zCoord = 0.0;
        } else {
            if (d4 != 0.0) {
                if (d5 > 0.0) {
                    f2 += d4 > 0.0 ? -keep : keep;
                } else if (d5 < 0.0) {
                    f2 += d4 > 0.0 ? keep : -keep;
                }
                d5 = 0.0;
                if (d4 > 0.0) {
                    d4 = 1.0;
                } else if (d4 < 0.0) {
                    d4 = -1.0;
                }
            }
            double d6 = Math.cos(Math.toRadians(f2 + 90.0f));
            double d7 = Math.sin(Math.toRadians(f2 + 90.0f));
            move.motion().xCoord = d4 * d * d6 + d5 * d * d7;
            move.motion().zCoord = d4 * d * d7 - d5 * d * d6;
        }
    }

    static double getCurrentSpeed() {
        if (Minecraft.player == null) {
            return 0.0;
        }
        TargetStrafe targetStrafe = get;
        double speed1 = targetStrafe.SpeedF.fValue;
        if (Minecraft.player.hurtTime != 0 && targetStrafe.DamageBoost.bValue) {
            speed1 = targetStrafe.DamageSpeed.fValue;
        }
        if (targetStrafe.SmartSpeed.bValue) {
            boolean elytra;
            speed1 = speed;
            if (Speed.get.actived && (Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Guardian") && EntityLivingBase.isSunRiseDamaged || Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Matrix") && Speed.get.DamageBoost.bValue && EntityLivingBase.isMatrixDamaged)) {
                if (Minecraft.player.onGround) {
                    speed1 *= speed < 0.62 ? 1.64 : 1.53;
                } else if (Minecraft.player.isJumping() && Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Guardian") && (!targetStrafe.DamageSpeed.bValue || !EntityLivingBase.isMatrixDamaged)) {
                    speed1 *= Minecraft.player.fallDistance == 0.0f ? 1.001 : (Speed.canMatrixBoost() && !Minecraft.player.isHandActive() && (speed < 0.4 || (double)Minecraft.player.fallDistance > 0.65 && speed < 0.6) ? 1.9 : 1.0);
                }
                speed1 = MathUtils.clamp(speed, 0.2499 - (Minecraft.player.ticksExisted % 2 == 0 ? 5.0E-7 : 0.0), 1.17455998 - (Minecraft.player.ticksExisted % 2 == 0 ? 1.0E-7 : 0.0));
            }
            boolean bl = elytra = ElytraBoost.get.actived && (ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixSpeed2") || ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixFly3")) && ElytraBoost.canElytra();
            if (elytra) {
                if (ElytraBoost.flSpeed > speed) {
                    speed1 = ElytraBoost.flSpeed / 1.011;
                }
            } else if (Speed.get.actived && (Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Guardian") && EntityLivingBase.isSunRiseDamaged || Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Matrix") && Speed.get.DamageBoost.bValue && EntityLivingBase.isMatrixDamaged)) {
                if (Minecraft.player.onGround) {
                    speed1 *= speed1 < 0.62 ? 1.64 : 1.528;
                } else if (Minecraft.player.isJumping() && Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Guardian")) {
                    speed1 *= Minecraft.player.fallDistance == 0.0f ? 1.001 : (Speed.canMatrixBoost() && !Minecraft.player.isHandActive() && (speed1 < 0.4 || (double)Minecraft.player.fallDistance > 0.65 && speed1 < 0.6) ? 1.9 : 1.0);
                }
                speed1 = MathUtils.clamp(speed1, 0.2499 - (Minecraft.player.ticksExisted % 2 == 0 ? 5.0E-7 : 0.0), 1.3);
            }
            if (Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("NCP") && (double)Speed.ncpSpeed > speed1 && Speed.get.DamageBoost.bValue) {
                speed1 = Speed.ncpSpeed;
            }
            if (elytra && ElytraBoost.flSpeed > speed1) {
                speed1 = ElytraBoost.flSpeed;
            }
            if (Speed.iceGo) {
                speed1 = (float)(Minecraft.player.isPotionActive(Potion.getPotionById(1)) ? 0.91 : 0.63) * 1.07f;
            }
            if (WaterSpeed.get.actived && WaterSpeed.get.Mode.currentMode.equalsIgnoreCase("Matrix") && WaterSpeed.speedInWater / 1.061 > speed1) {
                speed1 = WaterSpeed.speedInWater / 1.061;
            }
            if (Minecraft.player.isElytraFlying() && (double)EntityLivingBase.getElytraSpeed > speed1) {
                speed1 = EntityLivingBase.getElytraSpeed;
            }
            if (Fly.get.actived && MathUtils.clamp(Fly.flySpeed, (double)0.195f, (double)1.2f) > speed1) {
                speed1 = MathUtils.clamp(Fly.flySpeed, (double)0.195f, (double)1.2f);
            }
        }
        if (Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Vulcan") && speed > 0.1 && Minecraft.player.onGround && Minecraft.player.ticksExisted % 3 == 0) {
            Minecraft.player.motionY = 0.0391;
        }
        if (JesusSpeed.get.actived && JesusSpeed.isJesused) {
            if ((double)Minecraft.player.fallDistance > 0.02) {
                Enchantment depth = Enchantments.DEPTH_STRIDER;
                int depthLvl = EnchantmentHelper.getEnchantmentLevel(depth, Minecraft.player.inventory.armorItemInSlot(0));
                boolean isSpeedPot = Minecraft.player.isPotionActive(MobEffects.SPEED) && Minecraft.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier() > 0;
                speed1 = 1.16f;
                if (isSpeedPot && depthLvl > 0) {
                    speed1 += 0.443;
                }
            } else {
                speed1 = 0.12;
            }
        }
        if (JesusSpeed.get.actived && JesusSpeed.get.JesusMode.currentMode.equalsIgnoreCase("Matrix7.0.2") && JesusSpeed.ticksLinked > 1) {
            speed1 = JesusSpeed.getM7Speed();
        }
        if (Minecraft.player.isInWeb) {
            speed1 /= 2.0;
        }
        return speed1;
    }

    void targetStrafeElement() {
        if (Minecraft.player.isInWater()) {
            TargetStrafe.mc.gameSettings.keyBindJump.pressed = true;
        } else if (TargetStrafe.mc.gameSettings.keyBindJump.pressed && !Keyboard.isKeyDown(TargetStrafe.mc.gameSettings.keyBindJump.getKeyCode())) {
            TargetStrafe.mc.gameSettings.keyBindJump.pressed = false;
        }
        Minecraft.player.jumpMovementFactor = 0.0f;
        if (Minecraft.player.onGround && !Minecraft.player.isInWater() && !Minecraft.player.isInLava() && !Minecraft.player.isInWeb && this.AutoJump.bValue && !Minecraft.player.isJumping()) {
            Minecraft.player.jump();
        }
        if (Minecraft.player.isCollidedHorizontally && Minecraft.player.ticksExisted % 2 == 0) {
            b = -b;
        }
        b = Keyboard.isKeyDown(GameSettings.keyBindLeft.getKeyCode()) ? 1 : (Keyboard.isKeyDown(GameSettings.keyBindRight.getKeyCode()) ? -1 : b);
    }

    public static final boolean goStrafe() {
        if (target == null || LongJump.get.actived || PearlFlight.go) {
            return false;
        }
        return TargetStrafe.get.actived;
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByDouble(TargetStrafe.getCurrentSpeed());
    }

    @Override
    public void onUpdate() {
        EntityLivingBase entityLivingBase = HitAura.TARGET_ROTS != null ? HitAura.TARGET_ROTS : (target = TargetStrafe.mc.world.getLoadedEntityList().stream().anyMatch(entity -> entity == target) ? target : null);
        if (target != null && (!HitAura.get.actived || target != null && target.getHealth() == 0.0f || Minecraft.player.getSmoothDistanceToEntity(target) > TargetStrafe.getMaxRange())) {
            target = null;
        }
        if (!TargetStrafe.goStrafe() && TargetStrafe.mc.currentScreen == null) {
            TargetStrafe.mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(TargetStrafe.mc.gameSettings.keyBindJump.getKeyCode());
        }
        if (TargetStrafe.goStrafe()) {
            this.targetStrafeElement();
        }
        if (target == null) {
            target = null;
        }
    }

    @Override
    public void onMovement() {
        if (TargetStrafe.goStrafe()) {
            this.targetStrafeElement();
            if (!this.SmartSpeed.bValue) {
                this.getStrafe(TargetStrafe.getCurrentSpeed(), Minecraft.player.getSmoothDistanceToEntityXZ(target), this.Distance.fValue, true, TargetStrafe.isSmartKeep());
            }
        }
    }

    @EventTarget
    public void onMovementHui(EventPostMove move) {
        if (this.SmartSpeed.bValue && this.actived) {
            MatrixStrafeMovement.postMove(move.getHorizontalMove());
        }
    }

    @EventTarget
    public void onMovementHui2(EventAction move) {
        if (TargetStrafe.goStrafe() && this.SmartSpeed.bValue && this.actived && !HitAura.get.noRotateTick) {
            MatrixStrafeMovement.actionEvent(move);
        }
    }

    @EventTarget
    public void onMovements(EventMove2 move) {
        boolean elytra;
        if (!this.actived) {
            return;
        }
        boolean canBoost = false;
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        if (!TargetStrafe.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.addCoord(0.0, -0.09, 0.0)).isEmpty()) {
            canBoost = true;
        }
        boolean matrixSpeedDamageHop = Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Matrix") && Speed.get.StrafeDamageHop.bValue && EntityLivingBase.isMatrixDamaged;
        speed = MatrixStrafeMovement.calculateSpeed(matrixSpeedDamageHop || Strafe.get.Mode.currentMode.equalsIgnoreCase("Strict"), move, matrixSpeedDamageHop || EntityLivingBase.isMatrixDamaged && this.DamageBoost.bValue && canBoost, matrixSpeedDamageHop ? 0.17 : (double)this.DamageSpeed.fValue);
        if (EntityLivingBase.isMatrixDamaged && canBoost) {
            speed = MathUtils.clamp(speed, speed, (double)2.9f);
        }
        boolean bl = elytra = ElytraBoost.get.actived && (ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixSpeed2") || ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixFly3")) && ElytraBoost.canElytra();
        if (elytra && ElytraBoost.flSpeed > speed) {
            speed = ElytraBoost.flSpeed;
        }
        if (Speed.iceGo) {
            speed = (float)(Minecraft.player.isPotionActive(Potion.getPotionById(1)) ? 0.91 : 0.63) * 1.07f;
        }
        if (WaterSpeed.get.actived && WaterSpeed.get.Mode.currentMode.equalsIgnoreCase("Matrix") && WaterSpeed.speedInWater / 1.06 > speed) {
            speed = WaterSpeed.speedInWater / 1.06;
        }
        if (Minecraft.player.isElytraFlying() && (double)EntityLivingBase.getElytraSpeed > speed) {
            speed = EntityLivingBase.getElytraSpeed;
        }
        if (Fly.get.actived && Fly.flySpeed > speed) {
            speed = Fly.flySpeed / 5.0;
        }
        if (!Minecraft.player.isHandActive() || Minecraft.player.isJumping() || Minecraft.player.onGround) {
            // empty if block
        }
        if (JesusSpeed.get.actived && JesusSpeed.get.JesusMode.currentMode.equalsIgnoreCase("Matrix7.0.2") && JesusSpeed.ticksLinked > 0) {
            speed = JesusSpeed.getM7Speed();
        }
        double finalSpeed = TargetStrafe.getCurrentSpeed();
        if (TargetStrafe.goStrafe() && this.SmartSpeed.bValue && this.actived) {
            float pardon;
            float current;
            float dst = Minecraft.player.getSmoothDistanceToEntityXZ(target);
            int feya = dst > (current = this.Distance.fValue) + (pardon = 0.05f) ? 1 : (dst < current - pardon ? -1 : 0);
            float yaw = this.getRotations(target)[0];
            if (!this.SharpMoveDir.bValue || MathUtils.getDifferenceOf(yaw, this.prevRot) >= 30.0) {
                this.prevRot = yaw;
            }
            this.Motion2(move, finalSpeed, this.prevRot, b, feya, TargetStrafe.isSmartKeep());
        }
    }

    void getStrafe(double speed, float getDist, float dist, boolean onMove, boolean smartKeep) {
        float pardon = 0.05f;
        int feya = getDist > dist + pardon ? 1 : (getDist < dist - pardon ? -1 : 0);
        float yaw = this.getRotations(target)[0];
        if (!this.SharpMoveDir.bValue || MathUtils.getDifferenceOf(yaw, this.prevRot) >= 30.0) {
            this.prevRot = yaw;
        }
        this.Motion(speed, this.prevRot, b, feya, onMove, smartKeep);
    }

    @EventTarget
    public void onRender3D(Event3D event) {
        if (target != null && Minecraft.player.getSmoothDistanceToEntityXZ(target) <= TargetStrafe.getMaxRange() && this.RenderCurrentDist.bValue) {
            int c;
            double z;
            double y;
            double x;
            double r;
            int i;
            float xzDistance = this.Distance.fValue;
            float sataDistance = Minecraft.player.getSmoothDistanceToEntityXZ(target) / 2.0f;
            double eX = TargetStrafe.target.lastTickPosX + (TargetStrafe.target.posX - TargetStrafe.target.lastTickPosX) * (double)event.getPartialTicks();
            double eY = TargetStrafe.target.lastTickPosY + (TargetStrafe.target.posY - TargetStrafe.target.lastTickPosY) * (double)event.getPartialTicks();
            double eZ = TargetStrafe.target.lastTickPosZ + (TargetStrafe.target.posZ - TargetStrafe.target.lastTickPosZ) * (double)event.getPartialTicks();
            double meX = Minecraft.player.lastTickPosX + (Minecraft.player.posX - Minecraft.player.lastTickPosX) * (double)event.getPartialTicks();
            double meY = Minecraft.player.lastTickPosY + (Minecraft.player.posY - Minecraft.player.lastTickPosY) * (double)event.getPartialTicks();
            double meZ = Minecraft.player.lastTickPosZ + (Minecraft.player.posZ - Minecraft.player.lastTickPosZ) * (double)event.getPartialTicks();
            Vec3d overSataVec = BlockUtils.getOverallVec3d(new Vec3d(eX, eY, eZ), new Vec3d(meX, meY, meZ), 0.5f);
            double sataX = overSataVec.xCoord;
            double sataY = overSataVec.yCoord;
            double sataZ = overSataVec.zCoord;
            double glX = RenderManager.viewerPosX;
            double glY = RenderManager.viewerPosY;
            double glZ = RenderManager.viewerPosZ;
            boolean bloom = false;
            GL11.glPushMatrix();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, bloom ? GlStateManager.DestFactor.ONE : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            TargetStrafe.mc.entityRenderer.disableLightmap();
            GL11.glEnable(3042);
            GL11.glEnable(2832);
            GL11.glLineWidth(1.0f);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDisable(2896);
            GL11.glShadeModel(7425);
            GL11.glTranslated(-glX, -glY, -glZ);
            GL11.glBegin(3);
            for (i = 0; i <= 360; i += 6) {
                r = sataDistance;
                x = sataX - Math.sin(Math.toRadians(i)) * r;
                y = sataY;
                z = sataZ + Math.cos(Math.toRadians(i)) * r;
                c = ClientColors.getColor1((int)((float)i * 3.0f));
                RenderUtils.glColor(c);
                GL11.glVertex3d(x, y, z);
            }
            GL11.glEnd();
            GL11.glBegin(3);
            for (i = 0; i <= 360; i += 6) {
                r = sataDistance * 2.0f;
                x = eX - Math.sin(Math.toRadians(i)) * r;
                y = sataY;
                z = eZ + Math.cos(Math.toRadians(i)) * r;
                c = ClientColors.getColor1((int)((float)i * 3.0f), 0.333333f);
                RenderUtils.glColor(c);
                GL11.glVertex3d(x, y, z);
            }
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glVertex3d(eX, sataY, eZ);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glVertex3d(eX - (eX - meX) / 2.0, sataY, eZ - (eZ - meZ) / 2.0);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glVertex3d(meX, sataY, meZ);
            GL11.glEnd();
            GL11.glPointSize(8.0f);
            GL11.glBegin(0);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glVertex3d(eX, sataY, eZ);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glVertex3d(meX, sataY, meZ);
            GL11.glEnd();
            GL11.glPointSize(1.0f);
            double yaw = Math.toDegrees(Math.atan2(Minecraft.player.posZ - Minecraft.player.prevPosZ, Minecraft.player.posX - Minecraft.player.prevPosX));
            double prevYaw = yaw = yaw < 0.0 ? yaw + 360.0 : yaw;
            yaw = prevYaw - (prevYaw - yaw) * (double)event.getPartialTicks();
            double dx = Minecraft.player.posX - Minecraft.player.prevPosX;
            double dz = Minecraft.player.posZ - Minecraft.player.prevPosZ;
            double antsRange = Math.sqrt(dx * dx + dz * dz);
            GL11.glBegin(3);
            GL11.glVertex3d(meX, sataY, meZ);
            GL11.glVertex3d(meX - Math.sin(Math.toRadians(yaw - 90.0)) * antsRange, sataY, meZ + Math.cos(Math.toRadians(yaw - 90.0)) * antsRange);
            GL11.glEnd();
            GL11.glPointSize(8.0f);
            GL11.glBegin(0);
            GL11.glVertex3d(meX - Math.sin(Math.toRadians(yaw - 90.0)) * antsRange, sataY, meZ + Math.cos(Math.toRadians(yaw - 90.0)) * antsRange);
            GL11.glEnd();
            yaw = Math.toDegrees(Math.atan2(TargetStrafe.target.posZ - Minecraft.player.prevPosZ, TargetStrafe.target.posX - TargetStrafe.target.prevPosX));
            prevYaw = yaw = yaw < 0.0 ? yaw + 360.0 : yaw;
            yaw = prevYaw - (prevYaw - yaw) * (double)event.getPartialTicks();
            dx = TargetStrafe.target.posX - TargetStrafe.target.prevPosX;
            dz = TargetStrafe.target.posZ - TargetStrafe.target.prevPosZ;
            antsRange = Math.sqrt(dx * dx + dz * dz);
            GL11.glBegin(3);
            GL11.glVertex3d(eX, sataY, eZ);
            GL11.glVertex3d(eX - Math.sin(Math.toRadians(yaw - 90.0)) * antsRange, sataY, eZ + Math.cos(Math.toRadians(yaw - 90.0)) * antsRange);
            GL11.glEnd();
            GL11.glPointSize(8.0f);
            GL11.glBegin(0);
            GL11.glVertex3d(eX - Math.sin(Math.toRadians(yaw - 90.0)) * antsRange, sataY, eZ + Math.cos(Math.toRadians(yaw - 90.0)) * antsRange);
            GL11.glEnd();
            GL11.glPointSize(1.0f);
            GL11.glTranslated(glX, glY, glZ);
            GL11.glLineWidth(1.0f);
            GL11.glShadeModel(7424);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.resetColor();
            GL11.glPopMatrix();
        }
    }

    static {
        b = 1;
        speed = 0.23f;
        target = null;
    }
}

