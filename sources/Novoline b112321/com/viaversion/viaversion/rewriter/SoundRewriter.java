package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.SoundRewriter$1;
import net.EN;
import net.aTD;
import net.ayx;
import net.y7;

public class SoundRewriter {
   protected final ayx a;
   protected final IdRewriteFunction idRewriter;

   public SoundRewriter(ayx var1) {
      this.a = var1;
      this.idRewriter = SoundRewriter::lambda$new$0;
   }

   public SoundRewriter(ayx var1, IdRewriteFunction var2) {
      this.a = var1;
      this.idRewriter = var2;
   }

   public void a(y7 var1) {
      this.a.a((y7)var1, (PacketRemapper)(new SoundRewriter$1(this)));
   }

   public EN a() {
      return this::lambda$getSoundHandler$1;
   }

   private void lambda$getSoundHandler$1(PacketWrapperImpl var1) throws Exception {
      int var3 = ((Integer)var1.b(Type.VAR_INT, 0)).intValue();
      aTD.e();
      int var4 = this.idRewriter.rewrite(var3);
      if(var4 == -1) {
         var1.i();
      }

      if(var3 != var4) {
         var1.a(Type.VAR_INT, 0, Integer.valueOf(var4));
      }

   }

   private static int lambda$new$0(ayx var0, int var1) {
      return var0.d().b().a(var1);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
