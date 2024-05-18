package rina.turok.turok.values;

public class TurokBoolean {
   private String name;
   private String tag;
   private TurokGeneric value;

   public TurokBoolean(String name, String tag, boolean turok_bool) {
      this.name = name;
      this.tag = tag;
      this.value = new TurokGeneric(turok_bool);
   }

   public void set_value(boolean turok_bool) {
      this.value.set_value(turok_bool);
   }

   public String get_name() {
      return this.name;
   }

   public String get_tag() {
      return this.tag;
   }

   public boolean get_value() {
      return (Boolean)this.value.get_value();
   }
}
