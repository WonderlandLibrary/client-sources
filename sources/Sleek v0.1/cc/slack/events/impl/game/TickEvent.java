package cc.slack.events.impl.game;

import cc.slack.events.Event;
import cc.slack.events.State;

public class TickEvent extends Event {
   private State state;

   public TickEvent() {
      this.state = State.PRE;
   }

   public State getState() {
      return this.state;
   }

   public void setState(State state) {
      this.state = state;
   }
}
