/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.combat;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.UTILS.AStarCustomPathFinder;
import drunkclient.beta.UTILS.Math.RotationUtils;
import drunkclient.beta.UTILS.Vec3;
import drunkclient.beta.UTILS.world.PacketUtil;
import drunkclient.beta.UTILS.world.Timer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class TeleportAura
extends Module {
    private ArrayList<Vec3> path;
    private List<Vec3>[] test;
    private List<EntityLivingBase> targets;
    private Timer cpstimer;
    public static Timer timer;
    public static boolean canReach;
    int ticks;
    double startX;
    double startY;
    double startZ;
    private float lastHealth;
    double dashDistance;
    private Numbers<Double> reach = new Numbers<Double>("Range", "Range", 100.0, 20.0, 100.0, 1.0);

    public TeleportAura() {
        super("TeleportAura", new String[0], Type.COMBAT, "No");
        this.addValues(this.reach);
        this.path = new ArrayList();
        this.test = new ArrayList[50];
        this.targets = new CopyOnWriteArrayList<EntityLivingBase>();
        this.cpstimer = new Timer();
        this.ticks = 0;
        this.lastHealth = 0.0f;
        this.dashDistance = 5.0;
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        this.targets = this.getTargets();
        if (!this.cpstimer.hasElapsed(1000L, true)) return;
        if (this.targets.size() <= 0) return;
        this.test = new ArrayList[50];
        int i = 0;
        while (true) {
            if (i >= (this.targets.size() > 1 ? 1 : this.targets.size())) {
                this.cpstimer.reset();
                return;
            }
            EntityLivingBase T = this.targets.get(i);
            if ((double)Minecraft.thePlayer.getDistanceToEntity(T) > (Double)this.reach.getValue()) {
                return;
            }
            Vec3 topFrom = new Vec3(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ);
            Vec3 to = new Vec3(T.posX, T.posY, T.posZ);
            this.path = this.computePath(topFrom, to);
            this.test[i] = this.path;
            for (Vec3 pathElm : this.path) {
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
            }
            Minecraft.thePlayer.swingItem();
            TeleportAura.mc.playerController.attackEntity(Minecraft.thePlayer, T);
            Collections.reverse(this.path);
            for (Vec3 pathElm : this.path) {
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
            }
            float[] rots = RotationUtils.getRotations(T);
            e.setYaw(rots[0]);
            e.setPitch(rots[1]);
            ++i;
        }
    }

    @Override
    public void onEnable() {
        this.startX = Minecraft.thePlayer.posX;
        this.startY = Minecraft.thePlayer.posY;
        this.startZ = Minecraft.thePlayer.posZ;
        super.onEnable();
    }

    public boolean canAttack(EntityLivingBase player) {
        if (player == Minecraft.thePlayer) return false;
        if (player instanceof EntityArmorStand) return false;
        if (!(player instanceof EntityPlayer)) return false;
        return true;
    }

    private ArrayList<Vec3> computePath(Vec3 topFrom, Vec3 to) {
        AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
        pathfinder.compute();
        int i = 0;
        Vec3 lastLoc = null;
        Vec3 lastDashLoc = null;
        ArrayList<Vec3> path = new ArrayList<Vec3>();
        ArrayList<Vec3> pathFinderPath = pathfinder.getPath();
        Iterator<Vec3> iterator = pathFinderPath.iterator();
        while (iterator.hasNext()) {
            Vec3 pathElm = iterator.next();
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            } else {
                boolean canContinue = true;
                if (pathElm.squareDistanceTo(lastDashLoc) > this.dashDistance * this.dashDistance) {
                    canContinue = false;
                } else {
                    double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                    double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                    double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                    double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                    double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                    double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                    int x = (int)smallX;
                    block1: while ((double)x <= bigX) {
                        int y = (int)smallY;
                        while (true) {
                            int z;
                            if ((double)y <= bigY) {
                                z = (int)smallZ;
                            } else {
                                ++x;
                                continue block1;
                            }
                            while ((double)z <= bigZ) {
                                if (!AStarCustomPathFinder.checkPositionValidity(x, y, z, false)) {
                                    canContinue = false;
                                    break block1;
                                }
                                ++z;
                            }
                            ++y;
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            ++i;
        }
        return path;
    }

    private List<EntityLivingBase> getTargets() {
        ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        Iterator<Entity> iterator = TeleportAura.mc.theWorld.getLoadedEntityList().iterator();
        while (true) {
            EntityLivingBase entity;
            if (!iterator.hasNext()) {
                targets.sort((o1, o2) -> (int)(o1.getDistanceToEntity(Minecraft.thePlayer) * 1000.0f - o2.getDistanceToEntity(Minecraft.thePlayer) * 1000.0f));
                return targets;
            }
            Entity o3 = iterator.next();
            if (!(o3 instanceof EntityLivingBase) || !this.canAttack(entity = (EntityLivingBase)o3)) continue;
            targets.add(entity);
        }
    }
}

