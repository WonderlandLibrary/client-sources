package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.channel.ChannelHandlerContext;
import net.amb;
import net.aqQ;
import net.bgR;
import net.cY;

public abstract class MovementTransmitterProvider implements aqQ {
   public abstract Object getFlyingPacket();

   public abstract Object getGroundPacket();

   public void a(bgR var1) {
      amb.c();
      ChannelHandlerContext var3 = PipelineUtil.getContextBefore("decoder", var1.p().pipeline());
      if(((cY)var1.b(cY.class)).d()) {
         var3.fireChannelRead(this.getGroundPacket());
      }

      var3.fireChannelRead(this.getFlyingPacket());
      ((cY)var1.b(cY.class)).e();
   }
}
