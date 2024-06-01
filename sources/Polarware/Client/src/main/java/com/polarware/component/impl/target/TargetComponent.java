package com.polarware.component.impl.target;

import com.polarware.Client;
import com.polarware.module.impl.combat.KillAuraModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.BackendPacketEvent;
import com.polarware.event.impl.other.TickEvent;
import com.polarware.util.interfaces.InstanceAccess;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import packet.Packet;
import packet.impl.server.protection.lIllIIlllIIIIlIllIIIIllIlllllIll;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * @author Alan
 * @since 3/03/2022
 */
public class TargetComponent extends ConcurrentLinkedQueue<EntityLivingBase> implements InstanceAccess {

    boolean players = true;
    boolean invisibles = false;
    boolean animals = false;
    boolean mobs = false;
    boolean teams = false;

    private int loadedEntitySize;

    public void init() {
        Client.INSTANCE.getEventBus().register(this);
    }

    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        if (mc.thePlayer.ticksExisted % 150 == 0 || loadedEntitySize != mc.theWorld.loadedEntityList.size()) {
            this.updateTargets();
            loadedEntitySize = mc.theWorld.loadedEntityList.size();
        }
    };

    public void updateTargets() {
        try {
            KillAuraModule killAuraModule = getModule(KillAuraModule.class);
            players = killAuraModule.player.getValue();
            invisibles = killAuraModule.invisibles.getValue();
            animals = killAuraModule.animals.getValue();
            mobs = killAuraModule.mobs.getValue();
            teams = killAuraModule.teams.getValue();

        } catch (Exception ignored) {
            if (Client.DEVELOPMENT_SWITCH) ignored.printStackTrace();
        }
    }

    public List<EntityLivingBase> getTargets(double range) {
        return mc.theWorld.loadedEntityList

                .stream()

                .filter(entity -> entity instanceof EntityLivingBase)

                .map(entity -> (EntityLivingBase) entity)

                .filter(entity -> {

                    if (entity instanceof EntityPlayer && !players) {
                        return false;
                    }

                    if (entity.isOnSameTeam(mc.thePlayer) && !teams) {
                        return false;
                    }

                    if (entity instanceof EntityAnimal && !animals) {
                        return false;
                    }

                    if (entity instanceof EntityMob && !mobs) {
                        return false;
                    }

                    if (entity.isInvisible() && !invisibles) {
                        return false;
                    }

                    if (entity.deathTime != 0 || entity.isDead) {
                        return false;
                    }

                    if (entity instanceof EntityArmorStand) {
                        return false;
                    }

                    if (mc.thePlayer.getDistanceToEntity(entity) > range) {
                        return false;
                    }

                    return entity != mc.thePlayer;

                })

                .sorted(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity)))

                .collect(Collectors.toList());
    }

    @EventLink()
    public final Listener<BackendPacketEvent> onBackendPacket = event -> {

        if(true) return;

        Packet packet = event.getPacket();

        if (packet instanceof lIllIIlllIIIIlIllIIIIllIlllllIll) {
//            ChatUtil.display("Received Update");

            lIllIIlllIIIIlIllIIIIllIlllllIll targetUpdate = ((lIllIIlllIIIIlIllIIIIllIlllllIll) packet);

//            ChatUtil.display("SimpleEntity Size: " + targetUpdate.targets.size());

            ArrayList<Entity> targets = new ArrayList<>();

            targetUpdate.getTargets().forEach(lllIIllIlIIlIlllIllIlIIIIIlIlIlI -> {
                Entity entity = mc.theWorld.getEntityByID(lllIIllIlIIlIlllIllIlIIIIIlIlIlI.lllIIlIIllIlIIlIlllIllIIIIlIlIlI);
                if (entity != null) targets.add(entity);
            });

            this.clear();
            targets.forEach(target -> {
                if (target instanceof EntityLivingBase) {
                    this.add((EntityLivingBase) target);
                }
            });

//            ChatUtil.display("EntityLivingBase Size: " + this.size());
        }
    };
}