/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import io.netty.buffer.ByteBuf;
/*  4:   */ import io.netty.channel.ChannelHandlerContext;
/*  5:   */ import io.netty.handler.codec.MessageToByteEncoder;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ 
/*  8:   */ public class MessageSerializer2
/*  9:   */   extends MessageToByteEncoder
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00001256";
/* 12:   */   
/* 13:   */   protected void encode(ChannelHandlerContext p_150667_1_, ByteBuf p_150667_2_, ByteBuf p_150667_3_)
/* 14:   */   {
/* 15:14 */     int var4 = p_150667_2_.readableBytes();
/* 16:15 */     int var5 = PacketBuffer.getVarIntSize(var4);
/* 17:17 */     if (var5 > 3) {
/* 18:19 */       throw new IllegalArgumentException("unable to fit " + var4 + " into " + 3);
/* 19:   */     }
/* 20:23 */     PacketBuffer var6 = new PacketBuffer(p_150667_3_);
/* 21:24 */     var6.ensureWritable(var5 + var4);
/* 22:25 */     var6.writeVarIntToBuffer(var4);
/* 23:26 */     var6.writeBytes(p_150667_2_, p_150667_2_.readerIndex(), var4);
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void encode(ChannelHandlerContext p_encode_1_, Object p_encode_2_, ByteBuf p_encode_3_)
/* 27:   */   {
/* 28:32 */     encode(p_encode_1_, (ByteBuf)p_encode_2_, p_encode_3_);
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.MessageSerializer2
 * JD-Core Version:    0.7.0.1
 */