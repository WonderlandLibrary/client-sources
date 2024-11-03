package com.viaversion.viaversion.libs.opennbt.tag.limiter;

public interface TagLimiter {
   static TagLimiter create(int maxBytes, int maxLevels) {
      return new TagLimiterImpl(maxBytes, maxLevels);
   }

   static TagLimiter noop() {
      return NoopTagLimiter.INSTANCE;
   }

   void countBytes(int var1);

   void checkLevel(int var1);

   default void countByte() {
      this.countBytes(1);
   }

   default void countShort() {
      this.countBytes(2);
   }

   default void countInt() {
      this.countBytes(4);
   }

   default void countFloat() {
      this.countBytes(8);
   }

   default void countLong() {
      this.countBytes(8);
   }

   default void countDouble() {
      this.countBytes(8);
   }

   int maxBytes();

   int maxLevels();

   int bytes();

   void reset();
}
