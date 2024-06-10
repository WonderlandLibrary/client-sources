/*  1:   */ package net.minecraft.network;
/*  2:   */ 
/*  3:   */ import io.netty.buffer.ByteBuf;
/*  4:   */ import io.netty.buffer.ByteBufAllocator;
/*  5:   */ import io.netty.channel.ChannelHandlerContext;
/*  6:   */ import javax.crypto.Cipher;
/*  7:   */ import javax.crypto.ShortBufferException;
/*  8:   */ 
/*  9:   */ public class NettyEncryptionTranslator
/* 10:   */ {
/* 11:   */   private final Cipher field_150507_a;
/* 12:11 */   private byte[] field_150505_b = new byte[0];
/* 13:12 */   private byte[] field_150506_c = new byte[0];
/* 14:   */   private static final String __OBFID = "CL_00001237";
/* 15:   */   
/* 16:   */   protected NettyEncryptionTranslator(Cipher p_i45140_1_)
/* 17:   */   {
/* 18:17 */     this.field_150507_a = p_i45140_1_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   private byte[] func_150502_a(ByteBuf p_150502_1_)
/* 22:   */   {
/* 23:22 */     int var2 = p_150502_1_.readableBytes();
/* 24:24 */     if (this.field_150505_b.length < var2) {
/* 25:26 */       this.field_150505_b = new byte[var2];
/* 26:   */     }
/* 27:29 */     p_150502_1_.readBytes(this.field_150505_b, 0, var2);
/* 28:30 */     return this.field_150505_b;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected ByteBuf func_150503_a(ChannelHandlerContext p_150503_1_, ByteBuf p_150503_2_)
/* 32:   */     throws ShortBufferException
/* 33:   */   {
/* 34:35 */     int var3 = p_150503_2_.readableBytes();
/* 35:36 */     byte[] var4 = func_150502_a(p_150503_2_);
/* 36:37 */     ByteBuf var5 = p_150503_1_.alloc().heapBuffer(this.field_150507_a.getOutputSize(var3));
/* 37:38 */     var5.writerIndex(this.field_150507_a.update(var4, 0, var3, var5.array(), var5.arrayOffset()));
/* 38:39 */     return var5;
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected void func_150504_a(ByteBuf p_150504_1_, ByteBuf p_150504_2_)
/* 42:   */     throws ShortBufferException
/* 43:   */   {
/* 44:44 */     int var3 = p_150504_1_.readableBytes();
/* 45:45 */     byte[] var4 = func_150502_a(p_150504_1_);
/* 46:46 */     int var5 = this.field_150507_a.getOutputSize(var3);
/* 47:48 */     if (this.field_150506_c.length < var5) {
/* 48:50 */       this.field_150506_c = new byte[var5];
/* 49:   */     }
/* 50:53 */     p_150504_2_.writeBytes(this.field_150506_c, 0, this.field_150507_a.update(var4, 0, var3, this.field_150506_c));
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.NettyEncryptionTranslator
 * JD-Core Version:    0.7.0.1
 */