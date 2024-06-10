/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.BiMap;
/*  4:   */ import io.netty.buffer.ByteBuf;
/*  5:   */ import io.netty.channel.Channel;
/*  6:   */ import io.netty.channel.ChannelHandlerContext;
/*  7:   */ import io.netty.handler.codec.ByteToMessageDecoder;
/*  8:   */ import io.netty.util.Attribute;
/*  9:   */ import java.io.IOException;
/* 10:   */ import java.util.List;
/* 11:   */ import net.minecraft.network.NetworkManager;
/* 12:   */ import net.minecraft.network.Packet;
/* 13:   */ import net.minecraft.network.PacketBuffer;
/* 14:   */ import org.apache.logging.log4j.LogManager;
/* 15:   */ import org.apache.logging.log4j.Logger;
/* 16:   */ import org.apache.logging.log4j.Marker;
/* 17:   */ import org.apache.logging.log4j.MarkerManager;
/* 18:   */ 
/* 19:   */ public class MessageDeserializer
/* 20:   */   extends ByteToMessageDecoder
/* 21:   */ {
/* 22:19 */   private static final Logger logger = ;
/* 23:20 */   private static final Marker field_150799_b = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.logMarkerPackets);
/* 24:   */   private static final String __OBFID = "CL_00001252";
/* 25:   */   
/* 26:   */   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List p_decode_3_)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:25 */     if (p_decode_2_.readableBytes() != 0)
/* 30:   */     {
/* 31:27 */       PacketBuffer var4 = new PacketBuffer(p_decode_2_);
/* 32:28 */       int var5 = var4.readVarIntFromBuffer();
/* 33:29 */       Packet var6 = Packet.generatePacket((BiMap)p_decode_1_.channel().attr(NetworkManager.attrKeyReceivable).get(), var5);
/* 34:31 */       if (var6 == null) {
/* 35:33 */         throw new IOException("Bad packet id " + var5);
/* 36:   */       }
/* 37:37 */       var6.readPacketData(var4);
/* 38:39 */       if (var4.readableBytes() > 0) {
/* 39:41 */         throw new IOException("Packet was larger than I expected, found " + var4.readableBytes() + " bytes extra whilst reading packet " + var5);
/* 40:   */       }
/* 41:45 */       p_decode_3_.add(var6);
/* 42:47 */       if (logger.isDebugEnabled()) {
/* 43:49 */         logger.debug(field_150799_b, " IN: [{}:{}] {}[{}]", new Object[] { p_decode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), Integer.valueOf(var5), var6.getClass().getName(), var6.serialize() });
/* 44:   */       }
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.MessageDeserializer
 * JD-Core Version:    0.7.0.1
 */