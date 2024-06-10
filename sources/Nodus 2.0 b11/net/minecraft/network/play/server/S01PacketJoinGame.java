/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.network.INetHandler;
/*   5:    */ import net.minecraft.network.Packet;
/*   6:    */ import net.minecraft.network.PacketBuffer;
/*   7:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*   8:    */ import net.minecraft.world.EnumDifficulty;
/*   9:    */ import net.minecraft.world.WorldSettings.GameType;
/*  10:    */ import net.minecraft.world.WorldType;
/*  11:    */ 
/*  12:    */ public class S01PacketJoinGame
/*  13:    */   extends Packet
/*  14:    */ {
/*  15:    */   private int field_149206_a;
/*  16:    */   private boolean field_149204_b;
/*  17:    */   private WorldSettings.GameType field_149205_c;
/*  18:    */   private int field_149202_d;
/*  19:    */   private EnumDifficulty field_149203_e;
/*  20:    */   private int field_149200_f;
/*  21:    */   private WorldType field_149201_g;
/*  22:    */   private static final String __OBFID = "CL_00001310";
/*  23:    */   
/*  24:    */   public S01PacketJoinGame() {}
/*  25:    */   
/*  26:    */   public S01PacketJoinGame(int p_i45201_1_, WorldSettings.GameType p_i45201_2_, boolean p_i45201_3_, int p_i45201_4_, EnumDifficulty p_i45201_5_, int p_i45201_6_, WorldType p_i45201_7_)
/*  27:    */   {
/*  28: 27 */     this.field_149206_a = p_i45201_1_;
/*  29: 28 */     this.field_149202_d = p_i45201_4_;
/*  30: 29 */     this.field_149203_e = p_i45201_5_;
/*  31: 30 */     this.field_149205_c = p_i45201_2_;
/*  32: 31 */     this.field_149200_f = p_i45201_6_;
/*  33: 32 */     this.field_149204_b = p_i45201_3_;
/*  34: 33 */     this.field_149201_g = p_i45201_7_;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 41 */     this.field_149206_a = p_148837_1_.readInt();
/*  41: 42 */     short var2 = p_148837_1_.readUnsignedByte();
/*  42: 43 */     this.field_149204_b = ((var2 & 0x8) == 8);
/*  43: 44 */     int var3 = var2 & 0xFFFFFFF7;
/*  44: 45 */     this.field_149205_c = WorldSettings.GameType.getByID(var3);
/*  45: 46 */     this.field_149202_d = p_148837_1_.readByte();
/*  46: 47 */     this.field_149203_e = EnumDifficulty.getDifficultyEnum(p_148837_1_.readUnsignedByte());
/*  47: 48 */     this.field_149200_f = p_148837_1_.readUnsignedByte();
/*  48: 49 */     this.field_149201_g = WorldType.parseWorldType(p_148837_1_.readStringFromBuffer(16));
/*  49: 51 */     if (this.field_149201_g == null) {
/*  50: 53 */       this.field_149201_g = WorldType.DEFAULT;
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57: 62 */     p_148840_1_.writeInt(this.field_149206_a);
/*  58: 63 */     int var2 = this.field_149205_c.getID();
/*  59: 65 */     if (this.field_149204_b) {
/*  60: 67 */       var2 |= 0x8;
/*  61:    */     }
/*  62: 70 */     p_148840_1_.writeByte(var2);
/*  63: 71 */     p_148840_1_.writeByte(this.field_149202_d);
/*  64: 72 */     p_148840_1_.writeByte(this.field_149203_e.getDifficultyId());
/*  65: 73 */     p_148840_1_.writeByte(this.field_149200_f);
/*  66: 74 */     p_148840_1_.writeStringToBuffer(this.field_149201_g.getWorldTypeName());
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void processPacket(INetHandlerPlayClient p_149199_1_)
/*  70:    */   {
/*  71: 79 */     p_149199_1_.handleJoinGame(this);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String serialize()
/*  75:    */   {
/*  76: 87 */     return String.format("eid=%d, gameType=%d, hardcore=%b, dimension=%d, difficulty=%s, maxplayers=%d", new Object[] { Integer.valueOf(this.field_149206_a), Integer.valueOf(this.field_149205_c.getID()), Boolean.valueOf(this.field_149204_b), Integer.valueOf(this.field_149202_d), this.field_149203_e, Integer.valueOf(this.field_149200_f) });
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int func_149197_c()
/*  80:    */   {
/*  81: 92 */     return this.field_149206_a;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean func_149195_d()
/*  85:    */   {
/*  86: 97 */     return this.field_149204_b;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public WorldSettings.GameType func_149198_e()
/*  90:    */   {
/*  91:102 */     return this.field_149205_c;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int func_149194_f()
/*  95:    */   {
/*  96:107 */     return this.field_149202_d;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public EnumDifficulty func_149192_g()
/* 100:    */   {
/* 101:112 */     return this.field_149203_e;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int func_149193_h()
/* 105:    */   {
/* 106:117 */     return this.field_149200_f;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public WorldType func_149196_i()
/* 110:    */   {
/* 111:122 */     return this.field_149201_g;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void processPacket(INetHandler p_148833_1_)
/* 115:    */   {
/* 116:127 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S01PacketJoinGame
 * JD-Core Version:    0.7.0.1
 */