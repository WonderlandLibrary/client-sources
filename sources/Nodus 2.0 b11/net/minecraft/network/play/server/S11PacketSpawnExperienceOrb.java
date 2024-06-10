/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.entity.item.EntityXPOrb;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ import net.minecraft.util.MathHelper;
/* 10:   */ 
/* 11:   */ public class S11PacketSpawnExperienceOrb
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private int field_148992_a;
/* 15:   */   private int field_148990_b;
/* 16:   */   private int field_148991_c;
/* 17:   */   private int field_148988_d;
/* 18:   */   private int field_148989_e;
/* 19:   */   private static final String __OBFID = "CL_00001277";
/* 20:   */   
/* 21:   */   public S11PacketSpawnExperienceOrb() {}
/* 22:   */   
/* 23:   */   public S11PacketSpawnExperienceOrb(EntityXPOrb p_i45167_1_)
/* 24:   */   {
/* 25:24 */     this.field_148992_a = p_i45167_1_.getEntityId();
/* 26:25 */     this.field_148990_b = MathHelper.floor_double(p_i45167_1_.posX * 32.0D);
/* 27:26 */     this.field_148991_c = MathHelper.floor_double(p_i45167_1_.posY * 32.0D);
/* 28:27 */     this.field_148988_d = MathHelper.floor_double(p_i45167_1_.posZ * 32.0D);
/* 29:28 */     this.field_148989_e = p_i45167_1_.getXpValue();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:36 */     this.field_148992_a = p_148837_1_.readVarIntFromBuffer();
/* 36:37 */     this.field_148990_b = p_148837_1_.readInt();
/* 37:38 */     this.field_148991_c = p_148837_1_.readInt();
/* 38:39 */     this.field_148988_d = p_148837_1_.readInt();
/* 39:40 */     this.field_148989_e = p_148837_1_.readShort();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 43:   */     throws IOException
/* 44:   */   {
/* 45:48 */     p_148840_1_.writeVarIntToBuffer(this.field_148992_a);
/* 46:49 */     p_148840_1_.writeInt(this.field_148990_b);
/* 47:50 */     p_148840_1_.writeInt(this.field_148991_c);
/* 48:51 */     p_148840_1_.writeInt(this.field_148988_d);
/* 49:52 */     p_148840_1_.writeShort(this.field_148989_e);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void processPacket(INetHandlerPlayClient p_148987_1_)
/* 53:   */   {
/* 54:57 */     p_148987_1_.handleSpawnExperienceOrb(this);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String serialize()
/* 58:   */   {
/* 59:65 */     return String.format("id=%d, value=%d, x=%.2f, y=%.2f, z=%.2f", new Object[] { Integer.valueOf(this.field_148992_a), Integer.valueOf(this.field_148989_e), Float.valueOf(this.field_148990_b / 32.0F), Float.valueOf(this.field_148991_c / 32.0F), Float.valueOf(this.field_148988_d / 32.0F) });
/* 60:   */   }
/* 61:   */   
/* 62:   */   public int func_148985_c()
/* 63:   */   {
/* 64:70 */     return this.field_148992_a;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public int func_148984_d()
/* 68:   */   {
/* 69:75 */     return this.field_148990_b;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public int func_148983_e()
/* 73:   */   {
/* 74:80 */     return this.field_148991_c;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public int func_148982_f()
/* 78:   */   {
/* 79:85 */     return this.field_148988_d;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public int func_148986_g()
/* 83:   */   {
/* 84:90 */     return this.field_148989_e;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public void processPacket(INetHandler p_148833_1_)
/* 88:   */   {
/* 89:95 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 90:   */   }
/* 91:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S11PacketSpawnExperienceOrb
 * JD-Core Version:    0.7.0.1
 */