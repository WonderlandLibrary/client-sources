package rina.turok.turok.values;

public class TurokGeneric {
   Object value;

   public TurokGeneric(Object value) {
      this.value = value;
   }

   public void set_value(Object value) {
      this.value = value;
   }

   public Object get_value() {
      return this.value;
   }
}
