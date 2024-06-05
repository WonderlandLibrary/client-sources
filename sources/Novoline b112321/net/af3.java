package net;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.BasicFenceConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.BlockData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ChestConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ChorusPlantConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.DoorConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.FireConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.FlowerConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.GlassConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.MelonConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.NetherFenceConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.PumpkinConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.RedstoneConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.SnowyGrassConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.StairConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.TripwireConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.VineConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WallConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.aPT;
import net.aPh;
import net.aRP;
import net.abi;
import net.aiV;
import net.bgR;

public class af3 {
   private static final BlockChangeRecord1_8[] a = new BlockChangeRecord1_8[0];
   public static aPh d;
   static Int2ObjectMap f = new Int2ObjectOpenHashMap(8582, 1.0F);
   public static Map g = new HashMap(8582, 1.0F);
   static Int2ObjectMap h = new Int2ObjectOpenHashMap(1);
   static Int2ObjectMap c = new Int2ObjectOpenHashMap(1);
   static IntSet b = new IntOpenHashSet(377, 1.0F);
   private static String[] e;

   public static void a(bgR var0, Position var1) {
      abi.b();
      BlockFace[] var3 = BlockFace.values();
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         BlockFace var6 = var3[var5];
         Position var7 = var1.getRelative(var6);
         int var8 = d.a(var0, var7.getX(), var7.e(), var7.getZ());
         abi var9 = (abi)h.get(var8);
         if(var9 != null) {
            int var10 = var9.a(var0, var7, var8);
            PacketWrapperImpl var11 = new PacketWrapperImpl(11, (ByteBuf)null, var0);
            var11.a(Type.POSITION, var7);
            var11.a(Type.VAR_INT, Integer.valueOf(var10));
            PacketWrapperImpl var10000 = var11;
            Class var10001 = aRP.class;
            boolean var10002 = true;
            boolean var10003 = true;

            try {
               var10000.a(var10001, var10002, var10003);
            } catch (Exception var13) {
               var13.printStackTrace();
            }
         }

         ++var5;
      }

   }

   public static void a(bgR var0, int var1, int var2, int var3) {
      abi.b();
      int var5 = -1;
      if(var5 <= 1) {
         int var6 = -1;
         if(var6 <= 1) {
            if(Math.abs(var5) + Math.abs(var6) != 0) {
               ArrayList var7 = new ArrayList();
               if(Math.abs(var5) + Math.abs(var6) == 2) {
                  int var8 = var3 * 16;
                  if(var8 < var3 * 16 + 16) {
                     int var9 = var5 == 1?0:15;
                     int var10 = var6 == 1?0:15;
                     a(var0, new Position((var1 + var5 << 4) + var9, (short)var8, (var2 + var6 << 4) + var10), var7);
                     ++var8;
                  }
               }

               int var19 = var3 * 16;
               if(var19 < var3 * 16 + 16) {
                  if(var5 == 1) {
                     boolean var22 = false;
                     boolean var26 = true;
                     boolean var11 = false;
                     boolean var12 = true;
                  }

                  if(var5 == -1) {
                     boolean var23 = true;
                     boolean var27 = true;
                     boolean var30 = false;
                     boolean var33 = true;
                  }

                  if(var6 == 1) {
                     boolean var24 = false;
                     boolean var28 = true;
                     boolean var31 = false;
                     boolean var34 = true;
                  }

                  byte var25 = 0;
                  byte var29 = 16;
                  byte var32 = 14;
                  byte var35 = 16;
                  if(var25 < var29) {
                     if(var32 < var35) {
                        a(var0, new Position((var1 + var5 << 4) + var25, (short)var19, (var2 + var6 << 4) + var32), var7);
                        int var14 = var32 + 1;
                     }

                     int var13 = var25 + 1;
                  }

                  ++var19;
               }

               if(!var7.isEmpty()) {
                  PacketWrapperImpl var21 = new PacketWrapperImpl(15, (ByteBuf)null, var0);
                  var21.a(Type.I, Integer.valueOf(var1 + var5));
                  var21.a(Type.I, Integer.valueOf(var2 + var6));
                  var21.a(Type.BLOCK_CHANGE_RECORD_ARRAY, var7.toArray(a));
                  PacketWrapperImpl var10000 = var21;
                  Class var10001 = aRP.class;
                  boolean var10002 = true;
                  boolean var10003 = true;

                  try {
                     var10000.a(var10001, var10002, var10003);
                  } catch (Exception var15) {
                     var15.printStackTrace();
                  }
               }
            }

            ++var6;
         }

         ++var5;
      }

   }

   public static void a(bgR var0, Position var1, List var2) {
      abi.b();
      int var4 = d.a(var0, var1.getX(), var1.e(), var1.getZ());
      abi var5 = d(var4);
      if(var5 != null) {
         int var6 = var5.a(var0, var1, var4);
         var2.add(new BlockChangeRecord1_8(var1.getX() & 15, var1.e(), var1.getZ() & 15, var6));
      }
   }

   public static void a(bgR var0, int var1, int var2, int var3, int var4) {
      PacketRemapper[] var5 = abi.b();
      if(a()) {
         if(a(var4)) {
            d.a(var0, var1, var2, var3, var4);
         }

         d.c(var0, var1, var2, var3);
      }
   }

   public static void a(bgR var0) {
      PacketRemapper[] var1 = abi.b();
      if(a()) {
         d.a(var0);
      }
   }

   public static boolean a() {
      return d.c();
   }

   public static void a(bgR var0, Chunk var1) {
      abi.b();
      long var3 = (long)(var1.h() << 4);
      long var5 = (long)(var1.i() << 4);
      int var7 = 0;
      if(var7 < var1.f().length) {
         aiV var8 = var1.f()[var7];
         ++var7;
      }

   }

   public static void b() {
      PacketRemapper[] var0 = abi.b();
      if(Via.c().t()) {
         Via.d().getLogger().info("Loading block connection mappings ...");
         JsonObject var1 = MappingDataLoader.a("mapping-1.13.json", true);
         JsonObject var2 = var1.getAsJsonObject("blockstates");
         Iterator var3 = var2.entrySet().iterator();
         if(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            int var5 = Integer.parseInt((String)var4.getKey());
            String var6 = ((JsonElement)var4.getValue()).getAsString();
            f.put(var5, var6);
            g.put(var6, Integer.valueOf(var5));
         }

         h = new Int2ObjectOpenHashMap(3650, 1.0F);
         if(!Via.c().E()) {
            c = new Int2ObjectOpenHashMap(1146, 1.0F);
            JsonObject var18 = MappingDataLoader.b("blockConnections.json");
            Iterator var20 = var18.entrySet().iterator();
            if(var20.hasNext()) {
               Entry var22 = (Entry)var20.next();
               int var25 = ((Integer)g.get(var22.getKey())).intValue();
               BlockData var7 = new BlockData();
               Iterator var8 = ((JsonElement)var22.getValue()).getAsJsonObject().entrySet().iterator();
               if(var8.hasNext()) {
                  Entry var9 = (Entry)var8.next();
                  String var10 = (String)var9.getKey();
                  JsonObject var11 = ((JsonElement)var9.getValue()).getAsJsonObject();
                  boolean[] var12 = new boolean[6];
                  BlockFace[] var13 = BlockFace.values();
                  int var14 = var13.length;
                  int var15 = 0;
                  if(var15 < var14) {
                     BlockFace var16 = var13[var15];
                     String var17 = var16.toString().toLowerCase(Locale.ROOT);
                     if(var11.has(var17)) {
                        var12[var16.ordinal()] = var11.getAsJsonPrimitive(var17).getAsBoolean();
                     }

                     ++var15;
                  }

                  var7.put(var10, var12);
               }

               if(((String)var22.getKey()).contains("stairs")) {
                  var7.put("allFalseIfStairPre1_12", new boolean[6]);
               }

               c.put(var25, var7);
            }
         }

         JsonObject var19 = MappingDataLoader.b("blockData.json");
         JsonArray var21 = var19.getAsJsonArray("occluding");
         Iterator var23 = var21.iterator();
         if(var23.hasNext()) {
            JsonElement var26 = (JsonElement)var23.next();
            b.add(((Integer)g.get(var26.getAsString())).intValue());
         }

         ArrayList var24 = new ArrayList();
         var24.add(PumpkinConnectionHandler.init());
         var24.addAll(BasicFenceConnectionHandler.init());
         var24.add(NetherFenceConnectionHandler.init());
         var24.addAll(WallConnectionHandler.init());
         var24.add(MelonConnectionHandler.init());
         var24.addAll(GlassConnectionHandler.init());
         var24.add(ChestConnectionHandler.init());
         var24.add(DoorConnectionHandler.init());
         var24.add(RedstoneConnectionHandler.init());
         var24.add(StairConnectionHandler.init());
         var24.add(FlowerConnectionHandler.init());
         var24.addAll(ChorusPlantConnectionHandler.init());
         var24.add(TripwireConnectionHandler.init());
         var24.add(SnowyGrassConnectionHandler.init());
         var24.add(FireConnectionHandler.init());
         if(Via.c().p()) {
            var24.add(VineConnectionHandler.init());
         }

         Iterator var27 = g.keySet().iterator();
         if(var27.hasNext()) {
            String var28 = (String)var27.next();
            WrappedBlockData var29 = WrappedBlockData.fromString(var28);
            Iterator var30 = var24.iterator();
            if(var30.hasNext()) {
               ConnectionData$ConnectorInitAction var31 = (ConnectionData$ConnectorInitAction)var30.next();
               var31.check(var29);
            }
         }

         if(Via.c().getBlockConnectionMethod().equalsIgnoreCase("packet")) {
            d = new aPT();
            Via.b().f().b(aPh.class, d);
         }

      }
   }

   public static boolean a(int var0) {
      PacketRemapper[] var1 = abi.b();
      return c.containsKey(var0) || h.containsKey(var0);
   }

   public static boolean c(int var0) {
      return h.containsKey(var0);
   }

   public static int a(bgR var0, Position var1, int var2) {
      abi.b();
      abi var4 = (abi)h.get(var2);
      return var4 != null?var4.a(var0, var1, var2):var2;
   }

   public static abi d(int var0) {
      return (abi)h.get(var0);
   }

   public static int a(String var0) {
      return ((Integer)g.getOrDefault(var0, Integer.valueOf(-1))).intValue();
   }

   public static String b(int var0) {
      return (String)f.get(var0);
   }

   static {
      b((String[])null);
   }

   public static void b(String[] var0) {
      e = var0;
   }

   public static String[] c() {
      return e;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
