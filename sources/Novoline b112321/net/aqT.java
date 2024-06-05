package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import java.util.Optional;
import java.util.function.IntFunction;
import net.Dl;
import net.Wx;
import net.aQU;
import net.ac1;
import net.ac4;
import net.ac6;
import net.acE;
import net.acG;
import net.acI;
import net.acN;
import net.acU;
import net.acX;
import net.ace;
import net.ach;
import net.acq;
import net.agN;
import net.aqh;
import net.aqp;
import net.cQ;
import net.co;
import net.yb;
import net.md_5.bungee.api.ChatColor;
import viaversion.viabackwards.api.data.MappedLegacyBlockItem;
import viaversion.viabackwards.api.data.MappedLegacyBlockItem$BlockEntityHandler;
import viaversion.viabackwards.api.entities.meta.MetaHandler;
import viaversion.viabackwards.api.entities.storage.EntityTracker$ProtocolEntityTracker;
import viaversion.viabackwards.api.entities.storage.EntityTracker$StoredEntity;
import viaversion.viabackwards.api.exceptions.RemovedValueException;
import viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
import viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
import viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;

public class aqT extends aqh {
   private LegacyEnchantmentRewriter j;

   public aqT(Protocol1_10To1_11 var1) {
      super(var1, "1.11");
   }

   protected void registerPackets() {
      aqp.d();
      aQU var2 = new aQU(this.c, this::a, this::c);
      ((Protocol1_10To1_11)this.c).a(agN.SET_SLOT, new acN(this, var2));
      ((Protocol1_10To1_11)this.c).a(agN.WINDOW_ITEMS, new ac4(this));
      var2.f(agN.ENTITY_EQUIPMENT, Type.ITEM);
      ((Protocol1_10To1_11)this.c).a(agN.PLUGIN_MESSAGE, new acq(this));
      ((Protocol1_10To1_11)this.c).a(Wx.CLICK_WINDOW, new acI(this, var2));
      var2.b((ServerboundPacketType)Wx.CREATIVE_INVENTORY_ACTION, Type.ITEM);
      ((Protocol1_10To1_11)this.c).a(agN.CHUNK_DATA, new ac6(this));
      ((Protocol1_10To1_11)this.c).a(agN.BLOCK_CHANGE, new ace(this));
      ((Protocol1_10To1_11)this.c).a(agN.MULTI_BLOCK_CHANGE, new acX(this));
      ((Protocol1_10To1_11)this.c).a(agN.BLOCK_ENTITY_DATA, new acG(this));
      ((Protocol1_10To1_11)this.c).a(agN.OPEN_WINDOW, new ach(this));
      ((Protocol1_10To1_11)this.c).a(agN.CLOSE_WINDOW, new acU(this));
      ((Protocol1_10To1_11)this.c).a(Wx.CLOSE_WINDOW, new ac1(this));
      ((Protocol1_10To1_11)this.c).b().registerMetaHandler().handle(this::lambda$registerPackets$0);
      if(acE.b() == null) {
         aqp.c(new acE[3]);
      }

   }

   protected void registerRewrites() {
      MappedLegacyBlockItem var1 = (MappedLegacyBlockItem)this.g.computeIfAbsent(52, aqT::lambda$registerRewrites$1);
      var1.setBlockEntityHandler(aqT::lambda$registerRewrites$2);
      this.j = new LegacyEnchantmentRewriter(this.nbtTagName);
      this.j.registerEnchantment(71, "§cCurse of Vanishing");
      this.j.registerEnchantment(10, "§cCurse of Binding");
      this.j.setHideLevelForEnchants(new int[]{71, 10});
   }

