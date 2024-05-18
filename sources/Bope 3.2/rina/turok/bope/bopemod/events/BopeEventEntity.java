package rina.turok.bope.bopemod.events;

import net.minecraft.entity.Entity;
import rina.turok.bope.external.BopeEventCancellable;

public class BopeEventEntity extends BopeEventCancellable {
   private Entity entity;

   public BopeEventEntity(Entity entity) {
      this.entity = entity;
   }

   public Entity get_entity() {
      return this.entity;
   }

   public static class BopeEventColision extends BopeEventEntity {
      private double x;
      private double y;
      private double z;

      public BopeEventColision(Entity entity, double x, double y, double z) {
         super(entity);
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
}
