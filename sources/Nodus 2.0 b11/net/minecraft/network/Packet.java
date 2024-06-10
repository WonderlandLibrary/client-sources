/*  1:   */ package net.minecraft.network;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.BiMap;
/*  4:   */ import io.netty.buffer.ByteBuf;
/*  5:   */ import java.io.IOException;
/*  6:   */ import org.apache.logging.log4j.LogManager;
/*  7:   */ import org.apache.logging.log4j.Logger;
/*  8:   */ 
/*  9:   */ public abstract class Packet
/* 10:   */ {
/* 11:11 */   private static final Logger logger = ;
/* 12:   */   private static final String __OBFID = "CL_00001272";
/* 13:   */   
/* 14:   */   public static Packet generatePacket(BiMap p_148839_0_, int p_148839_1_)
/* 15:   */   {
/* 16:   */     try
/* 17:   */     {
/* 18:21 */       Class var2 = (Class)p_148839_0_.get(Integer.valueOf(p_148839_1_));
/* 19:22 */       return var2 == null ? null : (Packet)var2.newInstance();
/* 20:   */     }
/* 21:   */     catch (Exception var3)
/* 22:   */     {
/* 23:26 */       logger.error("Couldn't create packet " + p_148839_1_, var3);
/* 24:   */     }
/* 25:27 */     return null;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static void writeBlob(ByteBuf p_148838_0_, byte[] p_148838_1_)
/* 29:   */   {
/* 30:37 */     p_148838_0_.writeShort(p_148838_1_.length);
/* 31:38 */     p_148838_0_.writeBytes(p_148838_1_);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static byte[] readBlob(ByteBuf p_148834_0_)
/* 35:   */     throws IOException
/* 36:   */   {
/* 37:47 */     short var1 = p_148834_0_.readShort();
/* 38:49 */     if (var1 < 0) {
/* 39:51 */       throw new IOException("Key was smaller than nothing!  Weird key!");
/* 40:   */     }
/* 41:55 */     byte[] var2 = new byte[var1];
/* 42:56 */     p_148834_0_.readBytes(var2);
/* 43:57 */     return var2;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public abstract void readPacketData(PacketBuffer paramPacketBuffer)
/* 47:   */     throws IOException;
/* 48:   */   
/* 49:   */   public abstract void writePacketData(PacketBuffer paramPacketBuffer)
/* 50:   */     throws IOException;
/* 51:   */   
/* 52:   */   public abstract void processPacket(INetHandler paramINetHandler);
/* 53:   */   
/* 54:   */   public boolean hasPriority()
/* 55:   */   {
/* 56:79 */     return false;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public String toString()
/* 60:   */   {
/* 61:84 */     return getClass().getSimpleName();
/* 62:   */   }
/* 63:   */   
/* 64:   */   public String serialize()
/* 65:   */   {
/* 66:92 */     return "";
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.Packet
 * JD-Core Version:    0.7.0.1
 */