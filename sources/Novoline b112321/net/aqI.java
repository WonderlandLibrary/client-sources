package net;

import com.github.steveice10.opennbt.conversion.ConverterRegistry;
import com.github.steveice10.opennbt.tag.builtin.ByteTag;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.ShortTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.google.common.primitives.Ints;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.DA;
import net.VV;
import net.aC8;
import net.aCB;
import net.aCE;
import net.aCH;
import net.aCS;
import net.aCZ;
import net.aCf;
import net.aCi;
import net.aCl;
import net.aCm;
import net.aCs;
import net.aCv;
import net.aQU;
import net.acE;
import net.aqq;
import net.ayC;
import net.ayd;
import net.ayk;
import net.q1;
import net.r;
import viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13$4;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13$6;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13$7;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;

public class aqI extends aqq {
   private final Map j = new HashMap();
   private final String k = "VB|Protocol1_12_2To1_13|2";
   private static String[] i;

   public aqI(ayk var1) {
      super(var1, (TranslatableRewriter)null);
   }

   public static boolean a(int var0) {
      String[] var1 = a();
      return var0 >= 256 && var0 <= 259 || var0 == 261 || var0 >= 267 && var0 <= 279 || var0 >= 283 && var0 <= 286 || var0 >= 290 && var0 <= 294 || var0 >= 298 && var0 <= 317 || var0 == 346 || var0 == 359 || var0 == 398 || var0 == 442 || var0 == 443;
   }

   protected void registerPackets() {
      ((ayk)this.c).a(q1.COOLDOWN, new aCZ(this));
      ((ayk)this.c).a(q1.BLOCK_ACTION, new aCB(this));
      ((ayk)this.c).a(q1.BLOCK_ENTITY_DATA, new aCm(this));
      ((ayk)this.c).a(q1.UNLOAD_CHUNK, new BlockItemPackets1_13$4(this));
      ((ayk)this.c).a(q1.BLOCK_CHANGE, new aCS(this));
      ((ayk)this.c).a(q1.MULTI_BLOCK_CHANGE, new BlockItemPackets1_13$6(this));
      aQU var2 = new aQU(this.c, this::a, this::c);
      ((ayk)this.c).a(q1.WINDOW_ITEMS, new BlockItemPackets1_13$7(this, var2));
      ((ayk)this.c).a(q1.SET_SLOT, new aCs(this, var2));
      ((ayk)this.c).a(q1.CHUNK_DATA, new aCv(this));
      a();
      ((ayk)this.c).a(q1.EFFECT, new aCH(this));
      ((ayk)this.c).a(q1.MAP_DATA, new aCf(this));
      ((ayk)this.c).a(q1.ENTITY_EQUIPMENT, new aCi(this, var2));
      ((ayk)this.c).a(q1.WINDOW_PROPERTY, new aCE(this));
      ((ayk)this.c).a(r.CREATIVE_INVENTORY_ACTION, new aCl(this, var2));
      ((ayk)this.c).a(r.CLICK_WINDOW, new aC8(this, var2));
      if(acE.b() == null) {
         b(new String[4]);
      }

   }

   protected void registerRewrites() {
      this.j.put("minecraft:loyalty", "§7Loyalty");
      this.j.put("minecraft:impaling", "§7Impaling");
      this.j.put("minecraft:riptide", "§7Riptide");
      this.j.put("minecraft:channeling", "§7Channeling");
   }

