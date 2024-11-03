package vestige.module.impl.visual;

import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.Render3DEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.combat.Antibot;
import vestige.util.render.RenderUtils2;

public class Tracers extends Module {
   public Tracers() {
      super("Tracers", Category.VISUAL);
   }

   @Listener
   public void onRender3D(Render3DEvent event) {
      Iterator var2 = mc.theWorld.playerEntities.iterator();

      while(var2.hasNext()) {
         EntityPlayer playerEntity = (EntityPlayer)var2.next();
         if (playerEntity != mc.thePlayer && !((Antibot)Flap.instance.getModuleManager().getModule(Antibot.class)).isBot(playerEntity) && !playerEntity.isDead && !playerEntity.isInvisible()) {
            this.drawToPlayer(playerEntity);
         }
      }

      RenderUtils2.color(-1);
   }

   public void drawToPlayer(EntityLivingBase entity) {
      float var10000 = 1.0F;
      var10000 = 1.0F;
      var10000 = 1.0F;
      double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
      double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
      double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;
      this.render(1.0F, 1.0F, 1.0F, xPos, yPos, zPos);
   }

   public void render(float red, float green, float blue, double x, double y, double z) {
      drawTracerLine(x, y, z, red, green, blue, 0.3F, 1.5F);
   }

   public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
      GL11.glPushMatrix();
      GL11.glLoadIdentity();
      mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);
      GL11.glEnable(3042);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(lineWidth);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(2);
      GL11.glVertex3d(0.0D, (double)mc.thePlayer.getEyeHeight(), 0.0D);
      GL11.glVertex3d(x, y, z);
      GL11.glEnd();
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }
}
