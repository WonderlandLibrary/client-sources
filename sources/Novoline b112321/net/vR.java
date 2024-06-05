package net;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.BitSet;
import net.aRF;
import net.aiV;
import net.ays;
import net.cT;
import net.rX;
import net.w9;

public class vR extends PartialType {
   public vR(cT var1) {
      super(var1, "Chunk", Chunk.class);
   }

   public Chunk a(ByteBuf var1, cT var2) throws Exception {
      int var3 = ays.d();
      if(var2.d().c().b().b(aRF.class) && Via.c().isReplacePistons()) {
         boolean var20 = true;
      } else {
         boolean var10000 = false;
      }

      int var5 = Via.c().getPistonReplacementId();
      int var6 = var1.readInt();
      int var7 = var1.readInt();
      boolean var8 = var1.readBoolean();
      int var9 = Type.VAR_INT.readPrimitive(var1);
      Type.VAR_INT.readPrimitive(var1);
      BitSet var10 = new BitSet(16);
      aiV[] var11 = new aiV[16];
      int var12 = 0;
      if(var12 < 16) {
         if((var9 & 1 << var12) != 0) {
            var10.set(var12);
         }

         ++var12;
      }

      var12 = 0;
      if(var12 < 16) {
         if(var10.get(var12)) {
            aiV var13 = (aiV)rX.b.read(var1);
            var11[var12] = var13;
            var13.b(var1);
            if(var2.a() == Environment.NORMAL) {
               var13.c(var1);
            }

            var13.c(36, var5);
         }

         ++var12;
      }

      int[] var17 = var8?new int[256]:null;
      if(var8) {
         int var18 = 0;
         if(var18 < 256) {
            var17[var18] = var1.readByte() & 255;
            ++var18;
         }
      }

      return new BaseChunk(var6, var7, var8, false, var9, var11, var17, new ArrayList());
   }

   public void a(ByteBuf param1, cT param2, Chunk param3) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public Class getBaseClass() {
      return w9.class;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
