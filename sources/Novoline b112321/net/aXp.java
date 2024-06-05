package net;

import net.aSL;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

public enum aXp implements IStringSerializable {
   STONE(0, "stone"),
   COBBLESTONE(1, "cobblestone", "cobble"),
   STONEBRICK(2, "stone_brick", "brick"),
   MOSSY_STONEBRICK(3, "mossy_brick", "mossybrick"),
   CRACKED_STONEBRICK(4, "cracked_brick", "crackedbrick"),
   CHISELED_STONEBRICK(5, "chiseled_brick", "chiseledbrick");

   private static final aXp[] d = new aXp[values().length];
   private final int a;
   private final String e;
   private final String b;
   private static final aXp[] c = new aXp[]{STONE, COBBLESTONE, STONEBRICK, MOSSY_STONEBRICK, CRACKED_STONEBRICK, CHISELED_STONEBRICK};

   private aXp(int var3, String var4) {
      this(var3, var4, var4);
   }

   private aXp(int var3, String var4, String var5) {
      this.a = var3;
      this.e = var4;
      this.b = var5;
   }

   public int a() {
      return this.a;
   }

   public String toString() {
      return this.e;
   }

   public static aXp a(int var0) {
      if(var0 >= d.length) {
         var0 = 0;
      }

      return d[var0];
   }

   public String getName() {
      return this.e;
   }

   public String b() {
      return this.b;
   }

   public abstract IBlockState getModelBlock();

   public static aXp a(IBlockState var0) {
      for(aXp var4 : values()) {
         if(var0 == var4.getModelBlock()) {
            return var4;
         }
      }

      return STONE;
   }

   aXp(int var3, String var4, aSL var5) {
      this(var3, var4);
   }

   aXp(int var3, String var4, String var5, aSL var6) {
      this(var3, var4, var5);
   }

   static {
      for(aXp var11 : values()) {
         d[var11.a()] = var11;
      }

   }
}