   public Item a(Item var1) {
      acE[] var2 = aqp.d();
      if(var1 == null) {
         return null;
      } else {
         super.a(var1);
         CompoundTag var3 = var1.getTag();
         if(var3 == null) {
            return var1;
         } else {
            EntityIdRewriter.toClientItem(var1, true);
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
      acE[] var2 = aqp.d();
      if(var1 == null) {
         return null;
      } else {
         super.c(var1);
         CompoundTag var3 = var1.getTag();
         if(var3 == null) {
            return var1;
         } else {
            EntityIdRewriter.toServerItem(var1, true);
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

   private boolean a(UserConnection var1) {
      aqp.d();
      co var3 = (co)var1.b(co.class);
      if(var3.c() != null && var3.c().equals("EntityHorse")) {
         EntityTracker$ProtocolEntityTracker var4 = ((cQ)var1.b(cQ.class)).a(this.c());
         EntityTracker$StoredEntity var5 = var4.getEntity(var3.b());
         return var5 != null && var5.getType().is((EntityType)Dl.LIAMA);
      } else {
         return false;
      }
   }

   private Optional b(UserConnection var1) {
      aqp.d();
      co var3 = (co)var1.b(co.class);
      if(var3.c() != null && var3.c().equals("EntityHorse")) {
         EntityTracker$ProtocolEntityTracker var4 = ((cQ)var1.b(cQ.class)).a(this.c());
         EntityTracker$StoredEntity var5 = var4.getEntity(var3.b());
         if(var5 != null) {
            return Optional.of(var5.get(ChestedHorseStorage.class));
         }
      }

      return Optional.empty();
   }

   private int b(ChestedHorseStorage var1, int var2) {
      acE[] var3 = aqp.d();
      int var4 = !var1.isChested()?38:53;
      int var5 = var1.isChested()?var1.getLiamaStrength():0;
      int var6 = 2 + 3 * var5;
      int var7 = 15 - 3 * var5;
      return var2 >= var6 && var4 > var2 + var7?var7 + var2:(var2 == 1?0:var2);
   }

   private int a(ChestedHorseStorage var1, int var2) {
      acE[] var3 = aqp.d();
      int var4 = var1.isChested()?var1.getLiamaStrength():0;
      int var5 = 2 + 3 * var4;
      int var6 = 2 + 3 * (var1.isChested()?5:0);
      int var7 = var6 - var5;
      return var2 != 1 && (var2 < var5 || var2 >= var6)?(var2 >= var6?var2 - var7:(var2 == 0?1:var2)):0;
   }

   private Item a(ChestedHorseStorage var1, int var2, Item var3) {
      acE[] var4 = aqp.d();
      int var5 = var1.isChested()?var1.getLiamaStrength():0;
      int var6 = 2 + 3 * var5;
      int var7 = 2 + 3 * (var1.isChested()?5:0);
      return var2 >= var6 && var2 < var7?new Item(166, (byte)1, (short)0, this.a((String)(ChatColor.RED + "SLOT DISABLED"))):(var2 == 1?null:var3);
   }

   private static CompoundTag lambda$registerRewrites$2(int var0, CompoundTag var1) {
      EntityIdRewriter.toClientSpawner(var1, true);
      return var1;
   }

   private static MappedLegacyBlockItem lambda$registerRewrites$1(int var0) {
      return new MappedLegacyBlockItem(52, (short)-1, (String)null, false);
   }

   private Metadata lambda$registerPackets$0(yb var1) throws RemovedValueException {
      aqp.d();
      Metadata var3 = var1.i();
      if(var3.getMetaType().getType().equals(Type.ITEM)) {
         var3.setValue(this.a((Item)var3.getValue()));
      }

      return var3;
   }

   static boolean b(aqT var0, UserConnection var1) {
      return var0.a(var1);
   }

   static Optional a(aqT var0, UserConnection var1) {
      return var0.b(var1);
   }

   static int a(aqT var0, ChestedHorseStorage var1, int var2) {
      return var0.b(var1, var2);
   }

   static Item a(aqT var0, ChestedHorseStorage var1, int var2, Item var3) {
      return var0.a(var1, var2, var3);
   }

   static int b(aqT var0, ChestedHorseStorage var1, int var2) {
      return var0.a(var1, var2);
   }

   static void a(aqT var0, Chunk var1) {
      var0.a((Chunk)var1);
   }

   private static RemovedValueException a(RemovedValueException var0) {
      return var0;
   }
}
