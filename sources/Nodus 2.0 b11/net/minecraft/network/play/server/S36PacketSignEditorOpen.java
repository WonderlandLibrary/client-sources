/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S36PacketSignEditorOpen
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149133_a;
/* 13:   */   private int field_149131_b;
/* 14:   */   private int field_149132_c;
/* 15:   */   private static final String __OBFID = "CL_00001316";
/* 16:   */   
/* 17:   */   public S36PacketSignEditorOpen() {}
/* 18:   */   
/* 19:   */   public S36PacketSignEditorOpen(int p_i45207_1_, int p_i45207_2_, int p_i45207_3_)
/* 20:   */   {
/* 21:20 */     this.field_149133_a = p_i45207_1_;
/* 22:21 */     this.field_149131_b = p_i45207_2_;
/* 23:22 */     this.field_149132_c = p_i45207_3_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void processPacket(INetHandlerPlayClient p_149130_1_)
/* 27:   */   {
/* 28:27 */     p_149130_1_.handleSignEditorOpen(this);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:35 */     this.field_149133_a = p_148837_1_.readInt();
/* 35:36 */     this.field_149131_b = p_148837_1_.readInt();
/* 36:37 */     this.field_149132_c = p_148837_1_.readInt();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 40:   */     throws IOException
/* 41:   */   {
/* 42:45 */     p_148840_1_.writeInt(this.field_149133_a);
/* 43:46 */     p_148840_1_.writeInt(this.field_149131_b);
/* 44:47 */     p_148840_1_.writeInt(this.field_149132_c);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int func_149129_c()
/* 48:   */   {
/* 49:52 */     return this.field_149133_a;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int func_149128_d()
/* 53:   */   {
/* 54:57 */     return this.field_149131_b;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public int func_149127_e()
/* 58:   */   {
/* 59:62 */     return this.field_149132_c;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void processPacket(INetHandler p_148833_1_)
/* 63:   */   {
/* 64:67 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S36PacketSignEditorOpen
 * JD-Core Version:    0.7.0.1
 */