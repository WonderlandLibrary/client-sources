package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public abstract class Event {
   private final Mark startMark;
   private final Mark endMark;

   public Event(Mark var1, Mark var2) {
      this.startMark = var1;
      this.endMark = var2;
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

   public abstract boolean is(Event.ID var1);

   public boolean equals(Object var1) {
      return var1 instanceof Event ? this.toString().equals(var1.toString()) : false;
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public static enum ID {
      Alias,
      DocumentEnd,
      DocumentStart,
      MappingEnd,
      MappingStart,
      Scalar,
      SequenceEnd,
      SequenceStart,
      StreamEnd,
      StreamStart;

      private static final Event.ID[] $VALUES = new Event.ID[]{Alias, DocumentEnd, DocumentStart, MappingEnd, MappingStart, Scalar, SequenceEnd, SequenceStart, StreamEnd, StreamStart};
   }
}
