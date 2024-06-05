package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.DoorConnectionHandler$DoorData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.abi;
import net.af3;
import net.bgR;

public class DoorConnectionHandler extends abi {
   private static final Map doorDataMap = new HashMap();
   private static final Map connectedStates = new HashMap();

   static ConnectionData$ConnectorInitAction init() {
      LinkedList var0 = new LinkedList();
      var0.add("minecraft:oak_door");
      var0.add("minecraft:birch_door");
      var0.add("minecraft:jungle_door");
      var0.add("minecraft:dark_oak_door");
      var0.add("minecraft:acacia_door");
      var0.add("minecraft:spruce_door");
      var0.add("minecraft:iron_door");
      DoorConnectionHandler var1 = new DoorConnectionHandler();
      return DoorConnectionHandler::lambda$init$0;
   }

   private static short getStates(DoorConnectionHandler$DoorData var0) {
      abi.b();
      short var2 = 0;
      if(var0.isLower()) {
         var2 = (short)(var2 | 1);
      }

      if(var0.isOpen()) {
         var2 = (short)(var2 | 2);
      }

      if(var0.isPowered()) {
         var2 = (short)(var2 | 4);
      }

      if(var0.isRightHinge()) {
         var2 = (short)(var2 | 8);
      }

      var2 = (short)(var2 | var0.getFacing().ordinal() << 4);
      var2 = (short)(var2 | (var0.getType() & 7) << 6);
      return var2;
   }

   public int a(bgR var1, Position var2, int var3) {
      abi.b();
      DoorConnectionHandler$DoorData var5 = (DoorConnectionHandler$DoorData)doorDataMap.get(Integer.valueOf(var3));
      return var3;
   }

   private static void lambda$init$0(List var0, DoorConnectionHandler var1, WrappedBlockData var2) {
      abi.b();
      int var4 = var0.indexOf(var2.getMinecraftKey());
      if(var4 != -1) {
         int var5 = var2.getSavedBlockStateId();
         DoorConnectionHandler$DoorData var6 = new DoorConnectionHandler$DoorData(var2.getValue("half").equals("lower"), var2.getValue("hinge").equals("right"), var2.getValue("powered").equals("true"), var2.getValue("open").equals("true"), BlockFace.valueOf(var2.getValue("facing").toUpperCase(Locale.ROOT)), var4);
         doorDataMap.put(Integer.valueOf(var5), var6);
         connectedStates.put(Short.valueOf(getStates(var6)), Integer.valueOf(var5));
         af3.h.put(var5, var1);
      }
   }
}
