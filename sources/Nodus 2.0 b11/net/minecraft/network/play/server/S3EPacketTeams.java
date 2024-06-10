/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import net.minecraft.network.INetHandler;
/*   8:    */ import net.minecraft.network.Packet;
/*   9:    */ import net.minecraft.network.PacketBuffer;
/*  10:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  11:    */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*  12:    */ 
/*  13:    */ public class S3EPacketTeams
/*  14:    */   extends Packet
/*  15:    */ {
/*  16: 15 */   private String field_149320_a = "";
/*  17: 16 */   private String field_149318_b = "";
/*  18: 17 */   private String field_149319_c = "";
/*  19: 18 */   private String field_149316_d = "";
/*  20: 19 */   private Collection field_149317_e = new ArrayList();
/*  21:    */   private int field_149314_f;
/*  22:    */   private int field_149315_g;
/*  23:    */   private static final String __OBFID = "CL_00001334";
/*  24:    */   
/*  25:    */   public S3EPacketTeams() {}
/*  26:    */   
/*  27:    */   public S3EPacketTeams(ScorePlayerTeam p_i45225_1_, int p_i45225_2_)
/*  28:    */   {
/*  29: 28 */     this.field_149320_a = p_i45225_1_.getRegisteredName();
/*  30: 29 */     this.field_149314_f = p_i45225_2_;
/*  31: 31 */     if ((p_i45225_2_ == 0) || (p_i45225_2_ == 2))
/*  32:    */     {
/*  33: 33 */       this.field_149318_b = p_i45225_1_.func_96669_c();
/*  34: 34 */       this.field_149319_c = p_i45225_1_.getColorPrefix();
/*  35: 35 */       this.field_149316_d = p_i45225_1_.getColorSuffix();
/*  36: 36 */       this.field_149315_g = p_i45225_1_.func_98299_i();
/*  37:    */     }
/*  38: 39 */     if (p_i45225_2_ == 0) {
/*  39: 41 */       this.field_149317_e.addAll(p_i45225_1_.getMembershipCollection());
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public S3EPacketTeams(ScorePlayerTeam p_i45226_1_, Collection p_i45226_2_, int p_i45226_3_)
/*  44:    */   {
/*  45: 47 */     if ((p_i45226_3_ != 3) && (p_i45226_3_ != 4)) {
/*  46: 49 */       throw new IllegalArgumentException("Method must be join or leave for player constructor");
/*  47:    */     }
/*  48: 51 */     if ((p_i45226_2_ != null) && (!p_i45226_2_.isEmpty()))
/*  49:    */     {
/*  50: 53 */       this.field_149314_f = p_i45226_3_;
/*  51: 54 */       this.field_149320_a = p_i45226_1_.getRegisteredName();
/*  52: 55 */       this.field_149317_e.addAll(p_i45226_2_);
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 59 */       throw new IllegalArgumentException("Players cannot be null/empty");
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63: 68 */     this.field_149320_a = p_148837_1_.readStringFromBuffer(16);
/*  64: 69 */     this.field_149314_f = p_148837_1_.readByte();
/*  65: 71 */     if ((this.field_149314_f == 0) || (this.field_149314_f == 2))
/*  66:    */     {
/*  67: 73 */       this.field_149318_b = p_148837_1_.readStringFromBuffer(32);
/*  68: 74 */       this.field_149319_c = p_148837_1_.readStringFromBuffer(16);
/*  69: 75 */       this.field_149316_d = p_148837_1_.readStringFromBuffer(16);
/*  70: 76 */       this.field_149315_g = p_148837_1_.readByte();
/*  71:    */     }
/*  72: 79 */     if ((this.field_149314_f == 0) || (this.field_149314_f == 3) || (this.field_149314_f == 4))
/*  73:    */     {
/*  74: 81 */       short var2 = p_148837_1_.readShort();
/*  75: 83 */       for (int var3 = 0; var3 < var2; var3++) {
/*  76: 85 */         this.field_149317_e.add(p_148837_1_.readStringFromBuffer(16));
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  82:    */     throws IOException
/*  83:    */   {
/*  84: 95 */     p_148840_1_.writeStringToBuffer(this.field_149320_a);
/*  85: 96 */     p_148840_1_.writeByte(this.field_149314_f);
/*  86: 98 */     if ((this.field_149314_f == 0) || (this.field_149314_f == 2))
/*  87:    */     {
/*  88:100 */       p_148840_1_.writeStringToBuffer(this.field_149318_b);
/*  89:101 */       p_148840_1_.writeStringToBuffer(this.field_149319_c);
/*  90:102 */       p_148840_1_.writeStringToBuffer(this.field_149316_d);
/*  91:103 */       p_148840_1_.writeByte(this.field_149315_g);
/*  92:    */     }
/*  93:106 */     if ((this.field_149314_f == 0) || (this.field_149314_f == 3) || (this.field_149314_f == 4))
/*  94:    */     {
/*  95:108 */       p_148840_1_.writeShort(this.field_149317_e.size());
/*  96:109 */       Iterator var2 = this.field_149317_e.iterator();
/*  97:111 */       while (var2.hasNext())
/*  98:    */       {
/*  99:113 */         String var3 = (String)var2.next();
/* 100:114 */         p_148840_1_.writeStringToBuffer(var3);
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void processPacket(INetHandlerPlayClient p_149313_1_)
/* 106:    */   {
/* 107:121 */     p_149313_1_.handleTeams(this);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String func_149312_c()
/* 111:    */   {
/* 112:126 */     return this.field_149320_a;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String func_149306_d()
/* 116:    */   {
/* 117:131 */     return this.field_149318_b;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String func_149311_e()
/* 121:    */   {
/* 122:136 */     return this.field_149319_c;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String func_149309_f()
/* 126:    */   {
/* 127:141 */     return this.field_149316_d;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Collection func_149310_g()
/* 131:    */   {
/* 132:146 */     return this.field_149317_e;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int func_149307_h()
/* 136:    */   {
/* 137:151 */     return this.field_149314_f;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int func_149308_i()
/* 141:    */   {
/* 142:156 */     return this.field_149315_g;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void processPacket(INetHandler p_148833_1_)
/* 146:    */   {
/* 147:161 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 148:    */   }
/* 149:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S3EPacketTeams
 * JD-Core Version:    0.7.0.1
 */