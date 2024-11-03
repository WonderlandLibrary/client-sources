package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

public final class FlowMappingEndToken extends Token {
   public FlowMappingEndToken(Mark startMark, Mark endMark) {
      super(startMark, endMark);
   }

   @Override
   public Token.ID getTokenId() {
      return Token.ID.FlowMappingEnd;
   }
}
