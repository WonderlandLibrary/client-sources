/*   1:    */ package net.minecraft.client.mco;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import com.google.common.collect.Sets;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.Comparator;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.Timer;
/*  12:    */ import java.util.TimerTask;
/*  13:    */ import net.minecraft.client.Minecraft;
/*  14:    */ import net.minecraft.util.Session;
/*  15:    */ import org.apache.logging.log4j.LogManager;
/*  16:    */ import org.apache.logging.log4j.Logger;
/*  17:    */ 
/*  18:    */ public class McoServerList
/*  19:    */ {
/*  20: 19 */   private static final Logger logger = ;
/*  21:    */   private volatile boolean field_148488_b;
/*  22: 21 */   private UpdateTask field_148489_c = new UpdateTask(null);
/*  23: 22 */   private Timer field_148486_d = new Timer();
/*  24: 23 */   private Set field_148487_e = Sets.newHashSet();
/*  25: 24 */   private List field_148484_f = Lists.newArrayList();
/*  26:    */   private int field_148485_g;
/*  27:    */   private boolean field_148492_h;
/*  28:    */   private Session field_148493_i;
/*  29:    */   private int field_148491_j;
/*  30:    */   private static final String __OBFID = "CL_00000803";
/*  31:    */   
/*  32:    */   public McoServerList()
/*  33:    */   {
/*  34: 33 */     this.field_148486_d.schedule(this.field_148489_c, 0L, 10000L);
/*  35: 34 */     this.field_148493_i = Minecraft.getMinecraft().getSession();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public synchronized void func_148475_a(Session p_148475_1_)
/*  39:    */   {
/*  40: 39 */     this.field_148493_i = p_148475_1_;
/*  41: 41 */     if (this.field_148488_b)
/*  42:    */     {
/*  43: 43 */       this.field_148488_b = false;
/*  44: 44 */       this.field_148489_c = new UpdateTask(null);
/*  45: 45 */       this.field_148486_d = new Timer();
/*  46: 46 */       this.field_148486_d.schedule(this.field_148489_c, 0L, 10000L);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public synchronized boolean func_148472_a()
/*  51:    */   {
/*  52: 52 */     return this.field_148492_h;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public synchronized void func_148479_b()
/*  56:    */   {
/*  57: 57 */     this.field_148492_h = false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public synchronized List func_148473_c()
/*  61:    */   {
/*  62: 62 */     return Lists.newArrayList(this.field_148484_f);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int func_148468_d()
/*  66:    */   {
/*  67: 67 */     return this.field_148485_g;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int func_148469_e()
/*  71:    */   {
/*  72: 72 */     return this.field_148491_j;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public synchronized void func_148476_f()
/*  76:    */   {
/*  77: 77 */     this.field_148488_b = true;
/*  78: 78 */     this.field_148489_c.cancel();
/*  79: 79 */     this.field_148486_d.cancel();
/*  80:    */   }
/*  81:    */   
/*  82:    */   private synchronized void func_148474_a(List p_148474_1_)
/*  83:    */   {
/*  84: 84 */     int var2 = 0;
/*  85: 85 */     Iterator var3 = this.field_148487_e.iterator();
/*  86: 87 */     while (var3.hasNext())
/*  87:    */     {
/*  88: 89 */       McoServer var4 = (McoServer)var3.next();
/*  89: 91 */       if (p_148474_1_.remove(var4)) {
/*  90: 93 */         var2++;
/*  91:    */       }
/*  92:    */     }
/*  93: 97 */     if (var2 == 0) {
/*  94: 99 */       this.field_148487_e.clear();
/*  95:    */     }
/*  96:102 */     this.field_148484_f = p_148474_1_;
/*  97:103 */     this.field_148492_h = true;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public synchronized void func_148470_a(McoServer p_148470_1_)
/* 101:    */   {
/* 102:108 */     this.field_148484_f.remove(p_148470_1_);
/* 103:109 */     this.field_148487_e.add(p_148470_1_);
/* 104:    */   }
/* 105:    */   
/* 106:    */   private void func_148471_a(int p_148471_1_)
/* 107:    */   {
/* 108:114 */     this.field_148485_g = p_148471_1_;
/* 109:    */   }
/* 110:    */   
/* 111:    */   class UpdateTask
/* 112:    */     extends TimerTask
/* 113:    */   {
/* 114:    */     private McoClient field_148498_b;
/* 115:    */     private static final String __OBFID = "CL_00000805";
/* 116:    */     
/* 117:    */     private UpdateTask() {}
/* 118:    */     
/* 119:    */     public void run()
/* 120:    */     {
/* 121:126 */       if (!McoServerList.this.field_148488_b)
/* 122:    */       {
/* 123:128 */         func_148496_c();
/* 124:129 */         func_148494_a();
/* 125:130 */         func_148495_b();
/* 126:    */       }
/* 127:    */     }
/* 128:    */     
/* 129:    */     private void func_148494_a()
/* 130:    */     {
/* 131:    */       try
/* 132:    */       {
/* 133:138 */         if (McoServerList.this.field_148493_i != null)
/* 134:    */         {
/* 135:140 */           this.field_148498_b = new McoClient(McoServerList.this.field_148493_i.getSessionID(), McoServerList.this.field_148493_i.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 136:141 */           List var1 = this.field_148498_b.func_148703_a().field_148772_a;
/* 137:143 */           if (var1 != null)
/* 138:    */           {
/* 139:145 */             func_148497_a(var1);
/* 140:146 */             McoServerList.this.func_148474_a(var1);
/* 141:    */           }
/* 142:    */         }
/* 143:    */       }
/* 144:    */       catch (ExceptionMcoService var2)
/* 145:    */       {
/* 146:152 */         McoServerList.logger.error("Couldn't get server list", var2);
/* 147:    */       }
/* 148:    */       catch (IOException var3)
/* 149:    */       {
/* 150:156 */         McoServerList.logger.error("Couldn't parse response from server getting list");
/* 151:    */       }
/* 152:    */     }
/* 153:    */     
/* 154:    */     private void func_148495_b()
/* 155:    */     {
/* 156:    */       try
/* 157:    */       {
/* 158:164 */         if (McoServerList.this.field_148493_i != null)
/* 159:    */         {
/* 160:166 */           int var1 = this.field_148498_b.func_148701_f();
/* 161:167 */           McoServerList.this.func_148471_a(var1);
/* 162:    */         }
/* 163:    */       }
/* 164:    */       catch (ExceptionMcoService var2)
/* 165:    */       {
/* 166:172 */         McoServerList.logger.error("Couldn't get pending invite count", var2);
/* 167:    */       }
/* 168:    */     }
/* 169:    */     
/* 170:    */     private void func_148496_c()
/* 171:    */     {
/* 172:    */       try
/* 173:    */       {
/* 174:180 */         if (McoServerList.this.field_148493_i != null)
/* 175:    */         {
/* 176:182 */           McoClient var1 = new McoClient(McoServerList.this.field_148493_i.getSessionID(), McoServerList.this.field_148493_i.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 177:183 */           McoServerList.this.field_148491_j = var1.func_148702_d();
/* 178:    */         }
/* 179:    */       }
/* 180:    */       catch (ExceptionMcoService var2)
/* 181:    */       {
/* 182:188 */         McoServerList.logger.error("Couldn't get token count", var2);
/* 183:189 */         McoServerList.this.field_148491_j = 0;
/* 184:    */       }
/* 185:    */     }
/* 186:    */     
/* 187:    */     private void func_148497_a(List p_148497_1_)
/* 188:    */     {
/* 189:195 */       Collections.sort(p_148497_1_, new Comparator(McoServerList.this.field_148493_i.getUsername(), null));
/* 190:    */     }
/* 191:    */     
/* 192:    */     UpdateTask(Object par2McoServerListEmptyAnon)
/* 193:    */     {
/* 194:200 */       this();
/* 195:    */     }
/* 196:    */     
/* 197:    */     class Comparator
/* 198:    */       implements Comparator
/* 199:    */     {
/* 200:    */       private final String field_148504_b;
/* 201:    */       private static final String __OBFID = "CL_00000806";
/* 202:    */       
/* 203:    */       private Comparator(String par2Str)
/* 204:    */       {
/* 205:210 */         this.field_148504_b = par2Str;
/* 206:    */       }
/* 207:    */       
/* 208:    */       public int compare(McoServer p_148503_1_, McoServer p_148503_2_)
/* 209:    */       {
/* 210:215 */         if (p_148503_1_.field_148809_e.equals(p_148503_2_.field_148809_e)) {
/* 211:217 */           return p_148503_1_.field_148812_a > p_148503_2_.field_148812_a ? -1 : p_148503_1_.field_148812_a < p_148503_2_.field_148812_a ? 1 : 0;
/* 212:    */         }
/* 213:219 */         if (p_148503_1_.field_148809_e.equals(this.field_148504_b)) {
/* 214:221 */           return -1;
/* 215:    */         }
/* 216:223 */         if (p_148503_2_.field_148809_e.equals(this.field_148504_b)) {
/* 217:225 */           return 1;
/* 218:    */         }
/* 219:229 */         if ((p_148503_1_.field_148808_d.equals("CLOSED")) || (p_148503_2_.field_148808_d.equals("CLOSED")))
/* 220:    */         {
/* 221:231 */           if (p_148503_1_.field_148808_d.equals("CLOSED")) {
/* 222:233 */             return 1;
/* 223:    */           }
/* 224:236 */           if (p_148503_2_.field_148808_d.equals("CLOSED")) {
/* 225:238 */             return 0;
/* 226:    */           }
/* 227:    */         }
/* 228:242 */         return p_148503_1_.field_148812_a > p_148503_2_.field_148812_a ? -1 : p_148503_1_.field_148812_a < p_148503_2_.field_148812_a ? 1 : 0;
/* 229:    */       }
/* 230:    */       
/* 231:    */       public int compare(Object par1Obj, Object par2Obj)
/* 232:    */       {
/* 233:248 */         return compare((McoServer)par1Obj, (McoServer)par2Obj);
/* 234:    */       }
/* 235:    */       
/* 236:    */       Comparator(String par2Str, Object par3McoServerListEmptyAnon)
/* 237:    */       {
/* 238:253 */         this(par2Str);
/* 239:    */       }
/* 240:    */     }
/* 241:    */   }
/* 242:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.McoServerList
 * JD-Core Version:    0.7.0.1
 */