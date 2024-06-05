package net;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.utils.PacketUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import net.EN;
import net.Zv;
import net.aRE;

final class a9y extends PacketRemapper {
   public void registerMap() {
      this.a(Type.STRING);
      this.a(Type.k);
      this.map(Type.k, Type.VAR_INT);
      this.a(Type.c);
      this.a(Type.M);
      this.a(a9y::lambda$registerMap$0);
      this.a(a9y::lambda$registerMap$1);
   }

   private static void lambda$registerMap$1(PacketWrapperImpl var0) throws Exception {
      short var1 = ((Short)var0.b(Type.M, 0)).shortValue();
      PacketWrapperImpl var2 = new PacketWrapperImpl(28, (ByteBuf)null, var0.e());
      var2.a(Type.VAR_INT, Integer.valueOf(((EntityTracker)var0.e().b(EntityTracker.class)).getPlayerId()));
      ArrayList var3 = new ArrayList();
      var3.add(new Metadata(10, MetaType1_8.Byte, Byte.valueOf((byte)var1)));
      var2.a(Types1_8.METADATA_LIST, var3);
      PacketUtil.b(var2, aRE.class);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      var0.a(Type.VAR_INT, Integer.valueOf(1));
   }
}
