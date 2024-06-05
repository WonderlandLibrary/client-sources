package net;

import net.minecraft.client.stream.BroadcastController$BroadcastListener;
import net.minecraft.client.stream.BroadcastController$BroadcastState;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.StreamInfo;

public class ad4 {
   public static void a(BroadcastController$BroadcastListener var0) {
      var0.func_152895_a();
   }

   public static void a(BroadcastController$BroadcastListener var0, BroadcastController$BroadcastState var1) {
      var0.func_152891_a(var1);
   }

   public static void b(BroadcastController$BroadcastListener var0, ErrorCode var1) {
      var0.func_152893_b(var1);
   }

   public static void a(BroadcastController$BroadcastListener var0, ErrorCode var1, AuthToken var2) {
      var0.func_152900_a(var1, var2);
   }

   public static void a(BroadcastController$BroadcastListener var0, ErrorCode var1) {
      var0.func_152897_a(var1);
   }

   public static void a(BroadcastController$BroadcastListener var0, IngestList var1) {
      var0.func_152896_a(var1);
   }

   public static void a(BroadcastController$BroadcastListener var0, StreamInfo var1) {
      var0.func_152894_a(var1);
   }

   public static void a(BroadcastController$BroadcastListener var0, ErrorCode var1, GameInfo[] var2) {
      var0.func_152898_a(var1, var2);
   }

   public static void b(BroadcastController$BroadcastListener var0) {
      var0.func_152899_b();
   }

   public static void c(BroadcastController$BroadcastListener var0, ErrorCode var1) {
      var0.func_152892_c(var1);
   }

   public static void c(BroadcastController$BroadcastListener var0) {
      var0.func_152901_c();
   }
}
