package com.example.editme.util.client;

import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.regex.Pattern;

public class Friends {
   public static Setting friends;
   public static final Friends INSTANCE = new Friends();

   public static void initFriends() {
      friends = SettingsManager.custom("Friends", new ArrayList(), new Friends.FriendListConverter()).buildAndRegister("friends");
   }

   public static boolean isFriend(String var0) {
      return ((ArrayList)friends.getValue()).stream().anyMatch(Friends::lambda$isFriend$0);
   }

   private static boolean lambda$isFriend$0(String var0, Friends.Friend var1) {
      return var1.username.equalsIgnoreCase(var0);
   }

   private Friends() {
   }

   public static class FriendListConverter extends Converter {
      private String getUsernameByUUID(UUID var1, String var2) {
         String var3 = getSource(String.valueOf((new StringBuilder()).append("https://sessionserver.mojang.com/session/minecraft/profile/").append(var1.toString())));
         if (var3 != null && !var3.isEmpty()) {
            try {
               JsonElement var4 = (new JsonParser()).parse(var3);
               return var4.getAsJsonObject().get("name").getAsString();
            } catch (Exception var5) {
               var5.printStackTrace();
               System.err.println(var3);
               return var2;
            }
         } else {
            return var2;
         }
      }

      protected Object doForward(Object var1) {
         return this.doForward((ArrayList)var1);
      }

      protected Object doBackward(Object var1) {
         return this.doBackward((JsonElement)var1);
      }

      protected ArrayList doBackward(JsonElement var1) {
         String var2 = var1.getAsString();
         String[] var3 = var2.split(Pattern.quote("$"));
         ArrayList var4 = new ArrayList();
         String[] var5 = var3;
         int var6 = var3.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String var8 = var5[var7];

            try {
               String[] var9 = var8.split(";");
               String var10 = var9[0];
               UUID var11 = UUID.fromString(var9[1]);
               var4.add(new Friends.Friend(this.getUsernameByUUID(var11, var10), var11));
            } catch (Exception var12) {
            }
         }

         return var4;
      }

      private static String getSource(String var0) {
         try {
            URL var1 = new URL(var0);
            URLConnection var2 = var1.openConnection();
            BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.getInputStream()));
            StringBuilder var4 = new StringBuilder();

            String var5;
            while((var5 = var3.readLine()) != null) {
               var4.append(var5);
            }

            var3.close();
            return String.valueOf(var4);
         } catch (Exception var6) {
            return null;
         }
      }

      protected JsonElement doForward(ArrayList var1) {
         StringBuilder var2 = new StringBuilder();
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            Friends.Friend var4 = (Friends.Friend)var3.next();
            var2.append(String.format("%s;%s$", var4.username, var4.uuid.toString()));
         }

         return new JsonPrimitive(String.valueOf(var2));
      }
   }

   public static class Friend {
      UUID uuid;
      String username;

      public String getUsername() {
         return this.username;
      }

      public Friend(String var1, UUID var2) {
         this.username = var1;
         this.uuid = var2;
      }
   }
}
