/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.effect.EntityLightningBolt;
/*   6:    */ import net.minecraft.network.INetHandler;
/*   7:    */ import net.minecraft.network.Packet;
/*   8:    */ import net.minecraft.network.PacketBuffer;
/*   9:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  10:    */ import net.minecraft.util.MathHelper;
/*  11:    */ 
/*  12:    */ public class S2CPacketSpawnGlobalEntity
/*  13:    */   extends Packet
/*  14:    */ {
/*  15:    */   private int field_149059_a;
/*  16:    */   private int field_149057_b;
/*  17:    */   private int field_149058_c;
/*  18:    */   private int field_149055_d;
/*  19:    */   private int field_149056_e;
/*  20:    */   private static final String __OBFID = "CL_00001278";
/*  21:    */   
/*  22:    */   public S2CPacketSpawnGlobalEntity() {}
/*  23:    */   
/*  24:    */   public S2CPacketSpawnGlobalEntity(Entity p_i45191_1_)
/*  25:    */   {
/*  26: 25 */     this.field_149059_a = p_i45191_1_.getEntityId();
/*  27: 26 */     this.field_149057_b = MathHelper.floor_double(p_i45191_1_.posX * 32.0D);
/*  28: 27 */     this.field_149058_c = MathHelper.floor_double(p_i45191_1_.posY * 32.0D);
/*  29: 28 */     this.field_149055_d = MathHelper.floor_double(p_i45191_1_.posZ * 32.0D);
/*  30: 30 */     if ((p_i45191_1_ instanceof EntityLightningBolt)) {
/*  31: 32 */       this.field_149056_e = 1;
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 41 */     this.field_149059_a = p_148837_1_.readVarIntFromBuffer();
/*  39: 42 */     this.field_149056_e = p_148837_1_.readByte();
/*  40: 43 */     this.field_149057_b = p_148837_1_.readInt();
/*  41: 44 */     this.field_149058_c = p_148837_1_.readInt();
/*  42: 45 */     this.field_149055_d = p_148837_1_.readInt();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 53 */     p_148840_1_.writeVarIntToBuffer(this.field_149059_a);
/*  49: 54 */     p_148840_1_.writeByte(this.field_149056_e);
/*  50: 55 */     p_148840_1_.writeInt(this.field_149057_b);
/*  51: 56 */     p_148840_1_.writeInt(this.field_149058_c);
/*  52: 57 */     p_148840_1_.writeInt(this.field_149055_d);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void processPacket(INetHandlerPlayClient p_149054_1_)
/*  56:    */   {
/*  57: 62 */     p_149054_1_.handleSpawnGlobalEntity(this);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String serialize()
/*  61:    */   {
/*  62: 70 */     return String.format("id=%d, type=%d, x=%.2f, y=%.2f, z=%.2f", new Object[] { Integer.valueOf(this.field_149059_a), Integer.valueOf(this.field_149056_e), Float.valueOf(this.field_149057_b / 32.0F), Float.valueOf(this.field_149058_c / 32.0F), Float.valueOf(this.field_149055_d / 32.0F) });
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int func_149052_c()
/*  66:    */   {
/*  67: 75 */     return this.field_149059_a;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int func_149051_d()
/*  71:    */   {
/*  72: 80 */     return this.field_149057_b;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int func_149050_e()
/*  76:    */   {
/*  77: 85 */     return this.field_149058_c;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int func_149049_f()
/*  81:    */   {
/*  82: 90 */     return this.field_149055_d;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int func_149053_g()
/*  86:    */   {
/*  87: 95 */     return this.field_149056_e;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void processPacket(INetHandler p_148833_1_)
/*  91:    */   {
/*  92:100 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity
 * JD-Core Version:    0.7.0.1
 */