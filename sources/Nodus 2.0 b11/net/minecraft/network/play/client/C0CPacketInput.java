/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  8:   */ 
/*  9:   */ public class C0CPacketInput
/* 10:   */   extends Packet
/* 11:   */ {
/* 12:   */   private float field_149624_a;
/* 13:   */   private float field_149622_b;
/* 14:   */   private boolean field_149623_c;
/* 15:   */   private boolean field_149621_d;
/* 16:   */   private static final String __OBFID = "CL_00001367";
/* 17:   */   
/* 18:   */   public C0CPacketInput() {}
/* 19:   */   
/* 20:   */   public C0CPacketInput(float p_i45261_1_, float p_i45261_2_, boolean p_i45261_3_, boolean p_i45261_4_)
/* 21:   */   {
/* 22:21 */     this.field_149624_a = p_i45261_1_;
/* 23:22 */     this.field_149622_b = p_i45261_2_;
/* 24:23 */     this.field_149623_c = p_i45261_3_;
/* 25:24 */     this.field_149621_d = p_i45261_4_;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:32 */     this.field_149624_a = p_148837_1_.readFloat();
/* 32:33 */     this.field_149622_b = p_148837_1_.readFloat();
/* 33:34 */     this.field_149623_c = p_148837_1_.readBoolean();
/* 34:35 */     this.field_149621_d = p_148837_1_.readBoolean();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:43 */     p_148840_1_.writeFloat(this.field_149624_a);
/* 41:44 */     p_148840_1_.writeFloat(this.field_149622_b);
/* 42:45 */     p_148840_1_.writeBoolean(this.field_149623_c);
/* 43:46 */     p_148840_1_.writeBoolean(this.field_149621_d);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void processPacket(INetHandlerPlayServer p_149619_1_)
/* 47:   */   {
/* 48:51 */     p_149619_1_.processInput(this);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public float func_149620_c()
/* 52:   */   {
/* 53:56 */     return this.field_149624_a;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public float func_149616_d()
/* 57:   */   {
/* 58:61 */     return this.field_149622_b;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public boolean func_149618_e()
/* 62:   */   {
/* 63:66 */     return this.field_149623_c;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public boolean func_149617_f()
/* 67:   */   {
/* 68:71 */     return this.field_149621_d;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void processPacket(INetHandler p_148833_1_)
/* 72:   */   {
/* 73:76 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C0CPacketInput
 * JD-Core Version:    0.7.0.1
 */