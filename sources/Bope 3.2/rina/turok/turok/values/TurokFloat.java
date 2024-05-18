package rina.turok.turok.values;

public class TurokFloat {
   private String name;
   private String tag;
   private float value;
   private float max;
   private float min;

   public TurokFloat(String name, String tag, float turok_float, float min, float max) {
      this.name = name;
      this.tag = tag;
      this.value = this.value;
      this.max = max;
      this.min = min;
   }

   public void set_value(float turok_float) {
      this.value = turok_float;
   }

   public void set_slider_value(float turok_float) {
      if (turok_float >= this.max) {
         this.value = this.max;
      } else if (turok_float <= this.min) {
         this.value = this.min;
      } else {
         this.value = turok_float;
      }

   }

   public String get_name() {
      return this.name;
   }

   public String get_tag() {
      return this.tag;
   }

   public float get_value() {
      return this.value;
   }
}
