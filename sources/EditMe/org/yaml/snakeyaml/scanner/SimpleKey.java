package org.yaml.snakeyaml.scanner;

import org.yaml.snakeyaml.error.Mark;

final class SimpleKey {
   private int tokenNumber;
   private boolean required;
   private int index;
   private int line;
   private int column;
   private Mark mark;

   public SimpleKey(int var1, boolean var2, int var3, int var4, int var5, Mark var6) {
      this.tokenNumber = var1;
      this.required = var2;
      this.index = var3;
      this.line = var4;
      this.column = var5;
      this.mark = var6;
   }

   public int getTokenNumber() {
      return this.tokenNumber;
   }

   public int getColumn() {
      return this.column;
   }

   public Mark getMark() {
      return this.mark;
   }

   public int getIndex() {
      return this.index;
   }

   public int getLine() {
      return this.line;
   }

   public boolean isRequired() {
      return this.required;
   }

   public String toString() {
      return "SimpleKey - tokenNumber=" + this.tokenNumber + " required=" + this.required + " index=" + this.index + " line=" + this.line + " column=" + this.column;
   }
}
