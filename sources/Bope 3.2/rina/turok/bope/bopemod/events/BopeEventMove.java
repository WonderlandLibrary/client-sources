package rina.turok.bope.bopemod.events;

import rina.turok.bope.external.BopeEventCancellable;

public class BopeEventMove extends BopeEventCancellable {
   public double x;
   public double y;
   public double z;

   public BopeEventMove(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void set_x(double x) {
      this.x = x;
   }

   public void set_y(double y) {
      this.y = y;
   }

   public void set_z(double x) {
      this.z = this.z;
   }

   public double get_x() {
      return this.x;
   }

   public double get_y() {
      return this.y;
   }

   public double get_z() {
      return this.z;
   }
}
