package tk.rektsky.Utils.Entity;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.*;
import java.util.*;
import tk.rektsky.Module.Combat.*;
import tk.rektsky.Module.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;

public class EntityUtil
{
    public static ArrayList<EntityLivingBase> getEntitiesWithAntiBot() {
        final ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        final Collection<NetworkPlayerInfo> playerlist = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        final ArrayList<String> playerlists = new ArrayList<String>();
        for (final NetworkPlayerInfo info : playerlist) {
            if (info != null && info.getGameProfile() != null) {
                if (info.getGameProfile().getName() == null) {
                    continue;
                }
                playerlists.add(info.getGameProfile().getName());
            }
        }
        for (final Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
                for (final String name : playerlists) {
                    if (name.equals(((EntityLivingBase)o).getName())) {
                        final Entity e = (Entity)o;
                        final EntityLivingBase elb = (EntityLivingBase)e;
                        if (elb != Minecraft.getMinecraft().thePlayer) {
                            targets.add(elb);
                            break;
                        }
                        break;
                    }
                }
            }
            if (o instanceof EntityZombie) {
                final Entity e2 = (Entity)o;
                final EntityLivingBase elb2 = (EntityLivingBase)e2;
                targets.add(elb2);
                break;
            }
        }
        return targets;
    }
    
    public static ArrayList<EntityLivingBase> getEntities() {
        final ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        final Collection<NetworkPlayerInfo> playerlist = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        final ArrayList<String> playerlists = new ArrayList<String>();
        for (final NetworkPlayerInfo info : playerlist) {
            if (info != null && info.getGameProfile() != null) {
                if (info.getGameProfile().getName() == null) {
                    continue;
                }
                playerlists.add(info.getGameProfile().getName());
            }
        }
        for (final Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
                if (!ModulesManager.getModuleByClass(AntiBot.class).isToggled()) {
                    final Entity e = (Entity)o;
                    final EntityLivingBase elb = (EntityLivingBase)e;
                    if (elb != Minecraft.getMinecraft().thePlayer && !elb.isOnSameTeam(Minecraft.getMinecraft().thePlayer)) {
                        targets.add(elb);
                        continue;
                    }
                    continue;
                }
                else {
                    for (final String name : playerlists) {
                        if (name.equals(((EntityLivingBase)o).getName()) || !ModulesManager.getModuleByClass(AntiBot.class).isToggled()) {
                            final Entity e2 = (Entity)o;
                            final EntityLivingBase elb2 = (EntityLivingBase)e2;
                            if (elb2 != Minecraft.getMinecraft().thePlayer && !elb2.isOnSameTeam(Minecraft.getMinecraft().thePlayer)) {
                                targets.add(elb2);
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            if (o instanceof EntityZombie) {
                final Entity e = (Entity)o;
                final EntityLivingBase elb = (EntityLivingBase)e;
                targets.add(elb);
                break;
            }
        }
        return targets;
    }
    
    public static EntityLivingBase getClosest(final ArrayList<EntityLivingBase> entities, double distance) {
        EntityLivingBase target = null;
        for (final Object object : entities) {
            final Entity entity = (Entity)object;
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase player = (EntityLivingBase)entity;
                if (player instanceof EntityArmorStand || player instanceof EntitySlime || player == Minecraft.getMinecraft().thePlayer) {
                    continue;
                }
                final double currentDist = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(player);
                if (currentDist > distance) {
                    continue;
                }
                distance = currentDist;
                target = player;
            }
        }
        return target;
    }
}
