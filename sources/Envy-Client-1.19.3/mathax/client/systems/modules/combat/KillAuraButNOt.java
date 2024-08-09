package mathax.client.systems.modules.combat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class KillAuraButNOt extends Module {

    public KillAuraButNOt() {
        super(Categories.Combat, Items.AIR, "ServerSide KillAura", "KillAura that only swings ServerSide and doesnt bypass anything");
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player != null) {
            for (Entity target : mc.world.getEntities()) {
                if (target instanceof PlayerEntity && !target.equals(mc.player)) {
                    if (mc.player.distanceTo(target) > 0.1 & mc.player.distanceTo(target) < 5) {
                        double dX = mc.player.getX() - target.getX();
                        double dY = mc.player.getY() - target.getY();
                        double dZ = mc.player.getZ() - target.getZ();

                        double DistanceXZ = Math.sqrt(dX * dX + dZ * dZ);
                        double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + dY * dY);

                        double newYaw = Math.acos(dX / DistanceXZ) * 180 / Math.PI;
                        double newPitch = Math.acos(dY / -DistanceY) * 180 / Math.PI - 90;

                        if (dZ < 0.0)
                            newYaw = newYaw + Math.abs(180 - newYaw) * 2;
                        newYaw = (newYaw + 90);


                        //client side
                        //mc.player.setYaw((float) newYaw);
                        //mc.player.setPitch((float) newPitch);

                        //just serverside
                        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), (float) newYaw, (float) newPitch, mc.player.isOnGround()));
                    }
                    float cooldown = mc.player.getAttackCooldownProgress(mc.getTickDelta());
                    if (cooldown == 1) {
                        mc.interactionManager.attackEntity(mc.player, target);
                    }
                }
            }
        }
    }
}
