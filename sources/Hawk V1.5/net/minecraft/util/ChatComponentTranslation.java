package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatComponentTranslation extends ChatComponentStyle {
   List children = Lists.newArrayList();
   private static final String __OBFID = "CL_00001270";
   private long lastTranslationUpdateTimeInMilliseconds = -1L;
   private final String key;
   private final Object syncLock = new Object();
   public static final Pattern stringVariablePattern = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
   private final Object[] formatArgs;

   public ChatComponentTranslation(String var1, Object... var2) {
      this.key = var1;
      this.formatArgs = var2;
      Object[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object var6 = var3[var5];
         if (var6 instanceof IChatComponent) {
            ((IChatComponent)var6).getChatStyle().setParentStyle(this.getChatStyle());
         }
      }

   }

   public IChatComponent setChatStyle(ChatStyle var1) {
      super.setChatStyle(var1);
      Object[] var2 = this.formatArgs;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object var5 = var2[var4];
         if (var5 instanceof IChatComponent) {
            ((IChatComponent)var5).getChatStyle().setParentStyle(this.getChatStyle());
         }
      }

      if (this.lastTranslationUpdateTimeInMilliseconds > -1L) {
         Iterator var6 = this.children.iterator();

         while(var6.hasNext()) {
            IChatComponent var7 = (IChatComponent)var6.next();
            var7.getChatStyle().setParentStyle(var1);
         }
      }

      return this;
   }

   public String getUnformattedTextForChat() {
      this.ensureInitialized();
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = this.children.iterator();

      while(var2.hasNext()) {
         IChatComponent var3 = (IChatComponent)var2.next();
         var1.append(var3.getUnformattedTextForChat());
      }

      return String.valueOf(var1);
   }

   public Object[] getFormatArgs() {
      return this.formatArgs;
   }

   public ChatComponentTranslation createCopy() {
      Object[] var1 = new Object[this.formatArgs.length];

      for(int var2 = 0; var2 < this.formatArgs.length; ++var2) {
         if (this.formatArgs[var2] instanceof IChatComponent) {
            var1[var2] = ((IChatComponent)this.formatArgs[var2]).createCopy();
         } else {
            var1[var2] = this.formatArgs[var2];
         }
      }

      ChatComponentTranslation var5 = new ChatComponentTranslation(this.key, var1);
      var5.setChatStyle(this.getChatStyle().createShallowCopy());
      Iterator var3 = this.getSiblings().iterator();

      while(var3.hasNext()) {
         IChatComponent var4 = (IChatComponent)var3.next();
         var5.appendSibling(var4.createCopy());
      }

      return var5;
   }

   private IChatComponent getFormatArgumentAsComponent(int var1) {
      if (var1 >= this.formatArgs.length) {
         throw new ChatComponentTranslationFormatException(this, var1);
      } else {
         Object var2 = this.formatArgs[var1];
         Object var3;
         if (var2 instanceof IChatComponent) {
            var3 = (IChatComponent)var2;
         } else {
            var3 = new ChatComponentText(var2 == null ? "null" : var2.toString());
            ((IChatComponent)var3).getChatStyle().setParentStyle(this.getChatStyle());
         }

         return (IChatComponent)var3;
      }
   }

   protected void initializeFromFormat(String var1) {
      boolean var2 = false;
      Matcher var3 = stringVariablePattern.matcher(var1);
      int var4 = 0;
      int var5 = 0;

      try {
         int var6;
         for(; var3.find(var5); var5 = var6) {
            int var7 = var3.start();
            var6 = var3.end();
            if (var7 > var5) {
               ChatComponentText var8 = new ChatComponentText(String.format(var1.substring(var5, var7)));
               var8.getChatStyle().setParentStyle(this.getChatStyle());
               this.children.add(var8);
            }

            String var14 = var3.group(2);
            String var9 = var1.substring(var7, var6);
            if ("%".equals(var14) && "%%".equals(var9)) {
               ChatComponentText var15 = new ChatComponentText("%");
               var15.getChatStyle().setParentStyle(this.getChatStyle());
               this.children.add(var15);
            } else {
               if (!"s".equals(var14)) {
                  throw new ChatComponentTranslationFormatException(this, String.valueOf((new StringBuilder("Unsupported format: '")).append(var9).append("'")));
               }

               String var10 = var3.group(1);
               int var11 = var10 != null ? Integer.parseInt(var10) - 1 : var4++;
               if (var11 < this.formatArgs.length) {
                  this.children.add(this.getFormatArgumentAsComponent(var11));
               }
            }
         }

         if (var5 < var1.length()) {
            ChatComponentText var13 = new ChatComponentText(String.format(var1.substring(var5)));
            var13.getChatStyle().setParentStyle(this.getChatStyle());
            this.children.add(var13);
         }

      } catch (IllegalFormatException var12) {
         throw new ChatComponentTranslationFormatException(this, var12);
      }
   }

   public String toString() {
      return String.valueOf((new StringBuilder("TranslatableComponent{key='")).append(this.key).append('\'').append(", args=").append(Arrays.toString(this.formatArgs)).append(", siblings=").append(this.siblings).append(", style=").append(this.getChatStyle()).append('}'));
   }

   public IChatComponent createCopy() {
      return this.createCopy();
   }

   public String getKey() {
      return this.key;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ChatComponentTranslation)) {
         return false;
      } else {
         ChatComponentTranslation var2 = (ChatComponentTranslation)var1;
         return Arrays.equals(this.formatArgs, var2.formatArgs) && this.key.equals(var2.key) && super.equals(var1);
      }
   }

   synchronized void ensureInitialized() {
      Object var1 = this.syncLock;
      synchronized(this.syncLock) {
         long var3 = StatCollector.getLastTranslationUpdateTimeInMilliseconds();
         if (var3 == this.lastTranslationUpdateTimeInMilliseconds) {
            return;
         }

         this.lastTranslationUpdateTimeInMilliseconds = var3;
         this.children.clear();
      }

      try {
         this.initializeFromFormat(StatCollector.translateToLocal(this.key));
      } catch (ChatComponentTranslationFormatException var6) {
         this.children.clear();

         try {
            this.initializeFromFormat(StatCollector.translateToFallback(this.key));
         } catch (ChatComponentTranslationFormatException var5) {
            throw var6;
         }
      }

   }

   public int hashCode() {
      int var1 = super.hashCode();
      var1 = 31 * var1 + this.key.hashCode();
      var1 = 31 * var1 + Arrays.hashCode(this.formatArgs);
      return var1;
   }

   public Iterator iterator() {
      this.ensureInitialized();
      return Iterators.concat(createDeepCopyIterator(this.children), createDeepCopyIterator(this.siblings));
   }
}
