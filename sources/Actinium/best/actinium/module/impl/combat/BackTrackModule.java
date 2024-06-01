package best.actinium.module.impl.combat;

import best.actinium.Actinium;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.EarlyTickEvent;
import best.actinium.event.impl.game.TickEvent;
import best.actinium.event.impl.network.PacketDevEvent;
import best.actinium.event.impl.render.Render3DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.module.impl.visual.HudModule;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.ColorProperty;
import best.actinium.property.impl.InfoStringProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.math.RandomUtil;
import best.actinium.util.render.ChatUtil;
import best.actinium.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.play.server.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
@ModuleInfo(
        name = "Back Track",
        description = "Hits targets in their last pos.",
        category = ModuleCategory.COMBAT
)
public class BackTrackModule extends Module {
    //fix backtrack being weird xd
    private final TimerUtil timerUtil = new TimerUtil();
    public NumberProperty hitRange = new NumberProperty("Max Range", this, 3.0, 6.0, 6.0, 0.1);
    public NumberProperty minTimerDelay = new NumberProperty("Min Spoof Delay", this, 0, 600.0, 10000.0, 0.1);
    public NumberProperty maxTimerDelay = new NumberProperty("Max Spoof Delay", this, 0, 600.0, 10000.0, 0.1);
    public BooleanProperty aura = new BooleanProperty("Only With Aura", this, true);
    public BooleanProperty onlyWhenNeed = new BooleanProperty("Only With Low Distance", this, true);
    private BooleanProperty accurateReach = new BooleanProperty("Accurate Reach",this,true);
    private BooleanProperty disableOnHit = new BooleanProperty("Disabled On Hit",this,true);
    public BooleanProperty velocity = new BooleanProperty("Delay Velo", this, true);
    public BooleanProperty packetVelocityExplosion = new BooleanProperty("Explosion Velocity", this, true);
    public BooleanProperty packetTimeUpdate = new BooleanProperty("Time Update", this, true);
    public BooleanProperty packetKeepAlive = new BooleanProperty("Keep A live", this, true);
    private InfoStringProperty renderStuff = new InfoStringProperty("Render Stuff",this,false);
    public BooleanProperty esp = new BooleanProperty("Render ESP", this, true);
    private ColorProperty outlineColor = new ColorProperty("Outline Color",this,Color.red);
    private ColorProperty fillColor = new ColorProperty("Fill Color",this,Color.white);
    private InfoStringProperty targetStuff = new InfoStringProperty("Target Stuff",this,false);
    public BooleanProperty player = new BooleanProperty("Player", this, true);

    public BooleanProperty mob = new BooleanProperty("Mob", this, true);

    public BooleanProperty animal = new BooleanProperty("Animal", this, true);

    public BooleanProperty villager = new BooleanProperty("Villager", this, true);

    public BooleanProperty armorStand = new BooleanProperty("Armor Stand", this, true);
    private EntityLivingBase entity = null;
    private boolean blockPackets,b;
    private WorldClient lastWorld;
    private INetHandler packetListener = null;
    private final ArrayList<Packet> packets = new ArrayList<>();
    public AxisAlignedBB boundingBox;
    private Long stuff;

