/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*   5:    */ import net.minecraft.network.INetHandler;
/*   6:    */ import net.minecraft.network.Packet;
/*   7:    */ import net.minecraft.network.PacketBuffer;
/*   8:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*   9:    */ 
/*  10:    */ public class S39PacketPlayerAbilities
/*  11:    */   extends Packet
/*  12:    */ {
/*  13:    */   private boolean field_149119_a;
/*  14:    */   private boolean field_149117_b;
/*  15:    */   private boolean field_149118_c;
/*  16:    */   private boolean field_149115_d;
/*  17:    */   private float field_149116_e;
/*  18:    */   private float field_149114_f;
/*  19:    */   private static final String __OBFID = "CL_00001317";
/*  20:    */   
/*  21:    */   public S39PacketPlayerAbilities() {}
/*  22:    */   
/*  23:    */   public S39PacketPlayerAbilities(PlayerCapabilities p_i45208_1_)
/*  24:    */   {
/*  25: 24 */     func_149108_a(p_i45208_1_.disableDamage);
/*  26: 25 */     func_149102_b(p_i45208_1_.isFlying);
/*  27: 26 */     func_149109_c(p_i45208_1_.allowFlying);
/*  28: 27 */     func_149111_d(p_i45208_1_.isCreativeMode);
/*  29: 28 */     func_149104_a(p_i45208_1_.getFlySpeed());
/*  30: 29 */     func_149110_b(p_i45208_1_.getWalkSpeed());
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36: 37 */     byte var2 = p_148837_1_.readByte();
/*  37: 38 */     func_149108_a((var2 & 0x1) > 0);
/*  38: 39 */     func_149102_b((var2 & 0x2) > 0);
/*  39: 40 */     func_149109_c((var2 & 0x4) > 0);
/*  40: 41 */     func_149111_d((var2 & 0x8) > 0);
/*  41: 42 */     func_149104_a(p_148837_1_.readFloat());
/*  42: 43 */     func_149110_b(p_148837_1_.readFloat());
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 51 */     byte var2 = 0;
/*  49: 53 */     if (func_149112_c()) {
/*  50: 55 */       var2 = (byte)(var2 | 0x1);
/*  51:    */     }
/*  52: 58 */     if (func_149106_d()) {
/*  53: 60 */       var2 = (byte)(var2 | 0x2);
/*  54:    */     }
/*  55: 63 */     if (func_149105_e()) {
/*  56: 65 */       var2 = (byte)(var2 | 0x4);
/*  57:    */     }
/*  58: 68 */     if (func_149103_f()) {
/*  59: 70 */       var2 = (byte)(var2 | 0x8);
/*  60:    */     }
/*  61: 73 */     p_148840_1_.writeByte(var2);
/*  62: 74 */     p_148840_1_.writeFloat(this.field_149116_e);
/*  63: 75 */     p_148840_1_.writeFloat(this.field_149114_f);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void processPacket(INetHandlerPlayClient p_149113_1_)
/*  67:    */   {
/*  68: 80 */     p_149113_1_.handlePlayerAbilities(this);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String serialize()
/*  72:    */   {
/*  73: 88 */     return String.format("invuln=%b, flying=%b, canfly=%b, instabuild=%b, flyspeed=%.4f, walkspped=%.4f", new Object[] { Boolean.valueOf(func_149112_c()), Boolean.valueOf(func_149106_d()), Boolean.valueOf(func_149105_e()), Boolean.valueOf(func_149103_f()), Float.valueOf(func_149101_g()), Float.valueOf(func_149107_h()) });
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean func_149112_c()
/*  77:    */   {
/*  78: 93 */     return this.field_149119_a;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void func_149108_a(boolean p_149108_1_)
/*  82:    */   {
/*  83: 98 */     this.field_149119_a = p_149108_1_;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean func_149106_d()
/*  87:    */   {
/*  88:103 */     return this.field_149117_b;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void func_149102_b(boolean p_149102_1_)
/*  92:    */   {
/*  93:108 */     this.field_149117_b = p_149102_1_;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean func_149105_e()
/*  97:    */   {
/*  98:113 */     return this.field_149118_c;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void func_149109_c(boolean p_149109_1_)
/* 102:    */   {
/* 103:118 */     this.field_149118_c = p_149109_1_;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean func_149103_f()
/* 107:    */   {
/* 108:123 */     return this.field_149115_d;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void func_149111_d(boolean p_149111_1_)
/* 112:    */   {
/* 113:128 */     this.field_149115_d = p_149111_1_;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public float func_149101_g()
/* 117:    */   {
/* 118:133 */     return this.field_149116_e;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void func_149104_a(float p_149104_1_)
/* 122:    */   {
/* 123:138 */     this.field_149116_e = p_149104_1_;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public float func_149107_h()
/* 127:    */   {
/* 128:143 */     return this.field_149114_f;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void func_149110_b(float p_149110_1_)
/* 132:    */   {
/* 133:148 */     this.field_149114_f = p_149110_1_;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void processPacket(INetHandler p_148833_1_)
/* 137:    */   {
/* 138:153 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S39PacketPlayerAbilities
 * JD-Core Version:    0.7.0.1
 */