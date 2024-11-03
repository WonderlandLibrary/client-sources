package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class AliasEvent extends NodeEvent {
   public AliasEvent(String anchor, Mark startMark, Mark endMark) {
      super(anchor, startMark, endMark);
      if (anchor == null) {
         throw new NullPointerException("anchor is not specified for alias");
      }
   }

   @Override
   public Event.ID getEventId() {
      return Event.ID.Alias;
   }
}
