package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;

public class ConstructorException extends MarkedYAMLException {
   private static final long serialVersionUID = -8816339931365239910L;

   protected ConstructorException(String var1, Mark var2, String var3, Mark var4, Throwable var5) {
      super(var1, var2, var3, var4, var5);
   }

   protected ConstructorException(String var1, Mark var2, String var3, Mark var4) {
      this(var1, var2, var3, var4, (Throwable)null);
   }
}
