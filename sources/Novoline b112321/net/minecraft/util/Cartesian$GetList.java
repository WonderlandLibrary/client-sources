package net.minecraft.util;

import com.google.common.base.Function;
import java.util.Arrays;
import java.util.List;
import net.aZA;

class Cartesian$GetList implements Function {
   private Cartesian$GetList() {
   }

   public List apply(Object[] var1) {
      return Arrays.asList((Object[])var1);
   }

   Cartesian$GetList(aZA var1) {
      this();
   }
}
