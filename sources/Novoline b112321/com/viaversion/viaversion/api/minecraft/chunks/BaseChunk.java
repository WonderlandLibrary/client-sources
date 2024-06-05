package com.viaversion.viaversion.api.minecraft.chunks;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import java.util.List;
import net.aiV;

public class BaseChunk implements Chunk {
   protected final int e;
   protected final int g;
   protected final boolean fullChunk;
   protected boolean ignoreOldLightData;
   protected final int d;
   protected final aiV[] f;
   protected int[] biomeData;
   protected CompoundTag h;
   protected final List blockEntities;

   public BaseChunk(int var1, int var2, boolean var3, boolean var4, int var5, aiV[] var6, int[] var7, CompoundTag var8, List var9) {
      aiV.b();
      super();
      this.e = var1;
      this.g = var2;
      this.fullChunk = var3;
      this.ignoreOldLightData = var4;
      this.d = var5;
      this.f = var6;
      this.biomeData = var7;
      this.h = var8;
      this.blockEntities = var9;
   }

   public BaseChunk(int var1, int var2, boolean var3, boolean var4, int var5, aiV[] var6, int[] var7, List var8) {
      this(var1, var2, var3, var4, var5, var6, var7, (CompoundTag)null, var8);
   }

   public boolean isBiomeData() {
      return this.biomeData != null;
   }

   public int h() {
      return this.e;
   }

   public int i() {
      return this.g;
   }

   public boolean isFullChunk() {
      return this.fullChunk;
   }

   public boolean isIgnoreOldLightData() {
      return this.ignoreOldLightData;
   }

   public void setIgnoreOldLightData(boolean var1) {
      this.ignoreOldLightData = var1;
   }

   public int k() {
      return this.d;
   }

   public aiV[] f() {
      return this.f;
   }

   public int[] getBiomeData() {
      return this.biomeData;
   }

   public void setBiomeData(int[] var1) {
      this.biomeData = var1;
   }

   public CompoundTag c() {
      return this.h;
   }

   public void a(CompoundTag var1) {
      this.h = var1;
   }

   public List getBlockEntities() {
      return this.blockEntities;
   }
}
