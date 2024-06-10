/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class S23PacketBlockChange
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private int field_148887_a;
/* 15:   */   private int field_148885_b;
/* 16:   */   private int field_148886_c;
/* 17:   */   private Block field_148883_d;
/* 18:   */   private int field_148884_e;
/* 19:   */   private static final String __OBFID = "CL_00001287";
/* 20:   */   
/* 21:   */   public S23PacketBlockChange() {}
/* 22:   */   
/* 23:   */   public S23PacketBlockChange(int p_i45177_1_, int p_i45177_2_, int p_i45177_3_, World p_i45177_4_)
/* 24:   */   {
/* 25:24 */     this.field_148887_a = p_i45177_1_;
/* 26:25 */     this.field_148885_b = p_i45177_2_;
/* 27:26 */     this.field_148886_c = p_i45177_3_;
/* 28:27 */     this.field_148883_d = p_i45177_4_.getBlock(p_i45177_1_, p_i45177_2_, p_i45177_3_);
/* 29:28 */     this.field_148884_e = p_i45177_4_.getBlockMetadata(p_i45177_1_, p_i45177_2_, p_i45177_3_);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:36 */     this.field_148887_a = p_148837_1_.readInt();
/* 36:37 */     this.field_148885_b = p_148837_1_.readUnsignedByte();
/* 37:38 */     this.field_148886_c = p_148837_1_.readInt();
/* 38:39 */     this.field_148883_d = Block.getBlockById(p_148837_1_.readVarIntFromBuffer());
/* 39:40 */     this.field_148884_e = p_148837_1_.readUnsignedByte();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 43:   */     throws IOException
/* 44:   */   {
/* 45:48 */     p_148840_1_.writeInt(this.field_148887_a);
/* 46:49 */     p_148840_1_.writeByte(this.field_148885_b);
/* 47:50 */     p_148840_1_.writeInt(this.field_148886_c);
/* 48:51 */     p_148840_1_.writeVarIntToBuffer(Block.getIdFromBlock(this.field_148883_d));
/* 49:52 */     p_148840_1_.writeByte(this.field_148884_e);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void processPacket(INetHandlerPlayClient p_148882_1_)
/* 53:   */   {
/* 54:57 */     p_148882_1_.handleBlockChange(this);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String serialize()
/* 58:   */   {
/* 59:65 */     return String.format("type=%d, data=%d, x=%d, y=%d, z=%d", new Object[] { Integer.valueOf(Block.getIdFromBlock(this.field_148883_d)), Integer.valueOf(this.field_148884_e), Integer.valueOf(this.field_148887_a), Integer.valueOf(this.field_148885_b), Integer.valueOf(this.field_148886_c) });
/* 60:   */   }
/* 61:   */   
/* 62:   */   public Block func_148880_c()
/* 63:   */   {
/* 64:70 */     return this.field_148883_d;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public int func_148879_d()
/* 68:   */   {
/* 69:75 */     return this.field_148887_a;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public int func_148878_e()
/* 73:   */   {
/* 74:80 */     return this.field_148885_b;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public int func_148877_f()
/* 78:   */   {
/* 79:85 */     return this.field_148886_c;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public int func_148881_g()
/* 83:   */   {
/* 84:90 */     return this.field_148884_e;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public void processPacket(INetHandler p_148833_1_)
/* 88:   */   {
/* 89:95 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 90:   */   }
/* 91:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S23PacketBlockChange
 * JD-Core Version:    0.7.0.1
 */