package vestige.module.impl.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.jetbrains.annotations.NotNull;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.GuiRender;
import vestige.font.VestigeFontRenderer;
import vestige.module.impl.visual.ClientTheme;
import vestige.shaders.impl.GaussianBlur;
import vestige.util.render.ColorUtil2;
import vestige.util.render.RenderUtils2;

public class NotificationManagerExterno {
   private boolean initialised;
   private VestigeFontRenderer productSans;
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static final List<NotificationManagerExterno.Notification> notifications = new ArrayList();
   private ClientTheme theme;
   private static int ravenXD$hoverValue;

   private void initialise() {
      this.productSans = Flap.instance.getFontManager().getProductSans();
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
   }

   @Listener
   protected void renderModule(boolean inChat) {
      if (!this.initialised) {
         this.initialise();
         this.initialised = true;
      }

      renderNotifications();
   }

   @Listener
   public void onGuiRender(GuiRender event) {
      renderNotifications();
   }

   public static void showNotification(String title, String message, NotificationManagerExterno.NotificationType type, long duration) {
      ScaledResolution sr = new ScaledResolution(mc);
      notifications.add(new NotificationManagerExterno.Notification(title, message, type, duration, sr.getScaledWidth(), sr.getScaledHeight()));
   }

   public static void renderNotifications() {
      ScaledResolution sr = new ScaledResolution(mc);
      int yOffset = sr.getScaledHeight() - 30;

      for(int i = 0; i < notifications.size(); ++i) {
         NotificationManagerExterno.Notification notification = (NotificationManagerExterno.Notification)notifications.get(i);
         if (notification.isExpired()) {
            notifications.remove(i);
            --i;
         } else {
            notification.updatePosition(yOffset);
            GaussianBlur.startBlur();
            RenderUtils2.drawBloomShadow((float)(notification.getX() + 2), (float)(notification.getY() + 2), (float)(notification.getX() + 148), (float)(notification.getY() + 38), 3, 3, Color.black.getRGB(), false);
            GaussianBlur.endBlur(4.0F, 2.0F);
            Color rectColor = new Color(40, 42, 47, ravenXD$hoverValue);
            rectColor = flap$interpolateColorC(rectColor, ColorUtil2.brighter(rectColor, 0.4F), -1.0F);
            RenderUtils2.drawRoundOutline((double)notification.getX(), (double)notification.getY(), 150.0D, 40.0D, 3.5D, 0.30000001192092896D, new Color(0, 0, 0, 130), new Color(50, 50, 50, 200));
            Flap.instance.getFontManager().getProductSans().drawStringWithShadow(notification.getTitle(), (float)(notification.getX() + 13), (float)(notification.getY() + 10), applyOpacity(Color.WHITE.getRGB(), notification.getOpacity()));
            Flap.instance.getFontManager().getProductSans().drawStringWithShadow(notification.getMessage(), (float)(notification.getX() + 13), (float)(notification.getY() + 21), applyOpacity(Color.gray.getRGB(), notification.getOpacity()));
            yOffset -= 45;
         }
      }

   }

   private static int applyOpacity(int color, float opacity) {
      int alpha = (int)((float)(color >> 24 & 255) * opacity);
      return color & 16777215 | alpha << 24;
   }

   @NotNull
   private static Color flap$interpolateColorC(@NotNull Color color1, @NotNull Color color2, float amount) {
      if (color1 == null) {
         $$$reportNull$$$0(0);
      }

      if (color2 == null) {
         $$$reportNull$$$0(1);
      }

      amount = Math.min(1.0F, Math.max(0.0F, amount));
      return new Color(ColorUtil2.interpolateInt(color1.getRed(), color2.getRed(), (double)amount), ColorUtil2.interpolateInt(color1.getGreen(), color2.getGreen(), (double)amount), ColorUtil2.interpolateInt(color1.getBlue(), color2.getBlue(), (double)amount), ColorUtil2.interpolateInt(color1.getAlpha(), color2.getAlpha(), (double)amount));
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      Object[] var10001 = new Object[3];
      switch(var0) {
      case 0:
      default:
         var10001[0] = "color1";
         break;
      case 1:
         var10001[0] = "color2";
      }

      var10001[1] = "vestige/module/impl/client/NotificationManagerExterno";
      var10001[2] = "flap$interpolateColorC";
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }

   public static class Notification {
      private final String title;
      private final String message;
      private final NotificationManagerExterno.NotificationType type;
      private final long endTime;
      private int x;
      private int y;
      private float opacity;
      private boolean isExiting;

      public Notification(String title, String message, NotificationManagerExterno.NotificationType type, long duration, int screenWidth, int screenHeight) {
         this.title = title;
         this.message = message;
         this.type = type;
         this.endTime = System.currentTimeMillis() + duration;
         this.x = screenWidth - 160;
         this.y = screenHeight;
         this.opacity = 1.0F;
         this.isExiting = false;
      }

      public void updatePosition(int targetY) {
         if (this.isExiting) {
            this.x += 2;
            this.opacity -= 0.05F;
         } else if (this.y > targetY) {
            --this.y;
         }

         if (System.currentTimeMillis() > this.endTime - 500L && !this.isExiting) {
            this.isExiting = true;
         }

      }

      public boolean isExpired() {
         return System.currentTimeMillis() > this.endTime || this.opacity <= 0.0F;
      }

      public String getMessage() {
         return this.message;
      }

      public String getTitle() {
         return this.title;
      }

      public int getX() {
         return this.x;
      }

      public int getY() {
         return this.y - 25;
      }

      public float getOpacity() {
         return this.opacity;
      }

      public NotificationManagerExterno.NotificationType getType() {
         return this.type;
      }
   }

   public static enum NotificationType {
      INFO,
      WARNING,
      SUCESS;

      // $FF: synthetic method
      private static NotificationManagerExterno.NotificationType[] $values() {
         return new NotificationManagerExterno.NotificationType[]{INFO, WARNING, SUCESS};
      }
   }
}
