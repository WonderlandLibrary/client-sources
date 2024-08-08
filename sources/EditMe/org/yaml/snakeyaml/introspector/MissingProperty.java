package org.yaml.snakeyaml.introspector;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public class MissingProperty extends Property {
   public MissingProperty(String var1) {
      super(var1, Object.class);
   }

   public Class[] getActualTypeArguments() {
      return new Class[0];
   }

   public void set(Object var1, Object var2) throws Exception {
   }

   public Object get(Object var1) {
      return var1;
   }

   public List getAnnotations() {
      return Collections.emptyList();
   }

   public Annotation getAnnotation(Class var1) {
      return null;
   }
}
