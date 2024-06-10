/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.nbt.NBTTagCompound;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ 
/* 10:   */ public class S35PacketUpdateTileEntity
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_148863_a;
/* 14:   */   private int field_148861_b;
/* 15:   */   private int field_148862_c;
/* 16:   */   private int field_148859_d;
/* 17:   */   private NBTTagCompound field_148860_e;
/* 18:   */   private static final String __OBFID = "CL_00001285";
/* 19:   */   
/* 20:   */   public S35PacketUpdateTileEntity() {}
/* 21:   */   
/* 22:   */   public S35PacketUpdateTileEntity(int p_i45175_1_, int p_i45175_2_, int p_i45175_3_, int p_i45175_4_, NBTTagCompound p_i45175_5_)
/* 23:   */   {
/* 24:23 */     this.field_148863_a = p_i45175_1_;
/* 25:24 */     this.field_148861_b = p_i45175_2_;
/* 26:25 */     this.field_148862_c = p_i45175_3_;
/* 27:26 */     this.field_148859_d = p_i45175_4_;
/* 28:27 */     this.field_148860_e = p_i45175_5_;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:35 */     this.field_148863_a = p_148837_1_.readInt();
/* 35:36 */     this.field_148861_b = p_148837_1_.readShort();
/* 36:37 */     this.field_148862_c = p_148837_1_.readInt();
/* 37:38 */     this.field_148859_d = p_148837_1_.readUnsignedByte();
/* 38:39 */     this.field_148860_e = p_148837_1_.readNBTTagCompoundFromBuffer();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 42:   */     throws IOException
/* 43:   */   {
/* 44:47 */     p_148840_1_.writeInt(this.field_148863_a);
/* 45:48 */     p_148840_1_.writeShort(this.field_148861_b);
/* 46:49 */     p_148840_1_.writeInt(this.field_148862_c);
/* 47:50 */     p_148840_1_.writeByte((byte)this.field_148859_d);
/* 48:51 */     p_148840_1_.writeNBTTagCompoundToBuffer(this.field_148860_e);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void processPacket(INetHandlerPlayClient p_148858_1_)
/* 52:   */   {
/* 53:56 */     p_148858_1_.handleUpdateTileEntity(this);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int func_148856_c()
/* 57:   */   {
/* 58:61 */     return this.field_148863_a;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int func_148855_d()
/* 62:   */   {
/* 63:66 */     return this.field_148861_b;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public int func_148854_e()
/* 67:   */   {
/* 68:71 */     return this.field_148862_c;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public int func_148853_f()
/* 72:   */   {
/* 73:76 */     return this.field_148859_d;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public NBTTagCompound func_148857_g()
/* 77:   */   {
/* 78:81 */     return this.field_148860_e;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public void processPacket(INetHandler p_148833_1_)
/* 82:   */   {
/* 83:86 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S35PacketUpdateTileEntity
 * JD-Core Version:    0.7.0.1
 */