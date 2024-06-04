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
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.render.RenderUtil;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import util.time.StopWatch;

import java.awt.*;

/**
 * @author Flame7, Baldo0
 * @since 20.4.23
 */
@ModuleInfo(name = "BackTrack 2", category = Category.COMBAT, description = "Allows you to attack enemies in their previous location")
public final class Backtrack2 extends Module {

    private final StopWatch timeHelper = new StopWatch();
    public NumberValue hitRange = new NumberValue("Max Range", this, 6.0, 3.0, 6.0, 0.1);
    public NumberValue timerDelay = new NumberValue("Max Spoof Delay", this, 600.0, 0.0, 10000.0, 0.1);
    public BooleanValue esp = new BooleanValue("Render ESP", this, true);
    public BooleanValue onlyWhenNeed = new BooleanValue("Only With Low Distance", this, true);
    public BooleanValue velocity = new BooleanValue("Velocity", this, true);
    private EntityLivingBase entity;
    public boolean blockPackets;
    private final Animation animatedX = new Animation(Easing.LINEAR, 50);
    private final Animation animatedY = new Animation(Easing.LINEAR, 50);
    private final Animation animatedZ = new Animation(Easing.LINEAR, 50);

    @Override
    protected void onEnable() {
        this.entity = null;
        this.blockPackets = false;

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

    @EventLink()
    public final Listener<RealyPlayerTick> onPlayerUpdate = event -> {
        // ChatUtil.display("Early tick worki");
        if(mc.thePlayer.isDead) {
            this.setEnabled(false);
        }

        this.entity = (EntityLivingBase) getModule(KillAuraModule.class).target;

        if(entity == null) {
            return;
        }

        if(mc.thePlayer == null || mc.theWorld == null) return;

        final double entityRealX = this.entity.realPosX / 32.0;
        final double entityRealY = this.entity.realPosY / 32.0;
        final double entityRealZ = this.entity.realPosZ / 32.0;
        final double entityServerX = this.entity.serverPosX / 32.0;
        final double entityServerY = this.entity.serverPosY / 32.0;
        final double entityServerZ = this.entity.serverPosZ / 32.0;
        final double halfWidth = this.entity.width / 2.0F;

        final AxisAlignedBB entityServerPos = new AxisAlignedBB(entityServerX - halfWidth, entityServerY, entityServerZ - (double)halfWidth, entityServerX + (double)halfWidth, entityServerY + (double)this.entity.height, entityServerZ + (double)halfWidth);
        final Vec3 positionEyes = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);
        final double currentX = MathHelper.clamp_double(positionEyes.xCoord, entityServerPos.minX, entityServerPos.maxX);
        final double currentY = MathHelper.clamp_double(positionEyes.yCoord, entityServerPos.minY, entityServerPos.maxY);
        final double currentZ = MathHelper.clamp_double(positionEyes.zCoord, entityServerPos.minZ, entityServerPos.maxZ);
        final AxisAlignedBB entityPosMe = new AxisAlignedBB(entityRealX - (double)halfWidth, entityRealY, entityRealZ - (double)halfWidth, entityRealX + (double)halfWidth, entityRealY + (double)this.entity.height, entityRealZ + (double)halfWidth);
        final double realX = MathHelper.clamp_double(positionEyes.xCoord, entityPosMe.minX, entityPosMe.maxX);
        final double realY = MathHelper.clamp_double(positionEyes.yCoord, entityPosMe.minY, entityPosMe.maxY);
        final double realZ = MathHelper.clamp_double(positionEyes.zCoord, entityPosMe.minZ, entityPosMe.maxZ);
        double distance = this.hitRange.getValue().doubleValue();

        if (!mc.thePlayer.canEntityBeSeen(this.entity)) {
            distance = Math.min(distance, 3.0);
        }

        final double collision = this.entity.getCollisionBorderSize();
        final double playerWidth = mc.thePlayer.width / 2.0F;
        final double mePosXForPlayer = mc.thePlayer.getLastServerPosition().xCoord + (mc.thePlayer.getSeverPosition().xCoord - mc.thePlayer.getLastServerPosition().xCoord) / (double)MathHelper.clamp_int(mc.thePlayer.rotIncrement, 1, 3);
        final double mePosYForPlayer = mc.thePlayer.getLastServerPosition().yCoord + (mc.thePlayer.getSeverPosition().yCoord - mc.thePlayer.getLastServerPosition().yCoord) / (double)MathHelper.clamp_int(mc.thePlayer.rotIncrement, 1, 3);
        final double mePosZForPlayer = mc.thePlayer.getLastServerPosition().zCoord + (mc.thePlayer.getSeverPosition().zCoord - mc.thePlayer.getLastServerPosition().zCoord) / (double)MathHelper.clamp_int(mc.thePlayer.rotIncrement, 1, 3);
        AxisAlignedBB mePosForPlayerBox = new AxisAlignedBB(mePosXForPlayer - playerWidth, mePosYForPlayer, mePosZForPlayer - playerWidth, mePosXForPlayer + playerWidth, mePosYForPlayer + (double)mc.thePlayer.height, mePosZForPlayer + playerWidth);
        mePosForPlayerBox = mePosForPlayerBox.expand(collision, collision, collision);
        final Vec3 entityPosEyes = new Vec3(entityServerX, entityServerY + (double)this.entity.getEyeHeight(), entityServerZ);
        final double bestX = MathHelper.clamp_double(entityPosEyes.xCoord, mePosForPlayerBox.minX, mePosForPlayerBox.maxX);
        final double bestY = MathHelper.clamp_double(entityPosEyes.yCoord, mePosForPlayerBox.minY, mePosForPlayerBox.maxY);
        final double bestZ = MathHelper.clamp_double(entityPosEyes.zCoord, mePosForPlayerBox.minZ, mePosForPlayerBox.maxZ);

        boolean shouldBlock = false;
        if(entityPosEyes.distanceTo(new Vec3(bestX, bestY, bestZ)) > 3.0 || mc.thePlayer.hurtTime < 8 && mc.thePlayer.hurtTime > 3) {
            shouldBlock = true;
        }

        if (!this.onlyWhenNeed.getValue()) {
            shouldBlock = true;
        }
        //< or just dont check it
        if (shouldBlock && positionEyes.distanceTo(new Vec3(realX, realY, realZ)) > positionEyes.distanceTo(new Vec3(currentX, currentY, currentZ)) && mc.thePlayer.getSeverPosition().distanceTo(new Vec3(entityRealX, entityRealY, entityRealZ)) > distance && !this.timeHelper.finished(this.timerDelay.getValue().longValue())) {
            this.blockPackets = true;
        } else {
            this.blockPackets = false;
            this.timeHelper.reset();
        }
        //  ChatUtil.display(blockPackets);

        if(blockPackets) {
            PingSpoofComponent.setSpoofing(timerDelay.getValue().intValue(), true, true, velocity.getValue(), true, true);
        } else {
            // Ping spoof per poco tempo dopo che smette di bloccare i packets per evitare di flaggare Reach
            PingSpoofComponent.setSpoofing(30, true, true, velocity.getValue(), true, true);
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if (event.getDirection() == EnumPacketDirection.CLIENTBOUND) {
            Packet<?> p = event.getPacket();
            //ChatUtil.display("Direction worki");
            Entity entity1;
            EntityLivingBase entityLivingBase;
            if (p instanceof S14PacketEntity) {
                S14PacketEntity s14PacketEntity = (S14PacketEntity) p;
                entity1 = mc.theWorld.getEntityByID(s14PacketEntity.getEntityId());
                if (entity1 instanceof EntityLivingBase) {
                    entityLivingBase = (EntityLivingBase)entity1;
                    entityLivingBase.realPosX += s14PacketEntity.getPosX();
                    entityLivingBase.realPosY += s14PacketEntity.getPosY();
                    entityLivingBase.realPosZ += s14PacketEntity.getPosZ();
                }
            }

            if (p instanceof S18PacketEntityTeleport) {
                S18PacketEntityTeleport packet = (S18PacketEntityTeleport) p;
                entity1 = mc.theWorld.getEntityByID(packet.getEntityId());
                if (entity1 instanceof EntityLivingBase) {
                    entityLivingBase = (EntityLivingBase)entity1;
                    entityLivingBase.realPosX = packet.getX();
                    entityLivingBase.realPosY = packet.getY();
                    entityLivingBase.realPosZ = packet.getZ();
                }
            }
        }
    };

    private void renderFrozenEntity(Entity entity, Render3DEvent event) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return;
        }

