/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.entity.item.EntityPainting;
/*   5:    */ import net.minecraft.entity.item.EntityPainting.EnumArt;
/*   6:    */ import net.minecraft.network.INetHandler;
/*   7:    */ import net.minecraft.network.Packet;
/*   8:    */ import net.minecraft.network.PacketBuffer;
/*   9:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  10:    */ 
/*  11:    */ public class S10PacketSpawnPainting
/*  12:    */   extends Packet
/*  13:    */ {
/*  14:    */   private int field_148973_a;
/*  15:    */   private int field_148971_b;
/*  16:    */   private int field_148972_c;
/*  17:    */   private int field_148969_d;
/*  18:    */   private int field_148970_e;
/*  19:    */   private String field_148968_f;
/*  20:    */   private static final String __OBFID = "CL_00001280";
/*  21:    */   
/*  22:    */   public S10PacketSpawnPainting() {}
/*  23:    */   
/*  24:    */   public S10PacketSpawnPainting(EntityPainting p_i45170_1_)
/*  25:    */   {
/*  26: 24 */     this.field_148973_a = p_i45170_1_.getEntityId();
/*  27: 25 */     this.field_148971_b = p_i45170_1_.field_146063_b;
/*  28: 26 */     this.field_148972_c = p_i45170_1_.field_146064_c;
/*  29: 27 */     this.field_148969_d = p_i45170_1_.field_146062_d;
/*  30: 28 */     this.field_148970_e = p_i45170_1_.hangingDirection;
/*  31: 29 */     this.field_148968_f = p_i45170_1_.art.title;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 37 */     this.field_148973_a = p_148837_1_.readVarIntFromBuffer();
/*  38: 38 */     this.field_148968_f = p_148837_1_.readStringFromBuffer(EntityPainting.EnumArt.maxArtTitleLength);
/*  39: 39 */     this.field_148971_b = p_148837_1_.readInt();
/*  40: 40 */     this.field_148972_c = p_148837_1_.readInt();
/*  41: 41 */     this.field_148969_d = p_148837_1_.readInt();
/*  42: 42 */     this.field_148970_e = p_148837_1_.readInt();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 50 */     p_148840_1_.writeVarIntToBuffer(this.field_148973_a);
/*  49: 51 */     p_148840_1_.writeStringToBuffer(this.field_148968_f);
/*  50: 52 */     p_148840_1_.writeInt(this.field_148971_b);
/*  51: 53 */     p_148840_1_.writeInt(this.field_148972_c);
/*  52: 54 */     p_148840_1_.writeInt(this.field_148969_d);
/*  53: 55 */     p_148840_1_.writeInt(this.field_148970_e);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void processPacket(INetHandlerPlayClient p_148967_1_)
/*  57:    */   {
/*  58: 60 */     p_148967_1_.handleSpawnPainting(this);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String serialize()
/*  62:    */   {
/*  63: 68 */     return String.format("id=%d, type=%s, x=%d, y=%d, z=%d", new Object[] { Integer.valueOf(this.field_148973_a), this.field_148968_f, Integer.valueOf(this.field_148971_b), Integer.valueOf(this.field_148972_c), Integer.valueOf(this.field_148969_d) });
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int func_148965_c()
/*  67:    */   {
/*  68: 73 */     return this.field_148973_a;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int func_148964_d()
/*  72:    */   {
/*  73: 78 */     return this.field_148971_b;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int func_148963_e()
/*  77:    */   {
/*  78: 83 */     return this.field_148972_c;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int func_148962_f()
/*  82:    */   {
/*  83: 88 */     return this.field_148969_d;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int func_148966_g()
/*  87:    */   {
/*  88: 93 */     return this.field_148970_e;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String func_148961_h()
/*  92:    */   {
/*  93: 98 */     return this.field_148968_f;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void processPacket(INetHandler p_148833_1_)
/*  97:    */   {
/*  98:103 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S10PacketSpawnPainting
 * JD-Core Version:    0.7.0.1
 */