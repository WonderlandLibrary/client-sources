package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventPacket;
import exhibition.event.impl.EventRenderGui;
import exhibition.management.notifications.user.Notifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.ChatUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.network.play.server.S38PacketPlayerListItem;

public class AntiVanish extends Module {
   private List vanished = new CopyOnWriteArrayList();
   private int delay = -3200;
   private String name = "Failed Check?";

   public AntiVanish(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventPacket.class, EventRenderGui.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre()) {
            if (!this.vanished.isEmpty()) {
               if (this.delay > 3200) {
                  this.vanished.clear();
                  Notifications.getManager().post("Vanish Cleared", "§fVanish List has been §6Cleared.", 2500L, Notifications.Type.NOTIFY);
                  this.delay = -3200;
               } else {
                  ++this.delay;
               }
            }

            UUID uuid;
            try {
               for(Iterator var3 = this.vanished.iterator(); var3.hasNext(); this.vanished.remove(uuid)) {
                  uuid = (UUID)var3.next();
                  if (mc.getNetHandler().func_175102_a(uuid) != null && this.vanished.contains(uuid)) {
                     Notifications.getManager().post("Vanish Warning", "§b" + mc.getNetHandler().func_175102_a(uuid).getDisplayName(), 2500L, Notifications.Type.NOTIFY);
                     DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                     Date date = new Date();
                     ChatUtil.printChat("[" + dateFormat.format(date) + "] §b" + mc.getNetHandler().func_175102_a(uuid).getDisplayName() + "is no longer §6Vanished");
                  }
               }
            } catch (Exception var10) {
               Notifications.getManager().post("Vanish Error", "§cSomething happened.");
            }
         }
      }

      if (event instanceof EventPacket) {
         EventPacket ep = (EventPacket)event;
         if (ep.isIncoming() && mc.theWorld != null && ep.getPacket() instanceof S38PacketPlayerListItem) {
            S38PacketPlayerListItem listItem = (S38PacketPlayerListItem)ep.getPacket();
            if (listItem.func_179768_b() == S38PacketPlayerListItem.Action.UPDATE_LATENCY) {
               Iterator var13 = listItem.func_179767_a().iterator();

               while(var13.hasNext()) {
                  Object o = var13.next();
                  S38PacketPlayerListItem.AddPlayerData data = (S38PacketPlayerListItem.AddPlayerData)o;
                  if (mc.getNetHandler().func_175102_a(data.field_179964_d.getId()) == null && !this.checkList(data.field_179964_d.getId())) {
                     String name = this.getName(data.field_179964_d.getId());
                     Notifications.getManager().post("Vanish Warning", "§b" + name + "is §6Vanished!", 2500L, Notifications.Type.WARNING);
                     DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                     Date date = new Date();
                     ChatUtil.printChat("[" + dateFormat.format(date) + "] §b" + name + "is no longer §6Vanished");
                     this.delay = -3200;
                  }
               }
            }
         }
      }

   }

   public String getName(UUID uuid) {
      Thread thread = new Thread(() -> {
         try {
            URL url = new URL("https://namemc.com/profile/" + uuid.toString());
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Thread.sleep(500L);

            String line;
            while((line = reader.readLine()) != null) {
               if (line.contains("<title>")) {
                  this.name = line.split("§")[0].trim().replaceAll("<title>", "").replaceAll("</title>", "").replaceAll("– Minecraft Profile – NameMC", "").replaceAll("â€“ Minecraft Profile â€“ NameMC", "");
               }
            }

            reader.close();
         } catch (Exception var6) {
            var6.printStackTrace();
            this.name = "(Failed) " + uuid;
            Notifications.getManager().post("Failed Name Check", this.name, 2500L, Notifications.Type.WARNING);
         }

      });
      thread.start();
      return this.name;
   }

   private boolean checkList(UUID uuid) {
      if (this.vanished.contains(uuid)) {
         return true;
      } else {
         this.vanished.add(uuid);
         return false;
      }
   }
}
