/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ import net.minecraft.scoreboard.ScoreObjective;
/*  9:   */ 
/* 10:   */ public class S3BPacketScoreboardObjective
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private String field_149343_a;
/* 14:   */   private String field_149341_b;
/* 15:   */   private int field_149342_c;
/* 16:   */   private static final String __OBFID = "CL_00001333";
/* 17:   */   
/* 18:   */   public S3BPacketScoreboardObjective() {}
/* 19:   */   
/* 20:   */   public S3BPacketScoreboardObjective(ScoreObjective p_i45224_1_, int p_i45224_2_)
/* 21:   */   {
/* 22:21 */     this.field_149343_a = p_i45224_1_.getName();
/* 23:22 */     this.field_149341_b = p_i45224_1_.getDisplayName();
/* 24:23 */     this.field_149342_c = p_i45224_2_;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 28:   */     throws IOException
/* 29:   */   {
/* 30:31 */     this.field_149343_a = p_148837_1_.readStringFromBuffer(16);
/* 31:32 */     this.field_149341_b = p_148837_1_.readStringFromBuffer(32);
/* 32:33 */     this.field_149342_c = p_148837_1_.readByte();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:41 */     p_148840_1_.writeStringToBuffer(this.field_149343_a);
/* 39:42 */     p_148840_1_.writeStringToBuffer(this.field_149341_b);
/* 40:43 */     p_148840_1_.writeByte(this.field_149342_c);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void processPacket(INetHandlerPlayClient p_149340_1_)
/* 44:   */   {
/* 45:48 */     p_149340_1_.handleScoreboardObjective(this);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String func_149339_c()
/* 49:   */   {
/* 50:53 */     return this.field_149343_a;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String func_149337_d()
/* 54:   */   {
/* 55:58 */     return this.field_149341_b;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int func_149338_e()
/* 59:   */   {
/* 60:63 */     return this.field_149342_c;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void processPacket(INetHandler p_148833_1_)
/* 64:   */   {
/* 65:68 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S3BPacketScoreboardObjective
 * JD-Core Version:    0.7.0.1
 */