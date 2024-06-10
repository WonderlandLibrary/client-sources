/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ import net.minecraft.scoreboard.ScoreObjective;
/*  9:   */ 
/* 10:   */ public class S3DPacketDisplayScoreboard
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_149374_a;
/* 14:   */   private String field_149373_b;
/* 15:   */   private static final String __OBFID = "CL_00001325";
/* 16:   */   
/* 17:   */   public S3DPacketDisplayScoreboard() {}
/* 18:   */   
/* 19:   */   public S3DPacketDisplayScoreboard(int p_i45216_1_, ScoreObjective p_i45216_2_)
/* 20:   */   {
/* 21:20 */     this.field_149374_a = p_i45216_1_;
/* 22:22 */     if (p_i45216_2_ == null) {
/* 23:24 */       this.field_149373_b = "";
/* 24:   */     } else {
/* 25:28 */       this.field_149373_b = p_i45216_2_.getName();
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:37 */     this.field_149374_a = p_148837_1_.readByte();
/* 33:38 */     this.field_149373_b = p_148837_1_.readStringFromBuffer(16);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:46 */     p_148840_1_.writeByte(this.field_149374_a);
/* 40:47 */     p_148840_1_.writeStringToBuffer(this.field_149373_b);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void processPacket(INetHandlerPlayClient p_149372_1_)
/* 44:   */   {
/* 45:52 */     p_149372_1_.handleDisplayScoreboard(this);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int func_149371_c()
/* 49:   */   {
/* 50:57 */     return this.field_149374_a;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String func_149370_d()
/* 54:   */   {
/* 55:62 */     return this.field_149373_b;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void processPacket(INetHandler p_148833_1_)
/* 59:   */   {
/* 60:67 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S3DPacketDisplayScoreboard
 * JD-Core Version:    0.7.0.1
 */