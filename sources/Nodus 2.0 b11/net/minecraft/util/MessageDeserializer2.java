/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import io.netty.buffer.ByteBuf;
/*  4:   */ import io.netty.buffer.Unpooled;
/*  5:   */ import io.netty.channel.ChannelHandlerContext;
/*  6:   */ import io.netty.handler.codec.ByteToMessageDecoder;
/*  7:   */ import io.netty.handler.codec.CorruptedFrameException;
/*  8:   */ import java.util.List;
/*  9:   */ import net.minecraft.network.PacketBuffer;
/* 10:   */ 
/* 11:   */ public class MessageDeserializer2
/* 12:   */   extends ByteToMessageDecoder
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00001255";
/* 15:   */   
/* 16:   */   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List p_decode_3_)
/* 17:   */   {
/* 18:17 */     p_decode_2_.markReaderIndex();
/* 19:18 */     byte[] var4 = new byte[3];
/* 20:20 */     for (int var5 = 0; var5 < var4.length; var5++)
/* 21:   */     {
/* 22:22 */       if (!p_decode_2_.isReadable())
/* 23:   */       {
/* 24:24 */         p_decode_2_.resetReaderIndex();
/* 25:25 */         return;
/* 26:   */       }
/* 27:28 */       var4[var5] = p_decode_2_.readByte();
/* 28:30 */       if (var4[var5] >= 0)
/* 29:   */       {
/* 30:32 */         int var6 = new PacketBuffer(Unpooled.wrappedBuffer(var4)).readVarIntFromBuffer();
/* 31:34 */         if (p_decode_2_.readableBytes() < var6)
/* 32:   */         {
/* 33:36 */           p_decode_2_.resetReaderIndex();
/* 34:37 */           return;
/* 35:   */         }
/* 36:40 */         p_decode_3_.add(p_decode_2_.readBytes(var6));
/* 37:41 */         return;
/* 38:   */       }
/* 39:   */     }
/* 40:45 */     throw new CorruptedFrameException("length wider than 21-bit");
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.MessageDeserializer2
 * JD-Core Version:    0.7.0.1
 */