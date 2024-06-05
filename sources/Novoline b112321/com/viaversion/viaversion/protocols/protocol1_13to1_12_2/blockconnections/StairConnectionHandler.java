package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.StairConnectionHandler$1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.StairConnectionHandler$StairData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.abi;
import net.bgR;

public class StairConnectionHandler extends abi {
   private static final Map stairDataMap = new HashMap();
   private static final Map c = new HashMap();

   static ConnectionData$ConnectorInitAction init() {
      LinkedList var1 = new LinkedList();
      var1.add("minecraft:oak_stairs");
      abi.b();
      var1.add("minecraft:cobblestone_stairs");
      var1.add("minecraft:brick_stairs");
      var1.add("minecraft:stone_brick_stairs");
      var1.add("minecraft:nether_brick_stairs");
      var1.add("minecraft:sandstone_stairs");
      var1.add("minecraft:spruce_stairs");
      var1.add("minecraft:birch_stairs");
      var1.add("minecraft:jungle_stairs");
      var1.add("minecraft:quartz_stairs");
      var1.add("minecraft:acacia_stairs");
      var1.add("minecraft:dark_oak_stairs");
      var1.add("minecraft:red_sandstone_stairs");
      var1.add("minecraft:purpur_stairs");
      var1.add("minecraft:prismarine_stairs");
      var1.add("minecraft:prismarine_brick_stairs");
      var1.add("minecraft:dark_prismarine_stairs");
      StairConnectionHandler var2 = new StairConnectionHandler();
      ConnectionData$ConnectorInitAction var10000 = StairConnectionHandler::lambda$init$0;
      if(PacketRemapper.b() == null) {
         abi.b(new PacketRemapper[2]);
      }

      return var10000;
   }

   private static short a(StairConnectionHandler$StairData var0) {
      abi.b();
      short var2 = 0;
      if(var0.isBottom()) {
         var2 = (short)(var2 | 1);
      }

      var2 = (short)(var2 | var0.getShape() << 1);
      var2 = (short)(var2 | var0.getType() << 4);
      var2 = (short)(var2 | var0.getFacing().ordinal() << 9);
      return var2;
   }

   public int a(bgR var1, Position var2, int var3) {
      abi.b();
      StairConnectionHandler$StairData var5 = (StairConnectionHandler$StairData)stairDataMap.get(Integer.valueOf(var3));
      return var3;
   }

   private int a(bgR var1, Position var2, StairConnectionHandler$StairData var3) {
      abi.b();
      BlockFace var5 = var3.getFacing();
      StairConnectionHandler$StairData var6 = (StairConnectionHandler$StairData)stairDataMap.get(Integer.valueOf(this.a(var1, var2.getRelative(var5))));
      if(var6 != null && var6.isBottom() == var3.isBottom()) {
         BlockFace var7 = var6.getFacing();
         if(var5.getAxis() != var7.getAxis() && this.a(var1, var3, var2, var7.opposite())) {
            return var7 == this.rotateAntiClockwise(var5)?3:4;
         }
      }

      var6 = (StairConnectionHandler$StairData)stairDataMap.get(Integer.valueOf(this.a(var1, var2.getRelative(var5.opposite()))));
      if(var6 != null && var6.isBottom() == var3.isBottom()) {
         BlockFace var9 = var6.getFacing();
         if(var5.getAxis() != var9.getAxis() && this.a(var1, var3, var2, var9)) {
            return var9 == this.rotateAntiClockwise(var5)?1:2;
         }
      }

      return 0;
   }

   private boolean a(bgR var1, StairConnectionHandler$StairData var2, Position var3, BlockFace var4) {
      abi.b();
      StairConnectionHandler$StairData var6 = (StairConnectionHandler$StairData)stairDataMap.get(Integer.valueOf(this.a(var1, var3.getRelative(var4))));
      return var6 == null || var6.getFacing() != var2.getFacing() || var6.isBottom() != var2.isBottom();
   }

   private BlockFace rotateAntiClockwise(BlockFace var1) {
      switch(StairConnectionHandler$1.$SwitchMap$com$viaversion$viaversion$api$minecraft$BlockFace[var1.ordinal()]) {
      case 1:
         return BlockFace.WEST;
      case 2:
         return BlockFace.EAST;
      case 3:
         return BlockFace.NORTH;
      case 4:
         return BlockFace.SOUTH;
      default:
         return var1;
      }
   }

   private static void lambda$init$0(List var0, StairConnectionHandler var1, WrappedBlockData var2) {
      abi.b();
      int var4 = var0.indexOf(var2.getMinecraftKey());
      if(var4 != -1) {
         if(!var2.getValue("waterlogged").equals("true")) {
            String var6 = var2.getValue("shape");
            byte var7 = -1;
            switch(var6.hashCode()) {
            case 1787472634:
               if(!var6.equals("straight")) {
                  break;
               }

               var7 = 0;
            case 823365712:
               if(!var6.equals("inner_left")) {
                  break;
               }

               var7 = 1;
            case -239805709:
               if(!var6.equals("inner_right")) {
                  break;
               }

               var7 = 2;
            case 1743932747:
               if(!var6.equals("outer_left")) {
                  break;
               }

               var7 = 3;
            case -1766998696:
               if(var6.equals("outer_right")) {
                  var7 = 4;
               }
            }

            switch(var7) {
            case 0:
               boolean var5 = false;
            case 1:
               boolean var8 = true;
            case 2:
               boolean var9 = true;
            case 3:
               boolean var10 = true;
            case 4:
               boolean var11 = true;
            default:
            }
         }
      }
   }
}
