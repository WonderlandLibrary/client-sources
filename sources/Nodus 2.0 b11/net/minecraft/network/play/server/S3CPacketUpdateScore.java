/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ import net.minecraft.scoreboard.Score;
/*  9:   */ import net.minecraft.scoreboard.ScoreObjective;
/* 10:   */ 
/* 11:   */ public class S3CPacketUpdateScore
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:12 */   private String field_149329_a = "";
/* 15:13 */   private String field_149327_b = "";
/* 16:   */   private int field_149328_c;
/* 17:   */   private int field_149326_d;
/* 18:   */   private static final String __OBFID = "CL_00001335";
/* 19:   */   
/* 20:   */   public S3CPacketUpdateScore() {}
/* 21:   */   
/* 22:   */   public S3CPacketUpdateScore(Score p_i45227_1_, int p_i45227_2_)
/* 23:   */   {
/* 24:22 */     this.field_149329_a = p_i45227_1_.getPlayerName();
/* 25:23 */     this.field_149327_b = p_i45227_1_.func_96645_d().getName();
/* 26:24 */     this.field_149328_c = p_i45227_1_.getScorePoints();
/* 27:25 */     this.field_149326_d = p_i45227_2_;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public S3CPacketUpdateScore(String p_i45228_1_)
/* 31:   */   {
/* 32:30 */     this.field_149329_a = p_i45228_1_;
/* 33:31 */     this.field_149327_b = "";
/* 34:32 */     this.field_149328_c = 0;
/* 35:33 */     this.field_149326_d = 1;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 39:   */     throws IOException
/* 40:   */   {
/* 41:41 */     this.field_149329_a = p_148837_1_.readStringFromBuffer(16);
/* 42:42 */     this.field_149326_d = p_148837_1_.readByte();
/* 43:44 */     if (this.field_149326_d != 1)
/* 44:   */     {
/* 45:46 */       this.field_149327_b = p_148837_1_.readStringFromBuffer(16);
/* 46:47 */       this.field_149328_c = p_148837_1_.readInt();
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 51:   */     throws IOException
/* 52:   */   {
/* 53:56 */     p_148840_1_.writeStringToBuffer(this.field_149329_a);
/* 54:57 */     p_148840_1_.writeByte(this.field_149326_d);
/* 55:59 */     if (this.field_149326_d != 1)
/* 56:   */     {
/* 57:61 */       p_148840_1_.writeStringToBuffer(this.field_149327_b);
/* 58:62 */       p_148840_1_.writeInt(this.field_149328_c);
/* 59:   */     }
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void processPacket(INetHandlerPlayClient p_149325_1_)
/* 63:   */   {
/* 64:68 */     p_149325_1_.handleUpdateScore(this);
/* 65:   */   }
/* 66:   */   
/* 67:   */   public String func_149324_c()
/* 68:   */   {
/* 69:73 */     return this.field_149329_a;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public String func_149321_d()
/* 73:   */   {
/* 74:78 */     return this.field_149327_b;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public int func_149323_e()
/* 78:   */   {
/* 79:83 */     return this.field_149328_c;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public int func_149322_f()
/* 83:   */   {
/* 84:88 */     return this.field_149326_d;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public void processPacket(INetHandler p_148833_1_)
/* 88:   */   {
/* 89:93 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 90:   */   }
/* 91:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S3CPacketUpdateScore
 * JD-Core Version:    0.7.0.1
 */