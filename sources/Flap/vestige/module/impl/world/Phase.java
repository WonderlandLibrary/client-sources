package vestige.module.impl.world;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import org.jetbrains.annotations.NotNull;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PacketReceiveEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.player.Blink;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.ModeSetting;

public class Phase extends Module {
   private final ModeSetting mode = new ModeSetting("Mode", "Grim", new String[]{"Grim", "Watchdog", "Mush"});

   public Phase() {
      super("Phase", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.mode});
   }

   public void onEnable() {
      this.activatephase();
   }

   public void activatephase() {
      String var1 = this.mode.getMode();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case 2228079:
         if (var1.equals("Grim")) {
            var2 = 0;
         }
         break;
      case 2410013:
         if (var1.equals("Mush")) {
            var2 = 2;
         }
         break;
      case 609795629:
         if (var1.equals("Watchdog")) {
            var2 = 1;
         }
      }

      switch(var2) {
      case 0:
         EntityPlayerSP var10000 = mc.thePlayer;
         var10000.posY -= 10.0D;
      case 1:
      default:
         break;
      case 2:
         mc.thePlayer.serverPosY = -3;
         mc.thePlayer.posY = -3.0D;
      }

   }

   @Listener
   public void onPacketReceiveEvent(@NotNull PacketReceiveEvent event) {
      if (event == null) {
         $$$reportNull$$$0(0);
      }

      Packet<?> packet = event.getPacket();
      String var3 = this.mode.getMode();
      byte var4 = -1;
      switch(var3.hashCode()) {
      case 2228079:
         if (var3.equals("Grim")) {
            var4 = 0;
         }
         break;
      case 609795629:
         if (var3.equals("Watchdog")) {
            var4 = 1;
         }
      }

      S02PacketChat s02PacketChat;
      String chat;
      byte var8;
      switch(var4) {
      case 0:
         if (packet instanceof S02PacketChat) {
            s02PacketChat = (S02PacketChat)packet;
            chat = s02PacketChat.getChatComponent().getUnformattedText();
            var8 = -1;
            switch(chat.hashCode()) {
            case -1046175526:
               if (chat.equals("SKYWARS Começando partida")) {
                  var8 = 0;
               }
            default:
               switch(var8) {
               case 0:
                  ((Blink)Flap.instance.getModuleManager().getModule(Blink.class)).toggle();
               }
            }
         }
      case 1:
         if (packet instanceof S02PacketChat) {
            s02PacketChat = (S02PacketChat)packet;
            chat = s02PacketChat.getChatComponent().getUnformattedText();
            var8 = -1;
            switch(chat.hashCode()) {
            case -815930779:
               if (chat.equals("§r§r§r                               §r§f§lSkyWars Duel§r")) {
                  var8 = 1;
               }
               break;
            case 445216674:
               if (chat.equals("Cages opened! FIGHT!")) {
                  var8 = 0;
               }
               break;
            case 1865777181:
               if (chat.equals("§r§eCages opened! §r§cFIGHT!§r")) {
                  var8 = 2;
               }
            }

            switch(var8) {
            case 0:
            case 1:
            case 2:
               ((Blink)Flap.instance.getModuleManager().getModule(Blink.class)).toggle();
            }
         }
      default:
      }
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "event", "vestige/module/impl/world/Phase", "onPacketReceiveEvent"));
   }
}
