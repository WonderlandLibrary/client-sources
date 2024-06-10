/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.text.SimpleDateFormat;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   9:    */ import net.minecraft.client.AnvilConverterException;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.renderer.Tessellator;
/*  12:    */ import net.minecraft.client.resources.I18n;
/*  13:    */ import net.minecraft.util.EnumChatFormatting;
/*  14:    */ import net.minecraft.util.MathHelper;
/*  15:    */ import net.minecraft.world.storage.ISaveFormat;
/*  16:    */ import net.minecraft.world.storage.ISaveHandler;
/*  17:    */ import net.minecraft.world.storage.SaveFormatComparator;
/*  18:    */ import net.minecraft.world.storage.WorldInfo;
/*  19:    */ import org.apache.logging.log4j.LogManager;
/*  20:    */ import org.apache.logging.log4j.Logger;
/*  21:    */ 
/*  22:    */ public class GuiSelectWorld
/*  23:    */   extends GuiScreen
/*  24:    */ {
/*  25: 25 */   private static final Logger logger = ;
/*  26: 26 */   private final DateFormat field_146633_h = new SimpleDateFormat();
/*  27:    */   protected GuiScreen field_146632_a;
/*  28: 28 */   protected String field_146628_f = "Select world";
/*  29:    */   private boolean field_146634_i;
/*  30:    */   private int field_146640_r;
/*  31:    */   private List field_146639_s;
/*  32:    */   private List field_146638_t;
/*  33:    */   private String field_146637_u;
/*  34:    */   private String field_146636_v;
/*  35: 35 */   private String[] field_146635_w = new String[3];
/*  36:    */   private boolean field_146643_x;
/*  37:    */   private NodusGuiButton field_146642_y;
/*  38:    */   private NodusGuiButton field_146641_z;
/*  39:    */   private NodusGuiButton field_146630_A;
/*  40:    */   private NodusGuiButton field_146631_B;
/*  41:    */   private static final String __OBFID = "CL_00000711";
/*  42:    */   
/*  43:    */   public GuiSelectWorld(GuiScreen par1GuiScreen)
/*  44:    */   {
/*  45: 45 */     this.field_146632_a = par1GuiScreen;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void initGui()
/*  49:    */   {
/*  50: 53 */     this.field_146628_f = I18n.format("selectWorld.title", new Object[0]);
/*  51:    */     try
/*  52:    */     {
/*  53: 57 */       func_146627_h();
/*  54:    */     }
/*  55:    */     catch (AnvilConverterException var2)
/*  56:    */     {
/*  57: 61 */       logger.error("Couldn't load level list", var2);
/*  58: 62 */       this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", var2.getMessage()));
/*  59: 63 */       return;
/*  60:    */     }
/*  61: 66 */     this.field_146637_u = I18n.format("selectWorld.world", new Object[0]);
/*  62: 67 */     this.field_146636_v = I18n.format("selectWorld.conversion", new Object[0]);
/*  63: 68 */     this.field_146635_w[net.minecraft.world.WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival", new Object[0]);
/*  64: 69 */     this.field_146635_w[net.minecraft.world.WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative", new Object[0]);
/*  65: 70 */     this.field_146635_w[net.minecraft.world.WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure", new Object[0]);
/*  66: 71 */     this.field_146638_t = new List();
/*  67: 72 */     this.field_146638_t.func_148134_d(4, 5);
/*  68: 73 */     func_146618_g();
/*  69:    */   }
/*  70:    */   
/*  71:    */   private void func_146627_h()
/*  72:    */     throws AnvilConverterException
/*  73:    */   {
/*  74: 78 */     ISaveFormat var1 = this.mc.getSaveLoader();
/*  75: 79 */     this.field_146639_s = var1.getSaveList();
/*  76: 80 */     Collections.sort(this.field_146639_s);
/*  77: 81 */     this.field_146640_r = -1;
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected String func_146621_a(int p_146621_1_)
/*  81:    */   {
/*  82: 86 */     return ((SaveFormatComparator)this.field_146639_s.get(p_146621_1_)).getFileName();
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected String func_146614_d(int p_146614_1_)
/*  86:    */   {
/*  87: 91 */     String var2 = ((SaveFormatComparator)this.field_146639_s.get(p_146614_1_)).getDisplayName();
/*  88: 93 */     if ((var2 == null) || (MathHelper.stringNullOrLengthZero(var2))) {
/*  89: 95 */       var2 = I18n.format("selectWorld.world", new Object[0]) + " " + (p_146614_1_ + 1);
/*  90:    */     }
/*  91: 98 */     return var2;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void func_146618_g()
/*  95:    */   {
/*  96:103 */     this.buttonList.add(this.field_146641_z = new NodusGuiButton(1, width / 2 - 154, height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
/*  97:104 */     this.buttonList.add(new NodusGuiButton(3, width / 2 + 4, height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/*  98:105 */     this.buttonList.add(this.field_146630_A = new NodusGuiButton(6, width / 2 - 154, height - 28, 72, 20, I18n.format("selectWorld.rename", new Object[0])));
/*  99:106 */     this.buttonList.add(this.field_146642_y = new NodusGuiButton(2, width / 2 - 76, height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
/* 100:107 */     this.buttonList.add(this.field_146631_B = new NodusGuiButton(7, width / 2 + 4, height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
/* 101:108 */     this.buttonList.add(new NodusGuiButton(0, width / 2 + 82, height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
/* 102:109 */     this.field_146641_z.enabled = false;
/* 103:110 */     this.field_146642_y.enabled = false;
/* 104:111 */     this.field_146630_A.enabled = false;
/* 105:112 */     this.field_146631_B.enabled = false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 109:    */   {
/* 110:117 */     if (p_146284_1_.enabled) {
/* 111:119 */       if (p_146284_1_.id == 2)
/* 112:    */       {
/* 113:121 */         String var2 = func_146614_d(this.field_146640_r);
/* 114:123 */         if (var2 != null)
/* 115:    */         {
/* 116:125 */           this.field_146643_x = true;
/* 117:126 */           GuiYesNo var3 = func_146623_a(this, var2, this.field_146640_r);
/* 118:127 */           this.mc.displayGuiScreen(var3);
/* 119:    */         }
/* 120:    */       }
/* 121:130 */       else if (p_146284_1_.id == 1)
/* 122:    */       {
/* 123:132 */         func_146615_e(this.field_146640_r);
/* 124:    */       }
/* 125:134 */       else if (p_146284_1_.id == 3)
/* 126:    */       {
/* 127:136 */         this.mc.displayGuiScreen(new GuiCreateWorld(this));
/* 128:    */       }
/* 129:138 */       else if (p_146284_1_.id == 6)
/* 130:    */       {
/* 131:140 */         this.mc.displayGuiScreen(new GuiRenameWorld(this, func_146621_a(this.field_146640_r)));
/* 132:    */       }
/* 133:142 */       else if (p_146284_1_.id == 0)
/* 134:    */       {
/* 135:144 */         this.mc.displayGuiScreen(this.field_146632_a);
/* 136:    */       }
/* 137:146 */       else if (p_146284_1_.id == 7)
/* 138:    */       {
/* 139:148 */         GuiCreateWorld var5 = new GuiCreateWorld(this);
/* 140:149 */         ISaveHandler var6 = this.mc.getSaveLoader().getSaveLoader(func_146621_a(this.field_146640_r), false);
/* 141:150 */         WorldInfo var4 = var6.loadWorldInfo();
/* 142:151 */         var6.flush();
/* 143:152 */         var5.func_146318_a(var4);
/* 144:153 */         this.mc.displayGuiScreen(var5);
/* 145:    */       }
/* 146:    */       else
/* 147:    */       {
/* 148:157 */         this.field_146638_t.func_148147_a(p_146284_1_);
/* 149:    */       }
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void func_146615_e(int p_146615_1_)
/* 154:    */   {
/* 155:164 */     this.mc.displayGuiScreen(null);
/* 156:166 */     if (!this.field_146634_i)
/* 157:    */     {
/* 158:168 */       this.field_146634_i = true;
/* 159:169 */       String var2 = func_146621_a(p_146615_1_);
/* 160:171 */       if (var2 == null) {
/* 161:173 */         var2 = "World" + p_146615_1_;
/* 162:    */       }
/* 163:176 */       String var3 = func_146614_d(p_146615_1_);
/* 164:178 */       if (var3 == null) {
/* 165:180 */         var3 = "World" + p_146615_1_;
/* 166:    */       }
/* 167:183 */       if (this.mc.getSaveLoader().canLoadWorld(var2)) {
/* 168:185 */         this.mc.launchIntegratedServer(var2, var3, null);
/* 169:    */       }
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void confirmClicked(boolean par1, int par2)
/* 174:    */   {
/* 175:192 */     if (this.field_146643_x)
/* 176:    */     {
/* 177:194 */       this.field_146643_x = false;
/* 178:196 */       if (par1)
/* 179:    */       {
/* 180:198 */         ISaveFormat var3 = this.mc.getSaveLoader();
/* 181:199 */         var3.flushCache();
/* 182:200 */         var3.deleteWorldDirectory(func_146621_a(par2));
/* 183:    */         try
/* 184:    */         {
/* 185:204 */           func_146627_h();
/* 186:    */         }
/* 187:    */         catch (AnvilConverterException var5)
/* 188:    */         {
/* 189:208 */           logger.error("Couldn't load level list", var5);
/* 190:    */         }
/* 191:    */       }
/* 192:212 */       this.mc.displayGuiScreen(this);
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void drawScreen(int par1, int par2, float par3)
/* 197:    */   {
/* 198:221 */     this.field_146638_t.func_148128_a(par1, par2, par3);
/* 199:222 */     drawCenteredString(this.fontRendererObj, this.field_146628_f, width / 2, 20, 16777215);
/* 200:223 */     super.drawScreen(par1, par2, par3);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static GuiYesNo func_146623_a(GuiScreen p_146623_0_, String p_146623_1_, int p_146623_2_)
/* 204:    */   {
/* 205:228 */     String var3 = I18n.format("selectWorld.deleteQuestion", new Object[0]);
/* 206:229 */     String var4 = "'" + p_146623_1_ + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]);
/* 207:230 */     String var5 = I18n.format("selectWorld.deleteButton", new Object[0]);
/* 208:231 */     String var6 = I18n.format("gui.cancel", new Object[0]);
/* 209:232 */     GuiYesNo var7 = new GuiYesNo(p_146623_0_, var3, var4, var5, var6, p_146623_2_);
/* 210:233 */     return var7;
/* 211:    */   }
/* 212:    */   
/* 213:    */   class List
/* 214:    */     extends GuiSlot
/* 215:    */   {
/* 216:    */     private static final String __OBFID = "CL_00000712";
/* 217:    */     
/* 218:    */     public List()
/* 219:    */     {
/* 220:242 */       super(GuiSelectWorld.width, GuiSelectWorld.height, 32, GuiSelectWorld.height - 64, 36);
/* 221:    */     }
/* 222:    */     
/* 223:    */     protected int getSize()
/* 224:    */     {
/* 225:247 */       return GuiSelectWorld.this.field_146639_s.size();
/* 226:    */     }
/* 227:    */     
/* 228:    */     protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_)
/* 229:    */     {
/* 230:252 */       GuiSelectWorld.this.field_146640_r = p_148144_1_;
/* 231:253 */       boolean var5 = (GuiSelectWorld.this.field_146640_r >= 0) && (GuiSelectWorld.this.field_146640_r < getSize());
/* 232:254 */       GuiSelectWorld.this.field_146641_z.enabled = var5;
/* 233:255 */       GuiSelectWorld.this.field_146642_y.enabled = var5;
/* 234:256 */       GuiSelectWorld.this.field_146630_A.enabled = var5;
/* 235:257 */       GuiSelectWorld.this.field_146631_B.enabled = var5;
/* 236:259 */       if ((p_148144_2_) && (var5)) {
/* 237:261 */         GuiSelectWorld.this.func_146615_e(p_148144_1_);
/* 238:    */       }
/* 239:    */     }
/* 240:    */     
/* 241:    */     protected boolean isSelected(int p_148131_1_)
/* 242:    */     {
/* 243:267 */       return p_148131_1_ == GuiSelectWorld.this.field_146640_r;
/* 244:    */     }
/* 245:    */     
/* 246:    */     protected int func_148138_e()
/* 247:    */     {
/* 248:272 */       return GuiSelectWorld.this.field_146639_s.size() * 36;
/* 249:    */     }
/* 250:    */     
/* 251:    */     protected void drawBackground()
/* 252:    */     {
/* 253:277 */       GuiSelectWorld.this.drawDefaultBackground();
/* 254:    */     }
/* 255:    */     
/* 256:    */     protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_)
/* 257:    */     {
/* 258:282 */       SaveFormatComparator var8 = (SaveFormatComparator)GuiSelectWorld.this.field_146639_s.get(p_148126_1_);
/* 259:283 */       String var9 = var8.getDisplayName();
/* 260:285 */       if ((var9 == null) || (MathHelper.stringNullOrLengthZero(var9))) {
/* 261:287 */         var9 = GuiSelectWorld.this.field_146637_u + " " + (p_148126_1_ + 1);
/* 262:    */       }
/* 263:290 */       String var10 = var8.getFileName();
/* 264:291 */       var10 = var10 + " (" + GuiSelectWorld.this.field_146633_h.format(new Date(var8.getLastTimePlayed()));
/* 265:292 */       var10 = var10 + ")";
/* 266:293 */       String var11 = "";
/* 267:295 */       if (var8.requiresConversion())
/* 268:    */       {
/* 269:297 */         var11 = GuiSelectWorld.this.field_146636_v + " " + var11;
/* 270:    */       }
/* 271:    */       else
/* 272:    */       {
/* 273:301 */         var11 = GuiSelectWorld.this.field_146635_w[var8.getEnumGameType().getID()];
/* 274:303 */         if (var8.isHardcoreModeEnabled()) {
/* 275:305 */           var11 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + EnumChatFormatting.RESET;
/* 276:    */         }
/* 277:308 */         if (var8.getCheatsEnabled()) {
/* 278:310 */           var11 = var11 + ", " + I18n.format("selectWorld.cheats", new Object[0]);
/* 279:    */         }
/* 280:    */       }
/* 281:314 */       GuiSelectWorld.drawString(GuiSelectWorld.this.fontRendererObj, var9, p_148126_2_ + 2, p_148126_3_ + 1, 16777215);
/* 282:315 */       GuiSelectWorld.drawString(GuiSelectWorld.this.fontRendererObj, var10, p_148126_2_ + 2, p_148126_3_ + 12, 8421504);
/* 283:316 */       GuiSelectWorld.drawString(GuiSelectWorld.this.fontRendererObj, var11, p_148126_2_ + 2, p_148126_3_ + 12 + 10, 8421504);
/* 284:    */     }
/* 285:    */   }
/* 286:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiSelectWorld
 * JD-Core Version:    0.7.0.1
 */