package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;

public abstract class Token {
   private final Mark startMark;
   private final Mark endMark;

   public Token(Mark var1, Mark var2) {
      if (var1 != null && var2 != null) {
         this.startMark = var1;
         this.endMark = var2;
      } else {
         throw new YAMLException("Token requires marks.");
      }
   }

   public String toString() {
      return "<" + this.getClass().getName() + "(" + this.getArguments() + ")>";
   }

   public Mark getStartMark() {
      return this.startMark;
   }

   public Mark getEndMark() {
      return this.endMark;
   }

   protected String getArguments() {
      return "";
   }

   public abstract Token.ID getTokenId();

   public boolean equals(Object var1) {
      return var1 instanceof Token ? this.toString().equals(var1.toString()) : false;
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public static enum ID {
      Alias,
      Anchor,
      BlockEnd,
      BlockEntry,
      BlockMappingStart,
      BlockSequenceStart,
      Directive,
      DocumentEnd,
      DocumentStart,
      FlowEntry,
      FlowMappingEnd,
      FlowMappingStart,
      FlowSequenceEnd,
      FlowSequenceStart,
      Key,
      Scalar,
      StreamEnd,
      StreamStart,
      Tag,
      Value,
      Whitespace,
      Comment,
      Error;

      private static final Token.ID[] $VALUES = new Token.ID[]{Alias, Anchor, BlockEnd, BlockEntry, BlockMappingStart, BlockSequenceStart, Directive, DocumentEnd, DocumentStart, FlowEntry, FlowMappingEnd, FlowMappingStart, FlowSequenceEnd, FlowSequenceStart, Key, Scalar, StreamEnd, StreamStart, Tag, Value, Whitespace, Comment, Error};
   }
}
