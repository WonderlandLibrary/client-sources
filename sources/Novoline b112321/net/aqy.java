package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import net.Wx;
import net.aCW;
import net.aQU;
import net.agN;
import net.aqL;
import net.aqh;
import net.ayj;
import net.yb;
import viaversion.viabackwards.api.entities.meta.MetaHandler;
import viaversion.viabackwards.api.exceptions.RemovedValueException;
import viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.type.Type;

public class aqy extends aqh {
   private LegacyEnchantmentRewriter j;

   public aqy(ayj var1) {
      super(var1, "1.11.1");
   }

   protected void registerPackets() {
      aQU var1 = new aQU(this.c, this::a, this::c);
      var1.a((ClientboundPacketType)agN.SET_SLOT, Type.ITEM);
      var1.b((ClientboundPacketType)agN.WINDOW_ITEMS, Type.ITEM_ARRAY);
      var1.f(agN.ENTITY_EQUIPMENT, Type.ITEM);
      ((ayj)this.c).a(agN.PLUGIN_MESSAGE, new aCW(this));
      var1.a((ServerboundPacketType)Wx.CLICK_WINDOW, Type.ITEM);
      var1.b((ServerboundPacketType)Wx.CREATIVE_INVENTORY_ACTION, Type.ITEM);
      ((ayj)this.c).a().registerMetaHandler().handle(this::lambda$registerPackets$0);
   }

   protected void registerRewrites() {
      this.j = new LegacyEnchantmentRewriter(this.nbtTagName);
      this.j.registerEnchantment(22, "§7Sweeping Edge");
   }

   public Item a(Item var1) {
      String var2 = aqL.a();
      if(var1 == null) {
         return null;
      } else {
         super.a(var1);
         CompoundTag var3 = var1.getTag();
         if(var3 == null) {
            return var1;
         } else {
            if(var3.get("ench") instanceof ListTag) {
               this.j.rewriteEnchantmentsToClient(var3, false);
            }

            if(var3.get("StoredEnchantments") instanceof ListTag) {
               this.j.rewriteEnchantmentsToClient(var3, true);
            }

            return var1;
         }
      }
   }

   public Item c(Item var1) {
      String var2 = aqL.a();
      if(var1 == null) {
         return null;
      } else {
         super.c(var1);
         CompoundTag var3 = var1.getTag();
         if(var3 == null) {
            return var1;
         } else {
            if(var3.contains(this.nbtTagName + "|ench")) {
               this.j.rewriteEnchantmentsToServer(var3, false);
            }

            if(var3.contains(this.nbtTagName + "|StoredEnchantments")) {
               this.j.rewriteEnchantmentsToServer(var3, true);
            }

            return var1;
         }
      }
   }

   private Metadata lambda$registerPackets$0(yb var1) throws RemovedValueException {
      aqL.a();
      Metadata var3 = var1.i();
      if(var3.getMetaType().getType().equals(Type.ITEM)) {
         var3.setValue(this.a((Item)var3.getValue()));
      }

      return var3;
   }

   private static RemovedValueException a(RemovedValueException var0) {
      return var0;
   }
}
