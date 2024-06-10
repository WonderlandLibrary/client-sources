/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.network.INetHandler;
/*   6:    */ import net.minecraft.network.Packet;
/*   7:    */ import net.minecraft.network.PacketBuffer;
/*   8:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ 
/*  11:    */ public class S18PacketEntityTeleport
/*  12:    */   extends Packet
/*  13:    */ {
/*  14:    */   private int field_149458_a;
/*  15:    */   private int field_149456_b;
/*  16:    */   private int field_149457_c;
/*  17:    */   private int field_149454_d;
/*  18:    */   private byte field_149455_e;
/*  19:    */   private byte field_149453_f;
/*  20:    */   private static final String __OBFID = "CL_00001340";
/*  21:    */   
/*  22:    */   public S18PacketEntityTeleport() {}
/*  23:    */   
/*  24:    */   public S18PacketEntityTeleport(Entity p_i45233_1_)
/*  25:    */   {
/*  26: 25 */     this.field_149458_a = p_i45233_1_.getEntityId();
/*  27: 26 */     this.field_149456_b = MathHelper.floor_double(p_i45233_1_.posX * 32.0D);
/*  28: 27 */     this.field_149457_c = MathHelper.floor_double(p_i45233_1_.posY * 32.0D);
/*  29: 28 */     this.field_149454_d = MathHelper.floor_double(p_i45233_1_.posZ * 32.0D);
/*  30: 29 */     this.field_149455_e = ((byte)(int)(p_i45233_1_.rotationYaw * 256.0F / 360.0F));
/*  31: 30 */     this.field_149453_f = ((byte)(int)(p_i45233_1_.rotationPitch * 256.0F / 360.0F));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public S18PacketEntityTeleport(int p_i45234_1_, int p_i45234_2_, int p_i45234_3_, int p_i45234_4_, byte p_i45234_5_, byte p_i45234_6_)
/*  35:    */   {
/*  36: 35 */     this.field_149458_a = p_i45234_1_;
/*  37: 36 */     this.field_149456_b = p_i45234_2_;
/*  38: 37 */     this.field_149457_c = p_i45234_3_;
/*  39: 38 */     this.field_149454_d = p_i45234_4_;
/*  40: 39 */     this.field_149455_e = p_i45234_5_;
/*  41: 40 */     this.field_149453_f = p_i45234_6_;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47: 48 */     this.field_149458_a = p_148837_1_.readInt();
/*  48: 49 */     this.field_149456_b = p_148837_1_.readInt();
/*  49: 50 */     this.field_149457_c = p_148837_1_.readInt();
/*  50: 51 */     this.field_149454_d = p_148837_1_.readInt();
/*  51: 52 */     this.field_149455_e = p_148837_1_.readByte();
/*  52: 53 */     this.field_149453_f = p_148837_1_.readByte();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  56:    */     throws IOException
/*  57:    */   {
/*  58: 61 */     p_148840_1_.writeInt(this.field_149458_a);
/*  59: 62 */     p_148840_1_.writeInt(this.field_149456_b);
/*  60: 63 */     p_148840_1_.writeInt(this.field_149457_c);
/*  61: 64 */     p_148840_1_.writeInt(this.field_149454_d);
/*  62: 65 */     p_148840_1_.writeByte(this.field_149455_e);
/*  63: 66 */     p_148840_1_.writeByte(this.field_149453_f);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void processPacket(INetHandlerPlayClient p_149452_1_)
/*  67:    */   {
/*  68: 71 */     p_149452_1_.handleEntityTeleport(this);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int func_149451_c()
/*  72:    */   {
/*  73: 76 */     return this.field_149458_a;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int func_149449_d()
/*  77:    */   {
/*  78: 81 */     return this.field_149456_b;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int func_149448_e()
/*  82:    */   {
/*  83: 86 */     return this.field_149457_c;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int func_149446_f()
/*  87:    */   {
/*  88: 91 */     return this.field_149454_d;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public byte func_149450_g()
/*  92:    */   {
/*  93: 96 */     return this.field_149455_e;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public byte func_149447_h()
/*  97:    */   {
/*  98:101 */     return this.field_149453_f;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void processPacket(INetHandler p_148833_1_)
/* 102:    */   {
/* 103:106 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S18PacketEntityTeleport
 * JD-Core Version:    0.7.0.1
 */