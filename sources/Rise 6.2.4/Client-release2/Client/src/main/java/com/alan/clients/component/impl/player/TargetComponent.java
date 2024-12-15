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
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import rip.vantage.commons.packet.impl.client.protection.C2SPacketFilterEntities;
import rip.vantage.commons.packet.impl.server.protection.S2CPacketEntities;
import rip.vantage.commons.util.time.StopWatch;
import rip.vantage.network.core.Network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.json.XMLTokener.entity;

public class TargetComponent extends Component implements Accessor {

    private static KillAura killAura;

    public static EntityLivingBase getTarget(double range) {
        return getTargets(range).stream().findFirst().orElse(null);
    }

    public static List<EntityLivingBase> getTargets(double range) {
        if (killAura == null) {
            killAura = Client.INSTANCE.getModuleManager().get(KillAura.class);
        }

        return getTargets(killAura.player.getValue(), killAura.invisibles.getValue(), killAura.animals.getValue(), killAura.mobs.getValue(), killAura.teams.getValue()).stream().filter(entity -> mc.thePlayer.getDistanceToEntity(entity) <= range).collect(Collectors.toList());
    }

    public static List<EntityLivingBase> getTargets(double range, boolean players, boolean invisibles, boolean animals, boolean mobs, boolean teams) {
        return getTargets(players, invisibles, animals, mobs, teams).stream().filter(entity -> mc.thePlayer.getDistanceToEntity(entity) <= range).collect(Collectors.toList());
    }

    public static List<EntityLivingBase> getTargets() {
        if (killAura == null) {
            killAura = Client.INSTANCE.getModuleManager().get(KillAura.class);
        }

        return getTargets(killAura.player.getValue(), killAura.invisibles.getValue(), killAura.animals.getValue(), killAura.mobs.getValue(), killAura.teams.getValue());
    }

    public static List<EntityLivingBase> getTargets(boolean players, boolean invisibles, boolean animals, boolean mobs, boolean teams) {
        return getTargets(players, invisibles, animals, mobs, teams, false);
    }

    public static List<EntityLivingBase> getTargets(boolean players, boolean invisibles, boolean animals, boolean mobs, boolean teams, boolean friends) {

        return mc.theWorld.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityLivingBase
                            && !Client.INSTANCE.getBotManager().contains(entity)
                            && entity != mc.getRenderViewEntity()
                            && (!UserFriendAndTargetComponent.isFriend(entity.getCommandSenderName()) || friends)
                            && (!(entity instanceof EntityPlayer) || players)
                            && (!(entity instanceof IMob || entity instanceof INpc) || mobs)
                            && (!(entity instanceof EntityAnimal || entity instanceof EntityAmbientCreature || entity instanceof EntityWaterMob) || animals)
                            && (!entity.isInvisible() || invisibles)
                            && !(entity instanceof EntityArmorStand)
                )
                .map(entity -> ((EntityLivingBase) entity))
                .filter(entity -> !(entity instanceof EntityPlayer) || (!PlayerUtil.sameTeam(entity) || teams))
                .collect(Collectors.toList());
    }
}
