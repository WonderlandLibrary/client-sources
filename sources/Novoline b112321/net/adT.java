package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntArrayTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.LongTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import java.util.Iterator;
import java.util.UUID;
import net.Mo;
import net.aQU;
import net.aVL;
import net.aVP;
import net.aVU;
import net.aVb;
import net.acE;
import net.amx;
import net.sM;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.UUIDIntArrayType;
import viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets$4;
import viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets$6;

public class adT {
   private static acE[] b;

   public static void a(Protocol1_16To1_15_2 var0) {
      aQU var2 = new aQU(var0, adT::c, adT::a);
      b();
      var0.a(Mo.OPEN_WINDOW, new aVL());
      var0.a(Mo.CLOSE_WINDOW, new aVP());
      var0.a(Mo.WINDOW_PROPERTY, new aVb());
      var2.a((ClientboundPacketType)Mo.COOLDOWN);
      var2.b((ClientboundPacketType)Mo.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
      var2.e(Mo.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
      var2.a((ClientboundPacketType)Mo.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
      var2.c(Mo.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
      var0.a(Mo.ENTITY_EQUIPMENT, new InventoryPackets$4());
      (new amx(var0, adT::c)).registerDefaultHandler(Mo.DECLARE_RECIPES);
      var2.a((ServerboundPacketType)sM.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
      var2.b((ServerboundPacketType)sM.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
      var0.a(sM.CLOSE_WINDOW, new aVU());
      var0.a(sM.EDIT_BOOK, new InventoryPackets$6());
      var2.a(Mo.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
   }

   public static void c(Item var0) {
      acE[] var1 = b();
      if(var0 != null) {
         if(var0.getIdentifier() == 771 && var0.getTag() != null) {
            CompoundTag var2 = var0.getTag();
            Tag var3 = var2.get("SkullOwner");
            if(var3 instanceof CompoundTag) {
               CompoundTag var4 = (CompoundTag)var3;
               Tag var5 = var4.get("Id");
               if(var5 instanceof StringTag) {
                  UUID var6 = UUID.fromString((String)var5.getValue());
                  var4.put(new IntArrayTag("Id", UUIDIntArrayType.uuidToIntArray(var6)));
               }
            }
         }

         d(var0);
         var0.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getNewItemId(var0.getIdentifier()));
      }
   }

   public static void a(Item var0) {
      acE[] var1 = b();
      if(var0 != null) {
         var0.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getOldItemId(var0.getIdentifier()));
         if(var0.getIdentifier() == 771 && var0.getTag() != null) {
            CompoundTag var2 = var0.getTag();
            Tag var3 = var2.get("SkullOwner");
            if(var3 instanceof CompoundTag) {
               CompoundTag var4 = (CompoundTag)var3;
               Tag var5 = var4.get("Id");
               if(var5 instanceof IntArrayTag) {
                  UUID var6 = UUIDIntArrayType.uuidFromIntArray((int[])((int[])var5.getValue()));
                  var4.put(new StringTag("Id", var6.toString()));
               }
            }
         }

         b(var0);
      }
   }

   public static void d(Item var0) {
      acE[] var1 = b();
      if(var0.getTag() != null) {
         ListTag var2 = (ListTag)var0.getTag().get("AttributeModifiers");
         if(var2 != null) {
            Iterator var3 = var2.iterator();
            if(var3.hasNext()) {
               Tag var4 = (Tag)var3.next();
               CompoundTag var5 = (CompoundTag)var4;
               a(var5, "AttributeName", false);
               a(var5, "Name", false);
               Tag var6 = var5.get("UUIDLeast");
               if(var6 != null) {
                  Tag var7 = var5.get("UUIDMost");
                  int[] var8 = UUIDIntArrayType.bitsToIntArray(((Number)var6.getValue()).longValue(), ((Number)var7.getValue()).longValue());
                  var5.put(new IntArrayTag("UUID", var8));
               }
            }

         }
      }
   }

   public static void b(Item var0) {
      acE[] var1 = b();
      if(var0.getTag() != null) {
         ListTag var2 = (ListTag)var0.getTag().get("AttributeModifiers");
         if(var2 != null) {
            Iterator var3 = var2.iterator();
            if(var3.hasNext()) {
               Tag var4 = (Tag)var3.next();
               CompoundTag var5 = (CompoundTag)var4;
               a(var5, "AttributeName", true);
               a(var5, "Name", true);
               IntArrayTag var6 = (IntArrayTag)var5.get("UUID");
               if(var6 != null) {
                  UUID var7 = UUIDIntArrayType.uuidFromIntArray(var6.getValue());
                  var5.put(new LongTag("UUIDLeast", var7.getLeastSignificantBits()));
                  var5.put(new LongTag("UUIDMost", var7.getMostSignificantBits()));
               }
            }

         }
      }
   }

   public static void a(CompoundTag var0, String var1, boolean var2) {
      b();
      StringTag var4 = (StringTag)var0.get(var1);
      if(var4 != null) {
         String var5 = var4.getValue();
         if(var2 && !var5.startsWith("minecraft:")) {
            var5 = "minecraft:" + var5;
         }

         String var6 = (String)(var2?Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().inverse():Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings()).get(var5);
      }
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      b(new acE[1]);
   }
}
