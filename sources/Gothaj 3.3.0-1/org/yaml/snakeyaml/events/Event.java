package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public abstract class Event {
   private final Mark startMark;
   private final Mark endMark;

   public Event(Mark startMark, Mark endMark) {
      this.startMark = startMark;
      this.endMark = endMark;
   }

   @Override
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

   public boolean is(Event.ID id) {
      return this.getEventId() == id;
   }

   public abstract Event.ID getEventId();

   @Override
   public boolean equals(Object obj) {
      return obj instanceof Event ? this.toString().equals(obj.toString()) : false;
   }

   @Override
   public int hashCode() {
      return this.toString().hashCode();
   }

   public static enum ID {
      Alias,
      Comment,
      DocumentEnd,
      DocumentStart,
      MappingEnd,
      MappingStart,
      Scalar,
      SequenceEnd,
      SequenceStart,
      StreamEnd,
      StreamStart;
   }
}
