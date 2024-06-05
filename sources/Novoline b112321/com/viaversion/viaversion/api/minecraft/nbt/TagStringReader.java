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
import com.viaversion.viaversion.api.minecraft.nbt.CharBuffer;
import com.viaversion.viaversion.api.minecraft.nbt.StringTagParseException;
import com.viaversion.viaversion.api.minecraft.nbt.Tokens;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.IntStream.Builder;
import net.aPX;
import net.af_;

final class TagStringReader {
   private static final Field b = f();
   private final CharBuffer buffer;

   private static Field f() {
      Class var10000 = Tag.class;
      String var10001 = "name";

      try {
         return af_.c(var10000, var10001);
      } catch (NoSuchFieldException var1) {
         var1.printStackTrace();
         throw new IllegalArgumentException(var1);
      }
   }

   public TagStringReader(CharBuffer var1) {
      this.buffer = var1;
   }

   public CompoundTag i() throws StringTagParseException {
      aPX.b();
      this.buffer.expect('{');
      CompoundTag var2 = new CompoundTag("");
      if(this.buffer.peek() == 125) {
         this.buffer.take();
         return var2;
      } else {
         if(this.buffer.hasMore()) {
            String var3 = this.key();
            Tag var4 = this.b();

            try {
               if(!b.isAccessible()) {
                  b.setAccessible(true);
               }

               b.set(var4, var3);
            } catch (IllegalAccessException var6) {
               throw new IllegalArgumentException(var6);
            }

            var2.put(var4);
            if(this.separatorOrCompleteWith('}')) {
               return var2;
            }
         }

         throw this.buffer.makeError("Unterminated compound tag!");
      }
   }

   public ListTag e() throws StringTagParseException {
      aPX.b();
      ListTag var2 = new ListTag("");
      this.buffer.expect('[');
      boolean var3 = this.buffer.peek() == 48 && this.buffer.peek(1) == 58;
      if(this.buffer.hasMore()) {
         if(this.buffer.peek() == 93) {
            this.buffer.advance();
            return var2;
         }

         if(var3) {
            this.buffer.takeUntil(':');
         }

         Tag var4 = this.b();
         var2.add(var4);
         if(this.separatorOrCompleteWith(']')) {
            return var2;
         }
      }

      throw this.buffer.makeError("Reached end of file without end of list tag!");
   }

   public Tag a(char var1) throws StringTagParseException {
      aPX.b();
      this.buffer.expect('[').expect(var1).expect(';');
      if(var1 == 66) {
         return new ByteArrayTag("", this.byteArray());
      } else if(var1 == 73) {
         return new IntArrayTag("", this.intArray());
      } else if(var1 == 76) {
         return new LongArrayTag("", this.longArray());
      } else {
         throw this.buffer.makeError("Type " + var1 + " is not a valid element type in an array!");
      }
   }

   private byte[] byteArray() throws StringTagParseException {
      aPX.b();
      ArrayList var2 = new ArrayList();
      if(this.buffer.hasMore()) {
         CharSequence var3 = this.buffer.skipWhitespace().takeUntil('B');
         ArrayList var10000 = var2;
         CharSequence var10001 = var3;

         try {
            var10000.add(Byte.valueOf(var10001.toString()));
         } catch (NumberFormatException var6) {
            throw this.buffer.makeError("All elements of a byte array must be bytes!");
         }

         if(this.separatorOrCompleteWith(']')) {
            byte[] var4 = new byte[var2.size()];
            int var5 = 0;
            if(var5 < var2.size()) {
               var4[var5] = ((Byte)var2.get(var5)).byteValue();
               ++var5;
            }

            return var4;
         }
      }

      throw this.buffer.makeError("Reached end of document without array close");
   }

