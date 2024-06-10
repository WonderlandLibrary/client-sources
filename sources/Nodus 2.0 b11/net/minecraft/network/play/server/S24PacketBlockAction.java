/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ 
/* 10:   */ public class S24PacketBlockAction
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_148876_a;
/* 14:   */   private int field_148874_b;
/* 15:   */   private int field_148875_c;
/* 16:   */   private int field_148872_d;
/* 17:   */   private int field_148873_e;
/* 18:   */   private Block field_148871_f;
/* 19:   */   private static final String __OBFID = "CL_00001286";
/* 20:   */   
/* 21:   */   public S24PacketBlockAction() {}
/* 22:   */   
/* 23:   */   public S24PacketBlockAction(int p_i45176_1_, int p_i45176_2_, int p_i45176_3_, Block p_i45176_4_, int p_i45176_5_, int p_i45176_6_)
/* 24:   */   {
/* 25:24 */     this.field_148876_a = p_i45176_1_;
/* 26:25 */     this.field_148874_b = p_i45176_2_;
/* 27:26 */     this.field_148875_c = p_i45176_3_;
/* 28:27 */     this.field_148872_d = p_i45176_5_;
/* 29:28 */     this.field_148873_e = p_i45176_6_;
/* 30:29 */     this.field_148871_f = p_i45176_4_;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:37 */     this.field_148876_a = p_148837_1_.readInt();
/* 37:38 */     this.field_148874_b = p_148837_1_.readShort();
/* 38:39 */     this.field_148875_c = p_148837_1_.readInt();
/* 39:40 */     this.field_148872_d = p_148837_1_.readUnsignedByte();
/* 40:41 */     this.field_148873_e = p_148837_1_.readUnsignedByte();
/* 41:42 */     this.field_148871_f = Block.getBlockById(p_148837_1_.readVarIntFromBuffer() & 0xFFF);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 45:   */     throws IOException
/* 46:   */   {
/* 47:50 */     p_148840_1_.writeInt(this.field_148876_a);
/* 48:51 */     p_148840_1_.writeShort(this.field_148874_b);
/* 49:52 */     p_148840_1_.writeInt(this.field_148875_c);
/* 50:53 */     p_148840_1_.writeByte(this.field_148872_d);
/* 51:54 */     p_148840_1_.writeByte(this.field_148873_e);
/* 52:55 */     p_148840_1_.writeVarIntToBuffer(Block.getIdFromBlock(this.field_148871_f) & 0xFFF);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void processPacket(INetHandlerPlayClient p_148870_1_)
/* 56:   */   {
/* 57:60 */     p_148870_1_.handleBlockAction(this);
/* 58:   */   }
/* 59:   */   
/* 60:   */   public Block func_148868_c()
/* 61:   */   {
/* 62:65 */     return this.field_148871_f;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public int func_148867_d()
/* 66:   */   {
/* 67:70 */     return this.field_148876_a;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public int func_148866_e()
/* 71:   */   {
/* 72:75 */     return this.field_148874_b;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public int func_148865_f()
/* 76:   */   {
/* 77:80 */     return this.field_148875_c;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public int func_148869_g()
/* 81:   */   {
/* 82:85 */     return this.field_148872_d;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public int func_148864_h()
/* 86:   */   {
/* 87:90 */     return this.field_148873_e;
/* 88:   */   }
/* 89:   */   
/* 90:   */   public void processPacket(INetHandler p_148833_1_)
/* 91:   */   {
/* 92:95 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 93:   */   }
/* 94:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S24PacketBlockAction
 * JD-Core Version:    0.7.0.1
 */