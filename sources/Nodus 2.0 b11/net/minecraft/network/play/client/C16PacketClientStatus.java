/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  8:   */ 
/*  9:   */ public class C16PacketClientStatus
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private EnumState field_149437_a;
/* 13:   */   private static final String __OBFID = "CL_00001348";
/* 14:   */   
/* 15:   */   public C16PacketClientStatus() {}
/* 16:   */   
/* 17:   */   public C16PacketClientStatus(EnumState p_i45242_1_)
/* 18:   */   {
/* 19:18 */     this.field_149437_a = p_i45242_1_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:26 */     this.field_149437_a = EnumState.field_151404_e[(p_148837_1_.readByte() % EnumState.field_151404_e.length)];
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:34 */     p_148840_1_.writeByte(this.field_149437_a.field_151403_d);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void processPacket(INetHandlerPlayServer p_149436_1_)
/* 35:   */   {
/* 36:39 */     p_149436_1_.processClientStatus(this);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public EnumState func_149435_c()
/* 40:   */   {
/* 41:44 */     return this.field_149437_a;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void processPacket(INetHandler p_148833_1_)
/* 45:   */   {
/* 46:49 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static enum EnumState
/* 50:   */   {
/* 51:54 */     PERFORM_RESPAWN("PERFORM_RESPAWN", 0, 0),  REQUEST_STATS("REQUEST_STATS", 1, 1),  OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT", 2, 2);
/* 52:   */     
/* 53:   */     private final int field_151403_d;
/* 54:   */     private static final EnumState[] field_151404_e;
/* 55:   */     private static final EnumState[] $VALUES;
/* 56:   */     private static final String __OBFID = "CL_00001349";
/* 57:   */     
/* 58:   */     private EnumState(String p_i45241_1_, int p_i45241_2_, int p_i45241_3_)
/* 59:   */     {
/* 60:65 */       this.field_151403_d = p_i45241_3_;
/* 61:   */     }
/* 62:   */     
/* 63:   */     static
/* 64:   */     {
/* 65:58 */       field_151404_e = new EnumState[values().length];
/* 66:   */       
/* 67:60 */       $VALUES = new EnumState[] { PERFORM_RESPAWN, REQUEST_STATS, OPEN_INVENTORY_ACHIEVEMENT };
/* 68:   */       
/* 69:   */ 
/* 70:   */ 
/* 71:   */ 
/* 72:   */ 
/* 73:   */ 
/* 74:   */ 
/* 75:   */ 
/* 76:69 */       EnumState[] var0 = values();
/* 77:70 */       int var1 = var0.length;
/* 78:72 */       for (int var2 = 0; var2 < var1; var2++)
/* 79:   */       {
/* 80:74 */         EnumState var3 = var0[var2];
/* 81:75 */         field_151404_e[var3.field_151403_d] = var3;
/* 82:   */       }
/* 83:   */     }
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C16PacketClientStatus
 * JD-Core Version:    0.7.0.1
 */