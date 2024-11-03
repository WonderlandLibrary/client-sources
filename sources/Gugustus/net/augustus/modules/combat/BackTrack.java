package net.augustus.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import net.augustus.events.EventEarlyTick;
import net.augustus.events.EventReadPacket;
import net.augustus.events.EventRender3D;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.modules.combat.AntiBot;
import net.augustus.modules.misc.MidClick;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.BooleansSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.Setting;
import net.augustus.utils.RenderUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
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
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

public class BackTrack
extends Module {
    private final TimeHelper timeHelper = new TimeHelper();
    private final ArrayList<Packet> packets = new ArrayList();
    public AxisAlignedBB boundingBox;
    public boolean b;
    public boolean bb;
    public boolean aBoolean;
    public DoubleValue hitRange = new DoubleValue(6, "MaxHitRange", this, 6.0, 3.0, 10.0, 2);
    public DoubleValue timerDelay = new DoubleValue(7, "Time", this, 4000.0, 0.0, 80000.0, 0);
    public BooleanValue playerESP = new BooleanValue(14, "PlayerESP", this, true);
    public BooleanValue boxESP = new BooleanValue(14, "ESP", this, true);
    public BooleansSetting esp = new BooleansSetting(31, "ESPMode", this, new Setting[]{this.playerESP, this.boxESP});

    public BooleanValue onlyWhenNeed = new BooleanValue(19, "OnlyWhenNeed", this, true);
    public BooleanValue player = new BooleanValue(9, "Player", this, true);
    public BooleanValue mob = new BooleanValue(10, "Mob", this, true);
    public BooleanValue animal = new BooleanValue(11, "Animal", this, true);
    public BooleanValue villager = new BooleanValue(12, "Villager", this, true);
    public BooleanValue armorStand = new BooleanValue(13, "ArmorStand", this, true);
    public BooleansSetting targets = new BooleansSetting(31, "Targets", this, new Setting[]{this.player, this.mob, this.animal, this.villager, this.armorStand});
    public BooleanValue onlyKillAura = new BooleanValue(18, "OnlyKillAura", this, true);
    public DoubleValue range = new DoubleValue(5, "PreAimRange", this, 4.0, 0.0, 15.0, 1);
    public BooleanValue packetVelocity = new BooleanValue(14, "Velocity", this, true);
    public BooleanValue packetVelocityExplosion = new BooleanValue(15, "ExplosionVelocity", this, true);
    public BooleanValue packetTimeUpdate = new BooleanValue(16, "TimeUpdate", this, true);
    public BooleanValue packetKeepAlive = new BooleanValue(17, "KeepAlive", this, true);
    public BooleansSetting packetsToDelay = new BooleansSetting(32, "PacketsToDelay", this, new Setting[]{this.packetVelocity, this.packetVelocityExplosion, this.packetTimeUpdate, this.packetKeepAlive});
    private EntityLivingBase entity = null;
    private boolean blockPackets;
    private WorldClient lastWorld;
    private INetHandler packetListener = null;

    public BackTrack() {
        super("BackTrack", new Color(110, 186, 9), Categorys.COMBAT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.blockPackets = false;
        this.b = true;
        if (BackTrack.mc.theWorld != null && BackTrack.mc.thePlayer != null) {
            for (Entity entity : BackTrack.mc.theWorld.loadedEntityList) {
                if (!(entity instanceof EntityLivingBase)) continue;
                EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                entityLivingBase.realPosX = entityLivingBase.serverPosX;
                entityLivingBase.realPosZ = entityLivingBase.serverPosZ;
                entityLivingBase.realPosY = entityLivingBase.serverPosY;
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (this.packets.size() > 0 && this.packetListener != null) {
            this.resetPackets(this.packetListener);
        }
        this.packets.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    @EventTarget
    public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
        if (BackTrack.mm.killAura.isToggled()) {
            this.entity = BackTrack.mm.killAura.target;
        } else {
            Object[] listOfTargets = BackTrack.mc.theWorld.loadedEntityList.stream().filter(this::canAttacked).sorted(Comparator.comparingDouble(entityy -> BackTrack.mc.thePlayer.getDistanceToEntity((Entity)entityy))).toArray();
            if (listOfTargets.length > 0) {
                this.entity = (EntityLivingBase)listOfTargets[0];
            }
            if (this.onlyKillAura.getBoolean()) {
                this.entity = null;
            }
        }
        if (this.entity == null) return;
        if (BackTrack.mc.thePlayer == null) return;
        if (this.packetListener == null) return;
        if (BackTrack.mc.theWorld == null) return;
        double d0 = (double)this.entity.realPosX / 32.0;
        double d1 = (double)this.entity.realPosY / 32.0;
        double d2 = (double)this.entity.realPosZ / 32.0;
        double d3 = (double)this.entity.serverPosX / 32.0;
        double d4 = (double)this.entity.serverPosY / 32.0;
        double d5 = (double)this.entity.serverPosZ / 32.0;
        float f2 = this.entity.width / 2.0f;
        AxisAlignedBB entityServerPos = new AxisAlignedBB(d3 - (double)f2, d4, d5 - (double)f2, d3 + (double)f2, d4 + (double)this.entity.height, d5 + (double)f2);
        Vec3 positionEyes = BackTrack.mc.thePlayer.getPositionEyes(BackTrack.mc.getTimer().renderPartialTicks);
        double currentX = MathHelper.clamp_double(positionEyes.xCoord, entityServerPos.minX, entityServerPos.maxX);
        double currentY = MathHelper.clamp_double(positionEyes.yCoord, entityServerPos.minY, entityServerPos.maxY);
        double currentZ = MathHelper.clamp_double(positionEyes.zCoord, entityServerPos.minZ, entityServerPos.maxZ);
        AxisAlignedBB entityPosMe = new AxisAlignedBB(d0 - (double)f2, d1, d2 - (double)f2, d0 + (double)f2, d1 + (double)this.entity.height, d2 + (double)f2);
        double realX = MathHelper.clamp_double(positionEyes.xCoord, entityPosMe.minX, entityPosMe.maxX);
        double realY = MathHelper.clamp_double(positionEyes.yCoord, entityPosMe.minY, entityPosMe.maxY);
        double realZ = MathHelper.clamp_double(positionEyes.zCoord, entityPosMe.minZ, entityPosMe.maxZ);
        double distance = this.hitRange.getValue();
        if (!BackTrack.mc.thePlayer.canEntityBeSeen(this.entity)) {
            distance = distance > 3.0 ? 3.0 : distance;
        }
        double collision = this.entity.getCollisionBorderSize();
        double width = BackTrack.mc.thePlayer.width / 2.0f;
        double mePosXForPlayer = BackTrack.mc.thePlayer.getLastServerPosition().xCoord + (BackTrack.mc.thePlayer.getSeverPosition().xCoord - BackTrack.mc.thePlayer.getLastServerPosition().xCoord) / (double)MathHelper.clamp_int(BackTrack.mc.thePlayer.rotIncrement, 1, 3);
        double mePosYForPlayer = BackTrack.mc.thePlayer.getLastServerPosition().yCoord + (BackTrack.mc.thePlayer.getSeverPosition().yCoord - BackTrack.mc.thePlayer.getLastServerPosition().yCoord) / (double)MathHelper.clamp_int(BackTrack.mc.thePlayer.rotIncrement, 1, 3);
        double mePosZForPlayer = BackTrack.mc.thePlayer.getLastServerPosition().zCoord + (BackTrack.mc.thePlayer.getSeverPosition().zCoord - BackTrack.mc.thePlayer.getLastServerPosition().zCoord) / (double)MathHelper.clamp_int(BackTrack.mc.thePlayer.rotIncrement, 1, 3);
        AxisAlignedBB mePosForPlayerBox = new AxisAlignedBB(mePosXForPlayer - width, mePosYForPlayer, mePosZForPlayer - width, mePosXForPlayer + width, mePosYForPlayer + (double)BackTrack.mc.thePlayer.height, mePosZForPlayer + width);
        mePosForPlayerBox = mePosForPlayerBox.expand(collision, collision, collision);
        Vec3 entityPosEyes = new Vec3(d3, d4 + (double)this.entity.getEyeHeight(), d5);
        double bestX = MathHelper.clamp_double(entityPosEyes.xCoord, mePosForPlayerBox.minX, mePosForPlayerBox.maxX);
        double bestY = MathHelper.clamp_double(entityPosEyes.yCoord, mePosForPlayerBox.minY, mePosForPlayerBox.maxY);
        double bestZ = MathHelper.clamp_double(entityPosEyes.zCoord, mePosForPlayerBox.minZ, mePosForPlayerBox.maxZ);
        boolean b2 = false;
        if (entityPosEyes.distanceTo(new Vec3(bestX, bestY, bestZ)) > 3.0 || BackTrack.mc.thePlayer.hurtTime < 8 && BackTrack.mc.thePlayer.hurtTime > 3) {
            b2 = true;
        }
        if (!this.onlyWhenNeed.getBoolean()) {
            b2 = true;
        }
        if (b2) {
            Vec3 vec3 = new Vec3(realX, realY, realZ);
            Vec3 vec32 = new Vec3(currentX, currentY, currentZ);
            if (positionEyes.distanceTo(vec3) > positionEyes.distanceTo(vec32)) {
                Vec3 vec33 = new Vec3(d0, d1, d2);
                if (BackTrack.mc.thePlayer.getSeverPosition().distanceTo(vec33) < distance && !this.timeHelper.reached((long)this.timerDelay.getValue())) {
                    this.blockPackets = true;
                    return;
                }
            }
        }
        this.blockPackets = false;
        this.resetPackets(this.packetListener);
        this.timeHelper.reset();
    }

    @EventTarget
    public synchronized void onEventReadPacket(EventReadPacket eventReadPacket) {
        EntityLivingBase entityLivingBase;
        Packet<INetHandlerPlayClient> packet;
        Entity entity1;
        if (eventReadPacket.getNetHandler() != null) {
            this.packetListener = eventReadPacket.getNetHandler();
        }
        if (eventReadPacket.getDirection() != EnumPacketDirection.CLIENTBOUND) {
            return;
        }
        Packet<?> p2 = eventReadPacket.getPacket();
        if (p2 instanceof S08PacketPlayerPosLook) {
            this.resetPackets(eventReadPacket.getNetHandler());
        }
        if (p2 instanceof S14PacketEntity && (entity1 = BackTrack.mc.theWorld.getEntityByID(((S14PacketEntity)(packet = (S14PacketEntity)p2)).getEntityId())) instanceof EntityLivingBase) {
            entityLivingBase = (EntityLivingBase)entity1;
            entityLivingBase.realPosX += ((S14PacketEntity)packet).func_149062_c();
            entityLivingBase.realPosY += ((S14PacketEntity)packet).func_149061_d();
            entityLivingBase.realPosZ += ((S14PacketEntity)packet).func_149064_e();
        }
        if (p2 instanceof S18PacketEntityTeleport && (entity1 = BackTrack.mc.theWorld.getEntityByID(((S18PacketEntityTeleport)(packet = (S18PacketEntityTeleport)p2)).getEntityId())) instanceof EntityLivingBase) {
            entityLivingBase = (EntityLivingBase)entity1;
            entityLivingBase.realPosX = ((S18PacketEntityTeleport)packet).getX();
            entityLivingBase.realPosY = ((S18PacketEntityTeleport)packet).getY();
            entityLivingBase.realPosZ = ((S18PacketEntityTeleport)packet).getZ();
        }
        if (this.entity == null) {
            this.resetPackets(eventReadPacket.getNetHandler());
            return;
        }
        if (BackTrack.mc.theWorld != null && BackTrack.mc.thePlayer != null) {
            if (this.lastWorld != BackTrack.mc.theWorld) {
                this.resetPackets(eventReadPacket.getNetHandler());
                this.lastWorld = BackTrack.mc.theWorld;
                return;
            }
            this.addPackets(p2, eventReadPacket);
        }
        this.lastWorld = BackTrack.mc.theWorld;
    }

    private boolean canAttacked(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            if (entity.isInvisible()) {
                return false;
            }
            if (((EntityLivingBase)entity).deathTime > 1) {
                return false;
            }
            if (entity instanceof EntityArmorStand && !this.armorStand.getBoolean()) {
                return false;
            }
            if (entity instanceof EntityAnimal && !this.animal.getBoolean()) {
                return false;
            }
            if (entity instanceof EntityMob && !this.mob.getBoolean()) {
                return false;
            }
            if (entity instanceof EntityPlayer && !this.player.getBoolean()) {
                return false;
            }
            if (entity instanceof EntityVillager && !this.villager.getBoolean()) {
                return false;
            }
            if (entity.ticksExisted < 50) {
                return false;
            }
            if (entity instanceof EntityPlayer && BackTrack.mm.teams.isToggled() && BackTrack.mm.teams.getTeammates().contains(entity)) {
                return false;
            }
            if (entity instanceof EntityPlayer && (entity.getName().equals("\u00a7aShop") || entity.getName().equals("SHOP") || entity.getName().equals("UPGRADES"))) {
                return false;
            }
            if (entity.isDead) {
                return false;
            }
            if (entity instanceof EntityPlayer && BackTrack.mm.antiBot.isToggled() && AntiBot.bots.contains(entity)) {
                return false;
            }
            if (entity instanceof EntityPlayer && !BackTrack.mm.midClick.noFiends && MidClick.friends.contains(entity.getName())) {
                return false;
            }
        }
        return entity instanceof EntityLivingBase && entity != BackTrack.mc.thePlayer && (double)BackTrack.mc.thePlayer.getDistanceToEntity(entity) < this.range.getValue();
    }

    @EventTarget
    public void onEventRender3D(EventRender3D eventRender3D) {
        if (this.boxESP.getBoolean()) {
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
        if(playerESP.getBoolean() && this.entity != null && this.blockPackets) {
            float f = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * eventRender3D.getPartialTicks();
            EntityOtherPlayerMP f1 = new EntityOtherPlayerMP(this.mc.theWorld, new GameProfile(EntityPlayer.getUUID(mc.thePlayer.getGameProfile()),  ""));
            f1.setPosition( entity.realPosX / 32D,  entity.realPosY / 32D,  entity.realPosZ / 32D);
            f1.inventory = ((EntityOtherPlayerMP)entity).inventory;
            f1.inventoryContainer = ((EntityOtherPlayerMP)entity).inventoryContainer;
            f1.getHeldItem();
            f1.rotationYawHead =entity.rotationYawHead;
            f1.rotationYaw = entity.rotationYaw;
            f1.rotationPitch = entity.rotationPitch;
            f1.rotationPitchHead = entity.rotationPitchHead;
            f1.prevRotationPitch = entity.prevRotationPitch;
            f1.prevRotationPitchHead = entity.prevRotationPitchHead;
            f1.prevRotationYaw = entity.prevRotationYaw;
            f1.prevRotationYawHead = entity.prevRotationYawHead;
            f1.attackedAtYaw = entity.attackedAtYaw;
            f1.fallDistance = entity.fallDistance;
            f1.isBurning();
            f1.isSprinting();
            f1.swingItem();
            f1.isBlocking();
            f1.isEating();
            f1.isEntityAlive();
            f1.isInLava();
            f1.isInWater();
            f1.isWet();
            f1.isSneaking();
            
            this.mc.theWorld.addEntityToWorld(-42069, f1);
                this.render(this.entity);
             } else {
                 this.mc.theWorld.removeEntityFromWorld(-42069);

        }
     }    

    private void render(EntityLivingBase entity) {
        float red = 0.0f;
        float green = 1.1333333f;
        float blue = 0.0f;
        float lineWidth = 3.0f;
        float alpha = 0.03137255f;
        if (BackTrack.mc.thePlayer.getDistanceToEntity(entity) > 1.0f) {
            double d0 = 1.0f - BackTrack.mc.thePlayer.getDistanceToEntity(entity) / 20.0f;
            if (d0 < 0.3) {
                d0 = 0.3;
            }
            lineWidth = (float)((double)lineWidth * d0);
        }
        RenderUtil.drawEntityServerESP(entity, red, green, blue, alpha, 1.0f, lineWidth);
    }

    private void resetPackets(INetHandler netHandler) {
        if (this.packets.size() > 0) {
            while (this.packets.size() != 0) {
                Packet packet = this.packets.get(0);
                try {
                    if (packet != null) {
                        if (BackTrack.mm.velocity.isToggled() && (BackTrack.mm.velocity.mode.getSelected().equals("Spoof") || BackTrack.mm.velocity.mode.getSelected().equals("Basic") && BackTrack.mm.velocity.XZValue.getValue() == 0.0 && BackTrack.mm.velocity.YValue.getValue() == 0.0)) {
                            if (!(packet instanceof S12PacketEntityVelocity || packet instanceof S27PacketExplosion && BackTrack.mm.velocity.ignoreExplosion.getBoolean() && BackTrack.mm.velocity.mode.getSelected().equals("Basic"))) {
                                packet.processPacket(netHandler);
                            }
                        } else {
                            packet.processPacket(netHandler);
                        }
                    }
                } catch (ThreadQuickExitException threadQuickExitException) {
                    // empty catch block
                }
                this.packets.remove(this.packets.get(0));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addPackets(Packet packet, EventReadPacket eventReadPacket) {
        ArrayList<Packet> arrayList = this.packets;
        synchronized (arrayList) {
            if (this.delayPackets(packet)) {
                this.aBoolean = true;
                this.packets.add(packet);
                eventReadPacket.setCanceled(true);
            }
        }
    }

    private boolean delayPackets(Packet packet) {
        if (BackTrack.mc.currentScreen != null) {
            return false;
        }
        if (packet instanceof S03PacketTimeUpdate) {
            return this.packetTimeUpdate.getBoolean();
        }
        if (packet instanceof S00PacketKeepAlive) {
            return this.packetKeepAlive.getBoolean();
        }
        if (packet instanceof S12PacketEntityVelocity) {
            return this.packetVelocity.getBoolean();
        }
        if (packet instanceof S27PacketExplosion) {
            return this.packetVelocityExplosion.getBoolean();
        }
        if (packet instanceof S19PacketEntityStatus) {
            S19PacketEntityStatus entityStatus = (S19PacketEntityStatus)packet;
            return entityStatus.getOpCode() != 2 || !(BackTrack.mc.theWorld.getEntityByID(entityStatus.getEntityId()) instanceof EntityLivingBase);
        }
        return !(packet instanceof S06PacketUpdateHealth) && !(packet instanceof S29PacketSoundEffect) && !(packet instanceof S3EPacketTeams) && !(packet instanceof S0CPacketSpawnPlayer);
    }
}

