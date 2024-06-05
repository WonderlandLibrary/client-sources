package com.viaversion.viaversion.api.protocol.version;

import com.google.common.base.Preconditions;

public class VersionRange {
   private final String baseVersion;
   private final int rangeFrom;
   private final int rangeTo;

   public VersionRange(String var1, int var2, int var3) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkArgument(true);
      Preconditions.checkArgument(var3 > var2);
      this.baseVersion = var1;
      this.rangeFrom = var2;
      this.rangeTo = var3;
   }

   public String getBaseVersion() {
      return this.baseVersion;
   }

   public int getRangeFrom() {
      return this.rangeFrom;
   }

   public int getRangeTo() {
      return this.rangeTo;
   }
}
