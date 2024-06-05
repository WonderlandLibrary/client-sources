package net;

import net.acE;
import net.md_5.bungee.api.ChatColor;
import viaversion.viabackwards.api.data.BackwardsMappings;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;

public class ln {
   private final int b;
   private final String a;

   public ln(int var1, String var2) {
      this.b = var1;
      BackwardsMappings.b();
      this.a = ChatRewriter.legacyTextToJson(ChatColor.RESET + var2).toString();
      if(acE.b() == null) {
         BackwardsMappings.b("WFpomb");
      }

   }

   public int a() {
      return this.b;
   }

   public String b() {
      return this.a;
   }
}
