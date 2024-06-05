package net;

import com.viaversion.viaversion.api.type.Type;
import net.aMz;

public abstract class wM extends Type {
   public wM() {
      super("Item", aMz.class);
   }

   public wM(String var1) {
      super(var1, aMz.class);
   }

   public Class getBaseClass() {
      return wM.class;
   }
}
