package io.github.liticane.clients.feature.module.impl.combat;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketDevEvent;
import io.github.liticane.clients.feature.event.impl.other.RealPlayerTick;
import io.github.liticane.clients.feature.event.impl.render.Render3DEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.util.misc.TimerUtil;
import io.github.liticane.clients.util.player.ColorUtil;
import io.github.liticane.clients.util.render.RenderUtil;
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

import java.util.ArrayList;
import java.util.Comparator;

@Module.Info(name = "Backtrack", category = Module.Category.COMBAT)
public class Backtrack extends Module {
    //TODO: OPTIMIZE THE FUCKING CODE !!!
    public boolean aBoolean = false;
    private int count = 0;
    private final TimerUtil timeHelper = new TimerUtil();
    public NumberProperty hitRange = new NumberProperty("Max Range", this, 6.0, 3.0, 6.0, 0.1);
    public NumberProperty timerDelay = new NumberProperty("Max Spoof Delay", this, 600.0, 0.0, 10000.0, 0.1);
    public BooleanProperty esp = new BooleanProperty("Render ESP", this, true);
    public BooleanProperty aura = new BooleanProperty("Only With Aura", this, true);
    public BooleanProperty onlyWhenNeed = new BooleanProperty("Only With Low Distance", this, true);
    public BooleanProperty velocity = new BooleanProperty("Velocity", this, true);
    public BooleanProperty packetVelocityExplosion = new BooleanProperty("ExplosionVelocity", this, true);
    public BooleanProperty packetTimeUpdate = new BooleanProperty("TimeUpdate", this, true);

    public BooleanProperty packetKeepAlive = new BooleanProperty("KeepAlive", this, true);
    public BooleanProperty player = new BooleanProperty("Player", this, true);

    public BooleanProperty mob = new BooleanProperty("Mob", this, true);

    public BooleanProperty animal = new BooleanProperty("Animal", this, true);

    public BooleanProperty villager = new BooleanProperty("Villager", this, true);

