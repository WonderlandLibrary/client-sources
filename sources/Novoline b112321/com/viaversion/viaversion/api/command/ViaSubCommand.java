package com.viaversion.viaversion.api.command;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.Collections;
import java.util.List;
import net.aLy;

public abstract class ViaSubCommand {
   private static String[] b;

   public abstract String name();

   public abstract String description();

   public String usage() {
      return this.name();
   }

   public String permission() {
      return "viaversion.admin";
   }

   public abstract boolean execute(ViaCommandSender var1, String[] var2);

   public List onTabComplete(ViaCommandSender var1, String[] var2) {
      return Collections.emptyList();
   }

   public String a(String var1) {
      return aLy.a(var1);
   }

   public void a(ViaCommandSender var1, String var2, Object... var3) {
      aLy.a(var1, var2, var3);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[5]);
      }

   }
}
