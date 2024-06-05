package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.aRY;
import net.aqQ;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.storage.CommandBlockStorage;

public class amb implements aqQ {
   private static boolean b;

   public void a(UserConnection var1, Position var2, CompoundTag var3) throws Exception {
      c();
      this.d(var1);
      if(this.d()) {
         this.b(var1).addOrUpdateBlock(var2, var3);
      }

   }

   public Optional a(UserConnection var1, Position var2) throws Exception {
      c();
      this.d(var1);
      return this.d()?this.b(var1).getCommandBlock(var2):Optional.empty();
   }

   public void a(UserConnection var1, int var2, int var3) throws Exception {
      c();
      this.d(var1);
      if(this.d()) {
         this.b(var1).unloadChunk(var2, var3);
      }

   }

   private CommandBlockStorage b(UserConnection var1) {
      return (CommandBlockStorage)var1.b(CommandBlockStorage.class);
   }

   public void a(UserConnection var1) throws Exception {
      if(this.d()) {
         PacketWrapper var2 = new PacketWrapper(27, (ByteBuf)null, var1);
         var2.write(Type.INT, Integer.valueOf(((cq)var1.b(cq.class)).e()));
         var2.write(Type.BYTE, Byte.valueOf((byte)26));
         var2.send(aRY.class);
         ((CommandBlockStorage)var1.b(CommandBlockStorage.class)).setPermissions(true);
      }
   }

   private void d(UserConnection var1) throws Exception {
      boolean var2 = c();
      if(this.d()) {
         CommandBlockStorage var3 = this.b(var1);
         if(!var3.isPermissions()) {
            this.a(var1);
         }

      }
   }

   public boolean d() {
      return true;
   }

   public void c(UserConnection var1) {
      boolean var2 = c();
      if(this.d()) {
         this.b(var1).unloadChunks();
      }

   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
