package net;

import com.github.steveice10.opennbt.conversion.ConverterRegistry;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.ShortTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.google.common.primitives.Ints;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
import java.util.Iterator;
import java.util.Locale;
import net.BS;
import net.DA;
import net.aFA;
import net.aMz;
import net.aQU;
import net.aRP;
import net.aW8;
import net.aWB;
import net.aWE;
import net.aWH;
import net.aWZ;
import net.aWf;
import net.aWi;
import net.aWl;
import net.azW;
import net.uN;
import net.md_5.bungee.api.ChatColor;

public class vo {
   private static final String a = "ViaVersion|Protocol1_13To1_12_2";

   public static void a(aRP var0) {
      aQU var2 = new aQU(var0, vo::b, vo::a);
      var0.a(azW.SET_SLOT, new aWZ(var2));
      var0.a(azW.WINDOW_ITEMS, new aWH(var2));
      var0.a(azW.WINDOW_PROPERTY, new aWf(var0));
      var0.a(azW.PLUGIN_MESSAGE, new aWi());
      var0.a(azW.ENTITY_EQUIPMENT, new aWE(var2));
      var0.a(uN.CLICK_WINDOW, new aWl(var2));
      BS.b();
      var0.a(uN.PLUGIN_MESSAGE, new aW8());
      var0.a(uN.CREATIVE_INVENTORY_ACTION, new aWB(var2));
      if(PacketRemapper.b() == null) {
         BS.b(new PacketRemapper[3]);
      }

   }

