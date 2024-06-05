package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntArrayTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import java.util.Iterator;
import java.util.UUID;
import net.C4;
import net.aQU;
import net.adT;
import net.ahW;
import net.amx;
import net.anE;
import net.anH;
import net.anM;
import net.anW;
import net.anZ;
import net.anf;
import net.ani;
import net.aqq;
import net.ayd;
import viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16$2;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.api.rewriters.BlockRewriter;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.UUIDIntArrayType;

public class aqX extends aqq {
   private EnchantmentRewriter i;
   private static String[] j;

   public aqX(Protocol1_15_2To1_16 var1, TranslatableRewriter var2) {
      super(var1, var2);
   }

   protected void registerPackets() {
      aQU var2 = new aQU(this.c, this::a, this::c);
      a();
      BlockRewriter var3 = new BlockRewriter(this.c, Type.POSITION1_14);
      amx var4 = new amx(this.c, this::a);
      ((Protocol1_15_2To1_16)this.c).a(C4.DECLARE_RECIPES, new anW(this, var4));
      var2.a((ClientboundPacketType)C4.COOLDOWN);
      var2.b((ClientboundPacketType)C4.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
      var2.a((ClientboundPacketType)C4.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
      var2.e(C4.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
      var2.c(C4.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
      var3.registerAcknowledgePlayerDigging(C4.ACKNOWLEDGE_PLAYER_DIGGING);
      var3.registerBlockAction(C4.BLOCK_ACTION);
      var3.registerBlockChange(C4.BLOCK_CHANGE);
      var3.registerMultiBlockChange(C4.MULTI_BLOCK_CHANGE);
      ((Protocol1_15_2To1_16)this.c).a(C4.ENTITY_EQUIPMENT, new BlockItemPackets1_16$2(this));
      ((Protocol1_15_2To1_16)this.c).a(C4.UPDATE_LIGHT, new anM(this));
      ((Protocol1_15_2To1_16)this.c).a(C4.CHUNK_DATA, new anZ(this));
      var3.registerEffect(C4.EFFECT, 1010, 2001);
      var2.a(C4.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
      ((Protocol1_15_2To1_16)this.c).a(C4.WINDOW_PROPERTY, new anH(this));
      ((Protocol1_15_2To1_16)this.c).a(C4.MAP_DATA, new anf(this));
      ((Protocol1_15_2To1_16)this.c).a(C4.BLOCK_ENTITY_DATA, new ani(this));
      var2.a((ServerboundPacketType)ahW.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
      var2.b((ServerboundPacketType)ahW.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
      ((Protocol1_15_2To1_16)this.c).a(ahW.EDIT_BOOK, new anE(this));
   }

   private void a(CompoundTag var1) {
      a();
      StringTag var3 = (StringTag)var1.get("id");
      if(var3 != null) {
         String var4 = var3.getValue();
         if(var4.equals("minecraft:conduit")) {
            Tag var5 = var1.remove("Target");
            if(!(var5 instanceof IntArrayTag)) {
               return;
            }

            UUID var6 = UUIDIntArrayType.uuidFromIntArray((int[])((int[])var5.getValue()));
            var1.put(new StringTag("target_uuid", var6.toString()));
         }

         if(var4.equals("minecraft:skull")) {
            Tag var11 = var1.remove("SkullOwner");
            if(!(var11 instanceof CompoundTag)) {
               return;
            }

            CompoundTag var12 = (CompoundTag)var11;
            Tag var7 = var12.remove("Id");
            if(var7 instanceof IntArrayTag) {
               UUID var8 = UUIDIntArrayType.uuidFromIntArray((int[])((int[])var7.getValue()));
               var12.put(new StringTag("Id", var8.toString()));
            }

            CompoundTag var13 = new CompoundTag("Owner");
            Iterator var9 = var12.iterator();
            if(var9.hasNext()) {
               Tag var10 = (Tag)var9.next();
               var13.put(var10);
            }

            var1.put(var13);
         }

      }
   }

   protected void registerRewrites() {
      this.i = new EnchantmentRewriter(this.nbtTagName);
      this.i.registerEnchantment("minecraft:soul_speed", "§7Soul Speed");
   }

   public Item a(Item var1) {
      String[] var2 = a();
      if(var1 == null) {
         return null;
      } else {
         super.a(var1);
         CompoundTag var3 = var1.getTag();
         if(var1.getIdentifier() == 771 && var3 != null) {
            Tag var4 = var3.get("SkullOwner");
            if(var4 instanceof CompoundTag) {
               CompoundTag var5 = (CompoundTag)var4;
               Tag var6 = var5.get("Id");
               if(var6 instanceof IntArrayTag) {
                  UUID var7 = UUIDIntArrayType.uuidFromIntArray((int[])((int[])var6.getValue()));
                  var5.put(new StringTag("Id", var7.toString()));
               }
            }
         }

         adT.b(var1);
         this.i.handleToClient(var1);
         return var1;
      }
   }

   public Item c(Item var1) {
      String[] var2 = a();
      if(var1 == null) {
         return null;
      } else {
         int var3 = var1.getIdentifier();
         super.c(var1);
         CompoundTag var4 = var1.getTag();
         if(var3 == 771 && var4 != null) {
            Tag var5 = var4.get("SkullOwner");
            if(var5 instanceof CompoundTag) {
               CompoundTag var6 = (CompoundTag)var5;
               Tag var7 = var6.get("Id");
               if(var7 instanceof StringTag) {
                  UUID var8 = UUID.fromString((String)var7.getValue());
                  var6.put(new IntArrayTag("Id", UUIDIntArrayType.uuidToIntArray(var8)));
               }
            }
         }

         adT.d(var1);
         this.i.handleToServer(var1);
         return var1;
      }
   }

   static ayd a(aqX var0) {
      return var0.c;
   }

   static void a(aqX var0, CompoundTag var1) {
      var0.a(var1);
   }

   public static void b(String[] var0) {
      j = var0;
   }

   public static String[] a() {
      return j;
   }

   static {
      b((String[])null);
   }
}
