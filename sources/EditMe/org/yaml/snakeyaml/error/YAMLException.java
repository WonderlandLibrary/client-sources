package org.yaml.snakeyaml.error;

public class YAMLException extends RuntimeException {
   private static final long serialVersionUID = -4738336175050337570L;

   public YAMLException(String var1) {
      super(var1);
   }

   public YAMLException(Throwable var1) {
      super(var1);
   }

   public YAMLException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
