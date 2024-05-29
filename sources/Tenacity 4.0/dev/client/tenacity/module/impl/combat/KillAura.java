package dev.client.tenacity.module.impl.combat;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.impl.movement.Scaffold;
import dev.client.tenacity.module.impl.movement.Speed;
import dev.client.tenacity.utils.player.RotationUtils;
import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;
import dev.event.impl.player.MotionEvent;
import dev.settings.ParentAttribute;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.MultipleBoolSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.misc.MathUtils;
import dev.utils.network.PacketUtils;
import dev.utils.time.TimerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unused")
public final class KillAura extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Single", "Single", "Switch");
    private final NumberSetting switchDelay = new NumberSetting("Switch Delay", 100, 1000, 100, 1);
    private final NumberSetting reach = new NumberSetting("Reach", 4, 6, 1, 0.1);
    private final NumberSetting mincps = new NumberSetting("Min CPS", 12, 20, 1, 1);
    private final NumberSetting maxcps = new NumberSetting("Max CPS", 12, 20, 1, 1);
    private final ModeSetting autoBlockMode = new ModeSetting("AutoBlock Mode", "Watchdog", "Watchdog", "Interaction", "AAC");
    private final BooleanSetting autoblock = new BooleanSetting("Autoblock", true);
    private final MultipleBoolSetting targetsSetting = new MultipleBoolSetting("Targets",
            new BooleanSetting("Players", true),
            new BooleanSetting("Mobs", false),
            new BooleanSetting("Animals", false),
            new BooleanSetting("Invisibles", false));
    private final MultipleBoolSetting rotationsSetting = new MultipleBoolSetting("Rotations",
            new BooleanSetting("Dynamic", false),
            new BooleanSetting("Prediction", false),
            new BooleanSetting("Resolver", false),
            new BooleanSetting("Smooth", false));

    private final MultipleBoolSetting antiBotSettings = new MultipleBoolSetting("Antibot",
            new BooleanSetting("Ticks", true),
            new BooleanSetting("Invisible", false),
            new BooleanSetting("NameTags", false),
            new BooleanSetting("Packet", false));

    private final BooleanSetting matrix = new BooleanSetting("Matrix", false);
    private final BooleanSetting debug = new BooleanSetting("Debug", false);
    public List<EntityLivingBase> targets = new ArrayList<>();
    public static EntityLivingBase target;
    public static boolean blocking;
    public static boolean attacking;
    public TimerUtil timer = new TimerUtil(), swtichTimer = new TimerUtil();

    public KillAura() {
        super("KillAura", Category.COMBAT, "Automatically attacks players");
        this.addSettings(mode, switchDelay, reach, mincps, maxcps, autoBlockMode, autoblock, targetsSetting, rotationsSetting, antiBotSettings, matrix, debug);
        switchDelay.addParent(mode, mode -> mode.is("Switch"));
        autoBlockMode.addParent(autoblock, ParentAttribute.BOOLEAN_CONDITION);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        sortTargets();
        this.setSuffix(mode.getMode());
        if (Tenacity.INSTANCE.isToggled(Scaffold.class) || mc.thePlayer.isDead || mc.thePlayer.isSpectator())
            return;
        if (!targets.isEmpty()) {
            if (mode.is("Switch") && swtichTimer.hasTimeElapsed(switchDelay.getValue().longValue())) {

                swtichTimer.reset();
            }
            if (e.isPre()) {
                target = targets.get(0);
                float[] rotations = getRotationsToEnt(target);
                if (rotationsSetting.getSetting("Dynamic").isEnabled()) {
                    rotations[0] += MathUtils.getRandomInRange(1, 5);
                    rotations[1] += MathUtils.getRandomInRange(1, 5);
                }
                if (rotationsSetting.getSetting("Prediction").isEnabled()) {
                    rotations[0] = (float) (rotations[0] + ((Math.abs(target.posX - target.lastTickPosX) - Math.abs(target.posZ - target.lastTickPosZ)) * (2 / 3)) * 2);
                    rotations[1] = (float) (rotations[1] + ((Math.abs(target.posY - target.lastTickPosY) - Math.abs(target.getEntityBoundingBox().minY - target.lastTickPosY)) * (2 / 3)) * 2);
                }

                if (rotationsSetting.getSetting("Resolver").isEnabled()) {
                    if (target.posY < 0) {
                        rotations[1] = 1;
                    } else if (target.posY > 255) {
                        rotations[1] = 90;
                    }

                    if (Math.abs(target.posX - target.lastTickPosX) > 0.50 || Math.abs(target.posZ - target.lastTickPosZ) > 0.50) {
                        target.setEntityBoundingBox(new AxisAlignedBB(target.posX, target.posY, target.posZ, target.lastTickPosX, target.lastTickPosY, target.lastTickPosZ));
                        if (debug.isEnabled()) {
                            mc.thePlayer.addChatComponentMessage(new ChatComponentText("Tenacity: resloved target hitbox at " + target.posX + "," + target.posY + "," + target.posZ));
                        }
                    }
                }

                if (rotationsSetting.getSetting("Smooth").isEnabled()) {
                    float sens = RotationUtils.getSensitivityMultiplier();

                    rotations[0] = RotationUtils.smoothRotation(mc.thePlayer.rotationYaw, rotations[0], 360);
                    rotations[1] = RotationUtils.smoothRotation(mc.thePlayer.rotationPitch, rotations[1], 90);

                    rotations[0] = Math.round(rotations[0] / sens) * sens;
                    rotations[1] = Math.round(rotations[1] / sens) * sens;

                }

                if (matrix.isEnabled()) {
                    rotations[0] = rotations[0] + MathUtils.getRandomFloat(1.98f, -1.98f);
                }
                e.setYaw(rotations[0]);
                e.setPitch(rotations[1]);
                RotationUtils.setRotations(rotations);
            }

            if (autoblock.isEnabled() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                mc.playerController.syncCurrentPlayItem();
                blocking = true;
                switch (autoBlockMode.getMode()) {
                    case "Watchdog":
                        if (e.isPost()) {
                            if (mc.thePlayer.swingProgressInt == -1) {
                                PacketUtils.sendPacket(new C07PacketPlayerDigging(
                                        C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
                            } else if (mc.thePlayer.swingProgressInt == 0) {
                                PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(
                                        new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                            }
                        }
                        break;
                    case "Interaction":
                        if (e.isPost()) {
                            if (blocking) {
                                for (Entity current : targets) {
                                    mc.playerController.interactWithEntitySendPacket(mc.thePlayer, current);
                                }
                                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                            } else {
                                PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(
                                        new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                                blocking = false;
                            }
                        }
                        break;
                    case "AAC":
                        if (e.isPost()) {
                            if (blocking) {
                                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                            } else {
                                PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(
                                        new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                                blocking = false;
                            }
                        }
                        break;
                }
            }
            if (e.isPre()) {
                attacking = true;
                if (timer.hasTimeElapsed((1000 / MathUtils.getRandomInRange(mincps.getValue().intValue(), maxcps.getValue().intValue())), true)) {
                    mc.thePlayer.swingItem();
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                }
            }
        }
        if (targets.isEmpty()) {
            attacking = false;
            blocking = false;
        }
    };


    private final EventListener<PacketReceiveEvent> onPacketReceive = e -> {
        if (antiBotSettings.getSetting("Packet").isEnabled() && target != null) {
            if (e.getPacket() instanceof S18PacketEntityTeleport) {
                S18PacketEntityTeleport s18PacketEntityTeleport = (S18PacketEntityTeleport) e.getPacket();

                if (target.getEntityId() == s18PacketEntityTeleport.getEntityId()) {
                    if (s18PacketEntityTeleport.getX() == mc.thePlayer.posX && s18PacketEntityTeleport.getY() == mc.thePlayer.posY && s18PacketEntityTeleport.getZ() == mc.thePlayer.posZ) {
                        targets.remove(target);
                    }
                }
            }
        }
    };

    @Override
    public void onDisable() {
        targets.clear();
        blocking = false;
        attacking = false;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void sortTargets() {
        targets.clear();
        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entLiving = (EntityLivingBase) entity;
                if (mc.thePlayer.getDistanceToEntity(entLiving) < reach.getValue() && entLiving != mc.thePlayer && !entLiving.isDead && isValid(entLiving)) {
                    targets.add(entLiving);
                }
            }
        }
        targets.sort(Comparator.comparingDouble(mc.thePlayer::getDistanceToEntity));
    }

    public boolean isValid(EntityLivingBase entLiving) {
        if (entLiving instanceof EntityPlayer && targetsSetting.getSetting("Players").isEnabled() && !entLiving.isInvisible()) {
            return true;
        }
        if (entLiving instanceof EntityPlayer && targetsSetting.getSetting("Invisibles").isEnabled() && entLiving.isInvisible()) {
            return true;
        }
        if (entLiving instanceof EntityMob && targetsSetting.getSetting("Mobs").isEnabled()) {
            return true;
        }
        if (antiBotSettings.getSetting("Ticks").isEnabled() && entLiving.ticksExisted < 100) {
            return false;
        }
        if (entLiving.getDisplayName() != null && entLiving instanceof EntityPlayer) {
            if (antiBotSettings.getSetting("NameTags").isEnabled() && (entLiving.getDisplayName().getFormattedText().contains("§c") || entLiving.getDisplayName().getFormattedText().contains("§4"))) {
                return false;
            }
        }

        if (!entLiving.canEntityBeSeen(mc.thePlayer) && antiBotSettings.getSetting("Invisible").isEnabled()) {
            return false;
        }
        return entLiving instanceof EntityAnimal && targetsSetting.getSetting("Animals").isEnabled();
    }

    private float[] getRotationsToEnt(Entity ent) {
        final double differenceX = ent.posX - mc.thePlayer.posX;
        final double differenceY = (ent.posY + ent.height) - (mc.thePlayer.posY + mc.thePlayer.height) - 0.5;
        final double differenceZ = ent.posZ - mc.thePlayer.posZ;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY, mc.thePlayer.getDistanceToEntity(ent)) * 180.0D
                / Math.PI);
        final float finishedYaw = mc.thePlayer.rotationYaw
                + MathHelper.wrapAngleTo180_float(rotationYaw - mc.thePlayer.rotationYaw);
        final float finishedPitch = mc.thePlayer.rotationPitch
                + MathHelper.wrapAngleTo180_float(rotationPitch - mc.thePlayer.rotationPitch);
        return new float[]{finishedYaw, -MathHelper.clamp_float(finishedPitch, -90, 90)};
    }
}