    @Override
    public void onEnable() {
        blockPackets = false;
        b = true;
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
    public void onDisable() {
        if (!this.packets.isEmpty() && this.packetListener != null) {
            this.resetPackets(this.packetListener);
        }

        this.packets.clear();
    }

    @Callback
    public void onTick(EarlyTickEvent event) {
        stuff = RandomUtil.getRandomPing(minTimerDelay.getValue().longValue(),maxTimerDelay.getValue().longValue());
        setSuffix(stuff + " ms");

        if (Actinium.INSTANCE.getModuleManager().get(KillauraModule.class).isEnabled()) {
            entity = KillauraModule.target instanceof EntityLivingBase ? KillauraModule.target : null;
        } else {
            Object[] listOfTargets = mc.theWorld.loadedEntityList.stream().filter(this::canAttacked).sorted(Comparator.comparingDouble((entityy) -> (double) mc.thePlayer.getDistanceToEntity(entityy))).toArray();
            if (listOfTargets.length > 0) {
                this.entity = (EntityLivingBase) listOfTargets[0];
            }

            if (this.aura.isEnabled()) {
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
            Vec3 positionEyes = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);
            double currentX = MathHelper.clamp_double(positionEyes.xCoord, entityServerPos.minX, entityServerPos.maxX);
            double currentY = MathHelper.clamp_double(positionEyes.yCoord, entityServerPos.minY, entityServerPos.maxY);
            double currentZ = MathHelper.clamp_double(positionEyes.zCoord, entityServerPos.minZ, entityServerPos.maxZ);
            AxisAlignedBB entityPosMe = new AxisAlignedBB(d0 - (double) f, d1, d2 - (double) f, d0 + (double) f, d1 + (double) this.entity.height, d2 + (double) f);
            double realX = MathHelper.clamp_double(positionEyes.xCoord, entityPosMe.minX, entityPosMe.maxX);
            double realY = MathHelper.clamp_double(positionEyes.yCoord, entityPosMe.minY, entityPosMe.maxY);
            double realZ = MathHelper.clamp_double(positionEyes.zCoord, entityPosMe.minZ, entityPosMe.maxZ);
            double distance = this.hitRange.getValue();

            if (!mc.thePlayer.canEntityBeSeen(this.entity)) {
                //  distance = Math.min(distance, 3.0D);
                distance = accurateReach.isEnabled() ? Math.min(distance, 3.0D) : 3.0D;
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

            if (!this.onlyWhenNeed.isEnabled()) {
                b = true;
            }

            if(disableOnHit.isEnabled() && mc.thePlayer != null && mc.thePlayer.hurtTime != 0) {
                //b = false;
            }

            //todo: DONT FORGET ABOUT THIS TEST
            //b &&
            if (positionEyes.distanceTo(new Vec3(realX, realY, realZ)) > positionEyes.distanceTo(new Vec3(currentX, currentY, currentZ)) && mc.thePlayer.getSeverPosition().distanceTo(new Vec3(d0, d1, d2)) < distance && !this.timerUtil.finished(stuff)) {
                this.blockPackets = true;
            } else {
                this.blockPackets = false;
                this.resetPackets(this.packetListener);
                this.timerUtil.reset();
            }
        }
    }

    @Callback
    public void onDevPacket(PacketDevEvent event) {
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
                    entityLivingBase.realPosX += packet.func_149062_c();
                    entityLivingBase.realPosY += packet.func_149061_d();
                    entityLivingBase.realPosZ += packet.func_149064_e();
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
    }

    private boolean canAttacked(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            if (entity.isInvisible()) {
                return false;
            }

            if (((EntityLivingBase) entity).deathTime > 1) {
                return false;
            }

            if (entity instanceof EntityArmorStand && !this.armorStand.isEnabled()) {
                return false;
            }

            if (entity instanceof EntityAnimal && !this.animal.isEnabled()) {
                return false;
            }

            if (entity instanceof EntityMob && !this.mob.isEnabled()) {
                return false;
            }

            if (entity instanceof EntityPlayer && !this.player.isEnabled()) {
                return false;
            }

            if (entity instanceof EntityVillager && !this.villager.isEnabled()) {
                return false;
            }

            if (entity.ticksExisted < 50) {
                return false;
            }

            if (entity.isDead) {
                return false;
            }
        }

        return entity instanceof EntityLivingBase && entity != mc.thePlayer && (double) mc.thePlayer.getDistanceToEntity(entity) < this.hitRange.getValue();
    }

    @Callback
    public void onRender(Render3DEvent event) {
        if (this.esp.isEnabled()) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            GlStateManager.disableCull();
            GL11.glDepthMask(false);
            if (this.entity != null && this.blockPackets) {
                RenderUtil.drawEntityServerESP(entity, 0.23f, outlineColor.getColor(), fillColor.getColor());
            }

            GL11.glDepthMask(true);
            GlStateManager.enableCull();
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2848);
        }
    }

    private void resetPackets(INetHandler netHandler) {
        if (!this.packets.isEmpty()) {
            while (!this.packets.isEmpty()) {
                final Packet packet = this.packets.get(0);
                if (packet != null) {
                    try {
                        packet.processPacket(netHandler);
                    } catch (Exception e) {
                        //  e.printStackTrace();
                    }
                }

                try {
                    this.packets.remove(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addPackets(Packet packet, PacketDevEvent eventReadPacket) {
        synchronized (this.packets) {
            if (this.delayPackets(packet)) {
                this.packets.add(packet);
                eventReadPacket.setCancelled(true);
            }
        }
    }
    //something is fucked with the server pos calculatuib bgk
    private boolean delayPackets(Packet packet) {
        if (mc.currentScreen != null) {
            return false;

        } else if (packet instanceof S03PacketTimeUpdate) {
            return this.packetTimeUpdate.isEnabled();

        } else if (packet instanceof S00PacketKeepAlive) {
            return this.packetKeepAlive.isEnabled();

        } else if (packet instanceof S12PacketEntityVelocity) {
            return this.velocity.isEnabled();

        } else if (packet instanceof S27PacketExplosion) {
            return this.packetVelocityExplosion.isEnabled();

        } else if (packet instanceof S19PacketEntityStatus) {
            S19PacketEntityStatus entityStatus = (S19PacketEntityStatus) packet;
            return entityStatus.getOpCode() != 2 || !(mc.theWorld.getEntityByID(entityStatus.getEntityId()) instanceof EntityLivingBase);

        } else {
            return !(packet instanceof S06PacketUpdateHealth) && !(packet instanceof S29PacketSoundEffect) && !(packet instanceof S3EPacketTeams) && !(packet instanceof S0CPacketSpawnPlayer);
        }
    }
}
