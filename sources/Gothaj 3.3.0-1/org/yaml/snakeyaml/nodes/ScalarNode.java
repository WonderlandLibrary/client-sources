package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;

public class ScalarNode extends Node {
   private final DumperOptions.ScalarStyle style;
   private final String value;

   public ScalarNode(Tag tag, String value, Mark startMark, Mark endMark, DumperOptions.ScalarStyle style) {
      this(tag, true, value, startMark, endMark, style);
   }

   public ScalarNode(Tag tag, boolean resolved, String value, Mark startMark, Mark endMark, DumperOptions.ScalarStyle style) {
      super(tag, startMark, endMark);
      if (value == null) {
         throw new NullPointerException("value in a Node is required.");
      } else {
         this.value = value;
         if (style == null) {
            throw new NullPointerException("Scalar style must be provided.");
         } else {
            this.style = style;
            this.resolved = resolved;
         }
      }
   }

   public DumperOptions.ScalarStyle getScalarStyle() {
      return this.style;
   }

   @Override
   public NodeId getNodeId() {
      return NodeId.scalar;
   }

   public String getValue() {
      return this.value;
   }

   @Override
   public String toString() {
      return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", value=" + this.getValue() + ")>";
   }

   public boolean isPlain() {
      return this.style == DumperOptions.ScalarStyle.PLAIN;
   }
}
