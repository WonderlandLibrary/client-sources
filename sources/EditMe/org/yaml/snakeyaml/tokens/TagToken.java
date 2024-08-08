package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

public final class TagToken extends Token {
   private final TagTuple value;

   public TagToken(TagTuple var1, Mark var2, Mark var3) {
      super(var2, var3);
      this.value = var1;
   }

   public TagTuple getValue() {
      return this.value;
   }

   protected String getArguments() {
      return "value=[" + this.value.getHandle() + ", " + this.value.getSuffix() + "]";
   }

   public Token.ID getTokenId() {
      return Token.ID.Tag;
   }
}
