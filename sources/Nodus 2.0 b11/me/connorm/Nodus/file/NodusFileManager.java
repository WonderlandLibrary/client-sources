/*   1:    */ package me.connorm.Nodus.file;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.BufferedWriter;
/*   5:    */ import java.io.DataInputStream;
/*   6:    */ import java.io.File;
/*   7:    */ import java.io.FileInputStream;
/*   8:    */ import java.io.FileNotFoundException;
/*   9:    */ import java.io.FileOutputStream;
/*  10:    */ import java.io.FileWriter;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.io.InputStream;
/*  13:    */ import java.io.InputStreamReader;
/*  14:    */ import java.io.OutputStream;
/*  15:    */ import java.net.URL;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import me.connorm.Nodus.Nodus;
/*  19:    */ import me.connorm.Nodus.event.other.EventShutdown;
/*  20:    */ import me.connorm.Nodus.module.NodusModuleManager;
/*  21:    */ import me.connorm.Nodus.module.core.NodusModule;
/*  22:    */ import me.connorm.Nodus.module.modules.ClickGui;
/*  23:    */ import me.connorm.Nodus.module.modules.Console;
/*  24:    */ import me.connorm.Nodus.module.modules.Search;
/*  25:    */ import me.connorm.Nodus.module.modules.Xray;
/*  26:    */ import me.connorm.Nodus.relations.NodusRelationsManager;
/*  27:    */ import me.connorm.Nodus.relations.enemy.NodusEnemy;
/*  28:    */ import me.connorm.Nodus.relations.enemy.NodusEnemyManager;
/*  29:    */ import me.connorm.Nodus.relations.friend.NodusFriend;
/*  30:    */ import me.connorm.Nodus.relations.friend.NodusFriendManager;
/*  31:    */ import me.connorm.Nodus.ui.gui.NodusGuiManager;
/*  32:    */ import me.connorm.Nodus.ui.gui.theme.nodus.ColorUtils;
/*  33:    */ import me.connorm.Nodus.utils.FileUtils;
/*  34:    */ import me.connorm.lib.EventManager;
/*  35:    */ import me.connorm.lib.EventTarget;
/*  36:    */ import net.minecraft.client.Minecraft;
/*  37:    */ import org.darkstorm.minecraft.gui.component.Frame;
/*  38:    */ import org.lwjgl.input.Keyboard;
/*  39:    */ 
/*  40:    */ public class NodusFileManager
/*  41:    */ {
/*  42: 36 */   public File nodusDirectory = new File(Nodus.theNodus.getMinecraft().mcDataDir + File.separator + "Nodus");
/*  43: 38 */   public File nodusKeyBinds = new File(this.nodusDirectory + File.separator + "keybinds.nodus");
/*  44: 40 */   public File nodusXray = new File(this.nodusDirectory + File.separator + "xray.nodus");
/*  45: 42 */   public File nodusSearch = new File(this.nodusDirectory + File.separator + "search.nodus");
/*  46: 44 */   public File nodusFriends = new File(this.nodusDirectory + File.separator + "friends.nodus");
/*  47: 46 */   public File nodusEnemies = new File(this.nodusDirectory + File.separator + "enemies.nodus");
/*  48: 48 */   public File nodusEnabledMods = new File(this.nodusDirectory + File.separator + "mods.nodus");
/*  49: 50 */   public File nodusGuiConfig = new File(this.nodusDirectory + File.separator + "gui.nodus");
/*  50: 52 */   public File nodusBackgroundImage = new File(this.nodusDirectory + File.separator + "background.png");
/*  51: 54 */   public File nodusMOTD = new File(this.nodusDirectory + File.separator + "MOTD.txt");
/*  52:    */   public String MOTD;
/*  53:    */   public boolean isFirstTime;
/*  54:    */   
/*  55:    */   public NodusFileManager()
/*  56:    */   {
/*  57: 62 */     EventManager.register(this);
/*  58: 63 */     createFiles();
/*  59:    */     try
/*  60:    */     {
/*  61: 66 */       if (this.nodusKeyBinds.exists()) {
/*  62: 67 */         loadBinds();
/*  63:    */       }
/*  64: 68 */       if (this.nodusXray.exists()) {
/*  65: 69 */         loadXray();
/*  66:    */       }
/*  67: 70 */       if (this.nodusSearch.exists()) {
/*  68: 71 */         loadSearch();
/*  69:    */       }
/*  70: 72 */       if (this.nodusFriends.exists()) {
/*  71: 73 */         loadFriends();
/*  72:    */       }
/*  73: 74 */       if (this.nodusEnemies.exists()) {
/*  74: 75 */         loadEnemies();
/*  75:    */       }
/*  76: 76 */       if (this.nodusEnabledMods.exists()) {
/*  77: 77 */         loadEnabledMods();
/*  78:    */       }
/*  79: 78 */       if (this.nodusGuiConfig.exists()) {
/*  80: 79 */         loadGuiConfig();
/*  81:    */       }
/*  82:    */     }
/*  83:    */     catch (FileNotFoundException fileNotFoundException)
/*  84:    */     {
/*  85: 82 */       fileNotFoundException.printStackTrace();
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   @EventTarget
/*  90:    */   public void shutdownGame(EventShutdown theEvent)
/*  91:    */   {
/*  92: 89 */     saveEnabledMods();
/*  93: 90 */     saveGuiConfig();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void createFiles()
/*  97:    */   {
/*  98: 95 */     if (!this.nodusDirectory.exists()) {
/*  99: 97 */       this.nodusDirectory.mkdirs();
/* 100:    */     }
/* 101:    */     try
/* 102:    */     {
/* 103:101 */       downloadBackgroundImage();
/* 104:102 */       downloadMOTD();
/* 105:    */     }
/* 106:    */     catch (IOException ioException)
/* 107:    */     {
/* 108:105 */       ioException.printStackTrace();
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void saveKeyBinds()
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:113 */       this.nodusKeyBinds = new File(this.nodusDirectory + File.separator + "keybinds.nodus");
/* 117:114 */       BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.nodusKeyBinds));
/* 118:115 */       for (NodusModule nodusModule : Nodus.theNodus.moduleManager.getModules()) {
/* 119:117 */         if (nodusModule.getKeyBind() != 0)
/* 120:    */         {
/* 121:121 */           bufferedWriter.write(nodusModule.getName().replace(" ", "") + ":" + Keyboard.getKeyName(nodusModule.getKeyBind()));
/* 122:122 */           bufferedWriter.write("\r\n");
/* 123:    */         }
/* 124:    */       }
/* 125:125 */       bufferedWriter.close();
/* 126:    */     }
/* 127:    */     catch (Exception writeException)
/* 128:    */     {
/* 129:129 */       writeException.printStackTrace();
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   private void loadBinds()
/* 134:    */     throws FileNotFoundException
/* 135:    */   {
/* 136:135 */     FileInputStream fileInputStream = new FileInputStream(this.nodusKeyBinds.getAbsolutePath());
/* 137:136 */     DataInputStream dataInputStream = new DataInputStream(fileInputStream);
/* 138:137 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
/* 139:    */     try
/* 140:    */     {
/* 141:    */       String readLine;
/* 142:142 */       while ((readLine = bufferedReader.readLine()) != null)
/* 143:    */       {
/* 144:    */         String readLine;
/* 145:144 */         String currentLine = readLine.trim();
/* 146:145 */         String[] keyArgs = currentLine.split(":");
/* 147:146 */         String keyModule = keyArgs[0];
/* 148:147 */         int moduleBind = Keyboard.getKeyIndex(keyArgs[1].toUpperCase());
/* 149:148 */         for (NodusModule nodusModule : Nodus.theNodus.moduleManager.getModules()) {
/* 150:150 */           if (keyModule.equalsIgnoreCase(nodusModule.getName().toLowerCase().replace(" ", ""))) {
/* 151:152 */             nodusModule.setKeyBind(moduleBind);
/* 152:    */           }
/* 153:    */         }
/* 154:    */       }
/* 155:155 */       bufferedReader.close();
/* 156:    */     }
/* 157:    */     catch (IOException ioException)
/* 158:    */     {
/* 159:158 */       ioException.printStackTrace();
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void downloadBackgroundImage()
/* 164:    */     throws IOException
/* 165:    */   {
/* 166:164 */     saveImage("http://connorm.me/background.png", this.nodusDirectory + File.separator + "background.png");
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void downloadMOTD()
/* 170:    */     throws IOException
/* 171:    */   {
/* 172:169 */     saveImage("http://connorm.me/MOTD.txt", this.nodusDirectory + File.separator + "MOTD.txt");
/* 173:    */     
/* 174:171 */     ArrayList<String> MOTD = FileUtils.read(this.nodusMOTD);
/* 175:172 */     Iterator localIterator = MOTD.iterator();
/* 176:172 */     if (localIterator.hasNext())
/* 177:    */     {
/* 178:172 */       String theMOTD = (String)localIterator.next();
/* 179:    */       
/* 180:174 */       this.MOTD = theMOTD;
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void saveXray()
/* 185:    */   {
/* 186:181 */     ArrayList xrayArray = Nodus.theNodus.moduleManager.xrayModule.xrayBlocks;
/* 187:182 */     FileUtils.print(xrayArray, this.nodusXray);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void loadXray()
/* 191:    */   {
/* 192:187 */     ArrayList<String> savedBlocks = FileUtils.read(this.nodusXray);
/* 193:188 */     for (String blockName : savedBlocks) {
/* 194:190 */       Nodus.theNodus.moduleManager.xrayModule.xrayBlocks.add(blockName);
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void saveSearch()
/* 199:    */   {
/* 200:196 */     ArrayList xrayArray = Nodus.theNodus.moduleManager.searchModule.searchBlocks;
/* 201:197 */     FileUtils.print(xrayArray, this.nodusSearch);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void loadSearch()
/* 205:    */   {
/* 206:202 */     ArrayList<String> savedBlocks = FileUtils.read(this.nodusSearch);
/* 207:203 */     for (String blockName : savedBlocks) {
/* 208:205 */       Nodus.theNodus.moduleManager.searchModule.searchBlocks.add(blockName);
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void saveFriends()
/* 213:    */   {
/* 214:211 */     ArrayList<String> friendArray = new ArrayList();
/* 215:212 */     for (NodusFriend nodusFriend : Nodus.theNodus.relationsManager.friendManager.getFriends()) {
/* 216:214 */       friendArray.add(nodusFriend.getName());
/* 217:    */     }
/* 218:216 */     FileUtils.print(friendArray, this.nodusFriends);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void loadFriends()
/* 222:    */   {
/* 223:221 */     ArrayList<String> savedFriends = FileUtils.read(this.nodusFriends);
/* 224:222 */     for (String friendName : savedFriends) {
/* 225:224 */       Nodus.theNodus.relationsManager.friendManager.addFriend(friendName);
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void saveEnemies()
/* 230:    */   {
/* 231:230 */     ArrayList<String> enemyArray = new ArrayList();
/* 232:231 */     for (NodusEnemy nodusEnemy : Nodus.theNodus.relationsManager.enemyManager.getEnemies()) {
/* 233:233 */       enemyArray.add(nodusEnemy.getName());
/* 234:    */     }
/* 235:235 */     FileUtils.print(enemyArray, this.nodusEnemies);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void loadEnemies()
/* 239:    */   {
/* 240:240 */     ArrayList<String> savedEnemies = FileUtils.read(this.nodusEnemies);
/* 241:241 */     for (String enemyName : savedEnemies) {
/* 242:243 */       Nodus.theNodus.relationsManager.enemyManager.addEnemy(enemyName);
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void saveEnabledMods()
/* 247:    */   {
/* 248:249 */     ArrayList<String> enabledModsArray = new ArrayList();
/* 249:250 */     for (NodusModule nodusModule : Nodus.theNodus.moduleManager.getModules())
/* 250:    */     {
/* 251:252 */       if ((nodusModule.isToggled()) && (!(nodusModule instanceof ClickGui)) && (!(nodusModule instanceof Console))) {
/* 252:254 */         enabledModsArray.add(nodusModule.getName());
/* 253:    */       }
/* 254:256 */       if ((!nodusModule.isToggled()) && (enabledModsArray.contains(nodusModule.getName()))) {
/* 255:258 */         enabledModsArray.remove(nodusModule.getName());
/* 256:    */       }
/* 257:    */     }
/* 258:261 */     FileUtils.print(enabledModsArray, this.nodusEnabledMods);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void loadEnabledMods()
/* 262:    */   {
/* 263:266 */     ArrayList<String> savedEnabledMods = FileUtils.read(this.nodusEnabledMods);
/* 264:267 */     for (NodusModule nodusModule : Nodus.theNodus.moduleManager.getModules()) {
/* 265:269 */       if (savedEnabledMods.contains(nodusModule.getName())) {
/* 266:271 */         nodusModule.toggleModule();
/* 267:    */       }
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void saveGuiConfig()
/* 272:    */   {
/* 273:278 */     ArrayList<String> guiConfigArray = new ArrayList();
/* 274:279 */     for (Frame guiFrame : Nodus.theNodus.guiManager.getFrames()) {
/* 275:281 */       guiConfigArray.add(guiFrame.getTitle() + ":" + guiFrame.getX() + ":" + guiFrame.getY() + ":" + guiFrame.isPinned() + ":" + guiFrame.isMinimized());
/* 276:    */     }
/* 277:283 */     guiConfigArray.add("Primary:" + ColorUtils.primaryColor);
/* 278:284 */     guiConfigArray.add("Secondary:" + ColorUtils.secondaryColor);
/* 279:285 */     guiConfigArray.add("Chest:" + ColorUtils.chestESPColor);
/* 280:286 */     guiConfigArray.add("Aggressive:" + ColorUtils.aggressiveESPColor);
/* 281:287 */     guiConfigArray.add("Passive:" + ColorUtils.passiveESPColor);
/* 282:288 */     guiConfigArray.add("Player" + ColorUtils.playerESPColor);
/* 283:289 */     FileUtils.print(guiConfigArray, this.nodusGuiConfig);
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void loadGuiConfig()
/* 287:    */   {
/* 288:294 */     ArrayList<String> savedGuiConfig = FileUtils.read(this.nodusGuiConfig);
/* 289:296 */     for (Frame guiFrame : Nodus.theNodus.guiManager.getFrames()) {
/* 290:297 */       for (String frameConfig : savedGuiConfig)
/* 291:    */       {
/* 292:299 */         String[] guiConfig = frameConfig.split(":");
/* 293:301 */         if (!guiConfig[0].equalsIgnoreCase("X:")) {
/* 294:306 */           if (guiConfig[0].equalsIgnoreCase("Primary"))
/* 295:    */           {
/* 296:308 */             ColorUtils.primaryColor = Integer.parseInt(guiConfig[1]);
/* 297:    */           }
/* 298:312 */           else if (guiConfig[0].equalsIgnoreCase("Secondary"))
/* 299:    */           {
/* 300:314 */             ColorUtils.secondaryColor = Integer.parseInt(guiConfig[1]);
/* 301:    */           }
/* 302:318 */           else if (guiConfig[0].equalsIgnoreCase("Chest"))
/* 303:    */           {
/* 304:320 */             ColorUtils.chestESPColor = Integer.parseInt(guiConfig[1]);
/* 305:    */           }
/* 306:324 */           else if (guiConfig[0].equalsIgnoreCase("Aggressive"))
/* 307:    */           {
/* 308:326 */             ColorUtils.aggressiveESPColor = Integer.parseInt(guiConfig[1]);
/* 309:    */           }
/* 310:330 */           else if (guiConfig[0].equalsIgnoreCase("Passive"))
/* 311:    */           {
/* 312:332 */             ColorUtils.passiveESPColor = Integer.parseInt(guiConfig[1]);
/* 313:    */           }
/* 314:336 */           else if (guiConfig[0].equalsIgnoreCase("Player"))
/* 315:    */           {
/* 316:338 */             ColorUtils.playerESPColor = Integer.parseInt(guiConfig[1]);
/* 317:    */           }
/* 318:342 */           else if (guiFrame.getTitle().equalsIgnoreCase(guiConfig[0]))
/* 319:    */           {
/* 320:344 */             guiFrame.setX(Integer.parseInt(guiConfig[1]));
/* 321:345 */             guiFrame.setY(Integer.parseInt(guiConfig[2]));
/* 322:346 */             guiFrame.setPinned(Boolean.parseBoolean(guiConfig[3]));
/* 323:347 */             guiFrame.setMinimized(Boolean.parseBoolean(guiConfig[4]));
/* 324:    */           }
/* 325:    */         }
/* 326:    */       }
/* 327:    */     }
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void saveImage(String fileURL, String destinationPath)
/* 331:    */     throws IOException
/* 332:    */   {
/* 333:354 */     URL url = new URL(fileURL);
/* 334:355 */     InputStream inputStream = url.openStream();
/* 335:356 */     OutputStream outputStream = new FileOutputStream(destinationPath);
/* 336:    */     
/* 337:358 */     byte[] b = new byte[2048];
/* 338:    */     int length;
/* 339:361 */     while ((length = inputStream.read(b)) != -1)
/* 340:    */     {
/* 341:    */       int length;
/* 342:363 */       outputStream.write(b, 0, length);
/* 343:    */     }
/* 344:366 */     inputStream.close();
/* 345:367 */     outputStream.close();
/* 346:    */   }
/* 347:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.file.NodusFileManager
 * JD-Core Version:    0.7.0.1
 */