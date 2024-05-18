/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Event.events.EventUpdate;
import Monix.Mod.Mod;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class Criticals
extends Mod {
    private int ticks = 0;
    public static float yaw;
    public static float pitch;
    public static Entity entity;
    public static double cps;
    public static double reach;

    static {
        cps = 18.0;
        reach = 4.5;
    }

    public Criticals() {
        super("CritAura", "CritAura [Jump]", 45, Category.COMBAT);
    }

    public void onUpdate(EventUpdate event) {
        ++this.ticks;
        entity = this.closeEntity();
        if (entity != null && Criticals.mc.thePlayer.getDistanceToEntity(entity) <= (float)reach) {
            if (entity.isInvisible()) {
                return;
            }
            if (entity.isEntityAlive()) {
                if (Criticals.mc.thePlayer.getHeldItem() != null) {
                    Criticals.mc.thePlayer.setItemInUse(Criticals.mc.thePlayer.getHeldItem(), Criticals.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                }
                Criticals.LookAtEntity(entity);
                if ((double)this.ticks >= 20.0 / cps) {
                    Criticals.mc.thePlayer.jump();
                    Criticals.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                    Criticals.mc.thePlayer.swingItem();
                    this.ticks = 0;
                }
            }
        }
    }

    public Entity closeEntity() {
        Entity close = null;
        for (Object o : Criticals.mc.theWorld.loadedEntityList) {
            Entity e = (Entity)o;
            if (!(e instanceof EntityOtherPlayerMP) || !e.isEntityAlive() || e.isInvisible() || close != null && Criticals.mc.thePlayer.getDistanceToEntity(e) >= Criticals.mc.thePlayer.getDistanceToEntity(close)) continue;
            close = e;
        }
        return close;
    }

    public static float[] getRotations(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.boundingBox.maxY - 4.0;
        return Criticals.getRotationFromPosition(x, z, y);
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
        float[] rotations = Criticals.getRotations(entity);
        yaw = rotations[0];
        pitch = rotations[1];
    }

    public static void look() {
        Criticals.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, Criticals.mc.thePlayer.onGround));
    }

    public static void poslook() {
        Criticals.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.getEntityBoundingBox().minY, Criticals.mc.thePlayer.posZ, yaw, pitch, Criticals.mc.thePlayer.onGround));
    }
}

