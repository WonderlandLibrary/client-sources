/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.Client;
import ru.govno.client.friendsystem.Friend;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.AntiBot;
import ru.govno.client.module.modules.Blink;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class SexAura
extends Module {
    public static Module get;
    boolean processMove = false;
    boolean sex;

    public SexAura() {
        super("SexAura", 0, Module.Category.COMBAT);
    }

    private static EntityPlayer getMe() {
        return Minecraft.player;
    }

    private static boolean entityIsCurrentToFilter(EntityLivingBase entity, double range) {
        for (Friend friend : Client.friendManager.getFriends()) {
            if (entity == null || !entity.getName().equalsIgnoreCase(friend.getName())) continue;
            return false;
        }
        return entity != null && entity.getHealth() != 0.0f && !Blink.isFakeEntity(entity) && !(entity instanceof EntityPlayerSP) && !(entity instanceof EntityArmorStand) && SexAura.getMe().canEntityBeSeen(entity) && (entity instanceof EntityPlayer && !((EntityPlayer)entity).isCreative() || !(entity instanceof EntityPlayer)) && !AntiBot.entityIsBotAdded(entity) && SexAura.getMe().getSmartDistanceToAABB(RotationUtil.getLookRots(SexAura.getMe(), entity), entity) <= range;
    }

    public static final EntityLivingBase getCurrentTarget(float range) {
        EntityLivingBase base = null;
        for (Entity o : SexAura.mc.world.getLoadedEntityList()) {
            EntityLivingBase living;
            if (!(o instanceof EntityLivingBase) || !SexAura.entityIsCurrentToFilter(living = (EntityLivingBase)o, range) || living.getHealth() == 0.0f || !(SexAura.getMe().getDistanceToEntity(living) <= range)) continue;
            range = SexAura.getMe().getDistanceToEntity(living);
            base = living;
        }
        return base;
    }

    double getSpeedToFollowCoord(Vec3d pos) {
        double speed = MathUtils.clamp(MoveMeHelp.getSpeed() * 1.1, 0.0, 0.2499) - (Minecraft.player.ticksExisted % 2 == 0 ? 0.003 : 0.0);
        return MathUtils.clamp(speed, 0.0, 1.0 - Minecraft.player.getDistanceXZ(pos.xCoord, pos.zCoord));
    }

    Vec3d getVecButtomAtTarget(EntityLivingBase target, float setedRange) {
        return target.getPositionVector().addVector(Math.sin(Math.toRadians(target.renderYawOffset)) * (double)setedRange, 0.0, -Math.cos(Math.toRadians(target.renderYawOffset)) * (double)setedRange);
    }

    /*
     * Unable to fully structure code
     */
    boolean movedToProcess(Vec3d vec, float checkR, EntityLivingBase target) {
        block14: {
            block9: {
                block13: {
                    block12: {
                        block11: {
                            block10: {
                                if (!(Minecraft.player.getDistanceXZ(vec.xCoord, vec.zCoord) <= (double)checkR)) ** GOTO lbl-1000
                                if (MathUtils.getDifferenceOf(Minecraft.player.posY, vec.yCoord) < 1.0) {
                                    v0 = true;
                                } else lbl-1000:
                                // 2 sources

                                {
                                    v0 = false;
                                }
                                moved = v0;
                                rot = RotationUtil.getMatrixRots(target)[0];
                                if (moved) break block9;
                                if (MathUtils.getDifferenceOf(rot, Minecraft.player.rotationYaw) > 6.0) {
                                    Minecraft.player.rotationYaw = rot;
                                }
                                SexAura.mc.gameSettings.keyBindForward.pressed = true;
                                SexAura.mc.gameSettings.keyBindBack.pressed = false;
                                SexAura.mc.gameSettings;
                                GameSettings.keyBindRight.pressed = false;
                                SexAura.mc.gameSettings.keyBindBack.pressed = false;
                                speed = this.getSpeedToFollowCoord(vec);
                                addSelfYaw = Minecraft.player.getPositionVector().addVector(-Math.sin(Math.toRadians(Minecraft.player.rotationYaw)) * 0.8, 0.0, Math.cos(Math.toRadians(Minecraft.player.rotationYaw)) * 0.8);
                                if (!Minecraft.player.onGround) break block10;
                                if (Minecraft.player.getDistanceXZ(vec.xCoord, vec.zCoord) > 4.0) break block11;
                            }
                            if (!Speed.posBlock(addSelfYaw.xCoord, addSelfYaw.yCoord + 0.3, addSelfYaw.zCoord) || Speed.posBlock(addSelfYaw.xCoord, addSelfYaw.yCoord + 1.3, addSelfYaw.zCoord)) break block12;
                        }
                        v1 = SexAura.mc.gameSettings.keyBindJump;
                        if (!Minecraft.player.onGround) ** GOTO lbl-1000
                        if (Minecraft.player.getDistanceXZ(vec.xCoord, vec.zCoord) > 1.7) {
                            v2 = true;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v2 = false;
                        }
                        v1.pressed = v2;
                        break block13;
                    }
                    SexAura.mc.gameSettings.keyBindJump.pressed = false;
                }
                this.processMove = true;
                break block14;
            }
            if (this.processMove) {
                if (!Minecraft.player.isJumping()) {
                    SexAura.mc.gameSettings.keyBindJump.pressed = false;
                }
                SexAura.mc.gameSettings.keyBindForward.pressed = false;
                if (Minecraft.player.getDistanceToVec3d(vec) < 0.2) {
                    Minecraft.player.setPosition(vec.xCoord, Minecraft.player.posY, vec.zCoord);
                    Minecraft.player.multiplyMotionXZ(0.0f);
                }
                this.processMove = false;
            }
        }
        if (MathUtils.getDifferenceOf(rot, Minecraft.player.rotationYaw) > 6.0) {
            Minecraft.player.rotationYaw = rot;
        }
        return moved;
    }

    void doSex(boolean DO) {
        if (DO) {
            boolean bl = SexAura.mc.gameSettings.keyBindSneak.pressed = Minecraft.player.ticksExisted % 2 == 0;
            if (!this.sex) {
                this.sex = true;
            }
        } else if (this.sex) {
            SexAura.mc.gameSettings.keyBindSneak.pressed = false;
            this.sex = false;
        }
    }

    @Override
    public void onUpdate() {
        EntityLivingBase target = SexAura.getCurrentTarget(25.0f);
        if (target != null) {
            this.doSex(this.movedToProcess(this.getVecButtomAtTarget(target, 0.3f), MathUtils.clamp(target.width / 2.0f, 0.1f, 2.0f), target));
        }
    }

    @Override
    public void onToggled(boolean actived) {
        this.doSex(false);
        super.onToggled(this.processMove);
    }
}

