package com.polarware.module.impl.combat;

import com.polarware.Client;
import com.polarware.component.impl.player.PingSpoofComponent;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.RealyPlayerTick;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.event.impl.render.Render3DEvent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.render.TargetEsp;
import com.polarware.util.animation.Animation;
import com.polarware.util.animation.Easing;
import com.polarware.util.render.ColorUtil;
import com.polarware.util.render.RenderUtil;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.play.server.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import util.time.StopWatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Esound(and made better by nyghtfull)
 * @since ??/??/??
 */
@ModuleInfo(name = "BackTrack 3", category = Category.COMBAT, description = "Allows you to attack enemies in their previous location")
public final class Backtrack3 extends Module {
    //TODO: OPTIMIZE THE FUCKING CODE !!!
    public boolean aBoolean = false;
    private int count = 0;
    private final StopWatch timeHelper = new StopWatch();
    public NumberValue hitRange = new NumberValue("Max Range", this, 6.0, 3.0, 6.0, 0.1);
    public NumberValue timerDelay = new NumberValue("Max Spoof Delay", this, 600.0, 0.0, 10000.0, 0.1);
    public BooleanValue esp = new BooleanValue("Render ESP", this, true);
    public BooleanValue aura = new BooleanValue("Only With Aura", this, true);
    public BooleanValue onlyWhenNeed = new BooleanValue("Only With Low Distance", this, true);
    public BooleanValue velocity = new BooleanValue("Velocity", this, true);
    public BooleanValue packetVelocityExplosion = new BooleanValue("ExplosionVelocity", this, true);

    public BooleanValue packetTimeUpdate = new BooleanValue("TimeUpdate", this, true);

    public BooleanValue packetKeepAlive = new BooleanValue("KeepAlive", this, true);
    public BooleanValue player = new BooleanValue("Player", this, true);

    public BooleanValue mob = new BooleanValue("Mob", this, true);

    public BooleanValue animal = new BooleanValue("Animal", this, true);

    public BooleanValue villager = new BooleanValue("Villager", this, true);

    public BooleanValue armorStand = new BooleanValue("ArmorStand", this, true);
    private EntityLivingBase entity = null;

    private boolean blockPackets;

    private WorldClient lastWorld;

    private INetHandler packetListener = null;
    private final ArrayList<Packet> packets = new ArrayList<>();

    public AxisAlignedBB boundingBox;

    public boolean b;

    public boolean bb;


