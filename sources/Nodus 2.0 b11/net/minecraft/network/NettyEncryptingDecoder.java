/*  1:   */ package net.minecraft.network;
/*  2:   */ 
/*  3:   */ import io.netty.buffer.ByteBuf;
/*  4:   */ import io.netty.channel.ChannelHandlerContext;
/*  5:   */ import io.netty.handler.codec.MessageToMessageDecoder;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.crypto.Cipher;
/*  8:   */ import javax.crypto.ShortBufferException;
/*  9:   */ 
/* 10:   */ public class NettyEncryptingDecoder
/* 11:   */   extends MessageToMessageDecoder
/* 12:   */ {
/* 13:   */   private final NettyEncryptionTranslator field_150509_a;
/* 14:   */   private static final String __OBFID = "CL_00001238";
/* 15:   */   
/* 16:   */   public NettyEncryptingDecoder(Cipher p_i45141_1_)
/* 17:   */   {
/* 18:17 */     this.field_150509_a = new NettyEncryptionTranslator(p_i45141_1_);
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void decode(ChannelHandlerContext p_150508_1_, ByteBuf p_150508_2_, List p_150508_3_)
/* 22:   */     throws ShortBufferException
/* 23:   */   {
/* 24:22 */     p_150508_3_.add(this.field_150509_a.func_150503_a(p_150508_1_, p_150508_2_));
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected void decode(ChannelHandlerContext p_decode_1_, Object p_decode_2_, List p_decode_3_)
/* 28:   */     throws ShortBufferException
/* 29:   */   {
/* 30:27 */     decode(p_decode_1_, (ByteBuf)p_decode_2_, p_decode_3_);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.NettyEncryptingDecoder
 * JD-Core Version:    0.7.0.1
 */