/*  1:   */ package net.minecraft.network;
/*  2:   */ 
/*  3:   */ import io.netty.buffer.ByteBuf;
/*  4:   */ import io.netty.channel.ChannelHandlerContext;
/*  5:   */ import io.netty.handler.codec.MessageToByteEncoder;
/*  6:   */ import javax.crypto.Cipher;
/*  7:   */ import javax.crypto.ShortBufferException;
/*  8:   */ 
/*  9:   */ public class NettyEncryptingEncoder
/* 10:   */   extends MessageToByteEncoder
/* 11:   */ {
/* 12:   */   private final NettyEncryptionTranslator field_150750_a;
/* 13:   */   private static final String __OBFID = "CL_00001239";
/* 14:   */   
/* 15:   */   public NettyEncryptingEncoder(Cipher p_i45142_1_)
/* 16:   */   {
/* 17:16 */     this.field_150750_a = new NettyEncryptionTranslator(p_i45142_1_);
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected void encode(ChannelHandlerContext p_150749_1_, ByteBuf p_150749_2_, ByteBuf p_150749_3_)
/* 21:   */     throws ShortBufferException
/* 22:   */   {
/* 23:21 */     this.field_150750_a.func_150504_a(p_150749_2_, p_150749_3_);
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void encode(ChannelHandlerContext p_encode_1_, Object p_encode_2_, ByteBuf p_encode_3_)
/* 27:   */     throws ShortBufferException
/* 28:   */   {
/* 29:26 */     encode(p_encode_1_, (ByteBuf)p_encode_2_, p_encode_3_);
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.NettyEncryptingEncoder
 * JD-Core Version:    0.7.0.1
 */