package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRender3D;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class MotionPrediction extends Module {
   public MotionPrediction(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventRender3D.class}
   )
   public void onEvent(Event event) {
      EventRender3D er = (EventRender3D)event;
      EntityPlayer player = mc.thePlayer;
      GL11.glPushMatrix();
      RenderingUtil.pre3D();
      mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
      double x = player.prevPosX + (player.posX - player.prevPosX) * (double)er.renderPartialTicks - RenderManager.renderPosX;
      double y = player.prevPosY + (player.posY - player.prevPosY) * (double)er.renderPartialTicks - RenderManager.renderPosY;
      double z = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)er.renderPartialTicks - RenderManager.renderPosZ;
      double xDelta = player.posX - player.prevPosX;
      double yDelta = player.posY - player.prevPosY;
      double zDelta = player.posZ - player.prevPosZ;
      int color = Colors.getColor(0, 255, 0);
      double yMotion = 0.0D;
      double initVel = mc.thePlayer.motionY;

      for(int i = 0; i < 6; ++i) {
         yMotion += initVel - 0.002D * (double)i;
         GlStateManager.pushMatrix();
         GlStateManager.translate(x + xDelta * (double)i, y + (yDelta + yMotion) * (double)i, z + zDelta * (double)i);
         AxisAlignedBB var11 = player.getEntityBoundingBox();
         AxisAlignedBB var12 = new AxisAlignedBB(var11.minX - player.posX, var11.minY - player.posY, var11.minZ - player.posZ, var11.maxX - player.posX, var11.maxY - player.posY, var11.maxZ - player.posZ);
         RenderingUtil.glColor(color);
         RenderingUtil.drawOutlinedBoundingBox(var12);
         GlStateManager.popMatrix();
      }

      RenderingUtil.post3D();
      GL11.glPopMatrix();
   }
}
