package net.minecraft.util;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public abstract class ChatComponentStyle implements IChatComponent {
   protected List siblings = Lists.newArrayList();
   private ChatStyle style;

   public IChatComponent appendSibling(IChatComponent var1) {
      var1.getChatStyle().setParentStyle(this.getChatStyle());
      this.siblings.add(var1);
      return this;
   }

   public List getSiblings() {
      return this.siblings;
   }

   public IChatComponent appendText(String var1) {
      return this.appendSibling(new ChatComponentText(var1));
   }

   public IChatComponent setChatStyle(ChatStyle var1) {
      this.style = var1;

      for(IChatComponent var3 : this.siblings) {
         var3.getChatStyle().setParentStyle(this.getChatStyle());
      }

      return this;
   }

   public IChatComponent setColor(EnumChatFormatting var1) {
      if(this.style == null) {
         this.setChatStyle(new ChatStyle(var1));
      } else {
         this.setChatStyle(this.style.createDeepCopy().setColor(var1));
      }

      return this;
   }

   public ChatStyle getChatStyle() {
      if(this.style == null) {
         this.style = new ChatStyle();

         for(IChatComponent var2 : this.siblings) {
            var2.getChatStyle().setParentStyle(this.style);
         }
      }

      return this.style;
   }

   public Iterator iterator() {
      return Iterators.concat(Iterators.forArray(new ChatComponentStyle[]{this}), createDeepCopyIterator(this.siblings));
   }

   public final String getUnformattedText() {
      StringBuilder var1 = new StringBuilder();

      for(IChatComponent var3 : this) {
         var1.append(var3.getUnformattedTextForChat());
      }

      return var1.toString();
   }

   public final String getFormattedText() {
      StringBuilder var1 = new StringBuilder();

      for(IChatComponent var3 : this) {
         var1.append(var3.getChatStyle().getFormattingCode());
         var1.append(var3.getUnformattedTextForChat());
         var1.append(EnumChatFormatting.RESET);
      }

      return var1.toString();
   }

   public static Iterator createDeepCopyIterator(Iterable var0) {
      Iterator var1 = Iterators.concat(Iterators.transform(var0.iterator(), Iterable::iterator));
      return Iterators.transform(var1, ChatComponentStyle::lambda$createDeepCopyIterator$0);
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof ChatComponentStyle)) {
         return false;
      } else {
         ChatComponentStyle var2 = (ChatComponentStyle)var1;
         return this.siblings.equals(var2.siblings) && this.getChatStyle().equals(var2.getChatStyle());
      }
   }

   public int hashCode() {
      return 31 * this.style.hashCode() + this.siblings.hashCode();
   }

   public String toString() {
      return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
   }

   private static IChatComponent lambda$createDeepCopyIterator$0(IChatComponent var0) {
      IChatComponent var1 = var0.createCopy();
      return var1.setChatStyle(var1.getChatStyle().createDeepCopy());
   }
}
