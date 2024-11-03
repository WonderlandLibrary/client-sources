package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Stack;

public interface IntStack extends Stack<Integer> {
   void push(int var1);

   int popInt();

   int topInt();

   int peekInt(int var1);

   @Deprecated
   default void push(Integer o) {
      this.push(o.intValue());
   }

   @Deprecated
   default Integer pop() {
      return this.popInt();
   }

   @Deprecated
   default Integer top() {
      return this.topInt();
   }

   @Deprecated
   default Integer peek(int i) {
      return this.peekInt(i);
   }
}
