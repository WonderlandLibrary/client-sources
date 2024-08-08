package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;

public abstract class AbstractConstruct implements Construct {
   public void construct2ndStep(Node var1, Object var2) {
      if (var1.isTwoStepsConstruction()) {
         throw new IllegalStateException("Not Implemented in " + this.getClass().getName());
      } else {
         throw new YAMLException("Unexpected recursive structure for Node: " + var1);
      }
   }
}
