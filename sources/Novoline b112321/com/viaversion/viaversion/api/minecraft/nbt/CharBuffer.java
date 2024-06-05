package com.viaversion.viaversion.api.minecraft.nbt;

import com.viaversion.viaversion.api.minecraft.nbt.StringTagParseException;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.aPX;

final class CharBuffer {
   private final CharSequence sequence;
   private int index;

   CharBuffer(CharSequence var1) {
      this.sequence = var1;
   }

   public char peek() {
      return this.sequence.charAt(this.index);
   }

   public char peek(int var1) {
      return this.sequence.charAt(this.index + var1);
   }

   public char take() {
      return this.sequence.charAt(this.index++);
   }

   public boolean advance() {
      ++this.index;
      return this.hasMore();
   }

   public boolean hasMore() {
      String var1 = aPX.b();
      return this.index < this.sequence.length();
   }

   public CharSequence takeUntil(char var1) throws StringTagParseException {
      aPX.b();
      var1 = Character.toLowerCase(var1);
      int var3 = -1;
      int var4 = this.index;
      if(var4 < this.sequence.length()) {
         if(this.sequence.charAt(var4) == 92) {
            ++var4;
         }

         if(Character.toLowerCase(this.sequence.charAt(var4)) == var1) {
            var3 = var4;
         }

         ++var4;
      }

      if(var3 == -1) {
         throw this.makeError("No occurrence of " + var1 + " was found");
      } else {
         CharSequence var7 = this.sequence.subSequence(this.index, var3);
         this.index = var3 + 1;
         if(PacketRemapper.b() == null) {
            aPX.b("Nr1Otc");
         }

         return var7;
      }
   }

   public CharBuffer expect(char var1) throws StringTagParseException {
      aPX.b();
      this.skipWhitespace();
      if(!this.hasMore()) {
         throw this.makeError("Expected character \'" + var1 + "\' but got EOF");
      } else if(this.peek() != var1) {
         throw this.makeError("Expected character \'" + var1 + "\' but got \'" + this.peek() + "\'");
      } else {
         this.take();
         return this;
      }
   }

   public CharBuffer skipWhitespace() {
      String var1 = aPX.b();
      if(this.hasMore() && Character.isWhitespace(this.peek())) {
         this.advance();
      }

      return this;
   }

   public StringTagParseException makeError(String var1) {
      return new StringTagParseException(var1, this.sequence, this.index);
   }

   private static StringTagParseException a(StringTagParseException var0) {
      return var0;
   }
}
