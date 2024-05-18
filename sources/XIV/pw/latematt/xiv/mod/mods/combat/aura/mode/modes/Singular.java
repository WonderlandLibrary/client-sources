package pw.latematt.xiv.mod.mods.combat.aura.mode.modes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import pw.latematt.timer.Timer;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.mods.combat.aura.KillAura;
import pw.latematt.xiv.mod.mods.combat.aura.mode.AuraMode;
import pw.latematt.xiv.utils.EntityUtils;

import java.util.Optional;

/**
 * @author Matthew
 */
public class Singular extends AuraMode {
    private EntityLivingBase entityToAttack;
    private final Timer timer = new Timer();

    public Singular(KillAura killAura) {
        super("Singular", killAura);
    }

    @Override
    public void onPreMotionUpdate(MotionUpdateEvent event) {
        if (entityToAttack == null) {
            Optional<Entity> firstValidEntity = mc.theWorld.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityLivingBase)
                    .filter(entity -> killAura.isValidEntity((EntityLivingBase) entity))
                    .sorted((entity1, entity2) -> {
                        double entity1Distance = mc.thePlayer.getDistanceToEntity(entity1);
                        double entity2Distance = mc.thePlayer.getDistanceToEntity(entity2);
                        return entity1Distance > entity2Distance ? 1 : entity2Distance > entity1Distance ? -1 : 0;
                    }).findFirst();
            if (firstValidEntity.isPresent()) {
                entityToAttack = (EntityLivingBase) firstValidEntity.get();
            }
        }

        if (killAura.isValidEntity(entityToAttack)) {
            if (killAura.autoBlock.getValue() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                ItemSword sword = (ItemSword) mc.thePlayer.getCurrentEquippedItem().getItem();
                sword.onItemRightClick(mc.thePlayer.getCurrentEquippedItem(), mc.theWorld, mc.thePlayer);
                mc.playerController.updateController();
            }

            if (!killAura.isHealing()) {
                float[] rotations = EntityUtils.getEntityRotations(entityToAttack);
                if (killAura.silent.getValue()) {
                    event.setYaw(rotations[0]);
                    event.setPitch(rotations[1]);
                } else {
                    mc.thePlayer.rotationYaw = rotations[0];
                    mc.thePlayer.rotationPitch = rotations[1];
                }
            }
        } else {
            entityToAttack = null;
        }
    }

    @Override
    public void onPostMotionUpdate(MotionUpdateEvent event) {
        if (entityToAttack != null && !killAura.isHealing()) {
            if (timer.hasReached(killAura.getDelay())) {
                killAura.attack(entityToAttack);
                timer.reset();
            }
        }
    }

    @Override
    public void onMotionPacket(C03PacketPlayer packet) {
        if (entityToAttack != null && !killAura.isHealing()) {
            float[] rotations = EntityUtils.getEntityRotations(entityToAttack);
            packet.setYaw(rotations[0]);
            packet.setPitch(rotations[1]);
        }
    }

    @Override
    public boolean isAttacking() {
        return killAura.isValidEntity(entityToAttack);
    }

    @Override
    public void onDisabled() {
        entityToAttack = null;
    }
}
