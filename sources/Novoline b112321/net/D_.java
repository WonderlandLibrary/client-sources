package net;

import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IChatComponent;

public class D_ {
   public static EntityPlayerMP a(ICommandSender var0, String var1) {
      return PlayerSelector.matchOnePlayer(var0, var1);
   }

   public static Entity a(ICommandSender var0, String var1, Class var2) {
      return PlayerSelector.matchOneEntity(var0, var1, var2);
   }

   public static boolean a(String var0) {
      return PlayerSelector.hasArguments(var0);
   }

   public static List b(ICommandSender var0, String var1, Class var2) {
      return PlayerSelector.matchEntities(var0, var1, var2);
   }

   public static IChatComponent b(ICommandSender var0, String var1) {
      return PlayerSelector.matchEntitiesToChatComponent(var0, var1);
   }

   public static boolean b(String var0) {
      return PlayerSelector.matchesMultiplePlayers(var0);
   }
}
