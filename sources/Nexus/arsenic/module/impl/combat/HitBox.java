package arsenic.module.impl.combat;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventPacket;
import arsenic.event.impl.EventRenderWorldLast;
import arsenic.injection.accessor.IMixinC02PacketUseEntity;
import arsenic.injection.accessor.IMixinRenderManager;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;
import arsenic.utils.minecraft.PacketUtil;
import arsenic.utils.minecraft.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleInfo(name = "HitBox", category = ModuleCategory.Combat)
public class HitBox extends Module {

    //maybe a fix
    public final DoubleProperty expand = new DoubleProperty("Expand", new DoubleValue(0, 2, 0.2, 0.01));

    public final BooleanProperty showNewHitbox = new BooleanProperty("Show new Hitboxes", false);
    public final BooleanProperty fix = new BooleanProperty("packet fix (WIP)", false);
    public final BooleanProperty AntiRetard = new BooleanProperty("Warning", true);

    public double getExpand() {
        return isEnabled() ? expand.getValue().getInput() : 0;
    }

    @Override
    protected void onEnable() {
        if (AntiRetard.getValue()) {
            PlayerUtils.addWaterMarkedMessageToChat("HITBOXES BAN ON HYPIXEL DO NOT USE THEM ON THERE");
        }
    }

    @EventLink
    public final Listener<EventRenderWorldLast> eventRenderWorldLastListener = event -> {
        ICamera camera = new Frustum();
        for(Entity entity : Minecraft.getMinecraft().theWorld.playerEntities) {
            if(entity == mc.thePlayer)
                continue;
            IMixinRenderManager renderManager = (IMixinRenderManager) mc.getRenderManager();
            double x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.partialTicks) - renderManager.getRenderPosX();
            double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.partialTicks) - renderManager.getRenderPosY();
            double z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.partialTicks) - renderManager.getRenderPosZ();
            float f1 = entity.getCollisionBorderSize() + (float) getExpand();
            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(f1, f1, f1);
            AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX - entity.posX + x, axisalignedbb.minY - entity.posY + y, axisalignedbb.minZ - entity.posZ + z, axisalignedbb.maxX - entity.posX + x, axisalignedbb.maxY - entity.posY + y, axisalignedbb.maxZ - entity.posZ + z);
            if(!camera.isBoundingBoxInFrustum(axisalignedbb1))
                continue;
            Color color = new Color(0xFFFFFFFF);
            GlStateManager.pushMatrix();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDepthMask(false);
            GL11.glLineWidth(2.0F);
            RenderGlobal.drawOutlinedBoundingBox(axisalignedbb1, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            GL11.glLineWidth(1.0F);
            GlStateManager.popMatrix();
        }
    };

    @EventLink
    public final Listener<EventPacket.OutGoing> eventPacketEventListener = event -> {
        if(event.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity packet = (C02PacketUseEntity) event.getPacket();
            IMixinC02PacketUseEntity iPacket = (IMixinC02PacketUseEntity) event.getPacket();
            if(packet.getHitVec() != null) {
                PlayerUtils.addWaterMarkedMessageToChat(packet.getHitVec().xCoord + " " + packet.getHitVec().yCoord + " " + packet.getHitVec().zCoord);
            }
        }
    };

}
