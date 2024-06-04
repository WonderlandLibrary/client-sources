package com.polarware.module.impl.combat;

import com.polarware.Client;
import com.polarware.component.impl.player.PingSpoofComponent;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.RealyPlayerTick;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.event.impl.render.Render3DEvent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.render.TargetEsp;
import com.polarware.util.animation.Animation;
import com.polarware.util.animation.Easing;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.render.ColorUtil;
import com.polarware.util.render.RenderUtil;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import com.polarware.event.impl.network.PacketReceiveEvent;
import util.time.StopWatch;

import java.awt.*;
import java.util.List;

@ModuleInfo(name = "Back track", description = "module.combat.legitreach.description", category = Category.COMBAT)
public final class BacktrackModule extends Module {

    public NumberValue maxPingSpoof = new NumberValue("Max Ping Spoof", this, 1000, 50, 10000, 1);
    public BooleanValue preventRepeatedAttacks = new BooleanValue("Prevent repeated attacks", this, true);
    public BooleanValue delayVelocity = new BooleanValue("Delay Velocity", this, true);
    public BooleanValue delayBlockUpdates = new BooleanValue("Delay Block Updates", this, true);
    public BooleanValue playerespp = new BooleanValue("Player ESP", this, false);
    private final Animation animatedX = new Animation(Easing.LINEAR, 50);
    private final Animation animatedY = new Animation(Easing.LINEAR, 50);
    private final Animation animatedZ = new Animation(Easing.LINEAR, 50);

    private StopWatch stopWatch = new StopWatch();

    private Vec3 realTargetPosition = new Vec3(0, 0, 0);

    public Entity targetEntity;
    public boolean isActive, editedPackets;

    @Override
    protected void onEnable() {
        stopWatch.reset();
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        stopWatch.reset();
        super.onDisable();
    }

    @EventLink()
    public final Listener<RealyPlayerTick> onPreUpdate = event -> {
        List<EntityLivingBase> targets = Client.INSTANCE.getTargetComponent().getTargets(9);


        if (targets.isEmpty()) {
            isActive = true;
            targetEntity = null;
            return;
        }


        targetEntity = targets.get(0);
        //51
        // if (targetEntity == null || stopWatch.finished((long)MathHelper.randomFloatClamp(new Random(),48,55))) {
        //50
        if (targetEntity == null || stopWatch.finished(51)) {
           // PingSpoofComponent.setSpoofing(maxPingSpoof.getValue().intValue(), false, true, delayVelocity.getValue(), delayBlockUpdates.getValue(), true);
            stopWatch.reset();
            return;
        }
        //normal : true
        //teleport : true
        if(mc.thePlayer.isSwingInProgress) {
            PingSpoofComponent.setSpoofing(maxPingSpoof.getValue().intValue(), false, false, delayVelocity.getValue(), delayBlockUpdates.getValue(), true);
        } else {
            PingSpoofComponent.setSpoofing(150, false, false, delayVelocity.getValue(), delayBlockUpdates.getValue(), true);
        }
            if (isActive) {
                realTargetPosition = new Vec3(targetEntity.posX, targetEntity.posY, targetEntity.posZ);
                isActive = false;
            }
            //3
            while ((targetEntity.getDistanceToEntity(mc.thePlayer) > 6 ||
                    realTargetPosition.distanceTo(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)) > 6) &&
                    !PingSpoofComponent.incomingPackets.isEmpty()) {

                PacketUtil.TimedPacket packet = PingSpoofComponent.incomingPackets.poll();

                if (packet == null) break;

                PacketUtil.receiveNoEvent(packet.getPacket());
            }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        final Packet<?> packet = event.getPacket();

        if (targetEntity == null) {
            return;
        }

        if (packet instanceof S14PacketEntity) {
            S14PacketEntity s14PacketEntity = ((S14PacketEntity) packet);

            if (s14PacketEntity.entityId == targetEntity.getEntityId()) {
                realTargetPosition.xCoord += s14PacketEntity.getPosX() / 32D;
                realTargetPosition.yCoord += s14PacketEntity.getPosY() / 32D;
                realTargetPosition.zCoord += s14PacketEntity.getPosZ() / 32D;
            }
        } else if (packet instanceof S18PacketEntityTeleport) {
            S18PacketEntityTeleport s18PacketEntityTeleport = ((S18PacketEntityTeleport) packet);

            if (s18PacketEntityTeleport.getEntityId() == targetEntity.getEntityId()) {
                realTargetPosition = new Vec3(s18PacketEntityTeleport.getX() / 32D, s18PacketEntityTeleport.getY() / 32D, s18PacketEntityTeleport.getZ() / 32D);
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

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {

        if (targetEntity == null) {
            return;
        }
        if(Client.INSTANCE.getModuleManager().get(TargetEsp.class).isEnabled()) {
            switch (Client.INSTANCE.getModuleManager().get(TargetEsp.class).backtrackespmode.getValue().getName()) {
                case "Fake Player":
                    animatedX.run(realTargetPosition.xCoord);
                    animatedY.run(realTargetPosition.yCoord);
                    animatedZ.run(realTargetPosition.zCoord);

                    GL11.glPushMatrix();
                    GlStateManager.disableAlpha();

                    animatedX.setDuration(150);
                    animatedY.setDuration(150);
                    animatedZ.setDuration(150);


                    renderFrozenEntity(targetEntity, event);
                    GlStateManager.enableAlpha();
                    GlStateManager.resetColor();
                    GL11.glPopMatrix();
                    break;
                case "Box":
                        animatedX.run(realTargetPosition.xCoord);
                        animatedY.run(realTargetPosition.yCoord);
                        animatedZ.run(realTargetPosition.zCoord);
                        GlStateManager.pushMatrix();
                        GlStateManager.pushAttrib();
                        GlStateManager.enableBlend();
                        GlStateManager.disableTexture2D();
                        GlStateManager.disableLighting();
                        GL11.glDepthMask(false);

                        animatedX.setDuration(300);
                        animatedY.setDuration(300);
                        animatedZ.setDuration(300);

                        double expand = 0.14;
                        RenderUtil.color(ColorUtil.withAlpha(Color.white, 30));

                        RenderUtil.drawBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(-mc.thePlayer.posX, -mc.thePlayer.posY, -mc.thePlayer.posZ).
                                offset(animatedX.getValue(), animatedY.getValue(), animatedZ.getValue()).expand(expand, expand, expand));

                        GlStateManager.enableTexture2D();
                        GlStateManager.enableLighting();
                        GlStateManager.disableBlend();
                        GL11.glDepthMask(true);
                        GlStateManager.popAttrib();
                        GlStateManager.popMatrix();
                        GlStateManager.resetColor();
                    break;
            }
        }
    };


    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        if (preventRepeatedAttacks.getValue()) editedPackets = true;
    };
}
