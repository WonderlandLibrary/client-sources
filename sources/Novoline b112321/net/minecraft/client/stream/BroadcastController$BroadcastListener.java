package net.minecraft.client.stream;

import net.minecraft.client.stream.BroadcastController$BroadcastState;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.StreamInfo;

public interface BroadcastController$BroadcastListener {
   void func_152900_a(ErrorCode var1, AuthToken var2);

   void func_152897_a(ErrorCode var1);

   void func_152898_a(ErrorCode var1, GameInfo[] var2);

   void func_152891_a(BroadcastController$BroadcastState var1);

   void func_152895_a();

   void func_152894_a(StreamInfo var1);

   void func_152896_a(IngestList var1);

   void func_152893_b(ErrorCode var1);

   void func_152899_b();

   void func_152901_c();

   void func_152892_c(ErrorCode var1);
}
