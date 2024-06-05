package cc.novoline.utils.messages;

import java.util.Iterator;
import net.Ux;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextMessage extends Ux {
   private static final ChatStyle e = (new ChatStyle()).setColor(EnumChatFormatting.RESET);
   private final StringBuilder message;
   private static int d;

   protected TextMessage(@Nullable String var1, @Nullable EnumChatFormatting var2) {
      this.message = new StringBuilder(var1);
      this.setChatStyle((new ChatStyle()).setColor(var2));
   }

   protected TextMessage(@Nullable String var1) {
      this(var1, (EnumChatFormatting)null);
   }

   @NotNull
   public static TextMessage of(@Nullable String var0) {
      return new TextMessage(var0);
   }

   @NotNull
   public static TextMessage of(@Nullable String var0, @Nullable EnumChatFormatting var1) {
      return new TextMessage(var0, var1);
   }

   public TextMessage prefix(@Nullable TextMessage var1) {
      int var2 = Ux.a();
      return var1 != null?var1.createCopy().appendSibling(this):this;
   }

   public TextMessage suffix(@Nullable IChatComponent var1) {
      this.siblings.add(var1);
      return this;
   }

   public TextMessage newLine() {
      this.append("\n");
      return this;
   }

   public TextMessage append(@Nullable String var1, @Nullable EnumChatFormatting var2) {
      this.appendSibling(of(var1, var2));
      return this;
   }

   public TextMessage appendText(@Nullable String var1) {
      this.append(var1, EnumChatFormatting.RESET);
      return this;
   }

   public TextMessage append(@Nullable String var1) {
      this.appendText(var1);
      return this;
   }

   public TextMessage append(IChatComponent var1) {
      this.appendSibling(var1);
      return this;
   }

   public TextMessage appendSibling(IChatComponent var1) {
      super.appendSibling(var1);
      return this;
   }

   public TextMessage createCopy() {
      Ux.a();
      TextMessage var2 = of(this.message.toString());
      var2.setChatStyle(this.getChatStyle().createShallowCopy());
      Iterator var3 = this.getSiblings().iterator();
      if(var3.hasNext()) {
         IChatComponent var4 = (IChatComponent)var3.next();
         var2.appendSibling(var4.createCopy());
      }

      return var2;
   }

   public String getUnformattedTextForChat() {
      return this.message.toString();
   }

   static {
      a(32);
   }

   public static void a(int var0) {
      d = var0;
   }

   public static int d() {
      return d;
   }

   public static int e() {
      int var0 = d();
      return 23;
   }
}
