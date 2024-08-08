package org.yaml.snakeyaml.extensions.compactnotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompactData {
   private String prefix;
   private List arguments = new ArrayList();
   private Map properties = new HashMap();

   public CompactData(String var1) {
      this.prefix = var1;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public Map getProperties() {
      return this.properties;
   }

   public List getArguments() {
      return this.arguments;
   }

   public String toString() {
      return "CompactData: " + this.prefix + " " + this.properties;
   }
}
