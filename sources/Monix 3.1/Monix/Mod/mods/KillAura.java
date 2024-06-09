/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Event.EventTarget;
import Monix.Event.events.EventUpdate;
import Monix.Mod.Mod;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class KillAura
extends Mod {
    private int ticks = 0;
    public static float yaw;
    public static float pitch;
    public static Entity entity;
    public static double cps;
    public static double reach;

    static {
        cps = 18.0;
        reach = 4.0;
    }

    public KillAura() {
        super("KillAura", "KillAura", 19, Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        ++this.ticks;
        entity = this.closeEntity();
        if (entity != null && KillAura.mc.thePlayer.getDistanceToEntity(entity) <= (float)reach) {
            if (entity.isInvisible()) {
                return;
            }
            if (entity.isEntityAlive()) {
                if (KillAura.mc.thePlayer.getHeldItem() != null) {
                    KillAura.mc.thePlayer.setItemInUse(KillAura.mc.thePlayer.getHeldItem(), KillAura.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                }
                KillAura.LookAtEntity(entity);
                if ((double)this.ticks >= 20.0 / cps) {
                    KillAura.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                    KillAura.mc.thePlayer.swingItem();
                    this.ticks = 0;
                    this.setArrayListName("KillAura \u00a77[R:" + reach + ", APS: " + cps + "]");
                }
            }
        }
    }

    public Entity closeEntity() {
        Entity close = null;
        for (Object o : KillAura.mc.theWorld.loadedEntityList) {
            Entity e = (Entity)o;
            if (!(e instanceof EntityMob && e.isEntityAlive() && !e.isInvisible() || e instanceof EntityAnimal && e.isEntityAlive() && !e.isInvisible()) && (!(e instanceof EntityOtherPlayerMP) || !e.isEntityAlive() || e.isInvisible()) || close != null && KillAura.mc.thePlayer.getDistanceToEntity(e) >= KillAura.mc.thePlayer.getDistanceToEntity(close)) continue;
            close = e;
        }
        return close;
    }

    public static float[] getRotations(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.boundingBox.maxY - 4.0;
        return KillAura.getRotationFromPosition(x, z, y);
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight();
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(- Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

    public static void LookAtEntity(Entity entity) {
        float[] rotations = KillAura.getRotations(entity);
        yaw = rotations[0];
        pitch = rotations[1];
    }

    public static void look() {
        KillAura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, KillAura.mc.thePlayer.onGround));
    }

    public static void poslook() {
        KillAura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(KillAura.mc.thePlayer.posX, KillAura.mc.thePlayer.getEntityBoundingBox().minY, KillAura.mc.thePlayer.posZ, yaw, pitch, KillAura.mc.thePlayer.onGround));
    }
}

