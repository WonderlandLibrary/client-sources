// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.minigames;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.chrest.event.EventTarget;
import java.util.Iterator;
import net.minecraft.world.World;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import me.chrest.client.friend.FriendManager;
import me.chrest.utils.ClientUtils;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import java.util.HashMap;
import net.minecraft.util.Vec3;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "QuakeAura")
public class QuakeCraft extends Module
{
    private float oldPitch;
    private float oldYaw;
    @Option.Op(min = 1.0, max = 10.0, increment = 1.0, name = "Delay")
    public int delay;
    private EntityPlayer target;
    public int buffer;
    private Map<EntityPlayer, List<Vec3>> playerPositions;
    
    public QuakeCraft() {
        this.buffer = 10;
        this.playerPositions = new HashMap<EntityPlayer, List<Vec3>>();
    }
    
    @EventTarget(4)
    public void preTick(final UpdateEvent e) {
        final Event.State state = e.getState();
        e.getState();
        if (state == Event.State.PRE) {
            this.oldPitch = ClientUtils.mc().thePlayer.rotationPitch;
            this.oldYaw = ClientUtils.mc().thePlayer.rotationYaw;
            double targetWeight = Double.NEGATIVE_INFINITY;
            this.target = null;
            for (final Object o : ClientUtils.world().loadedEntityList) {
                if (o instanceof EntityPlayer && o != ClientUtils.mc().thePlayer) {
                    final EntityPlayer entity = (EntityPlayer)o;
                    if (entity.equals(ClientUtils.mc().thePlayer) || FriendManager.isFriend(entity.getName()) || !ClientUtils.mc().thePlayer.canEntityBeSeen(entity)) {
                        continue;
                    }
                    if (this.target == null) {
                        this.target = entity;
                        targetWeight = this.getTargetWeight(entity);
                    }
                    else {
                        if (this.getTargetWeight(entity) <= targetWeight) {
                            continue;
                        }
                        this.target = entity;
                        targetWeight = this.getTargetWeight(entity);
                    }
                }
            }
            for (final EntityPlayer player : this.playerPositions.keySet()) {
                if (!ClientUtils.mc().theWorld.playerEntities.contains(player)) {
                    this.playerPositions.remove(player);
                }
            }
            for (final Object o : ClientUtils.world().loadedEntityList) {
                if (o instanceof EntityPlayer && o != ClientUtils.mc().thePlayer) {
                    final EntityPlayer player2 = (EntityPlayer)o;
                    this.playerPositions.putIfAbsent(player2, new ArrayList<Vec3>());
                    final List<Vec3> previousPositions = this.playerPositions.get(player2);
                    previousPositions.add(new Vec3(player2.posX, player2.posY, player2.posZ));
                    if (previousPositions.size() <= this.buffer) {
                        continue;
                    }
                    int i = 0;
                    for (final Vec3 position : new ArrayList<Vec3>(previousPositions)) {
                        if (i < previousPositions.size() - this.buffer) {
                            previousPositions.remove(previousPositions.get(i));
                        }
                        ++i;
                    }
                }
            }
            if (this.target != null) {
                ++this.delay;
                final Entity simulated = this.predictPlayerMovement(this.target);
                final float[] rotations = this.getPlayerRotations(ClientUtils.mc().thePlayer, simulated.posX, simulated.posY + this.target.getEyeHeight() - 0.1, simulated.posZ);
                e.setYaw(rotations[0]);
                e.setPitch(rotations[1] + 1.0f);
                if (this.delay >= 7 && ClientUtils.mc().thePlayer.experienceLevel == 0) {
                    ClientUtils.mc().playerController.sendUseItem(ClientUtils.mc().thePlayer, ClientUtils.mc().theWorld, ClientUtils.mc().thePlayer.inventory.getCurrentItem());
                    this.delay = 0;
                }
            }
        }
    }
    
    @EventTarget(0)
    public void postUpdate(final UpdateEvent e) {
        final Event.State state = e.getState();
        e.getState();
        if (state == Event.State.POST) {
            e.setPitch(this.oldPitch);
            e.setYaw(this.oldYaw);
        }
    }
    
    public double getTargetWeight(final EntityPlayer p) {
        double weight = -ClientUtils.mc().thePlayer.getDistanceToEntity(p);
        if (p.lastTickPosX == p.posX && p.lastTickPosY == p.posY && p.lastTickPosZ == p.posZ) {
            weight += 200.0;
        }
        weight -= p.getDistanceToEntity(ClientUtils.mc().thePlayer) / 5.0f;
        return weight;
    }
    
    private Entity predictPlayerMovement(final EntityPlayer target) {
        final int pingTicks = (int)Math.ceil(ClientUtils.mc().getNetHandler().func_175102_a(ClientUtils.mc().thePlayer.getUniqueID()).getResponseTime() / 50.0);
        return this.predictPlayerLocation(target, pingTicks);
    }
    
    public Entity predictPlayerLocation(final EntityPlayer player, final int ticks) {
        if (this.playerPositions.containsKey(player)) {
            final List<Vec3> previousPositions = this.playerPositions.get(player);
            if (previousPositions.size() > 1) {
                final Vec3 origin = previousPositions.get(0);
                final List<Vec3> deltas = new ArrayList<Vec3>();
                Vec3 previous = origin;
                for (final Vec3 position : previousPositions) {
                    deltas.add(new Vec3(position.xCoord - previous.xCoord, position.yCoord - previous.yCoord, position.zCoord - previous.zCoord));
                    previous = position;
                }
                double x = 0.0;
                double y = 0.0;
                double z = 0.0;
                for (final Vec3 delta : deltas) {
                    x += delta.xCoord;
                    y += delta.yCoord;
                    z += delta.zCoord;
                }
                x /= deltas.size();
                y /= deltas.size();
                z /= deltas.size();
                final EntityPlayer simulated = new EntityOtherPlayerMP(ClientUtils.mc().theWorld, player.getGameProfile());
                simulated.noClip = false;
                simulated.setPosition(player.posX, player.posY, player.posZ);
                for (int i = 0; i < ticks; ++i) {
                    simulated.moveEntity(x, y, z);
                }
                return simulated;
            }
        }
        return player;
    }
    
    public static boolean isOnSameTeam(final EntityPlayer e, final EntityPlayer e2) {
        return e.getDisplayName().getFormattedText().contains("§" + getTeamFromName(e)) && e2.getDisplayName().getFormattedText().contains("§" + getTeamFromName(e));
    }
    
    public static String getTeamFromName(final Entity e) {
        final Matcher m = Pattern.compile("§(.).*§r").matcher(e.getDisplayName().getFormattedText());
        if (m.find()) {
            return m.group(1);
        }
        return "f";
    }
    
    private final float[] getPlayerRotations(final Entity player, final double x, final double y, final double z) {
        final double deltaX = x - player.posX;
        final double deltaY = y - player.posY - player.getEyeHeight() - 0.1;
        final double deltaZ = z - player.posZ;
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        final double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        yawToEntity = wrapAngleTo180((float)yawToEntity);
        pitchToEntity = wrapAngleTo180((float)pitchToEntity);
        return new float[] { (float)yawToEntity, (float)pitchToEntity };
    }
    
    private static float wrapAngleTo180(float angle) {
        for (angle %= 360.0f; angle >= 180.0f; angle -= 360.0f) {}
        while (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }
}
