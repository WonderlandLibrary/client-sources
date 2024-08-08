package com.example.editme.commands;

import com.example.editme.util.client.Friends;
import com.example.editme.util.command.syntax.ChunkBuilder;
import com.example.editme.util.command.syntax.parsers.EnumParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.util.UUIDTypeAdapter;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class FriendCommand extends Command {
   private void lambda$call$0(String[] var1) {
      Friends.Friend var2 = this.getFriendByName(var1[1]);
      if (var2 == null) {
         Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Failed to find UUID of ").append(var1[1])));
      } else {
         Friends var10000 = Friends.INSTANCE;
         ((ArrayList)Friends.friends.getValue()).add(var2);
         Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&b").append(var2.getUsername()).append("&r is now your homie")));
      }
   }

   public void call(String[] var1) {
      Friends var10000;
      if (var1[0] != null) {
         if (var1[1] == null) {
            Command.sendChatMessage(String.format(Friends.isFriend(var1[0]) ? "Yes, %s is your homie." : "No, %s isn't a homie of yours.", var1[0]));
            Command.sendChatMessage(String.format(Friends.isFriend(var1[0]) ? "Yes, %s is your homie." : "No, %s isn't a homie of yours.", var1[0]));
         } else if (!var1[0].equalsIgnoreCase("add") && !var1[0].equalsIgnoreCase("new")) {
            if (!var1[0].equalsIgnoreCase("del") && !var1[0].equalsIgnoreCase("remove") && !var1[0].equalsIgnoreCase("delete")) {
               Command.sendChatMessage("Please specify either &6add&r or &6remove");
            } else if (!Friends.isFriend(var1[1])) {
               Command.sendChatMessage("That nibba isn't your homie");
            } else {
               var10000 = Friends.INSTANCE;
               Friends.Friend var5 = (Friends.Friend)((ArrayList)Friends.friends.getValue()).stream().filter(FriendCommand::lambda$call$1).findFirst().get();
               var10000 = Friends.INSTANCE;
               ((ArrayList)Friends.friends.getValue()).remove(var5);
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&b").append(var5.getUsername()).append("&r has been unhomied")));
            }
         } else if (Friends.isFriend(var1[1])) {
            Command.sendChatMessage("That player is already your homie");
         } else {
            (new Thread(this::lambda$call$0)).start();
         }
      } else {
         var10000 = Friends.INSTANCE;
         if (((ArrayList)Friends.friends.getValue()).isEmpty()) {
            Command.sendChatMessage("You currently don't have any homies added. &bfriend add <name>&r to add one.");
         } else {
            String var2 = "";
            var10000 = Friends.INSTANCE;

            Friends.Friend var4;
            for(Iterator var3 = ((ArrayList)Friends.friends.getValue()).iterator(); var3.hasNext(); var2 = String.valueOf((new StringBuilder()).append(var2).append(var4.getUsername()).append(", "))) {
               var4 = (Friends.Friend)var3.next();
            }

            var2 = var2.substring(0, var2.length() - 2);
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Your homies: ").append(var2)));
         }
      }
   }

   private static boolean lambda$getFriendByName$2(String var0, NetworkPlayerInfo var1) {
      return var1.func_178845_a().getName().equalsIgnoreCase(var0);
   }

   private static String convertStreamToString(InputStream var0) {
      Scanner var1 = (new Scanner(var0)).useDelimiter("\\A");
      String var2 = var1.hasNext() ? var1.next() : "/";
      return var2;
   }

   private static boolean lambda$call$1(String[] var0, Friends.Friend var1) {
      return var1.getUsername().equalsIgnoreCase(var0[1]);
   }

   public FriendCommand() {
      super("friend", (new ChunkBuilder()).append("mode", true, new EnumParser(new String[]{"add", "del"})).append("name").build());
   }

   private Friends.Friend getFriendByName(String var1) {
      ArrayList var2 = new ArrayList(Minecraft.func_71410_x().func_147114_u().func_175106_d());
      NetworkPlayerInfo var3 = (NetworkPlayerInfo)var2.stream().filter(FriendCommand::lambda$getFriendByName$2).findFirst().orElse((Object)null);
      if (var3 != null) {
         Friends.Friend var10 = new Friends.Friend(var3.func_178845_a().getName(), var3.func_178845_a().getId());
         return var10;
      } else {
         Command.sendChatMessage("Player isn't online. Looking up UUID..");
         String var4 = requestIDs(String.valueOf((new StringBuilder()).append("[\"").append(var1).append("\"]")));
         if (var4 != null && !var4.isEmpty()) {
            JsonElement var5 = (new JsonParser()).parse(var4);
            if (var5.getAsJsonArray().size() == 0) {
               Command.sendChatMessage("Couldn't find player ID. (1)");
            } else {
               try {
                  String var6 = var5.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                  String var7 = var5.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                  Friends.Friend var8 = new Friends.Friend(var7, UUIDTypeAdapter.fromString(var6));
                  return var8;
               } catch (Exception var9) {
                  var9.printStackTrace();
                  Command.sendChatMessage("Couldn't find player ID. (2)");
               }
            }
         } else {
            Command.sendChatMessage("Couldn't find player ID. Are you connected to the internet? (0)");
         }

         return null;
      }
   }

   private static String requestIDs(String var0) {
      try {
         String var1 = "https://api.mojang.com/profiles/minecraft";
         URL var3 = new URL(var1);
         HttpURLConnection var4 = (HttpURLConnection)var3.openConnection();
         var4.setConnectTimeout(5000);
         var4.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
         var4.setDoOutput(true);
         var4.setDoInput(true);
         var4.setRequestMethod("POST");
         OutputStream var5 = var4.getOutputStream();
         var5.write(var0.getBytes("UTF-8"));
         var5.close();
         BufferedInputStream var6 = new BufferedInputStream(var4.getInputStream());
         String var7 = convertStreamToString(var6);
         var6.close();
         var4.disconnect();
         return var7;
      } catch (Exception var8) {
         return null;
      }
   }
}
