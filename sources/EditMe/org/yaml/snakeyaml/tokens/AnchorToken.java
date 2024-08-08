package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

public final class AnchorToken extends Token {
   private final String value;

   public AnchorToken(String var1, Mark var2, Mark var3) {
      super(var2, var3);
      this.value = var1;
   }

   public String getValue() {
      return this.value;
   }

   protected String getArguments() {
      return "value=" + this.value;
   }

   public Token.ID getTokenId() {
      return Token.ID.Anchor;
   }
}
