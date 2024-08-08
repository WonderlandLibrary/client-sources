package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

public final class BlockMappingStartToken extends Token {
   public BlockMappingStartToken(Mark var1, Mark var2) {
      super(var1, var2);
   }

   public Token.ID getTokenId() {
      return Token.ID.BlockMappingStart;
   }
}
