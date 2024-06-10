/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.network.INetHandler;
/*   6:    */ import net.minecraft.network.Packet;
/*   7:    */ import net.minecraft.network.PacketBuffer;
/*   8:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*   9:    */ 
/*  10:    */ public class S12PacketEntityVelocity
/*  11:    */   extends Packet
/*  12:    */ {
/*  13:    */   private int field_149417_a;
/*  14:    */   private int field_149415_b;
/*  15:    */   private int field_149416_c;
/*  16:    */   private int field_149414_d;
/*  17:    */   private static final String __OBFID = "CL_00001328";
/*  18:    */   
/*  19:    */   public S12PacketEntityVelocity() {}
/*  20:    */   
/*  21:    */   public S12PacketEntityVelocity(Entity p_i45219_1_)
/*  22:    */   {
/*  23: 22 */     this(p_i45219_1_.getEntityId(), p_i45219_1_.motionX, p_i45219_1_.motionY, p_i45219_1_.motionZ);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public S12PacketEntityVelocity(int p_i45220_1_, double p_i45220_2_, double p_i45220_4_, double p_i45220_6_)
/*  27:    */   {
/*  28: 27 */     this.field_149417_a = p_i45220_1_;
/*  29: 28 */     double var8 = 3.9D;
/*  30: 30 */     if (p_i45220_2_ < -var8) {
/*  31: 32 */       p_i45220_2_ = -var8;
/*  32:    */     }
/*  33: 35 */     if (p_i45220_4_ < -var8) {
/*  34: 37 */       p_i45220_4_ = -var8;
/*  35:    */     }
/*  36: 40 */     if (p_i45220_6_ < -var8) {
/*  37: 42 */       p_i45220_6_ = -var8;
/*  38:    */     }
/*  39: 45 */     if (p_i45220_2_ > var8) {
/*  40: 47 */       p_i45220_2_ = var8;
/*  41:    */     }
/*  42: 50 */     if (p_i45220_4_ > var8) {
/*  43: 52 */       p_i45220_4_ = var8;
/*  44:    */     }
/*  45: 55 */     if (p_i45220_6_ > var8) {
/*  46: 57 */       p_i45220_6_ = var8;
/*  47:    */     }
/*  48: 60 */     this.field_149415_b = ((int)(p_i45220_2_ * 8000.0D));
/*  49: 61 */     this.field_149416_c = ((int)(p_i45220_4_ * 8000.0D));
/*  50: 62 */     this.field_149414_d = ((int)(p_i45220_6_ * 8000.0D));
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56: 70 */     this.field_149417_a = p_148837_1_.readInt();
/*  57: 71 */     this.field_149415_b = p_148837_1_.readShort();
/*  58: 72 */     this.field_149416_c = p_148837_1_.readShort();
/*  59: 73 */     this.field_149414_d = p_148837_1_.readShort();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65: 81 */     p_148840_1_.writeInt(this.field_149417_a);
/*  66: 82 */     p_148840_1_.writeShort(this.field_149415_b);
/*  67: 83 */     p_148840_1_.writeShort(this.field_149416_c);
/*  68: 84 */     p_148840_1_.writeShort(this.field_149414_d);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void processPacket(INetHandlerPlayClient p_149413_1_)
/*  72:    */   {
/*  73: 89 */     p_149413_1_.handleEntityVelocity(this);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String serialize()
/*  77:    */   {
/*  78: 97 */     return String.format("id=%d, x=%.2f, y=%.2f, z=%.2f", new Object[] { Integer.valueOf(this.field_149417_a), Float.valueOf(this.field_149415_b / 8000.0F), Float.valueOf(this.field_149416_c / 8000.0F), Float.valueOf(this.field_149414_d / 8000.0F) });
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int func_149412_c()
/*  82:    */   {
/*  83:102 */     return this.field_149417_a;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int func_149411_d()
/*  87:    */   {
/*  88:107 */     return this.field_149415_b;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int func_149410_e()
/*  92:    */   {
/*  93:112 */     return this.field_149416_c;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int func_149409_f()
/*  97:    */   {
/*  98:117 */     return this.field_149414_d;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void processPacket(INetHandler p_148833_1_)
/* 102:    */   {
/* 103:122 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S12PacketEntityVelocity
 * JD-Core Version:    0.7.0.1
 */