   public static void b(aMz var0) {
      PacketRemapper[] var1 = BS.b();
      if(var0 != null) {
         CompoundTag var2 = var0.d();
         int var3 = var0.e() << 16 | var0.c() & '\uffff';
         int var4 = var0.e() << 4 | var0.c() & 15;
         if(a(var0.e())) {
            if(var2 == null) {
               var0.a(var2 = new CompoundTag("tag"));
            }

            var2.put(new IntTag("Damage", var0.c()));
         }

         if(var0.e() == 358) {
            if(var2 == null) {
               var0.a(var2 = new CompoundTag("tag"));
            }

            var2.put(new IntTag("map", var0.c()));
         }

         boolean var5 = var0.e() == 425;
         if((var5 || var0.e() == 442) && var2.get("BlockEntityTag") instanceof CompoundTag) {
            CompoundTag var6 = (CompoundTag)var2.get("BlockEntityTag");
            if(var6.get("Base") instanceof IntTag) {
               IntTag var7 = (IntTag)var6.get("Base");
               if(var5) {
                  var4 = 6800 + var7.getValue().intValue();
               }

               var7.setValue(15 - var7.getValue().intValue());
            }

            if(var6.get("Patterns") instanceof ListTag) {
               Iterator var28 = ((ListTag)var6.get("Patterns")).iterator();
               if(var28.hasNext()) {
                  Tag var8 = (Tag)var28.next();
                  if(var8 instanceof CompoundTag) {
                     IntTag var9 = (IntTag)((CompoundTag)var8).get("Color");
                     var9.setValue(15 - var9.getValue().intValue());
                  }
               }
            }
         }

         if(var2.get("display") instanceof CompoundTag) {
            CompoundTag var22 = (CompoundTag)var2.get("display");
            if(var22.get("Name") instanceof StringTag) {
               StringTag var29 = (StringTag)var22.get("Name");
               var22.put(new StringTag("ViaVersion|Protocol1_13To1_12_2|Name", var29.getValue()));
               var29.setValue(ChatRewriter.a(var29.getValue(), ChatColor.WHITE, true));
            }
         }

         if(var2.get("ench") instanceof ListTag) {
            ListTag var23 = (ListTag)var2.get("ench");
            ListTag var30 = new ListTag("Enchantments", CompoundTag.class);
            Iterator var35 = var23.iterator();
            if(var35.hasNext()) {
               Tag var39 = (Tag)var35.next();
               if(var39 instanceof CompoundTag) {
                  CompoundTag var10 = new CompoundTag("");
                  short var11 = ((Number)((CompoundTag)var39).get("id").getValue()).shortValue();
                  String var12 = (String)aRP.m.getOldEnchantmentsIds().get(Short.valueOf(var11));
                  var12 = "viaversion:legacy/" + var11;
                  var10.put(new StringTag("id", var12));
                  var10.put(new ShortTag("lvl", ((Number)((CompoundTag)var39).get("lvl").getValue()).shortValue()));
                  var30.add(var10);
               }
            }

            var2.remove("ench");
            var2.put(var30);
         }

         if(var2.get("StoredEnchantments") instanceof ListTag) {
            ListTag var24 = (ListTag)var2.get("StoredEnchantments");
            ListTag var31 = new ListTag("StoredEnchantments", CompoundTag.class);
            Iterator var36 = var24.iterator();
            if(var36.hasNext()) {
               Tag var40 = (Tag)var36.next();
               if(var40 instanceof CompoundTag) {
                  CompoundTag var43 = new CompoundTag("");
                  short var46 = ((Number)((CompoundTag)var40).get("id").getValue()).shortValue();
                  String var50 = (String)aRP.m.getOldEnchantmentsIds().get(Short.valueOf(var46));
                  var50 = "viaversion:legacy/" + var46;
                  var43.put(new StringTag("id", var50));
                  var43.put(new ShortTag("lvl", ((Number)((CompoundTag)var40).get("lvl").getValue()).shortValue()));
                  var31.add(var43);
               }
            }

            var2.remove("StoredEnchantments");
            var2.put(var31);
         }

         if(var2.get("CanPlaceOn") instanceof ListTag) {
            ListTag var25 = (ListTag)var2.get("CanPlaceOn");
            ListTag var32 = new ListTag("CanPlaceOn", StringTag.class);
            var2.put(ConverterRegistry.convertToTag("ViaVersion|Protocol1_13To1_12_2|CanPlaceOn", ConverterRegistry.convertToValue(var25)));
            Iterator var37 = var25.iterator();
            if(var37.hasNext()) {
               Tag var41 = (Tag)var37.next();
               Object var44 = var41.getValue();
               String var47 = var44.toString().replace("minecraft:", "");
               String var52 = (String)DA.c.get(Ints.tryParse(var47));
               if(var52 != null) {
                  var47 = var52;
               }

               String[] var13 = (String[])DA.d.get(var47.toLowerCase(Locale.ROOT));
               if(var13 != null) {
                  int var15 = var13.length;
                  int var16 = 0;
                  if(var16 < var15) {
                     String var17 = var13[var16];
                     var32.add(new StringTag("", var17));
                     ++var16;
                  }
               }

               var32.add(new StringTag("", var47.toLowerCase(Locale.ROOT)));
            }

            var2.put(var32);
         }

         if(var2.get("CanDestroy") instanceof ListTag) {
            ListTag var26 = (ListTag)var2.get("CanDestroy");
            ListTag var33 = new ListTag("CanDestroy", StringTag.class);
            var2.put(ConverterRegistry.convertToTag("ViaVersion|Protocol1_13To1_12_2|CanDestroy", ConverterRegistry.convertToValue(var26)));
            Iterator var38 = var26.iterator();
            if(var38.hasNext()) {
               Tag var42 = (Tag)var38.next();
               Object var45 = var42.getValue();
               String var48 = var45.toString().replace("minecraft:", "");
               String var53 = (String)DA.c.get(Ints.tryParse(var48));
               if(var53 != null) {
                  var48 = var53;
               }

               String[] var54 = (String[])DA.d.get(var48.toLowerCase(Locale.ROOT));
               if(var54 != null) {
                  int var55 = var54.length;
                  int var57 = 0;
                  if(var57 < var55) {
                     String var59 = var54[var57];
                     var33.add(new StringTag("", var59));
                     ++var57;
                  }
               }

               var33.add(new StringTag("", var48.toLowerCase(Locale.ROOT)));
            }

            var2.put(var33);
         }

         if(var0.e() == 383) {
            if(var2.get("EntityTag") instanceof CompoundTag) {
               CompoundTag var27 = (CompoundTag)var2.get("EntityTag");
               if(var27.get("id") instanceof StringTag) {
                  StringTag var34 = (StringTag)var27.get("id");
                  var4 = SpawnEggRewriter.getSpawnEggId(var34.getValue());
                  if(var4 == -1) {
                     var4 = 25100288;
                  }

                  var27.remove("id");
                  if(var27.isEmpty()) {
                     var2.remove("EntityTag");
                  }
               }

               var4 = 25100288;
            }

            var4 = 25100288;
         }

         if(var2.isEmpty()) {
            var2 = null;
            var0.a((CompoundTag)null);
         }

         if(!aRP.m.g().containsKey(var4)) {
            if(!a(var0.e()) && var0.e() != 358) {
               if(var2 == null) {
                  var0.a(var2 = new CompoundTag("tag"));
               }

               var2.put(new IntTag("ViaVersion|Protocol1_13To1_12_2", var3));
            }

            if(var0.e() == 31 && var0.c() == 0) {
               var4 = 512;
            }

            if(aRP.m.g().containsKey(var4 & -16)) {
               var4 = var4 & -16;
            }

            if(!Via.c().j() || Via.b().c()) {
               Via.d().getLogger().warning("Failed to get 1.13 item for " + var0.e());
            }

            var4 = 16;
         }

         var0.a(aRP.m.g().get(var4));
         var0.a((short)0);
      }
   }

