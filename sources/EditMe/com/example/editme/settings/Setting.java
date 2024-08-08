package com.example.editme.settings;

import com.example.editme.util.converters.Convertable;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class Setting implements Convertable, ISettingSafe {
   private Predicate restriction;
   private Predicate visibilityPredicate;
   private final Class valueType;
   Object value;
   String name;
   private BiConsumer consumer;

   public boolean isVisible() {
      return this.visibilityPredicate.test(this.getValue());
   }

   public BiConsumer changeListener() {
      return this.consumer;
   }

   public String getName() {
      return this.name;
   }

   public String getValueAsString() {
      return ((JsonElement)this.converter().convert(this.getValue())).toString();
   }

   public Setting(Object var1, Predicate var2, BiConsumer var3, String var4, Predicate var5) {
      this.value = var1;
      this.valueType = var1.getClass();
      this.restriction = var2;
      this.consumer = var3;
      this.name = var4;
      this.visibilityPredicate = var5;
   }

   public Class getValueClass() {
      return this.valueType;
   }

   public boolean setValue(Object var1) {
      Object var2 = this.getValue();
      if (!this.restriction.test(var1)) {
         return false;
      } else {
         this.value = var1;
         this.consumer.accept(var2, var1);
         return true;
      }
   }

   public void setValueFromString(String var1) {
      JsonParser var2 = new JsonParser();
      this.setValue(this.converter().reverse().convert(var2.parse(var1)));
   }

   public Object getValue() {
      return this.value;
   }
}
