/*   1:    */ package net.minecraft.network.play.client;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
/*   5:    */ import net.minecraft.network.INetHandler;
/*   6:    */ import net.minecraft.network.Packet;
/*   7:    */ import net.minecraft.network.PacketBuffer;
/*   8:    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*   9:    */ import net.minecraft.world.EnumDifficulty;
/*  10:    */ 
/*  11:    */ public class C15PacketClientSettings
/*  12:    */   extends Packet
/*  13:    */ {
/*  14:    */   private String field_149530_a;
/*  15:    */   private int field_149528_b;
/*  16:    */   private EntityPlayer.EnumChatVisibility field_149529_c;
/*  17:    */   private boolean field_149526_d;
/*  18:    */   private EnumDifficulty field_149527_e;
/*  19:    */   private boolean field_149525_f;
/*  20:    */   private static final String __OBFID = "CL_00001350";
/*  21:    */   
/*  22:    */   public C15PacketClientSettings() {}
/*  23:    */   
/*  24:    */   public C15PacketClientSettings(String p_i45243_1_, int p_i45243_2_, EntityPlayer.EnumChatVisibility p_i45243_3_, boolean p_i45243_4_, EnumDifficulty p_i45243_5_, boolean p_i45243_6_)
/*  25:    */   {
/*  26: 25 */     this.field_149530_a = p_i45243_1_;
/*  27: 26 */     this.field_149528_b = p_i45243_2_;
/*  28: 27 */     this.field_149529_c = p_i45243_3_;
/*  29: 28 */     this.field_149526_d = p_i45243_4_;
/*  30: 29 */     this.field_149527_e = p_i45243_5_;
/*  31: 30 */     this.field_149525_f = p_i45243_6_;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 38 */     this.field_149530_a = p_148837_1_.readStringFromBuffer(7);
/*  38: 39 */     this.field_149528_b = p_148837_1_.readByte();
/*  39: 40 */     this.field_149529_c = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(p_148837_1_.readByte());
/*  40: 41 */     this.field_149526_d = p_148837_1_.readBoolean();
/*  41: 42 */     this.field_149527_e = EnumDifficulty.getDifficultyEnum(p_148837_1_.readByte());
/*  42: 43 */     this.field_149525_f = p_148837_1_.readBoolean();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 51 */     p_148840_1_.writeStringToBuffer(this.field_149530_a);
/*  49: 52 */     p_148840_1_.writeByte(this.field_149528_b);
/*  50: 53 */     p_148840_1_.writeByte(this.field_149529_c.getChatVisibility());
/*  51: 54 */     p_148840_1_.writeBoolean(this.field_149526_d);
/*  52: 55 */     p_148840_1_.writeByte(this.field_149527_e.getDifficultyId());
/*  53: 56 */     p_148840_1_.writeBoolean(this.field_149525_f);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void processPacket(INetHandlerPlayServer p_149522_1_)
/*  57:    */   {
/*  58: 61 */     p_149522_1_.processClientSettings(this);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String func_149524_c()
/*  62:    */   {
/*  63: 66 */     return this.field_149530_a;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int func_149521_d()
/*  67:    */   {
/*  68: 71 */     return this.field_149528_b;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public EntityPlayer.EnumChatVisibility func_149523_e()
/*  72:    */   {
/*  73: 76 */     return this.field_149529_c;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean func_149520_f()
/*  77:    */   {
/*  78: 81 */     return this.field_149526_d;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public EnumDifficulty func_149518_g()
/*  82:    */   {
/*  83: 86 */     return this.field_149527_e;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean func_149519_h()
/*  87:    */   {
/*  88: 91 */     return this.field_149525_f;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String serialize()
/*  92:    */   {
/*  93: 99 */     return String.format("lang='%s', view=%d, chat=%s, col=%b, difficulty=%s, cape=%b", new Object[] { this.field_149530_a, Integer.valueOf(this.field_149528_b), this.field_149529_c, Boolean.valueOf(this.field_149526_d), this.field_149527_e, Boolean.valueOf(this.field_149525_f) });
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void processPacket(INetHandler p_148833_1_)
/*  97:    */   {
/*  98:104 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C15PacketClientSettings
 * JD-Core Version:    0.7.0.1
 */