        EntityOtherPlayerMP mp = new EntityOtherPlayerMP(mc.theWorld, ((EntityOtherPlayerMP) entity)
                .getGameProfile());

        mp.prevPosX = mp.lastTickPosX = mp.posX;
        mp.prevPosY = mp.lastTickPosY = mp.posY;
        mp.prevPosZ = mp.lastTickPosZ = mp.posZ;

        mp.posX = animatedX.getValue();
        mp.posY = animatedY.getValue();
        mp.posZ = animatedZ.getValue();

        mp.prevRotationYaw = entity.rotationYaw;
        mp.prevRotationPitch = entity.rotationPitch;

        mp.rotationYaw = entity.rotationYaw;
        mp.rotationPitch = entity.rotationPitch;

        mp.swingProgress = ((EntityOtherPlayerMP) entity).swingProgress;
        mp.swingProgressInt = ((EntityOtherPlayerMP) entity).swingProgressInt;

        mc.getRenderManager().renderEntitySimple(mp, event.getPartialTicks());
    }

    @EventLink
    private final Listener<Render3DEvent> onRender = event -> {
        if(entity == null || !Client.INSTANCE.getModuleManager().get(TargetEsp.class).isEnabled()) return;

        final double x = entity.realPosX / 32D;
        final double y = entity.realPosY / 32D;
        final double z = entity.realPosZ / 32D;

        // Setta prima
        animatedX.run(x);
        animatedY.run(y);
        animatedZ.run(z);

        //if(  !blockPackets) return;
        switch (Client.INSTANCE.getModuleManager().get(TargetEsp.class).backtrackespmode.getValue().getName()) {
            case "Box":
                GlStateManager.pushMatrix();
                GlStateManager.pushAttrib();
                GlStateManager.enableBlend();
                GlStateManager.disableTexture2D();
                GlStateManager.disableLighting();
                GL11.glDepthMask(false);

                animatedX.setDuration(150);
                animatedY.setDuration(150);
                animatedZ.setDuration(150);
                if (KillAuraModule.target != null) {
                    RenderUtil.color(KillAuraModule.target.hurtResistantTime > 1 ? Color.red : Color.green, 50);
                } else {
                    RenderUtil.color(Color.green, 50);
                }
                RenderUtil.drawBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(-mc.thePlayer.posX, -mc.thePlayer.posY, -mc.thePlayer.posZ)
                        .offset(animatedX.getValue(), animatedY.getValue(), animatedZ.getValue()).expand(0.14, 0.14, 0.14));

                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
                GL11.glDepthMask(true);
                GlStateManager.popAttrib();
                GlStateManager.popMatrix();
                GlStateManager.resetColor();
            break;

            case "Fake Player":
                GL11.glPushMatrix();
                GlStateManager.disableAlpha();

                animatedX.setDuration(150);
                animatedY.setDuration(150);
                animatedZ.setDuration(150);

                renderFrozenEntity(entity, event);
                GlStateManager.enableAlpha();
                GlStateManager.resetColor();
                GL11.glPopMatrix();
                break;
        }
    };
}