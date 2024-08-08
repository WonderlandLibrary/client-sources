package org.yaml.snakeyaml.serializer;

import java.text.NumberFormat;
import org.yaml.snakeyaml.nodes.Node;

public class NumberAnchorGenerator implements AnchorGenerator {
   private int lastAnchorId = 0;

   public NumberAnchorGenerator(int var1) {
      this.lastAnchorId = var1;
   }

   public String nextAnchor(Node var1) {
      ++this.lastAnchorId;
      NumberFormat var2 = NumberFormat.getNumberInstance();
      var2.setMinimumIntegerDigits(3);
      var2.setMaximumFractionDigits(0);
      var2.setGroupingUsed(false);
      String var3 = var2.format((long)this.lastAnchorId);
      return "id" + var3;
   }
}
