/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ import net.minecraft.world.EnumDifficulty;
/*  9:   */ import net.minecraft.world.WorldSettings.GameType;
/* 10:   */ import net.minecraft.world.WorldType;
/* 11:   */ 
/* 12:   */ public class S07PacketRespawn
/* 13:   */   extends Packet
/* 14:   */ {
/* 15:   */   private int field_149088_a;
/* 16:   */   private EnumDifficulty field_149086_b;
/* 17:   */   private WorldSettings.GameType field_149087_c;
/* 18:   */   private WorldType field_149085_d;
/* 19:   */   private static final String __OBFID = "CL_00001322";
/* 20:   */   
/* 21:   */   public S07PacketRespawn() {}
/* 22:   */   
/* 23:   */   public S07PacketRespawn(int p_i45213_1_, EnumDifficulty p_i45213_2_, WorldType p_i45213_3_, WorldSettings.GameType p_i45213_4_)
/* 24:   */   {
/* 25:24 */     this.field_149088_a = p_i45213_1_;
/* 26:25 */     this.field_149086_b = p_i45213_2_;
/* 27:26 */     this.field_149087_c = p_i45213_4_;
/* 28:27 */     this.field_149085_d = p_i45213_3_;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void processPacket(INetHandlerPlayClient p_149084_1_)
/* 32:   */   {
/* 33:32 */     p_149084_1_.handleRespawn(this);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:40 */     this.field_149088_a = p_148837_1_.readInt();
/* 40:41 */     this.field_149086_b = EnumDifficulty.getDifficultyEnum(p_148837_1_.readUnsignedByte());
/* 41:42 */     this.field_149087_c = WorldSettings.GameType.getByID(p_148837_1_.readUnsignedByte());
/* 42:43 */     this.field_149085_d = WorldType.parseWorldType(p_148837_1_.readStringFromBuffer(16));
/* 43:45 */     if (this.field_149085_d == null) {
/* 44:47 */       this.field_149085_d = WorldType.DEFAULT;
/* 45:   */     }
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 49:   */     throws IOException
/* 50:   */   {
/* 51:56 */     p_148840_1_.writeInt(this.field_149088_a);
/* 52:57 */     p_148840_1_.writeByte(this.field_149086_b.getDifficultyId());
/* 53:58 */     p_148840_1_.writeByte(this.field_149087_c.getID());
/* 54:59 */     p_148840_1_.writeStringToBuffer(this.field_149085_d.getWorldTypeName());
/* 55:   */   }
/* 56:   */   
/* 57:   */   public int func_149082_c()
/* 58:   */   {
/* 59:64 */     return this.field_149088_a;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public EnumDifficulty func_149081_d()
/* 63:   */   {
/* 64:69 */     return this.field_149086_b;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public WorldSettings.GameType func_149083_e()
/* 68:   */   {
/* 69:74 */     return this.field_149087_c;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public WorldType func_149080_f()
/* 73:   */   {
/* 74:79 */     return this.field_149085_d;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public void processPacket(INetHandler p_148833_1_)
/* 78:   */   {
/* 79:84 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S07PacketRespawn
 * JD-Core Version:    0.7.0.1
 */