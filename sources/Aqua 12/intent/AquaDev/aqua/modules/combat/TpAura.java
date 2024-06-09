// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.combat;

import java.util.Collections;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import intent.AquaDev.aqua.utils.FriendSystem;
import net.minecraft.entity.player.EntityPlayer;
import events.listeners.EventUpdate;
import intent.AquaDev.aqua.utils.RotationUtil;
import net.minecraft.entity.EntityLivingBase;
import events.listeners.EventPreMotion;
import events.Event;
import java.util.Iterator;
import java.util.ArrayList;
import intent.AquaDev.aqua.utils.PathFinder;
import java.util.List;
import net.minecraft.util.Vec3;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import net.minecraft.entity.Entity;
import intent.AquaDev.aqua.modules.Module;

public class TpAura extends Module
{
    public double delay;
    Entity target;
    TimeUtil time;
    
    public TpAura() {
        super("TpAura", Type.Combat, "TpAura", 0, Category.Combat);
        this.target = null;
        this.time = new TimeUtil();
        Aqua.setmgr.register(new Setting("TpDelay", this, 50.0, 0.0, 1000.0, false));
    }
    
    public static List<Vec3> calculatePath(final Vec3 startPos, final Vec3 endPos) {
        final PathFinder pathfinder = new PathFinder(startPos, endPos);
        pathfinder.calculatePath(2000);
        int i = 0;
        Vec3 lastLoc = null;
        Vec3 lastDashLoc = null;
        final List<Vec3> path = new ArrayList<Vec3>();
        final List<Vec3> pathFinderPath = pathfinder.getPath();
        for (final Vec3 pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            }
            else {
                boolean canContinue = true;
                Label_0332: {
                    if (pathElm.squareDistanceTo(lastDashLoc) > 10.0) {
                        canContinue = false;
                    }
                    else {
                        final double smallX = Math.min(lastDashLoc.xCoord, pathElm.xCoord);
                        final double smallY = Math.min(lastDashLoc.yCoord, pathElm.yCoord);
                        final double smallZ = Math.min(lastDashLoc.zCoord, pathElm.zCoord);
                        final double bigX = Math.max(lastDashLoc.xCoord, pathElm.xCoord);
                        final double bigY = Math.max(lastDashLoc.yCoord, pathElm.yCoord);
                        final double bigZ = Math.max(lastDashLoc.zCoord, pathElm.zCoord);
                        for (int x = (int)smallX; x <= bigX; ++x) {
                            for (int y = (int)smallY; y <= bigY; ++y) {
                                for (int z = (int)smallZ; z <= bigZ; ++z) {
                                    if (!PathFinder.checkPositionValidity(x, y, z, false)) {
                                        canContinue = false;
                                        break Label_0332;
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
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventPreMotion) {
            final float[] rota = RotationUtil.Intavee(TpAura.mc.thePlayer, (EntityLivingBase)this.target);
            ((EventPreMotion)e).setPitch(RotationUtil.pitch);
            ((EventPreMotion)e).setYaw(RotationUtil.yaw);
            RotationUtil.setYaw(rota[0], 180.0f);
            RotationUtil.setPitch(rota[1], 180.0f);
        }
        if (e instanceof EventUpdate) {
            final float delay = (float)Aqua.setmgr.getSetting("TpAuraTpDelay").getCurrentNumber();
            if (this.time.hasReached((long)delay)) {
                if (!TpAura.mc.thePlayer.isMoving()) {
                    this.attack(this.modes());
                }
                this.time.reset();
            }
        }
    }
    
    public Entity modes() {
        for (final EntityPlayer entity : TpAura.mc.theWorld.playerEntities) {
            if (entity != TpAura.mc.thePlayer && (this.target == null || entity.getDistanceToEntity(TpAura.mc.thePlayer) < this.target.getDistanceToEntity(TpAura.mc.thePlayer))) {
                this.target = entity;
            }
        }
        return this.target;
    }
    
    public void attack(final Entity entity) {
        if (entity == null) {
            return;
        }
        final List<Vec3> path = calculatePath(TpAura.mc.thePlayer.getPositionVector(), entity.getPositionVector());
        for (final Vec3 pos : path) {
            if (FriendSystem.isFriend(entity.getName())) {
                return;
            }
            TpAura.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pos.xCoord, pos.yCoord, pos.zCoord, false));
        }
        if (TpAura.mc.getCurrentServerData().serverIP.equalsIgnoreCase("cubecraft.net")) {
            TpAura.mc.playerController.attackEntity(TpAura.mc.thePlayer, this.target);
            TpAura.mc.thePlayer.swingItem();
        }
        else {
            TpAura.mc.thePlayer.swingItem();
            TpAura.mc.playerController.attackEntity(TpAura.mc.thePlayer, entity);
            TpAura.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        }
        Collections.reverse(path);
        for (final Vec3 pos : path) {
            TpAura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pos.xCoord, pos.yCoord, pos.zCoord, false));
        }
    }
}
