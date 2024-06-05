package net;

import java.util.HashSet;
import tv.twitch.ErrorCode;
import tv.twitch.chat.Chat;
import tv.twitch.chat.ChatBadgeData;
import tv.twitch.chat.ChatEmoticonData;
import tv.twitch.chat.IChatAPIListener;
import tv.twitch.chat.IChatChannelListener;

public class aPA {
   private static String b;

   public static ErrorCode a(Chat var0, HashSet var1, IChatAPIListener var2) {
      return var0.initialize(var1, var2);
   }

   public static ErrorCode c(Chat var0) {
      return var0.shutdown();
   }

   public static ErrorCode a(Chat var0) {
      return var0.flushEvents();
   }

   public static ErrorCode d(Chat var0) {
      return var0.downloadEmoticonData();
   }

   public static ErrorCode a(Chat var0, ChatEmoticonData var1) {
      return var0.getEmoticonData(var1);
   }

   public static ErrorCode b(Chat var0) {
      return var0.clearEmoticonData();
   }

   public static ErrorCode a(Chat var0, String var1, IChatChannelListener var2) {
      return var0.connectAnonymous(var1, var2);
   }

   public static ErrorCode a(Chat var0, String var1, String var2, String var3, IChatChannelListener var4) {
      return var0.connect(var1, var2, var3, var4);
   }

   public static ErrorCode a(Chat var0, String var1) {
      return var0.disconnect(var1);
   }

   public static ErrorCode a(Chat var0, String var1, String var2) {
      return var0.sendMessage(var1, var2);
   }

   public static ErrorCode b(Chat var0, String var1) {
      return var0.downloadBadgeData(var1);
   }

   public static ErrorCode a(Chat var0, String var1, ChatBadgeData var2) {
      return var0.getBadgeData(var1, var2);
   }

   public static ErrorCode c(Chat var0, String var1) {
      return var0.clearBadgeData(var1);
   }

   public static ErrorCode b(Chat var0, int var1) {
      return var0.setMessageFlushInterval(var1);
   }

   public static ErrorCode a(Chat var0, int var1) {
      return var0.setUserChangeEventInterval(var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("qSMLdc");
      }

   }
}
