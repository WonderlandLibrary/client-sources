package net;

import com.github.steveice10.opennbt.conversion.ConverterRegistry;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import java.util.Iterator;
import net.aQU;
import net.acE;
import net.amt;
import net.ao7;
import net.ao9;
import net.aoC;
import net.aoE;
import net.aoH;
import net.aoK;
import net.aoM;
import net.aoQ;
import net.aoV;
import net.aoW;
import net.aoZ;
import net.aoc;
import net.aof;
import net.aoi;
import net.aon;
import net.aoo;
import net.aqq;
import net.awj;
import net.ayd;
import net.q1;
import net.uN;
import viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14$14;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.api.rewriters.BlockRewriter;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;

public class aq6 extends aqq {
   private EnchantmentRewriter j;
   private static acE[] i;

   public aq6(Protocol1_13_2To1_14 var1, TranslatableRewriter var2) {
      super(var1, var2);
   }

   protected void registerPackets() {
      ((Protocol1_13_2To1_14)this.c).a(uN.EDIT_BOOK, new aoQ(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.OPEN_WINDOW, new aoW(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.OPEN_HORSE_WINDOW, q1.OPEN_WINDOW, new aoV(this));
      aQU var2 = new aQU(this.c, this::a, this::c);
      BlockRewriter var3 = new BlockRewriter(this.c, Type.POSITION);
      var2.a((ClientboundPacketType)awj.COOLDOWN);
      var2.b((ClientboundPacketType)awj.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
      var2.a((ClientboundPacketType)awj.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
      var2.c(awj.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
      ((Protocol1_13_2To1_14)this.c).a(awj.TRADE_LIST, q1.PLUGIN_MESSAGE, new aoM(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.OPEN_BOOK, q1.PLUGIN_MESSAGE, new aoZ(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.ENTITY_EQUIPMENT, new aoH(this, var2));
      amt var4 = new amt(this.c, this::a);
      a();
      ((Protocol1_13_2To1_14)this.c).a(awj.DECLARE_RECIPES, new aof(this, var4));
      var2.a((ServerboundPacketType)uN.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
      var2.b((ServerboundPacketType)uN.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
      ((Protocol1_13_2To1_14)this.c).a(awj.BLOCK_BREAK_ANIMATION, new aoi(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.BLOCK_ENTITY_DATA, new aoE(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.BLOCK_ACTION, new aoc(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.BLOCK_CHANGE, new aoC(this));
      var3.registerMultiBlockChange(awj.MULTI_BLOCK_CHANGE);
      ((Protocol1_13_2To1_14)this.c).a(awj.EXPLOSION, new aoo(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.CHUNK_DATA, new aon(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.UNLOAD_CHUNK, new BlockItemPackets1_14$14(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.EFFECT, new ao9(this));
      var2.a(awj.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.FLOAT);
      ((Protocol1_13_2To1_14)this.c).a(awj.MAP_DATA, new ao7(this));
      ((Protocol1_13_2To1_14)this.c).a(awj.SPAWN_POSITION, new aoK(this));
      if(acE.b() == null) {
         b(new acE[1]);
      }

   }

   protected void registerRewrites() {
      this.j = new EnchantmentRewriter(this.nbtTagName, false);
      this.j.registerEnchantment("minecraft:multishot", "§7Multishot");
      this.j.registerEnchantment("minecraft:quick_charge", "§7Quick Charge");
      this.j.registerEnchantment("minecraft:piercing", "§7Piercing");
   }

   public Item a(Item var1) {
      acE[] var2 = a();
      if(var1 == null) {
         return null;
      } else {
         super.a(var1);
         CompoundTag var3 = var1.getTag();
         if(var3 != null) {
            if(var3.get("display") instanceof CompoundTag) {
               CompoundTag var4 = (CompoundTag)var3.get("display");
               if(((CompoundTag)var3.get("display")).get("Lore") instanceof ListTag) {
                  ListTag var5 = (ListTag)var4.get("Lore");
                  ListTag var6 = (ListTag)var4.remove(this.nbtTagName + "|Lore");
                  if(var6 != null) {
                     var4.put(ConverterRegistry.convertToTag("Lore", ConverterRegistry.convertToValue(var6)));
                  }

                  for(Tag var8 : var5) {
                     if(var8 instanceof StringTag) {
                        String var9 = ((StringTag)var8).getValue();
                        if(var9 != null && !var9.isEmpty()) {
                           ((StringTag)var8).setValue(ChatRewriter.jsonTextToLegacy(var9));
                        }
                        break;
                     }
                  }
               }
            }

            this.j.handleToClient(var1);
         }

         return var1;
      }
   }

   public Item c(Item var1) {
      acE[] var2 = a();
      if(var1 == null) {
         return null;
      } else {
         super.c(var1);
         CompoundTag var3 = var1.getTag();
         if(var3 != null) {
            if(var3.get("display") instanceof CompoundTag) {
               CompoundTag var4 = (CompoundTag)var3.get("display");
               if(var4.get("Lore") instanceof ListTag) {
                  ListTag var5 = (ListTag)var4.get("Lore");
                  var4.put(ConverterRegistry.convertToTag(this.nbtTagName + "|Lore", ConverterRegistry.convertToValue(var5)));
                  Iterator var6 = var5.iterator();
                  if(var6.hasNext()) {
                     Tag var7 = (Tag)var6.next();
                     if(var7 instanceof StringTag) {
                        ((StringTag)var7).setValue(ChatRewriter.legacyTextToJson(((StringTag)var7).getValue()).toString());
                     }
                  }
               }
            }

            this.j.handleToServer(var1);
         }

         return var1;
      }
   }

   static ayd d(aq6 var0) {
      return var0.c;
   }

   static ayd e(aq6 var0) {
      return var0.c;
   }

   static ayd c(aq6 var0) {
      return var0.c;
   }

   static ayd a(aq6 var0) {
      return var0.c;
   }

   static ayd b(aq6 var0) {
      return var0.c;
   }

   public static void b(acE[] var0) {
      i = var0;
   }

   public static acE[] a() {
      return i;
   }

   static {
      b(new acE[5]);
   }
}
