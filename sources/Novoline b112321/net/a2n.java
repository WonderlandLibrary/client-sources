package net;

import cc.novoline.utils.messages.IRCMessage;
import cc.novoline.utils.messages.MessageFactory;
import cc.novoline.utils.messages.TextMessage;
import net.U_;
import net.Uj;
import net.Uk;
import net.Ux;
import net.VA;
import net.minecraft.util.EnumChatFormatting;
import net.skidunion.F;

public class a2n {
   private static String b;

   public static Uj a(String var0, String var1) {
      return MessageFactory.a(var0, var1);
   }

   public static TextMessage a(String var0, EnumChatFormatting var1) {
      return MessageFactory.text(var0, var1);
   }

   public static TextMessage a(String var0) {
      return MessageFactory.text(var0);
   }

   public static Ux a() {
      return MessageFactory.a();
   }

   public static U_ a(String var0, String var1, Uj[] var2) {
      return MessageFactory.a(var0, var1, var2);
   }

   public static IRCMessage c(String var0) {
      return MessageFactory.broadcast(var0);
   }

   public static Uk a(F var0) {
      return MessageFactory.a(var0);
   }

   public static VA a(net.skidunion.m var0, String var1) {
      return MessageFactory.b(var0, var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("ZGfj2");
      }

   }
}
