package rina.turok.bope.bopemod.events;

import rina.turok.bope.external.BopeEventCancellable;

public class BopeEventJoin extends BopeEventCancellable {
   String name;

   public BopeEventJoin(String name) {
      this.name = name;
   }

   public String get_name() {
      return this.name;
   }
}
