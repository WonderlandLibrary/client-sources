/*   1:    */ package net.minecraft.client.mco;
/*   2:    */ 
/*   3:    */ import com.google.gson.JsonArray;
/*   4:    */ import com.google.gson.JsonElement;
/*   5:    */ import com.google.gson.JsonIOException;
/*   6:    */ import com.google.gson.JsonObject;
/*   7:    */ import com.google.gson.JsonParser;
/*   8:    */ import com.google.gson.JsonSyntaxException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import org.apache.commons.lang3.builder.EqualsBuilder;
/*  13:    */ import org.apache.commons.lang3.builder.HashCodeBuilder;
/*  14:    */ 
/*  15:    */ public class McoServer
/*  16:    */ {
/*  17:    */   public long field_148812_a;
/*  18:    */   public String field_148810_b;
/*  19:    */   public String field_148811_c;
/*  20:    */   public String field_148808_d;
/*  21:    */   public String field_148809_e;
/*  22:    */   public List field_148806_f;
/*  23:    */   public String field_148807_g;
/*  24:    */   public boolean field_148819_h;
/*  25:    */   public int field_148820_i;
/*  26:    */   public int field_148817_j;
/*  27:    */   public int field_148818_k;
/*  28:    */   public int field_148815_l;
/*  29: 29 */   public String field_148816_m = "";
/*  30: 30 */   public String field_148813_n = "§70";
/*  31: 31 */   public boolean field_148814_o = false;
/*  32:    */   private static final String __OBFID = "CL_00001166";
/*  33:    */   
/*  34:    */   public String func_148800_a()
/*  35:    */   {
/*  36: 36 */     return this.field_148811_c;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String func_148801_b()
/*  40:    */   {
/*  41: 41 */     return this.field_148810_b;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void func_148803_a(String p_148803_1_)
/*  45:    */   {
/*  46: 46 */     this.field_148810_b = p_148803_1_;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void func_148804_b(String p_148804_1_)
/*  50:    */   {
/*  51: 51 */     this.field_148811_c = p_148804_1_;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void func_148799_a(McoServer p_148799_1_)
/*  55:    */   {
/*  56: 56 */     this.field_148816_m = p_148799_1_.field_148816_m;
/*  57: 57 */     this.field_148815_l = p_148799_1_.field_148815_l;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static McoServer func_148802_a(JsonObject p_148802_0_)
/*  61:    */   {
/*  62: 62 */     McoServer var1 = new McoServer();
/*  63:    */     try
/*  64:    */     {
/*  65: 66 */       var1.field_148812_a = (!p_148802_0_.get("id").isJsonNull() ? p_148802_0_.get("id").getAsLong() : -1L);
/*  66: 67 */       var1.field_148810_b = (!p_148802_0_.get("name").isJsonNull() ? p_148802_0_.get("name").getAsString() : null);
/*  67: 68 */       var1.field_148811_c = (!p_148802_0_.get("motd").isJsonNull() ? p_148802_0_.get("motd").getAsString() : null);
/*  68: 69 */       var1.field_148808_d = (!p_148802_0_.get("state").isJsonNull() ? p_148802_0_.get("state").getAsString() : State.CLOSED.name());
/*  69: 70 */       var1.field_148809_e = (!p_148802_0_.get("owner").isJsonNull() ? p_148802_0_.get("owner").getAsString() : null);
/*  70: 72 */       if (p_148802_0_.get("invited").isJsonArray()) {
/*  71: 74 */         var1.field_148806_f = func_148798_a(p_148802_0_.get("invited").getAsJsonArray());
/*  72:    */       } else {
/*  73: 78 */         var1.field_148806_f = new ArrayList();
/*  74:    */       }
/*  75: 81 */       var1.field_148818_k = (!p_148802_0_.get("daysLeft").isJsonNull() ? p_148802_0_.get("daysLeft").getAsInt() : 0);
/*  76: 82 */       var1.field_148807_g = (!p_148802_0_.get("ip").isJsonNull() ? p_148802_0_.get("ip").getAsString() : null);
/*  77: 83 */       var1.field_148819_h = ((!p_148802_0_.get("expired").isJsonNull()) && (p_148802_0_.get("expired").getAsBoolean()));
/*  78: 84 */       var1.field_148820_i = (!p_148802_0_.get("difficulty").isJsonNull() ? p_148802_0_.get("difficulty").getAsInt() : 0);
/*  79: 85 */       var1.field_148817_j = (!p_148802_0_.get("gameMode").isJsonNull() ? p_148802_0_.get("gameMode").getAsInt() : 0);
/*  80:    */     }
/*  81:    */     catch (IllegalArgumentException localIllegalArgumentException) {}
/*  82: 92 */     return var1;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private static List func_148798_a(JsonArray p_148798_0_)
/*  86:    */   {
/*  87: 97 */     ArrayList var1 = new ArrayList();
/*  88: 98 */     Iterator var2 = p_148798_0_.iterator();
/*  89:100 */     while (var2.hasNext()) {
/*  90:102 */       var1.add(((JsonElement)var2.next()).getAsString());
/*  91:    */     }
/*  92:105 */     return var1;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static McoServer func_148805_c(String p_148805_0_)
/*  96:    */   {
/*  97:110 */     McoServer var1 = new McoServer();
/*  98:    */     try
/*  99:    */     {
/* 100:114 */       JsonParser var2 = new JsonParser();
/* 101:115 */       JsonObject var3 = var2.parse(p_148805_0_).getAsJsonObject();
/* 102:116 */       var1 = func_148802_a(var3);
/* 103:    */     }
/* 104:    */     catch (JsonIOException localJsonIOException) {}catch (JsonSyntaxException localJsonSyntaxException) {}
/* 105:127 */     return var1;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int hashCode()
/* 109:    */   {
/* 110:132 */     return new HashCodeBuilder(17, 37).append(this.field_148812_a).append(this.field_148810_b).append(this.field_148811_c).append(this.field_148808_d).append(this.field_148809_e).append(this.field_148819_h).toHashCode();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean equals(Object par1Obj)
/* 114:    */   {
/* 115:137 */     if (par1Obj == null) {
/* 116:139 */       return false;
/* 117:    */     }
/* 118:141 */     if (par1Obj == this) {
/* 119:143 */       return true;
/* 120:    */     }
/* 121:145 */     if (par1Obj.getClass() != getClass()) {
/* 122:147 */       return false;
/* 123:    */     }
/* 124:151 */     McoServer var2 = (McoServer)par1Obj;
/* 125:152 */     return new EqualsBuilder().append(this.field_148812_a, var2.field_148812_a).append(this.field_148810_b, var2.field_148810_b).append(this.field_148811_c, var2.field_148811_c).append(this.field_148808_d, var2.field_148808_d).append(this.field_148809_e, var2.field_148809_e).append(this.field_148819_h, var2.field_148819_h).isEquals();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static enum State
/* 129:    */   {
/* 130:158 */     CLOSED("CLOSED", 0),  OPEN("OPEN", 1),  ADMIN_LOCK("ADMIN_LOCK", 2);
/* 131:    */     
/* 132:162 */     private static final State[] $VALUES = { CLOSED, OPEN, ADMIN_LOCK };
/* 133:    */     private static final String __OBFID = "CL_00001167";
/* 134:    */     
/* 135:    */     private State(String p_i45485_1_, int p_i45485_2_) {}
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.McoServer
 * JD-Core Version:    0.7.0.1
 */