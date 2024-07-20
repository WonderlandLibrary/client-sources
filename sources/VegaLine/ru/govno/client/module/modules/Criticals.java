/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.TimerHelper;

public class Criticals
extends Module {
    public static Criticals get;
    private static boolean snapped;
    private static boolean doAddPacket;
    private static boolean groundS;
    static TimerHelper timeCancel;
    static float yawS;
    static float pitchS;

    public Criticals() {
        super("Criticals", 0, Module.Category.COMBAT);
        this.settings.add(new Settings("Mode", "VanillaHop", (Module)this, new String[]{"VanillaHop", "Matrix", "Matrix2", "NCP", "MatrixElytra", "MatrixStand"}));
        get = this;
    }

    @Override
    public void onUpdate() {
        boolean debug;
        if (doAddPacket && yawS != 9.0f && pitchS != 0.0f) {
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Rotation(yawS, pitchS, groundS));
            Client.msg("SENT", false);
            doAddPacket = false;
        }
        if ((debug = false) && Minecraft.player.isJumping()) {
            Client.msg(Minecraft.player.posY, false);
        }
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByMode(this.currentMode("Mode"));
    }

    public static void crits(Entity entity) {
        Criticals mod;
        EntityLivingBase base;
        if (entity != null && entity instanceof EntityLivingBase && (double)Minecraft.player.getDistanceToEntity(base = (EntityLivingBase)entity) <= 6.0 && Minecraft.player != null && Minecraft.player.onGround && !Minecraft.player.isInWater() && !Minecraft.player.isInWeb && !Minecraft.player.isJumping() && (mod = get) != null && mod.actived) {
            String mode = mod.currentMode("Mode");
            double x = Minecraft.player.posX;
            double y = Minecraft.player.posY;
            double z = Minecraft.player.posZ;
            switch (mode) {
                case "Matrix": {
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y + 1.0E-6, z, false));
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, false));
                    break;
                }
                case "Matrix2": {
                    if (!EntityLivingBase.isMatrixDamaged) break;
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y + 1.0E-6, z, false));
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, false));
                    break;
                }
                case "NCP": {
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y + (double)0.05f, z, false));
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, false));
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y + (double)0.012511f, z, false));
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, false));
                    break;
                }
                case "MatrixElytra": {
                    if (InventoryUtil.getElytra() == -1 || InventoryUtil.getItemInInv(Items.air) == -1) break;
                    ElytraBoost.eq();
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y + 0.0201, z, true));
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y + 0.0201, z, false));
                    ElytraBoost.badPacket();
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y + 0.02, z, false));
                    ElytraBoost.badPacket();
                    Minecraft.player.setFlag(7, false);
                    ElytraBoost.deq();
                    break;
                }
                case "MatrixStand": {
                    if (!Minecraft.player.onGround || MoveMeHelp.getSpeed() != 0.0 || Minecraft.player.isJumping() || MoveMeHelp.getSpeed() != 0.0) break;
                    if (Criticals.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.maxYToMinY(Minecraft.player.boundingBox.maxY - Minecraft.player.boundingBox.minY + 1.26)).isEmpty()) {
                        for (double offset : new double[]{0.42f, 0.7531999805212024, 1.0013359791121417, 1.1661092609382138, 1.252203340253729, 1.1767592750642422, 1.0244240882136921, 0.7967356006687112, 0.49520087700592796, 0.02}) {
                            mc.getConnection().sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + offset, Minecraft.player.posZ, false));
                        }
                        snapped = true;
                        timeCancel.reset();
                        break;
                    }
                    if (Criticals.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.maxYToMinY(2.499)).isEmpty()) {
                        for (double offset : new double[]{0.41999998688698, 0.70000004768372, 0.62160004615784, 0.46636804164123, 0.23584067272827, 0.02}) {
                            mc.getConnection().sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + offset, Minecraft.player.posZ, false));
                        }
                        snapped = true;
                        timeCancel.reset();
                        break;
                    }
                    if (Criticals.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.maxYToMinY(1.999)).isEmpty()) {
                        for (double offset : new double[]{0.20000004768372, 0.12160004615784, 0.02}) {
                            mc.getConnection().sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + offset, Minecraft.player.posZ, false));
                        }
                        snapped = true;
                        timeCancel.reset();
                        break;
                    }
                    if (!Criticals.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.maxYToMinY(Minecraft.player.boundingBox.maxY - Minecraft.player.boundingBox.minY + 0.01)).isEmpty()) break;
                    for (double offset : new double[]{0.01250004768372, 0.01}) {
                        mc.getConnection().sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + offset, Minecraft.player.posZ, false));
                    }
                    snapped = true;
                    timeCancel.reset();
                }
            }
        }
    }

    @EventTarget
    public void onPacketSend(EventSendPacket event) {
        CPacketPlayer packet;
        Packet packet2 = event.getPacket();
        if (packet2 instanceof CPacketPlayer && ((packet = (CPacketPlayer)packet2) instanceof CPacketPlayer.PositionRotation || packet instanceof CPacketPlayer.Rotation) && this.currentMode("Mode").equalsIgnoreCase("MatrixStand") && Minecraft.player != null && Minecraft.player.onGround && MoveMeHelp.getSpeed() == 0.0 && !Minecraft.player.isJumping() && MoveMeHelp.getSpeed() == 0.0) {
            boolean replace;
            boolean bl = replace = Criticals.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.maxYToMinY(Minecraft.player.boundingBox.maxY - Minecraft.player.boundingBox.minY + 1.26)).isEmpty() || Criticals.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.maxYToMinY(2.499)).isEmpty() || Criticals.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.maxYToMinY(1.999)).isEmpty() || Criticals.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.maxYToMinY(Minecraft.player.boundingBox.maxY - Minecraft.player.boundingBox.minY + 0.01)).isEmpty();
            if (replace) {
                if (packet instanceof CPacketPlayer.PositionRotation) {
                    CPacketPlayer.PositionRotation positionRotationPacket = (CPacketPlayer.PositionRotation)packet;
                    if (timeCancel.hasReached(900.0f)) {
                        return;
                    }
                    if (Minecraft.player.ticksExisted < 100) {
                        return;
                    }
                    if (positionRotationPacket.pitch > 87.0f) {
                        return;
                    }
                    if (MathUtils.getDifferenceOf(positionRotationPacket.yaw, Minecraft.player.lastReportedYaw) > 10.0 && HitAura.TARGET == null) {
                        return;
                    }
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(positionRotationPacket.x, positionRotationPacket.y, positionRotationPacket.z, positionRotationPacket.onGround));
                    event.cancel();
                }
                if (packet instanceof CPacketPlayer.Rotation) {
                    CPacketPlayer.Rotation rotationPacket = (CPacketPlayer.Rotation)packet;
                    if (timeCancel.hasReached(900.0f)) {
                        return;
                    }
                    if (Minecraft.player.ticksExisted < 100) {
                        return;
                    }
                    if (rotationPacket.pitch > 87.0f) {
                        return;
                    }
                    if (MathUtils.getDifferenceOf(rotationPacket.yaw, Minecraft.player.lastReportedYaw) > 10.0 && HitAura.TARGET == null) {
                        return;
                    }
                    yawS = rotationPacket.yaw;
                    pitchS = rotationPacket.pitch;
                    groundS = rotationPacket.onGround;
                    event.cancel();
                }
            }
        }
    }

    static {
        timeCancel = new TimerHelper();
    }
}

