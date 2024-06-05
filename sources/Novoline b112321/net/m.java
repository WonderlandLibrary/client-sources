package net;

import net.minecraft.client.stream.ChatController;
import net.minecraft.client.stream.ChatController$ChatListener;
import net.minecraft.client.stream.ChatController$ChatState;
import net.minecraft.client.stream.ChatController$EnumChannelState;
import tv.twitch.AuthToken;

public class m {
   public static void b(ChatController var0, String var1) {
      var0.func_152998_c(var1);
   }

   public static void a(ChatController var0, AuthToken var1) {
      var0.func_152994_a(var1);
   }

   public static boolean b(ChatController var0) {
      return var0.func_175984_n();
   }

   public static void a(ChatController var0, ChatController$ChatListener var1) {
      var0.func_152990_a(var1);
   }

   public static void d(ChatController var0, String var1) {
      var0.func_152984_a(var1);
   }

   public static void c(ChatController var0) {
      var0.func_175988_p();
   }

   public static boolean c(ChatController var0, String var1) {
      return var0.func_175990_d(var1);
   }

   public static ChatController$ChatState d(ChatController var0) {
      return var0.func_153000_j();
   }

   public static ChatController$EnumChannelState f(ChatController var0, String var1) {
      return var0.func_175989_e(var1);
   }

   public static boolean e(ChatController var0, String var1) {
      return var0.func_175991_l(var1);
   }

   public static void a(ChatController var0) {
      var0.func_152997_n();
   }

   public static boolean a(ChatController var0, String var1) {
      return var0.func_152986_d(var1);
   }

   public static boolean a(ChatController var0, String var1, String var2) {
      return var0.func_175986_a(var1, var2);
   }
}
