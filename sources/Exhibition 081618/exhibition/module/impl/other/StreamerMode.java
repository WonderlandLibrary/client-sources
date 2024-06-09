package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.misc.ChatUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;

public class StreamerMode extends Module {
   private String NAMEPROTECT = "PROTECT";
   private String SCRAMBLE = "SCRAMBLE";
   private String HIDESCORE = "HIDESCORE";
   private String HIDETAB = "HIDETAB";
   private String SPOOFSKINS = "SPOOFSKINS";
   public static boolean scrambleNames;
   public static boolean hideTab;
   public static boolean hideScore;
   public static boolean spoofSkins;
   public static List strings = new ArrayList();

   public StreamerMode(ModuleData data) {
      super(data);
      this.settings.put(this.NAMEPROTECT, new Setting(this.NAMEPROTECT, true, "Protects your name."));
      this.settings.put(this.SCRAMBLE, new Setting(this.SCRAMBLE, true, "Scrambles other player names."));
      this.settings.put(this.HIDESCORE, new Setting(this.HIDESCORE, false, "Hides scoreboard."));
      this.settings.put(this.SPOOFSKINS, new Setting(this.SPOOFSKINS, false, "Spoofs player skins."));
      this.settings.put(this.HIDETAB, new Setting(this.HIDETAB, false, "Hides tablist/player list."));
   }

   public void onDisable() {
      super.onDisable();
      scrambleNames = false;
      hideScore = false;
      hideTab = false;
      spoofSkins = false;
      strings.clear();
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventPacket.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre()) {
            scrambleNames = ((Boolean)((Setting)this.settings.get(this.SCRAMBLE)).getValue()).booleanValue();
            spoofSkins = ((Boolean)((Setting)this.settings.get(this.SPOOFSKINS)).getValue()).booleanValue();
            hideScore = ((Boolean)((Setting)this.settings.get(this.HIDESCORE)).getValue()).booleanValue();
            hideTab = ((Boolean)((Setting)this.settings.get(this.HIDETAB)).getValue()).booleanValue();
            NetHandlerPlayClient var4 = mc.thePlayer.sendQueue;
            List players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(var4.func_175106_d());
            Iterator var5 = players.iterator();

            Object o;
            while(var5.hasNext()) {
               o = var5.next();
               NetworkPlayerInfo info = (NetworkPlayerInfo)o;
               if (info == null) {
                  break;
               }

               if (!strings.contains(info.getGameProfile().getName())) {
                  strings.add(info.getGameProfile().getName());
               }
            }

            var5 = mc.theWorld.getLoadedEntityList().iterator();

            while(var5.hasNext()) {
               o = var5.next();
               if (o instanceof EntityPlayer && !strings.contains(((EntityPlayer)o).getName())) {
                  strings.add(((EntityPlayer)o).getName());
               }
            }
         }
      }

      if (event instanceof EventPacket) {
         EventPacket ep = (EventPacket)event;
         if (ep.isIncoming() && ep.getPacket() instanceof S02PacketChat && ((Boolean)((Setting)this.settings.get(this.NAMEPROTECT)).getValue()).booleanValue()) {
            S02PacketChat packet = (S02PacketChat)ep.getPacket();
            if (packet.func_148915_c().getUnformattedText().contains(mc.thePlayer.getName())) {
               String temp = packet.func_148915_c().getFormattedText();
               ChatUtil.printChat(temp.replaceAll(mc.thePlayer.getName(), "§9You§r"));
               ep.setCancelled(true);
            } else {
               String[] list = new String[]{"join", "left", "leave", "leaving", "send", "lobby", "server", "fell", "died", "slain", "burn", "void", "disconnect", "kill", "by", "was", "quit", "blood", "game"};
               String[] var13 = list;
               int var14 = list.length;

               for(int var15 = 0; var15 < var14; ++var15) {
                  String str = var13[var15];
                  if (packet.func_148915_c().getUnformattedText().toLowerCase().contains(str)) {
                     ep.setCancelled(true);
                     break;
                  }
               }
            }
         }
      }

   }
}