   public Item a(Item var1) {
      String[] var2 = a();
      if(var1 == null) {
         return null;
      } else {
         int var3 = var1.getIdentifier();
         Integer var4 = null;
         boolean var5 = false;
         CompoundTag var6 = var1.getTag();
         Tag var7;
         if(var6 != null && (var7 = var6.remove(this.k)) != null) {
            var4 = (Integer)var7.getValue();
            var5 = true;
         }

         super.a(var1);
         if(var1.getIdentifier() == -1) {
            if(var3 == 362) {
               var4 = Integer.valueOf(15007744);
            }

            if(!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
               VV.d().getLogger().warning("Failed to get 1.12 item for " + var3);
            }

            var4 = Integer.valueOf(65536);
         }

         if(var6 == null) {
            var6 = var1.getTag();
         }

         var4 = Integer.valueOf(this.a(var1.getIdentifier(), var1, var6));
         var1.setIdentifier(var4.intValue() >> 16);
         var1.setData((short)(var4.intValue() & '\uffff'));
         if(a(var1.getIdentifier())) {
            Tag var8 = var6.remove("Damage");
            if(!var5 && var8 instanceof IntTag) {
               var1.setData((short)((Integer)var8.getValue()).intValue());
            }
         }

         if(var1.getIdentifier() == 358) {
            Tag var14 = var6.remove("map");
            if(var14 instanceof IntTag) {
               var1.setData((short)((Integer)var14.getValue()).intValue());
            }
         }

         this.a(var1, var6);
         CompoundTag var15 = (CompoundTag)var6.get("display");
         StringTag var9 = (StringTag)var15.get("Name");
         if(var9 instanceof StringTag) {
            var15.put(new StringTag(this.k + "|Name", var9.getValue()));
            var9.setValue(ChatRewriter.jsonTextToLegacy(var9.getValue()));
         }

         this.a(var6, false);
         this.a(var6, true);
         this.c(var6, "CanPlaceOn");
         this.c(var6, "CanDestroy");
         return var1;
      }
   }

   private int a(int var1, Item var2, CompoundTag var3) {
      a();
      Optional var5 = SpawnEggRewriter.getEntityId(var1);
      if(var5.isPresent()) {
         if(var3 == null) {
            var2.setTag(var3 = new CompoundTag("tag"));
         }

         if(!var3.contains("EntityTag")) {
            CompoundTag var6 = new CompoundTag("EntityTag");
            var6.put(new StringTag("id", (String)var5.get()));
            var3.put(var6);
         }

         return 25100288;
      } else {
         return var1 >> 4 << 16 | var1 & 15;
      }
   }

   private void c(CompoundTag var1, String var2) {
      String[] var3 = a();
      if(var1.get(var2) instanceof ListTag) {
         ListTag var4 = (ListTag)var1.get(var2);
         if(var4 != null) {
            ListTag var5 = new ListTag(var2, StringTag.class);
            var1.put(ConverterRegistry.convertToTag(this.k + "|" + var2, ConverterRegistry.convertToValue(var4)));
            Iterator var6 = var4.iterator();
            if(var6.hasNext()) {
               Tag var7 = (Tag)var6.next();
               Object var8 = var7.getValue();
               String[] var9 = var8 instanceof String?(String[])DA.a.get(((String)var8).replace("minecraft:", "")):null;
               if(var9 != null) {
                  int var11 = var9.length;
                  int var12 = 0;
                  if(var12 < var11) {
                     String var13 = var9[var12];
                     var5.add(new StringTag("", var13));
                     ++var12;
                  }
               }

               var5.add(var7);
            }

            var1.put(var5);
         }
      }
   }

