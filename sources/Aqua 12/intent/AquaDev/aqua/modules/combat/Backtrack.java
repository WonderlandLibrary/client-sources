// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.combat;

import intent.AquaDev.aqua.utils.RenderUtil;
import java.util.Iterator;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.AxisAlignedBB;
import events.listeners.EventTick;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.EnumPacketDirection;
import events.listeners.EventPacket;
import events.listeners.EventRenderNameTags;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.INetHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import java.util.ArrayList;
import intent.AquaDev.aqua.modules.Module;

public class Backtrack extends Module
{
    private final ArrayList<Packet> packets;
    private EntityLivingBase entity;
    public static EntityPlayer target;
    private boolean blockPackets;
    private INetHandler packetListener;
    private WorldClient lastWorld;
    private final TimeUtil timeUtil;
    
    public Backtrack() {
        super("Backtrack", Type.Combat, "Backtrack", 0, Category.Combat);
        this.packets = new ArrayList<Packet>();
        this.entity = null;
        this.packetListener = null;
        this.timeUtil = new TimeUtil();
        Aqua.setmgr.register(new Setting("Range", this, 6.0, 3.0, 6.0, false));
        Aqua.setmgr.register(new Setting("BacktrackMS", this, 1000.0, 50.0, 2000.0, false));
    }
    
