package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class StreamStartEvent extends Event {
   public StreamStartEvent(Mark startMark, Mark endMark) {
      super(startMark, endMark);
   }

   @Override
   public Event.ID getEventId() {
      return Event.ID.StreamStart;
   }
}
