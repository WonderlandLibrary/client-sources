package cc.novoline.utils.notifications;

import cc.novoline.Novoline;
import cc.novoline.utils.fonts.impl.Fonts$ICONFONT$ICONFONT_24;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_18;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_22;
import cc.novoline.utils.notifications.Notification;
import cc.novoline.utils.notifications.NotificationManager;
import cc.novoline.utils.notifications.NotificationRenderer$1;
import cc.novoline.utils.notifications.NotificationType;
import java.awt.Color;
import java.util.Iterator;
import net.m8;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.StringUtils;

public final class NotificationRenderer {
   private static final int RED = (new Color(255, 80, 80)).getRGB();
   private static final int GREEN = (new Color(135, 227, 49)).getRGB();
   private static final int ORANGE = (new Color(255, 215, 100)).getRGB();
   private static final int WHITE = (new Color(255, 255, 255)).getRGB();
   private static final Minecraft g = Minecraft.getInstance();
   private static final NotificationManager notificationManager = Novoline.getInstance().getNotificationManager();
   private static int displayHeight = 0;
   private static int displayWidth = 0;

   public static void a(ScaledResolution var0) {
      int[] var1 = NotificationType.b();
      if(!notificationManager.getNotifications().isEmpty()) {
         notificationManager.b();
      }

      Iterator var2 = notificationManager.getNotifications().iterator();
      if(var2.hasNext()) {
         Notification var3 = (Notification)var2.next();
         double var4 = var3.getX();
         double var6 = (double)var0.getScaledHeight() - var3.getY();
         String var8 = var3.getCallReason() == null?StringUtils.capitalize(var3.getType().toString()):var3.getCallReason();
         String var9 = var3.getMessage();
         String var10 = String.valueOf(((double)var3.getDelay() - var3.getCount()) / 1000.0D);
         String var11 = "(" + var10.substring(0, var10.indexOf(".") + 2) + "s) ";
         Gui.drawRect((double)var0.getScaledWidth() - var4 - 2.0D, var6 - 3.0D, (double)var0.getScaledWidth(), var6 + 24.0D, (new Color(0, 0, 0, 150)).getRGB());
         Fonts$SFBOLD$SFBOLD_22.SFBOLD_22.drawString(var8, (double)((float)var0.getScaledWidth() - (float)var4 + 25.0F), (double)((float)var6), -1, true);
         Fonts$SFBOLD$SFBOLD_18.SFBOLD_18.drawString(var9 + " " + var11, (double)((float)var0.getScaledWidth() - (float)var4 + 25.0F), (double)((float)var6 + 13.0F), (new Color(200, 200, 200, 255)).getRGB(), true);
         switch(NotificationRenderer$1.$SwitchMap$cc$novoline$utils$notifications$NotificationType[var3.getType().ordinal()]) {
         case 1:
            Fonts$ICONFONT$ICONFONT_24.ICONFONT_24.drawString("I", (float)((double)var0.getScaledWidth() - var4 + 6.5D), (float)var6 + 7.5F, RED);
         case 2:
            m8.a.drawString("A", (float)((double)var0.getScaledWidth() - var4 + 9.5D), (float)var6 + 7.5F, ORANGE);
         case 3:
            Fonts$ICONFONT$ICONFONT_24.ICONFONT_24.drawString("H", (float)((double)var0.getScaledWidth() - var4 + 5.5D), (float)var6 + 7.5F, GREEN);
         case 4:
            m8.a.drawString("B", (float)((double)var0.getScaledWidth() - var4 + 8.0D), (float)var6 + 7.5F, WHITE);
         default:
            throw new IllegalStateException("Unexpected value: " + var3.getType());
         }
      }
   }

   private static int a(NotificationType var0) {
      int[] var1 = NotificationType.b();
      switch(NotificationRenderer$1.$SwitchMap$cc$novoline$utils$notifications$NotificationType[var0.ordinal()]) {
      case 1:
         return RED;
      case 2:
         return ORANGE;
      case 3:
         return GREEN;
      case 4:
         return WHITE;
      default:
         return 0;
      }
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}
