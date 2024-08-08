package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

public final class ScalarToken extends Token {
   private final String value;
   private final boolean plain;
   private final char style;

   public ScalarToken(String var1, Mark var2, Mark var3, boolean var4) {
      this(var1, var4, var2, var3, '\u0000');
   }

   public ScalarToken(String var1, boolean var2, Mark var3, Mark var4, char var5) {
      super(var3, var4);
      this.value = var1;
      this.plain = var2;
      this.style = var5;
   }

   public boolean getPlain() {
      return this.plain;
   }

   public String getValue() {
      return this.value;
   }

   public char getStyle() {
      return this.style;
   }

   protected String getArguments() {
      return "value=" + this.value + ", plain=" + this.plain + ", style=" + this.style;
   }

   public Token.ID getTokenId() {
      return Token.ID.Scalar;
   }
}
