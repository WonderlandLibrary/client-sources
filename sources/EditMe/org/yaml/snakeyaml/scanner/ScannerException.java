package org.yaml.snakeyaml.scanner;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;

public class ScannerException extends MarkedYAMLException {
   private static final long serialVersionUID = 4782293188600445954L;

   public ScannerException(String var1, Mark var2, String var3, Mark var4, String var5) {
      super(var1, var2, var3, var4, var5);
   }

   public ScannerException(String var1, Mark var2, String var3, Mark var4) {
      this(var1, var2, var3, var4, (String)null);
   }
}
