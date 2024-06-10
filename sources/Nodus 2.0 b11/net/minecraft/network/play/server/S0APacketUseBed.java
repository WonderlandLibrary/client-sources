/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class S0APacketUseBed
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private int field_149097_a;
/* 15:   */   private int field_149095_b;
/* 16:   */   private int field_149096_c;
/* 17:   */   private int field_149094_d;
/* 18:   */   private static final String __OBFID = "CL_00001319";
/* 19:   */   
/* 20:   */   public S0APacketUseBed() {}
/* 21:   */   
/* 22:   */   public S0APacketUseBed(EntityPlayer p_i45210_1_, int p_i45210_2_, int p_i45210_3_, int p_i45210_4_)
/* 23:   */   {
/* 24:23 */     this.field_149095_b = p_i45210_2_;
/* 25:24 */     this.field_149096_c = p_i45210_3_;
/* 26:25 */     this.field_149094_d = p_i45210_4_;
/* 27:26 */     this.field_149097_a = p_i45210_1_.getEntityId();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:34 */     this.field_149097_a = p_148837_1_.readInt();
/* 34:35 */     this.field_149095_b = p_148837_1_.readInt();
/* 35:36 */     this.field_149096_c = p_148837_1_.readByte();
/* 36:37 */     this.field_149094_d = p_148837_1_.readInt();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 40:   */     throws IOException
/* 41:   */   {
/* 42:45 */     p_148840_1_.writeInt(this.field_149097_a);
/* 43:46 */     p_148840_1_.writeInt(this.field_149095_b);
/* 44:47 */     p_148840_1_.writeByte(this.field_149096_c);
/* 45:48 */     p_148840_1_.writeInt(this.field_149094_d);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void processPacket(INetHandlerPlayClient p_149093_1_)
/* 49:   */   {
/* 50:53 */     p_149093_1_.handleUseBed(this);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public EntityPlayer func_149091_a(World p_149091_1_)
/* 54:   */   {
/* 55:58 */     return (EntityPlayer)p_149091_1_.getEntityByID(this.field_149097_a);
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int func_149092_c()
/* 59:   */   {
/* 60:63 */     return this.field_149095_b;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public int func_149090_d()
/* 64:   */   {
/* 65:68 */     return this.field_149096_c;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public int func_149089_e()
/* 69:   */   {
/* 70:73 */     return this.field_149094_d;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public void processPacket(INetHandler p_148833_1_)
/* 74:   */   {
/* 75:78 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S0APacketUseBed
 * JD-Core Version:    0.7.0.1
 */