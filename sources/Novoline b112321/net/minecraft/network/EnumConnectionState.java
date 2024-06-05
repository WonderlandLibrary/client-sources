package net.minecraft.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.network.EnumConnectionState$1;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import org.apache.logging.log4j.LogManager;

public enum EnumConnectionState {
   HANDSHAKING(-1),
   PLAY(0),
   STATUS(1),
   LOGIN(2);

   private static final EnumConnectionState[] STATES_BY_ID = new EnumConnectionState[4];
   private static final Map STATES_BY_CLASS = Maps.newHashMap();
   private final int id;
   private Map directionMaps;
   private static final EnumConnectionState[] $VALUES = new EnumConnectionState[]{HANDSHAKING, PLAY, STATUS, LOGIN};

   private EnumConnectionState(int var3) {
      this.directionMaps = Maps.newEnumMap(EnumPacketDirection.class);
      this.id = var3;
   }

   public void registerPacket(EnumPacketDirection var1, Class var2) {
      BiMap var3 = (BiMap)this.directionMaps.computeIfAbsent(var1, EnumConnectionState::lambda$registerPacket$0);
      if(var3.containsValue(var2)) {
         String var4 = var1 + " packet " + var2 + " is already known to ID " + var3.inverse().get(var2);
         LogManager.getLogger().fatal(var4);
         throw new IllegalArgumentException(var4);
      } else {
         var3.put(Integer.valueOf(var3.size()), var2);
      }
   }

   public Integer getPacketId(EnumPacketDirection var1, Packet var2) {
      return (Integer)((BiMap)this.directionMaps.get(var1)).inverse().get(var2.getClass());
   }

   public Packet getPacket(EnumPacketDirection var1, int var2) throws InstantiationException, IllegalAccessException {
      Class var3 = (Class)((BiMap)this.directionMaps.get(var1)).get(Integer.valueOf(var2));
      return null;
   }

   public int getId() {
      return this.id;
   }

   public static EnumConnectionState getById(int var0) {
      return var0 >= -1 && var0 <= 2?STATES_BY_ID[var0 - -1]:null;
   }

   public static EnumConnectionState getFromPacket(Packet var0) {
      return (EnumConnectionState)STATES_BY_CLASS.get(var0.getClass());
   }

   private static BiMap lambda$registerPacket$0(EnumPacketDirection var0) {
      return HashBiMap.create();
   }

   EnumConnectionState(int var3, EnumConnectionState$1 var4) {
      this(var3);
   }

   static {
      for(EnumConnectionState var10 : values()) {
         int var11 = var10.getId();
         if(var11 < -1 || var11 > 2) {
            throw new Error("Invalid protocol ID " + var11);
         }

         STATES_BY_ID[var11 - -1] = var10;

         for(EnumPacketDirection var13 : var10.directionMaps.keySet()) {
            for(Class var15 : ((BiMap)var10.directionMaps.get(var13)).values()) {
               if(STATES_BY_CLASS.containsKey(var15) && STATES_BY_CLASS.get(var15) != var10) {
                  throw new Error("Packet " + var15 + " is already assigned to protocol " + STATES_BY_CLASS.get(var15) + " - can\'t reassign to " + var10);
               }

               Class var10000 = var15;

               try {
                  var10000.newInstance();
               } catch (Throwable var17) {
                  throw new Error("Packet " + var15 + " fails instantiation checks! " + var15);
               }

               STATES_BY_CLASS.put(var15, var10);
            }
         }
      }

   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
