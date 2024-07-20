/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.Fly;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class TPInfluence
extends Module {
    public static TPInfluence get;

    public TPInfluence() {
        super("TPInfluence", 0, Module.Category.COMBAT);
        get = this;
        this.settings.add(new Settings("UseOnHitAura", false, (Module)this));
        this.settings.add(new Settings("HitAuraRule", "Always", this, new String[]{"Always", "Fly", "Fly&SelfTicks", "ElytraFlying", "ElyBoost", "ElyBoost&Stand"}, () -> this.currentBooleanValue("UseOnHitAura")));
        this.settings.add(new Settings("SelfTicksAlive", 400.0f, 1000.0f, 100.0f, this, () -> this.currentBooleanValue("UseOnHitAura") && this.currentMode("HitAuraRule").equalsIgnoreCase("Fly&SelfTicks")));
        this.settings.add(new Settings("UseOnCrystalField", false, (Module)this));
        this.settings.add(new Settings("TeleportAction", "StepVH", this, new String[]{"StepVH", "StepV", "StepH", "StepHG", "VanillaVH", "VanillaH"}, () -> this.currentBooleanValue("UseOnHitAura") || this.currentBooleanValue("UseOnCrystalField")));
        this.settings.add(new Settings("MaxRange", 60.0f, 200.0f, 10.0f, this, () -> (this.currentBooleanValue("UseOnHitAura") || this.currentBooleanValue("UseOnCrystalField")) && this.currentMode("TeleportAction").contains("Step")));
    }

    private double sqrtAt(double val1) {
        return Math.sqrt(val1 * val1);
    }

    private double sqrtAt(double val1, double val2) {
        return Math.sqrt(val1 * val1 + val2 * val2);
    }

    private double sqrtAt(double val1, double val2, double val3) {
        return Math.sqrt(val1 * val1 + val2 * val2 + val3 * val3);
    }

    private double positive(double val) {
        return val < 0.0 ? -val : val;
    }

    public boolean defaultRule() {
        return (!FreeCam.get.isActived() || FreeCam.fakePlayer == null) && Minecraft.player != null;
    }

    private void send(double x, double y, double z, boolean ground) {
        mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, ground));
    }

    private void send(double x, double y, double z) {
        this.send(x, y, z, false);
    }

    private void send(boolean ground) {
        mc.getConnection().sendPacket(new CPacketPlayer(ground));
    }

    private void send() {
        mc.getConnection().sendPacket(new CPacketPlayer());
    }

    private Vec3d axisEntityPoint(Entity entityOf) {
        AxisAlignedBB bb = entityOf.getEntityBoundingBox();
        return bb != null ? new Vec3d(bb.minX + (bb.maxX - bb.minX) / 2.0, bb.minY, bb.minZ + (bb.maxZ - bb.minZ) / 2.0) : entityOf.getPositionVector();
    }

    public void teleportActionOfActionType(boolean pre, Vec3d to, String actionType) {
        int grInt;
        Vec3d self = Minecraft.player.getPositionVector();
        double dx = this.positive(self.xCoord - to.xCoord);
        double dy = this.positive(self.yCoord - to.yCoord);
        double dz = this.positive(self.zCoord - to.zCoord);
        int n = grInt = Minecraft.player.onGround ? 1 : 0;
        if (pre) {
            switch (actionType) {
                case "StepVH": {
                    double diffs = this.sqrtAt(dx, dy, dz);
                    for (int packetCount = (int)(diffs / 9.64) + 1; packetCount > 0; --packetCount) {
                        this.send(false);
                    }
                    if (grInt == 1) {
                        this.send(to.xCoord, to.yCoord + 0.08, to.zCoord);
                    }
                    this.send(to.xCoord, to.yCoord + 0.01, to.zCoord);
                    break;
                }
                case "StepV": {
                    double diffs = this.positive(dy);
                    int packetCount = 1 + (int)(diffs / 9.73);
                    if (grInt == 1) {
                        this.send(to.xCoord, self.yCoord + 0.08, to.zCoord);
                    }
                    while (packetCount > 0) {
                        this.send(false);
                        --packetCount;
                    }
                    this.send(self.xCoord, to.yCoord + 0.01, self.zCoord);
                    break;
                }
                case "StepH": {
                    double diffs = this.sqrtAt(dx, dz);
                    for (int packetCount = (int)(diffs / 8.953) + grInt; packetCount > 0; --packetCount) {
                        this.send(false);
                    }
                    this.send(to.xCoord, self.yCoord, to.zCoord);
                    break;
                }
                case "StepHG": {
                    double diffs = this.sqrtAt(dx, dz);
                    for (int packetCount = (int)(diffs / 8.317) + grInt; packetCount > 0; --packetCount) {
                        this.send(false);
                    }
                    this.send(to.xCoord, self.yCoord - (double)grInt * 1.0E-4, to.zCoord);
                    if (grInt != 0) break;
                    Minecraft.player.setPosY(self.yCoord);
                    break;
                }
                case "VanillaVH": {
                    this.send(false);
                    this.send(to.xCoord, to.yCoord, to.zCoord);
                }
                case "VanillaH": {
                    this.send(false);
                    this.send(to.xCoord, self.yCoord, to.zCoord);
                }
            }
            return;
        }
        switch (actionType) {
            case "StepVH": {
                this.send(self.xCoord, self.yCoord + 0.15, self.zCoord);
                this.send(self.xCoord, self.yCoord, self.zCoord);
                break;
            }
            case "StepV": {
                this.send(self.xCoord, self.yCoord, self.zCoord);
                this.send(self.xCoord, self.yCoord + (grInt == 1 ? 0.1 : -1.0E-13), self.zCoord);
                break;
            }
            case "StepH": {
                this.send(self.xCoord, self.yCoord, self.zCoord);
                break;
            }
            case "StepHG": {
                this.send(self.xCoord, self.yCoord - this.positive(grInt - 1) * 1.0E-4 * 2.0, self.zCoord);
                break;
            }
            case "VanillaVH": {
                this.send(self.xCoord, self.yCoord + 0.0016, self.zCoord);
                break;
            }
            case "VanillaH": {
                this.send(self.xCoord, self.yCoord, self.zCoord);
            }
        }
    }

    public boolean vectorRule(Vec3d to, double defaultDistanceMax, double distanceMin) {
        String action = this.currentMode("TeleportAction");
        Vec3d self = Minecraft.player.getPositionVector();
        double range = action.contains("Step") ? (double)this.currentFloatValue("MaxRange") : (action.contains("Vanilla") ? 9.23 : defaultDistanceMax);
        double dx = self.xCoord - to.xCoord;
        double dy = self.yCoord - to.yCoord;
        double dz = self.zCoord - to.zCoord;
        boolean isInRange = false;
        if (this.sqrtAt(dx, dy, dz) < distanceMin) {
            return false;
        }
        switch (this.currentMode("TeleportAction")) {
            case "StepVH": {
                isInRange = this.sqrtAt(dx, dy, dz) < range;
                break;
            }
            case "StepV": {
                isInRange = this.sqrtAt(dx, dz) < defaultDistanceMax && this.positive(dy) + this.sqrtAt(dx, dz) < range;
                break;
            }
            case "StepH": {
                isInRange = this.positive(dy) < defaultDistanceMax - 1.0 && this.sqrtAt(dx, dz) + this.positive(dy) < range;
                break;
            }
            case "StepHG": {
                isInRange = this.positive(dy) < defaultDistanceMax - 1.0 && this.sqrtAt(dx, dz) + this.positive(dy) < range;
                break;
            }
            case "VanillaVH": {
                isInRange = this.sqrtAt(dx, dy, dz) < range;
                break;
            }
            case "VanillaH": {
                isInRange = this.positive(dy) < defaultDistanceMax - 1.0 && this.sqrtAt(dx, dz) < range;
            }
        }
        return isInRange;
    }

    public boolean forHitAuraRule(EntityLivingBase target) {
        boolean sata;
        if (target == null || !target.isEntityAlive()) {
            return false;
        }
        boolean bl = sata = this.defaultRule() && this.currentBooleanValue("UseOnHitAura");
        if (sata) {
            String rule;
            switch (rule = this.currentMode("HitAuraRule")) {
                case "Always": {
                    sata = true;
                    break;
                }
                case "Fly": {
                    sata = Fly.get.isActived();
                    break;
                }
                case "Fly&SelfTicks": {
                    sata = Fly.get.isActived() && (float)Minecraft.player.ticksExisted < this.currentFloatValue("SelfTicksAlive");
                    break;
                }
                case "ElytraFlying": {
                    sata = Minecraft.player.isElytraFlying();
                }
                case "ElyBoost": {
                    sata = ElytraBoost.get.isActived() && ElytraBoost.canElytra();
                    break;
                }
                case "ElyBoost&Stand": {
                    sata = ElytraBoost.get.isActived() && ElytraBoost.canElytra() && MoveMeHelp.getSpeed() < 0.05 && !MoveMeHelp.moveKeysPressed() && this.positive(Minecraft.player.motionY) < 0.24;
                }
            }
        }
        return this.isActived() && sata && this.vectorRule(target.getPositionVector(), MathUtils.clamp(0.0, (double)HitAura.get.getAuraRange(HitAura.TARGET_ROTS) - 0.1, 5.8), 5.0);
    }

    public void hitAuraTPPre(EntityLivingBase target) {
        this.teleportActionOfActionType(true, target.getPositionVector(), this.currentMode("TeleportAction"));
    }

    public void hitAuraTPPost(EntityLivingBase target) {
        this.teleportActionOfActionType(false, target.getPositionVector(), this.currentMode("TeleportAction"));
    }
}

