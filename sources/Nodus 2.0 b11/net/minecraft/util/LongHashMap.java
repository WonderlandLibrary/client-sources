/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ public class LongHashMap
/*   4:    */ {
/*   5:  6 */   private transient Entry[] hashArray = new Entry[1024];
/*   6:    */   private transient int numHashElements;
/*   7:    */   private int capacity;
/*   8:    */   private final float percentUseable;
/*   9:    */   private volatile transient int modCount;
/*  10:    */   private static final String __OBFID = "CL_00001492";
/*  11:    */   
/*  12:    */   public LongHashMap()
/*  13:    */   {
/*  14: 27 */     this.capacity = ((int)(0.75F * this.hashArray.length));
/*  15: 28 */     this.percentUseable = 0.75F;
/*  16:    */   }
/*  17:    */   
/*  18:    */   private static int getHashedKey(long par0)
/*  19:    */   {
/*  20: 36 */     return (int)(par0 ^ par0 >>> 27);
/*  21:    */   }
/*  22:    */   
/*  23:    */   private static int hash(int par0)
/*  24:    */   {
/*  25: 44 */     par0 ^= par0 >>> 20 ^ par0 >>> 12;
/*  26: 45 */     return par0 ^ par0 >>> 7 ^ par0 >>> 4;
/*  27:    */   }
/*  28:    */   
/*  29:    */   private static int getHashIndex(int par0, int par1)
/*  30:    */   {
/*  31: 53 */     return par0 & par1 - 1;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getNumHashElements()
/*  35:    */   {
/*  36: 58 */     return this.numHashElements;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Object getValueByKey(long par1)
/*  40:    */   {
/*  41: 66 */     int var3 = getHashedKey(par1);
/*  42: 68 */     for (Entry var4 = this.hashArray[getHashIndex(var3, this.hashArray.length)]; var4 != null; var4 = var4.nextEntry) {
/*  43: 70 */       if (var4.key == par1) {
/*  44: 72 */         return var4.value;
/*  45:    */       }
/*  46:    */     }
/*  47: 76 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean containsItem(long par1)
/*  51:    */   {
/*  52: 81 */     return getEntry(par1) != null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   final Entry getEntry(long par1)
/*  56:    */   {
/*  57: 86 */     int var3 = getHashedKey(par1);
/*  58: 88 */     for (Entry var4 = this.hashArray[getHashIndex(var3, this.hashArray.length)]; var4 != null; var4 = var4.nextEntry) {
/*  59: 90 */       if (var4.key == par1) {
/*  60: 92 */         return var4;
/*  61:    */       }
/*  62:    */     }
/*  63: 96 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void add(long par1, Object par3Obj)
/*  67:    */   {
/*  68:104 */     int var4 = getHashedKey(par1);
/*  69:105 */     int var5 = getHashIndex(var4, this.hashArray.length);
/*  70:107 */     for (Entry var6 = this.hashArray[var5]; var6 != null; var6 = var6.nextEntry) {
/*  71:109 */       if (var6.key == par1)
/*  72:    */       {
/*  73:111 */         var6.value = par3Obj;
/*  74:112 */         return;
/*  75:    */       }
/*  76:    */     }
/*  77:116 */     this.modCount += 1;
/*  78:117 */     createKey(var4, par1, par3Obj, var5);
/*  79:    */   }
/*  80:    */   
/*  81:    */   private void resizeTable(int par1)
/*  82:    */   {
/*  83:125 */     Entry[] var2 = this.hashArray;
/*  84:126 */     int var3 = var2.length;
/*  85:128 */     if (var3 == 1073741824)
/*  86:    */     {
/*  87:130 */       this.capacity = 2147483647;
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:134 */       Entry[] var4 = new Entry[par1];
/*  92:135 */       copyHashTableTo(var4);
/*  93:136 */       this.hashArray = var4;
/*  94:137 */       float var10001 = par1;
/*  95:138 */       getClass();
/*  96:139 */       this.capacity = ((int)(var10001 * 0.75F));
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   private void copyHashTableTo(Entry[] par1ArrayOfLongHashMapEntry)
/* 101:    */   {
/* 102:148 */     Entry[] var2 = this.hashArray;
/* 103:149 */     int var3 = par1ArrayOfLongHashMapEntry.length;
/* 104:151 */     for (int var4 = 0; var4 < var2.length; var4++)
/* 105:    */     {
/* 106:153 */       Entry var5 = var2[var4];
/* 107:155 */       if (var5 != null)
/* 108:    */       {
/* 109:157 */         var2[var4] = null;
/* 110:    */         Entry var6;
/* 111:    */         do
/* 112:    */         {
/* 113:162 */           var6 = var5.nextEntry;
/* 114:163 */           int var7 = getHashIndex(var5.hash, var3);
/* 115:164 */           var5.nextEntry = par1ArrayOfLongHashMapEntry[var7];
/* 116:165 */           par1ArrayOfLongHashMapEntry[var7] = var5;
/* 117:166 */           var5 = var6;
/* 118:168 */         } while (var6 != null);
/* 119:    */       }
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Object remove(long par1)
/* 124:    */   {
/* 125:178 */     Entry var3 = removeKey(par1);
/* 126:179 */     return var3 == null ? null : var3.value;
/* 127:    */   }
/* 128:    */   
/* 129:    */   final Entry removeKey(long par1)
/* 130:    */   {
/* 131:187 */     int var3 = getHashedKey(par1);
/* 132:188 */     int var4 = getHashIndex(var3, this.hashArray.length);
/* 133:189 */     Entry var5 = this.hashArray[var4];
/* 134:    */     Entry var7;
/* 135:193 */     for (Entry var6 = var5; var6 != null; var6 = var7)
/* 136:    */     {
/* 137:195 */       var7 = var6.nextEntry;
/* 138:197 */       if (var6.key == par1)
/* 139:    */       {
/* 140:199 */         this.modCount += 1;
/* 141:200 */         this.numHashElements -= 1;
/* 142:202 */         if (var5 == var6) {
/* 143:204 */           this.hashArray[var4] = var7;
/* 144:    */         } else {
/* 145:208 */           var5.nextEntry = var7;
/* 146:    */         }
/* 147:211 */         return var6;
/* 148:    */       }
/* 149:214 */       var5 = var6;
/* 150:    */     }
/* 151:217 */     return var6;
/* 152:    */   }
/* 153:    */   
/* 154:    */   private void createKey(int par1, long par2, Object par4Obj, int par5)
/* 155:    */   {
/* 156:225 */     Entry var6 = this.hashArray[par5];
/* 157:226 */     this.hashArray[par5] = new Entry(par1, par2, par4Obj, var6);
/* 158:228 */     if (this.numHashElements++ >= this.capacity) {
/* 159:230 */       resizeTable(2 * this.hashArray.length);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public double getKeyDistribution()
/* 164:    */   {
/* 165:236 */     int countValid = 0;
/* 166:238 */     for (int i = 0; i < this.hashArray.length; i++) {
/* 167:240 */       if (this.hashArray[i] != null) {
/* 168:242 */         countValid++;
/* 169:    */       }
/* 170:    */     }
/* 171:246 */     return 1.0D * countValid / this.numHashElements;
/* 172:    */   }
/* 173:    */   
/* 174:    */   static class Entry
/* 175:    */   {
/* 176:    */     final long key;
/* 177:    */     Object value;
/* 178:    */     Entry nextEntry;
/* 179:    */     final int hash;
/* 180:    */     private static final String __OBFID = "CL_00001493";
/* 181:    */     
/* 182:    */     Entry(int par1, long par2, Object par4Obj, Entry par5LongHashMapEntry)
/* 183:    */     {
/* 184:259 */       this.value = par4Obj;
/* 185:260 */       this.nextEntry = par5LongHashMapEntry;
/* 186:261 */       this.key = par2;
/* 187:262 */       this.hash = par1;
/* 188:    */     }
/* 189:    */     
/* 190:    */     public final long getKey()
/* 191:    */     {
/* 192:267 */       return this.key;
/* 193:    */     }
/* 194:    */     
/* 195:    */     public final Object getValue()
/* 196:    */     {
/* 197:272 */       return this.value;
/* 198:    */     }
/* 199:    */     
/* 200:    */     public final boolean equals(Object par1Obj)
/* 201:    */     {
/* 202:277 */       if (!(par1Obj instanceof Entry)) {
/* 203:279 */         return false;
/* 204:    */       }
/* 205:283 */       Entry var2 = (Entry)par1Obj;
/* 206:284 */       Long var3 = Long.valueOf(getKey());
/* 207:285 */       Long var4 = Long.valueOf(var2.getKey());
/* 208:287 */       if ((var3 == var4) || ((var3 != null) && (var3.equals(var4))))
/* 209:    */       {
/* 210:289 */         Object var5 = getValue();
/* 211:290 */         Object var6 = var2.getValue();
/* 212:292 */         if ((var5 == var6) || ((var5 != null) && (var5.equals(var6)))) {
/* 213:294 */           return true;
/* 214:    */         }
/* 215:    */       }
/* 216:298 */       return false;
/* 217:    */     }
/* 218:    */     
/* 219:    */     public final int hashCode()
/* 220:    */     {
/* 221:304 */       return LongHashMap.getHashedKey(this.key);
/* 222:    */     }
/* 223:    */     
/* 224:    */     public final String toString()
/* 225:    */     {
/* 226:309 */       return getKey() + "=" + getValue();
/* 227:    */     }
/* 228:    */   }
/* 229:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.LongHashMap
 * JD-Core Version:    0.7.0.1
 */