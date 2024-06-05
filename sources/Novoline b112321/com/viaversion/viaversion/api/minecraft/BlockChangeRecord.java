package com.viaversion.viaversion.api.minecraft;

public interface BlockChangeRecord {
   byte getSectionX();

   byte getSectionY();

   byte getSectionZ();

   short getY(int var1);

   /** @deprecated */
   @Deprecated
   default short getY() {
      return this.getY(-1);
   }

   int getBlockId();

   void setBlockId(int var1);
}
