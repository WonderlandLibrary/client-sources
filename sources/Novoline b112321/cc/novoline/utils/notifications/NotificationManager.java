package cc.novoline.utils.notifications;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_18;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_22;
import cc.novoline.utils.notifications.Notification;
import cc.novoline.utils.notifications.NotificationType;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NotificationManager {
   private final int DEFAULT_DELAY = 2000;
   private final List NOTIFICATIONS = new CopyOnWriteArrayList();

   public void b() {
      NotificationType.b();
      Iterator var2 = this.NOTIFICATIONS.iterator();
      if(var2.hasNext()) {
         Notification var3 = (Notification)var2.next();
         int var4 = this.NOTIFICATIONS.indexOf(var3) * 37;
         if(var3.getY() < (double)(50 + var4)) {
            var3.setY(MathHelper.clamp_double(var3.getY() + 0.5D * (double)(2000 / Minecraft.getInstance().getDebugFPS()), 0.0D, (double)(50 + var4)));
         }

         if(var3.getY() > (double)(50 + var4)) {
            var3.setY(MathHelper.clamp_double(var3.getY() - 0.25D * (double)(2000 / Minecraft.getInstance().getDebugFPS()), (double)(50 + var4), 99999.0D));
         }

         ++var3.e;
         String var5 = var3.getDelay() / 1000 + "";
         String var6 = " (" + var5.substring(0, var5.indexOf(".") + 2) + "s) ";
         FontRenderer var7 = Minecraft.getInstance().fontRendererObj;
         if(var3.isExtending() && var3.getX() < (double)(Math.max(Fonts$SFBOLD$SFBOLD_18.SFBOLD_18.stringWidth(var3.getMessage() + var6), Fonts$SFBOLD$SFBOLD_22.SFBOLD_22.stringWidth(var3.getCallReason())) + 36)) {
            var3.setX(MathHelper.clamp_double(var3.getX() + 0.5D * (double)(2000 / Minecraft.getInstance().getDebugFPS()), 0.0D, (double)(Math.max(Fonts$SFBOLD$SFBOLD_18.SFBOLD_18.stringWidth(var3.getMessage() + var6), Fonts$SFBOLD$SFBOLD_22.SFBOLD_22.stringWidth(var3.getCallReason())) + 36)));
            var3.getTimer().reset();
         }

         var3.setExtending(false);
         if(!var3.isExtending() && var3.getTimer().delay((double)(var3.getDelay() + 150)) && var3.getX() > 0.0D) {
            var3.setX(var3.getX() - 0.5D * (double)(2000 / Minecraft.getInstance().getDebugFPS()));
         }

         if(var3.getX() <= 0.0D) {
            this.remove(var3);
         }
      }

   }

   public void pop(@NotNull String var1, int var2, @Nullable NotificationType var3) {
      int[] var4 = NotificationType.b();
      Notification var5 = new Notification(var1, var2, var3);
      Iterator var6 = this.NOTIFICATIONS.iterator();
      if(var6.hasNext()) {
         Notification var7 = (Notification)var6.next();
         if(var5.getMessage().equalsIgnoreCase(var7.getMessage())) {
            if(var7.getX() >= (double)(Minecraft.getInstance().fontRendererObj.d(var1) + 63)) {
               var7.getTimer().reset();
            }

            return;
         }
      }

      var5.setExtending(true);
      var5.getTimer().reset();
      this.add(var5);
      if(((Boolean)((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).getnSounds().get()).booleanValue()) {
         if(var5.getNotificationType() == NotificationType.SUCCESS) {
            Minecraft.getInstance().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.pop"), 1.0F));
         }

         if(var5.getNotificationType() == NotificationType.ERROR) {
            Minecraft.getInstance().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.orb"), 0.0F));
         }
      }

      if(acE.b() == null) {
         NotificationType.b(new int[1]);
      }

   }

   public void pop(@NotNull String var1, @NotNull String var2, int var3, @Nullable NotificationType var4) {
      int[] var5 = NotificationType.b();
      Notification var6 = new Notification(var1, var2, var3, var4);
      Iterator var7 = this.NOTIFICATIONS.iterator();
      if(var7.hasNext()) {
         Notification var8 = (Notification)var7.next();
         if(var6.getMessage().equalsIgnoreCase(var8.getMessage())) {
            if(var8.getX() >= (double)(Minecraft.getInstance().fontRendererObj.d(var2) + 63)) {
               var8.getTimer().reset();
            }

            return;
         }
      }

      var6.setExtending(true);
      var6.getTimer().reset();
      this.add(var6);
   }

   public void pop(@NotNull String var1, @Nullable NotificationType var2) {
      this.pop(var1, 2000, var2);
   }

   public void pop(@NotNull String var1, @NotNull String var2, @Nullable NotificationType var3) {
      this.pop(var1, var2, 2000, var3);
   }

   public void add(@NotNull Notification var1) {
      var1.setExtending(true);
      var1.getTimer().reset();
      this.NOTIFICATIONS.add(var1);
   }

   public void remove(@Nullable Notification var1) {
      this.NOTIFICATIONS.remove(var1);
   }

   public List getNotifications() {
      return this.NOTIFICATIONS;
   }
}
