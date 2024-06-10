/*   1:    */ package net.minecraft.client.mco;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.net.Proxy;
/*   6:    */ import java.net.URLEncoder;
/*   7:    */ 
/*   8:    */ public class McoClient
/*   9:    */ {
/*  10:    */   private final String field_148719_a;
/*  11:    */   private final String field_148717_b;
/*  12:    */   private final String field_148718_c;
/*  13:    */   private final Proxy field_148715_d;
/*  14: 14 */   private static String field_148716_e = "https://mcoapi.minecraft.net/";
/*  15:    */   private static final String __OBFID = "CL_00001156";
/*  16:    */   
/*  17:    */   public McoClient(String p_i45484_1_, String p_i45484_2_, String p_i45484_3_, Proxy p_i45484_4_)
/*  18:    */   {
/*  19: 19 */     this.field_148719_a = p_i45484_1_;
/*  20: 20 */     this.field_148717_b = p_i45484_2_;
/*  21: 21 */     this.field_148718_c = p_i45484_3_;
/*  22: 22 */     this.field_148715_d = p_i45484_4_;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public ValueObjectList func_148703_a()
/*  26:    */     throws ExceptionMcoService, IOException
/*  27:    */   {
/*  28: 27 */     String var1 = func_148713_a(Request.func_148666_a(field_148716_e + "worlds"));
/*  29: 28 */     return ValueObjectList.func_148771_a(var1);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public McoServer func_148709_a(long p_148709_1_)
/*  33:    */     throws ExceptionMcoService, IOException
/*  34:    */   {
/*  35: 33 */     String var3 = func_148713_a(Request.func_148666_a(field_148716_e + "worlds" + "/$ID".replace("$ID", String.valueOf(p_148709_1_))));
/*  36: 34 */     return McoServer.func_148805_c(var3);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public McoServerAddress func_148688_b(long p_148688_1_)
/*  40:    */     throws Exception
/*  41:    */   {
/*  42: 39 */     String var3 = field_148716_e + "worlds" + "/$ID/join".replace("$ID", new StringBuilder().append(p_148688_1_).toString());
/*  43: 40 */     String var4 = func_148713_a(Request.func_148670_a(var3, 5000, 30000));
/*  44: 41 */     return McoServerAddress.func_148769_a(var4);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void func_148707_a(String p_148707_1_, String p_148707_2_)
/*  48:    */     throws ExceptionMcoService, UnsupportedEncodingException
/*  49:    */   {
/*  50: 46 */     StringBuilder var3 = new StringBuilder();
/*  51: 47 */     var3.append(field_148716_e).append("worlds");
/*  52: 48 */     var3.append("?name=").append(func_148711_c(p_148707_1_));
/*  53: 49 */     var3.append("&template=").append(p_148707_2_);
/*  54: 50 */     func_148713_a(Request.func_148661_a(var3.toString(), "", 5000, 30000));
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Boolean func_148687_b()
/*  58:    */     throws ExceptionMcoService, IOException
/*  59:    */   {
/*  60: 55 */     String var1 = field_148716_e + "mco" + "/available";
/*  61: 56 */     String var2 = func_148713_a(Request.func_148666_a(var1));
/*  62: 57 */     return Boolean.valueOf(var2);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Boolean func_148695_c()
/*  66:    */     throws ExceptionMcoService, IOException
/*  67:    */   {
/*  68: 62 */     String var1 = field_148716_e + "mco" + "/client/outdated";
/*  69: 63 */     String var2 = func_148713_a(Request.func_148666_a(var1));
/*  70: 64 */     return Boolean.valueOf(var2);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int func_148702_d()
/*  74:    */     throws ExceptionMcoService
/*  75:    */   {
/*  76: 69 */     String var1 = field_148716_e + "payments" + "/unused";
/*  77: 70 */     String var2 = func_148713_a(Request.func_148666_a(var1));
/*  78: 71 */     return Integer.valueOf(var2).intValue();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void func_148694_a(long p_148694_1_, String p_148694_3_)
/*  82:    */     throws ExceptionMcoService
/*  83:    */   {
/*  84: 76 */     String var4 = field_148716_e + "invites" + "/$WORLD_ID/invite/$USER_NAME".replace("$WORLD_ID", String.valueOf(p_148694_1_)).replace("$USER_NAME", p_148694_3_);
/*  85: 77 */     func_148713_a(Request.func_148663_b(var4));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void func_148698_c(long p_148698_1_)
/*  89:    */     throws ExceptionMcoService
/*  90:    */   {
/*  91: 82 */     String var3 = field_148716_e + "invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_148698_1_));
/*  92: 83 */     func_148713_a(Request.func_148663_b(var3));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public McoServer func_148697_b(long p_148697_1_, String p_148697_3_)
/*  96:    */     throws ExceptionMcoService, IOException
/*  97:    */   {
/*  98: 88 */     String var4 = field_148716_e + "invites" + "/$WORLD_ID/invite/$USER_NAME".replace("$WORLD_ID", String.valueOf(p_148697_1_)).replace("$USER_NAME", p_148697_3_);
/*  99: 89 */     String var5 = func_148713_a(Request.func_148672_c(var4, ""));
/* 100: 90 */     return McoServer.func_148805_c(var5);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public BackupList func_148704_d(long p_148704_1_)
/* 104:    */     throws ExceptionMcoService
/* 105:    */   {
/* 106: 95 */     String var3 = field_148716_e + "worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(p_148704_1_));
/* 107: 96 */     String var4 = func_148713_a(Request.func_148666_a(var3));
/* 108: 97 */     return BackupList.func_148796_a(var4);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void func_148689_a(long p_148689_1_, String p_148689_3_, String p_148689_4_, int p_148689_5_, int p_148689_6_)
/* 112:    */     throws ExceptionMcoService, UnsupportedEncodingException
/* 113:    */   {
/* 114:102 */     StringBuilder var7 = new StringBuilder();
/* 115:103 */     var7.append(field_148716_e).append("worlds").append("/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_148689_1_)));
/* 116:104 */     var7.append("?name=").append(func_148711_c(p_148689_3_));
/* 117:106 */     if ((p_148689_4_ != null) && (!p_148689_4_.trim().equals(""))) {
/* 118:108 */       var7.append("&motd=").append(func_148711_c(p_148689_4_));
/* 119:    */     } else {
/* 120:112 */       var7.append("&motd=");
/* 121:    */     }
/* 122:115 */     var7.append("&difficulty=").append(p_148689_5_).append("&gameMode=").append(p_148689_6_);
/* 123:116 */     func_148713_a(Request.func_148668_d(var7.toString(), ""));
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void func_148712_c(long p_148712_1_, String p_148712_3_)
/* 127:    */     throws ExceptionMcoService
/* 128:    */   {
/* 129:121 */     String var4 = field_148716_e + "worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(p_148712_1_)) + "?backupId=" + p_148712_3_;
/* 130:122 */     func_148713_a(Request.func_148669_b(var4, "", 40000, 40000));
/* 131:    */   }
/* 132:    */   
/* 133:    */   public WorldTemplateList func_148693_e()
/* 134:    */     throws ExceptionMcoService
/* 135:    */   {
/* 136:127 */     String var1 = field_148716_e + "worlds" + "/templates";
/* 137:128 */     String var2 = func_148713_a(Request.func_148666_a(var1));
/* 138:129 */     return WorldTemplateList.func_148781_a(var2);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Boolean func_148692_e(long p_148692_1_)
/* 142:    */     throws ExceptionMcoService, IOException
/* 143:    */   {
/* 144:134 */     String var3 = field_148716_e + "worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(p_148692_1_));
/* 145:135 */     String var4 = func_148713_a(Request.func_148668_d(var3, ""));
/* 146:136 */     return Boolean.valueOf(var4);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Boolean func_148700_f(long p_148700_1_)
/* 150:    */     throws ExceptionMcoService, IOException
/* 151:    */   {
/* 152:141 */     String var3 = field_148716_e + "worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(p_148700_1_));
/* 153:142 */     String var4 = func_148713_a(Request.func_148668_d(var3, ""));
/* 154:143 */     return Boolean.valueOf(var4);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Boolean func_148699_d(long p_148699_1_, String p_148699_3_)
/* 158:    */     throws ExceptionMcoService, UnsupportedEncodingException
/* 159:    */   {
/* 160:148 */     StringBuilder var4 = new StringBuilder();
/* 161:149 */     var4.append(field_148716_e).append("worlds").append("/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(p_148699_1_)));
/* 162:151 */     if ((p_148699_3_ != null) && (p_148699_3_.length() > 0)) {
/* 163:153 */       var4.append("?seed=").append(func_148711_c(p_148699_3_));
/* 164:    */     }
/* 165:156 */     String var5 = func_148713_a(Request.func_148669_b(var4.toString(), "", 30000, 80000));
/* 166:157 */     return Boolean.valueOf(var5);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Boolean func_148696_e(long p_148696_1_, String p_148696_3_)
/* 170:    */     throws ExceptionMcoService
/* 171:    */   {
/* 172:162 */     StringBuilder var4 = new StringBuilder();
/* 173:163 */     var4.append(field_148716_e).append("worlds").append("/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(p_148696_1_)));
/* 174:165 */     if (p_148696_3_ != null) {
/* 175:167 */       var4.append("?template=").append(p_148696_3_);
/* 176:    */     }
/* 177:170 */     String var5 = func_148713_a(Request.func_148669_b(var4.toString(), "", 30000, 80000));
/* 178:171 */     return Boolean.valueOf(var5);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public ValueObjectSubscription func_148705_g(long p_148705_1_)
/* 182:    */     throws ExceptionMcoService, IOException
/* 183:    */   {
/* 184:176 */     String var3 = func_148713_a(Request.func_148666_a(field_148716_e + "subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_148705_1_))));
/* 185:177 */     return ValueObjectSubscription.func_148788_a(var3);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int func_148701_f()
/* 189:    */     throws ExceptionMcoService
/* 190:    */   {
/* 191:182 */     String var1 = func_148713_a(Request.func_148666_a(field_148716_e + "invites" + "/count/pending"));
/* 192:183 */     return Integer.parseInt(var1);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public PendingInvitesList func_148710_g()
/* 196:    */     throws ExceptionMcoService
/* 197:    */   {
/* 198:188 */     String var1 = func_148713_a(Request.func_148666_a(field_148716_e + "invites" + "/pending"));
/* 199:189 */     return PendingInvitesList.func_148767_a(var1);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void func_148691_a(String p_148691_1_)
/* 203:    */     throws ExceptionMcoService
/* 204:    */   {
/* 205:194 */     func_148713_a(Request.func_148668_d(field_148716_e + "invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", p_148691_1_), ""));
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String func_148708_h(long p_148708_1_)
/* 209:    */     throws ExceptionMcoService
/* 210:    */   {
/* 211:199 */     return func_148713_a(Request.func_148666_a(field_148716_e + "worlds" + "/$WORLD_ID/backups/download".replace("$WORLD_ID", String.valueOf(p_148708_1_))));
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void func_148706_b(String p_148706_1_)
/* 215:    */     throws ExceptionMcoService
/* 216:    */   {
/* 217:204 */     func_148713_a(Request.func_148668_d(field_148716_e + "invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", p_148706_1_), ""));
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void func_148714_h()
/* 221:    */     throws ExceptionMcoService
/* 222:    */   {
/* 223:209 */     func_148713_a(Request.func_148672_c(field_148716_e + "mco" + "/tos/agreed", ""));
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String func_148690_i()
/* 227:    */     throws ExceptionMcoService
/* 228:    */   {
/* 229:214 */     return func_148713_a(Request.func_148666_a(field_148716_e + "mco" + "/stat"));
/* 230:    */   }
/* 231:    */   
/* 232:    */   private String func_148711_c(String p_148711_1_)
/* 233:    */     throws UnsupportedEncodingException
/* 234:    */   {
/* 235:219 */     return URLEncoder.encode(p_148711_1_, "UTF-8");
/* 236:    */   }
/* 237:    */   
/* 238:    */   private String func_148713_a(Request p_148713_1_)
/* 239:    */     throws ExceptionMcoService
/* 240:    */   {
/* 241:224 */     McoClientConfig.func_148684_a(this.field_148715_d);
/* 242:225 */     p_148713_1_.func_148665_a("sid", this.field_148719_a);
/* 243:226 */     p_148713_1_.func_148665_a("user", this.field_148717_b);
/* 244:227 */     p_148713_1_.func_148665_a("version", this.field_148718_c);
/* 245:    */     try
/* 246:    */     {
/* 247:231 */       int var2 = p_148713_1_.func_148671_a();
/* 248:233 */       if (var2 == 503)
/* 249:    */       {
/* 250:235 */         int var3 = p_148713_1_.func_148664_b();
/* 251:236 */         throw new ExceptionRetryCall(var3);
/* 252:    */       }
/* 253:238 */       if ((var2 >= 200) && (var2 < 300)) {
/* 254:240 */         return p_148713_1_.func_148659_d();
/* 255:    */       }
/* 256:244 */       throw new ExceptionMcoService(p_148713_1_.func_148671_a(), p_148713_1_.func_148659_d(), p_148713_1_.func_148673_g());
/* 257:    */     }
/* 258:    */     catch (ExceptionMcoHttp var4)
/* 259:    */     {
/* 260:249 */       throw new ExceptionMcoService(500, "Server not available!", -1);
/* 261:    */     }
/* 262:    */   }
/* 263:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.McoClient
 * JD-Core Version:    0.7.0.1
 */