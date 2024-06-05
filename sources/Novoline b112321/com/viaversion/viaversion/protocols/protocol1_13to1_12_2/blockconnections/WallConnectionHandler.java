package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.AbstractFenceConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.ArrayList;
import java.util.List;
import net.abi;
import net.bgR;

public class WallConnectionHandler extends AbstractFenceConnectionHandler {
   private static final BlockFace[] BLOCK_FACES = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
   private static final int[] OPPOSITES = new int[]{3, 2, 1, 0};

   static List init() {
      ArrayList var0 = new ArrayList(2);
      var0.add((new WallConnectionHandler("cobbleWallConnections")).getInitAction("minecraft:cobblestone_wall"));
      var0.add((new WallConnectionHandler("cobbleWallConnections")).getInitAction("minecraft:mossy_cobblestone_wall"));
      return var0;
   }

   public WallConnectionHandler(String var1) {
      super(var1);
   }

   protected byte getStates(WrappedBlockData var1) {
      abi.b();
      byte var3 = super.getStates(var1);
      if(var1.getValue("up").equals("true")) {
         var3 = (byte)(var3 | 16);
      }

      return var3;
   }

   protected byte a(bgR var1, Position var2, int var3) {
      abi.b();
      byte var5 = super.a(var1, var2, var3);
      if(this.c(var1, var2)) {
         var5 = (byte)(var5 | 16);
      }

      return var5;
   }

   public boolean c(bgR var1, Position var2) {
      PacketRemapper[] var3 = abi.b();
      if(!this.isWall(this.a(var1, var2.getRelative(BlockFace.BOTTOM))) && !this.isWall(this.a(var1, var2.getRelative(BlockFace.TOP)))) {
         int var4 = this.b(var1, var2);
         if(var4 != 0 && var4 != 15) {
            int var5 = 0;
            if(var5 < BLOCK_FACES.length) {
               if((var4 & 1 << var5) != 0 && (var4 & 1 << OPPOSITES[var5]) == 0) {
                  return true;
               }

               ++var5;
            }

            return false;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   private int b(bgR var1, Position var2) {
      abi.b();
      int var4 = 0;
      int var5 = 0;
      if(var5 < BLOCK_FACES.length) {
         if(this.isWall(this.a(var1, var2.getRelative(BLOCK_FACES[var5])))) {
            var4 |= 1 << var5;
         }

         ++var5;
      }

      return var4;
   }

   private boolean isWall(int var1) {
      return this.getBlockStates().contains(Integer.valueOf(var1));
   }
}