   private void a(CompoundTag var1, boolean var2) {
      String[] var3 = a();
      String var4 = "StoredEnchantments";
      ListTag var5 = (ListTag)var1.get(var4);
      if(var5 != null) {
         ListTag var6 = new ListTag(this.k + "|" + var4, CompoundTag.class);
         ListTag var7 = new ListTag(var4, CompoundTag.class);
         ArrayList var8 = new ArrayList();
         boolean var9 = false;

         for(Tag var11 : var5.clone()) {
            CompoundTag var12 = (CompoundTag)var11;
            String var13 = (String)var12.get("id").getValue();
            Number var14 = (Number)var12.get("lvl").getValue();
            int var15 = var14.intValue();
            short var16 = var15 < 32767?var14.shortValue():32767;
            String var17 = (String)this.j.get(var13);
            if(var17 != null) {
               var8.add(new StringTag("", var17 + " " + EnchantmentRewriter.getRomanNumber(var16)));
               var6.add(var12);
            }

            if(!var13.isEmpty()) {
               Short var18 = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(var13);
               if(!var13.startsWith("viaversion:legacy/")) {
                  var6.add(var12);
                  if(VV.c().addCustomEnchantsToLore()) {
                     String var19 = var13;
                     int var20 = var13.indexOf(58) + 1;
                     if(var20 != 0 && var20 != var13.length()) {
                        var19 = var13.substring(var20);
                     }

                     var19 = "§7" + Character.toUpperCase(var19.charAt(0)) + var19.substring(1).toLowerCase(Locale.ROOT);
                     var8.add(new StringTag("", var19 + " " + EnchantmentRewriter.getRomanNumber(var16)));
                  }

                  if(!Via.getManager().isDebug()) {
                     continue;
                  }

                  VV.d().getLogger().warning("Found unknown enchant: " + var13);
               }

               var18 = Short.valueOf(var13.substring(18));
               if(var16 != 0) {
                  var9 = true;
               }

               CompoundTag var31 = new CompoundTag("");
               var31.put(new ShortTag("id", var18.shortValue()));
               var31.put(new ShortTag("lvl", var16));
               var7.add(var31);
            }
            break;
         }

         if(!var2 && !var9) {
            IntTag var21 = (IntTag)var1.get("HideFlags");
            if(var21 == null) {
               var21 = new IntTag("HideFlags");
               var1.put(new ByteTag(this.k + "|DummyEnchant"));
            }

            var1.put(new IntTag(this.k + "|OldHideFlags", var21.getValue().intValue()));
            if(var7.size() == 0) {
               CompoundTag var23 = new CompoundTag("");
               var23.put(new ShortTag("id", (short)0));
               var23.put(new ShortTag("lvl", (short)0));
               var7.add(var23);
            }

            int var24 = var21.getValue().intValue() | 1;
            var21.setValue(var24);
            var1.put(var21);
         }

         if(var6.size() != 0) {
            var1.put(var6);
            if(!var8.isEmpty()) {
               CompoundTag var22 = (CompoundTag)var1.get("display");
               if(var22 == null) {
                  var1.put(var22 = new CompoundTag("display"));
               }

               ListTag var25 = (ListTag)var22.get("Lore");
               if(var25 == null) {
                  var22.put(var25 = new ListTag("Lore", StringTag.class));
                  var1.put(new ByteTag(this.k + "|DummyLore"));
               }

               if(var25.size() != 0) {
                  ListTag var26 = new ListTag(this.k + "|OldLore", StringTag.class);
                  Iterator var27 = var25.iterator();
                  if(var27.hasNext()) {
                     Tag var28 = (Tag)var27.next();
                     var26.add(var28.clone());
                  }

                  var1.put(var26);
                  var8.addAll(var25.getValue());
               }

               var25.setValue(var8);
            }
         }

         var1.remove("Enchantments");
         var1.put(var7);
      }
   }

