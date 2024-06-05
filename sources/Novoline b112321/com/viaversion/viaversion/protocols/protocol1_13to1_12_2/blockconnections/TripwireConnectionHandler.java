package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.TripwireConnectionHandler$TripwireData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.abi;
import net.af3;
import net.bgR;

public class TripwireConnectionHandler extends abi {
   private static final Map tripwireDataMap = new HashMap();
   private static final Map connectedBlocks = new HashMap();
   private static final Map tripwireHooks = new HashMap();

   static ConnectionData$ConnectorInitAction init() {
      TripwireConnectionHandler var0 = new TripwireConnectionHandler();
      return TripwireConnectionHandler::lambda$init$0;
   }

   private static byte getStates(WrappedBlockData var0) {
      abi.b();
      byte var2 = 0;
      if(var0.getValue("attached").equals("true")) {
         var2 = (byte)(var2 | 1);
      }

      if(var0.getValue("disarmed").equals("true")) {
         var2 = (byte)(var2 | 2);
      }

      if(var0.getValue("powered").equals("true")) {
         var2 = (byte)(var2 | 4);
      }

      if(var0.getValue("east").equals("true")) {
         var2 = (byte)(var2 | 8);
      }

      if(var0.getValue("north").equals("true")) {
         var2 = (byte)(var2 | 16);
      }

      if(var0.getValue("south").equals("true")) {
         var2 = (byte)(var2 | 32);
      }

      if(var0.getValue("west").equals("true")) {
         var2 = (byte)(var2 | 64);
      }

      return var2;
   }

   public int a(bgR var1, Position var2, int var3) {
      abi.b();
      TripwireConnectionHandler$TripwireData var5 = (TripwireConnectionHandler$TripwireData)tripwireDataMap.get(Integer.valueOf(var3));
      return var3;
   }

   private static void lambda$init$0(TripwireConnectionHandler var0, WrappedBlockData var1) {
      PacketRemapper[] var2 = abi.b();
      if(var1.getMinecraftKey().equals("minecraft:tripwire_hook")) {
         tripwireHooks.put(Integer.valueOf(var1.getSavedBlockStateId()), BlockFace.valueOf(var1.getValue("facing").toUpperCase(Locale.ROOT)));
      }

      if(var1.getMinecraftKey().equals("minecraft:tripwire")) {
         TripwireConnectionHandler$TripwireData var3 = new TripwireConnectionHandler$TripwireData(var1.getValue("attached").equals("true"), var1.getValue("disarmed").equals("true"), var1.getValue("powered").equals("true"));
         tripwireDataMap.put(Integer.valueOf(var1.getSavedBlockStateId()), var3);
         connectedBlocks.put(Byte.valueOf(getStates(var1)), Integer.valueOf(var1.getSavedBlockStateId()));
         af3.h.put(var1.getSavedBlockStateId(), var0);
      }

   }
}
