/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S38PacketPlayerListItem
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private String field_149126_a;
/* 13:   */   private boolean field_149124_b;
/* 14:   */   private int field_149125_c;
/* 15:   */   private static final String __OBFID = "CL_00001318";
/* 16:   */   
/* 17:   */   public S38PacketPlayerListItem() {}
/* 18:   */   
/* 19:   */   public S38PacketPlayerListItem(String p_i45209_1_, boolean p_i45209_2_, int p_i45209_3_)
/* 20:   */   {
/* 21:20 */     this.field_149126_a = p_i45209_1_;
/* 22:21 */     this.field_149124_b = p_i45209_2_;
/* 23:22 */     this.field_149125_c = p_i45209_3_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:30 */     this.field_149126_a = p_148837_1_.readStringFromBuffer(16);
/* 30:31 */     this.field_149124_b = p_148837_1_.readBoolean();
/* 31:32 */     this.field_149125_c = p_148837_1_.readShort();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 35:   */     throws IOException
/* 36:   */   {
/* 37:40 */     p_148840_1_.writeStringToBuffer(this.field_149126_a);
/* 38:41 */     p_148840_1_.writeBoolean(this.field_149124_b);
/* 39:42 */     p_148840_1_.writeShort(this.field_149125_c);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void processPacket(INetHandlerPlayClient p_149123_1_)
/* 43:   */   {
/* 44:47 */     p_149123_1_.handlePlayerListItem(this);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String func_149122_c()
/* 48:   */   {
/* 49:52 */     return this.field_149126_a;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean func_149121_d()
/* 53:   */   {
/* 54:57 */     return this.field_149124_b;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public int func_149120_e()
/* 58:   */   {
/* 59:62 */     return this.field_149125_c;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void processPacket(INetHandler p_148833_1_)
/* 63:   */   {
/* 64:67 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S38PacketPlayerListItem
 * JD-Core Version:    0.7.0.1
 */