   public Item c(Item var1) {
      String[] var2 = a();
      if(var1 == null) {
         return null;
      } else {
         CompoundTag var3 = var1.getTag();
         int var4 = var1.getIdentifier() << 16 | var1.getData() & '\uffff';
         int var5 = var1.getIdentifier() << 4 | var1.getData() & 15;
         if(a(var1.getIdentifier())) {
            if(var3 == null) {
               var1.setTag(var3 = new CompoundTag("tag"));
            }

            var3.put(new IntTag("Damage", var1.getData()));
         }

         if(var1.getIdentifier() == 358) {
            if(var3 == null) {
               var1.setTag(var3 = new CompoundTag("tag"));
            }

            var3.put(new IntTag("map", var1.getData()));
         }

         if(var3 != null) {
            this.a(var1, var3);
            Tag var6 = var3.get("display");
            if(var6 instanceof CompoundTag) {
               CompoundTag var7 = (CompoundTag)var6;
               StringTag var8 = (StringTag)var7.get("Name");
               if(var8 instanceof StringTag) {
                  StringTag var9 = (StringTag)var7.remove(this.k + "|Name");
                  var8.setValue(var9 != null?var9.getValue():ChatRewriter.legacyTextToJson(var8.getValue()).toString());
               }
            }

            this.b(var3, false);
            this.b(var3, true);
            this.b(var3, "CanPlaceOn");
            this.b(var3, "CanDestroy");
            if(var1.getIdentifier() == 383) {
               label320: {
                  CompoundTag var12 = (CompoundTag)var3.get("EntityTag");
                  StringTag var14;
                  if(var12 != null && (var14 = (StringTag)var12.get("id")) != null) {
                     var5 = SpawnEggRewriter.getSpawnEggId(var14.getValue());
                     if(var5 == -1) {
                        var5 = 25100288;
                     }

                     var12.remove("id");
                     if(!var12.isEmpty()) {
                        break label320;
                     }

                     var3.remove("EntityTag");
                  }

                  var5 = 25100288;
               }
            }

            if(var3.isEmpty()) {
               var3 = null;
               var1.setTag((CompoundTag)null);
            }
         }

         int var11 = var1.getIdentifier();
         var1.setIdentifier(var5);
         super.c(var1);
         if(var1.getIdentifier() != var5 && var1.getIdentifier() != -1) {
            return var1;
         } else {
            var1.setIdentifier(var11);
            int var13 = -1;
            if(!((ayk)this.c).c().getItemMappings().inverse().containsKey(var5)) {
               if(!a(var1.getIdentifier()) && var1.getIdentifier() != 358) {
                  if(var3 == null) {
                     var1.setTag(var3 = new CompoundTag("tag"));
                  }

                  var3.put(new IntTag(this.k, var4));
               }

               if(var1.getIdentifier() == 229) {
                  var13 = 362;
               }

               if(var1.getIdentifier() == 31 && var1.getData() == 0) {
                  var5 = 512;
               }

               if(((ayk)this.c).c().getItemMappings().inverse().containsKey(var5 & -16)) {
                  var5 = var5 & -16;
               }

               if(!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                  VV.d().getLogger().warning("Failed to get 1.13 item for " + var1.getIdentifier());
               }

               var5 = 16;
            }

            if(var13 == -1) {
               var13 = ((ayk)this.c).c().getItemMappings().inverse().get(var5);
            }

            var1.setIdentifier(var13);
            var1.setData((short)0);
            return var1;
         }
      }
   }

   private void b(CompoundTag var1, String var2) {
      String[] var3 = a();
      if(var1.get(var2) instanceof ListTag) {
         ListTag var4 = (ListTag)var1.remove(this.k + "|" + var2);
         if(var4 != null) {
            var1.put(ConverterRegistry.convertToTag(var2, ConverterRegistry.convertToValue(var4)));
         }

         if((var4 = (ListTag)var1.get(var2)) != null) {
            ListTag var5 = new ListTag(var2, StringTag.class);
            Iterator var6 = var4.iterator();
            if(var6.hasNext()) {
               Tag var7 = (Tag)var6.next();
               Object var8 = var7.getValue();
               String var9 = var8.toString().replace("minecraft:", "");
               String var10 = (String)DA.c.get(Ints.tryParse(var9));
               if(var10 != null) {
                  var9 = var10;
               }

               String var11 = var9.toLowerCase(Locale.ROOT);
               String[] var12 = (String[])DA.d.get(var11);
               if(var12 != null) {
                  int var14 = var12.length;
                  int var15 = 0;
                  if(var15 < var14) {
                     String var16 = var12[var15];
                     var5.add(new StringTag("", var16));
                     ++var15;
                  }
               }

               var5.add(new StringTag("", var11));
            }

            var1.put(var5);
         }

      }
   }

