package me.xatzdevelopments.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventRender3D;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.util.RenderUtils;
import me.xatzdevelopments.util.Timer2;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TPAura extends Module
{
    public Timer2 timer;
    private static final ArrayList<Packet> Positions;
    double[] OriginalPostion;
    double[] TargetPosition;
    EntityLivingBase target;
    double DistanceFromTarget;
    double speed;
    
    static {
        Positions = new ArrayList<Packet>();
    }
    
    public TPAura() {
        super("TPAura", 0, Category.COMBAT, "It teleports you to your enemy and attacks them.");
        this.timer = new Timer2();
        this.OriginalPostion = new double[] { 0.0, 0.0, 0.0 };
        this.TargetPosition = new double[] { 0.0, 0.0, 0.0 };
    }
    
    @Override
    public void onEnable() {
        TPAura.Positions.clear();
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventMotion && e.isPre()) {
            List<EntityLivingBase> targets = (List<EntityLivingBase>)this.mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            targets = (List<EntityLivingBase>) targets.stream().filter(entity -> entity.getDistanceToEntity(this.mc.thePlayer) < 20.0f && entity != this.mc.thePlayer);
            targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(this.mc.thePlayer)));
            if (!targets.isEmpty()) {
                this.target = targets.get(0);
                this.TargetPosition[0] = this.target.posX;
                this.TargetPosition[1] = this.target.posY;
                this.TargetPosition[2] = this.target.posZ;
                this.OriginalPostion[0] = this.mc.thePlayer.posX;
                this.OriginalPostion[1] = this.mc.thePlayer.posY;
                this.OriginalPostion[2] = this.mc.thePlayer.posZ;
                this.DistanceFromTarget = this.mc.thePlayer.getDistance(this.TargetPosition[0], this.TargetPosition[1], this.TargetPosition[2]);
                final double DistanceX = this.OriginalPostion[0] - this.TargetPosition[0];
                final double DistanceY = this.OriginalPostion[1] - this.TargetPosition[1];
                final double DistanceZ = this.OriginalPostion[2] - this.TargetPosition[2];
                this.speed = 0.2;
                boolean stopSearching = false;
                for (int i = 0; i < 25 && !stopSearching; ++i) {
                    if (this.DistanceFromTarget < this.speed * 3.0) {
                        this.OriginalPostion[0] = this.TargetPosition[0];
                        this.OriginalPostion[1] = this.TargetPosition[1];
                        this.OriginalPostion[2] = this.TargetPosition[2];
                        stopSearching = false;
                        this.mc.thePlayer.swingItem();
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK));
                        final int i2 = 0;
                        while (i < TPAura.Positions.size()) {
                            ++i;
                        }
                        TPAura.Positions.clear();
                        return;
                    }
                    final double var7 = this.OriginalPostion[0] - this.TargetPosition[0];
                    final double var8 = this.OriginalPostion[1] - this.TargetPosition[1];
                    final double var9 = this.OriginalPostion[2] - this.TargetPosition[2];
                    this.DistanceFromTarget = MathHelper.sqrt_double(var7 * var7 + var8 * var8 + var9 * var9);
                    final double TeleportPosX = this.OriginalPostion[0] - DistanceX / this.DistanceFromTarget * this.speed;
                    final double TeleportPosY = this.OriginalPostion[1] - DistanceY / this.DistanceFromTarget * this.speed;
                    final double TeleportPosZ = this.OriginalPostion[2] - DistanceZ / this.DistanceFromTarget * this.speed;
                    this.OriginalPostion[0] = TeleportPosX;
                    this.OriginalPostion[1] = TeleportPosY;
                    this.OriginalPostion[2] = TeleportPosZ;
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(TeleportPosX, TeleportPosY, TeleportPosZ, false));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(TeleportPosX, TeleportPosY - 0.5, TeleportPosZ, false));
                    TPAura.Positions.add(new C03PacketPlayer.C04PacketPlayerPosition(TeleportPosX, TeleportPosY + 0.01, TeleportPosZ, true));
                }
            }
        }
        if (e instanceof EventRender3D) {
            if ((this.TargetPosition[0] == 0.0 && this.TargetPosition[1] == 0.0 && this.TargetPosition[2] == 0.0) || this.target == null) {
                return;
            }
            final double PosX = this.target.lastTickPosX + (this.target.posX - this.target.lastTickPosX) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().viewerPosX;
            final double PosY = this.target.lastTickPosY + (this.target.posY - this.target.lastTickPosY) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().viewerPosY;
            final double PosZ = this.target.lastTickPosZ + (this.target.posZ - this.target.lastTickPosZ) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().viewerPosZ;
            RenderUtils.drawEntityESP(PosX, PosY, PosZ, 0.36000001430511475, 1.940000057220459, 1.0f, 1.0f, 1.0f, 0.5f);
        }
    }
}
