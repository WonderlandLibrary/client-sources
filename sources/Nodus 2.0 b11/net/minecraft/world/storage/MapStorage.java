/*   1:    */ package net.minecraft.world.storage;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.lang.reflect.Constructor;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Set;
/*  15:    */ import net.minecraft.nbt.CompressedStreamTools;
/*  16:    */ import net.minecraft.nbt.NBTBase;
/*  17:    */ import net.minecraft.nbt.NBTTagCompound;
/*  18:    */ import net.minecraft.nbt.NBTTagShort;
/*  19:    */ import net.minecraft.world.WorldSavedData;
/*  20:    */ 
/*  21:    */ public class MapStorage
/*  22:    */ {
/*  23:    */   private ISaveHandler saveHandler;
/*  24: 24 */   private Map loadedDataMap = new HashMap();
/*  25: 27 */   private List loadedDataList = new ArrayList();
/*  26: 32 */   private Map idCounts = new HashMap();
/*  27:    */   private static final String __OBFID = "CL_00000604";
/*  28:    */   
/*  29:    */   public MapStorage(ISaveHandler par1ISaveHandler)
/*  30:    */   {
/*  31: 37 */     this.saveHandler = par1ISaveHandler;
/*  32: 38 */     loadIdCounts();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public WorldSavedData loadData(Class par1Class, String par2Str)
/*  36:    */   {
/*  37: 47 */     WorldSavedData var3 = (WorldSavedData)this.loadedDataMap.get(par2Str);
/*  38: 49 */     if (var3 != null) {
/*  39: 51 */       return var3;
/*  40:    */     }
/*  41: 55 */     if (this.saveHandler != null) {
/*  42:    */       try
/*  43:    */       {
/*  44: 59 */         File var4 = this.saveHandler.getMapFileFromName(par2Str);
/*  45: 61 */         if ((var4 != null) && (var4.exists()))
/*  46:    */         {
/*  47:    */           try
/*  48:    */           {
/*  49: 65 */             var3 = (WorldSavedData)par1Class.getConstructor(new Class[] { String.class }).newInstance(new Object[] { par2Str });
/*  50:    */           }
/*  51:    */           catch (Exception var7)
/*  52:    */           {
/*  53: 69 */             throw new RuntimeException("Failed to instantiate " + par1Class.toString(), var7);
/*  54:    */           }
/*  55: 72 */           FileInputStream var5 = new FileInputStream(var4);
/*  56: 73 */           NBTTagCompound var6 = CompressedStreamTools.readCompressed(var5);
/*  57: 74 */           var5.close();
/*  58: 75 */           var3.readFromNBT(var6.getCompoundTag("data"));
/*  59:    */         }
/*  60:    */       }
/*  61:    */       catch (Exception var8)
/*  62:    */       {
/*  63: 80 */         var8.printStackTrace();
/*  64:    */       }
/*  65:    */     }
/*  66: 84 */     if (var3 != null)
/*  67:    */     {
/*  68: 86 */       this.loadedDataMap.put(par2Str, var3);
/*  69: 87 */       this.loadedDataList.add(var3);
/*  70:    */     }
/*  71: 90 */     return var3;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setData(String par1Str, WorldSavedData par2WorldSavedData)
/*  75:    */   {
/*  76: 99 */     if (par2WorldSavedData == null) {
/*  77:101 */       throw new RuntimeException("Can't set null data");
/*  78:    */     }
/*  79:105 */     if (this.loadedDataMap.containsKey(par1Str)) {
/*  80:107 */       this.loadedDataList.remove(this.loadedDataMap.remove(par1Str));
/*  81:    */     }
/*  82:110 */     this.loadedDataMap.put(par1Str, par2WorldSavedData);
/*  83:111 */     this.loadedDataList.add(par2WorldSavedData);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void saveAllData()
/*  87:    */   {
/*  88:120 */     for (int var1 = 0; var1 < this.loadedDataList.size(); var1++)
/*  89:    */     {
/*  90:122 */       WorldSavedData var2 = (WorldSavedData)this.loadedDataList.get(var1);
/*  91:124 */       if (var2.isDirty())
/*  92:    */       {
/*  93:126 */         saveData(var2);
/*  94:127 */         var2.setDirty(false);
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void saveData(WorldSavedData par1WorldSavedData)
/* 100:    */   {
/* 101:137 */     if (this.saveHandler != null) {
/* 102:    */       try
/* 103:    */       {
/* 104:141 */         File var2 = this.saveHandler.getMapFileFromName(par1WorldSavedData.mapName);
/* 105:143 */         if (var2 != null)
/* 106:    */         {
/* 107:145 */           NBTTagCompound var3 = new NBTTagCompound();
/* 108:146 */           par1WorldSavedData.writeToNBT(var3);
/* 109:147 */           NBTTagCompound var4 = new NBTTagCompound();
/* 110:148 */           var4.setTag("data", var3);
/* 111:149 */           FileOutputStream var5 = new FileOutputStream(var2);
/* 112:150 */           CompressedStreamTools.writeCompressed(var4, var5);
/* 113:151 */           var5.close();
/* 114:    */         }
/* 115:    */       }
/* 116:    */       catch (Exception var6)
/* 117:    */       {
/* 118:156 */         var6.printStackTrace();
/* 119:    */       }
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   private void loadIdCounts()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:168 */       this.idCounts.clear();
/* 128:170 */       if (this.saveHandler == null) {
/* 129:172 */         return;
/* 130:    */       }
/* 131:175 */       File var1 = this.saveHandler.getMapFileFromName("idcounts");
/* 132:177 */       if ((var1 != null) && (var1.exists()))
/* 133:    */       {
/* 134:179 */         DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
/* 135:180 */         NBTTagCompound var3 = CompressedStreamTools.read(var2);
/* 136:181 */         var2.close();
/* 137:182 */         Iterator var4 = var3.func_150296_c().iterator();
/* 138:184 */         while (var4.hasNext())
/* 139:    */         {
/* 140:186 */           String var5 = (String)var4.next();
/* 141:187 */           NBTBase var6 = var3.getTag(var5);
/* 142:189 */           if ((var6 instanceof NBTTagShort))
/* 143:    */           {
/* 144:191 */             NBTTagShort var7 = (NBTTagShort)var6;
/* 145:192 */             short var9 = var7.func_150289_e();
/* 146:193 */             this.idCounts.put(var5, Short.valueOf(var9));
/* 147:    */           }
/* 148:    */         }
/* 149:    */       }
/* 150:    */     }
/* 151:    */     catch (Exception var10)
/* 152:    */     {
/* 153:200 */       var10.printStackTrace();
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int getUniqueDataId(String par1Str)
/* 158:    */   {
/* 159:209 */     Short var2 = (Short)this.idCounts.get(par1Str);
/* 160:211 */     if (var2 == null) {
/* 161:213 */       var2 = Short.valueOf((short)0);
/* 162:    */     } else {
/* 163:217 */       var2 = Short.valueOf((short)(var2.shortValue() + 1));
/* 164:    */     }
/* 165:220 */     this.idCounts.put(par1Str, var2);
/* 166:222 */     if (this.saveHandler == null) {
/* 167:224 */       return var2.shortValue();
/* 168:    */     }
/* 169:    */     try
/* 170:    */     {
/* 171:230 */       File var3 = this.saveHandler.getMapFileFromName("idcounts");
/* 172:232 */       if (var3 != null)
/* 173:    */       {
/* 174:234 */         NBTTagCompound var4 = new NBTTagCompound();
/* 175:235 */         Iterator var5 = this.idCounts.keySet().iterator();
/* 176:237 */         while (var5.hasNext())
/* 177:    */         {
/* 178:239 */           String var6 = (String)var5.next();
/* 179:240 */           short var7 = ((Short)this.idCounts.get(var6)).shortValue();
/* 180:241 */           var4.setShort(var6, var7);
/* 181:    */         }
/* 182:244 */         DataOutputStream var9 = new DataOutputStream(new FileOutputStream(var3));
/* 183:245 */         CompressedStreamTools.write(var4, var9);
/* 184:246 */         var9.close();
/* 185:    */       }
/* 186:    */     }
/* 187:    */     catch (Exception var8)
/* 188:    */     {
/* 189:251 */       var8.printStackTrace();
/* 190:    */     }
/* 191:254 */     return var2.shortValue();
/* 192:    */   }
/* 193:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.MapStorage
 * JD-Core Version:    0.7.0.1
 */