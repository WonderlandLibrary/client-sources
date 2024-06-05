package net;

import net.Wx;
import net.aCg;
import net.aQU;
import net.ac5;
import net.acE;
import net.ac_;
import net.aca;
import net.acd;
import net.acj;
import net.ack;
import net.acx;
import net.aqh;
import net.aqw;
import net.ay_;
import net.lV;
import net.yb;
import viaversion.viabackwards.api.entities.meta.MetaHandler;
import viaversion.viabackwards.api.exceptions.RemovedValueException;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.minecraft.metadata.Metadata;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.type.Type;

public class aq0 extends aqh {
   public aq0(ay_ var1) {
      super(var1, "1.12");
   }

   protected void registerPackets() {
      ((ay_)this.c).a(lV.MAP_DATA, new acx(this));
      aQU var2 = new aQU(this.c, this::a, this::c);
      var2.a((ClientboundPacketType)lV.SET_SLOT, Type.ITEM);
      var2.b((ClientboundPacketType)lV.WINDOW_ITEMS, Type.ITEM_ARRAY);
      aqw.a();
      var2.f(lV.ENTITY_EQUIPMENT, Type.ITEM);
      ((ay_)this.c).a(lV.PLUGIN_MESSAGE, new acd(this));
      ((ay_)this.c).a(Wx.CLICK_WINDOW, new aca(this));
      var2.b((ServerboundPacketType)Wx.CREATIVE_INVENTORY_ACTION, Type.ITEM);
      ((ay_)this.c).a(lV.CHUNK_DATA, new ac_(this));
      ((ay_)this.c).a(lV.BLOCK_CHANGE, new acj(this));
      ((ay_)this.c).a(lV.MULTI_BLOCK_CHANGE, new ac5(this));
      ((ay_)this.c).a(lV.BLOCK_ENTITY_DATA, new ack(this));
      ((ay_)this.c).e().registerMetaHandler().handle(this::lambda$registerPackets$0);
      ((ay_)this.c).a(Wx.CLIENT_STATUS, new aCg(this));
      if(acE.b() == null) {
         aqw.b("H2zZ4");
      }

   }

   private Metadata lambda$registerPackets$0(yb var1) throws RemovedValueException {
      aqw.a();
      Metadata var3 = var1.i();
      if(var3.getMetaType().getType().equals(Type.ITEM)) {
         var3.setValue(this.a((Item)var3.getValue()));
      }

      return var3;
   }

   static void a(aq0 var0, Chunk var1) {
      var0.a(var1);
   }

   private static RemovedValueException a(RemovedValueException var0) {
      return var0;
   }
}
