package com.viaversion.viaversion.libs.opennbt.stringified;

final class CharBuffer {
   private final CharSequence sequence;
   private int index;

   CharBuffer(CharSequence sequence) {
      this.sequence = sequence;
   }

   public char peek() {
      return this.sequence.charAt(this.index);
   }

   public char peek(int offset) {
      return this.sequence.charAt(this.index + offset);
   }

   public char take() {
      return this.sequence.charAt(this.index++);
   }

   public boolean advance() {
      this.index++;
      return this.hasMore();
   }

   public boolean hasMore() {
      return this.index < this.sequence.length();
   }

   public boolean hasMore(int offset) {
      return this.index + offset < this.sequence.length();
   }

   public CharSequence takeUntil(char until) throws StringifiedTagParseException {
      until = Character.toLowerCase(until);
      int endIdx = -1;

      for (int idx = this.index; idx < this.sequence.length(); idx++) {
         if (this.sequence.charAt(idx) == '\\') {
            idx++;
         } else if (Character.toLowerCase(this.sequence.charAt(idx)) == until) {
            endIdx = idx;
            break;
         }
      }

      if (endIdx == -1) {
         throw this.makeError("No occurrence of " + until + " was found");
      } else {
         CharSequence result = this.sequence.subSequence(this.index, endIdx);
         this.index = endIdx + 1;
         return result;
      }
   }

   public CharBuffer expect(char expectedChar) throws StringifiedTagParseException {
      this.skipWhitespace();
      if (!this.hasMore()) {
         throw this.makeError("Expected character '" + expectedChar + "' but got EOF");
      } else if (this.peek() != expectedChar) {
         throw this.makeError("Expected character '" + expectedChar + "' but got '" + this.peek() + "'");
      } else {
         this.take();
         return this;
      }
   }

   public boolean takeIf(char token) {
      this.skipWhitespace();
      if (this.hasMore() && this.peek() == token) {
         this.advance();
         return true;
      } else {
         return false;
      }
   }

   public int index() {
      return this.index;
   }

   public CharBuffer skipWhitespace() {
      while (this.hasMore() && Character.isWhitespace(this.peek())) {
         this.advance();
      }

      return this;
   }

   public StringifiedTagParseException makeError(String message) {
      return new StringifiedTagParseException(message, this.index);
   }
}