    @Override
    public void onEnable() {
        this.blockPackets = false;
        this.packets.clear();
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        this.packets.clear();
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventRenderNameTags) {
            this.render(Killaura.target);
        }
        if (event instanceof EventPacket) {
            final EventPacket eventPacket = (EventPacket)event;
            if (eventPacket.getDirection() != EnumPacketDirection.CLIENTBOUND) {
                return;
            }
            this.packetListener = eventPacket.getNetHandler();
            final Packet p = EventPacket.getPacket();
            if (p instanceof S14PacketEntity) {
                final S14PacketEntity packet = (S14PacketEntity)EventPacket.getPacket();
                final Entity entity1 = Backtrack.mc.theWorld.getEntityByID(packet.getEntityId());
                if (entity1 instanceof EntityLivingBase) {
                    final EntityLivingBase entityLivingBase2;
                    final EntityLivingBase entityLivingBase = entityLivingBase2 = (EntityLivingBase)entity1;
                    entityLivingBase2.realPosX += packet.func_149062_c();
                    final EntityLivingBase entityLivingBase3 = entityLivingBase;
                    entityLivingBase3.realPosY += packet.func_149061_d();
                    final EntityLivingBase entityLivingBase4 = entityLivingBase;
                    entityLivingBase4.realPosZ += packet.func_149064_e();
                }
            }
            if (p instanceof S18PacketEntityTeleport) {
                final S18PacketEntityTeleport packet2 = (S18PacketEntityTeleport)EventPacket.getPacket();
                final Entity entity1 = Backtrack.mc.theWorld.getEntityByID(packet2.getEntityId());
                if (entity1 instanceof EntityLivingBase) {
                    final EntityLivingBase entityLivingBase = (EntityLivingBase)entity1;
                    entityLivingBase.realPosX = packet2.getX();
                    entityLivingBase.realPosY = packet2.getY();
                    entityLivingBase.realPosZ = packet2.getZ();
                }
            }
            this.entity = Backtrack.target;
            if (this.entity == null) {
                this.resetPackets(eventPacket.getNetHandler());
                return;
            }
            if (Backtrack.mc.theWorld != null && Backtrack.mc.thePlayer != null) {
                if (this.lastWorld != Backtrack.mc.theWorld) {
                    this.resetPackets(eventPacket.getNetHandler());
                    this.lastWorld = Backtrack.mc.theWorld;
                    return;
                }
                this.addPackets(p, eventPacket);
            }
            this.lastWorld = Backtrack.mc.theWorld;
        }
        if (event instanceof EventTick) {
            if (Aqua.moduleManager.getModuleByName("Killaura").isToggled()) {
                Backtrack.target = this.searchTargets();
            }
            else {
                Backtrack.target = null;
            }
            if (Backtrack.mc.thePlayer != null && this.packetListener != null && Backtrack.mc.theWorld != null) {
                if (this.entity == null) {
                    this.resetPackets(this.packetListener);
                    return;
                }
                final double d0 = this.entity.realPosX / 32.0;
                final double d2 = this.entity.realPosY / 32.0;
                final double d3 = this.entity.realPosZ / 32.0;
                final double d4 = this.entity.serverPosX / 32.0;
                final double d5 = this.entity.serverPosY / 32.0;
                final double d6 = this.entity.serverPosZ / 32.0;
                final AxisAlignedBB alignedBB = new AxisAlignedBB(d4 - this.entity.width, d5, d6 - this.entity.width, d4 + this.entity.width, d5 + this.entity.height, d6 + this.entity.width);
                final Vec3 positionEyes = Backtrack.mc.thePlayer.getPositionEyes(Backtrack.mc.timer.renderPartialTicks);
                final double currentX = MathHelper.clamp_double(positionEyes.xCoord, alignedBB.minX, alignedBB.maxX);
                final double currentY = MathHelper.clamp_double(positionEyes.yCoord, alignedBB.minY, alignedBB.maxY);
                final double currentZ = MathHelper.clamp_double(positionEyes.zCoord, alignedBB.minZ, alignedBB.maxZ);
                final AxisAlignedBB alignedBB2 = new AxisAlignedBB(d0 - this.entity.width, d2, d3 - this.entity.width, d0 + this.entity.width, d2 + this.entity.height, d3 + this.entity.width);
                final double realX = MathHelper.clamp_double(positionEyes.xCoord, alignedBB2.minX, alignedBB2.maxX);
                final double realY = MathHelper.clamp_double(positionEyes.yCoord, alignedBB2.minY, alignedBB2.maxY);
                final double realZ = MathHelper.clamp_double(positionEyes.zCoord, alignedBB2.minZ, alignedBB2.maxZ);
                double distance = 6.0;
                if (!Backtrack.mc.thePlayer.canEntityBeSeen(this.entity)) {
                    distance = 3.0;
                }
                final double bestX = MathHelper.clamp_double(positionEyes.xCoord, this.entity.getEntityBoundingBox().minX, this.entity.getEntityBoundingBox().maxX);
                final double bestY = MathHelper.clamp_double(positionEyes.yCoord, this.entity.getEntityBoundingBox().minY, this.entity.getEntityBoundingBox().maxY);
                final double bestZ = MathHelper.clamp_double(positionEyes.zCoord, this.entity.getEntityBoundingBox().minZ, this.entity.getEntityBoundingBox().maxZ);
                boolean b = false;
                if (positionEyes.distanceTo(new Vec3(bestX, bestY, bestZ)) > 2.98) {
                    b = true;
                }
                final float delayMS = (float)Aqua.setmgr.getSetting("BacktrackBacktrackMS").getCurrentNumber();
                if (!b || positionEyes.distanceTo(new Vec3(realX, realY, realZ)) <= positionEyes.distanceTo(new Vec3(currentX, currentY, currentZ)) + 0.05 || Backtrack.mc.thePlayer.getDistance(d0, d2, d3) >= distance || this.timeUtil.hasReached((long)delayMS)) {
                    this.resetPackets(this.packetListener);
                    this.timeUtil.reset();
                }
            }
        }
    }
    
    private void resetPackets(final INetHandler netHandler) {
        if (this.packets.size() > 0) {
            while (this.packets.size() != 0) {
                try {
                    this.packets.get(0).processPacket(netHandler);
                }
                catch (ThreadQuickExitException ex) {}
                this.packets.remove(this.packets.get(0));
            }
        }
    }
    
    private void addPackets(final Packet packet, final EventPacket eventPacket) {
        synchronized (this.packets) {
            if (this.blockPacket(packet)) {
                this.packets.add(packet);
                eventPacket.setCancelled(true);
            }
        }
    }
    
    private boolean blockPacket(final Packet packet) {
        return packet instanceof S03PacketTimeUpdate || packet instanceof S00PacketKeepAlive || packet instanceof C00PacketKeepAlive || packet instanceof C0CPacketInput || packet instanceof S12PacketEntityVelocity || packet instanceof C01PacketEncryptionResponse || packet instanceof S27PacketExplosion || packet instanceof S32PacketConfirmTransaction || packet instanceof S14PacketEntity || packet instanceof S19PacketEntityHeadLook || packet instanceof S18PacketEntityTeleport || packet instanceof S0FPacketSpawnMob || packet instanceof S08PacketPlayerPosLook;
    }
    
    public EntityPlayer searchTargets() {
        final float range = (float)Aqua.setmgr.getSetting("BacktrackRange").getCurrentNumber();
        EntityPlayer player = null;
        double closestDist = 100000.0;
        for (final Entity o : Backtrack.mc.theWorld.loadedEntityList) {
            if (!o.getName().equals(Backtrack.mc.thePlayer.getName()) && o instanceof EntityPlayer && Backtrack.mc.thePlayer.getDistanceToEntity(o) < range) {
                final double dist = Backtrack.mc.thePlayer.getDistanceToEntity(o);
                if (dist >= closestDist) {
                    continue;
                }
                closestDist = dist;
                player = (EntityPlayer)o;
            }
        }
        return player;
    }
    
    private void render(final EntityLivingBase entity) {
        final float red = 0.0f;
        final float green = 1.1333333f;
        final float blue = 0.0f;
        float lineWidth = 3.0f;
        final float alpha = 0.03137255f;
        if (Backtrack.mc.thePlayer.getDistanceToEntity(entity) > 1.0f) {
            double d0 = 1.0f - Backtrack.mc.thePlayer.getDistanceToEntity(entity) / 20.0f;
            if (d0 < 0.3) {
                d0 = 0.3;
            }
            lineWidth *= (float)d0;
        }
        RenderUtil.drawEntityServerESP(entity, red, green, blue, alpha, 1.0f, 1.0f);
    }
    
    static {
        Backtrack.target = null;
    }
}