    public BooleanProperty armorStand = new BooleanProperty("ArmorStand", this, true);
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
        if (mc.world != null && mc.player != null) {
            for (Entity entity : mc.world.loadedEntityList) {
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

    @SubscribeEvent
    public final EventListener<RealPlayerTick> onPlayerUpdate = event -> {
        this.setSuffix(timerDelay.getValue() + " ms");
        if (Client.INSTANCE.getModuleManager().get(Aura.class).isToggled()) {
            //maybe dont cast it
            this.entity = (EntityLivingBase)Aura.target;
        } else {
            Object[] listOfTargets = mc.world.loadedEntityList.stream().filter(this::canAttacked).sorted(Comparator.comparingDouble((entityy) -> (double) mc.player.getDistanceToEntity(entityy))).toArray();
            if (listOfTargets.length > 0) {
                this.entity = (EntityLivingBase) listOfTargets[0];
            }

            if (this.aura.isToggled()) {
                this.entity = null;
            }
        }

        if (this.entity != null && mc.player != null && this.packetListener != null && mc.world != null) {
            double d0 = (double) this.entity.realPosX / 32.0D;
            double d1 = (double) this.entity.realPosY / 32.0D;
            double d2 = (double) this.entity.realPosZ / 32.0D;
            double d3 = (double) this.entity.serverPosX / 32.0D;
            double d4 = (double) this.entity.serverPosY / 32.0D;
            double d5 = (double) this.entity.serverPosZ / 32.0D;
            float f = this.entity.width / 2.0F;
            AxisAlignedBB entityServerPos = new AxisAlignedBB(d3 - (double) f, d4, d5 - (double) f, d3 + (double) f, d4 + (double) this.entity.height, d5 + (double) f);
            Vec3 positionEyes = mc.player.getPositionEyes(mc.timer.renderPartialTicks);
            double currentX = MathHelper.clamp_double(positionEyes.xCoord, entityServerPos.minX, entityServerPos.maxX);
            double currentY = MathHelper.clamp_double(positionEyes.yCoord, entityServerPos.minY, entityServerPos.maxY);
            double currentZ = MathHelper.clamp_double(positionEyes.zCoord, entityServerPos.minZ, entityServerPos.maxZ);
            AxisAlignedBB entityPosMe = new AxisAlignedBB(d0 - (double) f, d1, d2 - (double) f, d0 + (double) f, d1 + (double) this.entity.height, d2 + (double) f);
            double realX = MathHelper.clamp_double(positionEyes.xCoord, entityPosMe.minX, entityPosMe.maxX);
            double realY = MathHelper.clamp_double(positionEyes.yCoord, entityPosMe.minY, entityPosMe.maxY);
            double realZ = MathHelper.clamp_double(positionEyes.zCoord, entityPosMe.minZ, entityPosMe.maxZ);
            double distance = (double) this.hitRange.getValue();
            if (!mc.player.canEntityBeSeen(this.entity)) {
                distance = Math.min(distance, 3.0D);
            }

            double collision = this.entity.getCollisionBorderSize();
            double width = mc.player.width / 2.0F;
            //prob gona breaker cuz the rot inc thingy
            double mePosXForPlayer = mc.player.getLastServerPosition().xCoord + (mc.player.getSeverPosition().xCoord - mc.player.getLastServerPosition().xCoord) / (double) MathHelper.clamp_int(mc.player.rotIncrement, 1, 3);
            double mePosYForPlayer = mc.player.getLastServerPosition().yCoord + (mc.player.getSeverPosition().yCoord - mc.player.getLastServerPosition().yCoord) / (double) MathHelper.clamp_int(mc.player.rotIncrement, 1, 3);
            double mePosZForPlayer = mc.player.getLastServerPosition().zCoord + (mc.player.getSeverPosition().zCoord - mc.player.getLastServerPosition().zCoord) / (double) MathHelper.clamp_int(mc.player.rotIncrement, 1, 3);
            AxisAlignedBB mePosForPlayerBox = new AxisAlignedBB(mePosXForPlayer - width, mePosYForPlayer, mePosZForPlayer - width, mePosXForPlayer + width, mePosYForPlayer + (double) mc.player.height, mePosZForPlayer + width);
            mePosForPlayerBox = mePosForPlayerBox.expand(collision, collision, collision);
            Vec3 entityPosEyes = new Vec3(d3, d4 + (double) this.entity.getEyeHeight(), d5);
            double bestX = MathHelper.clamp_double(entityPosEyes.xCoord, mePosForPlayerBox.minX, mePosForPlayerBox.maxX);
            double bestY = MathHelper.clamp_double(entityPosEyes.yCoord, mePosForPlayerBox.minY, mePosForPlayerBox.maxY);
            double bestZ = MathHelper.clamp_double(entityPosEyes.zCoord, mePosForPlayerBox.minZ, mePosForPlayerBox.maxZ);
            boolean b = entityPosEyes.distanceTo(new Vec3(bestX, bestY, bestZ)) > 3.0D || mc.player.hurtTime < 8 && mc.player.hurtTime > 3;

            if (!this.onlyWhenNeed.isToggled()) {
                b = true;
            }

            if (b && positionEyes.distanceTo(new Vec3(realX, realY, realZ)) > positionEyes.distanceTo(new Vec3(currentX, currentY, currentZ)) && mc.player.getSeverPosition().distanceTo(new Vec3(d0, d1, d2)) < distance && !this.timeHelper.finished((long)this.timerDelay.getValue())) {
                this.blockPackets = true;
            } else {
                this.blockPackets = false;
                this.resetPackets(this.packetListener);
                this.timeHelper.reset();
            }
        }
    };

    @SubscribeEvent
    public final EventListener<PacketDevEvent> onPacketReceive = event -> {
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
                entity1 = mc.world.getEntityByID(packet.getEntityId());
                if (entity1 instanceof EntityLivingBase) {
                    entityLivingBase = (EntityLivingBase) entity1;
                    entityLivingBase.realPosX += packet.func_149062_c();
                    entityLivingBase.realPosY += packet.func_149061_d();
                    entityLivingBase.realPosZ += packet.func_149064_e();
                }
            }

            if (p instanceof S18PacketEntityTeleport) {
                S18PacketEntityTeleport packet = (S18PacketEntityTeleport) p;
                entity1 = mc.world.getEntityByID(packet.getEntityId());
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
                if (mc.world != null && mc.player != null) {
                    if (this.lastWorld != mc.world) {
                        this.resetPackets(event.getNetHandler());
                        this.lastWorld = mc.world;
                        return;
                    }

                    this.addPackets(p, event);
                }

                this.lastWorld = mc.world;
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

            if (entity instanceof EntityArmorStand && !this.armorStand.isToggled()) {
                return false;
            }

            if (entity instanceof EntityAnimal && !this.animal.isToggled()) {
                return false;
            }
            //maybe cause a expsion
            if (entity instanceof EntityMob && !this.mob.isToggled()) {
                return false;
            }

            if (entity instanceof EntityPlayer && !this.player.isToggled()) {
                return false;
            }

            if (entity instanceof EntityVillager && !this.villager.isToggled()) {
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

        return entity instanceof EntityLivingBase && entity != mc.player && (double) mc.player.getDistanceToEntity(entity) < this.hitRange.getValue();
    }


    @SubscribeEvent
    private final EventListener<Render3DEvent> onRender = event -> {
        if (this.esp.isToggled()) {
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
    //ColorUtil.rainbow(count * 20, 0.6f, 3.5)
    private void render(EntityLivingBase entity) {
        float lineWidth = 3.0F;
        count++;
        if (mc.player.getDistanceToEntity(entity) > 1.0F) {
            double d0 = 1.0F - mc.player.getDistanceToEntity(entity) / 20.0F;
            if (d0 < 0.3D) {
                d0 = 0.3D;
            }

            lineWidth = (float) ((double) lineWidth * d0);
        }

        RenderUtil.drawEntityServerESP(entity, ColorUtil.rainbow(count * 20, 0.6f, 3.5), 0.03137255f, 1.0f, lineWidth);
    }
    private void resetPackets(INetHandler netHandler) {
        if (!this.packets.isEmpty()) {
            for (; !this.packets.isEmpty(); this.packets.remove(this.packets.get(0))) {
                Packet packet = this.packets.get(0);

                try {
                    if (packet != null) {
                        if (Client.INSTANCE.getModuleManager().get(Velocity.class).isToggled()) {
                            if (!(packet instanceof S12PacketEntityVelocity) && (!(packet instanceof S27PacketExplosion))) {
                                packet.processPacket(netHandler);
                            }
                        } else {
                            packet.processPacket(netHandler);
                        }
                    }
                } catch (ThreadQuickExitException ignored) {}
            }
        }

    }

    private void addPackets(Packet packet, PacketDevEvent eventReadPacket) {
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
            return this.packetTimeUpdate.isToggled();
        } else if (packet instanceof S00PacketKeepAlive) {
            return this.packetKeepAlive.isToggled();
        } else if (packet instanceof S12PacketEntityVelocity) {
            return this.velocity.isToggled();
        } else if (packet instanceof S27PacketExplosion) {
            return this.packetVelocityExplosion.isToggled();
        } else if (packet instanceof S19PacketEntityStatus) {
            S19PacketEntityStatus entityStatus = (S19PacketEntityStatus) packet;
            return entityStatus.getOpCode() != 2 || !(mc.world.getEntityByID(entityStatus.getEntityId()) instanceof EntityLivingBase);
        } else {
            return !(packet instanceof S06PacketUpdateHealth) && !(packet instanceof S29PacketSoundEffect) && !(packet instanceof S3EPacketTeams) && !(packet instanceof S0CPacketSpawnPlayer);
        }
    }

}
