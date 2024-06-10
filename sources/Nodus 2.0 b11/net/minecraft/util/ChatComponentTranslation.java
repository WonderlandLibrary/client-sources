/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Iterators;
/*   4:    */ import com.google.common.collect.Lists;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.IllegalFormatException;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.regex.Matcher;
/*  10:    */ import java.util.regex.Pattern;
/*  11:    */ 
/*  12:    */ public class ChatComponentTranslation
/*  13:    */   extends ChatComponentStyle
/*  14:    */ {
/*  15:    */   private final String key;
/*  16:    */   private final Object[] formatArgs;
/*  17: 16 */   private final Object syncLock = new Object();
/*  18: 17 */   private long lastTranslationUpdateTimeInMilliseconds = -1L;
/*  19: 24 */   List children = Lists.newArrayList();
/*  20: 25 */   public static final Pattern stringVariablePattern = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
/*  21:    */   private static final String __OBFID = "CL_00001270";
/*  22:    */   
/*  23:    */   public ChatComponentTranslation(String p_i45160_1_, Object... p_i45160_2_)
/*  24:    */   {
/*  25: 30 */     this.key = p_i45160_1_;
/*  26: 31 */     this.formatArgs = p_i45160_2_;
/*  27: 32 */     Object[] var3 = p_i45160_2_;
/*  28: 33 */     int var4 = p_i45160_2_.length;
/*  29: 35 */     for (int var5 = 0; var5 < var4; var5++)
/*  30:    */     {
/*  31: 37 */       Object var6 = var3[var5];
/*  32: 39 */       if ((var6 instanceof IChatComponent)) {
/*  33: 41 */         ((IChatComponent)var6).getChatStyle().setParentStyle(getChatStyle());
/*  34:    */       }
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   synchronized void ensureInitialized()
/*  39:    */   {
/*  40: 51 */     Object var1 = this.syncLock;
/*  41: 53 */     synchronized (this.syncLock)
/*  42:    */     {
/*  43: 55 */       long var2 = StatCollector.getLastTranslationUpdateTimeInMilliseconds();
/*  44: 57 */       if (var2 == this.lastTranslationUpdateTimeInMilliseconds) {
/*  45: 59 */         return;
/*  46:    */       }
/*  47: 62 */       this.lastTranslationUpdateTimeInMilliseconds = var2;
/*  48: 63 */       this.children.clear();
/*  49:    */     }
/*  50:    */     try
/*  51:    */     {
/*  52: 68 */       initializeFromFormat(StatCollector.translateToLocal(this.key));
/*  53:    */     }
/*  54:    */     catch (ChatComponentTranslationFormatException var6)
/*  55:    */     {
/*  56: 72 */       this.children.clear();
/*  57:    */       try
/*  58:    */       {
/*  59: 76 */         initializeFromFormat(StatCollector.translateToFallback(this.key));
/*  60:    */       }
/*  61:    */       catch (ChatComponentTranslationFormatException var5)
/*  62:    */       {
/*  63: 80 */         throw var6;
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected void initializeFromFormat(String p_150269_1_)
/*  69:    */   {
/*  70: 90 */     boolean var2 = false;
/*  71: 91 */     Matcher var3 = stringVariablePattern.matcher(p_150269_1_);
/*  72: 92 */     int var4 = 0;
/*  73: 93 */     int var5 = 0;
/*  74:    */     try
/*  75:    */     {
/*  76:    */       int var7;
/*  77: 99 */       for (; var3.find(var5); var5 = var7)
/*  78:    */       {
/*  79:101 */         int var6 = var3.start();
/*  80:102 */         var7 = var3.end();
/*  81:104 */         if (var6 > var5)
/*  82:    */         {
/*  83:106 */           ChatComponentText var8 = new ChatComponentText(String.format(p_150269_1_.substring(var5, var6), new Object[0]));
/*  84:107 */           var8.getChatStyle().setParentStyle(getChatStyle());
/*  85:108 */           this.children.add(var8);
/*  86:    */         }
/*  87:111 */         String var14 = var3.group(2);
/*  88:112 */         String var9 = p_150269_1_.substring(var6, var7);
/*  89:114 */         if (("%".equals(var14)) && ("%%".equals(var9)))
/*  90:    */         {
/*  91:116 */           ChatComponentText var15 = new ChatComponentText("%");
/*  92:117 */           var15.getChatStyle().setParentStyle(getChatStyle());
/*  93:118 */           this.children.add(var15);
/*  94:    */         }
/*  95:    */         else
/*  96:    */         {
/*  97:122 */           if (!"s".equals(var14)) {
/*  98:124 */             throw new ChatComponentTranslationFormatException(this, "Unsupported format: '" + var9 + "'");
/*  99:    */           }
/* 100:127 */           String var10 = var3.group(1);
/* 101:128 */           int var11 = var10 != null ? Integer.parseInt(var10) - 1 : var4++;
/* 102:129 */           this.children.add(getFormatArgumentAsComponent(var11));
/* 103:    */         }
/* 104:    */       }
/* 105:133 */       if (var5 < p_150269_1_.length())
/* 106:    */       {
/* 107:135 */         ChatComponentText var13 = new ChatComponentText(String.format(p_150269_1_.substring(var5), new Object[0]));
/* 108:136 */         var13.getChatStyle().setParentStyle(getChatStyle());
/* 109:137 */         this.children.add(var13);
/* 110:    */       }
/* 111:    */     }
/* 112:    */     catch (IllegalFormatException var12)
/* 113:    */     {
/* 114:142 */       throw new ChatComponentTranslationFormatException(this, var12);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private IChatComponent getFormatArgumentAsComponent(int p_150272_1_)
/* 119:    */   {
/* 120:148 */     if (p_150272_1_ >= this.formatArgs.length) {
/* 121:150 */       throw new ChatComponentTranslationFormatException(this, p_150272_1_);
/* 122:    */     }
/* 123:154 */     Object var2 = this.formatArgs[p_150272_1_];
/* 124:    */     Object var3;
/* 125:    */     Object var3;
/* 126:157 */     if ((var2 instanceof IChatComponent))
/* 127:    */     {
/* 128:159 */       var3 = (IChatComponent)var2;
/* 129:    */     }
/* 130:    */     else
/* 131:    */     {
/* 132:163 */       var3 = new ChatComponentText(var2.toString());
/* 133:164 */       ((IChatComponent)var3).getChatStyle().setParentStyle(getChatStyle());
/* 134:    */     }
/* 135:167 */     return (IChatComponent)var3;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public IChatComponent setChatStyle(ChatStyle p_150255_1_)
/* 139:    */   {
/* 140:173 */     super.setChatStyle(p_150255_1_);
/* 141:174 */     Object[] var2 = this.formatArgs;
/* 142:175 */     int var3 = var2.length;
/* 143:177 */     for (int var4 = 0; var4 < var3; var4++)
/* 144:    */     {
/* 145:179 */       Object var5 = var2[var4];
/* 146:181 */       if ((var5 instanceof IChatComponent)) {
/* 147:183 */         ((IChatComponent)var5).getChatStyle().setParentStyle(getChatStyle());
/* 148:    */       }
/* 149:    */     }
/* 150:187 */     if (this.lastTranslationUpdateTimeInMilliseconds > -1L)
/* 151:    */     {
/* 152:189 */       Iterator var6 = this.children.iterator();
/* 153:191 */       while (var6.hasNext())
/* 154:    */       {
/* 155:193 */         IChatComponent var7 = (IChatComponent)var6.next();
/* 156:194 */         var7.getChatStyle().setParentStyle(p_150255_1_);
/* 157:    */       }
/* 158:    */     }
/* 159:198 */     return this;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Iterator iterator()
/* 163:    */   {
/* 164:203 */     ensureInitialized();
/* 165:204 */     return Iterators.concat(createDeepCopyIterator(this.children), createDeepCopyIterator(this.siblings));
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getUnformattedTextForChat()
/* 169:    */   {
/* 170:213 */     ensureInitialized();
/* 171:214 */     StringBuilder var1 = new StringBuilder();
/* 172:215 */     Iterator var2 = this.children.iterator();
/* 173:217 */     while (var2.hasNext())
/* 174:    */     {
/* 175:219 */       IChatComponent var3 = (IChatComponent)var2.next();
/* 176:220 */       var1.append(var3.getUnformattedTextForChat());
/* 177:    */     }
/* 178:223 */     return var1.toString();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public ChatComponentTranslation createCopy()
/* 182:    */   {
/* 183:231 */     Object[] var1 = new Object[this.formatArgs.length];
/* 184:233 */     for (int var2 = 0; var2 < this.formatArgs.length; var2++) {
/* 185:235 */       if ((this.formatArgs[var2] instanceof IChatComponent)) {
/* 186:237 */         var1[var2] = ((IChatComponent)this.formatArgs[var2]).createCopy();
/* 187:    */       } else {
/* 188:241 */         var1[var2] = this.formatArgs[var2];
/* 189:    */       }
/* 190:    */     }
/* 191:245 */     ChatComponentTranslation var5 = new ChatComponentTranslation(this.key, var1);
/* 192:246 */     var5.setChatStyle(getChatStyle().createShallowCopy());
/* 193:247 */     Iterator var3 = getSiblings().iterator();
/* 194:249 */     while (var3.hasNext())
/* 195:    */     {
/* 196:251 */       IChatComponent var4 = (IChatComponent)var3.next();
/* 197:252 */       var5.appendSibling(var4.createCopy());
/* 198:    */     }
/* 199:255 */     return var5;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean equals(Object par1Obj)
/* 203:    */   {
/* 204:260 */     if (this == par1Obj) {
/* 205:262 */       return true;
/* 206:    */     }
/* 207:264 */     if (!(par1Obj instanceof ChatComponentTranslation)) {
/* 208:266 */       return false;
/* 209:    */     }
/* 210:270 */     ChatComponentTranslation var2 = (ChatComponentTranslation)par1Obj;
/* 211:271 */     return (Arrays.equals(this.formatArgs, var2.formatArgs)) && (this.key.equals(var2.key)) && (super.equals(par1Obj));
/* 212:    */   }
/* 213:    */   
/* 214:    */   public int hashCode()
/* 215:    */   {
/* 216:277 */     int var1 = super.hashCode();
/* 217:278 */     var1 = 31 * var1 + this.key.hashCode();
/* 218:279 */     var1 = 31 * var1 + Arrays.hashCode(this.formatArgs);
/* 219:280 */     return var1;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String toString()
/* 223:    */   {
/* 224:285 */     return "TranslatableComponent{key='" + this.key + '\'' + ", args=" + Arrays.toString(this.formatArgs) + ", siblings=" + this.siblings + ", style=" + getChatStyle() + '}';
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getKey()
/* 228:    */   {
/* 229:290 */     return this.key;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public Object[] getFormatArgs()
/* 233:    */   {
/* 234:295 */     return this.formatArgs;
/* 235:    */   }
/* 236:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ChatComponentTranslation
 * JD-Core Version:    0.7.0.1
 */