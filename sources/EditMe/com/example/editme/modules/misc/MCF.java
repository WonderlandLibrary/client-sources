package com.example.editme.modules.misc;

import com.example.editme.commands.Command;
import com.example.editme.events.EventPlayerUpdate;
import com.example.editme.modules.Module;
import com.example.editme.util.client.Friends;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.util.UUIDTypeAdapter;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import org.lwjgl.input.Mouse;

@Module.Info(
   name = "MCF",
   category = Module.Category.MISC,
   description = "middle click homie"
)
public class MCF extends Module {
   @EventHandler
   private Listener onPlayerUpdate = new Listener(this::lambda$new$1, new Predicate[0]);
   int cooldown;

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

   private static boolean lambda$getFriendByName$2(String var0, NetworkPlayerInfo var1) {
      return var1.func_178845_a().getName().equalsIgnoreCase(var0);
   }

   private static String convertStreamToString(InputStream var0) {
      Scanner var1 = (new Scanner(var0)).useDelimiter("\\A");
      String var2 = var1.hasNext() ? var1.next() : "/";
      return var2;
   }

   private static boolean lambda$null$0(Entity var0, Friends.Friend var1) {
      return var1.getUsername().equalsIgnoreCase(var0.func_70005_c_());
   }

   public void onUpdate() {
      if (this.cooldown > 0) {
         --this.cooldown;
      }

   }

   private Friends.Friend getFriendByName(String var1) {
      ArrayList var2 = new ArrayList(Minecraft.func_71410_x().func_147114_u().func_175106_d());
      NetworkPlayerInfo var3 = (NetworkPlayerInfo)var2.stream().filter(MCF::lambda$getFriendByName$2).findFirst().orElse((Object)null);
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

   private void lambda$new$1(EventPlayerUpdate var1) {
      try {
         if (this.cooldown <= 0) {
            if (mc.field_71462_r == null) {
               if (Mouse.isButtonDown(2)) {
                  RayTraceResult var2 = mc.field_71476_x;
                  if (var2 != null && var2.field_72313_a == Type.ENTITY) {
                     Entity var3 = var2.field_72308_g;
                     if (var3 != null && var3 instanceof EntityPlayer) {
                        Friends var10000;
                        Friends.Friend var4;
                        if (Friends.isFriend(var3.func_70005_c_())) {
                           var10000 = Friends.INSTANCE;
                           var4 = (Friends.Friend)((ArrayList)Friends.friends.getValue()).stream().filter(MCF::lambda$null$0).findFirst().get();
                           var10000 = Friends.INSTANCE;
                           ((ArrayList)Friends.friends.getValue()).remove(var4);
                           Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&b").append(var4.getUsername()).append("&r has been unhomied")));
                           this.cooldown = 20;
                        } else {
                           var4 = this.getFriendByName(var3.func_70005_c_());
                           if (var4 == null) {
                              Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Failed to find UUID of ").append(var3.func_70005_c_())));
                              return;
                           }

                           var10000 = Friends.INSTANCE;
                           ((ArrayList)Friends.friends.getValue()).add(var4);
                           this.cooldown = 20;
                           Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&b").append(var4.getUsername()).append("&r is now your homie")));
                        }

                     }
                  }
               }
            }
         }
      } catch (Exception var5) {
      }
   }
}
