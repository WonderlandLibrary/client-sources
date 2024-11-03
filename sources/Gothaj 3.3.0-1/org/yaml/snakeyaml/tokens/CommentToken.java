package org.yaml.snakeyaml.tokens;

import java.util.Objects;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.error.Mark;

public final class CommentToken extends Token {
   private final CommentType type;
   private final String value;

   public CommentToken(CommentType type, String value, Mark startMark, Mark endMark) {
      super(startMark, endMark);
      Objects.requireNonNull(type);
      this.type = type;
      Objects.requireNonNull(value);
      this.value = value;
   }

   public CommentType getCommentType() {
      return this.type;
   }

   public String getValue() {
      return this.value;
   }

   @Override
   public Token.ID getTokenId() {
      return Token.ID.Comment;
   }
}
