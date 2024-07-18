package com.alan.clients.module.impl.combat;

import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PostMotionEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.List;

@ModuleInfo(aliases = {"module.combat.legitreach.name", "Back Track"}, description = "module.combat.legitreach.description", category = Category.COMBAT)
public final class LegitReach extends Module {

    public NumberValue maxPingSpoof = new NumberValue("Max Ping Spoof", this, 1000, 50, 10000, 1);
    public BooleanValue render = new BooleanValue("Render Real Location", this, true);
    private Vec3 realPosition = new Vec3(0, 0, 0);
    private final Animation animatedX = new Animation(Easing.LINEAR, 50);
    private final Animation animatedY = new Animation(Easing.LINEAR, 50);
    private final Animation animatedZ = new Animation(Easing.LINEAR, 50);
    public Entity targetEntity;
    public boolean isActive;
    @EventLink()
    public final Listener<PostMotionEvent> onPost = event -> {
        // Getting targets and selecting the nearest one
        List<EntityLivingBase> targets = TargetComponent.getTargets(9);

        if (targets.isEmpty()) {
            isActive = true;
            targetEntity = null;
            return;
        }

        Entity entity = targets.get(0);
        if (entity != targetEntity) {
            targetEntity = entity;

            realPosition.xCoord = entity.posX;
            realPosition.yCoord = entity.posY;
            realPosition.zCoord = entity.posZ;
        }

        if (targetEntity == null || !(mc.thePlayer.isSwingInProgress || getModule(KillAura.class).isEnabled())) {
            return;
        }

        double realDistance = realPosition.distanceTo(mc.thePlayer);
        double clientDistance = targetEntity.getDistanceToEntity(mc.thePlayer);

        boolean on = realDistance > clientDistance && realDistance > 2.3 && realDistance < 5.9;

        if (on) {
            PingSpoofComponent.spoof(maxPingSpoof.getValue().intValue(), true, true, true, true);
        } else {
            PingSpoofComponent.disable();
            PingSpoofComponent.dispatch();
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
                realPosition.xCoord += s14PacketEntity.getPosX() / 32D;
                realPosition.yCoord += s14PacketEntity.getPosY() / 32D;
                realPosition.zCoord += s14PacketEntity.getPosZ() / 32D;
            }
        } else if (packet instanceof S18PacketEntityTeleport) {
            S18PacketEntityTeleport s18PacketEntityTeleport = ((S18PacketEntityTeleport) packet);

            if (s18PacketEntityTeleport.getEntityId() == targetEntity.getEntityId()) {
                realPosition = new Vec3(s18PacketEntityTeleport.getX() / 32D, s18PacketEntityTeleport.getY() / 32D, s18PacketEntityTeleport.getZ() / 32D);
            }
        }
    };

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {
        if (targetEntity == null || !render.getValue()) {
            return;
        }

        animatedX.setDuration(150);
        animatedY.setDuration(150);
        animatedZ.setDuration(150);

        animatedX.run(realPosition.xCoord);
        animatedY.run(realPosition.yCoord);
        animatedZ.run(realPosition.zCoord);

        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GL11.glDepthMask(false);

        double expand = 0.14;
        RenderUtil.color(ColorUtil.withAlpha(getTheme().getFirstColor(), 50));

        RenderUtil.drawBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(-mc.thePlayer.posX, -mc.thePlayer.posY, -mc.thePlayer.posZ).
                offset(animatedX.getValue(), animatedY.getValue(), animatedZ.getValue()).expand(expand, expand, expand));

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GL11.glDepthMask(true);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        GlStateManager.resetColor();
    };
}