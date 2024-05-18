package pw.latematt.xiv.mod.mods.combat.aura.mode.modes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import pw.latematt.timer.Timer;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.mods.combat.aura.KillAura;
import pw.latematt.xiv.mod.mods.combat.aura.mode.AuraMode;
import pw.latematt.xiv.utils.EntityUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author Matthew
 */
public class Switch extends AuraMode {
    private final List<EntityLivingBase> entities;
    private EntityLivingBase entityToAttack;
    private final Timer timer = new Timer();

    public Switch(KillAura killAura) {
        super("Switch", killAura);
        entities = new CopyOnWriteArrayList<>();
    }

    @Override
    public void onPreMotionUpdate(MotionUpdateEvent event) {
        if (entities.isEmpty()) {
            mc.theWorld.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityLivingBase)
                    .filter(entity -> killAura.isValidEntity((EntityLivingBase) entity))
                    .forEach(entity -> entities.add((EntityLivingBase) entity));
        }

        if (!entities.isEmpty()) {
            EntityLivingBase firstInArray = entities.stream()
                    .sorted((entity1, entity2) -> {
                        float yaw = EntityUtils.getYawChange(entity1);
                        float pitch = EntityUtils.getPitchChange(entity1);
                        final float firstEntityDistance = (yaw + pitch) / 2F;

                        yaw = EntityUtils.getYawChange(entity2);
                        pitch = EntityUtils.getPitchChange(entity2);
                        final float secondEntityDistance = (yaw + pitch) / 2F;

                        return firstEntityDistance > secondEntityDistance ? 1 :
                                secondEntityDistance > firstEntityDistance ? -1 : 0;
                    }).collect(Collectors.toList()).get(0);
            if (killAura.isValidEntity(firstInArray)) {
                entityToAttack = firstInArray;
            } else {
                entities.remove(firstInArray);
            }
        }

        if (killAura.isValidEntity(entityToAttack)) {
            if (killAura.autoBlock.getValue() && Objects.nonNull(mc.thePlayer.getCurrentEquippedItem()) && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
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
            entities.remove(entityToAttack);
        }
    }

    @Override
    public void onPostMotionUpdate(MotionUpdateEvent event) {
        if (entityToAttack != null && !killAura.isHealing()) {
            if (timer.hasReached(killAura.getDelay())) {
                killAura.attack(entityToAttack);
                entities.remove(entityToAttack);
                entityToAttack = null;
                timer.reset();
            }
        }
    }

    @Override
    public void onMotionPacket(C03PacketPlayer packet) {
        if (entityToAttack != null && !killAura.isHealing()) {
            float[] rotations = EntityUtils.getEntityRotations(entityToAttack);
            if (killAura.silent.getValue()) {
                packet.setYaw(rotations[0]);
                packet.setPitch(rotations[1]);
            }
        }
    }

    @Override
    public boolean isAttacking() {
        return killAura.isValidEntity(entityToAttack);
    }

    @Override
    public void onDisabled() {
        entities.clear();
        entityToAttack = null;
    }
}
