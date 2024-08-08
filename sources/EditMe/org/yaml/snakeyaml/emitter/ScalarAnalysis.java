package org.yaml.snakeyaml.emitter;

public final class ScalarAnalysis {
   public String scalar;
   public boolean empty;
   public boolean multiline;
   public boolean allowFlowPlain;
   public boolean allowBlockPlain;
   public boolean allowSingleQuoted;
   public boolean allowBlock;

   public ScalarAnalysis(String var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7) {
      this.scalar = var1;
      this.empty = var2;
      this.multiline = var3;
      this.allowFlowPlain = var4;
      this.allowBlockPlain = var5;
      this.allowSingleQuoted = var6;
      this.allowBlock = var7;
   }
}
