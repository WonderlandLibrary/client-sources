package rina.turok.bope.bopemod.events;

import rina.turok.bope.external.BopeEventCancellable;

public class BopeEventLeave extends BopeEventCancellable {
   String name;

   public BopeEventLeave(String name) {
      this.name = name;
   }

   public String get_name() {
      return this.name;
   }
}
