package net.shadersmod.client;

import net.optifine.MatchBlock;
import net.shadersmod.client.ShaderOption;

public class BlockAlias {
   private int blockId;
   private MatchBlock[] matchBlocks;

   public BlockAlias(int var1, MatchBlock[] var2) {
      this.blockId = var1;
      this.matchBlocks = var2;
   }

   public int getBlockId() {
      return this.blockId;
   }

   public boolean matches(int var1, int var2) {
      MatchBlock[] var4 = this.matchBlocks;
      ShaderOption.p();
      int var5 = var4.length;
      int var6 = 0;
      if(var6 < var5) {
         MatchBlock var7 = var4[var6];
         if(var7.matches(var1, var2)) {
            return true;
         }

         ++var6;
      }

      return false;
   }

   public int[] getMatchBlockIds() {
      ShaderOption.p();
      int[] var2 = new int[this.matchBlocks.length];
      int var3 = 0;
      if(var3 < var2.length) {
         var2[var3] = this.matchBlocks[var3].getBlockId();
         ++var3;
      }

      return var2;
   }
}
