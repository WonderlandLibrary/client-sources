package com.example.editme.modules.render;

import com.example.editme.modules.Module;
import com.example.editme.util.client.Friends;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

@Module.Info(
   name = "TabFriends",
   category = Module.Category.HIDDEN
)
public class TabFriends extends Module {
   public static String getPlayerName(NetworkPlayerInfo var0) {
      String var1 = var0.func_178854_k() != null ? var0.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a(var0.func_178850_i(), var0.func_178845_a().getName());
      return Friends.isFriend(var1) ? String.format("%sa%s", '§', var1) : var1;
   }
}