   private void b(CompoundTag var1, boolean var2) {
      String[] var3 = a();
      String var4 = "StoredEnchantments";
      ListTag var5 = (ListTag)var1.get(var4);
      if(var5 != null) {
         ListTag var6 = new ListTag(var4, CompoundTag.class);
         boolean var7 = false;
         IntTag var8 = (IntTag)var1.remove(this.k + "|OldHideFlags");
         if(var8 != null) {
            var1.put(new IntTag("HideFlags", var8.getValue().intValue()));
            var7 = true;
         }

         if(var1.remove(this.k + "|DummyEnchant") != null) {
            var1.remove("HideFlags");
            var7 = true;
         }

         Iterator var14 = var5.iterator();
         if(var14.hasNext()) {
            Tag var9 = (Tag)var14.next();
            CompoundTag var10 = new CompoundTag("");
            short var11 = ((Number)((CompoundTag)var9).get("id").getValue()).shortValue();
            short var12 = ((Number)((CompoundTag)var9).get("lvl").getValue()).shortValue();
            if(var7 && var11 == 0 && var12 == 0) {
               ;
            }

            String var13 = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(Short.valueOf(var11));
            var13 = "viaversion:legacy/" + var11;
            var10.put(new StringTag("id", var13));
            var10.put(new ShortTag("lvl", var12));
            var6.add(var10);
         }

         ListTag var15 = (ListTag)var1.remove(this.k + "|Enchantments");
         if(var15 != null) {
            Iterator var16 = var15.iterator();
            if(var16.hasNext()) {
               Tag var18 = (Tag)var16.next();
               var6.add(var18);
            }
         }

         CompoundTag var17 = (CompoundTag)var1.get("display");
         if(var17 == null) {
            var1.put(var17 = new CompoundTag("display"));
         }

         ListTag var19 = (ListTag)var1.remove(this.k + "|OldLore");
         if(var19 != null) {
            ListTag var20 = (ListTag)var17.get("Lore");
            if(var20 == null) {
               var1.put(var20 = new ListTag("Lore"));
            }

            var20.setValue(var19.getValue());
         }

         if(var1.remove(this.k + "|DummyLore") != null) {
            var17.remove("Lore");
            if(var17.isEmpty()) {
               var1.remove("display");
            }
         }

         if(!var2) {
            var1.remove("ench");
         }

         var1.put(var6);
      }
   }

   private void a(Item var1, CompoundTag var2) {
      String[] var3 = a();
      if(var1.getIdentifier() == 442 || var1.getIdentifier() == 425) {
         Tag var4 = var2.get("BlockEntityTag");
         if(var4 instanceof CompoundTag) {
            CompoundTag var5 = (CompoundTag)var4;
            Tag var6 = var5.get("Base");
            if(var6 instanceof IntTag) {
               IntTag var7 = (IntTag)var6;
               var7.setValue(15 - var7.getValue().intValue());
            }

            Tag var12 = var5.get("Patterns");
            if(var12 instanceof ListTag) {
               for(Tag var10 : (ListTag)var12) {
                  if(var10 instanceof CompoundTag) {
                     IntTag var11 = (IntTag)((CompoundTag)var10).get("Color");
                     var11.setValue(15 - var11.getValue().intValue());
                     break;
                  }
               }
            }

         }
      }
   }

   private static void b(UserConnection var0, int var1, Position var2) throws Exception {
      if(FlowerPotHandler.isFlowah(var1)) {
         ayC var3 = (ayC)Via.getManager().f().b(ayC.class);
         CompoundTag var4 = var3.a(var0, var2, "minecraft:flower_pot");
         PacketWrapper var5 = new PacketWrapper(11, (ByteBuf)null, var0);
         var5.write(Type.POSITION, var2);
         var5.write(Type.VAR_INT, Integer.valueOf(0));
         var5.send(ayk.class, true);
         PacketWrapper var6 = new PacketWrapper(11, (ByteBuf)null, var0);
         var6.write(Type.POSITION, var2);
         var6.write(Type.VAR_INT, Integer.valueOf(ayk.k.getNewBlockStateId(var1)));
         var6.send(ayk.class, true);
         PacketWrapper var7 = new PacketWrapper(9, (ByteBuf)null, var0);
         var7.write(Type.POSITION, var2);
         var7.write(Type.UNSIGNED_BYTE, Short.valueOf((short)5));
         var7.write(Type.NBT, var4);
         var7.send(ayk.class, true);
      }

   }

   static ayd a(aqI var0) {
      return var0.c;
   }

   static ayd c(aqI var0) {
      return var0.c;
   }

   static void a(UserConnection var0, int var1, Position var2) throws Exception {
      b(var0, var1, var2);
   }

   static ayd b(aqI var0) {
      return var0.c;
   }

   static ayd f(aqI var0) {
      return var0.c;
   }

   static ayd d(aqI var0) {
      return var0.c;
   }

   static ayd g(aqI var0) {
      return var0.c;
   }

   static ayd e(aqI var0) {
      return var0.c;
   }

   public static void b(String[] var0) {
      i = var0;
   }

   public static String[] a() {
      return i;
   }

   static {
      b((String[])null);
   }
}
