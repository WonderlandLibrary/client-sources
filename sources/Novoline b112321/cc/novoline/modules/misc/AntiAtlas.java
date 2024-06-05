package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.LoadWorldEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.utils.Timer;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import org.jetbrains.annotations.NotNull;

public class AntiAtlas extends AbstractModule {
   private List reported = new CopyOnWriteArrayList();
   private Timer timer = new Timer();

   public AntiAtlas(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.MISC, "AntiAtlas", "Anti Atlas");
   }

   @EventTarget
   public void onUpdate(PlayerUpdateEvent var1) {
      WindowedFullscreen.a();
      Iterator var3 = this.mc.getNetHandler().getPlayerInfoMap().iterator();
      if(var3.hasNext()) {
         NetworkPlayerInfo var4 = (NetworkPlayerInfo)var3.next();
         UUID var5 = var4.getGameProfile().getId();
         String var6 = var4.getGameProfile().getName();
         if(this.timer.delay(1000.0D) && !this.reported.contains(var5) && !var5.equals(this.mc.player.getEntityUniqueID())) {
            this.sendPacketNoEvent(new C01PacketChatMessage("/report " + var6 + " killaura fly speed"));
            this.reported.add(var5);
            this.timer.reset();
         }
      }

   }

   @EventTarget
   public void onWorldLoad(LoadWorldEvent var1) {
      this.reported.clear();
      this.timer.reset();
   }

   @EventTarget
   public void onPacket(PacketEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getDirection().equals(PacketDirection.INCOMING) && var1.getPacket() instanceof S02PacketChat) {
         S02PacketChat var3 = (S02PacketChat)var1.getPacket();
         if(var3.getChatComponent().getFormattedText().startsWith("§cThere is no player named")) {
            var1.setCancelled(true);
         }
      }

   }
}