    @Override
    protected void onEnable() {
        this.blockPackets = false;
        this.b = true;
        if (mc.theWorld != null && mc.thePlayer != null) {
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                    entityLivingBase.realPosX = entityLivingBase.serverPosX;
                    entityLivingBase.realPosZ = entityLivingBase.serverPosZ;
                    entityLivingBase.realPosY = entityLivingBase.serverPosY;
                }
            }
        }
    }
    @Override
    protected void onDisable() {
        if (this.packets.size() > 0 && this.packetListener != null) {
            this.resetPackets(this.packetListener);
        }

        this.packets.clear();
    }

    @EventLink()
    public final Listener<RealyPlayerTick> onPlayerUpdate = event -> {
        if (Client.INSTANCE.getModuleManager().get(KillAuraModule.class).isEnabled()) {
            this.entity = (EntityLivingBase) KillAuraModule.target;
        } else {
            Object[] listOfTargets = mc.theWorld.loadedEntityList.stream().filter(this::canAttacked).sorted(Comparator.comparingDouble((entityy) -> (double) mc.thePlayer.getDistanceToEntity(entityy))).toArray();
            if (listOfTargets.length > 0) {
                this.entity = (EntityLivingBase) listOfTargets[0];
            }

            if (this.aura.getValue()) {
                this.entity = null;
            }
        }

        if (this.entity != null && mc.thePlayer != null && this.packetListener != null && mc.theWorld != null) {
            double d0 = (double) this.entity.realPosX / 32.0D;
            double d1 = (double) this.entity.realPosY / 32.0D;
            double d2 = (double) this.entity.realPosZ / 32.0D;
            double d3 = (double) this.entity.serverPosX / 32.0D;
            double d4 = (double) this.entity.serverPosY / 32.0D;
            double d5 = (double) this.entity.serverPosZ / 32.0D;
            float f = this.entity.width / 2.0F;
            AxisAlignedBB entityServerPos = new AxisAlignedBB(d3 - (double) f, d4, d5 - (double) f, d3 + (double) f, d4 + (double) this.entity.height, d5 + (double) f);
            Vec3 positionEyes = mc.thePlayer.getPositionEyes(mc.getTimer().renderPartialTicks);
            double currentX = MathHelper.clamp_double(positionEyes.xCoord, entityServerPos.minX, entityServerPos.maxX);
            double currentY = MathHelper.clamp_double(positionEyes.yCoord, entityServerPos.minY, entityServerPos.maxY);
            double currentZ = MathHelper.clamp_double(positionEyes.zCoord, entityServerPos.minZ, entityServerPos.maxZ);
            AxisAlignedBB entityPosMe = new AxisAlignedBB(d0 - (double) f, d1, d2 - (double) f, d0 + (double) f, d1 + (double) this.entity.height, d2 + (double) f);
            double realX = MathHelper.clamp_double(positionEyes.xCoord, entityPosMe.minX, entityPosMe.maxX);
            double realY = MathHelper.clamp_double(positionEyes.yCoord, entityPosMe.minY, entityPosMe.maxY);
            double realZ = MathHelper.clamp_double(positionEyes.zCoord, entityPosMe.minZ, entityPosMe.maxZ);
            double distance = this.hitRange.getValue().doubleValue();
            if (!mc.thePlayer.canEntityBeSeen(this.entity)) {
                distance = Math.min(distance, 3.0D);
            }

            double collision = this.entity.getCollisionBorderSize();
            double width = mc.thePlayer.width / 2.0F;
            //prob gona breaker cuz the rot inc thingy
            double mePosXForPlayer = mc.thePlayer.getLastServerPosition().xCoord + (mc.thePlayer.getSeverPosition().xCoord - mc.thePlayer.getLastServerPosition().xCoord) / (double) MathHelper.clamp_int(mc.thePlayer.rotIncrement, 1, 3);
            double mePosYForPlayer = mc.thePlayer.getLastServerPosition().yCoord + (mc.thePlayer.getSeverPosition().yCoord - mc.thePlayer.getLastServerPosition().yCoord) / (double) MathHelper.clamp_int(mc.thePlayer.rotIncrement, 1, 3);
            double mePosZForPlayer = mc.thePlayer.getLastServerPosition().zCoord + (mc.thePlayer.getSeverPosition().zCoord - mc.thePlayer.getLastServerPosition().zCoord) / (double) MathHelper.clamp_int(mc.thePlayer.rotIncrement, 1, 3);
            AxisAlignedBB mePosForPlayerBox = new AxisAlignedBB(mePosXForPlayer - width, mePosYForPlayer, mePosZForPlayer - width, mePosXForPlayer + width, mePosYForPlayer + (double) mc.thePlayer.height, mePosZForPlayer + width);
            mePosForPlayerBox = mePosForPlayerBox.expand(collision, collision, collision);
            Vec3 entityPosEyes = new Vec3(d3, d4 + (double) this.entity.getEyeHeight(), d5);
            double bestX = MathHelper.clamp_double(entityPosEyes.xCoord, mePosForPlayerBox.minX, mePosForPlayerBox.maxX);
            double bestY = MathHelper.clamp_double(entityPosEyes.yCoord, mePosForPlayerBox.minY, mePosForPlayerBox.maxY);
            double bestZ = MathHelper.clamp_double(entityPosEyes.zCoord, mePosForPlayerBox.minZ, mePosForPlayerBox.maxZ);
            boolean b = entityPosEyes.distanceTo(new Vec3(bestX, bestY, bestZ)) > 3.0D || mc.thePlayer.hurtTime < 8 && mc.thePlayer.hurtTime > 3;

            if (!this.onlyWhenNeed.getValue()) {
                b = true;
            }

            if (b && positionEyes.distanceTo(new Vec3(realX, realY, realZ)) > positionEyes.distanceTo(new Vec3(currentX, currentY, currentZ)) && mc.thePlayer.getSeverPosition().distanceTo(new Vec3(d0, d1, d2)) < distance && !this.timeHelper.finished(this.timerDelay.getValue().longValue())) {
                this.blockPackets = true;
            } else {
                this.blockPackets = false;
                this.resetPackets(this.packetListener);
                this.timeHelper.reset();
            }
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
       // if (event.getDirection() == EnumPacketDirection.CLIENTBOUND) {
        if (event.getNetHandler() != null) {
            this.packetListener = event.getNetHandler();
        }

        if (event.getDirection() == EnumPacketDirection.CLIENTBOUND) {
            Packet p = event.getPacket();
            if (p instanceof S08PacketPlayerPosLook) {
                this.resetPackets(event.getNetHandler());
            }

            Entity entity1;
            EntityLivingBase entityLivingBase;
            if (p instanceof S14PacketEntity) {
                S14PacketEntity packet = (S14PacketEntity) p;
                entity1 = mc.theWorld.getEntityByID(packet.getEntityId());
                if (entity1 instanceof EntityLivingBase) {
                    entityLivingBase = (EntityLivingBase) entity1;
                    entityLivingBase.realPosX += packet.getPosX();
                    entityLivingBase.realPosY += packet.getPosY();
                    entityLivingBase.realPosZ += packet.getPosZ();
                }
            }

            if (p instanceof S18PacketEntityTeleport) {
                S18PacketEntityTeleport packet = (S18PacketEntityTeleport) p;
                entity1 = mc.theWorld.getEntityByID(packet.getEntityId());
                if (entity1 instanceof EntityLivingBase) {
                    entityLivingBase = (EntityLivingBase) entity1;
                    entityLivingBase.realPosX = packet.getX();
                    entityLivingBase.realPosY = packet.getY();
                    entityLivingBase.realPosZ = packet.getZ();
                }
            }

            if (this.entity == null) {
                this.resetPackets(event.getNetHandler());
            } else {
                if (mc.theWorld != null && mc.thePlayer != null) {
                    if (this.lastWorld != mc.theWorld) {
                        this.resetPackets(event.getNetHandler());
                        this.lastWorld = mc.theWorld;
                        return;
                    }

                    this.addPackets(p, event);
                }

                this.lastWorld = mc.theWorld;
            }
        }
     //   }
    };

    private boolean canAttacked(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            if (entity.isInvisible()) {
                return false;
            }

            if (((EntityLivingBase) entity).deathTime > 1) {
                return false;
            }

            if (entity instanceof EntityArmorStand && !this.armorStand.getValue()) {
                return false;
            }

            if (entity instanceof EntityAnimal && !this.animal.getValue()) {
                return false;
            }

            if (entity instanceof EntityMob && !this.mob.getDefaultValue()) {
                return false;
            }

            if (entity instanceof EntityPlayer && !this.player.getValue()) {
                return false;
            }

            if (entity instanceof EntityVillager && !this.villager.getValue()) {
                return false;
            }

            if (entity.ticksExisted < 50) {
                return false;
            }
            //todo: do the teams thingy
           // if (entity instanceof EntityPlayer && mm.teams.isToggled() && mm.teams.getTeammates().contains(entity)) {
            //                return false;
            //            }

            //if (entity instanceof EntityPlayer && (entity.getDisplayName().equals("§aShop") || entity.getName().equals("SHOP") || entity.getName().equals("UPGRADES"))) {
            //                return false;
            //            }

            if (entity.isDead) {
                return false;
            }

            //if (entity instanceof EntityPlayer && mm.antiBot.isToggled() && AntiBot.bots.contains(entity)) {
            //                return false;
            //            }
            //
            //            if (entity instanceof EntityPlayer && !mm.midClick.noFiends && MidClick.friends.contains(entity.getName())) {
            //                return false;
            //            }
        }

        return entity instanceof EntityLivingBase && entity != mc.thePlayer && (double) mc.thePlayer.getDistanceToEntity(entity) < this.hitRange.getValue().doubleValue();
    }


    @EventLink
    private final Listener<Render3DEvent> onRender = event -> {
        if (this.esp.getValue()) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            GlStateManager.disableCull();
            GL11.glDepthMask(false);
            if (this.entity != null && this.blockPackets) {
                this.render(this.entity);
            }

            GL11.glDepthMask(true);
            GlStateManager.enableCull();
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2848);
        }
    };

    private void render(EntityLivingBase entity) {
        float lineWidth = 3.0F;
        count++;
        if (mc.thePlayer.getDistanceToEntity(entity) > 1.0F) {
            double d0 = 1.0F - mc.thePlayer.getDistanceToEntity(entity) / 20.0F;
            if (d0 < 0.3D) {
                d0 = 0.3D;
            }

            lineWidth = (float) ((double) lineWidth * d0);
        }

        RenderUtil.drawEntityServerESP(entity,ColorUtil.rainbow(count * 20, 0.6f, 3.5), 0.03137255f, 1.0f, lineWidth);
    }

    private void resetPackets(INetHandler netHandler) {
        if (!this.packets.isEmpty()) {
            while (!this.packets.isEmpty()) {
                final Packet packet = this.packets.get(0);
                try {
                    if (packet != null) {
                        packet.processPacket(netHandler);
                    }
                } catch (ThreadQuickExitException ex) {
                }
                this.packets.remove(0);
            }
        }
    }

    private void addPackets(Packet packet, PacketReceiveEvent eventReadPacket) {
        synchronized (this.packets) {
            if (this.delayPackets(packet)) {
                this.aBoolean = true;
                this.packets.add(packet);
                eventReadPacket.setCancelled(true);
            }
        }
    }

    private boolean delayPackets(Packet packet) {
        if (mc.currentScreen != null) {
            return false;
        } else if (packet instanceof S03PacketTimeUpdate) {
            return this.packetTimeUpdate.getValue();
        } else if (packet instanceof S00PacketKeepAlive) {
            return this.packetKeepAlive.getValue();
        } else if (packet instanceof S12PacketEntityVelocity) {
            return this.velocity.getValue();
        } else if (packet instanceof S27PacketExplosion) {
            return this.packetVelocityExplosion.getValue();
        } else if (packet instanceof S19PacketEntityStatus) {
            S19PacketEntityStatus entityStatus = (S19PacketEntityStatus) packet;
            return entityStatus.getOpCode() != 2 || !(mc.theWorld.getEntityByID(entityStatus.getEntityId()) instanceof EntityLivingBase);
        } else {
            return !(packet instanceof S06PacketUpdateHealth) && !(packet instanceof S29PacketSoundEffect) && !(packet instanceof S3EPacketTeams) && !(packet instanceof S0CPacketSpawnPlayer);
        }
    }
}