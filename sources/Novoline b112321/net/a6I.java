package net;

import com.google.common.collect.ComparisonChain;
import java.util.Comparator;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.world.WorldSettings$GameType;

public class a6I implements Comparator {
   public int a(NetworkPlayerInfo var1, NetworkPlayerInfo var2) {
      ScorePlayerTeam var3 = var1.getPlayerTeam();
      ScorePlayerTeam var4 = var2.getPlayerTeam();
      return ComparisonChain.start().compareTrueFirst(var1.getGameType() != WorldSettings$GameType.SPECTATOR, var2.getGameType() != WorldSettings$GameType.SPECTATOR).compare(var3.getRegisteredName(), var4.getRegisteredName()).compare(var1.getGameProfile().getName(), var2.getGameProfile().getName()).result();
   }
}
