package exhibition.management.notifications.user;

import exhibition.Client;
import exhibition.module.Module;
import exhibition.module.impl.hud.Enabled;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class NotificationRenderer implements INotificationRenderer {
   private ResourceLocation logo = new ResourceLocation("textures/spotify.png");

   public void draw(List notifications) {
      Minecraft mc = Minecraft.getMinecraft();
      ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
      float y = (float)scaledRes.getScaledHeight() - (float)(notifications.size() * 22 + (((Module)Client.getModuleManager().get(Enabled.class)).isEnabled() ? 12 : 0) + (mc.currentScreen != null && mc.currentScreen instanceof GuiChat ? 12 : 0));

      for(Iterator var5 = notifications.iterator(); var5.hasNext(); y += 22.0F) {
         INotification notification = (INotification)var5.next();
         Notification not = (Notification)notification;
         not.translate.interpolate(not.getTarX(), y, 0.3F);
         int s = scaledRes.getScaleFactor();
         float subHeaderWidth = Client.subHeader.getWidth(not.getSubtext());
         float headerWidth = Client.header.getWidth(not.getHeader());
         float x = (float)(scaledRes.getScaledWidth() - 20) - (headerWidth > subHeaderWidth ? headerWidth : subHeaderWidth);
         GL11.glPushMatrix();
         GL11.glEnable(3089);
         GL11.glScissor((int)not.translate.getX() * s, (int)((float)scaledRes.getScaledWidth() - not.translate.getY() * (float)s), scaledRes.getScaledWidth() * s, (int)((not.translate.getY() + 50.0F) * (float)s));
         RenderingUtil.rectangle((double)x, (double)not.translate.getY(), (double)scaledRes.getScaledWidth(), (double)(not.translate.getY() + 22.0F - 1.0F), Colors.getColor(0, 200));
         if (not.getType().equals(Notifications.Type.SPOTIFY)) {
            GlStateManager.pushMatrix();
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            mc.getTextureManager().bindTexture(this.logo);
            GlStateManager.translate((double)(x + 1.0F), (double)not.translate.getY() + 1.5D, 0.0D);
            RenderingUtil.drawIcon(0.0D, 0.0D, 0.0F, 0.0F, 18.0D, 18.0D, 18.0F, 18.0F);
            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.popMatrix();
         } else {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 10.0F, not.translate.getY() + 13.0F, 0.0F);
            GlStateManager.rotate(270.0F, 0.0F, 0.0F, 90.0F);

            for(int i = 0; i < 11; ++i) {
               RenderingUtil.drawCircle(0.0F, 0.0F, (float)(11 - i), 3, this.getColor(not.getType()));
            }

            RenderingUtil.drawCircle(0.0F, 0.0F, 11.0F, 3, Colors.getColor(0));
            GlStateManager.popMatrix();
            RenderingUtil.rectangle((double)x + 9.6D, (double)(not.translate.getY() + 5.0F), (double)x + 10.3D, (double)(not.translate.getY() + 13.0F), Colors.getColor(0));
            RenderingUtil.rectangle((double)x + 9.6D, (double)(not.translate.getY() + 15.0F), (double)x + 10.3D, (double)(not.translate.getY() + 17.0F), Colors.getColor(0));
         }

         Client.header.drawStringWithShadow(not.getHeader(), x + 20.0F, not.translate.getY() + 1.0F, -1);
         Client.subHeader.drawStringWithShadow(not.getSubtext(), x + 20.0F, not.translate.getY() + 12.0F, -1);
         GL11.glDisable(3089);
         GL11.glPopMatrix();
         if (not.checkTime() >= not.getDisplayTime() + not.getStart()) {
            not.setTarX(scaledRes.getScaledWidth());
            if (not.translate.getX() >= (float)scaledRes.getScaledWidth()) {
               notifications.remove(notification);
            }
         }
      }

   }

   private int getColor(Notifications.Type type) {
      int color = 0;
      switch(type) {
      case INFO:
         color = Colors.getColor(64, 131, 214);
         break;
      case NOTIFY:
         color = Colors.getColor(242, 206, 87);
         break;
      case WARNING:
         color = Colors.getColor(226, 74, 74);
         break;
      case SPOTIFY:
         color = Colors.getColor(30, 215, 96);
      }

      return color;
   }
}
