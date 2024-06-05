package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.fc;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import optifine.BlockPosM;
import optifine.Config;
import optifine.MatchBlock;

public class ClearWater {
   public static void updateWaterOpacity(GameSettings var0, World var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      byte var3 = 3;
      if(var0.ofClearWater) {
         var3 = 1;
      }

      fc.a(Blocks.water, var3);
      fc.a(Blocks.flowing_water, var3);
      if(var1 != null) {
         IChunkProvider var26 = var1.getChunkProvider();
         Entity var4 = Config.getMinecraft().getRenderViewEntity();
         if(var4 != null) {
            int var5 = (int)var4.posX / 16;
            int var6 = (int)var4.posZ / 16;
            int var7 = var5 - 512;
            int var8 = var5 + 512;
            int var9 = var6 - 512;
            int var10 = var6 + 512;
            int var11 = 0;
            if(var7 < var8) {
               if(var9 < var10) {
                  if(var26.chunkExists(var7, var9)) {
                     Chunk var14 = var26.provideChunk(var7, var9);
                     if(!(var14 instanceof EmptyChunk)) {
                        int var15 = var7 << 4;
                        int var16 = var9 << 4;
                        int var17 = var15 + 16;
                        int var18 = var16 + 16;
                        BlockPosM var19 = new BlockPosM(0, 0, 0);
                        BlockPosM var20 = new BlockPosM(0, 0, 0);
                        if(var15 < var17) {
                           if(var16 < var18) {
                              var19.setXyz(var15, 0, var16);
                              BlockPos var23 = var1.getPrecipitationHeight(var19);
                              int var24 = 0;
                              if(var24 < var23.getY()) {
                                 var20.setXyz(var15, var24, var16);
                                 IBlockState var25 = var1.getBlockState(var20);
                                 if(var25.getBlock().getMaterial() == Material.water) {
                                    var1.markBlocksDirtyVertical(var15, var16, var20.getY(), var23.getY());
                                    ++var11;
                                 }

                                 ++var24;
                              }

                              int var22 = var16 + 1;
                           }

                           int var21 = var15 + 1;
                        }
                     }
                  }

                  int var13 = var9 + 1;
               }

               int var12 = var7 + 1;
            }

            if(var11 > 0) {
               String var27 = "server";
               if(Config.isMinecraftThread()) {
                  var27 = "client";
               }

               Config.dbg("ClearWater (" + var27 + ") relighted " + var11 + " chunks");
            }
         }
      }

   }
}