   public static String b(String var0) {
      BS.b();
      byte var3 = -1;
      switch(var0.hashCode()) {
      case -37059198:
         if(!var0.equals("MC|TrList")) {
            break;
         }

         var3 = 0;
      case -294893183:
         if(!var0.equals("MC|Brand")) {
            break;
         }

         var3 = 1;
      case -295921722:
         if(!var0.equals("MC|BOpen")) {
            break;
         }

         var3 = 2;
      case 125533714:
         if(!var0.equals("MC|DebugPath")) {
            break;
         }

         var3 = 3;
      case 2076087261:
         if(!var0.equals("MC|DebugNeighborsUpdate")) {
            break;
         }

         var3 = 4;
      case 92413603:
         if(!var0.equals("REGISTER")) {
            break;
         }

         var3 = 5;
      case 1321107516:
         if(!var0.equals("UNREGISTER")) {
            break;
         }

         var3 = 6;
      case 1537336522:
         if(!var0.equals("BungeeCord")) {
            break;
         }

         var3 = 7;
      case -234943831:
         if(var0.equals("bungeecord:main")) {
            var3 = 8;
         }
      }

      switch(var3) {
      case 0:
         return "minecraft:trader_list";
      case 1:
         return "minecraft:brand";
      case 2:
         return "minecraft:book_open";
      case 3:
         return "minecraft:debug/paths";
      case 4:
         return "minecraft:debug/neighbors_update";
      case 5:
         return "minecraft:register";
      case 6:
         return "minecraft:unregister";
      case 7:
         return "bungeecord:main";
      case 8:
         return null;
      default:
         String var4 = (String)aRP.m.getChannelMappings().get(var0);
         return var4 != null?var4:(MappingData.isValid1_13Channel(var0)?var0:null);
      }
   }

   public static void a(aMz var0) {
      PacketRemapper[] var1 = BS.b();
   }

   public static String a(String var0) {
      BS.b();
      var0 = MappingData.validateNewChannel(var0);
      if(var0 == null) {
         return null;
      } else {
         byte var3 = -1;
         switch(var0.hashCode()) {
         case 1963953250:
            if(!var0.equals("minecraft:trader_list")) {
               break;
            }

            var3 = 0;
         case -420924333:
            if(!var0.equals("minecraft:book_open")) {
               break;
            }

            var3 = 1;
         case 832866277:
            if(!var0.equals("minecraft:debug/paths")) {
               break;
            }

            var3 = 2;
         case 1745645488:
            if(!var0.equals("minecraft:debug/neighbors_update")) {
               break;
            }

            var3 = 3;
         case 339275216:
            if(!var0.equals("minecraft:register")) {
               break;
            }

            var3 = 4;
         case -1963049943:
            if(!var0.equals("minecraft:unregister")) {
               break;
            }

            var3 = 5;
         case -1149721734:
            if(!var0.equals("minecraft:brand")) {
               break;
            }

            var3 = 6;
         case -234943831:
            if(var0.equals("bungeecord:main")) {
               var3 = 7;
            }
         }

         switch(var3) {
         case 0:
            return "MC|TrList";
         case 1:
            return "MC|BOpen";
         case 2:
            return "MC|DebugPath";
         case 3:
            return "MC|DebugNeighborsUpdate";
         case 4:
            return "REGISTER";
         case 5:
            return "UNREGISTER";
         case 6:
            return "MC|Brand";
         case 7:
            return "BungeeCord";
         default:
            String var4 = (String)aRP.m.getChannelMappings().inverse().get(var0);
            return var4 != null?var4:(var0.length() > 20?var0.substring(0, 20):var0);
         }
      }
   }

   public static boolean a(int var0) {
      PacketRemapper[] var1 = BS.b();
      return var0 >= 256 && var0 <= 259 || var0 == 261 || var0 >= 267 && var0 <= 279 || var0 >= 283 && var0 <= 286 || var0 >= 290 && var0 <= 294 || var0 >= 298 && var0 <= 317 || var0 == 346 || var0 == 359 || var0 == 398 || var0 == 442 || var0 == 443;
   }
}
