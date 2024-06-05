package cc.novoline;

import cc.novoline.Novoline;
import cc.novoline.SimpleEventListener$Holder;
import cc.novoline.utils.messages.MessageFactory;
import cc.novoline.utils.notifications.NotificationType;
import net.minecraft.client.Minecraft;
import net.skidunion.B;
import net.skidunion.U;
import net.skidunion.r;
import net.skidunion.s;
import net.skidunion.t;
import net.skidunion.u;
import net.skidunion.y;
import org.jetbrains.annotations.NotNull;

public class SimpleEventListener extends U {
   private String lastMessagedUsername;

   public static SimpleEventListener getInstance() {
      return SimpleEventListener$Holder.access$000(SimpleEventListener$Holder.INSTANCE);
   }

   public void a(@NotNull u var1) {
      if(Minecraft.getInstance().player != null && !var1.d()) {
         Minecraft.getInstance().player.addChatComponentMessage(MessageFactory.broadcast(var1.a()));
      }

   }

   public void a(@NotNull r var1) {
      int var2 = Novoline.C();
      if(Minecraft.getInstance().player != null) {
         Minecraft.getInstance().player.addChatComponentMessage(MessageFactory.a(var1.a()));
      }

   }

   public void a(@NotNull y var1) {
      if(var1.a() == 4004 && Minecraft.getInstance().player != null) {
         Novoline.getInstance().getNotificationManager().pop("Could not connect to IRC", "The authorization token was invalid, please restart the client", 2000, NotificationType.ERROR);
      }

   }

   public void a(@NotNull s var1) {
      int var2 = Novoline.C();
      if(Minecraft.getInstance().player != null) {
         this.lastMessagedUsername = var1.a().a().c();
         Minecraft.getInstance().player.addChatComponentMessage(MessageFactory.b(var1.a().a(), var1.a().b()));
      }

   }

   public void a(@NotNull t var1) {
      System.out.println("Kicked by an administrator.");
      System.exit(0);
   }

   public void a(@NotNull B var1) {
   }

   public String getLastMessagedUsername() {
      return this.lastMessagedUsername;
   }

   public void setLastMessagedUsername(String var1) {
      this.lastMessagedUsername = var1;
   }
}
