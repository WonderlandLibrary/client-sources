/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.io.FileNotFoundException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*   8:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ import net.minecraft.util.ResourceLocation;
/*  11:    */ 
/*  12:    */ public class NaturalTextures
/*  13:    */ {
/*  14: 14 */   private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];
/*  15:    */   
/*  16:    */   public static void update()
/*  17:    */   {
/*  18: 18 */     propertiesByIndex = new NaturalProperties[0];
/*  19: 20 */     if (Config.isNaturalTextures())
/*  20:    */     {
/*  21: 22 */       String fileName = "optifine/natural.properties";
/*  22:    */       try
/*  23:    */       {
/*  24: 26 */         ResourceLocation e = new ResourceLocation(fileName);
/*  25: 28 */         if (!Config.hasResource(e))
/*  26:    */         {
/*  27: 30 */           Config.dbg("NaturalTextures: configuration \"" + fileName + "\" not found");
/*  28: 31 */           propertiesByIndex = makeDefaultProperties();
/*  29: 32 */           return;
/*  30:    */         }
/*  31: 35 */         InputStream in = Config.getResourceStream(e);
/*  32: 36 */         ArrayList list = new ArrayList(256);
/*  33: 37 */         String configStr = Config.readInputStream(in);
/*  34: 38 */         in.close();
/*  35: 39 */         String[] configLines = Config.tokenize(configStr, "\n\r");
/*  36: 40 */         Config.dbg("Natural Textures: Parsing configuration \"" + fileName + "\"");
/*  37: 42 */         for (int i = 0; i < configLines.length; i++)
/*  38:    */         {
/*  39: 44 */           String line = configLines[i].trim();
/*  40: 46 */           if (!line.startsWith("#"))
/*  41:    */           {
/*  42: 48 */             String[] strs = Config.tokenize(line, "=");
/*  43: 50 */             if (strs.length != 2)
/*  44:    */             {
/*  45: 52 */               Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
/*  46:    */             }
/*  47:    */             else
/*  48:    */             {
/*  49: 56 */               String key = strs[0].trim();
/*  50: 57 */               String type = strs[1].trim();
/*  51: 58 */               TextureAtlasSprite ts = TextureMap.textureMapBlocks.getIconSafe(key);
/*  52: 60 */               if (ts == null)
/*  53:    */               {
/*  54: 62 */                 Config.warn("Natural Textures: Texture not found: \"" + fileName + "\" line: " + line);
/*  55:    */               }
/*  56:    */               else
/*  57:    */               {
/*  58: 66 */                 int tileNum = ts.getIndexInMap();
/*  59: 68 */                 if (tileNum < 0)
/*  60:    */                 {
/*  61: 70 */                   Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
/*  62:    */                 }
/*  63:    */                 else
/*  64:    */                 {
/*  65: 74 */                   NaturalProperties props = new NaturalProperties(type);
/*  66: 76 */                   if (props.isValid())
/*  67:    */                   {
/*  68: 78 */                     while (list.size() <= tileNum) {
/*  69: 80 */                       list.add(null);
/*  70:    */                     }
/*  71: 83 */                     list.set(tileNum, props);
/*  72: 84 */                     Config.dbg("NaturalTextures: " + key + " = " + type);
/*  73:    */                   }
/*  74:    */                 }
/*  75:    */               }
/*  76:    */             }
/*  77:    */           }
/*  78:    */         }
/*  79: 92 */         propertiesByIndex = (NaturalProperties[])list.toArray(new NaturalProperties[list.size()]);
/*  80:    */       }
/*  81:    */       catch (FileNotFoundException var15)
/*  82:    */       {
/*  83: 96 */         Config.warn("NaturalTextures: configuration \"" + fileName + "\" not found");
/*  84: 97 */         propertiesByIndex = makeDefaultProperties();
/*  85: 98 */         return;
/*  86:    */       }
/*  87:    */       catch (Exception var16)
/*  88:    */       {
/*  89:102 */         var16.printStackTrace();
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static NaturalProperties getNaturalProperties(IIcon icon)
/*  95:    */   {
/*  96:109 */     if (!(icon instanceof TextureAtlasSprite)) {
/*  97:111 */       return null;
/*  98:    */     }
/*  99:115 */     TextureAtlasSprite ts = (TextureAtlasSprite)icon;
/* 100:116 */     int tileNum = ts.getIndexInMap();
/* 101:118 */     if ((tileNum >= 0) && (tileNum < propertiesByIndex.length))
/* 102:    */     {
/* 103:120 */       NaturalProperties props = propertiesByIndex[tileNum];
/* 104:121 */       return props;
/* 105:    */     }
/* 106:125 */     return null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private static NaturalProperties[] makeDefaultProperties()
/* 110:    */   {
/* 111:132 */     Config.dbg("NaturalTextures: Checking default configuration.");
/* 112:133 */     ArrayList propsList = new ArrayList();
/* 113:134 */     setIconProperties(propsList, "grass_top", "4F");
/* 114:135 */     setIconProperties(propsList, "stone", "2F");
/* 115:136 */     setIconProperties(propsList, "dirt", "4F");
/* 116:137 */     setIconProperties(propsList, "grass_side", "F");
/* 117:138 */     setIconProperties(propsList, "grass_side_overlay", "F");
/* 118:139 */     setIconProperties(propsList, "stone_slab_top", "F");
/* 119:140 */     setIconProperties(propsList, "bedrock", "2F");
/* 120:141 */     setIconProperties(propsList, "sand", "4F");
/* 121:142 */     setIconProperties(propsList, "gravel", "2");
/* 122:143 */     setIconProperties(propsList, "log_oak", "2F");
/* 123:144 */     setIconProperties(propsList, "log_oak_top", "4F");
/* 124:145 */     setIconProperties(propsList, "gold_ore", "2F");
/* 125:146 */     setIconProperties(propsList, "iron_ore", "2F");
/* 126:147 */     setIconProperties(propsList, "coal_ore", "2F");
/* 127:148 */     setIconProperties(propsList, "diamond_ore", "2F");
/* 128:149 */     setIconProperties(propsList, "redstone_ore", "2F");
/* 129:150 */     setIconProperties(propsList, "lapis_ore", "2F");
/* 130:151 */     setIconProperties(propsList, "obsidian", "4F");
/* 131:152 */     setIconProperties(propsList, "leaves_oak", "2F");
/* 132:153 */     setIconProperties(propsList, "leaves_oak_opaque", "2F");
/* 133:154 */     setIconProperties(propsList, "leaves_jungle", "2");
/* 134:155 */     setIconProperties(propsList, "leaves_jungle_opaque", "2");
/* 135:156 */     setIconProperties(propsList, "snow", "4F");
/* 136:157 */     setIconProperties(propsList, "grass_side_snowed", "F");
/* 137:158 */     setIconProperties(propsList, "cactus_side", "2F");
/* 138:159 */     setIconProperties(propsList, "clay", "4F");
/* 139:160 */     setIconProperties(propsList, "mycelium_side", "F");
/* 140:161 */     setIconProperties(propsList, "mycelium_top", "4F");
/* 141:162 */     setIconProperties(propsList, "farmland_wet", "2F");
/* 142:163 */     setIconProperties(propsList, "farmland_dry", "2F");
/* 143:164 */     setIconProperties(propsList, "netherrack", "4F");
/* 144:165 */     setIconProperties(propsList, "soul_sand", "4F");
/* 145:166 */     setIconProperties(propsList, "glowstone", "4");
/* 146:167 */     setIconProperties(propsList, "log_spruce", "2F");
/* 147:168 */     setIconProperties(propsList, "log_birch", "F");
/* 148:169 */     setIconProperties(propsList, "leaves_spruce", "2F");
/* 149:170 */     setIconProperties(propsList, "leaves_spruce_opaque", "2F");
/* 150:171 */     setIconProperties(propsList, "log_jungle", "2F");
/* 151:172 */     setIconProperties(propsList, "end_stone", "4");
/* 152:173 */     setIconProperties(propsList, "sandstone_top", "4");
/* 153:174 */     setIconProperties(propsList, "sandstone_bottom", "4F");
/* 154:175 */     setIconProperties(propsList, "redstone_lamp_on", "4F");
/* 155:176 */     NaturalProperties[] terrainProps = (NaturalProperties[])propsList.toArray(new NaturalProperties[propsList.size()]);
/* 156:177 */     return terrainProps;
/* 157:    */   }
/* 158:    */   
/* 159:    */   private static void setIconProperties(List propsList, String iconName, String propStr)
/* 160:    */   {
/* 161:182 */     TextureMap terrainMap = TextureMap.textureMapBlocks;
/* 162:183 */     TextureAtlasSprite icon = terrainMap.getIconSafe(iconName);
/* 163:185 */     if (icon == null)
/* 164:    */     {
/* 165:187 */       Config.warn("*** NaturalProperties: Icon not found: " + iconName + " ***");
/* 166:    */     }
/* 167:189 */     else if (!(icon instanceof TextureAtlasSprite))
/* 168:    */     {
/* 169:191 */       Config.warn("*** NaturalProperties: Icon is not IconStitched: " + iconName + ": " + icon.getClass().getName() + " ***");
/* 170:    */     }
/* 171:    */     else
/* 172:    */     {
/* 173:195 */       TextureAtlasSprite ts = icon;
/* 174:196 */       int index = ts.getIndexInMap();
/* 175:198 */       if (index < 0)
/* 176:    */       {
/* 177:200 */         Config.warn("*** NaturalProperties: Invalid index for icon: " + iconName + ": " + index + " ***");
/* 178:    */       }
/* 179:202 */       else if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + iconName + ".png")))
/* 180:    */       {
/* 181:204 */         while (index >= propsList.size()) {
/* 182:206 */           propsList.add(null);
/* 183:    */         }
/* 184:209 */         NaturalProperties props = new NaturalProperties(propStr);
/* 185:210 */         propsList.set(index, props);
/* 186:211 */         Config.dbg("NaturalTextures: " + iconName + " = " + propStr);
/* 187:    */       }
/* 188:    */     }
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.NaturalTextures
 * JD-Core Version:    0.7.0.1
 */