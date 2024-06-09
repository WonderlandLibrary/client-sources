package exhibition.module.data;

import com.google.gson.annotations.Expose;
import java.lang.reflect.Type;

public class Setting {
   private final String name;
   private final String desc;
   private final Type type;
   private final double inc;
   private final double min;
   private final double max;
   @Expose
   private Object value;

   public String getDesc() {
      return this.desc;
   }

   public Setting(String name, Object value, String desc) {
      this.name = name;
      this.value = value;
      this.type = value.getClass().getGenericSuperclass();
      this.desc = desc;
      if (value instanceof Number) {
         this.inc = 0.5D;
         this.min = 1.0D;
         this.max = 20.0D;
      } else {
         this.inc = 0.0D;
         this.min = 0.0D;
         this.max = 0.0D;
      }

   }

   public Setting(String name, Object value, String desc, double inc, double min, double max) {
      this.name = name;
      this.value = value;
      this.type = value.getClass().getGenericSuperclass();
      this.desc = desc;
      this.inc = inc;
      this.min = min;
      this.max = max;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public Object getValue() {
      return this.value;
   }

   public String getName() {
      return this.name;
   }

   public Type getType() {
      return this.type;
   }

   public double getInc() {
      return this.inc;
   }

   public double getMin() {
      return this.min;
   }

   public double getMax() {
      return this.max;
   }

   public void update(Setting setting) {
      this.value = setting.value;
   }
}
