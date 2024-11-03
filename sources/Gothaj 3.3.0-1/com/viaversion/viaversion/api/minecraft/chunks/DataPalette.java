package com.viaversion.viaversion.api.minecraft.chunks;

public interface DataPalette {
   int index(int var1, int var2, int var3);

   int idAt(int var1);

   default int idAt(int sectionX, int sectionY, int sectionZ) {
      return this.idAt(this.index(sectionX, sectionY, sectionZ));
   }

   void setIdAt(int var1, int var2);

   default void setIdAt(int sectionX, int sectionY, int sectionZ, int id) {
      this.setIdAt(this.index(sectionX, sectionY, sectionZ), id);
   }

   int idByIndex(int var1);

   void setIdByIndex(int var1, int var2);

   int paletteIndexAt(int var1);

   void setPaletteIndexAt(int var1, int var2);

   void addId(int var1);

   void replaceId(int var1, int var2);

   int size();

   void clear();
}
