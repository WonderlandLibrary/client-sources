/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ 
/*  9:   */ public class S05PacketSpawnPosition
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private int field_149364_a;
/* 13:   */   private int field_149362_b;
/* 14:   */   private int field_149363_c;
/* 15:   */   private static final String __OBFID = "CL_00001336";
/* 16:   */   
/* 17:   */   public S05PacketSpawnPosition() {}
/* 18:   */   
/* 19:   */   public S05PacketSpawnPosition(int p_i45229_1_, int p_i45229_2_, int p_i45229_3_)
/* 20:   */   {
/* 21:20 */     this.field_149364_a = p_i45229_1_;
/* 22:21 */     this.field_149362_b = p_i45229_2_;
/* 23:22 */     this.field_149363_c = p_i45229_3_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:30 */     this.field_149364_a = p_148837_1_.readInt();
/* 30:31 */     this.field_149362_b = p_148837_1_.readInt();
/* 31:32 */     this.field_149363_c = p_148837_1_.readInt();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 35:   */     throws IOException
/* 36:   */   {
/* 37:40 */     p_148840_1_.writeInt(this.field_149364_a);
/* 38:41 */     p_148840_1_.writeInt(this.field_149362_b);
/* 39:42 */     p_148840_1_.writeInt(this.field_149363_c);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void processPacket(INetHandlerPlayClient p_149361_1_)
/* 43:   */   {
/* 44:47 */     p_149361_1_.handleSpawnPosition(this);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public boolean hasPriority()
/* 48:   */   {
/* 49:56 */     return false;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String serialize()
/* 53:   */   {
/* 54:64 */     return String.format("x=%d, y=%d, z=%d", new Object[] { Integer.valueOf(this.field_149364_a), Integer.valueOf(this.field_149362_b), Integer.valueOf(this.field_149363_c) });
/* 55:   */   }
/* 56:   */   
/* 57:   */   public int func_149360_c()
/* 58:   */   {
/* 59:69 */     return this.field_149364_a;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public int func_149359_d()
/* 63:   */   {
/* 64:74 */     return this.field_149362_b;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public int func_149358_e()
/* 68:   */   {
/* 69:79 */     return this.field_149363_c;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void processPacket(INetHandler p_148833_1_)
/* 73:   */   {
/* 74:84 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S05PacketSpawnPosition
 * JD-Core Version:    0.7.0.1
 */