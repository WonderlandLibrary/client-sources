package org.yaml.snakeyaml.introspector;

public enum BeanAccess {
   DEFAULT,
   FIELD,
   PROPERTY;

   private static final BeanAccess[] $VALUES = new BeanAccess[]{DEFAULT, FIELD, PROPERTY};
}
