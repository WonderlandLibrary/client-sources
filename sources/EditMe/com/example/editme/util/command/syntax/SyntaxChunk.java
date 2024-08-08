package com.example.editme.util.command.syntax;

public class SyntaxChunk {
   public static final SyntaxChunk[] EMPTY = new SyntaxChunk[0];
   private boolean necessary;
   private SyntaxParser parser;
   boolean headless;
   String type;
   String head;

   public boolean isNecessary() {
      return this.necessary;
   }

   public String getHead() {
      return this.head;
   }

   public boolean isHeadless() {
      return this.headless;
   }

   private String lambda$new$0(String var1, String var2, SyntaxChunk[] var3, SyntaxChunk var4, String[] var5, String var6) {
      return var6 != null ? null : String.valueOf((new StringBuilder()).append(var1).append(this.isNecessary() ? "<" : "[").append(var2).append(this.isNecessary() ? ">" : "]"));
   }

   public String getType() {
      return this.type;
   }

   public String getChunk(SyntaxChunk[] var1, SyntaxChunk var2, String[] var3, String var4) {
      String var5 = this.parser.getChunk(var1, var2, var3, var4);
      return var5 == null ? "" : var5;
   }

   public void setParser(SyntaxParser var1) {
      this.parser = var1;
   }

   public SyntaxChunk(String var1, boolean var2) {
      this("", var1, var2);
      this.headless = true;
   }

   public SyntaxChunk(String var1, String var2, boolean var3) {
      this.headless = false;
      this.head = var1;
      this.type = var2;
      this.necessary = var3;
      this.parser = this::lambda$new$0;
   }
}
