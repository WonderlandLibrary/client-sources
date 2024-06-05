package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.commands.impl.FriendCommand$1;
import cc.novoline.modules.PlayerManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.utils.java.Checks;
import cc.novoline.utils.messages.MessageFactory;
import cc.novoline.utils.messages.TextMessage;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.Uj;
import net.a_E;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public final class FriendCommand extends NovoCommand {
   public FriendCommand(Novoline var1) {
      super(var1, "f", "Adds friend", "friend");
   }

   private void sendHelp() {
      this.a("Friends help:", ".friend", new Uj[]{MessageFactory.a("add (name)", "adds friend"), MessageFactory.a("remove (name)", "removes friend"), MessageFactory.a("list", "shows friends")});
   }

   public void process(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.sendHelp();
      } else {
         if(var1.length == 1) {
            String var3 = var1[0];
            String var4 = var3.toLowerCase();
            byte var5 = -1;
            switch(var4.hashCode()) {
            case 108:
               if(!var4.equals("l")) {
                  break;
               }

               var5 = 0;
            case 3322014:
               if(!var4.equals("list")) {
                  break;
               }

               var5 = 1;
            case 94746189:
               if(var4.equals("clear")) {
                  var5 = 2;
               }
            }

            switch(var5) {
            case 0:
            case 1:
               List var6 = this.novoline.getPlayerManager().whoHas(PlayerManager$EnumPlayerType.FRIEND);
               TextMessage var7 = MessageFactory.text("Friends list:");
               if(var6.isEmpty()) {
                  var7.append((IChatComponent)MessageFactory.text(" (empty)", EnumChatFormatting.RED));
               }

               this.send(var7, true);
               Iterator var8 = var6.iterator();
               if(var8.hasNext()) {
                  String var9 = (String)var8.next();
                  this.send(MessageFactory.text(" - ").append((IChatComponent)MessageFactory.text(var9, EnumChatFormatting.GRAY)));
               }
            case 2:
               if(this.novoline.getPlayerManager().removeType(PlayerManager$EnumPlayerType.FRIEND, FriendCommand::lambda$process$0)) {
                  this.notify("Friend list was cleared");
               }

               this.notify("Friend list is empty");
            default:
               PlayerManager$EnumPlayerType var14 = this.novoline.getPlayerManager().getType(var3);
               if(var14 == PlayerManager$EnumPlayerType.TARGET) {
                  this.add(var3);
               }

               if(var14 == PlayerManager$EnumPlayerType.FRIEND) {
                  this.remove(var3);
               }
            }
         }

         try {
            String var11 = var1[0];
            String var12 = var1[1];
            String var13 = var11.toLowerCase();
            byte var15 = -1;
            switch(var13.hashCode()) {
            case 96417:
               if(!var13.equals("add")) {
                  break;
               }

               var15 = 0;
            case -934610812:
               if(!var13.equals("remove")) {
                  break;
               }

               var15 = 1;
            case -1335458389:
               if(!var13.equals("delete")) {
                  break;
               }

               var15 = 2;
            case 99339:
               if(!var13.equals("del")) {
                  break;
               }

               var15 = 3;
            case 112794:
               if(var13.equals("rem")) {
                  var15 = 4;
               }
            }

            switch(var15) {
            case 0:
               this.add(var12);
            case 1:
            case 2:
            case 3:
            case 4:
               this.remove(var12);
            default:
               this.notifyError("Illegal command specified: " + var1[0] + "!");
            }
         } catch (Exception var10) {
            var10.printStackTrace();
         }

      }
   }

   public void add(String var1) {
      Checks.notBlank(var1, "name");
      String var3 = var1.toLowerCase();
      PlayerManager var4 = this.novoline.getPlayerManager();
      a_E.b();
      PlayerManager$EnumPlayerType var5 = var4.getType(var3);
      switch(FriendCommand$1.$SwitchMap$cc$novoline$modules$PlayerManager$EnumPlayerType[var5.ordinal()]) {
      case 1:
         if(var4.removeType(var3, PlayerManager$EnumPlayerType.TARGET)) {
            this.notifyWarning("Removed " + var1 + " from targets!");
         }

         this.notifyWarning("Cannot remove " + var1 + " from targets!");
      case 2:
         this.notifyError(var1 + " is friend already!");
         return;
      default:
         boolean var6 = var4.setType(var3, PlayerManager$EnumPlayerType.FRIEND);
         this.notify("Added " + var1 + " to friends!");
         PlayerManager var10000 = var4;

         try {
            var10000.getConfig().save();
         } catch (IOException var8) {
            this.notifyError("Can\'t save to file");
            var4.getLogger().warn("An error occurred while saving friends list", var8);
            this.notifyError("Cannot add " + var1 + " to friends!");
         }

      }
   }

   public void remove(String var1) {
      a_E.b();
      Checks.notBlank(var1, "name");
      String var3 = var1.toLowerCase();
      PlayerManager var4 = this.novoline.getPlayerManager();
      if(var4.getType(var3) != PlayerManager$EnumPlayerType.FRIEND) {
         this.notifyError(var1 + " is not friend!");
      } else {
         boolean var5 = var4.removePlayer(var3);
         this.notify("Removed " + var1 + " from friends!");
         PlayerManager var10000 = var4;

         try {
            var10000.getConfig().save();
         } catch (IOException var7) {
            this.notifyError("Can\'t save to file");
            var4.getLogger().warn("An error occurred while saving friends list", var7);
            this.notifyError("Cannot remove " + var1 + " from friends!");
         }

      }
   }

   public List completeTabOptions(String[] var1) {
      int[] var2 = a_E.b();
      switch(var1.length) {
      case 1:
         return this.completeTab(Stream.of(new String[]{"add", "remove", "list"}), var1[0], true);
      case 2:
         if(var1[0].equalsIgnoreCase("add")) {
            return this.completeTab(NetHandlerPlayClient.playerInfoMap.values().stream().map(NetworkPlayerInfo::getGameProfile).map(GameProfile::getName).filter(this::lambda$completeTabOptions$1), var1[1], true);
         } else {
            if(var1[0].equalsIgnoreCase("remove")) {
               return this.completeTab(this.novoline.getPlayerManager().whoHas(PlayerManager$EnumPlayerType.FRIEND), var1[1], true);
            }

            return null;
         }
      default:
         return null;
      }
   }

   private boolean lambda$completeTabOptions$1(String var1) {
      int[] var2 = a_E.b();
      return !this.novoline.getPlayerManager().hasType(var1, PlayerManager$EnumPlayerType.FRIEND);
   }

   private static boolean lambda$process$0(Entry var0) {
      return true;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
