package net;

import net.ald;
import net.d3;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class aF9 {
   private static final EnumChatFormatting b = EnumChatFormatting.WHITE;
   private final ChatComponentText a;
   private boolean e;
   private ChatStyle d;
   private ChatComponentText c;

   public aF9(boolean var1, boolean var2) {
      d3.e();
      super();
      this.a = new ChatComponentText("");
      this.e = false;
      this.d = new ChatStyle();
      this.c = new ChatComponentText("");
      this.a.appendSibling(ald.a((new aF9(false, false)).a(EnumChatFormatting.AQUA + "Stella ").a(EnumChatFormatting.RED).a()));
      this.e = var2;
   }

   public aF9() {
      this.a = new ChatComponentText("");
      this.e = false;
      this.d = new ChatStyle();
      this.c = new ChatComponentText("");
   }

   public aF9 a(String var1) {
      this.d();
      this.c = new ChatComponentText(var1);
      d3.e();
      this.d = new ChatStyle();
      if(this.e) {
         this.a(b);
      }

      return this;
   }

   public aF9 a(EnumChatFormatting var1) {
      this.d.setColor(var1);
      return this;
   }

   public aF9 b() {
      this.d.setBold(Boolean.valueOf(true));
      return this;
   }

   public aF9 c() {
      this.d.setItalic(Boolean.valueOf(true));
      return this;
   }

   public aF9 f() {
      this.d.setStrikethrough(Boolean.valueOf(true));
      return this;
   }

   public aF9 e() {
      this.d.setUnderlined(Boolean.valueOf(true));
      return this;
   }

   public ald a() {
      this.d();
      return new ald(this.a);
   }

   private void d() {
      this.a.appendSibling(this.c.setChatStyle(this.d));
   }
}
