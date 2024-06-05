package com.viaversion.viaversion.api.minecraft.nbt;

import com.github.steveice10.opennbt.tag.builtin.ByteArrayTag;
import com.github.steveice10.opennbt.tag.builtin.ByteTag;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.DoubleTag;
import com.github.steveice10.opennbt.tag.builtin.FloatTag;
import com.github.steveice10.opennbt.tag.builtin.IntArrayTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.LongArrayTag;
import com.github.steveice10.opennbt.tag.builtin.LongTag;
import com.github.steveice10.opennbt.tag.builtin.ShortTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.api.minecraft.nbt.Tokens;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import net.aPX;

final class TagStringWriter implements AutoCloseable {
   private final Appendable out;
   private final String c = "  ";
   private int level;
   private boolean needsSeparator;

   public TagStringWriter(Appendable var1) {
      this.out = var1;
   }

   public TagStringWriter a(Tag var1) throws IOException {
      String var2 = aPX.b();
      if(var1 instanceof CompoundTag) {
         return this.a((CompoundTag)var1);
      } else if(var1 instanceof ListTag) {
         return this.a((ListTag)var1);
      } else if(var1 instanceof ByteArrayTag) {
         return this.a((ByteArrayTag)var1);
      } else if(var1 instanceof IntArrayTag) {
         return this.a((IntArrayTag)var1);
      } else if(var1 instanceof LongArrayTag) {
         return this.a((LongArrayTag)var1);
      } else if(var1 instanceof StringTag) {
         return this.value(((StringTag)var1).getValue(), '\u0000');
      } else if(var1 instanceof ByteTag) {
         return this.value(Byte.toString(((ByteTag)var1).getValue().byteValue()), 'B');
      } else if(var1 instanceof ShortTag) {
         return this.value(Short.toString(((ShortTag)var1).getValue().shortValue()), 'S');
      } else if(var1 instanceof IntTag) {
         return this.value(Integer.toString(((IntTag)var1).getValue().intValue()), 'I');
      } else if(var1 instanceof LongTag) {
         return this.value(Long.toString(((LongTag)var1).getValue().longValue()), 'L');
      } else if(var1 instanceof FloatTag) {
         return this.value(Float.toString(((FloatTag)var1).getValue().floatValue()), 'F');
      } else if(var1 instanceof DoubleTag) {
         return this.value(Double.toString(((DoubleTag)var1).getValue().doubleValue()), 'D');
      } else {
         throw new IOException("Unknown tag type: " + var1.getClass().getCanonicalName());
      }
   }

   private TagStringWriter a(CompoundTag var1) throws IOException {
      aPX.b();
      this.beginCompound();
      Iterator var3 = var1.iterator();
      if(var3.hasNext()) {
         Tag var4 = (Tag)var3.next();
         this.key(var4.getName());
         this.a(var4);
      }

      this.endCompound();
      return this;
   }

   private TagStringWriter a(ListTag var1) throws IOException {
      this.beginList();
      aPX.b();
      Iterator var3 = var1.iterator();
      if(var3.hasNext()) {
         Tag var4 = (Tag)var3.next();
         this.printAndResetSeparator();
         this.a(var4);
      }

      this.endList();
      return this;
   }

   private TagStringWriter a(ByteArrayTag var1) throws IOException {
      aPX.b();
      this.beginArray('B');
      byte[] var3 = var1.getValue();
      int var4 = 0;
      int var5 = var3.length;
      if(var4 < var5) {
         this.printAndResetSeparator();
         this.value(Byte.toString(var3[var4]), 'B');
         ++var4;
      }

      this.endArray();
      return this;
   }

   private TagStringWriter a(IntArrayTag var1) throws IOException {
      aPX.b();
      this.beginArray('I');
      int[] var3 = var1.getValue();
      int var4 = 0;
      int var5 = var3.length;
      if(var4 < var5) {
         this.printAndResetSeparator();
         this.value(Integer.toString(var3[var4]), 'I');
         ++var4;
      }

      this.endArray();
      return this;
   }

   private TagStringWriter a(LongArrayTag var1) throws IOException {
      this.beginArray('L');
      long[] var3 = var1.getValue();
      aPX.b();
      int var4 = 0;
      int var5 = var3.length;
      if(var4 < var5) {
         this.printAndResetSeparator();
         this.value(Long.toString(var3[var4]), 'L');
         ++var4;
      }

      this.endArray();
      return this;
   }

   public TagStringWriter beginCompound() throws IOException {
      this.printAndResetSeparator();
      ++this.level;
      this.out.append('{');
      return this;
   }

   public TagStringWriter endCompound() throws IOException {
      this.out.append('}');
      --this.level;
      this.needsSeparator = true;
      return this;
   }

   public TagStringWriter key(String var1) throws IOException {
      this.printAndResetSeparator();
      this.writeMaybeQuoted(var1, false);
      this.out.append(':');
      return this;
   }

   public TagStringWriter value(String var1, char var2) throws IOException {
      String var3 = aPX.b();
      if(var2 == 0) {
         this.writeMaybeQuoted(var1, true);
      }

      this.out.append(var1);
      if(var2 != 73) {
         this.out.append(var2);
      }

      this.needsSeparator = true;
      return this;
   }

   public TagStringWriter beginList() throws IOException {
      this.printAndResetSeparator();
      ++this.level;
      this.out.append('[');
      return this;
   }

   public TagStringWriter endList() throws IOException {
      this.out.append(']');
      --this.level;
      this.needsSeparator = true;
      return this;
   }

   private TagStringWriter beginArray(char var1) throws IOException {
      this.beginList().out.append(var1).append(';');
      return this;
   }

   private TagStringWriter endArray() throws IOException {
      return this.endList();
   }

   private void writeMaybeQuoted(String var1, boolean var2) throws IOException {
      String var3 = aPX.b();
      if(!var2) {
         int var4 = 0;
         if(var4 < var1.length()) {
            if(!Tokens.id(var1.charAt(var4))) {
               var2 = true;
            }

            ++var4;
         }
      }

      if(var2) {
         this.out.append('\"');
         this.out.append(escape(var1, '\"'));
         this.out.append('\"');
      }

      this.out.append(var1);
   }

   private static String escape(String var0, char var1) {
      aPX.b();
      StringBuilder var3 = new StringBuilder(var0.length());
      int var4 = 0;
      if(var4 < var0.length()) {
         char var5 = var0.charAt(var4);
         if(var5 == var1 || var5 == 92) {
            var3.append('\\');
         }

         var3.append(var5);
         ++var4;
      }

      return var3.toString();
   }

   private void printAndResetSeparator() throws IOException {
      String var1 = aPX.b();
      if(this.needsSeparator) {
         this.out.append(',');
         this.needsSeparator = false;
      }

   }

   public void close() throws IOException {
      String var1 = aPX.b();
      if(this.level != 0) {
         throw new IllegalStateException("Document finished with unbalanced start and end objects");
      } else {
         if(this.out instanceof Writer) {
            ((Writer)this.out).flush();
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
