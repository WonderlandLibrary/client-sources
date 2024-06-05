package net;

import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class aqn {
   private static String b;

   public static String a(ICommandSender var0) {
      return var0.getName();
   }

   public static void a(ICommandSender var0, CommandResultStats$Type var1, int var2) {
      var0.setCommandStat(var1, var2);
   }

   public static World c(ICommandSender var0) {
      return var0.getEntityWorld();
   }

   public static void a(ICommandSender var0, IChatComponent var1) {
      var0.addChatMessage(var1);
   }

   public static boolean a(ICommandSender var0, int var1, String var2) {
      return var0.canCommandSenderUseCommand(var1, var2);
   }

   public static BlockPos g(ICommandSender var0) {
      return var0.getPosition();
   }

   public static IChatComponent b(ICommandSender var0) {
      return var0.getDisplayName();
   }

   public static Entity e(ICommandSender var0) {
      return var0.getCommandSenderEntity();
   }

   public static Vec3 f(ICommandSender var0) {
      return var0.getPositionVector();
   }

   public static boolean d(ICommandSender var0) {
      return var0.sendCommandFeedback();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("PQ1EFc");
      }

   }
}
