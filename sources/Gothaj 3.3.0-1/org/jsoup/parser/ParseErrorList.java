package org.jsoup.parser;

import java.util.ArrayList;

public class ParseErrorList extends ArrayList<ParseError> {
   private static final int INITIAL_CAPACITY = 16;
   private final int initialCapacity;
   private final int maxSize;

   ParseErrorList(int initialCapacity, int maxSize) {
      super(initialCapacity);
      this.initialCapacity = initialCapacity;
      this.maxSize = maxSize;
   }

   ParseErrorList(ParseErrorList copy) {
      this(copy.initialCapacity, copy.maxSize);
   }

   boolean canAddError() {
      return this.size() < this.maxSize;
   }

   int getMaxSize() {
      return this.maxSize;
   }

   public static ParseErrorList noTracking() {
      return new ParseErrorList(0, 0);
   }

   public static ParseErrorList tracking(int maxSize) {
      return new ParseErrorList(16, maxSize);
   }

   @Override
   public Object clone() {
      return super.clone();
   }
}