   private int[] intArray() throws StringTagParseException {
      aPX.b();
      Builder var2 = IntStream.builder();
      if(this.buffer.hasMore()) {
         Tag var3 = this.b();
         if(!(var3 instanceof IntTag)) {
            throw this.buffer.makeError("All elements of an int array must be ints!");
         }

         var2.add(((IntTag)var3).getValue().intValue());
         if(this.separatorOrCompleteWith(']')) {
            return var2.build().toArray();
         }
      }

      throw this.buffer.makeError("Reached end of document without array close");
   }

   private long[] longArray() throws StringTagParseException {
      aPX.b();
      ArrayList var2 = new ArrayList();
      if(this.buffer.hasMore()) {
         CharSequence var3 = this.buffer.skipWhitespace().takeUntil('L');
         ArrayList var10000 = var2;
         CharSequence var10001 = var3;

         try {
            var10000.add(Long.valueOf(var10001.toString()));
         } catch (NumberFormatException var6) {
            throw this.buffer.makeError("All elements of a long array must be longs!");
         }

         if(this.separatorOrCompleteWith(']')) {
            long[] var4 = new long[var2.size()];
            int var5 = 0;
            if(var5 < var2.size()) {
               var4[var5] = ((Long)var2.get(var5)).longValue();
               ++var5;
            }

            return var4;
         }
      }

      throw this.buffer.makeError("Reached end of document without array close");
   }

   public String key() throws StringTagParseException {
      // $FF: Couldn't be decompiled
   }

   public Tag b() throws StringTagParseException {
      aPX.b();
      char var2 = this.buffer.skipWhitespace().peek();
      switch(var2) {
      case '\"':
      case '\'':
         this.buffer.advance();
         return new StringTag("", unescape(this.buffer.takeUntil(var2).toString()));
      case '[':
         if(this.buffer.peek(2) == 59) {
            return this.a(this.buffer.peek(1));
         }

         return this.e();
      case '{':
         return this.i();
      default:
         return this.h();
      }
   }

   private Tag h() {
      StringBuilder var2 = new StringBuilder();
      aPX.b();
      boolean var3 = true;
      if(this.buffer.hasMore()) {
         char var4 = this.buffer.peek();
         if(var3 && !Tokens.numeric(var4) && var2.length() != 0) {
            Object var5 = null;

            try {
               switch(Character.toUpperCase(var4)) {
               case 'B':
                  var5 = new ByteTag("", Byte.parseByte(var2.toString()));
                  break;
               case 'D':
                  var5 = new DoubleTag("", Double.parseDouble(var2.toString()));
                  break;
               case 'F':
                  var5 = new FloatTag("", Float.parseFloat(var2.toString()));
                  break;
               case 'L':
                  var5 = new LongTag("", Long.parseLong(var2.toString()));
                  break;
               case 'S':
                  var5 = new ShortTag("", Short.parseShort(var2.toString()));
               }
            } catch (NumberFormatException var8) {
               var3 = false;
            }

            if(var5 != null) {
               this.buffer.take();
               return (Tag)var5;
            }
         }

         if(var4 == 92) {
            this.buffer.advance();
            var2.append(this.buffer.take());
         }

         if(Tokens.id(var4)) {
            var2.append(this.buffer.take());
         }
      }

      String var10 = var2.toString();

      try {
         return new IntTag("", Integer.parseInt(var10));
      } catch (NumberFormatException var7) {
         return new StringTag("", var10);
      }
   }

   private boolean separatorOrCompleteWith(char var1) throws StringTagParseException {
      String var2 = aPX.b();
      if(this.buffer.skipWhitespace().peek() == var1) {
         this.buffer.take();
         return true;
      } else {
         this.buffer.expect(',');
         return false;
      }
   }

   private static String unescape(String var0) {
      aPX.b();
      int var2 = var0.indexOf(92);
      if(var2 == -1) {
         return var0;
      } else {
         int var3 = 0;
         StringBuilder var4 = new StringBuilder(var0.length());

         while(true) {
            var4.append(var0, var3, var2);
            var3 = var2 + 1;
            if((var2 = var0.indexOf(92, var3 + 1)) == -1) {
               break;
            }
         }

         var4.append(var0.substring(var3));
         return var4.toString();
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
