package com.alan.clients.component.impl.player;

import com.alan.clients.Client;
import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.BackendPacketEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.player.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import rip.vantage.commons.packet.impl.client.protection.C2SPacketFilterEntities;
import rip.vantage.commons.packet.impl.server.protection.S2CPacketEntities;
import rip.vantage.commons.util.time.StopWatch;
import rip.vantage.network.core.Network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TargetComponent extends Component implements Accessor {

    private static final HashMap<Class<?>, List<EntityLivingBase>> targetMap = new HashMap<>();
    private static final HashMap<Class<?>, Integer> entityAmountMap = new HashMap<>();
    private static final HashMap<Class<?>, StopWatch> timerMap = new HashMap<>();
    private static final HashMap<Integer, Class<?>> queuedMap = new HashMap<>();
    private static int id;
    private static KillAura killAura;
    private static boolean forceUpdate;

    public static void forceUpdate() {
        forceUpdate = true;
    }

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        targetMap.clear();
        entityAmountMap.clear();
        timerMap.clear();
        queuedMap.clear();
        id = 0;
    };

    @EventLink
    public final Listener<BackendPacketEvent> onBackendPacket = event -> {
        if (event.getPacket() instanceof S2CPacketEntities) {
            S2CPacketEntities wrapper = (S2CPacketEntities) event.getPacket();
            int uid = wrapper.getUid();
            if (queuedMap.containsKey(uid)) {
                Class<?> module = queuedMap.get(uid);

                List<EntityLivingBase> targetList = new ArrayList<>();

                for (C2SPacketFilterEntities.Entity entity : wrapper.getEntityList()) {
                    Entity e = mc.theWorld.getEntityByID(entity.getEntityId());

                    if (e == null) {
                        continue;
                    }

                    targetList.add((EntityLivingBase) e);
                }

                targetMap.put(module, targetList);
                queuedMap.remove(uid);
            }
        }
    };

    public static EntityLivingBase getTarget(double range) {
        return getTargets(range).stream().findFirst().orElse(null);
    }

    public static List<EntityLivingBase> getTargets(double range) {
        if (killAura == null) {
            killAura = Client.INSTANCE.getModuleManager().get(KillAura.class);
        }

        return getTargets(killAura.getClass(), killAura.player.getValue(), killAura.invisibles.getValue(), killAura.animals.getValue(), killAura.mobs.getValue(), killAura.teams.getValue()).stream().filter(entity -> mc.thePlayer.getDistanceToEntity(entity) <= range).collect(Collectors.toList());
    }

    public static List<EntityLivingBase> getTargets(Class<?> module, double range, boolean players, boolean invisibles, boolean animals, boolean mobs, boolean teams) {
        return getTargets(module, players, invisibles, animals, mobs, teams).stream().filter(entity -> mc.thePlayer.getDistanceToEntity(entity) <= range).collect(Collectors.toList());
    }

    public static List<EntityLivingBase> getTargets() {
        if (killAura == null) {
            killAura = Client.INSTANCE.getModuleManager().get(KillAura.class);
        }

        return getTargets(killAura.getClass(), killAura.player.getValue(), killAura.invisibles.getValue(), killAura.animals.getValue(), killAura.mobs.getValue(), killAura.teams.getValue());
    }

    public static List<EntityLivingBase> getTargets(Class<?> module, boolean players, boolean invisibles, boolean animals, boolean mobs, boolean teams) {
        if (queuedMap.containsValue(module) && !timerMap.get(module).finished(5000L) && !forceUpdate) {
            return targetMap.getOrDefault(module, new ArrayList<>()).stream().filter(entity -> mc.theWorld.loadedEntityList.contains(entity)).collect(Collectors.toList());
        }

        if (!targetMap.containsKey(module) ||
                timerMap.containsKey(module) && (timerMap.get(module).finished(5000L) ||
                        entityAmountMap.containsKey(module) && entityAmountMap.get(module) != mc.theWorld.loadedEntityList.size() && timerMap.get(module).finished(1000L)) || forceUpdate) {
            List<EntityLivingBase> startingTargets = mc.theWorld.loadedEntityList
                    .stream()

                    .filter(entity -> entity instanceof EntityLivingBase && entity != mc.getRenderViewEntity() && !UserFriendAndTargetComponent.isFriend(entity.getCommandSenderName()))

                    .map(entity -> ((EntityLivingBase) entity))

                    .filter(entity -> !Client.INSTANCE.getBotManager().contains(entity))

                    .filter(entity -> {
                        if (entity instanceof EntityPlayer) {
                            if (players) {
                                return (!PlayerUtil.sameTeam(entity) || teams);
                            } else {
                                return false;
                            }
                        }

                        return entity instanceof EntityAnimal || entity instanceof EntityMob;
                    })

                    .collect(Collectors.toList());

            if (startingTargets.isEmpty()) {
                return new ArrayList<>();
            }

            List<C2SPacketFilterEntities.Entity> entityList = new ArrayList<>();
            startingTargets.forEach(entity -> entityList.add(new C2SPacketFilterEntities.Entity(entity.getEntityId(), entity instanceof EntityPlayer ? 0 : entity instanceof EntityAnimal ? 1 : 2, entity.isInvisible())));

            id++;

            Network.getInstance().getClient().sendMessage(new C2SPacketFilterEntities(entityList, players, invisibles, animals, mobs, id).export());

            entityAmountMap.put(module, mc.theWorld.loadedEntityList.size());
            timerMap.put(module, new StopWatch());
            queuedMap.put(id, module);
        }

        forceUpdate = false;
        return targetMap.getOrDefault(module, new ArrayList<>()).stream().filter(entity -> mc.theWorld.loadedEntityList.contains(entity)).collect(Collectors.toList());
    }
}
