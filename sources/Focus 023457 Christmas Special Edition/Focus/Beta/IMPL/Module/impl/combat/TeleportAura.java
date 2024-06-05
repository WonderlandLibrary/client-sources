package Focus.Beta.IMPL.Module.impl.combat;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.UTILS.AStarCustomPathFinder;
import Focus.Beta.UTILS.Math.RotationUtils;
import Focus.Beta.UTILS.world.PacketUtil;
import Focus.Beta.UTILS.world.Timer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TeleportAura extends Module {

    private ArrayList<Focus.Beta.UTILS.Vec3> path;
    private List<Focus.Beta.UTILS.Vec3>[] test;
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
    private Numbers<Double> reach = new Numbers<>("Range", "Range", 100.0D, 20.0D, 100.0D, 1.0D);

    public TeleportAura() {
        super("TeleportAura", new String[0], Type.COMBAT, "No");
        this.addValues(reach);
        this.path = new ArrayList<Focus.Beta.UTILS.Vec3>();
        this.test = (List<Focus.Beta.UTILS.Vec3>[])new ArrayList[50];
        this.targets = new CopyOnWriteArrayList<EntityLivingBase>();
        this.cpstimer = new Timer();
        this.ticks = 0;
        this.lastHealth = 0.0f;
        this.dashDistance = 5.0;
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e){
        this.targets = getTargets();
        if(this.cpstimer.hasElapsed(1000, true) && this.targets.size() > 0){
            this.test = (List<Focus.Beta.UTILS.Vec3>[])new ArrayList[50];
            for(int i = 0; i < ((this.targets.size() > 1) ? 1 : this.targets.size()); ++i){
                final EntityLivingBase T = this.targets.get(i);
                if(mc.thePlayer.getDistanceToEntity(T) > reach.getValue()){
                    return;
                }

                final Focus.Beta.UTILS.Vec3 topFrom = new Focus.Beta.UTILS.Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                final Focus.Beta.UTILS.Vec3 to = new Focus.Beta.UTILS.Vec3(T.posX, T.posY, T.posZ);

                this.path = this.computePath(topFrom, to);

                this.test[i] = this.path;

                for (final Focus.Beta.UTILS.Vec3 pathElm : this.path){
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
                }

                mc.thePlayer.swingItem();
                mc.playerController.attackEntity(mc.thePlayer, T);
                Collections.reverse(this.path);
                for (final Focus.Beta.UTILS.Vec3 pathElm : this.path){
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
                }
                final float[] rots = RotationUtils.getRotations(T);
                e.setYaw(rots[0]);
                e.setPitch(rots[1]);
            }
            this.cpstimer.reset();
        }
    }

    @Override
    public void onEnable() {
        this.startX = mc.thePlayer.posX;
        this.startY = mc.thePlayer.posY;
        this.startZ = mc.thePlayer.posZ;
        super.onEnable();
    }

    public boolean canAttack(final EntityLivingBase player) {
        return player != mc.thePlayer && !(player instanceof EntityArmorStand) && player instanceof EntityPlayer;
    }
    private ArrayList<Focus.Beta.UTILS.Vec3 > computePath(Focus.Beta.UTILS.Vec3 topFrom, final Focus.Beta.UTILS.Vec3 to) {
        final AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
        pathfinder.compute();
        int i = 0;
        Focus.Beta.UTILS.Vec3  lastLoc = null;
        Focus.Beta.UTILS.Vec3  lastDashLoc = null;
        final ArrayList<Focus.Beta.UTILS.Vec3 > path = new ArrayList<Focus.Beta.UTILS.Vec3 >();
        final ArrayList<Focus.Beta.UTILS.Vec3 > pathFinderPath = pathfinder.getPath();
        for (final Focus.Beta.UTILS.Vec3  pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            }
            else {
                boolean canContinue = true;
                Label_0356: {
                    if (pathElm.squareDistanceTo(lastDashLoc) > this.dashDistance * this.dashDistance) {
                        canContinue = false;
                    }
                    else {
                        final double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                        final double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                        final double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                        final double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                        final double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                        final double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                        for (int x = (int)smallX; x <= bigX; ++x) {
                            for (int y = (int)smallY; y <= bigY; ++y) {
                                for (int z = (int)smallZ; z <= bigZ; ++z) {
                                    if (!AStarCustomPathFinder.checkPositionValidity(x, y, z, false)) {
                                        canContinue = false;
                                        break Label_0356;
                                    }
                                }
                            }
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
        final List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        for (final Object o3 : mc.theWorld.getLoadedEntityList()) {
            if (o3 instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)o3;
                if (!this.canAttack(entity)) {
                    continue;
                }
                targets.add(entity);
            }
        }
        targets.sort((o1, o2) -> (int)(o1.getDistanceToEntity(mc.thePlayer) * 1000.0f - o2.getDistanceToEntity(mc.thePlayer) * 1000.0f));
        return targets;
    }


}


