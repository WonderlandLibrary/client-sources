package me.vaziak.sensation.client.impl.combat;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InfiniteAura extends Module {
    private DoubleProperty cps = new DoubleProperty("CPS", "The maximum amount your aps can fluctuate.",10.0, 1, 20.0, 0.1);

    public InfiniteAura() {
        super("Infinite Aura", Category.COMBAT);
        registerValue(cps);
    }

    private List<EntityLivingBase> targetList = new ArrayList<>();
    private TimerUtil stopwatch = new TimerUtil();

    @Collect
    public void onEvent(PlayerUpdateEvent event) {
        updateTargetList();

        if (event.isPre()) {
            if (targetList.size() == 0)
                return;

            EntityLivingBase entity = targetList.get(0);

            double lastX = mc.thePlayer.posX, lastY = mc.thePlayer.posY, lastZ = mc.thePlayer.posZ;

            boolean safe = !(mc.thePlayer.getDistanceToEntity(entity) <= 5);
            double midx = entity.posX + mc.thePlayer.posX / 2;
            double midy = entity.posY + mc.thePlayer.posY / 2;
            double midz = entity.posZ + mc.thePlayer.posZ / 2; 
            event.yaw = 180.0f;
            event.pitch = 90.0f;
            
            if (stopwatch.hasPassed(1000 / (7))) {

                mc.thePlayer.swingItem();
                if (safe) {
                    mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY + .01, entity.posZ, false));
                	
                }
                mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));

                if (safe) {
                	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ, true));
                }
                stopwatch.reset();
                targetList.clear();
            } else if (stopwatch.hasPassed((1000 / cps.getValue()) - 15)) {
            	if (!safe) return;
                mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(midx, midy, midz, false));
                float f = (float) (midx - entity.posX);
                float f1 = (float) (midy - entity.posY);
                float f2 = (float) (midy - entity.posZ);
                if (!(MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2) < 5)) {
                    double midx1 = entity.posX + midx / 2;
                    double midy1 = entity.posY + midx / 2;
                    double midz1 = entity.posZ + midx / 2;

                    mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(midx1, midy1, midz1, false));
                    float f4 = (float) (midx1 - entity.posX);
                    float f5 = (float) (midy1 - entity.posY);
                    float f6 = (float) (midy1 - entity.posZ);
                    if (!(MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6) < 5)) {
                        double midx2 = entity.posX + midx1 / 2;
                        double midy3 = entity.posY + midx1 / 2;
                        double midz4 = entity.posZ + midx1 / 2;

                        mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(midx2, midy3, midz4, false));
                    }
                }
            }
        }
    }

    float[] Aim(Entity ent) {
        double x1 = ent.posX + (ent.posX - ent.lastTickPosX);
        double z1 = ent.posZ + (ent.posZ - ent.lastTickPosZ);
        double y1 = ent.posY + ent.getEyeHeight() / 2;

        double x2 = x1 - mc.thePlayer.posX;

        double z2 = z1 - mc.thePlayer.posZ;

        double y2 = y1 - mc.thePlayer.posY - 1.2;

        return new float[] { (float) (float) -(Math.atan2(x2, z2) * (58)), (float) -Math.toDegrees(Math.atan2(y2, MathHelper.sqrt_double(x2 * x2 + z2 * z2))) };
    }

    @Collect
    public void onEvent(SendPacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer packetPlayer = (C03PacketPlayer) event.getPacket();

            if (targetList.size() == 0) {
                return;
            }
        }
    }

    void updateTargetList() {
        targetList.clear();
        mc.theWorld.getLoadedEntityList().forEach(entity -> {
            if (entity != null && entity instanceof EntityLivingBase) {
                if (isValidTarget((EntityLivingBase) entity) && Sensation.instance.friendManager.getFriend(entity.getName()) == null) {
                    targetList.add((EntityLivingBase) entity);
                } else if (targetList.contains(entity)) {
                    targetList.remove(entity);
                }

            }
        });

        targetList.sort(Comparator.comparingDouble(mc.thePlayer::getDistanceToEntity));
        targetList.sort((e1, e2) -> Boolean.compare(e2 instanceof EntityPlayer, e1 instanceof EntityPlayer));
    }

    boolean isValidTarget(EntityLivingBase entity) {
        if (entity == mc.thePlayer)
            return false;

        return true;
    }
}
