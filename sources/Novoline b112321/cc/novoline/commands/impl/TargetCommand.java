package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.commands.impl.TargetCommand$1;
import cc.novoline.modules.PlayerManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.utils.java.Checks;
import cc.novoline.utils.messages.MessageFactory;
import cc.novoline.utils.messages.TextMessage;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.Arrays;
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

public final class TargetCommand extends NovoCommand {
   public TargetCommand(Novoline var1) {
      super(var1, "target", (Iterable)Arrays.asList(new String[]{"tar", "target"}));
   }

   public void process(String[] var1) {
      int[] var2 = a_E.b();
      if(var1.length < 1) {
         this.a("Targets help:", ".target", new Uj[]{MessageFactory.a("add (name)", "adds target"), MessageFactory.a("remove (name)", "removes target"), MessageFactory.a("list", "shows targets")});
      } else {
         if(var1.length == 1) {
            String var3 = var1[0];
            String var4 = var3.toLowerCase();
            byte var5 = -1;
            switch(var4.hashCode()) {
            case 3322014:
               if(!var4.equals("list")) {
                  break;
               }

               var5 = 0;
            case 108:
               if(!var4.equals("l")) {
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
               List var6 = this.novoline.getPlayerManager().whoHas(PlayerManager$EnumPlayerType.TARGET);
               TextMessage var7 = MessageFactory.text("Targets list:");
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
               if(this.novoline.getPlayerManager().removeType(PlayerManager$EnumPlayerType.TARGET, TargetCommand::lambda$process$0)) {
                  this.notify("Target list was cleared");
               }

               this.notify("Target list is empty");
            }

            PlayerManager$EnumPlayerType var15 = this.novoline.getPlayerManager().getType(var3);
            if(var15 == PlayerManager$EnumPlayerType.FRIEND || var15 == null) {
               this.add(var3);
            }

            if(var15 == PlayerManager$EnumPlayerType.TARGET) {
               this.remove(var3);
            }
         }

         try {
            String var11 = var1[0];
            String var12 = var1[1];
            String var13 = var11.toLowerCase();
            byte var14 = -1;
            switch(var13.hashCode()) {
            case 94746189:
               if(!var13.equals("clear")) {
                  break;
               }

               var14 = 0;
            case 96417:
               if(!var13.equals("add")) {
                  break;
               }

               var14 = 1;
            case -934610812:
               if(!var13.equals("remove")) {
                  break;
               }

               var14 = 2;
            case -1335458389:
               if(!var13.equals("delete")) {
                  break;
               }

               var14 = 3;
            case 99339:
               if(!var13.equals("del")) {
                  break;
               }

               var14 = 4;
            case 112794:
               if(var13.equals("rem")) {
                  var14 = 5;
               }
            }

            switch(var14) {
            case 0:
               this.novoline.getPlayerManager().removeType(PlayerManager$EnumPlayerType.TARGET, TargetCommand::lambda$process$1);
            case 1:
               this.add(var12);
            case 2:
            case 3:
            case 4:
            case 5:
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
      a_E.b();
      PlayerManager var4 = this.novoline.getPlayerManager();
      PlayerManager$EnumPlayerType var5 = var4.getType(var3);
      switch(TargetCommand$1.$SwitchMap$cc$novoline$modules$PlayerManager$EnumPlayerType[var5.ordinal()]) {
      case 1:
         this.notifyError(var1 + " is friend");
         return;
      case 2:
         this.notifyError(var1 + " is target already!");
         return;
      default:
         boolean var6 = var4.setType(var3, PlayerManager$EnumPlayerType.TARGET);
         this.notify("Added " + var1 + " to targets!");
         PlayerManager var10000 = var4;

         try {
            var10000.getConfig().save();
         } catch (IOException var8) {
            this.notifyError("Can\'t save to file");
            var4.getLogger().warn("An error occurred while saving targets list", var8);
            this.notifyError("Cannot add " + var1 + " to targets!");
         }

      }
   }

   public void remove(String var1) {
      a_E.b();
      Checks.notBlank(var1, "name");
      String var3 = var1.toLowerCase();
      PlayerManager var4 = this.novoline.getPlayerManager();
      if(var4.getType(var3) != PlayerManager$EnumPlayerType.TARGET) {
         this.notifyError(var1 + " is not target!");
      } else {
         boolean var5 = var4.removePlayer(var3);
         this.notify("Removed " + var1 + " from targets!");
         PlayerManager var10000 = var4;

         try {
            var10000.getConfig().save();
         } catch (IOException var7) {
            this.notifyError("Can\'t save to file");
            var4.getLogger().warn("An error occurred while saving targets list", var7);
            this.notifyError("Cannot remove " + var1 + " from targets!");
         }

      }
   }

   public List completeTabOptions(String[] var1) {
      int[] var2 = a_E.b();
      switch(var1.length) {
      case 1:
         return this.completeTab(Stream.of(new String[]{"add", "remove", "list"}), var1[0], true);
      case 2:
         PlayerManager var3 = this.novoline.getPlayerManager();
         if(var1[0].equalsIgnoreCase("add")) {
            return this.completeTab(NetHandlerPlayClient.playerInfoMap.values().stream().map(NetworkPlayerInfo::getGameProfile).map(GameProfile::getName).filter(TargetCommand::lambda$completeTabOptions$2), var1[1], true);
         } else {
            if(var1[0].equalsIgnoreCase("remove")) {
               return this.completeTab(var3.whoHas(PlayerManager$EnumPlayerType.TARGET), var1[1], true);
            }

            return null;
         }
      default:
         return null;
      }
   }

   private static boolean lambda$completeTabOptions$2(PlayerManager var0, String var1) {
      a_E.b();
      PlayerManager$EnumPlayerType var3 = var0.getType(var1);
      return var3 != PlayerManager$EnumPlayerType.TARGET && var3 != PlayerManager$EnumPlayerType.FRIEND;
   }

   private static boolean lambda$process$1(Entry var0) {
      return true;
   }

   private static boolean lambda$process$0(Entry var0) {
      return true;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
