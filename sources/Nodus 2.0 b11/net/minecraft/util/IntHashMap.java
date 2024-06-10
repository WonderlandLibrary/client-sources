/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Set;
/*   5:    */ 
/*   6:    */ public class IntHashMap
/*   7:    */ {
/*   8:  9 */   private transient Entry[] slots = new Entry[16];
/*   9:    */   private transient int count;
/*  10: 15 */   private int threshold = 12;
/*  11: 18 */   private final float growFactor = 0.75F;
/*  12:    */   private volatile transient int versionStamp;
/*  13: 24 */   private Set keySet = new HashSet();
/*  14:    */   private static final String __OBFID = "CL_00001490";
/*  15:    */   
/*  16:    */   private static int computeHash(int par0)
/*  17:    */   {
/*  18: 32 */     par0 ^= par0 >>> 20 ^ par0 >>> 12;
/*  19: 33 */     return par0 ^ par0 >>> 7 ^ par0 >>> 4;
/*  20:    */   }
/*  21:    */   
/*  22:    */   private static int getSlotIndex(int par0, int par1)
/*  23:    */   {
/*  24: 41 */     return par0 & par1 - 1;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Object lookup(int par1)
/*  28:    */   {
/*  29: 49 */     int var2 = computeHash(par1);
/*  30: 51 */     for (Entry var3 = this.slots[getSlotIndex(var2, this.slots.length)]; var3 != null; var3 = var3.nextEntry) {
/*  31: 53 */       if (var3.hashEntry == par1) {
/*  32: 55 */         return var3.valueEntry;
/*  33:    */       }
/*  34:    */     }
/*  35: 59 */     return null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean containsItem(int par1)
/*  39:    */   {
/*  40: 67 */     return lookupEntry(par1) != null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   final Entry lookupEntry(int par1)
/*  44:    */   {
/*  45: 75 */     int var2 = computeHash(par1);
/*  46: 77 */     for (Entry var3 = this.slots[getSlotIndex(var2, this.slots.length)]; var3 != null; var3 = var3.nextEntry) {
/*  47: 79 */       if (var3.hashEntry == par1) {
/*  48: 81 */         return var3;
/*  49:    */       }
/*  50:    */     }
/*  51: 85 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void addKey(int par1, Object par2Obj)
/*  55:    */   {
/*  56: 93 */     this.keySet.add(Integer.valueOf(par1));
/*  57: 94 */     int var3 = computeHash(par1);
/*  58: 95 */     int var4 = getSlotIndex(var3, this.slots.length);
/*  59: 97 */     for (Entry var5 = this.slots[var4]; var5 != null; var5 = var5.nextEntry) {
/*  60: 99 */       if (var5.hashEntry == par1)
/*  61:    */       {
/*  62:101 */         var5.valueEntry = par2Obj;
/*  63:102 */         return;
/*  64:    */       }
/*  65:    */     }
/*  66:106 */     this.versionStamp += 1;
/*  67:107 */     insert(var3, par1, par2Obj, var4);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void grow(int par1)
/*  71:    */   {
/*  72:115 */     Entry[] var2 = this.slots;
/*  73:116 */     int var3 = var2.length;
/*  74:118 */     if (var3 == 1073741824)
/*  75:    */     {
/*  76:120 */       this.threshold = 2147483647;
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80:124 */       Entry[] var4 = new Entry[par1];
/*  81:125 */       copyTo(var4);
/*  82:126 */       this.slots = var4;
/*  83:127 */       this.threshold = ((int)(par1 * 0.75F));
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void copyTo(Entry[] par1ArrayOfIntHashMapEntry)
/*  88:    */   {
/*  89:136 */     Entry[] var2 = this.slots;
/*  90:137 */     int var3 = par1ArrayOfIntHashMapEntry.length;
/*  91:139 */     for (int var4 = 0; var4 < var2.length; var4++)
/*  92:    */     {
/*  93:141 */       Entry var5 = var2[var4];
/*  94:143 */       if (var5 != null)
/*  95:    */       {
/*  96:145 */         var2[var4] = null;
/*  97:    */         Entry var6;
/*  98:    */         do
/*  99:    */         {
/* 100:150 */           var6 = var5.nextEntry;
/* 101:151 */           int var7 = getSlotIndex(var5.slotHash, var3);
/* 102:152 */           var5.nextEntry = par1ArrayOfIntHashMapEntry[var7];
/* 103:153 */           par1ArrayOfIntHashMapEntry[var7] = var5;
/* 104:154 */           var5 = var6;
/* 105:156 */         } while (var6 != null);
/* 106:    */       }
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Object removeObject(int par1)
/* 111:    */   {
/* 112:166 */     this.keySet.remove(Integer.valueOf(par1));
/* 113:167 */     Entry var2 = removeEntry(par1);
/* 114:168 */     return var2 == null ? null : var2.valueEntry;
/* 115:    */   }
/* 116:    */   
/* 117:    */   final Entry removeEntry(int par1)
/* 118:    */   {
/* 119:176 */     int var2 = computeHash(par1);
/* 120:177 */     int var3 = getSlotIndex(var2, this.slots.length);
/* 121:178 */     Entry var4 = this.slots[var3];
/* 122:    */     Entry var6;
/* 123:182 */     for (Entry var5 = var4; var5 != null; var5 = var6)
/* 124:    */     {
/* 125:184 */       var6 = var5.nextEntry;
/* 126:186 */       if (var5.hashEntry == par1)
/* 127:    */       {
/* 128:188 */         this.versionStamp += 1;
/* 129:189 */         this.count -= 1;
/* 130:191 */         if (var4 == var5) {
/* 131:193 */           this.slots[var3] = var6;
/* 132:    */         } else {
/* 133:197 */           var4.nextEntry = var6;
/* 134:    */         }
/* 135:200 */         return var5;
/* 136:    */       }
/* 137:203 */       var4 = var5;
/* 138:    */     }
/* 139:206 */     return var5;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void clearMap()
/* 143:    */   {
/* 144:214 */     this.versionStamp += 1;
/* 145:215 */     Entry[] var1 = this.slots;
/* 146:217 */     for (int var2 = 0; var2 < var1.length; var2++) {
/* 147:219 */       var1[var2] = null;
/* 148:    */     }
/* 149:222 */     this.count = 0;
/* 150:    */   }
/* 151:    */   
/* 152:    */   private void insert(int par1, int par2, Object par3Obj, int par4)
/* 153:    */   {
/* 154:230 */     Entry var5 = this.slots[par4];
/* 155:231 */     this.slots[par4] = new Entry(par1, par2, par3Obj, var5);
/* 156:233 */     if (this.count++ >= this.threshold) {
/* 157:235 */       grow(2 * this.slots.length);
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   static class Entry
/* 162:    */   {
/* 163:    */     final int hashEntry;
/* 164:    */     Object valueEntry;
/* 165:    */     Entry nextEntry;
/* 166:    */     final int slotHash;
/* 167:    */     private static final String __OBFID = "CL_00001491";
/* 168:    */     
/* 169:    */     Entry(int par1, int par2, Object par3Obj, Entry par4IntHashMapEntry)
/* 170:    */     {
/* 171:249 */       this.valueEntry = par3Obj;
/* 172:250 */       this.nextEntry = par4IntHashMapEntry;
/* 173:251 */       this.hashEntry = par2;
/* 174:252 */       this.slotHash = par1;
/* 175:    */     }
/* 176:    */     
/* 177:    */     public final int getHash()
/* 178:    */     {
/* 179:257 */       return this.hashEntry;
/* 180:    */     }
/* 181:    */     
/* 182:    */     public final Object getValue()
/* 183:    */     {
/* 184:262 */       return this.valueEntry;
/* 185:    */     }
/* 186:    */     
/* 187:    */     public final boolean equals(Object par1Obj)
/* 188:    */     {
/* 189:267 */       if (!(par1Obj instanceof Entry)) {
/* 190:269 */         return false;
/* 191:    */       }
/* 192:273 */       Entry var2 = (Entry)par1Obj;
/* 193:274 */       Integer var3 = Integer.valueOf(getHash());
/* 194:275 */       Integer var4 = Integer.valueOf(var2.getHash());
/* 195:277 */       if ((var3 == var4) || ((var3 != null) && (var3.equals(var4))))
/* 196:    */       {
/* 197:279 */         Object var5 = getValue();
/* 198:280 */         Object var6 = var2.getValue();
/* 199:282 */         if ((var5 == var6) || ((var5 != null) && (var5.equals(var6)))) {
/* 200:284 */           return true;
/* 201:    */         }
/* 202:    */       }
/* 203:288 */       return false;
/* 204:    */     }
/* 205:    */     
/* 206:    */     public final int hashCode()
/* 207:    */     {
/* 208:294 */       return IntHashMap.computeHash(this.hashEntry);
/* 209:    */     }
/* 210:    */     
/* 211:    */     public final String toString()
/* 212:    */     {
/* 213:299 */       return getHash() + "=" + getValue();
/* 214:    */     }
/* 215:    */   }
/* 216:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.IntHashMap
 * JD-Core Version:    0.7.0.1
 */