package cc.slack.events.impl.render;

import cc.slack.events.Event;
import cc.slack.events.State;

public class RenderScoreboard extends Event {
   State state;

   public RenderScoreboard(State state) {
      this.state = state;
   }

   public State getState() {
      return this.state;
   }

   public boolean isPre() {
      return this.state == State.PRE;
   }

   public void setState(State state) {
      this.state = state;
   }
}
