package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;

public class ParserException extends MarkedYAMLException {
   private static final long serialVersionUID = -2349253802798398038L;

   public ParserException(String var1, Mark var2, String var3, Mark var4) {
      super(var1, var2, var3, var4, (String)null, (Throwable)null);
   }
}
