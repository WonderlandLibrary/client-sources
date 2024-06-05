package net;

import cc.novoline.utils.PlayerUtils;
import java.awt.Color;
import net.minecraft.command.ICommandSender;

public class ak7 {
   public static boolean b(ICommandSender var0) {
      return PlayerUtils.inTeamWithMinecraftPlayer(var0);
   }

   public static Color a(ICommandSender var0) {
      return PlayerUtils.c(var0);
   }
}
