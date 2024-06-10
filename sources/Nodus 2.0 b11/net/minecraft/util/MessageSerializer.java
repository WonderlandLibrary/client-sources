/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.BiMap;
/*  4:   */ import io.netty.buffer.ByteBuf;
/*  5:   */ import io.netty.channel.Channel;
/*  6:   */ import io.netty.channel.ChannelHandlerContext;
/*  7:   */ import io.netty.handler.codec.MessageToByteEncoder;
/*  8:   */ import io.netty.util.Attribute;
/*  9:   */ import java.io.IOException;
/* 10:   */ import net.minecraft.network.NetworkManager;
/* 11:   */ import net.minecraft.network.Packet;
/* 12:   */ import net.minecraft.network.PacketBuffer;
/* 13:   */ import org.apache.logging.log4j.LogManager;
/* 14:   */ import org.apache.logging.log4j.Logger;
/* 15:   */ import org.apache.logging.log4j.Marker;
/* 16:   */ import org.apache.logging.log4j.MarkerManager;
/* 17:   */ 
/* 18:   */ public class MessageSerializer
/* 19:   */   extends MessageToByteEncoder
/* 20:   */ {
/* 21:18 */   private static final Logger logger = ;
/* 22:19 */   private static final Marker field_150797_b = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
/* 23:   */   private static final String __OBFID = "CL_00001253";
/* 24:   */   
/* 25:   */   protected void encode(ChannelHandlerContext p_150796_1_, Packet p_150796_2_, ByteBuf p_150796_3_)
/* 26:   */     throws IOException
/* 27:   */   {
/* 28:24 */     Integer var4 = (Integer)((BiMap)p_150796_1_.channel().attr(NetworkManager.attrKeySendable).get()).inverse().get(p_150796_2_.getClass());
/* 29:26 */     if (logger.isDebugEnabled()) {
/* 30:28 */       logger.debug(field_150797_b, "OUT: [{}:{}] {}[{}]", new Object[] { p_150796_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), var4, p_150796_2_.getClass().getName(), p_150796_2_.serialize() });
/* 31:   */     }
/* 32:31 */     if (var4 == null) {
/* 33:33 */       throw new IOException("Can't serialize unregistered packet");
/* 34:   */     }
/* 35:37 */     PacketBuffer var5 = new PacketBuffer(p_150796_3_);
/* 36:38 */     var5.writeVarIntToBuffer(var4.intValue());
/* 37:39 */     p_150796_2_.writePacketData(var5);
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected void encode(ChannelHandlerContext p_encode_1_, Object p_encode_2_, ByteBuf p_encode_3_)
/* 41:   */     throws IOException
/* 42:   */   {
/* 43:45 */     encode(p_encode_1_, (Packet)p_encode_2_, p_encode_3_);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.MessageSerializer
 * JD-Core Version:    0.7.0.1
 */